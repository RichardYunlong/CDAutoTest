package AutoTest;

/**
 *  特殊符号
 */
public class GSpecialCharacter {
	private GSpecialCharacter(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  特殊数据过滤规则
	 */
	public static final String[] DN5_3_FORBIDDEN = {"/", "\\\\", "\"", "=", "+", "<", ">", ";", "#", "*", "?", "|", ":","'"};
	
	/**
	 *  输入项过滤规则
	 */
	public static final String[] INPUT_FORBIDDEN = {"<", ">", "&", "%", "'", "{", "}", "\\\\", "\""};
}
