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
    /**
     *  获得操作系统类型标识符
     *
     *  @return 操作系统类型标识符
     */
    public String getBrowserOS() {
        return browserOS;
    }
    /**
     *  设置操作系统类型标识符
     *
     *  @param strBrowserOS 操作系统类型标识符
     */
    public void setBrowserOS(String strBrowserOS) {
        browserOS = strBrowserOS;
    }

    /**
     *  操作系统下浏览器安装目录
     */
    private String browserInstallPath;
    /**
     *  获得操作系统下浏览器安装目录
     *
     *  @return 操作系统下浏览器安装目录
     */
    public String getBrowserInstallPath() {
        return browserInstallPath;
    }
    /**
     *  设置操作系统下浏览器安装目录
     *
     *  @param strBrowserInstallPath 操作系统下浏览器安装目录
     */
    public void setBrowserInstallPath(String strBrowserInstallPath) {
        browserInstallPath = strBrowserInstallPath;
    }

    /**
     *  操作系统下浏览器启动程序名称
     */
    private String browserApplication;
    /**
     *  获得操作系统下浏览器启动程序名称
     *
     *  @return 操作系统下浏览器启动程序名称
     */
    public String getBrowserApplication() {
        return browserApplication;
    }
    /**
     *  设置操作系统下浏览器启动程序名称
     *
     *  @param strBrowserApplication 操作系统下浏览器启动程序名称
     */
    public void setBrowserApplication(String strBrowserApplication) {
        browserApplication = strBrowserApplication;
    }

    /**
     * 是否以无头模式启动Chrome
     */
    private String browserHeadless = "true";
    /**
     *  获得是否以无头模式启动Chrome
     *
     *  @return 是否以无头模式启动Chrome
     */
    public String getBrowserHeadless() {
        return browserHeadless;
    }
    /**
     *  设置是否以无头模式启动Chrome
     *
     *  @param strBrowserHeadless 是否以无头模式启动Chrome
     */
    public void setBrowserHeadless(String strBrowserHeadless) {
        browserHeadless = strBrowserHeadless;
    }

    /**
     *  是否更新浏览器驱动
     */
    private String browserDriverUpdate;
    /**
     *  获得是否更新浏览器驱动
     *
     *  @return 是否更新浏览器驱动
     */
    public String getBrowserDriverUpdate() {
        return browserDriverUpdate;
    }
    /**
     *  设置是否更新浏览器驱动
     *
     *  @param strBrowserDriverUpdate 是否更新浏览器驱动
     */
    public void setBrowserDriverUpdate(String strBrowserDriverUpdate) {
        browserDriverUpdate = strBrowserDriverUpdate;
    }

    /**
     *  浏览器驱动类型
     */
    private String browserDriverType;
    /**
     *  获得浏览器驱动类型
     *
     *  @return 浏览器驱动类型
     */
    public String getBrowserDriverType() {
        return browserDriverType;
    }
    /**
     *  设置浏览器驱动类型
     *
     *  @param strBrowserDriverType 浏览器驱动类型
     */
    public void setBrowserDriverType(String strBrowserDriverType) {
        browserDriverType = strBrowserDriverType;
    }

    /**
     *  浏览器驱动下载地址
     */
    private String browserDriverDownloadUrl;
    /**
     *  获得浏览器驱动下载地址
     *
     *  @return 浏览器驱动下载地址
     */
    public String getBrowserDriverDownloadUrl() {
        return browserDriverDownloadUrl;
    }
    /**
     *  设置浏览器驱动下载地址
     *
     *  @param strBrowserDriverDownloadUrl 浏览器驱动下载地址
     */
    public void setBrowserDriverDownloadUrl(String strBrowserDriverDownloadUrl) {
        browserDriverDownloadUrl = strBrowserDriverDownloadUrl;
    }

    /**
     *  浏览器驱动版本号
     */
    private String browserDriverVerision;
    /**
     *  获得浏览器驱动版本号
     *
     *  @return 浏览器驱动版本号
     */
    public String getBrowserDriverVerision() {
        return browserDriverVerision;
    }
    /**
     *  设置浏览器驱动版本号
     *
     *  @param strBrowserDriverVerision 浏览器驱动版本号
     */
    public void setBrowserDriverVerision(String strBrowserDriverVerision) {
        browserDriverVerision = strBrowserDriverVerision;
    }

    /**
     *  浏览器驱动文件名
     */
    private String browserDriverFileName;
    /**
     *  获得浏览器驱动文件名
     *
     *  @return 浏览器驱动文件名
     */
    public String getBrowserDriverFileName() {
        return browserDriverFileName;
    }
    /**
     *  设置浏览器驱动文件名
     *
     *  @param strBrowserDriverFileName 浏览器驱动文件名
     */
    public void setBrowserDriverFileName(String strBrowserDriverFileName) {
        browserDriverFileName = strBrowserDriverFileName;
    }

    /**
     *  浏览器驱动文件扩展名
     */
    private String browserDriverFileFormat;
    /**
     *  获得浏览器驱动文件扩展名
     *
     *  @return 浏览器驱动文件扩展名
     */
    public String getBrowserDriverFileFormat() {
        return browserDriverFileFormat;
    }
    /**
     *  设置浏览器驱动文件扩展名
     *
     *  @param strBrowserDriverFileFormat 浏览器驱动文件扩展名
     */
    public void setBrowserDriverFileFormat(String strBrowserDriverFileFormat) {
        browserDriverFileFormat = strBrowserDriverFileFormat;
    }

    /**
     *  浏览器驱动保存路径
     */
    private String browserDriverSavePath;
    /**
     *  获得浏览器驱动保存路径
     *
     *  @return 浏览器驱动保存路径
     */
    public String getBrowserDriverSavePath() {
        return browserDriverSavePath;
    }
    /**
     *  设置浏览器驱动保存路径
     *
     *  @param strBrowserDriverSavePath 浏览器驱动保存路径
     */
    public void setBrowserDriverSavePath(String strBrowserDriverSavePath) {
        browserDriverSavePath = strBrowserDriverSavePath;
    }

    /**
     *  浏览器驱动保存文件名
     */
    private String browserDriverSaveName;
    /**
     *  获得浏览器驱动保存文件名
     *
     *  @return 浏览器驱动保存文件名
     */
    public String getBrowserDriverSaveName() {
        return browserDriverSaveName;
    }
    /**
     *  设置浏览器驱动保存文件名
     *
     *  @param strBrowserDriverSaveName 浏览器驱动保存文件名
     */
    public void setBrowserDriverSaveName(String strBrowserDriverSaveName) {
        browserDriverSaveName = strBrowserDriverSaveName;
    }

    /**
     *  浏览器驱动文件解压文件夹名称
     */
    private String browserDriverDirName;
    /**
     *  获得浏览器驱动文件解压文件夹名称
     *
     *  @return 浏览器驱动文件解压文件夹名称
     */
    public String getBrowserDriverDirName() {
        return browserDriverDirName;
    }
    /**
     *  设置浏览器驱动文件解压文件夹名称
     *
     *  @param strBrowserDriverDirName 浏览器驱动文件解压文件夹名称
     */
    public void setBrowserDriverDirName(String strBrowserDriverDirName) {
        browserDriverDirName = strBrowserDriverDirName;
    }

    /**
     *  驱动器调用位置（解压到位置）
     */
    private String browserLibPath;
    /**
     *  获得驱动器调用位置（解压到位置）
     *
     *  @return 驱动器调用位置（解压到位置）
     */
    public String getBrowserLibPath() {
        return browserLibPath;
    }
    /**
     *  设置驱动器调用位置（解压到位置）
     *
     *  @param strBrowserLibPath 驱动器调用位置（解压到位置）
     */
    public void setBrowserLibPath(String strBrowserLibPath) {
        browserLibPath = strBrowserLibPath;
    }

    /**
     *  驱动器逻辑路径
     */
    private String browserDriverbin;
    /**
     *  获得驱动器逻辑路径
     *
     *  @return 驱动器逻辑路径
     */
    public String getBrowserDriverbin() {
        return browserDriverbin;
    }
    /**
     *  设置驱动器逻辑路径
     *
     *  @param strBrowserDriverbin 驱动器逻辑路径
     */
    public void setBrowserDriverbin(String strBrowserDriverbin) {
        browserDriverbin = strBrowserDriverbin;
    }

    /**
     *  驱动器逻辑名称
     */
    private String browserDriverLibName;
    /**
     *  获得驱动器逻辑名称
     *
     *  @return 驱动器逻辑名称
     */
    public String getBrowserDriverLibName() {
        return browserDriverLibName;
    }
    /**
     *  设置驱动器逻辑名称
     *
     *  @param strBrowserDriverLibName 驱动器逻辑名称
     */
    public void setBrowserDriverLibName(String strBrowserDriverLibName) {
        browserDriverLibName = strBrowserDriverLibName;
    }

    /**
     *  驱动器进程名称
     */
    private String browserDriverProcessName;
    /**
     *  获得驱动器进程名称
     *
     *  @return 驱动器进程名称
     */
    public String getBrowserDriverProcessName() {
        return browserDriverProcessName;
    }
    /**
     *  设置驱动器进程名称
     *
     *  @param strBrowserDriverProcessName 驱动器进程名称
     */
    public void setBrowserDriverProcessName(String strBrowserDriverProcessName) {
        browserDriverProcessName = strBrowserDriverProcessName;
    }

    /**
     *  驱动器进程名称
     */
    private String browserLogType;
    /**
     *  获得驱动器进程名称
     *
     *  @return 驱动器进程名称
     */
    public String getBrowserLogType() {
        return browserLogType;
    }
    /**
     *  设置驱动器进程名称
     *
     *  @param strBrowserLogType 驱动器进程名称
     */
    public void setBrowserLogType(String strBrowserLogType) {
        browserLogType = strBrowserLogType;
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
            throw new IllegalStateException("浏览器驱动配置不完整，请检查 webdriver.ini");
        }
    }
}
