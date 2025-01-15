package Webdriver;

import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 *  Button控件点击处理
 */
public class GWCtrlButtonClick {
	
	/**
	 *  submitButton类目标元素，通过Id查找提交按钮并点击
	 *
	 * 	@param webDriver 目标驱动
	 *  @param id id的值
	 */
	public static void ById(WebDriver webDriver, String id) {
		GLog.logRecordTime(9,  "[widget]----[button]----[[");
		try {
			WebElement submitButton = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, submitButton, 1, "");
			submitButton.submit();
			GLog.logRecordTime(9,  "----<button[" + id + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[button[" + id + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(9,  "]]----[button]----[widget]");
	}
	
	/**
	 *  submitButton类目标元素，通过xpath查找提交按钮并点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param xpath xpath的值
	 */
	public static void ByXpath(WebDriver webDriver, String xpath) {
		GLog.logRecordTime(9,  "[widget]----[button]----[[");
		try {
			WebElement submitButton = webDriver.findElement(By.xpath(xpath));
			GWCtrlHighLight.apply(webDriver, submitButton, 1,"");
			submitButton.submit();
			GLog.logRecordTime(9,  "----<input<submit<xpath[" + xpath + "]>>>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[button[" + xpath + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(9,  "]]----[button]----[widget]");
	}
	
	/**
	 *  submitButton类目标元素，通过className查找提交按钮并点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param className className的值
	 */
	public static void ByClassName(WebDriver webDriver, String className) {
		GLog.logRecordTime(9,  "[widget]----[button]----[[");
		try {
			WebElement searchButton = webDriver.findElement(By.className(className));
			GWCtrlHighLight.apply(webDriver, searchButton, 1,"");
			searchButton.submit();
			GLog.logRecordTime(9,  "----<input<submit<className[" + className + "]>>>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[button[" + className + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(9,  "]]----[button]----[widget]");
	}
	
	/**
	 *  button类目标元素，通过Id查找按钮并点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param id id的值
	 */
	public static void ByIdClick(WebDriver webDriver, String id) {
		GLog.logRecordTime(9,  "[widget]----[button]----[[");
		try {
			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, id);
			WebElement button = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, button, 1,"");
			button.click();
			GLog.logRecordTime(9,  "----<button<id[" + id + "]>>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[button[" + id + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(9,  "]]----[button]----[widget]");
	}
	
	/**
	 *  button类目标元素，通过xpath查找到按钮并点击提交
	 *
	 *  @param webDriver 目标驱动
	 *  @param xpath xpath的值
	 */
	public static void ByXpathClick(WebDriver webDriver, String xpath) {
		GLog.logRecordTime(9,  "[widget]----[button]----[[");
		try {
			WebElement button = webDriver.findElement(By.xpath(xpath));
			GWCtrlHighLight.apply(webDriver, button, 1,"");
			button.click();
			GLog.logRecordTime(9,  "----<button<xpath[" + xpath + "]>>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[button[" + xpath + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(9,  "]]----[button]----[widget]");
	}
	
	/**
	 *  button类目标元素，通过tagName查找按钮并点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param tagName tagName的值
	 */
	public static void ByTagName(WebDriver webDriver, String tagName) {
		GLog.logRecordTime(9,  "[widget]----[button]----[[");
		try {
			WebElement button = webDriver.findElement(By.tagName(tagName));
			GWCtrlHighLight.apply(webDriver, button, 1,"");
			button.click();
			GLog.logRecordTime(9,  "----<button<tagName[" + tagName + "]>>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[button[" + tagName + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(9,  "]]----[button]----[widget]");
	}
	
	/**
	 *  button类目标元素，通过linkText查找提交按钮并点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param linkText linkText的值
	 */
	public static void ByLinkText(WebDriver webDriver, String linkText) {
		GLog.logRecordTime(9,  "[widget]----[button]----[[");
		try {
			WebElement button = webDriver.findElement(By.linkText(linkText));
			GWCtrlHighLight.apply(webDriver, button, 1,"");
			button.click();
			GLog.logRecordTime(9,  "----<button<linkText[" + linkText + "]>>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[button[" + linkText + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(9,  "]]----[button]----[widget]");
	}
	
	/**
	 *  button类目标元素，通过Attribute名称和值查找按钮并点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param attributeName 属性名称
	 *  @param attributeValue 属性值
	 */
	public static void ByLinkTextAndTagName(WebDriver webDriver, String attributeName, String attributeValue) {
		GLog.logRecordTime(9,  "[widget]----[button]----[[");
		try {
			List<WebElement> buttons = webDriver.findElements(By.tagName("button"));
	        for (WebElement webElement : buttons) {
	            if (webElement.getAttribute(attributeName).equals(attributeValue)) {
	            	GWCtrlHighLight.apply(webDriver, webElement, 1,"");
	            	webElement.click();
	            	GLog.logRecordTime(9,  "----<button<attributeName[" + attributeName + "];attributeValue[" + attributeValue + "]>>" + GWCtrlMsg.ui_CLICK[0]);
	            }
	        }
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[button[attributeName[" + attributeName + "];attributeValue[" + attributeValue + "]]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(9,  "]]----[button]----[widget]");
	}
}
