package main.java.Quality;

import main.java.Sys.GPath;
import main.java.Sys.GStatic;
import main.java.Sys.GSys;
import main.java.Base.*;
import main.java.DT.GLog;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 质量模型
 */
public class GQualityReport {
	
    /**
     *  构造函数
     */
	public GQualityReport(){
		GClazz.thisADataUnitClass();
	}
	
	/**
	 * 报告地址
	 */
	private String REPORT_URL;
	public String getREPORT_URL() {
		return this.REPORT_URL;
	}
	public void setREPORT_URL(String rEPORT_URL) {
		this.REPORT_URL = rEPORT_URL;
	}

	/**
	 *  加载参数
	 */	
	public void loadConfig() {
		GStatic.gqFactor.set();
		
		if((GStatic.gQualityAlgo.getFUNCTIONALITY_FLEXIBILITY() != null )
		&& (GStatic.gQualityAlgo.getFUNCTIONALITY_ACCURACY() != null )
		&& (GStatic.gQualityAlgo.getFUNCTIONALITY_INTERACTIVITY() != null )
		&& (GStatic.gQualityAlgo.getFUNCTIONALITY_SECURITY() != null )
		&& (GStatic.gQualityAlgo.getFUNCTIONALITY_REGULATORY() != null )
		&& (GStatic.gQualityAlgo.getRELIABILITY_MATURITY() != null )
		&& (GStatic.gQualityAlgo.getRELIABILITY_FAULTTOLERANCE() != null )
		&& (GStatic.gQualityAlgo.getRELIABILITY_RECOVERABILITY() != null )
		&& (GStatic.gQualityAlgo.getRELIABILITY_REGULATORY() != null )
				
		&& (GStatic.gQualityAlgo.getUSABILITY_COMPREHENSIBILITY() != null )
		&& (GStatic.gQualityAlgo.getUSABILITY_EASYTOLEARN() != null )
		&& (GStatic.gQualityAlgo.getUSABILITY_OPERABILITY() != null )
		&& (GStatic.gQualityAlgo.getUSABILITY_STICKINESS() != null )
		&& (GStatic.gQualityAlgo.getUSABILITY_REGULATORY() != null )
				
		&& (GStatic.gQualityAlgo.getEFFICIENCY_TIMING() != null )
		&& (GStatic.gQualityAlgo.getEFFICIENCY_UTILIZATION() != null )
		&& (GStatic.gQualityAlgo.getEFFICIENCY_REGULATORY() != null )
				
		&& (GStatic.gQualityAlgo.getMAINTAINABILITY_ANALYZABILITY() != null )
		&& (GStatic.gQualityAlgo.getMAINTAINABILITY_STABILITY() != null )
		&& (GStatic.gQualityAlgo.getMAINTAINABILITY_CHANGEABILITY() != null )
		&& (GStatic.gQualityAlgo.getMAINTAINABILITY_TESTABILITY() != null )
		&& (GStatic.gQualityAlgo.getMAINTAINABILITY_REGULATORY() != null )
				
		&& (GStatic.gQualityAlgo.getPORTABILITY_ADAPTABILITY() != null )
		&& (GStatic.gQualityAlgo.getPORTABILITY_INSTALLATION() != null )
		&& (GStatic.gQualityAlgo.getPORTABILITY_COEXISTENCE() != null )
		&& (GStatic.gQualityAlgo.getPORTABILITY_REPLACEABILITY() != null )
		&& (GStatic.gQualityAlgo.getPORTABILITY_REGULATORY() != null )){
			GFile.writeStringToGuideBottom("GQualityReport Params Loaded");
		}else {
			GFile.writeStringToBottom(GSys.GUIDE ,"ERROR----" + "One of these quality params from qualityConfig may has no value, Please check again!");
			System.exit(0);
		}
	}
	
	/**
	 *  导出html版本覆盖率测试报告
	 */	
	public void exportReport() {
		long startTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom(GMissionMsg.getStepStart("QUALITY HTML REPORT START"));
		GFile.writeStringToGuideBottom(GTime.getDate() + "WRITE QUALITY");
		
		//执行历史删除，无论结果如何
		GFile.deleteFile(GPath.REPORT_QUALITY);
		//创建新待写入文件路径
		if(GFile.creatDir(GPath.REPORT_PATH)) {
			String[] logPath = GLog.getLogpath().clone();
			GFile.writeStringToBottom(logPath[8], GMsg.MSG_READY[0]);
		}
		
		//复制模板资源至导出区，创建并写入报告文件
		if(GFile.copyFile(GPath.REPORT_QUALITY_TEMP, GPath.REPORT_QUALITY)) {
			File templateFile = new File(GPath.REPORT_QUALITY);
			String content = null;
			
			try{
				content = FileUtils.readFileToString(templateFile, "utf-8");
				content = content.replaceAll("###fullName###", GStatic.gSut.getSysName());
				
				GStatic.gQualityAlgo.calculate();
				
				content = content.replaceAll("###FUNCTIONALITY_FLEXIBILITY###", GStatic.gQualityAlgo.getFUNCTIONALITY_FLEXIBILITY().toString());
				content = content.replaceAll("###FUNCTIONALITY_ACCURACY###", GStatic.gQualityAlgo.getFUNCTIONALITY_ACCURACY().toString());
				content = content.replaceAll("###FUNCTIONALITY_INTERACTIVITY###", GStatic.gQualityAlgo.getFUNCTIONALITY_INTERACTIVITY().toString());
				content = content.replaceAll("###FUNCTIONALITY_SECURITY###", GStatic.gQualityAlgo.getFUNCTIONALITY_SECURITY().toString());
				content = content.replaceAll("###FUNCTIONALITY_REGULATORY###", GStatic.gQualityAlgo.getFUNCTIONALITY_REGULATORY().toString());
				content = content.replaceAll("###FUNCTIONALITY###", GStatic.gQualityAlgo.getFUNCTIONALITY().toString());
				
				content = content.replaceAll("###RELIABILITY_MATURITY###", GStatic.gQualityAlgo.getRELIABILITY_MATURITY().toString());
				content = content.replaceAll("###RELIABILITY_FAULTTOLERANCE###", GStatic.gQualityAlgo.getRELIABILITY_FAULTTOLERANCE().toString());
				content = content.replaceAll("###RELIABILITY_RECOVERABILITY###", GStatic.gQualityAlgo.getRELIABILITY_RECOVERABILITY().toString());
				content = content.replaceAll("###RELIABILITY_REGULATORY###", GStatic.gQualityAlgo.getRELIABILITY_REGULATORY().toString());
				content = content.replaceAll("###RELIABILITY###",GStatic.gQualityAlgo.getRELIABILITY().toString());
				
				content = content.replaceAll("###USABILITY_COMPREHENSIBILITY###", GStatic.gQualityAlgo.getUSABILITY_COMPREHENSIBILITY().toString());
				content = content.replaceAll("###USABILITY_EASYTOLEARN###", GStatic.gQualityAlgo.getUSABILITY_EASYTOLEARN().toString());
				content = content.replaceAll("###USABILITY_OPERABILITY###", GStatic.gQualityAlgo.getUSABILITY_OPERABILITY().toString());
				content = content.replaceAll("###USABILITY_STICKINESS###", GStatic.gQualityAlgo.getUSABILITY_STICKINESS().toString());
				content = content.replaceAll("###USABILITY_REGULATORY###", GStatic.gQualityAlgo.getUSABILITY_REGULATORY().toString());
				content = content.replaceAll("###USABILITY###", GStatic.gQualityAlgo.getUSABILITY().toString());
				
				content = content.replaceAll("###EFFICIENCY_TIMING###", GStatic.gQualityAlgo.getEFFICIENCY_TIMING().toString());
				content = content.replaceAll("###EFFICIENCY_UTILIZATION###", GStatic.gQualityAlgo.getEFFICIENCY_UTILIZATION().toString());
				content = content.replaceAll("###EFFICIENCY_REGULATORY###", GStatic.gQualityAlgo.getEFFICIENCY_REGULATORY().toString());
				content = content.replaceAll("###EFFICIENCY###", GStatic.gQualityAlgo.getEFFICIENCY().toString());
				
				content = content.replaceAll("###MAINTAINABILITY_ANALYZABILITY###", GStatic.gQualityAlgo.getMAINTAINABILITY_ANALYZABILITY().toString());
				content = content.replaceAll("###MAINTAINABILITY_STABILITY###", GStatic.gQualityAlgo.getMAINTAINABILITY_STABILITY().toString());
				content = content.replaceAll("###MAINTAINABILITY_CHANGEABILITY###", GStatic.gQualityAlgo.getMAINTAINABILITY_CHANGEABILITY().toString());
				content = content.replaceAll("###MAINTAINABILITY_TESTABILITY###", GStatic.gQualityAlgo.getMAINTAINABILITY_TESTABILITY().toString());
				content = content.replaceAll("###MAINTAINABILITY_REGULATORY###", GStatic.gQualityAlgo.getMAINTAINABILITY_REGULATORY().toString());
				content = content.replaceAll("###MAINTAINABILITY###", GStatic.gQualityAlgo.getMAINTAINABILITY().toString());
				
				content = content.replaceAll("###PORTABILITY_ADAPTABILITY###", GStatic.gQualityAlgo.getPORTABILITY_ADAPTABILITY().toString());
				content = content.replaceAll("###PORTABILITY_INSTALLATION###", GStatic.gQualityAlgo.getPORTABILITY_INSTALLATION().toString());
				content = content.replaceAll("###PORTABILITY_COEXISTENCE###", GStatic.gQualityAlgo.getPORTABILITY_COEXISTENCE().toString());
				content = content.replaceAll("###PORTABILITY_REPLACEABILITY###", GStatic.gQualityAlgo.getPORTABILITY_REPLACEABILITY().toString());
				content = content.replaceAll("###PORTABILITY_REGULATORY###", GStatic.gQualityAlgo.getPORTABILITY_REGULATORY().toString());
				content = content.replaceAll("###PORTABILITY###", GStatic.gQualityAlgo.getFUNCTIONALITY().toString());
			} catch (IOException e) {
				GLog.logSysFunctionException("quality report", e);
			}
			
			GFile.outputStreamReport("quality report", content, templateFile);
		}else {
			GFile.writeStringToGuideBottom("EXPORT QUALITY HTML REPORT FAILED");
		}
		
		long endTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom("QUALITY HTML REPORT -SPEND:" + (endTime - startTime) + "MS");
		GFile.writeStringToGuideBottom(GMissionMsg.getStepEnd());
	}
}
