package Webdriver;

import DT.GLog;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  高亮等强调类提示
 */
public class GWCtrlHighLight {
	
	/**
	 *  是否开启强调的开关
	 *  1-仅开启边框
	 *  2-仅开启高亮
	 *  3-同时开启边框和高亮
	 *  其他-关闭强调效果
	 */
	public static final int bHighLight = 1;
	
	/**
	 *  边框宽度
	 */
	public static final String strBorder = "3";
	
	/**
	 *  高亮背景色
	 */
	public static final String strColor = "yellow";

	/**
	 *  通过制定元素的WebElement对象来添加边框
	 *  
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param msg 提示语 为空时不弹出提示窗
	 */
	private static void byWebElementBorder(WebDriver webDriver, WebElement webElement, String msg) {
		
		//绘制边框，黄底红边
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("element = arguments[0];" +
             "original_style = element.getAttribute('style');" +
             "element.setAttribute('style', original_style + \";" +
             "border: " + strBorder + "px solid red;\");" +
             "setTimeout(function(){element.setAttribute('style', original_style);}, " + GTestIndicators.DragonShowTime +");", webElement);
		
        if(msg != null && !msg.isEmpty()){
        	//绘制弹窗
            GLog.logRecordTime(9,  "[提示]----" + msg);
        } 
	}
	
	/**
	 *  通过制定元素的WebElement对象来添加高亮背景
	 *  
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param msg 提示语 为空时不弹出提示窗
	 */
	private static void byWebElementBackground(WebDriver webDriver, WebElement webElement, String msg) {
		
		//绘制边框，黄底红边
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("element = arguments[0];" +
             "original_style = element.getAttribute('style');" +
             "element.setAttribute('style', original_style + \";" +
             "background: " + strColor + ";\");" +
             "setTimeout(function(){element.setAttribute('style', original_style);}, " + GTestIndicators.DragonShowTime +");", webElement);
		
        if(msg != null && !msg.isEmpty()){
        	//绘制弹窗
            GLog.logRecordTime(9,  "[提示]----" + msg);
        } 
	}
	
	/**
	 *  通过制定元素的WebElement对象来添加高亮背景加边框
	 *  
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param msg 提示语 为空时不弹出提示窗
	 */
	private static void byWebElement(WebDriver webDriver, WebElement webElement, String msg) {
		
		//绘制边框，黄底红边
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("element = arguments[0];" +
             "original_style = element.getAttribute('style');" +
             "element.setAttribute('style', original_style + \";" +
             "background: " + strColor + "; border: " + strBorder + "px solid red;\");" +
             "setTimeout(function(){element.setAttribute('style', original_style);}, " + GTestIndicators.DragonShowTime +");", webElement);
		
        if(msg != null && !msg.isEmpty()){
        	//绘制弹窗
            GLog.logRecordTime(9,  "[提示]----" + msg);
        } 
	}
	
	/**
	 *  通过制定元素的WebElement对象来添加高亮背景加边框
	 *  
	 *  @param webDriver 全局驱动
	 *  @param webElement 目标元素
	 *  @param type 1-仅开启边框；2-仅开启高亮；3-同时开启边框和高亮;其他-关闭强调效果
	 *  @param msg 提示语 为空时不弹出提示窗
	 */
	@SuppressWarnings({"DataFlowIssue", "UnreachableCode"})
    public static void apply(WebDriver webDriver, WebElement webElement, int type, String msg) {
		switch(bHighLight) {
			case 1:{
				byWebElementBorder(webDriver, webElement, msg);
				break;
			}
			case 2:{
				byWebElementBackground(webDriver, webElement, msg);
				break;
			}
			case 3:{
				byWebElement(webDriver, webElement, msg);
				break;
			}
			default:{
				break;
			}
		}
	}
}
