package AutoTestWeb;

import AutoTest.GLog;
import AutoTest.GParam;

/**
 *  系统管理
 */
public class GSys {
	/**
	 *  系统自检是否就绪
	 */
	public static boolean IsTestReady = false;
	
	/**
	 *  初始化测试环境
	 */
	public static boolean initTestSys() {
		long startTime = 0;
		startTime = System.currentTimeMillis();
		
		// 初始化全局变量，用于传递参数;
		startTime = System.currentTimeMillis();
		new GParam();
		GLog.GLogDoReady(startTime, "GParam");
		
		// 加载浏览器设置;
		startTime = System.currentTimeMillis();
		new GBrowser();
		GLog.GLogDoReady(startTime, "GBrowser");
		
		return IsTestReady;
	}
}
