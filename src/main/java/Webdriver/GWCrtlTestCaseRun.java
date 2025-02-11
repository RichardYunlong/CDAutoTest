package Webdriver;

import io.qameta.allure.Step;
import Sys.GStatic;
import Test.GTestCaseRun;
import page.page.GHome;
import page.page.GLogin;
import page.widget.PrivacyStatement;
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
    private GTestCaseRun gTestCaseRun = new GTestCaseRun();
    public WebDriver gDr = null;

    @BeforeClass
    @Step("测试工具初始化")
    public static void beforeClass() {
        //noinspection InstantiationOfUtilityClass
        new GWCtrlWebElementId();
    }

    @Before
    @Step("测试工具初始化、保密协议、登入、切换租户")
    public void before() {
        gTestCaseRun.setTestFacilityByTestMode();
        gDr = gTestCaseRun.getGwedriver().getG_Dr();

        PrivacyStatement privacyStatement = new PrivacyStatement(gDr);
        privacyStatement.agree();

        GLogin login = new GLogin(gDr);
        login.showUnitsHash();
        login.getCas().signIn(gDr, GStatic.gTransfer.getKeyStorePath(), GStatic.gTransfer.getKeyStorePW());
        login.waitHomePage(gDr);
    }

    @After
    @Step("登出")
    public void after() {
        GHome home = new GHome(gDr);
        home.openSetting(gDr);
        home.getSetting().signOut(gDr);
        home.waitLoginPage(gDr);

        gTestCaseRun.clearTestFacilityByTestMode();
    }

    @SuppressWarnings("EmptyMethod")
    @AfterClass
    @Step("测试工具清理")
    public static void afterClass() {
        //noinspection UnnecessarySemicolon
        ;
    }

    /**
     *  切换租户
     *
     * @param tenantName 租户名称
     */
    public void switchTenant(String tenantName){
        GHome home = new GHome(gDr);
        if(GParam.getServerUrl().contains("yonyoucloud")) {
            home.openSetting(gDr);
            home.getSetting().changeOrg(gDr, tenantName);
        }
        home.waitHomePage(gDr);
    }

    /**
     *  打开菜单
     *
     *  @param org 应用中心菜单树选择，。仅支持长度为2的String[]，即一级菜单：大领域云
     *  @param product 应用中心结果树选择。仅支持长度为2的String[]，即三级菜单：子产品
     *  @param module 应用中心结果树选择。仅支持长度为2的String[]，即四级菜单：模块
     *  @param node 应用中心结果树选择。仅支持长度为2的String[]，即五级菜单：节点
     */
    public void openMenu(String org, String product, String module, String node){
        GHome home = new GHome(gDr);
        home.openMenuWarp(gDr);
        home.getMenuWarp().click(gDr, org, product, module, node);
        home.waitHomePage(gDr);
    }

    /**
     *  打开菜单
     *
     *  @param org 应用中心菜单树选择，。仅支持长度为2的String[]，即一级菜单：大领域云
     *  @param department 应用中心结果树选择。仅支持长度为2的String[]，即二级菜单：领域云
     *  @param product 应用中心结果树选择。仅支持长度为2的String[]，即三级菜单：子产品
     *  @param module 应用中心结果树选择。仅支持长度为2的String[]，即四级菜单：模块
     *  @param node 应用中心结果树选择。仅支持长度为2的String[]，即五级菜单：节点
     */
    public void openMenu(String org, String department, String product, String module, String node){
        GHome home = new GHome(gDr);
        home.openMenuWarp(gDr);
        home.getMenuWarp().click(gDr, org, department, product, module, node);
        home.waitHomePage(gDr);
    }

    /**
     *  切换租户
     *
     * @param topTabName 顶部页签名称
     */
    public void refreshTopTab(String topTabName){
        GHome home = new GHome(gDr);
        home.refreshTopTab(gDr, topTabName);
        home.waitHomePage(gDr);
    }

    /**
     *  关闭页签
     *
     * @param topTabName 顶部页签名称
     */
    public void closeTopTab(String topTabName){
        GHome home = new GHome(gDr);
        home.clickTopTab(gDr, topTabName);
        home.closeTopTab(gDr, topTabName);
        home.waitHomePage(gDr);
    }
}
