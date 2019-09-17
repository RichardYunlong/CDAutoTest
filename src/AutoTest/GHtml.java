package AutoTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;

public class GHtml {
	private GHtml(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  html版本测试报告模板位置
	 */
	public static final String REPORT_TEMP = "./config/template.html";
	
	/**
	 *  html版本测试报告帮助文件位置
	 */
	public static final String HELPER_TEMP = "./config/help.html";
	
	/**
	 *  html版本测试报告存储位置
	 */
	public static final String REPORT_PATH = "./report/";
	
	/**
	 *  html版本测试报告文件全名
	 */
	public static final String REPORT_NAME = REPORT_PATH + GParam.strTestVersion + "_TESTREPORT_" + GTime.getCurrentTime(GTime.FORMAT_14) + ".html";
	
	/**
	 *  html版本测试报告帮助文件全名
	 */
	public static final String REPORT_NAME_HELPER = REPORT_PATH + "help.html";
	
	/**
	 *  导出html版本测试报告
	 */	
	public static void OutPutHtml() {
		GFile.deleteFolder(REPORT_PATH);
		GFile.creatDir(REPORT_PATH);
		
		if(GFile.copyFile(REPORT_TEMP, REPORT_NAME) && GFile.copyFile(HELPER_TEMP, REPORT_NAME_HELPER)) {
			File templateFile = new File(REPORT_NAME);
			String content = null;
			try {
				content = FileUtils.readFileToString(templateFile, "utf-8");
				
				//以下为替换关键字的值
				content = content.replaceAll("###version###", GParam.strTestVersion);
				content = content.replaceAll("###date###", GTime.getCurrentTime(GTime.FORMAT_14_TEXT));
				
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				content = content.replaceAll("###startdate###", dateformat.format(GTestMission.startSysTime));
				content = content.replaceAll("###enddate###", dateformat.format(GTestMission.endSysTime));
				content = content.replaceAll("###spendtime###", String.valueOf(GTestMission.endSysTime - GTestMission.startSysTime) + "MS");
				
				content = content.replaceAll("###total_num###", String.valueOf(GProgress.getTestTotalNo()));
				Integer dRunTotal = GProgress.dTestReal + GProgress.dTestFail + GProgress.dTestUnReal + GProgress.dTestUnDo;
				content = content.replaceAll("###run_num###", dRunTotal.toString());
				Integer dJumpTotal = GProgress.getTestTotalNo() - dRunTotal;
				content = content.replaceAll("###jump_num###", dJumpTotal.toString());
				
				int dNum0 = 0;
				int dNum1 = 0;
				int dNum2 = 0;
				int dNum3 = 0;
				content = content.replaceAll("###success_total_num###", GTestPlan.dTestReal.toString());
				content = content.replaceAll("###success_run_num###", GProgress.dTestReal.toString());
				content = content.replaceAll("###success_pass_num###", GProgress.dTestReal.toString());
				dNum0 = GTestPlan.dTestReal.intValue() - GProgress.dTestReal.intValue();
				content = content.replaceAll("###success_fail_num###", String.valueOf(dNum0));
				if(dNum0 == 0) {
					content = content.replaceAll("###success_result###", "通过");
					content = content.replaceAll("###success_color###", "#FFFFFF");
				}else {
					content = content.replaceAll("###success_result###", "未通过");
					content = content.replaceAll("###success_color###", "#FF0000");
				}
				
				
				content = content.replaceAll("###error_total_num###", GTestPlan.dTestFail.toString());
				content = content.replaceAll("###error_run_num###", GProgress.dTestFail.toString());
				content = content.replaceAll("###error_pass_num###", GProgress.dTestFail.toString());
				dNum1 = GTestPlan.dTestFail.intValue() - GProgress.dTestFail.intValue();
				content = content.replaceAll("###error_fail_num###", String.valueOf(dNum1));
				if(dNum1 == 0) {
					content = content.replaceAll("###error_result###", "通过");
					content = content.replaceAll("###error_color###", "#FFFFFF");
				}else {
					content = content.replaceAll("###error_result###", "未通过");
					content = content.replaceAll("###error_color###", "#FF0000");
				}
				
				content = content.replaceAll("###code_total_num###", GTestPlan.dTestUnReal.toString());
				content = content.replaceAll("###code_run_num###", GProgress.dTestUnReal.toString());
				content = content.replaceAll("###code_pass_num###", GProgress.dTestUnReal.toString());
				dNum2 = GTestPlan.dTestUnReal.intValue() - GProgress.dTestUnReal.intValue();
				content = content.replaceAll("###code_fail_num###", String.valueOf(dNum2));
				if(dNum2 == 0) {
					content = content.replaceAll("###code_result###", "通过");
					content = content.replaceAll("###code_color###", "#FFFFFF");
				}else {
					content = content.replaceAll("###code_result###", "未通过");
					content = content.replaceAll("###code_color###", "#FF0000");
				}
				
				content = content.replaceAll("###interrupt_total_num###", GTestPlan.dTestUnDo.toString());
				content = content.replaceAll("###interrupt_run_num###", GProgress.dTestUnDo.toString());
				content = content.replaceAll("###interrupt_pass_num###", GProgress.dTestUnDo.toString());
				dNum3 = GTestPlan.dTestUnDo.intValue() - GProgress.dTestUnDo.intValue();
				content = content.replaceAll("###interrupt_fail_num###", String.valueOf(dNum3));
				if(dNum3 == 0) {
					content = content.replaceAll("###interrupt_result###", "通过");
					content = content.replaceAll("###interrupt_color###", "#FFFFFF");
				}else {
					content = content.replaceAll("###interrupt_result###", "未通过");
					content = content.replaceAll("###interrupt_color###", "#FF0000");
				}
				
				float f1 = 0.25f;
				float f2 = 0.25f;
				float f3 = 0.25f;
				float f4 = 0.25f;
				
				if(dRunTotal.intValue() > 0) {
					f1 = (float)(GTestPlan.dTestReal.floatValue()/dRunTotal.floatValue());
					f2 = (float)(GTestPlan.dTestFail.floatValue()/dRunTotal.floatValue());
					f3 = (float)(GTestPlan.dTestUnReal.floatValue()/dRunTotal.floatValue());
					f4 = (float)(GTestPlan.dTestUnDo.floatValue()/dRunTotal.floatValue());
				}
				
				content = content.replaceAll("###green###", String.valueOf(f1));
				content = content.replaceAll("###red###", String.valueOf(f2));
				content = content.replaceAll("###blue###", String.valueOf(f3));
				content = content.replaceAll("###yellow###", String.valueOf(f4));
				
				if(dNum0 == 0 && dNum1 == 0 && dNum2 == 0 && dNum3 == 0){
					content = content.replaceAll("###summary###", "通过");
					content = content.replaceAll("###summary_color###", "#00CC99");
				}else {
					content = content.replaceAll("###summary###", "未通过");
					content = content.replaceAll("###summary_color###", "#FF0000");
				}
				
				OutputStream fos = new FileOutputStream(templateFile);
				fos.write(content.getBytes("UTF-8"));
				fos.flush();
				fos.close();
				Runtime.getRuntime().exec("cmd.exe /c start " + REPORT_NAME);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			GSys.logSys("HTML TEST REPORT OUTPUT FAILED");
		}
	}
	
	public static void main(String[] agrs) {
		OutPutHtml();
		
//		float a = 18.650001f;
//		int scale = 2;//设置位数   
//		BigDecimal bd = new BigDecimal((double)a);  
//		bd = bd.setScale(scale,BigDecimal.ROUND_DOWN);  
//		a = bd.floatValue();  
//   
//		System.out.print(a);
		
	}
}
