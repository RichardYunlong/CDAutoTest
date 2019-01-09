package AutoTestWeb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * 系统参数管理
 */
public class GParam {
	
	/**
	 * 有界面浏览器信息:Visible Browser Info
	 * {"浏览器名称", 
	 *  "浏览器版本号", 
	 *  "浏览器启动应用程序属性名称",
	 *  "浏览器启动应用程序安装路径"
	 *  "浏览器启动应用程序名称", 
	 *  "浏览器驱动属性名称"
	 *  "浏览器驱动程序全名",
	 *  },
	 */
	public static final String VBInfo[][] = {
			{"ie",      "11", 
			 "webdriver.ie.bin",
			 "C:/Program Files/Internet Explorer/", 
			 "iexplore.exe", 
			 "webdriver.ie.driver", 
			 "./driver/ie/IEDriverServer_x64.exe", 
			 },
			{"chrome",  "40", 
			 "webdriver.chrome.bin",
			 "C:/Users/hewei/AppData/Local/Google/Chrome/Application/", 
			 "chrome.exe", 
			 "webdriver.chrome.driver", 
			 "./driver/chrome/chromedriver.exe", 
			 },
			{"firefox", "52", 
		     "webdriver.firefox.bin",
			 "E:/Program Files/Mozilla Firefox/", 
			 "firefox.exe", 
			 "webdriver.gecko.driver", 
			 "E:/Program Files/Mozilla Firefox/geckodriver64.exe", 
			 },
			{"opera",   "30",
			 "webdriver.opera.bin",
			 "E:/Program Files (x86)/Opera/", 
			 "launcher.exe", 
			 "", 
			 "./driver/Opera-driver.exe", 
			 },
			{"safari",  "50",
		     "webdriver.safari.bin",
			 "E:/Program Files (x86)/Safari/", 
			 "safari.exe", 
			 "", 
			 "./driver/Safari-driver.exe", 
			 }
	};
	
	/**
	 * 全局驱动变量
	 */
	public static WebDriver g_Dr;
	
	/**
	 * 根据驱动序号设置系统属性
	 */
	private static void setVBInfoByDriverIndex(int dDriverIndex){
        System.setProperty(GParam.VBInfo[dDriverIndex][2], GParam.VBInfo[dDriverIndex][3]+GParam.VBInfo[dDriverIndex][4]);
        System.setProperty(GParam.VBInfo[dDriverIndex][5], GParam.VBInfo[dDriverIndex][6]);
	}
	
	/**
	 * 设置内置驱动，可选参数如下：
	 * ie
	 * chrome
	 * firefox
	 * opera
	 * safari
	 * other-目前我为空
	 */
	@SuppressWarnings("deprecation")
	public static void setVBInfo(String strDriverName) {
		int dDriverIndex = 9;
		switch(strDriverName) {
			case "ie":
			{
				dDriverIndex = 0;
				setVBInfoByDriverIndex(dDriverIndex);
				g_Dr = new InternetExplorerDriver(GWCtrl.StopSafety());
				break;
			}
			case "chrome":
			{
				dDriverIndex = 1;
				setVBInfoByDriverIndex(dDriverIndex);
				g_Dr = new ChromeDriver();
				break;
			}
			case "firefox":
			{
				dDriverIndex = 2;
				setVBInfoByDriverIndex(dDriverIndex);
				g_Dr = new FirefoxDriver();
				break;
			}
			case "opera":
			{
				dDriverIndex = 3;
				setVBInfoByDriverIndex(dDriverIndex);
				break;
			}
			case "safari":
			{
				dDriverIndex = 4;
				setVBInfoByDriverIndex(dDriverIndex);
				break;
			}
			default:
			{
				break;
			}
		}
		if(dDriverIndex == 9) {
			System.out.println("Unknown Diver Type!");
		}
	}
	
	/**
	 * 设置内置驱动，可选参数如下：
	 * 1-ie
	 * 2-chrome
	 * 3-firefox
	 * 4-opera
	 * 5-safari
	 * other-目前我为空
	 */
	@SuppressWarnings("deprecation")
	public static void setVBInfo(int dDriverIndex) {
		dDriverIndex = 9;
		switch(dDriverIndex) {
			case 0:
			{
				dDriverIndex = 0;
				setVBInfoByDriverIndex(dDriverIndex);
				g_Dr = new InternetExplorerDriver(GWCtrl.StopSafety());
				break;
			}
			case 1:
			{
				dDriverIndex = 1;
				setVBInfoByDriverIndex(dDriverIndex);
				g_Dr = new ChromeDriver();
				break;
			}
			case 2:
			{
				dDriverIndex = 2;
				setVBInfoByDriverIndex(dDriverIndex);
				g_Dr = new FirefoxDriver();
				break;
			}
			case 3:
			{
				dDriverIndex = 3;
				setVBInfoByDriverIndex(dDriverIndex);
				break;
			}
			case 4:
			{
				dDriverIndex = 4;
				setVBInfoByDriverIndex(dDriverIndex);
				break;
			}
			default:
			{
				break;
			}
		}
		if(dDriverIndex == 9) {
			System.out.println("Unknown Diver Type!");
		}
	}
}
