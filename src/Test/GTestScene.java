package Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import DT.GLog;

/**
 *  场景加载器
 */
public class GTestScene {
	
	/**
	 *  当前场景名称
	 */
	private static String SCENE_NAME = "";

	/**
	 *  全局关键动态参数值-公共
	 */
	@SuppressWarnings("unused")
	private static Map<String, String> SCENE_COMMON_DATA = null;
	
	/**
	 *  全局关键动态参数值-业务
	 */
	@SuppressWarnings("unused")
	private static Map<String, String> SCENE_DYNAMIC_DATA = null;
	
	/**
	 *  按照java类名，执行java中名称为某指定名称的方法，称执行场景加载
	 *  
	 *  @param sceneClassFullPath 类全名
	 *  @param funcName 成员方法名
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GTestScene(String sceneClassFullPath, String funcName) {
		SCENE_COMMON_DATA = new HashMap<String, String>();
		SCENE_DYNAMIC_DATA = new HashMap<String, String>();
		try {
			if((sceneClassFullPath != null) && (!sceneClassFullPath.equals(""))) {
				Class onwClass = Class.forName(sceneClassFullPath);
				Object obj= onwClass.newInstance();
				Method constractor = onwClass.getMethod(funcName);  
				constractor.invoke(obj);
				
				SCENE_NAME = sceneClassFullPath;
			}
		} catch (Exception e) {
			GLog.logSysFunctionException("GTestScene", e);
		}
	}
	
	/**
	 *  全局关键动态参数值-业务
	 */
	public static String getSceneName() {
		return SCENE_NAME;
	}
}
