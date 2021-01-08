package AutoTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

/**
 * 报表
 */
public class GECharts {
	
	/**
	 * 
	 */
	private GECharts(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 * 用例类型
	 */
	private static String type_Temp = "";
	
	/**
	 * 用例编码
	 */
	private static String no_Temp = "";
	
	/**
	 * 用例名称
	 */
	private static String name_Temp = "";
	
	/**
	 * 用例响应码
	 */
	private static String res_Code_Temp = "";
	
	/**
	 * 用例响应信息
	 */
	private static String res_Msg_Temp = "";
	
	/**
	 * 用例描述
	 */
	private static String scr_Temp = "";
	
	/**
	 * 用例等级
	 */
	private static String level_Temp = "";
	
	/**
	 * 用例链接
	 */
	private static String url_Temp = "";
	
	/**
	 * 备注
	 */
	private static String mark_temp = "";

	/**
	 *  html版本测试详情测试报告内容计数器
	 */
	private static Integer report_Table_Content_Index = 0;
	
	/**
	 *  html版本测试详情测试报告内容
	 */
	private static Map<Integer, Map<String, String>> report_Table_Content_Text = new HashMap<Integer, Map<String, String>>();
	
	/**
	 *  添加html版本测试详情测试报告内容-枚举模式
	 *  
	 *  @param type 用例类型
	 *  @param no 用例编码
	 *  @param name 用例名称
	 *  @param code 用例响应码
	 *  @param msg 用例响应信息
	 *  @param scr 用例描述
	 *  @param url 用例链接
	 *  @param mark_temp 备注
	 */	
	public static void addDetailRport(String type, 
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
		
		Map<String, String> caseTemp = new HashMap<String, String>();
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
		
		if(GParam.bAutoCheckReport) {
			GFile.writeStringToBottom(GLog.strLogStyle[4], caseTemp.toString());
		}
	}
	
	/**
	 *  添加html版本测试详情测试报告内容-String分割模式
	 *  
	 *  @param case_Temp 用例类型
	 */	
	public static void addDetailRport(String case_Temp) {	
		Map<String, String> caseTemp = new HashMap<String, String>();
	
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
			
			if(GParam.bAutoCheckReport) {
				GFile.writeStringToBottom(GLog.strLogStyle[4], caseTemp.toString());
			}
		}else {
			GFile.writeStringToBottom(GLog.strLogStyle[8], "参数错误，相关条目添加失败:" + case_Temp);
		}
	}
	
	/**
	 *  加载html版本测试详情测试报告内容
	 */	
	public static String loadingDetailRport() {
		String dynamicTable = "";
		String color_Temp = "";
		
		int nIndex = 1;
		for(;nIndex < report_Table_Content_Text.size() + 1;nIndex++){
			
			String res_Code_Temp = report_Table_Content_Text.get(Integer.valueOf(nIndex)).get("响应码");
			
			if((report_Table_Content_Text.get(Integer.valueOf(nIndex))) != null) {
				//仅显示失败
				if(res_Code_Temp.equals("0000")) {
					color_Temp = GColor.SUCCESS_HIGH_GREEN;
				}else{
					color_Temp = GColor.FAIL_HIGH_RED;
					dynamicTable +=  "<tr align=\"center\" bgcolor=\"" + color_Temp + "\">"
						     + "<td style = \"width:100px;word-wrap:break-word;\"><font size=\"4\">" + (report_Table_Content_Text.get(Integer.valueOf(nIndex))).get("用例类型") + "</font></td>"
						     + "<td style = \"width:100px;word-wrap:break-word;\"><font size=\"4\">" + (report_Table_Content_Text.get(Integer.valueOf(nIndex))).get("用例编码") + "</font></td>"
						     + "<td style = \"width:100px;word-wrap:break-word;\"><font size=\"4\">" + (report_Table_Content_Text.get(Integer.valueOf(nIndex))).get("用例名称") + "</font></td>"
							 + "<td style = \"width:100px;word-wrap:break-word;\"><font size=\"4\">" + (report_Table_Content_Text.get(Integer.valueOf(nIndex))).get("响应码") + "</font></td>" 
							 + "<td style = \"width:200px;word-wrap:break-word;\"><font size=\"4\">" + (report_Table_Content_Text.get(Integer.valueOf(nIndex))).get("响应信息") + "</font></td>"
							 + "<td style = \"width:100px;word-wrap:break-word;\"><font size=\"4\">" + (report_Table_Content_Text.get(Integer.valueOf(nIndex))).get("等级") + "</font></td>" 
							 + "<td style = \"width:100px;word-wrap:break-word;\"><a href=\"" + (report_Table_Content_Text.get(Integer.valueOf(nIndex))).get("链接") + "\"><font size=\"4\">访问地址</font></a></td>"
							 + "<td style = \"width:106px;word-wrap:break-word;\"><font size=\"4\">" + (report_Table_Content_Text.get(Integer.valueOf(nIndex))).get("备注") + "</font></td>"
						     +"</tr>";
				}
			}
    	}
		
		return dynamicTable;
	}
	
	/**
	 *  导出html版本测试详情测试报告
	 */	
	public static void exportDetailRport() {
		int splitNum = 16;
		GFile.writeStringToBottom(GLog.strLogStyle[8], GText.getRandomStringByLength(splitNum, "-") + "EXPORT DETAIL HTML REPORT START" + GText.getRandomStringByLength(splitNum, "-") + "\r\n");
		
		GFile.deleteFolder(GPath.REPORT_NAME);
		if(GFile.creatDir(GPath.REPORT_PATH)) {
			GFile.writeStringToBottom(GLog.strLogStyle[8], GMsg.MSG_READY[0]);
		}
		GSummary.LoadSummary();
		
		if(GFile.copyFile(GPath.REPORT_TEMP, GPath.REPORT_NAME) && GFile.copyFile(GPath.HELPER_TEMP, GPath.REPORT_NAME_HELPER)) {
			File templateFile = new File(GPath.REPORT_NAME);
			String content = null;
			OutputStream fos = null;
			try {
				content = FileUtils.readFileToString(templateFile, "utf-8");
				
				//替换数据
				content = content.replaceAll("###sut_name###", GSummary.TAR_VERSION);
				content = content.replaceAll("###sut_version###", GSummary.TAR_VERSION);
				content = content.replaceAll("###test_date###", GSummary.TAR_DATE);
				content = content.replaceAll("###start_time###", GSummary.TAR_STARTDATE);
				content = content.replaceAll("###end_time###", GSummary.TAR_ENDDATE);
				content = content.replaceAll("###spend_time###",  GSummary.TAR_SPENDTIME);
				content = content.replaceAll("###total_num###", GSummary.TAR_LOADTOTALNO);
				content = content.replaceAll("###jump_num###", GSummary.strFailNum_Each[0]);
				content = content.replaceAll("###run_num###", GSummary.TAR_RUNTOTALNO);

				//加载正常场景用例统计数据并替换背景颜色
				content = content.replaceAll("###success_num###", GProgress.dTestReal.toString());
				content = content.replaceAll("###failed_num###", GProgress.dTestFail.toString());
				content = content.replaceAll("###warning_num###", GProgress.dTestUnReal.toString());
				content = content.replaceAll("###interrupt_num###", GProgress.dTestUnDo.toString());
				
				//加载测试结果
				if(GSummary.FailNum_Each[0] == 0
						&& GSummary.FailNum_Each[1] == 0 
						&& GSummary.FailNum_Each[2] == 0 
						&& GSummary.FailNum_Each[3] == 0 
						&& GSummary.FailNum_Each[4] == 0){
					content = content.replaceAll("###conclusion###", "成功");
					content = content.replaceAll("###conclusion_color###", GColor.SUCCESS_HIGH_GREEN);
				}else {
					content = content.replaceAll("###conclusion###", "失败");
					content = content.replaceAll("###conclusion_color###", GColor.FAIL_HIGH_RED);
				}
				
				content = content.replaceAll("###dymic_table###", loadingDetailRport());
				
				fos = new FileOutputStream(templateFile);
				fos.write(content.getBytes("UTF-8"));
				fos.flush();
				fos.close();
				Runtime.getRuntime().exec("cmd.exe /c start " + GPath.REPORT_NAME);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(fos != null)fos.close();
				} catch (IOException e) {
					GLog.logShowConsole(GMsg.MSG_CONSOLE[0] + content);
					e.printStackTrace();
				}
			}
		}else {
			GSys.logSys("EXPORT DETAIL HTML REPORT FAILED");
		}
		GFile.writeStringToBottom(GSys.GUIDE,GText.getRandomStringByLength(splitNum, "-") + "EXPORT DETAIL HTML REPORT END" + GText.getRandomStringByLength(splitNum, "-") + "\r\n");	
	}
	
	/**
	 *  导出html版本覆盖率测试报告
	 */	
	public static void exportCoverageRport() {
		int splitNum = 16;
		GFile.writeStringToBottom(GLog.strLogStyle[8], GText.getRandomStringByLength(splitNum, "-") + "EXPORT CAVERAGE HTML REPORT START" + GText.getRandomStringByLength(splitNum, "-") + "\r\n");
		
		//执行历史删除，无论结果如何
		GFile.deleteFile(GPath.REPORT_CAVERAGE);
		//创建新的待写入文件路径
		if(GFile.creatDir(GPath.REPORT_PATH)) {
			GFile.writeStringToBottom(GLog.strLogStyle[8], GMsg.MSG_READY[0]);
		}
		
		//复制模板资源至导出区，创建并写入报告文件
		if(GFile.copyFile(GPath.REPORT_CAVERAGE_TEMP, GPath.REPORT_CAVERAGE)) {
			File templateFile = new File(GPath.REPORT_CAVERAGE);
			String content = null;
			OutputStream fos = null;
			try {
				content = FileUtils.readFileToString(templateFile, "utf-8");
				content = content.replaceAll("###fullName###", GLoadConfig.sysName);
				
				fos = new FileOutputStream(templateFile);
				fos.write(content.getBytes("UTF-8"));
				fos.flush();
				fos.close();
				Runtime.getRuntime().exec("cmd.exe /c start " + GPath.REPORT_CAVERAGE);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(fos != null)fos.close();
				} catch (IOException e) {
					GLog.logShowConsole(GMsg.MSG_CONSOLE[0] + content);
					e.printStackTrace();
				}
			}
		}else {
			GSys.logSys("EXPORT CAVERAGE HTML REPORT FAILED");
		}
		
		GFile.writeStringToBottom(GSys.GUIDE,GText.getRandomStringByLength(splitNum, "-") + "EXPORT CAVERAGE HTML REPORT END" + GText.getRandomStringByLength(splitNum, "-") + "\r\n");	
	}
	
	/**
	 *  导出html版本覆盖率测试报告
	 */	
	public static void exportQualityRport() {
		int splitNum = 16;
		GFile.writeStringToBottom(GLog.strLogStyle[8], GText.getRandomStringByLength(splitNum, "-") + "EXPORT QUALITY HTML REPORT START" + GText.getRandomStringByLength(splitNum, "-") + "\r\n");
		
		//执行历史删除，无论结果如何
		GFile.deleteFile(GPath.REPORT_QUALITY);
		//创建新的待写入文件路径
		if(GFile.creatDir(GPath.REPORT_PATH)) {
			GFile.writeStringToBottom(GLog.strLogStyle[8], GMsg.MSG_READY[0]);
		}
		
		//复制模板资源至导出区，创建并写入报告文件
		if(GFile.copyFile(GPath.REPORT_QUALITY_TEMP, GPath.REPORT_QUALITY)) {
			File templateFile = new File(GPath.REPORT_QUALITY);
			String content = null;
			OutputStream fos = null;
			try {
				content = FileUtils.readFileToString(templateFile, "utf-8");
				
				//写入被测件名称
				content = content.replaceAll("###fullName###", GLoadConfig.sysName);
				
				
				fos = new FileOutputStream(templateFile);
				fos.write(content.getBytes("UTF-8"));
				fos.flush();
				fos.close();
				Runtime.getRuntime().exec("cmd.exe /c start " + GPath.REPORT_QUALITY);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(fos != null)fos.close();
				} catch (IOException e) {
					GLog.logShowConsole(GMsg.MSG_CONSOLE[0] + content);
					e.printStackTrace();
				}
			}
		}else {
			GSys.logSys("EXPORT QUALITY HTML REPORT FAILED");
		}
		
		GFile.writeStringToBottom(GSys.GUIDE,GText.getRandomStringByLength(splitNum, "-") + "EXPORT QUALITY HTML REPORT END" + GText.getRandomStringByLength(splitNum, "-") + "\r\n");	
	}
	
	/**
	 *  导出html测试报告
	 *  
	 *  @param reportType 报告类型，目前支持："caverage"-覆盖率模式；"quality"-质量模式；默认-详情模式。
	 */	
	public static void exportRport(String reportType) {
		switch(reportType) {
			case "caverage":
			case "1":{
				exportCoverageRport();
				break;
			}
			case "quality":
			case "2":{
				exportQualityRport();
				break;
			}
			default:{
				exportDetailRport();
				break;
			}
		}
	}
	
	public static void main(String[] agrs) {
		GTestMission.tmInit();
		GTestMission.tmLogOn();
		GTestMission.tmPreErrorCode();
		GTestMission.tmDateProvider();
		addDetailRport("1|2333|33333333|42222222|1111111222222221115|212121212122222222222222222212126|222222222227|212121212122222222222222222212126|212121212122222222222222222212126");
		exportRport("0");
	}
}
