package main.java.Base;

/**
 * 系统显示文字常量
 */
public class GMsg {
	
    /**
     *  构造函数
     */
	private GMsg(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  目标被找到
	 */
	public static final String[] MSG_FOUND = new String[] {
			"A VALUE BE FOUND",//找到一个值
			"AVAILABLE VALUES BE FOUND",//找到一些有效值
			"A FILE BE FOUND",//找到一个文件
			"AVAILABLE FILES BE FOUND"};//找到一些有效文件
	
	/**
	 *  目标未被找到
	 */
	public static final String[] MSG_NOTFOUND = new String[] {
			"NO VALUE BE FOUND",//未找到任何值
			"NO AVAILABLE VALUE BE FOUND",//未找到任何有效值
			"NO FILE BE FOUND",//未找到任何文件
			"NO AVAILABLE FILE BE FOUND"};//未找到任何有效文件
	
	/**
	 *  读写错误
	 */
	public static final String[] MSG_IOFAILED = new String[] {
			"READ FAILED",//读错误
			"WRITE FAILED",//些错误
			"CREATE FAILED",//创建错误
			"MUST BE OVERWRITTEN",//必须被重写
			"UNKOWN IO ERROR"};//未知的读写错误
	
	/**
	 *  是否打开
	 */
	public static final String[] MSG_ISOPENED = new String[] {
			"THE TARGET FILE IS NOT OPENED",//目标未被打开
			"THE TARGET FILE MUST BE CLOSE FIRST"};//目标必须先被关闭

	/**
	 *  是否存在
	 */
	public static final String[] MSG_EXIST = new String[] {
			"FILE EXIST",//文件存在
			"FILE DOSE NOT EXIST"};//文件不存在
	
	/**
	 *  是否为空
	 */
	public static final String[] MSG_EMPTY = new String[] {
			"FILE IS EMPTY",//为空
			"FILE IS NOT EMPTY"};//不为空
	
	/**
	 *  计量单位
	 */
	public static final String[] MSG_MEASUREMENT = new String[] {
			"",//系统预留
			"TIMES",//次
			"B",//B
			"KB",//KB
			"MB",//MB
			"GB",//GB
			"TB"};//TB
	
	/**
	 *  控制台提示
	 */
	public static final String[] MSG_CONSOLE = new String[] {
			"SOMETHING WRONG WITH PRINTING IN CONSOLE,DETAIL:",//请检查控制台
			"UNKOWN LOG TYPE"};//未知错误
	
	/**
	 *  数据库
	 */
	public static final String[] MSG_DB = new String[] {
			"EFFECT LINE NUMBER:",//影响行数
			"FIND LINE NUMBER:"};//目标行号
	
	/**
	 *  邮件发送
	 */
	public static final String[] MSG_EMAIL = new String[] {
			"EMAIL SENT SUCCESS",//发送成功
			"EMAIL SENT FAILED"};//发送失败
	
	/**
	 *  就绪
	 */
	public static final String[] MSG_READY = new String[] {
			"READY TO GO",//可以开始下一步
			"COMPLETE",//完成
			"ENDING"};//结束
	
	/**
	 *  运行结果
	 */
	public static final String[] MSG_TSRESULT = new String[] {
			"SUCCESS",//成功
			"FAILED",//失败
			"WARNING",//警告
			"INTERRUPT"};//中断
	
	/**
	 *  路径
	 */
	public static final String[] MSG_PATH_EXIST = new String[] {
			"PATH EXIST",//路径存在
			"PATH DOSE NOT EXIST"};//路径不存在

	/**
	 *  获取随机值
	 */
	public static final String[] MSG_RANDOM = new String[] {
			"GET RANDOM OBJECT FAILED",//获取随机值失败
			"GET RANDOM OBJECT SUCCESS"};//获取随机值成功
	
	/**
	 *  获取图片
	 */
	public static final String[] MSG_IMAGE = new String[] {
			"GET IMAGE OBJECT FAILED",//获取图片失败
			"GET IMAGE OBJECT SUCCESS"};//获取图片失败
	
	/**
	 *  命令启动
	 */
	public static final String[] MSG_CMD = new String[] {
			"cmd.exe /c start ",//关闭当前并启动
			"cmd.exe /k start "};//保留当前并启动
	
	/**
	 *  文件名标记
	 */
	public static final String[] MSG_FIlE_TIP = new String[] {
			"NonBlank_",
			"empty",};//无空白行的文件
}
