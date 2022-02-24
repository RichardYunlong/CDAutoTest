package testdemo;

import DT.GECharts;
import Test.GTestMission;

/**
 *  系统二级自检
 *  
 *  自检范围：需要加载整个系统才能验证的功能
 */
public class UnitTestSelfRun {


	public static void main(String[] agrs) {	
		//执行区-加载配置专用
		UnitTest.testGLoadConfig();
		
		GTestMission.tmInit();
		GTestMission.tmLogOn();
		GTestMission.tmPreErrorCode();
		GTestMission.tmDateProvider();
		
		GECharts.addDetailRport("1|2333|33333333|42222222|1111111222222221115|212121212122222222222222222212126|222222222227|212121212122222222222222222212126|212121212122222222222222222212126");

		//执行区-单元测试专用
		//UnitTest.testShowSysCfg();
		UnitTest.testConsoleDiffrentKey();
		
		GTestMission.tmOutputTestReport();
		GTestMission.tmLogOff();
	}
}
