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
	/**
	 *  获得是否为WebUI驱动模式
	 *
	 *  @return 是否为WebUI驱动模式
	 */
	public String getDriver() {
		return this.isWeDriver;
	}
	/**
	 *  设置是否为WebUI驱动模式
	 *
	 *  @param isWebDriverTemp 是否为WebUI驱动模式
	 */
	public void setDriver(String isWebDriverTemp) {
		this.isWeDriver = isWebDriverTemp;
	}
	
	/**
	 *  是否开启AI助理
	 */
	private boolean dragonShow = false;
	/**
	 *  获得是否开启AI助理
	 *
	 *  @return 是否开启AI助理
	 */
	public boolean isDragonShow() {
		return this.dragonShow;
	}
	/**
	 *  设置是否开启AI助理
	 *
	 *  @param dragonShow 是否开启AI助理
	 */
	public void setDragonShow(boolean dragonShow) {
		this.dragonShow = dragonShow;
	}
	
	/**
	 *  是否在测试完成后自动打开测试报告
	 */
	private boolean bAutoCheckReport = false;
	/**
	 *  获得是否在测试完成后自动打开测试报告
	 *
	 *  @return 是否在测试完成后自动打开测试报告
	 */
	public boolean isbAutoCheckReport() { return this.bAutoCheckReport; }
	/**
	 *  设置是否在测试完成后自动打开测试报告
	 *
	 *  @param bAutoCheckReport 是否在测试完成后自动打开测试报告
	 */
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
	/**
	 *  获得参数提供方式
	 *
	 *  @return 参数提供方式
	 */
	public Integer getINPUT_TYPE() {
		return this.INPUT_TYPE;
	}
	/**
	 *  设置参数提供方式
	 *
	 *  @param iNPUT_TYPE 参数提供方式
	 */
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
	/**
	 *  获得参数提供来源
	 *
	 *  @return 参数提供来源
	 */
	public Integer getINPUT_FROM() {
		return this.INPUT_FROM;
	}
	/**
	 *  设置参数提供来源
	 *
	 *  @param iNPUT_FROM 参数提供来源
	 */
	public void setINPUT_FROM(Integer iNPUT_FROM) {
		this.INPUT_FROM = iNPUT_FROM;
	}

	/**
	 *  参数提供外部参数表读取参数起始位置标记-行，默认为0
	 */
	private int INPUT_ROW_FROM = 1;
	/**
	 *  获得参数提供外部参数表读取参数起始位置标记-行，默认为0
	 *
	 *  @return 参数提供外部参数表读取参数起始位置标记-行，默认为0
	 */
	public int getINPUT_ROW_FROM() {
		return this.INPUT_ROW_FROM;
	}
	/**
	 *  设置参数提供外部参数表读取参数起始位置标记-行，默认为0
	 *
	 *  @param iNPUT_ROW_FROM 参数提供外部参数表读取参数起始位置标记-行，默认为0
	 */
	public void setINPUT_ROW_FROM(int iNPUT_ROW_FROM) {
		this.INPUT_ROW_FROM = iNPUT_ROW_FROM;
	}
	
	/**
	 *  参数提供外部参数表读取参数起始位置标记-列，默认为0
	 */
	private int INPUT_COL_FROM = 6;
	/**
	 *  获得参数提供外部参数表读取参数起始位置标记-列，默认为0
	 *
	 *  @return 参数提供外部参数表读取参数起始位置标记-列，默认为0
	 */
	public int getINPUT_COL_FROM() {
		return this.INPUT_COL_FROM;
	}
	/**
	 *  设置参数提供外部参数表读取参数起始位置标记-列，默认为0
	 *
	 *  @param iNPUT_COL_FROM 参数提供外部参数表读取参数起始位置标记-列，默认为0
	 */
	public void setINPUT_COL_FROM(int iNPUT_COL_FROM) {
		this.INPUT_COL_FROM = iNPUT_COL_FROM;
	}
	
	/**
	 *  当前执行用例序号或已执行用例数
	 *  初始值为0，但在实际运行中，执行一条用例此数值就+1
	 */
	private int dRunNum = 0;
	/**
	 *  获得当前执行用例序号或已执行用例数
	 *
	 *  @return 当前执行用例序号或已执行用例数
	 */
	public int getdRunNum() {
		return this.dRunNum;
	}
	/**
	 *  设置当前执行用例序号或已执行用例数
	 *
	 *  @param dRunNum 当前执行用例序号或已执行用例数
	 */
	public void setdRunNum(int dRunNum) {
		this.dRunNum = dRunNum;
	}
	
	/**
	 *  当前用例游标
	 */
	private int dRowIndex = 0;
	/**
	 *  获得当前用例游标
	 *
	 *  @return 当前用例游标
	 */
	public int getdRowIndex() {
		return this.dRowIndex;
	}
	/**
	 *  设置当前用例游标
	 *
	 *  @param dRowIndex 当前用例游标
	 */
	public void setdRowIndex(int dRowIndex) {
		this.dRowIndex = dRowIndex;
	}
	
	/**
	 *  当前读取到的单个用例参数个数上线
	 */
	private int COL_MAX = 32;
	/**
	 *  获得当前读取到的单个用例参数个数上线
	 *
	 *  @return 当前读取到的单个用例参数个数上线
	 */
	public int getCOL_MAX() {
		return this.COL_MAX;
	}
	/**
	 *  设置当前读取到的单个用例参数个数上线
	 *
	 *  @param cOL_MAX 当前读取到的单个用例参数个数上线
	 */
	public void setCOL_MAX(int cOL_MAX) {
		this.COL_MAX = cOL_MAX;
	}

	/**
	 *  当前单个用例输入参数游标
	 */
	private int dColIndex = 0;
	/**
	 *  获得当前单个用例输入参数游标
	 *
	 *  @return 当前单个用例输入参数游标
	 */
	public int getdColIndex() {
		return this.dColIndex;
	}
	/**
	 *  设置当前单个用例输入参数游标
	 *
	 *  @param dColIndex 当前单个用例输入参数游标
	 */
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
	/**
	 *  获得当前测试环境类型
	 *
	 *  @return 当前测试环境类型
	 */
	public Integer getEnvironment() {
		return this.environment;
	}
	/**
	 *  设置当前测试环境类型
	 *
	 *  @param environment 当前测试环境类型
	 */
	public void setEnvironment(Integer environment) {
		this.environment = environment;
	}
	
	/**
	 *  当前执行业务流水号
	 */
	private String flowNo = "";
	/**
	 *  获得当前执行业务流水号
	 *
	 *  @return 当前执行业务流水号
	 */
	public String getFlowNo() {
		return this.flowNo;
	}
	/**
	 *  设置当前执行业务流水号
	 *
	 *  @param flowNo 当前执行业务流水号
	 */
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	
	/**
	 *  当前请求报文
	 */
	private String req = "";
	/**
	 *  获得当前请求报文
	 *
	 *  @return 当前请求报文
	 */
	public String getReq() {
		return this.req;
	}
	/**
	 *  设置当前请求报文
	 *
	 *  @param req 当前请求报文
	 */
	public void setReq(String req) {
		this.req = req;
	}
	
	/**
	 *  当前响应报文
	 */
	private String res = "";
	/**
	 *  获得当前响应报文
	 *
	 *  @return 当前响应报文
	 */
	public String getRes() {
		return this.res;
	}
	/**
	 *  设置当前响应报文
	 *
	 *  @param res 当前响应报文
	 */
	public void setRes(String res) {
		this.res = res;
	}
	
	/**
	 *  最近一个错误的返回报文
	 *  最大长度180个汉字
	 */
	private String recRes = "";
	/**
	 *  获得最近一个错误的返回报文
	 *
	 *  @return 最近一个错误的返回报文
	 */
	public String getRecRes() {
		return this.recRes;
	}
	/**
	 *  把新的错误信息追加到返回报文头部
	 *
	 *  <p>以 {@code ";"} 连接新旧内容，并截断到 180 个字符，避免日志无限增长。
	 *  已达 180 个字符时不再追加。</p>
	 *
	 *  @param strError 新的错误信息
	 */
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
	/**
	 *  获得当前返回码
	 *
	 *  @return 当前返回码
	 */
	public String getCode() {
		return this.code;
	}
	/**
	 *  设置当前返回码
	 *
	 *  @param code 当前返回码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 *  当前返回信息
	 */
	private String msg = "UNKNOW";
	/**
	 *  获得当前返回信息
	 *
	 *  @return 当前返回信息
	 */
	public String getMsg() {
		return this.msg;
	}
	/**
	 *  设置当前返回信息
	 *
	 *  @param msg 当前返回信息
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	/**
	 *  当前系统时间
	 */
	private String sysTime = "";
	/**
	 *  获得当前系统时间
	 *
	 *  @return 当前系统时间
	 */
	public String getSysTime() {
		return this.sysTime;
	}
	/**
	 *  设置当前系统时间
	 *
	 *  @param sysTime 当前系统时间
	 */
	public void setSysTime(String sysTime) {
		this.sysTime = sysTime;
	}

	/**
	 *  当前操作批准时间
	 */
	private String authorizationTime = "";
	/**
	 *  获得当前操作批准时间
	 *
	 *  @return 当前操作批准时间
	 */
	public String getAuthorizationTime() {
		return this.authorizationTime;
	}
	/**
	 *  设置当前操作批准时间
	 *
	 *  @param authorizationTime 当前操作批准时间
	 */
	public void setAuthorizationTime(String authorizationTime) {
		this.authorizationTime = authorizationTime;
	}
	
	/**
	 *  当前系统主题
	 */
	private String sysTheme = "";
	/**
	 *  获得当前系统主题
	 *
	 *  @return 当前系统主题
	 */
	public String getSysTheme() {
		return this.sysTheme;
	}
	/**
	 *  设置当前系统主题
	 *
	 *  @param sysTheme 当前系统主题
	 */
	public void setSysTheme(String sysTheme) { this.sysTheme = sysTheme; }

	/**
	 *  当前授权验证码
	 */
	private String veriCode = "";
	/**
	 *  获得当前授权验证码
	 *
	 *  @return 当前授权验证码
	 */
	public String getVeriCode() {
		return this.veriCode;
	}
	/**
	 *  设置当前授权验证码
	 *
	 *  @param veriCode 当前授权验证码
	 */
	public void setVeriCode(String veriCode) {
		this.veriCode = veriCode;
	}
	
	/**
	 *  当前异常类型编号
	 */
	private int exceptionType = 0;
	/**
	 *  获得当前异常类型编号
	 *
	 *  @return 当前异常类型编号
	 */
	public int getExceptionType() {
		return this.exceptionType;
	}
	/**
	 *  设置当前异常类型编号
	 *
	 *  @param exceptionType 当前异常类型编号
	 */
	public void setExceptionType(int exceptionType) {
		this.exceptionType = exceptionType;
	}
	
	/**
	 *  当前异常类型字符串
	 */
	private String exceptionScr = "";
	/**
	 *  获得当前异常类型字符串
	 *
	 *  @return 当前异常类型字符串
	 */
	public String getExceptionScr() {
		return this.exceptionScr;
	}
	/**
	 *  设置当前异常类型字符串
	 *
	 *  @param exceptionScr 当前异常类型字符串
	 */
	public void setExceptionScr(String exceptionScr) {
		this.exceptionScr = exceptionScr;
	}
	
	/**
	 * 性能基线版本
	 */
	private String baseVersion = "";
	/**
	 *  获得性能基线版本
	 *
	 *  @return 性能基线版本
	 */
	public String getBaseVersion() {
		return this.baseVersion;
	}
	/**
	 *  设置性能基线版本
	 *
	 *  @param baseVersion 性能基线版本
	 */
	public void setBaseVersion(String baseVersion) {
		this.baseVersion = baseVersion;
	}
	
	/**
	 * 性能上次版本
	 */
	private String lastVersion = "";
	/**
	 *  获得性能上次版本
	 *
	 *  @return 性能上次版本
	 */
	public String getLastVersion() {
		return this.lastVersion;
	}
	/**
	 *  设置性能上次版本
	 *
	 *  @param lastVersion 性能上次版本
	 */
	public void setLastVersion(String lastVersion) {
		this.lastVersion = lastVersion;
	}

	/**
	 * 严重缓慢闭区间左值
	 */
	private static double failSection = 10000.0d;
	/**
	 *  获得严重缓慢闭区间左值
	 *
	 *  @return 严重缓慢闭区间左值
	 */
	public static double getFailSection() {
		return failSection;
	}
	/**
	 *  设置严重缓慢闭区间左值
	 *
	 *  @param failSection 待解析的数值字符串，如 {@code 5000}
	 *  @throws NumberFormatException 字符串不是合法浮点数时抛出
	 */
	public static void setFailSection(String failSection) { GParam.failSection = Double.parseDouble(failSection); }
	
	/**
	 * 缓慢闭区间左值
	 */
	private double waringSection = 3000.0d;
	/**
	 *  获得缓慢闭区间左值
	 *
	 *  @return 缓慢闭区间左值
	 */
	public double getWaringSection() {
		return this.waringSection;
	}
	/**
	 *  设置缓慢闭区间左值
	 *
	 *  @param waringSection 待解析的数值字符串，如 {@code 3000}
	 *  @throws NumberFormatException 字符串不是合法浮点数时抛出
	 */
	public  void setWaringSection(String waringSection) { this.waringSection = Double.parseDouble(waringSection); }
	
	/**
	 * 常规闭区间左值
	 */
	private double lowSection = 1000.0d;
	/**
	 *  获得常规闭区间左值
	 *
	 *  @return 常规闭区间左值
	 */
	public double getLowSection() {
		return this.lowSection;
	}
	/**
	 *  设置常规闭区间左值
	 *
	 *  @param lowSection 待解析的数值字符串，如 {@code 1000}
	 *  @throws NumberFormatException 字符串不是合法浮点数时抛出
	 */
	public void setLowSection(String lowSection) {
		this.lowSection = Double.parseDouble(lowSection);
	}

    /**
     * 全局计数器
     */
    private int counter = 0;
    /**
     *  获得全局计数器
     *
     *  @return 全局计数器
     */
    public int getCounter() {
        return this.counter;
    }
    /**
     *  设置全局计数器
     *
     *  @param dNum 全局计数器
     */
    public void setCounter(int dNum) {
        this.counter = dNum;
    }
    /**
     *  全局计数器自增 1
     */
    public void setCounterPlusOne() {
        this.counter ++;
    }
    /**
     *  全局计数器自减 1
     */
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
			throw new IllegalStateException("INPUT_TYPE/INPUT_FROM/INPUT_ROW_FROM/INPUT_COL_FROM 配置无效，请检查 sys.ini");
		}
	}
}