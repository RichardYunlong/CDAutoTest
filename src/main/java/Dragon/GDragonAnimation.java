package Dragon;

import java.awt.image.BufferedImage;

/**
 *  动作播放控制
 *
 *  <p>负责状态机与帧号换算：切换动作、按时间算当前帧。每个动作只在<b>自己这套帧</b>里
 *  往复循环，不再播完回落到 idle：不同动作的素材不是同一张脸，切换会明显突变。</p>
 *
 *  <p>它不含定时器、不碰界面，便于单独测试与在 {@link GDragonStage} 之外的场景复用。</p>
 *
 *  <p>帧号按"经过时间 × 帧率"计算，而不是每次加一，这样掉帧时动作速度依然稳定，
 *  配合 {@link GDragonStage} 的降帧策略可以做到"变卡不变慢"。</p>
 *
 *  @see GDragonStage
 *  @see GDragonAction
 */
public final class GDragonAnimation {

    /**
     *  系统属性：动作速度倍率
     */
    public static final String SYS_MOTION = "cdtest.dragon.motion";

    /**
     *  系统属性：动作幅度
     */
    public static final String SYS_AMPLITUDE = "cdtest.dragon.amplitude";

    /**
     *  默认动作速度倍率
     *
     *  <p>1.0 是素材原速。人物素材是按正常速度渲染的序列帧，原速播放时动作偏快，
     *  在自动化提示里显得抢眼，因此统一放慢到 0.8：动作仍能看清，但不再急。</p>
     *
     *  <p>可用 {@code -Dcdtest.dragon.motion=0.5} 覆盖，无需改代码。</p>
     */
    public static final double DEFAULT_MOTION = 0.8;

    /**
     *  默认动作幅度
     *
     *  <p>序列帧的姿态是渲染时就定死的，收敛幅度的办法是少走几帧：0.3 时只走出
     *  全程三成的姿态。现有素材是"站立→动作→回站立"的完整过程，动作峰值在
     *  中段，截掉后段就看不到挥手、点击、点赞这些关键姿态，观感像"没播完"，
     *  因此默认走满全程（1.0）。</p>
     *
     *  <p>1.0 时 {@link #displayIndex(long, GDragonFrames)} 直接返回原帧号，一次性
     *  动作播完末帧正好满足 {@code index >= size} 而回落 idle，不会再卡在半程不动。</p>
     *
     *  <p>可用 {@code -Dcdtest.dragon.amplitude=0.5} 覆盖，无需改代码。</p>
     *
     *  @see #displayIndex(long, GDragonFrames)
     */
    public static final double DEFAULT_AMPLITUDE = 1.0;

    /**
     *  素材
     */
    private final GDragonSkin skin;

    /**
     *  当前动作
     */
    private volatile GDragonAction action = GDragonAction.IDLE;

    /**
     *  当前动作起始时间
     */
    private volatile long startedAt = System.currentTimeMillis();

    /**
     *  累计暂停时长
     */
    private volatile long pausedTotal;

    /**
     *  暂停起始时间
     */
    private volatile long pausedAt;

    /**
     *  是否暂停
     */
    private volatile boolean paused;

    /**
     *  当前朝向：人物是否面朝左侧
     *
     *  <p>只对提供了左右两套素材的动作（如指向）生效；其余动作取帧时
     *  会自动退回动作本身，行为与以前完全一致。</p>
     */
    private volatile boolean pointingLeft = true;

    /**
     *  构造播放控制器
     *
     *  @param skin 素材
     */
    public GDragonAnimation(GDragonSkin skin) {
        this.skin = skin == null ? GDragonSkin.getDefault() : skin;
    }

    /**
     *  获得素材
     *
     *  @return 素材
     */
    public GDragonSkin getSkin() {
        return skin;
    }

    /**
     *  切换动作
     *
     *  @param action 动作；为 {@code null} 时切到 idle
     */
    public void play(GDragonAction action) {
        play(action, pointingLeft);
    }

    /**
     *  切换动作并指定朝向
     *
     *  @param action 动作；为 {@code null} 时切到 idle
     *  @param left 人物是否面朝左侧
     */
    public void play(GDragonAction action, boolean left) {
        GDragonAction target = action == null ? GDragonAction.IDLE : action;
        this.action = target;
        this.pointingLeft = left;
        this.startedAt = System.currentTimeMillis();
        this.pausedTotal = 0;
        this.pausedAt = 0;
    }

    /**
     *  获得当前动作
     *
     *  @return 当前动作
     */
    public GDragonAction getAction() {
        return action;
    }

    /**
     *  当前朝向
     *
     *  @return 面朝左侧返回 {@code true}
     */
    public boolean isPointingLeft() {
        return pointingLeft;
    }

    /**
     *  取当前动作在当前朝向上的帧序列
     *
     *  @return 帧序列；可能为空
     */
    private GDragonFrames frames() {
        return skin.getFrames(action, pointingLeft);
    }

    /**
     *  获得当前帧号
     *
     *  @param nowMs 当前时间，单位毫秒
     *  @return 帧号
     */
    public long getFrameIndex(long nowMs) {
        GDragonFrames frames = frames();
        if (frames == null || frames.size() == 0) {
            return 0;
        }
        long elapsed = Math.max(nowMs - startedAt - pausedDuration(nowMs), 0);
        //按倍率放慢：帧号推进变慢，动作幅度与节奏一起收敛
        return (long) (elapsed * frames.getFps() * motion() / 1000);
    }

    /**
     *  获得当前的速度倍率
     *
     *  <p>优先取系统属性 {@code cdtest.dragon.motion}，取不到或非法时用
     *  {@link #DEFAULT_MOTION}；夹在 0.05 到 2.0 之间，避免设成 0 后画面完全不动。</p>
     *
     *  @return 速度倍率
     */
    public static double motion() {
        double value = DEFAULT_MOTION;
        String text = System.getProperty(SYS_MOTION);
        if (text != null && !text.trim().isEmpty()) {
            try {
                value = Double.parseDouble(text.trim());
            } catch (NumberFormatException ignored) {
                value = DEFAULT_MOTION;
            }
        }
        return Math.max(Math.min(value, 2.0), 0.05);
    }

    /**
     *  获得当前的动作幅度
     *
     *  <p>优先取系统属性 {@code cdtest.dragon.amplitude}，取不到或非法时用
     *  {@link #DEFAULT_AMPLITUDE}；夹在 0.05 到 1.0 之间，超过 1 没有意义
     *  （素材本身的最大幅度就是 1）。</p>
     *
     *  @return 动作幅度
     */
    public static double amplitude() {
        double value = DEFAULT_AMPLITUDE;
        String text = System.getProperty(SYS_AMPLITUDE);
        if (text != null && !text.trim().isEmpty()) {
            try {
                value = Double.parseDouble(text.trim());
            } catch (NumberFormatException ignored) {
                value = DEFAULT_AMPLITUDE;
            }
        }
        return Math.max(Math.min(value, 1.0), 0.05);
    }



    /**
     *  获得当前帧
     *
     *  <p>动作只在自己这套帧里往复循环。原先一次性动作播完会回落 idle，但两套素材
     *  不是同一张脸，弹窗还开着的时候换脸很突兀，因此改为循环自身帧补满时长。</p>
     *
     *  <p>只有<b>当前动作素材不存在</b>时才退到 idle（那属于素材缺失的兜底）。</p>
     *
     *  @param nowMs 当前时间，单位毫秒
     *  @return 当前帧位图；无素材时返回 {@code null}
     */
    public BufferedImage currentFrame(long nowMs) {
        GDragonFrames frames = frames();
        if (frames == null) {
            if (action != GDragonAction.IDLE) {
                play(GDragonAction.IDLE);
                frames = skin.getFrames(GDragonAction.IDLE);
            }
            if (frames == null) {
                return null;
            }
        }
        return frames.frame(displayIndex(getFrameIndex(nowMs), frames));
    }

    /**
     *  按幅度换算出真正要显示的帧号
     *
     *  <p>序列帧的姿态是渲染时定死的，要收敛幅度只能少走几帧：上限是
     *  {@code 幅度 × (帧数 - 1)}，所有动作都在 {@code 0..上限} 之间往返。
     *  幅度 0.3 就是只走出全程三成的姿态。</p>
     *
     *  <p>往返必须往复而不能直接取模：取模会从上限帧一步跳回第 0 帧，
     *  那一下是硬跳，比动作本身还显眼。</p>
     *
     *  <p><b>不能改成叠半透明帧</b>：把当前帧按幅度叠在静止帧上数学上也能压幅度，
     *  但两层姿态同时可见，人物会带虚影。</p>
     *
     *  @param index 原始帧号
     *  @param frames 帧序列
     *  @return 实际显示的帧号
     */
    private int displayIndex(long index, GDragonFrames frames) {
        int size = frames.size();
        double amp = amplitude();
        if (size <= 1) {
            return 0;
        }
        int span = (int) Math.round((size - 1) * amp);
        if (span <= 0) {
            return 0;
        }
        //一次性与循环动作都往复：动作做完后反向收回，接着再做，帧始终来自同一套素材
        int period = span * 2;
        long p = Math.max(index, 0) % period;
        return (int) (p <= span ? p : period - p);
    }

    /**
     *  当前动作是否已播完（仅对一次性动作有意义）
     *
     *  @param nowMs 当前时间，单位毫秒
     *  @return 播完返回 {@code true}
     */
    public boolean isFinished(long nowMs) {
        GDragonFrames frames = frames();
        return frames != null && !frames.isLoop() && getFrameIndex(nowMs) >= frames.size();
    }

    /**
     *  获得当前动作帧率
     *
     *  @return 帧率
     */
    public int getFps() {
        GDragonFrames frames = frames();
        double base = frames == null ? GDragonAction.IDLE.getDefaultFps() : frames.getFps();
        //返回的是实际播放帧率：刷新率按它算才与画面推进一致
        return (int) Math.round(base * motion());
    }

    /**
     *  暂停播放（窗口不可见等场景调用，避免无谓绘制）
     */
    public void pause() {
        if (!paused) {
            paused = true;
            pausedAt = System.currentTimeMillis();
        }
    }

    /**
     *  恢复播放
     */
    public void resume() {
        if (paused) {
            pausedTotal += System.currentTimeMillis() - pausedAt;
            pausedAt = 0;
            paused = false;
        }
    }

    /**
     *  是否暂停
     *
     *  @return 暂停返回 {@code true}
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     *  获得累计暂停时长
     *
     *  @param nowMs 当前时间，单位毫秒
     *  @return 暂停时长，单位毫秒
     */
    private long pausedDuration(long nowMs) {
        return pausedTotal + (paused && pausedAt > 0 ? nowMs - pausedAt : 0);
    }
}
