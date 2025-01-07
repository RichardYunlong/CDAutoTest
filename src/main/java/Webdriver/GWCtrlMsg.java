package main.java.Webdriver;

/**
 *  文本常量
 */
public class GWCtrlMsg {
	
	/**
	 *  查找类
	 */
	public static final String[] ui_QUERY = {
		" query in progress......",//查找中
		" was found.",//找到了
		" was not found.",//未找到
		" is in visible state.",//可见的
		" is not in visible state.",//不可见的
	};
	
	/**
	 *  点击类
	 */
	public static final String[] ui_CLICK = {
		" is clicked.",//点击了
		" is not clicked."//未点击
	};
	
	/**
	 *  输入框类
	 */
	public static final String[] ui_INPUT = {
		" is keyed in.",//键入了
		" is filled in.",//填入了
		" is cleared.",//清理了
		" is deleted.",//清理了
		" is failed.",//失败
	};
	
	/**
	 *  选中框类
	 */
	public static final String[] ui_SELECT = {
		" is selected.",//选中了
		" is unselected.",//取消选中
	};
}
