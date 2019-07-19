package AutoTest;

/**
 *  全局路径
 */
public class GPath {
	private GPath(){
		System.out.println("This is a tool class.");
	}
	
	/**
	 *  日志路径存储位置
	 */
	public static String[] PathStyle = new String[10];
	
	/**
	 *  日志路径根目录
	 */
	public static final String LOGHOME = System.getProperty("user.dir") + "/test";
	
	/**
	 *  初始化所有路径配置
	 */
	public static void initGPath() {
		for (int i = 0; i < 10; i++) {
			PathStyle[i] = LOGHOME;// 输出根目录默认为测试机系统桌面
		}
	}
}
