package Webdriver;

import Base.GText;
import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Objects;

/**
 *  页面处理
 */
public class GWCtrlPage {
	
	/**
	 *  根据url获得当前版式
	 *
	 * @param webDriver 全局驱动
	 *
	 * @return 当前版式
	 */
	public static String ui_D_GET_LAYOUT(WebDriver webDriver) {
		GLog.logRecordTime(0, "[data]----[layout]----[[");
		String curLayout = "layoutE";
		
		String url = GWCtrlBasic.Geturl(webDriver);
		int sum = url.indexOf("Frame/");
        String layoutTemp = url.substring(sum+6,sum+13);
        if(!layoutTemp.isEmpty()) {
        	//全部转小写，方便做判断
        	curLayout = layoutTemp.toLowerCase();
        	
        }
        GLog.logRecordTime(0, "----<layout[" + curLayout + "]>" + GWCtrlMsg.ui_QUERY[1]);
        GLog.logRecordTime(0, "]]----[layout]----[data]");
        return curLayout;
	}
	
	/**
	 *  按照页签序号等待指定元素，需要提供指定元素的查询条件，包括【属性名称】和【属性值】，例如：以【id】方式查询，查询的【id值】为“main-content”
	 *
	 *  @param webDriver 目标驱动
	 *  @param tabIndex 页签序号 
	 *  @param waitByType 等待目标的条件类型 例如“id”，意为通过id来等待
	 *  @param waitByTar 等待目标的条件值  例如“main-content”，意为等待的目标元素id值为“main-content”
	 */
	public static void ui_D_IFRAME_INDEX(WebDriver webDriver, int tabIndex, String waitByType, String waitByTar){
		GLog.logRecordTime(0, "[widget]----[iframe]----[[");
		try {
			if(GWCtrlWebElementIframe.getIframe(tabIndex) != null) {
				GWCtrlFrame.ui_C_SWITCN_ELEMENT(webDriver, GWCtrlWebElementIframe.getIframe(tabIndex));
				GLog.logRecordTime(0, "----<iframe[" + tabIndex + "]>" + GWCtrlMsg.ui_QUERY[1]);
				GWCtrlWait.WaitingAll(webDriver, waitByType, waitByTar);
				GLog.logRecordTime(0, "----<iframe<waitByType[" + waitByType + "];waitByTar[" + waitByTar + "]>>" + GWCtrlMsg.ui_QUERY[1]);	
			}
			GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
		}catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<iframe<waitByType[" + waitByType + "];waitByTar[" + waitByTar + "]>>" + GWCtrlMsg.ui_QUERY[2], true);
		}
		GLog.logRecordTime(0, "]]----[iframe]----[widget]");
	}
	
    /**
    * 根据中部页签的文本名称定位页签并点击
    * 
    * @param webDriver 全局驱动
    * @param iframeId 基准id，不为空时以该值为查询条件
    * @param iframeXpath 基准xpath，基准Id为空时以该值为查询条件
    * @param tagName 目标元素标签类型
    * @param tabPropertyName 目标元素属性名称
    * @param tabPropertyValue 目标元素属性值
    * @param tabText 目标元素显示文本
    */
	public static void ui_C_CLICK_TAB(WebDriver webDriver, String iframeId, String iframeXpath, String tagName, String tabPropertyName, String tabPropertyValue, String tabText) {
		GLog.logRecordTime(0, "[widget]----[tab]----[[");
		WebElement iframe;
		
		try {
			if(!iframeId.isEmpty()) {
				GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, iframeId);
				iframe = webDriver.findElement(By.id(iframeId));
			}else{
				GWCtrlWait.ViewWaitingAllByXpath(webDriver, GTestIndicators.PageShowTime, iframeXpath);
				iframe = webDriver.findElement(By.xpath(iframeXpath));
			}
			
			GWCtrlFrame.ui_C_SWITCN_ELEMENT(webDriver, iframe);

			WebElement tab = GWCtrlStaticFind.getWebElementByIdOrXpath(webDriver, iframeId, "/html/body", tagName, tabPropertyName, tabPropertyValue, tabText);
			
			if(tab != null) {
				tab.click();
				GLog.logRecordTime(0, "----<tab[" + tabText + "]>" + GWCtrlMsg.ui_CLICK[0]);
			}else{
				GLog.logRecordTime(0, "----<tab[" + tabText + "]>" + GWCtrlMsg.ui_QUERY[2]);
			}

        	//游标切换会主窗体
			GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
		}catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[tab[" + tabText + "] " + GWCtrlMsg.ui_CLICK[1] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[tab]----[widget]");
	}
	
    /**
    * 根据同级id或xpath，点击相关联的按钮
    * 
    * @param webDriver 全局驱动
    * @param rootId 基准id，不为空时以该值为查询条件
    * @param rootXpath 基准xpath，基准Id为空时以该值为查询条件
    * @param tabName 目标元素标签类型
    */
	public static void ui_C_CLICK_INPUT_BTN(WebDriver webDriver, String rootId, String rootXpath,String tabName) {
		GLog.logRecordTime(0, "[widget]----[inputbutton]----[[");
		GLog.logRecordTime(0, "----<inputbutton[" + rootId + "]>被当做目标");
		
		WebElement tab;

		try {
			if(!rootId.isEmpty()) {
				tab = webDriver.findElement(By.id(rootId));
			}else{
				tab = webDriver.findElement(By.xpath(rootXpath));
			}
				
			if(tab != null) {
				//如果找到，获得父级节点
				WebElement tabParent = tab.findElement(By.xpath("./.."));
				//再从上步得到的父节点中查找子节点
				List<WebElement> tars = tabParent.findElements(By.tagName(tabName));
				for(WebElement tar:tars){
					if(tar != null) {
						tar.click();
						GLog.logRecordTime(0, "----<inputbutton[" + rootId + "]>" + GWCtrlMsg.ui_CLICK[0]);
						break;
					}
				}	
			}else{
				GLog.logRecordTime(0, "----<inputbutton[" + rootId + "]>" + GWCtrlMsg.ui_QUERY[1]);
			}
		}catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[inputbutton[" + rootId + "]" + GWCtrlMsg.ui_QUERY[2] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[inputbutton]----[widget]");
	}
	
    /**
    * 根据同级id或xpath，点击相关联的按钮
    *
	 * @param webDriver 全局驱动
    * @param rootId 基准id，不为空时以该值为查询条件
    * @param rootXpath 基准xpath，基准Id为空时以该值为查询条件
    */
	public static void ui_C_SELECT_INPUT_BTN(WebDriver webDriver, String rootId, String rootXpath) {
		ui_C_CLICK_INPUT_BTN(webDriver, rootId, rootXpath, "span");
	}
	
	/**
	 * 根据元素点击关联的按钮
	 *
	 * @param webDriver 全局驱动
	 * @param input input元素
	 */
	public static void ui_C_SELECT_INPUT_BTN(WebDriver webDriver, WebElement input) {
  	  WebElement inputParent;
  	  try {
  	    //根据元素找到选中值显示框的元素
  	    inputParent = input.findElement(By.xpath(".."));
  	    List<WebElement> inputSpans = inputParent.findElements(By.tagName("span"));
  	    if(inputSpans != null) {
  	        for (WebElement inputSpan : inputSpans) {
  	            if(inputSpan != null) {
  	                inputSpan.click();
  	                GLog.logRecordTime(0, "----<webElement[" + inputSpan + "]>>" + GWCtrlMsg.ui_CLICK[0]);
  	                break;
  	              }
  	          }
  	      }
        } catch (Exception e) {
          GWCtrlException.throwException(webDriver, "点击关联按钮失败！");
        }
	}
	
    /**
    * 根据同级id或xpath，点击相关联的按钮
	 *
    * @param webDriver 全局驱动
    * @param iframeIndex 目标iframe序号
    * @param rootId 基准id，不为空时以该值为查询条件
    * @param rootXpath 基准xpath，基准Id为空时以该值为查询条件
    */
	public static void ui_C_SELECT_INPUT_BTN(WebDriver webDriver, int iframeIndex, String rootId, String rootXpath) {
		GWCtrlFrame.ui_C_SWITCN_ELEMENT(webDriver, GWCtrlWebElementIframe.getIframe(iframeIndex));
		ui_C_CLICK_INPUT_BTN(webDriver, rootId, rootXpath, "span");
		GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
	}
	
    /**
    * 根据同级id或xpath，点击相关联的按钮
    *
	 * @param webDriver 全局驱动
    * @param iframeIndex 目标iframe序号
    * @param rootId 基准id，不为空时以该值为查询条件
    * @param rootXpath 基准xpath，基准Id为空时以该值为查询条件
    * @param isOpenChildWindow 是否唤起二级窗体
    */
	public static void ui_C_SELECT_INPUT_BTN(WebDriver webDriver, int iframeIndex, String rootId, String rootXpath, boolean isOpenChildWindow) {
		GWCtrlFrame.ui_C_SWITCN_ELEMENT(webDriver, GWCtrlWebElementIframe.getIframe(iframeIndex));
		ui_C_CLICK_INPUT_BTN(webDriver, rootId, rootXpath, "span");
		GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
		
		if(isOpenChildWindow) {
			GWCtrlWindow.windowHandles(webDriver);
		}
	}
	
    /**
    * 根据目标字符串选定日期
    *
	 * @param webDriver 全局驱动
	 * @param date 目标日期
    */
	public static void ui_C_SELECT_DATE_STR(WebDriver webDriver, String date) {
		GLog.logRecordTime(0, "[widget]----[date]----[[");
		String dateDivRootSelector = GText.getCssSelectorTxt("div", "class", "x-menu x-menu-floating x-layer x-date-menu x-menu-plain");
		GLog.logRecordTime(0, "----<date[" + dateDivRootSelector + "]>" + GWCtrlMsg.ui_QUERY[0]);
		
		try {
			WebElement dateDivRoot = webDriver.findElement(By.cssSelector(dateDivRootSelector));
			if(dateDivRoot != null) {
				List<WebElement> dateButtons = dateDivRoot.findElements(By.tagName("button"));
	    		for(WebElement dateButton:dateButtons){
	    			if(dateButton.getText().equals(date)) {
	    				dateButton.click();
	    				GLog.logRecordTime(0, "----<date[" + date + "]>" + GWCtrlMsg.ui_CLICK[0]);
	    				return;
	    			}
	    		}
	    		List<WebElement> dateSpans = dateDivRoot.findElements(By.tagName("span"));
	    		for(WebElement dateSpan:dateSpans){
	    			if(dateSpan.getText().equals(date)) {
	    				dateSpan.click();
	    				GLog.logRecordTime(0, "----<date[" + date + "]>" + GWCtrlMsg.ui_CLICK[0]);
	    				return;
	    			}
	    		}
			}else {
				GLog.logRecordTime(0, "----<date[" + date + "]>" + GWCtrlMsg.ui_QUERY[2]);
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[choose date failed]>", true);
		}
		GLog.logRecordTime(0, "]]----[date]----[widget]");
	}
	
    /**
    * 选定日期
	 *
	 * @param webDriver 全局驱动
	 * @param date 目标日期
    */
	public static void ui_C_SELECT_DATE(WebDriver webDriver, String date) {
		GLog.logRecordTime(0, "[widget]----[date]----[[");
		try {
			for(int i = 10;i <= 20;i++) {
				String strDateDiv = "/html/body/div[" + i + "]";
				WebElement webDateDiv = GWCtrlStaticFind.getWebElementByIdOrXpath(webDriver, "", strDateDiv, "button", date);
					if(webDateDiv != null) {
						webDateDiv.click();
						GLog.logRecordTime(0, "----<date[" + webDateDiv + "]>" + GWCtrlMsg.ui_CLICK[0]);
						break;
					}
			}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[choose date failed]>", true);
		}
		GLog.logRecordTime(0, "]]----[date]----[widget]");
	}
	
    /**
    * 选定乙方
	 *
	 * @param webDriver 全局驱动
    * @param cooTable 单位列表
    * @param cooTar 目标单位
    */
	public static void ui_C_SELECT_COOPRATION(WebDriver webDriver, String cooTable, String cooTar) {
		GLog.logRecordTime(0, "[widget]----[table]----[[");
		try {
			GWCtrlWait.ViewWaitingTextByXpath(webDriver, GTestIndicators.PageShowTime, cooTable, cooTar);
			WebElement coo = GWCtrlStaticFind.getWebElementByIdOrXpath(
					webDriver, 
					"", 
					cooTable, 
					"div", 
					"", 
					"", 
					cooTar);
			GWCtrlWait.Wait2BeClickableByWebElement(webDriver, GTestIndicators.PageShowTime, coo);
			Objects.requireNonNull(coo).click();
			GLog.logRecordTime(0, "----<table[" + cooTable + "];cooTar[" + cooTar + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[table[" + cooTable + "];cooTar[" + cooTar + "]" + GWCtrlMsg.ui_QUERY[2] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[table]----[widget]");
	}
	
	/**
	 *  根据条件字符串搜索【数据源】中某目标
	 *
	 * @param webDriver 全局驱动
	 * @param cooName 单位名称
	 *
	 * @return 目标元素
	 */
	public static boolean ui_C_SEARCH_COO(WebDriver webDriver, String cooName){
	    boolean isClick = false;
		GLog.logRecordTime(0, "[widget]----[table]----[[");
		WebElement layoutPanel1 = webDriver.findElement(By.id("layoutPanel1"));
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, layoutPanel1);
		if(layoutPanel1 != null) {
			try {
				List<WebElement> colCodes = layoutPanel1.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-panel-tbar x-panel-tbar-noheader x-panel-tbar-noborder")));
				if(colCodes != null) {
					for(WebElement colCode:colCodes){
						List<WebElement> inputs = colCode.findElements(By.tagName("input"));
						for(WebElement input:inputs){
							if(input.getAttribute("type").equals("text")) {
								GWCtrlInputFill.ByWebElementUnClear(webDriver, input, cooName);
								GWCtrlWait.ViewWaitingTextByWebElement(webDriver, GTestIndicators.PageShowTime, layoutPanel1, cooName);
								WebElement coo;
								if(ui_C_FIND_WEBELEMENT_EXIST(webDriver,GText.getCssSelectorTxt("div", "class", "x-grid3-cell-inner x-grid3-col-0 x-unselectable"))) {
									//适用于无勾选框场景
									coo = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-grid3-cell-inner x-grid3-col-0 x-unselectable")));
								}else {
									//适用于有勾选框场景
									coo = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "x-grid3-cell-inner x-grid3-col-1 x-unselectable")));
								}
								if (coo.getText().equals(cooName)) {
  								    coo.click();
  								    isClick = true;
                                    GLog.logRecordTime(0, "----<table<div[" + cooName + "]>>" + GWCtrlMsg.ui_CLICK[0]);
                                    break; 
                                }
								List<WebElement> spans = coo.findElements(By.tagName("span"));
								for(WebElement span:spans){
									if(span.getText().equals(cooName)) {
										coo.click();
										isClick = true;
										GLog.logRecordTime(0, "----<table<span[" + cooName + "]>>" + GWCtrlMsg.ui_CLICK[0]);
										break;
									}
								}
							}
							break;
						}
						break;
					}
				}else {
					GLog.logRecordTime(0, "----<table[" + cooName + "]>" + GWCtrlMsg.ui_QUERY[2]);
				}
				
			} catch (Exception e) {
				GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[table[" + cooName + "]" + GWCtrlMsg.ui_QUERY[2] + "]>", true);
			}
		}
		GLog.logRecordTime(0, "]]----[table]----[widget]");
		return isClick;
	}
	
    /**
    * 选定甲方
	 *
	 * @param webDriver	全局驱动
    * @param jiafangSearch 单位列表搜索区
    * @param jiafangTable 单位列表
    * @param jiafangTar 目标单位名称
    */
	public static void ui_C_SELECT_JIAFANG(WebDriver webDriver, String jiafangSearch, String jiafangTable, String jiafangTar) {
		GLog.logRecordTime(0, "[widget]----[table]----[[");
		try {
			GWCtrlWait.ViewWaitingAllByXpath(webDriver, GTestIndicators.PageShowTime, jiafangSearch);
			//搜索
			WebElement search = GWCtrlStaticFind.getWebElementByIdOrXpath(
					webDriver, 
					"", 
					jiafangSearch, 
					"input", 
					"type", 
					"text", 
					"");
			GWCtrlWait.Wait2BeClickableByWebElement(webDriver, GTestIndicators.PageShowTime, search);
			Objects.requireNonNull(search).sendKeys(jiafangTar);
			GLog.logRecordTime(0, "----<table<input[" + jiafangTar + "]>>" + GWCtrlMsg.ui_INPUT[1]);
			WebElement searchBtnJiaFang = GWCtrlStaticFind.getWebElementByIdOrXpath(
					webDriver, 
					"", 
					jiafangSearch, 
					"img", 
					"src", 
					"data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==", 
					"");
			GWCtrlWait.Wait2BeClickableByWebElement(webDriver, GTestIndicators.PageShowTime, searchBtnJiaFang);
			Objects.requireNonNull(searchBtnJiaFang).click();
			GLog.logRecordTime(0, "----<table<src[" + searchBtnJiaFang + "]>>" + GWCtrlMsg.ui_CLICK[0]);
			GWCtrlWait.ViewWaitingTextByXpath(webDriver, GTestIndicators.PageShowTime, jiafangTable, jiafangTar);
			GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "class", "x-grid3-cell-inner x-grid3-col-0 x-unselectable"));
			WebElement searchTar = GWCtrlStaticFind.getWebElementByIdOrXpath(
					webDriver, 
					"", 
					jiafangTable, 
					"span", 
					"", 
					"", 
					jiafangTar);
			GWCtrlWait.Wait2BeClickableByWebElement(webDriver, GTestIndicators.PageShowTime, searchTar);
			Objects.requireNonNull(searchTar).click();
			GLog.logRecordTime(0, "----<table<span[" + searchTar + "]>>" + GWCtrlMsg.ui_CLICK[0]);
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[table[" + jiafangTable + "];tar[" + jiafangTar + "]" + GWCtrlMsg.ui_QUERY[2] + "]>", true);
		}
		GLog.logRecordTime(0, "]]----[table]----[widget]");
	}
	
	/**
	 *  点击当前区域内某Id元素
	 *
	 *  @param webDriver 全局驱动
	 *  @param eId 指定元素id
	 *  @param sName 区分标记，用于判断是哪个操作唤起了确认窗
	 *  @param bOK 是或否
	 *  @param wId 等待元素Id
	 */
	public static void ui_C_CLICK_VERIFY(WebDriver webDriver, String eId, String sName, boolean bOK, String wId){
		GLog.logRecordTime(0, "[widget]----[verifywindow]----[[");

		//辅助标记
		//确认窗体的WebElement对象
		WebElement windowVerify;
		//确认窗体定位条件
		String windowVerifyCss = GText.getCssSelectorTxt("div", "class", " x-window x-window-plain x-window-dlg");
		try {
			//等待目标出现
	    	GWCtrlWait.Wait2BeClickableById(webDriver, GTestIndicators.PageShowTime, eId);
	    	if(sName.equals("删除") || sName.equals("删_除")) {
	    		//如果目标是删除按钮，需要等待一定的时间才能点击，此问题为前端缺陷
	    		GWCtrlTime.Pause(webDriver, GTestIndicators.SpecialShowTime);
	    	}
	    	//点击目标
	    	GWCtrlDivClick.ById(webDriver, eId);
	    	GLog.logRecordTime(0, "----<verifywindow[" + eId + "]>>" + GWCtrlMsg.ui_CLICK[0]);
	    	//获得确认窗体
	    	windowVerify = GWCtrlWindow.ui_C_GET_WINDOW(webDriver, "cssSelector", windowVerifyCss);
	    	GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, windowVerify);
	    	//如果存在确认窗体
	    	if(windowVerify != null) {
	    		GLog.logRecordTime(0, windowVerify.getText());
        		List<WebElement> buttons = windowVerify.findElements(By.tagName("button"));
        		for(WebElement button:buttons){
        			if(bOK && button.getText().equals("是")) {
        				button.click();
        				GLog.logRecordTime(0, "----<verifywindow[" + button.getText() + "]>" + GWCtrlMsg.ui_CLICK[0]);
        				if(sName.equals("删除") && eId.equals("btnDeleteYS")) {//如果点击的是【施工合同登记-预算书-删除】
        					List<WebElement> buttonsS2 = windowVerify.findElements(By.tagName("button"));
        					GLog.logRecordTime(0, windowVerify.getText() + GWCtrlMsg.ui_CLICK[0]);
        					for(WebElement buttonS2:buttonsS2){
        						if(button.getText().equals("否")) {
        							buttonS2.click();
        							GLog.logRecordTime(0, "----<verifywindow[" + button.getText() + "]>" + GWCtrlMsg.ui_CLICK[0]);
        						}
        					}
        				}
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
        			if(bOK && button.getText().equals("取消")) {
						button.click();
						GLog.logRecordTime(0, "----<verifywindow[" + button.getText() + "]>" + GWCtrlMsg.ui_CLICK[0]);
						break;
        			}
        		}
        	}else {
        		System.out.println("----<windowVerify<[" + GWCtrlMsg.ui_QUERY[2] + "]>");
        	}

			//检查按钮状态
	    	if(sName.equals("删_除")||sName.equals("删除")) {//如果删除按钮不是【置灰】格式，就一直等待到置灰
				GWCtrlWait.Wait2BeClickableById(webDriver, GTestIndicators.PageShowTime, eId);
	    	}
		}catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[verifywindow[" + eId + "]]>" + GWCtrlMsg.ui_CLICK[1], true);
		}
		GLog.logRecordTime(0, "]]----[verifywindow]----[widget]");
	}
	
	/**
	 *  点击某定区域内某Id元素
	 *
	 *  @param webDriver 全局驱动
	 *  @param tabIndex 指定页签序号
	 *  @param eId 指定元素id
	 *  @param sName 区分标记，用于判断是哪个操作唤起了确认窗
	 *  @param bOK 是或否
	 *  @param wId 等待元素Id
	 */
	public static void ui_C_CLICK_VERIFY(WebDriver webDriver, int tabIndex, String eId, String sName, boolean bOK, String wId){
		GLog.logRecordTime(0, "[widget]----[verifywindow]----[[");

		//辅助标记
		//确认窗体的WebElement对象
		WebElement windowVerify;
		//确认窗体定位条件
		String windowVerifyCss = GText.getCssSelectorTxt("div", "class", " x-window x-window-plain x-window-dlg");
		try {
			//由母版区域进入目标区域
			GWCtrlFrame.ui_C_SWITCN_ELEMENT(webDriver, GWCtrlWebElementIframe.getIframe(tabIndex));
			//等待目标出现
	    	GWCtrlWait.Wait2BeClickableById(webDriver, GTestIndicators.PageShowTime, eId);
	    	if(sName.equals("删除") || sName.equals("删_除")) {
	    		//如果目标是删除按钮，需要等待一定的时间才能点击，此问题为前端缺陷
	    		GWCtrlTime.Pause(webDriver, GTestIndicators.SpecialShowTime);
	    	}
	    	//点击目标
	    	GWCtrlDivClick.ById(webDriver, eId);
	    	GLog.logRecordTime(0, "----<verifywindow[" + eId + "]>>" + GWCtrlMsg.ui_CLICK[0]);
	    	//获得确认窗体
	    	windowVerify = GWCtrlWindow.ui_C_GET_WINDOW(webDriver, "cssSelector", windowVerifyCss);
	    	//如果存在确认窗体
	    	if(windowVerify != null) {
	    		GLog.logRecordTime(0, windowVerify.getText());
        		List<WebElement> buttons = windowVerify.findElements(By.tagName("button"));
        		for(WebElement button:buttons){
        			if(bOK && button.getText().equals("是")) {
        				button.click();
        				GLog.logRecordTime(0, "----<verifywindow[" + button.getText() + "]>" + GWCtrlMsg.ui_CLICK[0]);
        				if(sName.equals("删除") && eId.equals("btnDeleteYS")) {//如果点击的是【施工合同登记-预算书-删除】
        					List<WebElement> buttonsS2 = windowVerify.findElements(By.tagName("button"));
        					GLog.logRecordTime(0, windowVerify.getText() + GWCtrlMsg.ui_CLICK[0]);
        					for(WebElement buttonS2:buttonsS2){
        						if(button.getText().equals("否")) {
        							buttonS2.click();
        							GLog.logRecordTime(0, "----<verifywindow[" + buttonS2.getText() + "]>" + GWCtrlMsg.ui_CLICK[0]);
        						}
        					}
        				}
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
        	}else {
        		System.out.println("----<windowVerify<[" + GWCtrlMsg.ui_QUERY[2] + "]>");
        	}
	    	//返回母版区域
			GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
			//检查按钮状态
			
			//等待完成
			if(sName.equals("删除")) {
				if(!wId.isEmpty()) {
					if(wId.equals(GWCtrlWebElementId.CN_ID.get("单据列表")) || wId.equals(GWCtrlWebElementId.CN_ID.get("字典列表"))) {
						GWCtrlPage.ui_D_IFRAME_INDEX(webDriver, 1, "id", wId);
					}else {
						GWCtrlPage.ui_D_IFRAME_INDEX(webDriver, 2, "id", wId);
					}
				}else {
					GWCtrlPage.ui_D_IFRAME_INDEX(webDriver, 1, "id", GWCtrlWebElementId.CN_ID.get("单据列表"));
				}	
			}else {
				GWCtrlPage.ui_D_IFRAME_INDEX(webDriver, tabIndex, "id", wId);
			}
		}catch (Exception e){
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[verifywindow[" + eId + "]]>" + GWCtrlMsg.ui_CLICK[1], true);
		}
		GLog.logRecordTime(0, "]]----[verifywindow]----[widget]");
	}
	
	/**
	 * 根据页签序号和按钮的文本名称定位并点击，选择“确定”
	 *
	 * @param webDriver 全局驱动
	 * @param tabIndex iframe序号
	 * @param btnName 按钮名称
	 * @param wId 等待元素id
	 */
	public static void ui_C_CLICK_VERIFY_YES_INDEX(WebDriver webDriver, int tabIndex, String btnName, String wId){
		GWCtrlPage.ui_C_CLICK_VERIFY(webDriver,
									 tabIndex,
									 GWCtrlWebElementId.CN_ID.get(btnName),
									 btnName,
									true,
									 wId);
		if(btnName.equals("提交") || btnName.equals("提_交")) {
			GWCtrlPage.ui_D_IFRAME_INDEX(webDriver, 1, "id", GWCtrlWebElementId.CN_ID.get(btnName));
		}
		if(btnName.equals("删除") || btnName.equals("删_除")) {
			GWCtrlPage.ui_D_IFRAME_INDEX(webDriver, 1, "id", GWCtrlWebElementId.CN_ID.get(btnName));
		}
	}
	
	/**
	 * 根据页签序号和按钮的元素id定位并点击，并处理弹窗
	 *
	 * @param webDriver 全局驱动
	 * @param tabIndex iframe序号
	 * @param btnId 按钮元素id
	 * @param btnName 按钮名称
	 * @param btnChoose “是非”选定
	 * @param wId 等待元素id
	 */
	public static void ui_C_CLICK_ID_VERIFY(WebDriver webDriver, int tabIndex, String btnId, String btnName, boolean btnChoose, String wId){
		GWCtrlPage.ui_C_CLICK_VERIFY(webDriver, tabIndex, btnId, btnName, btnChoose, wId);
	}
	
	/**
    * 根据页签序号和按钮的文本名称定位并点击，选择“取消”
    *
	 * @param webDriver	目标驱动
	 * @param tabIndex 目标元素所在页签序号
	 * @param btnName 按钮名称
	 * @param wId 等待元素id
	*/
	public static void ui_C_CLICK_VERIFY_NO_INDEX(WebDriver webDriver, int tabIndex, String btnName, String wId){
		GWCtrlPage.ui_C_CLICK_VERIFY(webDriver,
									 tabIndex,
									 GWCtrlWebElementId.CN_ID.get(btnName),
									 btnName,
									 false,
									 wId);
	}
	
	/**
	 *  点击空白处
	 *
	 * @param webDriver 目标驱动
	 *  @param atrributeName 能够确定唯一空白区域对象的某属性名
	 *  @param atrributeValue 能够确定唯一空白区域对象的某属性值
	 */
	public static void ui_C_CLICK_BLANK_SPACE(WebDriver webDriver, String atrributeName, String atrributeValue){
		GLog.logRecordTime(0, "[widget]----[blankspace]----[[");
		try {
			WebElement blankSpace = GWCtrlQuery.ui_Q(webDriver, atrributeName, atrributeValue);
			if(blankSpace != null) {
				Actions action=new Actions(webDriver);
				action.click(blankSpace).perform();
			}
			GLog.logRecordTime(0, "----<blank_Space[" + Objects.requireNonNull(blankSpace) + "]>" + GWCtrlMsg.ui_CLICK[0]);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[blank_Space click failed]", true);
		}
		GLog.logRecordTime(0, "]]----[blankspace]");
	}
	
	/**
	 *  点击某定区域内某Id或Xpath的元素
	 *
	 *  @param webDriver 目标驱动
	 *  @param eId 指定id
	 *  @param eXpath 指定xpath 
	 */
	public static void ui_C_WAIT_CLICK(WebDriver webDriver, String eId, String eXpath){
		if(!eId.isEmpty()) {
			GWCtrlWait.Wait2BeClickableById(webDriver, GTestIndicators.PageShowTime, eId);
			GWCtrlDivClick.ById(webDriver, eId);
		}else{
			GWCtrlWait.Wait2BeClickableByXpath(webDriver, GTestIndicators.PageShowTime, eXpath);
			GWCtrlDivClick.ByXpath(webDriver, eXpath);
		}
	}
	
	/**
	 *  点击某定区域内某Id或Xpath的元素
	 *
	 *  @param webDriver 目标驱动
	 *  @param iframeIndex 目标iframe序号
	 *  @param eId 指定id
	 *  @param eXpath 指定xpath 
	 */
	public static void ui_C_WAIT_CLICK(WebDriver webDriver, int iframeIndex, String eId, String eXpath){
		GWCtrlFrame.ui_C_SWITCN_ELEMENT(webDriver, GWCtrlWebElementIframe.getIframe(iframeIndex));
		if(!eId.isEmpty()) {
			GWCtrlWait.Wait2BeClickableById(webDriver, GTestIndicators.PageShowTime, eId);
			GWCtrlDivClick.ById(webDriver, eId);
		}else{
			GWCtrlWait.Wait2BeClickableByXpath(webDriver, GTestIndicators.PageShowTime, eXpath);
			GWCtrlDivClick.ByXpath(webDriver, eXpath);
		}
		GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
	}
	
	/**
	 *  点击某定区域内某Id或Xpath的元素
	 *
	 *  @param webDriver 目标驱动
	 *  @param eId 指定id
	 *  @param eXpath 指定xpath 
	 *  
	 *  @param fromWhichIframe 指定id
	 *  @param isOpenChildWindow 是否开启二级窗体
	 *  @param isBack2LastWindow 是否回到最近窗体
	 *  @param isBack2DefaultIframe 是否回到基准iframe
	 */
    @SuppressWarnings("StatementWithEmptyBody")
    public static void ui_C_WAIT_CLICK(WebDriver webDriver,
                                       String eId,
                                       String eXpath,
                                       int fromWhichIframe,
                                       boolean isOpenChildWindow,
                                       boolean isBack2LastWindow,
                                       boolean isBack2DefaultIframe){
		if(fromWhichIframe == 0) {
			GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
		}else if(fromWhichIframe == -1) {
            //noinspection UnnecessarySemicolon
            ;
		}else {
			GWCtrlFrame.ui_C_SWITCN_ELEMENT(webDriver, GWCtrlWebElementIframe.getIframe(fromWhichIframe));
		}

		if(!eId.isEmpty()) {
			GWCtrlWait.Wait2BeClickableById(webDriver, GTestIndicators.PageShowTime, eId);
			GWCtrlDivClick.ById(webDriver, eId);
		}else{
			GWCtrlWait.Wait2BeClickableByXpath(webDriver, GTestIndicators.PageShowTime, eXpath);
			GWCtrlDivClick.ByXpath(webDriver, eXpath);
		}
		
		if(isOpenChildWindow) {
			GWCtrlWindow.windowHandles(webDriver);
			GWCtrlBasic.Maximize(webDriver);
		}
		
		if(isBack2LastWindow) {
			GWCtrlWindow.windowHandlePre(webDriver);
		}
		
		if(isBack2DefaultIframe) {
			GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
		}
	}
	
	/**
	 *  点击某定区域内某Id或Xpath的元素
	 *
	 *  @param webDriver 目标驱动
	 *  @param eId 指定id
	 *  @param eXpath 指定xpath 
	 *  
	 *  @param isOpenChildWindow 是否开启二级窗体
	 *  @param isBack2LastWindow 是否回到最近窗体
	 *  @param isBack2DefaultIframe 是否回到基准iframe
	 */
	public static void ui_C_WAIT_CLICK(WebDriver webDriver,
									   String eId,
									   String eXpath, 
									   boolean isOpenChildWindow, 
									   boolean isBack2LastWindow, 
									   boolean isBack2DefaultIframe){

		if(!eId.isEmpty()) {
			GWCtrlWait.Wait2BeClickableById(webDriver, GTestIndicators.PageShowTime, eId);
			GWCtrlDivClick.ById(webDriver, eId);
		}else{
			GWCtrlWait.Wait2BeClickableByXpath(webDriver, GTestIndicators.PageShowTime, eXpath);
			GWCtrlDivClick.ByXpath(webDriver, eXpath);
		}
		
		if(isOpenChildWindow) {
			GWCtrlWindow.windowHandles(webDriver);
			GWCtrlBasic.Maximize(webDriver);
		}
		
		if(isBack2LastWindow) {
			GWCtrlWindow.windowHandlePre(webDriver);
		}
		
		if(isBack2DefaultIframe) {
			GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
		}
	}
	
	/**
	 *  点击某定区域内某Id或Xpath的元素
	 *
	 *  @param webDriver 目标驱动
	 *  @param eId 指定id
	 *  @param btnText 指定btnText 
	 */
	@SuppressWarnings("UnreachableCode")
    public static void ui_C_WAIT_CLICK_BTN(WebDriver webDriver, String eId, String btnText){
		GLog.logRecordTime(0, "[widget]----[button]----[[");
		try {
			WebElement divRoot = webDriver.findElement(By.id(eId));
			GLog.logRecordTime(0, "----<button[" + eId + "]>被作为目标");
	    	if(divRoot != null) {
	    		List<WebElement> buttons = divRoot.findElements(By.tagName("button"));
	    		for(WebElement button:buttons){
	    			if(button.getText().equals(btnText)) {
	    				button.click();
	    				GLog.logRecordTime(0, "----<button[" + button.getText() + "]>" + GWCtrlMsg.ui_CLICK[0]);
	    				try {
							//noinspection UnnecessarySemicolon
	    					;
	    				}catch (Exception e){
	    					GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[some unknow error]>", true);
	    				}
	    				break;
	    			}
	    		}
	    	}
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[button" + GWCtrlMsg.ui_CLICK[1] + "]", true);
		}
		GLog.logRecordTime(0, "]]----[button]----[widget]");
	}
	
	/**
	 * 作者：赵君
	 * 指定页面元素是否存在
	 *
	 * @param webDriver 目标驱动
	 * @param cssSelector 目标元素cssSelector
	 *
	 * @return 查找结果
	 */
	public static boolean ui_C_FIND_WEBELEMENT_EXIST(WebDriver webDriver, String cssSelector) {
		try {
			GLog.logRecordTime(0, "----<webelement[" + cssSelector + "]>" + GWCtrlMsg.ui_QUERY[0]);
			webDriver.findElement(By.cssSelector(cssSelector));
			GLog.logRecordTime(0, "----<webelement[" + cssSelector + "]>" + GWCtrlMsg.ui_QUERY[1]);
			return true;
		}catch(NoSuchElementException e) {
			GLog.logRecordTime(0, "----<webelement[" + cssSelector + "]>" + GWCtrlMsg.ui_QUERY[2]);
			return false;
		}
	}
	
	/**
     * 指定元素下指定元素是否存在
	 *
     * @param domParent 目标元素
     * @param cssSelector 目标元素cssSelector
	 *
     * @return 查找结果
     */
    public static boolean ui_C_FIND_WEBELEMENT_EXIST(WebElement domParent, String cssSelector) {
        try {
            GLog.logRecordTime(0, "----<webelement[" + cssSelector + "]>" + GWCtrlMsg.ui_QUERY[0]);
            domParent.findElement(By.cssSelector(cssSelector));
            GLog.logRecordTime(0, "----<webelement[" + cssSelector + "]>" + GWCtrlMsg.ui_QUERY[1]);
            return true;
        }catch(NoSuchElementException e) {
            GLog.logRecordTime(0, "----<webelement[" + cssSelector + "]>" + GWCtrlMsg.ui_QUERY[2]);
            return false;
        }
    }
	
	/**
	 *  等待默认桌面加载完成
	 */
	private static void ui_C_WAIT_DESKTOP(WebDriver webDriver){
		try {
			GWCtrlWebElementIframe.setIframe(webDriver, 1, "cssSelector", "iframe_main show");
			GWCtrlFrame.ui_C_SWITCN_ELEMENT(webDriver, GWCtrlWebElementIframe.getIframe(1));
			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.DesktopShowTime, GWCtrlWebElementId.CN_ID.get("desktop"));
			GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
			GLog.logRecordTime(0, "----<webelement[" + GWCtrlWebElementId.CN_ID.get("desktop") + "]>" + GWCtrlMsg.ui_QUERY[1]);
		}catch(NoSuchElementException e) {
			GLog.logRecordTime(0, "----<webelement[" + GWCtrlWebElementId.CN_ID.get("desktop") + "]>" + GWCtrlMsg.ui_QUERY[2]);
		}
	}
}
