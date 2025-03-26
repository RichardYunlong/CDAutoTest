package Webdriver;

import Base.GClazz;
import Base.GFile;
import Sys.GSys;

/**
 *  全局参数-浏览器驱动类
 */
public class GWebDiverParam {

    /**
     *  构造函数
     */
    public GWebDiverParam(){
        GClazz.thisAToolClass();
    }

    /**
     *  操作系统类型标识符
     */
    private String browserOS;
    public String getBrowserOS() {
        return browserOS;
    }
    public void setBrowserOS(String strBrowserOS) {
        browserOS = strBrowserOS;
    }

    /**
     *  操作系统下浏览器安装目录
     */
    private String browserInstallPath;
    public String getBrowserInstallPath() {
        return browserInstallPath;
    }
    public void setBrowserInstallPath(String strBrowserInstallPath) {
        browserInstallPath = strBrowserInstallPath;
    }

    /**
     *  操作系统下浏览器启动程序名称
     */
    private String browserApplication;
    public String getBrowserApplication() {
        return browserApplication;
    }
    public void setBrowserApplication(String strBrowserApplication) {
        browserApplication = strBrowserApplication;
    }

    /**
     *  是否更新浏览器驱动
     */
    private String browserDriverUpdate;
    public String getBrowserDriverUpdate() {
        return browserDriverUpdate;
    }
    public void setBrowserDriverUpdate(String strBrowserDriverUpdate) {
        browserDriverUpdate = strBrowserDriverUpdate;
    }

    /**
     *  浏览器驱动类型
     */
    private String browserDriverType;
    public String getBrowserDriverType() {
        return browserDriverType;
    }
    public void setBrowserDriverType(String strBrowserDriverType) {
        browserDriverType = strBrowserDriverType;
    }

    /**
     *  浏览器驱动下载地址
     */
    private String browserDriverDownloadUrl;
    public String getBrowserDriverDownloadUrl() {
        return browserDriverDownloadUrl;
    }
    public void setBrowserDriverDownloadUrl(String strBrowserDriverDownloadUrl) {
        browserDriverDownloadUrl = strBrowserDriverDownloadUrl;
    }

    /**
     *  浏览器驱动版本号
     */
    private String browserDriverVerision;
    public String getBrowserDriverVerision() {
        return browserDriverVerision;
    }
    public void setBrowserDriverVerision(String strBrowserDriverVerision) {
        browserDriverVerision = strBrowserDriverVerision;
    }

    /**
     *  浏览器驱动文件名
     */
    private String browserDriverFileName;
    public String getBrowserDriverFileName() {
        return browserDriverFileName;
    }
    public void setBrowserDriverFileName(String strBrowserDriverFileName) {
        browserDriverFileName = strBrowserDriverFileName;
    }

    /**
     *  浏览器驱动文件扩展名
     */
    private String browserDriverFileFormat;
    public String getBrowserDriverFileFormat() {
        return browserDriverFileFormat;
    }
    public void setBrowserDriverFileFormat(String strBrowserDriverFileFormat) {
        browserDriverFileFormat = strBrowserDriverFileFormat;
    }

    /**
     *  浏览器驱动保存路径
     */
    private String browserDriverSavePath;
    public String getBrowserDriverSavePath() {
        return browserDriverSavePath;
    }
    public void setBrowserDriverSavePath(String strBrowserDriverSavePath) {
        browserDriverSavePath = strBrowserDriverSavePath;
    }

    /**
     *  浏览器驱动保存文件名
     */
    private String browserDriverSaveName;
    public String getBrowserDriverSaveName() {
        return browserDriverSaveName;
    }
    public void setBrowserDriverSaveName(String strBrowserDriverSaveName) {
        browserDriverSaveName = strBrowserDriverSaveName;
    }

    /**
     *  浏览器驱动文件解压文件夹名称
     */
    private String browserDriverDirName;
    public String getBrowserDriverDirName() {
        return browserDriverDirName;
    }
    public void setBrowserDriverDirName(String strBrowserDriverDirName) {
        browserDriverDirName = strBrowserDriverDirName;
    }

    /**
     *  驱动器调用位置（解压到位置）
     */
    private String browserLibPath;
    public String getBrowserLibPath() {
        return browserLibPath;
    }
    public void setBrowserLibPath(String strBrowserLibPath) {
        browserLibPath = strBrowserLibPath;
    }

    /**
     *  驱动器逻辑路径
     */
    private String browserDriverbin;
    public String getBrowserDriverbin() {
        return browserDriverbin;
    }
    public void setBrowserDriverbin(String strBrowserDriverbin) {
        browserDriverbin = strBrowserDriverbin;
    }

    /**
     *  驱动器逻辑名称
     */
    private String browserDriverLibName;
    public String getBrowserDriverLibName() {
        return browserDriverLibName;
    }
    public void setBrowserDriverLibName(String strBrowserDriverLibName) {
        browserDriverLibName = strBrowserDriverLibName;
    }

    /**
     *  驱动器进程名称
     */
    private String browserDriverProcessName;
    public String getBrowserDriverProcessName() {
        return browserDriverProcessName;
    }
    public void setBrowserDriverProcessName(String strBrowserDriverProcessName) {
        browserDriverProcessName = strBrowserDriverProcessName;
    }

    /**
     *  加载参数
     */
    public void loadConfig() {
        if((!browserDriverUpdate.isEmpty())
                &&(!browserDriverType.isEmpty())
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
