package main.java.Test;

import main.java.Base.*;
import main.java.DT.GLog;
import main.java.IO.GErrorCodeImport;
import main.java.Sys.GStatic;

import java.lang.reflect.Method;

/**
 *  任务管理
 */
public class GTestMission {
	
    /**
     *  构造函数
     */
	public GTestMission(){
		GClazz.thisAToolClass();
	}

	/**
	 *  错误码管理
	 */
	@SuppressWarnings("CanBeFinal")
    public static GErrorCodeImport gErrorCodeImport = new GErrorCodeImport();

	/**
	 *  参数表管理
	 */
	public static GTCNO gtcno = null;

	/**
	 *  用例管理
	 */
	public static GTestCase gTestCase = null;

	/**
	 *  计划管理
	 */
	@SuppressWarnings("CanBeFinal")
    public static GTestPlan gTestPlan = new GTestPlan();

	/**
	 *  进度管理
	 */
	@SuppressWarnings("CanBeFinal")
    public static GTestProgress gTestProgress = new GTestProgress();

	/**
	 *  进度管理
	 */
	@SuppressWarnings("CanBeFinal")
    public static GTestResult gTestResult = new GTestResult();

	/**
	 *  项目管理
	 */
	@SuppressWarnings("CanBeFinal")
    public static GTestProject gTestProject = new GTestProject();

	/**
	 *  是否只校验应用服务有效性，不运行任务
	 *  默认为true
	 */
	private boolean TM_CHECK_ONLY = true;
	public boolean isTM_CHECK_ONLY() {
		return this.TM_CHECK_ONLY;
	}
	public void setTM_CHECK_ONLY(boolean tM_CHECK_ONLY) {
		this.TM_CHECK_ONLY = tM_CHECK_ONLY;
	}
	
	/**
	 *  测试轮数
	 *  默认为1
	 */
	private int TM_LOOP_COURT = 1;
	public int getTM_LOOP_COURT() {
		return this.TM_LOOP_COURT;
	}
	public void setTM_LOOP_COURT(int tM_LOOP_COURT) {
		this.TM_LOOP_COURT = tM_LOOP_COURT;
	}
	
	/**
	 *  任务开始时间
	 */
	private long startSysTime;
	public long getStartSysTime() {
		return this.startSysTime;
	}
	public void setStartSysTime(long startSysTime) {
		this.startSysTime = startSysTime;
	}
	
	/**
	 *  任务结束时间
	 */
	private long endSysTime;
	public long getEndSysTime() {
		return this.endSysTime;
	}
	public void setEndSysTime(long endSysTime) {
		this.endSysTime = endSysTime;
	}
	
	/**
	 *  用例执行开始时间
	 */
	private long startTime;
	public long getStartTime() {
		return this.startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	/**
	 *  用例执行结束时间
	 */
	private long endTime;
	public long getEndTime() {
		return this.endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	/**
	 *  任务用例序号
	 */
	private int index = 0;
	public int getIndex() {
		return this.index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 *  任务用例计数器
	 */
	private int total = 0;
	public int getTotal() {
		return this.total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 *  全局参数表
	 */
	private final int progressTatol = 100;
	public GMaticProgress gMP = new GMaticProgress(0, this.progressTatol);
	public GMaticProgress getgMP() { return this.gMP; }
	public void setgMP(GMaticProgress gMP) { this.gMP = gMP; }

	/**
	 *  加载参数 如果加载失败把日志写到引导日志中
	 */	
	public void loadConfig() {
		if(this.TM_LOOP_COURT >= 1){
			GFile.writeStringToGuideBottom("GTestMission Params Loaded");
		}else {
			GFile.writeStringToGuideBottom("TM_LOOP_COURT from sysConfig may has no value, Please check again!");
			System.exit(0);
		}
	}
	
	/**
	 *  初始化任务
	 */
	public void tmInit() {
		this.startSysTime = System.currentTimeMillis();
		this.gMP.progress();
	}

	/**
	 *  加载任务预置错误码
	 */
	public void tmPreErrorCode() {
		gErrorCodeImport.preErrorCode();
		this.gMP.progress();
	}

	/**
	 *  加载任务用例输入参数表
	 */
	public void tmDateProvider() {
		//根据用例输入来源加载所有用例输入
		String inputType = GStatic.gP.getINPUT_TYPE().toString();
		gtcno = new GTCNO(inputType);
		gtcno.GTCNO_CONSTRUTION(inputType);
		this.gMP.progress();
		if(this.gMP.getPROGRESS_CUR() <= 100) {
			this.gMP.setPROGRESS_CUR(100);
		}else {
			GFile.writeStringToGuideBottom("PROCCESS ERROR");
		}
	}
	
	/**
	 *  测试任务开始处理
	 */
	public void tmStart() {
		this.startTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom(GMissionMsg.getStepTop("RUN TEST CASE START"));
		this.index = 0;
		this.total = GTestMission.gTestProgress.getTotalNum();
		this.gMP.progress();
	}
	
	/**
	 *  测试情况分支-使用默认套件，无效果
	 */
	public void tmTree() {
		String[][] tcno = gtcno.getgTCNO4().clone();
		if(this.TM_CHECK_ONLY) {
			//心跳分支
			tmTest("0", "9999", 0, "");
		} else {
			//用例分支
			for(int i=0;i<this.TM_LOOP_COURT;i++) {
				while(this.total > 0) {
					tmTest(tcno[this.index][1], tcno[this.index][2], GTestMission.gTestPlan.getTimeWait(), "");

					this.total--;
					this.index++;
					this.gMP.progress();
				}
			}
		}
	}
	
	/**
	 *  测试情况分支
	 *
	 *  @param clazzName 类名称
	 */
	public void tmTree(String clazzName) {
		String[][] tcno = gtcno.getgTCNO4().clone();
		if(this.TM_CHECK_ONLY) {
			//心跳分支
			tmTest("0", "9999", 0, clazzName);
		} else {
			//用例分支
			for(int i=0;i<this.TM_LOOP_COURT;i++) {
				while(this.total > 0) {
					tmTest(tcno[this.index][1], tcno[this.index][2], gTestPlan.getTimeWait(), clazzName);

					this.total--;
					this.index++;
					this.gMP.progress();
				}
			}
		}
	}
	
	/**
	 *  测试任务结束处理
	 */
	public void tmEnd() {
		this.endTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom("RUN TEST CASE -SPEND:" + (this.endTime - this.startTime) + "MS");
		GFile.writeStringToGuideBottom(GMissionMsg.getStepBottom("RUN TEST CASE END"));
		this.gMP.setPROGRESS_CUR(this.progressTatol);
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
	public void tmTest(String style, String no, int waittime, String clazzName) {
		//记录单个用例执行开始时间
		long caseStartTime = System.currentTimeMillis();
		gTestCase = new GTestCase();
		//初始化“关键量”
		gTestCase.setTC_TYPE_RES(Integer.valueOf(style));
		gTestCase.setTC_NO(Integer.valueOf(no));
		
		String curTSNO = gTestCase.getTC_NO().toString();
		//提示用例开始执行
		GLog.logRecord(9, "\r\n" + GTime.getDate() + " TEST CASE BEGIN CS-" + gTestCase.getTC_NO().toString());
		//处理用例
		try {
			//处理延时
			Thread.sleep(waittime);
			
			if((clazzName != null) && (!clazzName.isEmpty())) {//处理自定义测试套件
			    
				Class onwClass = Class.forName(clazzName);
				Object obj= onwClass.newInstance();
				Method constractor = onwClass.getMethod("TestRunReal");  
				constractor.invoke(obj);
			}else {//处理内置演示套件
				new GTestSuite();
			}
			
			//测试类执行结束后，根据“用例类型”处理日志和输出
			gTestCase.recordTCResultByTCTypeRes(gTestCase.getTC_TYPE_RES());
			//重置“关键量”
			GStatic.gP.resetGParam();
		} catch (Exception e) {
			GLog.logSysFunctionException("tmTest", e);
		}
		//记录用例执行结束时间
		long caseEndTime = System.currentTimeMillis();
		//提示用例执行结束
		GLog.logRecord(9,GTime.getDate() + " TEST CASE END CS-" + curTSNO + " -SPEND:" + (caseEndTime - caseStartTime) + "MS");
	}
}
