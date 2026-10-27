package Dragon;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 *  弹窗基类
 *
 *  <p>提供无边框窗体的鼠标拖拽、缩放与三个图片按钮。与人形弹窗业务逻辑解耦：
 *  按钮点击不再直接写日志，而是回调 {@link ButtonHandler}，由子类决定如何上报。</p>
 */
public class GBase extends JFrame implements MouseMotionListener, MouseListener {

    /**
     *  句柄编号
     */
    private static final long serialVersionUID = 7502511063783928888L;

    /**
     *  指针
     */
    Point sp;

    /**
     *  实际竖直高度
     */
    protected int compStartHeight;

    /**
     *  实际水平宽度
     */
    protected int compStartWidth;

    /**
     *  关闭按钮
     */
    protected JImageButton jBtn_Close;

    /**
     *  确定按钮
     */
    protected JImageButton jBtn_Yes;

    /**
     *  取消按钮
     */
    protected JImageButton jBtn_No;

    /**
     *  事件回调
     */
    protected GDragonListener listener = GDragonListener.silent();

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
     *  鼠标移动时切换光标形状，提示"可调整窗口大小"
     *
     *  <p>靠近右边界显示水平调整光标，靠近下边界显示垂直调整光标。</p>
     *
     *  @param e 鼠标事件，用于取得光标坐标与触发组件尺寸
     */
    public void mouseMoved(MouseEvent e) {
        Point p = e.getPoint();

        if (p.y > e.getComponent().getSize().height - 5) {
            setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        } else if (p.x > e.getComponent().getSize().width - 5) {
            setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
        }
    }

    /**
     *  鼠标拖拽时按光标类型调整窗口尺寸
     *
     *  <p>依据 {@link #mousePressed(MouseEvent)} 记录的起始尺寸与按下点，
     *  计算新的宽高并重设窗口大小，实现无边框窗口的拖拽缩放。</p>
     *
     *  @param e 鼠标事件，用于取得当前光标坐标
     */
    public void mouseDragged(MouseEvent e) {
        Point p = e.getPoint();
        int compWidth = getSize().width;
        int compHight = getSize().height;
        if (getCursor().getType() == Cursor.N_RESIZE_CURSOR) {
            int nextHeight = compStartHeight + p.y - sp.y;
            if (nextHeight > 100) {
                setSize(compWidth, nextHeight);
                setVisible(true);
            }
        } else if (getCursor().getType() == Cursor.W_RESIZE_CURSOR) {
            int nextWidth = compStartWidth + p.x - sp.x;
            if (nextWidth > 100) {
                setSize(nextWidth, compHight);
                setVisible(true);
            }
        } else {
            int x = getX() + p.x - sp.x;
            int y = getY() + p.y - sp.y;
            setLocation(x, y);
        }
    }

    /**
     *  鼠标按下时记录起始状态
     *
     *  <p>保存按下点坐标与当前窗口宽高，供 {@link #mouseDragged(MouseEvent)} 计算缩放量。</p>
     *
     *  @param e 鼠标事件，用于取得按下点坐标
     */
    public void mousePressed(MouseEvent e) {
        sp = e.getPoint();
        compStartHeight = getSize().height;
        compStartWidth = getSize().width;
    }

    /**
     *  鼠标进入组件
     *
     *  <p>当前为空实现，保留以完整实现 {@code MouseListener} 接口。</p>
     *
     *  @param e 鼠标事件
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     *  鼠标离开组件时恢复默认光标
     *
     *  <p>仅在未处于拖拽状态（{@code sp} 为 {@code null}）时恢复，
     *  避免缩放过程中光标闪烁。</p>
     *
     *  @param e 鼠标事件
     */
    public void mouseExited(MouseEvent e) {
        if (sp == null) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     *  鼠标点击
     *
     *  <p>当前为空实现，保留以完整实现 {@code MouseListener} 接口。</p>
     *
     *  @param e 鼠标事件
     */
    public void mouseClicked(MouseEvent e) {
    }

    /**
     *  鼠标释放时清除拖拽状态
     *
     *  @param e 鼠标事件
     */
    public void mouseReleased(MouseEvent e) {
        sp = null;
    }

    public GBase() {
        super("testing frame");
    }

    /**
     *  装配确定/取消/关闭三个图片按钮
     *
     *  <p>按钮点击后由 {@code handler} 决定后续动作，基类不再依赖日志体系。</p>
     *
     *  @param handler 按钮点击回调
     */
    protected void add3Button(ButtonHandler handler) {
        ButtonHandler safeHandler = handler == null ? action -> { } : handler;

        //配置关闭按钮
        jBtn_Close = newButton(GDragonResource.icon("close_tm.png"), Action.CLOSE, safeHandler);

        //配置确定按钮
        jBtn_Yes = newButton(GDragonResource.icon("ok_tm.png"), Action.YES, safeHandler);

        //配置取消按钮
        jBtn_No = newButton(GDragonResource.icon("cancel_tm.png"), Action.NO, safeHandler);
    }

    /**
     *  创建一个图片按钮
     *
     *  @param icon 按钮图片；为 {@code null} 时退化为文字按钮，保证按钮始终可见可用
     *  @param action 按钮动作
     *  @param handler 点击回调
     *  @return 图片按钮
     */
    private JImageButton newButton(ImageIcon icon, Action action, ButtonHandler handler) {
        JImageButton button;
        if (icon == null) {
            button = new JImageButton(new ImageIcon(new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB)));
            button.setText(action.name());
            button.setFont(new Font("宋体", Font.PLAIN, 12));
        } else {
            button = new JImageButton(icon);
        }
        button.addActionListener(e -> handler.onClick(action));
        return button;
    }

    /**
     *  按钮动作
     */
    public enum Action {
        /**
         *  确定
         */
        YES,
        /**
         *  取消
         */
        NO,
        /**
         *  关闭
         */
        CLOSE
    }

    /**
     *  按钮点击回调
     */
    @FunctionalInterface
    public interface ButtonHandler {

        /**
         *  按钮被点击
         *
         *  @param action 被点击的按钮
         */
        void onClick(Action action);
    }
}
