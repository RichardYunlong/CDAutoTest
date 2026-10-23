package Sys;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.PropertyPlaceholderHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

/**
 * 分层配置加载器。
 *
 * <p>后加载的键覆盖先加载的键：</p>
 * <ol>
 *     <li>classpath:config/*.ini —— 随包发布的默认值</li>
 *     <li>./config/*.ini —— 外部公共覆盖</li>
 *     <li>./config/{env}/*.ini —— 环境专属覆盖</li>
 * </ol>
 */
public class GPropertiesFactoryBean extends PropertiesFactoryBean {

    /**
     * 历史文件顺序只用于保持同名键的原覆盖规则；不在清单中的 ini 会被自动发现并追加加载。
     */
    private static final String[] CONFIG_FILES = {
            "sys.ini", "db.ini", "smtp.ini", "email.ini", "sut.ini",
            "local.ini", "coverage.ini", "quality.ini", "webdriver.ini"
    };

    /** 指定外部配置目录的系统属性名，缺省为 {@code config} */
    public static final String DIR_PROPERTY = "cdtest.config.dir";

    /** 指定运行环境名的系统属性名，用于选择 {@code config/{env}/} 覆盖目录 */
    public static final String ENV_PROPERTY = "cdtest.env";

    private static final PropertyPlaceholderHelper PLACEHOLDER_HELPER =
            new PropertyPlaceholderHelper("${", "}", ":", false);

    private final List<String> loaded = new ArrayList<>();

    /**
     * 按"classpath 默认值 → 外部公共覆盖 → 环境专属覆盖"的顺序合并全部 ini 配置
     *
     * <p>合并完成后统一解析 {@code ${...}} 占位符，并记录本次实际加载的来源文件。
     * 环境名取自 {@code cdtest.env} 系统属性、{@code CDTEST_ENV} 环境变量或
     * {@code sys.env} 配置项，三者依次降级。</p>
     *
     * @return 合并并解析后的配置集合
     * @throws IOException 读取任一 ini 文件失败时抛出
     */
    @Override
    protected Properties createProperties() throws IOException {
        Properties merged = new Properties();
        synchronized (loaded) {
            loaded.clear();
        }

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = GPropertiesFactoryBean.class.getClassLoader();
        }
        loadClasspathIni(merged, loader);

        File dir = new File(System.getProperty(DIR_PROPERTY, "config"));
        loadDirectoryIni(merged, dir);

        String env = resolveEnv(merged);
        if (!env.isEmpty()) {
            loadDirectoryIni(merged, new File(dir, env));
        }

        resolvePlaceholders(merged);
        return merged;
    }

    /**
     * 加载 classpath 下全部 {@code config/*.ini}
     *
     * <p>先按 {@link #CONFIG_FILES} 的历史顺序加载，保证同名键的覆盖关系与旧版本一致；
     * 再追加其余未列出的 ini（按文件名排序），从而支持新增配置文件而无需改代码。</p>
     *
     * @param merged 合并目标
     * @param loader 用于查找资源的类加载器
     * @throws IOException 读取资源失败时抛出
     */
    private void loadClasspathIni(Properties merged, ClassLoader loader) throws IOException {
        Resource[] found = new PathMatchingResourcePatternResolver(loader)
                .getResources("classpath*:config/*.ini");
        List<Resource> resources = new ArrayList<>(Arrays.asList(found));
        Collections.sort(resources, new Comparator<Resource>() {
            /**
             * 先按文件名忽略大小写排序，同名时再按资源描述排序
             *
             * <p>同名资源可能来自不同的 jar，二次排序保证加载顺序稳定可复现。</p>
             *
             * @param left 左侧资源
             * @param right 右侧资源
             * @return 负整数、0 或正整数，表示 {@code left} 排在 {@code right} 之前、等价或之后
             */
            @Override
            public int compare(Resource left, Resource right) {
                String leftName = left.getFilename() == null ? "" : left.getFilename();
                String rightName = right.getFilename() == null ? "" : right.getFilename();
                int byName = leftName.compareToIgnoreCase(rightName);
                return byName != 0 ? byName : left.getDescription().compareTo(right.getDescription());
            }
        });

        Set<String> loadedNames = new HashSet<>();
        for (String name : CONFIG_FILES) {
            Resource resource = firstByName(resources, name);
            if (resource != null) {
                loadResource(merged, resource, "classpath:config/" + name);
                loadedNames.add(name.toLowerCase(Locale.ROOT));
            }
        }
        for (Resource resource : resources) {
            String name = resource.getFilename();
            if (name != null && loadedNames.add(name.toLowerCase(Locale.ROOT))) {
                loadResource(merged, resource, "classpath:config/" + name);
            }
        }
    }

    /**
     * 在资源列表中按文件名查找第一个匹配项
     *
     * @param resources 已排序的资源列表
     * @param name 目标文件名
     * @return 第一个文件名忽略大小写匹配的资源；未找到返回 {@code null}
     */
    private static Resource firstByName(List<Resource> resources, String name) {
        for (Resource resource : resources) {
            if (name.equalsIgnoreCase(resource.getFilename())) {
                return resource;
            }
        }
        return null;
    }

    /**
     * 读取一个 classpath 资源并合并到配置集合
     *
     * @param merged 合并目标
     * @param resource 待读取的资源
     * @param source 记录到来源列表中的标识
     * @throws IOException 打开或读取资源失败时抛出
     */
    private void loadResource(Properties merged, Resource resource, String source) throws IOException {
        try (InputStream in = resource.getInputStream()) {
            loadInto(merged, in, source);
        }
    }

    /**
     * 加载指定目录下的全部 ini 文件
     *
     * <p>先按 {@link #CONFIG_FILES} 的历史顺序加载，再追加目录中其余 ini（按文件名排序）。</p>
     *
     * @param merged 合并目标
     * @param dir 目录；不存在时等价于不做任何加载
     * @throws IOException 读取文件失败时抛出
     */
    private void loadDirectoryIni(Properties merged, File dir) throws IOException {
        Set<String> knownNames = new HashSet<>();
        for (String name : CONFIG_FILES) {
            knownNames.add(name.toLowerCase(Locale.ROOT));
            loadIfExists(merged, new File(dir, name));
        }
        for (File file : discoverIni(dir)) {
            if (!knownNames.contains(file.getName().toLowerCase(Locale.ROOT))) {
                loadIfExists(merged, file);
            }
        }
    }

    /**
     * 发现目录下的全部 ini 文件并按文件名排序
     *
     * @param dir 待扫描目录
     * @return 已排序的 ini 文件列表；目录不存在或不可读时返回空列表
     */
    private static List<File> discoverIni(File dir) {
        File[] files = dir.listFiles(file -> file.isFile()
                && file.getName().toLowerCase(Locale.ROOT).endsWith(".ini"));
        if (files == null) {
            return Collections.emptyList();
        }
        List<File> result = new ArrayList<>(Arrays.asList(files));
        Collections.sort(result, new Comparator<File>() {
            /**
             * 按文件名忽略大小写排序
             *
             * @param left 左侧文件
             * @param right 右侧文件
             * @return 负整数、0 或正整数，表示 {@code left} 排在 {@code right} 之前、等价或之后
             */
            @Override
            public int compare(File left, File right) {
                return left.getName().compareToIgnoreCase(right.getName());
            }
        });
        return result;
    }

    /**
     * 若文件存在则加载并合并
     *
     * @param merged 合并目标
     * @param file 待加载的文件；不是普通文件时直接跳过
     * @throws IOException 读取文件失败时抛出
     */
    private void loadIfExists(Properties merged, File file) throws IOException {
        if (!file.isFile()) {
            return;
        }
        try (InputStream in = new FileInputStream(file)) {
            loadInto(merged, in, file.getPath());
        }
    }

    /**
     * 以 UTF-8 读取输入流并合并到配置集合，同时记录来源
     *
     * @param merged 合并目标
     * @param in 待读取的输入流，由调用方负责关闭
     * @param source 记录到来源列表中的标识
     * @throws IOException 读取失败时抛出
     */
    private void loadInto(Properties merged, InputStream in, String source) throws IOException {
        merged.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        synchronized (loaded) {
            loaded.add(source);
        }
    }

    /**
     * 解析全部配置值中的 {@code ${...}} 占位符
     *
     * <p>占位符查找顺序：配置集合自身 → JVM 系统属性 → 环境变量。
     * 三者都找不到时保留原样（值为 {@code null} 会被 {@code GConfig} 判定为配置错误）。</p>
     *
     * @param merged 待解析的配置集合，原地修改
     */
    private static void resolvePlaceholders(Properties merged) {
        for (String name : merged.stringPropertyNames()) {
            String resolved = PLACEHOLDER_HELPER.replacePlaceholders(
                    merged.getProperty(name), placeholderName -> {
                        String value = merged.getProperty(placeholderName);
                        if (value == null) {
                            value = System.getProperty(placeholderName);
                        }
                        if (value == null) {
                            value = System.getenv(placeholderName);
                        }
                        return value;
                    });
            merged.setProperty(name, resolved);
        }
    }

    /**
     * 解析当前运行环境名
     *
     * <p>取值优先级：{@code cdtest.env} 系统属性 → {@code CDTEST_ENV} 环境变量 →
     * 配置项 {@code sys.env}。</p>
     *
     * @param merged 已加载的配置集合
     * @return 环境名（已去空白）；未配置时返回空串
     */
    private static String resolveEnv(Properties merged) {
        String env = System.getProperty(ENV_PROPERTY);
        if (isBlank(env)) {
            env = System.getenv("CDTEST_ENV");
        }
        if (isBlank(env)) {
            env = merged.getProperty("sys.env");
        }
        return env == null ? "" : env.trim();
    }

    /**
     * 判断字符串是否为空白
     *
     * @param value 待判断的字符串
     * @return 为 {@code null}、空串或仅含空白字符时返回 {@code true}
     */
    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * 获得本次加载的配置来源列表
     *
     * @return 按实际加载顺序排列的来源标识副本，调用方可安全修改
     */
    public List<String> getLoadedResources() {
        synchronized (loaded) {
            return new ArrayList<>(loaded);
        }
    }
}
