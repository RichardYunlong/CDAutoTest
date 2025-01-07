package main.java.DB;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import main.java.DUnit.GAttribute;

/**
 *  数据库配置文件读取
 */
@Component
public class GDBConfig {
	
	/**
	 *  保存本类所有参数
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private static GAttribute dbAttribute = new GAttribute();
	
	public static GAttribute getDbAttribute() {
		return dbAttribute;
	}
	
	/**
	 *  数据库驱动类型
	 */
    @Value("${db.driverClassName}")
    private String driverClassName;

    public String getDriverClassName() {
    	dbAttribute.putAttribute("\n#数据库驱动类型\ndb.driverClassName", driverClassName);
        return driverClassName;
    }
    
    /**
     *  数据库地址
     */
    @Value("${db.url}")
    private String url;

    public String getUrl() {
    	dbAttribute.putAttribute("\n#数据库地址\ndb.url", url);
        return url;
    }
    
    /**
     *  数据库用户名
     */
    @Value("${db.username}")
    private String username;
    
    public String getUsername() {
    	dbAttribute.putAttribute("\n#数据库用户名\ndb.username", username);
        return username;
    }
    
    /**
     *  数据库用户密码
     */
    @Value("${db.password}")
    private String pwd;
    
    public String getPassword() {
    	dbAttribute.putAttribute("\n#数据库用户密码\ndb.password", pwd);
        return pwd;
    }
    
    /**
     *  数据库配有效性检查语句
     */
    @Value("${db.validationQuery}")
    private String validationQuery;
    
    public String getValidationQuery() {
    	dbAttribute.putAttribute("\n#数据库配有效性检查语句\ndb.validationQuery", validationQuery);
        return validationQuery;
    }
    
    /**
     *  数据库连接超时时间
     */
    @Value("${db.connectTimeoutAndReadTimeout}")
    private String connectTimeoutAndReadTimeout;
    
    public String getConnectTimeoutAndReadTimeout() {
    	dbAttribute.putAttribute("\n#数据库连接超时时间\ndb.connectTimeoutAndReadTimeout", connectTimeoutAndReadTimeout);
        return connectTimeoutAndReadTimeout;
    }
    
    /**
     *  连接池初始化连接数量
     */
    @Value("${db.initialSize}")
    private String initialSize;
    
    public String getInitialSize() {
    	dbAttribute.putAttribute("\n#连接池初始化连接数量\ndb.initialSize", initialSize);
        return initialSize;
    }
    
    /**
     *  连接池中最小的空闲的连接数
     */
    @Value("${db.minIdle}")
    private String minIdle;
    
    public String getMinIdle() {
    	dbAttribute.putAttribute("\n#连接池中最小的空闲的连接数\ndb.minIdle", minIdle);
        return minIdle;
    }
    
    /**
     *  连接池中最大的空闲的连接数
     */
    @Value("${db.maxIdle}")
    private String maxIdle;
    
    public String getMaxIdle() {
    	dbAttribute.putAttribute("\n#连接池中最大的空闲的连接数\ndb.maxIdle", maxIdle);
        return maxIdle;
    }
    
    /**
     *  连接池中可同时连接的最大的连接数
     */
    @Value("${db.maxActive}")
    private String maxActive;
    
    public String getMaxActive() {
    	dbAttribute.putAttribute("\n#连接池中可同时连接的最大的连接数\ndb.maxActive", maxActive);
        return maxActive;
    }
    
    /**
     *  最大建立连接等待时间,单位为毫秒
     */
    @Value("${db.maxWait}")
    private String maxWait;
    
    public String getMaxWait() {
    	dbAttribute.putAttribute("\n#最大建立连接等待时间,单位为毫秒\ndb.maxWait", maxWait);
        return maxWait;
    }
    
    /**
     *  是否自动提交
     */
    @Value("${db.defaultAutoCommit}")
    private String defaultAutoCommit;
    
    public String getDefaultAutoCommit() {
    	dbAttribute.putAttribute("\n#是否自动提交\ndb.defaultAutoCommit", defaultAutoCommit);
        return defaultAutoCommit;
    }
    
    /**
     *  是否自动回收超时连接
     */
    @Value("${db.removeAbandoned}")
    private String removeAbandoned;
    
    public String getRemoveAbandoned() {
    	dbAttribute.putAttribute("\n#是否自动回收超时连接\ndb.removeAbandoned", removeAbandoned);
        return removeAbandoned;
    }
    
    /**
     *  超时时间(以秒数为单位)
     */
    @Value("${db.removeAbandonedTimeout}")
    private String removeAbandonedTimeout;
    
    public String getRemoveAbandonedTimeout() {
    	dbAttribute.putAttribute("\n#超时时间(以秒数为单位)\ndb.removeAbandonedTimeout", removeAbandonedTimeout);
        return removeAbandonedTimeout;
    }
    
    /**
     *  是否在空闲时间测试连接
     */
    @Value("${db.testWhileIdle}")
    private String testWhileIdle;
    
    public String getTestWhileIdle() {
    	dbAttribute.putAttribute("\n#是否在空闲时间测试连接\ndb.testWhileIdle", testWhileIdle);
        return testWhileIdle;
    }
    
    /**
     *  一个连接在池中最小生存的时间,单位为毫秒
     */
    @Value("${db.timeBetweenEvictionRunsMillis}")
    private String timeBetweenEvictionRunsMillis;
    
    public String getTimeBetweenEvictionRunsMillis() {
    	dbAttribute.putAttribute("\n#一个连接在池中最小生存的时间,单位为毫秒\ndb.timeBetweenEvictionRunsMillis", timeBetweenEvictionRunsMillis);
        return timeBetweenEvictionRunsMillis;
    }
    
    /**
     *  连接池中连接可空闲的时间,单位为毫秒
     */
    @Value("${db.minEvictableIdleTimeMillis}")
    private String minEvictableIdleTimeMillis;
    
    public String getMinEvictableIdleTimeMillis() {
    	dbAttribute.putAttribute("\n#连接池中连接可空闲的时间,单位为毫秒\ndb.minEvictableIdleTimeMillis", minEvictableIdleTimeMillis);
        return minEvictableIdleTimeMillis;
    }
    
    /**
     *  每次检查链接的数量
     */
    @Value("${db.numTestsPerEvictionRun}")
    private String numTestsPerEvictionRun;
    
    public String getNumTestsPerEvictionRun() {
    	dbAttribute.putAttribute("\n#每次检查链接的数量\ndb.numTestsPerEvictionRun", numTestsPerEvictionRun);
        return numTestsPerEvictionRun;
    }
    
    /**
     *  返回对象时是否进行验证
     */
    @Value("${db.testOnReturn}")
    private String testOnReturn;
    
    public String getTestOnReturn() {
    	dbAttribute.putAttribute("\n#返回对象时是否进行验证\ndb.testOnReturn", testOnReturn);
        return testOnReturn;
    }
    
    /**
     *  取出对象时是否进行验证
     */
    @Value("${db.testOnBorrow}")
    private String testOnBorrow;
    
    public String getTestOnBorrow() {
    	dbAttribute.putAttribute("\n#取出对象时是否进行验证\ndb.testOnBorrow", testOnBorrow);
        return testOnBorrow;
    }
}
