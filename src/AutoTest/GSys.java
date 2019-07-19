package AutoTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
	 *  文件操作结果标记
	 */
	private static boolean flag = false;

	/**
	 *  文件变量
	 */
	private static File file = null;
	
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
		WriteStringToBottom(GUIDE, GetDate() + " [" + doName + "] READY -SPEND:" + (endTime - startTime) + "MS");
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
	 * 根据文件全名，向其尾部换行添加指定文本，如果改文件不存在则创建
	 * 
	 * @param file 目标文件全名
	 * @param conent 指定内容。
	 */
	private static void WriteStringToBottom(String file, String conent) {
		BufferedWriter out = null;
		OutputStreamWriter outS = null;
		FileOutputStream outF = null;
		try {
			if(null != file) {
				outF = new FileOutputStream(file, true);
				if(null != outF) {
					outS = new OutputStreamWriter(outF, "UTF-8");
					if(null != outS) {
						out = new BufferedWriter(outS);
						out.write(conent + "\r\n");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null)out.close();
				if(outS != null)outS.close();
				if(outF != null)outF.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除单个文件
	 * 
	 * @param sPath 被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	private static boolean deleteFile(String sPath) {
		flag = false;
		file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			if(file.delete())
				flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath 被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	private static boolean deleteDirectory(String sPath) {
		boolean res = false;
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return res;
		}
		flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		if (files != null && files.length >= 1) {
			for (int i = 0; i < files.length; i++) {
				// 删除子文件
				if (files[i].isFile()) {
					flag = deleteFile(files[i].getAbsolutePath());
					if (!flag)
						break;
				} // 删除子目录
				else {
					flag = deleteDirectory(files[i].getAbsolutePath());
					if (!flag)
						break;
				}
			}	
		} else {
			System.out.println("NO CHILD FILES");
		}

		if (!flag)return res;
		// 删除当前目录
		if (dirFile.delete()) {
			res = true;
		}
		
		return res;
	}
	
	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param sPath 要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	private static boolean DeleteFolder(String sPath) {
		flag = false;
		file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 *  初始化测试环境
	 */
	public static boolean initSys() {
		try {
			long startTime_sys = System.currentTimeMillis();;
			long startTime = 0;
			DeleteFolder(GUIDE);// 如果存在则删除测试日志
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
			GLogSys("TEST LOGS PREPARING");

			long endTime_sys = System.currentTimeMillis();
			GLogSys("TEST ENVIRONMENT READY -SPEND:" + (endTime_sys - startTime_sys) + "MS");
			
			IsTestReady = true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return IsTestReady;
	}
}
