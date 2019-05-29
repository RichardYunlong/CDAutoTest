package AutoTest;

/**
 *  第三方插件管理
 */
public class GPlugins {
	/**
	 *  允许你加载的插件最大值
	 */
	public static int pluginsNum_Max = 10;
	
	/**
	 *  插件列表
	 */
	public static String[] pluginsNames = new String[pluginsNum_Max];
	
	/**
	 *  初始化所有插件
	 */
	GPlugins(){
		for(int i=0;i<pluginsNum_Max;i++) {
			pluginsNames[i]="";
		}
	}
	
}
