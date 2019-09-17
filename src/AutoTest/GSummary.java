package AutoTest;

public class GSummary {
	private GSummary(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	
	/**
	 *  各类用例数占总用例数份额：0-执行用例总数；1-正常场景用例执行数；2-失败场景用例执行数；3-错误码场景用例执行数；4-异常中断场景用例执行数
	 */
	public static float[] Proportion_Total = {1.00f, 0.25f, 0.25f, 0.25f, 0.25f};
	
	/**
	 *  各类用例数实际执行数占计划执行数的比例
	 */
	public static float[] Ratio_Each = {1.00f, 1.00f, 1.00f, 1.00f, 1.00f};
	
	/**
	 *  计算各类用例数占总用例数份额
	 */
	public static void CalculateProportionTotal() {

	}
	
	/**
	 *  各类用例数实际执行数占计划执行数的比例
	 */
	public static void CalculateRatioEach() {

	}
}
