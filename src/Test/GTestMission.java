package Test;

import java.lang.reflect.Method;

import Base.GClazz;
import Base.GFile;
import Base.GMissionMsg;
import Base.GThread;
import Base.GTime;
import DT.GLog;
import IO.GErrorCodeImport;
import IO.GExcelExport;
import Sys.GParam;
import Sys.GPath;
import Sys.GSys;

/**
 *  任务管理
 */
public class GTestMission {
	
    /**
     *  构造函数
     */
	private GTestMission(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  是否只校验应用服务有效性，不运行任务
	 *  
	 *  默认为true
	 */
	private static boolean TM_CHECK_ONLY = true;
	
	/**
	 *  测试轮数
	 *  
	 *  默认为1
	 */
	private static int TM_LOOP_COURT = 1;
	
	/**
	 *  任务开始时间
	 */
	private static long startSysTime;
	
	/**
	 *  任务结束时间
	 */
	private static long endSysTime;
	
	/**
	 *  用例执行开始时间
	 */
	private static long startTime;
	
	/**
	 *  用例执行结束时间
	 */
	private static long endTime;
	
	/**
	 *  任务用例序号
	 */
	private static int index = 0;
	
	/**
	 *  任务用例计数器
	 */
	private static int total = 0;

	public static boolean isTM_CHECK_ONLY() {
		return TM_CHECK_ONLY;
	}

	public static void setTM_CHECK_ONLY(boolean tM_CHECK_ONLY) {
		TM_CHECK_ONLY = tM_CHECK_ONLY;
	}

	public static int getTM_LOOP_COURT() {
		return TM_LOOP_COURT;
	}

	public static void setTM_LOOP_COURT(int tM_LOOP_COURT) {
		TM_LOOP_COURT = tM_LOOP_COURT;
	}

	public static long getStartSysTime() {
		return startSysTime;
	}

	public static void setStartSysTime(long startSysTime) {
		GTestMission.startSysTime = startSysTime;
	}

	public static long getEndSysTime() {
		return endSysTime;
	}

	public static void setEndSysTime(long endSysTime) {
		GTestMission.endSysTime = endSysTime;
	}

	public static long getStartTime() {
		return startTime;
	}

	public static void setStartTime(long startTime) {
		GTestMission.startTime = startTime;
	}

	public static long getEndTime() {
		return endTime;
	}

	public static void setEndTime(long endTime) {
		GTestMission.endTime = endTime;
	}

	public static int getIndex() {
		return index;
	}

	public static void setIndex(int index) {
		GTestMission.index = index;
	}

	public static int getTotal() {
		return total;
	}

	public static void setTotal(int total) {
		GTestMission.total = total;
	}

	/**
	 *  加载参数
	 */	
	public static void loadConfig() {
		if(TM_LOOP_COURT >= 1){
			;
		}else {
			GSys.logErrorSys("One of these GTestMission params from sysConfig may has no value, Please check again!");
			System.exit(0);
		}
	}
	
	/**
	 *  初始化任务
	 */
	public static void tmInit() {
		startSysTime = System.currentTimeMillis();
		if (!GSys.initSys()) {
			GSys.logErrorSys("PREPARE TESTING ENVIRONMENT FAILED");
		}
		GSys.progress();
	}
	
	/**
	 *  测试任务开始处理
	 */
	public static void tmStart() {
		startTime = System.currentTimeMillis();
		GSys.logSys(GMissionMsg.getStepTop("RUN TEST CASE START"));
		index = 0;
		total = GTestProgress.getTotalNum();
	}
	
	/**
	 *  测试情况分支-使用默认套件，无效果
	 */
	public static void tmTree() {
		String[][] tcno = GTCNO.getgTCNO4().clone();
		if(TM_CHECK_ONLY) {
			//心跳分支
			tmTest("0", "9999", 0, "");
		} else {
			//用例分支
			for(int i=0;i<TM_LOOP_COURT;i++) {
				while(total > 0) {
					tmTest(tcno[index][1], tcno[index][2], GTestPlan.getTimeWait(), "");
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
		String[][] tcno = GTCNO.getgTCNO4().clone();
		if(TM_CHECK_ONLY) {
			//心跳分支
			tmTest("0", "9999", 0, clazzName);
		} else {
			//用例分支
			for(int i=0;i<TM_LOOP_COURT;i++) {
				while(total > 0) {
					tmTest(tcno[index][1], tcno[index][2], GTestPlan.getTimeWait(), clazzName);
					
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
		endTime = System.currentTimeMillis();
		GSys.logSys("RUN TEST CASE -SPEND:" + (endTime - startTime) + "MS");
		GSys.logSys(GMissionMsg.getStepBottom("RUN TEST CASE END"));	
	}
	
	/**
	 *  启动任务日志
	 */
	public static void tmLogOn() {
		GSys.logSys(GMissionMsg.getMisstionTip("MISSTION START"));
		GLog.logOn();
		GSys.progress();
	}
	
	/**
	 *  加载任务预置错误码
	 */
	public static void tmPreErrorCode() {
		GErrorCodeImport.preErrorCode();
		GSys.progress();
	}
	
	/**
	 *  加载任务用例输入参数表
	 */
	public static void tmDateProvider() {		
		//根据用例输入来源加载所有用例输入
		new GTCNO(GParam.getINPUT_TYPE().toString());
		GSys.progress();
		if(GSys.getPROGRESS_CUR() <= 100) {
			GSys.setPROGRESS_CUR(100);
		}else {
			GSys.logErrorSys("PROCCESS ERROR");
		}
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
		long caseStartTime = System.currentTimeMillis();
		//初始化“关键量”
		GTestCase.setTC_TYPE_RES(Integer.valueOf(style));
		GTestCase.setTC_NO(Integer.valueOf(no));
		
		String curTSNO = GTestCase.getTC_NO().toString();
		//提示用例开始执行
		GLog.logRecord(9, "\r\n" + GTime.getDate() + " TEST CASE BEGIN CS-" + GTestCase.getTC_NO().toString());
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
			GTestCase.recordTCResultByTCTypeRes(GTestCase.getTC_TYPE_RES());
			//重置“关键量”
			GParam.resetGParam();
		} catch (Exception e) {
			GLog.logSysFunctionException("tmTest", e);
		}
		//记录用例执行结束时间
		long caseEndTime = System.currentTimeMillis();
		//提示用例执行结束
		GLog.logRecord(9,GTime.getDate() + " TEST CASE END CS-" + curTSNO + " -SPEND:" + (caseEndTime - caseStartTime) + "MS");
	}
	
	/**
	 *  输出任务执行结果
	 */
	public static void tmOutputTestReport() {
		GFile.deleteFolder(GPath.OUTPUT_XLS_PATH + GPath.OUTPUT_XLS_NAME);// 如果存在则删除用例输出文件
		if (!GExcelExport.doExportExcel()) {
			GSys.logErrorSys("EXPORT EXCEL FAILED");
		}
	}
	
	/**
	 *  停止任务日志
	 */
	public static void tmLogOff() {
		endSysTime = System.currentTimeMillis();
		GLog.logOff();
		GSys.logSys("MISSTION -SPEND:" + (endSysTime - startSysTime) + "MS");
		GThread.ByeUI();
		GSys.logSys(GMissionMsg.getMisstionTip("MISSTION END"));
	}
}
