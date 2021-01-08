package AutoTest;

import java.text.SimpleDateFormat;

/**
 *  报告摘要
 */
public class GSummary {
	private GSummary(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  被测件名称
	 */
	public static String TAR_NAME = "";
	
	/**
	 *  被测件版本号
	 */
	public static String TAR_VERSION = "";
	
	/**
	 *  测试执行时间时间
	 */
	public static String TAR_DATE = "";
	
	/**
	 *  测试时间
	 */
	public static SimpleDateFormat TAR_DATEFORMAT = null;
	
	/**
	 *  测试开始时间
	 */
	public static String TAR_STARTDATE = "";
	
	/**
	 *  测试结束时间
	 */
	public static String TAR_ENDDATE = "";
	
	/**
	 *  测试耗时
	 */
	public static String TAR_SPENDTIME = "";
	
	/**
	 *  读取用例总数
	 */
	public static String TAR_LOADTOTALNO = "";
	
	/**
	 *  执行用例总数
	 */
	public static String TAR_RUNTOTALNO = "";
	
	
	/**
	 *  各类用例数占总用例数份额：0-执行用例总数；1-正常场景用例执行数；2-失败场景用例执行数；3-错误码场景用例执行数；4-异常中断场景用例执行数
	 */
	public static float[] Proportion_Total = {1.00f, 0.25f, 0.25f, 0.25f, 0.25f};
	
	/**
	 *  各类用例数占总用例数份额String类型：0-执行用例总数；1-正常场景用例执行数；2-失败场景用例执行数；3-错误码场景用例执行数；4-异常中断场景用例执行数
	 */
	public static String[] strProportion_Total = {"1.00", "0.25", "0.25", "0.25", "0.25"};
	
	/**
	 *  各类用例数实际执行数占计划执行数的比例
	 */
	public static int[] FailNum_Each = {0, 0, 0, 0, 0};
	
	/**
	 *  各类用例数实际执行失败数
	 */
	public static String[] strFailNum_Each = {"0", "0", "0", "0", "0"};
	
	/**
	 *  计算各类用例数占总用例数份额
	 */
	private static void CalculateProportionTotal() {
		
		if(GProgress.getTCTotalNum() > 0) {
			Proportion_Total[0] = (float)(
					(GTestPlan.dTestReal.floatValue() 
					+ GTestPlan.dTestReal.floatValue() 
					+ GTestPlan.dTestUnReal.floatValue() 
					+ GTestPlan.dTestUnDo.floatValue()
					)
					/((Integer.valueOf(GProgress.getTCTotalNum())).floatValue()));
			Proportion_Total[1] = (float)(GTestPlan.dTestReal.floatValue()/((Integer.valueOf(GProgress.getTCTotalNum())).floatValue()));
			Proportion_Total[2] = (float)(GTestPlan.dTestFail.floatValue()/((Integer.valueOf(GProgress.getTCTotalNum())).floatValue()));
			Proportion_Total[3] = (float)(GTestPlan.dTestUnReal.floatValue()/((Integer.valueOf(GProgress.getTCTotalNum())).floatValue()));
			Proportion_Total[4] = (float)(GTestPlan.dTestUnDo.floatValue()/((Integer.valueOf(GProgress.getTCTotalNum())).floatValue()));
		}
		
		for(int i = 0; i<Proportion_Total.length; i++) {
			strProportion_Total[i] = String.valueOf(Proportion_Total[i]);
		}
		
	}
	
	/**
	 *  各类用例数实际执行失败数统计
	 */
	private static void CalculateFailNumEach() {
		FailNum_Each[0] = GProgress.getTCTotalNum() - 
						(GProgress.dTestReal.intValue() 
						+ GProgress.dTestFail.intValue() 
						+ GProgress.dTestUnReal.intValue() 
						+ GProgress.dTestUnDo.intValue());
		FailNum_Each[1] = GTestPlan.dTestReal.intValue() - GProgress.dTestReal.intValue();
		FailNum_Each[2] = GTestPlan.dTestFail.intValue() - GProgress.dTestFail.intValue();
		FailNum_Each[3] = GTestPlan.dTestUnReal.intValue() - GProgress.dTestUnReal.intValue();
		FailNum_Each[4] = GTestPlan.dTestUnDo.intValue() - GProgress.dTestUnDo.intValue();
		
		for(int i = 0; i<FailNum_Each.length; i++) {
			strFailNum_Each[i] = String.valueOf(FailNum_Each[i]);
		}
	}
	
	/**
	 *  加载概要
	 */
	public static void LoadSummary() {
		TAR_NAME = GParam.gVersion;
		TAR_VERSION = GParam.gVersion;
		TAR_DATE = GTime.getCurrentTime(GTime.FORMAT_14_TEXT);
		TAR_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TAR_STARTDATE = TAR_DATEFORMAT.format(GTestMission.startSysTime);
		TAR_ENDDATE = TAR_DATEFORMAT.format(GTestMission.endSysTime);
		TAR_SPENDTIME = String.valueOf(GTestMission.endSysTime - GTestMission.startSysTime) + "MS";
		TAR_LOADTOTALNO = String.valueOf(GProgress.getTCTotalNum());
		TAR_RUNTOTALNO = String.valueOf(GProgress.dTestReal + GProgress.dTestFail + GProgress.dTestUnReal + GProgress.dTestUnDo);
		
		CalculateProportionTotal();
		CalculateFailNumEach();
	}
}
