package AutoTest;

/**
 *  用例输入处理
 */
public class GObjectInputs {
	private GObjectInputs(){
		System.out.println("This is a tool class.");
	}
	
	/**
	 *   配置单个用例参数个数最大值
	 */
	public static final int PARAM_NUM_MAX_OBJS = 32;
	
	/**
	 *  存放正常场景用例输入
	 */
	private static Object[][] PASSED = null;
	
	/**
	 *  正常场景用例个数
	 */
	private static int TestPassedNum = 0;
	
	/**
	 *  存放错误码场景用例输入
	 */
	private static Object[][] ERROR = null;
	
	/**
	 *  错误码场景用例个数
	 */
	private static int TestErrorNum = 0;
	
	/**
	 *  存放装填完成的用例输入
	 */
	private static Object[][] TESTCASES = null;
	
	/**
	 *  用例总个数
	 */
	private static int TestTotal = 0;
	
	/**
	 *  已执行用例个数
	 */
	public static int TestRunNo = 0;
	
	/**
	 *  设置正常场景用例个数
	 */
	public static void setTestPassedNum(int dTestPassedNum) {
		TestPassedNum = dTestPassedNum;
	}
	
	/**
	 *  获取正常场景用例个数
	 */
	public static int getTestPassedNum() {
		return TestPassedNum;
	}
	
	/**
	 *  设置错误码场景用例个数
	 */
	public static void setTestErrorNum(int dTestErrorNum) {
		TestErrorNum = dTestErrorNum;
	}
	
	/**
	 *  获取错误码场景用例个数
	 */
	public static int getTestErrorNum() {
		return TestErrorNum;
	}
	
	/**
	 *  设置用例总数
	 */
	public static void setTestTotal(int dTestTotal) {
		TestTotal = dTestTotal;
	}
	
	/**
	 *  获取用例总数
	 */
	public static int getTestTotal() {
		return TestTotal;
	}

	/**
	 *  设置正常场景用例
	 */
	public static boolean AddPassedCases(Object[][] curTestCases){
		GSys.GLogSys("ADD PASSED TEST CASES");
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
			GSys.GLogErrorSys(GExp.getExceptionAllinformation(e));
			e.printStackTrace();
			return false;
		}
		GSys.GLogSys("PASSED TEST CASES READY");
		return true;
	}
	
	/**
	 *  重置错误码用例集合
	 */
	public static void resetPassedCases() {
		PASSED = null;
	}
	
	/**
	 *  获取正常场景用例集合
	 */
	public static Object[][] getPassedCases() {
		return PASSED;
	}
	
	/**
	 *  设置错误码场景用例
	 */
	public static boolean AddErrorCases(Object[][] curTestCases){
		GSys.GLogSys("ADD ERROR TEST CASES");
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
			GSys.GLogErrorSys(GExp.getExceptionAllinformation(e));
			e.printStackTrace();
			return false;
		}
		GSys.GLogSys("ERROR TEST CASES READY");
		return true;
	}
	
	/**
	 *  重置错误码用例集合
	 */
	public static void resetErrorCases() {
		ERROR = null;
	}
	
	/**
	 *  获取错误码场景用例集合
	 */
	public static Object[][] getErrorCases() {
		return ERROR;
	}
	
	/**
	 *  获取用例总数
	 */
	public static boolean LoadTestCases(){
		GSys.GLogSys("START LOADING TEST CASES");
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
			if(TESTCASES != null) {
				setTestTotal(TESTCASES.length);
			}
			
		}catch(Exception e) {
			GSys.GLogErrorSys(GExp.getExceptionAllinformation(e));
			e.printStackTrace();
			return false;
		}
		GSys.GLogSys("TEST CASES READY");
		return true;
	}
	
	/**
	 *  重置所有用例集合
	 */
	public static void resetTestCases() {
		TESTCASES = null;
	}
	
	/**
	 *  获取所有用例集合
	 */
	public static Object[][] getTestCases() {
		return TESTCASES;
	}
	
	/**
	 *  获取所有用例集合的String类型结果
	 */
	public static String[][] getTestCasesToString() {
		
		String[][] strTESTCASES = null;
		if(TESTCASES != null)
			strTESTCASES = new String[TESTCASES.length][TESTCASES[0].length];
		
		if(strTESTCASES != null && TESTCASES != null) {
			for (int i = 0; i < TESTCASES.length; i++) {
				for (int j = 0; j < TESTCASES[0].length; j++) {
					if(TESTCASES[i][j] != null)
						strTESTCASES[i][j] = TESTCASES[i][j].toString();
				}
			}
		}
		
		return strTESTCASES;
	}
	
	/**
	 *  获取当前集合参数表行数
	 */
	public static int getInputTxtRowCourt() {
		//准备有效场景
		AddPassedCases(new Object[][]{/*23*/
			{"测试环境类型", "用例类型", "1001"},
			{0, 0, 1001},
		});
		//准备异常场景
		AddErrorCases(new Object[][]{/*74*/
			{0, 1, 100101},{0, 2, 100102},{0, 3, 100103},
		});
		//加载上述场景
		LoadTestCases();
		
		return TestTotal;
	}
	
	/**
	 *  加载参数到内存
	 */
	public static boolean importObjectInputs() {
		
		if(TESTCASES != null) {
			for (int i = 0; i < TestTotal; i++) {
				for (int j = 0; j < TESTCASES[i].length; j++) {
					if(TESTCASES[i][j] != null)
						GParam.TestCaseInputArray[i][j] = TESTCASES[i][j].toString();
				}
			}
			
			return true;
		}
		
		return false;

	}
}
