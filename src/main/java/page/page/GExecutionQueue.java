package page.page;

import DT.GLog;
import Sys.GStatic;
import Webdriver.GParam;
import Webdriver.GWCtrlQuery;
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
        super(webDriver, GParam.getId("执行队列页面"));

        leftSingleSelectTree = new LeftSingleSelectTree(webDriver, "cssSelector", GParam.getCssSelectorBy3K("执行队列页面_左侧树"));

        queryScheme = new QueryScheme(webDriver, "cssSelector", GParam.getCssSelectorBy3K("执行队列页面_右侧_筛选条件"));

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
                misPriority.setPriority(webDriver, priority);
            }
        }else{
            GLog.logRecordTime(9, "未查询到任务名称为：" + misName + "的任务");
        }
    }

    /**
     * 按组织全部修改任务优先级
     *
     * @param webDriver 目标驱动
     * @param orgName 组织名称
     * @param priority 优先级
     */
    public void modifyAllPriority(WebDriver webDriver, String orgName, String priority) {
        chooseOrg(webDriver, orgName);

        queryScheme.click(webDriver, "展开");
        queryScheme.setting(webDriver, "执行状态", "待运行");
        queryScheme.click(webDriver, "搜索");
        queryScheme.click(webDriver, "收起");

        int startRowaTotal;
        GLog.logRecordTime(9,  "开始获取表格数据");
        enhanceTable.reload(webDriver);
        startRowaTotal = enhanceTable.getRows().size();
        GLog.logRecordTime(9,  "首次获取共发现[" + startRowaTotal + "]行，其中第0行为表头行，不做处理");
        int decreaseRowaTotal;//记录每次数据变化

        if(startRowaTotal > 1){
            for(int i = 1;i < startRowaTotal;i++){
                if(i > 10){
                    GLog.logRecordTime(9,  "一次仅处理10行");
                    break;
                }
                if(i < 0 || i > enhanceTable.getRows().size() - 1){
                    //GLog.logRecordTime(9,  "目标行号[" + i + "]超出列表实际有效行数[" + (enhanceTable.getRows().size()-1) + "]，请检查代码处理是否存在异常");
                    break;
                }

                if(null != enhanceTable.getRows().get(i)){
                    GWCtrlQuery.ui_V(webDriver, enhanceTable.getRows().get(i));
                    GLog.logRecordTime(9,  "将第[" + i + "]行移动到可见区域");
                    hoverMenu = new HoverMenu(webDriver, enhanceTable.getRows().get(i));
                    GLog.logRecordTime(9,  "开始处理第[" + i + "]行");

                    if(null != hoverMenu){
                        hoverMenu.click(webDriver, "设置优先级");
                        misPriority = new MisPriority(webDriver);

                        if(null != misPriority){
                            misPriority.setPriority(webDriver,priority);
                            GStatic.gP.setCounterPlusOne();
                            enhanceTable.reload(webDriver);
                            GLog.logRecordTime(9,  "重新计算后列表剩余[" + enhanceTable.getRows().size() + "]行");
                            decreaseRowaTotal = startRowaTotal - enhanceTable.getRows().size();
                            GLog.logRecordTime(9,  "减少了[" + decreaseRowaTotal + "]行");
                            i = i - decreaseRowaTotal;
                        }else{
                            GLog.logRecordTime(9,  "设置优先级失败");
                        }

                    }else{
                        GLog.logRecordTime(9,  "悬浮失败");
                    }
                }
            }
        }else{
            GLog.logRecordTime(9,  "首次获取未发现有效行");
        }
    }
}
