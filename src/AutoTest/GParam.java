package AutoTest;

/**
 *  全局参数
 */
public class GParam {
	
	/**
	 *  
	 */
	private GParam(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  当前执行用例序号或已执行用例数
	 *  
	 *  初始值为0，但在实际运行中，得到一条用例就+1
	 */
	public static int dRunNum = 0;
	
	/**
	 *  当前用例游标
	 */
	public static int dRowIndex = 0;
	
	/**
	 *  当前读取到的单个用例参数个数上线
	 */
	public static int COL_MAX = 32;
	
	/**
	 *  当前单个用例输入参数游标
	 */
	public static int dColIndex = 0;
	
	/**
	 *  当前被测件版本号
	 */
	public static String gVersion = "";
	
	/**
	 *  当前执行业务流水号
	 */
	public static String gFlowNo = "";
	
	/**
	 *  当前发送报文
	 */
	public static String gReq = ""; 
	
	/**
	 *  当前返回报文
	 */
	public static String gRes = "";
	
	/**
	 *  当前返回码
	 */
	public static String gCode = "9"; 
	
	/**
	 *  当前返回信息
	 */
	public static String gMsg = "UNKNOW";
	
	/**
	 *  当前系统时间
	 */
	public static String gSysTime = "";
	
	/**
	 *  当前操作批准时间
	 */
	public static String gAuthorizationTime = "";

	/**
	 *  当前授权验证码
	 */
	public static String gVeriCode = "";
	
	/**
	 *  当前异常类型编号
	 */
	public static int gExceptionType = 0;
	
	/**
	 *  当前异常类型字符串
	 */
	public static String gExceptionScr = "";
	
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
	public static Integer gEnvironment;

	/**
	 *  是否开启AI助理
	 */
	public static boolean gDragonShow = false;
	
	/**
	 *  是否在测试完成后自动打开测试报告
	 */
	public static boolean bAutoCheckReport = false;
	
	/**
	 *  参数提供方式
	 *  
	 *  取值范围 [0,9]，默认为0
	 *  0-object集合
	 *  1-Excel表格 
	 *  2-Txt文本
	 */
	public static Integer INPUT_TYPE = 0;
	
	/**
	 *  参数提供来源
	 *  
	 *  取值范围 [0,9]，默认为0
	 *  0-工具内置
	 *  1-外部输入
	 */
	public static Integer INPUT_FROM = 0;

	/**
	 *  参数提供外部参数表读取参数起始位置标记-行，默认为0
	 */
	public static int INPUT_ROW_FROM = 0;
	
	/**
	 *  参数提供外部参数表读取参数起始位置标记-列，默认为0
	 */
	public static int INPUT_COL_FROM = 0;
	
	/**
	 *  重置全局参数
	 */
	public static void resetGParam() {
		gCode = "9";
		gMsg = "UNKNOWN";
		GTestCase.TC_NO = 0;
		GTestCase.TC_TYPE_RES = 3;
		gSysTime = GTime.getCurrentTime(GTime.FORMAT_14);
		gAuthorizationTime = GTime.getCurrentTime(GTime.FORMAT_14);
		gVeriCode = "";
		gReq = "";
		gRes = "";
	}
}