package AutoTest;

/**
 * 断言组件
 */
public class GAssert {
	
	/**
	 *  真值断言
	 *  
	 *  @param TestResult 测试值
	 *  @param value 标准值
	 *  @return 成功返回true，否则返回false
	 */
	public static boolean assertTrue(boolean TestResult,boolean value) {
		boolean AssertTestResult = false;
		
		boolean result = false;
		if(TestResult == true)result = TestResult;
		if(result == value)AssertTestResult = true;
		
		return AssertTestResult;
	}
	
	/**
	 *  字符串值全等断言
	 *  
	 *  @param TestResult 测试值
	 *  @param value 标准值
	 *  @return 成功返回true，否则返回false
	 */
	public static boolean assertStringEqual(String TestResult,String value) {
		boolean AssertTestResult = false;
		
		String result = "";
		if(TestResult != null)result = TestResult;
		if(result.equals(value))AssertTestResult = true;
		
		return AssertTestResult;
	}
	
	/**
	 *  整型值全等断言
	 *  
	 *  @param TestResult 测试值
	 *  @param value 标准值
	 *  @return 成功返回true，否则返回false
	 */
	public static boolean assertIntegerEqual(Integer TestResult,Integer value) {
		boolean AssertTestResult = false;
		
		Integer result = 0;
		if(TestResult != null)result = TestResult;
		if(result.equals(value))AssertTestResult = true;
		
		return AssertTestResult;
	}
	
	/**
	 *  字符串值包含断言
	 *  
	 *  @param TestResult 测试值
	 *  @param value 取值范围
	 *  @return 成功返回true，否则返回false
	 */
	public static boolean assertStringIsContain(String TestResult, String value) {
		boolean AssertTestResult = false;
		int AssertIndex = -1;
		
		int result = -1;
		String test = TestResult;       
		if (test.indexOf(value)!=-1){
			result = test.indexOf(value);
		}else{             
			System.out.println(GMsg.MSG_NOTFOUND[0]);
		}
		
		if(result != -1 ) {
			AssertTestResult = true;
			AssertIndex = result;
			System.out.println(GMsg.MSG_FOUND[1] + " " + String.valueOf(AssertIndex+1) + " " + GMsg.MSG_MEASUREMENT[1]);
		}else {
			System.out.println(GMsg.MSG_NOTFOUND[0]);
		}
		
		return AssertTestResult;
	}
	
	/**
	 *  字符串值包含断言
	 *  
	 *  @param TestResult 测试值
	 *  @param value 取值范围
	 *  @return 创建成功返回true，否则返回false
	 */
	public static int assertStringContainIndex(String TestResult, String value) {
		int AssertIndex = -1;
		
		int result = -1;
		String test = TestResult;       
		if (test.indexOf(value)!=-1){
			result = test.indexOf(value);
		}else{             
			System.out.println(GMsg.MSG_NOTFOUND[0]);
		}
		
		if(result != -1 ) {
			AssertIndex = result;
			System.out.println(GMsg.MSG_FOUND[1] + " " + String.valueOf(AssertIndex+1) + " " + GMsg.MSG_MEASUREMENT[1]);
		}else {
			System.out.println(GMsg.MSG_NOTFOUND[0]);
		}
		
		return AssertIndex;
	}
	
	/**
	 *  字符串值包含断言-二维数组
	 *  
	 *  @param TestResult 测试值
	 *  @param Array 取值范围
	 *  @return 创建成功返回true，否则返回false
	 */
	public static boolean assertStringConsistInDArray(String TestResult, String[][] Array) {
		boolean AssertTestResult = false;
		int AssertIndex = -1;
		int result = -1;
		for(int i=0;i<Array.length;i++) {
			for(int j=0;j<Array[i].length;j++) {
				if (Array[i][j] != null){
					if (Array[i][j].equals(TestResult)){
						result++;
					}
				}
			}
		}
		
		if(result != 0) {
			AssertTestResult = true;
			AssertIndex = result;
			System.out.println(GMsg.MSG_FOUND[1] + " " + String.valueOf(AssertIndex+1) + " " + GMsg.MSG_MEASUREMENT[1]);
		}else {
			System.out.println(GMsg.MSG_NOTFOUND[0]);
		}
		
		return AssertTestResult;
	}
	
	/**
	 *  字符串值包含断言-一维数组
	 *  
	 *  @param TestResult 测试值
	 *  @param Array 取值范围
	 *  @return 创建成功返回true，否则返回false
	 */
	public static boolean assertStringConsistInOArray(String TestResult, String[] Array) {
		boolean AssertTestResult = false;
		int AssertIndex = -1;
		int result = 0;
		for(int i=0;i<Array.length;i++) {
			if (Array[i] != null){
				if (Array[i].equals(TestResult)){
					result++;
				}
			}
		}
		
		if(result != 0) {
			AssertTestResult = true;
			AssertIndex = result;
			System.out.println(GMsg.MSG_FOUND[1] + " " + String.valueOf(AssertIndex+1) + " " + GMsg.MSG_MEASUREMENT[1]);
		}else {
			System.out.println(GMsg.MSG_NOTFOUND[0]);
		}
		
		return AssertTestResult;
	}
}
