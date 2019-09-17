package AutoTest;

/**
 *  测试进度
 */
public class GProgress {
	private GProgress(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 * 【核心数据结构】
	 *  输入参数文件缓存-Strin[][]g类型
	 *  用例输入详情：按照“行列”的形式，将输入参数文件全部读入到缓存中，包含参数文件的【字段名】行和【序号】列
	 */
	public static String[][] strTestCaseInputArray = null;
	
	/**
	 *  设置用例的数组行列值
	 *  
	 *  @param paramNum 单个用例参数个数
	 *  @param testCaseNum 用例总个数
	 */
	public static void initParamAndTestCaseNum(int paramNum, int testCaseNum) {
		if((paramNum > 0) && (testCaseNum > 0)) {
			GProgress.setTestTotalNo(testCaseNum);
			strTestCaseInputArray = new String[testCaseNum][paramNum];
		}
		
		for (int i = 0; i < testCaseNum; i++) {
			for (int j = 0; j < paramNum; j++) {
				strTestCaseInputArray[i][j] = "empty";
			}
		}
	}
	
	/**
	 *  使用某种用例参数输入方式下，共读取到的用例总数
	 */
	private static Integer dTestTotalNo = 0;
	
	/**
	 *  设置计划执行用例总数
	 *  
	 *  @param dNum 形参
	 */
	public static void setTestTotalNo(int dNum) {
		dTestTotalNo = dNum;
	}
	
	/**
	 *  获得计划执行用例总数
	 *  
	 *  @return 返回已加载的值
	 */
	public static int getTestTotalNo() {
		return dTestTotalNo;
	}
	
	/**
	 *  已执行有效类个数
	 */
	public static Integer dTestReal = 0;
	
	/**
	 *  已执行失败类个数
	 */
	public static Integer dTestFail = 0;
	
	/**
	 *  已执行异常场景类个数
	 */
	public static Integer dTestUnReal = 0;
	
	/**
	 *  已执行中断类个数
	 */
	public static Integer dTestUnDo = 0;
	
	/**
	 *  已跳过个数
	 */
	public static Integer dTestJump = 0;
}
