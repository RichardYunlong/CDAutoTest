package Dragon;

import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 *  人形弹窗统一入口
 *
 *  <p>改造前，日志模块每写一条日志就 {@code new Thread(new GDragon(...))}，
 *  弹窗数量、频率、生命周期都不可控，也无法在不启动整套框架的情况下预览效果。
 *  现在所有调用方统一走这里，得到三件事：</p>
 *  <ol>
 *      <li><b>统一开关</b>：总开关、提示弹窗开关、欢迎/再见界面开关，
 *          均可用配置文件或 {@code -Dcdtest.dragon.*} 系统属性控制；</li>
 *      <li><b>统一限流</b>：限制同时显示数量与最小弹出间隔，避免刷屏；</li>
 *      <li><b>统一隔离</b>：无界面环境（CI、headless）自动降级为只上报事件，不创建窗体。</li>
 *  </ol>
 *
 *  <p>独立调试建议走 {@link GDragonDebug} 或 {@link #show(GDragonOption)}（忽略提示开关强制显示）。</p>
 *
 *  @see GDragon
 *  @see GDragonOption
 *  @see GDragonDebug
 */
public final class GDragonService {

    /**
     *  系统属性前缀
     */
    private static final String SYS_PREFIX = "cdtest.dragon.";

    /**
     *  单例
     */
    private static final GDragonService INSTANCE = new GDragonService();

    /**
     *  当前存活的提示弹窗
     */
    private final List<GDragon> active = new ArrayList<>();

    /**
     *  当前存活的欢迎界面
     */
    private final List<GDragonII> activeWelcome = new ArrayList<>();

    /**
     *  上次弹出时间
     */
    private final AtomicLong lastShowAt = new AtomicLong(0);

    /**
     *  已弹出数量
     */
    private final AtomicInteger shownCount = new AtomicInteger();

    /**
     *  被跳过数量
     */
    private final AtomicInteger skippedCount = new AtomicInteger();

    /**
     *  总开关
     */
    private volatile boolean enabled = boolProp("enabled", true);

    /**
     *  提示弹窗开关，由 sys.DragonShow 控制
     */
    private volatile boolean tipEnabled = boolProp("tip", false);

    /**
     *  欢迎界面开关，由 sys.DragonWelcome 控制
     */
    private volatile boolean welcomeEnabled = boolProp("welcome", true);

    /**
     *  再见界面开关，由 sys.DragonBye 控制
     */
    private volatile boolean byeEnabled = boolProp("bye", true);

    /**
     *  同时显示的提示弹窗数量上限
     */
    private volatile int maxVisible = intProp("max", 3);

    /**
     *  两次弹窗的最小间隔，单位毫秒
     */
    private volatile long minIntervalMs = intProp("interval", 200);

    /**
     *  是否播放动画，关闭时只显示静态首帧
     */
    private volatile boolean animationEnabled = boolProp("animate", true);

    /**
     *  是否播报语音，默认关闭
     */
    private volatile boolean voiceEnabled = boolProp("voice", false);

    /**
     *  语音实现
     */
    private volatile GDragonVoice voice = GDragonVoice.silent();

    /**
     *  事件回调
     */
    private volatile GDragonListener listener = GDragonListener.console();

    private GDragonService() {
    }

    /**
     *  获得单例
     *
     *  @return 人形弹窗统一入口
     */
    public static GDragonService getInstance() {
        return INSTANCE;
    }

    /**
     *  当前环境是否支持显示窗体
     *
     *  @return 非 headless 环境返回 {@code true}
     */
    public static boolean isGuiAvailable() {
        return !GraphicsEnvironment.isHeadless();
    }

    /**
     *  弹窗是否可用
     *
     *  @return 总开关打开且环境支持窗体时返回 {@code true}
     */
    public boolean isAvailable() {
        return enabled && isGuiAvailable();
    }

    /**
     *  弹出提示窗
     *
     *  <p>受提示弹窗开关、并发上限与最小间隔约束。</p>
     *
     *  @param message 提示内容
     *  @return 弹窗对象；被跳过或不可用时返回 {@code null}
     */
    public GDragon showTip(String message) {
        return showTip(GDragonOption.of(message));
    }

    /**
     *  弹出提示窗
     *
     *  @param message 提示内容
     *  @param mTime 持续时间，单位毫秒
     *  @return 弹窗对象；被跳过或不可用时返回 {@code null}
     */
    public GDragon showTip(String message, int mTime) {
        return showTip(GDragonOption.builder().message(message).time(mTime).build());
    }

    /**
     *  弹出带动作的提示窗
     *
     *  <p>用例执行过程中提醒"正在点某个元素""这一步成功了"等场景使用。</p>
     *
     *  @param message 提示内容
     *  @param action 动作
     *  @return 弹窗对象；被跳过或不可用时返回 {@code null}
     */
    public GDragon showTip(String message, GDragonAction action) {
        return showTip(GDragonOption.builder().message(message).action(action).build());
    }

    /**
     *  弹出指向某个屏幕坐标的提示窗
     *
     *  <p>坐标由调用方用 {@code element.getRect()} 换算得到，Dragon 包不依赖 WebDriver。</p>
     *
     *  @param message 提示内容
     *  @param action 动作
     *  @param targetX 目标横坐标
     *  @param targetY 目标纵坐标
     *  @return 弹窗对象；被跳过或不可用时返回 {@code null}
     */
    public GDragon showTip(String message, GDragonAction action, int targetX, int targetY) {
        return showTip(GDragonOption.builder().message(message).action(action).target(targetX, targetY).build());
    }

    /**
     *  弹出指向某个屏幕矩形的提示窗
     *
     *  <p>传入完整矩形（网页元素的位置与大小）时，弹窗会自动挑选一个不压住该矩形的方位，
     *  适合 Selenium 操作元素时"一边红框高亮、一边用角色指向它"的场景。</p>
     *
     *  @param message 提示内容
     *  @param action 动作
     *  @param targetX 目标左上角横坐标
     *  @param targetY 目标左上角纵坐标
     *  @param targetWidth 目标宽
     *  @param targetHeight 目标高
     *  @return 弹窗对象；被跳过或不可用时返回 {@code null}
     */
    public GDragon showTip(String message, GDragonAction action,
                           int targetX, int targetY, int targetWidth, int targetHeight) {
        return showTip(GDragonOption.builder()
                .message(message)
                .action(action)
                .targetRect(targetX, targetY, targetWidth, targetHeight)
                .build());
    }

    /**
     *  弹出提示窗
     *
     *  @param option 显示参数
     *  @return 弹窗对象；被跳过或不可用时返回 {@code null}
     */
    public GDragon showTip(GDragonOption option) {
        return doShow(option, false);
    }

    /**
     *  强制弹出提示窗
     *
     *  <p>忽略提示弹窗开关，仅受总开关与运行环境约束；供调试入口与演示使用。</p>
     *
     *  @param option 显示参数
     *  @return 弹窗对象；不可用时返回 {@code null}
     */
    public GDragon show(GDragonOption option) {
        return doShow(option, true);
    }

    /**
     *  显示欢迎界面
     *
     *  @param message 欢迎语
     *  @return 欢迎界面对象；被跳过或不可用时返回 {@code null}
     */
    public GDragonII showWelcome(String message) {
        if (!isAvailable() || !welcomeEnabled) {
            skip(GDragonOption.of(message), "欢迎界面开关未开启");
            return null;
        }
        prune();
        GDragonII window = new GDragonII(message, 0, 0);
        window.setListener(listener);
        activeWelcome.add(window);
        shownCount.incrementAndGet();
        return window;
    }

    /**
     *  显示再见界面
     *
     *  @param message 再见语
     *  @param mTime 持续时间，单位毫秒
     *  @return 弹窗对象；被跳过或不可用时返回 {@code null}
     */
    public GDragon showBye(String message, int mTime) {
        if (!isAvailable() || !byeEnabled) {
            skip(GDragonOption.builder().message(message).time(mTime).build(), "再见界面开关未开启");
            return null;
        }
        return doShow(GDragonOption.builder().message(message).time(mTime).build(), true);
    }

    /**
     *  关闭全部弹窗
     *
     *  @return 本次关闭的弹窗数量
     */
    public int closeAll() {
        int closed = 0;
        synchronized (active) {
            for (GDragon window : active) {
                window.close(GDragonListener.Reason.CLOSE_ALL);
                closed++;
            }
            active.clear();
        }
        synchronized (activeWelcome) {
            for (GDragonII window : activeWelcome) {
                window.close(GDragonListener.Reason.CLOSE_ALL);
                closed++;
            }
            activeWelcome.clear();
        }
        return closed;
    }

    /**
     *  清理已关闭的弹窗
     *
     *  @return 清理数量
     */
    public int prune() {
        int removed = 0;
        synchronized (active) {
            Iterator<GDragon> iterator = active.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().isClosed()) {
                    iterator.remove();
                    removed++;
                }
            }
        }
        synchronized (activeWelcome) {
            Iterator<GDragonII> iterator = activeWelcome.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().isClosed()) {
                    iterator.remove();
                    removed++;
                }
            }
        }
        return removed;
    }

    /**
     *  获得当前存活的提示弹窗数量
     *
     *  @return 存活数量
     */
    public int visibleCount() {
        prune();
        synchronized (active) {
            return active.size();
        }
    }

    /**
     *  输出运行统计，便于调试时核对限流是否生效
     *
     *  @return 统计信息
     */
    public String stats() {
        return "GDragonService{gui=" + isGuiAvailable()
                + ", enabled=" + enabled
                + ", tip=" + tipEnabled
                + ", welcome=" + welcomeEnabled
                + ", bye=" + byeEnabled
                + ", maxVisible=" + maxVisible
                + ", minIntervalMs=" + minIntervalMs
                + ", animate=" + animationEnabled
                + ", voice=" + voiceEnabled
                + ", visible=" + visibleCount()
                + ", shown=" + shownCount.get()
                + ", skipped=" + skippedCount.get()
                + '}';
    }

    /**
     *  按配置文件应用开关
     *
     *  <p>系统属性优先级高于配置文件：已用 {@code -Dcdtest.dragon.*} 显式指定的项保持原值，
     *  这样临时调试不必改动配置文件。</p>
     *
     *  @param tip 提示弹窗开关，对应 sys.DragonShow
     *  @param welcome 欢迎界面开关，对应 sys.DragonWelcome
     *  @param bye 再见界面开关，对应 sys.DragonBye
     */
    public void applyConfig(boolean tip, boolean welcome, boolean bye) {
        if (System.getProperty(SYS_PREFIX + "tip") == null) {
            this.tipEnabled = tip;
        }
        if (System.getProperty(SYS_PREFIX + "welcome") == null) {
            this.welcomeEnabled = welcome;
        }
        if (System.getProperty(SYS_PREFIX + "bye") == null) {
            this.byeEnabled = bye;
        }
    }

    /**
     *  获得总开关
     *
     *  @return 总开关状态
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     *  设置总开关
     *
     *  @param enabled 总开关
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     *  获得提示弹窗开关
     *
     *  @return 提示弹窗开关状态
     */
    public boolean isTipEnabled() {
        return tipEnabled;
    }

    /**
     *  设置提示弹窗开关，对应配置项 sys.DragonShow
     *
     *  @param tipEnabled 提示弹窗开关
     */
    public void setTipEnabled(boolean tipEnabled) {
        this.tipEnabled = tipEnabled;
    }

    /**
     *  获得欢迎界面开关
     *
     *  @return 欢迎界面开关状态
     */
    public boolean isWelcomeEnabled() {
        return welcomeEnabled;
    }

    /**
     *  设置欢迎界面开关，对应配置项 sys.DragonWelcome
     *
     *  @param welcomeEnabled 欢迎界面开关
     */
    public void setWelcomeEnabled(boolean welcomeEnabled) {
        this.welcomeEnabled = welcomeEnabled;
    }

    /**
     *  获得再见界面开关
     *
     *  @return 再见界面开关状态
     */
    public boolean isByeEnabled() {
        return byeEnabled;
    }

    /**
     *  设置再见界面开关，对应配置项 sys.DragonBye
     *
     *  @param byeEnabled 再见界面开关
     */
    public void setByeEnabled(boolean byeEnabled) {
        this.byeEnabled = byeEnabled;
    }

    /**
     *  获得同时显示数量上限
     *
     *  @return 数量上限
     */
    public int getMaxVisible() {
        return maxVisible;
    }

    /**
     *  设置同时显示数量上限
     *
     *  @param maxVisible 数量上限；小于1时按1处理
     */
    public void setMaxVisible(int maxVisible) {
        this.maxVisible = Math.max(maxVisible, 1);
    }

    /**
     *  获得最小弹出间隔
     *
     *  @return 最小间隔，单位毫秒
     */
    public long getMinIntervalMs() {
        return minIntervalMs;
    }

    /**
     *  设置最小弹出间隔
     *
     *  @param minIntervalMs 最小间隔，单位毫秒
     */
    public void setMinIntervalMs(long minIntervalMs) {
        this.minIntervalMs = Math.max(minIntervalMs, 0);
    }

    /**
     *  设置事件回调
     *
     *  @param listener 事件回调
     */
    public void setListener(GDragonListener listener) {
        this.listener = listener == null ? GDragonListener.silent() : listener;
    }

    /**
     *  获得事件回调
     *
     *  @return 事件回调
     */
    public GDragonListener getListener() {
        return listener;
    }

    /**
     *  获得已弹出数量
     *
     *  @return 已弹出数量
     */
    public int getShownCount() {
        return shownCount.get();
    }

    /**
     *  获得被跳过数量
     *
     *  @return 被跳过数量
     */
    public int getSkippedCount() {
        return skippedCount.get();
    }

    /**
     *  执行弹窗显示
     *
     *  @param option 显示参数
     *  @param force 是否忽略提示开关
     *  @return 弹窗对象；被跳过返回 {@code null}
     */
    private GDragon doShow(GDragonOption option, boolean force) {
        GDragonOption target = option == null ? GDragonOption.defaults() : option;

        if (!isAvailable()) {
            skip(target, "弹窗总开关已关闭或当前为无界面环境");
            return null;
        }
        if (!force && !tipEnabled) {
            skip(target, "提示弹窗开关未开启(sys.DragonShow)");
            return null;
        }

        prune();
        synchronized (active) {
            if (active.size() >= Math.max(maxVisible, 1)) {
                skip(target, "同时显示数量已达上限 " + maxVisible);
                return null;
            }
        }

        long now = System.currentTimeMillis();
        long last = lastShowAt.get();
        if (!force && last > 0 && now - last < minIntervalMs) {
            skip(target, "弹出过于频繁，间隔需大于 " + minIntervalMs + "ms");
            return null;
        }
        lastShowAt.set(now);

        GDragon window = new GDragon(applyPolicy(target), listener);
        window.setVoice(resolveVoice(target));
        synchronized (active) {
            active.add(window);
        }
        window.display();
        shownCount.incrementAndGet();
        return window;
    }

    /**
     *  应用降级策略：关闭动画时统一改为静态显示
     *
     *  @param option 原始参数
     *  @return 应用策略后的参数
     */
    private GDragonOption applyPolicy(GDragonOption option) {
        return animationEnabled ? option : option.withAnimate(false);
    }

    /**
     *  确定语音实现
     *
     *  @param option 显示参数
     *  @return 语音实现；开关关闭或无语音文本时返回静默实现
     */
    private GDragonVoice resolveVoice(GDragonOption option) {
        if (!voiceEnabled || option.getVoice() == null || option.getVoice().trim().isEmpty()) {
            return GDragonVoice.silent();
        }
        if (voice == null || !voice.isAvailable()) {
            voice = GDragonVoice.system();
        }
        return voice;
    }

    /**
     *  后台预热素材，避免首次弹窗时因解码产生卡顿
     */
    public void preloadSkin() {
        Thread thread = new Thread(() -> GDragonSkin.getDefault(), "GDragonSkinPreload");
        thread.setDaemon(true);
        thread.start();
    }

    /**
     *  获得动画开关
     *
     *  @return 动画开关状态
     */
    public boolean isAnimationEnabled() {
        return animationEnabled;
    }

    /**
     *  设置动画开关
     *
     *  @param animationEnabled 动画开关
     */
    public void setAnimationEnabled(boolean animationEnabled) {
        this.animationEnabled = animationEnabled;
    }

    /**
     *  获得语音开关
     *
     *  @return 语音开关状态
     */
    public boolean isVoiceEnabled() {
        return voiceEnabled;
    }

    /**
     *  设置语音开关
     *
     *  <p>语音默认关闭（{@code -Dcdtest.dragon.voice=true} 打开），
     *  因为它是最容易拖慢执行、也最容易干扰办公环境的一项。</p>
     *
     *  @param voiceEnabled 语音开关
     */
    public void setVoiceEnabled(boolean voiceEnabled) {
        this.voiceEnabled = voiceEnabled;
    }

    /**
     *  设置语音实现
     *
     *  @param voice 语音实现
     */
    public void setVoice(GDragonVoice voice) {
        this.voice = voice == null ? GDragonVoice.silent() : voice;
    }

    /**
     *  获得语音实现
     *
     *  @return 语音实现
     */
    public GDragonVoice getVoice() {
        return voice;
    }

    /**
     *  上报一次"被跳过"
     *
     *  @param option 显示参数
     *  @param reason 跳过原因
     */
    private void skip(GDragonOption option, String reason) {
        skippedCount.incrementAndGet();
        listener.onSkipped(option, reason);
    }

    /**
     *  读取布尔型系统属性
     *
     *  @param name 属性名（不含前缀）
     *  @param defaultValue 缺省值
     *  @return 属性值或缺省值
     */
    private static boolean boolProp(String name, boolean defaultValue) {
        String value = System.getProperty(SYS_PREFIX + name);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value.trim());
    }

    /**
     *  读取整数型系统属性
     *
     *  @param name 属性名（不含前缀）
     *  @param defaultValue 缺省值
     *  @return 属性值或缺省值
     */
    private static int intProp(String name, int defaultValue) {
        String value = System.getProperty(SYS_PREFIX + name);
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
