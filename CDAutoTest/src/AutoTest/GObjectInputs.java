package AutoTest;

public class GObjectInputs {
	/**
	 * @see 存放正常场景用例输入
	 */
	private static Object[][] PASSED = null;
	
	/**
	 * @see 正常场景用例个数
	 */
	private static int TestPassedNum;
	
	/**
	 * @see 存放错误码场景用例输入
	 */
	private static Object[][] ERROR = null;
	
	/**
	 * @see 错误码场景用例个数
	 */
	private static int TestErrorNum;
	
	/**
	 * @see 存放装填完成的用例输入
	 */
	private static Object[][] TESTCASES = null;
	
	/**
	 * @see 用例总个数
	 */
	private static int TestTotal;
	
	/**
	 * @see 已执行用例个数
	 */
	public static int TestRunNo = 0;
	
	/**
	 * @see 设置正常场景用例个数
	 */
	public static void setTestPassedNum(int dTestPassedNum) {
		TestPassedNum = dTestPassedNum;
	}
	
	/**
	 * @see 获取正常场景用例个数
	 */
	public static int getTestPassedNum() {
		return TestPassedNum;
	}
	
	/**
	 * @see 设置错误码场景用例个数
	 */
	public static void setTestErrorNum(int dTestErrorNum) {
		TestErrorNum = dTestErrorNum;
	}
	
	/**
	 * @see 获取错误码场景用例个数
	 */
	public static int getTestErrorNum() {
		return TestErrorNum;
	}
	
	/**
	 * @see 设置用例总数
	 */
	public static void setTestTotal(int dTestTotal) {
		TestTotal = dTestTotal;
	}
	
	/**
	 * @see 获取用例总数
	 */
	public static int getTestTotal() {
		return TestTotal;
	}
	
	/**
	 * @see 设置正常场景用例
	 */
	public static boolean AddPassedCases(Object[][] curTestCases){
		GFile.WriteStringToBottom(GSys.Guide,"\r\nADD PASSED TEST CASES\r\n");
		try {
			if(PASSED != null) {
				Object[][] lastPassedCases = null;
				lastPassedCases = (Object[][])PASSED.clone();
				PASSED = null;
				PASSED = new Object[lastPassedCases.length + curTestCases.length][1];
				System.arraycopy(lastPassedCases, 0, PASSED, 0, lastPassedCases.length);
				System.arraycopy(curTestCases, 0, PASSED, lastPassedCases.length, curTestCases.length);
			}else {
				PASSED = (Object[][])curTestCases.clone();
			}
			if(PASSED != null)setTestPassedNum(PASSED.length);
		}catch(Exception e) {
			GFile.WriteStringToBottom(GSys.Guide,GException.getExceptionAllinformation(e));
			e.printStackTrace();
			return false;
		}
		GFile.WriteStringToBottom(GSys.Guide,"\r\nPASSED TEST CASES READY\r\n");
		return true;
	}
	
	/**
	 * @see 重置错误码用例集合
	 */
	public static void resetPassedCases() {
		PASSED = null;
	}
	
	/**
	 * @see 获取正常场景用例集合
	 */
	public static Object[][] getPassedCases() {
		return PASSED;
	}
	
	/**
	 * @see 设置错误码场景用例
	 */
	public static boolean AddErrorCases(Object[][] curTestCases){
		GFile.WriteStringToBottom(GSys.Guide,"\r\nADD ERROR TEST CASES\r\n");
		try {
			if(ERROR != null) {
				Object[][] lastPassedCases = null;
				lastPassedCases = (Object[][])ERROR.clone();
				ERROR = null;
				ERROR = new Object[lastPassedCases.length + curTestCases.length][1];
				System.arraycopy(lastPassedCases, 0, ERROR, 0, lastPassedCases.length);
				System.arraycopy(curTestCases, 0, ERROR, lastPassedCases.length, curTestCases.length);
			}else {
				ERROR = (Object[][])curTestCases.clone();
			}
			if(ERROR != null)setTestErrorNum(ERROR.length);
		}catch(Exception e) {
			GFile.WriteStringToBottom(GSys.Guide,GException.getExceptionAllinformation(e));
			e.printStackTrace();
			return false;
		}
		GFile.WriteStringToBottom(GSys.Guide,"\r\nERROR TEST CASES READY\r\n");
		return true;
	}
	
	/**
	 * @see 重置错误码用例集合
	 */
	public static void resetErrorCases() {
		ERROR = null;
	}
	
	/**
	 * @see 获取错误码场景用例集合
	 */
	public static Object[][] getErrorCases() {
		return ERROR;
	}
	
	/**
	 * @see 获取用例总数
	 */
	public static boolean LoadTestCases(){
		GFile.WriteStringToBottom(GSys.Guide,"\r\nSTART LOADING TEST CASES\r\n");
		try {
			if(PASSED != null && ERROR != null) {
				TESTCASES = new Object[PASSED.length + ERROR.length][1];
				System.arraycopy(PASSED, 0, TESTCASES, 0, PASSED.length);
				System.arraycopy(ERROR, 0, TESTCASES, PASSED.length, ERROR.length);
			}else if (PASSED != null && ERROR == null) {
				TESTCASES = (Object[][])PASSED.clone();
			}else if (PASSED == null && ERROR != null) {
				TESTCASES = (Object[][])ERROR.clone();
			}
			if(TESTCASES != null)setTestTotal(TESTCASES.length);
		}catch(Exception e) {
			GFile.WriteStringToBottom(GSys.Guide,GException.getExceptionAllinformation(e));
			e.printStackTrace();
			return false;
		}
		GFile.WriteStringToBottom(GSys.Guide,"\r\nTEST CASES READY\r\n");
		return true;
	}
	
	/**
	 * @see 重置所有用例集合
	 */
	public static void resetTestCases() {
		TESTCASES = null;
	}
	
	/**
	 * @see 获取所有用例集合
	 */
	public static Object[][] getTestCases() {
		return TESTCASES;
	}
	
	/**
	 * @see 字符串数二维组转为列表<列表>
	 */
	public static void StringArrayToList() {
		TESTCASES = null;
	}
	
}
