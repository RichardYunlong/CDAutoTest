package Base;

import DT.GLog;
import Test.GTestSuite;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

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

    /**
     * 获得当前调用栈的简化文本
     *
     * <p>把每一帧渲染成 {@code 类名.方法名}，用 {@code " -> "} 串成一条调用链，
     * 用于日志中快速定位调用来源。会跳过 {@code getStackTrace} 自身与直接调用方两帧。</p>
     *
     * @return 形如 {@code A.a -> B.b -> C.c} 的调用链文本
     */
    public static String getSimpleCallStackText() {
        return Arrays.stream(Thread.currentThread().getStackTrace())
                .skip(2)
                .map(ste -> ste.getClassName() + "." + ste.getMethodName())
                .collect(Collectors.joining(" -> "));
    }
}
