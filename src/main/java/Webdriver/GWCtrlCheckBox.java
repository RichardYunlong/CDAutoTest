package Webdriver;

import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GWCtrlCheckBox {
	
	/**
	 *  勾选单选框
	 *
	 *  @param webDriver 目标驱动
	 *  @param id 元素id
	 *  @param bChecked 是否选中
	 */
	public static void ById(WebDriver webDriver, String id, String bChecked) {
		GLog.logRecordTime(0, "[widget]----[checkbox]----[[");
		try {
			WebElement checkbox = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, checkbox, 1,"");
			if(bChecked.equals("true")) {
				if(!checkbox.isSelected()) {
					checkbox.click();
					GLog.logRecordTime(0, "----<checkbox[" + id + "]>selected");
				}
			}else {
				if(checkbox.isSelected()) {
					checkbox.click();
					GLog.logRecordTime(0, "----<checkbox[" + id + "]>selected");
				}
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[checkbox[" + id + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[checkbox]----[widget]");
	}
	
	/**
	 *  勾选单选框
	 *
	 *  @param webDriver 目标驱动
	 *  @param id 元素id
	 */
	public static void ById(WebDriver webDriver, String id) {
		GLog.logRecordTime(0, "[widget]----[checkbox]----[[");
		try {
			WebElement checkbox = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, checkbox, 1,"");
			if(!checkbox.isSelected()) {
				checkbox.click();
				GLog.logRecordTime(0, "----<checkbox[" + id + "]>selected");
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[checkbox[" + id + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[checkbox]----[widget]");
	}
	
	/**
	 *  取消勾选单选框
	 *
	 *  @param webDriver 目标驱动
	 *  @param id 元素id
	 */
	public static void ById2Cancel(WebDriver webDriver, String id) {
		GLog.logRecordTime(0, "[widget]----[checkbox]----[[");
		try {
			WebElement checkbox = webDriver.findElement(By.id(id));
			GWCtrlHighLight.apply(webDriver, checkbox, 1,"");
			if(checkbox.isSelected()) {
				checkbox.click();
				GLog.logRecordTime(0, "----<checkbox[" + id + "]>cancel selected");
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[checkbox[" + id + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[checkbox]----[widget]");
	}
}
