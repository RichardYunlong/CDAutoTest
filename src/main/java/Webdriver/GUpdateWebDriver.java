package main.java.Webdriver;

import main.java.Base.GFile;
import main.java.Base.GMissionMsg;

/**
 *  更新驱动
 */
public class GUpdateWebDriver {

    /**
     *  原有驱动类型
     */
    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
    private String oldBroName;
    /**
     *  原有驱动版本
     */
    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
    private String oldBroVersion;

    /**
     *  目标驱动类型
     */
    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
    private String tarBroName;
    /**
     *  目标驱动版本
     */
    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
    private String tarBroVersion;

    /**
     *  最新驱动类型
     */
    @SuppressWarnings("FieldMayBeFinal")
    private String recentBroName = "";
    /**
     *  最新驱动版本
     */
    @SuppressWarnings("FieldMayBeFinal")
    private String recentBroVersion = "";

    /**
     * 构造函数
     *
     * @param gwedriver 浏览器驱动类
     */
    public GUpdateWebDriver(GWebDriver gwedriver){
        GFile.writeStringToGuideBottom(GMissionMsg.getStepTop("UPDATE WEBDRIVER START"));

        this.tarBroName = gwedriver.getgBroDrParamListTemplate().getDYNAMIC_DATA().get("浏览器类型名称");
        this.tarBroVersion = gwedriver.getgBroDrParamListTemplate().getDYNAMIC_DATA().get("浏览器版本编号");
        GFile.writeStringToGuideBottom("WANTED DRIVER NAME [" + this.tarBroName + "]");
        GFile.writeStringToGuideBottom("WANTED DRIVER VERISON [" + this.tarBroVersion + "]");

        //获得本地浏览器版本号
        this.oldBroName = gwedriver.getBrsType();
        this.oldBroVersion = "";
        GFile.writeStringToGuideBottom("CURRENT BROWSER NAME IS [" + this.oldBroName + "]");
        GFile.writeStringToGuideBottom("CURRENT BROWSER NAME IS [" + this.oldBroVersion + "]");


    }

    /**
     * 更新到需要的版本
     */
    public static void update2Wanted(){
        GFile.writeStringToGuideBottom(GMissionMsg.getStepBottom("UPDATE WEBDRIVER COMPLETE"));
    }

    /**
     * 更新到最新的版本
     */
    public static void update2Recent(){
        GFile.writeStringToGuideBottom(GMissionMsg.getStepBottom("UPDATE WEBDRIVER COMPLETE"));
    }

    /**
     * 获得本地浏览器当前版本号
     *
     * @param strDriverName 驱动类型关键字
     */
    public void getLocalBroVersion(String strDriverName) {
        switch(strDriverName) {
            case "ie":
            case "chrome":
            case "firefox":
            case "opera":
            case "safari":
            case "edge": {
                break;
            }
            default:
            {
                System.out.println("Unknown Diver Type!");
                break;
            }
        }
    }
}
