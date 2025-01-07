package Webdriver;

import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 *  搜索框控件
 */
public class GWCtrlSearch {

	/**
	 * 按照文本进行搜索
	 *
	 * @param webDriver 目标驱动
	 * @param id 兄弟节点的table元素的id
	 * @param str 要写入的值
	 */
	public static void ByValue(WebDriver webDriver, String id, String str) {
		GLog.logRecordTime(0, "[widget]----[search]----[[");
		try {
			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, id);
			//根据id找到选中值显示框的元素
			WebElement tableld = webDriver.findElement(By.id(id));
			WebElement inputParent = tableld.findElement(By.xpath("./.."));
			List<WebElement> inputDivs = inputParent.findElements(By.tagName("div"));
			if(inputDivs != null) {
				for (WebElement inputDiv : inputDivs) {
					if(inputDiv != null) {
					    //目前不做处理，选择第一个input标签进行写入
					    WebElement input = inputDiv.findElement(By.tagName("input"));
					    GWCtrlInputFill.ByWebElement(webDriver, input, str);
					    GWCtrlPage.ui_C_SELECT_INPUT_BTN(webDriver, input);
						break;
					}
				}
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[search[" + id + "];str[" + str + "] failed]", true);
		}
		
		GLog.logRecordTime(0, "]]----[search]----[widget]");
	}
	
	/**
	 * 按照文本进行搜索
	 *
	 * @param webDriver 目标驱动
	 * @param inputTag input元素tagName 适用于页面上只有一个input元素时
	 * @param str 要写入的值
	 */
	public static void ByWebElement(WebDriver webDriver, String inputTag,String str) {
		GLog.logRecordTime(0, "[widget]----[search]----[[");
		try {
			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "btn_actOK");
			//根据id找到选中值显示框的元素
			WebElement input = webDriver.findElement(By.tagName(inputTag));
			GWCtrlInputFill.ByWebElementUnClear(webDriver, input, str);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[search[" + inputTag + "];str[" + str + "] failed]", true);
		}
		
		GLog.logRecordTime(0, "]]----[search]----[widget]");
	}
}
