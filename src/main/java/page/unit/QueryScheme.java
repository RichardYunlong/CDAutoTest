package page.unit;

import Base.GText;
import Webdriver.*;
import page.base.UniqueBase;
import page.base.WebElementHashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;

/**
 *  查询控件
 *  
 *  @author hewei
 *  
 *  @version 20220425204900
 */
public class QueryScheme extends UniqueBase {
	
	/**
	 *查询条件
	 *条件名，条件值
	 */
	private WebElementHashMap queryCriterias = null;
	
	/**
	 *调整
	 */
	private WebElement setting = null;
	
	/**
	 *查询条件设置
	 */
	private Poper poper = null;
	
	/**
	 *重置
	 */
	private WebElement reset = null;
	
	/**
	 *调整
	 */
	private WebElement search = null;

	/**
	 *构造函数
	 *
	 * @param webDriver 目标驱动
	 * @param locateTagName 定位标签名
	 * @param locateAtrributeName 定位属性名
	 * @param locateArributeValue 定位属性值
	 */
	public QueryScheme(WebDriver webDriver, String locateTagName, String locateAtrributeName, String locateArributeValue) {
		super(webDriver, locateTagName, locateAtrributeName, locateArributeValue);

		try {
			setting = super.getUniqueRoot().findElement(By.cssSelector(GText.getCssSelectorTxt("span", "class", "new_setting")));
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[setting" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		try {
			reset = super.getUniqueRoot().findElement(By.cssSelector(GText.getCssSelectorTxt("span", "class", "new_reset")));
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[setting" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		try {
			search = super.getUniqueRoot().findElement(By.id("2005627search"));
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[setting" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
	}
	
	/**
	 *获得当前所有查询条件对象
	 *
	 * @param webDriver 目标驱动
	 */
	public void loadQueryCriterias(WebDriver webDriver) {
		queryCriterias = new WebElementHashMap(super.getUniqueRoot());
		if(null != setting) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, setting);
			setting.click();
			poper = new Poper(webDriver, "div", "class", "wui-popover-content");
		}
	}
	
	/**
	 *调整查询条件是否可见
	 *
	 * @param webDriver 目标驱动
	 * @param queryCriterias 要操作的条件名称合适否可见
	 */
	public void setting(WebDriver webDriver, HashMap<String, String> queryCriterias) {
		if(null != setting) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, setting);
			setting.click();
			poper = new Poper(webDriver, "div", "class", "wui-popover-content");
			poper.multiSelect(webDriver, queryCriterias, "保存");
		}
	}
	
	/**
	 *调整查询条件是否可见
	 * @param webDriver 目标驱动
	 */
	public void reset(WebDriver webDriver) {
		if(null != reset) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, reset);
			reset.click();
			loadQueryCriterias(webDriver);
		}
	}
	
	/**
	 *填写查询条件
	 *
	 * @param webDriver 目标驱动
	 * @param name 条件名称
	 * @param value 条件值
	 */
	public void input(WebDriver webDriver, String name, String value) {
		if(null != queryCriterias) {
			WebElement input;
			input = queryCriterias.getWebElementHashMap().get(name);
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, input);
			GWCtrlInputFill.ByWebElement(webDriver, input, value);
			loadQueryCriterias(webDriver);
		}
	}
	
	/**
	 *点击查询
	 *
	 * @param webDriver 目标驱动
	 * @param name 条件名称
	 * @param value 条件值
	 */
	public void search(WebDriver webDriver, String name, String value) {
		if(null != search) {
			search.click();
			loadQueryCriterias(webDriver);
		}	
	}
}
