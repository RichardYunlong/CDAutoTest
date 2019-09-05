package AutoTest;

import java.lang.reflect.Method;

/**
 *  任务管理
 */
public class GTestMission {
	
	private GTestMission(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  任务开始时间
	 */
	public static long startSysTime;
	
	/**
	 *  任务结束时间
	 */
	public static long endSysTime;
	
	/**
	 *  任务用例序号
	 */
	private static int index = 0;
	
	/**
	 *  任务用例计数器
	 */
	private static int total = 0;
	
	/**
	 *  初始化任务
	 */
	public static void tmInit() {
		startSysTime = System.currentTimeMillis();
		if (!GSys.initSys()) {
			GSys.logErrorSys("PREPARE TESTING ENVIRONMENT FAILED");
		}else {
			GSys.logSys("TESTING ENVIRONMENT FOR " + GParam.strTestVersion);
		}
	}
	
	/**
	 *  测试任务开始处理
	 */
	public static void tmStart() {
		GText.doLine(GLog.strLogStyle[9], "*", 128);
		index = 0;
		total = GParam.dTestTotalNo;
	}
	
	/**
	 *  测试情况分支-使用默认套件，无效果
	 */
	public static void tmTree() {
		if(GTestCase.bTestCheckOnly) {
			//心跳分支
			tmTest("0", "9999", 0, "");
		} else {
			//用例分支
			for(int i=0;i<GConfig.dLoopCourt;i++) {
				while(total > 0) {
					tmTest(GTSNO.gStyleTSNO4[index][1], GTSNO.gStyleTSNO4[index][2], GConfig.dTimeWait, "");
					total--;
					index++;
				}
			}
		}
	}
	
	/**
	 *  测试情况分支
	 */
	public static void tmTree(String clazzName) {
		if(GTestCase.bTestCheckOnly) {
			//心跳分支
			tmTest("0", "9999", 0, clazzName);
		} else {
			//用例分支
			for(int i=0;i<GConfig.dLoopCourt;i++) {
				while(total > 0) {
					tmTest(GTSNO.gStyleTSNO4[index][1], GTSNO.gStyleTSNO4[index][2], GConfig.dTimeWait, clazzName);
					total--;
					index++;
				}
			}
		}
	}
	
	/**
	 *  测试任务结束处理
	 */
	public static void tmEnd() {
		GText.doLine(GLog.strLogStyle[9], "*", 128);
	}
	
	/**
	 *  启动任务日志
	 */
	public static void tmLogOn() {
		GLog.logOn();
	}
	
	/**
	 *  加载任务预置错误码
	 */
	public static void tmPreErrorCode() {
		GPreErrorCode.preErrorCode();
	}
	
	/**
	 *  加载任务用例输入参数表
	 */
	public static void tmDateProvider() {		
		//根据用例输入来源加载所有用例输入
		GTSNO lts = new GTSNO();
		lts.gTSNOSLIST(GTestCase.dTestInputType.intValue());
	}
	
	
	
	/**
	 *  根据任务参数执行单个用例
	 *  “关键量”为：
	 *  1.GTestCase.TSNO：用例编号-系统根据用例编码执行对应的测试类和测试逻辑；
	 *  2.GTestCase.TSSTYLE：用例类型-在测试输入中会预先自定义一个期望的用例类型，例如：0 ，在测试类执行过程中，根据执行逻辑随时修改GTestCase.TSSTYLE改变用例类型，在测试类执行结束后会根据用例类型处理日志和控制台输出；
	 *  3.如果需要处理更多的关键量，需修改当前系统逻辑。
	 *  
	 *  @param style 用例类型编码
	 *  @param no 用例编号
	 *  @param waittime 等待时间
	 *  @param clazzName 测试套件类全名
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void tmTest(String style, String no, int waittime, String clazzName) {
		//记录单个用例执行开始时间
		long testStartTime = System.currentTimeMillis();
		//初始化“关键量”
		GTestCase.dTSSTYLE = Integer.valueOf(style);
		GTestCase.dTSNO = Integer.valueOf(no);
		String curTSNO = GTestCase.dTSNO.toString();
		//提示用例开始执行
		GLog.logRecord(9, "\r\n" + GTime.getDate() + " TEST CASE BEGIN CS-" + GTestCase.dTSNO.toString());
		//处理用例
		try {
			//处理延时
			Thread.sleep(waittime);
			if((clazzName != null) && (!clazzName.equals(""))) {//处理自定义测试套件
				Class onwClass = Class.forName(clazzName);
				Object obj= onwClass.newInstance();
				Method constractor = onwClass.getMethod("TestRunReal");  
				constractor.invoke(obj);
			}else {//处理内置演示套件
				new GTestSuite();
			}
			
			//测试类执行结束后，根据“用例类型”处理日志和输出
			GTestCase.recordTestResultByTSSTYLE(GTestCase.dTSSTYLE);
			//重置“关键量”
			GParam.resetGParam();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//记录用例执行结束时间
		long testtEndTime = System.currentTimeMillis();
		//提示用例执行结束
		GLog.logRecord(9,GTime.getDate() + " TEST CASE END CS-" + curTSNO + " -SPEND:" + (testtEndTime - testStartTime) + "MS");
	}
	
	/**
	 *  输出任务执行结果
	 */
	public static void tmOutputTestReport() {
		GFile.deleteFolder(GParam.getTestCaseOutputFullName());// 如果存在则删除用例输出文件
		if (!GExportExcel.doExportExcel()) {
			GSys.logErrorSys("EXPORT EXCEL FAILED");
		}
	}
	
	/**
	 *  停止任务日志
	 */
	public static void tmLogOff() {
		GLog.logOff();
		endSysTime = System.currentTimeMillis();
		GSys.logSys(GTime.getDate() + " TEST MISSION -SPEND:" + (endSysTime - startSysTime) + "MS");
		GSys.logSys(GMsg.SYSTEM_ENDING);
		
		if(GResult.bAutoCheckReport) {
			GHtml.OutPutHtml();
		}
	}
}
