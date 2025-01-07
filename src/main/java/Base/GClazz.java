package main.java.Base;

import main.java.DT.GLog;
import main.java.Test.GTestSuite;

import java.lang.reflect.Method;

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
	 *  在控制台输出说明，提示目标是一个系统核心类
	 */
	public static void thisAMainClass(){
		GLog.logShowConsole("THIS IS A MAIN CLASS");
	}

	/**
	 *  在控制台输出说明，提示目标是一个数据类型类
	 */
	public static void thisADataUnitClass(){
		GLog.logShowConsole("THIS IS A DATA UNIT CLASS");
	}
	
	/**
	 *  根据类名称查找器成员方法并执行
	 *  @param clazzName 类名称
	 *  @param funcName 方法名称
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void findAndDoFuncByClazzAndFuncName(String clazzName, String funcName) {
		try {
			if((clazzName != null) && (!clazzName.isEmpty())) {
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
