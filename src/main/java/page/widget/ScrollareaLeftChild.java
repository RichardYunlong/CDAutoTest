package page.widget;

import Base.GText;
import DT.GLog;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.base.UniqueWebElementBase;
import page.baseused.WebElementArrayList;

/**
 *  应用中心左侧菜单树
 *  左侧菜单树
 *  
 *  @author hewei
 *  @version 202204181140100
 */
public class ScrollareaLeftChild extends UniqueWebElementBase {

	/**
	 *WebElement对象
	 */
    @SuppressWarnings("CanBeFinal")
    WebElement scrollareaLeftChild;

	/**
	 *一级菜单
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private WebElementArrayList menuChildList;

	/**
	 *构造函数
	 *
	 * @param webDriver WebDriver对象
	 * @param root 能都唯一定位菜单树区域的WebElement对象
	 */
	public ScrollareaLeftChild(WebDriver webDriver, WebElement root) {
		super(root);

		scrollareaLeftChild = super.getUniqueRoot();
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, scrollareaLeftChild);
		menuChildList = new WebElementArrayList(scrollareaLeftChild, "span", "class", "domain-item-text");
		
		if(!menuChildList.getWebElementArrayList().isEmpty()) {
			GLog.logRecordTime(9, "加载菜单树一级菜单的子菜单[" + menuChildList.getStringArrayList().toString() + "]成功");
		}
	}

	/**
	 * 查询条件输入
	 *
	 * @param webDriver WebDriver对象
	 * @param name 菜单名称
	 */
	public void searchEnter(WebDriver webDriver, String name) {
		SearchInput searchInput = new SearchInput(webDriver, "input", "class", "search-input");
		searchInput.input(webDriver, name);
	}
	
	/**
	 *点击查询结果
	 *
	 * @param webDriver WebDriver对象
	 * @param name 菜单名称
	 */
	public void searchClick(WebDriver webDriver, String name) {
		SearchResult searchResult = new SearchResult(webDriver, "input", "class", "search-result-wrap");
		searchResult.click(name);
	}

    /**
	 * 点击一级菜单
	 * 可能会显示二级菜单
	 *
	 * @param webDriver WebDriver对象
	 * @param dir 菜单名称
	 */
	public void clickMenuChild(WebDriver webDriver, String dir) {
		WebElement menuChild;
		menuChild = menuChildList.getWebElement(dir);
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menuChild);
		menuChild.click();
		GLog.logRecordTime(9, "点击菜单[" + dir + "]成功");
	}
}
