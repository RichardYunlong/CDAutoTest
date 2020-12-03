package AutoTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

/**
 *  导出Html报告
 */
public class GHtmlExport {
	private GHtmlExport(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  成功
	 */
	private static final String PASS_CN = "通过";
	
	/**
	 *  失败
	 */
	private static final String FAIL_CN = "未通过";
	
	/**
	 *  正产-强化
	 */
	private static final String SUCCESS_HIGH_GREEN = "#00CC99";
	
	/**
	 *  异常-强化
	 */
	private static final String FAIL_HIGH_RED = "#FF0000";
	
	/**
	 *  错误码-强化
	 */
	private static final String ERRORCODE_HIGH_BLUE = "#0000FF";
	
	/**
	 *  警告-强化
	 */
	private static final String INTERRUPT_HIGH_YELLOW = "#FFFF00";
	
	/**
	 *  常规
	 */
	private static final String NORMAL = "#FFFFFF";
	
	/**
	 *  导出html版本测试报告
	 */	
	public static void OutPutHtml() {
		GFile.deleteFolder(GPath.REPORT_PATH);
		GFile.creatDir(GPath.REPORT_PATH);
		GSummary.LoadSummary();
		
		if(GFile.copyFile(GPath.REPORT_TEMP, GPath.REPORT_NAME) && GFile.copyFile(GPath.HELPER_TEMP, GPath.REPORT_NAME_HELPER)) {
			File templateFile = new File(GPath.REPORT_NAME);
			String content = null;
			OutputStream fos = null;
			try {
				content = FileUtils.readFileToString(templateFile, "utf-8");
				
				//替换数据
				content = content.replaceAll("###version###", GSummary.TAR_VERSION);
				content = content.replaceAll("###date###", GSummary.TAR_DATE);
				content = content.replaceAll("###startdate###", GSummary.TAR_STARTDATE);
				content = content.replaceAll("###enddate###", GSummary.TAR_ENDDATE);
				content = content.replaceAll("###spendtime###",  GSummary.TAR_SPENDTIME);
				content = content.replaceAll("###total_num###", GSummary.TAR_LOADTOTALNO);
				content = content.replaceAll("###run_num###", GSummary.TAR_RUNTOTALNO);
				content = content.replaceAll("###jump_num###", GSummary.strFailNum_Each[0]);
				
				
				//替换用例类型字段背景颜色
				content = content.replaceAll("###success_status_color###", SUCCESS_HIGH_GREEN);
				content = content.replaceAll("###error_status_color###", FAIL_HIGH_RED);
				content = content.replaceAll("###code_status_color###", ERRORCODE_HIGH_BLUE);
				content = content.replaceAll("###interrupt_status_color###", INTERRUPT_HIGH_YELLOW);

				//加载正常场景用例统计数据并替换背景颜色
				content = content.replaceAll("###success_total_num###", GTestPlan.dTestReal.toString());
				content = content.replaceAll("###success_run_num###", GProgress.dTestReal.toString());
				content = content.replaceAll("###success_pass_num###", GProgress.dTestReal.toString());
				content = content.replaceAll("###success_fail_num###", GSummary.strFailNum_Each[1]);
				if(GSummary.FailNum_Each[1] == 0) {
					content = content.replaceAll("###success_result###", PASS_CN);
					content = content.replaceAll("###success_color###", NORMAL);
				}else {
					content = content.replaceAll("###success_result###", FAIL_CN);
					content = content.replaceAll("###success_color###", FAIL_HIGH_RED);
				}
				
				//加载异常场景用例统计数据并替换背景颜色
				content = content.replaceAll("###error_total_num###", GTestPlan.dTestFail.toString());
				content = content.replaceAll("###error_run_num###", GProgress.dTestFail.toString());
				content = content.replaceAll("###error_pass_num###", GProgress.dTestFail.toString());
				content = content.replaceAll("###error_fail_num###", GSummary.strFailNum_Each[2]);
				if(GSummary.FailNum_Each[2] == 0) {
					content = content.replaceAll("###error_result###", PASS_CN);
					content = content.replaceAll("###error_color###", NORMAL);
				}else {
					content = content.replaceAll("###error_result###", FAIL_CN);
					content = content.replaceAll("###error_color###", FAIL_HIGH_RED);
				}
				
				//加载错误码场景用例统计数据并替换背景颜色
				content = content.replaceAll("###code_total_num###", GTestPlan.dTestUnReal.toString());
				content = content.replaceAll("###code_run_num###", GProgress.dTestUnReal.toString());
				content = content.replaceAll("###code_pass_num###", GProgress.dTestUnReal.toString());
				content = content.replaceAll("###code_fail_num###", GSummary.strFailNum_Each[3]);
				if(GSummary.FailNum_Each[3] == 0) {
					content = content.replaceAll("###code_result###", PASS_CN);
					content = content.replaceAll("###code_color###", NORMAL);
				}else {
					content = content.replaceAll("###code_result###", FAIL_CN);
					content = content.replaceAll("###code_color###", FAIL_HIGH_RED);
				}
				
				//加载中断场景用例统计数据并替换背景颜色
				content = content.replaceAll("###interrupt_total_num###", GTestPlan.dTestUnDo.toString());
				content = content.replaceAll("###interrupt_run_num###", GProgress.dTestUnDo.toString());
				content = content.replaceAll("###interrupt_pass_num###", GProgress.dTestUnDo.toString());
				content = content.replaceAll("###interrupt_fail_num###", String.valueOf(GSummary.strFailNum_Each[4]));
				if(GSummary.FailNum_Each[4] == 0) {
					content = content.replaceAll("###interrupt_result###", PASS_CN);
					content = content.replaceAll("###interrupt_color###", NORMAL);
				}else {
					content = content.replaceAll("###interrupt_result###", FAIL_CN);
					content = content.replaceAll("###interrupt_color###", FAIL_HIGH_RED);
				}
				
				//加载饼图份额
				content = content.replaceAll("###green###", GSummary.strProportion_Total[1]);
				content = content.replaceAll("###red###", GSummary.strProportion_Total[2]);
				content = content.replaceAll("###blue###", GSummary.strProportion_Total[3]);
				content = content.replaceAll("###yellow###", GSummary.strProportion_Total[4]);
				
				//加载测试结果
				if(GSummary.FailNum_Each[0] == 0
						&& GSummary.FailNum_Each[1] == 0 
						&& GSummary.FailNum_Each[2] == 0 
						&& GSummary.FailNum_Each[3] == 0 
						&& GSummary.FailNum_Each[4] == 0){
					content = content.replaceAll("###summary###", PASS_CN);
					content = content.replaceAll("###summary_color###", SUCCESS_HIGH_GREEN);
				}else {
					content = content.replaceAll("###summary###", FAIL_CN);
					content = content.replaceAll("###summary_color###", FAIL_HIGH_RED);
				}
				
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
			GSys.logSys("HTML TEST REPORT OUTPUT FAILED");
		}
	}
	
//	public static void main(String[] agrs) {
//		OutPutHtml();
		
//		float a = 18.650001f;
//		int scale = 2;//设置位数   
//		BigDecimal bd = new BigDecimal((double)a);  
//		bd = bd.setScale(scale,BigDecimal.ROUND_DOWN);  
//		a = bd.floatValue();  
//   
//		System.out.print(a);
		
//	}
}
