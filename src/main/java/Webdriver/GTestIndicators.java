package Webdriver;

/**
 * 各项测试指标
 * 
 * @author hew-d
 */
public class GTestIndicators {
	
	/**
	 * 网页平均加载等待时间：指发出网页显示指令至网页完全显示所消耗的时间，单位秒(s)
	 */
	public static final int PageShowTime = 10;
	
	/**
	 * 桌面平均加载等待时间：指发出网页显示指令至网页完全显示所消耗的时间，单位秒(s)
	 */
	public static final int DesktopShowTime = 15;
	
	/**
	 * 二级窗体平均加载等待时间：指发出二级窗体网页显示指令至网页完全显示所消耗的时间，单位秒(s)
	 */
	public static final int SecondaryPageShowTime = 2;
	
	/**
	 * 特殊目标平均加载等待时间：指发出处理特殊指令前距离上一个指令完成必须等待至所消耗的时间，单位秒(s)
	 */
	public static final int SpecialShowTime = 2;
	
	/**
	 * 运算平均加载等待时间：指发出运算开始指令至得到运算结果所消耗的时间，单位秒(ms)
	 */
	public static final long ConsumeTime = 500;
	
	/**
	 * 提示窗持续时间：指提示窗出现至消失所持续的时间，单位秒(ms)
	 */
	public static final long DragonShowTime = 2000;
}
