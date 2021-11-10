package Quality;

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
 * 质量模型
 */
public class GQualityReport {
	
    /**
     *  构造函数
     */
	private GQualityReport(){
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
	 *  加载参数
	 */	
	public static void loadConfig() {
		GQFactor.set();
		
		if((GQualityAlgo.getFUNCTIONALITY_FLEXIBILITY() != null ) 
		&& (GQualityAlgo.getFUNCTIONALITY_ACCURACY() != null ) 
		&& (GQualityAlgo.getFUNCTIONALITY_INTERACTIVITY() != null )
		&& (GQualityAlgo.getFUNCTIONALITY_SECURITY() != null ) 
		&& (GQualityAlgo.getFUNCTIONALITY_REGULATORY() != null )
		&& (GQualityAlgo.getRELIABILITY_MATURITY() != null ) 
		&& (GQualityAlgo.getRELIABILITY_FAULTTOLERANCE() != null ) 
		&& (GQualityAlgo.getRELIABILITY_RECOVERABILITY() != null )
		&& (GQualityAlgo.getRELIABILITY_REGULATORY() != null )
				
		&& (GQualityAlgo.getUSABILITY_COMPREHENSIBILITY() != null )
		&& (GQualityAlgo.getUSABILITY_EASYTOLEARN() != null ) 
		&& (GQualityAlgo.getUSABILITY_OPERABILITY() != null ) 
		&& (GQualityAlgo.getUSABILITY_STICKINESS() != null )
		&& (GQualityAlgo.getUSABILITY_REGULATORY() != null )
				
		&& (GQualityAlgo.getEFFICIENCY_TIMING() != null ) 
		&& (GQualityAlgo.getEFFICIENCY_UTILIZATION() != null )
		&& (GQualityAlgo.getEFFICIENCY_REGULATORY() != null )
				
		&& (GQualityAlgo.getMAINTAINABILITY_ANALYZABILITY() != null )
		&& (GQualityAlgo.getMAINTAINABILITY_STABILITY() != null ) 
		&& (GQualityAlgo.getMAINTAINABILITY_CHANGEABILITY() != null ) 
		&& (GQualityAlgo.getMAINTAINABILITY_TESTABILITY() != null )
		&& (GQualityAlgo.getMAINTAINABILITY_REGULATORY() != null )
				
		&& (GQualityAlgo.getPORTABILITY_ADAPTABILITY() != null )
		&& (GQualityAlgo.getPORTABILITY_INSTALLATION() != null ) 
		&& (GQualityAlgo.getPORTABILITY_COEXISTENCE() != null ) 
		&& (GQualityAlgo.getPORTABILITY_REPLACEABILITY() != null )
		&& (GQualityAlgo.getPORTABILITY_REGULATORY() != null )){
			;
		}else {
			GSys.logErrorSys("One of these quality params from qualityConfig may has no value, Please check again!");
			System.exit(0);
		}
	}
	
	/**
	 *  导出html版本覆盖率测试报告
	 */	
	public static void exportReport() {
		long startTime = System.currentTimeMillis();
		GSys.logSys(GMissionMsg.getStepStart("QUALITY HTML REPORT START"));
		GSys.logSys(GTime.getDate() + "WRITE QUALITY");
		
		//执行历史删除，无论结果如何
		GFile.deleteFile(GPath.REPORT_QUALITY);
		//创建新的待写入文件路径
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
				content = content.replaceAll("###fullName###", GSut.getSysName());
				
				GQualityAlgo.calculate();
				
				content = content.replaceAll("###FUNCTIONALITY_FLEXIBILITY###", GQualityAlgo.getFUNCTIONALITY_FLEXIBILITY().toString());
				content = content.replaceAll("###FUNCTIONALITY_ACCURACY###", GQualityAlgo.getFUNCTIONALITY_ACCURACY().toString());
				content = content.replaceAll("###FUNCTIONALITY_INTERACTIVITY###", GQualityAlgo.getFUNCTIONALITY_INTERACTIVITY().toString());
				content = content.replaceAll("###FUNCTIONALITY_SECURITY###", GQualityAlgo.getFUNCTIONALITY_SECURITY().toString());
				content = content.replaceAll("###FUNCTIONALITY_REGULATORY###", GQualityAlgo.getFUNCTIONALITY_REGULATORY().toString());
				content = content.replaceAll("###FUNCTIONALITY###", GQualityAlgo.getFUNCTIONALITY().toString());
				
				content = content.replaceAll("###RELIABILITY_MATURITY###", GQualityAlgo.getRELIABILITY_MATURITY().toString());
				content = content.replaceAll("###RELIABILITY_FAULTTOLERANCE###", GQualityAlgo.getRELIABILITY_FAULTTOLERANCE().toString());
				content = content.replaceAll("###RELIABILITY_RECOVERABILITY###", GQualityAlgo.getRELIABILITY_RECOVERABILITY().toString());
				content = content.replaceAll("###RELIABILITY_REGULATORY###", GQualityAlgo.getRELIABILITY_REGULATORY().toString());
				content = content.replaceAll("###RELIABILITY###",GQualityAlgo.getRELIABILITY().toString());
				
				content = content.replaceAll("###USABILITY_COMPREHENSIBILITY###", GQualityAlgo.getUSABILITY_COMPREHENSIBILITY().toString());
				content = content.replaceAll("###USABILITY_EASYTOLEARN###", GQualityAlgo.getUSABILITY_EASYTOLEARN().toString());
				content = content.replaceAll("###USABILITY_OPERABILITY###", GQualityAlgo.getUSABILITY_OPERABILITY().toString());
				content = content.replaceAll("###USABILITY_STICKINESS###", GQualityAlgo.getUSABILITY_STICKINESS().toString());
				content = content.replaceAll("###USABILITY_REGULATORY###", GQualityAlgo.getUSABILITY_REGULATORY().toString());
				content = content.replaceAll("###USABILITY###", GQualityAlgo.getUSABILITY().toString());
				
				content = content.replaceAll("###EFFICIENCY_TIMING###", GQualityAlgo.getEFFICIENCY_TIMING().toString());
				content = content.replaceAll("###EFFICIENCY_UTILIZATION###", GQualityAlgo.getEFFICIENCY_UTILIZATION().toString());
				content = content.replaceAll("###EFFICIENCY_REGULATORY###", GQualityAlgo.getEFFICIENCY_REGULATORY().toString());
				content = content.replaceAll("###EFFICIENCY###", GQualityAlgo.getEFFICIENCY().toString());
				
				content = content.replaceAll("###MAINTAINABILITY_ANALYZABILITY###", GQualityAlgo.getMAINTAINABILITY_ANALYZABILITY().toString());
				content = content.replaceAll("###MAINTAINABILITY_STABILITY###", GQualityAlgo.getMAINTAINABILITY_STABILITY().toString());
				content = content.replaceAll("###MAINTAINABILITY_CHANGEABILITY###", GQualityAlgo.getMAINTAINABILITY_CHANGEABILITY().toString());
				content = content.replaceAll("###MAINTAINABILITY_TESTABILITY###", GQualityAlgo.getMAINTAINABILITY_TESTABILITY().toString());
				content = content.replaceAll("###MAINTAINABILITY_REGULATORY###", GQualityAlgo.getMAINTAINABILITY_REGULATORY().toString());
				content = content.replaceAll("###MAINTAINABILITY###", GQualityAlgo.getMAINTAINABILITY().toString());
				
				content = content.replaceAll("###PORTABILITY_ADAPTABILITY###", GQualityAlgo.getPORTABILITY_ADAPTABILITY().toString());
				content = content.replaceAll("###PORTABILITY_INSTALLATION###", GQualityAlgo.getPORTABILITY_INSTALLATION().toString());
				content = content.replaceAll("###PORTABILITY_COEXISTENCE###", GQualityAlgo.getPORTABILITY_COEXISTENCE().toString());
				content = content.replaceAll("###PORTABILITY_REPLACEABILITY###", GQualityAlgo.getPORTABILITY_REPLACEABILITY().toString());
				content = content.replaceAll("###PORTABILITY_REGULATORY###", GQualityAlgo.getPORTABILITY_REGULATORY().toString());
				content = content.replaceAll("###PORTABILITY###", GQualityAlgo.getFUNCTIONALITY().toString());
			} catch (IOException e) {
				GLog.logSysFunctionException("quality report", e);
			}
			
			GFile.outputStreamReport("quality report", content, templateFile);
		}else {
			GSys.logSys("EXPORT QUALITY HTML REPORT FAILED");
		}
		
		long endTime = System.currentTimeMillis();
		GSys.logSys("QUALITY HTML REPORT -SPEND:" + (endTime - startTime) + "MS");
		GSys.logSys(GMissionMsg.getStepEnd());
	}
}
