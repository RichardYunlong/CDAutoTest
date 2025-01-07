package Webdriver;

import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 *  判断元素是否可操作状态
 *  一般为可操作和不可操作两种状态
 */
public class GWCtrlOperationStatus {
	
	/**
	 *  判断元素是否可操作状态，仅判断一次
	 *
	 *  @param webDriver 目标驱动
	 *  @param iframeIndex 窗体序号
	 *  @param typeName 元素类型
	 *  @param keywords 元素描述关键词
	 *  @param tarAttributeName 目标属性名称
	 *  @param tarAttributeValue 目标属性值
	 *  @param waitAttributeName 等待属性名称
	 *  @param waitAttributeValue 等待属性值
	 *  @param mTime 等待时间
	 *  
	 *  @return true-可操作；false-不可操作
	 */
	public static boolean ui_C_GET_ONCE(WebDriver webDriver,
										int iframeIndex,
										String typeName,
										String keywords,
										String tarAttributeName,
										String tarAttributeValue,
										String waitAttributeName,
										String waitAttributeValue,
										int mTime){
		GLog.logRecordTime(0, "[status]----[" + typeName + "]----[[");
		
		//返回值预定义为可操作
		boolean bStatus = true;

		//进入目标窗口区域
		GWCtrlFrame.ui_C_SWITCN_ELEMENT(webDriver, GWCtrlWebElementIframe.getIframe(iframeIndex));
    	
    	//限制等待时间 如果传入的时间不大于10s，则等待时间减半
    	if(mTime <= 10) {
    		mTime = mTime / 2;
    		if(mTime <= 3) {
    			mTime = 3;
    		}
    	}
    	
		//等待目标出现
		WebElement tarRoot = GWCtrlQuery.ui_Q(webDriver, tarAttributeName, tarAttributeValue);
    	GWCtrlWait.Wait2BeClickableByWebElement(webDriver, mTime, tarRoot);
    	
		try {
	    	//定时检测
	    	GWCtrlTime.setTimer(mTime);

	    	while(GWCtrlTime.getTimer() != 0) {
	    		switch(keywords) {
		    		case "删_除":case "删除":{
						//判断状态是否为置灰
						String btnStatus = tarRoot.getAttribute("class");
						if(null != btnStatus && !btnStatus.isEmpty()) {
							if(btnStatus.contains("-disabled")) {
								bStatus = false;
								GLog.logRecordTime(0, "----<" + typeName + "[" + keywords + "]>[grey]");
								GWCtrlTime.setTimer(0);
							}else {
								GWCtrlTime.TimerMinus(webDriver);
							}
						}
		    			break;
		    		}
		    		case "提交":{
		    			//判断是否为取消提交
						List<WebElement> buttons = tarRoot.findElements(By.tagName("button"));
						for(WebElement button:buttons){
							if(button.getText().equals("取消提交")) {
								bStatus = false;
								GLog.logRecordTime(0, "----<" + typeName + "[" + keywords + "]>>[cancel commit]");
								GWCtrlTime.setTimer(0);
								break;
							}else {
								GWCtrlTime.TimerMinus(webDriver);
							}
						}
		    			break;
		    		}
		    		case "取消提交":{
		    			//判断是否为取消提交
						List<WebElement> buttons = tarRoot.findElements(By.tagName("button"));
						for(WebElement button:buttons){
							if(button.getText().equals("提交")) {
								bStatus = false;
								GLog.logRecordTime(0, "----<" + typeName + "[" + keywords + "]>[commit]");
								GWCtrlTime.setTimer(0);
								break;
							}else {
								GWCtrlTime.TimerMinus(webDriver);
							}
						}
		    			break;
		    		}
		    		default:{
		    			break;
		    		}
	    		}
	    	}
		}catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[" + typeName + "[" + keywords + "]get state failed]>", true);
		}
    	//返回母版区域
		GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
		//等待完成
		GWCtrlPage.ui_D_IFRAME_INDEX(webDriver, iframeIndex, waitAttributeName, waitAttributeValue);
		
		GLog.logRecordTime(0, "]]----[" + typeName + "]----[status]");
		
		return bStatus;
	}
	
	/**
	 *  循环判断元素是否可操作状态，直到目标不可操作
	 *
	 *  @param webDriver 目标驱动
	 *  @param iframeIndex 窗体序号
	 *  @param typeName 元素类型
	 *  @param keywords 元素描述关键词
	 *  @param tarAttributeName 目标属性名称
	 *  @param tarAttributeValue 目标属性值
	 *  @param waitAttributeName 等待属性名称
	 *  @param waitAttributeValue 等待属性值
	 *  @param mTime 等待时间
	 */
	public static void ui_C_GET_LOOP(WebDriver webDriver,
									 int iframeIndex,
									 String typeName,
									 String keywords,
									 String tarAttributeName,
									 String tarAttributeValue,
									 String waitAttributeName,
									 String waitAttributeValue,
									 int mTime) {
		if(ui_C_GET_ONCE(webDriver, iframeIndex, typeName, keywords, tarAttributeName, tarAttributeValue, waitAttributeName, waitAttributeValue, mTime)) {
			ui_C_GET_ONCE(webDriver, iframeIndex, typeName, keywords, tarAttributeName, tarAttributeValue, waitAttributeName, waitAttributeValue, mTime);
		}
	}
}
