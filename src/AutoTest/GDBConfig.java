package AutoTest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *  数据库配置文件读取
 */
@Component
public class GDBConfig {
    @Value("${db.driverClassName}")
    private String driverClassName;
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;
    @Value("${db.validationQuery}")
    private String validationQuery;
    @Value("${db.connectTimeoutAndReadTimeout}")
    private String connectTimeoutAndReadTimeout;
    @Value("${db.initialSize}")
    private String initialSize;
    @Value("${db.minIdle}")
    private String minIdle;
    @Value("${db.maxIdle}")
    private String maxIdle;
    @Value("${db.maxActive}")
    private String maxActive;
    @Value("${db.maxWait}")
    private String maxWait;
    @Value("${db.defaultAutoCommit}")
    private String defaultAutoCommit;
    @Value("${db.removeAbandoned}")
    private String removeAbandoned;
    @Value("${db.removeAbandonedTimeout}")
    private String removeAbandonedTimeout;
    @Value("${db.testWhileIdle}")
    private String testWhileIdle;
    @Value("${db.timeBetweenEvictionRunsMillis}")
    private String timeBetweenEvictionRunsMillis;
    @Value("${db.minEvictableIdleTimeMillis}")
    private String minEvictableIdleTimeMillis;
    @Value("${db.numTestsPerEvictionRun}")
    private String numTestsPerEvictionRun;
    @Value("${db.testOnReturn}")
    private String testOnReturn;
    @Value("${db.testOnBorrow}")
    private String testOnBorrow;

    public String getDriverClassName() {
        return driverClassName;
    }
    
    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }

    public String getValidationQuery() {
        return validationQuery;
    }
    
    public String getConnectTimeoutAndReadTimeout() {
        return connectTimeoutAndReadTimeout;
    }
    
    public String getInitialSize() {
        return initialSize;
    }
    
    public String getMinIdle() {
        return minIdle;
    }
    
    public String getMaxIdle() {
        return maxIdle;
    }
    
    public String getMaxActive() {
        return maxActive;
    }
    
    public String getMaxWait() {
        return maxWait;
    }
    
    public String getDefaultAutoCommit() {
        return defaultAutoCommit;
    }
    
    public String getRemoveAbandoned() {
        return removeAbandoned;
    }
    
    public String getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }
    
    public String getTestWhileIdle() {
        return testWhileIdle;
    }
    
    public String getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }
    
    public String getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }
    
    public String getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }
    
    public String getTestOnReturn() {
        return testOnReturn;
    }
    
    public String getTestOnBorrow() {
        return testOnBorrow;
    }
}
