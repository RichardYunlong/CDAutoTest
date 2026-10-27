package Dragon;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 *  角色绘制面板
 *
 *  <p>播放 {@link GDragonAnimation} 的当前帧。三条性能约定：</p>
 *  <ol>
 *      <li>双缓冲 + 只绘制角色区域，不做布局、不 new 对象；</li>
 *      <li><b>变卡不变慢</b>：帧号按时间算，掉帧只降低刷新率，动作速度不变；</li>
 *      <li>帧率自适应：绘制耗时持续超过帧预算时自动降档，最低 8fps；窗口不可见时暂停。</li>
 *  </ol>
 *
 *  <p>DPI：按屏幕缩放比（{@code -Dcdtest.dragon.scale} 可覆盖）决定绘制尺寸，
 *  放大时建议同时提供 2x 素材（skin.properties 里把 frameWidth/frameHeight 与 sheet 换成 2x）。</p>
 *
 *  @see GDragonAnimation
 */
public class GDragonStage extends JPanel {

    /**
     *  系统属性：绘制缩放比
     */
    public static final String SYS_SCALE = "cdtest.dragon.scale";

    /**
     *  最低帧率
     */
    public static final int MIN_FPS = 8;

    /**
     *  降档阈值：绘制耗时超过帧预算的该比例即降档
     */
    private static final double DEGRADE_THRESHOLD = 0.6;

    /**
     *  播放控制器
     */
    private final GDragonAnimation animation;

    /**
     *  绘制缩放比
     */
    private final int scale;

    /**
     *  刷新定时器
     */
    private Timer timer;

    /**
     *  帧率档位系数，1.0 为不降档
     */
    private volatile double quality = 1.0;

    /**
     *  平均绘制耗时，单位毫秒
     */
    private volatile double avgCostMs;

    /**
     *  绘制次数
     */
    private volatile long paintCount;

    private static final long serialVersionUID = -1189259393723327699L;

    /**
     *  构造绘制面板
     *
     *  @param animation 播放控制器
     */
    public GDragonStage(GDragonAnimation animation) {
        this(animation, resolveScale());
    }

    /**
     *  构造绘制面板
     *
     *  @param animation 播放控制器
     *  @param scale 绘制缩放比
     */
    public GDragonStage(GDragonAnimation animation, int scale) {
        this.animation = animation;
        this.scale = Math.max(scale, 1);
        setDoubleBuffered(true);
        setOpaque(false);
        GDragonSkin skin = animation == null ? null : animation.getSkin();
        int width = (skin == null ? GDragonPlaceholder.WIDTH : skin.getFrameWidth()) * this.scale;
        int height = (skin == null ? GDragonPlaceholder.HEIGHT : skin.getFrameHeight()) * this.scale;
        setPreferredSize(new Dimension(width, height));
    }

    /**
     *  获得播放控制器
     *
     *  @return 播放控制器
     */
    public GDragonAnimation getAnimation() {
        return animation;
    }

    /**
     *  开始播放
     */
    public void start() {
        if (timer != null) {
            return;
        }
        timer = new Timer(delayMillis(), e -> tick());
        timer.setRepeats(true);
        timer.start();
    }

    /**
     *  停止播放
     */
    public void stop() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    /**
     *  切换动作并同步刷新率
     *
     *  @param action 动作
     */
    public void play(GDragonAction action) {
        animation.play(action);
        if (timer != null) {
            timer.setDelay(delayMillis());
        }
        repaint();
    }

    /**
     *  切换动作并指定朝向
     *
     *  <p>指向这类动作需要人物面朝被指向的元素，左右两套素材在这里切换。</p>
     *
     *  @param action 动作
     *  @param left 人物是否面朝左侧
     */
    public void play(GDragonAction action, boolean left) {
        animation.play(action, left);
        if (timer != null) {
            timer.setDelay(delayMillis());
        }
        repaint();
    }

    /**
     *  定时器回调：重绘并按需调整刷新率
     */
    private void tick() {
        if (!isShowing()) {
            animation.pause();
            return;
        }
        animation.resume();
        repaint();
        if (timer != null) {
            timer.setDelay(delayMillis());
        }
    }

    /**
     *  计算刷新间隔
     *
     *  @return 刷新间隔，单位毫秒
     */
    private int delayMillis() {
        int fps = Math.max((int) Math.round(animation.getFps() * quality), MIN_FPS);
        return Math.max(1000 / fps, 1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        long begin = System.nanoTime();
        BufferedImage frame = animation.currentFrame(System.currentTimeMillis());
        if (frame != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(frame, 0, 0, getWidth(), getHeight(), null);
        }
        recordCost(System.nanoTime() - begin);
    }

    /**
     *  记录绘制耗时，并按帧预算调整档位
     *
     *  @param costNs 本次绘制耗时，单位纳秒
     */
    private void recordCost(long costNs) {
        double costMs = costNs / 1_000_000.0;
        avgCostMs = avgCostMs == 0 ? costMs : avgCostMs * 0.8 + costMs * 0.2;
        paintCount++;
        double budget = delayMillis();
        if (avgCostMs > budget * DEGRADE_THRESHOLD && quality > 0.25) {
            quality = Math.max(quality - 0.25, 0.25);
        }
    }

    /**
     *  输出播放状态，供 {@code --bench} 与调试排查
     *
     *  @return 播放状态
     */
    public String stats() {
        return "GDragonStage{fps=" + Math.max((int) Math.round(animation.getFps() * quality), MIN_FPS)
                + ", quality=" + quality
                + ", avgCostMs=" + String.format("%.2f", avgCostMs)
                + ", paints=" + paintCount
                + ", action=" + animation.getAction().getName()
                + ", scale=" + scale
                + '}';
    }

    /**
     *  确定绘制缩放比：优先系统属性，其次屏幕缩放比
     *
     *  @return 缩放比
     */
    public static int resolveScale() {
        String value = System.getProperty(SYS_SCALE);
        if (value != null && !value.trim().isEmpty()) {
            try {
                return Math.max(Integer.parseInt(value.trim()), 1);
            } catch (NumberFormatException e) {
                return 1;
            }
        }
        try {
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsConfiguration config = env.getDefaultScreenDevice().getDefaultConfiguration();
            double scaleX = config.getDefaultTransform().getScaleX();
            return scaleX >= 1.5 ? 2 : 1;
        } catch (Exception e) {
            return 1;
        }
    }
}
