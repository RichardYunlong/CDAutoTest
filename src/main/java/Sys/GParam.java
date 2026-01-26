package Sys;

import Base.GClazz;
import Base.GFile;
import Base.GTime;
import Test.GTestMission;

/**
 *  全局参数
 */
public class GParam {
	
    /**
     *  构造函数
     */
	public GParam(){ GClazz.thisAToolClass(); }
	
	/**
	 *  是否为WebUI驱动模式
	 */
	private String isWeDriver = "AutoTest";
	public String getDriver() {
		return this.isWeDriver;
	}
	public void setDriver(String isWebDriverTemp) {
		this.isWeDriver = isWebDriverTemp;
	}
	
	/**
	 *  是否开启AI助理
	 */
	private boolean dragonShow = false;
	public boolean isDragonShow() {
		return this.dragonShow;
	}
	public void setDragonShow(boolean dragonShow) {
		this.dragonShow = dragonShow;
	}
	
	/**
	 *  是否在测试完成后自动打开测试报告
	 */
	private boolean bAutoCheckReport = false;
	public boolean isbAutoCheckReport() { return this.bAutoCheckReport; }
	public void setbAutoCheckReport(boolean bAutoCheckReport) {
		this.bAutoCheckReport = bAutoCheckReport;
	}
	
	/**
	 *  参数提供方式
	 *  取值范围 [0,9]，默认为0
	 *  0-object集合
	 *  1-Excel表格 
	 *  2-Txt文本
	 */
	private Integer INPUT_TYPE = 2;
	public Integer getINPUT_TYPE() {
		return this.INPUT_TYPE;
	}
	public void setINPUT_TYPE(Integer iNPUT_TYPE) {
		this.INPUT_TYPE = iNPUT_TYPE;
	}
	
	/**
	 *  参数提供来源
	 *  取值范围 [0,9]，默认为0
	 *  0-工具内置
	 *  1-外部输入
	 */
	private Integer INPUT_FROM = 1;
	public Integer getINPUT_FROM() {
		return this.INPUT_FROM;
	}
	public void setINPUT_FROM(Integer iNPUT_FROM) {
		this.INPUT_FROM = iNPUT_FROM;
	}

	/**
	 *  参数提供外部参数表读取参数起始位置标记-行，默认为0
	 */
	private int INPUT_ROW_FROM = 1;
	public int getINPUT_ROW_FROM() {
		return this.INPUT_ROW_FROM;
	}
	public void setINPUT_ROW_FROM(int iNPUT_ROW_FROM) {
		this.INPUT_ROW_FROM = iNPUT_ROW_FROM;
	}
	
	/**
	 *  参数提供外部参数表读取参数起始位置标记-列，默认为0
	 */
	private int INPUT_COL_FROM = 6;
	public int getINPUT_COL_FROM() {
		return this.INPUT_COL_FROM;
	}
	public void setINPUT_COL_FROM(int iNPUT_COL_FROM) {
		this.INPUT_COL_FROM = iNPUT_COL_FROM;
	}
	
	/**
	 *  当前执行用例序号或已执行用例数
	 *  初始值为0，但在实际运行中，执行一条用例此数值就+1
	 */
	private int dRunNum = 0;
	public int getdRunNum() {
		return this.dRunNum;
	}
	public void setdRunNum(int dRunNum) {
		this.dRunNum = dRunNum;
	}
	
	/**
	 *  当前用例游标
	 */
	private int dRowIndex = 0;
	public int getdRowIndex() {
		return this.dRowIndex;
	}
	public void setdRowIndex(int dRowIndex) {
		this.dRowIndex = dRowIndex;
	}
	
	/**
	 *  当前读取到的单个用例参数个数上线
	 */
	private int COL_MAX = 32;
	public int getCOL_MAX() {
		return this.COL_MAX;
	}
	public void setCOL_MAX(int cOL_MAX) {
		this.COL_MAX = cOL_MAX;
	}

	/**
	 *  当前单个用例输入参数游标
	 */
	private int dColIndex = 0;
	public int getdColIndex() {
		return this.dColIndex;
	}
	public void setdColIndex(int dColIndex) {
		this.dColIndex = dColIndex;
	}


	/**
	 *  当前测试环境类型
	 *  取值范围[0,9]，默认为0
	 * 0-本地
	 * 1-测试系统
	 * 2-生产系统
	 * 3-本地Linux测试服务
	 * 4-本地加网关测试服务
	 */
	private Integer environment;
	public Integer getEnvironment() {
		return this.environment;
	}
	public void setEnvironment(Integer environment) {
		this.environment = environment;
	}
	
	/**
	 *  当前执行业务流水号
	 */
	private String flowNo = "";
	public String getFlowNo() {
		return this.flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	
	/**
	 *  当前请求报文
	 */
	private String req = "";
	public String getReq() {
		return this.req;
	}
	public void setReq(String req) {
		this.req = req;
	}
	
	/**
	 *  当前响应报文
	 */
	private String res = "";
	public String getRes() {
		return this.res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	
	/**
	 *  最近一个错误的返回报文
	 *  最大长度180个汉字
	 */
	private String recRes = "";
	public String getRecRes() {
		return this.recRes;
	}
	public void setRecRes(String strError) {
		if(this.recRes.length() <= 180) {
			this.recRes = strError + ";" + this.recRes;
			if(this.recRes.length() > 180) {
				this.recRes = this.recRes.substring(0, 180);
			}
		}
	}
	
	/**
	 *  当前返回码
	 */
	private String code = "9";
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 *  当前返回信息
	 */
	private String msg = "UNKNOW";
	public String getMsg() {
		return this.msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	/**
	 *  当前系统时间
	 */
	private String sysTime = "";
	public String getSysTime() {
		return this.sysTime;
	}
	public void setSysTime(String sysTime) {
		this.sysTime = sysTime;
	}

	/**
	 *  当前操作批准时间
	 */
	private String authorizationTime = "";
	public String getAuthorizationTime() {
		return this.authorizationTime;
	}
	public void setAuthorizationTime(String authorizationTime) {
		this.authorizationTime = authorizationTime;
	}
	
	/**
	 *  当前系统主题
	 */
	private String sysTheme = "";
	public String getSysTheme() {
		return this.sysTheme;
	}
	public void setSysTheme(String sysTheme) { this.sysTheme = sysTheme; }

	/**
	 *  当前授权验证码
	 */
	private String veriCode = "";
	public String getVeriCode() {
		return this.veriCode;
	}
	public void setVeriCode(String veriCode) {
		this.veriCode = veriCode;
	}
	
	/**
	 *  当前异常类型编号
	 */
	private int exceptionType = 0;
	public int getExceptionType() {
		return this.exceptionType;
	}
	public void setExceptionType(int exceptionType) {
		this.exceptionType = exceptionType;
	}
	
	/**
	 *  当前异常类型字符串
	 */
	private String exceptionScr = "";
	public String getExceptionScr() {
		return this.exceptionScr;
	}
	public void setExceptionScr(String exceptionScr) {
		this.exceptionScr = exceptionScr;
	}
	
	/**
	 * 性能基线版本
	 */
	private String baseVersion = "";
	public String getBaseVersion() {
		return this.baseVersion;
	}
	public void setBaseVersion(String baseVersion) {
		this.baseVersion = baseVersion;
	}
	
	/**
	 * 性能上次版本
	 */
	private String lastVersion = "";
	public String getLastVersion() {
		return this.lastVersion;
	}
	public void setLastVersion(String lastVersion) {
		this.lastVersion = lastVersion;
	}

	/**
	 * 严重缓慢闭区间左值
	 */
	private static double failSection = 10000.0d;
	public static double getFailSection() {
		return failSection;
	}
	public static void setFailSection(String failSection) { GParam.failSection = Double.parseDouble(failSection); }
	
	/**
	 * 缓慢闭区间左值
	 */
	private double waringSection = 3000.0d;
	public double getWaringSection() {
		return this.waringSection;
	}
	public  void setWaringSection(String waringSection) { this.waringSection = Double.parseDouble(waringSection); }
	
	/**
	 * 常规闭区间左值
	 */
	private double lowSection = 1000.0d;
	public double getLowSection() {
		return this.lowSection;
	}
	public void setLowSection(String lowSection) {
		this.lowSection = Double.parseDouble(lowSection);
	}

    /**
     * 全局计数器
     */
    private int counter = 0;
    public int getCounter() {
        return this.counter;
    }
    public void setCounter(int dNum) {
        this.counter = dNum;
    }
    public void setCounterPlusOne() {
        this.counter ++;
    }
    public void setCounterMinusOne() {
        this.counter --;
    }

	/**
	 *  重置全局参数
	 */
	public void resetGParam() {
		code = "9";
		this.msg = "UNKNOWN";
		GTestMission.gTestCase.setTC_NO(0);
		GTestMission.gTestCase.setTC_TYPE_RES(3);
		this.sysTime = GTime.getCurrentTime(GTime.FORMAT_14);
		this.authorizationTime = GTime.getCurrentTime(GTime.FORMAT_14);
		this.veriCode = "";
		this.req = "";
		this.res = "";
		this.recRes = "";
	}
	
	/**
	 *  加载参数
	 */	
	public void loadConfig() {
		if((!this.getDriver().isEmpty())
		&& (this.getINPUT_TYPE() <= 2)
		&& (this.getINPUT_FROM() <= 1)
		&& (this.getINPUT_ROW_FROM() > 0)
		&& (this.getINPUT_COL_FROM() > 0)){
			GStatic.gAbout.setVersion(this.getDriver());
		}else {
			GFile.writeStringToGuideBottom("INPUT_TYPE/INPUT_FROM/INPUT_ROW_FROM/INPUT_COL_FROM from sysConfig may has no value, Please check again!");
			System.exit(0);
		}
	}
}