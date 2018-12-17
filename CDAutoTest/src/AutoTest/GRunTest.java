package AutoTest;

import JUnitTestDemo.TestRunReal;

public class GRunTest {
	public static long startSysTime = System.currentTimeMillis();
	public static long endSysTime;
	public static long startTime = System.currentTimeMillis();
	public static String curTSNO = "";
	
	private static void SysInit() {
		GParam.TestVersion = "JUnit4 Demo";// 被测件名称及版本号

		// 初始化系统环境
		GSys gSys = new GSys();
		if (!gSys.initTestSys()) {
			GLog.GLogDoReady("PREPARE TESTING ENVIRONMENT FAILED");
		}
	}
	
	private static void LogOn() {
		// 开始日志记录
		GLog.GLogOn();
	}
	
	private static void PreErrorCode() {
		// 预置错误码表
		GPreErrorCode.PreErrorCode("./config/errorcode", ".txt");
	}
	
	private static void DateProvider() {
		GTSNO LTS = new GTSNO();
		LTS.GTSNOS_LIST(1);
	}
	
	private static void OutputTestReport() {
		GImportExcel example = new GImportExcel();
		if (GTSNO.getByExcel() && !example.doExportExcel()) {// 输出测试结果
			GLog.GLogDoReady("EXPORT EXCEL FAILED");
		}
		endSysTime = System.currentTimeMillis();
	}
	
	private static void LogOff() {
		// 结束日志记录
		GLog.GLogOff();// 结束日志记录
	}
	

	
	public GRunTest() {
		SysInit();
		LogOn();
		PreErrorCode();
		DateProvider();
		int index = 0;
		while(GParam.TestTotalNo > 0) {
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
			
			GAssert.AssertIntegerEqual(GTestCase.TestResult, 0);
			
			GParam.TestTotalNo--;
			index++;
		}
		OutputTestReport();
		GLog.GLogRecord(9, GTime.getDate() + " TEST MISSION -SPEND:" + (endSysTime - startSysTime) + "MS");
		LogOff();
	}
}
