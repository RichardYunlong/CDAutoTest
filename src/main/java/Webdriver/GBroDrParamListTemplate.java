package main.java.Webdriver;

import java.util.HashMap;
import java.util.Map;

/**
 *  有界面浏览器参数表
 */
public class GBroDrParamListTemplate {

    /**
     * 有界面浏览器信息
     * 浏览器类型名称
     * 浏览器版本编号
     * 浏览器安装目录
     * 浏览器进程名称
     * 驱动器逻辑路径
     * 驱动器逻辑全名
     * 驱动器物理全名
     * 驱动器进程名称
     */
    @SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private Map<String, String> DYNAMIC_DATA = new HashMap<>();
    public Map<String, String> getDYNAMIC_DATA() {
        return DYNAMIC_DATA;
    }

    /**
     * 构造函数
     */
    public GBroDrParamListTemplate(){
        this.DYNAMIC_DATA.put("驱动下载网址", "https://registry.npmmirror.com/-/binary/chrome-for-testing/");
        this.DYNAMIC_DATA.put("操作系统类型名称", "win32");
        this.DYNAMIC_DATA.put("驱动文件名称", "chromedriver-" + this.DYNAMIC_DATA.get("操作系统类型名称"));
        this.DYNAMIC_DATA.put("驱动文件后缀", ".zip");

        //默认写入chrome配置
        this.DYNAMIC_DATA.put("浏览器类型名称", "chrome");
        this.DYNAMIC_DATA.put("浏览器版本编号", "132.0.6834.6");
        this.DYNAMIC_DATA.put("浏览器安装目录", "C:/Users/hewei/AppData/Local/Google/Chrome/Application/");
        this.DYNAMIC_DATA.put("浏览器进程名称", "chrome.exe");

        this.DYNAMIC_DATA.put("驱动器逻辑路径", "webdriver.chrome.bin");
        this.DYNAMIC_DATA.put("驱动器逻辑全名", "webdriver.chrome.driver");
        this.DYNAMIC_DATA.put("驱动器物理全名", "./driver/chrome/chromedriver.exe");
        this.DYNAMIC_DATA.put("驱动器进程名称", "chromedriver.exe");
    }

    /**
     * 设置有界面浏览器参数表
     *
     * @param broType 浏览器类型，强匹配
     */
    public void set_DYNAMIC_DATA(String broType){
        switch (broType){
            case "chrome":{
                this.DYNAMIC_DATA.put("浏览器类型名称", "chrome");
                this.DYNAMIC_DATA.put("浏览器版本编号", "132.0.6834.6");
                this.DYNAMIC_DATA.put("浏览器安装目录", "C:/Users/hewei/AppData/Local/Google/Chrome/Application/");
                this.DYNAMIC_DATA.put("浏览器进程名称", "chrome.exe");

                this.DYNAMIC_DATA.put("驱动器逻辑路径", "webdriver.chrome.bin");
                this.DYNAMIC_DATA.put("驱动器逻辑全名", "webdriver.chrome.driver");
                this.DYNAMIC_DATA.put("驱动器物理全名", "./driver/chrome/chromedriver.exe");
                this.DYNAMIC_DATA.put("驱动器进程名称", "chromedriver.exe");
            }
            case "edge":{
                this.DYNAMIC_DATA.put("浏览器类型名称", "edge");
                this.DYNAMIC_DATA.put("浏览器版本编号", "88");
                this.DYNAMIC_DATA.put("浏览器安装目录", "C:/Program Files (x86)/Microsoft/Edge/Application");
                this.DYNAMIC_DATA.put("浏览器进程名称", "msedge.exe");

                this.DYNAMIC_DATA.put("驱动器逻辑路径", "webdriver.edge.bin");
                this.DYNAMIC_DATA.put("驱动器逻辑全名", "webdriver.edge.driver");
                this.DYNAMIC_DATA.put("驱动器物理全名", "./driver/edge/msedgedriver.exe");
                this.DYNAMIC_DATA.put("驱动器进程名称", "msedgedriver.exe");
            }
            case "firefox":{
                this.DYNAMIC_DATA.put("浏览器类型名称", "firefox");
                this.DYNAMIC_DATA.put("浏览器版本编号", "76");
                this.DYNAMIC_DATA.put("浏览器安装目录", "C:/Program Files/Mozilla Firefox/");
                this.DYNAMIC_DATA.put("浏览器进程名称", "firefox.exe");

                this.DYNAMIC_DATA.put("驱动器逻辑路径", "webdriver.firefox.bin");
                this.DYNAMIC_DATA.put("驱动器逻辑全名", "webdriver.gecko.driver");
                this.DYNAMIC_DATA.put("驱动器物理全名", "./driver/firefox/geckodriver.exe");
                this.DYNAMIC_DATA.put("驱动器进程名称", "geckodriver.exe");
            }
            case "ie":{
                this.DYNAMIC_DATA.put("浏览器类型名称", "ie");
                this.DYNAMIC_DATA.put("浏览器版本编号", "11");
                this.DYNAMIC_DATA.put("浏览器安装目录", "C:/Program Files/internet explorer");
                this.DYNAMIC_DATA.put("浏览器进程名称", "iexplore.exe");

                this.DYNAMIC_DATA.put("驱动器逻辑路径", "webdriver.ie.bin");
                this.DYNAMIC_DATA.put("驱动器逻辑全名", "webdriver.ie.driver");
                this.DYNAMIC_DATA.put("驱动器物理全名", "./driver/ie/IEDriverServer.exe");
                this.DYNAMIC_DATA.put("驱动器进程名称", "IEDriverServer.exe");
            }
            case "opera":{
                this.DYNAMIC_DATA.put("浏览器类型名称", "opera");
                this.DYNAMIC_DATA.put("浏览器版本编号", "30");
                this.DYNAMIC_DATA.put("浏览器安装目录", "C:/Program Files (x86)/Opera/");
                this.DYNAMIC_DATA.put("浏览器进程名称", "launcher.exe");

                this.DYNAMIC_DATA.put("驱动器逻辑路径", "webdriver.opera.bin");
                this.DYNAMIC_DATA.put("驱动器逻辑全名", "webdriver.opera.driver");
                this.DYNAMIC_DATA.put("驱动器物理全名", "./driver/opera/Opera-driver.exe");
                this.DYNAMIC_DATA.put("驱动器进程名称", "Opera-driver.exe");
            }
            case "safari":{
                this.DYNAMIC_DATA.put("浏览器类型名称", "safari");
                this.DYNAMIC_DATA.put("浏览器版本编号", "50");
                this.DYNAMIC_DATA.put("浏览器安装目录", "C:/Program Files (x86)/Safari");
                this.DYNAMIC_DATA.put("浏览器进程名称", "Safari.exe");

                this.DYNAMIC_DATA.put("驱动器逻辑路径", "webdriver.safari.bin");
                this.DYNAMIC_DATA.put("驱动器逻辑全名", "webdriver.safari.webdriver");
                this.DYNAMIC_DATA.put("驱动器物理全名", "./driver/opera/selenium-safari-driver.jar");
                this.DYNAMIC_DATA.put("驱动器进程名称", "safaridriver.jar");
            }
            default:
        }
    }

    /**
     * 获得浏览器应用程序全路径
     *
     * @return 浏览器应用程序全路径
     */
    public String getBrowserExePath(){
        return this.DYNAMIC_DATA.get("浏览器安装目录") + this.DYNAMIC_DATA.get("浏览器类型名称") + ".exe";
    }

    /**
     * 获得下载地址全路径
     *
     * @return 下载地址全路径
     */
    public String getWebDriverDownloadUrl(){
        String urlRoot = this.DYNAMIC_DATA.get("驱动下载网址");
        String versionNo = this.DYNAMIC_DATA.get("浏览器版本编号") + "/";
        String systemWer = this.DYNAMIC_DATA.get("操作系统类型名称") + "/";

        return urlRoot + versionNo + systemWer;
    }
}
