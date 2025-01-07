package DT;

import java.awt.Color;

import Base.GClazz;

/**
 * 配色
 */
public class GColor {
	
    /**
     *  构造函数
     */
	private GColor(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  字段名称背景
	 */
	public static final String COL_NAME = "#AAAAAA";
	
	/**
	 *  正常-强化
	 */
	public static final String SUCCESS_HIGH_GREEN = "#2b821d";
	
	/**
	 *  异常-强化
	 */
	public static final String FAIL_HIGH_RED = "#FF0000";
	
	/**
	 *  警告-强化
	 */
	public static final String WARNING_HIGH_YELLOW = "#fcce10";
	
	/**
	 *  中断-强化
	 */
	public static final String UNKOWN_HIGH_PURPLE = "#a0a7e6";
	
	/**
	 *  通过-强化提示文字颜色
	 */
	public static final Color GREEN_PASS = new Color(0,121,0);
	
	/**
	 *  未覆盖
	 */
	public static final String UNCOVER = "#eeeeee";
	
	/**
	 *  未覆盖-强化
	 */
	public static final String UNCOVER_HIGH = "#cccccc";
	
	/**
	 *  打开和关闭
	 */
	public static final String OPENCLOSE = "#82b6e9";
	
	/**
	 *  打开和关闭-强化
	 */
	public static final String OPENCLOSE_HIGH = "#6699ff";
	
	/**
	 *  算法
	 */
	public static final String ALGO = "#a4d8c2";
	
	/**
	 *  算法-强化
	 */
	public static final String ALGO_HIGH = "#38b6b6";
	
	/**
	 *  场景
	 */
	public static final String SCENE = "#ff6666";
	
	/**
	 *  场景-强化
	 */
	public static final String SCENE_HIGH = "#d3758f";
}
