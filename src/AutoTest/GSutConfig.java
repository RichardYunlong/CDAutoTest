package AutoTest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *  被测件配置文件读取
 */
@Component
public class GSutConfig {
	
	/**
	 *  系统名称
	 */
    @Value("${sut.sysName}")
    private String sysName;
    
    /**
     *  配置项名称1
     */
    @Value("${sut.sectionName1}")
    private String sectionName1;
    
    /**
     *  配置项名称2
     */
    @Value("${sut.sectionName2}")
    private String sectionName2;
    
    /**
     *  配置项名称3
     */
    @Value("${sut.sectionName3}")
    private String sectionName3;
    
    /**
     *  数据库服务名称
     */
    @Value("${sut.sid}")
    private String sid;

    /**
     *  获取协议类型
     */
    public String getSysName() {
        return sysName;
    }
    
    /**
     *  获取地址
     */
    public String getSectionName1() {
        return sectionName1;
    }

    /**
     *  获取后缀名
     */
    public String getSectionName2() {
        return sectionName2;
    }
    
    /**
     *  获取账号
     */
    public String getSectionName3() {
        return sectionName3;
    }

    /**
     *  获取密码
     */
    public String getSid() {
        return sid;
    }
}
