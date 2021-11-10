package Test;

import Base.GClazz;
import Sys.GSys;

/**
 * 计划管理
 */
public class GTestPlan {
	
    /**
     *  构造函数
     */
	private GTestPlan(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  可以设置将读入的参数表打印只特定的日志文档的条数，此项数字越大，执行速度越慢
	 */
	private static int recordInputParamListInTxt = 0;
	
	/**
	 *  用例之间的时间间隔
	 */
	private static int timeWait = 0;
	
	/**
	 *  是否启用备份
	 */
	private static boolean bIsBackup = false;
	
	/**
	 *  计划用例总分类标记
	 */
	private static String testCaseType = "";

	/**
	 *  计划用例总数
	 */
	private static int testCaseNum = 0;
	
	/**
	 *  计划执行有效类个数
	 */
	private static Integer testReal = 0;
	
	/**
	 *  计划执行失败类个数
	 */
	private static Integer testFail = 0;
	
	/**
	 * 计划执行异常场景类个数
	 */
	private static Integer testUnReal = 0;
	
	/**
	 *  计划执行中断类个数
	 */
	private static Integer testUnDo = 0;
	
	public static int getRecordInputParamListInTxt() {
		return recordInputParamListInTxt;
	}

	public static void setRecordInputParamListInTxt(int recordInputParamListInTxt) {
		GTestPlan.recordInputParamListInTxt = recordInputParamListInTxt;
	}

	public static int getTimeWait() {
		return timeWait;
	}

	public static void setTimeWait(int timeWait) {
		GTestPlan.timeWait = timeWait;
	}

	public static boolean isbIsBackup() {
		return bIsBackup;
	}

	public static void setbIsBackup(boolean bIsBackup) {
		GTestPlan.bIsBackup = bIsBackup;
	}
	
	public static String getTestCaseType() {
		return testCaseType;
	}

	public static void setTestCaseType(String testCaseType) {
		GTestPlan.testCaseType = testCaseType;
	}

	public static int getTestCaseNum() {
		return testCaseNum;
	}

	public static void setTestCaseNum(int testCaseNum) {
		GTestPlan.testCaseNum = testCaseNum;
	}

	public static Integer getTestReal() {
		return testReal;
	}
	
	public static void setTestReal(Integer testReal) {
		GTestPlan.testReal = testReal;
	}

	public static Integer getTestFail() {
		return testFail;
	}

	public static void setTestFail(Integer testFail) {
		GTestPlan.testFail = testFail;
	}

	public static Integer getTestUnReal() {
		return testUnReal;
	}

	public static void setTestUnReal(Integer testUnReal) {
		GTestPlan.testUnReal = testUnReal;
	}

	public static Integer getTestUnDo() {
		return testUnDo;
	}

	public static void setTestUnDo(Integer testUnDo) {
		GTestPlan.testUnDo = testUnDo;
	}
	
	/**
	 *  加载参数
	 */	
	public static void loadConfig() {
		if(recordInputParamListInTxt >= 0){
			;
		}else {
			GSys.logErrorSys("One of these GTestPlan params from sysConfig may has no value, Please check again!");
			System.exit(0);
		}
	}

	/**
	 *  根据用例类型编码增加对应计数
	 */
	public static void TestPlanNumPlus(String strType) {
		int dType = Integer.valueOf(strType).intValue();
		
		switch(dType) {
			case 0:{
				testReal++;
				break;
			}
			case 2:{
				testUnReal++;
				break;
			}
			case 3:{
				testUnDo++;
				break;
			}
			default:{
				testFail++;
				break;
			}
		}	
	}
}
