package Sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static Sys.GConfigKey.Section;

/**
 * 项目内置配置项定义。新增配置项时只需在对应分区声明一次。
 */
public final class GConfigKeys {

    private GConfigKeys() {
    }

    /**
     * 声明一个必需的字符串配置项
     *
     * @param name 配置项名称，同时作为说明文本
     * @param section 所属分区
     * @return 新建的配置键
     */
    private static GConfigKey<String> string(String name, Section section) {
        return GConfigKey.stringKey(name, name, section, null, true, false);
    }

    /**
     * 声明一个必需的敏感字符串配置项（密码、令牌等）
     *
     * @param name 配置项名称，同时作为说明文本
     * @param section 所属分区
     * @return 新建的配置键，其在审计输出中会被掩码
     */
    private static GConfigKey<String> sensitive(String name, Section section) {
        return GConfigKey.stringKey(name, name, section, null, true, true);
    }

    /**
     * 声明一个必需的整数配置项
     *
     * @param name 配置项名称，同时作为说明文本
     * @param section 所属分区
     * @return 新建的配置键
     */
    private static GConfigKey<Integer> integer(String name, Section section) {
        return GConfigKey.intKey(name, name, section, null, true);
    }

    /**
     * 声明一个必需的布尔配置项
     *
     * @param name 配置项名称，同时作为说明文本
     * @param section 所属分区
     * @return 新建的配置键
     */
    private static GConfigKey<Boolean> bool(String name, Section section) {
        return GConfigKey.booleanKey(name, name, section, null, true);
    }

    public static final class Sys {
        public static final GConfigKey<Boolean> DRAGON_SHOW = bool("sys.DragonShow", Section.SYSTEM);
        public static final GConfigKey<Boolean> DRAGON_WELCOME = GConfigKey.booleanKey(
                "sys.DragonWelcome", "sys.DragonWelcome", Section.SYSTEM, Boolean.TRUE, false);
        public static final GConfigKey<Boolean> DRAGON_BYE = GConfigKey.booleanKey(
                "sys.DragonBye", "sys.DragonBye", Section.SYSTEM, Boolean.TRUE, false);
        public static final GConfigKey<Integer> TEST_INPUT_TYPE = integer("sys.TestInputType", Section.SYSTEM);
        public static final GConfigKey<Integer> TEST_INPUT_SOURCE = integer("sys.TestInputSource", Section.SYSTEM);
        public static final GConfigKey<Integer> TEST_INPUT_BEGIN_ROW_INDEX = integer("sys.TestInputBeginRowIndex", Section.SYSTEM);
        public static final GConfigKey<Integer> TEST_INPUT_BEGIN_COLUMN_INDEX = integer("sys.TestInputBeginColumnIndex", Section.SYSTEM);
        public static final GConfigKey<Integer> IS_LOGGED_INPUTS = integer("sys.IsLoggedInputs", Section.SYSTEM);
        public static final GConfigKey<Boolean> IS_CHECK_ONLY = bool("sys.IsCheckOnly", Section.SYSTEM);
        public static final GConfigKey<Boolean> IS_AUTO_CHECK_REPORT = bool("sys.IsAutoCheckReport", Section.SYSTEM);
        public static final GConfigKey<String> DRIVER = string("sys.Driver", Section.SYSTEM);
        public static final GConfigKey<String> TEST_CASE_TYPE = string("sys.TestCaseType", Section.SYSTEM);
        public static final GConfigKey<Integer> LOOP_COURT = integer("sys.LoopCourt", Section.SYSTEM);
        public static final GConfigKey<Integer> TIME_WAIT = integer("sys.TimeWait", Section.SYSTEM);
        public static final GConfigKey<Boolean> IS_BACKUP = bool("sys.IsBackup", Section.SYSTEM);
        public static final GConfigKey<Integer> SERVER_CONN_TYPE = integer("sys.ServerConnType", Section.SYSTEM);
        public static final GConfigKey<String> SERVER_URL = string("sys.ServerUrl", Section.SYSTEM);
        public static final GConfigKey<String> SERVER_WWW = string("sys.ServerWWW", Section.SYSTEM);
        public static final GConfigKey<String> SERVER_IP = string("sys.ServerIp", Section.SYSTEM);
        public static final GConfigKey<String> SERVER_PORT = string("sys.ServerPort", Section.SYSTEM);
        public static final GConfigKey<String> SERVER_NAME = string("sys.ServerName", Section.SYSTEM);
        public static final GConfigKey<String> JKS_PATH = string("sys.JKS_PATH", Section.SYSTEM);
        public static final GConfigKey<String> JKS_PWD = sensitive("sys.JKS_PWD", Section.SYSTEM);
        public static final GConfigKey<String> COMMUNICATION_USER_ALIAS = string("sys.CommunicationUserALIAS", Section.SYSTEM);
        public static final GConfigKey<String> COMMUNICATION_ORG_ID = string("sys.CommunicationOrgID", Section.SYSTEM);
        public static final GConfigKey<String> COMMUNICATION_USER_ID = string("sys.CommunicationUserID", Section.SYSTEM);
        public static final GConfigKey<String> COMMUNICATION_IMG = string("sys.CommunicationImg", Section.SYSTEM);
        public static final GConfigKey<String> COMMUNICATION_SEAL = string("sys.CommunicationSeal", Section.SYSTEM);
        public static final GConfigKey<String> FEATURES_URL = string("sys.featuresURL", Section.SYSTEM);
        public static final GConfigKey<String> FEATURES_PORT = string("sys.featuresPort", Section.SYSTEM);
        public static final GConfigKey<String> REPORT_URL = string("sys.ReportURL", Section.SYSTEM);
        public static final GConfigKey<String> SPEND_TIMES_URL = string("sys.SpendTimesURL", Section.SYSTEM);
        public static final GConfigKey<String> REPORT_URL_OLD = string("sys.ReportURLOLD", Section.SYSTEM);

        private static final List<GConfigKey<?>> ALL = list(
                DRAGON_SHOW, DRAGON_WELCOME, DRAGON_BYE, TEST_INPUT_TYPE, TEST_INPUT_SOURCE, TEST_INPUT_BEGIN_ROW_INDEX,
                TEST_INPUT_BEGIN_COLUMN_INDEX, IS_LOGGED_INPUTS, IS_CHECK_ONLY, IS_AUTO_CHECK_REPORT,
                DRIVER, TEST_CASE_TYPE, LOOP_COURT, TIME_WAIT, IS_BACKUP, SERVER_CONN_TYPE,
                SERVER_URL, SERVER_WWW, SERVER_IP, SERVER_PORT, SERVER_NAME, JKS_PATH, JKS_PWD,
                COMMUNICATION_USER_ALIAS, COMMUNICATION_ORG_ID, COMMUNICATION_USER_ID,
                COMMUNICATION_IMG, COMMUNICATION_SEAL, FEATURES_URL, FEATURES_PORT,
                REPORT_URL, SPEND_TIMES_URL, REPORT_URL_OLD);

        private Sys() {
        }
    }

    public static final class Db {
        public static final GConfigKey<String> DRIVER_CLASS_NAME = string("db.driverClassName", Section.DB);
        public static final GConfigKey<String> URL = string("db.url", Section.DB);
        public static final GConfigKey<String> USERNAME = string("db.username", Section.DB);
        public static final GConfigKey<String> PASSWORD = sensitive("db.password", Section.DB);
        public static final GConfigKey<String> VALIDATION_QUERY = string("db.validationQuery", Section.DB);
        public static final GConfigKey<String> CONNECT_TIMEOUT_AND_READ_TIMEOUT = string("db.connectTimeoutAndReadTimeout", Section.DB);
        public static final GConfigKey<String> INITIAL_SIZE = string("db.initialSize", Section.DB);
        public static final GConfigKey<String> MIN_IDLE = string("db.minIdle", Section.DB);
        public static final GConfigKey<String> MAX_IDLE = string("db.maxIdle", Section.DB);
        public static final GConfigKey<String> MAX_ACTIVE = string("db.maxActive", Section.DB);
        public static final GConfigKey<String> MAX_WAIT = string("db.maxWait", Section.DB);
        public static final GConfigKey<String> DEFAULT_AUTO_COMMIT = string("db.defaultAutoCommit", Section.DB);
        public static final GConfigKey<String> REMOVE_ABANDONED = string("db.removeAbandoned", Section.DB);
        public static final GConfigKey<String> REMOVE_ABANDONED_TIMEOUT = string("db.removeAbandonedTimeout", Section.DB);
        public static final GConfigKey<String> TEST_WHILE_IDLE = string("db.testWhileIdle", Section.DB);
        public static final GConfigKey<String> TIME_BETWEEN_EVICTION_RUNS_MILLIS = string("db.timeBetweenEvictionRunsMillis", Section.DB);
        public static final GConfigKey<String> MIN_EVICTABLE_IDLE_TIME_MILLIS = string("db.minEvictableIdleTimeMillis", Section.DB);
        public static final GConfigKey<String> NUM_TESTS_PER_EVICTION_RUN = string("db.numTestsPerEvictionRun", Section.DB);
        public static final GConfigKey<String> TEST_ON_RETURN = string("db.testOnReturn", Section.DB);
        public static final GConfigKey<String> TEST_ON_BORROW = string("db.testOnBorrow", Section.DB);

        private static final List<GConfigKey<?>> ALL = list(
                DRIVER_CLASS_NAME, URL, USERNAME, PASSWORD, VALIDATION_QUERY, CONNECT_TIMEOUT_AND_READ_TIMEOUT,
                INITIAL_SIZE, MIN_IDLE, MAX_IDLE, MAX_ACTIVE, MAX_WAIT, DEFAULT_AUTO_COMMIT,
                REMOVE_ABANDONED, REMOVE_ABANDONED_TIMEOUT, TEST_WHILE_IDLE,
                TIME_BETWEEN_EVICTION_RUNS_MILLIS, MIN_EVICTABLE_IDLE_TIME_MILLIS,
                NUM_TESTS_PER_EVICTION_RUN, TEST_ON_RETURN, TEST_ON_BORROW);

        private Db() {
        }
    }

    public static final class Smtp {
        public static final GConfigKey<Boolean> IS_MAIL_ON = bool("smtp.ismailon", Section.SMTP);
        public static final GConfigKey<String> PROTOCOL = string("smtp.protocol", Section.SMTP);
        public static final GConfigKey<String> HOST = string("smtp.host", Section.SMTP);
        public static final GConfigKey<String> SUFFIX = string("smtp.suffix", Section.SMTP);
        public static final GConfigKey<String> ACCOUNT = string("smtp.account", Section.SMTP);
        public static final GConfigKey<String> PASSWORD = sensitive("smtp.password", Section.SMTP);
        public static final GConfigKey<String> PORT = string("smtp.port", Section.SMTP);
        public static final GConfigKey<String> AUTH = string("smtp.auth", Section.SMTP);
        public static final GConfigKey<String> TLS = string("smtp.tls", Section.SMTP);
        public static final GConfigKey<String> DEBUG = string("smtp.debug", Section.SMTP);

        private static final List<GConfigKey<?>> ALL = list(
                IS_MAIL_ON, PROTOCOL, HOST, SUFFIX, ACCOUNT, PASSWORD, PORT, AUTH, TLS, DEBUG);

        private Smtp() {
        }
    }

    public static final class Email {
        public static final GConfigKey<Boolean> IS_USED = bool("email.isused", Section.MAIL);
        public static final GConfigKey<String> DEV = string("email.dev", Section.MAIL);
        public static final GConfigKey<String> TEST = string("email.test", Section.MAIL);
        public static final GConfigKey<String> MANAGER = string("email.manager", Section.MAIL);
        public static final GConfigKey<String> RESEARCHER = string("email.researcher", Section.MAIL);
        public static final GConfigKey<String> MAIN_SEND = string("email.mainsend", Section.MAIL);
        public static final GConfigKey<String> COPY_SEND = string("email.copysend", Section.MAIL);
        public static final GConfigKey<String> SECRET_SEND = string("email.secretsend", Section.MAIL);

        private static final List<GConfigKey<?>> ALL = list(
                IS_USED, DEV, TEST, MANAGER, RESEARCHER, MAIN_SEND, COPY_SEND, SECRET_SEND);

        private Email() {
        }
    }

    public static final class Sut {
        public static final GConfigKey<String> SYS_NAME = string("sut.sysName", Section.SUT);
        public static final GConfigKey<String> SECTION_NAME_1 = string("sut.sectionName1", Section.SUT);
        public static final GConfigKey<String> SECTION_NAME_2 = string("sut.sectionName2", Section.SUT);
        public static final GConfigKey<String> SECTION_NAME_3 = string("sut.sectionName3", Section.SUT);
        public static final GConfigKey<String> SID = string("sut.sid", Section.SUT);

        private static final List<GConfigKey<?>> ALL = list(SYS_NAME, SECTION_NAME_1, SECTION_NAME_2, SECTION_NAME_3, SID);

        private Sut() {
        }
    }

    public static final class Coverage {
        public static final GConfigKey<Integer> BILL_TOTAL = integer("tc.billtotal", Section.COVERAGE);
        public static final GConfigKey<Integer> FLOW_TOTAL = integer("tc.flowtotal", Section.COVERAGE);
        public static final GConfigKey<Integer> ALGO_TOTAL = integer("tc.algototal", Section.COVERAGE);
        public static final GConfigKey<String> URL = string("tc.coverageURL", Section.COVERAGE);

        private static final List<GConfigKey<?>> ALL = list(BILL_TOTAL, FLOW_TOTAL, ALGO_TOTAL, URL);

        private Coverage() {
        }
    }

    public static final class Quality {
        public static final GConfigKey<Integer> FUNCTIONALITY_FLEXIBILITY = integer("tc.FUNCTIONALITY_FLEXIBILITY", Section.QUALITY);
        public static final GConfigKey<Integer> FUNCTIONALITY_ACCURACY = integer("tc.FUNCTIONALITY_ACCURACY", Section.QUALITY);
        public static final GConfigKey<Integer> FUNCTIONALITY_INTERACTIVITY = integer("tc.FUNCTIONALITY_INTERACTIVITY", Section.QUALITY);
        public static final GConfigKey<Integer> FUNCTIONALITY_SECURITY = integer("tc.FUNCTIONALITY_SECURITY", Section.QUALITY);
        public static final GConfigKey<Integer> FUNCTIONALITY_REGULATORY = integer("tc.FUNCTIONALITY_REGULATORY", Section.QUALITY);
        public static final GConfigKey<Integer> RELIABILITY_MATURITY = integer("tc.RELIABILITY_MATURITY", Section.QUALITY);
        public static final GConfigKey<Integer> RELIABILITY_FAULTTOLERANCE = integer("tc.RELIABILITY_FAULTTOLERANCE", Section.QUALITY);
        public static final GConfigKey<Integer> RELIABILITY_RECOVERABILITY = integer("tc.RELIABILITY_RECOVERABILITY", Section.QUALITY);
        public static final GConfigKey<Integer> RELIABILITY_REGULATORY = integer("tc.RELIABILITY_REGULATORY", Section.QUALITY);
        public static final GConfigKey<Integer> USABILITY_COMPREHENSIBILITY = integer("tc.USABILITY_COMPREHENSIBILITY", Section.QUALITY);
        public static final GConfigKey<Integer> USABILITY_EASYTOLEARN = integer("tc.USABILITY_EASYTOLEARN", Section.QUALITY);
        public static final GConfigKey<Integer> USABILITY_OPERABILITY = integer("tc.USABILITY_OPERABILITY", Section.QUALITY);
        public static final GConfigKey<Integer> USABILITY_STICKINESS = integer("tc.USABILITY_STICKINESS", Section.QUALITY);
        public static final GConfigKey<Integer> USABILITY_REGULATORY = integer("tc.USABILITY_REGULATORY", Section.QUALITY);
        public static final GConfigKey<Integer> EFFICIENCY_TIMING = integer("tc.EFFICIENCY_TIMING", Section.QUALITY);
        public static final GConfigKey<Integer> EFFICIENCY_UTILIZATION = integer("tc.EFFICIENCY_UTILIZATION", Section.QUALITY);
        public static final GConfigKey<Integer> EFFICIENCY_REGULATORY = integer("tc.EFFICIENCY_REGULATORY", Section.QUALITY);
        public static final GConfigKey<Integer> MAINTAINABILITY_ANALYZABILITY = integer("tc.MAINTAINABILITY_ANALYZABILITY", Section.QUALITY);
        public static final GConfigKey<Integer> MAINTAINABILITY_STABILITY = integer("tc.MAINTAINABILITY_STABILITY", Section.QUALITY);
        public static final GConfigKey<Integer> MAINTAINABILITY_CHANGEABILITY = integer("tc.MAINTAINABILITY_CHANGEABILITY", Section.QUALITY);
        public static final GConfigKey<Integer> MAINTAINABILITY_TESTABILITY = integer("tc.MAINTAINABILITY_TESTABILITY", Section.QUALITY);
        public static final GConfigKey<Integer> MAINTAINABILITY_REGULATORY = integer("tc.MAINTAINABILITY_REGULATORY", Section.QUALITY);
        public static final GConfigKey<Integer> PORTABILITY_ADAPTABILITY = integer("tc.PORTABILITY_ADAPTABILITY", Section.QUALITY);
        public static final GConfigKey<Integer> PORTABILITY_INSTALLATION = integer("tc.PORTABILITY_INSTALLATION", Section.QUALITY);
        public static final GConfigKey<Integer> PORTABILITY_COEXISTENCE = integer("tc.PORTABILITY_COEXISTENCE", Section.QUALITY);
        public static final GConfigKey<Integer> PORTABILITY_REPLACEABILITY = integer("tc.PORTABILITY_REPLACEABILITY", Section.QUALITY);
        public static final GConfigKey<Integer> PORTABILITY_REGULATORY = integer("tc.PORTABILITY_REGULATORY", Section.QUALITY);
        public static final GConfigKey<String> URL = string("tc.qualityURL", Section.QUALITY);

        private static final List<GConfigKey<?>> ALL = list(
                FUNCTIONALITY_FLEXIBILITY, FUNCTIONALITY_ACCURACY, FUNCTIONALITY_INTERACTIVITY,
                FUNCTIONALITY_SECURITY, FUNCTIONALITY_REGULATORY, RELIABILITY_MATURITY,
                RELIABILITY_FAULTTOLERANCE, RELIABILITY_RECOVERABILITY, RELIABILITY_REGULATORY,
                USABILITY_COMPREHENSIBILITY, USABILITY_EASYTOLEARN, USABILITY_OPERABILITY,
                USABILITY_STICKINESS, USABILITY_REGULATORY, EFFICIENCY_TIMING,
                EFFICIENCY_UTILIZATION, EFFICIENCY_REGULATORY, MAINTAINABILITY_ANALYZABILITY,
                MAINTAINABILITY_STABILITY, MAINTAINABILITY_CHANGEABILITY, MAINTAINABILITY_TESTABILITY,
                MAINTAINABILITY_REGULATORY, PORTABILITY_ADAPTABILITY, PORTABILITY_INSTALLATION,
                PORTABILITY_COEXISTENCE, PORTABILITY_REPLACEABILITY, PORTABILITY_REGULATORY, URL);

        private Quality() {
        }
    }

    public static final class Webdriver {
        public static final GConfigKey<String> OS = string("browserOS", Section.WEBDRIVER);
        public static final GConfigKey<String> INSTALL_PATH = string("browserInstallPath", Section.WEBDRIVER);
        public static final GConfigKey<String> APPLICATION = string("browserApplication", Section.WEBDRIVER);
        public static final GConfigKey<Boolean> HEADLESS = GConfigKey.booleanKey(
                "browserHeadless", "browserHeadless", Section.WEBDRIVER, Boolean.TRUE, false);
        public static final GConfigKey<String> DRIVER_UPDATE = string("browserDriverUpdate", Section.WEBDRIVER);
        public static final GConfigKey<String> DRIVER_TYPE = string("browserDriverType", Section.WEBDRIVER);
        public static final GConfigKey<String> DRIVER_DOWNLOAD_URL = string("browserDriverDownloadUrl", Section.WEBDRIVER);
        public static final GConfigKey<String> DRIVER_VERSION = string("browserDriverVerision", Section.WEBDRIVER);
        public static final GConfigKey<String> DRIVER_FILE_NAME = string("browserDriverFileName", Section.WEBDRIVER);
        public static final GConfigKey<String> DRIVER_FILE_FORMAT = string("browserDriverFileFormat", Section.WEBDRIVER);
        public static final GConfigKey<String> DRIVER_SAVE_PATH = string("browserDriverSavePath", Section.WEBDRIVER);
        public static final GConfigKey<String> DRIVER_SAVE_NAME = string("browserDriverSaveName", Section.WEBDRIVER);
        public static final GConfigKey<String> DRIVER_DIR_NAME = string("browserDriverDirName", Section.WEBDRIVER);
        public static final GConfigKey<String> LIB_PATH = string("browserLibPath", Section.WEBDRIVER);
        public static final GConfigKey<String> DRIVER_BIN = string("browserDriverbin", Section.WEBDRIVER);
        public static final GConfigKey<String> DRIVER_LIB_NAME = string("browserDriverLibName", Section.WEBDRIVER);
        public static final GConfigKey<String> DRIVER_PROCESS_NAME = string("browserDriverProcessName", Section.WEBDRIVER);
        public static final GConfigKey<String> LOG_TYPE = string("browserLogType", Section.WEBDRIVER);

        private static final List<GConfigKey<?>> ALL = list(
                OS, INSTALL_PATH, APPLICATION, HEADLESS, DRIVER_UPDATE, DRIVER_TYPE,
                DRIVER_DOWNLOAD_URL, DRIVER_VERSION, DRIVER_FILE_NAME, DRIVER_FILE_FORMAT,
                DRIVER_SAVE_PATH, DRIVER_SAVE_NAME, DRIVER_DIR_NAME, LIB_PATH, DRIVER_BIN,
                DRIVER_LIB_NAME, DRIVER_PROCESS_NAME, LOG_TYPE);

        private Webdriver() {
        }
    }

    /**
     * 把若干配置键打包成不可变列表
     *
     * @param keys 配置键可变参数
     * @return 不可修改的列表，供各分区的 {@code ALL} 常量使用
     */
    private static List<GConfigKey<?>> list(GConfigKey<?>... keys) {
        return Collections.unmodifiableList(Arrays.asList(keys));
    }

    /**
     * 获得全部已声明的配置键
     *
     * <p>按分区顺序拼接：系统、数据库、SMTP、邮件、被测件、覆盖率、质量、浏览器。</p>
     *
     * @return 不可修改的全部配置键列表
     */
    public static List<GConfigKey<?>> all() {
        List<GConfigKey<?>> keys = new ArrayList<>();
        keys.addAll(Sys.ALL);
        keys.addAll(Db.ALL);
        keys.addAll(Smtp.ALL);
        keys.addAll(Email.ALL);
        keys.addAll(Sut.ALL);
        keys.addAll(Coverage.ALL);
        keys.addAll(Quality.ALL);
        keys.addAll(Webdriver.ALL);
        return Collections.unmodifiableList(keys);
    }

    /**
     * 获得"配置项名称 → 配置键"的索引
     *
     * <p>保持声明顺序，便于审计输出稳定；若存在重名配置键会立即抛异常，
     * 相当于在类初始化阶段做一次自检。</p>
     *
     * @return 不可修改的名称索引
     * @throws IllegalStateException 存在重复的配置项名称时抛出
     */
    public static Map<String, GConfigKey<?>> byName() {
        Map<String, GConfigKey<?>> keys = new LinkedHashMap<>();
        for (GConfigKey<?> key : all()) {
            if (keys.put(key.getName(), key) != null) {
                throw new IllegalStateException("重复配置键: " + key.getName());
            }
        }
        return Collections.unmodifiableMap(keys);
    }
}
