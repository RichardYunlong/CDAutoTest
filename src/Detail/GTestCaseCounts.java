package Detail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

import Base.GClazz;
import Base.GFile;
import Base.GMissionMsg;
import Base.GMsg;
import Base.GTime;
import DT.GECharts;
import DT.GLog;
import DUnit.GTestCaseCount;
import Mail.GMail;
import Sut.GSut;
import Sys.GPath;
import Sys.GSys;

public class GTestCaseCounts {
	
    /**
     *  构造函数
     */
	private GTestCaseCounts(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  用例数统计
	 */
	private static GTestCaseCount testCaseCounts = new GTestCaseCount();
	
	public static void setTestCaseCounts(GTestCaseCount testCaseCount) {
		GTestCaseCounts.testCaseCounts.setTestCaseCount(testCaseCount.getTestCaseCount());;
	}

	public static GTestCaseCount getTestCaseCounts() {
		return testCaseCounts;
	}

	/**
	 *  文件全名
	 */
	private static String TESTCASECOUNT_NAME = GPath.REPORT_PATH + "testcasecourt.html";
	
	public static String getTESTCASECOUNT_NAME() {
		return TESTCASECOUNT_NAME;
	}

	/**
	 *  导出报告
	 */	
	public static void exportReport() {
		long startTime = System.currentTimeMillis();
		GSys.logSys(GMissionMsg.getStepStart("TESTCASECOUNT HTML REPORT START"));
		GSys.logSys(GTime.getDate() + "WRITE TESTCASECOUNT");
		
		//创建存放路径
		GFile.deleteFolder(TESTCASECOUNT_NAME);
		if(GFile.creatDir(GPath.REPORT_PATH)) {
			String[] logPath = GLog.getLogpath().clone();
			GFile.writeStringToBottom(logPath[8], GMsg.MSG_READY[0]);
		}
		
		//邮件是否发送标记
		boolean bResOld = false;
		boolean bResNew = false;
		//复制模板并开始写入报告文件
		if(GFile.copyFile(GPath.TESTCASECOUNT_TEMP, TESTCASECOUNT_NAME)) {
			File templateFile = new File(TESTCASECOUNT_NAME);
			String content = null;

			try{
				content = FileUtils.readFileToString(templateFile, "utf-8");
				
				content = content.replaceAll("###sut###", "当前版本：" + GSut.getSectionName1() + "+" + GSut.getSectionName2() + "+" + GSut.getSectionName3() + "&ensp;");
				content = content.replaceAll("###task_case_total_num_new###", "用例总数：" + GSubstitute.getSUBSTITUTE_DATA().getTOTALNUM() + "&ensp;");
				content = content.replaceAll("###testcasedemo###", "下图中出现的任意颜色，色彩越深说明重要程度或成熟度越高。图例如下" + "&ensp;");
				content = content.replaceAll("###testcasecount###", GECharts.loadingTestCaseCountReport());
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
				GMail.sendEmail("[成功]", TESTCASECOUNT_NAME);
			}else {
				GMail.sendEmail("[失败]", TESTCASECOUNT_NAME);
			}
		}else {
			GSys.logSys("EXPORT TESTCASECOUNT HTML REPORT FAILED");
		}
		
		//分割线和结束提示
		long endTime = System.currentTimeMillis();
		GSys.logSys("TESTCASECOUNT HTML REPORT -SPEND:" + (endTime - startTime) + "MS");
		GSys.logSys(GMissionMsg.getStepEnd());
	}
}
