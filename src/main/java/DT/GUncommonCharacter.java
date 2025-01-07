package main.java.DT;

import main.java.Base.GClazz;

/**
 *  生僻字
 */
public class GUncommonCharacter {
	
    /**
     *  构造函数
     */
	private GUncommonCharacter(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  初始化特殊字符集合Unicode编码
	 */
	protected static final String[] SECTION_A_UNICODE = {"\\u3400","\\u3402","\\u342e","\\u37cd","\\u3809","\\u3cf1","\\u49cc","\\u4a3b","\\u4dae","\\u4db5"};
	protected static final String[] SECTION_B_UNICODE = {"\\ud840\\udc00","\\ud840\\udc30","\\ud840\\udc68","\\ud852\\udf62","\\ud869\\udea5","\\ud869\\uded6","","","",""};
	protected static final String[] SECTION_C_UNICODE = {"\\ud869\\udf00","\\ud869\\udf4b","\\ud86a\\udcb5","\\ud86b\\udc12","\\ud86b\\udeab","\\ud86c\\udeef","\\ud86d\\uddc7","\\ud86d\\udf34","",""};
	protected static final String[] SECTION_D_UNICODE = {"\\ud86d\\udf40","\\ud86d\\udf66","\\ud86d\\udfd1","\\ud86d\\udf82","\\ud86e\\udc09","\\ud86e\\udc1d","","","",""};
	protected static final String[] SECTION_E_UNICODE = {"\\ud870\\udc29","","","","","","","","",""};
	
	/**
	 *  初始化特殊字符集合CN编码
	 */
	protected static final String[] SECTION_A_CN = {"㐀","㐂","㐮","㟍","㠉","㳱","䧌","䨻","䶮","䶵"};
	protected static final String[] SECTION_B_CN = {"𠀀","𠀰","𠁨","𤭢","𪚥","𪛖","","","",""};
	protected static final String[] SECTION_C_CN = {"𪜀","𪝋","𪢵","𪰒","𪺫","𫋯","𫗇","𫜴","",""};
	protected static final String[] SECTION_D_CN = {"𫝀","𫝦","𫟑","𫞂","𫠉","𫠝","","","",""};
	protected static final String[] SECTION_E_CN = {"𬀩","","","","","","","","",""};
   
	/**
	 * Unicode转Cn
	 * 
	 * @param unicode 源码
	 * @return 目标码
	 */
	public static String unicodeToCn(String unicode) {
       String[] strs = unicode.split("\\\\u");
       StringBuilder returnStr = new StringBuilder();
       // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
       for (int i = 1; i < strs.length; i++) {
    	   returnStr.append((char) Integer.valueOf(strs[i], 16).intValue());
       }
       return returnStr.toString();
	}
   
	/**
	 * Cn转Unicode
	 * 
	 * @param cn 源码
	 * @return 目标码
	 */
	public static String cnToUnicode(String cn) {
       char[] chars = cn.toCharArray();
       StringBuilder returnStr = new StringBuilder();
	   for (char aChar : chars) {
            returnStr.append("\\u").append(Integer.toString(aChar, 16));
	   }
       return returnStr.toString();
	}
	
	/**
	 * 打印所有预置生僻字
	 */
	public static void printAllToConsole() {
        for (String s : SECTION_A_UNICODE) {
            GLog.logShowConsole(unicodeToCn(s));
        }
        for (String s : SECTION_B_UNICODE) {
            GLog.logShowConsole(unicodeToCn(s));
        }
        for (String s : SECTION_C_UNICODE) {
            GLog.logShowConsole(unicodeToCn(s));
        }
        for (String s : SECTION_D_UNICODE) {
            GLog.logShowConsole(unicodeToCn(s));
        }
        for (String s : SECTION_E_UNICODE) {
            GLog.logShowConsole(unicodeToCn(s));
        }
        for (String s : SECTION_A_CN) {
            GLog.logShowConsole(cnToUnicode(s));
        }
        for (String s : SECTION_B_CN) {
            GLog.logShowConsole(cnToUnicode(s));
        }
        for (String s : SECTION_C_CN) {
            GLog.logShowConsole(cnToUnicode(s));
        }
        for (String s : SECTION_D_CN) {
            GLog.logShowConsole(cnToUnicode(s));
        }
        for (String s : SECTION_E_CN) {
            GLog.logShowConsole(cnToUnicode(s));
        }
	}
}
