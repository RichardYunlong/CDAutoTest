package Detail;

import Base.*;
import DT.GColor;
import DT.GLog;
import DT.GTaskSummary;
import Sys.GPath;
import Sys.GStatic;
import Test.GTestMission;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 测试详情
 */
public class GDetail {

    /**
     *  构造函数
     */
	public GDetail(){
		GClazz.thisADataUnitClass();
	}
	
	/**
	 *  报告数据
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private GTaskSummary DETAIL_DATA = new GTaskSummary();
	
	/**
	 *  文件全名
	 */
	private String REPORT_NAME = GPath.REPORT_PATH + GStatic.gSut.getSysName() + "_TESTREPORT_" + GTime.getCurrentTime(GTime.FORMAT_14) + ".html";
	public String getREPORT_NAME() {
		return REPORT_NAME;
	}
	public void setREPORT_NAME(String rEPORT_NAME) {
		REPORT_NAME = rEPORT_NAME;
	}

	/**
	 *  各类用例数实际执行数
	 *  {跳过数，成功数，失败数，警告数，中断}
	 */
	private int[] EACHNUM = new int[5];
	public int[] getEACHNUM() {
		return EACHNUM;
	}
	public void setEACHNUM(int[] eACHNUM) {
		EACHNUM = eACHNUM;
	}

	/**
	 *  各类用例数实际执行失败数占各类总数的比例
	 *  {跳过数，成功数，失败数，警告数，中断}
	 */
	private float[] EACHRANGE = new float[5];
	public float[] getEACHRANGE() {
		return EACHRANGE;
	}
	public void setEACHRANGE(float[] eACHRANGE) {
		EACHRANGE = eACHRANGE;
	}

	/**
	 *  各类用例数实际执行数及失败占比统计
	 *  数值
	 */
	public void calEach() {
		//跳过数=总数-“4大类”
		EACHNUM[0] = GTestMission.gTestProgress.getTotalNum() - GTestMission.gTestProgress.getdTestReal() - GTestMission.gTestProgress.getdTestFail() - GTestMission.gTestProgress.getdTestUnReal() - GTestMission.gTestProgress.getdTestUnDo();
		//跳过失败占比=跳过数/跳过数=1，由于程序无法记录“不执行的用例是否执行失败”，所以该值恒等于1
		EACHRANGE[0] = 1.00f;
		
		//成功数
		EACHNUM[1] = GTestMission.gTestProgress.getdTestReal();
		//计划成功实际失败占比
		EACHRANGE[1] = (GTestMission.gTestPlan.getTestReal().floatValue()
				- GTestMission.gTestProgress.getdTestReal().floatValue()) / GTestMission.gTestPlan.getTestReal().floatValue();
		
		//失败数
		EACHNUM[2] = GTestMission.gTestProgress.getdTestFail();
		//计划失败实际成功占比
		EACHRANGE[2] = (GTestMission.gTestPlan.getTestFail().floatValue()
				- GTestMission.gTestProgress.getdTestFail().floatValue()) / GTestMission.gTestPlan.getTestFail().floatValue();
		
		//失败数
		EACHNUM[3] = GTestMission.gTestProgress.getdTestUnReal();
		//计划失败实际成功占比
		EACHRANGE[3] = (GTestMission.gTestPlan.getTestUnReal().floatValue()
				- GTestMission.gTestProgress.getdTestUnReal().floatValue()) / GTestMission.gTestPlan.getTestUnReal().floatValue();
		
		//失败数
		EACHNUM[4] = GTestMission.gTestProgress.getdTestUnDo();
		//计划失败实际成功占比
		EACHRANGE[4] = (GTestMission.gTestPlan.getTestUnDo().floatValue()
				- GTestMission.gTestProgress.getdTestUnDo().floatValue()) / GTestMission.gTestPlan.getTestUnDo().floatValue();
	}
	
	/**
	 *  导出报告前检查或更新报告数据
	 */	
	private void checkAndUpdate() {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		calEach();
		
		DETAIL_DATA.setDATE(GTime.getCurrentTime(GTime.FORMAT_8_TEXT));
		DETAIL_DATA.setSTARTTIME(formatDate.format(GStatic.gTestMission.getStartSysTime()));
		DETAIL_DATA.setENDTIME(formatDate.format(GStatic.gTestMission.getEndSysTime()));
		DETAIL_DATA.setSPENDTIME(GStatic.gTestMission.getEndSysTime() - GStatic.gTestMission.getStartSysTime() + "MS");
		DETAIL_DATA.setTOTALNUM(String.valueOf(GTestMission.gTestProgress.getTotalNum()));
		DETAIL_DATA.setRUNNUM(String.valueOf(GTestMission.gTestProgress.getdTestReal() + GTestMission.gTestProgress.getdTestFail() + GTestMission.gTestProgress.getdTestUnReal() + GTestMission.gTestProgress.getdTestUnDo()));
		DETAIL_DATA.setJUMPNUM(String.valueOf(EACHNUM[0]));
		String[] serverUrl = GStatic.gTransfer.getgServerUrl().clone();
		DETAIL_DATA.setURL(serverUrl[0]);
		DETAIL_DATA.setNAME("");
		DETAIL_DATA.setSID(GStatic.gSut.getSysName());
		DETAIL_DATA.setVERSION(GStatic.gSut.getSectionName1() + "+" + GStatic.gSut.getSectionName2() + "+" + GStatic.gSut.getSectionName3());
	}
	
	/**
	 *  导出html版本测试详情测试报告
	 */	
	public void exportReport() {
		long startTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom(GMissionMsg.getStepStart("DETAIL HTML REPORT START"));
		GFile.writeStringToGuideBottom(GTime.getDate() + "WRITE DETAIL");
		
		//创建存放路径
		GFile.deleteFolder(REPORT_NAME);
		if(GFile.creatDir(GPath.REPORT_PATH)) {
			String[] logPath = GLog.getLogpath().clone();
			GFile.writeStringToBottom(logPath[8], GMsg.MSG_READY[0]);
		}
		
		//导出前检查并更新报告数据
		checkAndUpdate();
		
		//复制模板并开始写入报告文件
		if(GFile.copyFile(GPath.REPORT_TEMP, REPORT_NAME) 
		&& GFile.copyFile(GPath.HELPER_TEMP, GPath.REPORT_NAME_HELPER)) {
			File templateFile = new File(REPORT_NAME);
			String content = null;

			try{
				//发送邮件标记。加载测试结果 当【跳过数】、【失败数】、【警告数】、【中断数】均为0，且【执行总数】等于【成功数】时，测试结果为成功
				
				content = FileUtils.readFileToString(templateFile, "utf-8");
				
				//替换数据
				content = content.replaceAll("###sut_address###", DETAIL_DATA.getURL());
				content = content.replaceAll("###sut_name###", DETAIL_DATA.getSID());
				content = content.replaceAll("###sut_version###", DETAIL_DATA.getVERSION());
				content = content.replaceAll("###test_date###", DETAIL_DATA.getDATE());
				content = content.replaceAll("###start_time###", DETAIL_DATA.getSTARTTIME());
				content = content.replaceAll("###end_time###", DETAIL_DATA.getENDTIME());
				content = content.replaceAll("###spend_time###",  DETAIL_DATA.getSPENDTIME());
				content = content.replaceAll("###total_num###", DETAIL_DATA.getTOTALNUM());
				content = content.replaceAll("###jump_num###", DETAIL_DATA.getJUMPNUM());
				content = content.replaceAll("###run_num###", DETAIL_DATA.getRUNNUM());

				//加载正常场景用例统计数据并替换背景颜色
				content = content.replaceAll("###success_num###", GTestMission.gTestProgress.getdTestReal().toString());
				content = content.replaceAll("###failed_num###", GTestMission.gTestProgress.getdTestFail().toString());
				content = content.replaceAll("###warning_num###", GTestMission.gTestProgress.getdTestUnReal().toString());
				content = content.replaceAll("###interrupt_num###", GTestMission.gTestProgress.getdTestUnDo().toString());
				
				if(EACHNUM[0] == 0
					&& EACHNUM[2] == 0 
					&& EACHNUM[3] == 0 
					&& EACHNUM[4] == 0
					&& EACHNUM[1] == Integer.parseInt(DETAIL_DATA.getRUNNUM())){
					content = content.replaceAll("###conclusion###", "成功");
					content = content.replaceAll("###conclusion_color###", GColor.SUCCESS_HIGH_GREEN);
				}else {
					content = content.replaceAll("###conclusion###", "失败");
					content = content.replaceAll("###conclusion_color###", GColor.FAIL_HIGH_RED);
				}
				
				content = content.replaceAll("###dymic_table###", GStatic.geCharts.loadingDetailReport());
			} catch (IOException e) {
				GLog.logSysFunctionException("detail report", e);
			}
				
			GFile.outputStreamReport("detail report", content, templateFile);
		}else {
			GFile.writeStringToGuideBottom("EXPORT DETAIL HTML REPORT FAILED");
		}
		long endTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom("DETAIL HTML REPORT -SPEND:" + (endTime - startTime) + "MS");
		GFile.writeStringToGuideBottom(GMissionMsg.getStepEnd());
	}
}
