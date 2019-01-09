package AutoTestWeb;

/**
 *  用例管理
 */
public class GTestCase {
	
	/**
	 *  测试名称或简单描述
	 */
	private static String TestScripstion = "";
	
	/**
	 * 设置输出路径
	 */
	public static void setTestScripstion(String src) {
		TestScripstion = src;
	}
	
	/**
	 * 获得输出路径
	 */
	public static String getTestScripstion() {
		return TestScripstion;
	}
}
