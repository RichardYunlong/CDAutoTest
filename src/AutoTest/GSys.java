package AutoTest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.filechooser.FileSystemView;

/**
 *  系统加载
 */
public class GSys {
	private GSys(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  获取当前用户桌面路径
	 *  
	 * @return 返回当前系统桌面绝对路径
	 */
	public static String getCurUserDesktopURL() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File com = fsv.getHomeDirectory(); // 这便是读取桌面路径的方法了
		return com.getPath();
	}

	
	/**
	 *  系统引导文件
	 */
	public static final String GUIDE = System.getProperty("user.dir") + "/CDAutoTestGuide.txt";

	/**
	 *  系统自检是否就绪
	 */
	public static boolean bIsTestReady = false;
	
	/**
	 *  获取yyyy/MM/dd HH:mm:ss格式的日期字符串
	 *  
	 *  @return 返回格式化的日期
	 */
	private static String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date now = new Date();
		String curDate = "";
		curDate = dateFormat.format(now);
		return curDate;
	}
	
	/**
	 *  当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间
	 *  
	 * @param startTime 开始时间
	 * @param doName 操作名称 
	 */
	private static void logShowAndRecord(long startTime, String doName) {
		long endTime = System.currentTimeMillis();
		GFile.writeStringToBottom(GUIDE, getDate() + " [" + doName + "] READY -SPEND:" + (endTime - startTime) + "MS");
	}
	
	/**
	 *  当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间
	 *  
	 * @param str 内容
	 */
	public static void logSys(String str) {
		GFile.writeStringToBottom(GUIDE,"\r\n" + str +"\r\n");
	}
	
	/**
	 *  当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间
	 *  
	 * @param str 内容
	 */
	public static void logErrorSys(String str) {
		GFile.writeStringToBottom(GUIDE,"\r\n" + "ERROR----" + str +"\r\n");
	}

	/**
	 *  初始化测试环境
	 *  
	 *  @return 初始化成功返回true，否则返回false
	 */
	public static boolean initSys() {
		try {
			long startTimeSys = System.currentTimeMillis();;
			long startTime = 0;
			//如果存在则删除历史日志
			GFile.deleteFolder(GUIDE);
			logSys(GMsg.SYSTEM_WELCOME);
			logSys("TESTING ENVIRONMENT PREPARING");

			// 初始化系统插件
			startTime = System.currentTimeMillis();
			GPlugins.initGPlugins();
			logShowAndRecord(startTime, "GPlugins");
			// 初始化输出路径
			startTime = System.currentTimeMillis();
			GPath.initGPath();
			logShowAndRecord(startTime, "GPath");
			// 初始化全局日志
			startTime = System.currentTimeMillis();
			GLog.initGLog();
			logShowAndRecord(startTime, "GLog");
			// 初始化错误信息全局变量
			startTime = System.currentTimeMillis();
			GResult.initGError();
			logShowAndRecord(startTime, "GError");
			// 初始化配置文件全局变量
			startTime = System.currentTimeMillis();
			GConfig.loadConfig();
			logShowAndRecord(startTime, "GConfig");
			
			//初始化全部日志 
			logSys("TEST LOGS PREPARING");
			GFile.creatDir(GPath.LOGHOME);
			for (int i = 0; i < 10; i++) {
				logSys(GLog.strLogStyle[i]);
			}

			long endTimeSys = System.currentTimeMillis();
			logSys("TEST ENVIRONMENT READY -SPEND:" + (endTimeSys - startTimeSys) + "MS");
			
			bIsTestReady = true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return bIsTestReady;
	}
}
