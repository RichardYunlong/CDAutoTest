package Dragon;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.GeneralPath;

/**
 *  指向网页元素
 *
 *  <p>方案A中"点击网页元素/指向元素"这一环，Dragon 包刻意<b>不依赖 Selenium</b>：
 *  调用方用 {@code element.getRect()} 换算出屏幕坐标后传进来（滚动变化时可调用
 *  {@link GDragon#setTarget(Point)} 更新），高亮本身建议复用 Webdriver 层的
 *  {@code GWCtrlHighLight}（JS 注入，跟随页面滚动，比在 Swing 里画更准）。</p>
 *
 *  <p>这里负责两件事：</p>
 *  <ol>
 *      <li>把角色窗口摆到目标旁边（{@link #anchor}），让角色"站在"元素一侧；</li>
 *      <li>判断目标相对窗口的方向（{@link #direction}），弹窗据此调整气泡尾巴朝向。</li>
 *  </ol>
 *
 *  <p>{@link Layer} 是可选的独立箭头图层，<b>当前没有启用</b>：气泡尾巴已经在表达
 *  "元素在那个方向"，再画一条黄线加红圈属于重复提示。</p>
 */
public final class GDragonPointer {

    /**
     *  角色与目标之间的间距
     *
     *  <p>12 像素时箭头的尖端正好落在元素高亮框（6 像素描边 + 2 像素偏移）外侧，
     *  看起来是"指到"了元素；再大就会和元素之间出现明显空隙。</p>
     */
    public static final int GAP = 12;

    private GDragonPointer() {
    }

    /**
     *  计算角色窗口左上角坐标，使其紧邻目标且不越界
     *
     *  <p>只给出一个点时，退化为"目标在屏幕左半边则站右侧，反之站左侧"。</p>
     *
     *  @param target 目标屏幕坐标
     *  @param width 窗口宽
     *  @param height 窗口高
     *  @return 窗口左上角坐标
     */
    public static Point anchor(Point target, int width, int height) {
        return anchor(target == null ? null : new Rectangle(target.x, target.y, 0, 0), width, height);
    }

    /**
     *  计算角色窗口左上角坐标，在目标四周自动挑一个合理方位
     *
     *  <p>依次考察<b>右、左、下、上</b>四个方位，按下面两条规则挑最优解：</p>
     *  <ol>
     *      <li>窗口压住目标元素的面积越小越好（不能挡住正在操作的元素）；</li>
     *      <li>窗口被屏幕边界挤掉的位移越小越好（出界会被夹回，导致位置失真）。</li>
     *  </ol>
     *
     *  <p>两者都相同时按"右侧优先、其次左侧/下方/上方"的顺序取，符合多数阅读习惯。</p>
     *
     *  @param target 目标屏幕矩形；为 {@code null} 时贴屏幕右下角
     *  @param width 窗口宽
     *  @param height 窗口高
     *  @return 窗口左上角坐标
     */
    public static Point anchor(Rectangle target, int width, int height) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int maxX = Math.max(screen.width - width, 0);
        int maxY = Math.max(screen.height - height, 0);
        if (target == null) {
            return new Point(maxX, maxY);
        }

        int alignX = target.x + target.width / 2 - width / 2;
        int alignY = target.y + target.height / 2 - height / 2;
        int[][] candidates = {
                {target.x + target.width + GAP, alignY},
                {target.x - width - GAP, alignY},
                {alignX, target.y + target.height + GAP},
                {alignX, target.y - height - GAP},
        };

        long bestScore = Long.MAX_VALUE;
        int bestX = 0;
        int bestY = 0;
        long bestOverlap = 0;
        for (int i = 0; i < candidates.length; i++) {
            int x = clamp(candidates[i][0], 0, maxX);
            int y = clamp(candidates[i][1], 0, maxY);
            Rectangle window = new Rectangle(x, y, width, height);
            long overlap = overlapArea(window, target);
            long score = overlap * 100L
                    + (Math.abs(candidates[i][0] - x) + Math.abs(candidates[i][1] - y)) * 2L
                    + i;
            if (score < bestScore) {
                bestScore = score;
                bestX = x;
                bestY = y;
                bestOverlap = overlap;
            }
        }
        if (bestOverlap > 0) {
            //四个方位被屏幕边界夹回来之后仍然压着元素：宁可让窗口探出屏幕一点，
            //也不能盖住正在操作的地方——元素看得见，弹窗指的哪里才看得懂
            if (screen.width - (target.x + target.width) >= target.x) {
                bestX = target.x + target.width + GAP;
            } else {
                bestX = target.x - width - GAP;
            }
            bestY = clamp(alignY, 0, maxY);
        }
        return new Point(bestX, bestY);
    }

    /**
     *  计算两个矩形的重叠面积
     *
     *  @param one 矩形一
     *  @param other 矩形二
     *  @return 重叠面积；无重叠返回 0
     */
    private static long overlapArea(Rectangle one, Rectangle other) {
        int x = Math.max(one.x, other.x);
        int y = Math.max(one.y, other.y);
        int right = Math.min(one.x + one.width, other.x + other.width);
        int bottom = Math.min(one.y + one.height, other.y + other.height);
        if (right <= x || bottom <= y) {
            return 0;
        }
        return (long) (right - x) * (bottom - y);
    }

    /**
     *  把数值限制在给定区间内
     *
     *  @param value 数值
     *  @param min 下界
     *  @param max 上界
     *  @return 限制后的值
     */
    private static int clamp(int value, int min, int max) {
        if (max < min) {
            return min;
        }
        return Math.min(Math.max(value, min), max);
    }

    /**
     *  判断目标相对窗口的方向
     *
     *  @param target 目标屏幕坐标
     *  @param windowX 窗口横坐标
     *  @param windowY 窗口纵坐标
     *  @param width 窗口宽
     *  @param height 窗口高
     *  @return 方向：0-左上 1-右上 2-左下 3-右下
     */
    public static int direction(Point target, int windowX, int windowY, int width, int height) {
        if (target == null) {
            return 0;
        }
        boolean left = target.x < windowX + width / 2;
        boolean top = target.y < windowY + height / 2;
        if (left && top) {
            return 0;
        }
        if (!left && top) {
            return 1;
        }
        if (left) {
            return 2;
        }
        return 3;
    }

    /**
     *  指向箭头图层（可选）
     *
     *  <p>透明覆盖层，叠在角色绘制面板之上，画一个朝目标方向的黄色箭头与末端红圈。</p>
     *
     *  <p>当前弹窗没有启用它：方向由气泡尾巴表达，两者重复。需要独立的"指过去"效果时
     *  可以重新叠上去。注意箭头终点按方向取画布内的固定分位，不是元素的精确坐标，
     *  只能表示大概方位。</p>
     */
    public static class Layer extends JPanel {

        private static final long serialVersionUID = -4981247772933277691L;

        /**
         *  方向：0-左上 1-右上 2-左下 3-右下
         */
        private volatile int direction = 0;

        /**
         *  是否显示
         */
        private volatile boolean visibleArrow = true;

        /**
         *  构造图层
         */
        public Layer() {
            setOpaque(false);
        }

        /**
         *  设置方向
         *
         *  @param direction 方向
         */
        public void setDirection(int direction) {
            this.direction = direction;
            repaint();
        }

        /**
         *  设置是否显示箭头
         *
         *  @param visibleArrow 显示返回 {@code true}
         */
        public void setVisibleArrow(boolean visibleArrow) {
            this.visibleArrow = visibleArrow;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (!visibleArrow) {
                return;
            }
            int w = getWidth();
            int h = getHeight();
            if (w <= 0 || h <= 0) {
                return;
            }
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            float fromX = w * 0.5f;
            float fromY = h * 0.45f;
            float toX;
            float toY;
            switch (direction) {
                case 1:
                    toX = w - 6f;
                    toY = h * 0.28f;
                    break;
                case 2:
                    toX = 6f;
                    toY = h * 0.72f;
                    break;
                case 3:
                    toX = w - 6f;
                    toY = h * 0.72f;
                    break;
                default:
                    toX = 6f;
                    toY = h * 0.28f;
                    break;
            }

            //指向线
            g2.setColor(new Color(255, 196, 0, 220));
            g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine((int) fromX, (int) fromY, (int) toX, (int) toY);

            //箭头
            double angle = Math.atan2(toY - fromY, toX - fromX);
            float size = 12f;
            GeneralPath path = new GeneralPath();
            path.moveTo(toX, toY);
            path.lineTo((float) (toX - size * Math.cos(angle - 0.45)), (float) (toY - size * Math.sin(angle - 0.45)));
            path.lineTo((float) (toX - size * Math.cos(angle + 0.45)), (float) (toY - size * Math.sin(angle + 0.45)));
            path.closePath();
            g2.fill(path);

            //目标圈
            g2.setColor(new Color(255, 64, 64, 200));
            g2.setStroke(new BasicStroke(2.5f));
            g2.drawOval((int) toX - 9, (int) toY - 9, 18, 18);
        }
    }
}
