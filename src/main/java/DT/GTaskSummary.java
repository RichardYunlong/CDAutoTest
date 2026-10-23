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
	/**
	 *  获得任务执行日期 精确到日
	 *
	 *  @return 任务执行日期 精确到日
	 */
	public String getDATE() {
		return DATE;
	}
	/**
	 *  设置任务执行日期 精确到日
	 *
	 *  @param dATE 任务执行日期 精确到日
	 */
	public void setDATE(String dATE) {
		DATE = dATE;
	}

	/**
	 *  任务名称 用于区别各类任务，例如“每日构建”、“每月构建”、“版本构建”、“迭代构建”等
	 */
	private String NAME;
	/**
	 *  获得任务名称 用于区别各类任务，例如“每日构建”、“每月构建”、“版本构建”、“迭代构建”等
	 *
	 *  @return 任务名称 用于区别各类任务，例如“每日构建”、“每月构建”、“版本构建”、“迭代构建”等
	 */
	public String getNAME() {
		return NAME;
	}
	/**
	 *  设置任务名称 用于区别各类任务，例如“每日构建”、“每月构建”、“版本构建”、“迭代构建”等
	 *
	 *  @param nAME 任务名称 用于区别各类任务，例如“每日构建”、“每月构建”、“版本构建”、“迭代构建”等
	 */
	public void setNAME(String nAME) {
		NAME = nAME;
	}

	/**
	 *  任务描述
	 */
	private String DESC;
	/**
	 *  获得任务描述
	 *
	 *  @return 任务描述
	 */
	public String getDESC() {
		return DESC;
	}
	/**
	 *  设置描述文本，并把其中的 {@code ||} 转成换行标记
	 *
	 *  <p>用例描述里用 {@code ||} 分隔多个段落，写入报告前统一替换成 {@code <br/>}，
	 *  以便在 HTML 报告中正确换行。</p>
	 *
	 *  @param dESC 原始描述文本
	 */
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
	/**
	 *  获得任务开始时间
	 *
	 *  @return 任务开始时间
	 */
	public String getSTARTTIME() {
		return STARTTIME;
	}
	/**
	 *  设置任务开始时间
	 *
	 *  @param sTARTTIME 任务开始时间
	 */
	public void setSTARTTIME(String sTARTTIME) {
		STARTTIME = sTARTTIME;
	}

	/**
	 *  任务结束时间
	 */
	private String ENDTIME;
	/**
	 *  获得任务结束时间
	 *
	 *  @return 任务结束时间
	 */
	public String getENDTIME() {
		return ENDTIME;
	}
	/**
	 *  设置任务结束时间
	 *
	 *  @param eNDTIME 任务结束时间
	 */
	public void setENDTIME(String eNDTIME) {
		ENDTIME = eNDTIME;
	}

	/**
	 *  任务耗时总计
	 */
	private String SPENDTIME;
	/**
	 *  获得任务耗时总计
	 *
	 *  @return 任务耗时总计
	 */
	public String getSPENDTIME() {
		return SPENDTIME;
	}
	/**
	 *  设置任务耗时总计
	 *
	 *  @param sPENDTIME 任务耗时总计
	 */
	public void setSPENDTIME(String sPENDTIME) {
		SPENDTIME = sPENDTIME;
	}

	/**
	 *  任务目标地址
	 */
	private String URL;
	/**
	 *  获得任务目标地址
	 *
	 *  @return 任务目标地址
	 */
	public String getURL() {
		return URL;
	}
	/**
	 *  设置任务目标地址
	 *
	 *  @param uRL 任务目标地址
	 */
	public void setURL(String uRL) {
		URL = uRL;
	}

	/**
	 *  任务目标名称
	 */
	private String SID;
	/**
	 *  获得任务目标名称
	 *
	 *  @return 任务目标名称
	 */
	public String getSID() {
		return SID;
	}
	/**
	 *  设置任务目标名称
	 *
	 *  @param sID 任务目标名称
	 */
	public void setSID(String sID) {
		SID = sID;
	}
	
	/**
	 *  任务目标数据库名称
	 */
	private String DBSID;
	/**
	 *  获得任务目标数据库名称
	 *
	 *  @return 任务目标数据库名称
	 */
	public String getDBSID() {
		return DBSID;
	}
	/**
	 *  设置任务目标数据库名称
	 *
	 *  @param dBSID 任务目标数据库名称
	 */
	public void setDBSID(String dBSID) {
		DBSID = dBSID;
	}

	/**
	 *  任务目标版本
	 */
	private String VERSION;
	/**
	 *  获得任务目标版本
	 *
	 *  @return 任务目标版本
	 */
	public String getVERSION() {
		return VERSION;
	}
	/**
	 *  设置任务目标版本
	 *
	 *  @param vERSION 任务目标版本
	 */
	public void setVERSION(String vERSION) {
		VERSION = vERSION;
	}

	/**
	 *  任务用例总数
	 */
	private String TOTALNUM;
	/**
	 *  获得任务用例总数
	 *
	 *  @return 任务用例总数
	 */
	public String getTOTALNUM() {
		return TOTALNUM;
	}
	/**
	 *  设置任务用例总数
	 *
	 *  @param tOTALNUM 任务用例总数
	 */
	public void setTOTALNUM(String tOTALNUM) {
		TOTALNUM = tOTALNUM;
	}

	/**
	 *  任务跳过总数
	 */
	private String JUMPNUM;
	/**
	 *  获得任务跳过总数
	 *
	 *  @return 任务跳过总数
	 */
	public String getJUMPNUM() {
		return JUMPNUM;
	}
	/**
	 *  设置任务跳过总数
	 *
	 *  @param jUMPNUM 任务跳过总数
	 */
	public void setJUMPNUM(String jUMPNUM) {
		JUMPNUM = jUMPNUM;
	}

	/**
	 *  任务执行总数
	 */
	private String RUNNUM;
	/**
	 *  获得任务执行总数
	 *
	 *  @return 任务执行总数
	 */
	public String getRUNNUM() {
		return RUNNUM;
	}
	/**
	 *  设置任务执行总数
	 *
	 *  @param rUNNUM 任务执行总数
	 */
	public void setRUNNUM(String rUNNUM) {
		RUNNUM = rUNNUM;
	}

	/**
	 *  任务成功总数
	 */
	private String SUCCESSNUM;
	/**
	 *  获得任务成功总数
	 *
	 *  @return 任务成功总数
	 */
	public String getSUCCESSNUM() {
		return SUCCESSNUM;
	}
	/**
	 *  设置任务成功总数
	 *
	 *  @param sUCCESSNUM 任务成功总数
	 */
	public void setSUCCESSNUM(String sUCCESSNUM) {
		SUCCESSNUM = sUCCESSNUM;
	}

	/**
	 *  任务失败总数
	 */
	private String FAILEDNUM;
	/**
	 *  获得任务失败总数
	 *
	 *  @return 任务失败总数
	 */
	public String getFAILEDNUM() {
		return FAILEDNUM;
	}
	/**
	 *  设置任务失败总数
	 *
	 *  @param fAILEDNUM 任务失败总数
	 */
	public void setFAILEDNUM(String fAILEDNUM) {
		FAILEDNUM = fAILEDNUM;
	}

	/**
	 *  任务警告总数
	 */
	private String WARNINGNUM;
	/**
	 *  获得任务警告总数
	 *
	 *  @return 任务警告总数
	 */
	public String getWARNINGNUM() {
		return WARNINGNUM;
	}
	/**
	 *  设置任务警告总数
	 *
	 *  @param wARNINGNUM 任务警告总数
	 */
	public void setWARNINGNUM(String wARNINGNUM) {
		WARNINGNUM = wARNINGNUM;
	}

	/**
	 *  任务中断总数
	 */
	private String INTERRUPTNUM;
	/**
	 *  获得任务中断总数
	 *
	 *  @return 任务中断总数
	 */
	public String getINTERRUPTNUM() {
		return INTERRUPTNUM;
	}
	/**
	 *  设置任务中断总数
	 *
	 *  @param iNTERRUPTNUM 任务中断总数
	 */
	public void setINTERRUPTNUM(String iNTERRUPTNUM) {
		INTERRUPTNUM = iNTERRUPTNUM;
	}
	
	/**
	 *  任务摘要
	 */
	private String SUMMARY;
	/**
	 *  获得任务摘要
	 *
	 *  @return 任务摘要
	 */
	public String getSUMMARY() {
		return SUMMARY;
	}
	/**
	 *  设置任务摘要
	 *
	 *  @param sUMMARY 任务摘要
	 */
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
