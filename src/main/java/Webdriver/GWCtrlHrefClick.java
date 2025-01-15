package Webdriver;

import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 *  Href控件点击处理
 */
public class GWCtrlHrefClick {
	
	/**
	 *  href类目标元素，通过linkText查找按钮并点击
	 *  
	 *  @param webDriver 目标驱动
	 *  @param linkText 目标值
	 */
	public static void ByLinkText(WebDriver webDriver, String linkText) {
		GLog.logRecordTime(9,  "[widget]----[linkText]----[[");
		try {
			WebElement href = webDriver.findElement(By.linkText(linkText));
			GWCtrlHighLight.apply(webDriver, href, 1, "");
			href.click();
			GLog.logRecordTime(9,  "----<linkText[" + linkText + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[linkText[" + linkText + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(9,  "]]----[linkText]----[widget]");
	}
	
    /**
     * 点击符合目标值的元素集合的指定元素
	 *
	 * @param webDriver 目标驱动
     * @param linkText 目标值
     * @param i 查出元素的集合
     */
    public static void ByLinkText(WebDriver webDriver, String linkText , int i) {
        GLog.logRecordTime(9,  "[widget]----[linkText]----[[");
        try {
            List<WebElement> hrefs = webDriver.findElements(By.linkText(linkText));
            GWCtrlHighLight.apply(webDriver, hrefs.get(i), 1, "");
            hrefs.get(i).click();
            GLog.logRecordTime(9,  "----<linkText[" + linkText + "]>" + GWCtrlMsg.ui_CLICK[0]);
        }catch (Exception e) {
            GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[linkText[" + linkText + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
        }
        GLog.logRecordTime(9,  "]]----[linkText]----[widget]");
    }
	

	
	/**
	 *  href类目标元素，通过href点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param href 目标值
	 */
	public static void ClickHref(WebDriver webDriver, String href) {
		GLog.logRecordTime(9,  "[widget]----[href]----[[");
		try {
	        WebElement link = webDriver.findElement(By.linkText("请登录"));
	        GWCtrlHighLight.apply(webDriver, link, 1, "");
	        link.click();
	        GLog.logRecordTime(9,  "----<href[" + href + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[href[" + href + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(9,  "]]----[href]----[widget]");
	}
}
