package AutoTest;

/**
 *  全局参数
 */
public class GParam {
	private GParam(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  有效类个数
	 */
	public static Integer dTestReal = 0;
	
	/**
	 *  失败类个数
	 */
	public static Integer dTestFail = 0;
	
	/**
	 *  异常场景类个数
	 */
	public static Integer dTestUnReal = 0;
	
	/**
	 *  中断类个数
	 */
	public static Integer dTestUnDo = 0;
	
	/**
	 *  计划执行用例总数
	 */
	public static Integer dTestTotalNo = 0; 
	
	/**
	 *  当前执行用例序号
	 */
	public static int curCaseNO = 0;
	
	/**
	 *  当前执行业务流水号
	 */
	public static String curFlowNo = "";
	
	/**
	 *  当前返回码
	 */
	public static String strTestResultCode = "9"; 
	
	/**
	 *  当前返回信息
	 */
	public static String strTestResultMsg = "UNKNOW";
	
	/**
	 *  当前执行总数最大值
	 */
	public static final int CASE_NUM_MAX = 250000;
	
	/**
	 *  单个用例参数个数上限
	 */
	public static final int PARAM_NUM_MAX = 32;
	
	/**
	 *  单个用例输入参数游标
	 */
	public static int dParamIndex = 0;
	
	/**
	 *  用例游标
	 */
	public static int strTestCaseIndex = 0;
	
	/**
	 *  XLS输入文件目录
	 */
	public static final String INPUT_XLS_PATH = "./input/";
	
	/**
	 *  XLS输入文件文件名
	 */
	public static final String INPUT_XLS_NAME = "testcase.xls";
	
	/**
	 *  TXT输入文件目录
	 */
	public static final String INPUT_TXT_PATH = "./input/";
	
	/**
	 *  TXT输入文件文件名
	 */
	public static final String INPUT_TXT_NAME = "testcase.txt";
	
	/**
	 *  读取到的用例总数
	 */
	private static int dTestCaseNum = 0;
	
	/**
	 *  设置用例总数最大值
	 *  
	 *  @param dNum 形参
	 */
	public static void setTestCaseNum(int dNum) {
		dTestCaseNum = dNum;
	}
	
	/**
	 *  获得用例总数最大值
	 *  
	 *  @return 返回已加载的值
	 */
	public static int getTestCaseNum() {
		return dTestCaseNum;
	}
	
	/**
	 *  读取到的单个用例参数总数
	 */
	private static int dTestParamNum = PARAM_NUM_MAX;
	
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
	 * 【核心数据结构】
	 *  输入参数文件缓存-Strin[][]g类型
	 *  用例输入详情：按照“行列”的形式，将输入参数文件全部读入到缓存中，包含参数文件的【字段名】行和【序号】列
	 */
	public static String[][] strTestCaseInputArray = null;
	
	/**
	 *  设置用例的数组行列值
	 *  
	 *  @param paramNum 单个用例参数个数
	 *  @param testCaseNum 用例总个数
	 */
	public static void initParamAndTestCaseNum(int paramNum, int testCaseNum) {
		if((paramNum > 0) && (testCaseNum > 0)) {
			setTestCaseNum(testCaseNum);
			strTestCaseInputArray = new String[testCaseNum][paramNum];
		}
		
		for (int i = 0; i < testCaseNum; i++) {
			for (int j = 0; j < paramNum; j++) {
				strTestCaseInputArray[i][j] = "empty";
			}
		}
	}
	
	/**
	 *  被测件版本号
	 */
	public static String strTestVersion = "TARGETv1.0.0.0";
	
	/**
	 *  是否连接CS服务器,测试脚本结构时使用
	 */
	public static boolean bIsConnCSServer = false;
	
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
	 *  测试结果打包名称
	 */
	public static final String BACKUPFILE = GPath.strPathStyle[9] + "backup.zip";// 
	
	/**
	 *  是否备份测试结果
	 */
	public static boolean bTestOutputBackupResult = false;
	
	/**
	 *  是否启用备份
	 */
	public static boolean bIsBackup = false;

	
	/**
	 *  授权验证码
	 */
	public static String strVeriCode = "";
	
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
	 *   可以设置将读入的参数表打印只特定的日志文档的条数，此项数字越大，执行速度越慢
	 */
	public static int dRecordInputParamListInTxt = 0;
	
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