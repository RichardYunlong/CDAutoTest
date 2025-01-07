package Webdriver;

import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *  Div控件双击处理
 */
public class GWCtrlDivDoubleClick {
	
	/**
	 *  Div类目标元素，通过ID查找层，确认点击
	 *
	 * @param webDriver 浏览器驱动对象
	 * @param id ID
	 */
	public static void ById(WebDriver webDriver, String id) {
		GLog.logRecordTime(0, "[widget]----[div]----[[");
		try {
			GWCtrlWait.Wait2BeClickableById(webDriver, GTestIndicators.PageShowTime, id);
			WebElement div = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, div, 1, "");
			Actions action=new Actions(webDriver);
			action.doubleClick(div).build().perform();
			GLog.logRecordTime(0, "----<div[" + id + "]>" + GWCtrlMsg.ui_CLICK[0]);
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[div[" + id + "]" + GWCtrlMsg.ui_QUERY[2] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[div]----[widget]");
	}
	
	/**
	 *  Div类目标元素，通过Xpath查找层，确认点击
	 *
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param xpath Xpath
	 */
	public static void ByXpath(WebDriver webDriver, String xpath) {
		GLog.logRecordTime(0, "[widget]----[div]----[[");
		try {
			GWCtrlWait.Wait2BeClickableByXpath(webDriver, GTestIndicators.PageShowTime, xpath);
			WebElement div = webDriver.findElement(By.xpath(xpath));
			Actions action=new Actions(webDriver);
			action.doubleClick(div).build().perform();
			GLog.logRecordTime(0, "----<div[" + xpath + "]>>" + GWCtrlMsg.ui_CLICK[0]);
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[div[" + xpath + "]" + GWCtrlMsg.ui_QUERY[2] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[div]----[widget]");
	}
	
	/**
	 *  Div类目标元素，通过ClassName查找层，确认点击
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param className ClassName
	 */
	public static void ByClassName(WebDriver webDriver, String className) {
		GLog.logRecordTime(0, "[widget]----[div]----[[");
		try {
			WebElement div = webDriver.findElement(By.className(className));
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, div);
			Actions action=new Actions(webDriver);
			action.doubleClick(div).build().perform();
			GLog.logRecordTime(0, "----<div[" + className + "]>被点击了" + GWCtrlMsg.ui_CLICK[0]);
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[div[" + className + "]" + GWCtrlMsg.ui_QUERY[2] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[div]----[widget]");
	}
	
	/**
	 *  Div类目标元素，通过WebElement查找层，确认点击
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param webElement WebElement
	 */
	public static void ByWebElement(WebDriver webDriver, WebElement webElement) {
		GLog.logRecordTime(0, "[widget]----[div]----[[");
		try {
			GWCtrlWait.Wait2BeClickableByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
			Actions action=new Actions(webDriver);
			action.doubleClick(webElement).build().perform();
			GLog.logRecordTime(0, "----<div<webElement[" + webElement + "]>>被点击了");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[div[" + webElement.toString() + "]" + GWCtrlMsg.ui_QUERY[2] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[div]----[widget]");
	}
}
