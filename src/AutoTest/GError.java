package AutoTest;

/**
 *  执行结果管理
 */
public class GError {

	private GError(){
		GLog.logShowConsole("This is a tool class.");
	}
	
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
		strResultTSNO = new String[GParam.curCaseNumMAX][TSRESULT_FIELD_MAX];
		for (int i = 0; i < GParam.getTestCaseNumMAX(); i++) {
			for(int j = 0; j < TSRESULT_FIELD_MAX; j++) {
				strResultTSNO[i][j] = "empty";
			}	
		}
	}
}
