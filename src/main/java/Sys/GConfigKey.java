package Sys;

/**
 * 配置项元数据。
 *
 * @param <T> 配置值类型
 */
public final class GConfigKey<T> {

    /**
     * 配置分区
     *
     * <p>与 {@code GConfig} 中的 Section 内部类一一对应，决定审计属性表按哪个分区归类。</p>
     */
    public enum Section {
        /** 系统运行参数（{@code sys.*}） */
        SYSTEM,
        /** 数据库连接参数（{@code db.*}） */
        DB,
        /** SMTP 发送参数（{@code smtp.*}） */
        SMTP,
        /** 邮件收发参数（{@code email.*}） */
        MAIL,
        /** 被测件版本参数（{@code sut.*}） */
        SUT,
        /** 浏览器与驱动参数（{@code browser*}） */
        WEBDRIVER,
        /** 覆盖率采集参数（{@code coverage.*}） */
        COVERAGE,
        /** 质量评估参数（{@code tc.*}） */
        QUALITY
    }

    private final String name;
    private final String description;
    private final Section section;
    private final Class<T> type;
    private final T defaultValue;
    private final boolean required;
    private final boolean sensitive;

    private GConfigKey(String name, String description, Section section, Class<T> type,
                       T defaultValue, boolean required, boolean sensitive) {
        this.name = name;
        this.description = description;
        this.section = section;
        this.type = type;
        this.defaultValue = defaultValue;
        this.required = required;
        this.sensitive = sensitive;
    }

    /**
     * 定义一个字符串类型的配置键
     *
     * @param name 配置项名称，需与配置文件中的键完全一致
     * @param description 配置项说明，用于审计与报错提示
     * @param section 所属分区
     * @param defaultValue 默认值，允许为 {@code null}
     * @param required 是否为必需项；为 {@code true} 时缺失将抛出 {@link GConfigException}
     * @param sensitive 是否为敏感项；为 {@code true} 时审计输出会掩码
     * @return 新建的配置键
     */
    public static GConfigKey<String> stringKey(String name, String description, Section section,
                                                String defaultValue, boolean required, boolean sensitive) {
        return new GConfigKey<>(name, description, section, String.class, defaultValue, required, sensitive);
    }

    /**
     * 定义一个整数类型的配置键（非敏感）
     *
     * @param name 配置项名称
     * @param description 配置项说明
     * @param section 所属分区
     * @param defaultValue 默认值，允许为 {@code null}
     * @param required 是否为必需项
     * @return 新建的配置键
     */
    public static GConfigKey<Integer> intKey(String name, String description, Section section,
                                              Integer defaultValue, boolean required) {
        return new GConfigKey<>(name, description, section, Integer.class, defaultValue, required, false);
    }

    /**
     * 定义一个长整数类型的配置键（非敏感）
     *
     * @param name 配置项名称
     * @param description 配置项说明
     * @param section 所属分区
     * @param defaultValue 默认值，允许为 {@code null}
     * @param required 是否为必需项
     * @return 新建的配置键
     */
    public static GConfigKey<Long> longKey(String name, String description, Section section,
                                            Long defaultValue, boolean required) {
        return new GConfigKey<>(name, description, section, Long.class, defaultValue, required, false);
    }

    /**
     * 定义一个布尔类型的配置键（非敏感）
     *
     * @param name 配置项名称
     * @param description 配置项说明
     * @param section 所属分区
     * @param defaultValue 默认值，允许为 {@code null}
     * @param required 是否为必需项
     * @return 新建的配置键
     */
    public static GConfigKey<Boolean> booleanKey(String name, String description, Section section,
                                                  Boolean defaultValue, boolean required) {
        return new GConfigKey<>(name, description, section, Boolean.class, defaultValue, required, false);
    }

    /**
     * 获得配置项名称
     *
     * @return 配置项名称，如 {@code sys.DragonShow}
     */
    public String getName() {
        return name;
    }

    /**
     * 获得配置项说明
     *
     * @return 用于审计展示与异常提示的中文说明
     */
    public String getDescription() {
        return description;
    }

    /**
     * 获得配置项所属分区
     *
     * @return 配置分区
     */
    public Section getSection() {
        return section;
    }

    /**
     * 获得配置值类型
     *
     * @return 类型 Class 对象，仅可能是 {@link String}、{@link Integer}、{@link Long}、{@link Boolean}
     */
    public Class<T> getType() {
        return type;
    }

    /**
     * 获得配置项默认值
     *
     * @return 默认值；未声明默认值时返回 {@code null}
     */
    public T getDefaultValue() {
        return defaultValue;
    }

    /**
     * 是否为必需项
     *
     * @return 缺失即报错返回 {@code true}
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * 是否为敏感项
     *
     * @return 审计输出时需要掩码返回 {@code true}
     */
    public boolean isSensitive() {
        return sensitive;
    }
}
