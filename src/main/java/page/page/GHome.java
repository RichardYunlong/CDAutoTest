package main.java.page.page;

import main.java.Base.GText;
import main.java.DT.GLog;
import main.java.Webdriver.GTestIndicators;
import main.java.Webdriver.GWCtrlWait;
import main.java.page.base.QueryElement;
import main.java.page.unit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  首页
 *  用于驱动首页
 *  
 *  @author hewei
 *  
 *  @version 20220415171100
 */
public class GHome {
	
	/**
	 *  顶栏
	 */
	@SuppressWarnings("FieldMayBeFinal")
	private Header diwork_header_fixed;
	
	/**
	 *  顶页签
	 */
	private TopTab menus_3I1vW = null;
	
	/**
	 *  桌面
	 */
	@SuppressWarnings("FieldMayBeFinal")
	private Workbench workbench_menu_and_content;
	
	/**
	 *  应用中心
	 */
	private MenuWarp svgWrap_1_Qqn = null;
	
	/**
	 *  个人设置
	 */
	private Setting win_g5d_0 = null;
	
	/**
	 *  得到个人设置对象
	 *
	 * @return 个人设置对象
	 */
	public Setting getWin_g5d_0() {
		return win_g5d_0;
	}
	
	/**
	 *  个人设置是否被打开
	 */
	private boolean isWinOpen = false;
	
	/**
	 *  得到顶栏对象
	 *
	 * @return 顶栏对象
	 */
	public Header getDiwork_header_fixed() {
		return diwork_header_fixed;
	}

	/**
	 *  得到桌面对象
	 *
	 * @return 桌面对象
	 */
	public Workbench getWorkbench_menu_and_content() {
		return workbench_menu_and_content;
	}

	/**
	 *  得到应用中心对象
	 *
	 * @return 应用中心对象
	 */
	public MenuWarp getSvgWrap_1_Qqn() {
		return svgWrap_1_Qqn;
	}
	
	/**
	 *  等待首页加载完毕
	 *
	 *  @param webDriver 浏览器驱动对象
	 */
	public void waitLoginPage(WebDriver webDriver) {
		GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "yonbip_login_id");
	}
	
	/**
	 *  等待工作区域加载完毕
	 *
	 *  @param webDriver 浏览器驱动对象
	 */
	public void waitHomePage(WebDriver webDriver) {
		GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "workbench_content");
	}
	
	/**
	 *  构造函数
	 *
	 * @param webDriver 浏览器驱动对象
	 */
	public GHome(WebDriver webDriver) {
		diwork_header_fixed = new Header(webDriver, "div", "class", "diwork-header-fixed");
		workbench_menu_and_content = new Workbench(webDriver, "div", "class", "workbench-menu-and-content um-content");
	}
	
	/**
	 *  打印登录页主要对象的hashcode
	 */
	public void showUnitsHash() {
		GLog.logRecordTime(0, "diwork_header_fixed -> " + diwork_header_fixed.hashCode());
		GLog.logRecordTime(0, "workbench_menu_and_content -> " + workbench_menu_and_content.hashCode());
		GLog.logRecordTime(0, "svgWrap_1_Qqn -> " + svgWrap_1_Qqn.hashCode());
		GLog.logRecordTime(0, "win_g5d_0 -> " + win_g5d_0.hashCode());
	}
	
	/**
	 *  唤起应用中心界面
	 *
	 *  @param webDriver 浏览器驱动对象
	 */
	public void openMenuWarp(WebDriver webDriver) {
		WebElement menuWarp = QueryElement.ui_Q(webDriver, "cssSelector", "svgWrap--1_Qqn ", "div", "class");
		if(null != menuWarp) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menuWarp);
			menuWarp.click();
			GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "class", "sideBarContent"));
			svgWrap_1_Qqn = new MenuWarp(webDriver, "div", "class", "sideBarContent");
		}
	}
	
	/**
	 *  打开个人设置界面
	 *
	 *  @param webDriver 浏览器驱动对象
	 */
	public void openSetting(WebDriver webDriver) {
		WebElement setting = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "win--g5d_0")));
		if(null != setting) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, setting);
			setting.click();
			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "tenantToggleId");
			win_g5d_0 = new Setting(webDriver,"tenantToggleId");
			if(null != win_g5d_0.getUniqueRoot()) {
				isWinOpen = true;
			}
		}
	}
	
	/**
	 *  收起个人设置界面
	 *
	 *  @param webDriver 浏览器驱动对象
	 */
	public void closeSetting(WebDriver webDriver) {
		if(isWinOpen) {
			WebElement setting = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "win--g5d_0")));
			if(null != setting) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, setting);
				setting.click();
				isWinOpen = false;
			}
		}
	}
	
	/**
	 *  关闭指定页签
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param tabName 页签名称
	 */
	public void clickTopTab(WebDriver webDriver, String tabName) {
		menus_3I1vW = new TopTab(webDriver, "div", "class", "menus--3I1vW");
		menus_3I1vW.click(webDriver, tabName);
	}
	
	/**
	 *  关闭指定页签
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param iframeID 如果指定了iframeid则说明确认窗体在指定的iframe中，需要切换后操作
	 *  @param tabName 页签名称
	 */
	public void closeTopTab(WebDriver webDriver, String iframeID, String tabName) {
		menus_3I1vW = new TopTab(webDriver, "div", "class", "menus--3I1vW");
		menus_3I1vW.close(webDriver, tabName);
		menus_3I1vW.confirm(webDriver, iframeID, "确定");
	}
}
