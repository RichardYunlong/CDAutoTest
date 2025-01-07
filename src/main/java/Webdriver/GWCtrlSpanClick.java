package Webdriver;

import Base.GText;
import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *  Span控件点击处理
 */
public class GWCtrlSpanClick {
	
	/**
	 *  Span类目标元素，通过span-Text查找按钮并钮点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param str span-Text
	 */
	public static void ByText(WebDriver webDriver, String str) {
		GLog.logRecordTime(0, "[widget]----[span]----[[");
		try {
			GWCtrlHighLight.apply(webDriver, webDriver.findElement(By.xpath("//span[text()=\"" + str + "\"]")), 1, "");
			webDriver.findElement(By.xpath("//span[text()=\"" + str + "\"]")).click();
			GLog.logRecordTime(0, "----<span[" + str + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[span[" + str + "]" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GLog.logRecordTime(0, "]]----[span]----[widget]");
	}
	
	/**
	 *  Span类目标元素，通过cssSelector查找按钮并点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param cssSelector cssSelector
	 */
	public static void ByCssSelector(WebDriver webDriver, String cssSelector) {
		GLog.logRecordTime(0, "[widget]----[span]----[[");
		try {
			GWCtrlHighLight.apply(webDriver, webDriver.findElement(By.cssSelector(cssSelector)), 1, "");
			webDriver.findElement(By.cssSelector(cssSelector)).click();
			GLog.logRecordTime(0, "----<span[" + cssSelector + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[span[" + cssSelector + "]" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GLog.logRecordTime(0, "]]----[span]----[widget]");
	}
	
	/**
	 *  Span类目标元素，通过title查找按钮并点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param str title
	 *  @param tagName 标签名
	 */
	public static void ByCssSelectorByTagName(WebDriver webDriver, String str, String tagName) {
		GLog.logRecordTime(0, "[widget]----[span]----[[");
		try {
			GWCtrlHighLight.apply(webDriver, webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt(tagName, "title", str))), 1, "");
			webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt(tagName, "title", str))).click();
			GLog.logRecordTime(0, "----<span[" + str + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[span[" + str + "]" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GLog.logRecordTime(0, "]]----[span]----[widget]");
	}
	
	/**
	 *  Span类目标元素，通过linkText查找按钮并点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param linkText linkText
	 */
	public static void ByLinkText(WebDriver webDriver, String linkText) {
		GLog.logRecordTime(0, "[widget]----[span]----[[");
		try {
			GWCtrlHighLight.apply(webDriver, webDriver.findElement(By.linkText(linkText)), 1, "");
			webDriver.findElement(By.linkText(linkText)).click();
			GLog.logRecordTime(0, "----<span[" + linkText + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[span[" + linkText + "]" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GLog.logRecordTime(0, "]]----[span]----[widget]");
	}
}
