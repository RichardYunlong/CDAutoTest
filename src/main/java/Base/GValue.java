package main.java.Base;

/**
 *  边界值
 */
public class GValue {

    /**
     *  构造函数
     */
	private GValue(){
		GClazz.thisAToolClass();
	}

	/**
	 *  单个用例参数个数上限
	 */
	public static final int PARAM_NUM_MAX = 32;
	
	/**
	 *  单次测试执行总数最大值
	 */
	public static final int CASE_NUM_MAX = 250000;
	
	/**
	 *  单次配置参数总数最大值
	 */
	public static final int INI_NUM_MAX = 2000;
	
	/**
	 *  预签合同原文错误临时值
	 */
	public static final String SIGNATTR_ERR = "MWkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTcwMzA5MDkxOTU1WjAvBgkqhkiG9w0BCQQxIgQgmn5Zzs0uRe+SFcFFHcnW50q1DDF26GscX3lrohr1mMw=";
}
