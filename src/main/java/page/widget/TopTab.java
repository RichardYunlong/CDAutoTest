package page.widget;

import Base.GText;
import DT.GLog;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlFrame;
import Webdriver.GWCtrlWait;
import page.base.UniqueWebElementBase;
import page.baseused.WebElementArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 *  顶部页签
 *  
 *  @author hewei
 *  @version 20220420181700
 */
public class TopTab extends UniqueWebElementBase {
	
	/**
	 * 页签列表
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private WebElementArrayList tabs;
	
	/**
	 *当前焦点页签
	 */
	private WebElement activeTab = null;
	
	/**
	 *首页
	 */
	private WebElement homeTab = null;

	/**
	 *获得当前焦点页签
	 *
	 * @return 当前焦点页签
	 */
	public WebElement getActiveTab() {
		return activeTab;
	}
	
	/**
	 *确认窗体
	 */
	@SuppressWarnings({"FieldCanBeLocal", "RedundantSuppression"})
    private WebElement confirmWindow = null;

	/**
	 *构造函数
	 *
	 * @param webDriver 目标驱动
	 * @param tageName 元素标签名
	 * @param atrributeName 元素属性名称
	 * @param atrributeValue 元素属性值
	 */
	public TopTab(WebDriver webDriver, String tageName, String atrributeName, String atrributeValue) {
		super(webDriver, tageName, atrributeName, atrributeValue);
		
		tabs = new WebElementArrayList(webDriver, super.getUniqueRoot(), "li", "p");
		loadCommonTab(webDriver);
	}
	
	/**
	 *初始化特征页签
	 *加载首页和当前焦点页签
	 *
	 * @param webDriver 目标驱动
	 */
	private void loadCommonTab(WebDriver webDriver) {
		List<WebElement> mayHomes;
		mayHomes = super.getUniqueRoot().findElements(By.tagName("span"));
		if(null != mayHomes && !mayHomes.isEmpty()) {
			for(WebElement mayHome:mayHomes) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, mayHome);
				if("首页".equals(mayHome.getText())) {
					homeTab = mayHome;
					break;
				}
			}
		}
		if(null != tabs && !tabs.getWebElementArrayList().isEmpty()) {
			for(WebElement tab:tabs.getWebElementArrayList()) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, tab);
				if(tab.getAttribute("class").contains("active")) {
					activeTab = tab;
					break;
				}
			}
		}
	}
	
	/**
	 *点击首页
	 *
	 * @param webDriver 目标驱动
	 */
	public void clickHome(WebDriver webDriver) {
		if(null != homeTab) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, homeTab);
			homeTab.click();
			GLog.logRecordTime(9, "点击页签[首页]成功");
		}
	}
	
	/**
	 *点击指定页签
	 *
	 * @param webDriver 目标驱动
	 * @param tabName 页签标题
	 */
	public void click(WebDriver webDriver, String tabName) {
		WebElement tabTitleArea;
		tabTitleArea = tabs.getWebElement(tabName).findElement(By.tagName("p"));

		if(null != tabTitleArea) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, tabTitleArea);
			tabTitleArea.click();
			GLog.logRecordTime(9, "点击页签[" + tabName + "]成功");
		}
	}

	/**
	 *刷新指定页签
	 *
	 * @param webDriver 目标驱动
	 * @param tabName 页签标题
	 */
	public void refresh(WebDriver webDriver, String tabName) {
		WebElement tabTitleArea;
		tabTitleArea = tabs.getWebElement(tabName).findElement(By.tagName("p"));

		if(null != tabTitleArea) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, tabTitleArea);

			super.clickRight(webDriver, tabTitleArea);
			WebElement tabRefresh = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "fieldid", "workbench-tabs-ref")));
			tabRefresh.click();

			GLog.logRecordTime(9, "刷新页签[" + tabName + "]成功");
		}
	}

	/**
	 *关闭指定页签
	 *
	 * @param webDriver	目标驱动
	 * @param tabName 页签标题
	 */
	public void close(WebDriver webDriver, String tabName) {
		WebElement close;
		
		close = tabs.getWebElement(tabName).findElement(By.tagName("i"));
		
		if(null != close) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, close);
			close.click();
			GLog.logRecordTime(9, "点击页签[" + tabName + "]成功");
		}
	}
	
	/**
	 *确认窗体
	 *
	 * @param webDriver 目标驱动
	 *@param iframeID 如果指定了iframeid则说明确认窗体在指定的iframe中，需要切换后操作
	 *@param actionName 确认按钮文本
	 */
	public void confirm(WebDriver webDriver, String iframeID, String actionName) {

		try{
			confirmWindow = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "wui-modal wui-modal-open fc-modal  ")));
		} catch (Exception e) {
			GLog.logRecordTime(9, "未找到确认窗口");
		}

		
		if(null != confirmWindow) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, confirmWindow);
			GLog.logRecordTime(9, confirmWindow.findElement(By.cssSelector(GText.getCssSelectorTxt("span", "class", "w300 clearfix f14"))).getText());
			
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
			
			GLog.logRecordTime(9, "选择[" + actionName + "]");
		}
		
		if(null != iframeID && !iframeID.isEmpty()) {
			GWCtrlFrame.ui_C_SWITCN_DEFAULT(webDriver);
		}
	}
}
