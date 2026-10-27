package Dragon;

import java.awt.Rectangle;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *  提示节奏控制器（弹窗的防抖与合并）
 *
 *  <p>解决的问题：页面操作很快时，每个步骤都弹一次窗会导致弹窗刚出现就被下一个顶掉，
 *  文字看不清、画面跳动，既没起到提示作用也失去美感。</p>
 *
 *  <p>这里把"每一次操作都弹"改成"有节奏地弹"，四层控制：</p>
 *  <ol>
 *      <li><b>防抖合并</b>：提示不立即弹出，先等 {@code debounce} 毫秒；期间又来的提示会覆盖上一条，
 *          最终只弹最后一条。慢速操作自然逐步弹，快速操作合并成一条。</li>
 *      <li><b>单实例复用</b>：始终复用同一个弹窗对象，新提示只做原地更新
 *          （{@link GDragon#update(GDragonOption)}），不新建窗口、不重复入场动画。</li>
 *      <li><b>最小驻留</b>：弹出后至少停留一段时间，时长按文字长度估算，保证能读完；
 *          期间来了新提示也只换内容，不会一闪而过。</li>
 *      <li><b>忙时抑制</b>：一秒内操作数达到阈值即判定为"操作密集"，此时暂不弹窗，
 *          等空闲后再弹一条（期间的提示已合并），密集过程全程安静。</li>
 *  </ol>
 *
 *  <p>全部工作都在后台调度线程与事件派发线程上完成，调用方（用例线程）只做一次
 *  {@code synchronized} 的字段赋值，不会拖慢用例。</p>
 *
 *  <pre>
 *  可调参数（系统属性，单位毫秒）：
 *  -Dcdtest.dragon.debounce=500   防抖窗口，越大越倾向合并
 *  -Dcdtest.dragon.dwell=1200     最小驻留基数，实际时长按文字长度上浮
 *  -Dcdtest.dragon.busy=4         一秒内达到该操作次数即进入密集期；0 表示不启用
 *  -Dcdtest.dragon.idle=800       密集期结束后再等多久才弹
 *  </pre>
 */
public final class GDragonGuide {

    /**
     *  系统属性：防抖窗口
     */
    public static final String SYS_DEBOUNCE = "cdtest.dragon.debounce";

    /**
     *  系统属性：最小驻留基数
     */
    public static final String SYS_DWELL = "cdtest.dragon.dwell";

    /**
     *  系统属性：操作密集阈值
     */
    public static final String SYS_BUSY = "cdtest.dragon.busy";

    /**
     *  系统属性：密集期后的空闲等待
     */
    public static final String SYS_IDLE = "cdtest.dragon.idle";

    /**
     *  默认防抖窗口
     */
    public static final int DEFAULT_DEBOUNCE_MS = 500;

    /**
     *  默认最小驻留基数
     */
    public static final int DEFAULT_DWELL_MS = 1200;

    /**
     *  默认操作密集阈值
     */
    public static final int DEFAULT_BUSY_OPS = 4;

    /**
     *  默认空闲等待
     */
    public static final int DEFAULT_IDLE_MS = 800;

    /**
     *  密集判定窗口
     */
    private static final long BUSY_WINDOW_MS = 1000;

    /**
     *  驻留下限，低于此值文字来不及读
     */
    private static final long MIN_DWELL_MS = 1200;

    /**
     *  驻留上限，避免长文本长时间挡住页面
     */
    private static final long MAX_DWELL_MS = 6000;

    /**
     *  距离上次更新超过该值时才启用打字机效果，避免文字反复重打
     */
    private static final long TYPING_GAP_MS = 800;

    /**
     *  几何信息输出通道
     *
     *  <p>Dragon 包不依赖日志框架，由调用方（Webdriver 层）接到自己的日志里；
     *  没人接的时候安静丢弃。</p>
     */
    private static volatile Consumer<String> logger = msg -> {
    };

    /**
     *  设置几何信息输出通道
     *
     *  @param logger 输出方法；为 {@code null} 时丢弃
     */
    public static void setLogger(Consumer<String> logger) {
        GDragonGuide.logger = logger == null ? msg -> {
        } : logger;
    }

    /**
     *  弹窗显示期间重新读取目标位置的间隔
     */
    private static final long TRACK_PERIOD_MS = 300;

    /**
     *  目标位移不超过该像素数就不重新摆位，避免窗口来回抖动
     */
    private static final int TRACK_TOLERANCE = 4;

    private static final GDragonGuide INSTANCE = new GDragonGuide();

    private final Object lock = new Object();

    /**
     *  最近一段时间内的操作时刻，用于判定操作密度
     */
    private final Deque<Long> recentOps = new ArrayDeque<>();

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread thread = new Thread(r, "GDragonGuide");
        thread.setDaemon(true);
        return thread;
    });

    /**
     *  待显示的最新提示
     */
    private String pendingMessage;

    /**
     *  待显示提示的动作
     */
    private GDragonAction pendingAction = GDragonAction.TALK;

    /**
     *  待显示提示指向的矩形
     */
    private Rectangle pendingRect;

    /**
     *  调用方指定的持续时间
     */
    private long pendingTime;

    /**
     *  防抖窗口内被合并掉的提示条数
     */
    private int pendingCount;

    /**
     *  待显示提示的目标位置提供者
     *
     *  <p>优先于 {@link #pendingRect}：弹窗往往在提交后几百毫秒才真正显示，
     *  期间元素可能已经被重排或滚动走，只有显示时现取的坐标才准。</p>
     */
    private Supplier<Rectangle> pendingLocator;

    /**
     *  延迟弹出任务
     */
    private ScheduledFuture<?> flushTask;

    /**
     *  当前弹窗的目标位置提供者；为 {@code null} 时目标固定不变
     */
    private Supplier<Rectangle> currentLocator;

    /**
     *  跟踪当前弹窗目标位置的任务
     */
    private ScheduledFuture<?> trackTask;

    /**
     *  当前弹窗，复用
     */
    private GDragon current;

    /**
     *  上次更新弹窗内容的时刻
     */
    private volatile long currentUpdatedAt;

    static {
        //兜底：任务异常中断、没走到正常收尾时，进程退出也能清掉未弹出的提示
        Runtime.getRuntime().addShutdownHook(new Thread(GDragonGuide::shutdown, "GDragonGuideShutdown"));
    }

    private GDragonGuide() {
    }

    /**
     *  获得节奏控制器
     *
     *  @return 节奏控制器
     */
    public static GDragonGuide getInstance() {
        return INSTANCE;
    }

    /**
     *  进程退出时清理资源
     */
    private static void shutdown() {
        try {
            INSTANCE.reset();
            INSTANCE.scheduler.shutdownNow();
        } catch (Exception ignored) {
            //退出阶段不做补救，忽略即可
        }
    }

    /**
     *  提交一条指向屏幕矩形的提示
     *
     *  @param message 提示内容；为空时忽略
     *  @param action 动作
     *  @param rect 目标屏幕矩形；为 {@code null} 时不指向
     *  @param mTime 调用方期望的持续时间，毫秒；为 0 时按文字长度自动估算
     */
    public void tip(String message, GDragonAction action, Rectangle rect, long mTime) {
        if (message == null || message.trim().isEmpty()) {
            return;
        }
        long now = System.currentTimeMillis();
        synchronized (lock) {
            recentOps.addLast(now);
            while (recentOps.size() > 64) {
                recentOps.pollFirst();
            }
            pendingMessage = message.trim();
            pendingAction = action == null ? GDragonAction.TALK : action;
            pendingRect = rect;
            pendingLocator = null;
            pendingTime = mTime;
            pendingCount++;
            if (flushTask == null) {
                flushTask = scheduler.schedule(this::flush, debounceMs(), TimeUnit.MILLISECONDS);
            }
        }
    }

    /**
     *  提交一条指向元素的提示，目标位置在真正显示时才取
     *
     *  <p>与 {@link #tip(String, GDragonAction, Rectangle, long)} 的区别是位置不再快照：
     *  连续操作时弹窗会被防抖与密集期推迟，等它真的显示出来，元素可能已经不在原处，
     *  此时由 {@code locator} 现取一次坐标，箭头才会指到当前所在。</p>
     *
     *  <p>弹窗显示期间还会按 {@link #TRACK_PERIOD_MS} 复查，元素滚动或页面重排后
     *  窗口会跟着挪，位移小于 {@link #TRACK_TOLERANCE} 时不动，避免抖动。</p>
     *
     *  @param message 提示内容；为空时忽略
     *  @param action 动作
     *  @param locator 目标位置提供者；返回 {@code null} 或抛异常时退化为不指向
     *  @param mTime 调用方期望的持续时间，毫秒；为 0 时按文字长度自动估算
     */
    public void tip(String message, GDragonAction action, Supplier<Rectangle> locator, long mTime) {
        if (message == null || message.trim().isEmpty()) {
            return;
        }
        long now = System.currentTimeMillis();
        synchronized (lock) {
            recentOps.addLast(now);
            while (recentOps.size() > 64) {
                recentOps.pollFirst();
            }
            pendingMessage = message.trim();
            pendingAction = action == null ? GDragonAction.TALK : action;
            pendingRect = null;
            pendingLocator = locator;
            pendingTime = mTime;
            pendingCount++;
            if (flushTask == null) {
                flushTask = scheduler.schedule(this::flush, debounceMs(), TimeUnit.MILLISECONDS);
            }
        }
        //防抖与合并只该压文字，不该压位置：页面上红框已经画到新元素了，
        //弹窗还停在上一处，看起来就是"箭头没指对"。位置必须立刻跟过去
        followTarget(locator);
    }

    /**
     *  让已经显示的弹窗立刻改指向新的元素
     *
     * <p>提交新提示时调用。此时文案仍在防抖合并中，但窗口位置不能再等：
     * 上游的高亮框是同步画的，位置一滞后就会出现"框在登录框上、弹窗指着空白处"。</p>
     *
     * <p>取坐标要跑脚本，放在锁外执行，避免拖住其它线程的提交。</p>
     *
     * @param locator 新目标的位置提供者；为 {@code null} 时不处理
     */
    private void followTarget(Supplier<Rectangle> locator) {
        if (locator == null) {
            return;
        }
        GDragon window;
        synchronized (lock) {
            window = current;
        }
        if (window == null || window.isClosed()) {
            return;
        }
        Rectangle rect = locate(locator);
        if (rect == null) {
            return;
        }
        window.setTarget(rect);
        synchronized (lock) {
            currentLocator = locator;
        }
    }

    /**
     *  提交一条指向屏幕矩形的提示
     *
     *  @param message 提示内容
     *  @param action 动作
     *  @param x 目标左上角横坐标
     *  @param y 目标左上角纵坐标
     *  @param width 目标宽
     *  @param height 目标高
     *  @param mTime 期望持续时间，毫秒
     */
    public void tip(String message, GDragonAction action, int x, int y, int width, int height, long mTime) {
        tip(message, action, new Rectangle(x, y, width, height), mTime);
    }

    /**
     *  提交一条不指向元素的提示
     *
     *  @param message 提示内容
     *  @param action 动作
     *  @param mTime 期望持续时间，毫秒
     */
    public void tip(String message, GDragonAction action, long mTime) {
        tip(message, action, (Rectangle) null, mTime);
    }

    /**
     *  是否处于操作密集期
     *
     *  <p>密集期内弹窗会被推迟，调用方可据此缩短高亮框等视觉元素的持续时间。</p>
     *
     *  @return 处于密集期返回 {@code true}
     */
    public boolean isBusy() {
        synchronized (lock) {
            return busyLocked(System.currentTimeMillis());
        }
    }

    /**
     *  立即弹出待显示的提示，跳过防抖与密集期等待
     *
     *  <p>供调试与"最后一步必须看到"的场景使用。</p>
     */
    public void flushNow() {
        synchronized (lock) {
            if (flushTask != null) {
                flushTask.cancel(false);
                flushTask = null;
            }
            if (pendingMessage == null) {
                return;
            }
            recentOps.clear();
            showLocked(System.currentTimeMillis());
        }
    }

    /**
     *  清空待显示提示并关闭当前弹窗
     *
     *  <p>用例结束或切换场景时调用，避免残留弹窗。</p>
     */
    public void reset() {
        GDragon window;
        synchronized (lock) {
            if (flushTask != null) {
                flushTask.cancel(false);
                flushTask = null;
            }
            if (trackTask != null) {
                trackTask.cancel(false);
                trackTask = null;
            }
            pendingMessage = null;
            pendingRect = null;
            pendingLocator = null;
            currentLocator = null;
            pendingCount = 0;
            pendingTime = 0;
            recentOps.clear();
            window = current;
            current = null;
        }
        if (window != null) {
            window.close(GDragonListener.Reason.CLOSE);
        }
    }

    /**
     *  获得当前弹窗显示的文字
     *
     *  @return 弹窗文字；无弹窗返回 {@code null}
     */
    public String currentMessage() {
        synchronized (lock) {
            return current == null ? null : current.getOption().getMessage();
        }
    }

    /**
     *  输出节奏控制状态，便于调试核对合并是否生效
     *
     *  @return 状态摘要
     */
    public String stats() {
        synchronized (lock) {
            return "GDragonGuide{debounce=" + debounceMs()
                    + ", dwell=" + dwellMs()
                    + ", busy=" + busyOps()
                    + ", idle=" + idleMs()
                    + ", merged=" + pendingCount
                    + ", recentOps=" + recentOps.size()
                    + ", window=" + (current == null ? "none" : (current.isClosed() ? "closed" : "alive"))
                    + '}';
        }
    }

    /**
     *  防抖到期后真正弹出
     */
    private void flush() {
        synchronized (lock) {
            flushTask = null;
            if (pendingMessage == null) {
                return;
            }
            long now = System.currentTimeMillis();
            if (busyLocked(now)) {
                //操作密集：继续等，期间的提示持续合并，等停下来后只弹一条
                flushTask = scheduler.schedule(this::flush, idleMs(), TimeUnit.MILLISECONDS);
                return;
            }
            showLocked(now);
        }
    }

    /**
     *  弹出（或复用）弹窗
     *
     *  @param now 当前时刻
     */
    private void showLocked(long now) {
        String message = pendingMessage;
        GDragonAction action = pendingAction;
        Supplier<Rectangle> locator = pendingLocator;
        //显示时才取坐标：提交到显示之间隔着防抖与密集等待，元素很可能已经不在原处
        Rectangle rect = locator != null ? locate(locator) : pendingRect;
        long mTime = pendingTime;
        //清零前留一份：一次弹窗可能代表好几步操作，日志里看不到就会以为"那一步没弹"
        int merged = pendingCount;
        pendingMessage = null;
        pendingRect = null;
        pendingLocator = null;
        pendingCount = 0;
        pendingTime = 0;

        long dwell = dwellOf(message, mTime);
        //刚更新过就不做打字机，避免文字反复重打
        boolean typing = now - currentUpdatedAt > TYPING_GAP_MS;
        GDragonOption.Builder builder = GDragonOption.builder()
                .message(message)
                .action(action)
                .typing(typing)
                .time((int) dwell);
        if (rect != null) {
            builder.targetRect(rect.x, rect.y, rect.width, rect.height);
        }
        GDragonOption option = builder.build();
        currentUpdatedAt = now;

        GDragon window = current;
        if (window != null && !window.isClosed()) {
            window.update(option);
        } else {
            window = GDragonService.getInstance().show(option);
            current = window;
        }
        currentLocator = locator;
        startTrackingLocked();
        logGeometry(window, rect, merged);
    }

    /**
     *  记录一次弹窗几何信息
     *
     *  <p>窗口建好后还要等 EDT 构建并摆放箭头，所以延迟一点再读。
     *  这行日志是"元素在哪、窗口在哪、箭头在哪"的唯一凭据，
     *  位置不对时直接看这三个矩形，就能分清是坐标算错还是摆放算错。</p>
     *
     *  @param window 弹窗窗口
     *  @param rect 元素屏幕矩形
     *  @param merged 这一次代表了几步操作；大于 1 说明前面几步被合并掉了
     */
    private void logGeometry(GDragon window, Rectangle rect, int merged) {
        if (window == null) {
            return;
        }
        scheduler.schedule(() -> {
            try {
                if (window.isClosed()) {
                    return;
                }
                logger.accept("[弹窗]元素=" + rect + " 窗口=" + window.getBounds()
                        + " 箭头=" + window.getArrowBounds() + " 合并=" + merged);
            } catch (Throwable ignored) {
                //日志失败不能影响弹窗
            }
        }, 400, TimeUnit.MILLISECONDS);
    }

    /**
     *  取一次目标位置
     *
     *  <p>调用方给的通常是 WebElement 换算，取不到时返回 {@code null} 或直接抛异常，
     *  这里一律退化为“不指向”，不能因为取坐标失败就不弹窗。</p>
     *
     *  @param locator 目标位置提供者
     *  @return 目标矩形；取不到返回 {@code null}
     */
    private static Rectangle locate(Supplier<Rectangle> locator) {
        try {
            return locator.get();
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     *  开始跟踪当前弹窗的目标位置
     */
    private void startTrackingLocked() {
        if (trackTask != null) {
            trackTask.cancel(false);
            trackTask = null;
        }
        if (currentLocator == null) {
            return;
        }
        trackTask = scheduler.scheduleWithFixedDelay(this::track,
                TRACK_PERIOD_MS, TRACK_PERIOD_MS, TimeUnit.MILLISECONDS);
    }

    /**
     *  复查目标位置，变动超过容差就把弹窗挪过去
     */
    private void track() {
        GDragon window;
        Supplier<Rectangle> locator;
        synchronized (lock) {
            if (current == null || current.isClosed()) {
                if (trackTask != null) {
                    trackTask.cancel(false);
                    trackTask = null;
                }
                currentLocator = null;
                return;
            }
            window = current;
            locator = currentLocator;
        }
        if (locator == null) {
            return;
        }
        Rectangle next = locate(locator);
        if (next == null) {
            return;
        }
        Rectangle old = window.getTargetRect();
        if (old != null
                && Math.abs(old.x - next.x) <= TRACK_TOLERANCE
                && Math.abs(old.y - next.y) <= TRACK_TOLERANCE
                && Math.abs(old.width - next.width) <= TRACK_TOLERANCE
                && Math.abs(old.height - next.height) <= TRACK_TOLERANCE) {
            return;
        }
        window.setTarget(next);
    }

    /**
     *  判定是否处于操作密集期
     *
     *  @param now 当前时刻
     *  @return 密集返回 {@code true}
     */
    private boolean busyLocked(long now) {
        int threshold = busyOps();
        if (threshold <= 0) {
            return false;
        }
        while (!recentOps.isEmpty() && now - recentOps.peekFirst() > BUSY_WINDOW_MS) {
            recentOps.pollFirst();
        }
        return recentOps.size() >= threshold;
    }

    /**
     *  按文字长度估算驻留时长
     *
     *  @param message 提示内容
     *  @param requested 调用方期望时长
     *  @return 驻留时长，毫秒
     */
    private long dwellOf(String message, long requested) {
        long byText = 600 + message.length() * 90L;
        long dwell = Math.max(dwellMs(), byText);
        if (requested > dwell) {
            dwell = requested;
        }
        return Math.min(Math.max(dwell, MIN_DWELL_MS), MAX_DWELL_MS);
    }

    /**
     *  获得防抖窗口
     *
     *  @return 防抖窗口，毫秒
     */
    public int debounceMs() {
        return intProp(SYS_DEBOUNCE, DEFAULT_DEBOUNCE_MS);
    }

    /**
     *  获得最小驻留基数
     *
     *  @return 驻留基数，毫秒
     */
    public int dwellMs() {
        return intProp(SYS_DWELL, DEFAULT_DWELL_MS);
    }

    /**
     *  获得操作密集阈值
     *
     *  @return 一秒内的操作次数阈值
     */
    public int busyOps() {
        return intProp(SYS_BUSY, DEFAULT_BUSY_OPS);
    }

    /**
     *  获得空闲等待
     *
     *  @return 空闲等待，毫秒
     */
    public int idleMs() {
        return intProp(SYS_IDLE, DEFAULT_IDLE_MS);
    }

    private static int intProp(String name, int defaultValue) {
        String value = System.getProperty(name);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
