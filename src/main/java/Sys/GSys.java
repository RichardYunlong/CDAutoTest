package main.java.Sys;

import main.java.Base.*;
import main.java.DT.GLog;
import main.java.DT.GPlugins;
import main.java.Test.GTestMission;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *  系统加载
 */
public class GSys {
	
    /**
     *  构造函数
     */
	public GSys(){
		GLog.logShowConsole("This is a tool class.");
	}

	/**
	 *  系统引导文件
	 */
	public final static String GUIDE = System.getProperty("user.dir") + "/CDAutoTestGuide";

	/**
	 *  mvn生成文件根位置
	 */
	public static final String mavenProjectRootPath = System.getProperty("user.dir");
	
	/**
	 *  获取当前用户桌面路径
	 *  
	 * @return 返回当前系统桌面绝对路径
	 */
	public String getCurUserDesktopURL() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File com = fsv.getHomeDirectory();
		return com.getPath();
	}
	
	/**
	 *  获取yyyy/MM/dd HH:mm:ss格式的日期字符串
	 *  
	 *  @return 返回格式化的日期
	 */
	public String getDate() {
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
	public void logShowAndRecordGuide(long startTime, String doName) {
		long endTime = System.currentTimeMillis();
		GFile.writeStringToBottom(GUIDE, getDate() + " [" + doName + "] READY -SPEND:" + (endTime - startTime) + "MS");
	}
	
	/**
	 *  保存到GUIDE日志 当控制台输出和日志保存的内容一样时使用此方法
	 *  
	 *  @param str 内容
	 */
	public void logGuide(String str) { GFile.writeStringToBottom(GUIDE, str); }
	
	/**
	 *  保存异常信息到GUIDE日志 当控制台输出和日志保存的内容一样时使用此方法
	 *  
	 *  @param str 内容
	 */
	public void logErrorGuide(String str) { GFile.writeStringToBottom(GUIDE,"ERROR----" + str); }
	
	/**
	 *  当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间
	 *  
	 *  @param str 内容
	 */
	public void logWarningGuide(String str) {
		GFile.writeStringToBottom(GUIDE,"WARN----" + str);
	}

	/**
	 *  设置“系统信息”
	 */
	public void setDefault(){
		long startTime = 0;
		logGuide(GMissionMsg.getStepStart("GLogPath"));
		logGuide(getDate() + " LOG FILES PREPARING");
		GFile.creatDir(GLog.getLoghome());
		String[] logPath = GLog.getLogpath().clone();
		for (int i = 0; i < 10; i++) {
			logGuide(logPath[i]);
		}
		logShowAndRecordGuide(startTime, "GLog");
		logGuide(GMissionMsg.getStepEnd());
	}

	/**
	 * 	剪切方式另存日志
	 *  仅支持剪切文件前若干行
	 *
	 *  @param logFile 日志文件路径全名
	 *  @param rowNum 行数
	 */
	public void logShear(String logFile, int rowNum) {
		GLog.logRecord(8, "[" + GTime.getCurrentTime(GTime.FORMAT_14_TEXT) + "]check log file ......");
		if(GFile.getFileRows(logFile) < ((long)rowNum)){
			GLog.logRecord(8, "the rows number of [" + logFile + "] is less than " + rowNum + ",no need to be sheared");
			return;
		}

		logGuide("[" + GTime.getCurrentTime(GTime.FORMAT_14_TEXT)  + "]" + "cut " + rowNum + " lines from the [" + logFile + "] start");
		List<String> list = GFile.getTxtByRowInterval(logFile, 1, rowNum);
		String fileName = logFile + GTime.getCurrentTime(GTime.FORMAT_14) + ".txt";

		try(FileOutputStream fos = new FileOutputStream(fileName, true);
            OutputStreamWriter fsw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(fsw)) {
            for (String s : list) {
                writer.write(s + System.lineSeparator());
                writer.flush();
            }
			logGuide("[" + GTime.getCurrentTime(GTime.FORMAT_14_TEXT)  + "]cut " + rowNum + " lines from the [" + logFile + "] complete");
			GFile.readAndRemoveFirstLines(logFile, rowNum);
			logGuide("[" + GTime.getCurrentTime(GTime.FORMAT_14_TEXT)  + "]delete " + rowNum + " lines from the file");
		} catch (IOException e) {
			logGuide("[" + GTime.getCurrentTime(GTime.FORMAT_14_TEXT)  + "]cut " + rowNum + " lines from the [" + logFile + "] error");
			GLog.logSysFunctionException("logShear", e);
		}
	}

	/**
	 *  初始化测试环境
	 *  
	 *  @return 初始化成功返回true，否则返回false
	 */
	public boolean initSys() {
		boolean bIsTestReady = false;
		
		try {
			long startTimeSys = System.currentTimeMillis();
			
			//欢迎界面窗体
			GThread.welcomeUI();
			GStatic.gMP.progress();
			//加载系统常量
			GStatic.gAbout.setDefault();
			GStatic.gMP.progress();
			//根指引日志处理：如果存在则删除，创建新的根指引日志 
			GFile.deleteFolder(GUIDE);
			GStatic.gMP.progress();
			//欢迎语
			logGuide(GStatic.gAbout.getWelcome());
			GStatic.gMP.progress();
			//开始加载基础环境提示
			logGuide(GMissionMsg.getMisstionTip("ENVIRONMENT LOADING"));
			GStatic.gMP.progress();
			//初始化系统插件
			GPlugins.setDefault();
			GStatic.gMP.progress();
			//初始化输出路径
			GPath.setDefault();
			GStatic.gMP.progress();
			//初始化配置文件全局变量
			GLoadConfig.loadConfig();
			GStatic.gMP.progress();
			//初始化全局日志
			setDefault();
			//初始化全局行为记录器
			GTestMission.gTestResult.setDefault();
			GStatic.gMP.progress();
			//基础环境加载完成提示
			long endTimeSys = System.currentTimeMillis();
			logGuide("ENVIRONMENT LOADING -SPEND:" + (endTimeSys - startTimeSys) + "MS");
			logGuide(GMissionMsg.getMisstionTip("ENVIRONMENT ENVIRONMENT COMPLETE"));
			
			bIsTestReady = true;
			
		}catch(Exception e) {
			logErrorGuide("initSys[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		
		return bIsTestReady;
	}
}
