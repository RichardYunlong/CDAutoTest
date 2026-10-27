package Dragon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;

/**
 *  人形弹窗独立调试入口
 *
 *  <p>不依赖配置中心、日志体系与 Spring 容器，可以直接运行，用于把弹窗效果调好之后
 *  再接入主流程。运行方式（任选其一）：</p>
 *  <pre>
 *  mvn -q compile
 *  java -cp target/classes Dragon.GDragonDebug --demo
 *  java -cp target/classes Dragon.GDragonDebug --message "用例执行完毕" --time 5000 --x 1200 --y 700
 *  java -Dcdtest.dragon.time=8000 -Dcdtest.dragon.color=red -cp target/classes Dragon.GDragonDebug
 *  </pre>
 *
 *  <p>调试完成后，业务侧统一通过 {@link GDragonService} 调用，
 *  不需要改动弹窗内部实现。</p>
 *
 *  @see GDragonService
 *  @see GDragonOption
 */
public final class GDragonDebug {

    /**
     *  预览时每个弹窗的额外等待余量，单位毫秒
     */
    private static final long WAIT_MARGIN = 3000L;

    /**
     *  保持显示的持续时间，单位毫秒
     */
    private static final long KEEP_TIME = Integer.MAX_VALUE;

    private GDragonDebug() {
    }

    /**
     *  调试入口
     *
     *  @param args 调试参数，见 {@link #printUsage()}
     */
    public static void main(String[] args) {
        Map<String, String> params = parseArgs(args);

        if (params.isEmpty() || params.containsKey("help") || params.containsKey("h")) {
            printUsage();
            return;
        }

        //素材检查与性能基准不依赖窗体，先处理，便于在 CI 上也能跑
        if (params.containsKey("skin")) {
            printSkin();
            return;
        }
        if (params.containsKey("bench")) {
            runBench(params);
            return;
        }

        if (!GDragonService.isGuiAvailable()) {
            System.out.println("[Dragon] 当前为无界面环境(java.awt.headless=true)，无法预览弹窗。");
            System.out.println("[Dragon] 请去掉 -Djava.awt.headless=true 后重试。");
            System.exit(2);
            return;
        }

        if (params.containsKey("info")) {
            printInfo();
            return;
        }
        if (params.containsKey("roles")) {
            printRoles();
            return;
        }
        if (params.containsKey("welcome")) {
            previewWelcome(params);
            return;
        }
        if (params.containsKey("demo")) {
            runDemo();
            return;
        }
        if (params.containsKey("actions")) {
            runActions();
            return;
        }
        if (params.containsKey("rhythm")) {
            runRhythm();
            return;
        }
        showSingle(params);
    }

    /**
     *  按预设场景逐个预览，便于对比调参效果
     */
    private static void runDemo() {
        List<GDragonOption> presets = new ArrayList<>();

        presets.add(GDragonOption.builder()
                .title("场景1")
                .message("默认样式：贴右下角，3秒自动关闭")
                .build());

        presets.add(GDragonOption.builder()
                .title("场景2")
                .message("指定位置：左上角顶点(1200,700)，5秒自动关闭")
                .time(5000)
                .location(1200, 700)
                .build());

        presets.add(GDragonOption.builder()
                .title("场景3")
                .message("长文本换行：\n业务编码：CS1001\n报文类型：响应报文\n"
                        + "测试类型：Main\n报文内容：这是一段用于验证自动换行与气泡高度的较长文本。")
                .time(5000)
                .build());

        presets.add(GDragonOption.builder()
                .title("场景4")
                .message("字体样式：宋体 18 号 红色")
                .time(4000)
                .font("宋体", 18, Color.RED)
                .build());

        String role = GDragonResource.defaultRole();
        if (!role.isEmpty()) {
            presets.add(GDragonOption.builder()
                    .title("场景5")
                    .message("指定人形头像：" + role + "\n持续 6 秒")
                    .time(6000)
                    .image(role)
                    .build());
        }

        System.out.println("[Dragon] 开始预览 " + presets.size() + " 个场景，逐个自动关闭后进入下一个。");
        for (int i = 0; i < presets.size(); i++) {
            GDragonOption option = presets.get(i);
            System.out.println("[Dragon] >>> 场景 " + (i + 1) + "/" + presets.size() + " " + option);
            GDragon window = GDragonService.getInstance().show(option);
            if (window == null) {
                System.out.println("[Dragon] 场景 " + (i + 1) + " 未显示，请检查开关与无界面环境设置。");
                continue;
            }
            window.awaitClosed(option.getMTime() + WAIT_MARGIN);
        }
        System.out.println("[Dragon] 预览结束。 " + GDragonService.getInstance().stats());
    }

    /**
     *  按命令行参数显示单个弹窗
     *
     *  @param params 命令行参数
     */
    private static void showSingle(Map<String, String> params) {
        GDragonOption.Builder builder = GDragonOption.builder();
        String message = params.get("message") == null ? params.get("m") : params.get("message");
        if (message == null) {
            message = "人形弹窗调试：\n可在命令行用 --message 指定内容\n用 --time 指定持续时间";
        }
        builder.message(message);
        if (params.get("title") != null) {
            builder.title(params.get("title"));
        }
        if (params.get("action") != null) {
            builder.action(params.get("action"));
        }
        if (params.get("voice") != null) {
            builder.voice(params.get("voice"));
            GDragonService.getInstance().setVoiceEnabled(true);
        }
        if (params.get("time") != null) {
            builder.time(intOf(params.get("time"), 0));
        }
        if (params.get("x") != null || params.get("y") != null) {
            builder.location(intOf(params.get("x"), 0), intOf(params.get("y"), 0));
        }
        if (params.get("img") != null) {
            builder.image(params.get("img"));
        }
        if (params.get("font") != null) {
            builder.font(params.get("font"), 0, null);
        }
        if (params.get("size") != null) {
            builder.font(null, intOf(params.get("size"), 0), null);
        }
        if (params.get("color") != null) {
            builder.fontColor(colorOf(params.get("color")));
        }
        if (params.containsKey("keep")) {
            //不自动关闭，便于反复观察细节；手动点击关闭按钮后结束
            builder.time((int) KEEP_TIME);
        }
        builder.applySystemProperties();

        GDragonOption option = builder.build();
        System.out.println("[Dragon] 参数：" + option);

        GDragon window = GDragonService.getInstance().show(option);
        if (window == null) {
            System.out.println("[Dragon] 弹窗未显示。 " + GDragonService.getInstance().stats());
            return;
        }
        window.awaitClosed(option.getMTime() + WAIT_MARGIN);
        System.out.println("[Dragon] 结束。 " + GDragonService.getInstance().stats());
    }

    /**
     *  预览欢迎界面（二类弹窗），使用模拟进度
     *
     *  @param params 命令行参数
     */
    private static void previewWelcome(Map<String, String> params) {
        String message = params.get("message") == null ? "欢迎使用 CDAutoTest 人形弹窗调试模式" : params.get("message");
        System.out.println("[Dragon] 预览欢迎界面，进度为模拟值。");

        GDragonII window = new GDragonII(message, 0, 0, simulatedProgress(intOf(params.get("time"), 3000)));
        window.setListener(GDragonListener.console());
        window.awaitClosed(20000L);
        System.out.println("[Dragon] 欢迎界面预览结束。");
    }

    /**
     *  构造一个模拟进度来源
     *
     *  @param durationMs 从 0 涨到 100 所需时间，单位毫秒
     *  @return 进度来源
     */
    private static IntSupplier simulatedProgress(final int durationMs) {
        final long startAt = System.currentTimeMillis();
        final long span = Math.max(durationMs, 500);
        return () -> (int) Math.min((System.currentTimeMillis() - startAt) * 100 / span, 100);
    }

    /**
     *  休眠指定毫秒
     *
     *  @param millis 毫秒
     */
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     *  演示提示节奏控制：先模拟快速连续操作，再模拟慢速操作
     *
     *  <p>用于直接对比两种节奏下的观感：
     *  快速阶段只应弹出一次（期间的提示被合并），慢速阶段逐步更新同一个弹窗。</p>
     */
    private static void runRhythm() {
        GDragonGuide guide = GDragonGuide.getInstance();
        guide.reset();
        System.out.println("[Dragon] 节奏演示 " + guide.stats());

        System.out.println("[Dragon] 阶段一：快速连续操作 20 步（每步间隔 60 毫秒）");
        for (int i = 1; i <= 20; i++) {
            guide.tip("正在填写第 " + i + " 个输入框", GDragonAction.POINT, 0);
            sleep(60);
        }
        System.out.println("[Dragon] 提交完毕 " + guide.stats());
        sleep(3000);
        System.out.println("[Dragon] 阶段一结束 " + guide.stats());

        System.out.println("[Dragon] 阶段二：慢速操作 3 步（每步间隔 2.5 秒）");
        String[] steps = {"打开登录页", "输入账号密码", "点击登录按钮"};
        for (String step : steps) {
            guide.tip(step, GDragonAction.POINT, 0);
            sleep(2500);
        }
        System.out.println("[Dragon] 阶段二结束 " + guide.stats());
        guide.reset();
        System.out.println("[Dragon] 结束。");
    }

    /**
     *  逐个预览全部动作
     *
     *  <p>用于快速检查每个动作的观感与节奏；一次性动作播完会自动回落 idle。</p>
     */
    private static void runActions() {
        GDragonAction[] actions = GDragonAction.values();
        System.out.println("[Dragon] 开始预览 " + actions.length + " 个动作，每个 2.5 秒。");
        for (int i = 0; i < actions.length; i++) {
            GDragonAction action = actions[i];
            GDragonOption option = GDragonOption.builder()
                    .title(action.getLabel())
                    .message(action.getLabel() + "：" + action.getName()
                            + "\n帧率 " + action.getDefaultFps() + "fps，"
                            + (action.isLoop() ? "循环播放" : "播放一次后回落待机"))
                    .action(action)
                    .time(2500)
                    .build();
            System.out.println("[Dragon] >>> " + (i + 1) + "/" + actions.length + " " + action.getName());
            GDragon window = GDragonService.getInstance().show(option);
            if (window == null) {
                continue;
            }
            window.awaitClosed(option.getMTime() + WAIT_MARGIN);
        }
        System.out.println("[Dragon] 动作预览结束。 " + GDragonService.getInstance().stats());
    }

    /**
     *  输出素材信息
     */
    private static void printSkin() {
        GDragonSkin skin = GDragonSkin.getDefault();
        System.out.println("[Dragon] " + skin.stats());
        System.out.println("[Dragon] scale=" + GDragonStage.resolveScale()
                + "，角色=" + System.getProperty(GDragonSkin.SYS_CHARACTER, GDragonSkin.DEFAULT_CHARACTER));
        System.out.println("[Dragon] 动作目录：image\\<角色名>_<动作名>\\1.png 2.png ...");
        System.out.println("[Dragon] 素材规范尺寸：" + GDragonSkin.SOURCE_WIDTH + "x" + GDragonSkin.SOURCE_HEIGHT
                + "（透明底 PNG），缺失的动作由占位素材补齐。");
    }

    /**
     *  离屏绘制性能基准
     *
     *  <p>不创建窗体，直接在离屏位图上绘制指定帧数，用于判断动画是否拖慢执行：
     *  单帧绘制耗时远低于一帧预算（如 16.7ms@60fps、41.7ms@24fps）即认为安全。</p>
     *
     *  @param params 命令行参数
     */
    private static void runBench(Map<String, String> params) {
        int total = intOf(params.get("frames"), 300);
        long loadBegin = System.nanoTime();
        GDragonSkin skin = GDragonSkin.reload();
        double loadMs = (System.nanoTime() - loadBegin) / 1_000_000.0;

        int scale = GDragonStage.resolveScale();
        int width = skin.getFrameWidth() * scale;
        int height = skin.getFrameHeight() * scale;
        java.awt.image.BufferedImage canvas =
                new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE);
        java.awt.Graphics2D g = canvas.createGraphics();
        g.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        GDragonAnimation animation = new GDragonAnimation(skin);
        animation.play(GDragonAction.parse(params.get("action")));

        //预热，排除首次绘制的类加载与 JIT 影响
        for (int i = 0; i < 30; i++) {
            g.drawImage(animation.currentFrame(System.currentTimeMillis()), 0, 0, width, height, null);
        }
        long begin = System.nanoTime();
        for (int i = 0; i < total; i++) {
            g.drawImage(animation.currentFrame(System.currentTimeMillis()), 0, 0, width, height, null);
        }
        double costMs = (System.nanoTime() - begin) / 1_000_000.0;
        g.dispose();

        double avgMs = costMs / Math.max(total, 1);
        System.out.println("[Dragon] " + skin.stats());
        System.out.printf("[Dragon] 素材加载耗时：%.1fms%n", loadMs);
        System.out.printf("[Dragon] 离屏绘制 %d 帧（%dx%d，scale=%d）：总耗时 %.1fms，平均 %.3fms/帧，理论上限 %.0f fps%n",
                total, width, height, scale, costMs, avgMs, avgMs > 0 ? 1000.0 / avgMs : 0);
        System.out.printf("[Dragon] 24fps 单帧预算 41.7ms，占用比例 %.2f%%；60fps 单帧预算 16.7ms，占用比例 %.2f%%%n",
                avgMs / 41.7 * 100, avgMs / 16.7 * 100);
    }

    /**
     *  输出环境信息，用于排查"图片取不到/弹窗不显示"
     */
    private static void printInfo() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println("[Dragon] headless   : " + !GDragonService.isGuiAvailable());
        System.out.println("[Dragon] screen     : " + screen.width + "x" + screen.height);
        System.out.println("[Dragon] service    : " + GDragonService.getInstance().stats());

        String[] images = {GDragonResource.ROLE_IMAGE, "empty25.png",
                "close_tm.png", "ok_tm.png", "cancel_tm.png", "lt.png", "rt.png", "lb.png", "rb.png"};
        System.out.println("[Dragon] 图片资源：");
        for (String name : images) {
            URL url = GDragonResource.locate(name);
            Dimension size = GDragonResource.blockSize(name);
            System.out.println("[Dragon]   " + name + " -> " + (url == null ? "缺失" : size.width + "x" + size.height + " (" + url + ")"));
        }
        System.out.println("[Dragon] 可用人形头像：" + GDragonResource.availableRoles());
    }

    /**
     *  输出可用人形头像
     */
    private static void printRoles() {
        List<String> roles = GDragonResource.availableRoles();
        if (roles.isEmpty()) {
            System.out.println("[Dragon] 未找到任何可用人形头像，请检查 image 目录是否在 classpath 中。");
            return;
        }
        for (String role : roles) {
            Dimension size = GDragonResource.roleSize(role);
            System.out.println("[Dragon] " + role + " " + size.width + "x" + size.height);
        }
    }

    /**
     *  输出用法
     */
    private static void printUsage() {
        System.out.println("人形弹窗独立调试入口");
        System.out.println("用法：java -cp target/classes Dragon.GDragonDebug [选项]");
        System.out.println();
        System.out.println("  --help                 显示本帮助");
        System.out.println("  --info                 输出运行环境、图片资源与开关状态");
        System.out.println("  --roles                列出可用的人形头像");
        System.out.println("  --demo                 逐个预览预设场景（推荐先跑这个）");
        System.out.println("  --actions              逐个预览全部动作（8个，每个2.5秒）");
        System.out.println("  --welcome              预览欢迎界面（二类弹窗，模拟进度）");
        System.out.println("  --skin                 输出素材信息（来源、帧数、内存）");
        System.out.println("  --bench                离屏绘制性能基准，无需界面，可在CI上跑");
        System.out.println("  --rhythm               演示提示节奏：快速操作合并、慢速操作逐步更新");
        System.out.println();
        System.out.println("单窗调试：");
        System.out.println("  --message <文本>        提示内容，支持 \\n 换行");
        System.out.println("  --title <文本>          标题");
        System.out.println("  --time <毫秒>           持续时间");
        System.out.println("  --x <像素> --y <像素>   左上角顶点位置");
        System.out.println("  --img <图片名>          指定人形头像，如 role_tm.png");
        System.out.println("  --font <字体名>         字体名称");
        System.out.println("  --size <字号>           字号");
        System.out.println("  --color <颜色>          字色，支持 #RRGGBB 或 blue/red/green/black/white/gray/orange/yellow");
        System.out.println("  --keep                  不自动关闭，手动关闭后退出");
        System.out.println("  --action <动作>         动作：" + String.join("/", GDragonAction.names()));
        System.out.println("  --voice <文本>          语音播报文本（同时打开语音开关）");
        System.out.println("  --target <x,y>          指向的屏幕坐标，角色会贴到目标旁边");
        System.out.println();
        System.out.println("系统属性（优先级高于默认值，无需改配置文件）：");
        System.out.println("  -Dcdtest.dragon.time=5000       默认持续时间");
        System.out.println("  -Dcdtest.dragon.max=3           同时显示数量上限");
        System.out.println("  -Dcdtest.dragon.interval=200    最小弹出间隔");
        System.out.println("  -Dcdtest.dragon.tip=true        打开提示弹窗开关");
        System.out.println("  -Dcdtest.dragon.roles=a.png,b.png  追加人形头像候选");
        System.out.println("  -Dcdtest.dragon.animate=false   关闭动画，只显示静态帧（低性能环境降级）");
        System.out.println("  -Dcdtest.dragon.voice=true      打开语音（默认关闭）");
        System.out.println("  -Dcdtest.dragon.skin=<路径>     指定素材目录或skin.properties");
        System.out.println("  -Dcdtest.dragon.scale=2         按2倍绘制，需配合2x素材");
        System.out.println();
        System.out.println("接入真实素材（无需改代码）：");
        System.out.println("  动作目录：src\\main\\resources\\image\\<角色名>_<动作名>\\1.png 2.png ...（推荐）");
        System.out.println("  角色名默认 cdperson，可用 -Dcdtest.dragon.character=xxx 修改");
        System.out.println("  单张 PNG 规范尺寸：480x720，32位带 alpha（透明底）");
    }

    /**
     *  解析命令行参数
     *
     *  <p>同时支持 {@code --key=value} 与 {@code --key value} 两种写法。</p>
     *
     *  @param args 命令行参数
     *  @return 参数表
     */
    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> params = new LinkedHashMap<>();
        if (args == null) {
            return params;
        }
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (!arg.startsWith("--") && !arg.startsWith("-")) {
                continue;
            }
            String key = arg.replaceFirst("^-+", "");
            if (key.contains("=")) {
                String[] pair = key.split("=", 2);
                params.put(pair[0], pair[1]);
                continue;
            }
            if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                params.put(key, args[i + 1]);
                i++;
            } else {
                params.put(key, "");
            }
        }
        return params;
    }

    /**
     *  解析整数
     *
     *  @param text 文本
     *  @param defaultValue 缺省值
     *  @return 整数值或缺省值
     */
    private static int intOf(String text, int defaultValue) {
        if (text == null || text.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     *  解析颜色
     *
     *  @param text 文本
     *  @return 颜色；解析失败返回默认字色
     */
    private static Color colorOf(String text) {
        try {
            return Color.decode(text.trim());
        } catch (Exception e) {
            return GDragonOption.DEFAULT_FONT_COLOR;
        }
    }
}
