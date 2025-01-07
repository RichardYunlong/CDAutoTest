package main.java.Webdriver;

import main.java.Base.GText;
import main.java.DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *  window切换
 */
public class GWCtrlWindow {
	
	/**
	 *  主窗口句柄 
	 */
	public static String rootHandle = "";
	
	/**
	 *  window切换到最近上一层
	 *
	 *  @param webDriver 目标驱动
	 *
	 *  @return 是否切换成功
	 */
	@SuppressWarnings("UnusedReturnValue")
    public static boolean windowHandlePre(WebDriver webDriver) {
		GLog.logRecordTime(0, "[widget]----[window]----[[");
		boolean bHandle;
		try {
			webDriver.switchTo().window(GWCtrlWindow.rootHandle);
			bHandle = true;
			GParam.windowIndex = 0;
			GLog.logRecordTime(0, "----<window[switch to the last one]>");
		}catch(Exception e) {
			bHandle = false;
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[window[switch failed]]>", true);
		}
		GLog.logRecordTime(0, "]]----[window]----[widget]");
		
		return bHandle;
	}
	
	/**
	 *  两个window相互切换
	 *
	 *  @param webDriver 目标驱动
	 *
	 *  @return 是否切换成功
	 */
	@SuppressWarnings("UnusedReturnValue")
	public static boolean windowHandles(WebDriver webDriver) {
		GLog.logRecordTime(0, "[widget]----[window]----[[");
		boolean bHandle = false;
		try {
			//获取当前窗口句柄
			String handle = webDriver.getWindowHandle();
			//保存切换前窗口句柄
			rootHandle = handle;
			//获取所有窗口句柄
			Set<String> allWindows = webDriver.getWindowHandles();
			
			//循环判断是不是当前句柄
			for(String i:allWindows) {
				if(i.equals(handle)) {
					continue;
				}
				webDriver.switchTo().window(i);
				GLog.logRecordTime(0, "----<window[switch to window NO[" + i + "]]>");
			}
			
			bHandle = true;
			GParam.windowIndex = 1;
		}catch(Exception e) {
            GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[window[switch failed]]>", true);
		}
		GLog.logRecordTime(0, "]]----[window]----[widget]");
        GWCtrlBasic.Maximize(webDriver);
		return bHandle;
	}
	
	/**
	 *  多个window相互切换
	 *
	 *  @param webDriver 目标驱动
	 *  @param index 切换到的window序号
	 *
	 * @return 是否切换成功
	 */
	public static boolean windowsHandles(WebDriver webDriver, int index) {
		GLog.logRecordTime(0, "[widget]----[window]----[[");
		boolean bHandles = false;
		try {
			Set<String> windows = webDriver.getWindowHandles();
			List<String> allWindow = new ArrayList<>(windows);
			webDriver.switchTo().window(allWindow.get(index));
			GLog.logRecordTime(0, "----<window[[switch to window [" + index + "]]>");
			bHandles = true;
		}catch(Exception e) {
            GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[window[switch failed]]>", true);
		}
		GLog.logRecordTime(0, "]]----[window]----[widget]");
		
		return bHandles;
	}
	
	/**
	 *  指定window是否存在
	 *  存在则返回指定window的WebElement对象，不存在则返回null
	 *
	 *  @param webDriver 目标驱动
	 *  @param queryCriteriaName 能够确定window的某条件名称
	 *  @param queryCriteriaValue 能够确定window的某条件取值
	 *  
	 *  @return 指定window的WebElement对象
	 */
	public static WebElement ui_C_GET_WINDOW(WebDriver webDriver, String queryCriteriaName, String queryCriteriaValue) {
		GLog.logRecordTime(0, "[widget]----[window]----[[");
		
		WebElement window;
		window = GWCtrlQuery.ui_Q_V(webDriver, queryCriteriaName, queryCriteriaValue);
		
		GLog.logRecordTime(0, "]]----[window]----[widget]");
		
		return window;
	}
	
	/**点击指定div样式的提示窗
	 *
	 * @param webDriver 目标驱动
	 * @param bOK 是或否或者提示框文本值
	 *
	 * @return 是否点击成功
	 */
	public static boolean ui_C_CLICK_WINDOW_BUTTON(WebDriver webDriver, String bOK) {
	    boolean isClick = false;
  	    //确认窗体定位条件
        String windowVerifyCss = GText.getCssSelectorTxt("div", "class", " x-window x-window-plain x-window-dlg");
  	    //获得确认窗体
        WebElement windowVerify = GWCtrlWindow.ui_C_GET_WINDOW(webDriver, "cssSelector", windowVerifyCss);
        //如果存在确认窗体
        if(windowVerify != null) {
            List<WebElement> buttons = windowVerify.findElements(By.tagName("button"));
            for(WebElement button:buttons){
                if( button.getText().equals(bOK)) {
                    button.click();
                    isClick=true;
                    GLog.logRecordTime(0, "----<verifywindow[" + button.getText() + "]>" + GWCtrlMsg.ui_CLICK[0]);
                    break;
                }
            }
        }else {
            System.out.println("----<windowVerify<[" + GWCtrlMsg.ui_QUERY[2] + "]>");
        }
        return isClick;
    }
	
	/**
	 * 点击指定div样式的“导出设置”提示窗
	 *
	 * @param webDriver 目标驱动
	 * @param bOK 是或否或者提示框文本值
	 *
	 * @return 是否点击成功
	 */
	public static boolean ui_C_CLICK_WINDOW_EXPORT(WebDriver webDriver, String bOK) {
	    boolean isClick = false;
  	    //确认窗体定位条件
        String windowVerifyCss = GText.getCssSelectorTxt("div", "class", " x-window x-window-plain");
  	    //获得确认窗体
        WebElement windowVerify = GWCtrlWindow.ui_C_GET_WINDOW(webDriver, "cssSelector", windowVerifyCss);
        //如果存在确认窗体
        if(windowVerify != null) {
            List<WebElement> buttons = windowVerify.findElements(By.tagName("button"));
            for(WebElement button:buttons){
                if( button.getText().equals(bOK)) {
                    button.click();
                    isClick=true;
                    GLog.logRecordTime(0, "----<verifywindow[" + button.getText() + "]>" + GWCtrlMsg.ui_CLICK[0]);
                    break;
                }
            }
        }else {
            System.out.println("----<windowVerify<[" + GWCtrlMsg.ui_QUERY[2] + "]>");
        }
        return isClick;
    }
}
