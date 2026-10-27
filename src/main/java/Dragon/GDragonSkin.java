package Dragon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *  人形弹窗素材（皮肤）
 *
 *  <p>方案A的素材是"离线渲染好的 PNG 序列帧（带 alpha）"。这里支持四种来源，按优先级：</p>
 *  <ol>
 *      <li><b>动作目录</b>（推荐）：{@code image/<角色名>_<动作名>/1.png 2.png ...}，
 *          一个动作一个文件夹，文件名数字即播放顺序，放得越多动作越复杂；</li>
 *      <li><b>图集</b>：{@code skin.properties} + 一张 sprite sheet，按行切片，适合素材定稿后打包；</li>
 *      <li><b>单动作横排 PNG</b>：{@code image/skin/default/<动作名>.png}，帧从左到右横排；</li>
 *      <li><b>占位生成</b>：{@link GDragonPlaceholder} 程序化生成，保证没有素材也能跑通链路。</li>
 *  </ol>
 *
 *  <pre>
 *  动作目录规范：
 *  src\main\resources\image\
 *      cdperson_idle\1.png 2.png ... 12.png
 *      cdperson_talk\1.png 2.png ... 8.png
 *      cdperson_point\1.png ... 8.png
 *      cdperson_point_l\1.png ... 8.png      朝向左侧（元素在左时使用，可缺省）
 *      cdperson_point_r\1.png ... 8.png      朝向右侧（元素在右时使用，可缺省）
 *      cdperson_click\1.png ... 6.png
 *      cdperson_success\1.png ... 12.png
 *      cdperson_fail\1.png ... 8.png
 *      cdperson_thinking\1.png ... 8.png
 *      cdperson_bye\1.png ... 12.png
 *
 *  每张 PNG：480x720 像素，32 位带 alpha（透明底）
 *  角色名默认 cdperson，可用 -Dcdtest.dragon.character=xxx 修改
 *  </pre>
 *
 *  <p>只提供了部分动作时，缺失动作自动用占位帧补齐；
 *  素材尺寸不必与显示尺寸一致，加载时统一按显示倍率缩放一次，播放路径上不再缩放。</p>
 *
 *  <p>方向变体（{@code _l} / {@code _r} 后缀）用于"指向"这类需要面朝目标的动作：
 *  弹窗会按箭头方向自动选左或右，没有提供变体时退回该动作本身。</p>
 *
 *  <p>所有帧在加载阶段一次性解码常驻（{@code TYPE_INT_ARGB_PRE}），播放路径上不再解码。</p>
 *
 *  <pre>
 *  skin.properties 示例：
 *  name=default
 *  sheet=sheet.png
 *  frameWidth=240
 *  frameHeight=360
 *  idle.row=0
 *  idle.frames=12
 *  idle.fps=8
 *  idle.loop=true
 *  </pre>
 */
public final class GDragonSkin {

    /**
     *  系统属性：素材位置
     */
    public static final String SYS_SKIN = "cdtest.dragon.skin";

    /**
     *  系统属性：角色名（英文），决定动作目录名前缀
     */
    public static final String SYS_CHARACTER = "cdtest.dragon.character";

    /**
     *  默认角色名
     */
    public static final String DEFAULT_CHARACTER = "cdperson";

    /**
     *  素材规范尺寸：宽 480 像素
     *
     *  <p>为显示基准尺寸的 2 倍，在高 DPI 屏上可 1:1 显示；普通屏上降采样显示，边缘更锐利。</p>
     */
    public static final int SOURCE_WIDTH = 480;

    /**
     *  素材规范尺寸：高 720 像素
     */
    public static final int SOURCE_HEIGHT = 720;

    /**
     *  素材常驻内存预算，超出时给出提示（不阻断）
     */
    public static final long MEMORY_BUDGET = 80L * 1024 * 1024;

    /**
     *  默认素材目录（相对 classpath）
     */
    public static final String DEFAULT_DIR = "image/skin/default/";

    /**
     *  默认素材
     */
    private static volatile GDragonSkin defaultSkin;

    /**
     *  素材名
     */
    private final String name;

    /**
     *  来源描述
     */
    private final String source;

    /**
     *  动作帧序列
     */
    private final Map<GDragonAction, GDragonFrames> actions;

    /**
     *  方向变体帧序列
     *
     *  <p>key 为 {@code <动作名>_l} / {@code <动作名>_r}，对应
     *  {@code image/<角色名>_<动作名>_l} 与 {@code _r} 两个目录，
     *  用于"指向"这类需要朝向元素的动作。没有变体时自动退回该动作本身。</p>
     */
    private final Map<String, GDragonFrames> variants;

    /**
     *  帧宽
     */
    private final int frameWidth;

    /**
     *  帧高
     */
    private final int frameHeight;

    private GDragonSkin(String name, String source, Map<GDragonAction, GDragonFrames> actions,
                        Map<String, GDragonFrames> variants, int frameWidth, int frameHeight) {
        this.name = name;
        this.source = source;
        this.actions = actions;
        this.variants = variants;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }

    /**
     *  获得默认素材（懒加载并缓存）
     *
     *  @return 默认素材
     */
    public static GDragonSkin getDefault() {
        if (defaultSkin == null) {
            synchronized (GDragonSkin.class) {
                if (defaultSkin == null) {
                    defaultSkin = load(System.getProperty(SYS_SKIN));
                }
            }
        }
        return defaultSkin;
    }

    /**
     *  重新加载默认素材，供调试时反复换素材观察效果
     *
     *  @return 重新加载后的素材
     */
    public static GDragonSkin reload() {
        synchronized (GDragonSkin.class) {
            defaultSkin = load(System.getProperty(SYS_SKIN));
            return defaultSkin;
        }
    }

    /**
     *  加载素材
     *
     *  @param location 目录或 skin.properties 路径；为空时依次尝试默认目录与占位生成
     *  @return 素材
     */
    public static GDragonSkin load(String location) {
        Map<GDragonAction, GDragonFrames> loaded = new EnumMap<>(GDragonAction.class);
        String character = resolveCharacter();
        String name = character;
        String source = "placeholder";
        //逻辑尺寸决定窗体布局，像素尺寸决定位图分辨率，二者按显示倍率关联
        int logicWidth = GDragonPlaceholder.WIDTH;
        int logicHeight = GDragonPlaceholder.HEIGHT;
        int scale = Math.max(GDragonStage.resolveScale(), 1);
        int pixelWidth = logicWidth * scale;
        int pixelHeight = logicHeight * scale;

        Properties props = findProperties(location, DEFAULT_DIR + "skin.properties");
        if (props != null) {
            name = props.getProperty("name", character);
            character = props.getProperty("character", character);
            logicWidth = intOf(props.getProperty("frameWidth"), logicWidth);
            logicHeight = intOf(props.getProperty("frameHeight"), logicHeight);
            pixelWidth = logicWidth * scale;
            pixelHeight = logicHeight * scale;
        }

        Map<String, GDragonFrames> variants = new HashMap<>();
        //1.动作目录：image/<角色名>_<动作名>/1.png 2.png ...
        loaded = loadActionDirs(character, pixelWidth, pixelHeight);
        if (!loaded.isEmpty()) {
            source = "dirs:image/" + character + "_*";
            //方向变体：image/<角色名>_<动作名>_l（朝左）/ _r（朝右）
            variants.putAll(loadVariantDirs(character, pixelWidth, pixelHeight));
        }
        //2.图集
        if (loaded.isEmpty() && props != null) {
            loaded = loadSheet(props, pixelWidth, pixelHeight);
            if (!loaded.isEmpty()) {
                source = "sheet:" + props.getProperty("sheet", "per-action");
            }
        }
        //3.每个动作一张横排 PNG
        if (loaded.isEmpty()) {
            loaded = loadAutoSheets(0);
            if (!loaded.isEmpty()) {
                source = "autoSheet:" + DEFAULT_DIR;
            }
        }
        //4.旧版序列帧目录，兼容早期素材
        if (loaded.isEmpty()) {
            loaded = loadSequence(DEFAULT_DIR, pixelWidth, pixelHeight);
            if (!loaded.isEmpty()) {
                source = "sequence:" + DEFAULT_DIR;
            }
        }

        Map<GDragonAction, GDragonFrames> placeholder = GDragonPlaceholder.generate(pixelWidth, pixelHeight);
        Map<GDragonAction, GDragonFrames> merged = new EnumMap<>(GDragonAction.class);
        for (GDragonAction action : GDragonAction.values()) {
            GDragonFrames frames = loaded.get(action);
            merged.put(action, frames == null ? placeholder.get(action) : frames);
        }
        GDragonSkin skin = new GDragonSkin(name, source, merged, variants, logicWidth, logicHeight);
        long bytes = skin.estimateBytes();
        if (bytes > MEMORY_BUDGET) {
            System.out.println("[Dragon] 提示：素材常驻内存 " + (bytes / 1024 / 1024)
                    + "MB 超出 " + (MEMORY_BUDGET / 1024 / 1024)
                    + "MB 预算，建议减少帧数或把素材尺寸降到 " + (SOURCE_WIDTH / 2) + "x" + (SOURCE_HEIGHT / 2) + "。");
        }
        return skin;
    }

    /**
     *  确定角色名
     *
     *  @return 角色名；未配置时返回 {@link #DEFAULT_CHARACTER}
     */
    private static String resolveCharacter() {
        String character = System.getProperty(SYS_CHARACTER);
        if (character == null || character.trim().isEmpty()) {
            return DEFAULT_CHARACTER;
        }
        return character.trim();
    }

    /**
     *  获得动作的帧序列
     *
     *  @param action 动作
     *  @return 帧序列；不存在时返回 {@code null}
     */
    public GDragonFrames getFrames(GDragonAction action) {
        return actions.get(action == null ? GDragonAction.IDLE : action);
    }

    /**
     *  获得动作在指定朝向上的帧序列
     *
     *  <p>优先取方向变体（{@code image/<角色名>_<动作名>_l} / {@code _r}），
     *  没有变体时退回该动作本身——所以只有一套素材也能正常播放，不会取不到帧。</p>
     *
     *  @param action 动作
     *  @param left 人物是否面朝左侧
     *  @return 帧序列；不存在时返回 {@code null}
     */
    public GDragonFrames getFrames(GDragonAction action, boolean left) {
        GDragonAction target = action == null ? GDragonAction.IDLE : action;
        GDragonFrames variant = variants.get(target.getName() + (left ? "_l" : "_r"));
        return variant == null ? getFrames(target) : variant;
    }

    /**
     *  是否存在指定动作的方向变体
     *
     *  @param action 动作
     *  @param left 人物是否面朝左侧
     *  @return 存在返回 {@code true}
     */
    public boolean hasVariant(GDragonAction action, boolean left) {
        GDragonAction target = action == null ? GDragonAction.IDLE : action;
        return variants.containsKey(target.getName() + (left ? "_l" : "_r"));
    }

    /**
     *  是否存在指定动作的素材
     *
     *  @param action 动作
     *  @return 存在返回 {@code true}
     */
    public boolean has(GDragonAction action) {
        return getFrames(action) != null;
    }

    /**
     *  获得素材名
     *
     *  @return 素材名
     */
    public String getName() {
        return name;
    }

    /**
     *  获得来源描述
     *
     *  @return 来源描述
     */
    public String getSource() {
        return source;
    }

    /**
     *  获得帧宽
     *
     *  @return 帧宽
     */
    public int getFrameWidth() {
        return frameWidth;
    }

    /**
     *  获得帧高
     *
     *  @return 帧高
     */
    public int getFrameHeight() {
        return frameHeight;
    }

    /**
     *  估算常驻内存
     *
     *  @return 内存估算值，单位字节
     */
    public long estimateBytes() {
        long total = 0;
        for (GDragonFrames frames : actions.values()) {
            if (frames != null) {
                total += frames.estimateBytes();
            }
        }
        for (GDragonFrames frames : variants.values()) {
            if (frames != null) {
                total += frames.estimateBytes();
            }
        }
        return total;
    }

    /**
     *  输出素材摘要，便于调试时确认加载的是真实素材还是占位素材
     *
     *  @return 素材摘要
     */
    public String stats() {
        StringBuilder text = new StringBuilder("GDragonSkin{name=").append(name)
                .append(", source=").append(source)
                .append(", frame=").append(frameWidth).append('x').append(frameHeight)
                .append(", memory=").append(estimateBytes() / 1024).append("KB, actions=");
        for (GDragonAction action : GDragonAction.values()) {
            GDragonFrames frames = actions.get(action);
            text.append(action.getName()).append('(')
                    .append(frames == null ? 0 : frames.size())
                    .append('@')
                    .append(frames == null ? 0 : frames.getFps())
                    .append(") ");
        }
        if (!variants.isEmpty()) {
            text.append("variants=");
            for (Map.Entry<String, GDragonFrames> entry : variants.entrySet()) {
                text.append(entry.getKey()).append('(')
                        .append(entry.getValue().size())
                        .append('@')
                        .append(entry.getValue().getFps())
                        .append(") ");
            }
        }
        return text.append('}').toString();
    }

    /**
     *  查找配置文件
     *
     *  @param location 显式指定的目录或文件；可为空
     *  @param classpathDefault classpath 默认位置
     *  @return 配置；找不到返回 {@code null}
     */
    private static Properties findProperties(String location, String classpathDefault) {
        if (location != null && !location.trim().isEmpty()) {
            String path = location.trim();
            if (path.toLowerCase().endsWith(".properties")) {
                return readProperties(GDragonResource.locate(path), readFileProperties(new File(path)));
            }
            File dirFile = new File(path);
            Properties fromFile = readFileProperties(new File(dirFile, "skin.properties"));
            if (fromFile != null) {
                return fromFile;
            }
        }
        return readProperties(GDragonResource.locate(classpathDefault), null);
    }

    /**
     *  读取配置，优先取 classpath 结果，其次取文件系统结果
     *
     *  @param url classpath 地址
     *  @param fallback 文件系统结果
     *  @return 配置；都为空返回 {@code null}
     */
    private static Properties readProperties(URL url, Properties fallback) {
        if (url != null) {
            try (InputStream in = url.openStream()) {
                Properties props = new Properties();
                props.load(in);
                return props;
            } catch (Exception e) {
                return fallback;
            }
        }
        return fallback;
    }

    private static Properties readFileProperties(File file) {
        if (file == null || !file.isFile()) {
            return null;
        }
        try (InputStream in = new java.io.FileInputStream(file)) {
            Properties props = new Properties();
            props.load(in);
            return props;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *  按图集切片加载
     *
     *  @param props 配置
     *  @param width 帧宽
     *  @param height 帧高
     *  @return 动作与帧序列的映射
     */
    private static Map<GDragonAction, GDragonFrames> loadSheet(Properties props, int width, int height) {
        Map<GDragonAction, GDragonFrames> result = new EnumMap<>(GDragonAction.class);
        String globalSheet = props.getProperty("sheet");
        Map<String, BufferedImage> cache = new HashMap<>();
        for (GDragonAction action : GDragonAction.values()) {
            String prefix = action.getName() + ".";
            String sheetName = props.getProperty(prefix + "sheet", globalSheet);
            if (sheetName == null || sheetName.trim().isEmpty()) {
                continue;
            }
            BufferedImage sheet = cache.get(sheetName);
            if (sheet == null) {
                sheet = readImage(sheetName);
                if (sheet == null) {
                    continue;
                }
                cache.put(sheetName, sheet);
            }
            //未配置帧数时按图集宽度自动推断
            int frames = intOf(props.getProperty(prefix + "frames"), 0);
            if (frames <= 0) {
                frames = sheet.getWidth() / Math.max(width, 1);
            }
            int row = intOf(props.getProperty(prefix + "row"), 0);
            int count = Math.min(frames, sheet.getWidth() / Math.max(width, 1));
            List<BufferedImage> list = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                int x = i * width;
                int y = row * height;
                if (x + width > sheet.getWidth() || y + height > sheet.getHeight()) {
                    break;
                }
                list.add(copy(sheet.getSubimage(x, y, width, height)));
            }
            if (!list.isEmpty()) {
                int fps = intOf(props.getProperty(prefix + "fps"), action.getDefaultFps());
                Boolean loop = props.containsKey(prefix + "loop")
                        ? Boolean.parseBoolean(props.getProperty(prefix + "loop")) : null;
                result.put(action, new GDragonFrames(action, list, fps, loop));
            }
        }
        return result;
    }

    /**
     *  零配置加载：每个动作一张横排 PNG
     *
     *  <p>约定文件名即动作名，放在 {@code image/skin/default/} 下（或 {@code sheets/} 子目录）：
     *  {@code idle.png talk.png point.png click.png success.png fail.png thinking.png bye.png}。</p>
     *
     *  <p>单张图片内所有帧<b>从左到右横排一行</b>，帧高等于图片高度，帧宽按图片高度与 2:3 比例推断；
     *  只提供了部分动作时，缺失动作由占位素材补齐。</p>
     *
     *  @param preferredWidth 期望帧宽；为 0 时自动推断
     *  @return 动作与帧序列的映射
     */
    private static Map<GDragonAction, GDragonFrames> loadAutoSheets(int preferredWidth) {
        Map<GDragonAction, GDragonFrames> result = new EnumMap<>(GDragonAction.class);
        for (GDragonAction action : GDragonAction.values()) {
            BufferedImage sheet = readImage(action.getName() + ".png");
            if (sheet == null) {
                sheet = readImage("sheets/" + action.getName() + ".png");
            }
            if (sheet == null) {
                continue;
            }
            int frameHeight = sheet.getHeight();
            int frameWidth = preferredWidth > 0 ? preferredWidth : frameHeight * 2 / 3;
            if (frameWidth <= 0 || sheet.getWidth() % frameWidth != 0) {
                frameWidth = frameHeight;
            }
            int count = Math.max(sheet.getWidth() / Math.max(frameWidth, 1), 1);
            List<BufferedImage> list = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                int x = i * frameWidth;
                if (x + frameWidth > sheet.getWidth()) {
                    break;
                }
                list.add(copy(sheet.getSubimage(x, 0, frameWidth, frameHeight)));
            }
            if (!list.isEmpty()) {
                result.put(action, new GDragonFrames(action, list, action.getDefaultFps(), action.isLoop()));
            }
        }
        return result;
    }

    /**
     *  按动作目录加载：image/&lt;角色名&gt;_&lt;动作名&gt;/1.png 2.png ...
     *
     *  <p>文件名中的数字即播放顺序（按数值排序，不是字典序，10 会排在 2 之后）；
     *  目录内 PNG 数量就是该动作的帧数，放得越多动作越细腻。</p>
     *
     *  @param character 角色名（英文）
     *  @param width 目标帧宽
     *  @param height 目标帧高
     *  @return 动作与帧序列的映射
     */
    private static Map<GDragonAction, GDragonFrames> loadActionDirs(String character, int width, int height) {
        Map<GDragonAction, GDragonFrames> result = new EnumMap<>(GDragonAction.class);
        for (GDragonAction action : GDragonAction.values()) {
            String dir = GDragonResource.IMAGE_DIR + character + "_" + action.getName() + "/";
            List<String> names = listPng(dir);
            if (names.isEmpty()) {
                continue;
            }
            List<BufferedImage> list = new ArrayList<>(names.size());
            for (String name : names) {
                BufferedImage image = readImage(dir + name);
                if (image == null) {
                    continue;
                }
                list.add(image.getWidth() == width && image.getHeight() == height
                        ? copy(image) : scale(image, width, height));
            }
            if (!list.isEmpty()) {
                result.put(action, new GDragonFrames(action, list, action.getDefaultFps(), action.isLoop()));
            }
        }
        return result;
    }

    /**
     *  按方向变体目录加载：image/&lt;角色名&gt;_&lt;动作名&gt;_l/ 与 _r/
     *
     *  <p>帧规则与 {@link #loadActionDirs(String, int, int)} 一致，只是目录名带朝向后缀；
     *  帧率与是否循环沿用对应动作自身的设定。只有部分动作提供了变体也没关系，
     *  取帧时会自动退回该动作本身。</p>
     *
     *  @param character 角色名（英文）
     *  @param width 目标帧宽
     *  @param height 目标帧高
     *  @return 变体名与帧序列的映射
     */
    private static Map<String, GDragonFrames> loadVariantDirs(String character, int width, int height) {
        Map<String, GDragonFrames> result = new HashMap<>();
        for (GDragonAction action : GDragonAction.values()) {
            for (String suffix : new String[]{"_l", "_r"}) {
                String dir = GDragonResource.IMAGE_DIR + character + "_" + action.getName() + suffix + "/";
                List<String> names = listPng(dir);
                if (names.isEmpty()) {
                    continue;
                }
                List<BufferedImage> list = new ArrayList<>(names.size());
                for (String name : names) {
                    BufferedImage image = readImage(dir + name);
                    if (image == null) {
                        continue;
                    }
                    list.add(image.getWidth() == width && image.getHeight() == height
                            ? copy(image) : scale(image, width, height));
                }
                if (!list.isEmpty()) {
                    result.put(action.getName() + suffix,
                            new GDragonFrames(action, list, action.getDefaultFps(), action.isLoop()));
                }
            }
        }
        return result;
    }

    /**
     *  列出目录下的 PNG 文件名，按文件名中的数字排序
     *
     *  @param dir 目录，相对 classpath，以 {@code /} 结尾
     *  @return 文件名清单；目录不存在时返回空列表
     */
    private static List<String> listPng(String dir) {
        List<String> names = new ArrayList<>();
        collectPng(dir, names);
        if (names.isEmpty()) {
            //classpath 里没有（例如从 jar 之外的工作目录运行）时再查文件系统
            File folder = new File(dir);
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".png")
                            && !names.contains(file.getName())) {
                        names.add(file.getName());
                    }
                }
            }
        }
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String left, String right) {
                int diff = orderOf(left) - orderOf(right);
                return diff != 0 ? diff : left.compareTo(right);
            }
        });
        return names;
    }

    /**
     *  从 classpath 收集目录下的 PNG 文件名，支持开发目录与 jar 包
     *
     *  @param dir 目录
     *  @param names 收集结果
     */
    private static void collectPng(String dir, List<String> names) {
        try {
            ClassLoader loader = GDragonSkin.class.getClassLoader();
            Enumeration<URL> urls = loader != null ? loader.getResources(dir) : ClassLoader.getSystemResources(dir);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if ("file".equals(url.getProtocol())) {
                    File folder = new File(url.toURI());
                    File[] files = folder.listFiles();
                    if (files == null) {
                        continue;
                    }
                    for (File file : files) {
                        if (file.isFile() && file.getName().toLowerCase().endsWith(".png")
                                && !names.contains(file.getName())) {
                            names.add(file.getName());
                        }
                    }
                } else if ("jar".equals(url.getProtocol())) {
                    java.net.JarURLConnection connection = (java.net.JarURLConnection) url.openConnection();
                    java.util.jar.JarFile jar = connection.getJarFile();
                    Enumeration<java.util.jar.JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        String entry = entries.nextElement().getName();
                        if (!entry.startsWith(dir) || !entry.toLowerCase().endsWith(".png")) {
                            continue;
                        }
                        String name = entry.substring(dir.length());
                        if (!name.isEmpty() && !name.contains("/") && !names.contains(name)) {
                            names.add(name);
                        }
                    }
                }
            }
        } catch (Exception e) {
            //资源目录不存在或无法遍历时按空处理，由占位素材兜底
        }
    }

    /**
     *  取文件名中的数字作为播放顺序
     *
     *  @param name 文件名，如 {@code 12.png}
     *  @return 数字；无数字时返回一个足够大的值，使其排在后面
     */
    private static int orderOf(String name) {
        StringBuilder digits = new StringBuilder();
        int dot = name.lastIndexOf('.');
        String base = dot > 0 ? name.substring(0, dot) : name;
        for (int i = 0; i < base.length(); i++) {
            char c = base.charAt(i);
            if (Character.isDigit(c)) {
                digits.append(c);
            }
        }
        if (digits.length() == 0) {
            return Integer.MAX_VALUE;
        }
        try {
            return Integer.parseInt(digits.toString());
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }

    /**
     *  按序列帧目录加载（旧版规范，保留兼容）
     *
     *  @param dir 素材目录，相对 classpath
     *  @param width 帧宽
     *  @param height 帧高
     *  @return 动作与帧序列的映射
     */
    private static Map<GDragonAction, GDragonFrames> loadSequence(String dir, int width, int height) {
        Map<GDragonAction, GDragonFrames> result = new EnumMap<>(GDragonAction.class);
        for (GDragonAction action : GDragonAction.values()) {
            List<BufferedImage> list = new ArrayList<>();
            for (int i = 1; i <= 999; i++) {
                String name = "actions/" + action.getName() + "/" + String.format("%04d", i) + ".png";
                BufferedImage image = readImage(dir + name);
                if (image == null) {
                    break;
                }
                list.add(image.getWidth() == width && image.getHeight() == height
                        ? copy(image) : scale(image, width, height));
            }
            if (!list.isEmpty()) {
                result.put(action, new GDragonFrames(action, list, action.getDefaultFps(), action.isLoop()));
            }
        }
        return result;
    }

    /**
     *  读取图片
     *
     *  @param name 图片名
     *  @return 位图；读取失败返回 {@code null}
     */
    private static BufferedImage readImage(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        URL url = GDragonResource.locate(name);
        if (url == null && !name.startsWith("image/")) {
            url = GDragonResource.locate("image/skin/default/" + name);
        }
        if (url == null) {
            File file = new File(name);
            if (file.isFile()) {
                try {
                    return ImageIO.read(file);
                } catch (Exception e) {
                    return null;
                }
            }
            return null;
        }
        try {
            return ImageIO.read(url);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *  统一转为带 alpha 的位图
     *
     *  @param source 源图
     *  @return 转换后的位图
     */
    private static BufferedImage copy(BufferedImage source) {
        BufferedImage target = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        java.awt.Graphics2D g = target.createGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return target;
    }

    /**
     *  缩放并转为带 alpha 的位图
     *
     *  @param source 源图
     *  @param width 目标宽
     *  @param height 目标高
     *  @return 转换后的位图
     */
    private static BufferedImage scale(BufferedImage source, int width, int height) {
        BufferedImage target = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
        java.awt.Graphics2D g = target.createGraphics();
        g.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(source, 0, 0, width, height, null);
        g.dispose();
        return target;
    }

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
}
