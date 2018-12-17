package AutoTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GLog {

	/**
	 * @see 日志类型：0-有效用例日志；1-失败用例日志；2-无效用例日志；3-中断用例日志；4-参数读取；5-错误码统计；6-缓存配置；7-综合报告；8-系统功能日志；9-综合日志
	 */
	public static String[] LogStyle = new String[10];

	/**
	 * @see 是否启用备份
	 */
	public static boolean IsBackup = false;

	/**
	 * @see 初始化所有日志配置
	 */
	public GLog() {
		LogStyle[0] = GPath.PathStyle[0] + "\\log0.txt";// 有效用例日志
		LogStyle[1] = GPath.PathStyle[1] + "\\log1.txt";// 失败用例日志
		LogStyle[2] = GPath.PathStyle[2] + "\\log2.txt";// 无效用例日志
		LogStyle[3] = GPath.PathStyle[3] + "\\log3.txt";// 中断用例日志
		LogStyle[4] = GPath.PathStyle[4] + "\\inputs.txt";// 参数读取
		LogStyle[5] = GPath.PathStyle[5] + "\\errorcode.txt";// 错误码统计
		LogStyle[6] = GPath.PathStyle[6] + "\\cache.txt";// 缓存配置
		LogStyle[7] = GPath.PathStyle[7] + "\\log_summory.txt";// 综合报告
		LogStyle[8] = GPath.PathStyle[8] + "\\log_sys.txt";// 系统功能日志
		LogStyle[9] = GPath.PathStyle[9] + "\\logs.txt";// 综合日志
	}

	/**
	 * @see 向指定类型的日志输入指定内容
	 */
	public static void GLogRecord(int logIndex, String strLog) {
		if (logIndex < 0 || logIndex > 9) {
			System.out.println("Unknown Log Type!");
			return;
		}
		System.out.println(strLog);
		GFile.WriteStringToBottom(LogStyle[logIndex], strLog);
	}

	/**
	 * @see 初始化部分日志文件的内容：主日志、错误码日志、缓存日志
	 */
	public static void GLogOn() {
		for (int i = 0; i < 10; i++) {
			GFile.DeleteFolder(GLog.LogStyle[i]);// 如果存在则删除所有历史测试日志
		}
		GFile.DeleteFolder(GParam.TestCaseOutputExcelFullName);// 如果存在则删除用例输出文件

		GLogRecord(9, "\r\n" + GTime.getDate() + " [" + GParam.TestVersion + "] TEST MISSION START \r\n"); // 初始化主日志
		GLogRecord(5, "\r\n" + GTime.getDate() + " RECORD [" + GParam.TestVersion + "] ERROR SUMERY\r\n");// 初始化错误码日志
		GLogRecord(6, "[]");// 初始化缓存日志
	}

	/**
	 * @throws FileNotFoundException
	 * @see 关闭日志文件
	 */
	public static void GLogOff() {

		GLogRecord(9, "\r\n" + GTime.getDate() + " [" + GParam.TestVersion + "] TEST MISSION END\r\n");// 关闭测试日志
		GLogRecord(5, "\r\n" + GTime.getDate() + " ERROR SUMERY IS RECORDED");// 关闭错误码记录
		GFile.WriteStringToBottom(GSys.Guide, "\r\nTESTING END\r\n");

		if (IsBackup == true) {
				FileOutputStream fosTgs = null;
				try {
					fosTgs = new FileOutputStream(new File(GPath.PathStyle[9] + "\\backup.zip"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				GFile.toZip(GPath.PathStyle[9], fosTgs, true);
				GFile.WriteStringToBottom(GSys.Guide,
						"\r\nCHECK THE FOLLOWING BACKUP FILE TO REWEIW DETAILS DURING OR AFTER TESTING\r\n");
				GFile.WriteStringToBottom(GSys.Guide, GPath.PathStyle[9] + "\\backup.zip");
		}
	}

	/**
	 * @see 当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间、和动作名称
	 */
	public static void GLogDoReady(long startTime, String doName, String actionName) {
		long endTime = System.currentTimeMillis();
		System.out.println(
				"[" + endTime + "] [" + doName + "] " + actionName + "  - SPEND:" + (endTime - startTime) + "MS");
		GLogRecord(9, GTime.getDate() + " [" + doName + "] " + actionName + " -SPEND:" + (endTime - startTime) + "MS");
	}

	/**
	 * @see 当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间
	 */
	public static void GLogDoReady(long startTime, String doName) {
		long endTime = System.currentTimeMillis();
		System.out.println("[" + endTime + "] [" + doName + "] READY - SPEND:" + (endTime - startTime) + "MS");
		GLogRecord(9, GTime.getDate() + " [" + doName + "] READY -SPEND:" + (endTime - startTime) + "MS");
	}

	/**
	 * @see 当控制台输出和日志保存的内容一样时使用此方法
	 */
	public static void GLogDoReady(String doName) {
		System.out.println(doName);
		GLogRecord(9, doName);
	}
	
	/**
	 * @see 控制台输出和日志保存的一行相同的符号，常用于作为视觉分割
	 */
	public static void GLogDoLine(String str,int n) {
		if(n>=1) {
			for(int i = 0;i < n;i++) {
				System.out.print(str);
				GFile.WriteStringToRight(LogStyle[9], str);
			}
		}
		System.out.print("\n");
		GFile.WriteStringToRight(LogStyle[9], "\n");
	}
}
