package DT;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import Base.GClazz;
import Base.GFile;
import Base.GMissionMsg;
import Base.GMsg;
import Base.GTime;
import Dragon.GDragon;
import Sut.GSut;
import Sys.GCfgInfo;
import Sys.GParam;
import Sys.GSys;
import Test.GTestPlan;

/**
 *  日志
 */
public class GLog {
	
    /**
     *  构造函数
     */
	private GLog(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  日志路径-根目录设置
	 */
	private static final String LOGHOME = System.getProperty("user.dir") + "/test";
	
	public static String getLoghome() {
		return LOGHOME;
	}
	
	/**
	 *  获得标准报文
	 */
	private static String getStandardMsgLog(String msgCode, String type, String msg) {
		return "业务测试：\n业务编码\n" + msgCode + "\n报文类型\n" + type + "\n报文内容\n" + msg;
	}

	/**
	 *  日志类型：0-有效用例日志；1-失败用例日志；2-无效用例日志；3-中断用例日志；4-参数读取；5-错误码统计；6-缓存配置；7-综合报告；8-系统功能日志；9-综合日志
	 */
	private static final String[] logPath = new String[] {
		LOGHOME + "/log0.txt",// 有效用例日志
		LOGHOME + "/log1.txt",// 失败用例日志
		LOGHOME + "/log2.txt",// 无效用例日志
		LOGHOME + "/log3.txt",// 中断用例日志
		LOGHOME + "/inputs.txt",// 参数读取
		LOGHOME + "/errorcode.txt",// 错误码统计
		LOGHOME + "/cache.txt",// 缓存配置
		LOGHOME + "/log_summory.txt",// 综合报告
		LOGHOME + "/log_sys.txt",// 系统功能日志
		LOGHOME + "/logs.txt"// 综合日志
	};
	
	public static String[] getLogpath() {
		return logPath;
	}

	/**
	 *  输出到控制台
	 *
	 *	结尾换行
	 *  @param str 内容
	 */
	public static void logShowConsole(String str) {
		System.out.println(str);
	}
	
	/**
	 *  输出到控制台
	 *  
	 *  结尾不换行
	 *  @param str 内容
	 */
	public static void logShowNoEnter(String str) {
		System.out.print(str);
	}
	
	/**
	 *  打印日志
	 *
	 *	不计算耗时
	 *  结尾换行
	 *  @param logFile 文件路径
	 *  @param strLog 报文内容
	 */
	public static void logRecord(String logFile, String strLog) {
		GFile.writeStringToBottom(logFile, strLog);
	}
	
	/**
	 * 	打印日志
	 * 
	 * 	不计算耗时
	 *  结尾不换行
	 *  @param logFile 文件路径
	 *  @param strLog 报文内容
	 */
	public static void logRecordNoEnter(String logFile, String strLog) {
		GFile.writeStringToRight(logFile, strLog);
	}

	/**
	 *  打印日志
	 *  
	 *  不计算耗时
	 *  结尾换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日志序号
	 *  @param strLog 报文内容
	 */
	public static void logRecord(int logIndex, String strLog) {
		if (logIndex < 0 || logIndex > 9) {
			GFile.writeStringToBottom(logPath[logIndex], GMsg.MSG_CONSOLE[1]);
			return;
		}
		GFile.writeStringToBottom(logPath[logIndex], strLog);
	}
	
	/**
	 * 	打印日志
	 * 
	 * 	不计算耗时
	 *  结尾不换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日志序号
	 *  @param strLog 报文内容
	 */
	public static void logRecordNoEnter(int logIndex, String strLog) {
		if (logIndex < 0 || logIndex > 9) {
			GFile.writeStringToRight(logPath[logIndex], GMsg.MSG_CONSOLE[1]);
			return;
		}
		GFile.writeStringToRight(logPath[logIndex], strLog);
	}
	
	/**
	 *  打印日志
	 *  
	 *  记录时间
	 *  结尾换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日志序号
	 *  @param strLog 报文内容
	 */
	public static void logRecordTime(int logIndex, String strLog) {
		if (logIndex < 0 || logIndex > 9) {
			GFile.writeStringToBottom(logPath[logIndex], "[" + GTime.getDate() + "]" + GMsg.MSG_CONSOLE[1]);
			return;
		}
		GFile.writeStringToBottom(logPath[logIndex], "[" + GTime.getDate() + "]" + strLog);
	}
	
	/**
	 * 	打印日志
	 * 
	 * 	记录时间
	 *  结尾不换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日志序号
	 *  @param strLog 报文内容
	 */
	public static void logRecordTimeNoEnter(int logIndex, String strLog) {
		if (logIndex < 0 || logIndex > 9) {
			GFile.writeStringToRight(logPath[logIndex], "[" + GTime.getDate() + "]" + GMsg.MSG_CONSOLE[1]);
			return;
		}
		GFile.writeStringToRight(logPath[logIndex], "[" + GTime.getDate() + "]" + strLog);
	}

	/**
	 * 	打印日志
	 * 
	 * 	不弹窗
	 * 	不计算耗时，提示窗使用默认规则
	 *  向指定类型的日志输入指定格式的内容-结尾换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日至序号
	 *  @param msgCode 业务编码
	 *  @param msgType 报文类型
	 *  @param msg 报文内容
	 */
	public static void logRecordNoWindow(int logIndex, String msgCode, String msgType, String msg) {
		String type = "未知报文";
		
		if(logIndex < 0 || logIndex > 9) {
			GFile.writeStringToBottom(logPath[logIndex], GMsg.MSG_CONSOLE[1]);
			return;
		}
		
		if(msgType.equals("req") || msgType.equals("REQ")) {
			type = "发送报文";
			GParam.setReq(msg);
		}else if (msgType.equals("res") || msgType.equals("RES")) {
			type = "响应报文";
			GParam.setRes(msg);
		}
		
		GFile.writeStringToBottom(logPath[logIndex], getStandardMsgLog(msgCode, type, msg));
	}
	/**
	 * 	打印日志
	 * 
	 * 	不计算耗时，提示窗使用默认规则
	 *  向指定类型的日志输入指定格式的内容-结尾换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日至序号
	 *  @param msgCode 业务编码
	 *  @param msgType 报文类型
	 *  @param msg 报文内容
	 */
	public static void logRecord(int logIndex, String msgCode, String msgType, String msg) {
		logRecordNoWindow(logIndex, msgCode, msgType, msg);
		
		if(GParam.isDragonShow()) {
			new Thread(() -> new GDragon(getStandardMsgLog(msgCode, msgType, GParam.getRes()), 0, 0, 0, "", null, 0, null)).start();
		}
	}
	
	/**
	 *  打印日志并弹窗提示
	 * 
	 *  不计算耗时
	 *  向指定类型的日志输入指定格式的内容-结尾换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日至序号
	 *  @param msgCode 业务编码
	 *  @param msgType 报文类型
	 *  @param msg 报文内容
	 *  @param mTime 持续时间，单位毫秒
	 *  @param dX 提示位置横坐标
   	 *  @param dY 提示位置纵坐标
	 *  @param img 提示图片 分辨率必须为240*360
   	 *  @param fType 字体
   	 *  @param fSize 字号
   	 *  @param fColor 字色
	 */
	public static void logRecord(int logIndex, String msgCode, String msgType, String msg, 
			int mTime, int dX, int dY, String img, String fType, int fSize, Color fColor) {
		logRecordNoWindow(logIndex, msgCode, msgType, msg);
		
		if(GParam.isDragonShow()) {
			new Thread(() -> new GDragon(msg, mTime, dX, dY, img, fType, fSize, fColor)).start();
		}
	}
	
	/**
	 *  初始化部分日志处理任务：主日志、错误码日志、缓存日志
	 */
	public static void logOn() {
		for (int i = 0; i < 10; i++) {
			GFile.deleteFolder(GLog.logPath[i]);// 如果存在则删除所有历史测试日志
		}

		//部分日志需要先写入一些内容
		logRecord(9, "\r\n" + GTime.getDate() + " [" + GSut.getSysName() + "] TESTCASE PROCCESS \r\n"); // 初始化主日志
		logRecord(8, "\r\n" + GTime.getDate() + " [" + GSut.getSysName() + "] TEST DRIVER START \r\n"); // 初始化驱动日志日志
		logRecord(5, "\r\n" + GTime.getDate() + " RECORD [" + GSut.getSysName() + "] ERROR SUMERY\r\n");// 初始化错误码日志
		logRecord(6, "[]");// 初始化缓存日志
		
		//系统参数表写入8号日志
		if(GTestPlan.isbIsBackup()){
			GCfgInfo.logAll();
		}
	}

	/**
	 *  关闭日志处理任务
	 */
	public static void logOff() {

		logRecord(9, "\r\n" + GTime.getDate() + " [" + GSut.getSysName() + "] TEST MISSION END\r\n");// 关闭测试日志
		logRecord(5, "\r\n" + GTime.getDate() + " [" + GSut.getSysName() + "] ERROR SUMERY IS RECORDED\r\n");// 关闭错误码记录

		if (GTestPlan.isbIsBackup()) {
			GSys.logSys(GMissionMsg.getStepTop("BACKUP ZIP START"));
			GSys.logSys(GSys.getDate() + " WRITE ZIP");
			FileOutputStream fosTgs = null;
			try {
				fosTgs = new FileOutputStream(new File(LOGHOME + "/backup.zip"));
			} catch (FileNotFoundException e) {
				GLog.logSysFunctionException("logOff", e);
			}
			GFile.toZip(logPath[9], fosTgs, true);
			GSys.logSys("BACKUP TO [" + logPath[9] + "/backup.zip]");
			GSys.logSys(GMissionMsg.getStepBottom("BACKUP ZIP COMPLETE"));
		}
	}

	/**
	 *  打印任务及动作耗时日志
	 *  
	 * 	记录某个任务从某一时刻起，到执行完成时，共消耗了多长时间
	 *  当控制台输出和日志保存的内容一样时使用此方法
	 *  @param startTime 开始时间
	 *  @param doName 任务名称
	 *  @param actionName 动作名称
	 */
	public static void logDoReady(long startTime, String doName, String actionName) {
		long endTime = System.currentTimeMillis();
		logRecord(9, GTime.getDate() + " [" + doName + "] " + actionName + " -SPEND:" + (endTime - startTime) + "MS");
	}

	/**
	 * 	打印任务耗时日志
	 * 
	 * 	记录某个任务从某一时刻起，到执行完成时，共消耗了多长时间
	 *  当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间
	 *  @param startTime 开始时间
	 *  @param doName 任务名称
	 */
	public static void logDoReady(long startTime, String doName) {
		long endTime = System.currentTimeMillis();
		logRecord(9, GTime.getDate() + " [" + doName + "] READY -SPEND:" + (endTime - startTime) + "MS");
	}

	/**
	 * 	打印任务日志
	 * 
	 *  不计算耗时
	 *  当控制台输出和日志保存的内容一样时使用此方法
	 *  @param doName 任务名称
	 */
	public static void logDoReady(String doName) {
		logRecord(9, doName);
	}
	
	/**
	 * 	打印分割线
	 * 
	 *  控制台输出和日志保存的一行相同的符号，常用于作为视觉分割
	 *  @param str 元素
	 *  @param n 个数
	 */
	public static void logDoLine(String str,int n) {
		if(n>=1) {
			for(int i = 0;i < n;i++) {
				GFile.writeStringToRight(logPath[9], str);
			}
		}
		GFile.writeStringToRight(logPath[9], "\n");
	}
	
	/**
	 * 	打印系统方法日志
	 * 
	 *  不计算耗时
	 *  当控制台输出和日志保存的内容一样时使用此方法
	 *  
	 *  @param functionName 任务名称
	 *  @param functionMsg 任务名称
	 */
	public static void logSysFunction(String functionName, String functionMsg) {
		logRecord(GSys.GUIDE, "[" + functionName + "]");
		logRecord(GSys.GUIDE, functionMsg);
	}
	
	/**
	 * 	打印系统方法异常日志
	 * 
	 * 	异常处理专用
	 *  不计算耗时
	 *  当控制台输出和日志保存的内容一样时使用此方法
	 *  
	 *  @param functionName 任务名称
	 *  @param functionMsg 任务名称
	 */
	public static void logSysFunctionException(String functionName, Exception e) {
		logRecord(GSys.GUIDE, "[" + functionName + "]");
		logRecord(GSys.GUIDE, GException.getExceptionAllinformation(e));
	}

	/**
	 * 	剪切方式另存日志
	 * 
	 *  仅支持剪切文件前若干行
	 * 
	 *  @param logFile 日志文件路径全名
	 *  @param rowNum 行数
	 */
    public static void logShear(String logFile, int rowNum) {
    	logRecord(8, "[" + GTime.getCurrentTime(GTime.FORMAT_14_TEXT) + "]check log file ......");
    	if(GFile.getFileRows(logFile) < ((long)rowNum)){
    		logRecord(8, "the rows number of [" + logFile + "] is less than " + rowNum + ",no need to be sheared");
    		return;
    	}

        GSys.logSys("[" + GTime.getCurrentTime(GTime.FORMAT_14_TEXT)  + "]" + "cut " + String.valueOf(rowNum) + " lines from the [" + logFile + "] start");
        List<String> list = GFile.getTxtByRowInterval(logFile, 1, rowNum); 
        String fileName = logFile + GTime.getCurrentTime(GTime.FORMAT_14) + ".txt";
        
        try(FileOutputStream fos = new FileOutputStream(fileName, true);
        	OutputStreamWriter fsw = new OutputStreamWriter(fos,"UTF-8");
        	BufferedWriter writer = new BufferedWriter(fsw);) {
	        for(int i = 0;i<list.size();i++)  
	        {  
				writer.write(list.get(i) + System.lineSeparator());
	            writer.flush();
	        }
	        GSys.logSys("[" + GTime.getCurrentTime(GTime.FORMAT_14_TEXT)  + "]cut " + String.valueOf(rowNum) + " lines from the [" + logFile + "] complete");
	        GFile.readAndRemoveFirstLines(logFile, rowNum);
	        GSys.logSys("[" + GTime.getCurrentTime(GTime.FORMAT_14_TEXT)  + "]delete " + String.valueOf(rowNum) + " lines from the file");
		} catch (IOException e) {
			GSys.logSys("[" + GTime.getCurrentTime(GTime.FORMAT_14_TEXT)  + "]cut " + String.valueOf(rowNum) + " lines from the [" + logFile + "] error");
			GLog.logSysFunctionException("logShear", e);
		}
    }
    
	/**
	 *  设置“系统信息”
	 */
	public static void setDefault(){
		long startTime = 0;
		GSys.logSys(GMissionMsg.getStepStart("GLogPath"));
		GSys.logSys(GSys.getDate() + " LOG FILES PREPARING");
		GFile.creatDir(GLog.getLoghome());
		String[] logPath = GLog.getLogpath().clone();
		for (int i = 0; i < 10; i++) {
			GSys.logSys(logPath[i]);
		}
		GSys.logShowAndRecord(startTime, "GLog");
		GSys.logSys(GMissionMsg.getStepEnd());
    }
}