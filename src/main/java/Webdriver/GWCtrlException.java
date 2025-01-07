package main.java.Webdriver;

import main.java.DT.GLog;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 *  顶层页签
 */
public class GWCtrlException {

	/**
	 *  切换用例类型 当切换的用例类型后，用例执行结果的处理方式将按照切换后的类型进行处理
	 *
	 *  @param webDriver 目标驱动
	 *  @param e 所有异常
	 *  @param dTSStyle 切换至用例类型：1-失败；2-错误码；3-异常中断
	 *  @param logIndex 记录到的日志编号
	 *  @param eMsg 用例信息
	 *  @param bScreenShot 是否截图
	 *  
	 *  @return b2TSStyle 是否切换成功 true-切换成功，false-切换失败
	 */
	@SuppressWarnings({"CallToPrintStackTrace", "UnusedReturnValue"})
    public static boolean switchTo (WebDriver webDriver, Exception e, int dTSStyle, int logIndex, String eMsg, boolean bScreenShot) {
		boolean b2TSStyle = false;
		try {
			//记录当前测试步骤信息至控制台和日志文件
			GLog.logRecordTime(0, eMsg + "\n" + e.getMessage());
			//控制台打印错误堆栈信息
            e.printStackTrace();
			//需要截图的时候截图
			if(bScreenShot) {
				GWCtrlAllure.makeScreenShot(webDriver, eMsg);
			}
			b2TSStyle = true;
		}catch (Exception ex) {
            GLog.logRecordTime(0, "----<exception[用例类型切换失败，用例执行结果可能无法正常保存，请执行静态审查]>");
			e.printStackTrace();
		}
		Assert.assertEquals(eMsg, 1, 0);
		return b2TSStyle;
	}
	
	/**
	 * 此处处理主动抛出的异常
	 *
	 * @param webDriver 目标驱动
	 * @param str 异常信息
	 */
	public static void throwException(WebDriver webDriver, String str) {
	  try {
	    throw new Exception(str);
      } catch (Exception e) {
        GWCtrlException.switchTo(webDriver, e, 1, 0, str, true);
      }
	}
}
