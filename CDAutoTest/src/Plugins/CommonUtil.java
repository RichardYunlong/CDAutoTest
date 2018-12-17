package Plugins;

import java.io.File;

import AutoTest.GConfig;

public class CommonUtil {
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}

	public static boolean isNotEmpty(String str) {
		return (str != null && str.trim().length() != 0);
	}

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

	public static String getConfigPath() {
		return System.getProperty(SystemConst.CONFIG_LOCATION) + File.separator;
	}

	public static void dealException(Throwable e, String message) {
		System.out.println("*****************" + message + "***************");
		e.printStackTrace();
		System.exit(0);
	}
}