package main.java.Webdriver;

import main.java.DT.GLog;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 *  Div控件填写处理
 */
public class GWCtrlDivFill {
	
	/**
	 *  Div类目标元素，通过ID查找输入框，写入指定内容
	 *
	 *  @param webDriver 目标驱动
	 *  @param id ID值
	 *  @param str 内容
	 */
	public static void ById(WebDriver webDriver, String id, String str) {
		GLog.logRecordTime(0, "[widget]----[div]----[[");
		try {
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("document.getElementById('" + id + "').innerHTML="+ str);
			GLog.logRecordTime(0, "----<div[" + id + "]>" + GWCtrlMsg.ui_CLICK[0]);
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[div[" + id + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[div]----[widget]");
	}

	/**
	 *  Div类目标元素，通过ID查找输入框，写入指定内容
	 *
	 *  @param webDriver 目标驱动
	 *  @param xpath xpath值
	 *  @param str 内容
	 */
	public static void ByXpath(WebDriver webDriver, String xpath, String str) {
		GLog.logRecordTime(0, "[widget]----[div]----[[");
		try {
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("document.evaluate(" + xpath + ", document, null, 9, null).singleNodeValue.innerHTML="+ str);
			GLog.logRecordTime(0, "----<div[" + xpath + "]>" + GWCtrlMsg.ui_CLICK[0]);
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[div[" + xpath + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[div]----[widget]");
	}
}
