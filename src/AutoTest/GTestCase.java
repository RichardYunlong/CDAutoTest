package AutoTest;

import java.text.NumberFormat;

/**
 *  用例管理
 */
public class GTestCase {
	private GTestCase(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  测试类型
	 */
	public static Integer dTSSTYLE = 0;

	/**
	 *  测试编号
	 */
	public static Integer dTSNO = 0;

	/**
	 *  测试结果编号，测试成功与否标记位：0-有效，1失败，2无效，3中断
	 */
	public static Integer dTestResult = 3;
	
	/**
	 *  是否只校验不测试，默认为true
	 */
	public static boolean bTestCheckOnly = true;

	/**
	 *  参数提供方式，默认为0-0-object集合，1-Excel表格， 2-Txt文本
	 */
	public static Integer dTestInputType = 0;
	
	/**
	 *  参数提供来源，默认为0-0-工具内置，1-外部输入
	 */
	public static Integer dTestInputSource = 0;

	/**
	 *  参数提供外部参数表读取参数起始位置标记-行
	 */
	public static int dTestInputBeginRowIndex = 0;
	
	/**
	 *  参数提供外部参数表读取参数起始位置标记-列
	 */
	public static int dTestInputBeginColumnIndex = 0;
	
	/**
	 *  标记单个用例测试是否通过或其他结果描述
	 *  
	 *  @return 显示成功返回true，佛祖额返回false
	 */
	public static boolean showTestResult() {
		String strTestResult = "";
		boolean bTestResult = false;
		switch (dTestResult.intValue()) {
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
		GLog.logRecord(9, "TARGET CS-" + dTSNO.toString() + " TEST RESULT:" + strTestResult + "\r\n");
		return bTestResult;
	}

	/**
	 *  根据用例类型测试结果记录：用例类型计数，保存错误码，输出计数状态
	 *  
	 *  @param srcTestStyle 用例类型编码
	 */
	public static void recordTestStyleResult(Integer srcTestStyle) {
		// 处理用例类型
		if (srcTestStyle.intValue() == 0) {
			GProgress.dTestReal++;// 有效类加1
			/* 处理有效用例 */
		} else if (srcTestStyle.intValue() == 1) {
			GProgress.dTestFail++;
			/* 处理失败用例 */
			GLog.logShowConsole("Run " + dTSNO.toString() + " Error!");
		} else if (srcTestStyle.intValue() == 2) {
			GProgress.dTestUnReal++;// 无效类加1
			/* 处理无效用例 */
		} else if (srcTestStyle.intValue() == 3) {
			GProgress.dTestUnDo++;
			/* 处理中断用例 */
		} else {
			GLog.logRecord(9, "UNKNOW,NOT COURT");
		}
		dTestResult = srcTestStyle;
		
		// 记录用例执行进度
		String strTestStatus = "";
		if (GProgress.getTestTotalNo() <= 0) {
			strTestStatus = "COURT ERROR";
		} else {
			Integer curTestNo = GProgress.dTestReal + GProgress.dTestFail + GProgress.dTestUnReal + GProgress.dTestUnDo;
			double num = (double) curTestNo.intValue() / (double) GProgress.getTestTotalNo();
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			strTestStatus = nt.format(num);
		}
		GLog.logRecord(9, "\nSUMERY REPORT:\n(1)PASSED*" + GProgress.dTestReal + "\n(2)UNKNOW*" + GProgress.dTestFail + "\n(3)ERROR *"
				+ GProgress.dTestUnReal + "\n(4)FAILED*" + GProgress.dTestUnDo + "\nTESTCASE TOTAL PROCESS：" + strTestStatus);
		
		//输出当前用例执行结果
		showTestResult();
	}

	/**
	 *  根据用例号判断用例类型：0 有效类 1失败类 2无效类 3中断类;
	 *  
	 *  @param dTestNO 用例编号
	 *  @return 用类型类型编码 
	 */
	public static Integer getTestStyleByNo(Integer dTestNO) {
		if (dTestNO.intValue() > 1000 && dTestNO.intValue() < 99999) {
			dTSSTYLE = 0;
		} else if (dTestNO.intValue() > 100000 && dTestNO.intValue() < 99999999) {
			dTSSTYLE = 2;
		} else if (dTestNO.intValue() > 100000000 && dTestNO.intValue() < 999999999) {
			dTSSTYLE = 3;
		} else {
			dTSSTYLE = 1;
		}

		return dTSSTYLE;
	}

	/**
	 *  根据用例类型记录返回码和返回信息
	 *  
	 *  @param dTSSTYLE 用例类型编码
	 */
	public static void recordTestResultByTSSTYLE(Integer dTSSTYLE) {
		GParam.strCaseNO++;
		GLog.logRecord(9, "\nRESULTCODE:\r\n" + GParam.strTestResultCode + " \nRESULTMESSAGE:\r\n" + GParam.strTestResultMsg);
		switch (dTSSTYLE) {
			case 0: {
				GResult.strResultTSNO[GParam.strCaseNO][0] = "SUCCESS";
				GResult.strResultTSNO[GParam.strCaseNO][1] = GParam.gRes;
				GResult.strResultTSNO[GParam.strCaseNO][2] = "Y";
				GResult.strResultTSNO[GParam.strCaseNO][4] = "A";
				break;
			}
			case 1: {
				GResult.strResultTSNO[GParam.strCaseNO][0] = "EXCEPTION";
				GResult.strResultTSNO[GParam.strCaseNO][1] = GParam.gRes;
				GResult.strResultTSNO[GParam.strCaseNO][2] = "N";
				GResult.strResultTSNO[GParam.strCaseNO][4] = "A";
				GLog.logRecord(5, GParam.gRes);
				break;
			}
			case 2: {
				GResult.strResultTSNO[GParam.strCaseNO][0] = GParam.strTestResultCode;
				GResult.strResultTSNO[GParam.strCaseNO][1] = GParam.strTestResultMsg;
				GResult.strResultTSNO[GParam.strCaseNO][2] = "Y";
				GResult.strResultTSNO[GParam.strCaseNO][4] = "B";
				GLog.logRecord(5, GParam.gRes);
				break;
			}
			default: {
				GResult.strResultTSNO[GParam.strCaseNO][0] = "UNKNOW";
				GResult.strResultTSNO[GParam.strCaseNO][1] = GParam.gRes;
				GResult.strResultTSNO[GParam.strCaseNO][2] = "N";
				GResult.strResultTSNO[GParam.strCaseNO][4] = "A";
				GLog.logRecord(5, GParam.gRes);
				break;
			}
		}
		recordTestStyleResult(dTSSTYLE);
	}
}
