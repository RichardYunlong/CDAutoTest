package AutoTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *  日志处理
 */
public class GLog {
	private GLog(){
		logShowConsole("This is a tool class.");
	}

	/**
	 *  日志类型：0-有效用例日志；1-失败用例日志；2-无效用例日志；3-中断用例日志；4-参数读取；5-错误码统计；6-缓存配置；7-综合报告；8-系统功能日志；9-综合日志
	 */
	public static String[] strLogStyle = new String[10];
	
	/**
	 *  初始化所有日志配置
	 */
	public static void initGLog() {
		strLogStyle[0] = GPath.strPathStyle[0] + "/log0.txt";// 有效用例日志
		strLogStyle[1] = GPath.strPathStyle[1] + "/log1.txt";// 失败用例日志
		strLogStyle[2] = GPath.strPathStyle[2] + "/log2.txt";// 无效用例日志
		strLogStyle[3] = GPath.strPathStyle[3] + "/log3.txt";// 中断用例日志
		strLogStyle[4] = GPath.strPathStyle[4] + "/inputs.txt";// 参数读取
		strLogStyle[5] = GPath.strPathStyle[5] + "/errorcode.txt";// 错误码统计
		strLogStyle[6] = GPath.strPathStyle[6] + "/cache.txt";// 缓存配置
		strLogStyle[7] = GPath.strPathStyle[7] + "/log_summory.txt";// 综合报告
		strLogStyle[8] = GPath.strPathStyle[8] + "/log_sys.txt";// 系统功能日志
		strLogStyle[9] = GPath.strPathStyle[9] + "/logs.txt";// 综合日志
	}
	
	/**
	 *  仅输出到控制台-结尾换行
	 *  
	 *  @param str 内容
	 */
	public static void logShowConsole(String str) {
		System.out.println(str);
	}
	
	/**
	 *  仅输出到控制台-结尾不换行
	 *  
	 *  @param str 内容
	 */
	public static void logShowNoEnter(String str) {
		System.out.print(str);
	}
	
	/**
	 *  向指定类型的文件写入指定内容-结尾换行
	 *  @param logFile 文件路径
	 *  @param strLog 报文内容
	 */
	public static void logRecord(String logFile, String strLog) {
		GFile.writeStringToBottom(logFile, strLog);
	}
	
	/**
	 *  向指定类型的文件写入指定内容-结尾不换行
	 *  @param logFile 文件路径
	 *  @param strLog 报文内容
	 */
	public static void logRecordNoEnter(String logFile, String strLog) {
		GFile.writeStringToRight(logFile, strLog);
	}

	/**
	 *  向指定类型的日志输入指定内容-结尾换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日志序号
	 *  @param strLog 报文内容
	 */
	public static void logRecord(int logIndex, String strLog) {
		if (logIndex < 0 || logIndex > 9) {
			GFile.writeStringToBottom(strLogStyle[logIndex], GMsg.MSG_LOGERROR[0]);
			return;
		}
		GFile.writeStringToBottom(strLogStyle[logIndex], strLog);
	}
	
	/**
	 *  向指定类型的日志输入指定内容-结尾不换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日志序号
	 *  @param strLog 报文内容
	 */
	public static void logRecordNoEnter(int logIndex, String strLog) {
		if (logIndex < 0 || logIndex > 9) {
			GFile.writeStringToBottom(strLogStyle[logIndex], GMsg.MSG_LOGERROR[0]);
			return;
		}
		GFile.writeStringToBottom(strLogStyle[logIndex], strLog);
	}

	/**
	 *  向指定类型的日志输入指定格式的内容-结尾换行-将校验日志序号，如果对应日志不存在则不写入
	 *  @param logIndex 日至序号
	 *  @param msgCode 业务编码
	 *  @param msgType 报文类型
	 *  @param msg 报文内容
	 */
	public static void logRecord(int logIndex, String msgCode, String msgType, String msg) {
		String type = "未知报文";
		if(logIndex < 0 || logIndex > 9) {
			GFile.writeStringToBottom(strLogStyle[logIndex], GMsg.MSG_LOGERROR[0]);
			return;
		}
		
		if(msgType.equals("req") || msgType.equals("REQ")) {
			type = "发送报文";
			GParam.gReq = msg;
		}else if (msgType.equals("res") || msgType.equals("RES")) {
			type = "响应报文";
			GParam.gRes = msg;
		}
		
		GFile.writeStringToBottom(strLogStyle[logIndex], "业务测试：\n业务编码\n" + msgCode + "\n报文类型\n" + type + "\n报文内容\n" + msg);
	}
	
	/**
	 *  初始化部分日志文件的内容：主日志、错误码日志、缓存日志
	 */
	public static void logOn() {
		GSys.logSys("TESTING START");
		for (int i = 0; i < 10; i++) {
			GFile.deleteFolder(GLog.strLogStyle[i]);// 如果存在则删除所有历史测试日志
		}

		logRecord(9, "\r\n" + GTime.getDate() + " [" + GParam.strTestVersion + "] TEST MISSION START \r\n"); // 初始化主日志
		logRecord(5, "\r\n" + GTime.getDate() + " RECORD [" + GParam.strTestVersion + "] ERROR SUMERY\r\n");// 初始化错误码日志
		logRecord(6, "[]");// 初始化缓存日志
	}

	/**
	 *  关闭日志文件
	 */
	public static void logOff() {

		logRecord(9, "\r\n" + GTime.getDate() + " [" + GParam.strTestVersion + "] TEST MISSION END\r\n");// 关闭测试日志
		logRecord(5, "\r\n" + GTime.getDate() + " ERROR SUMERY IS RECORDED");// 关闭错误码记录

		if (GTestPlan.bTestOutputBackupResult) {
				FileOutputStream fosTgs = null;
				try {
					fosTgs = new FileOutputStream(new File(GPath.strPathStyle[9] + "/backup.zip"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				GFile.toZip(GPath.strPathStyle[9], fosTgs, true);
				GSys.logSys(GPath.strPathStyle[9] + "/backup.zip");
				GSys.logSys("CHECK THE FOLLOWING BACKUP FILE TO REWEIW DETAILS DURING OR AFTER TESTING");
		}
		GSys.logSys("TESTING END");
	}

	/**
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
	 *  当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间
	 *  @param startTime 开始时间
	 *  @param doName 任务名称
	 */
	public static void logDoReady(long startTime, String doName) {
		long endTime = System.currentTimeMillis();
		logRecord(9, GTime.getDate() + " [" + doName + "] READY -SPEND:" + (endTime - startTime) + "MS");
	}

	/**
	 *  当控制台输出和日志保存的内容一样时使用此方法
	 *  @param doName 任务名称
	 */
	public static void logDoReady(String doName) {
		logRecord(9, doName);
	}
	
	/**
	 *  控制台输出和日志保存的一行相同的符号，常用于作为视觉分割
	 *  @param str 元素
	 *  @param n 个数
	 */
	public static void logDoLine(String str,int n) {
		if(n>=1) {
			for(int i = 0;i < n;i++) {
				GFile.writeStringToRight(strLogStyle[9], str);
			}
		}
		GFile.writeStringToRight(strLogStyle[9], "\n");
	}
}
