package page.widget;

import Base.GText;
import DT.GLog;
import Webdriver.*;
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
	@SuppressWarnings("FieldCanBeLocal")
    private String curTenant = "";

	/**
	 *目标租户名称
	 */
	private String tarTenant = "";
	
	/**
	 *切换前租户名称与切换目标是否相同
	 */
	private boolean isSame = true;
	
	/**
	 *弹出下拉菜单的按钮
	 *
	 * @param webDriver 目标驱动
	 */
	public void eject(WebDriver webDriver) {
		if(!isSame) {
			tenant_toggle = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("i", "fieldid", "settings-item-tenantToggle_suffix_icon")));
			if (null != tenant_toggle) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, tenant_toggle);
				tenant_toggle.click();
				GLog.logRecordTime(9, "展开下拉菜单");
			}
		}else{
			WebElement setting = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "win--g5d_0")));
			if(null != setting) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, setting);
				setting.click();
				GLog.logRecordTime(9, "收起下拉菜单");
			}
		}
	}
	
	/**
	 * 输入查询条件
	 *
	 * @param webDriver 目标驱动
	 * @param name 租户名称
	 */
	public void input(WebDriver webDriver, String name) {
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
				GLog.logRecordTime(9, "搜索租户[" + name + "]");
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
			WebElement searchButton = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("i", "class", "iconfont font_family_u8c__iconfont___QVVJZ icon-sousuo__iconfont___oUCmL md__index___2lR2r tenant-search-icon-query")));
			if(null != searchButton) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, searchButton);
				searchButton.click();
				GLog.logRecordTime(9, "搜索租户...");
			}	
		}
	}
	
	/**
	 *点击目标
	 *
	 * @param webDriver 目标驱动
	 */
	public void click(WebDriver webDriver) {
		if(!isSame) {
			menuitem = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("li", "role", "menuitem")));
			
			if(null != menuitem) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menuitem);
				List<WebElement> menuItems = menuitem.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "class", "liTitle--2O1pi")));
				
				if(null != menuItems && !menuItems.isEmpty()) {
					for(WebElement menu:menuItems) {
						GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menu);
						if(tarTenant.equals(menu.getText())) {
							menu.click();
							GLog.logRecordTime(9, "点击[" + tarTenant + "]");
							break;
						}
					}
				}
				
				//如果当前租户和目标租户不一致，一直尝试检查确认窗口是否打开，直到前租户和目标租户一致
				try {
					confirmWindow = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "wui-modal wui-modal-open wui-modal-centered")));
					if(null != confirmWindow) {
						GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, confirmWindow);
					}
				}catch (Exception e) {
					GLog.logRecordTime(9, "选择[确认窗口未打开]或者[确认窗口未找到]");
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
		if(!isSame) {
			if (null != confirmWindow) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, confirmWindow);
				GLog.logRecordTime(9, confirmWindow.findElement(By.cssSelector(GText.getCssSelectorTxt("p", "class", "content_p"))).getText());

				List<WebElement> buttons = confirmWindow.findElements(By.cssSelector(GText.getCssSelectorTxt("span", "class", "wui-button-text-wrap")));

				if (null != buttons && !buttons.isEmpty()) {
					for (WebElement button : buttons) {
						GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, button);
						if (actionName.equals(button.getText())) {
							button.click();
							GLog.logRecordTime(9, "点击[" + actionName + "]");
							break;
						}
					}
				}

				GLog.logRecordTime(9, "选择[" + actionName + "]成功");
			}
		}
	}

    /**
	 *  构造函数
	 *
	 * @param webDriver 目标驱动
	 * @param name 租户名称
	 */
	public TenantDropDown(WebDriver webDriver, String name) {
		try {
			WebElement home_title = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "home_title")));
			WebElement curTenantWebElement = home_title.findElement(By.tagName("span"));

			tenant_toggle = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("li", "class", "tenant-toggle")));
			curTenant = curTenantWebElement.getAttribute("title");

			if(!curTenant.equals(name)) {
				isSame = false;
				tarTenant = name;
			}
			
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, tenant_toggle);
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[TenantDropDown" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}	
	}
}
