package Base;

import java.lang.reflect.Method;

import DT.GLog;
import Test.GTestSuite;

public class GClazz {
	
	/**
	 *  构造函数
	 */
	private GClazz(){
		thisAToolClass();
	}
	
	/**
	 *  在控制台输出说明，提示目标是一个工具类
	 */
	public static void thisAToolClass(){
		GLog.logShowConsole("THIS IS A TOOL CLASS");
	}
	
	/**
	 *  根据类名称查找器成员方法并执行
	 *  @param clazzName 类名称
	 *  @param funcName 方法名称
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void findAndDoFuncByClazzAndFuncName(String clazzName, String funcName) {
		try {
			if((clazzName != null) && (!clazzName.equals(""))) {
				Class onwClass = Class.forName(clazzName);
				Object obj= onwClass.newInstance();
				Method constractor = onwClass.getMethod(funcName);  
				constractor.invoke(obj);
			}else {//处理内置演示套件
				new GTestSuite();
			}
		}catch (Exception e) {
			GLog.logSysFunctionException("findAndDoFuncByClazzAndFuncName", e);
		}
	}
}
