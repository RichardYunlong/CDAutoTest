package AutoTest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *  邮件服务器配置文件读取
 */
@Component
public class GSmtpConfig {
	
	/**
	 *  传输协议
	 */
    @Value("${smtp.ismailon}")
    private String ismailon;
    
	/**
	 *  传输协议
	 */
    @Value("${smtp.protocol}")
    private String protocol;
    
    /**
     *  地址
     */
    @Value("${smtp.host}")
    private String host;
    
    /**
     *  后缀
     */
    @Value("${smtp.suffix}")
    private String suffix;
    
    /**
     *  账号
     */
    @Value("${smtp.account}")
    private String account;
    
    /**
     *  密码
     */
    @Value("${smtp.password}")
    private String password;
    
    /**
     *  端口
     */
    @Value("${smtp.port}")
    private String port;
    
    /**
     *  认证方式
     */
    @Value("${smtp.auth}")
    private String auth;
    
    /**
     *  tls是否开启
     */
    @Value("${smtp.tls}")
    private String tls;
    
    /**
     *  debug模式是否开启
     */
    @Value("${smtp.debug}")
    private String debug;

    /**
     *  是否允许发送邮件
     */
    public String getIsMailOn() {
        return ismailon;
    }

    /**
     *  获取协议类型
     */
    public String getProtocol() {
        return protocol;
    }
    
    /**
     *  获取地址
     */
    public String getHost() {
        return host;
    }

    /**
     *  获取后缀名
     */
    public String getSuffix() {
        return suffix;
    }
    
    /**
     *  获取账号
     */
    public String getAccount() {
        return account;
    }

    /**
     *  获取密码
     */
    public String getPassword() {
        return password;
    }
    
    /**
     *  获取端口
     */
    public String getPort() {
        return port;
    }
    
    /**
     *  获取认证方式
     */
    public String getAuth() {
        return auth;
    }
    
    /**
     *  获取是否开启tls
     */
    public String getTls() {
        return tls;
    }
    
    /**
     *  获取是否启动debug
     */
    public String getDebug() {
        return debug;
    }
}
