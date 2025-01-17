package page.widget;

import DT.GLog;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.base.UniqueWebElementBase;
import page.baseused.WebElementArrayList;

public class MidTab extends UniqueWebElementBase {

    /**
     *  关键根对象
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private WebElement midTabRoot;
    public WebElement getMidTabRoot() { return midTabRoot; }

    /**
     *  关键根对象
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private WebElementArrayList tabList;
    public WebElementArrayList getTabList() { return tabList; }

    public MidTab(WebDriver webDriver, String locateTagName, String locateAtrributeName, String locateArributeValue) {
        super(webDriver, locateTagName, locateAtrributeName, locateArributeValue);

        midTabRoot = getUniqueRoot();
        tabList = new WebElementArrayList(midTabRoot, "div", "role", "tab");

        if(tabList.size() < 0){
            GLog.logRecordTime(9,"未获取到有用的页签");
        }
    }
}
