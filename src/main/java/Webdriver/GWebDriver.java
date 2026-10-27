package Webdriver;

import Base.*;
import DT.GLog;
import Sys.GStatic;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *  全局浏览器驱动
 */
public class GWebDriver {

    /**
     * 全局驱动属性值清单
     */
    @SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private GBroDrParamListTemplate gBroDrParamListTemplate = new GBroDrParamListTemplate();
    /**
     * 获得全局驱动属性值清单
     *
     * @return 驱动属性模板，调用方修改其中的值可直接影响后续的驱动创建
     */
    public GBroDrParamListTemplate getgBroDrParamListTemplate() {
        return gBroDrParamListTemplate;
    }

    /**
     * 全局驱动变量
     */
    private WebDriver g_Dr = null;
    /**
     *  获得全局驱动变量
     *
     *  @return 全局驱动变量
     */
    public WebDriver getG_Dr() {
        return g_Dr;
    }

    /**
     *  当前驱动类型名称
     */
    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private String curBrsType;

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
        if(GStatic.gWebDiverParam.getBrowserDriverUpdate().contains("true")){
            this.updateLocalWebDriver();
        }

        this.curBrsType = driverName;
        GLog.logRecordTime(9, "驱动类型[" + this.curBrsType + "]");
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
    @SuppressWarnings("CommentedOutCode")
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

        GLog.logRecordTime(9, "浏览器-IE开启了保护模式");
        return dc;
    }

    /**
     * 更新本地驱动
     */
    public void updateLocalWebDriver() {
        String browserVersion = GBrowser.getcurrentChromeVersion(
                gBroDrParamListTemplate.getDYNAMIC_DATA().get("浏览器安装目录")
                        + gBroDrParamListTemplate.getDYNAMIC_DATA().get("浏览器进程名称"));
        String currentDriverVersion = GBrowser.getChromeDriverVersion(
                gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动器物理全名"));
        String fallbackVersion = GStatic.gWebDiverParam.getBrowserDriverVerision();
        GLog.logRecordTime(9, "本地浏览器-Chrome版本为" + browserVersion);
        GLog.logRecordTime(9, "本地历史驱动器-Chromedriver版本为" + currentDriverVersion);
        GLog.logRecordTime(9, "备用下载驱动器-Chromedriver版本为" + fallbackVersion);

        if (browserVersion.isEmpty()) {
            throw new IllegalStateException("无法获取本地Chrome版本，不能自动更新ChromeDriver");
        }
        if (sameFirstThreeVersionSegments(browserVersion, currentDriverVersion)) {
            GLog.logRecordTime(9, "本地Chrome浏览器与驱动器前三段版本一致，无需更新驱动器");
            return;
        }

        Path driverPath = Paths.get(gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动器物理全名"))
                .toAbsolutePath().normalize();
        Path driverDirectory = driverPath.getParent();
        if (driverDirectory == null) {
            throw new IllegalStateException("ChromeDriver路径无效: " + driverPath);
        }

        String archiveName = gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动文件名称")
                + gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动文件后缀");
        String driverDirectoryName = gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动文件名称");
        String driverFileName = gBroDrParamListTemplate.getDYNAMIC_DATA().get("驱动器进程名称");
        Path stagingDirectory = driverDirectory.resolve(".webdriver-update");
        List<String> candidateVersions = new ArrayList<>();
        candidateVersions.add(browserVersion);
        if (!fallbackVersion.isEmpty()
                && sameMajorVersion(browserVersion, fallbackVersion)
                && !candidateVersions.contains(fallbackVersion)
                && !fallbackVersion.equals(currentDriverVersion)) {
            candidateVersions.add(fallbackVersion);
        }

        for (String candidateVersion : candidateVersions) {
            List<String> downloadBases = new ArrayList<>();
            downloadBases.add(ensureTrailingSlash(
                    gBroDrParamListTemplate.getWebDriverDownloadUrl(candidateVersion)));
            String officialBase = "https://storage.googleapis.com/chrome-for-testing-public/"
                    + candidateVersion + "/"
                    + gBroDrParamListTemplate.getDYNAMIC_DATA().get("操作系统类型名称") + "/";
            if (!downloadBases.contains(officialBase)) {
                downloadBases.add(officialBase);
            }

            for (String downloadBase : downloadBases) {
                if (downloadAndInstallDriver(downloadBase, candidateVersion, archiveName,
                        driverDirectoryName, driverFileName, driverPath, stagingDirectory)) {
                    GLog.logRecordTime(9, "ChromeDriver已更新至版本" + candidateVersion);
                    return;
                }
            }
        }

        if (!currentDriverVersion.isEmpty() && sameMajorVersion(browserVersion, currentDriverVersion)) {
            GLog.logRecordTime(9, "ERROR----ChromeDriver自动更新失败，继续使用同主版本旧驱动 "
                    + currentDriverVersion);
            return;
        }
        throw new IllegalStateException("ChromeDriver自动更新失败；浏览器版本为"
                + browserVersion + "，当前驱动版本为" + currentDriverVersion);
    }

    /**
     * 下载指定版本的驱动压缩包、解压校验后安装到目标路径
     *
     * <p>完整流程：</p>
     * <ol>
     *   <li>在 {@code stagingDirectory} 下清理并下载 {@code archiveName}；</li>
     *   <li>从压缩包中取出驱动文件；</li>
     *   <li>校验解压出的驱动版本与 {@code version} 一致，不一致则放弃；</li>
     *   <li>覆盖写入 {@code driverPath}，再次校验版本；</li>
     *   <li>无论成败，最后清理临时目录。</li>
     * </ol>
     *
     * @param downloadBase 下载地址前缀，需以 {@code /} 结尾
     * @param version 期望的驱动版本号
     * @param archiveName 压缩包文件名，如 {@code chromedriver-win64.zip}
     * @param driverDirectoryName 压缩包内驱动所在目录名，允许为空表示根目录
     * @param driverFileName 压缩包内驱动文件名，如 {@code chromedriver.exe}
     * @param driverPath 驱动的最终安装路径
     * @param stagingDirectory 下载与解压使用的临时目录
     * @return 安装并校验成功返回 {@code true}；下载失败或版本不匹配返回 {@code false}
     */
    private boolean downloadAndInstallDriver(String downloadBase, String version, String archiveName,
                                             String driverDirectoryName, String driverFileName,
                                             Path driverPath, Path stagingDirectory) {
        Path archivePath = stagingDirectory.resolve(archiveName);
        Path extractedDriverPath = stagingDirectory.resolve("candidate").resolve(driverFileName);
        String downloadUrl = downloadBase + archiveName;
        try {
            Files.createDirectories(stagingDirectory);
            Files.deleteIfExists(archivePath);
            Files.deleteIfExists(extractedDriverPath);
            GLog.logRecordTime(9, "尝试下载ChromeDriver: " + downloadUrl);
            GDownload download = new GDownload(downloadBase, archiveName,
                    stagingDirectory.toString() + File.separator, archiveName);
            if (!download.todo()) {
                GLog.logRecordTime(9, "ChromeDriver下载失败: " + downloadUrl);
                return false;
            }

            extractDriver(archivePath, extractedDriverPath, driverDirectoryName, driverFileName);
            String downloadedVersion = GBrowser.getChromeDriverVersion(extractedDriverPath.toString());
            if (!version.equals(downloadedVersion)) {
                GLog.logRecordTime(9, "下载的ChromeDriver版本不匹配，期望"
                        + version + "，实际" + downloadedVersion + ": " + downloadUrl);
                return false;
            }

            Files.move(extractedDriverPath, driverPath, StandardCopyOption.REPLACE_EXISTING);
            String installedVersion = GBrowser.getChromeDriverVersion(driverPath.toString());
            if (!version.equals(installedVersion)) {
                throw new IOException("安装后版本校验失败，期望" + version + "，实际" + installedVersion);
            }
            return true;
        } catch (IOException e) {
            GLog.logRecordTime(9, "ChromeDriver更新失败: " + downloadUrl + "，原因: " + e.getMessage());
            return false;
        } finally {
            if (Files.exists(stagingDirectory) && !GFile.deleteFolder(stagingDirectory.toString())) {
                GLog.logRecordTime(9, "WARN----无法清理ChromeDriver临时目录: " + stagingDirectory);
            }
        }
    }

    /**
     * 从 zip 压缩包中提取驱动文件到指定位置
     *
     * <p>按 {@code 目录名/文件名} 匹配条目，同时兼容压缩包内带上层目录（以 {@code /} 结尾匹配）的情况。</p>
     *
     * @param archivePath 驱动压缩包路径
     * @param destination 驱动文件的输出路径，父目录不存在时自动创建
     * @param driverDirectoryName 压缩包内驱动所在目录名
     * @param driverFileName 压缩包内驱动文件名
     * @throws IOException 输出路径的父目录无效、压缩包读取失败或包内找不到驱动文件时抛出
     */
    private void extractDriver(Path archivePath, Path destination, String driverDirectoryName,
                               String driverFileName) throws IOException {
        String expectedEntry = driverDirectoryName.replace('\\', '/') + "/" + driverFileName;
        Path parent = destination.getParent();
        if (parent == null) {
            throw new IOException("ChromeDriver临时文件路径无效: " + destination);
        }
        Files.createDirectories(parent);

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(archivePath))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                String entryName = entry.getName().replace('\\', '/');
                if (!entry.isDirectory()
                        && (entryName.equals(expectedEntry) || entryName.endsWith("/" + expectedEntry))) {
                    Files.copy(zipInputStream, destination, StandardCopyOption.REPLACE_EXISTING);
                    return;
                }
            }
        }
        throw new IOException("压缩包中找不到驱动文件 " + expectedEntry);
    }

    /**
     * 确保字符串以 {@code /} 结尾
     *
     * @param value 原始字符串
     * @return 已补全斜杠的字符串；{@code null} 会触发 {@link NullPointerException}
     */
    private static String ensureTrailingSlash(String value) {
        return value.endsWith("/") ? value : value + "/";
    }

    /**
     * 判断两个版本号的主版本号（第一个点号之前的部分）是否相同
     *
     * @param firstVersion 第一个版本号，如 {@code 141.0.7390.65}
     * @param secondVersion 第二个版本号
     * @return 主版本号相同返回 {@code true}；任一版本号不含点号时返回 {@code false}
     */
    private static boolean sameMajorVersion(String firstVersion, String secondVersion) {
        int firstDot = firstVersion.indexOf('.');
        int secondDot = secondVersion.indexOf('.');
        return firstDot > 0 && secondDot > 0
                && firstVersion.substring(0, firstDot).equals(secondVersion.substring(0, secondDot));
    }

    /**
     * 判断两个版本号的前三段是否完全相同
     *
     * <p>Chrome 与 ChromeDriver 的主版本号一致即可配对，前三位一致则意味着补丁级别也相同，
     * 可用于更严格的匹配场景。</p>
     *
     * @param firstVersion 第一个版本号，如 {@code 141.0.7390.65}
     * @param secondVersion 第二个版本号
     * @return 三段全部相同返回 {@code true}；任一版本号不足三段时返回 {@code false}
     */
    private static boolean sameFirstThreeVersionSegments(String firstVersion, String secondVersion) {
        String[] firstSegments = firstVersion.split("\\.");
        String[] secondSegments = secondVersion.split("\\.");
        return firstSegments.length >= 3 && secondSegments.length >= 3
                && firstSegments[0].equals(secondSegments[0])
                && firstSegments[1].equals(secondSegments[1])
                && firstSegments[2].equals(secondSegments[2]);
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
    @SuppressWarnings("CommentedOutCode")
    public void setWebDriver(String strDriverName) {
        GLog.logRecordTime(9, "开始配置驱动，切勿操作webdriver驱动文件!");
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
                boolean headless = Boolean.parseBoolean(GStatic.gWebDiverParam.getBrowserHeadless());
                if (headless) {
                    g_Opt.addArguments("--headless");
                    g_Opt.addArguments("--disable-gpu");
                    g_Opt.addArguments("--window-size=1920,1080");
                }
                GLog.logRecordTime(9, headless
                        ? "Chrome以无头模式启动"
                        : "Chrome以可视模式启动");

                String chromeBinaryPath = gBroDrParamListTemplate.getDYNAMIC_DATA().get("浏览器安装目录")
                        + gBroDrParamListTemplate.getDYNAMIC_DATA().get("浏览器进程名称");
                File chromeBinary = new File(chromeBinaryPath);
                if (chromeBinary.exists()) {
                    g_Opt.setBinary(chromeBinaryPath);
                    GLog.logRecordTime(9, "浏览器-Chrome使用可执行文件[" + chromeBinaryPath + "]");
                } else {
                    GLog.logRecordTime(9, "WARN----未找到Chrome可执行文件[" + chromeBinaryPath + "]，由驱动器自行查找浏览器");
                }

//                移动端模式打开
//                HashMap<String,String> mobileEmulation = new HashMap<>();//增加本行
//                mobileEmulation.put("deviceName","iPhone X");//这里是要使用的模拟器名称
//                GParam.g_Opt.setExperimentalOption("mobileEmulation",mobileEmulation);//增加本行

                this.g_Dr = new ChromeDriver(g_Opt);
                Set<Cookie> coo = this.g_Dr.manage().getCookies();
                GLog.logRecordTime(9, coo.toString());
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
                GLog.logRecordTime(9, "Unknown Diver Type!");
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
            GLog.logRecordTime(9, "Diver must have more than one pid-value!");
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
