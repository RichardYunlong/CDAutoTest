package page.unit;

import DT.GLog;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlWait;
import page.base.WebElementArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  菜单滚动区
 */
public class MenuScrollArea {

	/**
	 *WebElement对象
	 */
    @SuppressWarnings("CanBeFinal")
    WebElement menus;

	/**
	 *一级菜单
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private WebElementArrayList menusList;

	/**
	 *构造函数
	 *
	 * @param webDriver WebDriver对象
	 * @param root 能都唯一定位菜单树区域的WebElement对象
	 */
	public MenuScrollArea(WebDriver webDriver, WebElement root) {
		menus = root;
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menus);
		menusList = new WebElementArrayList(menus, "span", "class", "topText-domain");
		
		if(!menusList.getWebElementList().isEmpty()) {
			GLog.logRecordTime(0, "加载菜单树[" + menusList.getStringList().toString() + "]成功，得到目标节点" + menusList.size() + "个");
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
	 * @param dir 菜单名称
	 */
	public void click(WebDriver webDriver, String dir) {
		WebElement level1;
		level1 = menusList.getWebElement(dir).findElement(By.xpath("../../.."));
		WebElement status;
		status = level1.findElement(By.tagName("div"));
		if(!status.getAttribute("class").contains("active")) {
			menusList.click(dir);
		}
	}
}
