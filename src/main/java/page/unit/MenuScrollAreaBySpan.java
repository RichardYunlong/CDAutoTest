package page.unit;

import Base.GText;
import DT.GLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 *  菜单滚动区
 */
public class MenuScrollAreaBySpan {

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
	public MenuScrollAreaBySpan(WebDriver webDriver, WebElement root) {
		menus = root;
		//GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menus);
		List<WebElement> menusTemp = menus.findElements(By.cssSelector(GText.getCssSelectorTxt("div", "fieldid", "nav-node")));
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
		for(WebElement menu : menusList){
			String title = menu.getAttribute("title");
			if(title.equals(dir)){
				menu.click();
			}
		}
	}
}
