package AutoTest;

import java.text.NumberFormat;

/**
 *  用例管理
 */
public class GTestCase {
	private GTestCase(){
		System.out.println("This is a tool class.");
	}
	
	/**
	 *  测试类型
	 */
	public static Integer TSSTYLE = 0;

	/**
	 *  测试编号
	 */
	public static Integer TSNO = 0;

	/**
	 *  测试结果编号，测试成功与否标记位：0-有效，1失败，2无效，3中断
	 */
	public static Integer TestResult = 3;
	
	/**
	 *  是否只校验不测试，默认为true
	 */
	public static boolean TestCheckOnly = true;

	/**
	 *  参数提供方式，默认为0-0-object集合，1-Excel表格， 2-Txt文本
	 */
	public static Integer TestInputType = 0;
	
	/**
	 *  参数提供来源，默认为0-0-工具内置，1-外部输入
	 */
	public static Integer TestInputSource = 0;

	/**
	 *  参数提供外部参数表读取参数起始位置标记-行
	 */
	public static int TestInputBeginRowIndex = 0;
	
	/**
	 *  参数提供外部参数表读取参数起始位置标记-列
	 */
	public static int TestInputBeginColumnIndex = 0;
	
	/**
	 *  标记单个用例测试是否通过或其他结果描述
	 */
	public static boolean ShowTestResult() {
		String strTestResult = "";
		boolean bTestResult = false;
		switch (TestResult.intValue()) {
			case 0:{
				strTestResult = "PASSED";
				bTestResult = true;
				break;// 测试通过
			}
			case 1:{
				strTestResult = "FAILED";
				break;// 预期结果与实际结果不一致
			}
			case 2:{
				strTestResult = "ERROR";
				bTestResult = true;
				break;// 出现预期错误码和错误信息
			}
			case 3:{
				strTestResult = "UNKNOW";
				break;// 程序执行中途中断，未收到返回信息，无法判断测试有效性
			}
			default:{break;}
		}
		GLog.GLogRecord(9, "TARGET CS-" + TSNO.toString() + " TEST RESULT:" + strTestResult + "\r\n");
		return bTestResult;
	}

	/**
	 *  根据用例类型测试结果记录：用例类型计数，保存错误码，输出计数状态
	 */
	public static void RecordTestStyleResult(Integer srcTestStyle) {
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
			GLog.GLogRecord(9, "UNKNOW,NOT COURT");
		}
		TestResult = srcTestStyle;
		
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
		GLog.GLogRecord(9, "\nSUMERY REPORT:\n(1)PASSED*" + GParam.TestReal + "\n(2)UNKNOW*" + GParam.TestFail + "\n(3)ERROR *"
				+ GParam.TestUnReal + "\n(4)FAILED*" + GParam.TestUnDo + "\nTESTCASE TOTAL PROCESS：" + TestStatus);
		
		//输出当前用例执行结果
		ShowTestResult();
	}

	/**
	 *  根据用例号判断用例类型：0 有效类 1失败类 2无效类 3中断类;
	 */
	public static Integer GetTestStyleByNo(Integer srcTestNO) {
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
	 *  根据用例类型记录返回码和返回信息
	 */
	public static void RecordTestResultByTSSTYLE(Integer dTSSTYLE) {
		GParam.curCaseNO++;
		GLog.GLogRecord(9, "\nRESULTCODE:\r\n" + GParam.TestResultCode + " \nRESULTMESSAGE:\r\n" + GParam.TestResultMsg);
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
				GLog.GLogRecord(5, GParam.gRes);
				break;
			}
			case 2: {
				GError.TSRESULT_TSNO[GParam.curCaseNO][0] = GParam.TestResultCode;
				GError.TSRESULT_TSNO[GParam.curCaseNO][1] = GParam.TestResultMsg;
				GError.TSRESULT_TSNO[GParam.curCaseNO][2] = "Y";
				GError.TSRESULT_TSNO[GParam.curCaseNO][4] = "B";
				GLog.GLogRecord(5, GParam.gRes);
				break;
			}
			default: {
				GError.TSRESULT_TSNO[GParam.curCaseNO][0] = "UNKNOW";
				GError.TSRESULT_TSNO[GParam.curCaseNO][1] = GParam.gRes;
				GError.TSRESULT_TSNO[GParam.curCaseNO][2] = "N";
				GError.TSRESULT_TSNO[GParam.curCaseNO][4] = "A";
				GLog.GLogRecord(5, GParam.gRes);
				break;
			}
		}
		RecordTestStyleResult(dTSSTYLE);
	}
}
