package main.java.Webdriver;

import main.java.DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  Div控件单击处理
 */
public class GWCtrlDivDate {
	
	public static String divDate ="";
	
	/**
	 * 查找div上的内容
	 *
	 * @param webDriver 目标驱动
	 * @param id id
	 * @return divDate
	 */
	public static String ById(WebDriver webDriver, String id) {
		GLog.logRecordTime(0, "[widget]----[date]----[[");
		try {
			WebElement div = webDriver.findElement(By.id(id));
			divDate = div.getAttribute("innerHTML");
			GLog.logRecordTime(0, "----<div<id[" + id + "]>>" + GWCtrlMsg.ui_QUERY[1]);
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[date[" + id + "]" + GWCtrlMsg.ui_QUERY[2] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[date]----[widget]");
		
		return divDate;
	}
	
	/**
	 * 查找div上的内容
	 *
	 * @param webDriver 目标驱动
	 * @param xpath xpath
	 * @return divDate
	 */
	public static String ByXpath(WebDriver webDriver, String xpath) {
		GLog.logRecordTime(0, "[widget]----[date]----[[");
		try {
			WebElement div = webDriver.findElement(By.xpath(xpath));
			divDate = div.getAttribute("innerHTML");
			GLog.logRecordTime(0, "----<div<xpath[" + xpath + "]>>" + GWCtrlMsg.ui_QUERY[1]);
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[date[" + xpath + "]" + GWCtrlMsg.ui_QUERY[2] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[date]----[widget]");
		
		return divDate;	
	}
	
	/**
	 * 查找div上的内容
	 *
	 * @param webDriver 目标驱动
	 * @param className className
	 * @return divDate
	 */
	public static String ByClassName(WebDriver webDriver, String className) {
		GLog.logRecordTime(0, "[widget]----[date]----[[");
		try {
			WebElement div = webDriver.findElement(By.className(className));
			divDate = div.getAttribute("innerHTML");
			GLog.logRecordTime(0, "----<div<className[" + className + "]>>" + GWCtrlMsg.ui_QUERY[1]);
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[date[" + className + "]" + GWCtrlMsg.ui_QUERY[2] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[date]----[widget]");
		
		return divDate;
	}
}
