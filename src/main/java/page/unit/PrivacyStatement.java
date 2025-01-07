package page.unit;

import Base.GText;
import Webdriver.GWCtrlQuery;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  隐私申明
 */
public class PrivacyStatement {

    /**
     *  隐私申明标题
     */
    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
    private String title;

    /**
     *  隐私申明内容
     */
    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
    private String content;

    /**
     *  隐私申明取消按钮
     */
    @SuppressWarnings("FieldMayBeFinal")
    private WebElement cancel;

    /**
     *  隐私申明同意按钮
     */
    @SuppressWarnings("FieldMayBeFinal")
    private WebElement agree;

    /**
     *  构造函数
     *
     * @param webDriver 浏览器驱动
     */
    public PrivacyStatement(WebDriver webDriver){
        title = GWCtrlQuery.ui_Q(webDriver, "cssSelector", GText.getCssSelectorTxt("p", "class","title")).getText();
        content = GWCtrlQuery.ui_Q(webDriver, "cssSelector", GText.getCssSelectorTxt("p", "class","content")).getText();
        cancel = GWCtrlQuery.ui_Q(webDriver, "cssSelector", GText.getCssSelectorTxt("button", "class","button_f"));
        agree = GWCtrlQuery.ui_Q(webDriver, "cssSelector", GText.getCssSelectorTxt("button", "class","button_f button_accept"));
    }

    /**
     *  拒绝隐私申明
     */
    public void cancel(){
        cancel.click();
    }

    /**
     *  同意隐私申明
     */
    public void agree(){
        agree.click();
    }
}
