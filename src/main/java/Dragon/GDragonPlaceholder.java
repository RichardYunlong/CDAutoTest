package Dragon;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 *  占位素材生成器
 *
 *  <p>方案A的正式素材应当是"离线渲染好的 PNG 序列帧（带 alpha）"。
 *  在素材到位之前，这里用人形形象图 {@link GDragonResource#ROLE_IMAGE} 做程序化变换，
 *  生成 8 个动作的可播放帧，使整套播放链路可以立即单独调试、测性能。</p>
 *
 *  <p><b>它只是占位实现</b>：一旦 {@code image/skin/} 下放入真实素材（图集或序列帧目录），
 *  {@link GDragonSkin} 会优先加载真实素材，这里的生成结果自动让位，不需要改任何代码。</p>
 *
 *  @see GDragonSkin
 */
public final class GDragonPlaceholder {

    /**
     *  默认帧宽
     */
    public static final int WIDTH = 240;

    /**
     *  默认帧高
     */
    public static final int HEIGHT = 360;

    /**
     *  系统属性：是否把形象图的纯色背景抠成透明
     *
     *  <p>形象图若能导出带 alpha 的 PNG，建议关闭本项（{@code -Dcdtest.dragon.chroma=false}），
     *  直接用原图更干净；只有背景为纯色/浅色渐变时才需要程序化抠图。</p>
     */
    public static final String SYS_CHROMA = "cdtest.dragon.chroma";

    /**
     *  背景色容差，取值越大抠得越狠，越容易误伤浅色衣物
     */
    private static final int CHROMA_TOLERANCE = 30;

    /**
     *  各动作生成的帧数
     */
    private static final Map<GDragonAction, Integer> FRAME_COUNT = new EnumMap<>(GDragonAction.class);

    static {
        FRAME_COUNT.put(GDragonAction.IDLE, 12);
        FRAME_COUNT.put(GDragonAction.TALK, 8);
        FRAME_COUNT.put(GDragonAction.POINT, 8);
        FRAME_COUNT.put(GDragonAction.CLICK, 6);
        FRAME_COUNT.put(GDragonAction.SUCCESS, 12);
        FRAME_COUNT.put(GDragonAction.FAIL, 8);
        FRAME_COUNT.put(GDragonAction.THINKING, 8);
        FRAME_COUNT.put(GDragonAction.BYE, 12);
    }

    private GDragonPlaceholder() {
    }

    /**
     *  生成全部动作的占位帧序列
     *
     *  @return 动作与帧序列的映射
     */
    public static Map<GDragonAction, GDragonFrames> generate() {
        return generate(WIDTH, HEIGHT);
    }

    /**
     *  生成全部动作的占位帧序列
     *
     *  @param width 帧宽
     *  @param height 帧高
     *  @return 动作与帧序列的映射
     */
    public static Map<GDragonAction, GDragonFrames> generate(int width, int height) {
        BufferedImage base = loadBase(width, height);
        Map<GDragonAction, GDragonFrames> result = new EnumMap<>(GDragonAction.class);
        for (GDragonAction action : GDragonAction.values()) {
            int count = FRAME_COUNT.containsKey(action) ? FRAME_COUNT.get(action) : 8;
            List<BufferedImage> frames = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                frames.add(render(action, i, count, base, width, height));
            }
            result.put(action, new GDragonFrames(action, frames, action.getDefaultFps(), action.isLoop()));
        }
        return result;
    }

    /**
     *  渲染单帧
     *
     *  @param action 动作
     *  @param index 帧下标
     *  @param total 帧总数
     *  @param base 基础人形图
     *  @param width 帧宽
     *  @param height 帧高
     *  @return 帧位图，带 alpha
     */
    private static BufferedImage render(GDragonAction action, int index, int total,
                                        BufferedImage base, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        double phase = total <= 1 ? 0.0 : (double) index / total;
        Transform transform = new Transform();
        switch (action) {
            case TALK:
                transform.dy = -2 * Math.sin(2 * Math.PI * phase);
                break;
            case POINT:
                transform.dx = -10 * phase;
                transform.dy = -6 * phase;
                break;
            case CLICK:
                transform.scale = 1.0 - 0.03 * Math.sin(Math.PI * phase);
                transform.dy = 4 * Math.sin(Math.PI * phase);
                break;
            case SUCCESS:
                transform.dy = -14 * Math.sin(Math.PI * Math.min(phase * 1.5, 1.0));
                break;
            case FAIL:
                transform.rotate = 0.10 * Math.sin(4 * Math.PI * phase);
                break;
            case THINKING:
                transform.rotate = 0.05 * Math.sin(2 * Math.PI * phase);
                break;
            case BYE:
                transform.dy = -2 * Math.sin(2 * Math.PI * phase);
                break;
            case IDLE:
            default:
                transform.dy = 3 * Math.sin(2 * Math.PI * phase);
                transform.scale = 1.0 + 0.01 * Math.sin(2 * Math.PI * phase);
                break;
        }

        AffineTransform at = new AffineTransform();
        at.translate(width / 2.0 + transform.dx, height / 2.0 + transform.dy);
        at.rotate(transform.rotate);
        at.scale(transform.scale, transform.scale);
        at.translate(-base.getWidth() / 2.0, -base.getHeight() / 2.0);
        g.drawImage(base, at, null);

        drawOverlay(g, action, phase, width, height);
        g.dispose();
        return image;
    }

    /**
     *  绘制叠加元素：口型、手臂、涟漪、星光、思考点等
     *
     *  @param g 画笔
     *  @param action 动作
     *  @param phase 进度 0-1
     *  @param width 帧宽
     *  @param height 帧高
     */
    private static void drawOverlay(Graphics2D g, GDragonAction action, double phase, int width, int height) {
        switch (action) {
            case TALK:
                //嘴部位置按半身像构图取位：约在画面高度 47% 处
                drawMouth(g, width * 0.50, height * 0.472,
                        width * 0.075, height * (0.006 + 0.010 * Math.abs(Math.sin(4 * Math.PI * phase))));
                break;
            case POINT:
                drawArm(g, width * 0.64, height * 0.76, width * (0.86 + 0.06 * phase), height * (0.58 - 0.05 * phase));
                drawArrow(g, width * (0.86 + 0.06 * phase), height * (0.58 - 0.05 * phase));
                break;
            case CLICK:
                drawRipple(g, width * 0.86, height * 0.52, width * 0.03 + 26 * phase, 1.0 - phase);
                break;
            case SUCCESS:
                drawStars(g, width, height, phase);
                break;
            case FAIL:
                drawCross(g, width * 0.86, height * 0.14, phase);
                break;
            case THINKING:
                drawThinkingDots(g, width * 0.82, height * 0.10, phase);
                break;
            case BYE:
                drawArm(g, width * 0.64, height * 0.78,
                        width * (0.90 + 0.03 * Math.sin(4 * Math.PI * phase)),
                        height * (0.50 - 0.08 * Math.abs(Math.sin(4 * Math.PI * phase))));
                break;
            case IDLE:
            default:
                break;
        }
    }

    /**
     *  绘制口型
     *
     *  @param g 画笔
     *  @param x 嘴巴中心横坐标
     *  @param y 嘴巴中心纵坐标
     *  @param width 嘴巴宽度
     *  @param height 嘴巴高度
     */
    private static void drawMouth(Graphics2D g, double x, double y, double width, double height) {
        g.setColor(new Color(120, 40, 60, 220));
        g.fillOval((int) (x - width / 2), (int) (y - height / 2),
                (int) Math.max(width, 4), (int) Math.max(height, 2));
    }

    /**
     *  绘制手臂
     */
    private static void drawArm(Graphics2D g, double x1, double y1, double x2, double y2) {
        g.setColor(new Color(255, 226, 196, 235));
        g.setStroke(new BasicStroke(9f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    /**
     *  绘制指向箭头
     */
    private static void drawArrow(Graphics2D g, double x, double y) {
        int[] xs = {(int) x, (int) x + 18, (int) x + 4};
        int[] ys = {(int) y, (int) y + 6, (int) y + 16};
        g.setColor(new Color(255, 215, 0, 235));
        g.fillPolygon(xs, ys, 3);
    }

    /**
     *  绘制点击涟漪
     */
    private static void drawRipple(Graphics2D g, double x, double y, double radius, double alpha) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) Math.max(alpha, 0.05)));
        g.setColor(new Color(255, 255, 255, 200));
        g.setStroke(new BasicStroke(3f));
        g.drawOval((int) (x - radius), (int) (y - radius), (int) (radius * 2), (int) (radius * 2));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    /**
     *  绘制成功星光
     */
    private static void drawStars(Graphics2D g, int width, int height, double phase) {
        double[] xs = {0.24, 0.78, 0.86, 0.16};
        double[] ys = {0.20, 0.16, 0.34, 0.30};
        for (int i = 0; i < xs.length; i++) {
            double local = Math.max(Math.min(phase * 1.4 - i * 0.12, 1.0), 0.0);
            if (local <= 0.0) {
                continue;
            }
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (local * (1.0 - local) * 4)));
            g.setColor(new Color(255, 236, 120, 255));
            int cx = (int) (width * xs[i]);
            int cy = (int) (height * ys[i] - 20 * local);
            int r = (int) (4 + 6 * local);
            g.fillOval(cx - r, cy - r, r * 2, r * 2);
        }
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    /**
     *  绘制失败的叉号
     */
    private static void drawCross(Graphics2D g, double x, double y, double phase) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) Math.min(phase * 3, 1)));
        g.setColor(new Color(220, 60, 60, 235));
        g.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int size = 12;
        g.drawLine((int) (x - size), (int) (y - size), (int) (x + size), (int) (y + size));
        g.drawLine((int) (x + size), (int) (y - size), (int) (x - size), (int) (y + size));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    /**
     *  绘制思考省略号
     */
    private static void drawThinkingDots(Graphics2D g, double x, double y, double phase) {
        g.setColor(new Color(60, 60, 60, 200));
        g.setFont(new Font("宋体", Font.BOLD, 22));
        int visible = (int) (phase * 6) % 4;
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < visible; i++) {
            text.append('.');
        }
        g.drawString(text.toString(), (int) x, (int) y);
    }

    /**
     *  加载基础人形图；缺失时程序化画一个简易人形，保证永不失败
     *
     *  @param width 目标宽
     *  @param height 目标高
     *  @return 基础人形图
     */
    private static BufferedImage loadBase(int width, int height) {
        java.net.URL url = GDragonResource.locate(GDragonResource.ROLE_IMAGE);
        if (url != null) {
            try {
                BufferedImage source = javax.imageio.ImageIO.read(url);
                if (source != null) {
                    boolean chroma = !"false".equalsIgnoreCase(System.getProperty(SYS_CHROMA, "true"));
                    BufferedImage base = chroma ? stripBackground(source, CHROMA_TOLERANCE) : source;
                    return toArg(base, width, height);
                }
            } catch (Exception e) {
                return drawSimpleFigure(width, height);
            }
        }
        return drawSimpleFigure(width, height);
    }

    /**
     *  把与边界连通的纯色背景抠成透明
     *
     *  <p>只处理"从四边可达"的像素，人物内部的浅色衣物不会被打洞；
     *  适用于浅灰渐变底的形象图。若形象图本身带 alpha，请用
     *  {@code -Dcdtest.dragon.chroma=false} 关闭，避免误伤。</p>
     *
     *  @param source 源图
     *  @param tolerance 背景色容差
     *  @return 带 alpha 的位图
     */
    private static BufferedImage stripBackground(BufferedImage source, int tolerance) {
        int width = source.getWidth();
        int height = source.getHeight();
        BufferedImage target = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g = target.createGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();

        int[] pixels = ((java.awt.image.DataBufferInt) target.getRaster().getDataBuffer()).getData();
        int total = width * height;
        int background = cornerColor(pixels, width, height);
        int bgR = (background >> 16) & 0xFF;
        int bgG = (background >> 8) & 0xFF;
        int bgB = background & 0xFF;

        boolean[] queued = new boolean[total];
        int[] stack = new int[total];
        int top = 0;
        for (int x = 0; x < width; x++) {
            stack[top++] = x;
            stack[top++] = (height - 1) * width + x;
            queued[x] = true;
            queued[(height - 1) * width + x] = true;
        }
        for (int y = 0; y < height; y++) {
            stack[top++] = y * width;
            stack[top++] = y * width + width - 1;
            queued[y * width] = true;
            queued[y * width + width - 1] = true;
        }

        while (top > 0) {
            int index = stack[--top];
            int pixel = pixels[index];
            if (((pixel >>> 24) & 0xFF) == 0) {
                continue;
            }
            int diff = Math.max(Math.abs(((pixel >> 16) & 0xFF) - bgR),
                    Math.max(Math.abs(((pixel >> 8) & 0xFF) - bgG), Math.abs((pixel & 0xFF) - bgB)));
            if (diff > tolerance) {
                continue;
            }
            pixels[index] = 0;

            int x = index % width;
            int y = index / width;
            if (x > 0 && !queued[index - 1]) {
                queued[index - 1] = true;
                stack[top++] = index - 1;
            }
            if (x < width - 1 && !queued[index + 1]) {
                queued[index + 1] = true;
                stack[top++] = index + 1;
            }
            if (y > 0 && !queued[index - width]) {
                queued[index - width] = true;
                stack[top++] = index - width;
            }
            if (y < height - 1 && !queued[index + width]) {
                queued[index + width] = true;
                stack[top++] = index + width;
            }
        }
        return target;
    }

    /**
     *  取四角颜色的平均值作为背景色
     *
     *  @param pixels 像素数组
     *  @param width 宽
     *  @param height 高
     *  @return 背景色
     */
    private static int cornerColor(int[] pixels, int width, int height) {
        int[] corners = {pixels[0], pixels[width - 1], pixels[(height - 1) * width],
                pixels[height * width - 1]};
        int r = 0;
        int g = 0;
        int b = 0;
        for (int corner : corners) {
            r += (corner >> 16) & 0xFF;
            g += (corner >> 8) & 0xFF;
            b += corner & 0xFF;
        }
        int count = corners.length;
        return ((r / count) << 16) | ((g / count) << 8) | (b / count);
    }

    /**
     *  统一转为带 alpha 的位图并缩放到目标尺寸
     *
     *  @param source 源图
     *  @param width 目标宽
     *  @param height 目标高
     *  @return 转换后的位图
     */
    private static BufferedImage toArg(BufferedImage source, int width, int height) {
        BufferedImage target = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(source, 0, 0, width, height, null);
        g.dispose();
        return target;
    }

    /**
     *  画一个简易人形，作为基础图缺失时的兜底
     *
     *  @param width 宽
     *  @param height 高
     *  @return 简易人形图
     */
    private static BufferedImage drawSimpleFigure(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(90, 160, 220, 235));
        g.fillOval(width / 2 - 34, 24, 68, 68);
        g.setColor(new Color(240, 240, 240, 235));
        g.fillRoundRect(width / 2 - 46, 96, 92, 150, 28, 28);
        g.setColor(new Color(255, 226, 196, 235));
        g.fillOval(width / 2 - 60, 150, 24, 96);
        g.fillOval(width / 2 + 36, 150, 24, 96);
        g.dispose();
        return image;
    }

    /**
     *  单帧变换参数
     */
    private static final class Transform {

        private double dx;
        private double dy;
        private double scale = 1.0;
        private double rotate;
    }
}
