package main.java.Webdriver;

import main.java.Base.GTime;
import main.java.DT.GLog;
import org.openqa.selenium.WebDriver;

/**
 *  时间处理功能
 */
public class GWCtrlTime {
	
	/**
	 *  计时器，单位为秒(s)
	 */
	private static int Timer = 0;
	
	/**
	 *  根据时间暂停，单位为秒(s)
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param stime 指定时间
	 */
	public static void Pause(WebDriver webDriver, int stime) {
		GLog.logRecordTime(0, "----<time>pause [" + stime + "]s");
		for(int i = stime;i >= 0;i--) {
			try {
				Thread.sleep(1000);
				System.out.println("[" + GTime.getDate() + "]----" + i + "s");
			} catch (Exception e) {
				GWCtrlException.switchTo(webDriver,e, 1, 0, "----<exception[Pause Failed]>", true);
			}
		}
	}
	
	/**
	 *  根据时间暂停，单位为秒(ms)
	 *
	 *  @param webDriver 浏览器驱动对象
	 *  @param mtime 指定时间
	 */
	public static void Pause(WebDriver webDriver, long mtime) {
		GLog.logRecordTime(0, "----<time>pause [" + mtime + "]ms");
		
		try {
			Thread.sleep(mtime);
			GLog.logRecordTime(0, "----<time>resume");
		} catch (Exception e) {
			GWCtrlException.switchTo(webDriver,e, 1, 0, "----<exception[pause failed]>", true);
		}
	}
	
	/**
	 *  设置计时器
	 *
	 * @param dTime 时间
	 */
	public static void setTimer(int dTime) {
		Timer = dTime;
	}
	
	/**
	 *  获得计时器数值
	 *
	 * @return 计时器数值
	 */
	public static int getTimer() {
		return Timer;
	}
	
	/**
	 *  正计时，单位为秒(s)
	 *
	 *  @param webDriver 浏览器驱动对象
	 *
	 *  @return 剩余时间
	 */
	public static int TimerPlus(WebDriver webDriver) {
		GWCtrlTime.Pause(webDriver, 1);
		Timer++;
		if(Timer == 0) {
			GLog.logRecordTime(0, "----<time>end of time++");
			return 0;
		}else if(Timer < -30000) {
			GLog.logRecordTime(0, "----<time>error of time++ format");
			return -1;
		}else {
			System.out.println("[" + GTime.getDate() + "]----<time>[" + Timer + "]");
		}
		return Timer;
	}
	
	/**
	 *  倒计时，单位为秒(s)
	 *
	 *  @param webDriver 浏览器驱动对象
	 *
	 *  @return 剩余时间
	 */
	public static int TimerMinus(WebDriver webDriver) {
		GWCtrlTime.Pause(webDriver, 1);
		Timer--;
		if(Timer == 0) {
			GLog.logRecordTime(0, "----<time>end of time--");
			return 0;
		}else if(Timer < -30000) {
			GLog.logRecordTime(0, "----<time>error of time-- format");
			return -1;
		}else {
			System.out.println("[" + GTime.getDate() + "]----<time>[" + Timer + "]");
		}
		return Timer;
	}
}
