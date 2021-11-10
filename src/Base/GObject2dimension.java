package Base;

import DT.GLog;
import Sys.GSys;

/**
 *  对象操作
 */
public class GObject2dimension {

    /**
     *  构造函数
     */
	private GObject2dimension(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  用例输入-集合
	 */
	private static Object[][] inputList = null;

	/**
	 *  用例输入-集合，用于“仅校验不测试”场景
	 */
	private static Object[][] checkList = null;
	
	/**
	 *  用例输入-个数
	 */
	private static int totalNum = 0;
	
	
	public static Object[][] getInputList() {
		return inputList;
	}

	public static void setInputList(Object[][] inputList) {
		GObject2dimension.inputList = inputList;
	}

	public static Object[][] getCheckList() {
		return checkList;
	}

	public static void setCheckList(Object[][] checkList) {
		GObject2dimension.checkList = checkList;
	}

	public static int getTotalNum() {
		return totalNum;
	}

	public static void setTotalNum(int totalNum) {
		GObject2dimension.totalNum = totalNum;
	}
	
	/**
	 *  获取用例总数
	 *  
	 *  @return 加载完成则返回true，否则返回false
	 */
	public static boolean addObject2d(Object[][] object2d){
		GSys.logSys(GMissionMsg.getStepTop("START LOADING OBJECT 2D TEST CASES"));
		GSys.logSys(GSys.getDate() + " LOAD OBJECTS");
		
		Object[][] lastObject2d = null;
		boolean bAdd = false;
		
		try {
			if(inputList != null) {
				lastObject2d = (Object[][])inputList.clone();
				inputList = null;
				inputList = new Object[lastObject2d.length + object2d.length][1];
				System.arraycopy(lastObject2d, 0, inputList, 0, lastObject2d.length);
				System.arraycopy(object2d, 0, inputList, lastObject2d.length, object2d.length);
			}else {
				inputList = (Object[][])object2d.clone();
			}
			
			if(inputList != null) {
				totalNum = inputList.length;
			}
			
			bAdd = true;
		}catch(Exception e) {
			GLog.logSysFunctionException("addObject2d", e);
			bAdd = false;
		}
		
		GSys.logSys(GMissionMsg.getStepBottom("OBJECT 2D TEST CASES READY"));
		return bAdd;
	}
	
	/**
	 *  设置“仅校验不测试”用例集
	 */
	public static void addCheckObject2d(Object[][] object2d){
		GSys.logSys(GMissionMsg.getStepTop("START LOADING CHECK OBJECT 2D TEST CASES"));
		GSys.logSys(GSys.getDate() + " LOAD CHECK OBJECTS");
		
		if(object2d != null){
			checkList = (Object[][])object2d.clone();
		}
		GSys.logSys(GMissionMsg.getStepBottom("CHECK OBJECT 2D TEST CASES READY"));
	}
}
