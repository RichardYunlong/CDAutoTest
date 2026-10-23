package testdemo;

/**
 *  系统自检 二级自检
 *  自检范围：简单调用即可验证的功能，主要用于自检一些静态功能
 */
public class UnitTestSelfNotRun {

	/**
	 * 系统一级自检入口
	 *
	 * <p>不启动浏览器，只验证静态工具方法是否可用。</p>
	 *
	 * @param agrs 命令行参数，未使用
	 */
	public static void main(String[] agrs) {		
		//执行区
		UnitTest.testGMsg();
		UnitTest.testGTime();
	}
}
