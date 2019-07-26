package AutoTest;

/**
 * 断言组件
 */
public class GAsrt {
	
	private GAsrt(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  真值断言
	 *  
	 *  @param bTestResult 测试值
	 *  @param value 标准值
	 *  @return 比对一致返回true，否则返回false
	 */
	public static boolean assertTrue(boolean bTestResult,boolean value) {
		boolean assertTestResult = false;
		
		boolean result = false;
		if(bTestResult)result = bTestResult;
		if(result == value)assertTestResult = true;
		
		return assertTestResult;
	}
	
	/**
	 *  字符串值全等断言
	 *  
	 *  @param strTestResult 测试值
	 *  @param value 标准值
	 *  @return 比对一致返回true，否则返回false
	 */
	public static boolean assertStringEqual(String strTestResult,String value) {
		boolean assertTestResult = false;
		
		String result = "";
		if(strTestResult != null)result = strTestResult;
		if(result.equals(value))assertTestResult = true;
		
		return assertTestResult;
	}
	
	/**
	 *  整型值全等断言
	 *  
	 *  @param dTestResult 测试值
	 *  @param value 标准值
	 *  @return 比对一致返回true，否则返回false
	 */
	public static boolean assertIntegerEqual(Integer dTestResult,Integer value) {
		boolean assertTestResult = false;
		
		Integer result = 0;
		if(dTestResult != null)result = dTestResult;
		if(result.equals(value))assertTestResult = true;
		
		return assertTestResult;
	}
	
	/**
	 *  字符串值包含断言
	 *  
	 *  @param strTestResult 测试值
	 *  @param value 取值范围
	 *  @return 比对一致返回true，否则返回false
	 */
	public static boolean assertStringIsContain(String strTestResult, String value) {
		boolean assertTestResult = false;
		int assertIndex = -1;
		
		int result = -1;
		String test = strTestResult;       
		if (test.indexOf(value)!=-1){
			result = test.indexOf(value);
		}else{             
			GLog.logShowConsole(GMsg.MSG_NOTFOUND[0]);
		}
		
		if(result != -1 ) {
			assertTestResult = true;
			assertIndex = result;
			GLog.logShowConsole(GMsg.MSG_FOUND[1] + " " + Integer.toString(assertIndex+1) + " " + GMsg.MSG_MEASUREMENT[1]);
		}else {
			GLog.logShowConsole(GMsg.MSG_NOTFOUND[0]);
		}
		
		return assertTestResult;
	}
	
	/**
	 *  字符串值包含断言
	 *  
	 *  @param strTestResult 测试值
	 *  @param value 取值范围
	 *  @return 比对一致返回true，否则返回false
	 */
	public static int assertStringContainIndex(String strTestResult, String value) {
		int assertIndex = -1;
		
		int result = -1;
		String test = strTestResult;       
		if (test.indexOf(value)!=-1){
			result = test.indexOf(value);
		}else{             
			GLog.logShowConsole(GMsg.MSG_NOTFOUND[0]);
		}
		
		if(result != -1 ) {
			assertIndex = result;
			GLog.logShowConsole(GMsg.MSG_FOUND[1] + " " + Integer.toString(assertIndex+1) + " " + GMsg.MSG_MEASUREMENT[1]);
		}else {
			GLog.logShowConsole(GMsg.MSG_NOTFOUND[0]);
		}
		
		return assertIndex;
	}
	
	/**
	 *  字符串值包含断言-二维数组
	 *  
	 *  @param strTestResult 测试值
	 *  @param aryArray 取值范围
	 *  @return 存在则返回true，否则返回false
	 */
	public static boolean assertStringConsistInDArray(String strTestResult, String[][] aryArray) {
		boolean assertTestResult = false;
		int assertIndex = -1;
		int result = -1;
		for(int i=0;i<aryArray.length;i++) {
			for(int j=0;j<aryArray[i].length;j++) {
				if( (aryArray[i][j] != null) && aryArray[i][j].equals(strTestResult) ){
						result++;
				}
			}
		}
		
		if(result != 0) {
			assertTestResult = true;
			assertIndex = result;
			GLog.logShowConsole(GMsg.MSG_FOUND[1] + " " + Integer.toString(assertIndex+1) + " " + GMsg.MSG_MEASUREMENT[1]);
		}else {
			GLog.logShowConsole(GMsg.MSG_NOTFOUND[0]);
		}
		
		return assertTestResult;
	}
	
	/**
	 *  字符串值包含断言-一维数组
	 *  
	 *  @param strTestResult 测试值
	 *  @param aryArray 取值范围
	 *  @return 存在返回true，否则返回false
	 */
	public static boolean assertStringConsistInOArray(String strTestResult, String[] aryArray) {
		boolean assertTestResult = false;
		int assertIndex = -1;
		int result = 0;
		for(int i=0;i<aryArray.length;i++) {
			if (aryArray[i] != null && aryArray[i].equals(strTestResult)){
					result++;
			}
		}
		
		if(result != 0) {
			assertTestResult = true;
			assertIndex = result;
			GLog.logShowConsole(GMsg.MSG_FOUND[1] + " " + Integer.toString(assertIndex+1) + " " + GMsg.MSG_MEASUREMENT[1]);
		}else {
			GLog.logShowConsole(GMsg.MSG_NOTFOUND[0]);
		}
		
		return assertTestResult;
	}
}
