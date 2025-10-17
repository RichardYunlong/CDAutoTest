package page.widget;

import Base.GText;
import Webdriver.*;
import page.base.UniqueWebElementBase;
import page.baseused.WebElementHashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  查询控件
 *  
 *  @author hewei
 */
public class QueryScheme extends UniqueWebElementBase {

	/**
	 * 搜索按钮
	 */
	private WebElement querySchemeRoot;

	/**
	 *点击型查询条件
	 *条件名，条件值
	 */
	@SuppressWarnings("FieldMayBeFinal")
    private WebElementHashMap queryCriteriasClickType = null;

	/**
	 *下拉型查询条件
	 *条件名，条件值
	 */
	@SuppressWarnings("FieldMayBeFinal")
	private WebElementHashMap queryCriteriasDropdownType = null;

	/**
	 *参照型查询条件
	 *条件名，条件值
	 */
	@SuppressWarnings("FieldMayBeFinal")
	private WebElementHashMap queryCriteriasReferToType = null;

	/**
	 *输入型查询条件
	 *条件名，条件值
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private WebElementHashMap queryCriteriasInputType = null;

	/**
	 *下拉多选型查询条件
	 *条件名，条件值
	 */
	@SuppressWarnings("FieldMayBeFinal")
	private WebElementHashMap queryCriteriasPoperType = null;

	/**
	 *折叠按钮
	 * 默认查询条件为收起状态
	 */
	@SuppressWarnings("FieldCanBeLocal")
    private boolean isShowMore = false;

    /**
     *构造函数
     *
     * @param webDriver 目标驱动
     * @param cssSelectorType 表示使用固定的cssSelector表达式
     * @param cssSelector cssSelector表达式
     */
    public QueryScheme(WebDriver webDriver, String cssSelectorType, String cssSelector) {
        super(webDriver, cssSelectorType, cssSelector);
        querySchemeRoot = super.getUniqueRoot();
    }
	
	/**
	 *填写输入型查询条件
	 *
	 * @param webDriver 目标驱动
	 * @param name 条件名称
	 * @param value 条件值
	 */
	public void input(WebDriver webDriver, String name, String value) {
		if(null != queryCriteriasInputType) {
			WebElement input;
			input = queryCriteriasInputType.getWebElementHashMap().get(name);
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, input);
			GWCtrlInputFill.ByWebElement(webDriver, input, value);
			GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GParam.getCssSelectorBy3K("筛选条件_右侧"));
		}
	}

	/**
	 * 根据目标类型输入查询条件
	 *
	 * @param webDriver 目标驱动
	 * @param name 条件名称
	 * @param value 条件值
	 */
	public void setting(WebDriver webDriver, String name, String value) {
		WebElement filter = null;
		try {
			String filterTagProValue;
            //noinspection SwitchStatementWithTooFewBranches
            switch(name){
				case "执行状态":{
					filterTagProValue = "yontest_task_exec_newTreeTable|status_search_input";
					break;
				}
				default:{
					filterTagProValue = "yontest_task_exec_newTreeTable|name";
					break;
				}
			}
			filter = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("input", "fieldid", filterTagProValue)));
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[" + name + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		if(null != filter) {
			GWCtrlInputFill.ByWebElementForStatus(webDriver, filter, value);
			reload(webDriver);
		}
	}

	/**
	 *点击共有按钮
	 *
	 * @param webDriver 目标驱动
	 * @param buttonName 按钮名称或关键字
	 */
	public void click(WebDriver webDriver, String buttonName) {
		WebElement button = null;
		try {
			String buttonTagProValue;
			switch(buttonName){
				case "重置":{
					buttonTagProValue = "yontest_task_exec_newTreeTable|reset";
					break;
				}
				case "高级":{
					buttonTagProValue = "yontest_task_exec_newTreeTable|advance";
					break;
				}
				case "展开":{
					buttonTagProValue = "yontest_task_exec_newTreeTable|more-xiangxia-copy";
					break;
				}
				case "收起":{
					buttonTagProValue = "yontest_task_exec_newTreeTable|more-arrow-down";
					break;
				}
				default:{
					buttonTagProValue = "yontest_task_exec_newTreeTable|search";
					break;
				}
			}
			GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("button", "fieldid", buttonTagProValue));
			button = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("button", "fieldid", buttonTagProValue)));
		}catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[" + buttonName + GWCtrlMsg.ui_QUERY[2] + "]", true);
		}
		if(null != button) {
			button.click();
			reload(webDriver);
            isShowMore = buttonName.contains("展开");
		}
	}

	/**
	 * 页面刷新，加载新元素
	 *
	 * @param webDriver 目标驱动
	 */
	public void reload(WebDriver webDriver){
		GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "class", "new-filter-container"));
		querySchemeRoot = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "new-filter-container")));
	}
}
