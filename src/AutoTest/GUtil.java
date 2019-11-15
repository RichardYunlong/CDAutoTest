package AutoTest;

import java.lang.reflect.Method;

public class GUtil {
	private GUtil(){
		GLog.logShowConsole("This is a tool class.");
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
			e.printStackTrace();
		}
	}
}
