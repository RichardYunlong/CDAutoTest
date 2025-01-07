package Detail;

import Base.*;
import Coverage.GCoverageReport;
import DT.GColor;
import DT.GLog;
import DT.GTaskSummary;
import Sys.GPath;
import Sys.GStatic;
import Test.GTestMission;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 *  报告动态内容替换
 */
public class GSubstitute {
	
    /**
     *  构造函数
     */
	public GSubstitute(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  报告数据
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private GTaskSummary SUBSTITUTE_DATA = new GTaskSummary();
	public GTaskSummary getSUBSTITUTE_DATA() {
		return SUBSTITUTE_DATA;
	}
	
	/**
	 *  旧报告数据
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private GTaskSummary SUBSTITUTE_DATA_OLD = new GTaskSummary();
	public GTaskSummary getSUBSTITUTE_DATA_OLD() {
		return SUBSTITUTE_DATA_OLD;
	}
	
	/**
	 *  文件全名
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private String SUBSTITUTE_NAME = GPath.REPORT_PATH + "SUBSTITUTE_" + GTime.getCurrentTime(GTime.FORMAT_14) + ".html";
	public String getSUBSTITUTE_NAME() {
		return SUBSTITUTE_NAME;
	}
	
	/**
	 *  报告地址
	 */
	private String SUBSTITUTE_URL = "";
	public void setSUBSTITUTE_URL(String sUBSTITUTE_URL) {
		SUBSTITUTE_URL = sUBSTITUTE_URL;
	}
	
	/**
	 *  报告比对地址
	 */
	private String SUBSTITUTE_URLOLD = "";
	public void setSUBSTITUTE_URLOLD(String sUBSTITUTE_URLOLD) {
		SUBSTITUTE_URLOLD = sUBSTITUTE_URLOLD;
	}

	/**
	 *  导出报告前检查或更新报告数据
	 */	
	private void checkAndUpdate() {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GStatic.gDetail.calEach();

		SUBSTITUTE_DATA.setDATE(GTime.getCurrentTime(GTime.FORMAT_8_TEXT));
		SUBSTITUTE_DATA.setSTARTTIME(formatDate.format(GStatic.gTestMission.getStartSysTime()));
		SUBSTITUTE_DATA.setENDTIME(formatDate.format(GStatic.gTestMission.getEndSysTime()));
		SUBSTITUTE_DATA.setSPENDTIME(GStatic.gTestMission.getEndSysTime() - GStatic.gTestMission.getStartSysTime() + "MS");
		SUBSTITUTE_DATA.setTOTALNUM(String.valueOf(GTestMission.gTestProgress.getTotalNum()));
		SUBSTITUTE_DATA.setRUNNUM(String.valueOf(GTestMission.gTestProgress.getdTestReal() + GTestMission.gTestProgress.getdTestFail() + GTestMission.gTestProgress.getdTestUnReal() + GTestMission.gTestProgress.getdTestUnDo()));
		int[] eachNum= Arrays.copyOf(GStatic.gDetail.getEACHNUM(), GStatic.gDetail.getEACHNUM().length);
		SUBSTITUTE_DATA.setJUMPNUM(String.valueOf(eachNum[0]));
		SUBSTITUTE_DATA.setSUCCESSNUM(String.valueOf(eachNum[1]));
		SUBSTITUTE_DATA.setFAILEDNUM(String.valueOf(eachNum[2]));
		SUBSTITUTE_DATA.setWARNINGNUM(String.valueOf(eachNum[3]));
		SUBSTITUTE_DATA.setINTERRUPTNUM(String.valueOf(eachNum[4]));
		String[] serverUrl = GStatic.gTransfer.getgServerUrl().clone();
		SUBSTITUTE_DATA.setURL(serverUrl[0]);
		SUBSTITUTE_DATA.setSID(GStatic.gSut.getSysName());
		SUBSTITUTE_DATA.setVERSION(GStatic.gSut.getSectionName1() + "+" + GStatic.gSut.getSectionName2() + "+" + GStatic.gSut.getSectionName3());
	}

	/**
	 *  导出报告
	 */
	public void exportReport() {
		long startTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom(GMissionMsg.getStepStart("SUBSTITUTE HTML REPORT START"));
		GFile.writeStringToGuideBottom(GTime.getDate() + "WRITE SUBSTITUTE");

		//创建存放路径
		GFile.deleteFolder(SUBSTITUTE_NAME);
		if(GFile.creatDir(GPath.REPORT_PATH)) {
			String[] logPath = GLog.getLogpath().clone();
			GFile.writeStringToBottom(logPath[8], GMsg.MSG_READY[0]);
		}

		//导出前检查并更新报告数据
		checkAndUpdate();

		//邮件是否发送标记
		boolean bResOld = false;
		boolean bResNew = false;
		//复制模板并开始写入报告文件
		if(GFile.copyFile(GPath.SUBSTITUTE_TEMP, SUBSTITUTE_NAME)
		&& GFile.copyFile(GPath.SUBSTITUTE_HELPER_TEMP, GPath.REPORT_SUBSTITUTE_HELPER)) {
			File templateFile = new File(SUBSTITUTE_NAME);
			String content = null;

			try{
				content = FileUtils.readFileToString(templateFile, "utf-8");

				//替换数据及颜色-原工具数据
				content = content.replaceAll("###task_date_old###", SUBSTITUTE_DATA_OLD.getDATE());
				content = content.replaceAll("###task_name_old###", SUBSTITUTE_DATA_OLD.getNAME());
				content = content.replaceAll("###task_description_old###", SUBSTITUTE_DATA_OLD.getDESC());
				content = content.replaceAll("###task_start_time_old###", SUBSTITUTE_DATA_OLD.getSTARTTIME());
				content = content.replaceAll("###task_end_time_old###", SUBSTITUTE_DATA_OLD.getENDTIME());
				content = content.replaceAll("###task_server_url_old###", SUBSTITUTE_DATA_OLD.getURL());
				content = content.replaceAll("###task_server_name_old###", SUBSTITUTE_DATA_OLD.getSID());
				content = content.replaceAll("###task_server_version_old###", SUBSTITUTE_DATA_OLD.getVERSION());
				content = content.replaceAll("###task_cost_time_old###", SUBSTITUTE_DATA_OLD.getSPENDTIME());
				content = content.replaceAll("###task_case_total_num_old###", SUBSTITUTE_DATA_OLD.getTOTALNUM());
				content = content.replaceAll("###task_case_jump_num_old###", SUBSTITUTE_DATA_OLD.getJUMPNUM());
				content = content.replaceAll("###task_case_run_num_old###", SUBSTITUTE_DATA_OLD.getRUNNUM());
				content = content.replaceAll("###task_case_success_num_old###", SUBSTITUTE_DATA_OLD.getSUCCESSNUM());
				content = content.replaceAll("###task_case_failed_num_old###", SUBSTITUTE_DATA_OLD.getFAILEDNUM());
				content = content.replaceAll("###task_case_warning_num_old###", SUBSTITUTE_DATA_OLD.getWARNINGNUM());
				content = content.replaceAll("###task_case_interrupt_num_old###", SUBSTITUTE_DATA_OLD.getINTERRUPTNUM());

				if(SUBSTITUTE_DATA_OLD.getTOTALNUM().equals(SUBSTITUTE_DATA_OLD.getRUNNUM())){
						content = content.replaceAll("###conclusion_old###", "成功");
						content = content.replaceAll("###conclusion_color_old###", GColor.SUCCESS_HIGH_GREEN);
						bResOld = true;
				}else {
					content = content.replaceAll("###conclusion_old###", "失败");
					content = content.replaceAll("###conclusion_color_old###", GColor.FAIL_HIGH_RED);
                }

				//替换数据及颜色-新工具数据
				content = content.replaceAll("###task_date_new###", SUBSTITUTE_DATA.getDATE() + "&ensp;");
				content = content.replaceAll("###task_name_new###", SUBSTITUTE_DATA.getNAME() + "&ensp;");
				content = content.replaceAll("###task_description_new###", SUBSTITUTE_DATA.getDESC() + "&ensp;");
				content = content.replaceAll("###task_start_time_new###", SUBSTITUTE_DATA.getSTARTTIME() + "&ensp;");
				content = content.replaceAll("###task_end_time_new###", SUBSTITUTE_DATA.getENDTIME() + "&ensp;");
				content = content.replaceAll("###task_server_url_new###", SUBSTITUTE_DATA.getURL() + "&ensp;");
				content = content.replaceAll("###task_server_name_new###", SUBSTITUTE_DATA.getSID() + "&ensp;");
				content = content.replaceAll("###task_server_version_new###", SUBSTITUTE_DATA.getVERSION() + "&ensp;");
				content = content.replaceAll("###task_cost_time_new###", SUBSTITUTE_DATA.getSPENDTIME() + "&ensp;");
				content = content.replaceAll("###task_case_total_num_new###", SUBSTITUTE_DATA.getTOTALNUM() + "&ensp;");
				content = content.replaceAll("###task_case_jump_num_new###", SUBSTITUTE_DATA.getJUMPNUM() + "&ensp;");
				content = content.replaceAll("###task_case_run_num_new###", SUBSTITUTE_DATA.getRUNNUM() + "&ensp;");
				content = content.replaceAll("###task_case_success_num_new###", SUBSTITUTE_DATA.getSUCCESSNUM() + "&ensp;");
				content = content.replaceAll("###task_case_failed_num_new###", SUBSTITUTE_DATA.getFAILEDNUM() + "&ensp;");
				content = content.replaceAll("###task_case_warning_num_new###", SUBSTITUTE_DATA.getWARNINGNUM() + "&ensp;");
				content = content.replaceAll("###task_case_interrupt_num_new###", SUBSTITUTE_DATA.getINTERRUPTNUM() + "&ensp;");

				int[] eachNum= Arrays.copyOf(GStatic.gDetail.getEACHNUM(), GStatic.gDetail.getEACHNUM().length);
				if(eachNum[0] == 0
				&& eachNum[2] == 0
				&& eachNum[3] == 0
				&& eachNum[4] == 0
				&& eachNum[1] == Integer.parseInt(SUBSTITUTE_DATA.getRUNNUM())){
						content = content.replaceAll("###conclusion_new###", "成功&ensp;");
						content = content.replaceAll("###conclusion_color_new###", GColor.SUCCESS_HIGH_GREEN);
						bResNew = true;
				}else {
					content = content.replaceAll("###conclusion_new###", "失败&ensp;");
					content = content.replaceAll("###conclusion_color_new###", GColor.FAIL_HIGH_RED);
                }

				//替换内容
				content = content.replaceAll("###RELOAD###", "399");
				content = content.replaceAll("###RELOADNEW###", GCoverageReport.getALGO_TYPE_NUM().toString());
				content = content.replaceAll("###dymic_substitute###", SUBSTITUTE_DATA.getSUMMARY());
				content = content.replaceAll("###detailold.html###", SUBSTITUTE_URLOLD);
				content = content.replaceAll("###detail.html####", SUBSTITUTE_URL);
				content = content.replaceAll("###coverage.html###", GCoverageReport.getREPORT_URL());
				content = content.replaceAll("###quality.html###", GStatic.gQualityReport.getREPORT_URL());
				content = content.replaceAll("###spendtime.html###", GStatic.gSpendtimes.getREPORT_URL());
			} catch (IOException e) {
				GLog.logSysFunctionException("exportRport-content", e);
			}
		
			if(content != null) {
				try(OutputStream fos = Files.newOutputStream(templateFile.toPath())){
					fos.write(content.getBytes(StandardCharsets.UTF_8));
					fos.flush();
				} catch (IOException e) {
					GLog.logSysFunctionException("exportRport", e);
				}
			}
			//只有原工具和新工具均质性成功才认为综合执行成功
			if(bResOld && bResNew) {
				GStatic.gMail.sendEmail("[成功]", SUBSTITUTE_NAME);
			}else {
				GStatic.gMail.sendEmail("[失败]", SUBSTITUTE_NAME);
			}
		}else {
			GFile.writeStringToGuideBottom("EXPORT SUBSTITUTE HTML REPORT FAILED");
		}
		
		//分割线和结束提示
		long endTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom("SUBSTITUTE HTML REPORT -SPEND:" + (endTime - startTime) + "MS");
		GFile.writeStringToGuideBottom(GMissionMsg.getStepEnd());
	}
}
