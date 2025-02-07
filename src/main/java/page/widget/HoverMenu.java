package page.widget;

import Base.GText;
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
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private WebElement hoverMenuRoot;

    /**
     * 右键菜单对象
     */
    @SuppressWarnings("FieldCanBeLocal")
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
    public HoverMenu(WebElement row) {
        super(row);

        hoverMenuRoot = super.getUniqueRoot();
    }

    /**
     * 单行点击右键，在右键菜单上操作
     *
     * @param webDriver 目标驱动
     * @param buttonName 按钮名称
     */
    public void clickRight(WebDriver webDriver, String buttonName) {
        super.clickRight(webDriver, hoverMenuRoot);
        rightMenu = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("div", "class", "context-menu")));
        if(null != rightMenu){
            rightClick = new WebElementHashMap(webDriver, rightMenu, "div", "class", "context-menu-item");
            rightClick.click(buttonName);
        }
    }
}
