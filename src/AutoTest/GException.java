package AutoTest;

import java.io.IOException;

/**
 *  异常处理
 */
public class GException {

	/**
	 *  已知异常类型
	 */
	public static final String[] strExceptionName = { "Exception", "IOException", "PKIException", "DocumentException",
			"GeneralSecurityException" };

	/**
	 *  处理所有错误码
	 */
	public static void RecordErrorCode(String curTestNO, String curErrorCode, String curErrorMsg) {
		GLog.GLogRecord(5, "     CASE NUMBER:" + curTestNO + "      ERROR CODE:" + curErrorCode + "      ERROR MESSAGE:"
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
		if (!GParam.gRes.equals("") && (GTestCase.GetTestStyle(GTestCase.TSNO) == 2)) {
			GTestCase.RecordMessage("UNKNOW ERROR,FROM:" + strReq);
			GTestCase.RecordTestStyleResult(2, GParam.gRes);
		} else {
			GTestCase.RecordTestStyleResult(1, GParam.gRes);
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
		if (!GParam.gRes.equals("") && (GTestCase.GetTestStyle(GTestCase.TSNO) == 2)) {
			GTestCase.RecordMessage("IO ERROR:" + strReq);
			GTestCase.RecordTestStyleResult(2, GParam.gRes);
		} else {
			GTestCase.RecordTestStyleResult(1, GParam.gRes);
			e.printStackTrace();
		}
	}

	/**
	 *  获取错误信息
	 *  
	 *  @param e 异常实体
	 */
	public static String getExceptionAllinformation(Exception ex) {
		String sOut = "";
		StackTraceElement[] trace = ex.getStackTrace();
		for (StackTraceElement s : trace) {
			sOut += "\tat " + s + "\r\n";
		}
		return sOut;
	}
	
	/**
	 *  控制台输出和日志保存的一行相同的符号，常用于作为视觉分割
	 *  
	 *  @param strReq 字符
	 *  @param n 个数
	 */
	public static void RecordErrorLine(String str,int n) {
		if(n>=1) {
			for(int i = 0;i < n;i++) {
				System.out.print(str);
				GFile.WriteStringToRight(GLog.LogStyle[5], str);
			}
			GFile.WriteStringToRight(GLog.LogStyle[5], "\r\n");
		}
	}
}
