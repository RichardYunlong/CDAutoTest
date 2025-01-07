package main.java.Webdriver;

import main.java.DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

public class GWCtrlStaticFind {
	
    /**
    * 根据详细层级元素查询目标元素 全遍历，速度慢，慎用，目前仅支持按照目标的getText()值查找
    * 
    * @param webDriver 全局驱动
    * @param divId 基准div的id，不为空时以该值为查询条件
    * @param divXpath 基准div的xpath，divId为空时以该值为查询条件
    * @param tarTagName 目标元素标签类型
    * @param tarKeyword 目标元素关键词
	 *
	 * @return 符合条件的目标元素
    */
	public static WebElement getWebElementByIdOrXpath(WebDriver webDriver, String divId, String divXpath, String tarTagName, String tarKeyword){
		GLog.logRecordTime(0, "[widget]----[" + tarTagName + "]----[[");
		WebElement res = null;
		WebElement divRoot;
		try {
			if(divId == null || divId.isEmpty()) {
				if(divXpath == null || divXpath.isEmpty()) {
					GLog.logRecordTime(0, "----<" + tarTagName + ">id or xpath must not be empty at the same time");
					return null;
				}
			}

			if(!Objects.requireNonNull(divId).isEmpty()) {
				divRoot = webDriver.findElement(By.id(divId));
			}else{
				divRoot = webDriver.findElement(By.xpath(divXpath));
			}

			List<WebElement> buttons = divRoot.findElements(By.tagName(tarTagName));
			for(WebElement button:buttons){
				if(button.getText().equals(tarKeyword)) {
					res = button;
					GLog.logRecordTime(0, "----<" + tarTagName + "[" + res + "]>>>" + GWCtrlMsg.ui_QUERY[1]);
					break;
				}
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[tarTagName[" + tarTagName + "];tarKeyword[" + tarKeyword + "]" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GLog.logRecordTime(0, "]]----[" + tarTagName + "]----[widget]");
		
		return res;
	}
	
    /**
    * 根据详细层级元素查询目标元素 全遍历，速度慢，慎用
    * 
    * @param webDriver 全局驱动
    * @param divId 基准元素的id，不为空时以该值为查询条件
    * @param divXpath 基准元素的xpath，divId为空时以该值为查询条件
    * @param tarTagName 目标元素标签类型
    * @param tarPropertyName 属性名
    * @param tarPropertyValue 属性值
    * @param tarText 显示文本
	 *
	 * @return 符合条件的目标元素
    */
	public static WebElement getWebElementByIdOrXpath(WebDriver webDriver,
													  String divId,
													  String divXpath,
													  String tarTagName,
													  String tarPropertyName,
													  String tarPropertyValue,
													  String tarText){
		GLog.logRecordTime(0, "[widget]----[" + tarTagName + "]----[[");
		WebElement res = null;
		WebElement divRoot;
		try {
			if(divId == null || divId.isEmpty()) {
				if(divXpath == null || divXpath.isEmpty()) {
					GLog.logRecordTime(0, "----<div>id or xpath must not be empty at the same time");
					return null;
				}
			}
			
			if(!Objects.requireNonNull(divId).isEmpty()) {
				divRoot = webDriver.findElement(By.id(divId));
			}else{
				divRoot = webDriver.findElement(By.xpath(divXpath));
			}
			
			List<WebElement> tars = divRoot.findElements(By.tagName(tarTagName));
			
			for(WebElement tar:tars){
	
				if(!tarPropertyName.isEmpty() && !tarPropertyValue.isEmpty()) {
					if(tar.getAttribute(tarPropertyName).equals(tarPropertyValue)) {
						res = tar;
						GLog.logRecordTime(0, "----<" + tarTagName + "<attributeName[" + tarPropertyName + "];attributeValue[" + tarPropertyValue + "]>>" + GWCtrlMsg.ui_QUERY[1]);
						break;
					}
				}else {
					if(!tarText.isEmpty()) {
						if(tarText.contains("_")) {
							if(tar.getText().contains(tarText)) {
								res = tar;
								GLog.logRecordTime(0, "----<" + tarTagName + "[" + tar.getText() + "]>" + GWCtrlMsg.ui_QUERY[1]);
								break;
							}		
						}else {
							if(tar.getText().equals(tarText)) {
								res = tar;
								GLog.logRecordTime(0, "----<" + tarTagName + "[" + tar.getText() + "]>" + GWCtrlMsg.ui_QUERY[1]);
								break;
							}
						}
					}
				}
	
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[tarPropertyName[" + tarPropertyName + "];tarPropertyValue[" + tarPropertyValue + "];tabText[" + tarText + "]" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GLog.logRecordTime(0, "]]----[" + tarTagName + "]----[widget]");
		
		return res;
	}
	
    /**
    * 根据基准元素查询目标元素 全遍历，速度慢，慎用
	 *
    * @param webDriver 全局驱动
    * @param divRoot 基准元素
    * @param tarTagName 目标元素标签类型
    * @param tarPropertyName 属性名
    * @param tarPropertyValue 属性值
    * @param tarText 显示文本
	 *
	 * @return 符合条件的目标元素
    */
	public static WebElement getWebElementByWebElement(WebDriver webDriver,
													   WebElement divRoot,
													   String tarTagName,
													   String tarPropertyName,
													   String tarPropertyValue,
													   String tarText){
		GLog.logRecordTime(0, "[widget]----[" + tarTagName + "]----[[");
		WebElement res = null;
		try {
			List<WebElement> tars = divRoot.findElements(By.tagName(tarTagName));
			
			for(WebElement tar:tars){
	
				if(!tarPropertyName.isEmpty() && !tarPropertyValue.isEmpty()) {
					if(tar.getAttribute(tarPropertyName).equals(tarPropertyValue)) {
						res = tar;
						GLog.logRecordTime(0, "----<" + tarTagName + "<attributeName[" + tarPropertyName + "];attributeValue[" + tarPropertyValue + "]>>被找到了");
						break;
					}
				}else {
					if(!tarText.isEmpty()) {
						if(tar.getText().equals(tarText)) {
							res = tar;
							GLog.logRecordTime(0, "----<" + tarTagName + "<tabText[" + tar.getText() + "]>>" + GWCtrlMsg.ui_QUERY[1]);
							break;
						}
					}
				}
	
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[tarPropertyName[" + tarPropertyName + "];tarPropertyValue[" + tarPropertyValue + "];tabText[" + tarText + "]" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		GLog.logRecordTime(0, "]]----[" + tarTagName + "]----[widget]");
		return res;
	}
}
