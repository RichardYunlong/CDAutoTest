package main.java.yonbip.unit;

import main.java.Base.GText;
import main.java.DT.GLog;
import main.java.Webdriver.*;
import main.java.yonbip.base.QueryElement;
import main.java.yonbip.base.UniqueBase;
import main.java.yonbip.base.WebElementArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 *  下拉复选框
 *  
 *  @author hewei
 *  
 *  @version 20220426095700
 */
public class Poper extends UniqueBase {
	
	/**
	 *查询
	 */
	private WebElement searchInput = null;
	
	/**
	 *滚动
	 */
	private WebElement scroll = null;
	
	/**
	 *复选框
	 */
	private WebElementArrayList checkBoxs = null;
	
	/**
	 *确认按钮
	 */
	private WebElementArrayList buttons = null;

	/**
	 *构造函数
	 *
	 * @param webDriver 目标驱动
	 * @param locateTagName 定位标签名
	 * @param locateAtrributeName 定位属性名
	 * @param locateArributeValue 定位属性值
	 */
	public Poper(WebDriver webDriver,
				 String locateTagName,
				 String locateAtrributeName,
				 String locateArributeValue) {
		super(webDriver, locateTagName, locateAtrributeName, locateArributeValue);
		
		try {
			searchInput = super.getUniqueRoot().findElement(By.cssSelector(GText.getCssSelectorTxt("input", "type", "search")));
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[search" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		
		try {
			scroll = super.getUniqueRoot().findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "search_scroll")));
			
			if(null != scroll) {
				GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, scroll);
				checkBoxs = new WebElementArrayList(scroll, "span", "class", "wui-checkbox-label");
			}
			
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[search" + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
	}
	
	/**
	 *搜索
	 *
	 * @param webDriver 目标驱动
	 * @param queryCriteria 搜索条件
	 */
	public void search(WebDriver webDriver, String queryCriteria) {
		GWCtrlInputFill.ByWebElement(webDriver, searchInput, queryCriteria);
		scroll = super.getUniqueRoot().findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "search_scroll")));
		if(null != scroll) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, scroll);
			checkBoxs = new WebElementArrayList(scroll, "span", "class", "wui-checkbox-label");
			buttons = new WebElementArrayList(scroll, "span", "class", "wui-button-text-wrap");
		}
		
		try {
			WebElement haveno_search = super.getUniqueRoot().findElement(By.cssSelector(GText.getCssSelectorTxt("span", "class", "haveno_search")));
			GLog.logRecordTime(0, "[" + haveno_search.getText() + "]");
		}catch (Exception e) {
			GLog.logRecordTime(0, "[请检查搜索结果]");
		}
		
	}
	
	/**
	 *仅选中
	 *
	 * @param webDriver 目标驱动
	 * @param checkBoxName 搜索条件
	 * @param checked 是否选中
	 */
	@SuppressWarnings("StatementWithEmptyBody")
    public void select(WebDriver webDriver, String checkBoxName, String checked) {
		boolean isChecked = "ture".equals(checked);

        WebElement checkTemp = null;
		checkTemp = checkBoxs.getWebElement(checkBoxName);
		WebElement isCheckTemp = null;
		isCheckTemp = QueryElement.ui_Q(webDriver, checkTemp, 1);
		if(isCheckTemp.getAttribute("class").contains("is-checked")) {
			if(isChecked) {
				GLog.logRecordTime(0, "目标[" + checkBoxName + "]已经是选中状态");
			}else {
				isCheckTemp.click();
				if(!isCheckTemp.getAttribute("class").contains("is-checked")) {
					GLog.logRecordTime(0, "目标[" + checkBoxName + "]被取消选中了");
				}
			}
		}else {
			if(isChecked) {
				isCheckTemp.click();
				if(isCheckTemp.getAttribute("class").contains("is-checked")) {
					GLog.logRecordTime(0, "目标[" + checkBoxName + "]被选中了");
				}
			}else {
				;
			}
		}
	}
	
	/**
	 *单项选中
	 *
	 * @param webDriver 目标驱动
	 * @param checkBoxName 搜索条件
	 * @param checked 是否选中
	 * @param verify 确认操作名称
	 */
	public void singleSelect(WebDriver webDriver, String checkBoxName, String checked, String verify) {
		select(webDriver, checkBoxName, checked);
		verify(verify);
	}
	
	/**
	 *多项选中
	 *
	 * @param webDriver 目标驱动
	 * @param queryCriterias 搜索条件
	 * @param verify 确认操作名称
	 */
	public void multiSelect(WebDriver webDriver, HashMap<String, String> queryCriterias, String verify) {
		if(null != queryCriterias && !queryCriterias.isEmpty()) {
			for(Entry<String, String> queryCriteria:queryCriterias.entrySet()) {
				select(webDriver, queryCriteria.getKey(), queryCriteria.getValue());
			}
		}
		verify(verify);
	}
	
	/**
	 *确认
	 *
	 *@param verify 确认操作名称
	 */
	public void verify(String verify) {
		if(null != buttons && buttons.size() > 0) {
			buttons.click(verify);
		}
	}
}
