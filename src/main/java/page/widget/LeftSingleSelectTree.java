package page.widget;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Webdriver.GParam;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlInputFill;
import Webdriver.GWCtrlWait;
import page.base.UniqueWebElementBase;
import page.baseused.WebElementArrayList;

/**
 * 左侧单选树
 * 1.支持搜索。输入查询条件点击回车或者搜索按钮后触发查询
 * 2.单击节点后触发查询
 */
public class LeftSingleSelectTree extends UniqueWebElementBase {

    /**
     * 搜索按钮
     */
    @SuppressWarnings({ "CanBeFinal", "FieldMayBeFinal" })
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
     * @param webDriver       目标驱动
     * @param cssSelectorType 表示使用固定的cssSelector表达式
     * @param cssSelector     cssSelector表达式
     */
    public LeftSingleSelectTree(WebDriver webDriver, String cssSelectorType, String cssSelector) {
        super(webDriver, cssSelectorType, cssSelector);
        leftSingleSelectTreeRoot = super.getUniqueRoot();
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime,
                GParam.getCssSelectorBy3K("执行队列页面_左侧树_搜索框"));
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime,
                GParam.getCssSelectorBy3K("执行队列页面_左侧树_搜索"));
        input = leftSingleSelectTreeRoot.findElement(By.cssSelector(GParam.getCssSelectorBy3K("执行队列页面_左侧树_搜索框")));
        searchBtn = leftSingleSelectTreeRoot.findElement(By.cssSelector(GParam.getCssSelectorBy3K("执行队列页面_左侧树_搜索")));
        orgTree = new WebElementArrayList(webDriver, cssSelectorType, GParam.getCssSelectorBy3K("执行队列页面_左侧树"),
                GParam.getCssSelectorType("执行队列页面_左侧树_领域"),
                GParam.getCssSelectorName("执行队列页面_左侧树_领域"),
                GParam.getCssSelectorValue("执行队列页面_左侧树_领域"));
    }

    /**
     * 组织树
     *
     * @param webDriver 目标驱动
     * @param orgName   组织名称
     */
    public void input(WebDriver webDriver, String orgName) {
        if (null != input) {
            GWCtrlWait.ViewWaitingAllByWebElement(webDriver, GTestIndicators.PageShowTime, input);
            GWCtrlInputFill.ByWebElementForStatus(webDriver, input, orgName);
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime,
                    GParam.getCssSelectorBy3K("执行队列页面_右侧_筛选条件"));
        }
        reload(webDriver);
    }

    /**
     * 点击搜索按钮
     *
     * @param webDriver 目标驱动
     */
    public void clickSeach(WebDriver webDriver) {
        if (null != searchBtn) {
            searchBtn.click();
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime,
                    GParam.getCssSelectorBy3K("执行队列页面_右侧_筛选条件"));
        }
        reload(webDriver);
    }

    /**
     * 点击搜索按钮
     *
     * @param webDriver 目标驱动
     * @param orgName   组织名称
     */
    public void clickOrgName(WebDriver webDriver, String orgName) {
        if (null != orgTree) {
            orgTree.getWebElement(orgName).click();
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime,
                    GParam.getCssSelectorBy3K("执行队列页面_右侧_筛选条件"));
        }
        reload(webDriver);
    }

    /**
     * 页面刷新，加载新元素
     *
     * @param webDriver 目标驱动
     */
    private void reload(WebDriver webDriver) {
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime,
                GParam.getCssSelectorBy3K("执行队列页面_左侧树"));

        if (null != leftSingleSelectTreeRoot) {
            input = leftSingleSelectTreeRoot.findElement(By.cssSelector(GParam.getCssSelectorBy3K("执行队列页面_左侧树_搜索框")));
            searchBtn = leftSingleSelectTreeRoot
                    .findElement(By.cssSelector(GParam.getCssSelectorBy3K("执行队列页面_左侧树_搜索")));
            orgTree = new WebElementArrayList(webDriver, "cssSelector", GParam.getCssSelectorBy3K("执行队列页面_左侧树"),
                    GParam.getCssSelectorType("执行队列页面_左侧树_领域"),
                    GParam.getCssSelectorName("执行队列页面_左侧树_领域"),
                    GParam.getCssSelectorValue("执行队列页面_左侧树_领域"));
        }
    }
}
