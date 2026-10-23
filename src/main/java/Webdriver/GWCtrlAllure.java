package Webdriver;

import DT.GLog;
import io.qameta.allure.Attachment;
import io.qameta.allure.Issue;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Allure测试报告美化
 * 
 * @author hewei
 *  Epic 可用作您的产品或项目的大量需求的占位符。Epic将在适当的时候分为较小的用户故事。
 *  Features 用户故事可以拆分为较小的任务，并且可以是较大的Feature和Epic的一部分。
 *  Stories/@Story 是一个标注信息注解，但是改标注可以把相同的标注统一到相同模块下用于筛选
 *  Severity(SeverityLevel.BLOCKER) 用@Severity批注测试缺陷等级，例如BLOCKER（阻断），CRITICAL（警告），NORMAL（常规），MINOR（轻微），TRIVIAL（提示）
 *  Description("测试流程描述") 测试方法描述
 *  Step 注释是对任何（公共，私有，受保护）对任何方法进行注释。例如- @Step（“输入{0}和{1}”）
 *  Attachment 附件只是带有注释的方法，@Attachment该方法返回String或byte []，应将其添加到报表中。我们可以将故障屏幕截图作为附件
 *  Links 我们可以将测试链接到某些资源，例如TMS（测试管理系统）或错误跟踪器。将测试用例链接到测试方法总是有帮助的。
*/
public class GWCtrlAllure {
	/**
	 *  截取当前页面并作为附件写入 Allure 报告
	 *
	 *  <p>附件名固定为"截图"，取当前视口的 PNG 截图。</p>
	 *
	 *  @param webDriver 浏览器驱动对象，必须已实现 {@link TakesScreenshot}
	 *  @return PNG 截图的字节数组，由 Allure 直接写入报告
	 */
	@Attachment("截图")
    public static byte[] makeScreenShot(WebDriver webDriver) {
		GLog.logRecordTime(9, "[function]----[screenshot]----[[]]");
        return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
    }
 
    /**
     *  截取当前页面并作为带标记的附件写入 Allure 报告
     *
     *  <p>附件名为"截图[标记]"，用于在同一用例内区分多张截图。</p>
     *
     *  @param webDriver 浏览器驱动对象，必须已实现 {@link TakesScreenshot}
     *  @param strMark 截图标记，会拼接到附件名中
     *  @return PNG 截图的字节数组，由 Allure 直接写入报告
     */
    @SuppressWarnings("UnusedReturnValue")
    @Attachment("截图[{0}]")
    public static byte[] makeScreenShot(WebDriver webDriver, String strMark) {
    	GLog.logRecordTime(9, "[function]----[screenshot]----[[" + strMark + "]]");
        return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
    }
   
    /**
     *  生成一条空的备注附件
     *
     *  <p>用于在报告步骤中占位，保持附件列表的对齐。</p>
     *
     *  @return 固定返回字符串"默认为空"
     */
    @SuppressWarnings("SameReturnValue")
    @Attachment("备注")
    public static String makeAttach() {
    	GLog.logRecordTime(9, "[function]----[mark]----[[]]");
    	return "默认为空";
    }

    /**
     *  生成一条带标记的备注附件
     *
     *  @param strMark 备注内容，同时作为附件名中的标记
     *  @return 原样返回 {@code strMark}
     */
    @Attachment("备注[{0}]")
    public static String makeAttach(String strMark) {
    	GLog.logRecordTime(9, "[function]----[mark]----[[" + strMark + "]]");
    	return strMark;
    }
   
    /**
     *  把类路径下的示例 CSV 文件作为附件写入 Allure 报告
     *
     *  <p>读取 {@code sample.csv}，以 {@code text/csv} 类型挂到报告上。
     *  资源缺失时会通过 {@link GWCtrlException#switchTo} 记录异常并中断。</p>
     *
     *  @param webDriver 浏览器驱动对象，仅用于异常处理时截图
     *  @return CSV 文件的字节内容
     *  @throws URISyntaxException 资源 URL 转 URI 失败时抛出
     *  @throws IOException 读取文件失败时抛出
     */
    @Issue("csv")
    @Attachment(value = "Sample csv attachment", type = "text/csv")
    public byte[] saveCsvAttachment(WebDriver webDriver) throws URISyntaxException, IOException {
    	URL resource = getClass().getClassLoader().getResource("sample.csv");
    	if (resource == null) {
            GWCtrlException.switchTo(webDriver, new Exception(""), 1, 8, "----<exception[csv file could not be opened]", true);
    	}
    	GLog.logRecordTime(9, "[function]----[file]----[[" + Objects.requireNonNull(resource) + "]]");
    	return Files.readAllBytes(Paths.get(resource.toURI()));
    }
}
