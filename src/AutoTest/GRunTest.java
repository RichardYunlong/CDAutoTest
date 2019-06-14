package AutoTest;

/**
 *  运行主体
 */
public class GRunTest {
	
	/**
	 *  测试任务架构
	 */
	public GRunTest() {
		GTestMission.SysInit();
		GTestMission.LogOn();
		GTestMission.PreErrorCode();
		GTestMission.DateProvider();
		GTestMission.TSMStart();
		GTestMission.TSMTree();
		GTestMission.TSMEnd();
		GTestMission.OutputTestReport();
		GTestMission.LogOff();
	}
	
	public static void main(String[] args) {
        new GRunTest();
    }
}
