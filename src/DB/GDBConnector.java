package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Base.GClazz;
import DT.GLog;
import Sys.GSys;

/**
 *  数据库连接驱动
 */
public class GDBConnector {
	
    /**
     *  构造函数
     */
	private GDBConnector(){
		GClazz.thisAToolClass();
	}
	
	/*
	 * 参数表 
	 */
	private static String driverClassName;

	private static String url;
	private static String username;
	private static String pwd;
	private static String validationQuery;
	private static String connectTimeoutAndReadTimeout;
	private static String initialSize;
	private static String minIdle;
	private static String maxIdle;
	private static String maxActive;
	private static String maxWait;
	private static String defaultAutoCommit;
	private static String removeAbandoned;
	private static String removeAbandonedTimeout;
	private static String testWhileIdle;
	private static String timeBetweenEvictionRunsMillis;
	private static String minEvictableIdleTimeMillis;
	private static String numTestsPerEvictionRun;
	private static String testOnReturn;
	private static String testOnBorrow;
	
	public static String getDriverClassName() {
		return driverClassName;
	}

	public static void setDriverClassName(String driverClassName) {
		GDBConnector.driverClassName = driverClassName;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		GDBConnector.url = url;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		GDBConnector.username = username;
	}

	public static String getPwd() {
		return pwd;
	}

	public static void setPwd(String pwd) {
		GDBConnector.pwd = pwd;
	}

	public static String getValidationQuery() {
		return validationQuery;
	}

	public static void setValidationQuery(String validationQuery) {
		GDBConnector.validationQuery = validationQuery;
	}

	public static String getConnectTimeoutAndReadTimeout() {
		return connectTimeoutAndReadTimeout;
	}

	public static void setConnectTimeoutAndReadTimeout(String connectTimeoutAndReadTimeout) {
		GDBConnector.connectTimeoutAndReadTimeout = connectTimeoutAndReadTimeout;
	}

	public static String getInitialSize() {
		return initialSize;
	}

	public static void setInitialSize(String initialSize) {
		GDBConnector.initialSize = initialSize;
	}

	public static String getMinIdle() {
		return minIdle;
	}

	public static void setMinIdle(String minIdle) {
		GDBConnector.minIdle = minIdle;
	}

	public static String getMaxIdle() {
		return maxIdle;
	}

	public static void setMaxIdle(String maxIdle) {
		GDBConnector.maxIdle = maxIdle;
	}

	public static String getMaxActive() {
		return maxActive;
	}

	public static void setMaxActive(String maxActive) {
		GDBConnector.maxActive = maxActive;
	}

	public static String getMaxWait() {
		return maxWait;
	}

	public static void setMaxWait(String maxWait) {
		GDBConnector.maxWait = maxWait;
	}

	public static String getDefaultAutoCommit() {
		return defaultAutoCommit;
	}

	public static void setDefaultAutoCommit(String defaultAutoCommit) {
		GDBConnector.defaultAutoCommit = defaultAutoCommit;
	}

	public static String getRemoveAbandoned() {
		return removeAbandoned;
	}

	public static void setRemoveAbandoned(String removeAbandoned) {
		GDBConnector.removeAbandoned = removeAbandoned;
	}

	public static String getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}

	public static void setRemoveAbandonedTimeout(String removeAbandonedTimeout) {
		GDBConnector.removeAbandonedTimeout = removeAbandonedTimeout;
	}

	public static String getTestWhileIdle() {
		return testWhileIdle;
	}

	public static void setTestWhileIdle(String testWhileIdle) {
		GDBConnector.testWhileIdle = testWhileIdle;
	}

	public static String getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public static void setTimeBetweenEvictionRunsMillis(String timeBetweenEvictionRunsMillis) {
		GDBConnector.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public static String getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public static void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis) {
		GDBConnector.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public static String getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	public static void setNumTestsPerEvictionRun(String numTestsPerEvictionRun) {
		GDBConnector.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public static String getTestOnReturn() {
		return testOnReturn;
	}

	public static void setTestOnReturn(String testOnReturn) {
		GDBConnector.testOnReturn = testOnReturn;
	}

	public static String getTestOnBorrow() {
		return testOnBorrow;
	}

	public static void setTestOnBorrow(String testOnBorrow) {
		GDBConnector.testOnBorrow = testOnBorrow;
	}

	
    /** 
     * 获得数据库连接 
     *  
     * @return conn
     */  
    public static Connection getConnection() {  
        Connection conn = null;  
  
        try {  
            Class.forName(driverClassName);  
            conn = DriverManager.getConnection(url, username, pwd);  
        } catch (ClassNotFoundException e) {
            GLog.logSysFunctionException("getConnection", e);
        } catch (SQLException e) {
            GLog.logSysFunctionException("getConnection", e);
        }  
        return conn;  
    }  
  
    /** 
     * 关闭数据库连接 
     *  
     * @param conn 
     * @param ps 
     * @param rs 
     */  
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {  
        // 关闭原则:先开后关,后开先关
        try {  
        	if (rs != null) {rs.close();}
        	if (ps != null) {ps.close();}
        	if (conn != null) {conn.close();} 
        } catch (SQLException e) {  
        	GLog.logSysFunctionException("close", e);
        }
    } 
    
	/**
	 *  加载参数
	 */	
	public static void loadConfig() {
		if((!driverClassName.equals("")) && (!url.equals("")) && (!username.equals("")) && (!pwd.equals(""))){
			;
		}else {
			GSys.logErrorSys("One of these database params from dbConfig may has no value, Please check again!");
			System.exit(0);
		}
	}
}
