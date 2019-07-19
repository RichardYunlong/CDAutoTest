package AutoTest;

/**
 *  执行结果管理
 */
public class GError {

	private GError(){
		System.out.println("This is a tool class.");
	}
	
	/**
	 *  执行结果输出缓存区
	 */
	public static String[][] TSRESULT_TSNO = null;
	
	/**
	 *  执行结果输出缓存区字段数量最大值
	 */
	public static final int TSRESULT_FIELD_MAX = 11;
	
	/**
	 *  执行结果输出缓存区初始化
	 */
	public static void initGError() {
		TSRESULT_TSNO = new String[GParam.curCaseNO_MAX][TSRESULT_FIELD_MAX];
		for (int i = 0; i < GParam.getTestCaseNum_MAX(); i++) {
			for(int j = 0; j < TSRESULT_FIELD_MAX; j++) {
				TSRESULT_TSNO[i][j] = "empty";
			}	
		}
	}
	
	/**
	 *  执行结果输出缓存区初始化
	 */
	public static void resetGError() {
		TSRESULT_TSNO = new String[GParam.curCaseNO_MAX][TSRESULT_FIELD_MAX];
		for (int i = 0; i < GParam.getTestCaseNum_MAX(); i++) {
			for(int j = 0; j < TSRESULT_FIELD_MAX; j++) {
				TSRESULT_TSNO[i][j] = "empty";
			}	
		}
	}
}
