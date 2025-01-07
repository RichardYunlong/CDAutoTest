package DT;

/**
 *  测试任务数据摘要
 *  一般用于记录人物数据，为导出测试报告提供数据来源
 */
public class GTaskSummary {
	
	/**
	 *  任务执行日期 精确到日
	 */
	private String DATE;
	public String getDATE() {
		return DATE;
	}
	public void setDATE(String dATE) {
		DATE = dATE;
	}

	/**
	 *  任务名称 用于区别各类任务，例如“每日构建”、“每月构建”、“版本构建”、“迭代构建”等
	 */
	private String NAME;
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}

	/**
	 *  任务描述
	 */
	private String DESC;
	public String getDESC() {
		return DESC;
	}
	@SuppressWarnings("StringConcatenationInLoop")
    public void setDESC(String dESC) {
		String mark = "\\|\\|";
		if(dESC.contains("||")) {
			String[] strTpRanges= dESC.split(mark);
			String back = "<br/>";

            for (String strTpRange : strTpRanges) {
                DESC += (strTpRange + back);
            }
		}else {
			DESC = dESC;
		}	
	}

	/**
	 *  任务开始时间
	 */
	private String STARTTIME;
	public String getSTARTTIME() {
		return STARTTIME;
	}
	public void setSTARTTIME(String sTARTTIME) {
		STARTTIME = sTARTTIME;
	}

	/**
	 *  任务结束时间
	 */
	private String ENDTIME;
	public String getENDTIME() {
		return ENDTIME;
	}
	public void setENDTIME(String eNDTIME) {
		ENDTIME = eNDTIME;
	}

	/**
	 *  任务耗时总计
	 */
	private String SPENDTIME;
	public String getSPENDTIME() {
		return SPENDTIME;
	}
	public void setSPENDTIME(String sPENDTIME) {
		SPENDTIME = sPENDTIME;
	}

	/**
	 *  任务目标地址
	 */
	private String URL;
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}

	/**
	 *  任务目标名称
	 */
	private String SID;
	public String getSID() {
		return SID;
	}
	public void setSID(String sID) {
		SID = sID;
	}
	
	/**
	 *  任务目标数据库名称
	 */
	private String DBSID;
	public String getDBSID() {
		return DBSID;
	}
	public void setDBSID(String dBSID) {
		DBSID = dBSID;
	}

	/**
	 *  任务目标版本
	 */
	private String VERSION;
	public String getVERSION() {
		return VERSION;
	}
	public void setVERSION(String vERSION) {
		VERSION = vERSION;
	}

	/**
	 *  任务用例总数
	 */
	private String TOTALNUM;
	public String getTOTALNUM() {
		return TOTALNUM;
	}
	public void setTOTALNUM(String tOTALNUM) {
		TOTALNUM = tOTALNUM;
	}

	/**
	 *  任务跳过总数
	 */
	private String JUMPNUM;
	public String getJUMPNUM() {
		return JUMPNUM;
	}
	public void setJUMPNUM(String jUMPNUM) {
		JUMPNUM = jUMPNUM;
	}

	/**
	 *  任务执行总数
	 */
	private String RUNNUM;
	public String getRUNNUM() {
		return RUNNUM;
	}
	public void setRUNNUM(String rUNNUM) {
		RUNNUM = rUNNUM;
	}

	/**
	 *  任务成功总数
	 */
	private String SUCCESSNUM;
	public String getSUCCESSNUM() {
		return SUCCESSNUM;
	}
	public void setSUCCESSNUM(String sUCCESSNUM) {
		SUCCESSNUM = sUCCESSNUM;
	}

	/**
	 *  任务失败总数
	 */
	private String FAILEDNUM;
	public String getFAILEDNUM() {
		return FAILEDNUM;
	}
	public void setFAILEDNUM(String fAILEDNUM) {
		FAILEDNUM = fAILEDNUM;
	}

	/**
	 *  任务警告总数
	 */
	private String WARNINGNUM;
	public String getWARNINGNUM() {
		return WARNINGNUM;
	}
	public void setWARNINGNUM(String wARNINGNUM) {
		WARNINGNUM = wARNINGNUM;
	}

	/**
	 *  任务中断总数
	 */
	private String INTERRUPTNUM;
	public String getINTERRUPTNUM() {
		return INTERRUPTNUM;
	}
	public void setINTERRUPTNUM(String iNTERRUPTNUM) {
		INTERRUPTNUM = iNTERRUPTNUM;
	}
	
	/**
	 *  任务摘要
	 */
	private String SUMMARY;
	public String getSUMMARY() {
		return SUMMARY;
	}
	public void setSUMMARY(String sUMMARY) {
		SUMMARY = sUMMARY;
	}

	/**
	 * 构造函数
	 * 构造时会给实例添加一些测试用的数据
	 */
	public GTaskSummary(){
		DATE = "1970-01-01";
		NAME = "构造-任务名称";
		DESC = "构造-任务描述";
		STARTTIME = "1970-01-01 00-00-00";
		ENDTIME = "1970-01-01 03-00-00";
		SPENDTIME = "3h";
		URL = "http://localhost:8080/";
		SID = "构造-任务目标名称";
		DBSID = "构造-任务目标数据库名称";
		VERSION = "构造-任务目标版本";
		TOTALNUM = "100";
		JUMPNUM = "0";
		RUNNUM = "100";
		SUCCESSNUM = "100";
		FAILEDNUM = "0";
		WARNINGNUM = "0";
		INTERRUPTNUM = "0";
		SUMMARY ="<b>自动化方面：</b>"
				+ "<br />新自动化工具GAT：合同管理、物资管理、机械管理、分包管理、资金管理、成本管理"
				+ "<br />[P0-模块打开和关闭]100%;"
				+ "<br />[P1-模块独立算法]95%;"
				+ "<br />[P2-模块组合场景]16.17%;"
				+ "<br /><b>性能方面：</b>"
				+ "<br />[耗时统计统计工具]100%"
				+ "<br /><b>安全性方面：</b>"
				+ "<br />[系统级]Nessus"
				+ "<br />[Web应用]Archni"
				+ "<br />[网络报文级]Burpsuite";
	}
}
