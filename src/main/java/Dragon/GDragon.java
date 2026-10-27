package Dragon;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JFrame;
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
            Rectangle rect = next.getTargetRect();
            if (rect != null) {
                Point location = GDragonPointer.anchor(rect, getWidth(), getHeight());
                setLocation(location.x, location.y);
                Point center = new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
                arrowType = GDragonPointer.direction(center, location.x, location.y, getWidth(), getHeight());
                if (bubble != null) {
                    bubble.setTail(tailOf(arrowType));
                }
            }
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
     *  @param target 目标屏幕坐标；为 {@code null} 时隐藏箭头
     */
    public void setTarget(Point target) {
        SwingUtilities.invokeLater(() -> {
            if (stage == null) {
                return;
            }
            if (target == null) {
                return;
            }
            Point location = GDragonPointer.anchor(
                    new Rectangle(target.x, target.y, 0, 0), getWidth(), getHeight());
            setLocation(location.x, location.y);
            arrowType = GDragonPointer.direction(target, location.x, location.y,
                    getWidth(), getHeight());
            if (bubble != null) {
                bubble.setTail(tailOf(arrowType));
            }
        });
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
     *  按气泡箭头方向确定泡泡尾巴朝向
     *
     *  @param arrowType 箭头方向 0-左上 1-右上 2-左下 3-右下
     *  @return 尾巴朝向
     */
    private static GDragonBubble.Tail tailOf(int arrowType) {
        switch (arrowType) {
            case 1:
                return GDragonBubble.Tail.RIGHT_TOP;
            case 2:
                return GDragonBubble.Tail.LEFT_BOTTOM;
            case 3:
                return GDragonBubble.Tail.RIGHT_BOTTOM;
            default:
                return GDragonBubble.Tail.LEFT_TOP;
        }
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

        //宽度默认为4个角色宽度；高度默认为一个角色高度加功能按钮高度
        compStartWidth = role.width * 4 + block.width * 2;
        compStartHeight = closeButton.height + role.height;

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

        //配置箭头区
        JLabel jL_Lp = new JLabel();
        JLabel jL_Rp = new JLabel();
        Icon iconLp;
        Icon iconRp;
        switch (arrowType) {
            case 1: {
                iconLp = loadIcon(IMG25);
                iconRp = loadIcon("rt.png");
                jL_Rp.setVerticalAlignment(JLabel.TOP);
                break;
            }
            case 2: {
                iconLp = loadIcon("lb.png");
                iconRp = loadIcon(IMG25);
                jL_Lp.setVerticalAlignment(JLabel.BOTTOM);
                break;
            }
            case 3: {
                iconLp = loadIcon(IMG25);
                iconRp = loadIcon("rb.png");
                jL_Rp.setVerticalAlignment(JLabel.BOTTOM);
                break;
            }
            default: {
                iconLp = loadIcon("lt.png");
                iconRp = loadIcon(IMG25);
                jL_Lp.setVerticalAlignment(JLabel.TOP);
                break;
            }
        }
        jL_Lp.setIcon(iconLp);
        jL_Rp.setIcon(iconRp);

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
        JLabel jL_Title = new JLabel(option.getTitle());
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
            bubble.setTail(tailOf(arrowType));
        }

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

        //文字区：宽度占3个人形头像宽，高度与头像一致
        JPanel msg = new JPanel();
        msg.setPreferredSize(new Dimension(role.width * 3, role.height));
        msg.setLayout(new BorderLayout(0, 0));
        msg.add(jL_Title, BorderLayout.NORTH);
        if (bubble != null) {
            msg.add(bubble, BorderLayout.CENTER);
        } else {
            msg.add(jL_MSG, BorderLayout.CENTER);
        }
        msg.setOpaque(false);

        //功能按钮区：按钮之间按剩余宽度均分间隔
        JPanel btn = new JPanel();
        int buttonArea = Math.max(compStartWidth - block.width * 2, 0);
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

        //文字及头像区
        JPanel msgAndRole = new JPanel();
        msgAndRole.setLayout(new BorderLayout(0, 0));
        msgAndRole.add(msg, BorderLayout.WEST);
        msgAndRole.add(rolePanel, BorderLayout.EAST);
        msgAndRole.add(btn, BorderLayout.SOUTH);
        msgAndRole.setOpaque(false);

        //装配整个区域
        setLayout(new BorderLayout(0, 0));
        add(jL_Lp, BorderLayout.WEST);
        add(msgAndRole, BorderLayout.CENTER);
        add(jL_Rp, BorderLayout.EAST);

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
