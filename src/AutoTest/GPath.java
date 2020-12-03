package AutoTest;

/**
 *  全局路径
 */
public class GPath {
	private GPath(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  日志路径-保存日志路径
	 */
	public static String[] strPathStyle = new String[10];
	
	/**
	 *  日志路径-根目录设置
	 */
	public static final String LOGHOME = System.getProperty("user.dir") + "/test";
	
	/**
	 *  日志路径-初始化
	 */
	public static void initGPath() {
		for (int i = 0; i < 10; i++) {
			strPathStyle[i] = LOGHOME;// 输出根目录默认为测试机系统桌面
		}
	}
	
	/**
	 *  XLS输入文件-存储位置
	 */
	public static final String INPUT_XLS_PATH = "./input/";
	
	/**
	 *  XLS输入文件-文件名
	 */
	public static final String INPUT_XLS_NAME = "testcase.xls";
	
	/**
	 *  XLS输出文件-存储位置
	 */
	public static final String OUTPUT_XLS_PATH = "./test/";
	
	/**
	 *  XLS输出文件-文件名
	 */
	public static final String OUTPUT_XLS_NAME = "output.xls";
	
	/**
	 *  TXT输入文件-存储位置
	 */
	public static final String INPUT_TXT_PATH = "./input/";
	
	/**
	 *  TXT输入文件-文件名
	 */
	public static final String INPUT_TXT_NAME = "testcase.txt";
	
	/**
	 *  html版本测试报告模板-存储位置
	 */
	public static final String REPORT_TEMP = "./config/template.html";
	
	/**
	 *  html版本测试报告帮助文件-存储位置
	 */
	public static final String HELPER_TEMP = "./config/help.html";
	
	/**
	 *  html版本测试报告-存储位置
	 */
	public static final String REPORT_PATH = "./report/";
	
	/**
	 * image版本测试报告-存储位置
	 */
	public static final String IMAGE_PATH = REPORT_PATH + "/images/";
	
	/**
	 *  html版本测试报告-文件全名
	 */
	public static final String REPORT_NAME = REPORT_PATH + GParam.strTestVersion + "_TESTREPORT_" + GTime.getCurrentTime(GTime.FORMAT_14) + ".html";
	
	/**
	 *  html版本测试报告帮助-文件全名
	 */
	public static final String REPORT_NAME_HELPER = REPORT_PATH + "help.html";
	
	/**
	 *  测试结果打包-文件全名
	 */
	public static final String BACKUPFILE = GPath.strPathStyle[9] + "backup.zip";
}
