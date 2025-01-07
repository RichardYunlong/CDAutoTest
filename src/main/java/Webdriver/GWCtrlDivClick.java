package main.java.Webdriver;

import main.java.Base.GText;
import main.java.DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 *  Div控件单击处理
 */
public class GWCtrlDivClick {
	
	/**
	 *  用于点击table中包含button的场景
	 *
	 *  @param webDriver 目标驱动
	 *  @param id id
	 *
	 *  @return 是否点击成功
	 */
	@SuppressWarnings("UnusedReturnValue")
    public static boolean buttonInTableById(WebDriver webDriver, String id) {
	    boolean isclick = false;
		GLog.logRecordTime(0, "[widget]----[tablebutton]----[[");
		try {
			GWCtrlWait.Wait2BeClickableById(webDriver, GTestIndicators.PageShowTime, id);
			WebElement table = webDriver.findElement(By.id(id));
			
			List<WebElement> buttons = table.findElements(By.tagName("button"));
			for(WebElement button:buttons){
				if(button != null) {
					GLog.logRecordTime(0, "----<tablebutton[" + id + "]>state：" + button.isDisplayed());
					GWCtrlHighLight.apply(webDriver, button, 1, "");
					GWCtrlTime.Pause(webDriver, GTestIndicators.ConsumeTime);
					button.click();
					isclick = true;
					GWCtrlTime.Pause(webDriver, GTestIndicators.ConsumeTime);
					GLog.logRecordTime(0, "----<tablebutton<[" + id + "]>" + GWCtrlMsg.ui_CLICK[0]);
					break;
				}
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[button[" + id + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[tablebutton]----[widget]");
		return isclick;
	}
	
	/**
	 *  Div类目标元素，通过ID查找层，确认点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param id id
	 */
	public static void ById(WebDriver webDriver, String id) {
		GLog.logRecordTime(0, "[widget]----[div]----[[");
		try {
			GWCtrlWait.Wait2BeClickableById(webDriver, GTestIndicators.PageShowTime, id);
			WebElement button = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, button, 1, "");
			GLog.logRecordTime(0, "----<div[" + id + "]>state：" + button.isDisplayed());
			Actions action=new Actions(webDriver);
			action.click(button).perform();
			GLog.logRecordTime(0, "----<div[" + id + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[div[" + id + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[div]----[widget]");
	}
	
	/**
	 *  Div类目标元素，通过ID查找层，确认点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param iframeIndex iframe序号
	 *  @param id id
	 */
	public static void ById(WebDriver webDriver, int iframeIndex, String id) {
		GLog.logRecordTime(0, "[widget]----[div]----[[");
		GWCtrlFrame.ui_C_SWITCN_ELEMENT(webDriver, GWCtrlWebElementIframe.getIframe(iframeIndex));
		try {
			GWCtrlWait.Wait2BeClickableById(webDriver, GTestIndicators.PageShowTime, id);
			WebElement button = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, button, 1, "");
			GLog.logRecordTime(0, "----<div[" + id + "]>state：" + button.isDisplayed());
			Actions action=new Actions(webDriver);
			action.click(button).perform();
			GLog.logRecordTime(0, "----<div[" + id + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[div[" + id + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
		GLog.logRecordTime(0, "]]----[div]----[widget]");
	}
	
	/**
	 *  Div类目标元素，通过ID查找层，确认点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param id id
	 */
	public static void ByIdClick(WebDriver webDriver, String id) {
		GLog.logRecordTime(0, "[widget]----[div]----[[");
		try {
			GWCtrlWait.Wait2BeClickableById(webDriver, GTestIndicators.PageShowTime, id);
			WebElement div = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, div, 1, "");
			div.click();
			GLog.logRecordTime(0, "----<div[" + id + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[div[" + id + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[div]----[widget]");
	}
	
	/**
	 *  Div类目标元素，通过Xpath查找层，确认点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param xpath xpath
	 */
	public static void ByXpath(WebDriver webDriver, String xpath) {
		GLog.logRecordTime(0, "[widget]----[div]----[[");
		try {
			GWCtrlWait.Wait2BeClickableByXpath(webDriver, GTestIndicators.PageShowTime, xpath);
			WebElement div = webDriver.findElement(By.xpath(xpath));
			GWCtrlHighLight.apply(webDriver, div, 1, "");
			JavascriptExecutor js=(JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].scrollIntoView(true);",div);
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, div);
			Actions action=new Actions(webDriver);
			action.click(div).perform();
			GLog.logRecordTime(0, "----<button[" + xpath + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[div[" + xpath + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[div]----[widget]");
	}
	
	/**
	 *  Div类目标元素，通过ClassName查找层，确认点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param className className
	 */
	public static void ByClassName(WebDriver webDriver, String className) {
		GLog.logRecordTime(0, "[widget]----[div]----[[");
		try {
			WebElement div = webDriver.findElement(By.className(className));
			GWCtrlHighLight.apply(webDriver, div, 1, "");
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, div);
			Actions action=new Actions(webDriver);
			action.click(div).perform();
			GLog.logRecordTime(0, "----<div[" + className + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[div[" + className + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[div]----[widget]");
	}
	
	/**
	 *  Div类目标元素，通过cssSelector查找层，确认点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param cssSelector cssSelector
	 */
	public static void ByCssSelector(WebDriver webDriver, String cssSelector) {
		GLog.logRecordTime(0, "[widget]----[div]----[[");
		try {
			String divCss;
			if(null != cssSelector && !cssSelector.isEmpty()) {
				divCss = cssSelector;
			}else {
				divCss = GText.getCssSelectorTxt("table", "class", "x-btn g-btn-recommend x-btn-noicon");
			}
			GWCtrlWait.Wait2BeClickableByCssSelector(webDriver, GTestIndicators.PageShowTime, divCss);
			WebElement div = webDriver.findElement(By.cssSelector(divCss));
			GWCtrlHighLight.apply(webDriver, div, 1, "");
			div.click();
			GLog.logRecordTime(0, "----<div[" + cssSelector + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[div[" + cssSelector + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[div]----[widget]");
	}
	
	/**
	 *  Div类目标元素，通过WebElement查找层，确认点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param webElement webElement
	 */
	public static void ByWebElement(WebDriver webDriver, WebElement webElement) {
		GLog.logRecordTime(0, "[widget]----[div]----[[");
		try {
			GWCtrlHighLight.apply(webDriver, webElement, 1, "");
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, webElement);
			Actions action=new Actions(webDriver);
			action.click(webElement).build().perform();
			GLog.logRecordTime(0, "----<div<webElement[" + webElement + "]>>被点击了");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[div[" + webElement.toString() + "]" + GWCtrlMsg.ui_QUERY[2] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[div]----[widget]");
	}
}
