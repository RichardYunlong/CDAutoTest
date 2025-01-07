package main.java.DT;

import main.java.Base.GClazz;
import main.java.Base.GFile;
import main.java.Base.GMissionMsg;
import main.java.Sys.GStatic;

/**
 *  第三方插件
 */
public class GPlugins {
	
    /**
     *  构造函数
     */
	private GPlugins(){
		GClazz.thisAMainClass();
	}

	/**
	 *  允许你加载的插件最大值
	 */
	private static final int PLUGINS_NUM_MAX = 10;

	/**
	 *  插件列表
	 */
	private static String[] pluginsNames = new String[PLUGINS_NUM_MAX];
	public static String[] getPluginsNames() { return pluginsNames; }
	public static void setPluginsNames(String[] pluginsNames) { GPlugins.pluginsNames = pluginsNames; }

	/**
	 *  初始化所有插件
	 */
	public static void setDefault(){
		long startTime = 0;
		startTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom(GMissionMsg.getStepStart("GPlugins"));
		for(int i=0;i<PLUGINS_NUM_MAX;i++) {
			pluginsNames[i]="";
		}
		GStatic.gSys.logShowAndRecordGuide(startTime, "GPlugins");
		GFile.writeStringToGuideBottom(GMissionMsg.getStepEnd());
	}
	
}
