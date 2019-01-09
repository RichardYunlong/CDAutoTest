package AutoTestWeb;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GWCtrl {
	
	// 打开IE时使用非安全模式
	public static DesiredCapabilities StopSafety() {
		DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
		dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		// IE默认启动保护模式，要么手动在浏览器的设置中关闭保护模式，要么在代码中加上这一句，即可
		dc.setCapability("ignoreProtectedModeSettings", true);

		return dc;
	}

	// 打开首页
	public static void Open(String Url) {
		GParam.g_Dr.get(Url);
	}

	// 浏览器最大化
	public static void Maximize() {
		GParam.g_Dr.manage().window().maximize();
	}

	// 刷新浏览器
	public static void Refresh() {
		GParam.g_Dr.navigate().refresh();
	}

	// 浏览器后退
	public static void Back() {
		GParam.g_Dr.navigate().back();
	}

	// 浏览器前进
	public static void Forward() {
		GParam.g_Dr.navigate().forward();
	}

	// 找空填空
	public static void FindAndFillInputById(String inputId, String str) {
		WebElement searchBox = GParam.g_Dr.findElement(By.id(inputId));
		searchBox.sendKeys(str);
	}

	// 通过Id找提交按钮点击提交
	public static void FindAndClickButtonById(String buttonId) {
		WebElement searchButton = GParam.g_Dr.findElement(By.id(buttonId));
		searchButton.submit();
	}

	// 通过ClassName找提交按钮点击提交
	public static void FindAndClickButtonByClassName(String className) {
		WebElement searchButton = GParam.g_Dr.findElement(By.className(className));
		searchButton.submit();
	}

	// 截屏
	public static void TakesScreenshot(String imgURL) {
		File srcFile = ((org.openqa.selenium.TakesScreenshot) GParam.g_Dr).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(GOutPutCtrl.getOutputPath() + GTestCase.getTestScripstion() + imgURL));
		} catch (Exception e) {
			System.out.println("TakesScreenshoting Failed");
		}
	}

	// 关闭浏览器
	public static void Quit() {
		GParam.g_Dr.quit();
	}

	// 根据时间暂停
	public static void Pause(int mtime) {
		try {
			Thread.sleep(mtime);
		} catch (Exception e) {
			System.out.println("Pause Failed");
		}
	}

	// 智能等待
	public static void Waiting(int mtime) {
		try {
			GParam.g_Dr.manage().timeouts().implicitlyWait(mtime, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("Page Reload TimeOut");
		}
	}

	// 显式等待目标Id
	public static void ViewWaitingById(int mtime, String tagId) {
		WebDriverWait wait = new WebDriverWait(GParam.g_Dr, mtime);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(tagId)));
	}

	// 显式等待目标链接字符串
	public static void ViewWaitingByLinkText(int mtime, String linkText) {
		WebDriverWait wait = new WebDriverWait(GParam.g_Dr, mtime);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
	}
}
