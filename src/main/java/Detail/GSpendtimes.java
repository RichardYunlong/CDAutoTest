package main.java.Detail;

import main.java.DT.GLog;
import main.java.Sys.GPath;
import main.java.Sys.GStatic;
import main.java.Base.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 耗时详情
 */
public class GSpendtimes {

    /**
     *  构造函数
     */
	public GSpendtimes(){
		GClazz.thisAToolClass();
	}

	/**
	 * 报告地址
	 */
	private String REPORT_URL;
	public String getREPORT_URL() {
		return REPORT_URL;
	}
	public void setREPORT_URL(String rEPORT_URL) {
		REPORT_URL = rEPORT_URL;
	}
	
	/**
	 *  文件全名
	 */
	@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    private String REPORT_NAME = GPath.REPORT_PATH + "spendtime.html";
	public String getREPORT_NAME() {
		return REPORT_NAME;
	}

	/**
	 *  导出html版本测试详情测试报告
	 */	
	public void exportReport() {
		long startTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom(GMissionMsg.getStepStart("SPENDTIME HTML REPORT START"));
		GFile.writeStringToGuideBottom(GTime.getDate() + "WRITE SPENDTIME");
		
		//创建存放路径
		GFile.deleteFolder(REPORT_NAME);
		if(GFile.creatDir(GPath.REPORT_PATH)) {
			String[] logPath = GLog.getLogpath().clone();
			GFile.writeStringToBottom(logPath[8], GMsg.MSG_READY[0]);
		}
		
		//复制模板并开始写入报告文件
		if(GFile.copyFile(GPath.SPENDTIMES_TEMP, REPORT_NAME)) {
			File templateFile = new File(REPORT_NAME);
			String content = null;

			try{
				content = FileUtils.readFileToString(templateFile, "utf-8");
				content = content.replaceAll("###sut###", GStatic.gSut.getSectionName1() + "+" + GStatic.gSut.getSectionName2() + "+" + GStatic.gSut.getSectionName3());
				
			} catch (IOException e) {
				GLog.logSysFunctionException("spendtime report", e);
			}
				
			GFile.outputStreamReport("spendtime report", content, templateFile);
		}else {
			GFile.writeStringToGuideBottom("EXPORT SPENDTIME HTML REPORT FAILED");
		}
		long endTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom("SPENDTIME HTML REPORT -SPEND:" + (endTime - startTime) + "MS");
		GFile.writeStringToGuideBottom(GMissionMsg.getStepEnd());
	}
}
