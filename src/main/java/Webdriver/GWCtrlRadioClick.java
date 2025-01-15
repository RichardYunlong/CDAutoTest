package Webdriver;

import Base.GText;
import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 *  单选选择功能
 */
public class GWCtrlRadioClick {
	
	/**
	 *  选中radio
	 *
	 *  @param webDriver 目标驱动
	 *  @param id 目标radio的id
	 */
	public static void ById(WebDriver webDriver, String id) {
		GLog.logRecordTime(9,  "[widget]----[radio]----[[");
		try {
			WebElement radio = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, radio, 1, "");
			if(!radio.isSelected()) {
				radio.click();
				GLog.logRecordTime(9,  "----<radio[" + id + "]>" + GWCtrlMsg.ui_SELECT[0]);
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[radio[" + id + "] select failed]", true);
		}
		
		GLog.logRecordTime(9,  "]]----[radio]----[widget]");
	}
	
	/**
	 *  取消选中radio
	 *
	 *  @param webDriver 目标驱动
	 *  @param id 目标radio的id
	 */
	public static void ById2Cancel(WebDriver webDriver, String id) {
		GLog.logRecordTime(9,  "[widget]----[radio]----[[");
		try {
			WebElement radio = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, radio, 1, "");
			if(radio.isSelected()) {
				radio.click();
				GLog.logRecordTime(9,  "----<radio[" + id + "]>" + GWCtrlMsg.ui_SELECT[1]);
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[radio[" + id + "] cancel select failed]", true);
		}
		
		GLog.logRecordTime(9,  "]]----[radio]----[widget]");
	}
	
	/**
	 *  通过查询条件值选中radio，适用于查询模块设置radio类型查询条件
	 *
	 *  @param webDriver 目标驱动
	 *  @param value 目标查询条件值
	 */
	public static void ByValue(WebDriver webDriver, String value) {
		GLog.logRecordTime(9,  "[widget]----[radio]----[[");
		try {
			List<WebElement> radios = webDriver.findElements(By.cssSelector(GText.getCssSelectorTxt("input", "type", "radio")));
			if(radios != null) {
				for(WebElement radio:radios) {
					//通过radio的父查找同级label，对比label的text
					WebElement radioParent = radio.findElement(By.xpath(".."));
					WebElement label = radioParent.findElement(By.tagName("label"));
					if(label.getText().equals(value)) {
						radio.click();
						GLog.logRecordTime(9,  "----<radio[" + value + "]>" + GWCtrlMsg.ui_SELECT[0]);
						break;
					}
				}
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[radio[" + value + "] select failed]", true);
		}
		
		GLog.logRecordTime(9,  "]]----[radio]----[widget]");
	}
}
