package main.java.page.base;

import main.java.DT.GLog;
import main.java.Webdriver.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map.Entry;

/**
 *  frame切换
 */
public class Iframe {
	
	/**
	 *  焦点切换回母版iframe
	 *
	 * @param webDriver 浏览器驱动对象
	 */
	public static void ui_C_SWITCN_DEFAULT(WebDriver webDriver) {
		GLog.logRecordTime(0, "[widget]----[iframe]----[[");
		try {
			webDriver.switchTo().defaultContent();
			GParam.iframeIndex = 0;
			GLog.logRecordTime(0, "----<iframe<switch to default>>");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[switch to default failed]>", true);
		}
		GLog.logRecordTime(0, "]]----[iframe]----[widget]");
	}
	
	/**
	 *  根据frame的索引来定位，也就是说一个页面如果有多个frame，可以根据frame(1),frame(2)从上往下去定位。
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param nIndex 焦点转到目标iframe
	 */
	public static void ui_C_SWITCN_INDEX(WebDriver webDriver, int nIndex) {
		GLog.logRecordTime(0, "[widget]----[iframe]----[[");
		try {
			webDriver.switchTo().frame(nIndex);
			GParam.iframeIndex = nIndex;
			GLog.logRecordTime(0, "----<iframe<switch to [" + nIndex + "]>>");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[switch to [" + nIndex + "] failed]>", true);
		}
		GLog.logRecordTime(0, "]]----[iframe]----[widget]");
	}
	
	/**
	 *  根据frame的ID或者name去识别
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param id 焦点转到目标iframe
	 */
	public static void ui_C_SWITCN_ID(WebDriver webDriver, String id) {
		GLog.logRecordTime(0, "[widget]----[iframe]----[[");
		try {
			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, id);
			webDriver.switchTo().frame(id);
			GLog.logRecordTime(0, "----<iframe<switch to id[" + id + "]>>");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[switch to id[" + id + "] failed]>", true);
		}
		GLog.logRecordTime(0, "]]----[iframe]----[widget]");
	}
	
	/**
	 *  切换到目标iframe
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param xpath 焦点转到目标iframe
	 */
	public static void ui_C_SWITCN_XPATH(WebDriver webDriver, String xpath){
		GLog.logRecordTime(0, "[widget]----[iframe]----[[");
		try {
			GWCtrlWait.ViewWaitingAllByXpath(webDriver, GTestIndicators.PageShowTime, xpath);
			WebElement iframe = webDriver.findElement(By.xpath(xpath));
			ui_C_SWITCN_ELEMENT(webDriver, iframe);
			GLog.logRecordTime(0, "----<iframe<switch to [" + xpath + "]>>");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[switch to [" + xpath + "] failed]>", true);
		}
		GLog.logRecordTime(0, "]]----[iframe]----[widget]");
	}
	
	/**
	 *  根据该找到iframe这个WebElement去识别，可通过xpath定位
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param webElement 焦点转到目标iframe
	 */
	public static void ui_C_SWITCN_ELEMENT(WebDriver webDriver, WebElement webElement) {
		GLog.logRecordTime(0, "[widget]----[iframe]----[[");
		try {
			if(GWCtrlWebElementIframe.getCN_IFRAME() != null) {
				for (Entry<String, WebElement>iframe : GWCtrlWebElementIframe.getCN_IFRAME().entrySet()) {
					if (iframe.getValue().equals(webElement)) {
						GParam.iframeIndex = Integer.parseInt(iframe.getKey());
					}
				}
			}

			webDriver.switchTo().frame(webElement);
			GLog.logRecordTime(0, "----<iframe<switch to webElement[" + webElement.toString() + "]>>");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[switch to webElement[" + webElement.toString() + "] failed]>", true);
		}
		GLog.logRecordTime(0, "]]----[iframe]----[widget]");
	}
}
