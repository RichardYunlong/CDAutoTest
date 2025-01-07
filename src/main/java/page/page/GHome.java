package page.page;

import Base.GText;
import DT.GLog;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlWait;
import page.base.QueryElement;
import page.unit.*;
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
	private Header header;
	public Header getHeader() { return header; }

	/**
	 *  顶页签
	 */
	@SuppressWarnings("FieldMayBeFinal")
    private TopTab topTab = null;
	public TopTab getTopTab() { return topTab; }

	/**
	 *  桌面
	 */
	@SuppressWarnings("FieldMayBeFinal")
	private Workbench workbench;
	public Workbench getWorkbench() { return workbench; }

	/**
	 *  应用中心:包含全部四个级别菜单的所有内容对象
	 */
	@SuppressWarnings("FieldMayBeFinal")
    private MenuWarp menuWarp = null;
	public MenuWarp getMenuWarp() { return menuWarp; }
	
	/**
	 *  个人设置
	 */
	private Setting setting = null;
	public Setting getSetting() {
		return setting;
	}

	/**
	 *  个人设置是否被打开
	 */
	private boolean isWinOpen = false;
	public boolean isWinOpen() { return isWinOpen; }

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
		header = new Header(webDriver, "div", "class", "diwork-header-fixed");
		workbench = new Workbench(webDriver, "div", "class", "diwork-content-fixed um-content");
	}
	
	/**
	 *  打印登录页主要对象的hashcode
	 */
	public void showUnitsHash() {
		GLog.logRecordTime(0, "header -> " + header.hashCode());
		GLog.logRecordTime(0, "workbench -> " + workbench.hashCode());
		GLog.logRecordTime(0, "menuWarp -> " + menuWarp.hashCode());
		GLog.logRecordTime(0, "setting -> " + setting.hashCode());
	}
	
	/**
	 *  唤起应用中心界面
	 *
	 *  @param webDriver 浏览器驱动对象
	 */
	public void openMenuWarp(WebDriver webDriver) {
		WebElement menuWarpTemp = QueryElement.ui_Q(webDriver, "cssSelector", "menuWrap--3oJM8", "div", "class");
		if(null != menuWarpTemp) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menuWarpTemp);
			menuWarpTemp.click();
			GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "class", "sideBarContent"));
			menuWarp = new MenuWarp(webDriver, "div", "class", "sideBarContent");
		}
	}
	
	/**
	 *  打开个人设置界面
	 *
	 *  @param webDriver 浏览器驱动对象
	 */
	public void openSetting(WebDriver webDriver) {
		WebElement settingTemp = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "win--g5d_0")));
		if(!isWinOpen && null != settingTemp) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, settingTemp);
			settingTemp.click();
			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "tenantToggleId");
			setting = new Setting(webDriver,"tenantToggleId");
			if(null != setting.getUniqueRoot()) {
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
			WebElement settingTemp = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "win--g5d_0")));
			if(null != setting) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, settingTemp);
				settingTemp.click();
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
		topTab = new TopTab(webDriver, "div", "class", "menus--3I1vW");
		topTab.click(webDriver, tabName);
	}
	
	/**
	 *  关闭指定页签
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param iframeID 如果指定了iframeid则说明确认窗体在指定的iframe中，需要切换后操作
	 *  @param tabName 页签名称
	 */
	public void closeTopTab(WebDriver webDriver, String iframeID, String tabName) {
		topTab = new TopTab(webDriver, "div", "class", "menus--3I1vW");
		topTab.close(webDriver, tabName);
		topTab.confirm(webDriver, iframeID, "确定");
	}
}
