package AutoTest;

/**
 * 执行结果
 */
public class GResult {
	
	/**
	 * 
	 */
	private GResult(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  执行结果输出缓存区
	 *  
	 *  第二维
	 *  0-响应码
	 *  1-响应信息
	 *  2-是否通过
	 *  3-用例类型
	 *  4-用例等级
	 *  5-
	 *  6-
	 *  7-
	 *  8-
	 *  9-
	 *  10-
	 *  11-
	 */
	public static String[][] RESULT_STR = null;
	
	/**
	 *  执行结果输出缓存区字段数量最大值
	 */
	private static int RESULT_ROW_MAX = 0;
	
	/**
	 *  执行结果输出缓存区字段数量最大值
	 */
	public static final int RESULT_COL_MAX = 11;
	
	/**
	 *  执行结果输出缓存区初始化。当没有测试用例输入时，默认创建一个1024长度的
	 */
	public static void initRESULT_STR() {
		if(GProgress.getTCTotalNum() <= 0) {
			RESULT_ROW_MAX = 1024;
		}else {
			RESULT_ROW_MAX = GProgress.getTCTotalNum();
		}
		RESULT_STR = new String[RESULT_ROW_MAX][RESULT_COL_MAX];
		for (int i = 0; i < GProgress.getTCTotalNum(); i++) {
			for(int j = 0; j < RESULT_COL_MAX; j++) {
				RESULT_STR[i][j] = "";
			}	
		}
	}
}
