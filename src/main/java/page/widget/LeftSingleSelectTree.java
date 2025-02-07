package page.widget;

import Base.GText;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlInputFill;
import Webdriver.GWCtrlWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.base.UniqueWebElementBase;
import page.baseused.WebElementArrayList;
import page.baseused.WebElementHashMap;

/**
 * 左侧单选树
 * 1.支持搜索。输入查询条件点击回车或者搜索按钮后触发查询
 * 2.单击节点后触发查询
 */
public class LeftSingleSelectTree extends UniqueWebElementBase {

    /**
     * 搜索按钮
     */
    private WebElement leftSingleSelectTreeRoot;

    /**
     * 搜索框
     */
    private WebElement input;

    /**
     * 搜索按钮
     */
    private WebElement searchBtn;

    /**
     * 组织树
     */
    private WebElementArrayList orgTree;

    /**
     * 组织树
     *
     * @param webDriver 目标驱动
     * @param locateTagName 定位标签名
     * @param locateAtrributeName 定位属性名
     * @param locateArributeValue 定位属性值
     */
    public LeftSingleSelectTree(WebDriver webDriver, String locateTagName, String locateAtrributeName, String locateArributeValue) {
        super(webDriver, locateTagName, locateAtrributeName, locateArributeValue);
        leftSingleSelectTreeRoot = super.getUniqueRoot();
        input = leftSingleSelectTreeRoot.findElement(By.cssSelector(GText.getCssSelectorTxt("input", "fieldid", "yontest_task_exec_newTreeTable|children|search")));
        searchBtn = leftSingleSelectTreeRoot.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "fieldid", "yontest_task_exec_newTreeTable|children|search_search")));
        orgTree = new WebElementArrayList(webDriver, locateTagName, locateAtrributeName, locateArributeValue, "a", "class", "wui-tree-node-content-wrapper wui-tree-node-content-wrapper-normal");
    }

    /**
     * 组织树
     *
     * @param webDriver 目标驱动
     * @param orgName 组织名称
     */
    public void input(WebDriver webDriver, String orgName){
        if(null != input) {
            GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, input);
            GWCtrlInputFill.ByWebElement(webDriver, input, orgName);
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "class", "new-filter-container"));
        }
        reload(webDriver);
    }

    /**
     * 点击搜索按钮
     *
     * @param webDriver 目标驱动
     */
    public void clickSeach(WebDriver webDriver){
        if(null != searchBtn) {
            searchBtn.click();
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "class", "new-filter-container"));
        }
        reload(webDriver);
    }

    /**
     * 点击搜索按钮
     *
     * @param webDriver 目标驱动
     * @param orgName 组织名称
     */
    public void clickOrgName(WebDriver webDriver, String orgName){
        if(null != orgTree) {
            orgTree.getWebElement(orgName).click();
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "class", "new-filter-container"));
        }
        reload(webDriver);
    }

    /**
     * 页面刷新，加载新元素
     *
     * @param webDriver 目标驱动
     */
    private void reload(WebDriver webDriver){
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "class", "left-container jDiwork-container"));
        leftSingleSelectTreeRoot = webDriver.findElement(By.cssSelector( GText.getCssSelectorTxt("div", "class", "left-container jDiwork-container")));

        if(null != leftSingleSelectTreeRoot){
            input = leftSingleSelectTreeRoot.findElement(By.cssSelector(GText.getCssSelectorTxt("input", "fieldid", "yontest_task_exec_newTreeTable|children|search")));
            searchBtn = leftSingleSelectTreeRoot.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "fieldid", "yontest_task_exec_newTreeTable|children|search_search")));
            orgTree = new WebElementArrayList(webDriver, "div","class", "left-container jDiwork-container", "a", "class", "wui-tree-node-content-wrapper wui-tree-node-content-wrapper-normal");
        }
    }
}
