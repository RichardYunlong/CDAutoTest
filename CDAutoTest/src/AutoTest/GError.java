package AutoTest;

/**
 *  执行结果管理
 */
public class GError {

	/**
	 *  执行结果输出缓存区
	 */
	public static String[][] TSRESULT_TSNO = null;

	/**
	 *  执行结果输出缓存区初始化
	 */
	public GError() {
		TSRESULT_TSNO = new String[GParam.curCaseNO_MAX][5];
		for (int i = 0; i < GParam.getTestCaseNum_MAX(); i++) {
			TSRESULT_TSNO[i][0] = "empty";
			TSRESULT_TSNO[i][1] = "empty";
			TSRESULT_TSNO[i][2] = "empty";
			TSRESULT_TSNO[i][3] = "empty";
			TSRESULT_TSNO[i][4] = "empty";
		}
	}

	/**
	 *  执行结果输出缓存区重置
	 */
	public static void resetGError() {
		TSRESULT_TSNO = new String[GParam.getTestCaseNum_MAX()][5];
		for (int i = 0; i < GParam.getTestCaseNum_MAX(); i++) {
			TSRESULT_TSNO[i][0] = "empty";
			TSRESULT_TSNO[i][1] = "empty";
			TSRESULT_TSNO[i][2] = "";
			TSRESULT_TSNO[i][3] = "接口测试";
			TSRESULT_TSNO[i][4] = "";
		}
	}

}
