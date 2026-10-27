package Webdriver;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import Base.GThread;
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

    /**
     * 测试类级别的初始化钩子
     *
     * <p>当前为空实现，保留为扩展点：若后续需要在整类用例执行前做一次性准备
     * （如启动外部服务），可在此补充。</p>
     */
    @SuppressWarnings("EmptyMethod")
    @BeforeClass
    @Step("系统初始化")
    public static void beforeClass() {

    }

    /**
     * 每条用例执行前的准备：测试工具初始化、保密协议处理、登录、切换租户
     *
     * <p>顺序固定为：按测试模式装配测试设施 → 创建驱动 → 装载元素定位表 →
     * 同意隐私声明（无弹窗则跳过）→ 执行单点登录 → 等待首页就绪。</p>
     */
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
            gDr.switchTo().defaultContent();
        } catch (Exception e) {
            GLog.logRecordTime(9, "无隐私申明");
        }

        GLogin login = new GLogin(gDr);
        login.showUnitsHash();
        login.getCas().signIn(gDr, GStatic.gTransfer.getKeyStorePath(), GStatic.gTransfer.getKeyStorePW());
        login.waitHomePage(gDr);
    }

    /**
     * 每条用例执行后的收尾：登出并清理测试设施
     *
     * <p>登出属于"尽力而为"的动作，异常只记录不复抛；但 {@code finally} 中一定会执行
     * {@link GTestCaseRun#clearTestFacilityByTestMode()}，确保浏览器与驱动进程被回收，
     * 避免连续执行时残留 Chrome 进程耗尽机器资源。</p>
     *
     * <p>清理之后还会清空人形弹窗（{@link GThread#clearDragon()}）：丢弃尚未弹出的提示、
     * 关闭还在显示的弹窗，避免上一条用例的提示飘到下一条用例上，也避免拖住进程退出。</p>
     */
    @After
    @Step("登出")
    public void after() {
        // 登出属于“尽力而为”的收尾动作：
        // 无论登出是否成功，都必须执行测试设施清理(关闭浏览器、杀掉驱动进程)，
        // 否则用例一旦在登出环节失败就会残留 Chrome 进程，连续执行数十条后机器资源耗尽。
        try {
            GLog.logRecordTime(9, "全局计数器(请根据实际业务场景明确其定义):" + GStatic.gP.getCounter());

            GHome home = new GHome(gDr);
            home.openSetting(gDr);
            home.getSetting().signOut(gDr);
            home.waitLoginPage(gDr);
        } catch (Exception e) {
            GLog.logSysFunctionException("登出", e);
        } finally {
            gTestCaseRun.clearTestFacilityByTestMode();
            //放在清理之后：清理过程打的日志也会提交提示，一并丢弃
            GThread.clearDragon();
        }
    }

    /**
     * 测试类级别的清理钩子
     *
     * <p>当前为空实现，保留为扩展点；真正的资源回收在 {@link #after()} 中完成。</p>
     */
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
