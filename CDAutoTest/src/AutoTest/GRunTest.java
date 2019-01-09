package AutoTest;

import TestDemo.TestRunReal;

/**
 *  运行主体
 */
public class GRunTest {
	
	/**
	 *  系统开始时间
	 */
	public static long startSysTime = System.currentTimeMillis();
	
	/**
	 *  系统结束时间
	 */
	public static long endSysTime;
	
	/**
	 *  业务开始时间
	 */
	public static long startTime = System.currentTimeMillis();
	
	/**
	 *  当前用例编号
	 */
	public static String curTSNO = "";
	
	/**
	 *  初始化系统
	 */
	private static void SysInit() {
		GParam.TestVersion = "Demo 1001";// 被测件名称及版本号

		// 初始化系统环境
		GSys gSys = new GSys();
		if (!gSys.initTestSys()) {
			GLog.GLogDoReady("PREPARE TESTING ENVIRONMENT FAILED");
		}
	}
	
	/**
	 *  启动日志
	 */
	private static void LogOn() {
		// 开始日志记录
		GLog.GLogOn();
	}
	
	/**
	 *  加载预置错误码
	 */
	private static void PreErrorCode() {
		// 预置错误码表
		GPreErrorCode.PreErrorCode("./config/errorcode", ".txt");
	}
	
	/**
	 *  加载用例输入参数表
	 */
	private static void DateProvider() {
		GTSNO LTS = new GTSNO();
		LTS.GTSNOS_LIST(1);
	}
	
	/**
	 *  输出执行结果
	 */
	private static void OutputTestReport() {
		GImportExcel example = new GImportExcel();
		if (GTSNO.getByExcel() && !example.doExportExcel()) {// 输出测试结果
			GLog.GLogDoReady("EXPORT EXCEL FAILED");
		}
		endSysTime = System.currentTimeMillis();
	}
	
	/**
	 *  停止日志
	 */
	private static void LogOff() {
		// 结束日志记录
		GLog.GLogOff();// 结束日志记录
	}
	
	/**
	 *  执行入口
	 */
	public GRunTest() {
		SysInit();
		LogOn();
		PreErrorCode();
		DateProvider();
		int index = 0;
		int total = GParam.TestTotalNo;
		while(total > 0) {
			GTestCase.TSSTYLE = Integer.valueOf(GTSNO.TSSTYLE_TSNO4[index][0]);// 接收入口用例类型编号
			GTestCase.TSNO = Integer.valueOf(GTSNO.TSSTYLE_TSNO4[index][1]);
			curTSNO = GTestCase.TSNO.toString();// 接收入口用例编号
			
			startTime = System.currentTimeMillis();
			GLog.GLogRecord(9, "\r\n" + GTime.getDate() + " TEST CASE BEGIN CS-" + curTSNO);
			try {//
				Thread.sleep(0);// 用于延时
				new TestRunReal(GTestCase.TSNO);
				GTestCase.RecordResultArrayByTSSTYLE(GTestCase.TSSTYLE);
				GTestCase.RecordTestResult(curTSNO, GTestCase.TestResult);// 记录测试结果
				long endTime = System.currentTimeMillis();
				GLog.GLogRecord(9,GTime.getDate() + " TEST CASE END CS-" + curTSNO + " -SPEND:" + (endTime - startTime) + "MS");
				GParam.resetGParam();// 重置系统全局参数
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			total--;
			index++;
		}
		OutputTestReport();
		GLog.GLogRecord(9, GTime.getDate() + " TEST MISSION -SPEND:" + (endSysTime - startSysTime) + "MS");
		LogOff();
	}
}
