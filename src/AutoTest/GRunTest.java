package AutoTest;

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
	
	public static void main(String[] args) {
        new GRunTest();
    }
}
