package Webdriver;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import DT.GLog;
import Sys.GStatic;
import Test.GTestCaseRun;
import io.qameta.allure.Step;
import page.page.GHome;
import page.page.GLogin;
import page.widget.PrivacyStatement;

/**
 * UI自动化测试用例执行框架
 * 包括：1-系统初始化；2-登入、切换租户；3-自定义步骤；4-登出；5-清理系统残留
 */
public class GWCrtlTestCaseRun {

    /**
     * 系统初始化变量
     */
    @SuppressWarnings({ "FieldMayBeFinal", "CanBeFinal" })
    private GTestCaseRun gTestCaseRun = new GTestCaseRun();
    public WebDriver gDr = null;

    @SuppressWarnings("EmptyMethod")
    @BeforeClass
    @Step("系统初始化")
    public static void beforeClass() {

    }

    @Before
    @Step("测试工具初始化、保密协议、登入、切换租户")
    public void before() {
        gTestCaseRun.setTestFacilityByTestMode();
        gDr = gTestCaseRun.getGwedriver().getG_Dr();

        // noinspection InstantiationOfUtilityClass
        new GWCtrlWebElementId();
        // noinspection InstantiationOfUtilityClass
        new GWCtrlWebElementCssSelector();

        // 隐私申明处理
        try {
            PrivacyStatement privacyStatement = new PrivacyStatement(gDr);
            privacyStatement.agree();
        } catch (Exception e) {
            GLog.logRecordTime(9, "无隐私申明");
        }

        GLogin login = new GLogin(gDr);
        login.showUnitsHash();
        login.getCas().signIn(gDr, GStatic.gTransfer.getKeyStorePath(), GStatic.gTransfer.getKeyStorePW());
        login.waitHomePage(gDr);
    }

    @After
    @Step("登出")
    public void after() {
        GLog.logRecordTime(9, "全局计数器(请根据实际业务场景明确其定义):" + GStatic.gP.getCounter());

        GHome home = new GHome(gDr);
        home.openSetting(gDr);
        home.getSetting().signOut(gDr);
        home.waitLoginPage(gDr);

        gTestCaseRun.clearTestFacilityByTestMode();
    }

    @SuppressWarnings("EmptyMethod")
    @AfterClass
    @Step("系统清理并退出")
    public static void afterClass() {
        // noinspection UnnecessarySemicolon
        ;
    }

    /**
     * 切换租户
     *
     * @param tenantName 租户名称
     */
    public void switchTenant(String tenantName) {
        GHome home = new GHome(gDr);
        if (GStatic.gTransfer.getgServerUrl()[0].contains("yonyoucloud")) {
            home.openSetting(gDr);
            home.getSetting().changeOrg(gDr, tenantName, home.isWinOpen());
        }
        home.waitHomePage(gDr);
    }

    /**
     * 打开菜单
     *
     * @param org        应用中心菜单树选择，。仅支持长度为2的String[]，即一级菜单：大领域云
     * @param department 应用中心结果树选择。仅支持长度为2的String[]，即二级菜单：领域云
     * @param product    应用中心结果树选择。仅支持长度为2的String[]，即三级菜单：子产品
     * @param module     应用中心结果树选择。仅支持长度为2的String[]，即四级菜单：模块
     * @param node       应用中心结果树选择。仅支持长度为2的String[]，即五级菜单：节点
     */
    public void openMenu(String org, String department, String product, String module, String node) {
        GHome home = new GHome(gDr);
        home.openMenuWarp(gDr);
        home.getMenuWarp().click(gDr, org, department, product, module, node);
        home.waitHomePage(gDr);
    }

    /**
     * 切换租户
     *
     * @param topTabName 顶部页签名称
     */
    public void refreshTopTab(String topTabName) {
        GHome home = new GHome(gDr);
        home.refreshTopTab(gDr, topTabName);
        home.waitHomePage(gDr);
    }

    /**
     * 关闭页签
     *
     * @param topTabName 顶部页签名称
     */
    public void closeTopTab(String topTabName) {
        GHome home = new GHome(gDr);
        home.clickTopTab(gDr, topTabName);
        home.closeTopTab(gDr, topTabName);
        home.waitHomePage(gDr);
    }
}
