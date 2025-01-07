package main.java.Webdriver;

import main.java.Base.GClazz;
import main.java.Base.GFile;
import main.java.Sys.GSys;

/**
 *  全局参数-浏览器驱动类
 */
public class GWebDiverParam {

    /**
     *  构造函数
     */
    private GWebDiverParam(){
        GClazz.thisAToolClass();
    }

    /**
     *  浏览器驱动类型
     */
    private static String browserDriverType;

    /**
     *  浏览器驱动下载地址
     */
    private static String browserDriverDownloadUrl;

    /**
     *  浏览器驱动版本号
     */
    private static String browserDriverVerision;

    /**
     *  浏览器驱动文件名
     */
    private static String browserDriverFileName;

    /**
     *  浏览器驱动保存路径
     */
    private static String browserDriverSavePath;

    /**
     *  浏览器驱动保存文件名
     */
    private static String browserDriverSaveName;

    public static String getBrowserDriverType() {
        return browserDriverType;
    }

    public static void setBrowserDriverType(String browserDriverType) {
        GWebDiverParam.browserDriverType = browserDriverType;
    }

    public static String getBrowserDriverDownloadUrl() {
        return browserDriverDownloadUrl;
    }

    public static void setBrowserDriverDownloadUrl(String browserDriverDownloadUrl) {
        GWebDiverParam.browserDriverDownloadUrl = browserDriverDownloadUrl;
    }

    public static String getBrowserDriverVerision() {
        return browserDriverVerision;
    }

    public static void setBrowserDriverVerision(String browserDriverVerision) {
        GWebDiverParam.browserDriverVerision = browserDriverVerision;
    }

    public static String getBrowserDriverFileName() {
        return browserDriverFileName;
    }

    public static void setBrowserDriverFileName(String browserDriverFileName) {
        GWebDiverParam.browserDriverFileName = browserDriverFileName;
    }

    public static String getBrowserDriverSavePath() {
        return browserDriverSavePath;
    }

    public static void setBrowserDriverSavePath(String browserDriverSavePath) {
        GWebDiverParam.browserDriverSavePath = browserDriverSavePath;
    }

    public static String getBrowserDriverSaveName() {
        return browserDriverSaveName;
    }

    public static void setBrowserDriverSaveName(String browserDriverSaveName) {
        GWebDiverParam.browserDriverSaveName = browserDriverSaveName;
    }

    /**
     *  加载参数
     */
    public static void loadConfig() {
        if((!browserDriverType.isEmpty())
                && (!browserDriverDownloadUrl.isEmpty())
                && (!browserDriverVerision.isEmpty())
                && (!browserDriverFileName.isEmpty())
                && (!browserDriverSavePath.isEmpty())
                && (!browserDriverSaveName.isEmpty())
        ){
            GFile.writeStringToGuideBottom("GWebDiverParam Params Loaded");
        }else {
            GFile.writeStringToBottom(GSys.GUIDE,"ERROR----" +"One of these database params from webDiverConfig may has no value, Please check again!");
            System.exit(0);
        }
    }
}
