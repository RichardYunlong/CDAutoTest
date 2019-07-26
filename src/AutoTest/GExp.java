package AutoTest;

import java.io.IOException;

/**
 *  异常处理
 */
public class GExp {
	private GExp(){
		GLog.logShowConsole("This is a tool class.");
	}

	/**
	 *  已知异常类型
	 */
	private static final String[] strExceptionName = { "Exception", "IOException", "PKIException", "DocumentException",
			"GeneralSecurityException" };

	/**
	 *  处理所有错误码
	 *  
	 *  @param curTestNO 用例编号
	 *  @param curErrorCode 错误码
	 *  @param curErrorMsg 错误类型
	 */
	public static void recordErrorCode(String curTestNO, String curErrorCode, String curErrorMsg) {
		GLog.logRecord(5, "     CASE NUMBER:" + curTestNO + "      ERROR CODE:" + curErrorCode + "      ERROR MESSAGE:"
				+ curErrorMsg);
	}

	/**
	 *  根据错误类名称获取异常类型编号
	 *  
	 *  @param errorName 错误类型名称
	 *  @return 返回错误类型序号
	 */
	public static int getKindByExceptionName(String errorName) {
		int dIndex = 0;
		for (int i = 0; i < strExceptionName.length; i++) {
			if (errorName.equals(strExceptionName[i])) {
				dIndex = i;
			}
		}
		return dIndex;
	}

	/**
	 *  处理未知错误
	 *  
	 *  @param strReq 请求报文
	 *  @param e 异常实体
	 */
	public static void doCatchException(String strReq, Exception e) {
		if (!GParam.gRes.equals("") && (GTestCase.getTestStyleByNo(GTestCase.dTSNO) == 2)) {
			GLog.logRecord(9, "UNKNOW ERROR,FROM:" + strReq);
			GTestCase.recordTestStyleResult(2);
		} else {
			GTestCase.recordTestStyleResult(1);
			e.printStackTrace();
		}
	}

	/**
	 *  处理IO类错误
	 *  
	 *  @param strReq 请求报文
	 *  @param e 异常实体
	 */
	public static void doCatchIOException(String strReq, IOException e) {
		if (!GParam.gRes.equals("") && (GTestCase.getTestStyleByNo(GTestCase.dTSNO) == 2)) {
			GLog.logRecord(9, "IO ERROR:" + strReq);
			GTestCase.recordTestStyleResult(2);
		} else {
			GTestCase.recordTestStyleResult(1);
			e.printStackTrace();
		}
	}

	/**
	 *  获取错误信息
	 *  
	 *  @param e 异常实体
	 *  @return 返回异常堆栈信息
	 */
	public static String getExceptionAllinformation(Exception e) {
		String sOut = "";
		StackTraceElement[] trace = e.getStackTrace();
		for (StackTraceElement s : trace) {
			sOut += "\tat " + s + "\r\n";
		}
		return sOut;
	}
	
	/**
	 *  控制台输出和日志保存的一行相同的符号，常用于作为视觉分割
	 *  
	 *  @param str 字符
	 *  @param n 个数
	 */
	public static void recordErrorLine(String str,int n) {
		if(n>=1) {
			for(int i = 0;i < n;i++) {
				GLog.logShowNoEnter(str);
				GFile.writeStringToRight(GLog.strLogStyle[5], str);
			}
			GFile.writeStringToRight(GLog.strLogStyle[5], "\r\n");
		}
	}
}
