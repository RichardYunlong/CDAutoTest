package JUnitTestDemo;

import AutoTest.GTestCase;
import CSDemo.CS1001;

public class TestRunReal {

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
		/* 例如 *//*
				 * case 3301:{;new CS3301();break;} case 3101:{new CS3101();new CS3207();new
				 * CS3302();new CS3208();break;}//
				 */
		default:
			;
		}
	}/*
		 * public static void main(String[] args) { // TODO 自动生成的方法存根 }
		 */
}
