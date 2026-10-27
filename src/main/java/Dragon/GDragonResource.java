package Dragon;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *  人形弹窗图片资源定位与加载
 *
 *  <p>原实现直接使用 {@code image/xxx.png} 这类相对路径，只在特定工作目录下才能取到图，
 *  {@code mvn test} 时几乎必然取不到，于是弹窗尺寸被算成负值。这里统一改为
 *  "classpath 优先、文件系统兜底"，使弹窗在任意工作目录、任意 IDE 下都能独立跑起来。</p>
 *
 *  <p>同时内置头像清单改为"实际存在才纳入随机"，避免出现随机到不存在图片后头像区空白。</p>
 *
 *  @see GDragon
 */
public final class GDragonResource {

    /**
     *  图片资源目录
     */
    public static final String IMAGE_DIR = "image/";

    /**
     *  系统属性：额外的人形头像图片名，逗号分隔
     */
    public static final String SYS_ROLES = "cdtest.dragon.roles";

    /**
     *  人形头像图片名
     *
     *  <p>人形弹窗统一使用这一张形象图；需要换形象时替换
     *  {@code image/cdpersonnalico.png} 即可，无需改动代码。</p>
     */
    public static final String ROLE_IMAGE = "cdpersonnalico.png";

    /**
     *  内置人形头像候选
     */
    private static final String[] KNOWN_ROLES = {ROLE_IMAGE};

    /**
     *  人形头像缺省尺寸 720*1080（2:3，与原 240*360 一致，缩放不变形）
     */
    private static final Dimension DEFAULT_ROLE_SIZE = new Dimension(720, 1080);

    /**
     *  单位块缺省尺寸
     */
    private static final Dimension DEFAULT_BLOCK_SIZE = new Dimension(25, 25);

    /**
     *  文件系统兜底根目录
     */
    private static final String[] FILE_ROOTS = {"", "src/main/resources/", "target/classes/"};

    private GDragonResource() {
    }

    /**
     *  定位图片
     *
     *  <p>查找顺序：classpath（打进 jar 也有效）→ 工作目录下若干候选根目录。</p>
     *
     *  @param name 图片名，如 {@code cdpersonnalico.png}；允许带 {@code image/} 前缀
     *  @return 图片地址；找不到返回 {@code null}
     */
    public static URL locate(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String trimmed = name.trim();
        String relative = trimmed.startsWith(IMAGE_DIR) ? trimmed : IMAGE_DIR + trimmed;

        ClassLoader loader = GDragonResource.class.getClassLoader();
        if (loader != null) {
            URL url = loader.getResource(relative);
            if (url != null) {
                return url;
            }
        }
        URL systemUrl = ClassLoader.getSystemResource(relative);
        if (systemUrl != null) {
            return systemUrl;
        }

        for (String root : FILE_ROOTS) {
            File file = new File(root + relative);
            if (file.isFile()) {
                try {
                    return file.toURI().toURL();
                } catch (MalformedURLException e) {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     *  加载图片
     *
     *  @param name 图片名
     *  @return 图片图标；找不到返回 {@code null}
     */
    public static ImageIcon icon(String name) {
        URL url = locate(name);
        return url == null ? null : new ImageIcon(url);
    }

    /**
     *  读取图片尺寸
     *
     *  @param name 图片名
     *  @param fallback 读取失败时的兜底尺寸
     *  @return 图片尺寸
     */
    public static Dimension size(String name, Dimension fallback) {
        URL url = locate(name);
        if (url != null) {
            try {
                BufferedImage image = ImageIO.read(url);
                if (image != null) {
                    return new Dimension(image.getWidth(), image.getHeight());
                }
            } catch (Exception e) {
                return fallback;
            }
        }
        return fallback;
    }

    /**
     *  读取人形头像尺寸，失败时回退到 240*360
     *
     *  @param name 图片名
     *  @return 图片尺寸
     */
    public static Dimension roleSize(String name) {
        return size(name, DEFAULT_ROLE_SIZE);
    }

    /**
     *  读取单位块/按钮图片尺寸，失败时回退到 25*25
     *
     *  @param name 图片名
     *  @return 图片尺寸
     */
    public static Dimension blockSize(String name) {
        return size(name, DEFAULT_BLOCK_SIZE);
    }

    /**
     *  列出实际可用的人形头像
     *
     *  @return 可加载的头像图片名清单；无可用图片时返回空列表
     */
    public static List<String> availableRoles() {
        List<String> roles = new ArrayList<>();
        for (String candidate : KNOWN_ROLES) {
            if (locate(candidate) != null) {
                roles.add(candidate);
            }
        }
        String extra = System.getProperty(SYS_ROLES);
        if (extra != null) {
            for (String item : extra.split(",")) {
                String name = item.trim();
                if (!name.isEmpty() && !roles.contains(name) && locate(name) != null) {
                    roles.add(name);
                }
            }
        }
        return Collections.unmodifiableList(roles);
    }

    /**
     *  随机选取一张可用人形头像
     *
     *  @return 图片名；无可用图片时返回空串
     */
    public static String randomRole() {
        List<String> roles = availableRoles();
        if (roles.isEmpty()) {
            return "";
        }
        return roles.get(new Random().nextInt(roles.size()));
    }

    /**
     *  获得首选人形头像
     *
     *  @return 图片名；无可用图片时返回空串
     */
    public static String defaultRole() {
        List<String> roles = availableRoles();
        return roles.isEmpty() ? "" : roles.get(0);
    }
}
