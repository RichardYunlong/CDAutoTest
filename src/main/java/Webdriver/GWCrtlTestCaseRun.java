package Webdriver;

import io.qameta.allure.Step;
import Sys.GStatic;
import Test.GTestCaseRun;
import page.page.GHome;
import page.page.GLogin;
import page.unit.PrivacyStatement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

/**
 *  UI自动化测试用例执行框架
 *  包括：1-系统初始化；2-登入、切换租户；3-自定义步骤；4-登出；5-清理系统残留
 */
public class GWCrtlTestCaseRun {

    /**
     *  系统初始化变量
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private static GTestCaseRun gTestCaseRun = new GTestCaseRun();
    public static WebDriver gDr = null;

    @BeforeClass
    @Step("测试工具初始化")
    public static void beforeClass() {
        //noinspection InstantiationOfUtilityClass
        new GWCtrlWebElementId();
        gTestCaseRun.setTestFacilityByTestMode();
        gDr = gTestCaseRun.getGwedriver().getG_Dr();
    }

    @Before
    @Step("保密协议、登入、切换租户")
    public void before() {
        PrivacyStatement privacyStatement = new PrivacyStatement(gDr);
        privacyStatement.agree();

        GLogin login = new GLogin(gDr);
        login.showUnitsHash();
        login.getCas().signIn(gDr, GStatic.gTransfer.getKeyStorePath(), GStatic.gTransfer.getKeyStorePW());
        login.waitHomePage(gDr);

        GHome home = new GHome(gDr);
		home.openSetting(gDr);
		home.getSetting().changeOrg(gDr,GStatic.gTransfer.getServerName());
		home.waitHomePage(gDr);
    }

    @After
    @Step("登出")
    public void after() {
        GHome home = new GHome(gDr);
        home.openSetting(gDr);
        home.getSetting().signOut(gDr);
        home.waitLoginPage(gDr);
    }

    @AfterClass
    @Step("测试工具清理")
    public static void afterClass() {
        gTestCaseRun.clearTestFacilityByTestMode();
    }
}
