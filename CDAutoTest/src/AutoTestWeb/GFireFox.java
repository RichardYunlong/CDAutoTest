package AutoTestWeb;

public class GFireFox {
	public static void TestDemo(){
		GParam.setVBInfo("firefox");
		GOutPutCtrl.setOutputPath("./output/firefox/");
		GTestCase.setTestScripstion("百度查询CFCA");

		GWCtrl.Open("https://www.baidu.com");
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
