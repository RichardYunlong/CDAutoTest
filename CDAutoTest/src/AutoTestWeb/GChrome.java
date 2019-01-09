package AutoTestWeb;

public class GChrome {
	public static void TestDemo(){
		GParam.setVBInfo("chrome");
		GOutPutCtrl.setOutputPath("./output/chrome/");
		GTestCase.setTestScripstion("百度查询CFCA");

		GWCtrl.Open( "https://www.baidu.com");
		GWCtrl.ViewWaitingById(10, "su");
		GWCtrl.Maximize();
		GWCtrl.Refresh();
		GWCtrl.FindAndFillInputById("kw", "中国金融认证中心");
		GWCtrl.FindAndClickButtonById("su");
		GWCtrl.ViewWaitingById(10, "content_bottom");
		GWCtrl.TakesScreenshot("_1.png");
		GWCtrl.Pause(1000);
		GWCtrl.Quit();
	}
}
