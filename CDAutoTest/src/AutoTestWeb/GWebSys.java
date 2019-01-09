package AutoTestWeb;

import AutoTest.GLog;
import AutoTest.GParam;

/**
 *  系统管理
 */
public class GWebSys {
	/**
	 *  系统自检是否就绪
	 */
	public static boolean IsTestReady = false;
	
	/**
	 *  初始化测试环境
	 */
	public boolean initTestSys() {
		long startTime = 0;
		startTime = System.currentTimeMillis();
		
		// 初始化全局变量，用于传递参数;
		startTime = System.currentTimeMillis();
		new GParam();
		GLog.GLogDoReady(startTime, "GParam");
		
		// 初始化用例管理器;
		startTime = System.currentTimeMillis();
		new GTestCase();
		GLog.GLogDoReady(startTime, "GTestCase");
		
		// 初始化用例输出路径;
		startTime = System.currentTimeMillis();
		new GOutPutCtrl();
		GLog.GLogDoReady(startTime, "GOutPutCtrl");
		
		// 重写webdriver基础方法;
		startTime = System.currentTimeMillis();
		new GWCtrl();
		GLog.GLogDoReady(startTime, "GWCtrl");
		
		// 加载浏览器设置;
		startTime = System.currentTimeMillis();
		new GBrowser();
		GLog.GLogDoReady(startTime, "GBrowser");
		
		return IsTestReady;
	}
}
