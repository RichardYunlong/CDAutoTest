package main.java.page.unit;

import main.java.Webdriver.GTestIndicators;
import main.java.Webdriver.GWCtrlInputFill;
import main.java.Webdriver.GWCtrlWait;
import main.java.page.base.UniqueBase;
import main.java.page.base.WebElementArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  分页控件
 *  
 *  @author hewei
 *  
 *  @version 20220425170900
 */
public class Paging extends UniqueBase {
	
	private WebElementArrayList paging = null;

	/**
	 *构造函数
	 *
	 * @param webDriver 目标驱动
	 * @param locateTagName 定位标签名
	 * @param locateAtrributeName 定位属性名
	 * @param locateArributeValue 定位属性值
	 */
	public Paging(WebDriver webDriver, String locateTagName, String locateAtrributeName, String locateArributeValue) {
		super(webDriver, locateTagName, locateAtrributeName, locateArributeValue);
		
		paging = new WebElementArrayList(super.getUniqueRoot(), "li");
	}
	
	/**
	 *单页前进
	 */
	public void forward() {
		paging.click("上一页");
	}
	
	/**
	 *单页后退
	 */
	public void back() {
		paging.click("下一页");
	}
	
	/**
	 *退到第一页
	 */
	public void first() {
		paging.click("返回首页");
	}
	
	/**
	 *退到最后一页
	 */
	public void last() {
		paging.click("跳至尾页");
	}
	
	/**
	 *设置每页显示个数
	 *
	 * @param webDriver 目标驱动
	 * @param n 每页显示个数
	 */
	public void setSize(WebDriver webDriver, int n) {
		WebElement input = super.getUniqueRoot().findElement(By.id("customerdoc_listpagination-size-changer"));
		if(null != input) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, input);
		}
		
		//	
	}
	
	/**
	 *调到指定页
	 *
	 * @param webDriver 目标驱动
	 * @param pageNum 目标页
	 */
	public void jump(WebDriver webDriver, String pageNum) {
		WebElement input = super.getUniqueRoot().findElement(By.id("customerdoc_listpagination-jump"));
		if(null != input) {
			GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, input);
			GWCtrlInputFill.ByWebElement(webDriver, input, pageNum);
			GWCtrlWait.ViewWaitingAllById(webDriver, GTestIndicators.PageShowTime, "customerdoc_listpagination");
		}
	}
}
