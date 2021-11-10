package Sys;

import Base.GAbout;
import Base.GClazz;
import Base.GTime;
import Test.GTestCase;

/**
 *  全局参数
 */
public class GParam {
	
    /**
     *  构造函数
     */
	private GParam(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  是否开启AI助理
	 */
	private static String driver = "AutoTest";
	
	/**
	 *  是否开启AI助理
	 */
	private static boolean dragonShow = false;
	
	/**
	 *  是否在测试完成后自动打开测试报告
	 */
	private static boolean bAutoCheckReport = false;
	
	/**
	 *  参数提供方式
	 *  
	 *  取值范围 [0,9]，默认为0
	 *  0-object集合
	 *  1-Excel表格 
	 *  2-Txt文本
	 */
	private static Integer INPUT_TYPE = 2;
	
	/**
	 *  参数提供来源
	 *  
	 *  取值范围 [0,9]，默认为0
	 *  0-工具内置
	 *  1-外部输入
	 */
	private static Integer INPUT_FROM = 1;

	/**
	 *  参数提供外部参数表读取参数起始位置标记-行，默认为0
	 */
	private static int INPUT_ROW_FROM = 1;
	
	/**
	 *  参数提供外部参数表读取参数起始位置标记-列，默认为0
	 */
	private static int INPUT_COL_FROM = 6;
	
	/**
	 *  当前执行用例序号或已执行用例数
	 *  
	 *  初始值为0，但在实际运行中，得到一条用例就+1
	 */
	private static int dRunNum = 0;
	
	/**
	 *  当前用例游标
	 */
	private static int dRowIndex = 0;
	
	/**
	 *  当前读取到的单个用例参数个数上线
	 */
	private static int COL_MAX = 32;
	
	/**
	 *  当前单个用例输入参数游标
	 */
	private static int dColIndex = 0;
	
	/**
	 *  当前测试环境类型
	 *  
	 *  取值范围[0,9]，默认为0
	 * 0-本地
	 * 1-测试系统
	 * 2-生产系统
	 * 3-本地Linux测试服务
	 * 4-本地加网关测试服务
	 */
	private static Integer environment;
	
	/**
	 *  当前执行业务流水号
	 */
	private static String flowNo = "";
	
	/**
	 *  当前发送报文
	 */
	private static String req = ""; 
	
	/**
	 *  当前返回报文
	 */
	private static String res = "";
	
	/**
	 *  最近一个错误的返回报文
	 *  
	 *  最大长度180个汉字
	 */
	private static String recRes = "";
	
	/**
	 *  当前返回码
	 */
	private static String code = "9"; 
	
	/**
	 *  当前返回信息
	 */
	private static String msg = "UNKNOW";
	
	/**
	 *  当前系统时间
	 */
	private static String sysTime = "";
	
	/**
	 *  当前系统主题
	 */
	private static String sysTheme = "";
	
	/**
	 *  当前操作批准时间
	 */
	private static String authorizationTime = "";

	/**
	 *  当前授权验证码
	 */
	private static String veriCode = "";
	
	/**
	 *  当前异常类型编号
	 */
	private static int exceptionType = 0;
	
	/**
	 *  当前异常类型字符串
	 */
	private static String exceptionScr = "";
	
	/**
	 * 性能基线版本
	 */
	private static String baseVersion = "";
	
	/**
	 * 性能上次版本
	 */
	private static String lastVersion = "";

	/**
	 * 严重缓慢闭区间左值
	 */
	private static double failSection = 10000.0d;
	
	/**
	 * 缓慢闭区间左值
	 */
	private static double waringSection = 3000.0d;
	
	/**
	 * 常规闭区间左值
	 */
	private static double lowSection = 1000.0d;
	
	public static String getDriver() {
		return driver;
	}

	public static void setDriver(String driver) {
		GParam.driver = driver;
	}

	public static boolean isDragonShow() {
		return dragonShow;
	}

	public static void setDragonShow(boolean dragonShow) {
		GParam.dragonShow = dragonShow;
	}

	public static boolean isbAutoCheckReport() {
		return bAutoCheckReport;
	}

	public static void setbAutoCheckReport(boolean bAutoCheckReport) {
		GParam.bAutoCheckReport = bAutoCheckReport;
	}

	public static Integer getINPUT_TYPE() {
		return INPUT_TYPE;
	}

	public static void setINPUT_TYPE(Integer iNPUT_TYPE) {
		INPUT_TYPE = iNPUT_TYPE;
	}

	public static Integer getINPUT_FROM() {
		return INPUT_FROM;
	}

	public static void setINPUT_FROM(Integer iNPUT_FROM) {
		INPUT_FROM = iNPUT_FROM;
	}

	public static int getINPUT_ROW_FROM() {
		return INPUT_ROW_FROM;
	}

	public static void setINPUT_ROW_FROM(int iNPUT_ROW_FROM) {
		INPUT_ROW_FROM = iNPUT_ROW_FROM;
	}

	public static int getINPUT_COL_FROM() {
		return INPUT_COL_FROM;
	}

	public static void setINPUT_COL_FROM(int iNPUT_COL_FROM) {
		INPUT_COL_FROM = iNPUT_COL_FROM;
	}

	public static int getdRunNum() {
		return dRunNum;
	}

	public static void setdRunNum(int dRunNum) {
		GParam.dRunNum = dRunNum;
	}

	public static int getdRowIndex() {
		return dRowIndex;
	}

	public static void setdRowIndex(int dRowIndex) {
		GParam.dRowIndex = dRowIndex;
	}

	public static int getCOL_MAX() {
		return COL_MAX;
	}

	public static void setCOL_MAX(int cOL_MAX) {
		COL_MAX = cOL_MAX;
	}

	public static int getdColIndex() {
		return dColIndex;
	}

	public static void setdColIndex(int dColIndex) {
		GParam.dColIndex = dColIndex;
	}

	public static Integer getEnvironment() {
		return environment;
	}

	public static void setEnvironment(Integer environment) {
		GParam.environment = environment;
	}

	public static String getFlowNo() {
		return flowNo;
	}

	public static void setFlowNo(String flowNo) {
		GParam.flowNo = flowNo;
	}

	public static String getReq() {
		return req;
	}

	public static void setReq(String req) {
		GParam.req = req;
	}

	public static String getRes() {
		return res;
	}

	public static void setRes(String res) {
		GParam.res = res;
	}

	public static String getCode() {
		return code;
	}

	public static void setCode(String code) {
		GParam.code = code;
	}

	public static String getMsg() {
		return msg;
	}

	public static void setMsg(String msg) {
		GParam.msg = msg;
	}

	public static String getSysTime() {
		return sysTime;
	}

	public static void setSysTime(String sysTime) {
		GParam.sysTime = sysTime;
	}

	public static String getAuthorizationTime() {
		return authorizationTime;
	}

	public static void setAuthorizationTime(String authorizationTime) {
		GParam.authorizationTime = authorizationTime;
	}

	public static String getVeriCode() {
		return veriCode;
	}

	public static void setVeriCode(String veriCode) {
		GParam.veriCode = veriCode;
	}

	public static int getExceptionType() {
		return exceptionType;
	}

	public static void setExceptionType(int exceptionType) {
		GParam.exceptionType = exceptionType;
	}

	public static String getExceptionScr() {
		return exceptionScr;
	}

	public static void setExceptionScr(String exceptionScr) {
		GParam.exceptionScr = exceptionScr;
	}

	public static String getRecRes() {
		return recRes;
	}
	
	public static String getSysTheme() {
		return sysTheme;
	}

	public static void setSysTheme(String sysTheme) {
		GParam.sysTheme = sysTheme;
	}
	
	public static String getBaseVersion() {
		return baseVersion;
	}

	public static void setBaseVersion(String baseVersion) {
		GParam.baseVersion = baseVersion;
	}

	public static String getLastVersion() {
		return lastVersion;
	}

	public static void setLastVersion(String lastVersion) {
		GParam.lastVersion = lastVersion;
	}
	
	public static double getFailSection() {
		return failSection;
	}

	public static void setFailSection(String failSection) {
		GParam.failSection = Double.valueOf(failSection).doubleValue();
	}

	public static double getWaringSection() {
		return waringSection;
	}

	public static void setWaringSection(String waringSection) {
		GParam.waringSection = Double.valueOf(waringSection).doubleValue();
	}

	public static double getLowSection() {
		return lowSection;
	}

	public static void setLowSection(String lowSection) {
		GParam.lowSection = Double.valueOf(lowSection).doubleValue();
	}

	/**
	 *  重置全局参数
	 */
	public static void resetGParam() {
		code = "9";
		msg = "UNKNOWN";
		GTestCase.setTC_NO(Integer.valueOf(0));
		GTestCase.setTC_TYPE_RES(Integer.valueOf(3));
		sysTime = GTime.getCurrentTime(GTime.FORMAT_14);
		authorizationTime = GTime.getCurrentTime(GTime.FORMAT_14);
		veriCode = "";
		req = "";
		res = "";
		recRes = "";
	}
	
	/**
	 *  设置最近一个错误的返回报文
	 */
	public static void setRecRes(String strError) {
		if(recRes.length() <= 180) {
			recRes = strError + ";" + recRes;
			if(recRes.length() > 180) {
				recRes = recRes.substring(0, 180);
			}
		}
	}
	
	/**
	 *  加载参数
	 */	
	public static void loadConfig() {
		if((!GParam.getDriver().equals(""))
		&& (GParam.getINPUT_TYPE() <= 2) 
		&& (GParam.getINPUT_FROM() <= 1) 
		&& (GParam.getINPUT_ROW_FROM() > 0)
		&& (GParam.getINPUT_COL_FROM() > 0)){
			GAbout.setVersion(GParam.getDriver());
		}else {
			GSys.logErrorSys("One of these GParam params from sysConfig may has no value, Please check again!");
			System.exit(0);
		}
	}
}