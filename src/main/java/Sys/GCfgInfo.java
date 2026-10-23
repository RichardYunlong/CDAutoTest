package Sys;

import Base.GFile;
import Base.GMissionMsg;
import DT.GLog;
import DUnit.GUnit;

/**
 *  配置文件信息处理
 */
public class GCfgInfo {
	
	/**
	 * 	日志路径参数
	 */
	private final String[] logPath = GLog.getLogpath().clone();
	
	/**
	 * 	系统参数
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private GUnit sysCfg = new GUnit();
	
	/**
	 * 	数据库参数
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private GUnit dbCfg = new GUnit();
	
	/**
	 * 	邮件服务器参数
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private GUnit smtpCfg = new GUnit();
	
	/**
	 * 	邮件发送服务参数
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private GUnit mailCfg = new GUnit();
	
	/**
	 * 	被测件参数
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private GUnit sutCfg = new GUnit();

	/**
	 * 	被测件参数
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private GUnit webdriverCfg = new GUnit();
	
	/**
	 * 	覆盖率报告参数
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private GUnit coverageCfg = new GUnit();
	
	/**
	 * 	质量报告参数
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private GUnit qualityCfg = new GUnit();
	
	/**
	 * 	按分区输出配置信息到质量报告日志
	 *
	 *  <p>把 {@link GConfig} 中对应分区的审计属性写入选定的报告文件，
	 *  敏感项已由配置中心掩码。</p>
	 *
	 *  @param cfgType 分区标识，取值如 {@code SYSTEM}、{@code DB}、{@code SMTP}、
	 *                 {@code EMAIL}、{@code SUT}、{@code COVERAGE}、{@code QUALITY}、{@code WEBDRIVER}
	 */
    private void logSysByType(String cfgType) {
    	GFile.writeStringToBottom(logPath[8],GMissionMsg.getStepTop(cfgType));
    	switch(cfgType) {
	    	case "SYSTEM":{
	    		sysCfg.putUnitInfo("系统参数配置区\n", GConfig.current().getAttribute(GConfigKey.Section.SYSTEM));
	        	GFile.writeStringToBottom(logPath[8],sysCfg.getUnitInfoString().toString());
	    		break;
	    	}
	    	case "DB":{
	    		dbCfg.putUnitInfo("数据库参数配置区\n", GConfig.current().getAttribute(GConfigKey.Section.DB));
	        	GFile.writeStringToBottom(logPath[8],dbCfg.getUnitInfoString().toString());
	    		break;
	    	}
	    	case "SMTP":{
	    		smtpCfg.putUnitInfo("邮件服务器参数配置区\n", GConfig.current().getAttribute(GConfigKey.Section.SMTP));
	        	GFile.writeStringToBottom(logPath[8],smtpCfg.getUnitInfoString().toString());
	    		break;
	    	}
	    	case "MAIL":{
	    		mailCfg.putUnitInfo("邮件发送参数配置区\n", GConfig.current().getAttribute(GConfigKey.Section.MAIL));
	        	GFile.writeStringToBottom(logPath[8],mailCfg.getUnitInfoString().toString());
	    		break;
	    	}
			case "SUT":{
				sutCfg.putUnitInfo("被测件参数配置区\n", GConfig.current().getAttribute(GConfigKey.Section.SUT));
				GFile.writeStringToBottom(logPath[8],sutCfg.getUnitInfoString().toString());
				break;
			}
			case "WEBDRIVER":{
				webdriverCfg.putUnitInfo("浏览器驱动参数配置区\n", GConfig.current().getAttribute(GConfigKey.Section.WEBDRIVER));
				GFile.writeStringToBottom(logPath[8],webdriverCfg.getUnitInfoString().toString());
				break;
			}
	    	case "COVERAGE":{
	        	coverageCfg.putUnitInfo("覆盖率报告参数配置区\n", GConfig.current().getAttribute(GConfigKey.Section.COVERAGE));
	        	GFile.writeStringToBottom(logPath[8],coverageCfg.getUnitInfoString().toString());
	    		break;
	    	}
	    	case "QUALITY":{
	        	qualityCfg.putUnitInfo("质量报告参数配置区\n", GConfig.current().getAttribute(GConfigKey.Section.QUALITY));
	        	GFile.writeStringToBottom(logPath[8],qualityCfg.getUnitInfoString().toString());
	    		break;
	    	}
	    	default:{
	    		GFile.writeStringToBottom(logPath[8],cfgType);
	    		break;
	    	}
    	}
    	GFile.writeStringToBottom(logPath[8],GMissionMsg.getStepBottom("END"));
    }
    
	/**
	 * 	打印参数配置文件加载结果
	 */
    public void logAll() {
    	long startTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom(GMissionMsg.getStepTop("SYSTEM CONFIGURAION LOADING"));
		GFile.writeStringToGuideBottom(GStatic.gSys.getDate() + " READ CONFIGURAION");
    	logSysByType("SYSTEM");
    	logSysByType("DB");
    	logSysByType("SMTP");
    	logSysByType("MAIL");
    	logSysByType("SUT");
		logSysByType("WEBDRIVER");
    	logSysByType("COVERAGE");
    	logSysByType("QUALITY");
    	logSysByType("自定义");
		long endTime = System.currentTimeMillis();
		GFile.writeStringToGuideBottom("SYSTEM CONFIGURAION LOADING -SPEND:" + (endTime - startTime) + "MS");
		GFile.writeStringToGuideBottom(GMissionMsg.getStepBottom("SYSTEM CONFIGURAION COMPLETE"));
	}
}
