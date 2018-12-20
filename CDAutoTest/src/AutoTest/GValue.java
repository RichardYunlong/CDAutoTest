package AutoTest;

/**
 *  边界值
 */
public class GValue {

	/**
	 *  保存随机字符串
	 */
	public static String RandomStringByLength = "";

	/**
	 *  预签合同原文错误临时值
	 */
	public static final String NewSignContractSignatureAttr_Error = "MWkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTcwMzA5MDkxOTU1WjAvBgkqhkiG9w0BCQQxIgQgmn5Zzs0uRe+SFcFFHcnW50q1DDF26GscX3lrohr1mMw=";

	public GValue() {
	}

	/**
	 *  按照长度值和组成字母获取字符串
	 */
	public static String getRandomStringByLength(int length, String tChar) {
		for (int i = 0; i < length; i++) {
			RandomStringByLength = RandomStringByLength + tChar;
		}

		return RandomStringByLength;
	}
}
