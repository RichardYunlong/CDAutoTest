package main.java.Webdriver;

import main.java.DT.GLog;
import main.java.Base.GDownload;
import main.java.Base.GFile;
import main.java.Base.GZip;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.io.IOException;
import java.util.Set;

/**
 *  全局浏览器驱动
 */
public class GWebDriver {

    /**
     * 全局驱动属性值清单
     */
    @SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private GBroDrParamListTemplate gBroDrParamListTemplate = new GBroDrParamListTemplate();
    public GBroDrParamListTemplate getgBroDrParamListTemplate() {
        return gBroDrParamListTemplate;
    }

    /**
     * 全局驱动变量
     */
    private WebDriver g_Dr = null;
    public WebDriver getG_Dr() {
        return g_Dr;
    }

    /**
     *  当前驱动类型名称
     */
    private String curBrsType = "";

    /**
     *  获取当前浏览器类型
     *
     *  @return 当前浏览器类型
     */
    public String getBrsType() {
        return curBrsType;
    }

    /**
     * 全局浏览器打开状态
     * true：已打开
     * false:未打开
     */
    public boolean browserOpenStatus = false;

    /**
     * 全局驱动初始化状态
     * ""：未配置
     * 不为空:已配置
     */
    public String driverStatus = "";

    /**
     *构造函数
     *
     * @param driverName 驱动类型名称
     *
     */
    public GWebDriver(String driverName) {
        this.updateLocalWebDriver();

        this.curBrsType = driverName;
        GLog.logRecordTime(0, "驱动类型[" + this.curBrsType + "]");
    }

    /**
     * 根据驱动序号设置系统属性
     */
    private void setProperties(){
        System.setProperty(gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动器逻辑路径"),
                            gBroDrParamListTemplate.getDYNAMIC_DATA().get("浏览器安装目录") + gBroDrParamListTemplate.getDYNAMIC_DATA().get("浏览器进程名称"));
        System.setProperty(gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动器逻辑全名"),
                            gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动器物理全名"));
    }

    /**
     *  打开IE时使用非安全模式
     *
     *  @return ie属性表
     */
    public DesiredCapabilities stopSafety() {
        DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
        // IE默认启动保护模式，要么手动在浏览器的设置中关闭保护模式，要么在代码中加上这一句，即可
        //dc.setCapability("ignoreProtectedModeSettings", true);
//		dc.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
//		dc.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
//		dc.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
//		dc.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
//		dc.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);

        System.out.println("浏览器-IE开启了保护模式");
        return dc;
    }

    /**
     * 更新本地驱动
     */
    public void updateLocalWebDriver() {
        //下载并更新驱动
        String saveAs = "./driver/chrome/";
        String name = gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动文件名称") + gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动文件后缀");
        String driverFileName = gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动器进程名称");
        //先删除老驱动
        GFile.clearDirectory(saveAs);
        System.out.println("删除——" + saveAs + "下的老驱动");
        //下载新驱动包
        GDownload dl = new GDownload(gBroDrParamListTemplate.getWebDriverDownloadUrl(), name ,saveAs, name);
        dl.todo();
        System.out.println("下载——" + gBroDrParamListTemplate.getWebDriverDownloadUrl() + name);
        //解压驱动包
        GZip.unzip(saveAs + name, saveAs);
        System.out.println("解压——" + name);
        //复制驱动包
        GFile.copyBinaryFile(saveAs + gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动文件名称") + "/" + driverFileName, saveAs + driverFileName);
        System.out.println("拷贝——" + driverFileName + "到" + saveAs);
        //删除无用文件
        GFile.deleteFolder(saveAs + gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动文件名称"));
        System.out.println("删除——" + saveAs + gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动文件名称"));
        GFile.deleteFile(saveAs + name);
        System.out.println("删除——" + saveAs + name);
    }

    /**
     * 设置内置驱动，可选参数如下：
     * ie
     * chrome
     * firefox
     * opera
     * safari
     * other-目前我为空
     *
     * @param strDriverName 驱动类型关键词
     */
    public void setWebDriver(String strDriverName) {
        System.out.println("开始配置驱动，切勿操作webdriver驱动文件!");
        ChromeOptions g_Opt;
        setProperties();
        switch(strDriverName) {
            case "ie":
            {
                this.g_Dr = new InternetExplorerDriver();
                //this.g_Dr = new InternetExplorerDriver(stopSafety());
                break;
            }
            case "chrome":
            {
                g_Opt = new ChromeOptions();
                g_Opt.addArguments("--whitelisted-ips=\"\"");

//                移动端模式打开
//                HashMap<String,String> mobileEmulation = new HashMap<>();//增加本行
//                mobileEmulation.put("deviceName","iPhone X");//这里是要使用的模拟器名称
//                GParam.g_Opt.setExperimentalOption("mobileEmulation",mobileEmulation);//增加本行

                this.g_Dr = new ChromeDriver(g_Opt);
                Set<Cookie> coo = this.g_Dr.manage().getCookies();
                GLog.logRecordTime(0, coo.toString());
                this.g_Dr.manage().deleteAllCookies();
                break;
            }
            case "firefox":
            {
                g_Dr = new FirefoxDriver();
                break;
            }
            case "opera":
            {
                g_Dr = new OperaDriver();
                break;
            }
            case "safari":
            {
                g_Dr = new SafariDriver();
                break;
            }
            case "edge":
            {
                g_Dr = new EdgeDriver();
                break;
            }
            default:
            {
                System.out.println("Unknown Diver Type!");
                break;
            }
        }
    }

    /**
     * 杀掉所有进程
     *
     * @param driverPid -all 按名称全部杀死；-进程号 按照进程号杀死
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public void killDriver(String driverPid) {
        if(driverPid.isEmpty()) {
            System.out.println("Diver must have more than one pid-value!");
        }else {
            try {
                if(driverPid.equals("all")) {
                    Runtime.getRuntime().exec("taskkill /f /im " + gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动器进程名称"));
                }else{
                    Runtime.getRuntime().exec("taskkill /pid " + driverPid + " -t -f");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
