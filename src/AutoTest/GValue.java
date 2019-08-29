package AutoTest;

import java.util.Random;

/**
 *  边界值
 */
public class GValue {
	private GValue(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  随机字符串组成元素
	 */
	public static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	/**
	 *  预签合同原文错误临时值
	 */
	public static final String SIGNATTR_ERR = "MWkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTcwMzA5MDkxOTU1WjAvBgkqhkiG9w0BCQQxIgQgmn5Zzs0uRe+SFcFFHcnW50q1DDF26GscX3lrohr1mMw=";

	/**
	 *  按照长度值和组成字母获取指定字符串
	 *  
	 *  @param length
	 *  @param tChar
	 *  @return 返回目标字符串
	 */
	public static String getRandomStringByLength(int length, String tChar) {
		String randomStringByLength = "";
		
		for (int i = 0; i < length; i++) {
			randomStringByLength = randomStringByLength + tChar;
		}

		return randomStringByLength;
	}
	
	/**
	 *  按照长度值和取值范围获取随机字符串
	 *  
	 *  @param length
	 *  @return 返回目标字符串
	 */
	public static String getRandomStringByLength(int length) {		
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < length; i++) {
	        int number = random.nextInt(CHARS.length());
	        sb.append(CHARS.charAt(number));
	    }

		return sb.toString();
	}
}
