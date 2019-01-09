package TestDemo;

//import org.junit.runner.JUnitCore;

import AutoTest.GRunTest;

/**
 *  程序样例入口
 */
public class Main {
	public static void main(String[] args) {			
		//for(int i=0;i<1;i++) {JUnitCore.runClasses(new Class[] {TestRunAll.class});}//运行测试
		for(int i=0;i<1;i++) {new GRunTest();}//运行测试
    }
}