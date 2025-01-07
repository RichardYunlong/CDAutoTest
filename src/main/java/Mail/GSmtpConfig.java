package main.java.Mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import main.java.DUnit.GAttribute;

/**
 *  邮件服务器配置文件读取
 */
@Component
public class GSmtpConfig {
	
	/**
	 *  保存本类所有参数
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private static GAttribute smtpAttribute = new GAttribute();
	
	public static GAttribute getSmtpAttribute() {
		return smtpAttribute;
	}
	
	/**
	 *  是否开启
	 */
    @Value("${smtp.ismailon}")
    private String ismailon;
    
    public String getIsMailOn() {
    	smtpAttribute.putAttribute("\n#传输协议\nsmtp.ismailon", ismailon);
        return ismailon;
    }
    
	/**
	 *  传输协议
	 */
    @Value("${smtp.protocol}")
    private String protocol;
    
    public String getProtocol() {
    	smtpAttribute.putAttribute("\n#传输协议\nsmtp.protocol", protocol);
        return protocol;
    }
    
    /**
     *  地址
     */
    @Value("${smtp.host}")
    private String host;
    
    public String getHost() {
    	smtpAttribute.putAttribute("\n#地址\nsmtp.host", host);
        return host;
    }
    
    /**
     *  后缀
     */
    @Value("${smtp.suffix}")
    private String suffix;
    
    public String getSuffix() {
    	smtpAttribute.putAttribute("\n#后缀\nsmtp.suffix", suffix);
        return suffix;
    }
    
    /**
     *  账号
     */
    @Value("${smtp.account}")
    private String account;
    
    public String getAccount() {
    	smtpAttribute.putAttribute("\n#账号\nsmtp.account", account);
        return account;
    }
    
    /**
     *  密码
     */
    @Value("${smtp.password}")
    private String password;
    
    public String getPassword() {
    	smtpAttribute.putAttribute("\n#密码\nsmtp.password", password);
        return password;
    }
    
    /**
     *  端口
     */
    @Value("${smtp.port}")
    private String port;
    
    public String getPort() {
    	smtpAttribute.putAttribute("\n#端口\nsmtp.port", port);
        return port;
    }
    
    /**
     *  认证方式
     */
    @Value("${smtp.auth}")
    private String auth;
    
    public String getAuth() {
    	smtpAttribute.putAttribute("\n#认证方式\nsmtp.auth", auth);
        return auth;
    }
    
    /**
     *  tls是否开启
     */
    @Value("${smtp.tls}")
    private String tls;
    
    public String getTls() {
    	smtpAttribute.putAttribute("\n#tls是否开启\nsmtp.tls", tls);
        return tls;
    }
    
    /**
     *  debug模式是否开启
     */
    @Value("${smtp.debug}")
    private String debug;

    public String getDebug() {
    	smtpAttribute.putAttribute("\n#debug模式是否开启\nsmtp.debug", debug);
        return debug;
    }
}
