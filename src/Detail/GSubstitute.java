package Detail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import Base.GClazz;
import Base.GFile;
import Base.GMissionMsg;
import Base.GMsg;
import Base.GTime;
import Coverage.GCoverageReport;
import DT.GColor;
import DT.GLog;
import DT.GTaskSummary;
import DT.GTransfer;
import Mail.GMail;
import Quality.GQualityReport;
import Sut.GSut;
import Sys.GPath;
import Sys.GSys;
import Test.GTestMission;
import Test.GTestProgress;

public class GSubstitute {
	
    /**
     *  构造函数
     */
	private GSubstitute(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  报告数据
	 */
	private static GTaskSummary SUBSTITUTE_DATA = new GTaskSummary();
	
	public static GTaskSummary getSUBSTITUTE_DATA() {
		return SUBSTITUTE_DATA;
	}
	
	/**
	 *  旧报告数据
	 */
	private static GTaskSummary SUBSTITUTE_DATA_OLD = new GTaskSummary();
	
	public static GTaskSummary getSUBSTITUTE_DATA_OLD() {
		return SUBSTITUTE_DATA_OLD;
	}
	
	/**
	 *  文件全名
	 */
	private static String SUBSTITUTE_NAME = GPath.REPORT_PATH + "SUBSTITUTE_" + GTime.getCurrentTime(GTime.FORMAT_14) + ".html";
	
	public static String getSUBSTITUTE_NAME() {
		return SUBSTITUTE_NAME;
	}
	
	/**
	 *  报告地址
	 */
	private static String SUBSTITUTE_URL = "";

	public static void setSUBSTITUTE_URL(String sUBSTITUTE_URL) {
		SUBSTITUTE_URL = sUBSTITUTE_URL;
	}
	
	/**
	 *  报告比对地址
	 */
	private static String SUBSTITUTE_URLOLD = "";

	public static void setSUBSTITUTE_URLOLD(String sUBSTITUTE_URLOLD) {
		SUBSTITUTE_URLOLD = sUBSTITUTE_URLOLD;
	}

	/**
	 *  导出报告前检查或更新报告数据
	 */	
	private static void checkAndUpdate() {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GDetail.calEach();
		
		SUBSTITUTE_DATA.setDATE(GTime.getCurrentTime(GTime.FORMAT_8_TEXT));
		SUBSTITUTE_DATA.setSTARTTIME(formatDate.format(GTestMission.getStartSysTime()));
		SUBSTITUTE_DATA.setENDTIME(formatDate.format(GTestMission.getEndSysTime()));
		SUBSTITUTE_DATA.setSPENDTIME(String.valueOf(GTestMission.getEndSysTime() - GTestMission.getStartSysTime()) + "MS");
		SUBSTITUTE_DATA.setTOTALNUM(String.valueOf(GTestProgress.getTotalNum()));
		SUBSTITUTE_DATA.setRUNNUM(String.valueOf(GTestProgress.getdTestReal() + GTestProgress.getdTestFail() + GTestProgress.getdTestUnReal() + GTestProgress.getdTestUnDo()));
		int[] eachNum= Arrays.copyOf(GDetail.getEACHNUM(),GDetail.getEACHNUM().length);
		SUBSTITUTE_DATA.setJUMPNUM(String.valueOf(eachNum[0]));
		SUBSTITUTE_DATA.setSUCCESSNUM(String.valueOf(eachNum[1]));
		SUBSTITUTE_DATA.setFAILEDNUM(String.valueOf(eachNum[2]));
		SUBSTITUTE_DATA.setWARNINGNUM(String.valueOf(eachNum[3]));
		SUBSTITUTE_DATA.setINTERRUPTNUM(String.valueOf(eachNum[4]));
		String[] serverUrl = GTransfer.getgServerUrl().clone();
		SUBSTITUTE_DATA.setURL(serverUrl[0]);
		SUBSTITUTE_DATA.setSID(GSut.getSysName());
		SUBSTITUTE_DATA.setVERSION(GSut.getSectionName1() + "+" + GSut.getSectionName2() + "+" + GSut.getSectionName3());
	}

	/**
	 *  导出报告
	 */	
	public static void exportReport() {
		long startTime = System.currentTimeMillis();
		GSys.logSys(GMissionMsg.getStepStart("SUBSTITUTE HTML REPORT START"));
		GSys.logSys(GTime.getDate() + "WRITE SUBSTITUTE");
		
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
					bResOld = false;
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
				
				int[] eachNum= Arrays.copyOf(GDetail.getEACHNUM(),GDetail.getEACHNUM().length);
				if(eachNum[0] == 0
				&& eachNum[2] == 0 
				&& eachNum[3] == 0 
				&& eachNum[4] == 0
				&& eachNum[1] == Integer.valueOf(SUBSTITUTE_DATA.getRUNNUM()).intValue()){
						content = content.replaceAll("###conclusion_new###", "成功&ensp;");
						content = content.replaceAll("###conclusion_color_new###", GColor.SUCCESS_HIGH_GREEN);
						bResNew = true;
				}else {
					content = content.replaceAll("###conclusion_new###", "失败&ensp;");
					content = content.replaceAll("###conclusion_color_new###", GColor.FAIL_HIGH_RED);
					bResNew = false;
				}
				
				//替换内容
				content = content.replaceAll("###RELOAD###", "399");
				content = content.replaceAll("###RELOADNEW###", GCoverageReport.getALGO_TYPE_NUM().toString());
				content = content.replaceAll("###dymic_substitute###", SUBSTITUTE_DATA.getSUMMARY());
				content = content.replaceAll("###detailold.html###", SUBSTITUTE_URLOLD);
				content = content.replaceAll("###detail.html####", SUBSTITUTE_URL);
				content = content.replaceAll("###coverage.html###", GCoverageReport.getREPORT_URL());
				content = content.replaceAll("###quality.html###", GQualityReport.getREPORT_URL());
				content = content.replaceAll("###spendtime.html###", GSpendtimes.getREPORT_URL());
			} catch (IOException e) {
				GLog.logSysFunctionException("exportRport-content", e);
			}
		
			if(content != null) {
				try(OutputStream fos = new FileOutputStream(templateFile);){
					fos.write(content.getBytes(StandardCharsets.UTF_8));
					fos.flush();
				} catch (IOException e) {
					GLog.logSysFunctionException("exportRport", e);
				}
			}
			//只有原工具和新工具均质性成功才认为综合执行成功
			if(bResOld && bResNew) {
				GMail.sendEmail("[成功]", SUBSTITUTE_NAME);
			}else {
				GMail.sendEmail("[失败]", SUBSTITUTE_NAME);
			}
		}else {
			GSys.logSys("EXPORT SUBSTITUTE HTML REPORT FAILED");
		}
		
		//分割线和结束提示
		long endTime = System.currentTimeMillis();
		GSys.logSys("SUBSTITUTE HTML REPORT -SPEND:" + (endTime - startTime) + "MS");
		GSys.logSys(GMissionMsg.getStepEnd());
	}
}
