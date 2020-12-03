package AutoTest;

import java.io.File;

/**
 *  常规处理
 */
public class GCommonUtil {
	private GCommonUtil(){
		GLog.logShowConsole("This is a tool class.");
	}
	
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
		GLog.logShowConsole(GText.getRandomStringByLength(68, "*"));
		GLog.logShowConsole("*" + GText.getRandomStringByLength(66, " ") + "*");
		GLog.logShowConsole("*" + GText.getRandomStringByLength(66, " ") + "*");
		String strWelcome = "hi!!";
		GLog.logShowConsole("*" + GText.getRandomStringByLength(30, " ") + strWelcome + GText.getRandomStringByLength(30, " ") + "*");
		int dLeft = (68-GLoadConfig.strWelcomeStr.length())/2;
		GLog.logShowNoEnter("*");
		for(int i=0;i<dLeft-1;i++) {
			GLog.logShowNoEnter(" ");
		}
		GLog.logShowNoEnter(GLoadConfig.strWelcomeStr);
		for(int i=0;i<dLeft-1;i++) {
			GLog.logShowNoEnter(" ");
		}
		GLog.logShowNoEnter("*");
		GLog.logShowConsole("*" + GText.getRandomStringByLength(66, " ") + "*");
		GLog.logShowConsole("*" + GText.getRandomStringByLength(66, " ") + "*");
		GLog.logShowConsole(GText.getRandomStringByLength(68, "*"));
	}

	/**
	 *  获取配置文件路径
	 */
	public static String getConfigPath() {
		return System.getProperty(GSystemConst.CONFIG_LOCATION) + File.separator;
	}
	
	/**
	 *  文本解码：此处未使用加密参数
	 */
	public static String decodeStr(String inputStr) {
		String decodeResult = null;
		if (null != inputStr && !"".equals(inputStr)) {
			decodeResult = inputStr;
		}
		return decodeResult;
	}
}
