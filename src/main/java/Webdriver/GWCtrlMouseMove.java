package Webdriver;

import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *  控制鼠标悬浮
 */
public class GWCtrlMouseMove {

	/**
	 *  控制鼠标悬浮在某元素
	 *
	 *  @param webDriver 全局驱动
	 *  @param eWeb 目标元素
	 */
	public static void ToElement(WebDriver webDriver, WebElement eWeb){
		GLog.logRecordTime(9,  "[widget]----[mouse]----[[");
		try {
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].dispatchEvent(new MouseEvent('mouseover',{'view':window,'bubbles':true}));", eWeb);

			GLog.logRecordTime(9,  "----<mouse[hover over [" + eWeb.toString() + "]]>");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[hover over failed]>", true);
		}
		GLog.logRecordTime(9,  "]]----[mouse]----[widget]");
	}

	/**
	 *  控制鼠标悬浮在某元素
	 *  
	 *  @param webDriver 全局驱动
	 *  @param eWeb 目标元素
	 */
	 public static void ToWebElement(WebDriver webDriver, WebElement eWeb){
		GLog.logRecordTime(9,  "[widget]----[mouse]----[[");
		try {
		    //创建鼠标属性方法
		    Actions action=new Actions(webDriver);
		    // 获取 moveToElement 方法 ，元素定位到想要移上去的元素上 
		    action.moveToElement(eWeb).perform();
		    GLog.logRecordTime(9,  "----<mouse[hover over [" + eWeb.toString() + "]]>");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[hover over failed]>", true);
		}
		GLog.logRecordTime(9,  "]]----[mouse]----[widget]");
	 }
	 
	/**
	 *  控制鼠标悬浮在某元素
	 *  
	 *  @param webDriver 全局驱动
	 *  @param eCss 目标元素css
	 */
	 public static void ToCssSelector(WebDriver webDriver, String eCss){
		GLog.logRecordTime(9,  "[widget]----[mouse]----[[");
		try {
		    //创建鼠标属性方法
		    Actions action=new Actions(webDriver);
		    GWCtrlHighLight.apply(webDriver, webDriver.findElement(By.cssSelector(eCss)), 1, "");
		    // 获取 moveToElement 方法 ，元素定位到想要移上去的元素上 
		    action.moveToElement(webDriver.findElement(By.cssSelector(eCss))).perform();
		    GLog.logRecordTime(9,  "----<mouse[hover over [" + eCss + "]]>");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[hover over failed]>", true);
		}
		GLog.logRecordTime(9,  "]]----[mouse]----[widget]");
	 }
	 
	/**
	 *  控制鼠标悬浮在某元素
	 *  
	 *  @param webDriver 全局驱动
	 *  @param linkText 目标元素文本
	 */
	 public static void ToLinkText(WebDriver webDriver, String linkText){
		GLog.logRecordTime(9,  "[widget]----[mouse]----[[");
		try {
		    //创建鼠标属性方法
		    Actions action=new Actions(webDriver);
		    // 获取 moveToElement 方法 ，元素定位到想要移上去的元素上 
		    action.moveToElement(webDriver.findElement(By.linkText(linkText))).perform();
		    GLog.logRecordTime(9,  "----<mouse[hover over [" + linkText + "]]>");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[hover over failed]>", true);
		}
		GLog.logRecordTime(9,  "]]----[mouse]----[widget]");
	 }
	 
	/**
	 *  控制鼠标悬浮在某元素
	 *  
	 *  @param webDriver 全局驱动
	 *  @param id 目标元素id
	 */
	 public static void ToId(WebDriver webDriver, String id){
		GLog.logRecordTime(9,  "[widget]----[mouse]----[[");
		try {
		    //创建鼠标属性方法
		    Actions action=new Actions(webDriver);
		    // 获取 moveToElement 方法 ，元素定位到想要移上去的元素上 
		    action.moveToElement(webDriver.findElement(By.id(id))).perform();
		    GLog.logRecordTime(9,  "----<mouse[hover over [" + id + "]]>");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[hover over failed]>", true);
		}
		GLog.logRecordTime(9,  "]]----[mouse]----[widget]");
	 }
}
