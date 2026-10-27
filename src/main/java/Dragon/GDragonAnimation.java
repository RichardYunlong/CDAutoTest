package Dragon;

import java.awt.image.BufferedImage;

/**
 *  动作播放控制
 *
 *  <p>负责状态机与帧号换算：切换动作、按时间算当前帧、一次性动作播完自动回落 idle。
 *  它不含定时器、不碰界面，便于单独测试与在 {@link GDragonStage} 之外的场景复用。</p>
 *
 *  <p>帧号按"经过时间 × 帧率"计算，而不是每次加一，这样掉帧时动作速度依然稳定，
 *  配合 {@link GDragonStage} 的降帧策略可以做到"变卡不变慢"。</p>
 *
 *  @see GDragonStage
 *  @see GDragonAction
 */
public final class GDragonAnimation {

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
        return elapsed * frames.getFps() / 1000;
    }

    /**
     *  获得当前帧
     *
     *  <p>一次性动作播完后自动回落 idle。</p>
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
        long index = getFrameIndex(nowMs);
        if (!frames.isLoop() && action != GDragonAction.IDLE && index >= frames.size()) {
            play(GDragonAction.IDLE);
            frames = skin.getFrames(GDragonAction.IDLE);
            if (frames == null) {
                return null;
            }
            index = 0;
        }
        return frames.frame((int) index);
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
        return frames == null ? GDragonAction.IDLE.getDefaultFps() : frames.getFps();
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
