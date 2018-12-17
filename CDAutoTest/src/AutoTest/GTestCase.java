package AutoTest;

import java.text.NumberFormat;

public class GTestCase {

	/**
	 * @see 测试类型
	 */
	public static Integer TSSTYLE = 0;

	/**
	 * @see 测试编号
	 */
	public static Integer TSNO = 0;

	/**
	 * @see 测试结果编号，测试成功与否标记位：0-有效，1失败，2无效，3中断
	 */
	public static Integer TestResult = 3;
	
	/**
	 * @see 是否只校验不测试，默认为true
	 */
	public static boolean TestCheckOnly = true;

	/**
	 * @see 参数提供方式，默认为0-0-object集合，1-Excel表格
	 */
	public static Integer TestInputType = 0;
	
	/**
	 * @see 参数提供来源，默认为0-0-工具内置，1-外部输入
	 */
	public static Integer TestInputSource = 0;
	
	/**
	 * @see 记录测试结果
	 */
	public static void RecordTestResult(String curTSNO, int dTestResult) {
		String strTestResult = "";
		switch (dTestResult) {
		case 0:
			strTestResult = "PASSED";
			break;// 测试通过
		case 1:
			strTestResult = "FAILED";
			break;// 预期结果与实际结果不一致
		case 2:
			strTestResult = "ERROR";
			break;// 出现预期错误码和错误信息
		case 3:
			strTestResult = "UNKNOW";
			break;// 程序执行中途中断，未收到返回信息，无法判断测试有效性
		}
		GLog.GLogRecord(9, "TARGET CS-" + curTSNO + " TEST RESULT:" + strTestResult + "\r\n");
	}

	/**
	 * @see 记录错误码
	 */
	public static void RecordErrorCode(String curTestNO, String curErrorCode, String curErrorMsg) {
		GLog.GLogRecord(5, "     CASE NUMBER:" + curTestNO + "      ERROR CODE:" + curErrorCode + "      ERROR MESSAGE:"
				+ curErrorMsg);
	}

	/**
	 * @see 记录输入参数
	 */
	public static void RecordInputParams(String txt, String testno) {
		GLog.GLogRecord(9, "\nDEAL WITH:REQUEST MESSAGE:\r\n" + txt);
	}

	/**
	 * @see 记录输入参数
	 */
	public static void RecordRRMessage(String reCode, String reMessage) {
		GLog.GLogRecord(9, "\nRESULTCODE:\r\n" + reCode + " \nRESULTMESSAGE:\r\n" + reMessage);
	}

	/**
	 * @see 记录发送和返回报文
	 */
	public static void RecordMessage(String req, String res) {
		GLog.GLogRecord(9, "\nCS TARGET START" + TSNO.toString());
		GLog.GLogRecord(9, "\nREQUEST MESSAGE:\n" + req + "\nRESPONSE MESSAGE:\n" + res);
		GLog.GLogRecord(9, "\nCS TARGET END");
	}

	/**
	 * @see 记录操作内容
	 */
	public static void RecordMessage(String txt) {
		GLog.GLogRecord(9, "\nDEAL WITH:" + txt);
	}

	/**
	 * @see 记录错误内容
	 */
	public static void RecordError(String txt) {
		GLog.GLogRecord(5, txt);
	}

	/**
	 * @see 创建批量业务配置文件，用于JMeter
	 */
	public static void CreateBacthParams() {
		GFile.WriteStringToBottom("C:\\Users\\hewei\\Desktop\\certInfo.txt",
				"3202,20,200,B712,3C1B0F4B098944BA97113D7ED0B14D59");
		GFile.WriteStringToBottom("C:\\Users\\hewei\\Desktop\\certInfo.txt", "3211,B711,0,0,0");
		GFile.WriteStringToBottom("C:\\Users\\hewei\\Desktop\\certInfo.txt", "9902,0,0,0,0");
	}

	/**
	 * @see 根据用例类型测试结果记录：用例类型计数，保存错误码，输出计数状态
	 */
	public static void RecordTestStyleResult(Integer srcTestStyle, String curRes) {
		// 处理用例类型
		if (srcTestStyle.intValue() == 0) {
			GParam.TestReal++;// 有效类加1
			/* 处理有效用例 */
		} else if (srcTestStyle.intValue() == 1) {
			GParam.TestFail++;
			/* 处理失败用例 */
			System.out.println("Run " + TSNO.toString() + " Error!");
		} else if (srcTestStyle.intValue() == 2) {
			GParam.TestUnReal++;// 无效类加1
			/* 处理无效用例 */
		} else if (srcTestStyle.intValue() == 3) {
			GParam.TestUnDo++;
			/* 处理中断用例 */
		} else {
			RecordMessage("UNKNOW,NOT COURT");
		}
		// 记录返回报文
		RecordMessage("RESPONSE MESSAGE:\r\n" + curRes);
		// 记录用例执行进度
		String TestStatus = "";
		if (GParam.TestTotalNo <= 0) {
			TestStatus = "COURT ERROR";
		} else {
			Integer curTestNo = GParam.TestReal + GParam.TestFail + GParam.TestUnReal + GParam.TestUnDo;
			double num = (double) curTestNo.intValue() / (double) GParam.TestTotalNo.intValue();
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			TestStatus = nt.format(num);
		}
		RecordMessage("SUMERY REPORT:\n(1)PASSED*" + GParam.TestReal + "\n(2)UNKNOW*" + GParam.TestFail + "\n(3)ERROR *"
				+ GParam.TestUnReal + "\n(4)FAILED*" + GParam.TestUnDo + "\nTESTCASE TOTAL PROCESS：" + TestStatus);
		TestResult = srcTestStyle.intValue();
	}

	/**
	 * @see 根据用例号判断用例类型：0 有效类 1失败类 2无效类 3中断类;
	 */
	public static Integer GetTestStyle(Integer srcTestNO) {
		if (srcTestNO.intValue() > 1000 && srcTestNO.intValue() < 99999) {
			TSSTYLE = 0;
		} else if (srcTestNO.intValue() > 100000 && srcTestNO.intValue() < 99999999) {
			TSSTYLE = 2;
		} else if (srcTestNO.intValue() > 100000000 && srcTestNO.intValue() < 999999999) {
			TSSTYLE = 3;
		} else {
			TSSTYLE = 1;
		}

		return TSSTYLE;
	}

	/**
	 * @see 根据用例类型记录返回码和返回信息
	 */
	public static void RecordResultArrayByTSSTYLE(Integer dTSSTYLE) {
		GParam.curCaseNO++;
		RecordRRMessage(GParam.TestResultCode, GParam.TestResultMsg);
		switch (dTSSTYLE) {
			case 0: {
				GError.TSRESULT_TSNO[GParam.curCaseNO][0] = "SUCCESS";
				GError.TSRESULT_TSNO[GParam.curCaseNO][1] = GParam.gRes;
				GError.TSRESULT_TSNO[GParam.curCaseNO][2] = "Y";
				GError.TSRESULT_TSNO[GParam.curCaseNO][4] = "A";
				break;
			}
			case 1: {
				GError.TSRESULT_TSNO[GParam.curCaseNO][0] = "EXCEPTION";
				GError.TSRESULT_TSNO[GParam.curCaseNO][1] = GParam.gRes;
				GError.TSRESULT_TSNO[GParam.curCaseNO][2] = "N";
				GError.TSRESULT_TSNO[GParam.curCaseNO][4] = "A";
				RecordError(GParam.gRes);
				break;
			}
			case 2: {
				GError.TSRESULT_TSNO[GParam.curCaseNO][0] = GParam.TestResultCode;
				GError.TSRESULT_TSNO[GParam.curCaseNO][1] = GParam.TestResultMsg;
				GError.TSRESULT_TSNO[GParam.curCaseNO][2] = "Y";
				GError.TSRESULT_TSNO[GParam.curCaseNO][4] = "B";
				RecordError(GParam.gRes);
				RecordError(GParam.gRes);
				break;
			}
			default: {
				GError.TSRESULT_TSNO[GParam.curCaseNO][0] = "UNKNOW";
				GError.TSRESULT_TSNO[GParam.curCaseNO][1] = GParam.gRes;
				GError.TSRESULT_TSNO[GParam.curCaseNO][2] = "N";
				GError.TSRESULT_TSNO[GParam.curCaseNO][4] = "A";
				RecordError(GParam.gRes);
				break;
			}
		}
		RecordTestStyleResult(dTSSTYLE, GParam.gRes);
	}
}
