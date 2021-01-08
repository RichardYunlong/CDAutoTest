package AutoTest;

import java.text.NumberFormat;

/**
 *  用例管理
 */
public class GTestCase {
	
	/**
	 * 
	 */
	public GTestCase(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  用例类型编码
	 *  
	 *  预估的用例类型，用例执行过程中可能发生改变。
	 *  
	 *  取值范围 [0,9]，默认为0
	 *  0-成功用例 所有输入均正确有效，结果与预期一致，可简单描述为“最优，测试通过”
	 *  1-失败用例 所有输入均正确有效，结果与预期不符，可简单描述为“最坏，测试不通过”
	 *  2-警告用例 自定义所有“有效但不一定正确”的输入，结果可控制且与预期一致，错误码表中有定义，可简单描述为“计划内的容错机制，测试可以通过”
	 *  3-中断用例 自定义所有“无效且不一定正确”的输入，程序执行到一半断开，未返回任何结果，错误码表中未定义，可简单描述为“不明原因，需仔细调研，不能通过”
	 */
	public static Integer TC_TYPE_REQ = 0;
	
	/**
	 *  用例类型编码
	 *  
	 *  执行结束后最终的用例类型编码
	 *  
	 *  取值范围 [0,9]，默认为3
	 *  0-执行成功
	 *  1-执行失败
	 *  2-错误提示
	 *  3-执行中断 
	 */
	public static Integer TC_TYPE_RES = 3;

	/**
	 *  用例唯一识别码
	 *  
	 *  数字类型，可称作“序号”
	 */
	public static Integer TC_NO = 0;
	
	/**
	 *  用例唯一识别码
	 *  
	 *  文本类型，可称作“编码”，推荐最大长度32字节
	 */
	public static String TC_CODE = "";
	
	/**
	 *  用例名称
	 *  
	 *  文本类型，推荐最大长度32字节
	 */
	public static String TC_NAME = "";
	
	/**
	 *  用例动作
	 *  
	 *  文本类型，推荐最大长度32字节
	 */
	public static String TC_ACT = "";
	
	/**
	 *  用例概述
	 *  
	 */
	public static String TC_SCR = "";
	
	/**
	 *  用例关键词
	 *  
	 */
	public static String TC_KEYS = "";
	
	/**
	 *  用例步骤
	 */
	public static String TC_STEP = "";
	
	/**
	 *  根据执行后的结果编码，获得高阶测试结果
	 *  
	 *  目前暂定将“成功”和“警告”类用例划入“测试通过”大类，将“失败”和“中断”类用例划入“测试不通过”大类，可能根据此大类结果决定是否要执行其他辅助功能
	 *  
	 *  @return 显示成功返回true，否则返回false
	 */
	public static boolean getHigherTCResult() {
		boolean bTestResult = false;
		switch (TC_TYPE_RES.intValue()) {
			case 0:
			case 2:{
				bTestResult = true;
				break;// 测试通过
			}
			case 1:
			case 3:{
				bTestResult = false;
				break;
			}
			default:{break;}
		}
		GLog.logRecord(9, "TARGET CS-" + TC_NO.toString() + " TEST RESULT:" + GMsg.MSG_TSRESULT[TC_TYPE_RES.intValue()] + "\r\n");
		return bTestResult;
	}

	/**
	 *  根据用例执行结果编码记录测试结果
	 *  
	 *  记录包括：用例类型计数，错误码，测试进度
	 *  
	 *  @param tcTypeRes 用例执行结果编码
	 */
	public static void recordTCResult(Integer tcTypeRes) {
		//记录最终执行结果编码
		TC_TYPE_RES = tcTypeRes;
		//按照用例类型执行用例计数
		if (TC_TYPE_RES.intValue() == 0) {
			GProgress.dTestReal++;// 有效类加1
		} else if (TC_TYPE_RES.intValue() == 1) {
			GProgress.dTestFail++;
		} else if (TC_TYPE_RES.intValue() == 2) {
			GProgress.dTestUnReal++;// 无效类加1
		} else if (TC_TYPE_RES.intValue() == 3) {
			GProgress.dTestUnDo++;
		} else {
			GLog.logRecord(9, "UNKNOW TYPE,NOT COURT");
		}
		
		//按照“是否视作测试通过”分类标准，输出当前用例执行结果
		if(!getHigherTCResult()) {
			//记录到5号日志
			GLog.logRecord(5, "\nRESULTCODE:\r\n" + GParam.gCode + " \nRESULTMESSAGE:\r\n" + GParam.gMsg);
			GLog.logRecord(9, "TESTCASE \"" + TC_NO.toString() + "\" FAILED OR UNKNOW,PLEASE CHECK OVER");
		}else{
			GLog.logRecord(9, "TESTCASE \"" + TC_NO.toString() + "\" PASSED");
		}
		
		// 记录用例执行进度
		String strTestStatus = "";
		if (GProgress.getTCTotalNum() <= 0) {
			strTestStatus = "COURT ERROR";
		} else {
			Integer curTestNo = GProgress.dTestReal + GProgress.dTestFail + GProgress.dTestUnReal + GProgress.dTestUnDo;
			double num = (double) curTestNo.intValue() / (double) GProgress.getTCTotalNum();
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			strTestStatus = nt.format(num);
		}
		GLog.logRecord(9, "\nSUMERY REPORT:\n(1)PASSED*" + GProgress.dTestReal + "\n(2)UNKNOW*" + GProgress.dTestFail + "\n(3)ERROR *"
				+ GProgress.dTestUnReal + "\n(4)FAILED*" + GProgress.dTestUnDo + "\nTESTCASE TOTAL PROCESS：" + strTestStatus);
	}

	/**
	 *  根据用例编码判断用例类型编码
	 *  
	 *  @param dTestNO 用例编号
	 *  @return 用类型类型编码 
	 */
	public static Integer getTCTypeByNo(Integer tcNO) {
		Integer tarType = 3;
		if (tcNO.intValue() > 1000 && tcNO.intValue() < 99999) {
			tarType = 0;
		} else if (tcNO.intValue() > 100000 && tcNO.intValue() < 99999999) {
			tarType = 2;
		} else if (tcNO.intValue() > 100000000 && tcNO.intValue() < 999999999) {
			tarType = 3;
		} else {
			tarType = 1;
		}

		return tarType;
	}

	/**
	 *  根据用例类型记录返回码和返回信息
	 *  
	 *  @param tcTypeRes 用例类型编码
	 */
	public static void recordTCResultByTCTypeRes(Integer tcTypeRes) {
		//已执行用例计数加1
		GParam.dRunNum++;
		//记录最终执行结果编码
		TC_TYPE_RES = tcTypeRes;
		GLog.logRecord(9, "\nRESULTCODE:\r\n" + GParam.gCode + " \nRESULTMESSAGE:\r\n" + GParam.gMsg);
		switch (TC_TYPE_RES.intValue()) {
			case 0: {
				GResult.RESULT_STR[GParam.dRunNum-1][0] = "0000";
				GResult.RESULT_STR[GParam.dRunNum-1][1] = GParam.gRes;
				GResult.RESULT_STR[GParam.dRunNum-1][2] = "Y";
				GResult.RESULT_STR[GParam.dRunNum-1][4] = "A";
				break;
			}
			case 1: {
				GResult.RESULT_STR[GParam.dRunNum-1][0] = "FAILED";
				GResult.RESULT_STR[GParam.dRunNum-1][1] = GParam.gRes;
				GResult.RESULT_STR[GParam.dRunNum-1][2] = "N";
				GResult.RESULT_STR[GParam.dRunNum-1][4] = "A";
				GLog.logRecord(5, GParam.gRes);
				break;
			}
			case 2: {
				GResult.RESULT_STR[GParam.dRunNum-1][0] = GParam.gCode;
				GResult.RESULT_STR[GParam.dRunNum-1][1] = GParam.gMsg;
				GResult.RESULT_STR[GParam.dRunNum-1][2] = "Y";
				GResult.RESULT_STR[GParam.dRunNum-1][4] = "B";
				GLog.logRecord(5, GParam.gRes);
				break;
			}
			default: {
				GResult.RESULT_STR[GParam.dRunNum-1][0] = "UNKNOW";
				GResult.RESULT_STR[GParam.dRunNum-1][1] = GParam.gRes;
				GResult.RESULT_STR[GParam.dRunNum-1][2] = "N";
				GResult.RESULT_STR[GParam.dRunNum-1][4] = "A";
				GLog.logRecord(5, GParam.gRes);
				break;
			}
		}
		GECharts.addDetailRport(TC_TYPE_RES.toString(), 
								TC_NO.toString(), 
								TC_NAME.toString(), 
								GResult.RESULT_STR[GParam.dRunNum-1][0], 
								GResult.RESULT_STR[GParam.dRunNum-1][1], 
								GResult.RESULT_STR[GParam.dRunNum-1][4],
								GTransfer.gServerUrl[0], 
								"");
								
								
		recordTCResult(TC_TYPE_RES);
	}
}
