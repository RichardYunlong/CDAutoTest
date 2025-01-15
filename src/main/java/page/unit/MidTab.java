package page.unit;

import DT.GLog;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.base.UniqueBase;
import page.base.WebElementArrayList;

public class MidTab extends UniqueBase {

    /**
     *  关键根对象
     */
    @SuppressWarnings("FieldMayBeFinal")
    private WebElement midTabRoot;
    public WebElement getMidTabRoot() { return midTabRoot; }

    /**
     *  关键根对象
     */
    @SuppressWarnings("FieldMayBeFinal")
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
