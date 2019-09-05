package AutoTest;

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
	 *  执行结果输出缓存区初始化
	 */
	public static void initGError() {
		strResultTSNO = new String[GParam.getTestCaseNum()][TSRESULT_FIELD_MAX];
		for (int i = 0; i < GParam.getTestCaseNum(); i++) {
			for(int j = 0; j < TSRESULT_FIELD_MAX; j++) {
				strResultTSNO[i][j] = "empty";
			}	
		}
	}
}
