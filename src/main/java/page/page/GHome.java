package page.page;

import DT.GLog;
import Sys.GStatic;
import Webdriver.GParam;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlQuery;
import Webdriver.GWCtrlWait;
import page.base.UniqueWebElementBase;
import page.widget.*;
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
public class GHome extends UniqueWebElementBase {

	/**
	 *  顶栏
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private Header header;
	public Header getHeader() { return header; }

	/**
	 *  顶页签
	 */
    private TopTab topTab;
	public TopTab getTopTab() { return topTab; }

	/**
	 *  桌面
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private Workbench workbench;
	public Workbench getWorkbench() { return workbench; }

	/**
	 *  应用中心:包含全部四个级别菜单的所有内容对象
	 */
    private MenuWarp menuWarp;
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
		super(webDriver, GParam.getId("登录页面"));
		header = new Header(webDriver, GParam.getCssSelectorBy3K("首页_页签栏"));
		workbench = new Workbench(webDriver, GParam.getCssSelectorBy3K("首页_工作台"));
        topTab = null;
        menuWarp = null;
    }
	
	/**
	 *  唤起应用中心界面
	 *
	 *  @param webDriver 浏览器驱动对象
	 */
	public void openMenuWarp(WebDriver webDriver) {
        String menuWarpCssSelectorTemp = GParam.getCssSelectorBy3K("应用中心");

        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, menuWarpCssSelectorTemp);
		WebElement menuWarpTemp = GWCtrlQuery.ui_Q(webDriver, "cssSelector", menuWarpCssSelectorTemp);
		if(null != menuWarpTemp) {
			menuWarpTemp.click();
			GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GParam.getCssSelectorBy3K("菜单内容"));
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GParam.getCssSelectorBy3K("菜单左侧区域"));
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GParam.getCssSelectorBy3K("菜单右侧区域"));
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GParam.getCssSelectorBy3K("菜单右侧区域_三级菜单"));
			menuWarp = new MenuWarp(webDriver, GParam.getCssSelectorBy3K("菜单内容"));
			GLog.logRecordTime(9, "应用中心已打开");
		}
	}
	
	/**
	 *  打开个人设置界面
	 *
	 *  @param webDriver 浏览器驱动对象
	 */
	public void openSetting(WebDriver webDriver) {
		WebElement settingTemp = webDriver.findElement(By.cssSelector(GParam.getCssSelectorBy3K("个人设置")));
		if(!isWinOpen && null != settingTemp) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, settingTemp);
			settingTemp.click();
			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, GParam.getId("租户切换"));
			setting = new Setting(webDriver, GParam.getId("租户切换"));
			if(null != setting.getUniqueRoot()) {
				isWinOpen = true;
				GLog.logRecordTime(9, "设置界面已打开");
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
			WebElement settingTemp = webDriver.findElement(By.cssSelector(GParam.getCssSelectorBy3K("个人设置")));
			if(null != setting) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, settingTemp);
				settingTemp.click();
				isWinOpen = false;
				GLog.logRecordTime(9, "设置界面已收起");
			}
		}
	}
	
	/**
	 *  切换到指定顶层页签
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param tabName 页签名称
	 */
	public void clickTopTab(WebDriver webDriver, String tabName) {
		topTab = new TopTab(webDriver, GParam.getCssSelectorBy3K("页签菜单"));
		topTab.click(webDriver, tabName);
		GLog.logRecordTime(9, "切换到[" + tabName + "]页签");
	}

	/**
	 *  刷新指定顶层页签
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param tabName 页签名称
	 */
	public void refreshTopTab(WebDriver webDriver, String tabName) {
		topTab = new TopTab(webDriver, GParam.getCssSelectorBy3K("页签菜单"));
		topTab.refresh(webDriver, tabName);
		GLog.logRecordTime(9, "切换到[" + tabName + "]页签");
	}
	
	/**
	 *  关闭指定顶层页签
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param tabName 页签名称
	 */
	public void closeTopTab(WebDriver webDriver, String tabName) {
		topTab = new TopTab(webDriver, GParam.getCssSelectorBy3K("页签菜单"));
		topTab.close(webDriver, tabName);
		topTab.confirm(webDriver, GParam.getId("首页_确认框"), "确定");
		GLog.logRecordTime(9, "关闭[" + tabName + "]页签");
	}

	/**
	 *  打印登录页主要对象的hashcode
	 */
	public void showUnitsHash() {
        if(GStatic.gWebDiverParam.getBrowserLogType().equals("mainChinese")){
            return;
        }

        GLog.logToBottom(9, "------------------------------------------------------------------");
        GLog.logToBottom(9, "|                        MEMBER OBJECT                           |");
		GLog.logRecordTime(9, "GHome.header -> " + header.hashCode());
		GLog.logRecordTime(9, "GHome.workbench -> " + workbench.hashCode());
		GLog.logRecordTime(9, "GHome.menuWarp -> " + menuWarp.hashCode());
		GLog.logRecordTime(9, "GHome.setting -> " + setting.hashCode());
        GLog.logToBottom(9, "|                              END                               |");
        GLog.logToBottom(9, "------------------------------------------------------------------");
	}
}
