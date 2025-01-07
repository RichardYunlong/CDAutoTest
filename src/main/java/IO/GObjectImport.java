package main.java.IO;

import main.java.Base.GClazz;
import main.java.Base.GFile;
import main.java.Base.GObject2dimension;
import main.java.Base.GValue;
import main.java.DT.GLog;
import main.java.Sys.GStatic;
import main.java.Test.GTestMission;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

/**
 *  用例输入-集合类
 *  集合类用例输入由“预计通过的”和“预计不通过的”两大类组成
 *  Object类用例输入只能内置在此文件中
 */
public class GObjectImport {
	
	/**
	 * 
	 */
	public GObjectImport(){
		GClazz.thisADataUnitClass();
	}
	
	/**
	 *  预计通过的
	 */
	private Object[][] inputList_Passed = null;
	
	/**
	 *  预计不通过的
	 */
	private Object[][] inputList_Error = null;
	
	/**
	 *  用例输入-集合类
	 */
	private Object[][] inputList = null;
	
	/**
	 *  用例总个数
	 */
	private int totalNum = 0;
	
	/**
	 *  预计通过的个数
	 */
	private int passedNum = 0;
	
	/**
	 *  预计不通过的个数
	 */
	private int errorNum = 0;

	/**
	 *  设置正常场景用例
	 *  
	 *  @param passed 预计通过的用例输入枚举对象
	 *  
	 *  @return 添加是否成功
	 */
	@SuppressWarnings("UnusedReturnValue")
    public boolean addPassed(Object[][] passed){
		GFile.writeStringToGuideBottom("ADD PASSED TEST CASES");
		
		Object[][] lastPassedCases = null;
		boolean bAdd = false;
		
		try {
			if(this.inputList_Passed != null) {
				lastPassedCases = this.inputList_Passed.clone();
				this.inputList_Passed = null;
				this.inputList_Passed = new Object[lastPassedCases.length + passed.length][1];
				System.arraycopy(lastPassedCases, 0, this.inputList_Passed, 0, lastPassedCases.length);
				System.arraycopy(passed, 0, this.inputList_Passed, lastPassedCases.length, passed.length);
			}else {
				this.inputList_Passed = passed.clone();
			}
			
			if(this.inputList_Passed != null) {
				this.passedNum = this.inputList_Passed.length;
			}
			
			bAdd = true;
		}catch(Exception e) {
			GFile.writeStringErrorToGuideBottom("addPassed[" + Arrays.toString(e.getStackTrace()) +"]");
        }
		
		GFile.writeStringToGuideBottom("PASSED TEST CASES READY");
		return bAdd;
	}
	
	/**
	 *  设置错误码场景用例
	 *  
	 *  @param error 形式参数表
	 *  
	 *  @return 添加是否成功
	 */
	@SuppressWarnings("UnusedReturnValue")
    public boolean addError(Object[][] error){
		GFile.writeStringToGuideBottom("ADD ERROR TEST CASES");
		
		Object[][] lastPassedCases = null;
		boolean bAdd = false;
		try {
			if(this.inputList_Error != null) {
				
				lastPassedCases = this.inputList_Error.clone();
				this.inputList_Error = null;
				this.inputList_Error = new Object[lastPassedCases.length + error.length][1];
				System.arraycopy(lastPassedCases, 0, this.inputList_Error, 0, lastPassedCases.length);
				System.arraycopy(error, 0, this.inputList_Error, lastPassedCases.length, error.length);
			}else {
				this.inputList_Error = error.clone();
			}
			
			if(this.inputList_Error != null) {
				this.errorNum = this.inputList_Error.length;
			}
			
			bAdd = true;
		}catch(Exception e) {
			GFile.writeStringErrorToGuideBottom("addError[" + Arrays.toString(e.getStackTrace()) +"]");
        }
		
		GFile.writeStringToGuideBottom("ERROR TEST CASES READY");
		return bAdd;
	}
	
	/**
	 *  获取用例总数
	 *  
	 *  @return 加载完成则返回true，否则返回false
	 */
	@SuppressWarnings("UnusedReturnValue")
    public boolean load(){
		GFile.writeStringToGuideBottom("START LOADING TEST CASES");
		
		boolean bLoaded = false;
		try {
			if(this.inputList_Passed != null) {
				if(this.inputList_Error != null){
					this.inputList = new Object[this.inputList_Passed.length + this.inputList_Error.length][1];
					System.arraycopy(this.inputList_Passed, 0, this.inputList, 0, this.inputList_Passed.length);
					System.arraycopy(this.inputList_Error, 0, this.inputList, this.inputList_Passed.length, this.inputList_Error.length);
				}else {
					this.inputList = this.inputList_Passed.clone();
				}
			}else{
				if(this.inputList_Error != null) {
					this.inputList = this.inputList_Error.clone();
				}
			}
			
			if(this.inputList != null) {
				this.totalNum = this.inputList.length;
				GTestMission.gTestProgress.setTotalNum(this.totalNum);
			}

			bLoaded = true;
		}catch(Exception e) {
			GFile.writeStringErrorToGuideBottom("load[" + Arrays.toString(e.getStackTrace()) +"]");
        }
		
		if(this.totalNum == this.passedNum + this.errorNum) {
			GFile.writeStringToGuideBottom("OBJ TEST CASES READY");
		}
		
		return bLoaded;
	}
		
	/**
	 *  获取所有用例集合的Object类型结果
	 *  
	 *  @return 返回已加载的参数表的String[][]类型值
	 */
	public Object[][] get() {
		return this.inputList;
	}
	
	/**
	 *  获取所有用例集合的String类型结果
	 *  
	 *  @return 返回已加载的参数表的String[][]类型值
	 */
	public String[][] getString() {
		String[][] inputList_Temp = null;
		
		if(this.inputList != null && this.inputList.length > 0 && this.inputList[0].length > 0) {
			inputList_Temp = new String[this.inputList.length][this.inputList[0].length];
			for (int i = 0; i < this.inputList.length; i++) {
				for (int j = 0; j < this.inputList[0].length; j++) {
					if(this.inputList[i][j] != null)
						inputList_Temp[i][j] = this.inputList[i][j].toString();
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
	public int getRowCourt() {
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
		
		return this.totalNum;
	}
	
	/**
	 *  输出用例输入缓存
	 */
	public void doLogXls() {
		int index = 0;
		String[] logPath = GLog.getLogpath().clone();
		String[][] objs = GTestMission.gtcno.getTCNO_STR().clone();
		for (int i = 0; i < GTestMission.gTestProgress.getTotalNum(); i++) {
			for (int j = 0; j < GValue.PARAM_NUM_MAX; j++) {
				GFile.writeStringToRight(logPath[4], objs[i][j] + "  ");
				index++;
			}
			GLog.logShowConsole("INIT TESTCASE:" + Integer.valueOf(i).toString() + " TOTAL:" + index + "/" + objs.length);
		}
	}
	
	/**
	 *  加载参数到内存
	 *  
	 *  @return 加载完成返回true，否则返回false
	 */
	public boolean doImportObjs() {
		String[][] objs = GTestMission.gtcno.getTCNO_STR().clone();
		if(this.inputList != null) {
			for (int i = 0; i < this.totalNum; i++) {
				for (int j = 0; j < this.inputList[i].length; j++) {
					if(this.inputList[i][j] != null)
						objs[i][j] = this.inputList[i][j].toString();
				}
			}
			GTestMission.gtcno.setTCNO_STR(objs);
			return true;
		}
		return false;
	}
	
	/**
	 *  通过“反射”方式加载参数类到内存
	 *
	 * @param packagePath 代码包位置
	 * @param tpRange 类名称
	 * @param funcName 方法名称
	 *
	 * @return Collection 类的内容
	 */
	@SuppressWarnings("rawtypes")
	public Collection doImportObjsByReflex(String packagePath, String tpRange, String funcName) {
		try {
			if((tpRange != null) && (!tpRange.isEmpty())) {

	        	String mark = "\\|\\|";
	    		String[] strTpRanges= tpRange.split(mark);
	    		if(strTpRanges.length <= 1) {//如果只找到了一个执行目标
					Class tarClass = Class.forName(packagePath + "." + tpRange);
					Object obj= tarClass.newInstance();
					Field field;
					Object fieldValue = null;
					field = obj.getClass().getDeclaredField(funcName);
					fieldValue = field.get(obj);
	    			GObject2dimension.addObject2d((Object[][])fieldValue);
	    		}else {
                    for (String strTpRange : strTpRanges) {
                        Class tarClass = Class.forName(packagePath + "." + strTpRange);
                        Object obj = tarClass.newInstance();
                        Field field;
                        Object fieldValue = null;
                        field = obj.getClass().getDeclaredField(funcName);
                        fieldValue = field.get(obj);
                        GObject2dimension.addObject2d((Object[][]) fieldValue);
                    }
	    		}	
			}
			
		} catch (Exception e) {
			GFile.writeStringErrorToGuideBottom("doImportObjsByReflex[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		GTestMission.gTestProgress.setTotalNum(GObject2dimension.getTotalNum());
		GTestMission.gTestProject.setTP_RANGE(tpRange);
		if(GStatic.gTestMission.isTM_CHECK_ONLY()) {
		    return Arrays.asList(GObject2dimension.getCheckList());
		}else {
		    return Arrays.asList(GObject2dimension.getInputList());
		}
	}

	@SuppressWarnings("ConfusingMainMethod")
    public void main(String[] agrs) {
		String str = "123||456";
    	String mark = "\\|\\|";
		String[] strTpRanges= str.split(mark);
		
		System.out.println("分割出了" + strTpRanges.length + "个快");
        for (String strTpRange : strTpRanges) {
            System.out.println(strTpRange);
        }
	}
}
