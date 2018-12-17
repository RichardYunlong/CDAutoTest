package JUnitTestDemo;

//import org.junit.runner.JUnitCore;

import AutoTest.GRunTest;

public class Main {
	public static void main(String[] args) {			
		//for(int i=0;i<1;i++) {JUnitCore.runClasses(new Class[] {TestRunAll.class});}//运行测试
		for(int i=0;i<1;i++) {new GRunTest();}//运行测试
    }
}