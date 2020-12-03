package AutoTest;

/**
 *  边界值
 */
public class GValue {
	private GValue(){
		GLog.logShowConsole("This is a tool class.");
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
	 *  预签合同原文错误临时值
	 */
	public static final String SIGNATTR_ERR = "MWkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTcwMzA5MDkxOTU1WjAvBgkqhkiG9w0BCQQxIgQgmn5Zzs0uRe+SFcFFHcnW50q1DDF26GscX3lrohr1mMw=";
}
