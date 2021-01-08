package AutoTest;

/**
 *  用例输入-集合类
 *  
 *  集合类用例输入由“预计通过的”和“预计不通过的”两大类组成
 *  Object类用例输入只能内置在此文件中
 */
public class GObjectImport {
	
	/**
	 * 
	 */
	private GObjectImport(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  预计通过的
	 */
	private static Object[][] inputList_Passed = null;
	
	/**
	 *  预计不通过的
	 */
	private static Object[][] inputList_Error = null;
	
	/**
	 *  用例输入-集合类
	 */
	private static Object[][] inputList = null;
	
	/**
	 *  用例总个数
	 */
	private static int totalNum = 0;
	
	/**
	 *  预计通过的个数
	 */
	private static int passedNum = 0;
	
	/**
	 *  预计不通过的个数
	 */
	private static int errorNum = 0;

	/**
	 *  设置正常场景用例
	 *  
	 *  @param passed 预计通过的用例输入枚举对象
	 *  
	 *  @return 添加是否成功
	 */
	public static boolean addPassed(Object[][] passed){
		GSys.logSys("ADD PASSED TEST CASES");
		
		Object[][] lastPassedCases = null;
		boolean bAdd = false;
		
		try {
			if(inputList_Passed != null) {
				lastPassedCases = (Object[][])inputList_Passed.clone();
				inputList_Passed = null;
				inputList_Passed = new Object[lastPassedCases.length + passed.length][1];
				System.arraycopy(lastPassedCases, 0, inputList_Passed, 0, lastPassedCases.length);
				System.arraycopy(passed, 0, inputList_Passed, lastPassedCases.length, passed.length);
			}else {
				inputList_Passed = (Object[][])passed.clone();
			}
			
			if(inputList_Passed != null) {
				passedNum = inputList_Passed.length;
			}
			
			bAdd = true;
		}catch(Exception e) {
			GSys.logErrorSys(GException.getExceptionAllinformation(e));
			e.printStackTrace();
			bAdd = false;
		}
		
		GSys.logSys("PASSED TEST CASES READY");
		return bAdd;
	}
	
	/**
	 *  设置错误码场景用例
	 *  
	 *  @param error 形式参数表
	 *  
	 *  @return 添加是否成功
	 */
	public static boolean addError(Object[][] error){
		GSys.logSys("ADD ERROR TEST CASES");
		
		Object[][] lastPassedCases = null;
		boolean bAdd = false;
		try {
			if(inputList_Error != null) {
				
				lastPassedCases = (Object[][])inputList_Error.clone();
				inputList_Error = null;
				inputList_Error = new Object[lastPassedCases.length + error.length][1];
				System.arraycopy(lastPassedCases, 0, inputList_Error, 0, lastPassedCases.length);
				System.arraycopy(error, 0, inputList_Error, lastPassedCases.length, error.length);
			}else {
				inputList_Error = (Object[][])error.clone();
			}
			
			if(inputList_Error != null) {
				errorNum = inputList_Error.length;
			}
			
			bAdd = true;
		}catch(Exception e) {
			GSys.logErrorSys(GException.getExceptionAllinformation(e));
			e.printStackTrace();
			bAdd = false;
		}
		
		GSys.logSys("ERROR TEST CASES READY");		
		return bAdd;
	}
	
	/**
	 *  获取用例总数
	 *  
	 *  @return 加载完成则返回true，否则返回false
	 */
	public static boolean load(){
		GSys.logSys("START LOADING TEST CASES");
		
		boolean bLoaded = false;
		try {
			if(inputList_Passed != null) {
				if(inputList_Error != null){
					inputList = new Object[inputList_Passed.length + inputList_Error.length][1];
					System.arraycopy(inputList_Passed, 0, inputList, 0, inputList_Passed.length);
					System.arraycopy(inputList_Error, 0, inputList, inputList_Passed.length, inputList_Error.length);
				}else {
					inputList = (Object[][])inputList_Passed.clone();
				}
			}else{
				if(inputList_Error != null) {
					inputList = (Object[][])inputList_Error.clone();
				}
			}
			
			if(inputList != null) {
				totalNum = inputList.length;
				GProgress.setTCTotalNum(totalNum);
			}

			bLoaded = true;
		}catch(Exception e) {
			GSys.logErrorSys(GException.getExceptionAllinformation(e));
			e.printStackTrace();
			bLoaded = false;
		}
		
		if(totalNum == passedNum + errorNum) {
			GSys.logSys("OBJ TEST CASES READY");
		}
		
		return bLoaded;
	}
		
	/**
	 *  获取所有用例集合的Object类型结果
	 *  
	 *  @return 返回已加载的参数表的String[][]类型值
	 */
	public static Object[][] get() {
		return inputList;
	}
	
	/**
	 *  获取所有用例集合的String类型结果
	 *  
	 *  @return 返回已加载的参数表的String[][]类型值
	 */
	public static String[][] getString() {
		String[][] inputList_Temp = null;
		
		if(inputList != null && inputList.length > 0 && inputList[0].length > 0) {
			inputList_Temp = new String[inputList.length][inputList[0].length];
			for (int i = 0; i < inputList.length; i++) {
				for (int j = 0; j < inputList[0].length; j++) {
					if(inputList[i][j] != null)
						inputList_Temp[i][j] = inputList[i][j].toString();
				}
			}
		}
		
		return inputList_Temp;
	}
	
	/**
	 *  获取当前集合参数表行数
	 *  
	 *  @return 返回已加载的参数表行数
	 */
	public static int getRowCourt() {
		//准备有效场景
		addPassed(new Object[][]{//第一行为表头，必须添加
			{"测试环境类型", "用例类型", "1001"},
			{0, 0, 1001},
		});
		//准备异常场景
		addError(new Object[][]{
			{0, 1, 100101},
			{0, 2, 100102},
			{0, 3, 100103},
		});
		//加载上述场景
		load();
		
		return totalNum;
	}
	
	/**
	 *  输出用例输入缓存
	 */
	public static void doLogXls() {
		int index = 0;
		for (int i = 0; i < GProgress.getTCTotalNum(); i++) {
			for (int j = 0; j < GValue.PARAM_NUM_MAX; j++) {
				GFile.writeStringToRight(GLog.strLogStyle[4], GTCNO.TCNO_STR[i][j] + "  ");
				index++;
			}
			GLog.logShowConsole("INIT TESTCASE:" + Integer.toString(i) + " TOTAL:" + Integer.toString(index) + "/" + GTCNO.TCNO_STR.length);
		}
	}
	
	/**
	 *  加载参数到内存
	 *  
	 *  @return 加载完成返回true，否则返回false
	 */
	public static boolean doImportObjs() {
		if(inputList != null) {
			for (int i = 0; i < totalNum; i++) {
				for (int j = 0; j < inputList[i].length; j++) {
					if(inputList[i][j] != null)
						GTCNO.TCNO_STR[i][j] = inputList[i][j].toString();
				}
			}
			return true;
		}
		return false;
	}
}
