package page.widget;

import Base.GText;
import DT.GLog;
import Webdriver.GWCtrlQuery;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *  隐私申明
 */
public class PrivacyStatement {

    /**
     *  隐私申明标题
     */
    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CanBeFinal"})
    private String title;

    /**
     *  隐私申明内容
     */
    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CanBeFinal"})
    private String content;

    /**
     *  隐私申明取消按钮
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private WebElement cancel;

    /**
     *  隐私申明同意按钮
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private WebElement agree;

    /**
     *  隐私申明同意按钮
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private boolean isExist;

    /**
     *  构造函数
     *
     * @param webDriver 浏览器驱动
     */
    public PrivacyStatement(WebDriver webDriver){
        try{
            WebElement privacyStatementTemp = webDriver.findElement(By.cssSelector(GText.getCssSelectorTxt("button", "class","button_f button_accept")));
            if(null != privacyStatementTemp){
                title = GWCtrlQuery.ui_Q(webDriver, "cssSelector", GText.getCssSelectorTxt("p", "class","title")).getText();
                content = GWCtrlQuery.ui_Q(webDriver, "cssSelector", GText.getCssSelectorTxt("p", "class","content")).getText();
                cancel = GWCtrlQuery.ui_Q(webDriver, "cssSelector", GText.getCssSelectorTxt("button", "class","button_f"));
                agree = GWCtrlQuery.ui_Q(webDriver, "cssSelector", GText.getCssSelectorTxt("button", "class","button_f button_accept"));
                isExist = true;
            }
        }catch (Exception e){
            isExist = false;
            GLog.logRecordTime(9, "没有找到隐私申明区域");
        }
    }

    /**
     *  拒绝隐私申明
     */
    public void cancel(){
        if(isExist){
            cancel.click();
            GLog.logRecordTime(9, "点击[拒绝]");
        }
    }

    /**
     *  同意隐私申明
     */
    public void agree(){
        if(isExist){
            agree.click();
            GLog.logRecordTime(9, "点击[同意]");
        }
    }
}
