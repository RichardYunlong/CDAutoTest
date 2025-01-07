package Webdriver;

import Base.GFile;
import DT.GLog;
import Test.GTestMission;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;

import java.io.File;

/**
 *  日志处理
 */
public class GWCtrlLog {
	
	/**
	 * image版本测试报告-存储位置
	 */
	public static final String IMAGE_PATH = GLog.getLoghome() + "/images/";
	
	/**
	 *  默认截图或视频序号，每次保存成功后加1，用于区别结果
	 */
	public static int dSaveAsIndex = 0;
	
	/**
	 *  截屏
	 *  
	 *  @param webDriver 目标驱动
	 *  @param imgName 截图对象
	 */
	public static void TakesScreenshot(WebDriver webDriver, String imgName) {
		GLog.logRecordTime(0, "[screenshot]----[img]----[[");
		File srcFile;
		try {
			srcFile = ((org.openqa.selenium.TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
			GFile.creatDir(IMAGE_PATH + GTestMission.gTestCase.getTC_SCR());
			if(imgName.isEmpty()) {
				imgName = "unNamed" + "_" + dSaveAsIndex;
			}
			GFile.copyFile(srcFile, IMAGE_PATH + GTestMission.gTestCase.getTC_SCR(), GTestMission.gTestCase.getTC_SCR() + imgName);
			dSaveAsIndex++;
			GLog.logRecordTime(0, "----<screenshot[" + GTestMission.gTestCase.getTC_SCR() + imgName + "]>image is saved");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver, e, 1, 0, "----<exception[screenshot is failed]>", true);
		}
		GLog.logRecordTime(0, "]]----[img]----[screenshot]");
	}
}
