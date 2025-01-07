package main.java.yonbip.unit;

import main.java.Base.GText;
import main.java.DT.GLog;
import main.java.Webdriver.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 *  用户设置
 *  
 *  @author hewei
 *  
 *  @version 202204191655200
 */
public class TenantDropDown {
	
	/**
	 *弹出下拉菜单的点击区
	 */
	private WebElement tenant_toggle = null;
	
	/**
	 *搜索区输入框
	 */
	private WebElement tenant_search_input = null;
	
	/**
	 *搜索结果区域
	 */
	@SuppressWarnings("FieldCanBeLocal")
    private WebElement menuitem = null;
	
	/**
	 *确认窗体
	 */
	private WebElement confirmWindow = null;
	
	/**
	 *切换前租户名称
	 */
	private String curTenant = "";
	
	/**
	 *切换前租户名称与切换目标是否相同
	 */
	private boolean isSame = false;
	
	/**
	 *弹出下拉菜单的按钮
	 *
	 * @param webDriver 目标驱动
	 */
	public void eject(WebDriver webDriver) {
		tenant_toggle = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("li", "class", "tenant-toggle")));
		if(null != tenant_toggle) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, tenant_toggle);
			tenant_toggle.click();
		}
	}
	
	/**
	 * 输入查询条件
	 *
	 * @param webDriver 目标驱动
	 * @param name 租户名称
	 */
	public void input(WebDriver webDriver, String name) {
		if(curTenant.equals(name)) {
			isSame = true;
		}
		
		if(!isSame) {
			List<WebElement> inputs = webDriver.findElements(By.cssSelector(GText.getCssSelectorTxt("input", "class", "tenant-search-input")));
			
			if(null != inputs && !inputs.isEmpty()) {
				for(WebElement input:inputs) {
					GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, input);
					if(input.isDisplayed()) {
						tenant_search_input = input;
						break;
					}
				}
			}
			
			
			if(null != tenant_search_input) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, tenant_search_input);
				GWCtrlInputFill.ByWebElement(webDriver, tenant_search_input, name);
			}
		}
	}
	
	/**
	 *点击查询按钮
	 *
	 * @param webDriver 目标驱动
	 */
	public void search(WebDriver webDriver) {
		if(!isSame) {
			WebElement searchButton = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("i", "class", "iconfont font_family_u8c__iconfont___2OzrA icon-sousuo__iconfont___17ipx md__index___3HmlR tenant-search-icon-query")));
			if(null != searchButton) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, searchButton);
				searchButton.click();
			}	
		}
	}
	
	/**
	 *点击目标
	 *
	 * @param webDriver 目标驱动
	 * @param name 租户名称
	 */
	public void click(WebDriver webDriver, String name) {
		if(!isSame) {
			menuitem = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("li", "role", "menuitem")));
			
			if(null != menuitem) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menuitem);
				List<WebElement> menuItems = menuitem.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "liTitle--2O1pi")));
				
				if(null != menuItems && !menuItems.isEmpty()) {
					for(WebElement menu:menuItems) {
						GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menu);
						if(name.equals(menu.getText())) {
							menu.click();
							break;
						}
					}
				}
				
				//如果当前租户和目标租户不一致，一直尝试检查确认窗口是否打开，直到前租户和目标租户一致
				try {	
					confirmWindow = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "wui-modal-content")));
					if(null != confirmWindow) {
						GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, confirmWindow);
					}
				}catch (Exception e) {
					GLog.logRecordTime(0, "选择[确认窗口未打开]");
				}
			}

		}
	}
	
	/**
	 *确认窗体
	 *
	 * @param webDriver 目标驱动
	 * @param actionName 确认动作名称
	 */
	public void confirm(WebDriver webDriver, String actionName) {
		if(null != confirmWindow) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, confirmWindow);
			GLog.logRecordTime(0, confirmWindow.findElement(By.cssSelector(GText.getCssSelectorTxt("p", "class", "content_p"))).getText());
			
			List<WebElement> buttons = confirmWindow.findElements(By.cssSelector(GText.getCssSelectorTxt("span", "class", "wui-button-text-wrap")));
			
			if(null != buttons && !buttons.isEmpty()) {
				for(WebElement button:buttons) {
					GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, button);
					if(actionName.equals(button.getText())) {
						button.click();
						break;
					}
				}
			}
			
			GLog.logRecordTime(0, "选择[" + actionName + "]成功");
		}
	}
	
	/**
	 *  构造函数
	 *
	 * @param webDriver 目标驱动
	 */
	public TenantDropDown(WebDriver webDriver) {
		try {
			tenant_toggle = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("li", "class", "tenant-toggle")));
			curTenant = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "home_title"))).getText();
			
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, tenant_toggle);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[TenantDropDown" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}	
	}
}
