package main.java.DT;

import main.java.Base.*;
import main.java.Coverage.GCoverageReport;
import main.java.Sys.GPath;
import main.java.Sys.GStatic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 构造测试报表
 */
public class GECharts {
	
    /**
     *  构造函数
     */
	public GECharts(){
		GClazz.thisADataUnitClass();
	}
	
	/**
	 * 用例类型
	 */
	private String type_Temp = "";
	
	/**
	 * 用例编码
	 */
	private String no_Temp = "";
	
	/**
	 * 用例名称
	 */
	private String name_Temp = "";
	
	/**
	 * 用例响应码
	 */
	private String res_Code_Temp = "";
	
	/**
	 * 用例响应信息
	 */
	private String res_Msg_Temp = "";

	/**
	 * 用例描述
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal", "FieldCanBeLocal"})
    private String scr_Temp = "";
	
	/**
	 * 用例等级
	 */
	private String level_Temp = "";
	
	/**
	 * 用例链接
	 */
	private String url_Temp = "";
	
	/**
	 * 备注
	 */
	private String mark_temp = "";

	/**
	 *  html版本测试详情测试报告内容计数器
	 */
	private Integer report_Table_Content_Index = 0;
	
	/**
	 *  html版本测试详情测试报告内容
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private Map<Integer, Map<String, String>> report_Table_Content_Text = new HashMap<>();
	
	/**
	 *  添加html版本测试详情测试报告内容-枚举模式
	 *  
	 *  @param type 用例类型
	 *  @param no 用例编码
	 *  @param name 用例名称
	 *  @param code 用例响应码
	 *  @param msg 用例响应信息
	 *  @param level 用例描述
	 *  @param url 用例链接
	 *  @param mark 备注
	 */	
	public void addDetailReport(String type,
								String no,
								String name,
								String code,
								String msg,
								String level,
								String url,
								String mark) {
		type_Temp = type;
		no_Temp = no;
		name_Temp = name;
		res_Code_Temp = code;
		res_Msg_Temp = msg;
		level_Temp = level;
		url_Temp = url;
		mark_temp = mark;
		
		Map<String, String> caseTemp = new HashMap<>();
		caseTemp.put("用例类型", type_Temp);
		caseTemp.put("用例编码", no_Temp);
		caseTemp.put("用例名称", name_Temp);
		caseTemp.put("响应码", res_Code_Temp);
		caseTemp.put("响应信息", res_Msg_Temp);
		caseTemp.put("等级", level_Temp);
		caseTemp.put("链接", url_Temp);
		caseTemp.put("备注", mark_temp);
		
		report_Table_Content_Index++;
		report_Table_Content_Text.put(report_Table_Content_Index, caseTemp);
		
		if(GStatic.gP.isbAutoCheckReport()) {
			String[] logPath = GLog.getLogpath().clone();
			GFile.writeStringToBottom(logPath[4], caseTemp.toString());
		}
	}
	
	/**
	 *  添加html版本测试详情测试报告内容-String分割模式
	 *  
	 *  @param case_Temp 用例类型
	 */	
	public void addDetailReport(String case_Temp) {
		Map<String, String> caseTemp = new HashMap<>();
		String[] logPath = GLog.getLogpath().clone();
		if(GText.getRepeatCount(case_Temp, "|") >= 6) {
			String[] strArr = case_Temp.split("\\|");
			type_Temp = strArr[0];
			no_Temp = strArr[1];
			name_Temp = strArr[2];
			res_Code_Temp = strArr[3];
			res_Msg_Temp = strArr[4];
			level_Temp = strArr[5];
			url_Temp = strArr[6];
			mark_temp = strArr[7];
			
			caseTemp.put("用例类型", type_Temp);
			caseTemp.put("用例编码", no_Temp);
			caseTemp.put("用例名称", name_Temp);
			caseTemp.put("响应码", res_Code_Temp);
			caseTemp.put("响应信息", res_Msg_Temp);
			caseTemp.put("等级", scr_Temp);
			caseTemp.put("链接", url_Temp);
			caseTemp.put("备注", mark_temp);
			
			report_Table_Content_Index++;
			report_Table_Content_Text.put(report_Table_Content_Index, caseTemp);
			
			if(GStatic.gP.isbAutoCheckReport()) {
				GFile.writeStringToBottom(logPath[4], caseTemp.toString());
			}
		}else {
			GFile.writeStringToBottom(logPath[8], "参数错误，相关条目添加失败:" + case_Temp);
		}
	}
	
	/**
	 *  加载html版本测试详情测试报告内容
	 *
	 *  @return 报告内容
	 */	
	public String loadingTestCaseCountReport() {
		StringBuilder dynamicTable = new StringBuilder();

        Map<String, HashMap<String, String>> counts = new HashMap<>(GStatic.gTestCaseCounts.getTestCaseCounts().getTestCaseCount());
		
		dynamicTable.append("<div style=\"display:flex;flex-wrap:wrap;\">");
		for(Entry<String, HashMap<String, String>> entry:counts.entrySet()) {
			//属性1
			String attr1Tip = "重要程度描述";
			String attr1show = entry.getValue().get("重要程度");
			String attr1Color = "";
			if(attr1show != null) {
                switch (attr1show) {
                    case "关键":
                        attr1Color = GColor.SCENE_HIGH;
                        break;
                    case "重要":
                        attr1Color = GColor.ALGO_HIGH;
                        break;
                    case "常规":
                        attr1Color = GColor.OPENCLOSE_HIGH;
                        break;
                    default:
                        attr1Color = GColor.UNCOVER_HIGH;
                        break;
                }
			}

			//数字1
			String num1Tip = "算法类测试用例个数";
			String num1show = entry.getValue().get("算法");
			String num1Color = "";
			if(num1show != null) {
				if(Integer.parseInt(num1show) > 2) {
					num1Color = GColor.SCENE_HIGH;
				}else if(Integer.parseInt(num1show) == 2) {
					num1Color = GColor.ALGO_HIGH;
				}else if(Integer.parseInt(num1show) == 1) {
					num1Color = GColor.OPENCLOSE_HIGH;
				}else {
					num1Color = GColor.UNCOVER_HIGH;
				}
			}
			
			//名称
			String moduleshow = entry.getKey();
			String moduleTip = "【模块名称】最长16个汉字";
			String moduleColor = "";
			
			//属性2
			String attr2show = entry.getValue().get("负责人");
			String attr2Tip = "负责人标识符";
			String attr2Color = "";
			
			//数字2
			String num2show = entry.getValue().get("场景");
			String num2Tip = "场景类测试用例个数";
			String num2Color = "";
			if(num2show != null) {
				if(Integer.parseInt(num2show) > 2) {
					num2Color = GColor.SCENE_HIGH;
					moduleColor = GColor.SCENE;
					attr2Color = GColor.SCENE_HIGH;
				}else if(Integer.parseInt(num2show) == 2) {
					num2Color = GColor.ALGO_HIGH;
					moduleColor = GColor.ALGO;
					attr2Color = GColor.ALGO_HIGH;
				}else if(Integer.parseInt(num2show) == 1) {
					num2Color = GColor.OPENCLOSE_HIGH;
					moduleColor = GColor.OPENCLOSE;
					attr2Color = GColor.OPENCLOSE_HIGH;
				}else {
					num2Color = GColor.UNCOVER_HIGH;
					moduleColor = GColor.UNCOVER;
					attr2Color = GColor.UNCOVER_HIGH;
				}
			}
			
			
			String attr1 = "";
			if(attr1show != null && !attr1show.isEmpty()) {
				attr1 = "													    				<tr bgcolor=\"" + attr1Color + "\">\r\n"
					 	+ "													    					<td title=\"" + attr1Tip + "\">\r\n"
					 	+ "													    						<font size=\"1\">" + attr1show + "</font>\r\n"
					 	+ "													    					</td>\r\n"
					 	+ "													    				</tr>\r\n";
			}
			String num1 = "";
			if(num1show != null && !num1show.isEmpty()) {
				num1 = "													    				<tr bgcolor=\"" + num1Color + "\">\r\n"
					 	+ "													    					<td title=\"" + num1Tip + "\">\r\n"
					 	+ "													    						<font size=\"1\">" + num1show +"</font>\r\n"
					 	+ "													    					</td>\r\n"
					 	+ "													    				</tr>\r\n";
			}
			String module = "";
			if(moduleshow != null && !moduleshow.isEmpty()) {
				module = "										    				<tr bgcolor=\"" + moduleColor + "\">\r\n"
				 		+ "										    					<td align=\"center\" title=\"" + moduleTip + "\">\r\n"
				 		+ "										    						<font size=\"1\">" + moduleshow + "</font>\r\n"
				 		+ "										    					</td>\r\n"
				 		+ "										    				</tr>\r\n";
			}
			String attr2 = "";
			if(attr2show != null && !attr2show.isEmpty()) {
				attr2 = "													    				<tr bgcolor=\"" + attr2Color + "\">\r\n"
					 	+ "													    					<td title=\"" + attr2Tip + "\">\r\n"
					 	+ "													    						<font size=\"1\">" + attr2show + "</font>\r\n"
					 	+ "													    					</td>\r\n"
					 	+ "													    				</tr>\r\n";
			}
			String num2 = "";
			if(num2show != null && !num2show.isEmpty()) {
				num2 = "													    				<tr bgcolor=\"" + num2Color + "\">\r\n"
						+ "													    					<td title=\"" + num2Tip + "\">\r\n"
						+ "													    						<font size=\"1\">" + num2show +"</font>\r\n"
						+ "													    					</td>\r\n"
						+ "													    				</tr>\r\n";
			}
			
			String moduleBorder = "2";
			String moduleWidth = "110px";
			String moduleHeight = "110px";
			
			dynamicTable.append("<div>" + "								<table border=\"")
					.append(moduleBorder).append("\" width=\"")
					.append(moduleWidth).append("\" height=\"")
					.append(moduleHeight).append("\" cellspacing=\"0px\" cellpadding=\"0px\">\r\n")
					.append("					   				<tr bgcolor=\"").append(moduleColor)
					.append("\">\r\n").append("					   					<td>\r\n")
					.append("					   						<table width=\"100%\" height=\"100%\" cellspacing=\"0px\" cellpadding=\"0px\">\r\n")
					.append("						   						<tr valign=\"top\">\r\n")
					.append("						   							<td>\r\n")
					.append("						    							<table width=\"100%\" cellspacing=\"0px\" cellpadding=\"0px\">\r\n")
					.append("							    							<tr>\r\n")
					.append("							    								<td align=\"left\" width=\"50%\">\r\n")
					.append("													    			<table border=\"1\" cellspacing=\"0px\" cellpadding=\"0px\">\r\n")
					.append(attr1)
					.append("													    			</table>\r\n")
					.append("													    		</td>\r\n")
					.append("													    		<td align=\"right\" width=\"50%\">\r\n")
					.append("													    			<table border=\"1\" cellspacing=\"0px\" cellpadding=\"0px\">\r\n")
					.append(num1)
					.append("												    				</table>\r\n")
					.append("													    		</td>\r\n")
					.append("							    							</tr>\r\n")
					.append("							    						</table>\r\n")
					.append("					   						   		</td>\r\n")
					.append("						    					</tr>")
					.append("						    					<tr valign=\"middle\">\r\n")
					.append("						   							<td>\r\n")
					.append("						    							<table width=\"100%\" cellspacing=\"0px\" cellpadding=\"0px\">\r\n")
					.append(module).append("									    				</table>\r\n")
					.append("					   						   		</td>\r\n")
					.append("						    					</tr>")
					.append("												<tr valign=\"bottom\">\r\n")
					.append("						   							<td>\r\n")
					.append("						    							<table width=\"100%\" cellspacing=\"0px\" cellpadding=\"0px\">\r\n")
					.append("							    							<tr>\r\n")
					.append("												    			<td align=\"left\" width=\"50%\">\r\n")
					.append("												    				<table border=\"1\" cellspacing=\"0px\" cellpadding=\"0px\">\r\n")
					.append(attr2).append("													    			</table>\r\n")
					.append("													    		</td>\r\n")
					.append("													    		<td align=\"right\" width=\"50%\">\r\n")
					.append("													    			<table border=\"1\" cellspacing=\"0px\" cellpadding=\"0px\">\r\n")
					.append(num2).append("												    				</table>\r\n")
					.append("												    			</td>\r\n")
					.append("												    		</tr>\r\n")
					.append("												    	</table>\r\n")
					.append("					   						   		</td>\r\n")
					.append("						    					</tr>\r\n")
					.append("					   						</table>\r\n")
					.append("					   				    </td>\r\n")
					.append("						    		</tr>\r\n")
					.append("								</table>")
					.append("</div>");
		}
		dynamicTable.append("</div>");
		
		return dynamicTable.toString();
	}
	
	/**
	 *  加载html版本测试详情测试报告内容
	 *
	 *  @return 报告内容
	 */	
	@SuppressWarnings("StatementWithEmptyBody")
    public String loadingDetailReport() {
		StringBuilder dynamicTable = new StringBuilder();
		
		int nIndex = 1;
		for(;nIndex < report_Table_Content_Text.size() + 1;nIndex++){
			
			String res_Code_Temp = report_Table_Content_Text.get(nIndex).get("响应码");
			
			if((report_Table_Content_Text.get(nIndex)) != null) {
				//仅显示失败
				if(res_Code_Temp.equals("0000")) {

				}else{
					if ((report_Table_Content_Text.get(nIndex)).get("响应信息").length()>1024) {
					  String newCodeStr = (report_Table_Content_Text.get(nIndex)).get("响应信息").substring(0, 300);
					  (report_Table_Content_Text.get(nIndex)).put("响应信息", newCodeStr);
                    }
					dynamicTable.append("<tr align=\"center\" bgcolor=\"" + GColor.FAIL_HIGH_RED + "\">" + "<td style = \"width:100px;word-wrap:break-word;\"><font size=\"4\">")
							.append((report_Table_Content_Text.get(nIndex)).get("用例类型"))
							.append("</font></td>")
							.append("<td style = \"width:100px;word-wrap:break-word;\"><font size=\"4\">")
							.append((report_Table_Content_Text.get(nIndex)).get("用例编码"))
							.append("</font></td>")
							.append("<td style = \"width:100px;word-wrap:break-word;\"><font size=\"4\">")
							.append((report_Table_Content_Text.get(nIndex)).get("用例名称"))
							.append("</font></td>")
							.append("<td style = \"width:100px;word-wrap:break-word;\"><font size=\"4\">")
							.append((report_Table_Content_Text.get(nIndex)).get("响应码"))
							.append("</font></td>")
							.append("<td style = \"width:200px;word-wrap:break-word;\"><font size=\"4\">")
							.append((report_Table_Content_Text.get(nIndex)).get("响应信息"))
							.append("</font></td>")
							.append("<td style = \"width:100px;word-wrap:break-word;\"><font size=\"4\">")
							.append((report_Table_Content_Text.get(nIndex)).get("等级"))
							.append("</font></td>")
							.append("<td style = \"width:100px;word-wrap:break-word;\"><a href=\"")
							.append((report_Table_Content_Text.get(nIndex)).get("链接"))
							.append("\"><font size=\"4\">访问地址</font></a></td>")
							.append("<td style = \"width:106px;word-wrap:break-word;\"><font size=\"4\">")
							.append((report_Table_Content_Text.get(nIndex)).get("备注"))
							.append("</font></td>")
							.append("</tr>");
				}
			}
    	}
		
		return dynamicTable.toString();
	}
	
	/**
	 *  导出html测试报告
	 *  
	 *  @param reportType 报告类型，目前支持："caverage"-覆盖率模式；"quality"-质量模式；默认-详情模式。
	 */	
	public void exportReport(String reportType) {
		try {
			GStatic.gDetail.setREPORT_NAME(GPath.REPORT_PATH + GStatic.gSut.getSysName() + "_TESTREPORT_" + GTime.getCurrentTime(GTime.FORMAT_14) + ".html");
			switch(reportType) {
				case "caverage":
				case "1":{
					GCoverageReport.exportReport();
					break;
				}
				case "quality":
				case "2":{
					GStatic.gQualityReport.exportReport();
					break;
				}
				case "substitute":
				case "3":{
					GStatic.gSubstitute.exportReport();
					break;
				}
				case "spendtimes":
				case "4":{
					GStatic.gSpendtimes.exportReport();
					break;
				}
				case "testcasecount":
				case "5":{
					GStatic.gTestCaseCounts.exportReport();
					break;
				}
				default:{
					GStatic.gDetail.exportReport();
					break;
				}
			}
		} catch (Exception e) {
			GLog.logSysFunctionException("exportRport", e);
		}
	}
	
	/**
	 *  导出html测试报告，并指定邮件名称关键词
	 *  
	 *  @param reportType 报告类型，目前支持："caverage"-覆盖率模式；"quality"-质量模式；默认-详情模式。
	 *  @param mailSubject 邮件名称
	 */	
	public void report(String reportType, String mailSubject) {
		long startTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom(GMissionMsg.getMisstionTip("ECHARTING START"));
		if(GStatic.gP.isbAutoCheckReport()) {
			GStatic.gMail.setSubject(mailSubject);
			if(reportType.equals("all")) {
				this.exportReport("0");
				this.exportReport("1");
				this.exportReport("2");
				this.exportReport("3");
				this.exportReport("4");
				this.exportReport("5");
			}else {
				exportReport(reportType);
			}
		}
		long endTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom("ECHARTING -SPEND:" + (endTime - startTime) + "MS");
		GFile.writeStringToGuideBottom(GMissionMsg.getMisstionTip("ECHARTING END"));
		GFile.writeStringToGuideBottom(GStatic.gAbout.getEnding());
	}
}
