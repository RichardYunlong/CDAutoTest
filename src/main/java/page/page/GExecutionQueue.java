package page.page;

import Base.GText;
import DT.GLog;
import Webdriver.GWCtrlBasic;
import Webdriver.GWCtrlQuery;
import Webdriver.GWCtrlWebElementId;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import page.base.UniqueWebElementBase;
import page.table.EnhanceTable;
import page.page2window.MisPriority;
import page.widget.HoverMenu;
import page.widget.LeftSingleSelectTree;
import page.widget.QueryScheme;

/**
 * 执行队列
 */
public class GExecutionQueue extends UniqueWebElementBase {

    /**
     * 组织单选
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private LeftSingleSelectTree leftSingleSelectTree;

    /**
     * 查询方案
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private QueryScheme queryScheme;

    /**
     * 表体
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private EnhanceTable enhanceTable;

    /**
     * 表体
     */
    private HoverMenu hoverMenu;

    /**
     * 设置优先级
     */
    private MisPriority misPriority;

    /**
     * 构造函数
     *
     * @param webDriver 目标驱动
     */
    public GExecutionQueue(WebDriver webDriver) {
        super(webDriver, GWCtrlWebElementId.CN_ID.get("执行队列"));

        leftSingleSelectTree = new LeftSingleSelectTree(webDriver, "div", "class", "left-container jDiwork-container");

        queryScheme = new QueryScheme(webDriver, "div", "class", "new-filter-container");

        enhanceTable = new EnhanceTable(webDriver,
                "single",
                "div",
                "class",
                "fixedDataTableLayout_rowsContainer",
                "fixedDataTableLayout_rowsContainer",
                "fixedDataTableRowLayout_rowWrapper",
                "ScrollbarLayout_face ScrollbarLayout_faceVertical public_Scrollbar_face",
                "ScrollbarLayout_face ScrollbarLayout_faceHorizontal public_Scrollbar_face"
        );
    }

    /**
     * 选中组织
     * 当前页仅支持单选
     *
     * @param webDriver 目标驱动
     * @param orgName 组织名称
     */
    public void chooseOrg(WebDriver webDriver, String orgName) {
        leftSingleSelectTree.input(webDriver, orgName);
        leftSingleSelectTree.clickOrgName(webDriver, orgName);
        queryScheme.reload(webDriver);
    }

    /**
     * 选中组织
     * 当前页仅支持单选
     *
     * @param webDriver 目标驱动
     * @param misName 任务名称
     * @param priority 优先级P0\P1\P2\P3
     */
    public void modifyPriority(WebDriver webDriver, String misName, String priority) {
        queryScheme.click(webDriver, "展开");
        queryScheme.setting(webDriver, "任务名称", misName);
        queryScheme.click(webDriver, "搜索");
        enhanceTable.reload(webDriver);

        if(!misName.isEmpty() && !enhanceTable.getRows().isEmpty()){
            //默认修改查询到的第一行
            hoverMenu = new HoverMenu(webDriver, enhanceTable.getRows().get(1));
            if(hoverMenu.isExist("设置优先级")){
                hoverMenu.clickRight(webDriver, "设置优先级");
                misPriority = new MisPriority(webDriver);
                misPriority.setPriority(priority);
            }
        }else{
            GLog.logRecordTime(9, "未查询到任务名称为：" + misName + "的任务");
        }
    }

    /**
     * 选中组织
     * 当前页仅支持单选
     *
     * @param webDriver 目标驱动
     * @param orgName 组织名称
     * @param priority 优先级
     */
    public void modifyAllPriority(WebDriver webDriver, String orgName, String priority) {
        queryScheme.click(webDriver, "展开");
        queryScheme.setting(webDriver, "执行状态", "待执行");
        queryScheme.click(webDriver, "搜索");
        queryScheme.click(webDriver, "收起");

        enhanceTable.reload(webDriver);

        int rowaTotal = enhanceTable.getRows().size();

        if(rowaTotal > 0){
            for(int i = 0;i < rowaTotal;i++){
                GWCtrlQuery.ui_V(webDriver, enhanceTable.getRows().get(i + 1));
                enhanceTable.reload(webDriver);
                hoverMenu = new HoverMenu(webDriver, enhanceTable.getRows().get(i + 1));
                if(hoverMenu.isExist("设置优先级")){
                    hoverMenu.clickRight(webDriver, "设置优先级");
                    misPriority = new MisPriority(webDriver);
                    misPriority.setPriority(priority);
                    enhanceTable.reload(webDriver);
                }
            }
        }
    }
}
