package Base;

import Dragon.GDragonGuide;
import Dragon.GDragonService;
import Sys.GStatic;

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
 *
 *  <p>界面显示统一交给 {@link GDragonService}：开关由 sys.DragonWelcome 控制，
 *  也可用 {@code -Dcdtest.dragon.welcome=false} 关闭，不再需要改代码。</p>
 *
 *  <p>任务开始时先清空提示弹窗，避免上一轮残留的提示飘在新任务上。</p>
 */
public static void welcomeUI() {
	GDragonGuide.getInstance().reset();
	GDragonService.getInstance().showWelcome(GStatic.gAbout.getWelcome());
}

/**
 *  再见界面
 *
 *  <p>开关由 sys.DragonBye 控制，也可用 {@code -Dcdtest.dragon.bye=false} 关闭。</p>
 *
 *  <p>任务结束时先清空提示弹窗（含尚未弹出的合并提示），
 *  否则最后一步操作的提示会与再见界面同屏，也会拖住进程退出。</p>
 */
public static void ByeUI() {
	GDragonGuide.getInstance().reset();
	GDragonService.getInstance().showBye(GStatic.gAbout.getEndingMini(), 3000);
}

/**
 *  清空全部人形弹窗
 *
 *  <p>供任务异常中断、或被外部框架接管收尾时调用：关闭提示弹窗与再见界面，
 *  并丢弃尚未弹出的提示。</p>
 */
public static void clearDragon() {
	GDragonGuide.getInstance().reset();
	GDragonService.getInstance().closeAll();
}
}
