package Webdriver;

import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.NoSuchElementException;

/**
 *  Textarea类型输入处理
 */
public class GWCtrlTextareaFill {
	
	/**
	 *  textarea类目标元素，通过ID查找文本框，写入指定内容
	 *
	 *  @param webDriver 目标驱动
	 *  @param id 目标元素ID
	 *  @param str 写入内容
	 */
	public static void ById(WebDriver webDriver, String id, String str) {
		GLog.logRecordTime(0, "[widget]----[textarea]----[[");
		try {
			WebElement textarea = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, textarea, 1, "");
			textarea.clear();
			GLog.logRecordTime(0, "----<textarea[" + id + "]>" + GWCtrlMsg.ui_INPUT[2]);
			textarea.sendKeys(str);
			GLog.logRecordTime(0, "----<textarea[" + str + "]>" + GWCtrlMsg.ui_INPUT[1]);
	    }catch (NoSuchElementException e){
	    	GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[textarea[" + id + "];str[" + str + "]>", true);
	    }
		GLog.logRecordTime(0, "]]----[textarea]----[widget]");
	}
}
