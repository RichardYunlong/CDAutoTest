package AutoTest;

/**
 *  全局参数
 */
public class GParam {
	private GParam(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  当前执行用例序号
	 */
	public static int strCaseNO = 0;
	
	/**
	 *  当前执行业务流水号
	 */
	public static String strFlowNo = "";
	
	/**
	 *  当前返回码
	 */
	public static String strTestResultCode = "9"; 
	
	/**
	 *  当前返回信息
	 */
	public static String strTestResultMsg = "UNKNOW";
	
	/**
	 *  单个用例输入参数游标
	 */
	public static int dParamIndex = 0;
	
	/**
	 *  用例游标
	 */
	public static int dTestCaseIndex = 0;
	
	/**
	 *  读取到的单个用例参数总数
	 */
	private static int dTestParamNum = GTestPlan.PARAM_NUM_MAX;
	
	/**
	 *  设置用例总数最大值
	 *  
	 *  @param dNum 形参
	 */
	public static void setTestParamNum(int dNum) {
		dTestParamNum = dNum;
	}
	
	/**
	 *  获得用例总数最大值
	 *  
	 *  @return 返回已加载的值
	 */
	public static int getTestParamNum() {
		return dTestParamNum;
	}
	
	/**
	 * 当前被测件版本号
	 */
	public static String strTestVersion = "v1.0.0.0";
	
	/**
	 *  系统时间
	 */
	public static String strSysTime = GTime.getCurrentTime(GTime.FORMAT_14);
	
	/**
	 *  操作批准时间
	 */
	public static String strAuthorizationTime = GTime.getCurrentTime(GTime.FORMAT_14);
	
	/**
	 *  用例输入参数Excel文件命名
	 */
	private static String strTestCaseInputFullName = "";
	
	/**
	 *  设置用例输入参数Excel文件全名
	 *  
	 *  @param strInputFullName 源文件全名
	 */
	public static void setTestCaseInputFullName(String strInputFullName) {
		strTestCaseInputFullName = strInputFullName;
	}
	
	/**
	 *  获得用例输入参数Excel文件全名
	 */
	public static String getTestCaseInputFullName() {
		return strTestCaseInputFullName;
	}
	
	/**
	 *  用例输出参数Excel文件全名
	 */
	private static String strTestCaseOutputFullName = "";
	
	/**
	 *  设置用例输出参数Excel文件全名
	 *  
	 *  @param strFullName 源文件全名
	 */
	public static void setTestCaseOutputFullName(String strFullName) {
		strTestCaseOutputFullName = strFullName;
	}
	
	/**
	 *  获得用例输出参数Excel文件全名
	 */
	public static String getTestCaseOutputFullName() {
		return strTestCaseOutputFullName;
	}

	
	/**
	 *  当前授权验证码
	 */
	public static String strVeriCode = "";
	
	/**
	 *  当前发送报文
	 */
	public static String gReq = ""; 
	
	/**
	 *  当前返回报文
	 */
	public static String gRes = "";
	
	/**
	 *  当前异常类型编号
	 */
	public static int gIntExceptionType = 0;
	
	/**
	 *  当前异常类型字符串
	 */
	public static String gStrExceptionType = "";
	
	/**
	 *  当前测试环境类型：0-本地 1-测试系统 2-生产系统 3-本地Linux测试服务 4-本地加网关测试服务
	 */
	public static Integer gEnvironment;
	
	/**
	 *  重置全局参数
	 */
	public static void resetGParam() {
		strTestResultCode = "9";
		strTestResultMsg = "UNKNOWN";
		GTestCase.dTSNO = 0;
		GTestCase.dTSSTYLE = 3;
		strSysTime = GTime.getCurrentTime(GTime.FORMAT_14);
		strAuthorizationTime = GTime.getCurrentTime(GTime.FORMAT_14);
		strVeriCode = "";
		gReq = "";
		gRes = "";
	}
}