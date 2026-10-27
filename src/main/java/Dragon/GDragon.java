package Dragon;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.OverlayLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *  智能助理提示窗（人形弹窗）
 *
 *  <p>一个无边框、可定时自动消失的 Swing 提示气泡：左侧显示人形头像，右侧显示提示文本，
 *  底部提供确定/取消/关闭三个按钮。</p>
 *
 *  <p>相比改造前，这里做了三处关键调整，使它可以被独立调试与持续优化：</p>
 *  <ol>
 *      <li><b>构造函数不再阻塞</b>：原实现在构造函数里 {@code Thread.sleep} 再 {@code dispose}，
 *          既无法单步观察，也无法在单线程环境复用；现在构造只准备参数，
 *          {@link #display()} 才真正显示，{@link #close(GDragonListener.Reason)} 主动关闭；</li>
 *      <li><b>不再依赖日志体系</b>：生命周期通过 {@link GDragonListener} 上报，
 *          调试时用 {@link GDragonListener#console()}，接入主流程时由调用方注入桥接实现；</li>
 *      <li><b>图片按 classpath 定位</b>：见 {@link GDragonResource}，不再受工作目录影响，
 *          图片缺失时使用兜底尺寸而不是负值。</li>
 *  </ol>
 *
 *  <p>典型用法（推荐）：</p>
 *  <pre>{@code
 *  GDragonOption option = GDragonOption.builder().message("用例执行完毕").time(3000).build();
 *  GDragon window = new GDragon(option);
 *  window.display();
 *  }</pre>
 *
 *  <p>兼容用法（构造即显示，不阻塞调用线程）：</p>
 *  <pre>{@code
 *  new GDragon("用例执行完毕", 3000, 1200, 700, "", "微软雅黑", 14, Color.BLUE);
 *  }</pre>
 *
 *  @see GDragonOption
 *  @see GDragonService
 *  @see GBase
 */
public class GDragon extends GBase {

    /**
     *  句柄编号
     */
    private static final long serialVersionUID = -2185678343896377699L;

    /**
     *  单位块图片
     */
    private static final String IMG25 = "empty25.png";

    /**
     *  关闭按钮图片
     */
    private static final String IMG_CLOSE = "close_tm.png";

    /**
     *  显示参数
     *
     *  <p>非 final：连续提示时由 {@link #update(GDragonOption)} 原地换内容，避免反复新建窗口。</p>
     */
    private volatile GDragonOption option;

    /**
     *  实际使用的人形头像
     */
    private volatile String roleImage = "";

    /**
     *  箭头方向
     *  0-左上
     *  1-右上
     *  2-左下
     *  3-右下
     */
    private int arrowType = 0;

    /**
     *  动作播放控制器
     */
    private final GDragonAnimation animation;

    /**
     *  角色绘制面板
     */
    private GDragonStage stage;

    /**
     *  泡泡文字
     */
    private GDragonBubble bubble;

    /**
     *  消息标题
     */
    private JLabel jL_Title;

    /**
     *  指向目标元素的黑色箭头
     *
     *  <p>放在分层窗格的浮层里而不是布局里：窗口可能位于元素的上下左右任意一侧，
     *  箭头只有离开布局才能贴到对应的那条边上，并沿边对齐元素最近的那个角。</p>
     */
    private JLabel jL_Arrow;

    /**
     *  标题与气泡所在的面板
     */
    private JPanel msgPanel;

    /**
     *  当前指向的目标
     *
     *  <p>由 {@link #setTarget(Rectangle)} 持续更新；为 {@code null} 时回退到
     *  {@link GDragonOption#getTargetRect()}。元素滚动或页面重排后，只有这里跟着变，
     *  箭头和窗口位置才会跟着走。</p>
     */
    private Rectangle liveTarget;

    /**
     *  角色显示尺寸
     */
    private Dimension roleSize;

    /**
     *  单位块尺寸
     */
    private Dimension blockSize;

    /**
     *  功能按钮区高度
     */
    private int closeHeight;

    /**
     *  语音输出
     */
    private volatile GDragonVoice voice = GDragonVoice.silent();

    /**
     *  自动关闭定时器
     */
    private Timer closeTimer;

    /**
     *  关闭信号，供调试时串行预览多个弹窗
     */
    private final CountDownLatch closedSignal = new CountDownLatch(1);

    /**
     *  构造人形弹窗
     *
     *  <p>仅准备参数，不创建窗体，不阻塞线程。</p>
     *
     *  @param option 显示参数
     */
    public GDragon(GDragonOption option) {
        this(option, GDragonListener.console());
    }

    /**
     *  构造人形弹窗
     *
     *  @param option 显示参数
     *  @param listener 事件回调
     */
    public GDragon(GDragonOption option, GDragonListener listener) {
        super();
        this.option = option == null ? GDragonOption.defaults() : option;
        this.animation = new GDragonAnimation(GDragonSkin.getDefault());
        setListener(listener);
    }

    /**
     *  构造人形弹窗并立即显示
     *
     *  <p>保留历史签名，供既有调用点平滑过渡；与改造前的区别是不再阻塞调用线程。</p>
     *
     *  @param message 信息内容
     *  @param mTime 持续时间，单位毫秒
     *  @param dX 提示位置横坐标
     *  @param dY 提示位置纵坐标
     *  @param img 人形头像图片名；为空时随机选取
     *  @param fType 字体
     *  @param fSize 字号
     *  @param fColor 字色
     */
    public GDragon(String message, int mTime, int dX, int dY, String img, String fType, int fSize, Color fColor) {
        this(GDragonOption.builder()
                .message(message)
                .time(mTime)
                .location(dX, dY)
                .image(img)
                .font(fType, fSize, fColor)
                .build());
        display();
    }

    /**
     *  获得显示参数
     *
     *  @return 显示参数
     */
    public GDragonOption getOption() {
        return option;
    }

    /**
     *  按参数中的持续时间显示弹窗
     *
     *  <p>窗体创建与显示都安排在事件派发线程上，调用方可以在任意线程调用。</p>
     */
    public void display() {
        display(option.getMTime());
    }

    /**
     *  显示弹窗并在指定时间后自动关闭
     *
     *  @param millis 持续时间，单位毫秒；小于等于0时取参数中的默认值
     */
    public void display(long millis) {
        final long keep = millis > 0 ? millis : option.getMTime();
        SwingUtilities.invokeLater(() -> {
            try {
                buildUI();
                scheduleClose(keep);
                startAnimationAndVoice();
                setVisible(true);
                listener.onShow(option);
            } catch (Exception e) {
                listener.onError("display", e);
                markClosed(GDragonListener.Reason.ERROR);
            }
        });
    }

    /**
     *  原地更新弹窗内容
     *
     *  <p>连续提示时复用同一个窗口：只换文字、动作与指向，并重新计时长，
     *  不新建窗口、不重新播放入场过程。这样快速操作时屏幕上始终只有这一个弹窗，
     *  不会出现"一堆弹窗冒出来又消失、文字没看清"的情况。</p>
     *
     *  <p>已关闭的窗口会被忽略，调用方需要重新弹出时请用
     *  {@link GDragonService#show(GDragonOption)}。</p>
     *
     *  @param next 新的显示参数；为 {@code null} 时忽略
     */
    public void update(GDragonOption next) {
        if (next == null) {
            return;
        }
        SwingUtilities.invokeLater(() -> {
            if (closedSignal.getCount() == 0 || !isDisplayable()) {
                return;
            }
            option = next;
            if (bubble != null) {
                if (next.isTyping()) {
                    bubble.type(next.getMessage(), Math.min(next.getMTime(), 1500));
                } else {
                    bubble.setText(next.getMessage());
                }
            }
            //文字变了宽度也要跟着变，上限仍是标准尺寸
            computeSize();
            setSize(compStartWidth, compStartHeight);
            Rectangle rect = next.getTargetRect();
            liveTarget = rect;
            if (rect != null) {
                Point location = GDragonPointer.anchor(rect, compStartWidth, compStartHeight);
                setLocation(location.x, location.y);
                Point center = new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
                arrowType = GDragonPointer.direction(center, location.x, location.y,
                        compStartWidth, compStartHeight);
            }
            applyArrowIcons();
            placeArrows();
            revalidate();
            repaint();
            //放在箭头方向算出来之后：指向动作要按最新的朝向换左右素材
            if (animation != null) {
                animation.play(next.getAction(), pointingLeft(arrowType));
            }
            scheduleClose(next.getMTime());
        });
    }

    /**
     *  关闭弹窗
     *
     *  @param reason 关闭原因
     */
    public void close(GDragonListener.Reason reason) {
        SwingUtilities.invokeLater(() -> {
            stopTimer();
            if (stage != null) {
                stage.stop();
            }
            if (bubble != null) {
                bubble.stopTyping();
            }
            if (isDisplayable()) {
                dispose();
            }
            markClosed(reason);
        });
    }

    /**
     *  等待弹窗关闭
     *
     *  <p>供调试入口串行预览多个弹窗时使用。</p>
     *
     *  @param millis 最长等待时间，单位毫秒
     *  @return 弹窗已关闭返回 {@code true}；超时返回 {@code false}
     */
    public boolean awaitClosed(long millis) {
        try {
            return closedSignal.await(millis, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     *  弹窗是否已关闭
     *
     *  @return 已关闭返回 {@code true}
     */
    public boolean isClosed() {
        return closedSignal.getCount() == 0;
    }

    /**
     *  获得动作播放控制器
     *
     *  @return 播放控制器
     */
    public GDragonAnimation getAnimation() {
        return animation;
    }

    /**
     *  获得角色绘制面板
     *
     *  @return 绘制面板；窗体未构建时返回 {@code null}
     */
    public GDragonStage getStage() {
        return stage;
    }

    /**
     *  设置语音输出
     *
     *  @param voice 语音实现
     */
    public void setVoice(GDragonVoice voice) {
        this.voice = voice == null ? GDragonVoice.silent() : voice;
    }

    /**
     *  获得语音输出
     *
     *  @return 语音实现
     */
    public GDragonVoice getVoice() {
        return voice;
    }

    /**
     *  更新指向目标
     *
     *  <p>网页元素滚动或位置变化时，调用方换算出新坐标后调用本方法，
     *  角色会重新贴到目标旁边并调整箭头方向。</p>
     *
     *  @param target 目标屏幕坐标；为 {@code null} 时保持原样
     */
    public void setTarget(Point target) {
        setTarget(target == null ? null : new Rectangle(target.x, target.y, 0, 0));
    }

    /**
     *  更新指向目标
     *
     *  <p>给出完整矩形时窗口按矩形避让摆放，只给点时退化为按点定位。
     *  方向变了会重播一次动作，人物才不会“背对着”新位置的元素。</p>
     *
     *  @param rect 目标屏幕矩形；为 {@code null} 时保持原样
     */
    public void setTarget(Rectangle rect) {
        SwingUtilities.invokeLater(() -> {
            if (stage == null || rect == null) {
                return;
            }
            liveTarget = rect;
            int oldType = arrowType;
            Point location = GDragonPointer.anchor(rect, getWidth(), getHeight());
            setLocation(location.x, location.y);
            arrowType = GDragonPointer.direction(
                    new Point(rect.x + rect.width / 2, rect.y + rect.height / 2),
                    location.x, location.y, getWidth(), getHeight());
            applyArrowIcons();
            placeArrows(rect);
            if (animation != null && oldType != arrowType) {
                animation.play(animation.getAction(), pointingLeft(arrowType));
            }
        });
    }

    /**
     *  获得当前指向的目标矩形
     *
     *  @return 目标屏幕矩形；无目标返回 {@code null}
     */
    public Rectangle getTargetRect() {
        return liveTarget != null ? liveTarget : option.getTargetRect();
    }

    /**
     *  获得箭头在屏幕上的位置
     *
     * <p>箭头放在分层窗格浮层里，坐标是相对窗口的；换算成屏幕坐标后
     * 才能和元素矩形直接相减，判断到底指偏了多少。</p>
     *
     *  @return 箭头屏幕矩形；箭头未摆放时返回 {@code null}
     */
    public Rectangle getArrowBounds() {
        if (jL_Arrow == null || jL_Arrow.getWidth() <= 0) {
            return null;
        }
        Rectangle bounds = jL_Arrow.getBounds();
        bounds.translate(getX(), getY());
        return bounds;
    }

    /**
     *  输出弹窗与播放状态，供调试核对
     *
     *  @return 状态摘要
     */
    public String stats() {
        return "GDragon{action=" + animation.getAction().getName()
                + ", role='" + roleImage + '\''
                + ", " + (stage == null ? "stage=none" : stage.stats())
                + ", voice=" + voice.describe()
                + '}';
    }

    /**
     *  启动动画、泡泡文字与语音
     *
     *  <p>必须在事件派发线程上执行。</p>
     */
    private void startAnimationAndVoice() {
        if (stage == null) {
            return;
        }
        final GDragonAction action = option.getAction();
        final boolean left = pointingLeft(arrowType);
        if (option.isAnimate()) {
            stage.play(action, left);
            stage.start();
        } else {
            animation.play(action, left);
            stage.repaint();
        }

        if (bubble != null) {
            if (option.isTyping()) {
                bubble.type(option.getMessage(), Math.min(option.getMTime(), 1500));
            } else {
                bubble.setText(option.getMessage());
            }
        }

        String voiceText = option.getVoice();
        if (voiceText != null && !voiceText.trim().isEmpty() && voice.isAvailable()) {
            stage.play(GDragonAction.TALK);
            voice.speak(voiceText, () -> SwingUtilities.invokeLater(() -> {
                if (!isClosed() && stage != null) {
                    stage.play(action, left);
                }
            }));
        }
    }

    /**
     *  按文字长度计算窗口与气泡尺寸
     *
     *  <p>宽度取气泡所需宽度与标题宽度的较大者，再加上角色与左右箭头列；
     *  气泡高度按换行行数计算。两者都以标准尺寸为上限，文字多时不会撑出原尺寸。</p>
     */
    private void computeSize() {
        int maxMsgW = roleSize.width * 3;
        int titleW = jL_Title == null ? 0 : jL_Title.getPreferredSize().width;
        int titleH = jL_Title == null ? 0 : jL_Title.getPreferredSize().height;
        int maxBubbleH = Math.max(roleSize.height - titleH - 4, 40);

        Dimension bubbleDim;
        if (bubble != null) {
            bubbleDim = bubble.fitSize(maxMsgW, maxBubbleH);
            bubble.setPreferredSize(bubbleDim);
            bubble.setMinimumSize(bubbleDim);
            bubble.setMaximumSize(bubbleDim);
        } else {
            bubbleDim = new Dimension(maxMsgW, maxBubbleH);
        }

        int msgW = Math.min(Math.max(bubbleDim.width, titleW) + 4, maxMsgW);
        compStartWidth = msgW + roleSize.width + blockSize.width * 2;
        compStartHeight = closeHeight + roleSize.height;
        if (msgPanel != null) {
            msgPanel.setPreferredSize(new Dimension(msgW, roleSize.height));
        }
    }

    /**
     *  按当前方向设置箭头图标
     *
     *  <p>四个图标对应元素的四个象限：箭头贴在元素所在那一侧的边上，指向元素的那个角。</p>
     */
    private void applyArrowIcons() {
        if (jL_Arrow == null) {
            return;
        }
        String name;
        switch (arrowType) {
            case 1: {
                name = "rt.png";
                break;
            }
            case 2: {
                name = "lb.png";
                break;
            }
            case 3: {
                name = "rb.png";
                break;
            }
            default: {
                name = "lt.png";
                break;
            }
        }
        jL_Arrow.setIcon(loadIcon(name));
    }

    /**
     *  把箭头摆到元素最近的那个角外侧
     */
    private void placeArrows() {
        placeArrows(liveTarget != null ? liveTarget : option.getTargetRect());
    }

    /**
     *  把箭头摆到目标元素最近的那个角外侧
     *
     *  <p>先看窗口是被放在元素的哪一侧：左右分开就贴窗口的左右边，上下分开就贴上下边，
     *  再沿这条边对准元素最近的那个角。原来箭头只会贴在窗口左右两侧，
     *  窗口一旦落到元素上方或下方，箭头就横向偏出元素一整段，看起来完全没指到。</p>
     *
     *  @param rect 元素屏幕矩形；为 {@code null} 时隐藏箭头
     */
    private void placeArrows(Rectangle rect) {
        if (jL_Arrow == null) {
            return;
        }
        if (rect == null) {
            jL_Arrow.setBounds(0, 0, 0, 0);
            return;
        }
        int size = jL_Arrow.getIcon() == null ? 0 : jL_Arrow.getIcon().getIconWidth();
        if (size <= 0) {
            size = 25;
        }
        int wx = getX();
        int wy = getY();
        int ww = getWidth();
        int wh = getHeight();

        //窗口与元素在哪个轴上分得更开，就贴哪条边
        int apartX = Math.max(rect.x - (wx + ww), wx - (rect.x + rect.width));
        int apartY = Math.max(rect.y - (wy + wh), wy - (rect.y + rect.height));

        int x;
        int y;
        if (apartX >= apartY) {
            x = rect.x + rect.width <= wx ? 0 : ww - size;
            //沿边对准元素靠近窗口的那条横边，箭头就落在元素的角旁边
            int corner = rect.y + rect.height / 2 < wy + wh / 2 ? rect.y + rect.height : rect.y;
            y = corner - wy - size / 2;
        } else {
            y = rect.y + rect.height <= wy ? 0 : wh - size;
            int corner = rect.x + rect.width / 2 < wx + ww / 2 ? rect.x + rect.width : rect.x;
            x = corner - wx - size / 2;
        }
        x = Math.max(Math.min(x, Math.max(ww - size, 0)), 0);
        y = Math.max(Math.min(y, Math.max(wh - size, 0)), 0);
        jL_Arrow.setBounds(x, y, size, size);
    }

    /**
     *  按气泡箭头方向确定人物朝向
     *
     *  <p>元素在弹窗左侧时人物朝左、右侧时朝右，指向动作才不会"背对着目标"。</p>
     *
     *  @param arrowType 箭头方向 0-左上 1-右上 2-左下 3-右下
     *  @return 人物是否面朝左侧
     */
    private static boolean pointingLeft(int arrowType) {
        return arrowType == 0 || arrowType == 2;
    }

    /**
     *  构建窗体内容
     *
     *  <p>必须在事件派发线程上执行。</p>
     */
    private void buildUI() {
        roleImage = resolveRole();

        int scale = GDragonStage.resolveScale();
        GDragonSkin skin = animation.getSkin();
        Dimension role = new Dimension(skin.getFrameWidth() * scale, skin.getFrameHeight() * scale);
        Dimension block = GDragonResource.blockSize(IMG25);
        Dimension closeButton = GDragonResource.blockSize(IMG_CLOSE);

        //标准尺寸：宽度为4个角色宽度，高度为角色高度加功能按钮高度
        //它是“最大尺寸”，实际大小由 computeSize() 按文字长度收缩
        roleSize = role;
        blockSize = block;
        closeHeight = closeButton.height;

        //箭头放浮层：不参与布局，才能贴到窗口的任意一条边上
        jL_Arrow = new JLabel();
        jL_Arrow.setOpaque(false);
        //左右两条留白，让气泡与人物不贴着窗口边缘
        JPanel spacerL = new JPanel();
        spacerL.setPreferredSize(new Dimension(block.width, role.height));
        spacerL.setOpaque(false);
        JPanel spacerR = new JPanel();
        spacerR.setPreferredSize(new Dimension(block.width, role.height));
        spacerR.setOpaque(false);

        //配置角色舞台：序列帧绘制面板
        //方向指示交给气泡尾巴，不再叠加独立箭头图层——两者重复且箭头终点是估算值
        stage = new GDragonStage(animation, scale);
        JPanel rolePanel = new JPanel();
        rolePanel.setLayout(new OverlayLayout(rolePanel));
        rolePanel.setOpaque(false);
        stage.setAlignmentX(0.5f);
        stage.setAlignmentY(0.5f);
        rolePanel.add(stage);

        //配置消息标题
        jL_Title = new JLabel(option.getTitle());
        jL_Title.setFont(new Font(option.getFontType(), Font.BOLD, option.getFontSize() + 2));
        jL_Title.setForeground(Color.GREEN);
        jL_Title.setOpaque(false);

        //配置消息内容
        JTextArea jL_MSG = new JTextArea(option.getMessage());
        jL_MSG.setLineWrap(true);
        jL_MSG.setWrapStyleWord(true);
        jL_MSG.setEditable(false);
        jL_MSG.setFont(new Font(option.getFontType(), Font.PLAIN, option.getFontSize()));
        jL_MSG.setForeground(option.getFontColor());
        jL_MSG.setOpaque(false);

        //泡泡文字：矢量绘制，缩放不失真；关闭时回退到普通文本域
        if (option.isBubble()) {
            bubble = new GDragonBubble();
            bubble.setTextStyle(new Font(option.getFontType(), Font.PLAIN, option.getFontSize()), option.getFontColor());
            //尾巴固定朝右：气泡在人物左侧，尖角要指向人物，指向元素由黑色箭头负责
            bubble.setTail(GDragonBubble.Tail.RIGHT_TOP);
            //先放文字再量尺寸：量的时候还没有文本会按空串算，气泡高度会少算行数
            bubble.setText(option.getMessage());
        }

        //按文字长度收缩尺寸（上限为上面的标准尺寸），再摆到目标元素旁边
        computeSize();
        setSize(compStartWidth, compStartHeight);
        Rectangle targetRect = option.getTargetRect();
        Point location = targetRect == null
                ? resolveLocation(option.getDX(), option.getDY(), compStartWidth, compStartHeight)
                : GDragonPointer.anchor(targetRect, compStartWidth, compStartHeight);
        setLocation(location.x, location.y);
        if (option.getTarget() != null) {
            arrowType = GDragonPointer.direction(option.getTarget(), location.x, location.y,
                    compStartWidth, compStartHeight);
        }
        applyArrowIcons();
        placeArrows();

        //添加功能按钮
        add3Button(action -> {
            switch (action) {
                case YES:
                    close(GDragonListener.Reason.YES);
                    break;
                case NO:
                    close(GDragonListener.Reason.NO);
                    break;
                default:
                    close(GDragonListener.Reason.CLOSE);
                    break;
            }
        });

        //文字区：宽度按气泡实际大小，高度与角色一致
        msgPanel = new JPanel();
        msgPanel.setPreferredSize(new Dimension(compStartWidth - block.width * 2 - role.width, role.height));
        msgPanel.setLayout(new BorderLayout(0, 0));
        msgPanel.add(jL_Title, BorderLayout.NORTH);
        if (bubble != null) {
            //气泡按自身尺寸摆放：水平靠左、垂直居中，剩余空间保持透明，不再被拉满整块区域
            JPanel holder = new JPanel();
            holder.setLayout(new BoxLayout(holder, BoxLayout.X_AXIS));
            holder.setOpaque(false);
            holder.add(bubble);
            holder.add(Box.createHorizontalGlue());
            msgPanel.add(holder, BorderLayout.CENTER);
        } else {
            msgPanel.add(jL_MSG, BorderLayout.CENTER);
        }
        msgPanel.setOpaque(false);

        //功能按钮区：宽度与角色一致，按钮在人物正下方均分
        //宽度跟着角色而不是整窗，否则气泡变窄时按钮会整体左移、离人物越来越远
        JPanel btn = new JPanel();
        int buttonArea = Math.max(role.width, 0);
        int buttonUsed = iconWidth(jBtn_Yes, block.width) + iconWidth(jBtn_No, block.width) + iconWidth(jBtn_Close, block.width);
        int gap = Math.max((buttonArea - buttonUsed) / 4, 0);
        btn.setPreferredSize(new Dimension(buttonArea, closeButton.height));
        btn.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btn.add(Box.createHorizontalStrut(gap));
        btn.add(jBtn_Yes);
        btn.add(Box.createHorizontalStrut(gap));
        btn.add(jBtn_No);
        btn.add(Box.createHorizontalStrut(gap));
        btn.add(jBtn_Close);
        btn.setOpaque(false);

        //角色与按钮：按钮挂在角色下沿，不跨到文字区
        JPanel roleBox = new JPanel();
        roleBox.setLayout(new BorderLayout(0, 0));
        roleBox.setOpaque(false);
        roleBox.add(rolePanel, BorderLayout.CENTER);
        roleBox.add(btn, BorderLayout.SOUTH);

        //文字及头像区
        JPanel msgAndRole = new JPanel();
        msgAndRole.setLayout(new BorderLayout(0, 0));
        msgAndRole.add(msgPanel, BorderLayout.WEST);
        msgAndRole.add(roleBox, BorderLayout.EAST);
        msgAndRole.setOpaque(false);

        //装配整个区域
        setLayout(new BorderLayout(0, 0));
        add(spacerL, BorderLayout.WEST);
        add(msgAndRole, BorderLayout.CENTER);
        add(spacerR, BorderLayout.EAST);

        //去掉标题栏
        setUndecorated(true);

        //添加鼠标事件监听
        addMouseMotionListener(this);
        addMouseListener(this);

        //设置窗口总在上面，但不抢占输入焦点，避免干扰自动化操作
        setAlwaysOnTop(true);
        setFocusableWindowState(false);

        //关闭窗体只销毁自身，绝不能结束进程
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //设置窗体为全透明：背景一块都不画，可见内容只剩气泡圆角矩形、角色与按钮图标，
        //避免灰底把底下正在操作的页面整块盖住
        setBackground(new Color(0, 0, 0, 0));

        //箭头叠在内容之上：位置由 placeArrows() 按元素方位决定，不能交给布局管理器
        getLayeredPane().add(jL_Arrow, Integer.valueOf(JLayeredPane.PALETTE_LAYER));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                markClosed(GDragonListener.Reason.DISPOSED);
            }
        });
    }

    /**
     *  安排自动关闭
     *
     *  @param millis 持续时间，单位毫秒
     */
    private void scheduleClose(long millis) {
        stopTimer();
        int delay = (int) Math.min(Math.max(millis, 1), Integer.MAX_VALUE);
        closeTimer = new Timer(delay, e -> close(GDragonListener.Reason.TIMEOUT));
        closeTimer.setRepeats(false);
        closeTimer.start();
    }

    /**
     *  停止自动关闭定时器
     */
    private void stopTimer() {
        if (closeTimer != null) {
            closeTimer.stop();
            closeTimer = null;
        }
    }

    /**
     *  标记弹窗已关闭并上报一次
     *
     *  @param reason 关闭原因
     */
    private void markClosed(GDragonListener.Reason reason) {
        if (closedSignal.getCount() > 0) {
            closedSignal.countDown();
            listener.onClose(option, reason);
        }
    }

    /**
     *  确定实际使用的人形头像
     *
     *  <p>未指定时随机选取；指定或随机到的图片不存在时回退到首选图片。</p>
     *
     *  @return 图片名
     */
    private String resolveRole() {
        String role = option.getImg();
        if (role == null || role.trim().isEmpty() || GDragonResource.locate(role) == null) {
            role = GDragonResource.randomRole();
        }
        if (role == null || role.isEmpty()) {
            role = GDragonResource.defaultRole();
        }
        return role == null ? "" : role;
    }

    /**
     *  计算左上角顶点位置，同时确定气泡箭头方向
     *
     *  <p>窗口越界时自动回缩，保证完整落在屏幕内。</p>
     *
     *  @param dX 期望横坐标
     *  @param dY 期望纵坐标
     *  @param width 窗口宽度
     *  @param height 窗口高度
     *  @return 实际左上角顶点
     */
    private Point resolveLocation(int dX, int dY, int width, int height) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        if (dX <= 0 || dY <= 0) {
            //默认紧贴右下角摆放
            arrowType = 0;
            return new Point(Math.max(screen.width - width, 0), Math.max(screen.height - height, 0));
        }

        int x = dX;
        int y = dY;
        if (dX + width > screen.width) {
            x = screen.width - width - (screen.width - dX);
            if (dY + height > screen.height) {
                y = screen.height - height - (screen.height - dY);
                arrowType = 3;
            } else {
                arrowType = 1;
            }
        } else if (dY + height > screen.height) {
            y = screen.height - height - (screen.height - dY);
            arrowType = 2;
        } else {
            arrowType = 0;
        }

        return new Point(Math.max(x, 0), Math.max(y, 0));
    }

    /**
     *  加载图片图标
     *
     *  @param name 图片名
     *  @return 图标；图片不存在时返回 {@code null}
     */
    private static Icon loadIcon(String name) {
        return GDragonResource.icon(name);
    }

    /**
     *  获得按钮图片宽度
     *
     *  @param button 图片按钮
     *  @param fallback 兜底宽度
     *  @return 图片宽度
     */
    private static int iconWidth(JImageButton button, int fallback) {
        if (button == null || button.getIcon() == null) {
            return fallback;
        }
        int width = button.getIcon().getIconWidth();
        return width > 0 ? width : fallback;
    }
}
