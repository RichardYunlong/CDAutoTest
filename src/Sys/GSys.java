package Sys;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.swing.filechooser.FileSystemView;

import Base.GAbout;
import Base.GFile;
import Base.GMissionMsg;
import Base.GThread;
import DT.GErrorCode;
import DT.GLog;
import DT.GPlugins;
import Test.GTestResult;

/**
 *  系统加载
 */
public class GSys {
	
    /**
     *  构造函数
     */
	private GSys(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	   *  已加载
	 */
	private static int PROGRESS_CUR = 0;
    
    /**
     *  共需要加载
     */
	public static final int PROGRESS_TOTAL = 100;
	

	public static int getPROGRESS_CUR() {
		return PROGRESS_CUR;
	}

	public static void setPROGRESS_CUR(int pROGRESS_CUR) {
		PROGRESS_CUR = pROGRESS_CUR;
	}
	
	public static void progress() {
		PROGRESS_CUR++;
	}

	/**
	 *  系统引导文件
	 */
	public static final String GUIDE = System.getProperty("user.dir") + "/CDAutoTestGuide.txt";
	
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
	 *  获取yyyy/MM/dd HH:mm:ss格式的日期字符串
	 *  
	 *  @return 返回格式化的日期
	 */
	public static String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date now = new Date();
		String curDate = "";
		curDate = dateFormat.format(now);
		return curDate;
	}
	
	/**
	 *  当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间
	 *  
	 *  @param startTime 开始时间
	 *  @param doName 操作名称 
	 */
	public static void logShowAndRecord(long startTime, String doName) {
		long endTime = System.currentTimeMillis();
		GFile.writeStringToBottom(GUIDE, getDate() + " [" + doName + "] READY -SPEND:" + (endTime - startTime) + "MS");
	}
	
	/**
	 *  保存到GUIDE日志 当控制台输出和日志保存的内容一样时使用此方法
	 *  
	 *  @param str 内容
	 */
	public static void logSys(String str) {
		GFile.writeStringToBottom(GUIDE, str);
	}
	
	/**
	 *  保存异常信息到GUIDE日志 当控制台输出和日志保存的内容一样时使用此方法
	 *  
	 *  @param str 内容
	 */
	public static void logErrorSys(String str) {
		GFile.writeStringToBottom(GUIDE,"ERROR----" + str);
	}
	
	/**
	 *  当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间
	 *  
	 *  @param str 内容
	 */
	public static void logWarningSys(String str) {
		GFile.writeStringToBottom(GUIDE,"WARN----" + str);
	}

	/**
	 *  初始化测试环境
	 *  
	 *  @return 初始化成功返回true，否则返回false
	 */
	public static boolean initSys() {
		boolean bIsTestReady = false;
		
		try {
			long startTimeSys = System.currentTimeMillis();
			
			//欢迎界面窗体
			GThread.welcomeUI();
			PROGRESS_CUR++;
			//加载系统常量
			GAbout.setDefault();
			PROGRESS_CUR++;
			//根指引日志处理：如果存在则删除，创建新的根指引日志 
			GFile.deleteFolder(GUIDE);
			PROGRESS_CUR++;
			//欢迎语
			logSys(GAbout.getWelcome());
			PROGRESS_CUR++;
			//开始加载基础环境提示
			logSys(GMissionMsg.getMisstionTip("ENVIRONMENT LOADING"));
			PROGRESS_CUR++;
			//初始化系统插件
			GPlugins.setDefault();
			PROGRESS_CUR++;
			//初始化输出路径
			GPath.setDefault();
			PROGRESS_CUR++;
			//初始化错误信息全局变量
			GErrorCode.setDefault();
			PROGRESS_CUR++;
			//初始化配置文件全局变量
			GLoadConfig.loadConfig();
			PROGRESS_CUR++;
			//初始化全局日志
			GLog.setDefault();
			//初始化全局行为记录器
			GTestResult.setDefault();
			PROGRESS_CUR++;
			//基础环境加载完成提示
			long endTimeSys = System.currentTimeMillis();
			logSys("ENVIRONMENT LOADING -SPEND:" + (endTimeSys - startTimeSys) + "MS");
			GSys.logSys(GMissionMsg.getMisstionTip("ENVIRONMENT ENVIRONMENT COMPLETE"));
			
			bIsTestReady = true;
			
		}catch(Exception e) {
			GSys.logErrorSys("initSys[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		
		return bIsTestReady;
	}
}
