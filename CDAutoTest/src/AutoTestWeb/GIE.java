package AutoTestWeb;

public class GIE {
	public static void main(String[] args) throws InterruptedException {
		GParam.setVBInfo("ie");
		GOutPutCtrl.setOutputPath("./output/ie/");
		GTestCase.setTestScripstion("百度查询CFCA");

		GWCtrl.Open("https://www.baidu.com");
		GWCtrl.ViewWaitingById(10, "s_btn_wr");
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
