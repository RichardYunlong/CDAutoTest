import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.util.Arrays;
import java.util.Random;

/**
 * 素材生成器：把多角度参考图转成人形弹窗要求的动作序列帧。
 *
 * <p>输出：image/cdperson_&lt;动作&gt;/1.png ... N.png，480x720，32 位带 alpha。</p>
 *
 * <p>构图取"半身出镜"（头顶到大腿），人物占画面约 70% 宽度，脸部细节在弹窗里看得清。</p>
 *
 * <pre>
 * 用法（在项目根目录执行，生成器不参与 Maven 编译）：
 * javac -encoding UTF-8 -d target/skin-gen tools/SkinGen.java
 * java -cp target/skin-gen SkinGen tools/ref/cdperson src/main/resources/image
 *
 * 换形象：把同一角色的正面全身图命名为 01*.png 放进新目录，
 *        然后用 -Dcdtest.dragon.character=新角色名 启动用例。
 * 调幅度：改各 genXxx 方法里的系数后重新生成即可。
 * </pre>
 */
public class SkinGen {

    static final int W = 480;
    static final int H = 720;
    /** 半身像在画面中的高度，顶部留 40 像素给跳跃/上浮动作 */
    static final int BODY_H = H - 40;
    /** 从头顶往下保留的身体比例，0.60 约到膝盖上方 */
    static final double BODY_RATIO = 0.60;
    /** 底边位置：半身像下缘贴着画布底边 */
    static final int BOTTOM = H;

    /** 保存时每通道保留的位数，7 位肉眼无损、体积明显更小 */
    static final int QUANT_BITS = 6;

    static File srcDir;
    static File outDir;

    public static void main(String[] args) throws Exception {
        srcDir = new File(args[0]);
        outDir = new File(args[1]);

        clean();
        BufferedImage front = align(read("01"), "正面全身");
        BufferedImage leftSide = align(read("02"), "左侧全身");
        BufferedImage side = align(read("03"), "右侧全身");
        BufferedImage back = align(read("04"), "背面全身");

        genIdle(front);
        genTalk(front);
        genPoint(front);
        //方向变体：指向时人物侧身朝向元素（左/右各一套）
        genPointVariant(leftSide, "point_l", -1.5);
        genPointVariant(side, "point_r", 1.5);
        genClick(front);
        genSuccess(front);
        genFail(front);
        genThinking(front);
        genBye(front, side, back);
        overview();
        System.out.println("生成完成 -> " + outDir.getAbsolutePath());
    }

    // ---------------------------------------------------------------- 动作

    /** 待机：呼吸起伏，下缘不动 */
    static void genIdle(BufferedImage base) throws Exception {
        int n = 12;
        for (int i = 0; i < n; i++) {
            double p = i * 2 * Math.PI / n;
            double sy = 1 + 0.012 * Math.sin(p);
            double sx = 1 - 0.005 * Math.sin(p);
            double rot = 0.35 * Math.sin(p + Math.PI / 2);
            int dy = (int) Math.round(-1.5 * Math.sin(p));
            save(transform(base, sx, sy, rot, 0, dy, 0.0), "idle", i + 1);
        }
    }

    /** 说话：以颈部为轴的点头，配合轻微起伏 */
    static void genTalk(BufferedImage base) throws Exception {
        int n = 8;
        for (int i = 0; i < n; i++) {
            double p = i * 2 * Math.PI / n;
            double nod = Math.abs(Math.sin(p));
            double sy = 1 - 0.013 * nod;
            int dy = (int) Math.round(5.5 * nod);
            double rot = -2.4 * nod;
            save(transform(base, 1, sy, rot, 0, dy, 0.78), "talk", i + 1);
        }
    }

    /** 指向：被关注感——上浮、放大、轻微侧倾（方向中立，配合代码画的箭头） */
    static void genPoint(BufferedImage base) throws Exception {
        int n = 8;
        for (int i = 0; i < n; i++) {
            double s = ease(i / (double) (n - 1));
            double scale = 1 + 0.030 * s;
            int dy = (int) Math.round(-8 * s);
            double rot = 1.2 * s;
            save(transform(base, scale, scale, rot, 0, dy, 0.0), "point", i + 1);
        }
    }

    /**
     * 指向的方向变体：朝左、朝右两套
     *
     * <p>用侧身素材让人物面朝被指向的元素，配合代码侧的箭头方向选中对应的一套；
     * 时序与 {@link #genPoint(BufferedImage)} 一致，只是换了人物朝向与倾斜方向。</p>
     */
    static void genPointVariant(BufferedImage base, String name, double tilt) throws Exception {
        int n = 8;
        for (int i = 0; i < n; i++) {
            double s = ease(i / (double) (n - 1));
            double scale = 1 + 0.030 * s;
            int dy = (int) Math.round(-8 * s);
            save(transform(base, scale, scale, tilt * s, 0, dy, 0.0), name, i + 1);
        }
    }

    /** 点击：下压回弹 */
    static void genClick(BufferedImage base) throws Exception {
        int n = 6;
        double[] press = {0, 0.55, 1, 0.78, 0.38, 0};
        for (int i = 0; i < n; i++) {
            double s = press[i];
            double sy = 1 - 0.028 * s;
            double sx = 1 + 0.014 * s;
            int dy = (int) Math.round(6 * s);
            save(transform(base, sx, sy, 0.6 * s, 0, dy, 0.0), "click", i + 1);
        }
    }

    /** 成功：蓄力起跳 + 金色星光 */
    static void genSuccess(BufferedImage base) throws Exception {
        int[] jump = {0, -4, -8, 3, 14, 20, 18, 11, 4, -3, -1, 0};
        double[] glow = {0, 0.12, 0.24, 0.4, 0.78, 1, 0.92, 0.68, 0.42, 0.2, 0.1, 0};
        for (int i = 0; i < jump.length; i++) {
            int dy = jump[i];
            //放大系数压到很小：头顶只有 40 像素余量，跳起时再放大会被裁切
            double scale = 1 + Math.max(0, dy) * 0.0006;
            double sy = 1 - Math.max(0, -dy) * 0.006;
            BufferedImage frame = transform(base, scale, sy, 0, 0, dy, 0.0);
            drawStars(frame, glow[i]);
            save(frame, "success", i + 1);
        }
    }

    /** 失败：摇头 + 垂头，整体降低饱和度 */
    static void genFail(BufferedImage base) throws Exception {
        int n = 8;
        for (int i = 0; i < n; i++) {
            double t = i / (double) (n - 1);
            double swing = Math.sin(t * 3 * Math.PI);
            int dx = (int) Math.round(8 * swing);
            double rot = 2.2 * swing;
            int dy = (int) Math.round(6 * t);
            BufferedImage frame = transform(base, 1, 1 - 0.016 * t, rot, dx, dy, 0.78);
            desaturate(frame, 0.24 * t);
            save(frame, "fail", i + 1);
        }
    }

    /** 思考：持续侧头 + 呼吸，右上角三个思考点依次亮起 */
    static void genThinking(BufferedImage base) throws Exception {
        int n = 8;
        for (int i = 0; i < n; i++) {
            double p = i * 2 * Math.PI / n;
            double rot = 3.2 + 0.7 * Math.sin(p);
            int dy = (int) Math.round(-2.0 * Math.sin(p));
            BufferedImage frame = transform(base, 1, 1 + 0.008 * Math.sin(p), rot, 0, dy, 0.78);
            drawDots(frame, i, n);
            save(frame, "thinking", i + 1);
        }
    }

    /**
     * 再见：转身离开
     *
     * <p>正面 → 侧身 → 背面 三段硬切，用三张真实角度的素材做出"转身"，
     * 不做半透明叠加（叠加会出现两个身子重影）。后段边缩小边右移淡出，像走出画面。</p>
     */
    static void genBye(BufferedImage front, BufferedImage side, BufferedImage back) throws Exception {
        int n = 12;
        for (int i = 0; i < n; i++) {
            double t = i / (double) (n - 1);
            BufferedImage frame;
            if (t < 0.28) {
                //正面：轻微上浮，像站起身准备走
                double s = ease(t / 0.28);
                frame = transform(front, 1 + 0.02 * s, 1 + 0.02 * s, 0, 0,
                        (int) Math.round(-5 * s), 0.0);
            } else if (t < 0.46) {
                //侧身：转身的中间画
                frame = transform(side, 1.01, 1.01, 0, 0, -5, 0.0);
            } else {
                //背面：缩小右移淡出
                double k = (t - 0.46) / 0.54;
                double e = ease(k);
                double scale = 1 - 0.13 * e;
                frame = transform(back, scale, scale, -1.6 * e,
                        (int) Math.round(58 * e), (int) Math.round(10 * e), 0.0);
                frame = withAlpha(frame, 1 - 0.22 * e);
            }
            save(frame, "bye", i + 1);
        }
    }

    // ---------------------------------------------------------------- 绘制

    static void drawStars(BufferedImage target, double alpha) {
        if (alpha <= 0.02) {
            return;
        }
        Graphics2D g = target.createGraphics();
        quality(g);
        Random rnd = new Random(20261027L);
        for (int k = 0; k < 8; k++) {
            //分布在人物两侧，避开脸部
            int x = (k % 2 == 0) ? 12 + rnd.nextInt(66) : 396 + rnd.nextInt(66);
            int y = 30 + rnd.nextInt(320);
            int r = 10 + rnd.nextInt(10);
            double a = alpha * (0.55 + 0.45 * rnd.nextDouble());
            g.setColor(new Color(255, 208, 74, (int) (255 * a)));
            g.fill(star(x, y, r));
            g.setColor(new Color(255, 252, 220, (int) (235 * a)));
            g.fillOval(x - 3, y - 3, 6, 6);
        }
        g.dispose();
    }

    static Path2D star(double cx, double cy, double r) {
        Path2D p = new Path2D.Double();
        double s = r * 0.15;
        p.moveTo(cx, cy - r);
        p.quadTo(cx + s, cy - s, cx + r, cy);
        p.quadTo(cx + s, cy + s, cx, cy + r);
        p.quadTo(cx - s, cy + s, cx - r, cy);
        p.quadTo(cx - s, cy - s, cx, cy - r);
        p.closePath();
        return p;
    }

    static void drawDots(BufferedImage target, int index, int total) {
        Graphics2D g = target.createGraphics();
        quality(g);
        int baseX = 352;
        int baseY = 112;
        for (int k = 0; k < 3; k++) {
            double phase = ((index / (double) total) + k * 0.18) % 1.0;
            double a = 0.25 + 0.75 * Math.max(0, Math.sin(phase * Math.PI));
            int r = 12 + k * 4;
            int x = baseX + k * 32;
            int y = baseY - k * 18;
            //深色实心 + 白色描边：浅色页面与白衬衫上都能看清
            g.setColor(new Color(72, 92, 132, (int) (215 * a)));
            g.fillOval(x, y, r, r);
            g.setColor(new Color(255, 255, 255, (int) (235 * a)));
            g.setStroke(new BasicStroke(2.2f));
            g.drawOval(x, y, r, r);
        }
        g.dispose();
    }

    // ---------------------------------------------------------------- 变换

    static BufferedImage transform(BufferedImage base, double sx, double sy, double rotDeg,
                                   int dx, int dy, double anchorYRatio) {
        BufferedImage out = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        quality(g);
        double ax = W / 2.0;
        double ay = BOTTOM - BODY_H * anchorYRatio;
        AffineTransform t = new AffineTransform();
        t.translate(ax + dx, ay + dy);
        t.rotate(Math.toRadians(rotDeg));
        t.scale(sx, sy);
        t.translate(-ax, -ay);
        g.drawImage(base, t, null);
        g.dispose();
        return out;
    }

    /**
     * 两帧按比例叠加，用于转身这类"中间画"
     *
     * @param one 前景
     * @param other 另一帧
     * @param ratio other 的占比，0 与 1 时直接返回其中一张
     * @return 叠加结果
     */
    static BufferedImage blend(BufferedImage one, BufferedImage other, double ratio) {
        if (ratio <= 0.001) {
            return one;
        }
        if (ratio >= 0.999) {
            return other;
        }
        BufferedImage out = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        quality(g);
        g.drawImage(one, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) ratio));
        g.drawImage(other, 0, 0, null);
        g.dispose();
        return out;
    }

    static BufferedImage withAlpha(BufferedImage image, double alpha) {
        BufferedImage out = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        quality(g);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha));
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return out;
    }

    static void desaturate(BufferedImage image, double amount) {
        if (amount <= 0) {
            return;
        }
        int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < pixels.length; i++) {
            int c = pixels[i];
            int a = (c >>> 24) & 0xFF;
            if (a == 0) {
                continue;
            }
            int r = (c >> 16) & 0xFF;
            int g = (c >> 8) & 0xFF;
            int b = c & 0xFF;
            int gray = (int) (0.299 * r + 0.587 * g + 0.114 * b);
            r = (int) (r * (1 - amount) + gray * amount);
            g = (int) (g * (1 - amount) + gray * amount);
            b = (int) (b * (1 - amount) + gray * amount);
            pixels[i] = (a << 24) | (r << 16) | (g << 8) | b;
        }
    }

    // ---------------------------------------------------------------- 读写

    static BufferedImage read(String prefix) throws Exception {
        File[] files = srcDir.listFiles();
        if (files == null) {
            throw new IllegalStateException("参考图目录不存在: " + srcDir);
        }
        for (File file : files) {
            if (file.isFile() && file.getName().startsWith(prefix)
                    && file.getName().toLowerCase().endsWith(".png")) {
                return ImageIO.read(file);
            }
        }
        throw new IllegalStateException("找不到以 " + prefix + " 开头的参考图");
    }

    /** 裁到人物范围后取上半身，统一高度并对齐底边中线 */
    static BufferedImage align(BufferedImage source, String label) {
        int[] box = bbox(source);
        int bw = box[2];
        int bh = (int) Math.round(box[3] * BODY_RATIO);
        double scale = BODY_H / (double) bh;
        int nw = (int) Math.round(bw * scale);
        BufferedImage out = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        quality(g);
        BufferedImage cropped = source.getSubimage(box[0], box[1], bw, bh);
        int x = (W - nw) / 2;
        int y = H - BODY_H;
        g.drawImage(cropped, x, y, nw, BODY_H, null);
        g.dispose();
        System.out.println("  " + label + " bbox=" + bw + "x" + box[3]
                + " 取上半身 " + bw + "x" + bh
                + " 缩放=" + String.format("%.3f", scale)
                + " -> 人物 " + nw + "x" + BODY_H);
        return out;
    }

    static int[] bbox(BufferedImage image) {
        int minX = image.getWidth();
        int minY = image.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (((image.getRGB(x, y) >>> 24) & 0xFF) > 8) {
                    if (x < minX) {
                        minX = x;
                    }
                    if (y < minY) {
                        minY = y;
                    }
                    if (x > maxX) {
                        maxX = x;
                    }
                    if (y > maxY) {
                        maxY = y;
                    }
                }
            }
        }
        if (maxX < 0) {
            return new int[]{0, 0, image.getWidth(), image.getHeight()};
        }
        return new int[]{minX, minY, maxX - minX + 1, maxY - minY + 1};
    }

    static void clean() {
        File[] dirs = outDir.listFiles();
        if (dirs == null) {
            return;
        }
        for (File dir : dirs) {
            if (dir.isDirectory() && dir.getName().startsWith("cdperson_")) {
                File[] files = dir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        //noinspection ResultOfMethodCallIgnored
                        file.delete();
                    }
                }
                //noinspection ResultOfMethodCallIgnored
                dir.delete();
            }
        }
    }

    static void save(BufferedImage image, String action, int index) throws Exception {
        File dir = new File(outDir, "cdperson_" + action);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalStateException("无法创建目录: " + dir);
        }
        quantize(image, QUANT_BITS);
        ImageIO.write(image, "png", new File(dir, index + ".png"));
    }

    /**
     * 降低 RGB 色深后保存
     *
     * <p>照片级素材在 PNG 里是无损存储，24 位色深下体积很大；
     * 每通道保留 7 位（128 级）肉眼几乎无差别，体积可显著下降。
     * 只处理 RGB，alpha 保持完整，透明的柔和边缘不受影响。</p>
     *
     * @param image 目标位图（就地修改）
     * @param bits 每通道保留位数
     */
    static void quantize(BufferedImage image, int bits) {
        if (bits >= 8 || bits < 1) {
            return;
        }
        int mask = (0xFF << (8 - bits)) & 0xFF;
        int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < pixels.length; i++) {
            int c = pixels[i];
            int a = (c >>> 24) & 0xFF;
            if (a == 0) {
                continue;
            }
            pixels[i] = (a << 24)
                    | ((((c >> 16) & 0xFF) & mask) << 16)
                    | ((((c >> 8) & 0xFF) & mask) << 8)
                    | ((c & 0xFF) & mask);
        }
    }

    static void quality(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    }

    static double ease(double t) {
        return t * t * (3 - 2 * t);
    }

    /** 生成总览图，便于快速核对观感 */
    static void overview() throws Exception {
        String[] actions = {"idle", "talk", "point", "point_l", "point_r",
                "click", "success", "fail", "thinking", "bye"};
        int cellW = 130;
        int cellH = 195;
        int cols = 6;
        BufferedImage sheet = new BufferedImage(cellW * cols, cellH * actions.length,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = sheet.createGraphics();
        quality(g);
        g.setColor(new Color(236, 238, 244));
        g.fillRect(0, 0, sheet.getWidth(), sheet.getHeight());
        g.setColor(new Color(110, 120, 140));
        g.setFont(new Font("微软雅黑", Font.PLAIN, 11));
        for (int row = 0; row < actions.length; row++) {
            File dir = new File(outDir, "cdperson_" + actions[row]);
            File[] frames = dir.listFiles();
            if (frames == null) {
                continue;
            }
            Arrays.sort(frames, (a, b) -> number(a.getName()) - number(b.getName()));
            for (int col = 0; col < cols; col++) {
                int pick = frames.length == 0 ? 0
                        : Math.min(frames.length - 1, col * frames.length / cols);
                BufferedImage frame = ImageIO.read(frames[pick]);
                g.drawImage(frame, col * cellW + 2, row * cellH + 14, cellW - 4, cellH - 20, null);
            }
            g.drawString(actions[row] + " (" + frames.length + "帧)", 4, row * cellH + 11);
        }
        g.dispose();
        File target = new File(System.getProperty("user.dir"), "target/skin-overview.png");
        ImageIO.write(sheet, "png", target);
        System.out.println("总览图 -> " + target.getAbsolutePath());
    }

    static int number(String name) {
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isDigit(c)) {
                digits.append(c);
            }
        }
        return digits.length() == 0 ? Integer.MAX_VALUE : Integer.parseInt(digits.toString());
    }
}
