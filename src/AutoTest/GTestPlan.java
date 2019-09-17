package AutoTest;

public class GTestPlan {
	private GTestPlan(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  XLS输入文件目录
	 */
	public static final String INPUT_XLS_PATH = "./input/";
	
	/**
	 *  XLS输入文件文件名
	 */
	public static final String INPUT_XLS_NAME = "testcase.xls";
	
	/**
	 *  TXT输入文件目录
	 */
	public static final String INPUT_TXT_PATH = "./input/";
	
	/**
	 *  TXT输入文件文件名
	 */
	public static final String INPUT_TXT_NAME = "testcase.txt";
	
	/**
	 *  html版本测试报告模板位置
	 */
	public static final String REPORT_TEMP = "./config/template.html";
	
	/**
	 *  html版本测试报告帮助文件位置
	 */
	public static final String HELPER_TEMP = "./config/help.html";
	
	/**
	 *  html版本测试报告存储位置
	 */
	public static final String REPORT_PATH = "./report/";
	
	/**
	 *  html版本测试报告文件全名
	 */
	public static final String REPORT_NAME = REPORT_PATH + GParam.strTestVersion + "_TESTREPORT_" + GTime.getCurrentTime(GTime.FORMAT_14) + ".html";
	
	/**
	 *  html版本测试报告帮助文件全名
	 */
	public static final String REPORT_NAME_HELPER = REPORT_PATH + "help.html";
	
	/**
	 *   可以设置将读入的参数表打印只特定的日志文档的条数，此项数字越大，执行速度越慢
	 */
	public static int dRecordInputParamListInTxt = 0;
	
	/**
	 *  是否备份测试结果
	 */
	public static boolean bTestOutputBackupResult = false;
	
	/**
	 *  是否启用备份
	 */
	public static boolean bIsBackup = false;
	
	/**
	 *  测试结果打包名称
	 */
	public static final String BACKUPFILE = GPath.strPathStyle[9] + "backup.zip";// 
	
	/**
	 *  单个用例参数个数上限
	 */
	public static final int PARAM_NUM_MAX = 32;
	
	/**
	 *  单次测试执行总数最大值
	 */
	public static final int CASE_NUM_MAX = 250000;
	
	/**
	 *  计划用例总数
	 */
	public static int dTestCaseNum = 0;
	
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
