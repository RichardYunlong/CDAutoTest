package Webdriver;

import DT.GLog;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 *  浏览器基本功能
 */
public class GWCtrlBasic {
	
	/**
	 *  打开目标地址
	 *
	 *  @param webDriver 目标驱动
	 *  @param Url 目标地址
	 */
	public static void Open(WebDriver webDriver, String Url) {
		webDriver.get(Url);
		GLog.logRecordTime(9,  "[browser]----[open]----[[" + Url + "]]");
	}
	
	/**
	 *  浏览器最大化
	 *
	 *  @param webDriver 目标驱动
	 */
	public static void Maximize(WebDriver webDriver) {
		webDriver.manage().window().maximize();
		GLog.logRecordTime(9,  "[browser]----[maximize]----[[]]");
	}
	
	/**
	 *  浏览器最小化
	 *  占位方法，功能不正确，需要补充
	 *
	 *  @param webDriver 目标驱动
	 */
	public static void Minimize(WebDriver webDriver) {
		webDriver.manage().window().maximize();
		GLog.logRecordTime(9,  "[browser]----[maximize]----[[]]");
	}
	
	/**
	 *  刷新浏览器
	 *
	 *  @param webDriver 目标驱动
	 */
	public static void Refresh(WebDriver webDriver) {
		webDriver.navigate().refresh();
		GLog.logRecordTime(9,  "[browser]----[refresh]----[[]]");
	}

	/**
	 *  浏览器后退
	 *
	 *  @param webDriver 目标驱动
	 */
	public static void Back(WebDriver webDriver) {
		webDriver.navigate().back();
		GLog.logRecordTime(9,  "[browser]----[back]----[[]]");
	}

	/**
	 *  浏览器前进
	 *
	 *  @param webDriver 目标驱动
	 */
	public static void Forward(WebDriver webDriver) {
		webDriver.navigate().forward();
		GLog.logRecordTime(9,  "[browser]----[forward]----[[]]");
	}
	
	/**
	 *  浏览器关闭
	 *
	 *  @param webDriver 浏览器自由驱动
	 *  @param gwedriver 自定义驱动
	 */
	public static void Quit(WebDriver webDriver,GWebDriver gwedriver) {
		webDriver.quit();
		gwedriver.driverStatus = "";
		gwedriver.browserOpenStatus = false;
		GLog.logRecordTime(9,  "[browser]----[quit]----[[]]");
	}
	
	/**
	 *  页签关闭
	 *
	 *  @param webDriver 目标驱动
	 */
	public static void Close(WebDriver webDriver) {
		webDriver.close();
		GLog.logRecordTime(9,  "[browser]----[close]----[[]]");
	}
	
	/**
	 *  获取当前地址栏文本
	 *
	 *  @param webDriver 目标驱动
	 *
	 *  @return 目标地址
	 */
	public static String Geturl(WebDriver webDriver) {
		String url= webDriver.getCurrentUrl();
		GLog.logRecordTime(9,  "[browser]----[url]----[[" + url + "]]");
		return url;
	}
	
	/**
	 *  获取窗口标题
	 *
	 *  @param webDriver 目标驱动
	 *
	 *  @return 目标地址
	 */
	public static String GetTitle(WebDriver webDriver) {
		String url= webDriver.getTitle();
		GLog.logRecordTime(9,  "[browser]----[url]----[[" + url + "]]");
		return url;
	}

	/**
	 *  窗口缩放比
	 *
	 *  @param webDriver 目标驱动
	 *  @param zoomTo 缩放比分子（分母为100）
	 */
	public static void PageZoomTo(WebDriver webDriver, int zoomTo) {
		String zoomLevel = String.valueOf(zoomTo / 100.0);
		((JavascriptExecutor) webDriver).executeScript("document.body.style.zoom='" + zoomLevel + "'");

		GWCtrlTime.Pause(webDriver, 3);
	}

	/**
	 *  窗口缩放比至指定比例，缓慢缩放，确保加载顺利
	 *
	 *  @param webDriver 目标驱动
	 *  @param zoomTo 缩放比分子（分母为100）
	 */
	public static void PageSlowlyZoomTo(WebDriver webDriver, int zoomTo) {
		PageZoomTo(webDriver, 95);
	}

	/**
	 *  窗口缩放比
	 *
	 *  @param webDriver 目标驱动
	 *  @param width 宽度
	 *  @param height 高度
	 */
	public static void PageResizeTo(WebDriver webDriver, int width, int height) {
		((JavascriptExecutor) webDriver).executeScript("window.resizeTo(" + width + ", " + height + ");");
		GWCtrlTime.Pause(webDriver, 3);
	}
}
