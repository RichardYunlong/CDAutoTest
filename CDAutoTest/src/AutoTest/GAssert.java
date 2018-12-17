package AutoTest;

public class GAssert {
	
	/**
	 * @see 断言结果
	 */
	public static boolean AssertTestResult = false;
	
	/**
	 * @see 断言标记值，在进行查询类断言时，用于标记查询到的目标在查询范围中：
	 * 对于查询范围是字符串的，记录第一次出现的位置
	 * 对于查询范围是字符串数组的的，记录最后一次出现的位置
	 */
	public static int AssertIndex = -1;
	
	/**
	 * @see 真值断言
	 */
	public static boolean AssertTrue(boolean TestResult,boolean value) {
		boolean result = false;
		if(TestResult == true || TestResult == false)result = TestResult;
		
		if(result == value)AssertTestResult = true;
		
		return AssertTestResult;
	}
	
	/**
	 * @see 字符串值全等断言
	 */
	public static boolean AssertStringEqual(String TestResult,String value) {
		String result = "";
		if(TestResult != null || TestResult == "")result = TestResult;
		if(result == value)AssertTestResult = true;
		
		return AssertTestResult;
	}
	
	/**
	 * @see 整型值全等断言
	 */
	public static boolean AssertIntegerEqual(Integer TestResult,Integer value) {
		Integer result = 0;
		if(TestResult != null)result = TestResult;
		if(result == value)AssertTestResult = true;
		
		return AssertTestResult;
	}
	
	/**
	 * @see 字符串值包含断言
	 */
	public static boolean AssertStringContain(String TestResult, String value) {
		int result = -1;
		String test = TestResult;       
		if (test.indexOf(value)!=-1){
			result = test.indexOf(value);
		}else{             
			System.out.println("NON VALUE FOUND");
		}
		
		if(result != -1 ) {
			AssertTestResult = true;
			AssertIndex = result;
			System.out.println("QUALIFIED VALUES ARE FOUND "+ String.valueOf(result+1) + " TIMES");
		}else {
			System.out.println("NON VALUE FOUND");
		}
		
		return AssertTestResult;
	}
	
	/**
	 * @see 字符串值包含断言-二维数组
	 */
	public static boolean AssertStringConsistInDArray(String TestResult, String[][] Array) {
		int result = -1;
		for(int i=0;i<Array.length;i++) {
			for(int j=0;j<Array[i].length;j++) {
				if (Array[i][j] != null){
					if (Array[i][j] == TestResult){
						result++;
					}
				}
			}
		}
		
		if(result != 0) {
			AssertTestResult = true;
			AssertIndex = result;
			System.out.println("QUALIFIED VALUES ARE FOUND "+ String.valueOf(result+1) + " TIMES");
		}else {
			System.out.println("NON VALUE FOUND");
		}
		
		return AssertTestResult;
	}
	
	/**
	 * @see 字符串值包含断言-一维数组
	 */
	public static boolean AssertStringConsistInOArray(String TestResult, String[] Array) {
		int result = 0;
		for(int i=0;i<Array.length;i++) {
			if (Array[i] != null){
				if (Array[i] == TestResult){
					result++;
				}
			}
		}
		
		if(result != 0) {
			AssertTestResult = true;
			AssertIndex = result;
			System.out.println("QUALIFIED VALUES ARE FOUND "+ String.valueOf(result+1) + " TIMES");
		}else {
			System.out.println("NON VALUE FOUND");
		}
		
		return AssertTestResult;
	}
}