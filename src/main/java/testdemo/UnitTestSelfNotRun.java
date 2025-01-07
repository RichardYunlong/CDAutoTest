package main.java.testdemo;

/**
 *  系统自检 二级自检
 *  自检范围：简单调用即可验证的功能，主要用于自检一些静态功能
 */
public class UnitTestSelfNotRun {

	public static void main(String[] agrs) {		
		//执行区
		UnitTest.testGMsg();
		UnitTest.testGTime();
	}
}
