package Test;

import testdemo.CS1001;

/**
 * 套件管理
 */
public class GTestSuite {

	/**
	 * 构造函数
	 */
	public GTestSuite() {
		switch (GTestMission.gTestCase.getTC_NO()) {
		/* 根据用例编号处理有效类 */
		case 1001:
		case 100101:
		case 100102:
		case 100103: {
			new CS1001();
			break;
		}
		case 1:
		case 10010003: {
			new CS1001();
			break;
		}
		default:
			break;
		}
	}
}
