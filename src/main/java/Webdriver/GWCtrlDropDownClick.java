package Webdriver;

import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 *  枚举选择功能
 */
public class GWCtrlDropDownClick {
	
	/**
	 *  处理下拉菜单：按照元素序号选中
	 *
	 *  @param webDriver 目标驱动
	 *  @param dropdownId 元素ID
	 *  @param Index 序号
	 */
	public static void ByIndex(WebDriver webDriver, String dropdownId, int Index) {
		GLog.logRecordTime(0, "[widget]----[dropdown]----[[");
		try {
			//1、根据元素定位找到select这个标签
			Select sel = new Select(webDriver.findElement(By.id(dropdownId)));
			//2、getOptions()方法获得的是一个WebElement的集合
			List<WebElement> webElements = sel.getOptions();
	
			//3、新建一个List，用来存储每个选项的文本值
			@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
			List<String> downs = new ArrayList<>();
	
			//4、for-each循环每个选项        
			for (WebElement webElement : webElements) {
				GLog.logRecordTime(0, "----<dropdown[" + webElement.getText() + "]>" + GWCtrlMsg.ui_QUERY[1]);
			        //5、将每个选项的文本值添加到List集合
			    downs.add(webElement.getText()); 
			}
			
			//6、获取下拉选项的数量        
			int num = webElements.size();
			//7、根据序号选择
			if(Index >= 0 && Index < num) {
				sel.selectByIndex(Index);
				GLog.logRecordTime(0, "----<dropdown[" + Index + "]>" + GWCtrlMsg.ui_CLICK[0]);
			}
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[dropdown[" + dropdownId + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[dropdown]----[widget]");
	}
	
	/**
	 *  处理下拉菜单：按照元素文字值选中
	 *
	 *  @param webDriver 目标驱动
	 *  @param dropdownId 元素ID
	 *  @param strValue 元素文字值
	 */
	public static void ByValue(WebDriver webDriver, String dropdownId,String strValue) {
		GLog.logRecordTime(0, "[widget]----[dropdown]----[[");
		try {
			//1、根据元素定位找到select这个标签
			Select sel = new Select(webDriver.findElement(By.id(dropdownId)));
			//2、getOptions()方法获得的是一个WebElement的集合
			List<WebElement> webElements = sel.getOptions();
	
			//3、新建一个List，用来存储每个选项的文本值
			@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
			List<String> downs = new ArrayList<>();
	
			//4、for-each循环每个选项        
			for (WebElement webElement : webElements) {
			    GLog.logRecordTime(0, "----<dropdown[" + webElement.getText() + "]>" + GWCtrlMsg.ui_QUERY[1]);
			    //5、将每个选项的文本值添加到List集合
			    downs.add(webElement.getText()); 
			    if(webElement.getText().equals(strValue)) {
			    	//6、根据值选择
			    	sel.selectByValue(strValue);
			    	GLog.logRecordTime(0, "----<dropdown[" + strValue + "]>" + GWCtrlMsg.ui_CLICK[0]);
			    }
			}
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[dropdown[" + dropdownId + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[dropdown]----[widget]");
	}
	
	/**
	 *  处理下拉菜单
	 *  用于处理不适用getOptions()方法的不规则下拉框
	 *
	 *  @param webDriver 目标驱动
	 *  @param dropdownId 元素ID
	 *  @param strValue 元素文字值
	 *
	 *  @return 是否点击成功
	 */
	public static Boolean BySpanText(WebDriver webDriver, String dropdownId,String strValue) {
	    boolean isClick = false;
		GLog.logRecordTime(0, "[widget]----[dropdown]----[[");
		try {
			WebElement xMenu = webDriver.findElement(By.id(dropdownId));
			
			if(xMenu != null){
				List<WebElement> xMenuItems = xMenu.findElements(By.tagName("span"));
				for (WebElement xMenuItem : xMenuItems) {
				    GLog.logRecordTime(0, "----<dropdown[" + xMenuItem.getText() + "]>" + GWCtrlMsg.ui_QUERY[1]); 
				    if(xMenuItem.getText().equals(strValue)) {
				    	xMenuItem.click();
				    	isClick = true;
				    	GLog.logRecordTime(0, "----<dropdown[" + strValue + "]>" + GWCtrlMsg.ui_CLICK[0]);
				    	break;
				    }
				}
			}
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[dropdown[" + dropdownId + "]" + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[dropdown]----[widget]");
		return isClick;
	}
}
