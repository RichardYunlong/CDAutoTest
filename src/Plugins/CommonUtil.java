package Plugins;

import java.io.File;

import AutoTest.GConfig;
import AutoTest.GValue;

/**
 *  常用标准化方法
 */
public class CommonUtil {
	
	/**
	 *  为空判断
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 *  不为空判断
	 */
	public static boolean isNotEmpty(String str) {
		return (str != null && str.trim().length() != 0);
	}

	/**
	 *  欢迎文本
	 */
	public static void welcome() {
		System.out.println(GValue.getRandomStringByLength(68, "*"));
		System.out.println("*" + GValue.getRandomStringByLength(66, " ") + "*");
		System.out.println("*" + GValue.getRandomStringByLength(66, " ") + "*");
		String strWelcome = "hi!!";
		System.out.println("*" + GValue.getRandomStringByLength(30, " ") + strWelcome + GValue.getRandomStringByLength(30, " ") + "*");
		int dLeft = (68-GConfig.WelcomeStr.length())/2;
		System.out.print("*");
		for(int i=0;i<dLeft-1;i++) {
			System.out.print(" ");
		}
		System.out.print(GConfig.WelcomeStr);
		for(int i=0;i<dLeft-1;i++) {
			System.out.print(" ");
		}
		System.out.print("*");
		System.out.println("*" + GValue.getRandomStringByLength(66, " ") + "*");
		System.out.println("*" + GValue.getRandomStringByLength(66, " ") + "*");
		System.out.println(GValue.getRandomStringByLength(68, "*"));
	}

	/**
	 *  获取配置文件路径
	 */
	public static String getConfigPath() {
		return System.getProperty(SystemConst.CONFIG_LOCATION) + File.separator;
	}

	/**
	 *  异常处理
	 */
	public static void dealException(Throwable e, String message) {
		System.out.println("*****************" + message + "***************");
		e.printStackTrace();
		System.exit(0);
	}
	
	/**
	 *  文本解码：此处未使用加密参数
	 */
	public static String decodeStr(String inputStr) {
		String decodeResult = null;
		if (null != inputStr && !"".equals(inputStr)) {
			decodeResult = new String(inputStr);
		}
		return decodeResult;
	}
}