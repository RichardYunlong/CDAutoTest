package AutoTest;

/**
 *  任务管理
 */
public class GTestMission {
	
	/**
	 *  系统开始时间
	 */
	public static long startSysTime;
	
	/**
	 *  系统结束时间
	 */
	public static long endSysTime;
	
	/**
	 *  用例序号
	 */
	public static int index = 0;
	
	/**
	 *  用例计数器
	 */
	public static int total = 0;
	
	/**
	 *  当前用例编号
	 */
	public static String curTSNO = "";
	
	/**
	 *  初始化系统
	 */
	public static void SysInit() {
		startSysTime = System.currentTimeMillis();
		new GSys();
		if (!GSys.IsTestReady) {
			GFile.WriteStringToBottom(GSys.Guide, "PREPARE TESTING ENVIRONMENT FAILED");
		}
	}
	
	/**
	 *  测试任务开始处理
	 */
	public static void TSMStart() {
		GText.DoLine("*", 128);
		index = 0;
		total = GParam.TestTotalNo;
	}
	
	/**
	 *  测试情况分支
	 */
	public static void TSMTree() {
		if(GTestCase.TestCheckOnly) {
			//心跳分支
			Test("0", "9999", 0);
		} else {
			//用例分支
			for(int i=0;i<GConfig.LoopCourt;i++) {
				while(total > 0) {
					Test(GTSNO.TSSTYLE_TSNO4[index][0], GTSNO.TSSTYLE_TSNO4[index][1], GConfig.TimeWait);
					total--;
					index++;
				}
			}
		}
	}
	
	/**
	 *  测试任务结束处理
	 */
	public static void TSMEnd() {
		GFile.WriteStringToBottom(GSys.Guide, GTime.getDate() + " TEST MISSION -SPEND:" + (endSysTime - startSysTime) + "MS");
		GText.DoLine("*", 128);
	}
	
	/**
	 *  启动日志
	 */
	public static void LogOn() {
		GLog.GLogOn();
	}
	
	/**
	 *  加载预置错误码
	 */
	public static void PreErrorCode() {
		GPreErrorCode.PreErrorCode();
	}
	
	/**
	 *  加载用例输入参数表
	 */
	public static void DateProvider() {		
		//根据用例输入来源加载所有用例输入
		GTSNO LTS = new GTSNO();
		LTS.GTSNOS_LIST(GTestCase.TestInputType.intValue());
	}
	
	
	
	/**
	 *  根据参数执行单个用例
	 */
	public static void Test(String style, String no, int waittime) {
		long startTime = System.currentTimeMillis();
		GTestCase.TSSTYLE = Integer.valueOf(style);
		GTestCase.TSNO = Integer.valueOf(no);
		curTSNO = GTestCase.TSNO.toString();
		GLog.GLogRecord(9, "\r\n" + GTime.getDate() + " TEST CASE BEGIN CS-" + curTSNO);
		try {
			Thread.sleep(waittime);
			new GTestSuite(GTestCase.TSNO);
			GTestCase.RecordResultArrayByTSSTYLE(GTestCase.TSSTYLE);
			GTestCase.RecordTestResult(curTSNO, GTestCase.TestResult);
			GParam.resetGParam();
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		GLog.GLogRecord(9,GTime.getDate() + " TEST CASE END CS-" + curTSNO + " -SPEND:" + (endTime - startTime) + "MS");
	}
	
	/**
	 *  输出执行结果
	 */
	public static void OutputTestReport() {
		GImportExcel example = new GImportExcel();
		if (GTSNO.getByExcel() && !example.doExportExcel()) {
			GFile.WriteStringToBottom(GSys.Guide, "EXPORT EXCEL FAILED");
		}
		endSysTime = System.currentTimeMillis();
	}
	
	/**
	 *  停止日志
	 */
	public static void LogOff() {
		GLog.GLogOff();
	}
}
