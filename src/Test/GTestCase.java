package Test;

import java.text.NumberFormat;

import Base.GClazz;
import Base.GMsg;
import DT.GECharts;
import DT.GLog;
import DT.GTransfer;
import Sys.GParam;

/**
 *  用例管理
 */
public class GTestCase {
	
    /**
     *  构造函数
     */
	private GTestCase(){
		GClazz.thisAToolClass();
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
	private static Integer TC_TYPE_REQ = 0;
	
	public static Integer getTC_TYPE_REQ() {
		return TC_TYPE_REQ;
	}

	public static void setTC_TYPE_REQ(Integer tC_TYPE_REQ) {
		TC_TYPE_REQ = tC_TYPE_REQ;
	}

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
	private static Integer TC_TYPE_RES = 3;

	/**
	 *  用例唯一识别码
	 *  
	 *  数字类型，可称作“序号”
	 */
	private static Integer TC_NO = 0;
	
	/**
	 *  用例唯一识别码
	 *  
	 *  文本类型，可称作“编码”，推荐最大长度32字节
	 */
	private static String TC_CODE = "";
	
	/**
	 *  用例名称
	 *  
	 *  文本类型，推荐最大长度32字节
	 */
	private static String TC_NAME = "";
	
	/**
	 *  用例动作
	 *  
	 *  文本类型，推荐最大长度32字节
	 */
	private static String TC_ACT = "";
	
	/**
	 *  用例概述
	 *  
	 */
	private static String TC_SCR = "";
	
	/**
	 *  用例关键词
	 *  
	 */
	private static String TC_KEYS = "";
	
	/**
	 *  用例步骤
	 */
	private static String TC_STEP = "";
	
	/**
	 *  当前用例的运行状态标记
	 *  
	 *  0-正常
	 *  >=1-出现了1及以上个异常点
	 */
	private static int TC_STATUS = 0;
	
	/**
	 *  用例外置数据
	 *  
	 *  测试用例所需要的外置数据若干文件名称，以“||”(双竖线)分割，每个内部以json格式存在
	 */
	private static String TC_DATA = "";

	public static Integer getTC_TYPE_RES() {
		return TC_TYPE_RES;
	}

	public static void setTC_TYPE_RES(Integer tC_TYPE_RES) {
		TC_TYPE_RES = tC_TYPE_RES;
	}

	public static Integer getTC_NO() {
		return TC_NO;
	}

	public static void setTC_NO(Integer tC_NO) {
		TC_NO = tC_NO;
	}

	public static String getTC_CODE() {
		return TC_CODE;
	}

	public static void setTC_CODE(String tC_CODE) {
		TC_CODE = tC_CODE;
	}

	public static String getTC_NAME() {
		return TC_NAME;
	}

	public static void setTC_NAME(String tC_NAME) {
		TC_NAME = tC_NAME;
	}

	public static String getTC_ACT() {
		return TC_ACT;
	}

	public static void setTC_ACT(String tC_ACT) {
		TC_ACT = tC_ACT;
	}

	public static String getTC_SCR() {
		return TC_SCR;
	}

	public static void setTC_SCR(String tC_SCR) {
		TC_SCR = tC_SCR;
	}

	public static String getTC_KEYS() {
		return TC_KEYS;
	}

	public static void setTC_KEYS(String tC_KEYS) {
		TC_KEYS = tC_KEYS;
	}

	public static String getTC_STEP() {
		return TC_STEP;
	}

	public static void setTC_STEP(String tC_STEP) {
		TC_STEP = tC_STEP;
	}

	public static int getTC_STATUS() {
		return TC_STATUS;
	}

	public static void setTC_STATUS(int tC_STATUS) {
		TC_STATUS = tC_STATUS;
	}

	public static String getTC_DATA() {
		return TC_DATA;
	}

	public static void setTC_DATA(String tC_DATA) {
		TC_DATA = tC_DATA;
	}
	
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
				;
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
			GTestProgress.setdTestReal(GTestProgress.getdTestReal() + 1);// 有效类加1
		} else if (TC_TYPE_RES.intValue() == 1) {
			GTestProgress.setdTestFail(GTestProgress.getdTestFail() + 1);
		} else if (TC_TYPE_RES.intValue() == 2) {
			GTestProgress.setdTestUnReal(GTestProgress.getdTestUnReal() + 1);// 无效类加1
		} else if (TC_TYPE_RES.intValue() == 3) {
			GTestProgress.setdTestUnDo(GTestProgress.getdTestUnDo() + 1);
		} else {
			GLog.logRecord(9, "UNKNOW TYPE,NOT COURT");
		}
		
		//按照“是否视作测试通过”分类标准，输出当前用例执行结果
		if(!getHigherTCResult()) {
			//记录到5号日志
			GLog.logRecord(5, "\nRESULTCODE:\r\n" + GParam.getCode() + " \nRESULTMESSAGE:\r\n" + GParam.getMsg());
			GLog.logRecord(9, "TESTCASE \"" + TC_NO.toString() + "\" FAILED OR UNKNOW,PLEASE CHECK OVER");
		}else{
			GLog.logRecord(9, "TESTCASE \"" + TC_NO.toString() + "\" PASSED");
		}
		
		// 记录用例执行进度
		String strTestStatus = "";
		if (GTestProgress.getTotalNum() <= 0) {
			strTestStatus = "COURT ERROR";
		} else {
			Integer curTestNo = GTestProgress.getdTestReal() 
							  + GTestProgress.getdTestFail() 
							  + GTestProgress.getdTestUnReal() 
							  + GTestProgress.getdTestUnDo();
			double num = (double) curTestNo.intValue() / (double) GTestProgress.getTotalNum();
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			strTestStatus = nt.format(num);
		}
		GLog.logRecord(9, "\nSUMERY REPORT:"
				+ "\n(1)PASSED*" + GTestProgress.getdTestReal() 
				+ "\n(2)FAILED*" + GTestProgress.getdTestFail() 
				+ "\n(3)ERROR *" + GTestProgress.getdTestUnReal() 
				+ "\n(4)UNKNOW*" + GTestProgress.getdTestUnDo() 
				+ "\nTESTCASE TOTAL PROCESS：" + strTestStatus);
	}

	/**
	 *  根据用例编码判断用例类型编码
	 *  
	 *  @param dTestNO 用例编号
	 *  @return 用类型类型编码 
	 */
	public static Integer getTCTypeByNo(Integer tcNO) {
		Integer tarType;
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
		GParam.setdRunNum(GParam.getdRunNum() + 1);
		//记录最终执行结果编码
		TC_TYPE_RES = tcTypeRes;
		GLog.logRecord(9, "\nRESULTCODE:\r\n" + GParam.getCode() + " \nRESULTMESSAGE:\r\n" + GParam.getMsg());
		String[][] tcResult = GTestResult.getResultString().clone();
		switch (TC_TYPE_RES.intValue()) {
			case 0: {
				tcResult[GParam.getdRunNum()-1][0] = "0000";
				tcResult[GParam.getdRunNum()-1][1] = GParam.getRes();
				tcResult[GParam.getdRunNum()-1][2] = "Y";
				tcResult[GParam.getdRunNum()-1][4] = "A";
				break;
			}
			case 1: {
				tcResult[GParam.getdRunNum()-1][0] = "FAILED";
				//当最近一个错误的返回报文不为空，且当前用例的错误点个数大于0时
				if(!GParam.getRecRes().equals("") && TC_STATUS > 0) {
					if(GTestCase.TC_STATUS > 0) {
						GParam.setRecRes(GParam.getRecRes() + "[共(" + String.valueOf(GTestCase.TC_STATUS) + ")个错误，详情参见Detail]");
					}
					tcResult[GParam.getdRunNum()-1][1] = GParam.getRecRes();
					TC_STATUS = 0;
				}else {
					tcResult[GParam.getdRunNum()-1][1] = GParam.getRes();
				}
				tcResult[GParam.getdRunNum()-1][2] = "N";
				tcResult[GParam.getdRunNum()-1][4] = "A";
				GLog.logRecord(5, GParam.getRes());
				break;
			}
			case 2: {
				tcResult[GParam.getdRunNum()-1][0] = GParam.getCode();
				tcResult[GParam.getdRunNum()-1][1] = GParam.getMsg();
				tcResult[GParam.getdRunNum()-1][2] = "Y";
				tcResult[GParam.getdRunNum()-1][4] = "B";
				GLog.logRecord(5, GParam.getRes());
				break;
			}
			default: {
				tcResult[GParam.getdRunNum()-1][0] = "UNKNOW";
				tcResult[GParam.getdRunNum()-1][1] = GParam.getRes();
				tcResult[GParam.getdRunNum()-1][2] = "N";
				tcResult[GParam.getdRunNum()-1][4] = "A";
				GLog.logRecord(5, GParam.getRes());
				break;
			}
		}
		GTestResult.setResultString(tcResult);
		String[] serverUrl = GTransfer.getgServerUrl().clone();
		GECharts.addDetailRport(TC_TYPE_RES.toString(), 
								TC_NO.toString(), 
								TC_NAME.toString(), 
								tcResult[GParam.getdRunNum()-1][0], 
								tcResult[GParam.getdRunNum()-1][1], 
								tcResult[GParam.getdRunNum()-1][4],
								serverUrl[0], 
								"");
								
								
		recordTCResult(TC_TYPE_RES);
	}
}
