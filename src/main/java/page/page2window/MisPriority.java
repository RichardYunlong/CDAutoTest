package page.page2window;

import Base.GText;
import Webdriver.GParam;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.base.UniqueWebElementBase;

/**
 * 设置优先级二级窗体
 */
public class MisPriority extends UniqueWebElementBase {

    /**
     * 悬停菜单
     */
    @SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal", "CanBeFinal", "RedundantSuppression"})
    private WebElement setPriorityRoot;

    /**
     * 当前优先级
     */
    @SuppressWarnings({"CanBeFinal"})
    private WebElement currentPriority;

    /**
     * 确认按钮
     */
    @SuppressWarnings({"CanBeFinal"})
    private WebElement okButton;

    /**
     * 下拉菜单
     */
    @SuppressWarnings("FieldCanBeLocal")
    private WebElement dropdownMenu;

    /**
     * 构造函数
     *
     * @param webDriver 浏览器驱动
     */
    public MisPriority(WebDriver webDriver) {
        super(webDriver, "div", "class", "wui-modal-content react-draggable");

        setPriorityRoot = super.getUniqueRoot();

        //添加二级窗体处理
        currentPriority = setPriorityRoot.findElement(By.cssSelector(GText.getCssSelectorTxt("input", "fieldid", "yontest_task_exec_newTreeTable|execPriority_search_input")));
        okButton = setPriorityRoot.findElement(By.cssSelector(GText.getCssSelectorTxt("button", "fieldid", "modalPriority|modal|ok")));
    }

    /**
     * 设置优先级
     *
     * @param priority 优先级
     */
    public void setPriority(WebDriver webDriver, String priority) {
        String priorityRootTemp = GParam.getCssSelectorBy3K("二级窗体_设置优先级");
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, priorityRootTemp);
        setPriorityRoot = webDriver.findElement(By.cssSelector(priorityRootTemp));

        String currentPriorityTemp = GParam.getCssSelectorBy3K("二级窗体_当前优先级");
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, currentPriorityTemp);
        currentPriority = setPriorityRoot.findElement(By.cssSelector(currentPriorityTemp));

        if(!currentPriority.getText().contains(priority)){
            currentPriority.click();

            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GParam.getCssSelectorBy3K("二级窗体_优先级下拉菜单"));
            dropdownMenu = webDriver.findElement(By.cssSelector(GParam.getCssSelectorBy3K("二级窗体_优先级下拉菜单")));

            String priorityTemp;
            switch (priority) {
                case "P3": {
                    priorityTemp = "yontest_task_exec_newTreeTable|execPriority_option_3";
                    break;
                }
                case "P2": {
                    priorityTemp = "yontest_task_exec_newTreeTable|execPriority_option_2";
                    break;
                }
                case "P1": {
                    priorityTemp = "yontest_task_exec_newTreeTable|execPriority_option_1";
                    break;
                }
                default: {
                    priorityTemp = "yontest_task_exec_newTreeTable|execPriority_option_0";
                    break;
                }
            }
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GText.getCssSelectorTxt("div", "fieldid", priorityTemp));
            WebElement P0 = dropdownMenu.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "fieldid", priorityTemp)));
            if(null != P0){
                P0.click();
            }
            String okButtonTemp = GText.getCssSelectorTxt("button", "fieldid", "modalPriority|modal|ok");
            GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, okButtonTemp);
            okButton = webDriver.findElement(By.cssSelector(okButtonTemp));
            okButton.click();
            GWCtrlWait.Waiting(4000);
        }
    }
}
