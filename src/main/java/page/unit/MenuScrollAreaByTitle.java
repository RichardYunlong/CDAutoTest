package page.unit;

import Base.GText;
import DT.GLog;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 *  菜单滚动区
 */
public class MenuScrollAreaByTitle {

	/**
	 *WebElement对象
	 */
    @SuppressWarnings("CanBeFinal")
    WebElement menus;

	/**
	 *一级菜单
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
	private ArrayList<WebElement> menusList;

	/**
	 *构造函数
	 *
	 * @param webDriver WebDriver对象
	 * @param root 能都唯一定位菜单树区域的WebElement对象
	 */
	public MenuScrollAreaByTitle(WebDriver webDriver, WebElement root) {
		menus = root;
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menus);
		List<WebElement> menusTemp = menus.findElements(By.cssSelector(GText.getCssSelectorTxt("li", "class", "sub-menu-item")));
		menusList = new ArrayList<>(menusTemp);

		if(!menusList.isEmpty()) {
			GLog.logRecordTime(0, "加载菜单树[" + menusList + "]成功，得到目标节点" + menusList.size() + "个");
		}
	}

    /**
	 * 点击一级菜单
	 * 可能会显示二级菜单
	 *
	 * @param webDriver WebDriver对象
	 * @param dir 菜单名称
	 */
	public void click(WebDriver webDriver, String dir) {
		WebElement level1;

		for(WebElement menu : menusList){
			if(menu.getAttribute("title").equals(dir)){
				menu.click();
			}
		}
	}
}
