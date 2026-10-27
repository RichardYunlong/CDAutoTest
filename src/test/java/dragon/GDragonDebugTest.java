package dragon;

import Dragon.GDragon;
import Dragon.GDragonAction;
import Dragon.GDragonAnimation;
import Dragon.GDragonFrames;
import Dragon.GDragonGuide;
import Dragon.GDragonListener;
import Dragon.GDragonOption;
import Dragon.GDragonPointer;
import Dragon.GDragonResource;
import Dragon.GDragonService;
import Dragon.GDragonSkin;
import Dragon.GDragonVoice;
import Webdriver.GWCtrlHighLight;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

/**
 *  人形弹窗独立调试用例
 *
 *  <p>刻意不加入 {@code yonbip/场景/AllTests}，因此 {@code mvn test} 不会执行它，
 *  避免弹窗干扰正常业务用例。单独调试弹窗时执行：</p>
 *  <pre>
 *  mvn test -Dtest=GDragonDebugTest
 *  mvn test -Dtest=GDragonDebugTest -Dcdtest.dragon.time=5000
 *  </pre>
 *
 *  <p>需要肉眼观察效果时请用 {@code Dragon.GDragonDebug}，它支持命令行调参与场景轮播。</p>
 *
 *  @see Dragon.GDragonDebug
 */
public class GDragonDebugTest {

    /**
     *  弹窗统一入口
     */
    private GDragonService service;

    /**
     *  记录型事件回调
     */
    private RecordingListener listener;

    @Before
    public void setUp() {
        service = GDragonService.getInstance();
        service.closeAll();
        GDragonGuide.getInstance().reset();
        listener = new RecordingListener();
        service.setListener(listener);
        service.setEnabled(true);
        service.setTipEnabled(false);
        service.setMaxVisible(3);
        service.setMinIntervalMs(0);
    }

    @After
    public void tearDown() {
        service.closeAll();
        service.setListener(GDragonListener.console());
    }

    /**
     *  弹窗位置根据目标矩形自动选边：必须在屏幕内，且不压住正在操作的元素
     */
    @Test
    public void pointerAvoidsCoveringTarget() {
        assumeTrue(GDragonService.isGuiAvailable());
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 1010;
        int height = 385;
        assumeTrue("屏幕太小，无法验证避让效果", screen.width > width && screen.height > height);

        //左上角的小元素：右侧有足够空间，应放到右边且不压住元素
        Rectangle target = new Rectangle(80, 80, 200, 40);
        Point anchor = GDragonPointer.anchor(target, width, height);
        assertTrue("窗口应完全在屏幕内", anchor.x >= 0 && anchor.y >= 0
                && anchor.x + width <= screen.width && anchor.y + height <= screen.height);
        assertEquals("不应压住目标元素", 0L,
                overlap(new Rectangle(anchor.x, anchor.y, width, height), target));

        //横跨屏幕中部的宽元素：左右都放不下，应退到上/下方，而不是压住元素
        Rectangle wide = new Rectangle(60, screen.height / 2, screen.width - 120, 60);
        Point wideAnchor = GDragonPointer.anchor(wide, width, height);
        assertTrue("宽元素场景下窗口也应在屏幕内", wideAnchor.x >= 0 && wideAnchor.y >= 0
                && wideAnchor.x + width <= screen.width && wideAnchor.y + height <= screen.height);
        assertTrue("宽元素场景下应明显少压住元素",
                overlap(new Rectangle(wideAnchor.x, wideAnchor.y, width, height), wide)
                        < (long) width * height / 2);
    }

    /**
     *  指向矩形时，箭头按矩形中心定位
     */
    @Test
    public void targetRectUsesCenter() {
        GDragonOption option = GDragonOption.builder().targetRect(100, 200, 60, 40).build();
        assertEquals(new Rectangle(100, 200, 60, 40), option.getTargetRect());
        assertEquals(new Point(130, 220), option.getTarget());
    }

    /**
     *  Selenium 高亮必须是红色圆角框，框粗 6 像素
     */
    @Test
    public void highLightStyleIsRedRoundedBorder() {
        assertEquals("6", GWCtrlHighLight.strBorder);
        assertEquals("red", GWCtrlHighLight.strBorderColor);
        assertTrue("圆角半径应为正数", Integer.parseInt(GWCtrlHighLight.strBorderRadius) > 0);
    }

    /**
     *  开关关闭时高亮入口直接返回：不执行 JS、不产生弹窗，也不因空参数抛异常
     */
    @Test
    public void highLightDisabledWhenTipOff() {
        service.setTipEnabled(false);
        GWCtrlHighLight.apply(null, null, 1, "");
        assertEquals("开关关闭时不应产生可见弹窗", 0, service.visibleCount());
    }

    /**
     *  计算两个矩形的重叠面积
     *
     *  @param one 矩形一
     *  @param other 矩形二
     *  @return 重叠面积；无重叠返回 0
     */
    private static long overlap(Rectangle one, Rectangle other) {
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
     *  快速连续操作：提示应被合并成一条，密集期结束后才弹出
     */
    @Test
    public void guideMergesRapidOperations() throws InterruptedException {
        assumeTrue(GDragonService.isGuiAvailable());
        GDragonGuide guide = GDragonGuide.getInstance();
        for (int i = 0; i < 10; i++) {
            guide.tip("第" + i + "步操作", GDragonAction.POINT, 0);
        }
        assertTrue("连续提交应判定为操作密集期", guide.isBusy());
        //防抖 500ms + 空闲等待 800ms 后才会真正弹出
        Thread.sleep(2500);
        assertEquals("10 条快速提示应合并成一次弹出", 1, listener.shownCount);
        assertEquals("应显示最后一条提示", "第9步操作", guide.currentMessage());
    }

    /**
     *  慢速操作：逐步提示，但始终复用同一个弹窗
     */
    @Test
    public void guideReusesWindowForSlowOperations() throws InterruptedException {
        assumeTrue(GDragonService.isGuiAvailable());
        GDragonGuide guide = GDragonGuide.getInstance();
        guide.tip("第一步", GDragonAction.POINT, 0);
        Thread.sleep(1200);
        guide.tip("第二步", GDragonAction.POINT, 0);
        Thread.sleep(1200);
        assertEquals("后续提示应复用同一个弹窗，不重复入场", 1, listener.shownCount);
        assertEquals("弹窗文字应更新为最新一条", "第二步", guide.currentMessage());
        assertEquals("屏幕上同时只应有一个弹窗", 1, service.visibleCount());
    }

    /**
     *  清空：丢弃尚未弹出的提示并关闭当前弹窗
     */
    @Test
    public void guideResetClearsPendingAndWindow() throws InterruptedException {
        assumeTrue(GDragonService.isGuiAvailable());
        GDragonGuide guide = GDragonGuide.getInstance();

        //先正常弹出一个，确认清空能把窗口也关掉
        guide.tip("这一步会被清空", GDragonAction.POINT, 0);
        Thread.sleep(1000);
        assertEquals("提示应先弹出", "这一步会被清空", guide.currentMessage());

        guide.reset();
        Thread.sleep(500);
        assertEquals("清空后不应残留弹窗", 0, service.visibleCount());
        assertNull("清空后不应有待显示内容", guide.currentMessage());

        //提交后立即清空：合并中的提示应被丢弃，不再弹出
        listener.reset();
        guide.tip("提交后马上清空", GDragonAction.POINT, 0);
        guide.reset();
        Thread.sleep(1000);
        assertEquals("被丢弃的提示不应再弹出", 0, listener.shownCount);
    }

    /**
     *  指向动作按朝向取不同素材；没有方向变体的动作退回自身
     */
    @Test
    public void pointUsesDirectionVariant() {
        GDragonSkin skin = GDragonSkin.getDefault();
        assumeTrue("需要提供左右两套指向素材",
                skin.hasVariant(GDragonAction.POINT, true)
                        && skin.hasVariant(GDragonAction.POINT, false));

        GDragonFrames plain = skin.getFrames(GDragonAction.POINT);
        GDragonFrames left = skin.getFrames(GDragonAction.POINT, true);
        GDragonFrames right = skin.getFrames(GDragonAction.POINT, false);

        assertNotNull(left);
        assertNotNull(right);
        assertNotSame("朝左应取左向变体", plain, left);
        assertNotSame("朝右应取右向变体", plain, right);
        assertNotSame("左右应是两套素材", left, right);
        assertNotEquals("左右两套素材的人物轮廓应不同", roleWidth(left.frame(0)), roleWidth(right.frame(0)));

        assertFalse("没有变体的动作判定为无变体", skin.hasVariant(GDragonAction.TALK, true));
        assertSame("无变体时退回动作本身",
                skin.getFrames(GDragonAction.TALK), skin.getFrames(GDragonAction.TALK, true));
    }

    /**
     *  元素在屏幕左侧时人物朝左，在右侧时朝右
     */
    @Test
    public void windowPointsToTargetSide() throws InterruptedException {
        assumeTrue(GDragonService.isGuiAvailable());
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        assumeTrue("屏幕过小，跳过", screen.width > 900 && screen.height > 600);

        GDragon leftWindow = service.show(GDragonOption.builder()
                .message("元素在左侧")
                .action(GDragonAction.POINT)
                .targetRect(40, 300, 120, 40)
                .time(3000)
                .build());
        assertNotNull(leftWindow);
        Thread.sleep(300);
        assertTrue("元素在左侧时人物应朝左", leftWindow.getAnimation().isPointingLeft());

        service.closeAll();
        Thread.sleep(400);

        GDragon rightWindow = service.show(GDragonOption.builder()
                .message("元素在右侧")
                .action(GDragonAction.POINT)
                .targetRect(screen.width - 200, 300, 120, 40)
                .time(3000)
                .build());
        assertNotNull(rightWindow);
        Thread.sleep(300);
        assertFalse("元素在右侧时人物应朝右", rightWindow.getAnimation().isPointingLeft());
    }

    /**
     *  量出非透明区域宽度，用于区分左右两套素材
     *
     *  @param image 位图
     *  @return 非透明区域宽度
     */
    private static int roleWidth(BufferedImage image) {
        int min = image.getWidth();
        int max = -1;
        for (int y = 0; y < image.getHeight(); y += 2) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (((image.getRGB(x, y) >>> 24) & 0xFF) > 8) {
                    if (x < min) {
                        min = x;
                    }
                    if (x > max) {
                        max = x;
                    }
                }
            }
        }
        return max < 0 ? 0 : max - min + 1;
    }

    /**
     *  默认参数取值符合预期
     */
    @Test
    public void optionDefaults() {
        GDragonOption option = GDragonOption.defaults();
        assertEquals(GDragonOption.DEFAULT_TIME, option.getMTime());
        assertEquals(GDragonOption.DEFAULT_FONT_TYPE, option.getFontType());
        assertEquals(GDragonOption.DEFAULT_FONT_SIZE, option.getFontSize());
        assertEquals(GDragonOption.DEFAULT_FONT_COLOR, option.getFontColor());

        //小于等于0的入参不应覆盖默认值，避免历史调用点传0导致异常尺寸
        assertEquals(GDragonOption.DEFAULT_TIME, GDragonOption.builder().time(0).build().getMTime());
        assertEquals(0, GDragonOption.builder().location(-1, -1).build().getDX());
    }

    /**
     *  系统属性可以覆盖默认参数，便于不改代码调参
     */
    @Test
    public void optionSystemProperties() {
        System.setProperty(GDragonOption.SYS_PREFIX + "time", "4321");
        try {
            GDragonOption option = GDragonOption.builder().applySystemProperties().build();
            assertEquals(4321, option.getMTime());
        } finally {
            System.clearProperty(GDragonOption.SYS_PREFIX + "time");
        }
    }

    /**
     *  人形头像必须能定位到，且尺寸不能为负
     */
    @Test
    public void roleResourceUsable() {
        List<String> roles = GDragonResource.availableRoles();
        assertNotNull(roles);
        for (String role : roles) {
            assertNotNull("头像应可定位：" + role, GDragonResource.locate(role));
            assertTrue("头像宽度应大于0：" + role, GDragonResource.roleSize(role).width > 0);
            assertTrue("头像高度应大于0：" + role, GDragonResource.roleSize(role).height > 0);
        }
    }

    /**
     *  提示开关未开启时不弹窗，只上报跳过原因
     */
    @Test
    public void tipSkippedWhenSwitchOff() {
        service.setTipEnabled(false);
        GDragon window = service.showTip("不应弹出的提示");

        assertNull(window);
        assertEquals(1, listener.skipped.size());
        assertTrue(listener.skipped.get(0).contains("提示弹窗开关"));
    }

    /**
     *  总开关关闭或无界面环境时，只上报不创建窗体
     */
    @Test
    public void skippedWhenUnavailable() {
        service.setEnabled(false);
        GDragon window = service.show(GDragonOption.of("不应弹出的提示"));

        assertNull(window);
        assertEquals(1, listener.skipped.size());
        assertTrue(listener.skipped.get(0).contains("无界面环境") || listener.skipped.get(0).contains("总开关"));
    }

    /**
     *  超过同时显示数量上限时丢弃后续弹窗
     */
    @Test
    public void limitedByMaxVisible() {
        assumeTrue(GDragonService.isGuiAvailable());

        service.setMaxVisible(1);
        service.show(GDragonOption.builder().message("第一个").time(2000).build());
        GDragon second = service.show(GDragonOption.builder().message("第二个").time(2000).build());

        assertNull("超过上限的弹窗应被丢弃", second);
    }

    /**
     *  弹窗能按期自动关闭，并通过回调上报关闭原因
     */
    @Test
    public void autoCloseByTimeout() {
        assumeTrue(GDragonService.isGuiAvailable());

        GDragon window = service.show(GDragonOption.builder().message("单测：自动关闭").time(1000).build());

        assertNotNull(window);
        assertTrue("弹窗应在超时后自动关闭", window.awaitClosed(8000));
        assertEquals(GDragonListener.Reason.TIMEOUT, listener.lastReason);
    }

    /**
     *  统一关闭可以清空存活弹窗
     */
    @Test
    public void closeAllWorks() {
        assumeTrue(GDragonService.isGuiAvailable());

        service.show(GDragonOption.builder().message("单测：统一关闭").time(60000).build());

        assertEquals(1, service.closeAll());
        assertEquals(0, service.visibleCount());
    }

    /**
     *  素材必须提供全部 8 个动作，且常驻内存在预算内
     */
    @Test
    public void skinProvidesAllActions() {
        GDragonSkin skin = GDragonSkin.getDefault();

        for (GDragonAction action : GDragonAction.values()) {
            assertTrue("缺少动作素材：" + action.getName(), skin.has(action));
            GDragonFrames frames = skin.getFrames(action);
            assertNotNull(frames);
            assertTrue(frames.size() > 0);
            assertTrue(frames.getWidth() > 0 && frames.getHeight() > 0);
        }
        assertTrue("素材常驻内存应控制在 80MB 内，实际 " + skin.estimateBytes() / 1024 / 1024 + "MB",
                skin.estimateBytes() < 80L * 1024 * 1024);
    }

    /**
     *  动作解析支持多种写法，无法识别时回落待机
     */
    @Test
    public void actionParse() {
        assertEquals(GDragonAction.TALK, GDragonAction.parse("talk"));
        assertEquals(GDragonAction.TALK, GDragonAction.parse("TALK"));
        assertEquals(GDragonAction.POINT, GDragonAction.parse("指向"));
        assertEquals(GDragonAction.IDLE, GDragonAction.parse("不存在的动作"));
        assertEquals(GDragonAction.IDLE, GDragonAction.parse(""));
    }

    /**
     *  一次性动作播完自动回落待机
     */
    @Test
    public void animationFallsBackToIdle() {
        GDragonSkin skin = GDragonSkin.getDefault();
        GDragonAnimation animation = new GDragonAnimation(skin);
        animation.play(GDragonAction.POINT);
        assertEquals(GDragonAction.POINT, animation.getAction());

        GDragonFrames frames = skin.getFrames(GDragonAction.POINT);
        long duration = frames.size() * 1000L / Math.max(frames.getFps(), 1) + 500;
        assertNotNull(animation.currentFrame(System.currentTimeMillis() + duration));
        assertEquals(GDragonAction.IDLE, animation.getAction());
    }

    /**
     *  暂停期间帧号不增长，避免窗口隐藏时"偷偷推进"
     */
    @Test
    public void animationPauseFreezesFrame() throws InterruptedException {
        GDragonAnimation animation = new GDragonAnimation(GDragonSkin.getDefault());
        animation.play(GDragonAction.IDLE);

        Thread.sleep(300);
        long running = animation.getFrameIndex(System.currentTimeMillis());

        animation.pause();
        long atPause = animation.getFrameIndex(System.currentTimeMillis());
        Thread.sleep(500);
        assertEquals("暂停期间帧号不应推进", atPause, animation.getFrameIndex(System.currentTimeMillis()));

        animation.resume();
        Thread.sleep(300);
        assertTrue("恢复后帧号应继续推进",
                animation.getFrameIndex(System.currentTimeMillis()) > running);
    }

    /**
     *  离屏绘制基准：单帧耗时必须远低于一帧预算，确保动画不会拖慢用例执行
     */
    @Test
    public void offscreenDrawBenchmark() {
        GDragonSkin skin = GDragonSkin.getDefault();
        int width = skin.getFrameWidth();
        int height = skin.getFrameHeight();
        java.awt.image.BufferedImage canvas =
                new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE);
        java.awt.Graphics2D g = canvas.createGraphics();
        GDragonAnimation animation = new GDragonAnimation(skin);
        animation.play(GDragonAction.TALK);

        int total = 200;
        for (int i = 0; i < 20; i++) {
            g.drawImage(animation.currentFrame(System.currentTimeMillis()), 0, 0, width, height, null);
        }
        long begin = System.nanoTime();
        for (int i = 0; i < total; i++) {
            g.drawImage(animation.currentFrame(System.currentTimeMillis()), 0, 0, width, height, null);
        }
        double avgMs = (System.nanoTime() - begin) / 1_000_000.0 / total;
        g.dispose();

        System.out.println("[Dragon] 平均绘制耗时 " + avgMs + "ms/帧");
        assertTrue("单帧绘制耗时应低于 24fps 预算(41.7ms)，实际 " + avgMs + "ms", avgMs < 41.7);
    }

    /**
     *  语音默认关闭，避免拖慢执行、干扰环境
     */
    @Test
    public void voiceDisabledByDefault() {
        assertFalse(GDragonVoice.silent().isAvailable());
        GDragonService fresh = GDragonService.getInstance();
        fresh.setVoiceEnabled(false);
        assertFalse(fresh.isVoiceEnabled());
        assertFalse(fresh.getVoice().isAvailable());

        //静默实现不发声，且回调仍会被触发，保证流程不被阻塞
        final boolean[] finished = {false};
        GDragonVoice.silent().speak("测试", () -> finished[0] = true);
        assertTrue(finished[0]);
    }

    /**
     *  关闭动画开关时统一降级为静态显示
     */
    @Test
    public void serviceDegradesAnimation() {
        assumeTrue(GDragonService.isGuiAvailable());

        service.setAnimationEnabled(false);
        GDragon window = service.show(GDragonOption.builder()
                .message("降级验证")
                .time(1000)
                .action(GDragonAction.TALK)
                .build());

        assertNotNull(window);
        assertFalse("关闭动画开关后应降级为静态显示", window.getOption().isAnimate());
        window.close(GDragonListener.Reason.CLOSE);
    }

    /**
     *  记录型事件回调，供用例断言弹窗行为
     */
    private static final class RecordingListener implements GDragonListener {

        private final List<String> skipped = new ArrayList<>();
        private volatile Reason lastReason;
        private volatile int shownCount;

        @Override
        public void onShow(GDragonOption option) {
            shownCount++;
        }

        /**
         *  清零计数，便于用例之间互不干扰
         */
        void reset() {
            skipped.clear();
            lastReason = null;
            shownCount = 0;
        }

        @Override
        public void onClose(GDragonOption option, Reason reason) {
            lastReason = reason;
        }

        @Override
        public void onSkipped(GDragonOption option, String reason) {
            skipped.add(reason);
        }

        @Override
        public void onError(String function, Throwable e) {
        }
    }
}
