package AutoTest;

/**
 *  边界值
 */
public class GValue {
	private GValue(){
		System.out.println("This is a tool class.");
	}

	/**
	 *  预签合同原文错误临时值
	 */
	public static final String SIGNATTR_ERR = "MWkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTcwMzA5MDkxOTU1WjAvBgkqhkiG9w0BCQQxIgQgmn5Zzs0uRe+SFcFFHcnW50q1DDF26GscX3lrohr1mMw=";

	/**
	 *  按照长度值和组成字母获取字符串
	 */
	public static String getRandomStringByLength(int length, String tChar) {
		String RandomStringByLength = "";
		
		for (int i = 0; i < length; i++) {
			RandomStringByLength = RandomStringByLength + tChar;
		}

		return RandomStringByLength;
	}
}
