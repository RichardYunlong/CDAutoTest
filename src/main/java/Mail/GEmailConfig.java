package Mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import DUnit.GAttribute;

/**
 *  邮件服务器配置文件读取
 */
@Component
public class GEmailConfig {
	
	/**
	 *  保存本类所有参数
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private static GAttribute mailAttribute = new GAttribute();
	
	public static GAttribute getMailAttribute() {
		return mailAttribute;
	}
	
	/**
	 *  是否使用此配置 此项为true时，所有系统产生的邮件将按照以下收件人发送；此项为false时，将有程序内置逻辑确定收件人
	 */
    @Value("${email.isused}")
    private String isused;
    
    public String getIsUsed() {
    	mailAttribute.putAttribute("\n#是否使用此配置 此项为true时，所有系统产生的邮件将按照以下收件人发送；此项为false时，将有程序内置逻辑确定收件人\nemail.isused", isused);
        return isused;
    }
    
	/**
	 *  开发负责人
	 */
    @Value("${email.dev}")
    private String dev;
    
    public String getDev() {
    	mailAttribute.putAttribute("\n#开发负责人\nemail.dev", dev);
        return dev;
    }
    
    /**
     *  测试负责人
     */
    @Value("${email.test}")
    private String test;
    
    public String getTest() {
    	mailAttribute.putAttribute("\n#测试负责人\nemail.test", test);
        return test;
    }
    
    /**
     *  项目经理
     */
    @Value("${email.manager}")
    private String manager;
    
    public String getManager() {
    	mailAttribute.putAttribute("\n#项目经理\nemail.manager", manager);
        return manager;
    }
    
    /**
     *  产品经理
     */
    @Value("${email.researcher}")
    private String researcher;
    
    public String getResearcher() {
    	mailAttribute.putAttribute("\n#产品经理\nemail.researcher", researcher);
        return researcher;
    }
    
    /**
     *  主送列表
     */
    @Value("${email.mainsend}")
    private String mainsend;
    
	public String getMainsend() {
		mailAttribute.putAttribute("\n#主送列表\nemail.mainsend", mainsend);
		return mainsend;
	}
    
    /**
     *  抄送列表
     */
    @Value("${email.copysend}")
    private String copysend;
    
	public String getCopysend() {
		mailAttribute.putAttribute("\n#抄送列表\nemail.copysend", copysend);
		return copysend;
	}
    
    /**
     *  密送列表
     */
    @Value("${email.secretsend}")
    private String secretsend;

	public String getSecretsend() {
		mailAttribute.putAttribute("\n#密送列表\nemail.secretsend", secretsend);
		return secretsend;
	}
}
