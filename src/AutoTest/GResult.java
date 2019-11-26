package AutoTest;

/**
 * 执行结果
 */
public class GResult {
	private GResult(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  是否在测试完成后自动打开测试报告
	 */
	public static boolean bAutoCheckReport = false;
	
	/**
	 *  执行结果输出缓存区
	 */
	public static String[][] strResultTSNO = null;
	
	/**
	 *  执行结果输出缓存区字段数量最大值
	 */
	public static final int TSRESULT_FIELD_MAX = 11;
	
	/**
	 *  执行结果输出缓存区初始化。当没有测试用例输入时，默认创建一个1024长度的
	 */
	public static void initGResult() {
		int curTestTotalNo = 0;
		if(GProgress.getTestTotalNo() <= 0) {
			curTestTotalNo = 1024;
		}else {
			curTestTotalNo = GProgress.getTestTotalNo();
		}
		strResultTSNO = new String[curTestTotalNo][TSRESULT_FIELD_MAX];
		for (int i = 0; i < GProgress.getTestTotalNo(); i++) {
			for(int j = 0; j < TSRESULT_FIELD_MAX; j++) {
				strResultTSNO[i][j] = "empty";
			}	
		}
	}
}
