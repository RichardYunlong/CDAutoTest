package DB;

import Base.GClazz;
import Base.GFile;
import DT.GLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  数据库连接驱动
 *
 *  <p>以静态方式持有数据库连接参数，并提供连接的创建与释放。</p>
 *
 *  <p>参数由 {@code config/db.ini} 经 {@link #loadConfig()} 校验后注入。需要注意：当前实现
 *  <strong>没有连接池</strong>，{@link #getConnection()} 每次都会新建一条物理连接，
 *  因此 {@code initialSize}、{@code maxActive}、{@code testOnBorrow} 等池化参数虽然会被读入，
 *  但本类并不使用它们。</p>
 *
 *  @see GDBDoSQL
 */
public class GDBConnector {
	
    /**
     *  构造函数
     */
	private GDBConnector(){
		GClazz.thisAToolClass();
	}
	
	/**
	 * 参数表
	 *
	 * <p>下列静态字段与 {@code db.ini} 中的配置项一一对应，由配置加载阶段赋值。</p>
	 */
	private static String driverClassName;

	/** 数据库连接地址，形如 {@code jdbc:oracle:thin:@host:1521:sid} */
	private static String url;
	/** 数据库用户名 */
	private static String username;
	/** 数据库密码 */
	private static String pwd;
	/** 连接校验语句 */
	private static String validationQuery;
	/** 连接超时与读取超时，单位毫秒 */
	private static String connectTimeoutAndReadTimeout;
	/** 连接池初始连接数（当前实现未使用） */
	private static String initialSize;
	/** 连接池最小空闲连接数（当前实现未使用） */
	private static String minIdle;
	/** 连接池最大空闲连接数（当前实现未使用） */
	private static String maxIdle;
	/** 连接池最大活跃连接数（当前实现未使用） */
	private static String maxActive;
	/** 获取连接的最大等待时间，单位毫秒（当前实现未使用） */
	private static String maxWait;
	/** 是否自动提交（当前实现未使用） */
	private static String defaultAutoCommit;
	/** 是否回收泄露连接（当前实现未使用） */
	private static String removeAbandoned;
	/** 泄露连接回收超时，单位秒（当前实现未使用） */
	private static String removeAbandonedTimeout;
	/** 空闲时是否校验连接（当前实现未使用） */
	private static String testWhileIdle;
	/** 空闲连接检测线程的运行间隔，单位毫秒（当前实现未使用） */
	private static String timeBetweenEvictionRunsMillis;
	/** 连接可被驱逐的最小空闲时长，单位毫秒（当前实现未使用） */
	private static String minEvictableIdleTimeMillis;
	/** 每次驱逐检测的连接数量（当前实现未使用） */
	private static String numTestsPerEvictionRun;
	/** 归还连接时是否校验（当前实现未使用） */
	private static String testOnReturn;
	/** 借出连接时是否校验（当前实现未使用） */
	private static String testOnBorrow;
	
	/**
	 * 获得数据库驱动类名
	 *
	 * @return 驱动类名，如 {@code oracle.jdbc.driver.OracleDriver}
	 */
	public static String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * 设置数据库驱动类名
	 *
	 * @param driverClassName 驱动类名，不可为空
	 */
	public static void setDriverClassName(String driverClassName) {
		GDBConnector.driverClassName = driverClassName;
	}

	/**
	 * 获得数据库连接地址
	 *
	 * @return JDBC 连接串
	 */
	public static String getUrl() {
		return url;
	}

	/**
	 * 设置数据库连接地址
	 *
	 * @param url JDBC 连接串，不可为空
	 */
	public static void setUrl(String url) {
		GDBConnector.url = url;
	}

	/**
	 * 获得数据库用户名
	 *
	 * @return 数据库用户名
	 */
	public static String getUsername() {
		return username;
	}

	/**
	 * 设置数据库用户名
	 *
	 * @param username 数据库用户名，不可为空
	 */
	public static void setUsername(String username) {
		GDBConnector.username = username;
	}

	/**
	 * 获得数据库密码
	 *
	 * @return 数据库密码明文
	 */
	public static String getPwd() {
		return pwd;
	}

	/**
	 * 设置数据库密码
	 *
	 * @param pwd 数据库密码，不可为空
	 */
	public static void setPwd(String pwd) {
		GDBConnector.pwd = pwd;
	}

	/**
	 * 获得连接校验语句
	 *
	 * @return 校验用 SQL，如 {@code SELECT 1 FROM DUAL}
	 */
	public static String getValidationQuery() {
		return validationQuery;
	}

	/**
	 * 设置连接校验语句
	 *
	 * @param validationQuery 校验用 SQL
	 */
	public static void setValidationQuery(String validationQuery) {
		GDBConnector.validationQuery = validationQuery;
	}

	/**
	 * 获得连接超时与读取超时
	 *
	 * @return 超时时间，单位毫秒
	 */
	public static String getConnectTimeoutAndReadTimeout() {
		return connectTimeoutAndReadTimeout;
	}

	/**
	 * 设置连接超时与读取超时
	 *
	 * @param connectTimeoutAndReadTimeout 超时时间，单位毫秒
	 */
	public static void setConnectTimeoutAndReadTimeout(String connectTimeoutAndReadTimeout) {
		GDBConnector.connectTimeoutAndReadTimeout = connectTimeoutAndReadTimeout;
	}

	/**
	 * 获得连接池初始连接数
	 *
	 * @return 初始连接数（当前实现未使用）
	 */
	public static String getInitialSize() {
		return initialSize;
	}

	/**
	 * 设置连接池初始连接数
	 *
	 * @param initialSize 初始连接数（当前实现未使用）
	 */
	public static void setInitialSize(String initialSize) {
		GDBConnector.initialSize = initialSize;
	}

	/**
	 * 获得连接池最小空闲连接数
	 *
	 * @return 最小空闲连接数（当前实现未使用）
	 */
	public static String getMinIdle() {
		return minIdle;
	}

	/**
	 * 设置连接池最小空闲连接数
	 *
	 * @param minIdle 最小空闲连接数（当前实现未使用）
	 */
	public static void setMinIdle(String minIdle) {
		GDBConnector.minIdle = minIdle;
	}

	/**
	 * 获得连接池最大空闲连接数
	 *
	 * @return 最大空闲连接数（当前实现未使用）
	 */
	public static String getMaxIdle() {
		return maxIdle;
	}

	/**
	 * 设置连接池最大空闲连接数
	 *
	 * @param maxIdle 最大空闲连接数（当前实现未使用）
	 */
	public static void setMaxIdle(String maxIdle) {
		GDBConnector.maxIdle = maxIdle;
	}

	/**
	 * 获得连接池最大活跃连接数
	 *
	 * @return 最大活跃连接数（当前实现未使用）
	 */
	public static String getMaxActive() {
		return maxActive;
	}

	/**
	 * 设置连接池最大活跃连接数
	 *
	 * @param maxActive 最大活跃连接数（当前实现未使用）
	 */
	public static void setMaxActive(String maxActive) {
		GDBConnector.maxActive = maxActive;
	}

	/**
	 * 获得获取连接的最大等待时间
	 *
	 * @return 最大等待时间，单位毫秒（当前实现未使用）
	 */
	public static String getMaxWait() {
		return maxWait;
	}

	/**
	 * 设置获取连接的最大等待时间
	 *
	 * @param maxWait 最大等待时间，单位毫秒（当前实现未使用）
	 */
	public static void setMaxWait(String maxWait) {
		GDBConnector.maxWait = maxWait;
	}

	/**
	 * 获得是否自动提交
	 *
	 * @return 自动提交开关（当前实现未使用）
	 */
	public static String getDefaultAutoCommit() {
		return defaultAutoCommit;
	}

	/**
	 * 设置是否自动提交
	 *
	 * @param defaultAutoCommit 自动提交开关（当前实现未使用）
	 */
	public static void setDefaultAutoCommit(String defaultAutoCommit) {
		GDBConnector.defaultAutoCommit = defaultAutoCommit;
	}

	/**
	 * 获得是否回收泄露连接
	 *
	 * @return 回收开关（当前实现未使用）
	 */
	public static String getRemoveAbandoned() {
		return removeAbandoned;
	}

	/**
	 * 设置是否回收泄露连接
	 *
	 * @param removeAbandoned 回收开关（当前实现未使用）
	 */
	public static void setRemoveAbandoned(String removeAbandoned) {
		GDBConnector.removeAbandoned = removeAbandoned;
	}

	/**
	 * 获得泄露连接回收超时
	 *
	 * @return 回收超时，单位秒（当前实现未使用）
	 */
	public static String getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}

	/**
	 * 设置泄露连接回收超时
	 *
	 * @param removeAbandonedTimeout 回收超时，单位秒（当前实现未使用）
	 */
	public static void setRemoveAbandonedTimeout(String removeAbandonedTimeout) {
		GDBConnector.removeAbandonedTimeout = removeAbandonedTimeout;
	}

	/**
	 * 获得空闲时是否校验连接
	 *
	 * @return 空闲校验开关（当前实现未使用）
	 */
	public static String getTestWhileIdle() {
		return testWhileIdle;
	}

	/**
	 * 设置空闲时是否校验连接
	 *
	 * @param testWhileIdle 空闲校验开关（当前实现未使用）
	 */
	public static void setTestWhileIdle(String testWhileIdle) {
		GDBConnector.testWhileIdle = testWhileIdle;
	}

	/**
	 * 获得空闲连接检测线程的运行间隔
	 *
	 * @return 运行间隔，单位毫秒（当前实现未使用）
	 */
	public static String getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	/**
	 * 设置空闲连接检测线程的运行间隔
	 *
	 * @param timeBetweenEvictionRunsMillis 运行间隔，单位毫秒（当前实现未使用）
	 */
	public static void setTimeBetweenEvictionRunsMillis(String timeBetweenEvictionRunsMillis) {
		GDBConnector.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	/**
	 * 获得连接可被驱逐的最小空闲时长
	 *
	 * @return 最小空闲时长，单位毫秒（当前实现未使用）
	 */
	public static String getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	/**
	 * 设置连接可被驱逐的最小空闲时长
	 *
	 * @param minEvictableIdleTimeMillis 最小空闲时长，单位毫秒（当前实现未使用）
	 */
	public static void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis) {
		GDBConnector.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	/**
	 * 获得每次驱逐检测的连接数量
	 *
	 * @return 每次检测的连接数（当前实现未使用）
	 */
	public static String getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	/**
	 * 设置每次驱逐检测的连接数量
	 *
	 * @param numTestsPerEvictionRun 每次检测的连接数（当前实现未使用）
	 */
	public static void setNumTestsPerEvictionRun(String numTestsPerEvictionRun) {
		GDBConnector.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	/**
	 * 获得归还连接时是否校验
	 *
	 * @return 归还校验开关（当前实现未使用）
	 */
	public static String getTestOnReturn() {
		return testOnReturn;
	}

	/**
	 * 设置归还连接时是否校验
	 *
	 * @param testOnReturn 归还校验开关（当前实现未使用）
	 */
	public static void setTestOnReturn(String testOnReturn) {
		GDBConnector.testOnReturn = testOnReturn;
	}

	/**
	 * 获得借出连接时是否校验
	 *
	 * @return 借出校验开关（当前实现未使用）
	 */
	public static String getTestOnBorrow() {
		return testOnBorrow;
	}

	/**
	 * 设置借出连接时是否校验
	 *
	 * @param testOnBorrow 借出校验开关（当前实现未使用）
	 */
	public static void setTestOnBorrow(String testOnBorrow) {
		GDBConnector.testOnBorrow = testOnBorrow;
	}

	
    /**
     *  驱动是否已加载(Class.forName 具有幂等性，但每次都要走一遍类加载器查找，没必要重复)
     */
    private static boolean driverLoaded = false;

    /** 
     * 获得数据库连接 
     *
     *  当前实现是每次调用新建一条物理连接、用完即关，并没有连接池：
     *  initialSize/maxActive/testOnBorrow 等参数虽然会从 db.ini 读入，但本类从未使用，
     *  调整它们不会改变连接行为。真正的数据访问入口是 GDBDoSQL，该类目前只在 testdemo 中调用。
     *  
     * @return conn 非空连接
     * @throws IllegalStateException 驱动缺失或连接失败
     */  
    public static Connection getConnection() {  
        Connection conn = null;  
  
        try {  
            if (!driverLoaded) {
                Class.forName(driverClassName);
                driverLoaded = true;
            }
            conn = DriverManager.getConnection(url, username, pwd);  
        } catch (Exception e) {
            GLog.logSysFunctionException("getConnection", e);
            // 原来失败时返回 null，调用方随后 connection.prepareStatement 直接 NPE，
            // 真正的根因(驱动缺失/网络不通/口令错误)被完全掩盖。
            throw new IllegalStateException("数据库连接失败，请检查 db.driverClassName/db.url 与网络连通性:" + url, e);
        }

        return conn;  
    }  
  
    /** 
     * 关闭数据库连接 
     *  
     * @param conn 链接对象
     * @param ps 语句
     * @param rs 值集合
     */  
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {  
        // 关闭原则:先开后关,后开先关
        // 每层独立 try：原来的单层 try 中 rs.close() 一旦抛异常，ps/conn 就不会被关闭，最终连接数耗尽
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                GLog.logSysFunctionException("close-rs", e);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                GLog.logSysFunctionException("close-ps", e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                GLog.logSysFunctionException("close-conn", e);
            }
        }
    } 
    
	/**
	 *  加载参数
	 *
	 *  <p>校验四项必填参数是否齐全，缺失时在日志与异常中逐项列出，避免只给一句
	 *  "其中某一项为空" 而必须逐个排查。口令来自环境变量 {@code CDTEST_DB_PASSWORD}
	 *  （或同名 JVM 系统属性），未设置时解析为空串，是最常见的缺失项。</p>
	 *
	 *  @throws IllegalStateException 任一项为空时抛出
	 */	
	public static void loadConfig() {
		List<String> missing = new ArrayList<>();
		if(isBlank(driverClassName)) {
			missing.add("db.driverClassName");
		}
		if(isBlank(url)) {
			missing.add("db.url");
		}
		if(isBlank(username)) {
			missing.add("db.username");
		}
		if(isBlank(pwd)) {
			missing.add("db.password(来自环境变量 CDTEST_DB_PASSWORD)");
		}

		if(missing.isEmpty()) {
			GFile.writeStringToGuideBottom("GDBConnector Params Loaded");
			return;
		}

		// 只打印缺失项名称，不打印值，避免口令落盘
		String message = "DB params from dbConfig has no value: " + String.join(", ", missing)
				+ "，请检查 config/db.ini，并设置环境变量 CDTEST_DB_PASSWORD 或 JVM 属性 -DCDTEST_DB_PASSWORD";
		GFile.writeStringToGuideBottom(message);
		throw new IllegalStateException(message);
	}

	/**
	 *  判断参数是否未配置
	 *
	 *  @param value 参数值
	 *  @return 为 {@code null}、空串或仅含空白字符时返回 {@code true}
	 */	
	private static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
