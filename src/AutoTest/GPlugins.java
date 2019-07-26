package AutoTest;

/**
 *  第三方插件管理
 */
public class GPlugins {
	private GPlugins(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  允许你加载的插件最大值
	 */
	public static final int PLUGINS_NUM_MAX = 10;
	
	/**
	 *  插件列表
	 */
	public static String[] pluginsNames = new String[PLUGINS_NUM_MAX];
	
	/**
	 *  初始化所有插件
	 */
	public static void initGPlugins(){
		for(int i=0;i<PLUGINS_NUM_MAX;i++) {
			pluginsNames[i]="";
		}
	}
	
}
