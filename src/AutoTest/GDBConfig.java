package AutoTest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *  数据库配置文件读取
 */
@Component
public class GDBConfig {
	/**
	 *  数据库驱动类型
	 */
    @Value("${db.driverClassName}")
    private String driverClassName;
    
    /**
     *  数据库地址
     */
    @Value("${db.url}")
    private String url;
    
    /**
     *  数据库用户名
     */
    @Value("${db.username}")
    private String username;
    
    /**
     *  数据库用户密码
     */
    @Value("${db.password}")
    private String pwd;
    
    /**
     *  数据库配有效性检查语句
     */
    @Value("${db.validationQuery}")
    private String validationQuery;
    
    /**
     *  数据库连接超时时间
     */
    @Value("${db.connectTimeoutAndReadTimeout}")
    private String connectTimeoutAndReadTimeout;
    
    /**
     *  连接池初始化连接数量
     */
    @Value("${db.initialSize}")
    private String initialSize;
    
    /**
     *  连接池中最小的空闲的连接数
     */
    @Value("${db.minIdle}")
    private String minIdle;
    
    /**
     *  连接池中最大的空闲的连接数
     */
    @Value("${db.maxIdle}")
    private String maxIdle;
    
    /**
     *  连接池中可同时连接的最大的连接数
     */
    @Value("${db.maxActive}")
    private String maxActive;
    
    /**
     *  最大建立连接等待时间,单位为毫秒
     */
    @Value("${db.maxWait}")
    private String maxWait;
    
    /**
     *  是否自动提交
     */
    @Value("${db.defaultAutoCommit}")
    private String defaultAutoCommit;
    
    /**
     *  是否自动回收超时连接
     */
    @Value("${db.removeAbandoned}")
    private String removeAbandoned;
    
    /**
     *  超时时间(以秒数为单位)
     */
    @Value("${db.removeAbandonedTimeout}")
    private String removeAbandonedTimeout;
    
    /**
     *  是否在空闲时间测试连接
     */
    @Value("${db.testWhileIdle}")
    private String testWhileIdle;
    
    /**
     *  一个连接在池中最小生存的时间,单位为毫秒
     */
    @Value("${db.timeBetweenEvictionRunsMillis}")
    private String timeBetweenEvictionRunsMillis;
    
    /**
     *  连接池中连接可空闲的时间,单位为毫秒
     */
    @Value("${db.minEvictableIdleTimeMillis}")
    private String minEvictableIdleTimeMillis;
    
    /**
     *  每次检查链接的数量
     */
    @Value("${db.numTestsPerEvictionRun}")
    private String numTestsPerEvictionRun;
    
    /**
     *  返回对象时是否进行验证
     */
    @Value("${db.testOnReturn}")
    private String testOnReturn;
    
    /**
     *  取出对象时是否进行验证
     */
    @Value("${db.testOnBorrow}")
    private String testOnBorrow;

    /**
     *  获取数据库驱动类型
     */
    public String getDriverClassName() {
        return driverClassName;
    }
    
    /**
     *  获取数据库地址
     */
    public String getUrl() {
        return url;
    }

    /**
     *  获取数据库用户名
     */
    public String getUsername() {
        return username;
    }
    
    /**
     *  获取数据库用户密码
     */
    public String getPassword() {
        return pwd;
    }

    /**
     *  获取数据库有效性检查语句
     */
    public String getValidationQuery() {
        return validationQuery;
    }
    
    /**
     *  获取数据库配连接超时时间
     */
    public String getConnectTimeoutAndReadTimeout() {
        return connectTimeoutAndReadTimeout;
    }
    
    /**
     *  获取连接池初始化连接数量
     */
    public String getInitialSize() {
        return initialSize;
    }
    
    /**
     *  获取连接池中最小的空闲的连接数
     */
    public String getMinIdle() {
        return minIdle;
    }
    
    /**
     *  获取连接池中最大的空闲的连接数
     */
    public String getMaxIdle() {
        return maxIdle;
    }
    
    /**
     *  获取连接池中可同时连接的最大的连接数
     */
    public String getMaxActive() {
        return maxActive;
    }
    
    /**
     *  获取最大建立连接等待时间,单位为毫秒 
     */
    public String getMaxWait() {
        return maxWait;
    }
    
    /**
     *  获取是否自动提交
     */
    public String getDefaultAutoCommit() {
        return defaultAutoCommit;
    }
    
    /**
     *  获取是否自动回收超时连接
     */
    public String getRemoveAbandoned() {
        return removeAbandoned;
    }
    
    /**
     *  获取超时时间(以秒数为单位)
     */
    public String getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }
    
    /**
     *  获取是否在空闲时间测试连接
     */
    public String getTestWhileIdle() {
        return testWhileIdle;
    }
    
    /**
     *  获取一个连接在池中最小生存的时间,单位为毫秒
     */
    public String getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }
    
    /**
     *  获取连接池中连接可空闲的时间,单位为毫秒
     */
    public String getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }
    
    /**
     *  获得每次检查链接的数量 
     */
    public String getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }
    
    /**
     *  获得返回对象时是否进行验证 
     */
    public String getTestOnReturn() {
        return testOnReturn;
    }
    
    /**
     *  获得取出对象时是否进行验证
     */
    public String getTestOnBorrow() {
        return testOnBorrow;
    }
}
