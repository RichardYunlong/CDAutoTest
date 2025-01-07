package Webdriver;

import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  Input控件点击处理
 */
public class GWCtrlInputClick {
	
	/**
	 *  input类目标元素，通过id查找输入框，确认点击
	 *
	 *  @param webDriver 目标驱动
	 *  @param id 目标id
	 */
	public static void ById(WebDriver webDriver, String id) {
		GLog.logRecordTime(0, "[widget]----[input]----[[");
		try {
			WebElement input = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, input, 1, "");
			input.click();
			GLog.logRecordTime(0, "----<input[" + id + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[input[" + id + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[input]----[widget]");
	}
}
