package main.java.page.unit;

import main.java.DT.GLog;
import main.java.Webdriver.GTestIndicators;
import main.java.Webdriver.GWCtrlWait;
import main.java.page.base.WebElementArrayList;
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
	WebElement scrollareaLeft = null;
	
	/**
	 *一级菜单
	 */
	private WebElementArrayList menuLevel1 = null;
	
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
	public void clickMenuLevel1(String dir) {
		WebElement level1 = null;
		level1 = menuLevel1.getWebElement(dir).findElement(By.xpath("../../.."));
		WebElement status = null;
		status = level1.findElement(By.tagName("i"));
		if(status.getAttribute("class").contains("shrink")) {
			menuLevel1.click(dir);
		}
		
		menuLevel2 = new WebElementArrayList(scrollareaLeft, "span", "class", "domain-item-text");
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
