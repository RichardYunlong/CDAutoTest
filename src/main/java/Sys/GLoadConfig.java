package Sys;

import Base.GFile;
import Base.GMissionMsg;
import Coverage.GCoverageConfig;
import Coverage.GCoverageReport;
import DB.GDBConfig;
import DB.GDBConnector;
import DT.GLog;
import DT.GRemote;
import Mail.GEmailConfig;
import Mail.GSmtpConfig;
import Quality.GQualityConfig;
import Sut.GSutConfig;
import Test.GTestMission;
import Webdriver.GWebDiverParam;
import Webdriver.GWebdriverConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.internet.InternetAddress;
import java.nio.charset.StandardCharsets;

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
     *  WebDriver配置项
     */
    @Autowired
    private GWebdriverConfig webbdriverConfig;
    
	/**
	 *  系统spring对象
	 */
	private static ApplicationContext appContext = null;
    
    /**
     *  读取配置文件中各项的值
     */
    @PostConstruct
    public void init() {
        GStatic.gP.setDriver(sysConfig.getDriver());
        GStatic.gP.setINPUT_TYPE(Integer.valueOf(sysConfig.getTestInputType()));
        GStatic.gP.setINPUT_FROM(Integer.valueOf(sysConfig.getTestInputSource()));
        GStatic.gP.setINPUT_ROW_FROM(Integer.parseInt(sysConfig.getTestInputBeginRowIndex()));
        GStatic.gP.setINPUT_COL_FROM(Integer.parseInt(sysConfig.getTestInputBeginColumnIndex()));
		if(sysConfig.getIsAutoCheckReport().equals("true")) {
            GStatic.gP.setbAutoCheckReport(true);
		}
		if(sysConfig.getDragonShow().equals("true")) {
            GStatic.gP.setDragonShow(true);
		}

		if(sysConfig.getIsCheckOnly().equals("false")) {
            GStatic.gTestMission.setTM_CHECK_ONLY(false);
		}
		if(sysConfig.getLoopCourt() > 1) {
            GStatic.gTestMission.setTM_LOOP_COURT(sysConfig.getLoopCourt());
		}
    	
		GTestMission.gTestPlan.setRecordInputParamListInTxt(Integer.parseInt(sysConfig.getIsLoggedInputs()));
		GTestMission.gTestPlan.setTimeWait(sysConfig.getTimeWait());
		GTestMission.gTestPlan.setTestCaseType(sysConfig.getTestCaseType());
		if(sysConfig.getIsBackup().equals("true")) {
			GTestMission.gTestPlan.setbIsBackup(true);
		}

        GStatic.gTransfer.setServerConnType(Integer.parseInt(sysConfig.getServerConnType()));
        String[] serverUrl = GStatic.gTransfer.getgServerUrl().clone();
        serverUrl[0] = sysConfig.getServerUrl();
        GStatic.gTransfer.setgServerUrl(serverUrl);
        
        String[] serverWWW = GStatic.gTransfer.getgServerWWW().clone();
        serverWWW[0] = sysConfig.getServerWWW();
        GStatic.gTransfer.setgServerWWW(serverWWW);
        
        String[] serverIp = GStatic.gTransfer.getgServerIp().clone();
		serverIp[0] = sysConfig.getServerIp();
		GStatic.gTransfer.setgServerIp(serverIp);
		
		String serverPort = sysConfig.getServerPort();
		int[] innerPort = {0, 80, 443};
		if(!serverPort.isEmpty()) {
			innerPort[0] = Integer.parseInt(serverPort);
			GStatic.gTransfer.setgServerPort(innerPort);
		}

		GStatic.gTransfer.setServerName(sysConfig.getServerName());
		GStatic.gTransfer.setKeyStorePath(sysConfig.getJKS_PATH());
		GStatic.gTransfer.setKeyStorePW(sysConfig.getJKS_PWD());
		GStatic.gTransfer.setTrustStorePath(sysConfig.getJKS_PATH());
		GStatic.gTransfer.setTrustStorePW(sysConfig.getJKS_PWD());
		GStatic.gTransfer.setCommunicationUserALIAS(sysConfig.getCommunicationUserALIAS());
		GStatic.gTransfer.setCommunicationOrgID(sysConfig.getCommunicationOrgID());
		GStatic.gTransfer.setCommunicationUserID(sysConfig.getCommunicationUserID());
		GStatic.gTransfer.setCommunicationImg(sysConfig.getCommunicationImg());
		GStatic.gTransfer.setCommunicationSeal(sysConfig.getCommunicationSeal());

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

        GStatic.gMail.setMailOn(smtpConfig.getIsMailOn().equals("true"));
        GStatic.gMail.setServerProtocol(smtpConfig.getProtocol());
        GStatic.gMail.setServerHost(smtpConfig.getHost());
        GStatic.gMail.setServerSuffix(smtpConfig.getSuffix());
        GStatic.gMail.setServerAccount(smtpConfig.getAccount());
        GStatic.gMail.setServerPassword(smtpConfig.getPassword());
        GStatic.gMail.setServerPort(smtpConfig.getPort());
        GStatic.gMail.setServerAuth(smtpConfig.getAuth());
        GStatic.gMail.setServerTls(smtpConfig.getTls());
        GStatic.gMail.setServerDebug(smtpConfig.getDebug());
        GStatic.gMail.setReceiveMailIsUsed(emailConfig.getIsUsed().equals("true"));
        
        if(emailConfig.getMainsend().isEmpty()) {
        	GStatic.gMail.setReceiveMailAccountDev(emailConfig.getDev());
            GStatic.gMail.setReceiveMailAccountTest(emailConfig.getTest());
            GStatic.gMail.setReceiveMailAccountManager(emailConfig.getManager());
            GStatic.gMail.setReceiveMailAccountReseacher(emailConfig.getResearcher());
        }else {
        	try {
	        	String mark = "\\|\\|";
	        	
	    		String[] strListRecipientsMC = emailConfig.getMainsend().split(mark);
	    		if(strListRecipientsMC.length > 0) {
	    			InternetAddress[] iaListRecipientsMC = new InternetAddress[strListRecipientsMC.length];
	    			for(int i = 0;i < iaListRecipientsMC.length;i++) {
	    				iaListRecipientsMC[i] = new InternetAddress(strListRecipientsMC[i], strListRecipientsMC[i], StandardCharsets.UTF_8.toString());
	    			}
	    			GStatic.gMail.setListRecipientsMC(iaListRecipientsMC);
	    		}
	
	     		String[] strListRecipientsCC = emailConfig.getCopysend().split(mark);
	    		if(strListRecipientsCC.length > 0) {
	    			InternetAddress[] iaListRecipientsCC = new InternetAddress[strListRecipientsCC.length];
	    			for(int i = 0;i < iaListRecipientsCC.length;i++) {
	    				iaListRecipientsCC[i] = new InternetAddress(strListRecipientsCC[i], strListRecipientsCC[i], StandardCharsets.UTF_8.toString());
	    			}
	    			GStatic.gMail.setListRecipientsCC(iaListRecipientsCC);
	    		}
	     		
	     		String[] strListRecipientsSC = emailConfig.getSecretsend().split(mark);
	    		if(strListRecipientsSC.length > 0) {
	    			InternetAddress[] iaListRecipientsSC = new InternetAddress[strListRecipientsSC.length];
	    			for(int i = 0;i < iaListRecipientsSC.length;i++) {
	    				iaListRecipientsSC[i] = new InternetAddress(strListRecipientsSC[i], strListRecipientsSC[i], StandardCharsets.UTF_8.toString());
	    			}
	    			GStatic.gMail.setListRecipientsCC(iaListRecipientsSC);
	    		}
    		} catch (Exception e) {
    			GLog.logSysFunctionException("createEmailRecipients", e);
    		}
        }

        GStatic.gSut.setSysName(sutConfig.getSysName());
        GStatic.gSut.setSectionName1(sutConfig.getSectionName1());
        GStatic.gSut.setSectionName2(sutConfig.getSectionName2());
        GStatic.gSut.setSectionName3(sutConfig.getSectionName3());
        GStatic.gSut.setSid(sutConfig.getSid());
        
        GCoverageReport.setBILL_TYPE_TOTAL(Integer.valueOf(coverageConfig.getBillTotal()));
        GCoverageReport.setFLOW_TYPE_TOTAL(Integer.valueOf(coverageConfig.getFlowTotal()));
        GCoverageReport.setALGO_TYPE_TOTAL(Integer.valueOf(coverageConfig.getAlgoTotal()));

        GStatic.gQualityAlgo.setFUNCTIONALITY_FLEXIBILITY(Integer.valueOf(qualityConfig.get_FUNCTIONALITY_FLEXIBILITY()));
        GStatic.gQualityAlgo.setFUNCTIONALITY_ACCURACY(Integer.valueOf(qualityConfig.get_FUNCTIONALITY_ACCURACY()));
        GStatic.gQualityAlgo.setFUNCTIONALITY_INTERACTIVITY(Integer.valueOf(qualityConfig.get_FUNCTIONALITY_INTERACTIVITY()));
        GStatic.gQualityAlgo.setFUNCTIONALITY_SECURITY(Integer.valueOf(qualityConfig.get_FUNCTIONALITY_SECURITY()));
        GStatic.gQualityAlgo.setFUNCTIONALITY_REGULATORY(Integer.valueOf(qualityConfig.get_FUNCTIONALITY_REGULATORY()));
        
        GStatic.gQualityAlgo.setRELIABILITY_MATURITY(Integer.valueOf(qualityConfig.get_RELIABILITY_MATURITY()));
        GStatic.gQualityAlgo.setRELIABILITY_FAULTTOLERANCE(Integer.valueOf(qualityConfig.get_RELIABILITY_FAULTTOLERANCE()));
        GStatic.gQualityAlgo.setRELIABILITY_RECOVERABILITY(Integer.valueOf(qualityConfig.get_RELIABILITY_RECOVERABILITY ()));
        GStatic.gQualityAlgo.setRELIABILITY_REGULATORY(Integer.valueOf(qualityConfig.get_RELIABILITY_REGULATORY()));
        
        GStatic.gQualityAlgo.setUSABILITY_COMPREHENSIBILITY(Integer.valueOf(qualityConfig.get_USABILITY_COMPREHENSIBILITY()));
        GStatic.gQualityAlgo.setUSABILITY_EASYTOLEARN(Integer.valueOf(qualityConfig.get_USABILITY_EASYTOLEARN()));
        GStatic.gQualityAlgo.setUSABILITY_OPERABILITY(Integer.valueOf(qualityConfig.get_USABILITY_OPERABILITY()));
        GStatic.gQualityAlgo.setUSABILITY_STICKINESS(Integer.valueOf(qualityConfig.get_USABILITY_STICKINESS()));
        GStatic.gQualityAlgo.setUSABILITY_REGULATORY(Integer.valueOf(qualityConfig.get_USABILITY_REGULATORY()));
        
        GStatic.gQualityAlgo.setEFFICIENCY_TIMING(Integer.valueOf(qualityConfig.get_EFFICIENCY_TIMING()));
        GStatic.gQualityAlgo.setEFFICIENCY_UTILIZATION(Integer.valueOf(qualityConfig.get_EFFICIENCY_UTILIZATION()));
        GStatic.gQualityAlgo.setEFFICIENCY_REGULATORY(Integer.valueOf(qualityConfig.get_EFFICIENCY_REGULATORY()));
        
        GStatic.gQualityAlgo.setMAINTAINABILITY_ANALYZABILITY(Integer.valueOf(qualityConfig.get_MAINTAINABILITY_ANALYZABILITY()));
        GStatic.gQualityAlgo.setMAINTAINABILITY_STABILITY(Integer.valueOf(qualityConfig.get_MAINTAINABILITY_STABILITY()));
        GStatic.gQualityAlgo.setMAINTAINABILITY_CHANGEABILITY(Integer.valueOf(qualityConfig.get_MAINTAINABILITY_CHANGEABILITY()));
        GStatic.gQualityAlgo.setMAINTAINABILITY_TESTABILITY(Integer.valueOf(qualityConfig.get_MAINTAINABILITY_TESTABILITY()));
        GStatic.gQualityAlgo.setMAINTAINABILITY_REGULATORY(Integer.valueOf(qualityConfig.get_MAINTAINABILITY_REGULATORY()));
        
        GStatic.gQualityAlgo.setPORTABILITY_ADAPTABILITY(Integer.valueOf(qualityConfig.get_PORTABILITY_ADAPTABILITY()));
        GStatic.gQualityAlgo.setPORTABILITY_INSTALLATION(Integer.valueOf(qualityConfig.get_PORTABILITY_INSTALLATION()));
        GStatic.gQualityAlgo.setPORTABILITY_COEXISTENCE(Integer.valueOf(qualityConfig.get_PORTABILITY_COEXISTENCE()));
        GStatic.gQualityAlgo.setPORTABILITY_REPLACEABILITY(Integer.valueOf(qualityConfig.get_PORTABILITY_REPLACEABILITY()));
        GStatic.gQualityAlgo.setPORTABILITY_REGULATORY(Integer.valueOf(qualityConfig.get_PORTABILITY_REGULATORY()));
        
        GStatic.gTransfer.setNetworkSegment(sysConfig.getStrFeaturesURL());
        GStatic.gTransfer.setReportPort(sysConfig.getStrFeaturesPort());
        GStatic.gSubstitute.setSUBSTITUTE_URLOLD(sysConfig.getStrReportURLOLD());
        if(GStatic.gTransfer.getNetworkSegment() == null || GStatic.gTransfer.getNetworkSegment().isEmpty()) {
            GStatic.gSubstitute.setSUBSTITUTE_URL(sysConfig.getStrReportURL());
            GStatic.gSpendtimes.setREPORT_URL(sysConfig.getStrSpendTimesURL());
            GCoverageReport.setREPORT_URL(coverageConfig.getCoverageURL());
            GStatic.gQualityReport.setREPORT_URL(qualityConfig.getTC_QUALITY_URL());
        }else {
        	if(GStatic.gTransfer.getReportPort() != null && !GStatic.gTransfer.getReportPort().isEmpty()) {
        		String ip = GRemote.getNetworkIP(GStatic.gTransfer.getNetworkSegment());
        		String port = GStatic.gTransfer.getReportPort();
                GStatic.gSubstitute.setSUBSTITUTE_URL(ip + ":" + port + "/" + sysConfig.getStrReportURL());
                GStatic.gSpendtimes.setREPORT_URL(ip + ":" + port + "/" + sysConfig.getStrSpendTimesURL());
                GCoverageReport.setREPORT_URL(ip + ":" + port + "/" + coverageConfig.getCoverageURL());
                GStatic.gQualityReport.setREPORT_URL(ip + ":" + port + "/" + qualityConfig.getTC_QUALITY_URL());
        	}
        }

        GWebDiverParam.setBrowserDriverType(webbdriverConfig.getBrowserDriverType());
        GWebDiverParam.setBrowserDriverDownloadUrl(webbdriverConfig.getBrowserDriverDownloadUrl());
        GWebDiverParam.setBrowserDriverVerision(webbdriverConfig.getBrowserDriverVerision());
        GWebDiverParam.setBrowserDriverFileName(webbdriverConfig.getBrowserDriverFileName());
        GWebDiverParam.setBrowserDriverSavePath(webbdriverConfig.getBrowserDriverSavePath());
        GWebDiverParam.setBrowserDriverSaveName(webbdriverConfig.getBrowserDriverSaveName());
    }

    /**
     *  将配置文件中各项的值赋给全局变量
     */
    public static void loadConfig() {
    	long startTime;
        GFile.writeStringToGuideBottom(GMissionMsg.getStepStart("GConfig"));
		startTime = System.currentTimeMillis();
        try {
            appContext = new FileSystemXmlApplicationContext("./xml/spring.xml");

            GStatic.gP.loadConfig();
        	GTestMission.gTestPlan.loadConfig();
            GStatic.gTestMission.loadConfig();
    		GStatic.gTransfer.loadConfig();
    		GDBConnector.loadConfig();
    		GStatic.gMail.loadConfig();
    		GStatic.gSut.loadConfig();
            GWebDiverParam.loadConfig();
    		GCoverageReport.loadConfig();
    		GStatic.gQualityReport.loadConfig();

        } catch (Exception e) {
            if (appContext != null) {
                ((ConfigurableApplicationContext) appContext).close();
            }
        }
        GStatic.gSys.logShowAndRecordGuide(startTime, "GConfig");
        GFile.writeStringToGuideBottom(GMissionMsg.getStepEnd());
    }
}
