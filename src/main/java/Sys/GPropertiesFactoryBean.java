package Sys;

import org.springframework.beans.factory.config.PropertiesFactoryBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 *  分层配置加载器
 *
 *  <p>原实现把 9 个 ini 全部写死在 spring.xml 的 classpath 里，
 *  要改一个地址就必须重新打包 19MB 的 jar。这里改为三层覆盖，后加载的键覆盖先加载的键：</p>
 *
 *  <ol>
 *      <li>classpath:config/*.ini  —— 随包发布的默认值</li>
 *      <li>./config/*.ini         —— 外部覆盖，部署时直接改，不用重打 jar</li>
 *      <li>./config/{env}/*.ini   —— 环境专属覆盖(env 取 local/test/pro 等)</li>
 *  </ol>
 *
 *  <p>环境名取值优先级：-Dcdtest.env &gt; 环境变量 CDTEST_ENV &gt; ini 中的 sys.env。</p>
 *  <p>外部目录默认是启动目录下的 config，可用 -Dcdtest.config.dir 指定绝对或相对路径。</p>
 */
public class GPropertiesFactoryBean extends PropertiesFactoryBean {

    /**
     *  配置文件清单，与原 spring.xml 中列出的 9 个文件保持一致
     */
    private static final String[] CONFIG_FILES = {
            "sys.ini", "db.ini", "smtp.ini", "email.ini", "sut.ini",
            "local.ini", "coverage.ini", "quality.ini", "webdriver.ini"
    };

    /**
     *  外部配置目录的系统属性名
     */
    public static final String DIR_PROPERTY = "cdtest.config.dir";

    /**
     *  环境名的系统属性名
     */
    public static final String ENV_PROPERTY = "cdtest.env";

    /**
     *  实际加载到的配置来源，供启动日志输出，便于现场确认"用的到底是哪份配置"
     */
    private static final List<String> LOADED = Collections.synchronizedList(new ArrayList<String>());

    /**
     *  构建配置集(Spring 4.3 的扩展点是 createProperties，getObject 为 final 且返回缓存实例)
     *
     *  @return 合并后的配置
     *  @throws IOException 读取失败
     */
    @Override
    protected Properties createProperties() throws IOException {
        Properties merged = new Properties();
        LOADED.clear();

        // 第一层：随包发布的默认值
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = GPropertiesFactoryBean.class.getClassLoader();
        }
        for (String name : CONFIG_FILES) {
            try (InputStream in = loader.getResourceAsStream("config/" + name)) {
                if (in != null) {
                    loadInto(merged, in, "classpath:config/" + name);
                }
            }
        }

        // 第二层：外部目录覆盖
        File dir = new File(System.getProperty(DIR_PROPERTY, "config"));
        for (String name : CONFIG_FILES) {
            loadIfExists(merged, new File(dir, name));
        }

        // 第三层：环境专属覆盖
        String env = resolveEnv(merged);
        if (!env.isEmpty()) {
            File envDir = new File(dir, env);
            for (String name : CONFIG_FILES) {
                loadIfExists(merged, new File(envDir, name));
            }
        }

        return merged;
    }

    /**
     *  加载单个文件(存在才加载)
     *
     *  @param merged 汇总结果
     *  @param file   配置文件
     *  @throws IOException 读取失败
     */
    private static void loadIfExists(Properties merged, File file) throws IOException {
        if (!file.isFile()) {
            return;
        }
        try (InputStream in = new FileInputStream(file)) {
            loadInto(merged, in, file.getPath());
        }
    }

    /**
     *  按 UTF-8 读入一个配置文件
     *
     *  @param merged 汇总结果
     *  @param in     输入流
     *  @param source 来源描述
     *  @throws IOException 读取失败
     */
    private static void loadInto(Properties merged, InputStream in, String source) throws IOException {
        merged.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        LOADED.add(source);
    }

    /**
     *  解析环境名
     *
     *  @param merged 已加载的配置(用于读取 ini 中的 sys.env)
     *  @return 环境名，未配置时返回空串
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
     *  判断空白
     *
     *  @param value 字符串
     *  @return 是否为空白
     */
    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     *  获取本次加载到的配置来源清单
     *
     *  @return 来源清单副本
     */
    public static List<String> getLoadedResources() {
        synchronized (LOADED) {
            return new ArrayList<>(LOADED);
        }
    }
}
