package Sys;

import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import Base.GMissionMsg;
import Coverage.GCoverageReport;
import Coverage.GCoverageConfig;
import DB.GDBConfig;
import DB.GDBConnector;
import DT.GLog;
import DT.GRemote;
import DT.GTransfer;
import Detail.GSpendtimes;
import Detail.GSubstitute;
import Mail.GEmailConfig;
import Mail.GMail;
import Mail.GSmtpConfig;
import Quality.GQualityAlgo;
import Quality.GQualityConfig;
import Quality.GQualityReport;
import Sut.GSut;
import Sut.GSutConfig;
import Test.GTestMission;
import Test.GTestPlan;

/**
 *  加载所有配置文件
 */
@Component
public class GLoadConfig {
	
	/**
	 *  系统配置项
	 */
    @Autowired
    private GSysConfig sysConfig;
    
    /**
     *  数据库配置项
     */
    @Autowired
    private GDBConfig dbConfig;
    
    /**
     *  SMTP配置项
     */
    @Autowired
    private GSmtpConfig smtpConfig;
    
    /**
     *  EMAIL配置项
     */
    @Autowired
    private GEmailConfig emailConfig;
    
    /**
     *  SUT配置项
     */
    @Autowired
    private GSutConfig sutConfig;
    
    /**
     *  Coverage配置项
     */
    @Autowired
    private GCoverageConfig coverageConfig;
    
    /**
     *  Quality配置项
     */
    @Autowired
    private GQualityConfig qualityConfig;
    
	/**
	 *  系统spring对象
	 */
	private static ApplicationContext appContext = null;
    
    /**
     *  读取配置文件中各项的值
     */
    @PostConstruct
    public void init() {
		GParam.setDriver(sysConfig.getDriver());
		GParam.setINPUT_TYPE(Integer.valueOf(sysConfig.getTestInputType()));
		GParam.setINPUT_FROM(Integer.valueOf(sysConfig.getTestInputSource()));
		GParam.setINPUT_ROW_FROM((Integer.valueOf(sysConfig.getTestInputBeginRowIndex())).intValue());
		GParam.setINPUT_COL_FROM((Integer.valueOf(sysConfig.getTestInputBeginColumnIndex())).intValue());
		if(sysConfig.getIsAutoCheckReport().equals("true")) {
			GParam.setbAutoCheckReport(true);
		}
		if(sysConfig.getDragonShow().equals("true")) {
			GParam.setDragonShow(true);
		}

		if(sysConfig.getIsCheckOnly().equals("false")) {
			GTestMission.setTM_CHECK_ONLY(false);
		}
		if(sysConfig.getLoopCourt() > 1) {
			GTestMission.setTM_LOOP_COURT(sysConfig.getLoopCourt());
		}
    	
		GTestPlan.setRecordInputParamListInTxt((Integer.valueOf(sysConfig.getIsLoggedInputs())).intValue());
		GTestPlan.setTimeWait((Integer.valueOf(sysConfig.getTimeWait())).intValue());
		GTestPlan.setTestCaseType(sysConfig.getTestCaseType());
		if(sysConfig.getIsBackup().equals("true")) {
			GTestPlan.setbIsBackup(true);
		}
        
        GTransfer.setServerConnType((Integer.valueOf(sysConfig.getServerConnType())).intValue());
        String[] serverUrl = GTransfer.getgServerUrl().clone();
        serverUrl[0] = sysConfig.getServerUrl();
        GTransfer.setgServerUrl(serverUrl);
        
        String[] serverWWW = GTransfer.getgServerWWW().clone();
        serverWWW[0] = sysConfig.getServerWWW();
        GTransfer.setgServerWWW(serverWWW);
        
        String[] serverIp = GTransfer.getgServerIp().clone();
		serverIp[0] = sysConfig.getServerIp();
		GTransfer.setgServerIp(serverIp);
		
		String serverPort = sysConfig.getServerPort();
		int[] innerPort = {0, 80, 443};
		if(!serverPort.equals("")) {
			innerPort[0] = (Integer.valueOf(serverPort)).intValue();
			GTransfer.setgServerPort(innerPort);
		}

		GTransfer.setServerName(sysConfig.getServerName());
		GTransfer.setKeyStorePath(sysConfig.getJKS_PATH());
		GTransfer.setKeyStorePW(sysConfig.getJKS_PWD());
		GTransfer.setTrustStorePath(sysConfig.getJKS_PATH());
		GTransfer.setTrustStorePW(sysConfig.getJKS_PWD());
		GTransfer.setCommunicationUserALIAS(sysConfig.getCommunicationUserALIAS());
		GTransfer.setCommunicationOrgID(sysConfig.getCommunicationOrgID());
		GTransfer.setCommunicationUserID(sysConfig.getCommunicationUserID());
		GTransfer.setCommunicationImg(sysConfig.getCommunicationImg());
		GTransfer.setCommunicationSeal(sysConfig.getCommunicationSeal());

        GDBConnector.setDriverClassName(dbConfig.getDriverClassName());
        GDBConnector.setUrl(dbConfig.getUrl());
        GDBConnector.setUsername(dbConfig.getUsername());
        GDBConnector.setPwd(dbConfig.getPassword());
        GDBConnector.setValidationQuery(dbConfig.getValidationQuery());
        GDBConnector.setConnectTimeoutAndReadTimeout(dbConfig.getConnectTimeoutAndReadTimeout());
        GDBConnector.setInitialSize(dbConfig.getInitialSize());
        GDBConnector.setMinIdle(dbConfig.getMinIdle());
        GDBConnector.setMaxIdle(dbConfig.getMaxIdle());
        GDBConnector.setMaxActive(dbConfig.getMaxActive());
        GDBConnector.setMaxWait(dbConfig.getMaxWait());
        GDBConnector.setDefaultAutoCommit(dbConfig.getDefaultAutoCommit());
        GDBConnector.setRemoveAbandoned(dbConfig.getRemoveAbandoned());
        GDBConnector.setRemoveAbandonedTimeout(dbConfig.getRemoveAbandonedTimeout());
        GDBConnector.setTestWhileIdle(dbConfig.getTestWhileIdle());
        GDBConnector.setTimeBetweenEvictionRunsMillis(dbConfig.getTimeBetweenEvictionRunsMillis());
        GDBConnector.setMinEvictableIdleTimeMillis(dbConfig.getMinEvictableIdleTimeMillis());
        GDBConnector.setNumTestsPerEvictionRun(dbConfig.getNumTestsPerEvictionRun());
        GDBConnector.setTestOnReturn(dbConfig.getTestOnReturn());
        GDBConnector.setTestOnBorrow(dbConfig.getTestOnBorrow());
        
		if(smtpConfig.getIsMailOn().equals("true")) {
			GMail.setMailOn(true);
		}else{
			GMail.setMailOn(false);
		}
        GMail.setServerProtocol(smtpConfig.getProtocol());
        GMail.setServerHost(smtpConfig.getHost());
        GMail.setServerSuffix(smtpConfig.getSuffix());
        GMail.setServerAccount(smtpConfig.getAccount());
        GMail.setServerPassword(smtpConfig.getPassword());
        GMail.setServerPort(smtpConfig.getPort());
        GMail.setServerAuth(smtpConfig.getAuth());
        GMail.setServerTls(smtpConfig.getTls());
        GMail.setServerDebug(smtpConfig.getDebug());
        
		if(emailConfig.getIsUsed().equals("true")) {
			GMail.setReceiveMailIsUsed(true);
		}else{
			GMail.setReceiveMailIsUsed(false);
		}
        
        if(emailConfig.getMainsend().equals("")) {
        	GMail.setReceiveMailAccountDev(emailConfig.getDev());
            GMail.setReceiveMailAccountTest(emailConfig.getTest());
            GMail.setReceiveMailAccountManager(emailConfig.getManager());
            GMail.setReceiveMailAccountReseacher(emailConfig.getResearcher());
        }else {
        	try {
	        	String mark = "\\|\\|";
	        	
	    		String[] strListRecipientsMC = emailConfig.getMainsend().split(mark);
	    		if(strListRecipientsMC.length > 0) {
	    			InternetAddress[] iaListRecipientsMC = new InternetAddress[strListRecipientsMC.length];
	    			for(int i = 0;i < iaListRecipientsMC.length;i++) {
	    				iaListRecipientsMC[i] = new InternetAddress(strListRecipientsMC[i], strListRecipientsMC[i], StandardCharsets.UTF_8.toString());
	    			}
	    			GMail.setListRecipientsMC(iaListRecipientsMC);
	    		}
	
	     		String[] strListRecipientsCC = emailConfig.getCopysend().split(mark);
	    		if(strListRecipientsCC.length > 0) {
	    			InternetAddress[] iaListRecipientsCC = new InternetAddress[strListRecipientsCC.length];
	    			for(int i = 0;i < iaListRecipientsCC.length;i++) {
	    				iaListRecipientsCC[i] = new InternetAddress(strListRecipientsCC[i], strListRecipientsCC[i], StandardCharsets.UTF_8.toString());
	    			}
	    			GMail.setListRecipientsCC(iaListRecipientsCC);
	    		}
	     		
	     		String[] strListRecipientsSC = emailConfig.getSecretsend().split(mark);
	    		if(strListRecipientsSC.length > 0) {
	    			InternetAddress[] iaListRecipientsSC = new InternetAddress[strListRecipientsSC.length];
	    			for(int i = 0;i < iaListRecipientsSC.length;i++) {
	    				iaListRecipientsSC[i] = new InternetAddress(strListRecipientsSC[i], strListRecipientsSC[i], StandardCharsets.UTF_8.toString());
	    			}
	    			GMail.setListRecipientsCC(iaListRecipientsSC);
	    		}
    		} catch (Exception e) {
    			GLog.logSysFunctionException("createEmailRecipients", e);
    		}
        }

        GSut.setSysName(sutConfig.getSysName());
        GSut.setSectionName1(sutConfig.getSectionName1());
        GSut.setSectionName2(sutConfig.getSectionName2());
        GSut.setSectionName3(sutConfig.getSectionName3());
        GSut.setSid(sutConfig.getSid());
        
        GCoverageReport.setBILL_TYPE_TOTAL(Integer.valueOf(coverageConfig.getBillTotal()));
        GCoverageReport.setFLOW_TYPE_TOTAL(Integer.valueOf(coverageConfig.getFlowTotal()));
        GCoverageReport.setALGO_TYPE_TOTAL(Integer.valueOf(coverageConfig.getAlgoTotal()));
        
        GQualityAlgo.setFUNCTIONALITY_FLEXIBILITY(Integer.valueOf(qualityConfig.get_FUNCTIONALITY_FLEXIBILITY()));
        GQualityAlgo.setFUNCTIONALITY_ACCURACY(Integer.valueOf(qualityConfig.get_FUNCTIONALITY_ACCURACY()));
        GQualityAlgo.setFUNCTIONALITY_INTERACTIVITY(Integer.valueOf(qualityConfig.get_FUNCTIONALITY_INTERACTIVITY()));
        GQualityAlgo.setFUNCTIONALITY_SECURITY(Integer.valueOf(qualityConfig.get_FUNCTIONALITY_SECURITY()));
        GQualityAlgo.setFUNCTIONALITY_REGULATORY(Integer.valueOf(qualityConfig.get_FUNCTIONALITY_REGULATORY()));
        
        GQualityAlgo.setRELIABILITY_MATURITY(Integer.valueOf(qualityConfig.get_RELIABILITY_MATURITY()));
        GQualityAlgo.setRELIABILITY_FAULTTOLERANCE(Integer.valueOf(qualityConfig.get_RELIABILITY_FAULTTOLERANCE()));
        GQualityAlgo.setRELIABILITY_RECOVERABILITY(Integer.valueOf(qualityConfig.get_RELIABILITY_RECOVERABILITY ()));
        GQualityAlgo.setRELIABILITY_REGULATORY(Integer.valueOf(qualityConfig.get_RELIABILITY_REGULATORY()));
        
        GQualityAlgo.setUSABILITY_COMPREHENSIBILITY(Integer.valueOf(qualityConfig.get_USABILITY_COMPREHENSIBILITY()));
        GQualityAlgo.setUSABILITY_EASYTOLEARN(Integer.valueOf(qualityConfig.get_USABILITY_EASYTOLEARN()));
        GQualityAlgo.setUSABILITY_OPERABILITY(Integer.valueOf(qualityConfig.get_USABILITY_OPERABILITY()));
        GQualityAlgo.setUSABILITY_STICKINESS(Integer.valueOf(qualityConfig.get_USABILITY_STICKINESS()));
        GQualityAlgo.setUSABILITY_REGULATORY(Integer.valueOf(qualityConfig.get_USABILITY_REGULATORY()));
        
        GQualityAlgo.setEFFICIENCY_TIMING(Integer.valueOf(qualityConfig.get_EFFICIENCY_TIMING()));
        GQualityAlgo.setEFFICIENCY_UTILIZATION(Integer.valueOf(qualityConfig.get_EFFICIENCY_UTILIZATION()));
        GQualityAlgo.setEFFICIENCY_REGULATORY(Integer.valueOf(qualityConfig.get_EFFICIENCY_REGULATORY()));
        
        GQualityAlgo.setMAINTAINABILITY_ANALYZABILITY(Integer.valueOf(qualityConfig.get_MAINTAINABILITY_ANALYZABILITY()));
        GQualityAlgo.setMAINTAINABILITY_STABILITY(Integer.valueOf(qualityConfig.get_MAINTAINABILITY_STABILITY()));
        GQualityAlgo.setMAINTAINABILITY_CHANGEABILITY(Integer.valueOf(qualityConfig.get_MAINTAINABILITY_CHANGEABILITY()));
        GQualityAlgo.setMAINTAINABILITY_TESTABILITY(Integer.valueOf(qualityConfig.get_MAINTAINABILITY_TESTABILITY()));
        GQualityAlgo.setMAINTAINABILITY_REGULATORY(Integer.valueOf(qualityConfig.get_MAINTAINABILITY_REGULATORY()));
        
        GQualityAlgo.setPORTABILITY_ADAPTABILITY(Integer.valueOf(qualityConfig.get_PORTABILITY_ADAPTABILITY()));
        GQualityAlgo.setPORTABILITY_INSTALLATION(Integer.valueOf(qualityConfig.get_PORTABILITY_INSTALLATION()));
        GQualityAlgo.setPORTABILITY_COEXISTENCE(Integer.valueOf(qualityConfig.get_PORTABILITY_COEXISTENCE()));
        GQualityAlgo.setPORTABILITY_REPLACEABILITY(Integer.valueOf(qualityConfig.get_PORTABILITY_REPLACEABILITY()));
        GQualityAlgo.setPORTABILITY_REGULATORY(Integer.valueOf(qualityConfig.get_PORTABILITY_REGULATORY()));
        
        GTransfer.setNetworkSegment(sysConfig.getStrFeaturesURL());
        GTransfer.setReportPort(sysConfig.getStrFeaturesPort());
        GSubstitute.setSUBSTITUTE_URLOLD(sysConfig.getStrReportURLOLD());
        if(GTransfer.getNetworkSegment() == null || GTransfer.getNetworkSegment().equals("")) {
        	GSubstitute.setSUBSTITUTE_URL(sysConfig.getStrReportURL());
        	GSpendtimes.setREPORT_URL(sysConfig.getStrSpendTimesURL());
            GCoverageReport.setREPORT_URL(coverageConfig.getCoverageURL());
            GQualityReport.setREPORT_URL(qualityConfig.getTC_QUALITY_URL());
        }else {
        	if(GTransfer.getReportPort() != null && !GTransfer.getReportPort().equals("")) {
        		String ip = GRemote.getNetworkIP(GTransfer.getNetworkSegment());
        		String port = GTransfer.getReportPort();
        		GSubstitute.setSUBSTITUTE_URL(ip + ":" + port + "/" + sysConfig.getStrReportURL());
        		GSpendtimes.setREPORT_URL(ip + ":" + port + "/" + sysConfig.getStrSpendTimesURL());
                GCoverageReport.setREPORT_URL(ip + ":" + port + "/" + coverageConfig.getCoverageURL());
                GQualityReport.setREPORT_URL(ip + ":" + port + "/" + qualityConfig.getTC_QUALITY_URL());
        	}
        }
    }

    /**
     *  将配置文件中各项的值赋给全局变量
     */
    public static void loadConfig() {
    	long startTime = 0;
    	GSys.logSys(GMissionMsg.getStepStart("GConfig"));
		startTime = System.currentTimeMillis();
        try {
        	appContext = new FileSystemXmlApplicationContext("./xml/spring.xml");
    		
        	GParam.loadConfig();
        	GTestPlan.loadConfig();
        	GTestMission.loadConfig();
    		GTransfer.loadConfig();
    		GDBConnector.loadConfig();
    		GMail.loadConfig();
    		GSut.loadConfig();
    		GCoverageReport.loadConfig();
    		GQualityReport.loadConfig();

        } catch (Exception e) {
            if (appContext != null) {
                ((ConfigurableApplicationContext) appContext).close();
            }
        }
        GSys.logShowAndRecord(startTime, "GConfig");
        GSys.logSys(GMissionMsg.getStepEnd());
    }
}
