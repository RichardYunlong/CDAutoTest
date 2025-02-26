package DT;

import Base.GFile;
import Sys.GStatic;
import Test.GTestMission;
import Base.GClazz;

import java.io.IOException;
import java.util.Arrays;

/**
 *  异常处理
 */
public class GException {
	
    /**
     *  构造函数
     */
	private GException(){
		GClazz.thisAToolClass();
	}

	/**
	 *  已知异常类型
	 */
	private static final String[] EXCEPTION_NAME = { "Exception", "IOException", "PKIException", "DocumentException",
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
		for (int i = 0; i < EXCEPTION_NAME.length; i++) {
			if (errorName.equals(EXCEPTION_NAME[i])) {
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
		if (!GStatic.gP.getRes().isEmpty() && (GTestMission.gTestCase.getTCTypeByTCNo(GTestMission.gTestCase.getTC_NO()) == 2)) {
			GLog.logRecord(9, "UNKNOW ERROR,FROM:" + strReq);
			GTestMission.gTestCase.recordTCResult(2);
		} else {
			GTestMission.gTestCase.recordTCResult(1);
			GFile.writeStringErrorToGuideBottom("doCatchException[" + Arrays.toString(e.getStackTrace()) +"]");
		}
	}

	/**
	 *  处理IO类错误
	 *  
	 *  @param strReq 请求报文
	 *  @param e 异常实体
	 */
	public static void doCatchIOException(String strReq, IOException e) {
		if (!GStatic.gP.getRes().isEmpty() && (GTestMission.gTestCase.getTCTypeByTCNo(GTestMission.gTestCase.getTC_NO()) == 2)) {
			GLog.logRecord(9, "IO ERROR:" + strReq);
			GTestMission.gTestCase.recordTCResult(2);
		} else {
			GTestMission.gTestCase.recordTCResult(1);
			GFile.writeStringErrorToGuideBottom("doCatchIOException[" + Arrays.toString(e.getStackTrace()) +"]");
		}
	}

	/**
	 *  获取错误信息
	 *  
	 *  @param e 异常实体
	 *  @return 返回异常堆栈信息
	 */
	public static String getExceptionAllinformation(Exception e) {
		StringBuilder sOut = new StringBuilder();
		StackTraceElement[] trace = e.getStackTrace();
		for (StackTraceElement s : trace) {
			sOut.append("\tat ")
					.append(s)
					.append("\r\n");
		}
		return sOut.toString();
	}
	
	/**
	 *  控制台输出和日志保存的一行相同的符号，常用于作为视觉分割
	 *  
	 *  @param str 字符
	 *  @param n 个数
	 */
	public static void recordErrorLine(String str,int n) {
		String[] logPath = GLog.getLogpath().clone();
		if(n>=1) {
			for(int i = 0;i < n;i++) {
				GLog.logShowNoEnter(str);
				GFile.writeStringToRight(logPath[5], str);
			}
			GFile.writeStringToRight(logPath[5], "\r\n");
		}
	}
	
	/**
	 *  切换用例类型 当切换的用例类型后，用例执行结果的处理方式将按照切换后的类型进行处理
	 *  
	 *  @param e 所有异常
	 *  @param dTSStyle 切换至用例类型：1-失败；2-错误码；3-异常中断
	 *  @param logIndex 记录到的日志编号
	 *  @param eMsg 用例信息
	 *  
	 *  @return b2TSStyle 是否切换成功 true-切换成功，false-切换失败
	 */
	@SuppressWarnings("UnusedReturnValue")
    public static boolean SwtichTo (Exception e, int dTSStyle, int logIndex, String eMsg) {
		boolean b2TSStyle = false;
		
		try {
			//记录当前测试步骤信息至控制台和日志文件
			GLog.logRecordTime(logIndex, eMsg);
			//控制台打印错误堆栈信息
			GLog.logRecordTime(8, "SwtichTo[" + Arrays.toString(e.getStackTrace()) +"]");
			//用例类型判断
			if (GTestMission.gTestCase.getTC_TYPE_RES() == 1) {
				//如果预制的用例类型就是1，即失败用例，将错误堆栈信息作为返回信息
				GStatic.gP.setRes(GException.getExceptionAllinformation(e));
			}else {
				//如果预制的用例类型不是1，即测试用例执行失败是计划外的，需要将测试用例类型置为1，即转变为失败用例，供用例执行完成后的数量变更
				GTestMission.gTestCase.setTC_TYPE_RES(1);
			}

			b2TSStyle = true;
		}catch (Exception ex) {
            GLog.logRecordTime(8, "用例类型切换失败，用例执行结果可能无法正常保存，请执行静态审查");
			GLog.logSysFunctionException("SwtichTo", e);
		}
		
		return b2TSStyle;
	}
}
