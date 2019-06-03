package AutoTest;

/**
 *  运行主体
 */
public class GRunTest {
	
	/**
	 *  系统开始时间
	 */
	public long startSysTime = System.currentTimeMillis();
	
	/**
	 *  系统结束时间
	 */
	public long endSysTime;
	
	/**
	 *  业务开始时间
	 */
	public long startTime = System.currentTimeMillis();
	
	/**
	 *  当前用例编号
	 */
	public String curTSNO = "";
	
	/**
	 *  初始化系统
	 */
	public void SysInit() {
		// 初始化系统环境
		new GSys();
		if (!GSys.IsTestReady) {
			GFile.WriteStringToBottom(GSys.Guide, "PREPARE TESTING ENVIRONMENT FAILED");
		}
	}
	
	/**
	 *  启动日志
	 */
	public void LogOn() {
		// 开始日志记录
		GLog.GLogOn();
	}
	
	/**
	 *  加载预置错误码
	 */
	public void PreErrorCode() {
		// 预置错误码表
		GPreErrorCode.PreErrorCode();
	}
	
	/**
	 *  加载用例输入参数表
	 */
	public void DateProvider() {
		GTSNO LTS = new GTSNO();
		LTS.GTSNOS_LIST(GTestCase.TestInputType.intValue());
	}
	
	/**
	 *  输出执行结果
	 */
	public void OutputTestReport() {
		GImportExcel example = new GImportExcel();
		if (GTSNO.getByExcel() && !example.doExportExcel()) {// 输出测试结果
			GFile.WriteStringToBottom(GSys.Guide, "EXPORT EXCEL FAILED");
		}
		endSysTime = System.currentTimeMillis();
	}
	
	/**
	 *  停止日志
	 */
	public void LogOff() {
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
		startTime = System.currentTimeMillis();
		GText.DoLine("*", 128);
		if(GTestCase.TestCheckOnly) {
			GTestCase.TSSTYLE = Integer.valueOf(0);// 接收入口用例类型编号
			GTestCase.TSNO = Integer.valueOf(9999);
			curTSNO = GTestCase.TSNO.toString();// 接收入口用例编号
			GFile.WriteStringToBottom(GSys.Guide, GTime.getDate() + " TEST CASE BEGIN CS-" + curTSNO);
			try {//
				Thread.sleep(0);// 用于延时
				new GTestSuite(9999);
				GTestCase.RecordResultArrayByTSSTYLE(GTestCase.TSSTYLE);
				GTestCase.RecordTestResult(curTSNO, GTestCase.TestResult);// 记录测试结果
				long endTime = System.currentTimeMillis();
				GLog.GLogRecord(9,GTime.getDate() + " TEST CASE END CS-" + curTSNO + " -SPEND:" + (endTime - startTime) + "MS");
				GParam.resetGParam();// 重置系统全局参数
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else {
			for(int i=0;i<GConfig.LoopCourt;i++) {
				while(total > 0) {
					GTestCase.TSSTYLE = Integer.valueOf(GTSNO.TSSTYLE_TSNO4[index][0]);// 接收入口用例类型编号
					GTestCase.TSNO = Integer.valueOf(GTSNO.TSSTYLE_TSNO4[index][1]);
					curTSNO = GTestCase.TSNO.toString();// 接收入口用例编号
					
					startTime = System.currentTimeMillis();
					GLog.GLogRecord(9, "\r\n" + GTime.getDate() + " TEST CASE BEGIN CS-" + curTSNO);
					try {//
						Thread.sleep(0);// 用于延时
						new GTestSuite(GTestCase.TSNO);
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
			}
		}
		GFile.WriteStringToBottom(GSys.Guide, GTime.getDate() + " TEST MISSION -SPEND:" + (endSysTime - startSysTime) + "MS");
		GText.DoLine("*", 128);
		
		OutputTestReport();
		LogOff();
	}
	
	public static void main(String[] args) {
        new GRunTest();
    }
}
