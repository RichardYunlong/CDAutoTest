package main.java.Detail;

import main.java.Base.*;
import main.java.DT.GLog;
import main.java.DUnit.GTestCaseCount;
import main.java.Sys.GPath;
import main.java.Sys.GStatic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 *  测试用例统计
 */
public class GTestCaseCounts {
	
    /**
     *  构造函数
     */
	public GTestCaseCounts(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  用例数统计
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private GTestCaseCount testCaseCounts = new GTestCaseCount();
	public void setTestCaseCounts(GTestCaseCount testCaseCount) { this.testCaseCounts.setTestCaseCount(testCaseCount.getTestCaseCount()); }
	public GTestCaseCount getTestCaseCounts() {
		return testCaseCounts;
	}

	/**
	 *  文件全名
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private String TESTCASECOUNT_NAME = GPath.REPORT_PATH + "testcasecourt.html";
	public String getTESTCASECOUNT_NAME() {
		return TESTCASECOUNT_NAME;
	}

	/**
	 *  导出报告
	 */	
	public void exportReport() {
		long startTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom(GMissionMsg.getStepStart("TESTCASECOUNT HTML REPORT START"));
		GFile.writeStringToGuideBottom(GTime.getDate() + "WRITE TESTCASECOUNT");
		
		//创建存放路径
		GFile.deleteFolder(TESTCASECOUNT_NAME);
		if(GFile.creatDir(GPath.REPORT_PATH)) {
			String[] logPath = GLog.getLogpath().clone();
			GFile.writeStringToBottom(logPath[8], GMsg.MSG_READY[0]);
		}
		
		//复制模板并开始写入报告文件
		if(GFile.copyFile(GPath.TESTCASECOUNT_TEMP, TESTCASECOUNT_NAME)) {
			File templateFile = new File(TESTCASECOUNT_NAME);
			String content = null;

			try{
				content = FileUtils.readFileToString(templateFile, "utf-8");
				
				content = content.replaceAll("###sut###", "当前版本：" + GStatic.gSut.getSectionName1() + "+" + GStatic.gSut.getSectionName2() + "+" + GStatic.gSut.getSectionName3() + "&ensp;");
				content = content.replaceAll("###task_case_total_num_new###", "用例总数：" + GStatic.gSubstitute.getSUBSTITUTE_DATA().getTOTALNUM() + "&ensp;");
				content = content.replaceAll("###testcasedemo###", "说明：下图中出现的任意颜色，色彩越深说明重要程度或成熟度越高。图例如下" + "&ensp;");
				content = content.replaceAll("###testcasecount###", GStatic.geCharts.loadingTestCaseCountReport());
				content = content.replaceAll("###module_num_new###", "覆盖模块：" + this.getTestCaseCounts().getTestCaseCount().size() + "&ensp;");
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
		}else {
			GFile.writeStringToGuideBottom("EXPORT TESTCASECOUNT HTML REPORT FAILED");
		}
		
		//分割线和结束提示
		long endTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom("TESTCASECOUNT HTML REPORT -SPEND:" + (endTime - startTime) + "MS");
		GFile.writeStringToGuideBottom(GMissionMsg.getStepEnd());
	}
}
