package main.java.Base;

import main.java.Dragon.GDragon;
import main.java.Dragon.GDragonII;
import main.java.Sys.GStatic;

/**
 *  线程管理
 */
public class GThread {
	
    /**
     *  构造函数
     */
	private GThread(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  欢迎界面
	 */
	public static void welcomeUI() {
		//此类界面显示暂时无法通过配置参数来控制，需要优化
		new Thread(() -> new GDragonII(GStatic.gAbout.getWelcome(), 0, 0)).start();
	}
	
	/**
	 *  再见界面
	 */
	public static void ByeUI() {
		//此类界面显示暂时无法通过配置参数来控制，需要优化
		new Thread(() -> new GDragon(GStatic.gAbout.getEndingMini(), 3000, 0, 0, "", null, 0, null)).start();
	}
}
