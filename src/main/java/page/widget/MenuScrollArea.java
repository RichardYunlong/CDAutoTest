package page.widget;

import DT.GLog;
import Sys.GStatic;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlWait;
import page.baseused.WebElementArrayList;
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
	 *二级菜单
	 */
	private ScrollareaLeftChild scrollareaLeftChild = null;

    /**
     * 是否存在二级菜单
     */
    private boolean isScrollareaLeftChild = true;

	/**
	 *构造函数
	 * 使用("span", "class", "topText-domain")查找到的为大领域云菜单
	 *
	 * @param webDriver WebDriver对象
	 * @param root 能都唯一定位菜单树区域的WebElement对象
	 */
	public MenuScrollArea(WebDriver webDriver, WebElement root) {
		menus = root;
		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menus);
		menusList = new WebElementArrayList(menus, "span", "class", "topText-domain");
		
		if(!menusList.getWebElementArrayList().isEmpty()) {
            String menusListTemp = menusList.getStringArrayList().toString();
            if(GStatic.gWebDiverParam.getBrowserLogType().equals("mainChinese")){
                menusListTemp = "......";
            }
			GLog.logRecordTime(9, "加载菜单树[" + menusListTemp + "]成功，得到目标节点" + menusList.size() + "个");
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
	public void click(WebDriver webDriver, String dir) {
		WebElement menu;
		menu = menusList.getWebElement(dir);

		GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, menu);
		WebElement foldFlag = null;

		try{
			foldFlag = menu.findElement(By.xpath("../../..")).findElement(By.tagName("ul"));
		}catch (Exception e){
			GLog.logRecordTime(9, "一级菜单没有折叠或者此一级菜单没有下属二级菜单");
		}

		//如果没有折叠，则直接点击一级菜单
		if(null != foldFlag){
			//先设置为“收起”
			@SuppressWarnings("UnusedAssignment") String foldStatus = "";
			try{
				foldStatus = foldFlag.getAttribute("class");

				//如果是收起状态，则点击
				if(foldStatus.contains("fold")){
					menu.click();
				}
			}catch (Exception e){
				GLog.logRecordTime(9, "一级菜单的折叠无效");
			}
		}else{
            isScrollareaLeftChild = false;
            menu.click();
        }

		scrollareaLeftChild = new ScrollareaLeftChild(webDriver, menu.findElement(By.xpath("../../..")));
		GLog.logRecordTime(9, "点击菜单[" + dir + "]成功");
	}

	/**
	 * 点击一级菜单
	 * 可能会显示二级菜单
	 *
	 * @param webDriver WebDriver对象
	 * @param dir 菜单名称
	 */
	public void clickChild(WebDriver webDriver, String dir){
        if(isScrollareaLeftChild){
            scrollareaLeftChild.clickMenuChild(webDriver, dir);
            GLog.logRecordTime(9, "点击子菜单[" + dir + "]成功");
        }
	}
}
