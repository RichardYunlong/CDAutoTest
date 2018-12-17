package AutoTest;

public class GPath {
	/**
	 * @see 路径类型：0-输出根目录
	 */
	public static String[] PathStyle = new String[10];

	/**
	 * @see 初始化所有路径配置
	 */
	GPath() {
		for (int i = 0; i < 10; i++) {
			PathStyle[i] = GSys.GetCurUserDesktopURL() + "\\test";// 输出根目录默认为测试机系统桌面
		}
	}
}
