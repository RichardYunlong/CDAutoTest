package page.widget;

import DT.GLog;
import Webdriver.GParam;
import Webdriver.GTestIndicators;
import Webdriver.GWCtrlMouseMove;
import Webdriver.GWCtrlWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.base.UniqueWebElementBase;
import page.baseused.WebElementHashMap;

/**
 * 悬停菜单
 * 1.展开为悬停
 * 2.收起为常驻
 * 3.点击右键
 */
public class HoverMenu extends UniqueWebElementBase {
    /**
     * 悬停菜单
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal", "FieldCanBeLocal"})
    private WebElement hoverMenuRoot;

    /**
     * 右键菜单对象
     */
    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CanBeFinal"})
    private WebElement rightMenu;

    /**
     * 右键菜单
     */
    WebElementHashMap rightClick;

    /**
     * 构造函数
     *
     * @param webDriver 浏览器驱动
     * @param row 行本身
     */
    public HoverMenu(WebDriver webDriver, WebElement row) {
        super(row);
        hoverMenuRoot = super.getUniqueRoot();
        WebElement target = hoverMenuRoot.findElement(By.cssSelector(GParam.getCssSelectorBy3K("表格_悬停菜单")));
        GWCtrlMouseMove.ToElement(webDriver, target);
        super.clickRight(webDriver, target);
        GWCtrlWait.ViewWaitingAllByCssSelector(webDriver, GTestIndicators.PageShowTime, GParam.getCssSelectorBy3K("表格_悬停菜单内容"));
        rightMenu = webDriver.findElement(By.cssSelector(GParam.getCssSelectorBy3K("表格_悬停菜单内容")));
        if(null != rightMenu){
            rightClick = new WebElementHashMap(webDriver, rightMenu, GParam.getCssSelectorBy3K("表格_悬停菜单节点"));
        }
    }

    /**
     * 是否存在目标按钮
     *
     * @param buttonName 按钮名称
     *
     * @return 是否存在
     */
    public boolean isExist(String buttonName) {
        return rightClick.isContainKey(buttonName);
    }

    /**
     * 单行点击右键，在右键菜单上操作
     *
     * @param webDriver 目标驱动
     * @param buttonName 按钮名称
     */
    public void clickRight(WebDriver webDriver, String buttonName) {
        if (isExist(buttonName)) {
            rightClick.click(buttonName);
        }else{
            GLog.logRecordTime(9, "未找到按钮名称为：" + buttonName + "的按钮");
        }
    }
}
