package Dragon;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 *  泡泡文字提示
 *
 *  <p>全部用矢量绘制（圆角矩形 + 尾巴 + 换行文字），因此任意缩放下都清晰，
 *  不受位图分辨率限制；这是方案A里"位图只画角色、规则元素运行时绘制"的落地。</p>
 *
 *  <p>支持打字机效果：逐字显示，避免长报文一次性糊在屏幕上。</p>
 */
public class GDragonBubble extends JPanel {

    /**
     *  尾巴朝向
     */
    public enum Tail {
        /**
         *  左上角
         */
        LEFT_TOP,
        /**
         *  右上角
         */
        RIGHT_TOP,
        /**
         *  左下角
         */
        LEFT_BOTTOM,
        /**
         *  右下角
         */
        RIGHT_BOTTOM
    }

    /**
     *  默认背景色：25% 不透明
     *
     *  <p>弹窗浮在业务页面上，背景太实会把底下的操作对象挡住；
     *  25% 的白底既能托住深色文字，又基本不影响看页面。文字在背景之上绘制，
     *  不受这个透明度影响，仍然是实色。</p>
     */
    public static final Color DEFAULT_BACKGROUND = new Color(255, 255, 255, 64);

    /**
     *  默认边框色
     *
     * <p>背景降到 25% 之后基本看不出色差，气泡轮廓靠这条边框撑住，
     * 因此保持较高的 alpha，否则在浅色页面上提示会"没有形状"。</p>
     */
    public static final Color DEFAULT_BORDER = new Color(110, 140, 190, 210);

    private static final long serialVersionUID = -3227699371332766119L;

    /**
     *  构造泡泡
     *
     *  <p>关闭 Swing 的背景填充：可见背景全部由 {@link #paintComponent(Graphics)}
     *  自绘成圆角矩形 + 尾巴，否则 {@code super.paintComponent} 会在圆角之外
     *  铺满一整块不透明直角背景，把半透明效果盖掉。</p>
     */
    public GDragonBubble() {
        setOpaque(false);
    }

    /**
     *  文本内容
     */
    private String text = "";

    /**
     *  字体
     */
    private Font font = new Font(GDragonOption.DEFAULT_FONT_TYPE, Font.PLAIN, GDragonOption.DEFAULT_FONT_SIZE);

    /**
     *  字色
     */
    private Color textColor = GDragonOption.DEFAULT_FONT_COLOR;

    /**
     *  尾巴朝向
     */
    private Tail tail = Tail.LEFT_TOP;

    /**
     *  已显示字数，用于打字机效果
     */
    private int shownChars = Integer.MAX_VALUE;

    /**
     *  打字机定时器
     */
    private Timer typingTimer;

    /**
     *  圆角半径
     */
    private int arc = 16;

    /**
     *  内边距
     */
    private int padding = 12;

    /**
     *  设置文本
     *
     *  @param text 文本
     */
    public void setText(String text) {
        this.text = text == null ? "" : text;
        this.shownChars = Integer.MAX_VALUE;
        repaint();
    }

    /**
     *  设置字体与字色
     *
     *  @param font 字体
     *  @param textColor 字色
     */
    public void setTextStyle(Font font, Color textColor) {
        if (font != null) {
            this.font = font;
        }
        if (textColor != null) {
            this.textColor = textColor;
        }
        repaint();
    }

    /**
     *  设置尾巴朝向
     *
     *  @param tail 尾巴朝向
     */
    public void setTail(Tail tail) {
        this.tail = tail == null ? Tail.LEFT_TOP : tail;
        repaint();
    }

    /**
     *  获得尾巴朝向
     *
     *  @return 尾巴朝向
     */
    public Tail getTail() {
        return tail;
    }

    /**
     *  以打字机方式显示文本
     *
     *  @param text 文本
     *  @param durationMs 显示完所需时间，单位毫秒
     */
    public void type(String text, long durationMs) {
        stopTyping();
        this.text = text == null ? "" : text;
        int total = this.text.length();
        if (total == 0) {
            repaint();
            return;
        }
        long span = Math.max(durationMs, 200);
        int step = Math.max(total / (int) (span / 40), 1);
        shownChars = 0;
        typingTimer = new Timer(40, e -> {
            shownChars += step;
            if (shownChars >= total) {
                shownChars = Integer.MAX_VALUE;
                stopTyping();
            }
            repaint();
        });
        typingTimer.start();
    }

    /**
     *  停止打字机效果
     */
    public void stopTyping() {
        if (typingTimer != null) {
            typingTimer.stop();
            typingTimer = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) {
            return;
        }

        int tailSize = 14;
        int bodyW = w - tailSize;
        int bodyX = tail == Tail.LEFT_TOP || tail == Tail.LEFT_BOTTOM ? tailSize : 0;
        int bodyH = h - 6;

        //气泡主体
        g2.setColor(DEFAULT_BACKGROUND);
        g2.fill(new RoundRectangle2D.Float(bodyX, 2, Math.max(bodyW, 10), Math.max(bodyH, 10), arc, arc));
        g2.setColor(DEFAULT_BORDER);
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Float(bodyX, 2, Math.max(bodyW, 10), Math.max(bodyH, 10), arc, arc));

        //气泡尾巴
        g2.setColor(DEFAULT_BACKGROUND);
        GeneralPath path = new GeneralPath();
        boolean left = tail == Tail.LEFT_TOP || tail == Tail.LEFT_BOTTOM;
        boolean top = tail == Tail.LEFT_TOP || tail == Tail.RIGHT_TOP;
        float tipX = left ? 1f : w - 1f;
        float baseY = top ? (float) h * 0.28f : (float) h * 0.72f;
        path.moveTo(tipX, baseY);
        path.lineTo(left ? tailSize + 1f : w - tailSize - 1f, baseY - tailSize / 2f);
        path.lineTo(left ? tailSize + 1f : w - tailSize - 1f, baseY + tailSize / 2f);
        path.closePath();
        g2.fill(path);
        g2.setColor(DEFAULT_BORDER);
        g2.draw(path);

        //文本
        g2.setFont(font);
        g2.setColor(textColor);
        FontMetrics metrics = g2.getFontMetrics();
        String visible = shownChars == Integer.MAX_VALUE ? text : text.substring(0, Math.min(shownChars, text.length()));
        List<String> lines = wrap(visible, metrics, Math.max(bodyW - padding * 2, 10));
        int lineHeight = metrics.getHeight();
        int y = padding + metrics.getAscent();
        int maxLines = Math.max((h - padding) / Math.max(lineHeight, 1), 1);
        for (int i = 0; i < lines.size() && i < maxLines; i++) {
            g2.drawString(lines.get(i), bodyX + padding, y);
            y += lineHeight;
        }
    }

    /**
     *  按可用宽度换行
     *
     *  @param text 文本
     *  @param metrics 字体度量
     *  @param maxWidth 最大宽度
     *  @return 行文本清单
     */
    private static List<String> wrap(String text, FontMetrics metrics, int maxWidth) {
        List<String> lines = new ArrayList<>();
        if (text.isEmpty()) {
            return lines;
        }
        for (String paragraph : text.split("\n", -1)) {
            if (paragraph.isEmpty()) {
                lines.add("");
                continue;
            }
            StringBuilder line = new StringBuilder();
            for (char c : paragraph.toCharArray()) {
                if (metrics.stringWidth(line.toString() + c) > maxWidth && line.length() > 0) {
                    lines.add(line.toString());
                    line = new StringBuilder();
                }
                line.append(c);
            }
            lines.add(line.toString());
        }
        return lines;
    }
}
