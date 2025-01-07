package main.java.page.unit;

import main.java.Base.GText;
import main.java.DT.GLog;
import main.java.Webdriver.GTestIndicators;
import main.java.Webdriver.GWCtrlWait;
import main.java.page.base.QueryElement;
import main.java.page.base.WebElementArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScrollareaRight {
	
	/**
	 *结果树的WebElement对象
	 */
	WebElement scrollareaRight = null;
	
	/**
	 *一级菜单的WebElement对象
	 */
	WebElement menuLevel1Temp = null;
	
	/**
	 *叶子菜单的WebElement对象
	 */
	WebElement menuLeavesTemp = null;
	
	/**
	 *一级菜单
	 */
	private WebElementArrayList menuLevel1 = null;
	
	/**
	 *叶子菜单
	 */
	private WebElementArrayList menuLeaves = null;

	/**
	 *构造函数
	 *
	 * @param webDriver 目标驱动
	 * @param root 能都唯一定位菜单树区域的WebElement对象
	 */
	public ScrollareaRight(WebDriver webDriver, WebElement root) {
		scrollareaRight = root;
		WebElement active = scrollareaRight.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "funcWrap active")));
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, active);
		
		menuLevel1Temp = active.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "scrollarea navbarSecond")));
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menuLevel1Temp);

		menuLeavesTemp = active.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "navbarThird")));
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menuLeavesTemp);
		
		menuLevel1 = new WebElementArrayList(menuLevel1Temp, "li");
		
		if(!menuLevel1.getWebElementList().isEmpty()) {
			GLog.logRecordTime(0, "加载结果树一级菜单[" + menuLevel1.getStringList().toString() + "]成功");
		}
	}
	
	/**
	 * 点击一级菜单
	 * 可能会显示二级菜单
	 * 
	 * @param dir 一级菜单名称
	 */
	public void clickMenuLevel1(String dir) {
		menuLevel1.click(dir);
		menuLeaves = new WebElementArrayList(menuLeavesTemp, "span", "class", "new-node-name");
		GLog.logRecordTime(0, "加载结果树一级菜单[" + dir + "]成功");
		if(null != menuLeaves && !menuLeaves.getWebElementList().isEmpty()) {
			GLog.logRecordTime(0, "加载结果树叶子菜单[" + menuLeaves.getStringList().toString() + "]成功");
		}
	}
	
	/**
	 * 点击叶子菜单
	 * 
	 * @param name 叶子菜单名称
	 */
	public void clickLeaf(String name) {
		menuLeaves.click(name);
	}
	
	/**
	 * 点击叶子菜单
	 *
	 * @param webDriver 目标驱动
	 * @param name 叶子菜单名称
	 */
	public void clickLeafByText(WebDriver webDriver, String name) {
		WebElement leaf = QueryElement.ui_T(webDriver, menuLeavesTemp, "span", name);
		if(null != leaf) {
			try {
				leaf.click();
			}catch(Exception e) {
				GLog.logRecordTime(0, e.getMessage());
			}
		}
	}
}
