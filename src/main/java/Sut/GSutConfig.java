package main.java.Sut;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import main.java.DUnit.GAttribute;

/**
 *  被测件配置文件读取
 */
@Component
public class GSutConfig {
	
	/**
	 *  保存本类所有参数
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private static GAttribute sutAttribute = new GAttribute();
	
	public static GAttribute getSutAttribute() {
		return sutAttribute;
	}
	
	/**
	 *  系统名称
	 */
    @Value("${sut.sysName}")
    private String sysName;
    
    public String getSysName() {
    	sutAttribute.putAttribute("\n#系统名称\nsut.sysName", sysName);
        return sysName;
    }
    
    /**
     *  配置项名称1
     */
    @Value("${sut.sectionName1}")
    private String sectionName1;
    
    public String getSectionName1() {
    	sutAttribute.putAttribute("\n#配置项名称1\nsut.sectionName1", sectionName1);
        return sectionName1;
    }
    
    /**
     *  配置项名称2
     */
    @Value("${sut.sectionName2}")
    private String sectionName2;
    
    public String getSectionName2() {
    	sutAttribute.putAttribute("\n#配置项名称2\nsut.sectionName2", sectionName2);
        return sectionName2;
    }
    
    /**
     *  配置项名称3
     */
    @Value("${sut.sectionName3}")
    private String sectionName3;
    
    public String getSectionName3() {
    	sutAttribute.putAttribute("\n#配置项名称3\nsut.sectionName3", sectionName3);
        return sectionName3;
    }
    
    /**
     *  数据库服务名称
     */
    @Value("${sut.sid}")
    private String sid;

    public String getSid() {
    	sutAttribute.putAttribute("\n#数据库服务名称\nsut.sid", sid);
        return sid;
    }
}
