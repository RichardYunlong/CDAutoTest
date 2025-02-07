package page.page2window;

import Base.GText;
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
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private String currentPriority;

    /**
     * 打开下拉菜单的按钮
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private WebElement dropdownButton;

    /**
     * 下拉菜单
     */
    @SuppressWarnings("FieldCanBeLocal")
    private WebElement dropdownMenu;

    /**
     * 构造函数
     */
    public MisPriority(WebDriver webDriver) {
        super(webDriver, "div", "fieldid", "modalPriority|modal");

        setPriorityRoot = super.getUniqueRoot();

        //添加二级窗体处理
        currentPriority = setPriorityRoot.findElement(By.cssSelector(GText.getCssSelectorTxt("input", "fieldid", "wui-select-selection-search"))).getText();
        dropdownButton = setPriorityRoot.findElement(By.cssSelector(GText.getCssSelectorTxt("i", "fieldid", "yontest_task_exec_newTreeTable|execPriority_suffix")));
    }

    /**
     * 设置优先级
     */
    public void setPriority(String priority) {
        if(!currentPriority.contains(priority)){
            dropdownButton.click();
            dropdownMenu = setPriorityRoot.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "fieldid", "yontest_task_exec_newTreeTable|execPriority")));

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

            WebElement P0 = dropdownMenu.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "fieldid", priorityTemp)));
            if(null != P0){
                P0.click();
            }
        }
    }
}
