package Dragon;

import java.awt.Rectangle;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
     *  延迟弹出任务
     */
    private ScheduledFuture<?> flushTask;

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
            pendingTime = mTime;
            pendingCount++;
            if (flushTask == null) {
                flushTask = scheduler.schedule(this::flush, debounceMs(), TimeUnit.MILLISECONDS);
            }
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
        tip(message, action, null, mTime);
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
            pendingMessage = null;
            pendingRect = null;
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
        Rectangle rect = pendingRect;
        long mTime = pendingTime;
        pendingMessage = null;
        pendingRect = null;
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

        if (current != null && !current.isClosed()) {
            current.update(option);
            return;
        }
        current = GDragonService.getInstance().show(option);
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
