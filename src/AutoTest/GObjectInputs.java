package AutoTest;

/**
 *  用例输入处理
 */
public class GObjectInputs {
	private GObjectInputs(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  存放正常场景用例输入
	 */
	private static Object[][] objPASSED = null;
	
	/**
	 *  正常场景用例个数
	 */
	private static int dTestPassedNum = 0;
	
	/**
	 *  存放错误码场景用例输入
	 */
	private static Object[][] objERROR = null;
	
	/**
	 *  错误码场景用例个数
	 */
	private static int dTestErrorNum = 0;
	
	/**
	 *  存放装填完成的用例输入
	 */
	private static Object[][] objTESTCASES = null;
	
	/**
	 *  用例总个数
	 */
	private static int dTestTotal = 0;
	
	/**
	 *  已执行用例个数
	 */
	public static int dTestRunNo = 0;
	
	/**
	 *  设置正常场景用例个数
	 */
	public static void setTestPassedNum(int dPassedNum) {
		dTestPassedNum = dPassedNum;
	}
	
	/**
	 *  获取正常场景用例个数
	 */
	public static int getTestPassedNum() {
		return dTestPassedNum;
	}
	
	/**
	 *  设置错误码场景用例个数
	 */
	public static void setTestErrorNum(int dErrorNum) {
		dTestErrorNum = dErrorNum;
	}
	
	/**
	 *  获取错误码场景用例个数
	 */
	public static int getTestErrorNum() {
		return dTestErrorNum;
	}
	
	/**
	 *  设置用例总数
	 */
	public static void setTestTotal(int dTotal) {
		dTestTotal = dTotal;
	}
	
	/**
	 *  获取用例总数
	 */
	public static int getTestTotal() {
		return dTestTotal;
	}

	/**
	 *  设置正常场景用例
	 *  
	 *  @param curTestCases 形式参数表
	 */
	public static boolean addPassedCases(Object[][] curTestCases){
		GSys.logSys("ADD PASSED TEST CASES");
		try {
			if(objPASSED != null) {
				Object[][] lastPassedCases = null;
				lastPassedCases = (Object[][])objPASSED.clone();
				objPASSED = null;
				objPASSED = new Object[lastPassedCases.length + curTestCases.length][1];
				System.arraycopy(lastPassedCases, 0, objPASSED, 0, lastPassedCases.length);
				System.arraycopy(curTestCases, 0, objPASSED, lastPassedCases.length, curTestCases.length);
			}else {
				objPASSED = (Object[][])curTestCases.clone();
			}
			if(objPASSED != null)setTestPassedNum(objPASSED.length);
		}catch(Exception e) {
			GSys.logErrorSys(GExp.getExceptionAllinformation(e));
			e.printStackTrace();
			return false;
		}
		GSys.logSys("PASSED TEST CASES READY");
		return true;
	}
	
	/**
	 *  重置错误码用例集合
	 */
	public static void resetPassedCases() {
		objPASSED = null;
	}
	
	/**
	 *  获取正常场景用例集合
	 */
	public static Object[][] getPassedCases() {
		return objPASSED;
	}
	
	/**
	 *  设置错误码场景用例
	 *  
	 *  @param curTestCases 形式参数表
	 */
	public static boolean addErrorCases(Object[][] curTestCases){
		GSys.logSys("ADD ERROR TEST CASES");
		try {
			if(objERROR != null) {
				Object[][] lastPassedCases = null;
				lastPassedCases = (Object[][])objERROR.clone();
				objERROR = null;
				objERROR = new Object[lastPassedCases.length + curTestCases.length][1];
				System.arraycopy(lastPassedCases, 0, objERROR, 0, lastPassedCases.length);
				System.arraycopy(curTestCases, 0, objERROR, lastPassedCases.length, curTestCases.length);
			}else {
				objERROR = (Object[][])curTestCases.clone();
			}
			if(objERROR != null)setTestErrorNum(objERROR.length);
		}catch(Exception e) {
			GSys.logErrorSys(GExp.getExceptionAllinformation(e));
			e.printStackTrace();
			return false;
		}
		GSys.logSys("ERROR TEST CASES READY");
		return true;
	}
	
	/**
	 *  重置错误码用例集合
	 */
	public static void resetErrorCases() {
		objERROR = null;
	}
	
	/**
	 *  获取错误码场景用例集合
	 */
	public static Object[][] getErrorCases() {
		return objERROR;
	}
	
	/**
	 *  获取用例总数
	 *  
	 *  @return 加载完成则返回true，否则返回false
	 */
	public static boolean loadTestCases(){
		GSys.logSys("START LOADING TEST CASES");
		try {
			if(objPASSED != null) {
				if(objERROR != null){
					objTESTCASES = new Object[objPASSED.length + objERROR.length][1];
					System.arraycopy(objPASSED, 0, objTESTCASES, 0, objPASSED.length);
					System.arraycopy(objERROR, 0, objTESTCASES, objPASSED.length, objERROR.length);
				}else {
					objTESTCASES = (Object[][])objPASSED.clone();
				}
			}else{
				if(objERROR != null) {
					objTESTCASES = (Object[][])objERROR.clone();
				}
			}
			if(objTESTCASES != null) {
				setTestTotal(objTESTCASES.length);
			}
			
		}catch(Exception e) {
			GSys.logErrorSys(GExp.getExceptionAllinformation(e));
			e.printStackTrace();
			return false;
		}
		GSys.logSys("TEST CASES READY");
		return true;
	}
	
	/**
	 *  重置所有用例集合
	 */
	public static void resetTestCases() {
		objTESTCASES = null;
	}
	
	/**
	 *  获取所有用例集合
	 *  
	 *  @return 返回已加载的参数表
	 */
	public static Object[][] getTestCases() {
		return objTESTCASES;
	}
	
	/**
	 *  获取所有用例集合的String类型结果
	 *  
	 *  @return 返回已加载的参数表的String[][]类型值
	 */
	public static String[][] getTestCasesToString() {
		
		String[][] strTESTCASES = null;
		if(objTESTCASES != null && objTESTCASES.length > 0 && objTESTCASES[0].length > 0) {
			strTESTCASES = new String[objTESTCASES.length][objTESTCASES[0].length];
			for (int i = 0; i < objTESTCASES.length; i++) {
				for (int j = 0; j < objTESTCASES[0].length; j++) {
					if(objTESTCASES[i][j] != null)
						strTESTCASES[i][j] = objTESTCASES[i][j].toString();
				}
			}
		}
		
		return strTESTCASES;
	}
	
	/**
	 *  获取当前集合参数表行数
	 *  
	 *  @return 返回已加载的参数表行数
	 */
	public static int getInputTxtRowCourt() {
		//准备有效场景
		addPassedCases(new Object[][]{/*23*/
			{"测试环境类型", "用例类型", "1001"},
			{0, 0, 1001},
		});
		//准备异常场景
		addErrorCases(new Object[][]{/*74*/
			{0, 1, 100101},{0, 2, 100102},{0, 3, 100103},
		});
		//加载上述场景
		loadTestCases();
		
		return dTestTotal;
	}
	
	/**
	 *  加载参数到内存
	 *  
	 *  @return 加载完成返回true，佛祖额返回false
	 */
	public static boolean importObjectInputs() {
		
		if(objTESTCASES != null) {
			for (int i = 0; i < dTestTotal; i++) {
				for (int j = 0; j < objTESTCASES[i].length; j++) {
					if(objTESTCASES[i][j] != null)
						GParam.strTestCaseInputArray[i][j] = objTESTCASES[i][j].toString();
				}
			}
			
			return true;
		}
		
		return false;

	}
}
