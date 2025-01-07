package Webdriver;

import Base.GText;
import Base.GTime;
import DT.GLog;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 *  主显示区-提示处理
 *  
 *  @author hew-d
 */
public class GWCtrlAlert {
	
	/**
	 * 支持弹出提示前的操作列表
	 */
	public static String[] PRE_ALERTS = {"SAVE", "MODIFY", "SUBMIT", "CANCELSUBMIT"};
	
	/**
	 * 记录弹出提示前的操作 默认为"save"，即保存操作
	 */
	public static String PRE_ALERT = "SAVE";
	
	/**
	 * 记录弹出提示前的操作 默认为"save"，即保存操作
	 *
	 * @param webDriver 驱动对象
	 * @param tabIndex 页面区域编号 
	 * @param strPreAlert 窗口用途
	 *
	 * @return 成功返回true
	 */
	public static boolean ui_C_ALERT(WebDriver webDriver, int tabIndex, String strPreAlert) {
		GLog.logRecordTime(0, "[widget]----[alert]----[[");
		boolean bRes = false;
		try {
			while (!bRes) {
				GLog.logRecordTime(0, "----<alert[" + strPreAlert + "]>" + GWCtrlMsg.ui_QUERY[0]);
				GWCtrlFrame.ui_C_SWITCN_ELEMENT(webDriver, GWCtrlWebElementIframe.getIframe(tabIndex));
				String cssCheck = GText.getCssSelectorTxt("div", "class", " x-window x-window-plain x-window-dlg");
				//GWCtrlWait.ViewWaitingByCssSelector(3, cssCheck);
				WebElement iframeDiv = webDriver.findElement(By.cssSelector(cssCheck));
		    	if(iframeDiv != null) {
	    			GLog.logRecordTime(0, "----<alert[" + strPreAlert + "]>" + GWCtrlMsg.ui_QUERY[1]);
                    List<WebElement> buttons = iframeDiv.findElements(By.tagName("button"));
                    if (strPreAlert.equals("保存")) {
                        for (WebElement button : buttons) {
                            if (button.getText().equals("是")) {
                                button.click();
                                GLog.logRecordTime(0, "----<alert[" + button.getText() + "]>" + GWCtrlMsg.ui_CLICK[0]);
                                break;
                            }
                        }
                    } else {
                        for (WebElement button : buttons) {
                            if (button.getText().equals("确定")) {
                                button.click();
                                GLog.logRecordTime(0, "----<alert[" + button.getText() + "]>" + GWCtrlMsg.ui_CLICK[0]);
                                break;
                            }
                        }
                    }
                    bRes = true;
                }
			}
        }catch(Exception e) {
        	System.out.println("[" + GTime.getDate() + "]----<exception[alert[" + strPreAlert + "]" + GWCtrlMsg.ui_QUERY[2] + "]");
        	//警告窗口不存在不作为异常处理，暂时注释此项，需要时开启
        	//GWCtrlException.switchTo(e, 1, 0, "----<exception[alert[" + strPreAlert + "]" + GWCtrlMsg.ui_QUERY[2] + "]", true);
        }
		GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
		GLog.logRecordTime(0, "]]----[alert]----[widget]");

		return bRes;
	}

	/**
	 * 浏览器弹窗处理
	 *
	 * @param webDriver 驱动对象
	 * @param tabIndex 页面区域编号
	 * @param processAlert 处理方式
	 *
	 * @return 成功返回true
	 */
	public static boolean ui_C_ALERT_BROWSER(WebDriver webDriver, int tabIndex,String processAlert) {
		GLog.logRecordTime(0, "[widget]----[alert]----[[");
		boolean bRes = false;
		try {
			while (!bRes) {
				Alert javascriptAlert = webDriver.switchTo().alert();
				GLog.logRecordTime(0, "----<alert[" + javascriptAlert.getText() + "]>" + GWCtrlMsg.ui_QUERY[0]);
                if (processAlert.equals("确定")) {
                    javascriptAlert.accept();
                } else {
                    javascriptAlert.dismiss();
                }
                bRes = true;
            }
        }catch(Exception e) {
        	System.out.println("[" + GTime.getDate() + "]----<exception[alert[" + processAlert + "]" + GWCtrlMsg.ui_QUERY[2] + "]");
        	//警告窗口不存在不作为异常处理，暂时注释此项，需要时开启
        	//GWCtrlException.switchTo(e, 1, 0, "----<exception[alert[" + strPreAlert + "]" + GWCtrlMsg.ui_QUERY[2] + "]", true);
        }
		GLog.logRecordTime(0, "]]----[alert]----[widget]");
		
		return bRes;		
	}
}
