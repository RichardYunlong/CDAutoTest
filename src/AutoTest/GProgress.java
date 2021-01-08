package AutoTest;

/**
 *  进度
 */
public class GProgress {
	private GProgress(){
		GLog.logShowConsole("This is a tool class.");
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
	public static void setTCTotalNum(int dNum) {
		dTestTotalNo = dNum;
	}
	
	/**
	 *  获得计划执行用例总数
	 *  
	 *  @return 返回已加载的值
	 */
	public static int getTCTotalNum() {
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
