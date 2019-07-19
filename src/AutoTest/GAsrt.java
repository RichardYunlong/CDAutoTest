package AutoTest;

/**
 * 断言组件
 */
public class GAsrt {
	
	private GAsrt(){
		System.out.println("This is a tool class.");
	}
	
	/**
	 *  真值断言
	 *  
	 *  @param TestResult 测试值
	 *  @param value 标准值
	 *  @return 成功返回true，否则返回false
	 */
	public static boolean assertTrue(boolean bTestResult,boolean value) {
		boolean assertTestResult = false;
		
		boolean result = false;
		if(bTestResult == true)result = bTestResult;
		if(result == value)assertTestResult = true;
		
		return assertTestResult;
	}
	
	/**
	 *  字符串值全等断言
	 *  
	 *  @param TestResult 测试值
	 *  @param value 标准值
	 *  @return 成功返回true，否则返回false
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
	 *  @param TestResult 测试值
	 *  @param value 标准值
	 *  @return 成功返回true，否则返回false
	 */
	public static boolean assertIntegerEqual(Integer strTestResult,Integer value) {
		boolean assertTestResult = false;
		
		Integer result = 0;
		if(strTestResult != null)result = strTestResult;
		if(result.equals(value))assertTestResult = true;
		
		return assertTestResult;
	}
	
	/**
	 *  字符串值包含断言
	 *  
	 *  @param TestResult 测试值
	 *  @param value 取值范围
	 *  @return 成功返回true，否则返回false
	 */
	public static boolean assertStringIsContain(String strTestResult, String value) {
		boolean assertTestResult = false;
		int assertIndex = -1;
		
		int result = -1;
		String test = strTestResult;       
		if (test.indexOf(value)!=-1){
			result = test.indexOf(value);
		}else{             
			System.out.println(GMsg.MSG_NOTFOUND[0]);
		}
		
		if(result != -1 ) {
			assertTestResult = true;
			assertIndex = result;
			System.out.println(GMsg.MSG_FOUND[1] + " " + String.valueOf(assertIndex+1) + " " + GMsg.MSG_MEASUREMENT[1]);
		}else {
			System.out.println(GMsg.MSG_NOTFOUND[0]);
		}
		
		return assertTestResult;
	}
	
	/**
	 *  字符串值包含断言
	 *  
	 *  @param TestResult 测试值
	 *  @param value 取值范围
	 *  @return 创建成功返回true，否则返回false
	 */
	public static int assertStringContainIndex(String strTestResult, String value) {
		int assertIndex = -1;
		
		int result = -1;
		String test = strTestResult;       
		if (test.indexOf(value)!=-1){
			result = test.indexOf(value);
		}else{             
			System.out.println(GMsg.MSG_NOTFOUND[0]);
		}
		
		if(result != -1 ) {
			assertIndex = result;
			System.out.println(GMsg.MSG_FOUND[1] + " " + String.valueOf(assertIndex+1) + " " + GMsg.MSG_MEASUREMENT[1]);
		}else {
			System.out.println(GMsg.MSG_NOTFOUND[0]);
		}
		
		return assertIndex;
	}
	
	/**
	 *  字符串值包含断言-二维数组
	 *  
	 *  @param TestResult 测试值
	 *  @param Array 取值范围
	 *  @return 创建成功返回true，否则返回false
	 */
	public static boolean assertStringConsistInDArray(String strTestResult, String[][] Array) {
		boolean assertTestResult = false;
		int assertIndex = -1;
		int result = -1;
		for(int i=0;i<Array.length;i++) {
			for(int j=0;j<Array[i].length;j++) {
				if( (Array[i][j] != null) && Array[i][j].equals(strTestResult) ){
						result++;
				}
			}
		}
		
		if(result != 0) {
			assertTestResult = true;
			assertIndex = result;
			System.out.println(GMsg.MSG_FOUND[1] + " " + String.valueOf(assertIndex+1) + " " + GMsg.MSG_MEASUREMENT[1]);
		}else {
			System.out.println(GMsg.MSG_NOTFOUND[0]);
		}
		
		return assertTestResult;
	}
	
	/**
	 *  字符串值包含断言-一维数组
	 *  
	 *  @param TestResult 测试值
	 *  @param Array 取值范围
	 *  @return 创建成功返回true，否则返回false
	 */
	public static boolean assertStringConsistInOArray(String strTestResult, String[] Array) {
		boolean assertTestResult = false;
		int assertIndex = -1;
		int result = 0;
		for(int i=0;i<Array.length;i++) {
			if (Array[i] != null && Array[i].equals(strTestResult)){
					result++;
			}
		}
		
		if(result != 0) {
			assertTestResult = true;
			assertIndex = result;
			System.out.println(GMsg.MSG_FOUND[1] + " " + String.valueOf(assertIndex+1) + " " + GMsg.MSG_MEASUREMENT[1]);
		}else {
			System.out.println(GMsg.MSG_NOTFOUND[0]);
		}
		
		return assertTestResult;
	}
}
