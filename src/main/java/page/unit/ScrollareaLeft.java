package page.unit;

import DT.GLog;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlWait;
import page.base.WebElementArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  应用中心左侧菜单树
 *  左侧菜单树
 *  
 *  @author hewei
 *  @version 202204181140100
 */
public class ScrollareaLeft {
	
	/**
	 *WebElement对象
	 */
    @SuppressWarnings("CanBeFinal")
    WebElement scrollareaLeft;
	
	/**
	 *一级菜单
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private WebElementArrayList menuLevel1;
	
	/**
	 *二级菜单
	 */
	private WebElementArrayList menuLevel2 = null;

	/**
	 *构造函数
	 *
	 * @param webDriver WebDriver对象
	 * @param root 能都唯一定位菜单树区域的WebElement对象
	 */
	public ScrollareaLeft(WebDriver webDriver, WebElement root) {
		scrollareaLeft = root;
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, scrollareaLeft);
		menuLevel1 = new WebElementArrayList(scrollareaLeft, "span", "class", "topText-domain");
		
		if(!menuLevel1.getWebElementList().isEmpty()) {
			GLog.logRecordTime(0, "加载菜单树一级菜单[" + menuLevel1.getStringList().toString() + "]成功");
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
	public void clickMenuLevel1(WebDriver webDriver, String dir) {
		WebElement level1;
		level1 = menuLevel1.getWebElement(dir).findElement(By.xpath("../../.."));
		WebElement status;
		status = level1.findElement(By.tagName("div"));
		if(!status.getAttribute("class").contains("active")) {
			menuLevel1.click(dir);
		}

		menuLevel2 = new WebElementArrayList(scrollareaLeft, "div", "class", "scrollarea navbarSecond");
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menuLevel2.getWebElement(""));
		if(!menuLevel2.getWebElementList().isEmpty()) {
			GLog.logRecordTime(0, "加载菜单树二级菜单[" + menuLevel2.getStringList().toString() + "]成功");
		}
	}
	
	/**
	 * 点击二级菜单
	 *
	 *@param name 菜单名称
	 */
	public void clickMenuLevel2(String name) {
		if(!"".equals(name)) {
			menuLevel2.click(name);
		}
	}
}
