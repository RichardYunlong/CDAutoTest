package Dragon;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

/**
 *  动作帧序列
 *
 *  <p>一个动作对应一组已解码的位图。所有帧在加载阶段就解码完成并常驻，
 *  播放时只做 {@code drawImage}，不在播放路径上解码，避免卡顿事件派发线程。</p>
 *
 *  @see GDragonSkin
 */
public final class GDragonFrames {

    /**
     *  动作
     */
    private final GDragonAction action;

    /**
     *  帧序列
     */
    private final List<BufferedImage> frames;

    /**
     *  帧率
     */
    private final int fps;

    /**
     *  是否循环
     */
    private final boolean loop;

    /**
     *  帧宽
     */
    private final int width;

    /**
     *  帧高
     */
    private final int height;

    /**
     *  构造帧序列
     *
     *  @param action 动作
     *  @param frames 帧序列
     *  @param fps 帧率；小于等于0时取动作默认帧率
     *  @param loop 是否循环；为 {@code null} 时取动作默认属性
     */
    public GDragonFrames(GDragonAction action, List<BufferedImage> frames, int fps, Boolean loop) {
        this.action = action;
        this.frames = Collections.unmodifiableList(frames);
        this.fps = fps > 0 ? fps : action.getDefaultFps();
        this.loop = loop == null ? action.isLoop() : loop;
        int w = 0;
        int h = 0;
        for (BufferedImage frame : this.frames) {
            w = Math.max(w, frame.getWidth());
            h = Math.max(h, frame.getHeight());
        }
        this.width = w;
        this.height = h;
    }

    /**
     *  获得动作
     *
     *  @return 动作
     */
    public GDragonAction getAction() {
        return action;
    }

    /**
     *  获得指定帧
     *
     *  <p>循环动作按下标取模，一次性动作到末帧后保持末帧。</p>
     *
     *  @param index 帧下标，允许越界
     *  @return 帧位图
     */
    public BufferedImage frame(int index) {
        if (frames.isEmpty()) {
            return null;
        }
        int size = frames.size();
        int safe = index % size;
        if (safe < 0) {
            safe += size;
        }
        if (!loop && index >= size) {
            safe = size - 1;
        }
        return frames.get(safe);
    }

    /**
     *  获得帧数
     *
     *  @return 帧数
     */
    public int size() {
        return frames.size();
    }

    /**
     *  获得帧率
     *
     *  @return 帧率
     */
    public int getFps() {
        return fps;
    }

    /**
     *  是否循环
     *
     *  @return 循环返回 {@code true}
     */
    public boolean isLoop() {
        return loop;
    }

    /**
     *  获得帧宽
     *
     *  @return 帧宽
     */
    public int getWidth() {
        return width;
    }

    /**
     *  获得帧高
     *
     *  @return 帧高
     */
    public int getHeight() {
        return height;
    }

    /**
     *  估算常驻内存
     *
     *  @return 内存估算值，单位字节
     */
    public long estimateBytes() {
        return (long) width * height * 4L * frames.size();
    }
}
