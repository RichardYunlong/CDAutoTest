package AutoTest;

/**
 *  第三方插件管理
 */
public class GPlugins {
	private GPlugins(){
		System.out.println("This is a tool class.");
	}
	
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
	public static void initGPlugins(){
		for(int i=0;i<pluginsNum_Max;i++) {
			pluginsNames[i]="";
		}
	}
	
}
