package AutoTest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.filechooser.FileSystemView;

/**
 *  系统管理
 */
public class GSys {
	private GSys(){
		System.out.println("This is a tool class.");
	}
	
	/**
	 *  获取当前用户桌面路径
	 */
	public static String GetCurUserDesktopURL() {
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
	public static boolean IsTestReady = false;
	
	/**
	 *  自动化测试框架版本号
	 */
	public static String Version = "3.0.4.1";
	
	/**
	 *  获取yyyy/MM/dd HH:mm:ss格式的日期字符串
	 */
	private static String GetDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date now = new Date();
		String curDate = "";
		curDate = dateFormat.format(now);
		return curDate;
	}
	
	/**
	 *  当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间
	 * @param startTime 开始时间
	 * @param doName 操作名称 
	 */
	private static void GLogShowAndRecord(long startTime, String doName) {
		long endTime = System.currentTimeMillis();
		GFile.WriteStringToBottom(GUIDE, GetDate() + " [" + doName + "] READY -SPEND:" + (endTime - startTime) + "MS");
	}
	
	/**
	 *  当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间
	 * @param startTime 开始时间
	 * @param doName 操作名称 
	 */
	public static void GLogSys(String str) {
		GFile.WriteStringToBottom(GUIDE,"\r\n" + str +"\r\n");
	}
	
	/**
	 *  当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间
	 * @param startTime 开始时间
	 * @param doName 操作名称 
	 */
	public static void GLogErrorSys(String str) {
		GFile.WriteStringToBottom(GUIDE,"\r\n" + "EROOR----" + str +"\r\n");
	}

	/**
	 *  初始化测试环境
	 */
	public static boolean initSys() {
		try {
			long startTime_sys = System.currentTimeMillis();;
			long startTime = 0;
			GFile.DeleteFolder(GUIDE);// 如果存在则删除测试日志
			GLogSys("WELCOME TO USE AUTOTEST ENGINNE " + Version
					+ "\r\n\r\n\r\n"
					+ "DESIDN BY Richard.YunLong FROM CDragon Studio & CFCA"
					+ "\r\n\r\n\r\n"
					+ "TESTING ENVIRONMENT PREPARING");

			// 初始化系统插件，用于驱动外置配置文件
			startTime = System.currentTimeMillis();
			GPlugins.initGPlugins();
			GLogShowAndRecord(startTime, "GPlugins");
			// 初始化输出路径
			startTime = System.currentTimeMillis();
			GPath.initGPath();
			GLogShowAndRecord(startTime, "GPath");
			// 初始化全局日志，用于写入文件
			startTime = System.currentTimeMillis();
			GLog.initGLog();
			GLogShowAndRecord(startTime, "GLog");
			// 初始化错误信息全局变量，用于传递参数;
			startTime = System.currentTimeMillis();
			GError.initGError();
			GLogShowAndRecord(startTime, "GError");
			// 初始化配置文件全局变量，用于传递参数;
			startTime = System.currentTimeMillis();
			GConfig.loadConfig();
			GLogShowAndRecord(startTime, "GConfig");
			
			//初始化全部日志 
			GLogSys("TEST LOGS PREPARING");
			GFile.creatDir(GPath.LOGHOME);
			for (int i = 0; i < 10; i++) {
				GLogSys(GLog.LogStyle[i]);
			}

			long endTime_sys = System.currentTimeMillis();
			GLogSys("TEST ENVIRONMENT READY -SPEND:" + (endTime_sys - startTime_sys) + "MS");
			
			IsTestReady = true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return IsTestReady;
	}
}
