package AutoTest;

import java.lang.reflect.Method;

/**
 *  任务管理
 */
public class GTestMission {
	
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
	public static int index = 0;
	
	/**
	 *  任务用例计数器
	 */
	public static int total = 0;
	
	/**
	 *  初始化任务
	 */
	public static void SysInit() {
		startSysTime = System.currentTimeMillis();
		if (!GSys.initSys()) {
			GSys.GLogErrorSys("PREPARE TESTING ENVIRONMENT FAILED");
		}else {
			GSys.GLogSys("TESTING ENVIRONMENT FOR " + GParam.TestVersion);
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
	 *  测试情况分支-使用默认套件，无效果
	 */
	public static void TSMTree() {
		if(GTestCase.TestCheckOnly) {
			//心跳分支
			Test("0", "9999", 0, "");
		} else {
			//用例分支
			for(int i=0;i<GConfig.LoopCourt;i++) {
				while(total > 0) {
					Test(GTSNO.TSSTYLE_TSNO4[index][1], GTSNO.TSSTYLE_TSNO4[index][2], GConfig.TimeWait, "");
					total--;
					index++;
				}
			}
		}
	}
	
	/**
	 *  测试情况分支
	 */
	public static void TSMTree(String ClassName) {
		if(GTestCase.TestCheckOnly) {
			//心跳分支
			Test("0", "9999", 0, ClassName);
		} else {
			//用例分支
			for(int i=0;i<GConfig.LoopCourt;i++) {
				while(total > 0) {
					Test(GTSNO.TSSTYLE_TSNO4[index][1], GTSNO.TSSTYLE_TSNO4[index][2], GConfig.TimeWait, ClassName);
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
		GSys.GLogSys(GTime.getDate() + " TEST MISSION -SPEND:" + (endSysTime - startSysTime) + "MS");
		GText.DoLine("*", 128);
	}
	
	/**
	 *  启动任务日志
	 */
	public static void LogOn() {
		GLog.GLogOn();
	}
	
	/**
	 *  加载任务预置错误码
	 */
	public static void PreErrorCode() {
		GPreErrorCode.PreErrorCode();
	}
	
	/**
	 *  加载任务用例输入参数表
	 */
	public static void DateProvider() {		
		//根据用例输入来源加载所有用例输入
		GTSNO LTS = new GTSNO();
		LTS.GTSNOS_LIST(GTestCase.TestInputType.intValue());
	}
	
	
	
	/**
	 *  根据任务参数执行单个用例
	 *  “关键量”为：
	 *  1.GTestCase.TSNO：用例编号-系统根据用例编码执行对应的测试类和测试逻辑；
	 *  2.GTestCase.TSSTYLE：用例类型-在测试输入中会预先自定义一个期望的用例类型，例如：0 ，在测试类执行过程中，根据执行逻辑随时修改GTestCase.TSSTYLE改变用例类型，在测试类执行结束后会根据用例类型处理日志和控制台输出；
	 *  3.如果需要处理更多的关键量，需修改当前系统逻辑。
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void Test(String style, String no, int waittime, String ClassName) {
		//记录单个用例执行开始时间
		long startTime = System.currentTimeMillis();
		//初始化“关键量”
		GTestCase.TSSTYLE = Integer.valueOf(style);
		GTestCase.TSNO = Integer.valueOf(no);
		//提示用例开始执行
		GLog.GLogRecord(9, "\r\n" + GTime.getDate() + " TEST CASE BEGIN CS-" + GTestCase.TSNO.toString());
		//处理用例
		try {
			//处理延时
			Thread.sleep(waittime);
			if((ClassName != null) && (!ClassName.equals(""))) {//处理自定义测试套件
				Class onwClass = Class.forName(ClassName);
				Object obj= onwClass.newInstance();
				Method constractor = onwClass.getMethod("TestRunReal");  
				constractor.invoke(obj);
			}else {//处理内置演示套件
				new GTestSuite();
			}
			
			//测试类执行结束后，根据“用例类型”处理日志和输出
			GTestCase.RecordTestResultByTSSTYLE(GTestCase.TSSTYLE);
			//重置“关键量”
			GParam.resetGParam();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//记录用例执行结束时间
		long endTime = System.currentTimeMillis();
		//提示用例执行结束
		GLog.GLogRecord(9,GTime.getDate() + " TEST CASE END CS-" + GTestCase.TSNO.toString() + " -SPEND:" + (endTime - startTime) + "MS");
	}
	
	/**
	 *  输出任务执行结果
	 */
	public static void OutputTestReport() {
		GFile.DeleteFolder(GParam.getTestCaseOutputFullName());// 如果存在则删除用例输出文件
		if (!GExportExcel.doExportExcel()) {
			GSys.GLogErrorSys("EXPORT EXCEL FAILED");
		}
		endSysTime = System.currentTimeMillis();
	}
	
	/**
	 *  停止任务日志
	 */
	public static void LogOff() {
		GLog.GLogOff();
	}
}
