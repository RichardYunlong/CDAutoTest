package Webdriver;

import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *  元素等待功能
 */
public class GWCtrlWait {
	
	/**
	 *  隐式等待，对webdriver对象进行等待时间设置，有效范围为该webdriver对象整个生命周期，一旦设置不便于灵活控制，所以不推荐使用
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 */
	public static void Waiting(WebDriver webDriver, int mtime) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		try {
			webDriver.manage().timeouts().implicitlyWait(mtime, TimeUnit.SECONDS);
			GLog.logRecordTime(0, "----<wait<implicitlyWait  time[" + mtime + "]>>");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load timeout, it should be less than " + GTestIndicators.PageShowTime + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待等待，需要提供指定元素的查询条件，包括【属性名称】和【属性值】，例如：以【id】方式查询，查询的【id值】为“main-content”
	 *  等待时间受全局参数影响，目前为【GTestIndicators.PageShowTime】，请注意该值得改动情况
	 *
	 *  @param webDriver 目标驱动
	 *  @param waitByType 等待目标的条件类型 例如“id”，意为通过id来等待
	 *  @param waitByTar 等待目标的条件值  例如“main-content”，意为等待的目标元素id值为“main-content”
	 */
	public static void WaitingAll(WebDriver webDriver, String waitByType, String waitByTar) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait<waitByType[" + waitByType + "];waitByTar[" + waitByTar + "]>>loading");
			switch(waitByType) {
                case "cssSelector":{
					ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, waitByTar);
					break;
				}
				case "xpath":{
					ViewWaitingAllByXpath(webDriver, GTestIndicators.PageShowTime, waitByTar);
					break;
				}
                case "id":
                default:{
					ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, waitByTar);
					break;
				}
			}
			GLog.logRecordTime(0, "----<wait<waitByType[" + waitByType + "];waitByTar[" + waitByTar + "]>>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + waitByType + "][" + waitByTar + "]>>failed, it should be less than " + GTestIndicators.PageShowTime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标Id的某个元素出现，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param id 目标id
	 */
	public static void ViewWaitingById(WebDriver webDriver, int mtime, String id) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + id + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
			GLog.logRecordTime(0, "----<wait[" + id + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + id + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标Id的某一组元素出现，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param id 目标id
	 */
	public static void ViewWaitingAllById(WebDriver webDriver, int mtime, String id) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + id + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(id)));
			GLog.logRecordTime(0, "----<wait[" + id + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + id + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标Xpath的某个元素出现，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param xpath 目标xpath
	 */
	public static void ViewWaitingByXpath(WebDriver webDriver, int mtime, String xpath) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + xpath + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			GLog.logRecordTime(0, "----<wait[" + xpath + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + xpath + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标Xpath的某一组元素出现，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param xpath 目标xpath
	 */
	public static void ViewWaitingAllByXpath(WebDriver webDriver, int mtime, String xpath) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + xpath + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
			GLog.logRecordTime(0, "----<wait[" + xpath + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + xpath + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标linkText的某个元素出现，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param linkText 目标文本
	 */
	public static void ViewWaitingByLinkText(WebDriver webDriver, int mtime, String linkText) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + linkText + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
			GLog.logRecordTime(0, "----<wait[" + linkText + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + linkText + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标linkText的某一组元素出现，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param linkText 目标文本
	 */
	public static void ViewWaitingAllByLinkText(WebDriver webDriver, int mtime, String linkText) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + linkText + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(linkText)));
			GLog.logRecordTime(0, "----<wait[" + linkText + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + linkText + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标cssSelector的某个元素出现，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param cssSelector 目标cssSelector
	 */
	public static void ViewWaitingByCssSelector(WebDriver webDriver, int mtime, String cssSelector) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + cssSelector + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
			GLog.logRecordTime(0, "----<wait[" + cssSelector + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + cssSelector + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标cssSelector的某一组元素出现，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param cssSelector 目标cssSelector
	 */
	public static void ViewWaitingAllByCssSelector(WebDriver webDriver, int mtime, String cssSelector) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + cssSelector + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(cssSelector)));
			GLog.logRecordTime(0, "----<wait[" + cssSelector + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + cssSelector + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标className的某个元素出现，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param className 目标className
	 */
	public static void ViewWaitingByClassName(WebDriver webDriver, int mtime, String className) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + className + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className(className)));
			GLog.logRecordTime(0, "----<wait[" + className + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + className + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标className的某一组元素出现，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param className 目标className
	 */
	public static void ViewWaitingAllByClassName(WebDriver webDriver, int mtime, String className) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + className + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(className)));
			GLog.logRecordTime(0, "----<wait[" + className + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + className + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 某个元素出现，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param webElement 目标webElement
	 */
	public static void ViewWaitingByWebElement(WebDriver webDriver, int mtime, WebElement webElement) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + webElement.toString() + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.visibilityOf(webElement));
			GLog.logRecordTime(0, "----<wait[" + webElement + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + webElement.toString() + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 某一组元素出现，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param webElement 目标webElement
	 */
	public static void ViewWaitingAllByWebElement(WebDriver webDriver, int mtime, WebElement webElement) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + webElement.toString() + "]>" + mtime + "s");
			ArrayList<WebElement> elements = new ArrayList<>();
			elements.add(webElement);
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.visibilityOfAllElements(elements));
			GLog.logRecordTime(0, "----<wait[" + webElement + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + webElement.toString() + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标Id的某个元素中出现指定字符串，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param id 目标id
	 *  @param text 目标字符串
	 */
	public static void ViewWaitingTextById(WebDriver webDriver, int mtime, String id, String text) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + id + "][" + text + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id(id), text));
			GLog.logRecordTime(0, "----<wait[" + id + "][" + text + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + id + "][" + text + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标Id的某个元素已经可被点击，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param id 目标id
	 */
	public static void Wait2BeClickableById(WebDriver webDriver, int mtime, String id) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + id + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
			GLog.logRecordTime(0, "----<wait[" + id + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + id + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标xpath的某个元素中出现指定字符串，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param xpath 目标xpath
	 *  @param text 目标字符串
	 */
	public static void ViewWaitingTextByXpath(WebDriver webDriver, int mtime, String xpath, String text) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + xpath + "][" + text + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpath), text));
			GLog.logRecordTime(0, "----<wait[" + xpath + "][" + text + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + xpath + "][" + text + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标Id的某个元素已经可被点击，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param xpath 目标xpath
	 */
	public static void Wait2BeClickableByXpath(WebDriver webDriver, int mtime, String xpath) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + xpath + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			GLog.logRecordTime(0, "----<wait[" + xpath + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + xpath + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标cssSelector的某个元素中出现指定字符串，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param cssSelector 目标cssSelector
	 *  @param text 目标字符串
	 */
	public static void ViewWaitingTextByCssSelector(WebDriver webDriver, int mtime, String cssSelector, String text) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + cssSelector + "][" + text + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(cssSelector), text));
			GLog.logRecordTime(0, "----<wait[" + cssSelector + "][" + text + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + cssSelector + "][" + text + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标cssSelector的某个元素已经可被点击，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param cssSelector 目标cssSelector
	 */
	public static void Wait2BeClickableByCssSelector(WebDriver webDriver, int mtime, String cssSelector) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + cssSelector + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
			GLog.logRecordTime(0, "----<wait[" + cssSelector + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + cssSelector + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标webElement的某个元素中出现指定字符串，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime  等待时间
	 *  @param webElement 目标webElement
	 *  @param text 目标字符串
	 */
	public static void ViewWaitingTextByWebElement(WebDriver webDriver, int mtime, WebElement webElement, String text) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + webElement.toString() + "][" + text + "]>" + mtime + "s");
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.textToBePresentInElement(webElement, text));
			GLog.logRecordTime(0, "----<wait[" + webElement + "][" + text + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + webElement.toString() + "][" + text + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
	
	/**
	 *  显式等待 符合目标webElement的某个元素已经可被点击，单位为秒(s)
	 *
	 *  @param webDriver 目标驱动
	 *  @param mtime 等待时间
	 *  @param webElement 目标webElement
	 */
	public static void Wait2BeClickableByWebElement(WebDriver webDriver, int mtime, WebElement webElement) {
		GLog.logRecordTime(0, "[function]----[wait]----[[");
		
		try {
			GLog.logRecordTime(0, "----<wait[" + webElement.toString() + "]>" + mtime + "s");
	
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].click();", webElement);
			
			WebDriverWait wait = new WebDriverWait(webDriver, mtime);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			GLog.logRecordTime(0, "----<wait[" + webElement + "]>complete");
		} catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[load [" + webElement.toString() + "] failed, it should be less than " + mtime + "s]>", true);
		}
		
		GLog.logRecordTime(0, "]]----[wait]----[function]");
	}
}
