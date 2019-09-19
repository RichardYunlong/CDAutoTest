package AutoTest;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import Plugins.CommonUtil;
import Plugins.SystemConst;

public class GDBConfig {
	private GDBConfig(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  配置文件主体内容
	 */
	public static ApplicationContext applicationContext;
	
	/**
	 *  配置文件属性
	 */
	private static Properties property;
	
	/**
	 *  数据库驱动名称
	 */
	public static String driverClassName = "";
	
	/**
	 *  数据库地址
	 */
	public static String url = "";
	
	/**
	 *  用户名
	 */
	public static String username = "";
	
	/**
	 *  密码
	 */
	public static String password = "";
	
	/**
	 *  检查语句
	 */
	public static String validationQuery = "";
	
	/**
	 *  链接与读取超时
	 */
	public static String connectTimeoutAndReadTimeout = "";
	
	/**
	 *  连接池初始化连接数量
	 */
	public static String initialSize = "";
	
	/**
	 *  连接池中最小的空闲的连接数
	 */
	public static String minIdle = "";
	
	/**
	 *  连接池中最大的空闲的连接数 
	 */
	public static String maxIdle = "";
	
	/**
	 *  连接池中可同时连接的最大的连接数   
	 */
	public static String maxActive = "";
	
	/**
	 *  最大建立连接等待时间,单位为毫秒 
	 */
	public static String maxWait = "";
	
	/**
	 *  是否自动提交 
	 */
	public static String defaultAutoCommit = "";
	
	/**
	 *  是否自动回收超时连接 
	 */
	public static String removeAbandoned = "";

	/**
	 *  超时时间(以秒数为单位) 
	 */
	public static String removeAbandonedTimeout = "";
	
	/**
	 *  是否在空闲时间测试连接 
	 */
	public static String testWhileIdle = "";
	
	/**
	 *  一个连接在池中最小生存的时间,单位为毫秒  
	 */
	public static String timeBetweenEvictionRunsMillis = "";
	
	/**
	 *  连接池中连接可空闲的时间,单位为毫秒 
	 */
	public static String minEvictableIdleTimeMillis = "";
	
	/**
	 *  每次检查链接的数量  
	 */
	public static String numTestsPerEvictionRun = "";
	
	/**
	 *  返回对象时是否进行验证 
	 */
	public static String testOnReturn = "";
	
	/**
	 *  取出对象时是否进行验证 
	 */
	public static String testOnBorrow = "";

	/**
	 *  初始化配置文件
	 *  
	 *  @param appContext 参数集
	 */
	public static void init(ApplicationContext appContext) {
		applicationContext = appContext;
		property = (Properties) applicationContext.getBean("property");
		
		driverClassName = (String) property.get("db.driverClassName");
		url = (String) property.get("db.url");
		username = (String) property.get("db.username");
		password = (String) property.get("db.password");
		validationQuery = (String) property.get("db.validationQuery");
		connectTimeoutAndReadTimeout = (String) property.get("db.connectTimeoutAndReadTimeout");
		initialSize = (String) property.get("db.initialSize");
		minIdle = (String) property.get("db.minIdle");
		maxIdle = (String) property.get("db.maxIdle");
		maxActive = (String) property.get("db.maxActive");
		maxWait = (String) property.get("db.maxWait");
		defaultAutoCommit = (String) property.get("db.defaultAutoCommit");
		removeAbandoned = (String) property.get("db.removeAbandoned");
		removeAbandonedTimeout = (String) property.get("db.removeAbandonedTimeout");
		testWhileIdle = (String) property.get("db.testWhileIdle");
		timeBetweenEvictionRunsMillis = (String) property.get("db.timeBetweenEvictionRunsMillis");
		minEvictableIdleTimeMillis = (String) property.get("db.minEvictableIdleTimeMillis");
		numTestsPerEvictionRun = (String) property.get("db.numTestsPerEvictionRun");
		testOnReturn = (String) property.get("db.testOnReturn");
		testOnBorrow = (String) property.get("db.testOnBorrow");
		
		if((!driverClassName.equals("")) 
			&& (!url.equals("")) 
			&& (!username.equals("")) 
			&& (!password.equals("")) 
			&& (!validationQuery.equals("")) 
			&& (!connectTimeoutAndReadTimeout.equals("")) 
			&& (!initialSize.equals("")) 
			&& (!minIdle.equals("")) 
			&& (!maxIdle.equals("")) 
			&& (!maxActive.equals("")) 
			&& (!maxWait.equals(""))
			&& (!defaultAutoCommit.equals("")) 
			&& (!removeAbandoned.equals("")) 
			&& (!removeAbandonedTimeout.equals("")) 
			&& (!testWhileIdle.equals("")) 
			&& (!timeBetweenEvictionRunsMillis.equals("")) 
			&& (!minEvictableIdleTimeMillis.equals("")) 
			&& (!numTestsPerEvictionRun.equals("")) 
			&& (!testOnReturn.equals("")) 
			&& (!testOnBorrow.equals("")) 
		) {
			
		}else {
			GSys.logErrorSys("One of these db params from config may has no value, Please check again!");
			System.exit(0);
		}
	}

	/**
	 *  获得配置文件主体内容
	 *  
	 *  @return 返回参数集
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 *  获得配置文件处理类
	 *  
	 *  @param name 参数集名称
	 *  @return 参数集类
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 *  获得配置文件属性
	 *  
	 *  @return 属性值
	 */
	public static Properties getProperty() {
		return property;
	}
	
	/**
	 *  配置文件驱动构造
	 */
	public static void loadConfig() {
		try {
			String configLocation = "config";
			System.setProperty(SystemConst.CONFIG_LOCATION, configLocation);

			String springConfigFile = CommonUtil.getConfigPath() + SystemConst.SPRING_CONFIG_FILE;
			ApplicationContext appContext = new FileSystemXmlApplicationContext(springConfigFile);
			init(appContext);
		}catch(Exception e){
			GSys.logErrorSys(GMsg.MSG_IOFAILED[0]);
			e.printStackTrace();
			System.exit(0);
		}
	}
}
