package AutoTest;

import DT.GECharts;
import DT.GRemote;
import Test.GTestMission;

/**
 *  运行主体
 */
public class GRunTest {
	
	/**
	 *  测试任务架构
	 */
	public GRunTest() {
		
		GTestMission.tmInit();
		GTestMission.tmLogOn();
		GTestMission.tmPreErrorCode();
		GTestMission.tmDateProvider();
		GTestMission.tmStart();
		GTestMission.tmTree();
		GTestMission.tmEnd();
		GTestMission.tmOutputTestReport();
		GTestMission.tmLogOff();
	}
	
	/**
	 *  全局执行入口
	 */
	public static void main(String[] args) {
        new GRunTest();
        GECharts.report("all","[CD]" + GRemote.getIP());
    }
}
