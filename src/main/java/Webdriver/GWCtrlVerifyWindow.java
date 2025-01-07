package main.java.Webdriver;

import main.java.Base.GText;
import main.java.DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GWCtrlVerifyWindow {
	
	/**
	 *  点击某定区域内某Id元素
	 *
	 *  @param webDriver 目标驱动
	 *  @param iframeIndex 窗体序号
	 *  @param keywords 指定元素关键词
	 *  @param tarAttributeName 目标元素属性名
	 *  @param tarAttributeValue 等待元素属性值
	 *  @param bOK 是或否
	 *  @param waitAttributeName 等待元素属性名
	 *  @param waitAttributeValue 等待元素属性值
	 *  @param mTime 等待时间
	 */
	public static void ui_C_CLICK_VERIFY(
			WebDriver webDriver,
			int iframeIndex, 
			String keywords, 
			String tarAttributeName, 
			String tarAttributeValue, 
			boolean bOK, 
			String waitAttributeName, 
			String waitAttributeValue,
			int mTime){
		GLog.logRecordTime(0, "[widget]----[verifywindow]----[[");
		
		//由母版区域进入目标区域
		GWCtrlFrame.ui_C_SWITCN_ELEMENT(webDriver, GWCtrlWebElementIframe.getIframe(iframeIndex));
		
    	//限制等待时间 如果传入的时间不大于10s，则等待时间减半
    	if(mTime <= 10) {
    		mTime = mTime / 2;
    		if(mTime <= 3) {
    			mTime = 3;
    		}
    	}

    	WebElement callVerify = GWCtrlQuery.ui_Q(webDriver, tarAttributeName, tarAttributeValue);
    	GWCtrlWait.Wait2BeClickableByWebElement(webDriver, mTime, callVerify);

		try {
	    	if(keywords.equals("删除") || keywords.equals("删_除")) {
	    		//如果目标是删除按钮，需要等待一定的时间才能点击，此问题为前端缺陷
	    		GWCtrlTime.Pause(webDriver, GTestIndicators.SpecialShowTime);
	    	}
	    	//点击目标
	    	GWCtrlDivClick.ByWebElement(webDriver, callVerify);
	    	
			//确认窗体的WebElement对象
			WebElement windowVerify = null;
	    	String windowVerifyCss = GText.getCssSelectorTxt("div", "class", " x-window x-window-plain x-window-dlg");
	    	windowVerify = GWCtrlWindow.ui_C_GET_WINDOW(webDriver, "cssSelector", windowVerifyCss);
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, mTime, windowVerify);
			
	    	GLog.logRecordTime(0, "----<verifywindow[" + windowVerify.getText() + "]>>" + GWCtrlMsg.ui_CLICK[0]);

	    	//如果存在确认窗体
            List<WebElement> buttons = windowVerify.findElements(By.tagName("button"));
            for(WebElement button:buttons){
                if(bOK && button.getText().equals("是")) {
                    button.click();
                    GLog.logRecordTime(0, "----<verifywindow[" + button.getText() + "]>" + GWCtrlMsg.ui_CLICK[0]);
                    break;
                }
                if(!bOK && button.getText().equals("否")) {
                    button.click();
                    GLog.logRecordTime(0, "----<verifywindow[" + button.getText() + "]>" + GWCtrlMsg.ui_CLICK[0]);
                    break;
                }
                if(bOK && button.getText().equals("确定")) {
                    button.click();
                    GLog.logRecordTime(0, "----<verifywindow[" + button.getText() + "]>" + GWCtrlMsg.ui_CLICK[0]);
                    break;
}
            }
            //返回母版区域
			GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
			//检查按钮状态
	    	if(keywords.equals("删_除")||keywords.equals("删除")) {//如果删除按钮不是【置灰】格式，就一直等待到置灰
	    		GWCtrlOperationStatus.ui_C_GET_LOOP(webDriver, iframeIndex, waitAttributeName, keywords, tarAttributeName, tarAttributeValue, waitAttributeName, waitAttributeValue, mTime);
	    	}
			
			//等待完成
			if(keywords.equals("删除")) {
				if(!waitAttributeValue.isEmpty()) {
					if(waitAttributeValue.equals(GWCtrlWebElementId.CN_ID.get("单据列表")) || waitAttributeValue.equals(GWCtrlWebElementId.CN_ID.get("字典列表"))) {
						GWCtrlPage.ui_D_IFRAME_INDEX(webDriver, 1, "id", waitAttributeValue);
					}else {
						GWCtrlPage.ui_D_IFRAME_INDEX(webDriver, 2, "id", waitAttributeValue);
					}
				}else {
					GWCtrlPage.ui_D_IFRAME_INDEX(webDriver, 1, "id", GWCtrlWebElementId.CN_ID.get("单据列表"));
				}	
			}else {
				GWCtrlPage.ui_D_IFRAME_INDEX(webDriver, iframeIndex, waitAttributeName, waitAttributeValue);
			}
		}catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[verifywindow[" + waitAttributeValue + "]]>" + GWCtrlMsg.ui_CLICK[1], true);
		}
		GLog.logRecordTime(0, "]]----[verifywindow]----[widget]");
	}

	/**
	 *  确认窗口处理
	 *
	 *  @param webDriver 目标驱动
	 *  @param bYes 为[true]时点击“确定”或者“是”，为[false]时点击“取消”或者“否”
	 */
	public static void ui_C_CLICK(WebDriver webDriver, boolean bYes) {
		GLog.logRecordTime(0, "[widget]----[verifywindow]----[[");
		String CCS = GText.getCssSelectorTxt("div", "class", " x-window x-window-plain x-window-dlg");
    	GWCtrlWait.ViewWaitingByCssSelector(webDriver, GTestIndicators.PageShowTime, CCS);
    	WebElement divRoot = webDriver.findElement(By.cssSelector(CCS));
    	
    	if(divRoot != null) {
    		List<WebElement> buttons = divRoot.findElements(By.tagName("button"));
    		for(WebElement button:buttons){
    			if(bYes && button.getText().equals("是")) {
    				button.click();
    				break;
    			}
    			if(bYes && button.getText().equals("确定")) {
    				button.click();
    				break;
    			}
    			if(!bYes && button.getText().equals("否")) {
    				
    				button.click();
    				break;
    			}
    			if(!bYes && button.getText().equals("取消")) {
    				button.click();
    				break;
    			}
    			GLog.logRecordTime(0, "----<verifywindow[" + bYes + "]>" + GWCtrlMsg.ui_CLICK[0]);
    		}
    	}
    	GLog.logRecordTime(0, "]]----[verifywindow]");
	}
}
