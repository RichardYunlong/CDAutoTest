package Quality;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import DUnit.GAttribute;

/**
 *  邮件服务器配置文件读取
 */
@Component
public class GQualityConfig {
	
	/**
	 *  保存本类所有参数
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private static GAttribute qualityAttribute = new GAttribute();
	
	public static GAttribute getQualityAttribute() {
		return qualityAttribute;
	}

	/**
	 *  功能性、适应性
	 */
    @Value("${tc.FUNCTIONALITY_FLEXIBILITY}")
    private String tc_FUNCTIONALITY_FLEXIBILITY;

    public String get_FUNCTIONALITY_FLEXIBILITY() {
    	qualityAttribute.putAttribute("\n#功能性-适应性\ntc.FUNCTIONALITY_FLEXIBILITY", tc_FUNCTIONALITY_FLEXIBILITY);
        return tc_FUNCTIONALITY_FLEXIBILITY;
    }

	/**
	 *  准确性
	 */
    @Value("${tc.FUNCTIONALITY_ACCURACY}")
    private String tc_FUNCTIONALITY_ACCURACY;

    public String get_FUNCTIONALITY_ACCURACY() {
    	qualityAttribute.putAttribute("\n#功能性-准确性\ntc.FUNCTIONALITY_ACCURACY", tc_FUNCTIONALITY_ACCURACY);
        return tc_FUNCTIONALITY_ACCURACY;
    }
    
    /**
     *  交互性
     */
    @Value("${tc.FUNCTIONALITY_INTERACTIVITY}")
    private String tc_FUNCTIONALITY_INTERACTIVITY;

    public String get_FUNCTIONALITY_INTERACTIVITY() {
    	qualityAttribute.putAttribute("\n#功能性-交互性\ntc.FUNCTIONALITY_INTERACTIVITY", tc_FUNCTIONALITY_INTERACTIVITY);
        return tc_FUNCTIONALITY_INTERACTIVITY;
    }

    /**
     *  安全性
     */
    @Value("${tc.FUNCTIONALITY_SECURITY}")
    private String tc_FUNCTIONALITY_SECURITY;

    public String get_FUNCTIONALITY_SECURITY() {
    	qualityAttribute.putAttribute("\n#功能性-安全性\ntc.FUNCTIONALITY_SECURITY", tc_FUNCTIONALITY_SECURITY);
        return tc_FUNCTIONALITY_SECURITY;
    }
    
    /**
     *  功能合规性
     */
    @Value("${tc.FUNCTIONALITY_REGULATORY}")
    private String tc_FUNCTIONALITY_REGULATORY;

    public String get_FUNCTIONALITY_REGULATORY() {
    	qualityAttribute.putAttribute("\n#功能性-功能合规性\ntc.FUNCTIONALITY_REGULATORY", tc_FUNCTIONALITY_REGULATORY);
        return tc_FUNCTIONALITY_REGULATORY;
    }

    /**
     *  可靠性、成熟性
     */
    @Value("${tc.RELIABILITY_MATURITY}")
    private String tc_RELIABILITY_MATURITY;

    public String get_RELIABILITY_MATURITY() {
    	qualityAttribute.putAttribute("\n#可靠性-成熟性\ntc.RELIABILITY_MATURITY", tc_RELIABILITY_MATURITY);
        return tc_RELIABILITY_MATURITY;
    }
    
    /**
     *  容错性
     */
    @Value("${tc.RELIABILITY_FAULTTOLERANCE}")
    private String tc_RELIABILITY_FAULTTOLERANCE;

    public String get_RELIABILITY_FAULTTOLERANCE() {
    	qualityAttribute.putAttribute("\n#可靠性-成熟性\ntc.RELIABILITY_FAULTTOLERANCE", tc_RELIABILITY_FAULTTOLERANCE);
        return tc_RELIABILITY_FAULTTOLERANCE;
    }
    
    /**
     *  易恢复性
     */
    @Value("${tc.RELIABILITY_RECOVERABILITY}")
    private String tc_RELIABILITY_RECOVERABILITY;

    public String get_RELIABILITY_RECOVERABILITY() {
    	qualityAttribute.putAttribute("\n#可靠性-易恢复性\ntc.RELIABILITY_RECOVERABILITY", tc_RELIABILITY_RECOVERABILITY);
        return tc_RELIABILITY_RECOVERABILITY;
    }
    
    /**
     *  可靠合规性
     */
    @Value("${tc.RELIABILITY_REGULATORY}")
    private String tc_RELIABILITY_REGULATORY;

    public String get_RELIABILITY_REGULATORY() {
    	qualityAttribute.putAttribute("\n#可靠性-可靠合规性\ntc.RELIABILITY_REGULATORY", tc_RELIABILITY_REGULATORY);
        return tc_RELIABILITY_REGULATORY;
    }

    /**
     *  可使用性、易理解性
     */
    @Value("${tc.USABILITY_COMPREHENSIBILITY}")
    private String tc_USABILITY_COMPREHENSIBILITY;

    public String get_USABILITY_COMPREHENSIBILITY() {
    	qualityAttribute.putAttribute("\n#可使用性-易理解性\ntc.USABILITY_COMPREHENSIBILITY", tc_USABILITY_COMPREHENSIBILITY);
        return tc_USABILITY_COMPREHENSIBILITY;
    }
    
    /**
     *  易学习性
     */
    @Value("${tc.USABILITY_EASYTOLEARN}")
    private String tc_USABILITY_EASYTOLEARN;

    public String get_USABILITY_EASYTOLEARN() {
    	qualityAttribute.putAttribute("\n#可使用性-易学习性\ntc.USABILITY_EASYTOLEARN", tc_USABILITY_EASYTOLEARN);
        return tc_USABILITY_EASYTOLEARN;
    }
    
    /**
     *  易操作性
     */
    @Value("${tc.USABILITY_OPERABILITY}")
    private String tc_USABILITY_OPERABILITY;

    public String get_USABILITY_OPERABILITY() {
    	qualityAttribute.putAttribute("\n#可使用性-易操作性\ntc.USABILITY_OPERABILITY", tc_USABILITY_OPERABILITY);
        return tc_USABILITY_OPERABILITY;
    }
    
    /**
     *  用户粘性
     */
    @Value("${tc.USABILITY_STICKINESS}")
    private String tc_USABILITY_STICKINESS;

    public String get_USABILITY_STICKINESS() {
    	qualityAttribute.putAttribute("\n#可使用性-用户粘性\ntc.USABILITY_STICKINESS", tc_USABILITY_STICKINESS);
        return tc_USABILITY_STICKINESS;
    }
    
    /**
     *  易用合规性
     */
    @Value("${tc.USABILITY_REGULATORY}")
    private String tc_USABILITY_REGULATORY;

    public String get_USABILITY_REGULATORY() {
    	qualityAttribute.putAttribute("\n#可使用性-易用合规性\ntc.USABILITY_REGULATORY", tc_USABILITY_REGULATORY);
        return tc_USABILITY_REGULATORY;
    }

    /**
     *  效率、时间特性
     */
    @Value("${tc.EFFICIENCY_TIMING}")
    private String tc_EFFICIENCY_TIMING;

    public String get_EFFICIENCY_TIMING() {
    	qualityAttribute.putAttribute("\n#效率-时间特性\ntc.EFFICIENCY_TIMING", tc_EFFICIENCY_TIMING);
        return tc_EFFICIENCY_TIMING;
    }
    
    /**
     *  资源利用率
     */
    @Value("${tc.EFFICIENCY_UTILIZATION}")
    private String tc_EFFICIENCY_UTILIZATION;

    public String get_EFFICIENCY_UTILIZATION() {
    	qualityAttribute.putAttribute("\n#效率-资源利用率\ntc.EFFICIENCY_UTILIZATION", tc_EFFICIENCY_UTILIZATION);
        return tc_EFFICIENCY_UTILIZATION;
    }
    
    /**
     *  效率合规性
     */
    @Value("${tc.EFFICIENCY_REGULATORY}")
    private String tc_EFFICIENCY_REGULATORY;

    public String get_EFFICIENCY_REGULATORY() {
    	qualityAttribute.putAttribute("\n#效率-效率合规性\ntc.EFFICIENCY_REGULATORY", tc_EFFICIENCY_REGULATORY);
        return tc_EFFICIENCY_REGULATORY;
    }

    /**
     *  可维护性、易分析性
     */
    @Value("${tc.MAINTAINABILITY_ANALYZABILITY}")
    private String tc_MAINTAINABILITY_ANALYZABILITY;

    public String get_MAINTAINABILITY_ANALYZABILITY() {
    	qualityAttribute.putAttribute("\n#可维护性-易分析性\ntc.MAINTAINABILITY_ANALYZABILITY", tc_MAINTAINABILITY_ANALYZABILITY);
        return tc_MAINTAINABILITY_ANALYZABILITY;
    }
    
    /**
     *  稳定性
     */
    @Value("${tc.MAINTAINABILITY_STABILITY}")
    private String tc_MAINTAINABILITY_STABILITY;

    public String get_MAINTAINABILITY_STABILITY() {
    	qualityAttribute.putAttribute("\n#可维护性-稳定性\ntc.MAINTAINABILITY_STABILITY", tc_MAINTAINABILITY_STABILITY);
        return tc_MAINTAINABILITY_STABILITY;
    }
    
    /**
     *  易变更性
     */
    @Value("${tc.MAINTAINABILITY_CHANGEABILITY}")
    private String tc_MAINTAINABILITY_CHANGEABILITY;

    public String get_MAINTAINABILITY_CHANGEABILITY() {
    	qualityAttribute.putAttribute("\n#可维护性-易变更性\ntc.MAINTAINABILITY_CHANGEABILITY", tc_MAINTAINABILITY_CHANGEABILITY);
        return tc_MAINTAINABILITY_CHANGEABILITY;
    }
    
    /**
     *  易测试性
     */
    @Value("${tc.MAINTAINABILITY_TESTABILITY}")
    private String tc_MAINTAINABILITY_TESTABILITY;

    public String get_MAINTAINABILITY_TESTABILITY() {
    	qualityAttribute.putAttribute("\n#可维护性-易测试性\ntc.MAINTAINABILITY_TESTABILITY", tc_MAINTAINABILITY_TESTABILITY);
        return tc_MAINTAINABILITY_TESTABILITY;
    }
    
    /**
     *  可维护合规性
     */
    @Value("${tc.MAINTAINABILITY_REGULATORY}")
    private String tc_MAINTAINABILITY_REGULATORY;

    public String get_MAINTAINABILITY_REGULATORY() {
    	qualityAttribute.putAttribute("\n#可维护性-可维护合规性\ntc.MAINTAINABILITY_REGULATORY", tc_MAINTAINABILITY_REGULATORY);
        return tc_MAINTAINABILITY_REGULATORY;
    }

    /**
     *  可移植性、适应性
     */
    @Value("${tc.PORTABILITY_ADAPTABILITY}")
    private String tc_PORTABILITY_ADAPTABILITY;

    public String get_PORTABILITY_ADAPTABILITY() {
    	qualityAttribute.putAttribute("\n#可移植性-适应性\ntc.PORTABILITY_ADAPTABILITY", tc_PORTABILITY_ADAPTABILITY);
        return tc_PORTABILITY_ADAPTABILITY;
    }
    
    /**
     *  易安装性
     */
    @Value("${tc.PORTABILITY_INSTALLATION}")
    private String tc_PORTABILITY_INSTALLATION;

    public String get_PORTABILITY_INSTALLATION() {
    	qualityAttribute.putAttribute("\n#可移植性-易安装性\ntc.PORTABILITY_INSTALLATION", tc_PORTABILITY_INSTALLATION);
        return tc_PORTABILITY_INSTALLATION;
    }
    
    /**
     *  共存性
     */
    @Value("${tc.PORTABILITY_COEXISTENCE}")
    private String tc_PORTABILITY_COEXISTENCE;

    public String get_PORTABILITY_COEXISTENCE() {
    	qualityAttribute.putAttribute("\n#可移植性-共存性\ntc.PORTABILITY_COEXISTENCE", tc_PORTABILITY_COEXISTENCE);
        return tc_PORTABILITY_COEXISTENCE;
    }
    
    /**
     *  易替换性
     */
    @Value("${tc.PORTABILITY_REPLACEABILITY}")
    private String tc_PORTABILITY_REPLACEABILITY;

    public String get_PORTABILITY_REPLACEABILITY() {
    	qualityAttribute.putAttribute("\n#可移植性-易替换性\ntc.PORTABILITY_REPLACEABILITY", tc_PORTABILITY_REPLACEABILITY);
        return tc_PORTABILITY_REPLACEABILITY;
    }
    
    /**
     *  可移植合规性
     */
    @Value("${tc.PORTABILITY_REGULATORY}")
    private String tc_PORTABILITY_REGULATORY;

    public String get_PORTABILITY_REGULATORY() {
    	qualityAttribute.putAttribute("\n#可移植性-可移植合规性\ntc.PORTABILITY_REGULATORY", tc_PORTABILITY_REGULATORY);
        return tc_PORTABILITY_REGULATORY;
    }
    
    /**
     *  报告地址
     */
    @Value("${tc.qualityURL}")
    private String tc_QUALITY_URL;

	public String getTC_QUALITY_URL() {
		qualityAttribute.putAttribute("\n#可移植性-报告地址\ntc.qualityURL", tc_QUALITY_URL);
		return tc_QUALITY_URL;
	}
}
