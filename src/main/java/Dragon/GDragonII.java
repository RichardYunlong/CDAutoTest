package Dragon;

import Sys.GStatic;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.IntSupplier;

/**
 *  欢迎界面（二类弹窗）
 *
 *  <p>无边框欢迎窗体，带进度条；进度到达 100% 后自动关闭。</p>
 *
 *  <p>改造要点与 {@link GDragon} 一致：构造不阻塞、事件走回调、关闭只销毁自身。
 *  另外补了两处保护：进度源可注入（便于脱离主流程单独预览），
 *  并增加最长等待时间，避免进度始终不到 100% 时窗体永远驻留。</p>
 *
 *  @see GDragon
 *  @see GBase
 */
public class GDragonII extends GBase {

    /**
     *  句柄编号
     */
    private static final long serialVersionUID = 1366934872088790043L;

    /**
     *  窗体宽度
     */
    private static final int WIDTH = 720;

    /**
     *  窗体高度
     */
    private static final int HEIGHT = 265;

    /**
     *  进度刷新间隔，单位毫秒
     */
    private static final int PROGRESS_INTERVAL = 100;

    /**
     *  系统属性：欢迎界面最长等待时间，单位毫秒
     */
    private static final String SYS_MAX_WAIT = "cdtest.dragon.welcome.maxWait";

    /**
     *  默认最长等待时间，单位毫秒
     */
    private static final long DEFAULT_MAX_WAIT = 60000L;

    /**
     *  欢迎语
     */
    private final String message;

    /**
     *  横坐标
     */
    private final int dX;

    /**
     *  纵坐标
     */
    private final int dY;

    /**
     *  进度来源
     */
    private final IntSupplier progressSource;

    /**
     *  最长等待时间，单位毫秒
     */
    private final long maxWaitMs;

    /**
     *  进度刷新定时器
     */
    private Timer progressTimer;

    /**
     *  进度条引用，供定时器更新
     */
    private JProgressBar progressBarRef;

    /**
     *  关闭信号
     */
    private final CountDownLatch closedSignal = new CountDownLatch(1);

    /**
     *  构造欢迎界面并立即显示
     *
     *  <p>进度来源默认取全局测试进度；调试时可用
     *  {@link #GDragonII(String, int, int, IntSupplier)} 注入模拟进度。</p>
     *
     *  @param message 欢迎语
     *  @param dX 横坐标；小于等于0时居中
     *  @param dY 纵坐标；小于等于0时居中
     */
    public GDragonII(String message, int dX, int dY) {
        this(message, dX, dY, GDragonII::defaultProgress);
    }

    /**
     *  构造欢迎界面并立即显示
     *
     *  @param message 欢迎语
     *  @param dX 横坐标；小于等于0时居中
     *  @param dY 纵坐标；小于等于0时居中
     *  @param progressSource 进度来源，返回 0-100
     */
    public GDragonII(String message, int dX, int dY, IntSupplier progressSource) {
        super();
        this.message = message == null ? "" : message;
        this.dX = dX;
        this.dY = dY;
        this.progressSource = progressSource == null ? () -> 0 : progressSource;
        this.maxWaitMs = readMaxWait();
        display();
    }

    /**
     *  显示欢迎界面
     */
    public void display() {
        SwingUtilities.invokeLater(() -> {
            try {
                buildUI();
                startProgress();
                setVisible(true);
                listener.onShow(GDragonOption.of(message));
            } catch (Exception e) {
                listener.onError("display", e);
                markClosed(GDragonListener.Reason.ERROR);
            }
        });
    }

    /**
     *  关闭欢迎界面
     *
     *  @param reason 关闭原因
     */
    public void close(GDragonListener.Reason reason) {
        SwingUtilities.invokeLater(() -> {
            stopTimer();
            if (isDisplayable()) {
                dispose();
            }
            markClosed(reason);
        });
    }

    /**
     *  等待窗体关闭
     *
     *  @param millis 最长等待时间，单位毫秒
     *  @return 已关闭返回 {@code true}；超时返回 {@code false}
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
     *  窗体是否已关闭
     *
     *  @return 已关闭返回 {@code true}
     */
    public boolean isClosed() {
        return closedSignal.getCount() == 0;
    }

    /**
     *  构建窗体内容
     */
    private void buildUI() {
        setSize(WIDTH, HEIGHT);
        if (dX > 0 && dY > 0) {
            setLocation(dX, dY);
        } else {
            setLocationRelativeTo(null);
        }

        //配置标题
        JLabel jL_Title = new JLabel("");
        jL_Title.setFont(new Font("宋体", Font.HANGING_BASELINE, 36));
        jL_Title.setForeground(Color.BLUE);
        jL_Title.setOpaque(false);

        //欢迎语
        JTextArea jL_MSG = new JTextArea(message);
        jL_MSG.setLineWrap(true);
        jL_MSG.setWrapStyleWord(true);
        jL_MSG.setEditable(false);
        jL_MSG.setFont(new Font("宋体", Font.PLAIN, 14));
        jL_MSG.setForeground(Color.BLUE);
        jL_MSG.setOpaque(false);

        //进度条
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        //添加功能按钮
        add3Button(action -> close(GDragonListener.Reason.CLOSE));

        JPanel jL_ProccessBar = new JPanel();
        jL_ProccessBar.setSize(WIDTH, 25);
        jL_ProccessBar.setLayout(new FlowLayout(1, 0, 0));
        jL_ProccessBar.add(progressBar);
        jL_ProccessBar.add(jBtn_Close);
        jL_ProccessBar.setOpaque(false);

        setLayout(new BorderLayout(0, 0));
        add(jL_Title, BorderLayout.NORTH);
        add(jL_MSG, BorderLayout.CENTER);
        add(jL_ProccessBar, BorderLayout.SOUTH);

        setUndecorated(true);
        addMouseMotionListener(this);
        addMouseListener(this);
        setAlwaysOnTop(true);
        setFocusableWindowState(false);

        //关闭窗体只销毁自身，绝不能结束进程
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new Color(192, 192, 192, 100));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                markClosed(GDragonListener.Reason.DISPOSED);
            }
        });

        progressBarRef = progressBar;
    }

    /**
     *  启动进度刷新
     */
    private void startProgress() {
        stopTimer();
        final long startAt = System.currentTimeMillis();
        progressTimer = new Timer(PROGRESS_INTERVAL, e -> {
            int value = progressSource.getAsInt();
            if (value < 0) {
                value = 0;
            }
            if (value > 100) {
                value = 100;
            }
            progressBarRef.setString(value + "%");
            progressBarRef.setValue(value);
            if (value >= 100) {
                progressBarRef.setForeground(Color.GREEN);
                close(GDragonListener.Reason.DISPOSED);
                return;
            }
            //进度迟迟不到100%时的兜底关闭，避免窗体永远驻留
            if (System.currentTimeMillis() - startAt > maxWaitMs) {
                close(GDragonListener.Reason.TIMEOUT);
            }
        });
        progressTimer.start();
    }

    /**
     *  停止进度刷新定时器
     */
    private void stopTimer() {
        if (progressTimer != null) {
            progressTimer.stop();
            progressTimer = null;
        }
    }

    /**
     *  标记窗体已关闭并上报一次
     *
     *  @param reason 关闭原因
     */
    private void markClosed(GDragonListener.Reason reason) {
        if (closedSignal.getCount() > 0) {
            closedSignal.countDown();
            listener.onClose(GDragonOption.of(message), reason);
        }
    }

    /**
     *  读取最长等待时间
     *
     *  @return 最长等待时间，单位毫秒
     */
    private static long readMaxWait() {
        String value = System.getProperty(SYS_MAX_WAIT);
        if (value == null || value.trim().isEmpty()) {
            return DEFAULT_MAX_WAIT;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            return DEFAULT_MAX_WAIT;
        }
    }

    /**
     *  默认进度来源：全局测试进度
     *
     *  @return 当前进度值
     */
    private static int defaultProgress() {
        return GStatic.gMP == null ? 0 : GStatic.gMP.getPROGRESS_CUR();
    }
}
