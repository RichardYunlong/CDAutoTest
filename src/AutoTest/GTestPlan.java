package AutoTest;

public class GTestPlan {
	private GTestPlan(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  计划执行有效类个数
	 */
	public static Integer dTestReal = 0;
	
	/**
	 *  计划执行失败类个数
	 */
	public static Integer dTestFail = 0;
	
	/**
	 * 计划执行异常场景类个数
	 */
	public static Integer dTestUnReal = 0;
	
	/**
	 *  计划执行中断类个数
	 */
	public static Integer dTestUnDo = 0;
	
	/**
	 *  根据用例类型编码增加对应计数
	 */
	public static void TestPlanNumPlus(String strType) {
		int dType = Integer.valueOf(strType).intValue();
		
		switch(dType) {
			case 0:{
				dTestReal++;
				break;
			}
			case 2:{
				dTestUnReal++;
				break;
			}
			case 3:{
				dTestUnDo++;
				break;
			}
			default:{
				dTestFail++;
				break;
			}
		}	
	}
}
