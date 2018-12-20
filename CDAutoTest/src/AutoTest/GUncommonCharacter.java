package AutoTest;

/**
 *  生僻字
 */
public class GUncommonCharacter {
	/**
	 *  初始化特殊字符集合Unicode编码
	 */
	public static String[] SECTION_A_UNICODE = {"\\u3400","\\u3402","\\u342e","\\u37cd","\\u3809","\\u3cf1","\\u49cc","\\u4a3b","\\u4dae","\\u4db5"};
	public static String[] SECTION_B_UNICODE = {"\\ud840\\udc00","\\ud840\\udc30","\\ud840\\udc68","\\ud852\\udf62","\\ud869\\udea5","\\ud869\\uded6","","","",""};
	public static String[] SECTION_C_UNICODE = {"\\ud869\\udf00","\\ud869\\udf4b","\\ud86a\\udcb5","\\ud86b\\udc12","\\ud86b\\udeab","\\ud86c\\udeef","\\ud86d\\uddc7","\\ud86d\\udf34","",""};
	public static String[] SECTION_D_UNICODE = {"\\ud86d\\udf40","\\ud86d\\udf66","\\ud86d\\udfd1","\\ud86d\\udf82","\\ud86e\\udc09","\\ud86e\\udc1d","","","",""};
	public static String[] SECTION_E_UNICODE = {"\\ud870\\udc29","","","","","","","","",""};
	
	/**
	 *  初始化特殊字符集合CN编码
	 */
	public static String[] SECTION_A_CN = {"㐀","㐂","㐮","㟍","㠉","㳱","䧌","䨻","䶮","䶵"};
	public static String[] SECTION_B_CN = {"𠀀","𠀰","𠁨","𤭢","𪚥","𪛖","","","",""};
	public static String[] SECTION_C_CN = {"𪜀","𪝋","𪢵","𪰒","𪺫","𫋯","𫗇","𫜴","",""};
	public static String[] SECTION_D_CN = {"𫝀","𫝦","𫟑","𫞂","𫠉","𫠝","","","",""};
	public static String[] SECTION_E_CN = {"𬀩","","","","","","","","",""};
   
	public static String unicodeToCn(String unicode) {
       /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
       String[] strs = unicode.split("\\\\u");
       String returnStr = "";
       // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
       for (int i = 1; i < strs.length; i++) {
         returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
       }
       return returnStr;
	}
   
	public static String cnToUnicode(String cn) {
       char[] chars = cn.toCharArray();
       String returnStr = "";
       for (int i = 0; i < chars.length; i++) {
         returnStr += "\\u" + Integer.toString(chars[i], 16);
       }
       return returnStr;
	}
   
//	public static void main(String[] args) {  
//		for(int i=0;i<SECTION_A_UNICODE.length;i++) {
//			System.out.println(unicodeToCn(SECTION_A_UNICODE[i]));  
//		}
//		for(int i=0;i<SECTION_B_UNICODE.length;i++) {
//			System.out.println(unicodeToCn(SECTION_B_UNICODE[i]));  
//		}
//		for(int i=0;i<SECTION_C_UNICODE.length;i++) {
//			System.out.println(unicodeToCn(SECTION_C_UNICODE[i]));  
//		}
//		for(int i=0;i<SECTION_D_UNICODE.length;i++) {
//			System.out.println(unicodeToCn(SECTION_D_UNICODE[i]));  
//		}
//		for(int i=0;i<SECTION_E_UNICODE.length;i++) {
//			System.out.println(unicodeToCn(SECTION_E_UNICODE[i]));  
//		}
//		for(int i=0;i<SECTION_A_CN.length;i++) {
//			System.out.println(cnToUnicode(SECTION_A_CN[i]));  
//		}
//		for(int i=0;i<SECTION_B_CN.length;i++) {
//			System.out.println(cnToUnicode(SECTION_B_CN[i]));  
//		}
//		for(int i=0;i<SECTION_C_CN.length;i++) {
//			System.out.println(cnToUnicode(SECTION_C_CN[i]));  
//		}
//		for(int i=0;i<SECTION_D_CN.length;i++) {
//			System.out.println(cnToUnicode(SECTION_D_CN[i]));  
//		}
//		for(int i=0;i<SECTION_E_CN.length;i++) {
//			System.out.println(cnToUnicode(SECTION_E_CN[i]));  
//		}
//	}
}
