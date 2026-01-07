package page.widget;

import DT.GLog;
import Webdriver.GParam;
import Webdriver.GWCtrlQuery;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.base.UniqueWebElementBase;

/**
 *  隐私申明
 */
public class PrivacyStatement extends UniqueWebElementBase {

    /**
     *  隐私申明标题
     */
    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CanBeFinal", "RedundantSuppression"})
    private String title;

    /**
     *  隐私申明内容
     */
    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CanBeFinal", "RedundantSuppression"})
    private String content;

    /**
     *  隐私申明取消按钮
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal", "RedundantSuppression"})
    private WebElement cancel;

    /**
     *  隐私申明同意按钮
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal", "RedundantSuppression"})
    private WebElement agree;

    /**
     *  隐私申明同意按钮
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal", "RedundantSuppression"})
    private boolean isExist;

    /**
     *  构造函数
     *
     * @param webDriver 浏览器驱动
     */
    public PrivacyStatement(WebDriver webDriver){
        super(webDriver, "cssSelector", GParam.getCssSelectorBy3K("隐私申明"));
        try{
            WebElement privacyStatementTemp = super.getUniqueRoot();

            if(null != privacyStatementTemp){
                title = GWCtrlQuery.ui_Q_V(webDriver, "cssSelector", GParam.getCssSelectorBy3K("隐私申明_标题")).getText();
                content = GWCtrlQuery.ui_Q_V(webDriver, "cssSelector", GParam.getCssSelectorBy3K("隐私申明_内容")).getText();
                cancel = GWCtrlQuery.ui_Q_V(webDriver, "cssSelector", GParam.getCssSelectorBy3K("隐私申明_拒绝"));
                agree = GWCtrlQuery.ui_Q_V(webDriver, "cssSelector", GParam.getCssSelectorBy3K("隐私申明_接受"));
                isExist = true;
                GLog.logRecordTime(9, "发现隐私申明弹窗");
            }
        }catch (Exception e){
            isExist = false;
            GLog.logRecordTime(9, "隐私申明弹窗构造失败");
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
