package Test;

import Base.GClazz;
import DT.GLog;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *  场景管理
 */
public class GTestScene {

	/**
	 *  构造函数
	 */
	public GTestScene(){
		GClazz.thisADataUnitClass();
	}
	
	/**
	 *  当前场景名称
	 */
	private String SCENE_NAME = "";

	/**
	 *  全局关键动态参数值-公共
	 */
	@SuppressWarnings({"unused", "FieldCanBeLocal"})
	private Map<String, String> SCENE_COMMON_DATA = null;
	
	/**
	 *  全局关键动态参数值-业务
	 */
	@SuppressWarnings({"unused", "FieldCanBeLocal"})
	private Map<String, String> SCENE_DYNAMIC_DATA = null;
	
	/**
	 *  按照java类名，执行java中名称为某指定名称的方法，称执行场景加载
	 *  
	 *  @param sceneClassFullPath 类全名
	 *  @param funcName 成员方法名
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GTestScene(String sceneClassFullPath, String funcName) {
		SCENE_COMMON_DATA = new HashMap<>();
		SCENE_DYNAMIC_DATA = new HashMap<>();
		try {
			if((sceneClassFullPath != null) && (!sceneClassFullPath.isEmpty())) {
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
	 *
	 *  @return 全局关键动态参数值
	 */
	public String getSceneName() {
		return SCENE_NAME;
	}
}
