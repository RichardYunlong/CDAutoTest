package main.java.Webdriver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *  场景加载器
 */
public class GScene {
	
	/**
	 *  当前场景名称
	 */
	private static String sceneName = "";
	
	public static String getSceneName() {
		return sceneName;
	}

	public static void setSceneName(String GSceneName) {
		GScene.sceneName = GSceneName;
	}

	/**
	 *  已支持场景，根据类名称调用，所以新增场景脚本需要注册到这个变量中
	 */
	private static Map<String, String> sceneNames = null;
	
	public static Map<String, String> getSceneNames() {
		return sceneNames;
	}

	public static void setSceneNames(Map<String, String> GSceneNames) {
		sceneNames = new HashMap<>();
		GScene.sceneNames.putAll(GSceneNames);
	}

	/**
	 *  全局关键动态参数值-公共
	 */
	public static Map<String, String> COMMON_DATA = null;
	
	/**
	 *  全局关键动态参数值-业务
	 */
	@SuppressWarnings("CanBeFinal")
    public static Map<String, String> DYNAMIC_DATA = null;
	
    /**
     *  自定义全局场景器-物资管理全局测试数据
     */
    @SuppressWarnings("CanBeFinal")
    public static Map<String, String> MATERIAL_GLOBAL_DATA = null;
    
    /**
     *  自定义全局场景器-分包管理全局测试数据
     */
    public static Map<String, String> SUBPACKAGE_GLOBAL_DATA = null;
    
    /**
     *自定义全局场景器-机械社保固定资产编号list
     */
    public static ArrayList<String> FixedAssetNumber = null;
    
	/**
	 *  构造函数
	 *
	 *  @param sceneName 场景名称
	 */
	@SuppressWarnings({"rawtypes", "unchecked", "CallToPrintStackTrace"})
	public GScene(String sceneName) {
		try {
			if((sceneName != null) && (!sceneName.isEmpty())) {
				Class onwClass = Class.forName(sceneName);
				Object obj= onwClass.newInstance();
				Method constractor = onwClass.getMethod("ui_C_PARAMS");
				constractor.invoke(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(GScene.DYNAMIC_DATA.toString());
		System.out.println(GScene.MATERIAL_GLOBAL_DATA.toString());
	}
}
