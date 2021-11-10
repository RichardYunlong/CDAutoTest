package Detail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import Base.GClazz;
import Base.GFile;
import Base.GMissionMsg;
import Base.GMsg;
import Base.GTime;
import DT.GLog;
import Sut.GSut;
import Sys.GPath;
import Sys.GSys;

/**
 * 测试详情
 */
public class GSpendtimes {

    /**
     *  构造函数
     */
	private GSpendtimes(){
		GClazz.thisAToolClass();
	}

	/**
	 * 报告地址
	 */
	private static String REPORT_URL;

	public static String getREPORT_URL() {
		return REPORT_URL;
	}

	public static void setREPORT_URL(String rEPORT_URL) {
		REPORT_URL = rEPORT_URL;
	}
	
	/**
	 *  文件全名
	 */
	private static String REPORT_NAME = GPath.REPORT_PATH + "spendtime.html";
	
	public static String getREPORT_NAME() {
		return REPORT_NAME;
	}

	/**
	 *  导出html版本测试详情测试报告
	 */	
	public static void exportReport() {
		long startTime = System.currentTimeMillis();
		GSys.logSys(GMissionMsg.getStepStart("SPENDTIME HTML REPORT START"));
		GSys.logSys(GTime.getDate() + "WRITE SPENDTIME");
		
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
				content = content.replaceAll("###sut###", GSut.getSectionName1() + "+" + GSut.getSectionName2() + "+" + GSut.getSectionName3());
				
			} catch (IOException e) {
				GLog.logSysFunctionException("spendtime report", e);
			}
				
			GFile.outputStreamReport("spendtime report", content, templateFile);
		}else {
			GSys.logSys("EXPORT SPENDTIME HTML REPORT FAILED");
		}
		long endTime = System.currentTimeMillis();
		GSys.logSys("SPENDTIME HTML REPORT -SPEND:" + (endTime - startTime) + "MS");
		GSys.logSys(GMissionMsg.getStepEnd());
	}
}
