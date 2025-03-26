package Webdriver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import DUnit.GAttribute;

/**
 *  浏览器驱动变量
 */
@Component
public class GWebdriverConfig {

    /**
     *  保存本类所有参数
     */
    @SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private static GAttribute webdriverAttribute = new GAttribute();

    public static GAttribute getWebdriverAttribute() {
        return webdriverAttribute;
    }

    /**
     *  操作系统类型标识符
     */
    @Value("${browserOS}")
    private String browserOS;
    public String getBrowserOS() {
        webdriverAttribute.putAttribute("\n#操作系统类型标识符\nbrowserOS", browserOS);
        return browserOS;
    }

    /**
     *  操作系统下浏览器安装目录
     */
    @Value("${browserInstallPath}")
    private String browserInstallPath;
    public String getBrowserInstallPath() {
        webdriverAttribute.putAttribute("\n#操作系统下浏览器安装目录\nbrowserInstallPath", browserInstallPath);
        return browserInstallPath;
    }

    /**
     *  操作系统下浏览器启动程序名称
     */
    @Value("${browserApplication}")
    private String browserApplication;
    public String getBrowserApplication() {
        webdriverAttribute.putAttribute("\n#操作系统下浏览器启动程序名称\nbrowserInstallPath", browserInstallPath);
        return browserApplication;
    }

    /**
     *  浏览器驱动类型
     */
    @Value("${browserDriverUpdate}")
    private String browserDriverUpdate;
    public String getBrowserDriverUpdate() {
        webdriverAttribute.putAttribute("\n#是否更新浏览器驱动\nbrowserDriverUpdate", browserDriverUpdate);
        return browserDriverUpdate;
    }

    /**
     *  浏览器驱动类型
     */
    @Value("${browserDriverType}")
    private String browserDriverType;
    public String getBrowserDriverType() {
        webdriverAttribute.putAttribute("\n#浏览器驱动类型\nbrowserDriverType", browserDriverType);
        return browserDriverType;
    }

    /**
     *  浏览器驱动下载地址
     */
    @Value("${browserDriverDownloadUrl}")
    private String browserDriverDownloadUrl;
    public String getBrowserDriverDownloadUrl() {
        webdriverAttribute.putAttribute("\n#浏览器驱动下载地址\nbrowserDriverDownloadUrl", browserDriverDownloadUrl);
        return browserDriverDownloadUrl;
    }

    /**
     *  浏览器驱动版本
     */
    @Value("${browserDriverVerision}")
    private String browserDriverVerision;
    public String getBrowserDriverVerision() {
        webdriverAttribute.putAttribute("\n#浏览器驱动版本号\nbrowserDriverVerision", browserDriverVerision);
        return browserDriverVerision;
    }

    /**
     *  浏览器驱动文件名称
     */
    @Value("${browserDriverFileName}")
    private String browserDriverFileName;
    public String getBrowserDriverFileName() {
        webdriverAttribute.putAttribute("\n#浏览器驱动文件名\nbrowserDriverFileName", browserDriverFileName);
        return browserDriverFileName;
    }

    /**
     *  浏览器驱动文件扩展名称
     */
    @Value("${browserDriverFileFormat}")
    private String browserDriverFileFormat;
    public String getBrowserDriverFileFormat() {
        webdriverAttribute.putAttribute("\n#浏览器驱动文件扩展名\nbrowserDriverFileFormat", browserDriverFileFormat);
        return browserDriverFileFormat;
    }

    /**
     *  浏览器驱动文件保存路径
     */
    @Value("${browserDriverSavePath}")
    private String browserDriverSavePath;
    public String getBrowserDriverSavePath() {
        webdriverAttribute.putAttribute("\n#浏览器驱动保存路径\nbrowserDriverSavePath", browserDriverSavePath);
        return browserDriverSavePath;
    }

    /**
     *  浏览器驱动文件保存名称
     */
    @Value("${browserDriverSaveName}")
    private String browserDriverSaveName;
    public String getBrowserDriverSaveName() {
        webdriverAttribute.putAttribute("\n#浏览器驱动保存文件名\nbrowserDriverSaveName", browserDriverSaveName);
        return browserDriverSaveName;
    }

    /**
     *  浏览器驱动文件解压文件夹名称
     */
    @Value("${browserDriverDirName}")
    private String browserDriverDirName;
    public String getBrowserDriverDirName() {
        webdriverAttribute.putAttribute("\n#浏览器驱动文件解压文件夹名称\nbrowserDriverSaveName", browserDriverDirName);
        return browserDriverDirName;
    }

    /**
     *  驱动器调用位置（解压到位置）
     */
    @Value("${browserLibPath}")
    private String browserLibPath;
    public String getBrowserlibPath() {
        webdriverAttribute.putAttribute("\n#驱动器调用位置（解压到位置）\nbrowserLibPath", browserLibPath);
        return browserLibPath;
    }

    /**
     *  驱动器逻辑路径
     */
    @Value("${browserDriverbin}")
    private String browserDriverbin;
    public String getBrowserDriverbin() {
        webdriverAttribute.putAttribute("\n#驱动器逻辑路径\nbrowserDriverbin", browserDriverbin);
        return browserDriverbin;
    }

    /**
     *  驱动器逻辑名称
     */
    @Value("${browserDriverLibName}")
    private String browserDriverLibName;
    public String getBrowserDriverLibName() {
        webdriverAttribute.putAttribute("\n#驱动器逻辑名称\nbrowserDriverLibName", browserDriverLibName);
        return browserDriverLibName;
    }

    /**
     *  驱动器进程名称
     */
    @Value("${browserDriverProcessName}")
    private String browserDriverProcessName;
    public String getBrowserDriverProcessName() {
        webdriverAttribute.putAttribute("\n#驱动器进程名称\nbrowserDriverProcessName", browserDriverProcessName);
        return browserDriverProcessName;
    }
}
