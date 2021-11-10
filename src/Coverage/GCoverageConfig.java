package Coverage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import DUnit.GAttribute;

/**
 *  邮件服务器配置文件读取
 */
@Component
public class GCoverageConfig {
	
	/**
	 *  保存本类所有参数
	 */
	private static GAttribute coverageAttribute = new GAttribute();
	
	public static GAttribute getCoverageAttribute() {
		return coverageAttribute;
	}
	
	/**
	 *  订单类测试点总数
	 */
    @Value("${tc.billtotal}")
    private String billTotal;
    
    public String getBillTotal() {
    	coverageAttribute.putAttribute("\n#订单类测试点总数\ntc.billtotal", billTotal);
        return billTotal;
    }
    
	/**
	 *  流程类测试点总数
	 */
    @Value("${tc.flowtotal}")
    private String flowTotal;
    
    public String getFlowTotal() {
    	coverageAttribute.putAttribute("\n#流程类测试点总数\ntc.flowtotal", flowTotal);
        return flowTotal;
    }
    
    /**
     *  算法类测试点总数
     */
    @Value("${tc.algototal}")
    private String algoTotal;
    
    public String getAlgoTotal() {
    	coverageAttribute.putAttribute("\n#算法类测试点总数\ntc.algototal", algoTotal);
        return algoTotal;
    }
    
    /**
     *  报告地址
     */
    @Value("${tc.coverageURL}")
    private String coverageURL;

	public String getCoverageURL() {
		coverageAttribute.putAttribute("\n#报告地址\ntc.coverageURL", coverageURL);
		return coverageURL;
	} 
}
