package Test;

import Base.GClazz;
import Base.GMsg;
import DT.GLog;
import Sys.GStatic;

import java.text.NumberFormat;

/**
 *  测试用例-关系数据
 */
public class GTestCase {
	
    /**
     *  构造函数
	 *  适用于非UI测试的用例
     */
	public GTestCase(){ GClazz.thisADataUnitClass(); }
	
	/**
	 *  用例类型编码
	 *  预估的用例类型，用例执行过程中可能发生改变。
	 *  取值范围 [0,9]，默认为0
	 *  0-成功用例 所有输入均正确有效，结果与预期一致，可简单描述为“最优，测试通过”
	 *  1-失败用例 所有输入均正确有效，结果与预期不符，可简单描述为“最坏，测试不通过”
	 *  2-警告用例 自定义所有“有效但不一定正确”的输入，结果可控制且与预期一致，错误码表中有定义，可简单描述为“计划内的容错机制，测试可以通过”
	 *  3-中断用例 自定义所有“无效且不一定正确”的输入，程序执行到一半断开，未返回任何结果，错误码表中未定义，可简单描述为“不明原因，需仔细调研，不能通过”
	 */
	private Integer TC_TYPE_REQ = 0;
	public Integer getTC_TYPE_REQ() { return TC_TYPE_REQ; }
	public void setTC_TYPE_REQ(Integer tC_TYPE_REQ) { TC_TYPE_REQ = tC_TYPE_REQ; }

	/**
	 *  用例类型编码
	 *  执行结束后最终的用例类型编码
	 *  取值范围 [0,9]，默认为3
	 *  0-执行成功
	 *  1-执行失败
	 *  2-错误提示
	 *  3-执行中断 
	 */
	private Integer TC_TYPE_RES = 3;
	public Integer getTC_TYPE_RES() { return TC_TYPE_RES; }
	public void setTC_TYPE_RES(Integer tC_TYPE_RES) { TC_TYPE_RES = tC_TYPE_RES;}

	/**
	 *  用例唯一识别码
	 *  数字类型，可称作“序号”
	 */
	private Integer TC_NO = 0;
	public Integer getTC_NO() { return TC_NO; }
	public void setTC_NO(Integer tC_NO) { TC_NO = tC_NO; }

	/**
	 *  用例唯一识别码
	 *  文本类型，可称作“编码”，推荐最大长度为32 字节
	 */
	private String TC_CODE = "";
	public String getTC_CODE() { return TC_CODE; }
	public void setTC_CODE(String tC_CODE) { TC_CODE = tC_CODE; }
	
	/**
	 *  用例名称：测试用例的名字
	 *  文本类型，推荐最大长度为32 字节
	 */
	private String TC_NAME = "";
	public String getTC_NAME() { return TC_NAME; }
	public void setTC_NAME(String tC_NAME) { TC_NAME = tC_NAME; }
	
	/**
	 *  用例动作：测试用例的核心动作，例如“新建、保存、查询、删除”等
	 *  文本类型，推荐最大长度为32 字节
	 */
	private String TC_ACT = "";
	public String getTC_ACT() { return TC_ACT; }
	public void setTC_ACT(String tC_ACT) { TC_ACT = tC_ACT; }
	
	/**
	 *  用例概述：用例较为详细的描述，协助测试人员了解测试用例
	 */
	private String TC_SCR = "";
	public String getTC_SCR() { return TC_SCR; }
	public void setTC_SCR(String tC_SCR) { TC_SCR = tC_SCR; }

	/**
	 *  用例关键词：测试用例的关键词，用于快速检索
	 */
	private String TC_KEYS = "";
	public String getTC_KEYS() { return TC_KEYS; }
	public void setTC_KEYS(String tC_KEYS) { TC_KEYS = tC_KEYS;}
	
	/**
	 *  用例步骤：测试用例的详细步骤
	 */
	private String TC_STEP = "";
	public String getTC_STEP() { return TC_STEP; }
	public void setTC_STEP(String tC_STEP) { TC_STEP = tC_STEP; }
	
	/**
	 *  当前用例的运行状态标记
	 *  0-正常
	 *  >=1-出现了1及以上个异常点
	 */
	private int TC_STATUS = 0;
	public int getTC_STATUS() { return TC_STATUS; }
	public void setTC_STATUS(int tC_STATUS) { TC_STATUS = tC_STATUS; }

	/**
	 *  用例外置数据
	 *  测试用例所需要的外置数据的文件名称，以“||”(双竖线)分割，每个内部以json格式存在
	 */
	private String TC_DATA = "";
	public String getTC_DATA() { return TC_DATA; }
	public void setTC_DATA(String tC_DATA) { TC_DATA = tC_DATA; }
	
	/**
	 *  根据执行后的结果编码，获得高阶测试结果
	 *  目前暂定将“成功”和“警告”类用例划入“测试通过”大类，将“失败”和“中断”类用例划入“测试不通过”大类，可能根据此大类结果决定是否要执行其他辅助功能
	 *  
	 *  @return 显示成功返回true，否则返回false
	 */
	public boolean getHigherTCResult() {
		boolean bTestResult = false;
		switch (TC_TYPE_RES) {
			case 0:
			case 2:{
				bTestResult = true;
				break;// 测试通过
			}
			case 1:
			case 3:
			default:
			{
				break;
			}
		}
		GLog.logRecord(9, "TARGET CS-" + TC_NO.toString() + " TEST RESULT:" + GMsg.MSG_TSRESULT[TC_TYPE_RES] + "\r\n");
		return bTestResult;
	}

	/**
	 *  根据用例执行结果编码记录测试结果
	 *  记录包括：用例类型计数，错误码，测试进度
	 *  
	 *  @param tcTypeRes 用例执行结果编码
	 */
	public void recordTCResult(Integer tcTypeRes) {
		//记录最终执行结果编码
		TC_TYPE_RES = tcTypeRes;
		//按照用例类型执行用例计数
		if (TC_TYPE_RES == 0) {
			GTestMission.gTestProgress.setdTestReal(GTestMission.gTestProgress.getdTestReal() + 1);// 有效类加1
		} else if (TC_TYPE_RES == 1) {
			GTestMission.gTestProgress.setdTestFail(GTestMission.gTestProgress.getdTestFail() + 1);
		} else if (TC_TYPE_RES == 2) {
			GTestMission.gTestProgress.setdTestUnReal(GTestMission.gTestProgress.getdTestUnReal() + 1);// 无效类加1
		} else if (TC_TYPE_RES == 3) {
			GTestMission.gTestProgress.setdTestUnDo(GTestMission.gTestProgress.getdTestUnDo() + 1);
		} else {
			GLog.logRecord(9, "UNKNOW TYPE,NOT COURT");
		}
		
		//按照“是否视作测试通过”分类标准，输出当前用例执行结果
		if(!getHigherTCResult()) {
			//记录到5号日志
			GLog.logRecord(5, "\nRESULTCODE:\r\n" + GStatic.gP.getCode() + " \nRESULTMESSAGE:\r\n" + GStatic.gP.getMsg());
			GLog.logRecord(9, "TESTCASE \"" + TC_NO.toString() + "\" FAILED OR UNKNOW,PLEASE CHECK OVER");
		}else{
			GLog.logRecord(9, "TESTCASE \"" + TC_NO.toString() + "\" PASSED");
		}
		
		// 记录用例执行进度
		String strTestStatus = getPercentageOfExecutionProgress();
		GLog.logRecord(9, "\nSUMERY REPORT:"
				+ "\n(1)PASSED*" + GTestMission.gTestProgress.getdTestReal() 
				+ "\n(2)FAILED*" + GTestMission.gTestProgress.getdTestFail() 
				+ "\n(3)ERROR *" + GTestMission.gTestProgress.getdTestUnReal() 
				+ "\n(4)UNKNOW*" + GTestMission.gTestProgress.getdTestUnDo() 
				+ "\nTESTCASE TOTAL PROCESS：" + strTestStatus);
	}

	/**
	 *  获取测试用例执行进度维度的进度
	 *  进度=当前已执行用例数/总用例数*100%
	 *
	 * @return 用例执行进度百分比数值，精确到小数点后两位
	 */
	private static String getPercentageOfExecutionProgress() {
		String strTestStatus;
		if (GTestMission.gTestProgress.getTotalNum() <= 0) {
			strTestStatus = "COURT ERROR";
		} else {
			int curTestNo = GTestMission.gTestProgress.getdTestReal()
							  + GTestMission.gTestProgress.getdTestFail()
							  + GTestMission.gTestProgress.getdTestUnReal()
							  + GTestMission.gTestProgress.getdTestUnDo();
			double num = (double) curTestNo / (double) GTestMission.gTestProgress.getTotalNum();
			NumberFormat nt = NumberFormat.getPercentInstance();
			nt.setMinimumFractionDigits(2);
			strTestStatus = nt.format(num);
		}
		return strTestStatus;
	}

	/**
	 *  根据用例编码判断用例类型编码
	 *  此算法是暂行算法，后续可根据实际情况进行调整
	 *  
	 *  @param tcNo 用例编号
	 *
	 *  @return 用类型类型编码 
	 */
	public Integer getTCTypeByTCNo(Integer tcNo) {
		int tarType;
		if (tcNo > 1000 && tcNo < 99999) {
			tarType = 0;
		} else if (tcNo > 100000 && tcNo < 99999999) {
			tarType = 2;
		} else if (tcNo > 100000000 && tcNo < 999999999) {
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
	public void recordTCResultByTCTypeRes(Integer tcTypeRes) {
		//已执行用例计数加1
		GStatic.gP.setdRunNum(GStatic.gP.getdRunNum() + 1);
		//记录最终执行结果编码
		TC_TYPE_RES = tcTypeRes;
		GLog.logRecord(9, "\nRESULTCODE:\r\n" + GStatic.gP.getCode() + " \nRESULTMESSAGE:\r\n" + GStatic.gP.getMsg());
		String[][] tcResult = GTestMission.gTestResult.getResultString().clone();
		switch (TC_TYPE_RES) {
			case 0: {
				tcResult[GStatic.gP.getdRunNum()-1][0] = "0000";
				tcResult[GStatic.gP.getdRunNum()-1][1] = GStatic.gP.getRes();
				tcResult[GStatic.gP.getdRunNum()-1][2] = "Y";
				tcResult[GStatic.gP.getdRunNum()-1][4] = "A";
				break;
			}
			case 1: {
				tcResult[GStatic.gP.getdRunNum()-1][0] = "FAILED";
				//当最近一个错误的返回报文不为空，且当前用例的错误点个数大于0时
				if(!GStatic.gP.getRecRes().isEmpty()) {
					if(this.TC_STATUS > 0) {
						GStatic.gP.setRecRes(GStatic.gP.getRecRes() + "[共(" + this.TC_STATUS + ")个错误，详情参见Detail]");
					}
					tcResult[GStatic.gP.getdRunNum()-1][1] = GStatic.gP.getRecRes();
					TC_STATUS = 0;
				}else {
					tcResult[GStatic.gP.getdRunNum()-1][1] = GStatic.gP.getRes();
				}
				tcResult[GStatic.gP.getdRunNum()-1][2] = "N";
				tcResult[GStatic.gP.getdRunNum()-1][4] = "A";
				GLog.logRecord(5, GStatic.gP.getRes());
				break;
			}
			case 2: {
				tcResult[GStatic.gP.getdRunNum()-1][0] = GStatic.gP.getCode();
				tcResult[GStatic.gP.getdRunNum()-1][1] = GStatic.gP.getMsg();
				tcResult[GStatic.gP.getdRunNum()-1][2] = "Y";
				tcResult[GStatic.gP.getdRunNum()-1][4] = "B";
				GLog.logRecord(5, GStatic.gP.getRes());
				break;
			}
			default: {
				tcResult[GStatic.gP.getdRunNum()-1][0] = "UNKNOW";
				tcResult[GStatic.gP.getdRunNum()-1][1] = GStatic.gP.getRes();
				tcResult[GStatic.gP.getdRunNum()-1][2] = "N";
				tcResult[GStatic.gP.getdRunNum()-1][4] = "A";
				GLog.logRecord(5, GStatic.gP.getRes());
				break;
			}
		}
		GTestMission.gTestResult.setResultString(tcResult);
		String[] serverUrl = GStatic.gTransfer.getgServerUrl().clone();
		GStatic.geCharts.addDetailReport(TC_TYPE_RES.toString(),
								TC_NO.toString(),
                				TC_NAME,
								tcResult[GStatic.gP.getdRunNum()-1][0],
								tcResult[GStatic.gP.getdRunNum()-1][1],
								tcResult[GStatic.gP.getdRunNum()-1][4],
								serverUrl[0], 
								"");
								
								
		recordTCResult(TC_TYPE_RES);
	}
}
