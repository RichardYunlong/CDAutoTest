package AutoTestWeb;

/**
 *  浏览器设置
 */
public class GBrowser {
	/**
	 *  系统自检是否就绪
	 */
	private static String BrsType = "";
	
	/**
	 *  获取当前浏览器类型
	 */
	public static String getBrsType() {
		return BrsType;
	}
	
	/**
	 *  设置当前浏览器类型
	 */
	public static void setBrsType(String strType) {
		BrsType = strType;
	}
}
