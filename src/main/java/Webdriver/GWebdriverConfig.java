package main.java.Webdriver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import main.java.DUnit.GAttribute;

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
     *  数据库驱动类型
     */
    @Value("${browserDriverType}")
    private String browserDriverType;
    public String getBrowserDriverType() {
        webdriverAttribute.putAttribute("\n#浏览器驱动类型\nbrowserDriverType", browserDriverType);
        return browserDriverType;
    }

    /**
     *  数据库驱动下载地址
     */
    @Value("${browserDriverDownloadUrl}")
    private String browserDriverDownloadUrl;
    public String getBrowserDriverDownloadUrl() {
        webdriverAttribute.putAttribute("\n#浏览器驱动下载地址\nbrowserDriverDownloadUrl", browserDriverDownloadUrl);
        return browserDriverDownloadUrl;
    }

    /**
     *  数据库驱动版本
     */
    @Value("${browserDriverVerision}")
    private String browserDriverVerision;
    public String getBrowserDriverVerision() {
        webdriverAttribute.putAttribute("\n#浏览器驱动版本号\nbrowserDriverVerision", browserDriverVerision);
        return browserDriverVerision;
    }

    /**
     *  数据库驱动文件名称
     */
    @Value("${browserDriverFileName}")
    private String browserDriverFileName;
    public String getBrowserDriverFileName() {
        webdriverAttribute.putAttribute("\n#浏览器驱动文件名\nbrowserDriverFileName", browserDriverFileName);
        return browserDriverFileName;
    }

    /**
     *  数据库驱动文件保存路径
     */
    @Value("${browserDriverSavePath}")
    private String browserDriverSavePath;
    public String getBrowserDriverSavePath() {
        webdriverAttribute.putAttribute("\n#浏览器驱动保存路径\nbrowserDriverSavePath", browserDriverSavePath);
        return browserDriverSavePath;
    }

    /**
     *  数据库驱动文件保存名称
     */
    @Value("${browserDriverSaveName}")
    private String browserDriverSaveName;
    public String getBrowserDriverSaveName() {
        webdriverAttribute.putAttribute("\n#浏览器驱动保存文件名\nbrowserDriverSaveName", browserDriverSaveName);
        return browserDriverSaveName;
    }
}
