package AutoTest;

import TestClass.CS1001;

public class GTestSuite {
	public GTestSuite(Integer RealTSNO) {
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
