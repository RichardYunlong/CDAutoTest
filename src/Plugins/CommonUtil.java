package Plugins;

import java.io.File;

import AutoTest.GConfig;

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
		System.out.println("*******************************************************************");
		System.out.println("*                                                                 *");
		System.out.println("************************欢迎使用RA客户端测试工具*************************");
		System.out.println("*                                                                 *");
		int dLeft = (68-GConfig.WelcomeStr.length())/2;
		for(int i=0;i<dLeft;i++) {
			System.out.print("*");
		}
		System.out.print(GConfig.WelcomeStr);
		for(int i=0;i<dLeft;i++) {
			System.out.print("*");
		}
		System.out.println("*                                                                 *");
		System.out.println("*******************************************************************");
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