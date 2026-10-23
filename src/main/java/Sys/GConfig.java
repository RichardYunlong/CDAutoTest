package Sys;

import DUnit.GAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;

/**
 * 统一类型化配置中心。
 */
@Component
public class GConfig {

    private static final String MASKED_VALUE = "******(已脱敏)";
    private static volatile GConfig current;

    @Resource(name = "properties")
    private Properties mergedProperties;

    @Autowired
    private ApplicationContext applicationContext;

    private final Properties properties = new Properties();
    private final Map<String, GConfigKey<?>> keyDefinitions = GConfigKeys.byName();
    private final Map<GConfigKey.Section, GAttribute> attributes =
            new EnumMap<>(GConfigKey.Section.class);
    private List<String> loadedSources = Collections.emptyList();

    private final SysSection sys = new SysSection();
    private final DbSection db = new DbSection();
    private final SmtpSection smtp = new SmtpSection();
    private final EmailSection email = new EmailSection();
    private final SutSection sut = new SutSection();
    private final CoverageSection coverage = new CoverageSection();
    private final QualitySection quality = new QualitySection();
    private final WebdriverSection webdriver = new WebdriverSection();

    /**
     * 初始化配置中心
     *
     * <p>由 Spring 在容器启动后自动调用，依次完成：</p>
     * <ol>
     *   <li>校验 properties 是否已注入；</li>
     *   <li>按分区（{@link GConfigKey.Section}）建立空的属性表；</li>
     *   <li>预读全部已声明的配置项，触发占位符解析与类型转换；</li>
     *   <li>校验取值范围，生成脱敏后的审计属性；</li>
     *   <li>记录配置来源文件，并把当前实例发布为 {@link #current()} 的单例。</li>
     * </ol>
     *
     * @throws IllegalStateException 未取得 properties bean 时抛出
     * @throws GConfigException 任一配置项缺失、格式错误或超出允许范围时抛出
     */
    @PostConstruct
    public void initialize() {
        if (mergedProperties == null || mergedProperties.isEmpty()) {
            throw new IllegalStateException("GConfig 未取得 properties bean，请检查 xml/spring.xml");
        }
        properties.putAll(mergedProperties);
        for (GConfigKey.Section section : GConfigKey.Section.values()) {
            attributes.put(section, new GAttribute());
        }
        for (GConfigKey<?> key : GConfigKeys.all()) {
            get(key);
        }
        validateRanges();
        buildAuditAttributes();
        try {
            Object factory = applicationContext.getBean("&properties");
            if (factory instanceof GPropertiesFactoryBean) {
                loadedSources = Collections.unmodifiableList(
                        new ArrayList<>(((GPropertiesFactoryBean) factory).getLoadedResources()));
            }
        } catch (RuntimeException e) {
            loadedSources = Collections.emptyList();
        }
        current = this;
    }

    /**
     * 获得已初始化的配置中心实例
     *
     * @return 全局唯一的配置中心实例
     * @throws IllegalStateException 配置中心尚未初始化时抛出
     * @see #initialize()
     */
    public static GConfig current() {
        GConfig value = current;
        if (value == null) {
            throw new IllegalStateException("配置中心尚未初始化，请先执行 GLoadConfig.loadConfig()");
        }
        return value;
    }

    /**
     * 按已声明的配置键读取配置值
     *
     * <p>取值优先级：配置文件中的值 &gt; 键声明的默认值；两者都没有且键为必需项时抛异常，
     * 否则返回 {@code null}。</p>
     *
     * @param <T> 配置值的类型，由键本身决定
     * @param key 配置键定义，见 {@link GConfigKeys}
     * @return 转换后的配置值，可能为 {@code null}（见上文）
     * @throws GConfigException 键为必需项但未配置，或值无法转换成目标类型时抛出
     */
    public <T> T get(GConfigKey<T> key) {
        String raw = properties.getProperty(key.getName());
        if (raw == null) {
            if (key.getDefaultValue() != null) {
                return key.getDefaultValue();
            }
            if (key.isRequired()) {
                throw new GConfigException(key.getName(), "未配置");
            }
            return null;
        }
        return convert(key.getName(), raw, key.getType());
    }

    /**
     * 按配置项名称读取字符串值（必填）
     *
     * @param key 配置项名称，如 {@code sys.dragon.show}
     * @return 配置项原始字符串
     * @throws GConfigException 配置项不存在，或值中仍残留未解析的 {@code ${...}} 占位符时抛出
     */
    public String getString(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new GConfigException(key, "未配置");
        }
        ensureResolved(key, value);
        return value;
    }

    /**
     * 按配置项名称读取字符串值，缺失时返回默认值
     *
     * @param key 配置项名称
     * @param defaultValue 配置项不存在时返回的默认值
     * @return 配置项原始字符串，或 {@code defaultValue}
     * @throws GConfigException 值中残留未解析的 {@code ${...}} 占位符时抛出
     */
    public String getString(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        ensureResolved(key, value);
        return value;
    }

    /**
     * 按配置项名称读取整数值（必填）
     *
     * @param key 配置项名称
     * @return 整数值
     * @throws GConfigException 配置项不存在，或值不是合法整数时抛出
     */
    public int getInt(String key) {
        return convert(key, getString(key), Integer.class);
    }

    /**
     * 按配置项名称读取整数值，缺失时返回默认值
     *
     * @param key 配置项名称
     * @param defaultValue 配置项不存在时返回的默认值
     * @return 整数值或 {@code defaultValue}
     * @throws GConfigException 值不是合法整数时抛出
     */
    public int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        return value == null ? defaultValue : convert(key, value, Integer.class);
    }

    /**
     * 按配置项名称读取长整数值（必填）
     *
     * @param key 配置项名称
     * @return 长整数值
     * @throws GConfigException 配置项不存在，或值不是合法长整数时抛出
     */
    public long getLong(String key) {
        return convert(key, getString(key), Long.class);
    }

    /**
     * 按配置项名称读取长整数值，缺失时返回默认值
     *
     * @param key 配置项名称
     * @param defaultValue 配置项不存在时返回的默认值
     * @return 长整数值或 {@code defaultValue}
     * @throws GConfigException 值不是合法长整数时抛出
     */
    public long getLong(String key, long defaultValue) {
        String value = properties.getProperty(key);
        return value == null ? defaultValue : convert(key, value, Long.class);
    }

    /**
     * 按配置项名称读取布尔值（必填）
     *
     * @param key 配置项名称
     * @return 布尔值
     * @throws GConfigException 配置项不存在，或值不是 {@code true}/{@code false} 时抛出
     */
    public boolean getBoolean(String key) {
        return convert(key, getString(key), Boolean.class);
    }

    /**
     * 按配置项名称读取布尔值，缺失时返回默认值
     *
     * @param key 配置项名称
     * @param defaultValue 配置项不存在时返回的默认值
     * @return 布尔值或 {@code defaultValue}
     * @throws GConfigException 值不是 {@code true}/{@code false} 时抛出
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        return value == null ? defaultValue : convert(key, value, Boolean.class);
    }

    /**
     * 把配置项的原始字符串转换成目标类型
     *
     * <p>支持 {@link String}、{@link Integer}、{@link Long}、{@link Boolean} 四种类型；
     * 布尔值只接受 {@code true}/{@code false}（忽略大小写）。</p>
     *
     * @param <T> 目标类型
     * @param key 配置项名称，仅用于异常信息
     * @param raw 原始字符串
     * @param type 目标类型的 Class 对象
     * @return 转换后的值
     * @throws GConfigException 值中残留占位符、类型不受支持或格式非法时抛出
     */
    private <T> T convert(String key, String raw, Class<T> type) {
        ensureResolved(key, raw);
        try {
            Object value;
            if (String.class.equals(type)) {
                value = raw;
            } else if (Integer.class.equals(type)) {
                value = Integer.valueOf(raw.trim());
            } else if (Long.class.equals(type)) {
                value = Long.valueOf(raw.trim());
            } else if (Boolean.class.equals(type)) {
                String normalized = raw.trim().toLowerCase(Locale.ROOT);
                if (!"true".equals(normalized) && !"false".equals(normalized)) {
                    throw new IllegalArgumentException("仅允许 true 或 false");
                }
                value = Boolean.valueOf(normalized);
            } else {
                throw new IllegalArgumentException("不支持的配置类型 " + type.getName());
            }
            return type.cast(value);
        } catch (RuntimeException e) {
            if (e instanceof GConfigException) {
                throw e;
            }
            throw new GConfigException(key, "值格式错误，期望类型为 " + type.getSimpleName(), e);
        }
    }

    /**
     * 校验配置值中的占位符是否已全部解析
     *
     * <p>残留 {@code ${...}} 通常意味着属性源加载不完整，属于配置错误，应当立即暴露。</p>
     *
     * @param key 配置项名称，仅用于异常信息
     * @param value 配置值，允许为 {@code null}
     * @throws GConfigException 值中包含未解析的占位符时抛出
     */
    private static void ensureResolved(String key, String value) {
        if (value != null && value.contains("${")) {
            throw new GConfigException(key, "包含未解析的占位符");
        }
    }

    /**
     * 校验关键配置项的取值区间
     *
     * <p>覆盖测试输入方式、输入来源、起始行列、是否记录输入以及循环次数等
     * 会直接影响用例执行的配置项。</p>
     *
     * @throws GConfigException 任一配置项超出允许范围时抛出
     */
    private void validateRanges() {
        requireRange(GConfigKeys.Sys.TEST_INPUT_TYPE, 0, 2);
        requireRange(GConfigKeys.Sys.TEST_INPUT_SOURCE, 0, 1);
        requireMinimum(GConfigKeys.Sys.TEST_INPUT_BEGIN_ROW_INDEX, 1);
        requireMinimum(GConfigKeys.Sys.TEST_INPUT_BEGIN_COLUMN_INDEX, 1);
        requireMinimum(GConfigKeys.Sys.IS_LOGGED_INPUTS, 0);
        requireMinimum(GConfigKeys.Sys.LOOP_COURT, 1);
    }

    /**
     * 断言配置值落在闭区间内
     *
     * @param key 待校验的整数配置键
     * @param minimum 允许的最小值（含）
     * @param maximum 允许的最大值（含）
     * @throws GConfigException 取值超出 {@code [minimum, maximum]} 时抛出
     */
    private void requireRange(GConfigKey<Integer> key, int minimum, int maximum) {
        int value = get(key);
        if (value < minimum || value > maximum) {
            throw new GConfigException(key.getName(), "必须位于 " + minimum + " 到 " + maximum + " 之间");
        }
    }

    /**
     * 断言配置值不小于给定下界
     *
     * @param key 待校验的整数配置键
     * @param minimum 允许的最小值（含）
     * @throws GConfigException 取值小于 {@code minimum} 时抛出
     */
    private void requireMinimum(GConfigKey<Integer> key, int minimum) {
        int value = get(key);
        if (value < minimum) {
            throw new GConfigException(key.getName(), "不能小于 " + minimum);
        }
    }

    /**
     * 生成用于审计的配置属性表
     *
     * <p>按配置项名称排序后逐条写入对应分区的 {@link GAttribute}，键形如
     * {@code #描述 配置项名称}。密码、令牌、私钥等敏感项的值会被替换为掩码。</p>
     */
    private void buildAuditAttributes() {
        for (String keyName : new TreeSet<>(properties.stringPropertyNames())) {
            GConfigKey<?> definition = keyDefinitions.get(keyName);
            GConfigKey.Section section = definition == null ? inferSection(keyName) : definition.getSection();
            String description = definition == null ? keyName : definition.getDescription();
            String value = properties.getProperty(keyName, "");
            if ((definition != null && definition.isSensitive()) || isSensitiveKeyName(keyName)) {
                value = MASKED_VALUE;
            }
            attributes.get(section).putAttribute("\n#" + description + "\n" + keyName, value);
        }
    }

    /**
     * 根据配置项名称前缀推断所属分区
     *
     * <p>用于处理配置文件中存在、但未在 {@link GConfigKeys} 中声明的"额外配置项"。</p>
     *
     * @param key 配置项名称
     * @return 推断出的分区；无法识别时归入 {@link GConfigKey.Section#SYSTEM}
     */
    private GConfigKey.Section inferSection(String key) {
        if (key.startsWith("db.")) {
            return GConfigKey.Section.DB;
        }
        if (key.startsWith("smtp.")) {
            return GConfigKey.Section.SMTP;
        }
        if (key.startsWith("email.")) {
            return GConfigKey.Section.MAIL;
        }
        if (key.startsWith("sut.")) {
            return GConfigKey.Section.SUT;
        }
        if (key.startsWith("browser")) {
            return GConfigKey.Section.WEBDRIVER;
        }
        if (key.startsWith("tc.")) {
            return GConfigKey.Section.QUALITY;
        }
        return GConfigKey.Section.SYSTEM;
    }

    /**
     * 按名称判断配置项是否属于敏感信息
     *
     * <p>名称（忽略大小写）包含 {@code password}、{@code token}、{@code credential}、
     * {@code privatekey}，或以 {@code .pwd} 结尾时视为敏感项。</p>
     *
     * @param key 配置项名称
     * @return 敏感返回 {@code true}
     */
    private boolean isSensitiveKeyName(String key) {
        String normalized = key.toLowerCase(Locale.ROOT);
        return normalized.contains("password") || normalized.endsWith(".pwd")
                || normalized.contains("token") || normalized.contains("credential")
                || normalized.contains("privatekey");
    }

    /**
     * 获得指定分区的审计属性表
     *
     * @param section 配置分区
     * @return 该分区的只读审计视图，敏感值已掩码；分区不存在时返回 {@code null}
     */
    public GAttribute getAttribute(GConfigKey.Section section) {
        return attributes.get(section);
    }

    /**
     * 获得本次加载的配置文件来源列表
     *
     * @return 按加载顺序排列的配置来源路径（不可修改）
     */
    public List<String> getLoadedSources() {
        return loadedSources;
    }

    /**
     * 获得系统分区配置
     *
     * @return {@code sys.*} 配置项的强类型访问入口
     */
    public SysSection sys() {
        return sys;
    }

    /**
     * 获得数据库分区配置
     *
     * @return {@code db.*} 配置项的强类型访问入口
     */
    public DbSection db() {
        return db;
    }

    /**
     * 获得 SMTP 分区配置
     *
     * @return {@code smtp.*} 配置项的强类型访问入口
     */
    public SmtpSection smtp() {
        return smtp;
    }

    /**
     * 获得邮件分区配置
     *
     * @return {@code email.*} 配置项的强类型访问入口
     */
    public EmailSection email() {
        return email;
    }

    /**
     * 获得被测件分区配置
     *
     * @return {@code sut.*} 配置项的强类型访问入口
     */
    public SutSection sut() {
        return sut;
    }

    /**
     * 获得覆盖率分区配置
     *
     * @return {@code coverage.*} 配置项的强类型访问入口
     */
    public CoverageSection coverage() {
        return coverage;
    }

    /**
     * 获得质量评估分区配置
     *
     * @return {@code tc.*} 配置项的强类型访问入口
     */
    public QualitySection quality() {
        return quality;
    }

    /**
     * 获得浏览器分区配置
     *
     * @return {@code browser*} 配置项的强类型访问入口
     */
    public WebdriverSection webdriver() {
        return webdriver;
    }

    /**
     * 把配置键对应的值转为字符串
     *
     * @param key 配置键
     * @return 配置值的字符串形式；值为 {@code null} 时返回 {@code null}
     */
    private String text(GConfigKey<?> key) {
        Object value = get(key);
        return value == null ? null : String.valueOf(value);
    }

    public final class SysSection {
        /**
         *  获得配置项 {@link GConfigKeys.Sys#DRAGON_SHOW} 的值
         *
         *  @see GConfigKeys.Sys#DRAGON_SHOW
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getDragonShow() { return text(GConfigKeys.Sys.DRAGON_SHOW); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#TEST_INPUT_TYPE} 的值
         *
         *  @see GConfigKeys.Sys#TEST_INPUT_TYPE
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getTestInputType() { return text(GConfigKeys.Sys.TEST_INPUT_TYPE); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#TEST_INPUT_SOURCE} 的值
         *
         *  @see GConfigKeys.Sys#TEST_INPUT_SOURCE
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getTestInputSource() { return text(GConfigKeys.Sys.TEST_INPUT_SOURCE); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#TEST_INPUT_BEGIN_ROW_INDEX} 的值
         *
         *  @see GConfigKeys.Sys#TEST_INPUT_BEGIN_ROW_INDEX
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getTestInputBeginRowIndex() { return text(GConfigKeys.Sys.TEST_INPUT_BEGIN_ROW_INDEX); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#TEST_INPUT_BEGIN_COLUMN_INDEX} 的值
         *
         *  @see GConfigKeys.Sys#TEST_INPUT_BEGIN_COLUMN_INDEX
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getTestInputBeginColumnIndex() { return text(GConfigKeys.Sys.TEST_INPUT_BEGIN_COLUMN_INDEX); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#IS_LOGGED_INPUTS} 的值
         *
         *  @see GConfigKeys.Sys#IS_LOGGED_INPUTS
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getIsLoggedInputs() { return text(GConfigKeys.Sys.IS_LOGGED_INPUTS); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#IS_CHECK_ONLY} 的值
         *
         *  @see GConfigKeys.Sys#IS_CHECK_ONLY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getIsCheckOnly() { return text(GConfigKeys.Sys.IS_CHECK_ONLY); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#IS_AUTO_CHECK_REPORT} 的值
         *
         *  @see GConfigKeys.Sys#IS_AUTO_CHECK_REPORT
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getIsAutoCheckReport() { return text(GConfigKeys.Sys.IS_AUTO_CHECK_REPORT); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#DRIVER} 的值
         *
         *  @see GConfigKeys.Sys#DRIVER
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getDriver() { return get(GConfigKeys.Sys.DRIVER); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#TEST_CASE_TYPE} 的值
         *
         *  @see GConfigKeys.Sys#TEST_CASE_TYPE
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getTestCaseType() { return get(GConfigKeys.Sys.TEST_CASE_TYPE); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#LOOP_COURT} 的值
         *
         *  @see GConfigKeys.Sys#LOOP_COURT
         *  @return 配置项值；未配置时返回该配置项声明的默认值
         */
        public int getLoopCourt() { return get(GConfigKeys.Sys.LOOP_COURT); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#TIME_WAIT} 的值
         *
         *  @see GConfigKeys.Sys#TIME_WAIT
         *  @return 配置项值；未配置时返回该配置项声明的默认值
         */
        public int getTimeWait() { return get(GConfigKeys.Sys.TIME_WAIT); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#IS_BACKUP} 的值
         *
         *  @see GConfigKeys.Sys#IS_BACKUP
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getIsBackup() { return text(GConfigKeys.Sys.IS_BACKUP); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#SERVER_CONN_TYPE} 的值
         *
         *  @see GConfigKeys.Sys#SERVER_CONN_TYPE
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getServerConnType() { return text(GConfigKeys.Sys.SERVER_CONN_TYPE); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#SERVER_URL} 的值
         *
         *  @see GConfigKeys.Sys#SERVER_URL
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getServerUrl() { return get(GConfigKeys.Sys.SERVER_URL); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#SERVER_WWW} 的值
         *
         *  @see GConfigKeys.Sys#SERVER_WWW
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getServerWWW() { return get(GConfigKeys.Sys.SERVER_WWW); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#SERVER_IP} 的值
         *
         *  @see GConfigKeys.Sys#SERVER_IP
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getServerIp() { return get(GConfigKeys.Sys.SERVER_IP); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#SERVER_PORT} 的值
         *
         *  @see GConfigKeys.Sys#SERVER_PORT
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getServerPort() { return get(GConfigKeys.Sys.SERVER_PORT); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#SERVER_NAME} 的值
         *
         *  @see GConfigKeys.Sys#SERVER_NAME
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getServerName() { return get(GConfigKeys.Sys.SERVER_NAME); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#JKS_PATH} 的值
         *
         *  @see GConfigKeys.Sys#JKS_PATH
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getJKS_PATH() { return get(GConfigKeys.Sys.JKS_PATH); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#JKS_PWD} 的值
         *
         *  @see GConfigKeys.Sys#JKS_PWD
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getJKS_PWD() { return get(GConfigKeys.Sys.JKS_PWD); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#COMMUNICATION_USER_ALIAS} 的值
         *
         *  @see GConfigKeys.Sys#COMMUNICATION_USER_ALIAS
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getCommunicationUserALIAS() { return get(GConfigKeys.Sys.COMMUNICATION_USER_ALIAS); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#COMMUNICATION_ORG_ID} 的值
         *
         *  @see GConfigKeys.Sys#COMMUNICATION_ORG_ID
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getCommunicationOrgID() { return get(GConfigKeys.Sys.COMMUNICATION_ORG_ID); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#COMMUNICATION_USER_ID} 的值
         *
         *  @see GConfigKeys.Sys#COMMUNICATION_USER_ID
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getCommunicationUserID() { return get(GConfigKeys.Sys.COMMUNICATION_USER_ID); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#COMMUNICATION_IMG} 的值
         *
         *  @see GConfigKeys.Sys#COMMUNICATION_IMG
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getCommunicationImg() { return get(GConfigKeys.Sys.COMMUNICATION_IMG); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#COMMUNICATION_SEAL} 的值
         *
         *  @see GConfigKeys.Sys#COMMUNICATION_SEAL
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getCommunicationSeal() { return get(GConfigKeys.Sys.COMMUNICATION_SEAL); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#FEATURES_URL} 的值
         *
         *  @see GConfigKeys.Sys#FEATURES_URL
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getStrFeaturesURL() { return get(GConfigKeys.Sys.FEATURES_URL); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#FEATURES_PORT} 的值
         *
         *  @see GConfigKeys.Sys#FEATURES_PORT
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getStrFeaturesPort() { return get(GConfigKeys.Sys.FEATURES_PORT); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#REPORT_URL} 的值
         *
         *  @see GConfigKeys.Sys#REPORT_URL
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getStrReportURL() { return get(GConfigKeys.Sys.REPORT_URL); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#SPEND_TIMES_URL} 的值
         *
         *  @see GConfigKeys.Sys#SPEND_TIMES_URL
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getStrSpendTimesURL() { return get(GConfigKeys.Sys.SPEND_TIMES_URL); }
        /**
         *  获得配置项 {@link GConfigKeys.Sys#REPORT_URL_OLD} 的值
         *
         *  @see GConfigKeys.Sys#REPORT_URL_OLD
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getStrReportURLOLD() { return get(GConfigKeys.Sys.REPORT_URL_OLD); }
    }

    public final class DbSection {
        /**
         *  获得配置项 {@link GConfigKeys.Db#DRIVER_CLASS_NAME} 的值
         *
         *  @see GConfigKeys.Db#DRIVER_CLASS_NAME
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getDriverClassName() { return get(GConfigKeys.Db.DRIVER_CLASS_NAME); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#URL} 的值
         *
         *  @see GConfigKeys.Db#URL
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getUrl() { return get(GConfigKeys.Db.URL); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#USERNAME} 的值
         *
         *  @see GConfigKeys.Db#USERNAME
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getUsername() { return get(GConfigKeys.Db.USERNAME); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#PASSWORD} 的值
         *
         *  @see GConfigKeys.Db#PASSWORD
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getPassword() { return get(GConfigKeys.Db.PASSWORD); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#VALIDATION_QUERY} 的值
         *
         *  @see GConfigKeys.Db#VALIDATION_QUERY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getValidationQuery() { return get(GConfigKeys.Db.VALIDATION_QUERY); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#CONNECT_TIMEOUT_AND_READ_TIMEOUT} 的值
         *
         *  @see GConfigKeys.Db#CONNECT_TIMEOUT_AND_READ_TIMEOUT
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getConnectTimeoutAndReadTimeout() { return get(GConfigKeys.Db.CONNECT_TIMEOUT_AND_READ_TIMEOUT); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#INITIAL_SIZE} 的值
         *
         *  @see GConfigKeys.Db#INITIAL_SIZE
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getInitialSize() { return get(GConfigKeys.Db.INITIAL_SIZE); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#MIN_IDLE} 的值
         *
         *  @see GConfigKeys.Db#MIN_IDLE
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getMinIdle() { return get(GConfigKeys.Db.MIN_IDLE); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#MAX_IDLE} 的值
         *
         *  @see GConfigKeys.Db#MAX_IDLE
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getMaxIdle() { return get(GConfigKeys.Db.MAX_IDLE); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#MAX_ACTIVE} 的值
         *
         *  @see GConfigKeys.Db#MAX_ACTIVE
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getMaxActive() { return get(GConfigKeys.Db.MAX_ACTIVE); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#MAX_WAIT} 的值
         *
         *  @see GConfigKeys.Db#MAX_WAIT
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getMaxWait() { return get(GConfigKeys.Db.MAX_WAIT); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#DEFAULT_AUTO_COMMIT} 的值
         *
         *  @see GConfigKeys.Db#DEFAULT_AUTO_COMMIT
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getDefaultAutoCommit() { return get(GConfigKeys.Db.DEFAULT_AUTO_COMMIT); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#REMOVE_ABANDONED} 的值
         *
         *  @see GConfigKeys.Db#REMOVE_ABANDONED
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getRemoveAbandoned() { return get(GConfigKeys.Db.REMOVE_ABANDONED); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#REMOVE_ABANDONED_TIMEOUT} 的值
         *
         *  @see GConfigKeys.Db#REMOVE_ABANDONED_TIMEOUT
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getRemoveAbandonedTimeout() { return get(GConfigKeys.Db.REMOVE_ABANDONED_TIMEOUT); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#TEST_WHILE_IDLE} 的值
         *
         *  @see GConfigKeys.Db#TEST_WHILE_IDLE
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getTestWhileIdle() { return get(GConfigKeys.Db.TEST_WHILE_IDLE); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#TIME_BETWEEN_EVICTION_RUNS_MILLIS} 的值
         *
         *  @see GConfigKeys.Db#TIME_BETWEEN_EVICTION_RUNS_MILLIS
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getTimeBetweenEvictionRunsMillis() { return get(GConfigKeys.Db.TIME_BETWEEN_EVICTION_RUNS_MILLIS); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#MIN_EVICTABLE_IDLE_TIME_MILLIS} 的值
         *
         *  @see GConfigKeys.Db#MIN_EVICTABLE_IDLE_TIME_MILLIS
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getMinEvictableIdleTimeMillis() { return get(GConfigKeys.Db.MIN_EVICTABLE_IDLE_TIME_MILLIS); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#NUM_TESTS_PER_EVICTION_RUN} 的值
         *
         *  @see GConfigKeys.Db#NUM_TESTS_PER_EVICTION_RUN
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getNumTestsPerEvictionRun() { return get(GConfigKeys.Db.NUM_TESTS_PER_EVICTION_RUN); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#TEST_ON_RETURN} 的值
         *
         *  @see GConfigKeys.Db#TEST_ON_RETURN
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getTestOnReturn() { return get(GConfigKeys.Db.TEST_ON_RETURN); }
        /**
         *  获得配置项 {@link GConfigKeys.Db#TEST_ON_BORROW} 的值
         *
         *  @see GConfigKeys.Db#TEST_ON_BORROW
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getTestOnBorrow() { return get(GConfigKeys.Db.TEST_ON_BORROW); }
    }

    public final class SmtpSection {
        /**
         *  获得配置项 {@link GConfigKeys.Smtp#IS_MAIL_ON} 的值
         *
         *  @see GConfigKeys.Smtp#IS_MAIL_ON
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getIsMailOn() { return text(GConfigKeys.Smtp.IS_MAIL_ON); }
        /**
         *  获得配置项 {@link GConfigKeys.Smtp#PROTOCOL} 的值
         *
         *  @see GConfigKeys.Smtp#PROTOCOL
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getProtocol() { return get(GConfigKeys.Smtp.PROTOCOL); }
        /**
         *  获得配置项 {@link GConfigKeys.Smtp#HOST} 的值
         *
         *  @see GConfigKeys.Smtp#HOST
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getHost() { return get(GConfigKeys.Smtp.HOST); }
        /**
         *  获得配置项 {@link GConfigKeys.Smtp#SUFFIX} 的值
         *
         *  @see GConfigKeys.Smtp#SUFFIX
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getSuffix() { return get(GConfigKeys.Smtp.SUFFIX); }
        /**
         *  获得配置项 {@link GConfigKeys.Smtp#ACCOUNT} 的值
         *
         *  @see GConfigKeys.Smtp#ACCOUNT
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getAccount() { return get(GConfigKeys.Smtp.ACCOUNT); }
        /**
         *  获得配置项 {@link GConfigKeys.Smtp#PASSWORD} 的值
         *
         *  @see GConfigKeys.Smtp#PASSWORD
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getPassword() { return get(GConfigKeys.Smtp.PASSWORD); }
        /**
         *  获得配置项 {@link GConfigKeys.Smtp#PORT} 的值
         *
         *  @see GConfigKeys.Smtp#PORT
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getPort() { return get(GConfigKeys.Smtp.PORT); }
        /**
         *  获得配置项 {@link GConfigKeys.Smtp#AUTH} 的值
         *
         *  @see GConfigKeys.Smtp#AUTH
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getAuth() { return get(GConfigKeys.Smtp.AUTH); }
        /**
         *  获得配置项 {@link GConfigKeys.Smtp#TLS} 的值
         *
         *  @see GConfigKeys.Smtp#TLS
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getTls() { return get(GConfigKeys.Smtp.TLS); }
        /**
         *  获得配置项 {@link GConfigKeys.Smtp#DEBUG} 的值
         *
         *  @see GConfigKeys.Smtp#DEBUG
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getDebug() { return get(GConfigKeys.Smtp.DEBUG); }
    }

    public final class EmailSection {
        /**
         *  获得配置项 {@link GConfigKeys.Email#IS_USED} 的值
         *
         *  @see GConfigKeys.Email#IS_USED
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getIsUsed() { return text(GConfigKeys.Email.IS_USED); }
        /**
         *  获得配置项 {@link GConfigKeys.Email#DEV} 的值
         *
         *  @see GConfigKeys.Email#DEV
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getDev() { return get(GConfigKeys.Email.DEV); }
        /**
         *  获得配置项 {@link GConfigKeys.Email#TEST} 的值
         *
         *  @see GConfigKeys.Email#TEST
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getTest() { return get(GConfigKeys.Email.TEST); }
        /**
         *  获得配置项 {@link GConfigKeys.Email#MANAGER} 的值
         *
         *  @see GConfigKeys.Email#MANAGER
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getManager() { return get(GConfigKeys.Email.MANAGER); }
        /**
         *  获得配置项 {@link GConfigKeys.Email#RESEARCHER} 的值
         *
         *  @see GConfigKeys.Email#RESEARCHER
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getResearcher() { return get(GConfigKeys.Email.RESEARCHER); }
        /**
         *  获得配置项 {@link GConfigKeys.Email#MAIN_SEND} 的值
         *
         *  @see GConfigKeys.Email#MAIN_SEND
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getMainsend() { return get(GConfigKeys.Email.MAIN_SEND); }
        /**
         *  获得配置项 {@link GConfigKeys.Email#COPY_SEND} 的值
         *
         *  @see GConfigKeys.Email#COPY_SEND
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getCopysend() { return get(GConfigKeys.Email.COPY_SEND); }
        /**
         *  获得配置项 {@link GConfigKeys.Email#SECRET_SEND} 的值
         *
         *  @see GConfigKeys.Email#SECRET_SEND
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getSecretsend() { return get(GConfigKeys.Email.SECRET_SEND); }
    }

    public final class SutSection {
        /**
         *  获得配置项 {@link GConfigKeys.Sut#SYS_NAME} 的值
         *
         *  @see GConfigKeys.Sut#SYS_NAME
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getSysName() { return get(GConfigKeys.Sut.SYS_NAME); }
        /**
         *  获得配置项 {@link GConfigKeys.Sut#SECTION_NAME_1} 的值
         *
         *  @see GConfigKeys.Sut#SECTION_NAME_1
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getSectionName1() { return get(GConfigKeys.Sut.SECTION_NAME_1); }
        /**
         *  获得配置项 {@link GConfigKeys.Sut#SECTION_NAME_2} 的值
         *
         *  @see GConfigKeys.Sut#SECTION_NAME_2
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getSectionName2() { return get(GConfigKeys.Sut.SECTION_NAME_2); }
        /**
         *  获得配置项 {@link GConfigKeys.Sut#SECTION_NAME_3} 的值
         *
         *  @see GConfigKeys.Sut#SECTION_NAME_3
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getSectionName3() { return get(GConfigKeys.Sut.SECTION_NAME_3); }
        /**
         *  获得配置项 {@link GConfigKeys.Sut#SID} 的值
         *
         *  @see GConfigKeys.Sut#SID
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getSid() { return get(GConfigKeys.Sut.SID); }
    }

    public final class CoverageSection {
        /**
         *  获得配置项 {@link GConfigKeys.Coverage#BILL_TOTAL} 的值
         *
         *  @see GConfigKeys.Coverage#BILL_TOTAL
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBillTotal() { return text(GConfigKeys.Coverage.BILL_TOTAL); }
        /**
         *  获得配置项 {@link GConfigKeys.Coverage#FLOW_TOTAL} 的值
         *
         *  @see GConfigKeys.Coverage#FLOW_TOTAL
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getFlowTotal() { return text(GConfigKeys.Coverage.FLOW_TOTAL); }
        /**
         *  获得配置项 {@link GConfigKeys.Coverage#ALGO_TOTAL} 的值
         *
         *  @see GConfigKeys.Coverage#ALGO_TOTAL
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getAlgoTotal() { return text(GConfigKeys.Coverage.ALGO_TOTAL); }
        /**
         *  获得配置项 {@link GConfigKeys.Coverage#URL} 的值
         *
         *  @see GConfigKeys.Coverage#URL
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getCoverageURL() { return get(GConfigKeys.Coverage.URL); }
    }

    public final class QualitySection {
        /**
         *  获得配置项 {@link GConfigKeys.Quality#FUNCTIONALITY_FLEXIBILITY} 的值
         *
         *  @see GConfigKeys.Quality#FUNCTIONALITY_FLEXIBILITY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_FUNCTIONALITY_FLEXIBILITY() { return text(GConfigKeys.Quality.FUNCTIONALITY_FLEXIBILITY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#FUNCTIONALITY_ACCURACY} 的值
         *
         *  @see GConfigKeys.Quality#FUNCTIONALITY_ACCURACY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_FUNCTIONALITY_ACCURACY() { return text(GConfigKeys.Quality.FUNCTIONALITY_ACCURACY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#FUNCTIONALITY_INTERACTIVITY} 的值
         *
         *  @see GConfigKeys.Quality#FUNCTIONALITY_INTERACTIVITY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_FUNCTIONALITY_INTERACTIVITY() { return text(GConfigKeys.Quality.FUNCTIONALITY_INTERACTIVITY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#FUNCTIONALITY_SECURITY} 的值
         *
         *  @see GConfigKeys.Quality#FUNCTIONALITY_SECURITY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_FUNCTIONALITY_SECURITY() { return text(GConfigKeys.Quality.FUNCTIONALITY_SECURITY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#FUNCTIONALITY_REGULATORY} 的值
         *
         *  @see GConfigKeys.Quality#FUNCTIONALITY_REGULATORY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_FUNCTIONALITY_REGULATORY() { return text(GConfigKeys.Quality.FUNCTIONALITY_REGULATORY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#RELIABILITY_MATURITY} 的值
         *
         *  @see GConfigKeys.Quality#RELIABILITY_MATURITY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_RELIABILITY_MATURITY() { return text(GConfigKeys.Quality.RELIABILITY_MATURITY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#RELIABILITY_FAULTTOLERANCE} 的值
         *
         *  @see GConfigKeys.Quality#RELIABILITY_FAULTTOLERANCE
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_RELIABILITY_FAULTTOLERANCE() { return text(GConfigKeys.Quality.RELIABILITY_FAULTTOLERANCE); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#RELIABILITY_RECOVERABILITY} 的值
         *
         *  @see GConfigKeys.Quality#RELIABILITY_RECOVERABILITY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_RELIABILITY_RECOVERABILITY() { return text(GConfigKeys.Quality.RELIABILITY_RECOVERABILITY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#RELIABILITY_REGULATORY} 的值
         *
         *  @see GConfigKeys.Quality#RELIABILITY_REGULATORY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_RELIABILITY_REGULATORY() { return text(GConfigKeys.Quality.RELIABILITY_REGULATORY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#USABILITY_COMPREHENSIBILITY} 的值
         *
         *  @see GConfigKeys.Quality#USABILITY_COMPREHENSIBILITY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_USABILITY_COMPREHENSIBILITY() { return text(GConfigKeys.Quality.USABILITY_COMPREHENSIBILITY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#USABILITY_EASYTOLEARN} 的值
         *
         *  @see GConfigKeys.Quality#USABILITY_EASYTOLEARN
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_USABILITY_EASYTOLEARN() { return text(GConfigKeys.Quality.USABILITY_EASYTOLEARN); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#USABILITY_OPERABILITY} 的值
         *
         *  @see GConfigKeys.Quality#USABILITY_OPERABILITY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_USABILITY_OPERABILITY() { return text(GConfigKeys.Quality.USABILITY_OPERABILITY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#USABILITY_STICKINESS} 的值
         *
         *  @see GConfigKeys.Quality#USABILITY_STICKINESS
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_USABILITY_STICKINESS() { return text(GConfigKeys.Quality.USABILITY_STICKINESS); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#USABILITY_REGULATORY} 的值
         *
         *  @see GConfigKeys.Quality#USABILITY_REGULATORY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_USABILITY_REGULATORY() { return text(GConfigKeys.Quality.USABILITY_REGULATORY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#EFFICIENCY_TIMING} 的值
         *
         *  @see GConfigKeys.Quality#EFFICIENCY_TIMING
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_EFFICIENCY_TIMING() { return text(GConfigKeys.Quality.EFFICIENCY_TIMING); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#EFFICIENCY_UTILIZATION} 的值
         *
         *  @see GConfigKeys.Quality#EFFICIENCY_UTILIZATION
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_EFFICIENCY_UTILIZATION() { return text(GConfigKeys.Quality.EFFICIENCY_UTILIZATION); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#EFFICIENCY_REGULATORY} 的值
         *
         *  @see GConfigKeys.Quality#EFFICIENCY_REGULATORY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_EFFICIENCY_REGULATORY() { return text(GConfigKeys.Quality.EFFICIENCY_REGULATORY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#MAINTAINABILITY_ANALYZABILITY} 的值
         *
         *  @see GConfigKeys.Quality#MAINTAINABILITY_ANALYZABILITY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_MAINTAINABILITY_ANALYZABILITY() { return text(GConfigKeys.Quality.MAINTAINABILITY_ANALYZABILITY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#MAINTAINABILITY_STABILITY} 的值
         *
         *  @see GConfigKeys.Quality#MAINTAINABILITY_STABILITY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_MAINTAINABILITY_STABILITY() { return text(GConfigKeys.Quality.MAINTAINABILITY_STABILITY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#MAINTAINABILITY_CHANGEABILITY} 的值
         *
         *  @see GConfigKeys.Quality#MAINTAINABILITY_CHANGEABILITY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_MAINTAINABILITY_CHANGEABILITY() { return text(GConfigKeys.Quality.MAINTAINABILITY_CHANGEABILITY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#MAINTAINABILITY_TESTABILITY} 的值
         *
         *  @see GConfigKeys.Quality#MAINTAINABILITY_TESTABILITY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_MAINTAINABILITY_TESTABILITY() { return text(GConfigKeys.Quality.MAINTAINABILITY_TESTABILITY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#MAINTAINABILITY_REGULATORY} 的值
         *
         *  @see GConfigKeys.Quality#MAINTAINABILITY_REGULATORY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_MAINTAINABILITY_REGULATORY() { return text(GConfigKeys.Quality.MAINTAINABILITY_REGULATORY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#PORTABILITY_ADAPTABILITY} 的值
         *
         *  @see GConfigKeys.Quality#PORTABILITY_ADAPTABILITY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_PORTABILITY_ADAPTABILITY() { return text(GConfigKeys.Quality.PORTABILITY_ADAPTABILITY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#PORTABILITY_INSTALLATION} 的值
         *
         *  @see GConfigKeys.Quality#PORTABILITY_INSTALLATION
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_PORTABILITY_INSTALLATION() { return text(GConfigKeys.Quality.PORTABILITY_INSTALLATION); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#PORTABILITY_COEXISTENCE} 的值
         *
         *  @see GConfigKeys.Quality#PORTABILITY_COEXISTENCE
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_PORTABILITY_COEXISTENCE() { return text(GConfigKeys.Quality.PORTABILITY_COEXISTENCE); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#PORTABILITY_REPLACEABILITY} 的值
         *
         *  @see GConfigKeys.Quality#PORTABILITY_REPLACEABILITY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_PORTABILITY_REPLACEABILITY() { return text(GConfigKeys.Quality.PORTABILITY_REPLACEABILITY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#PORTABILITY_REGULATORY} 的值
         *
         *  @see GConfigKeys.Quality#PORTABILITY_REGULATORY
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String get_PORTABILITY_REGULATORY() { return text(GConfigKeys.Quality.PORTABILITY_REGULATORY); }
        /**
         *  获得配置项 {@link GConfigKeys.Quality#URL} 的值
         *
         *  @see GConfigKeys.Quality#URL
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getTC_QUALITY_URL() { return get(GConfigKeys.Quality.URL); }
    }

    public final class WebdriverSection {
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#OS} 的值
         *
         *  @see GConfigKeys.Webdriver#OS
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserOS() { return get(GConfigKeys.Webdriver.OS); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#INSTALL_PATH} 的值
         *
         *  @see GConfigKeys.Webdriver#INSTALL_PATH
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserInstallPath() { return get(GConfigKeys.Webdriver.INSTALL_PATH); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#APPLICATION} 的值
         *
         *  @see GConfigKeys.Webdriver#APPLICATION
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserApplication() { return get(GConfigKeys.Webdriver.APPLICATION); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#HEADLESS} 的值
         *
         *  @see GConfigKeys.Webdriver#HEADLESS
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserHeadless() { return text(GConfigKeys.Webdriver.HEADLESS); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#DRIVER_UPDATE} 的值
         *
         *  @see GConfigKeys.Webdriver#DRIVER_UPDATE
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserDriverUpdate() { return get(GConfigKeys.Webdriver.DRIVER_UPDATE); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#DRIVER_TYPE} 的值
         *
         *  @see GConfigKeys.Webdriver#DRIVER_TYPE
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserDriverType() { return get(GConfigKeys.Webdriver.DRIVER_TYPE); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#DRIVER_DOWNLOAD_URL} 的值
         *
         *  @see GConfigKeys.Webdriver#DRIVER_DOWNLOAD_URL
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserDriverDownloadUrl() { return get(GConfigKeys.Webdriver.DRIVER_DOWNLOAD_URL); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#DRIVER_VERSION} 的值
         *
         *  @see GConfigKeys.Webdriver#DRIVER_VERSION
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserDriverVerision() { return get(GConfigKeys.Webdriver.DRIVER_VERSION); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#DRIVER_FILE_NAME} 的值
         *
         *  @see GConfigKeys.Webdriver#DRIVER_FILE_NAME
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserDriverFileName() { return get(GConfigKeys.Webdriver.DRIVER_FILE_NAME); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#DRIVER_FILE_FORMAT} 的值
         *
         *  @see GConfigKeys.Webdriver#DRIVER_FILE_FORMAT
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserDriverFileFormat() { return get(GConfigKeys.Webdriver.DRIVER_FILE_FORMAT); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#DRIVER_SAVE_PATH} 的值
         *
         *  @see GConfigKeys.Webdriver#DRIVER_SAVE_PATH
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserDriverSavePath() { return get(GConfigKeys.Webdriver.DRIVER_SAVE_PATH); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#DRIVER_SAVE_NAME} 的值
         *
         *  @see GConfigKeys.Webdriver#DRIVER_SAVE_NAME
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserDriverSaveName() { return get(GConfigKeys.Webdriver.DRIVER_SAVE_NAME); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#DRIVER_DIR_NAME} 的值
         *
         *  @see GConfigKeys.Webdriver#DRIVER_DIR_NAME
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserDriverDirName() { return get(GConfigKeys.Webdriver.DRIVER_DIR_NAME); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#LIB_PATH} 的值
         *
         *  @see GConfigKeys.Webdriver#LIB_PATH
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserlibPath() { return get(GConfigKeys.Webdriver.LIB_PATH); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#DRIVER_BIN} 的值
         *
         *  @see GConfigKeys.Webdriver#DRIVER_BIN
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserDriverbin() { return get(GConfigKeys.Webdriver.DRIVER_BIN); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#DRIVER_LIB_NAME} 的值
         *
         *  @see GConfigKeys.Webdriver#DRIVER_LIB_NAME
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserDriverLibName() { return get(GConfigKeys.Webdriver.DRIVER_LIB_NAME); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#DRIVER_PROCESS_NAME} 的值
         *
         *  @see GConfigKeys.Webdriver#DRIVER_PROCESS_NAME
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserDriverProcessName() { return get(GConfigKeys.Webdriver.DRIVER_PROCESS_NAME); }
        /**
         *  获得配置项 {@link GConfigKeys.Webdriver#LOG_TYPE} 的值
         *
         *  @see GConfigKeys.Webdriver#LOG_TYPE
         *  @return 配置项值；未配置且该配置项未声明默认值时返回 {@code null}
         */
        public String getBrowserLogType() { return get(GConfigKeys.Webdriver.LOG_TYPE); }
    }
}
