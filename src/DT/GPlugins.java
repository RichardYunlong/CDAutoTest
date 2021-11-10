package DT;

import Base.GClazz;
import Base.GMissionMsg;
import Sys.GSys;

/**
 *  第三方插件
 */
public class GPlugins {
	
    /**
     *  构造函数
     */
	private GPlugins(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  允许你加载的插件最大值
	 */
	private static final int PLUGINS_NUM_MAX = 10;
	
	/**
	 *  插件列表
	 */
	private static String[] pluginsNames = new String[PLUGINS_NUM_MAX];
	
	/**
	 *  初始化所有插件
	 */
	public static void setDefault(){
		long startTime = 0;
		startTime = System.currentTimeMillis();
		GSys.logSys(GMissionMsg.getStepStart("GPlugins"));
		for(int i=0;i<PLUGINS_NUM_MAX;i++) {
			pluginsNames[i]="";
		}
		GSys.logShowAndRecord(startTime, "GPlugins");
		GSys.logSys(GMissionMsg.getStepEnd());
	}
	
}
