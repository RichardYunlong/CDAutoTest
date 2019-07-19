package Plugins;

/**
 *  插件常量
 */
public class SystemConst {

	private SystemConst(){
		System.out.println("This is a tool class.");
	}
	
	/**
	 *  设置配置文件来源名
	 */
	public static final String CONFIG_LOCATION = "configLocation";
	
	/**
	 *  spring框架配置文件名
	 */
	public static final String SPRING_CONFIG_FILE = "spring.xml";
	
	/**
	 *  计数
	 */
	public static final int COUNT = 500;
	
	/**
	 *  行数
	 */
	public static final int FETCH_SIZE = 1000;

}