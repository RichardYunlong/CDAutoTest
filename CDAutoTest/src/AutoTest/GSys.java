package AutoTest;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

/**
 *  系统管理
 */
public class GSys {

	/**
	 *  系统自检是否就绪
	 */
	public static boolean IsTestReady = false;

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
	public static final String Guide = GetCurUserDesktopURL() + "\\CDAutoTestGuide.txt";

	/**
	 *  当控制台输出和日志保存的内容一样时使用此方法 需要输入开始时间
	 */
	public static void GLogDoReady(long startTime, String doName) {
		long endTime = System.currentTimeMillis();
		System.out.println("[" + endTime + "] [" + doName + "] READY - SPEND:" + (endTime - startTime) + "MS");
		GFile.WriteStringToBottom(Guide,
				GTime.getDate() + " [" + doName + "] READY -SPEND:" + (endTime - startTime) + "MS");
	}

	/**
	 *  初始化测试环境
	 */
	public GSys() {
		try {
			long startTime = 0;
			GFile.DeleteFolder(Guide);// 如果存在则删除测试日志
			GFile.WriteStringToBottom(Guide,
					"WELCOME TO USE AUTOTEST ENGINNE " + GParam.TestAutoVersion
							+ " \r\n\r\nDESIDN BY Richard.YunLong FROM CDragon Studio & CFCA\r\n\r\n\r\n"
							+ "\r\nPREPARE TESTING ENVIRONMENT START\r\n");

			startTime = System.currentTimeMillis();
			// 初始化第三方组件调用
			new GPlugins();
			GLogDoReady(startTime, "GPlugins");
			// 初始化输出路径
			new GPath();
			GLogDoReady(startTime, "GPath");
			// 初始化文件处理全局变量，用于提供处理方法
			startTime = System.currentTimeMillis();
			new GFile();
			GLogDoReady(startTime, "GFile");
			// 初始化全局日志，用于写入文件
			startTime = System.currentTimeMillis();
			new GLog();
			GLogDoReady(startTime, "GLog");
			// 初始化用例处理类
			startTime = System.currentTimeMillis();
			new GTestCase();
			GLogDoReady(startTime, "GTestCase");
			// 初始化异常处理全局变量，用于提供处理方法，依赖GTestCase
			startTime = System.currentTimeMillis();
			new GException();
			GLogDoReady(startTime, "GException");
			// 初始化文本处理全局变量，用于提供处理方法，不依赖自定义类
			startTime = System.currentTimeMillis();
			new GText();
			GLogDoReady(startTime, "GText");

			/* 全局变量-参数传递-初始化 */
			// 初始化获取时间，用于传递参数
			startTime = System.currentTimeMillis();
			new GTime();
			GLogDoReady(startTime, "GTime");
			// 初始化全局常量，用于传递参数
			startTime = System.currentTimeMillis();
			new GValue();
			GLogDoReady(startTime, "GValue");
			// 初始通信信息全局变量，用于传递参数;
			startTime = System.currentTimeMillis();
			new GTransfer();
			GLogDoReady(startTime, "GTransfer");
			// 初始化全局变量，用于传递参数;
			startTime = System.currentTimeMillis();
			new GParam();
			GLogDoReady(startTime, "GParam");
			// 初始化错误信息全局变量，用于传递参数;
			startTime = System.currentTimeMillis();
			new GError();
			GLogDoReady(startTime, "GError");
			// 初始化配置文件全局变量，用于传递参数;
			startTime = System.currentTimeMillis();
			new GConfig();
			GLogDoReady(startTime, "GConfig");
			// 初始化GObjectInputs输入参数全局变量，用于装填Object[][]用例输入参数集合;
			startTime = System.currentTimeMillis();
			new GObjectInputs();
			GLogDoReady(startTime, "GObjectInputs");
			// 初始化GSpecialCharacter输入参数全局变量，用于初始化特殊字符处理类;
			startTime = System.currentTimeMillis();
			new GSpecialCharacter();
			GLogDoReady(startTime, "GSpecialCharacter");
			// 初始化GUncommonCharacter输入参数全局变量，用于初始化生僻汉字处理类;
			startTime = System.currentTimeMillis();
			new GUncommonCharacter();
			GLogDoReady(startTime, "GUncommonCharacter");
			// 初始化GPreErrorCode输入参数全局变量，用于加载预置错误码和错误信息表;
			startTime = System.currentTimeMillis();
			new GPreErrorCode();
			GLogDoReady(startTime, "GPreErrorCode");
			
			/* 全局变量-参数传递-初始化完毕 */

			GFile.WriteStringToBottom(Guide,
					"\r\nCHECK THE FOLLOWING LOG FILE TO KNOW DETAILS DURING OR AFTER TESTING\r\n");
			for (int i = 0; i < 10; i++) {
				GFile.WriteStringToBottom(Guide, GLog.LogStyle[i]);
			}

			GFile.WriteStringToBottom(Guide, "\r\nTESTING ENVIRONMENT READY\r\n");
		}catch(Exception e) {
			IsTestReady = false;
			e.printStackTrace();
		}

		IsTestReady = true;
	}

	// public static void main(String[] args){/*
	// RecordTestStyleResult(0,"");
	// RecordTestStyleResult(1,"");
	// RecordTestStyleResult(2,"");
	// RecordTestStyleResult(3,"");*/
	// //System.out.println(GetTestRunEntra(300101).toString());
	// //CourtTag("C:\\Users\\hewei\\Desktop\\log.txt","errorCode:",242);
	// System.out.println(GetCurUserDesktopURL());
	// }
}
