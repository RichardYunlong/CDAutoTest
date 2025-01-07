package main.java.DT;

import java.util.Arrays;
import java.util.List;

import main.java.Base.GClazz;

/**
 *  特殊符号
 */
public class GSpecialCharacter {
	
    /**
     *  构造函数
     */
	private GSpecialCharacter(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  特殊数据过滤规则
	 */
	public static final List<String> DN5_3_FORBIDDEN = Arrays.asList("/", "\\\\", "\"", "=", "+", "<", ">", ";", "#", "*", "?", "|", ":","'");
	
	/**
	 *  输入项过滤规则
	 */
	public static final List<String> INPUT_FORBIDDEN = Arrays.asList("<", ">", "&", "%", "'", "{", "}", "\\\\", "\"");
}
