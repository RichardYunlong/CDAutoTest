package yonbip.登陆;

import DT.GLog;
import Sys.GStatic;
import Test.GTestCaseRun;
import Webdriver.GWCtrlWebElementCssSelector;
import Webdriver.GWCtrlWebElementId;
import io.qameta.allure.Step;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import page.page.GLogin;
import org.junit.BeforeClass;
import org.junit.Test;
import page.widget.PrivacyStatement;

public class testcase_签入 {

	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private static GTestCaseRun gTestCaseRun = new GTestCaseRun();
    public WebDriver gDr = null;
    
	@BeforeClass
    @Step("系统初始化")
	public static void beforeClass() {

	}

    @Before
    @Step("测试工具初始化、保密协议、登入、切换租户")
    public void before() {
        gTestCaseRun.setTestFacilityByTestMode();
        gDr = gTestCaseRun.getGwedriver().getG_Dr();

        //noinspection InstantiationOfUtilityClass
        new GWCtrlWebElementId();
        //noinspection InstantiationOfUtilityClass
        new GWCtrlWebElementCssSelector();

        //隐私申明处理
        try{
            PrivacyStatement privacyStatement = new PrivacyStatement(gDr);
            privacyStatement.agree();
        }catch (Exception e){
            GLog.logRecordTime(9, "无隐私申明");
        }

        GLogin login = new GLogin(gDr);
        login.showUnitsHash();
        login.getCas().signIn(gDr, GStatic.gTransfer.getKeyStorePath(), GStatic.gTransfer.getKeyStorePW());
        login.waitHomePage(gDr);
    }

	@Test
    @Step("签入")
	public void test() {
		GLog.logRecordTime(9,"仅演示签入，不进行实际操作，也不停止进程，请注意手动关闭");
	}
}
