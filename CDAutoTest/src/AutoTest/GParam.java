package AutoTest;

/**
 *  全局参数
 */
public class GParam {
	/**
	 *  有效类个数
	 */
	public static Integer TestReal = 0;
	
	/**
	 *  失败类个数
	 */
	public static Integer TestFail = 0;
	
	/**
	 *  异常场景类个数
	 */
	public static Integer TestUnReal = 0;
	
	/**
	 *  中断类个数
	 */
	public static Integer TestUnDo = 0;
	
	/**
	 *  计划执行用例总数
	 */
	public static Integer TestTotalNo = 0; 
	
	/**
	 *  当前执行用例序号
	 */
	public static int curCaseNO = 0;
	
	/**
	 *  当前执行业务流水号
	 */
	public static String curFlowNo = "";
	
	/**
	 *  当前执行总数最大值
	 */
	public static int curCaseNO_MAX = 1024;
	
	/**
	 *  当前返回码
	 */
	public static String TestResultCode = "9"; 
	
	/**
	 *  当前返回信息
	 */
	public static String TestResultMsg = "UNKNOW";
	
	/**
	 *  单个用例输入参数游标
	 */
	public static int ParamIndex = 0;
	
	/**
	 *  用例游标
	 */
	public static int TestCaseIndex = 0; 
	
	/**
	 *  单个用例参数个数上限
	 */
	private static int ParamNum_MAX = 0;
	
	/**
	 *  通信证书全名
	 */
	public static void setTestParamNum_MAX(int dParamNum_MAX) {
		ParamNum_MAX = dParamNum_MAX;
	}
	
	/**
	 *  通信证书全名
	 */
	public static int getTestParamNum_MAX() {
		return ParamNum_MAX;
	}
	
	/**
	 *  用例总数最大值
	 */
	private static int TestCaseNum_MAX = 0;
	
	/**
	 *  设置用例总数最大值
	 */
	public static void setTestCaseNum_MAX(int dTestCaseNum_MAX) {
		TestCaseNum_MAX = dTestCaseNum_MAX;
	}
	
	/**
	 *  获得用例总数最大值
	 */
	public static int getTestCaseNum_MAX() {
		return TestCaseNum_MAX;
	}
	
	/**
	 *  用例输入详情
	 */
	public static String[][] TestCaseInputArray = null;
	
	/**
	 *  自动化测试框架件版本号
	 */
	public static String TestAutoVersion = "3.0.1.1";
	
	/**
	 *  被测件版本号
	 */
	public static String TestVersion = "";
	
	/**
	 *  是否连接CS服务器,测试脚本结构时使用
	 */
	public static boolean IsConnCSServer = false;
	
	/**
	 *  系统时间
	 */
	public static String SysTime = GTime.getCurrentTime(GTime.FORMAT_14);
	
	/**
	 *  操作批准时间
	 */
	public static String AuthorizationTime = GTime.getCurrentTime(GTime.FORMAT_14);
	
	/**
	 *  用例输入参数Excel文件命名
	 */
	public static String TestCaseInputExcelFullName = "";
	
	/**
	 *  设置用例输入参数Excel文件全名
	 */
	public static void setTestCaseInputExcelFullName(String strTestCaseInputExcelFullName) {
		TestCaseInputExcelFullName = strTestCaseInputExcelFullName;
	}
	
	/**
	 *  获得用例输入参数Excel文件全名
	 */
	public static String getTestCaseInputExcelFullName() {
		return TestCaseInputExcelFullName;
	}
	
	/**
	 *  用例输出参数Excel文件全名
	 */
	public static String TestCaseOutputExcelFullName = "";// 用例输入参数Excel文件命名
	
	/**
	 *  设置用例输出参数Excel文件全名
	 */
	public static void setTestCaseOutputExcelFullName(String strTestCaseOutputExcelFullName) {
		TestCaseOutputExcelFullName = strTestCaseOutputExcelFullName;
	}
	
	/**
	 *  获得用例输出参数Excel文件全名
	 */
	public static String getTestCaseOutputExcelFullName() {
		return TestCaseOutputExcelFullName;
	}
	
	/**
	 *  测试结果打包路径
	 */
	public static String TestOutputBackupPath = GPath.PathStyle[9];
	
	/**
	 *  测试结果打包名称
	 */
	public static String TestOutputBackupName = TestOutputBackupPath + "backup.zip";// 
	
	/**
	 *  是否备份测试结果
	 */
	public static boolean TestOutputBackupResult = false;
	
	/**
	 *  授权验证码
	 */
	public static String VeriCode = "";
	
	/**
	 *  发送报文
	 */
	public static String gReq = ""; 
	
	/**
	 *  返回报文
	 */
	public static String gRes = "";
	
	/**
	 *  异常类型编号
	 */
	public static int gIntExceptionType = 0;
	
	/**
	 *  异常类型字符串
	 */
	public static String gStrExceptionType = "";
	
	/**
	 *  测试环境类型：0-本地 1-测试系统 2-生产系统 3-本地Linux测试服务 4-本地加网关测试服务
	 */
	public static Integer gEnvironment;

	/**
	 *  重置全局参数
	 */
	public static void resetGParam() {
		TestResultCode = "9";
		TestResultMsg = "UNKNOWN";
		GTestCase.TSNO = 0;
		SysTime = GTime.getCurrentTime(GTime.FORMAT_14);
		AuthorizationTime = GTime.getCurrentTime(GTime.FORMAT_14);
		VeriCode = "";
		gReq = "";
		gRes = "";
	}
}