package main.java.AutoTest;

import main.java.Base.GClazz;
import main.java.Sys.GStatic;

/**
 *  运行主体
 */
public class GRunTest {

	/**
	 *  构造函数
	 */
	public GRunTest() {
		GClazz.thisAMainClass();
	}

	/**
	 *  执行步骤
	 */
	public void runTemplate(){
		GStatic gs = new GStatic();
		gs.testInit();
		gs.testRun();
		gs.testFinish();
	}

	
	/**
	 *  全局执行入口
	 *
	 *  @param args 入参列表
	 */
	public static void main(String[] args) {
		GRunTest grt = new GRunTest();
		grt.runTemplate();
    }
}
