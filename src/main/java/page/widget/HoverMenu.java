package page.widget;

import Base.GText;
import DT.GLog;
import Webdriver.GWCtrlMouseMove;
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
     * @param row 行本身
     */
    public HoverMenu(WebDriver webDriver, WebElement row) {
        super(row);
        hoverMenuRoot = super.getUniqueRoot();
        WebElement target = hoverMenuRoot.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "rowNo")));
        GWCtrlMouseMove.ToElement(webDriver, target);
        super.clickRight(webDriver, target);
        rightMenu = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "context-menu")));
        if(null != rightMenu){
            rightClick = new WebElementHashMap(webDriver, rightMenu, "a", "class", "context-menu-item");
        }
    }

    /**
     * 是否存在目标按钮
     *
     * @param buttonName 按钮名称
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
