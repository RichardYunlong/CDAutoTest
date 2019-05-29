package JUnitTestDemo;

import AutoTest.GTestCase;
import CSDemo.CS1001;

/**
 *  测试套件
 */
public class TestRunReal {

	/**
	 *  有效用例测试套件
	 */
	public TestRunReal(Integer RealTSNO) {
		switch (GTestCase.TSNO.intValue()) {
		/* 根据用例编号处理有效类 */
		case 1001:
		case 100101:
		case 100102:
		case 100103: {
			;
			new CS1001();
			break;
		}
		case 1:
		case 10010003: {
			;
			new CS1001();
			break;
		}
		default:
			;
		}
	}
}
