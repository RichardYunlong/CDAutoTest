package main.java.Coverage;

import main.java.Base.*;
import main.java.DT.GLog;
import main.java.Sys.GPath;
import main.java.Sys.GStatic;
import main.java.Sys.GSys;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * 覆盖率模型
 */
public class GCoverageReport {
	
    /**
     *  构造函数
     */
	private GCoverageReport(){
		GClazz.thisAToolClass();
	}
	
	/**
	 * 累计订单类用例数量
	 */
	private static final Integer BILL_TYPE_NUM = 301;
	
	/**
	 * 累计算法类用例数量
	 */
	private static Integer ALGO_TYPE_NUM = 1187;
	
	/**
	 * 累计流程类用例数量
	 */
	private static final Integer FLOW_TYPE_NUM = 1396;

	public static Integer getALGO_TYPE_NUM() {
		return ALGO_TYPE_NUM;
	}

	public static void setALGO_TYPE_NUM(Integer aLGO_TYPE_NUM) {
		ALGO_TYPE_NUM = aLGO_TYPE_NUM;
	}

	/**
	 * 订单类用例总数
	 */
	private static Integer BILL_TYPE_TOTAL;

	/**
	 * 流程类用例总数
	 */
	private static Integer FLOW_TYPE_TOTAL;
	
	/**
	 * 算法类用例总数
	 */
	private static Integer ALGO_TYPE_TOTAL;
	
	/**
	 * 报告地址
	 */
	private static String REPORT_URL;
	
	public static Integer getBILL_TYPE_TOTAL() {
		return BILL_TYPE_TOTAL;
	}

	public static void setBILL_TYPE_TOTAL(Integer bILL_TYPE_TOTAL) {
		BILL_TYPE_TOTAL = bILL_TYPE_TOTAL;
	}

	public static Integer getFLOW_TYPE_TOTAL() {
		return FLOW_TYPE_TOTAL;
	}

	public static void setFLOW_TYPE_TOTAL(Integer fLOW_TYPE_TOTAL) {
		FLOW_TYPE_TOTAL = fLOW_TYPE_TOTAL;
	}

	public static Integer getALGO_TYPE_TOTAL() {
		return ALGO_TYPE_TOTAL;
	}

	public static void setALGO_TYPE_TOTAL(Integer aLGO_TYPE_TOTAL) {
		ALGO_TYPE_TOTAL = aLGO_TYPE_TOTAL;
	}
	
	public static String getREPORT_URL() {
		return REPORT_URL;
	}

	public static void setREPORT_URL(String rEPORT_URL) {
		REPORT_URL = rEPORT_URL;
	}

	/**
	 * 近半年内
	 * [月序号,每月用例总数(个),每月用例自动执行时间(min)]
	 */
	private static final Integer[][] LAST_HALF_YEAR_NUM = {
			{2, 95, 90},
			{3, 105, 95},
			{4, 113, 113},
			{5, 121, 115},
			{6, 129, 133},
			{7, 168, 179}};
    
	/**
	 *  加载参数
	 */	
	public static void loadConfig() {
		if((BILL_TYPE_TOTAL != null) 
		&& (FLOW_TYPE_TOTAL != null) 
		&& (ALGO_TYPE_TOTAL != null)
		&& (!BILL_TYPE_TOTAL.toString().isEmpty())
		&& (!FLOW_TYPE_TOTAL.toString().isEmpty())
		&& (!ALGO_TYPE_TOTAL.toString().isEmpty())){
			
			int curMonth = GTime.getCurrentTime14Split(1);
			LAST_HALF_YEAR_NUM[5][0] = curMonth;
			
			for(int i = 1;i <= 12;i++) {
				if((curMonth - i) > 0) {
					LAST_HALF_YEAR_NUM[5 - i][0] = curMonth - i;
				}else{ 
					LAST_HALF_YEAR_NUM[5 - i][0] = 12 - i + 1;
				}
			}

		}else {
			GFile.writeStringErrorToGuideBottom("One of these coverage params from coverageConfig may has no value, Please check again!");
			System.exit(0);
		}
	}
	
	/**
	 *  导出html版本覆盖率测试报告
	 */	
	public static void exportReport() {
		long startTime = System.currentTimeMillis();
		GFile.writeStringToBottom(GSys.GUIDE,GMissionMsg.getStepStart("CAVERAGE HTML REPORT START"));
		GFile.writeStringToBottom(GSys.GUIDE,GTime.getDate() + "WRITE CAVERAGE");
		
		//执行历史删除，无论结果如何
		GFile.deleteFile(GPath.REPORT_CAVERAGE);
		//创建新待写入文件路径
		if(GFile.creatDir(GPath.REPORT_PATH)) {
			String[] logPath = GLog.getLogpath().clone();
			GFile.writeStringToBottom(logPath[8], GMsg.MSG_READY[0]);
		}
		
		//复制模板资源至导出区，创建并写入报告文件
		if(GFile.copyFile(GPath.REPORT_CAVERAGE_TEMP, GPath.REPORT_CAVERAGE)) {
			File templateFile = new File(GPath.REPORT_CAVERAGE);
			
			String content = null;
			
			try {
				content = FileUtils.readFileToString(templateFile, "utf-8");
				content = content.replaceAll("###fullName###", GStatic.gSut.getSysName());
				
				content = content.replaceAll("###ALGO_TYPE_NUM###", ALGO_TYPE_NUM.toString());
				content = content.replaceAll("###BILL_TYPE_NUM###", BILL_TYPE_NUM.toString());
				content = content.replaceAll("###FLOW_TYPE_NUM###", FLOW_TYPE_NUM.toString());
				
				content = content.replaceAll("###ALGO_TYPE_TOTAL###", ALGO_TYPE_TOTAL.toString());
				content = content.replaceAll("###BILL_TYPE_TOTAL###", BILL_TYPE_TOTAL.toString());
				content = content.replaceAll("###FLOW_TYPE_TOTAL###", FLOW_TYPE_TOTAL.toString());
				
				for(int i = 0;i < 6;i++) {
					String monIndex = "###mon" + i + "###";
					content = content.replaceAll(monIndex, LAST_HALF_YEAR_NUM[i][0].toString());
					String numIndex = "###num" + i + "###";
					content = content.replaceAll(numIndex, LAST_HALF_YEAR_NUM[i][1].toString());
					String timeIndex = "###time" + i + "###";
					content = content.replaceAll(timeIndex, LAST_HALF_YEAR_NUM[i][2].toString());
				}
				
				double up;
				double down;
				
				DecimalFormat dR = new DecimalFormat("#.00");
				
				up = BILL_TYPE_NUM.doubleValue();
				down = BILL_TYPE_TOTAL.doubleValue();
				String strBillRage = dR.format(up/down* 100.0d);
				content = content.replaceAll("###BILL_COVERAGE###", strBillRage);
				
				up = FLOW_TYPE_NUM.doubleValue();
				down = FLOW_TYPE_TOTAL.doubleValue();
				String strFlowRage = dR.format(up/down* 100.0d);
				content = content.replaceAll("###FLOW_COVERAGE###", strFlowRage);
				
				up = ALGO_TYPE_NUM.doubleValue();
				down = ALGO_TYPE_TOTAL.doubleValue();
				String strAlgoRage = dR.format(up/down* 100.0d);
				content = content.replaceAll("###ALGO_COVERAGE###", strAlgoRage);
			} catch (IOException e) {
				GLog.logSysFunctionException("coverage report", e);
			}

			GFile.outputStreamReport("coverage report", content, templateFile);
		}else {
			GFile.writeStringToBottom(GSys.GUIDE,"EXPORT CAVERAGE HTML REPORT FAILED");
		}
		
		long endTime = System.currentTimeMillis();
		GFile.writeStringToBottom(GSys.GUIDE,"CAVERAGE HTML REPORT -SPEND:" + (endTime - startTime) + "MS");
		GFile.writeStringToBottom(GSys.GUIDE,GMissionMsg.getStepEnd());
	}
}
