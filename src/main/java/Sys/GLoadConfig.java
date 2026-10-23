package Sys;

import Base.GFile;
import Base.GMissionMsg;
import Coverage.GCoverageReport;
import DB.GDBConnector;
import DT.GLog;
import DT.GRemote;
import Test.GTestMission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
	 *  统一配置中心
	 */
    @Autowired
    private GConfig config;
    
	/**
	 *  系统spring对象
	 */
	private static ApplicationContext appContext = null;
    
    /**
     *  读取配置文件中各项的值
     */
    @PostConstruct
    public void init() {
        GStatic.gP.setDriver(config.sys().getDriver());
        GStatic.gP.setINPUT_TYPE(Integer.valueOf(config.sys().getTestInputType()));
        GStatic.gP.setINPUT_FROM(Integer.valueOf(config.sys().getTestInputSource()));
        GStatic.gP.setINPUT_ROW_FROM(Integer.parseInt(config.sys().getTestInputBeginRowIndex()));
        GStatic.gP.setINPUT_COL_FROM(Integer.parseInt(config.sys().getTestInputBeginColumnIndex()));
		if(config.sys().getIsAutoCheckReport().equals("true")) {
            GStatic.gP.setbAutoCheckReport(true);
		}
		if(config.sys().getDragonShow().equals("true")) {
            GStatic.gP.setDragonShow(true);
		}

		if(config.sys().getIsCheckOnly().equals("false")) {
            GStatic.gTestMission.setTM_CHECK_ONLY(false);
		}
		if(config.sys().getLoopCourt() > 1) {
            GStatic.gTestMission.setTM_LOOP_COURT(config.sys().getLoopCourt());
		}
    	
		GTestMission.gTestPlan.setRecordInputParamListInTxt(Integer.parseInt(config.sys().getIsLoggedInputs()));
		GTestMission.gTestPlan.setTimeWait(config.sys().getTimeWait());
		GTestMission.gTestPlan.setTestCaseType(config.sys().getTestCaseType());
		if(config.sys().getIsBackup().equals("true")) {
			GTestMission.gTestPlan.setbIsBackup(true);
		}

        GStatic.gTransfer.setServerConnType(Integer.parseInt(config.sys().getServerConnType()));
        String[] serverUrl = GStatic.gTransfer.getgServerUrl().clone();
        serverUrl[0] = config.sys().getServerUrl();
        GStatic.gTransfer.setgServerUrl(serverUrl);
        
        String[] serverWWW = GStatic.gTransfer.getgServerWWW().clone();
        serverWWW[0] = config.sys().getServerWWW();
        GStatic.gTransfer.setgServerWWW(serverWWW);
        
        String[] serverIp = GStatic.gTransfer.getgServerIp().clone();
		serverIp[0] = config.sys().getServerIp();
		GStatic.gTransfer.setgServerIp(serverIp);
		
		String serverPort = config.sys().getServerPort();
		int[] innerPort = {0, 80, 443};
		if(!serverPort.isEmpty()) {
			innerPort[0] = Integer.parseInt(serverPort);
			GStatic.gTransfer.setgServerPort(innerPort);
		}

		GStatic.gTransfer.setServerName(config.sys().getServerName());
		GStatic.gTransfer.setKeyStorePath(config.sys().getJKS_PATH());
		GStatic.gTransfer.setKeyStorePW(config.sys().getJKS_PWD());
		GStatic.gTransfer.setTrustStorePath(config.sys().getJKS_PATH());
		GStatic.gTransfer.setTrustStorePW(config.sys().getJKS_PWD());
		GStatic.gTransfer.setCommunicationUserALIAS(config.sys().getCommunicationUserALIAS());
		GStatic.gTransfer.setCommunicationOrgID(config.sys().getCommunicationOrgID());
		GStatic.gTransfer.setCommunicationUserID(config.sys().getCommunicationUserID());
		GStatic.gTransfer.setCommunicationImg(config.sys().getCommunicationImg());
		GStatic.gTransfer.setCommunicationSeal(config.sys().getCommunicationSeal());

        GDBConnector.setDriverClassName(config.db().getDriverClassName());
        GDBConnector.setUrl(config.db().getUrl());
        GDBConnector.setUsername(config.db().getUsername());
        GDBConnector.setPwd(config.db().getPassword());
        GDBConnector.setValidationQuery(config.db().getValidationQuery());
        GDBConnector.setConnectTimeoutAndReadTimeout(config.db().getConnectTimeoutAndReadTimeout());
        GDBConnector.setInitialSize(config.db().getInitialSize());
        GDBConnector.setMinIdle(config.db().getMinIdle());
        GDBConnector.setMaxIdle(config.db().getMaxIdle());
        GDBConnector.setMaxActive(config.db().getMaxActive());
        GDBConnector.setMaxWait(config.db().getMaxWait());
        GDBConnector.setDefaultAutoCommit(config.db().getDefaultAutoCommit());
        GDBConnector.setRemoveAbandoned(config.db().getRemoveAbandoned());
        GDBConnector.setRemoveAbandonedTimeout(config.db().getRemoveAbandonedTimeout());
        GDBConnector.setTestWhileIdle(config.db().getTestWhileIdle());
        GDBConnector.setTimeBetweenEvictionRunsMillis(config.db().getTimeBetweenEvictionRunsMillis());
        GDBConnector.setMinEvictableIdleTimeMillis(config.db().getMinEvictableIdleTimeMillis());
        GDBConnector.setNumTestsPerEvictionRun(config.db().getNumTestsPerEvictionRun());
        GDBConnector.setTestOnReturn(config.db().getTestOnReturn());
        GDBConnector.setTestOnBorrow(config.db().getTestOnBorrow());

        GStatic.gMail.setMailOn(config.smtp().getIsMailOn().equals("true"));
        GStatic.gMail.setServerProtocol(config.smtp().getProtocol());
        GStatic.gMail.setServerHost(config.smtp().getHost());
        GStatic.gMail.setServerSuffix(config.smtp().getSuffix());
        GStatic.gMail.setServerAccount(config.smtp().getAccount());
        GStatic.gMail.setServerPassword(config.smtp().getPassword());
        GStatic.gMail.setServerPort(config.smtp().getPort());
        GStatic.gMail.setServerAuth(config.smtp().getAuth());
        GStatic.gMail.setServerTls(config.smtp().getTls());
        GStatic.gMail.setServerDebug(config.smtp().getDebug());
        GStatic.gMail.setReceiveMailIsUsed(config.email().getIsUsed().equals("true"));
        
        if(config.email().getMainsend().isEmpty()) {
        	GStatic.gMail.setReceiveMailAccountDev(config.email().getDev());
            GStatic.gMail.setReceiveMailAccountTest(config.email().getTest());
            GStatic.gMail.setReceiveMailAccountManager(config.email().getManager());
            GStatic.gMail.setReceiveMailAccountReseacher(config.email().getResearcher());
        }else {
        	try {
	        	String mark = "\\|\\|";
	        	
	    		String[] strListRecipientsMC = config.email().getMainsend().split(mark);
	    		if(strListRecipientsMC.length > 0) {
	    			InternetAddress[] iaListRecipientsMC = new InternetAddress[strListRecipientsMC.length];
	    			for(int i = 0;i < iaListRecipientsMC.length;i++) {
	    				iaListRecipientsMC[i] = new InternetAddress(strListRecipientsMC[i], strListRecipientsMC[i], StandardCharsets.UTF_8.toString());
	    			}
	    			GStatic.gMail.setListRecipientsMC(iaListRecipientsMC);
	    		}
	
	     		String[] strListRecipientsCC = config.email().getCopysend().split(mark);
	    		if(strListRecipientsCC.length > 0) {
	    			InternetAddress[] iaListRecipientsCC = new InternetAddress[strListRecipientsCC.length];
	    			for(int i = 0;i < iaListRecipientsCC.length;i++) {
	    				iaListRecipientsCC[i] = new InternetAddress(strListRecipientsCC[i], strListRecipientsCC[i], StandardCharsets.UTF_8.toString());
	    			}
	    			GStatic.gMail.setListRecipientsCC(iaListRecipientsCC);
	    		}
	     		
	     		String[] strListRecipientsSC = config.email().getSecretsend().split(mark);
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

        GStatic.gSut.setSysName(config.sut().getSysName());
        GStatic.gSut.setSectionName1(config.sut().getSectionName1());
        GStatic.gSut.setSectionName2(config.sut().getSectionName2());
        GStatic.gSut.setSectionName3(config.sut().getSectionName3());
        GStatic.gSut.setSid(config.sut().getSid());
        
        GCoverageReport.setBILL_TYPE_TOTAL(Integer.valueOf(config.coverage().getBillTotal()));
        GCoverageReport.setFLOW_TYPE_TOTAL(Integer.valueOf(config.coverage().getFlowTotal()));
        GCoverageReport.setALGO_TYPE_TOTAL(Integer.valueOf(config.coverage().getAlgoTotal()));

        GStatic.gQualityAlgo.setFUNCTIONALITY_FLEXIBILITY(Integer.valueOf(config.quality().get_FUNCTIONALITY_FLEXIBILITY()));
        GStatic.gQualityAlgo.setFUNCTIONALITY_ACCURACY(Integer.valueOf(config.quality().get_FUNCTIONALITY_ACCURACY()));
        GStatic.gQualityAlgo.setFUNCTIONALITY_INTERACTIVITY(Integer.valueOf(config.quality().get_FUNCTIONALITY_INTERACTIVITY()));
        GStatic.gQualityAlgo.setFUNCTIONALITY_SECURITY(Integer.valueOf(config.quality().get_FUNCTIONALITY_SECURITY()));
        GStatic.gQualityAlgo.setFUNCTIONALITY_REGULATORY(Integer.valueOf(config.quality().get_FUNCTIONALITY_REGULATORY()));
        
        GStatic.gQualityAlgo.setRELIABILITY_MATURITY(Integer.valueOf(config.quality().get_RELIABILITY_MATURITY()));
        GStatic.gQualityAlgo.setRELIABILITY_FAULTTOLERANCE(Integer.valueOf(config.quality().get_RELIABILITY_FAULTTOLERANCE()));
        GStatic.gQualityAlgo.setRELIABILITY_RECOVERABILITY(Integer.valueOf(config.quality().get_RELIABILITY_RECOVERABILITY ()));
        GStatic.gQualityAlgo.setRELIABILITY_REGULATORY(Integer.valueOf(config.quality().get_RELIABILITY_REGULATORY()));
        
        GStatic.gQualityAlgo.setUSABILITY_COMPREHENSIBILITY(Integer.valueOf(config.quality().get_USABILITY_COMPREHENSIBILITY()));
        GStatic.gQualityAlgo.setUSABILITY_EASYTOLEARN(Integer.valueOf(config.quality().get_USABILITY_EASYTOLEARN()));
        GStatic.gQualityAlgo.setUSABILITY_OPERABILITY(Integer.valueOf(config.quality().get_USABILITY_OPERABILITY()));
        GStatic.gQualityAlgo.setUSABILITY_STICKINESS(Integer.valueOf(config.quality().get_USABILITY_STICKINESS()));
        GStatic.gQualityAlgo.setUSABILITY_REGULATORY(Integer.valueOf(config.quality().get_USABILITY_REGULATORY()));
        
        GStatic.gQualityAlgo.setEFFICIENCY_TIMING(Integer.valueOf(config.quality().get_EFFICIENCY_TIMING()));
        GStatic.gQualityAlgo.setEFFICIENCY_UTILIZATION(Integer.valueOf(config.quality().get_EFFICIENCY_UTILIZATION()));
        GStatic.gQualityAlgo.setEFFICIENCY_REGULATORY(Integer.valueOf(config.quality().get_EFFICIENCY_REGULATORY()));
        
        GStatic.gQualityAlgo.setMAINTAINABILITY_ANALYZABILITY(Integer.valueOf(config.quality().get_MAINTAINABILITY_ANALYZABILITY()));
        GStatic.gQualityAlgo.setMAINTAINABILITY_STABILITY(Integer.valueOf(config.quality().get_MAINTAINABILITY_STABILITY()));
        GStatic.gQualityAlgo.setMAINTAINABILITY_CHANGEABILITY(Integer.valueOf(config.quality().get_MAINTAINABILITY_CHANGEABILITY()));
        GStatic.gQualityAlgo.setMAINTAINABILITY_TESTABILITY(Integer.valueOf(config.quality().get_MAINTAINABILITY_TESTABILITY()));
        GStatic.gQualityAlgo.setMAINTAINABILITY_REGULATORY(Integer.valueOf(config.quality().get_MAINTAINABILITY_REGULATORY()));
        
        GStatic.gQualityAlgo.setPORTABILITY_ADAPTABILITY(Integer.valueOf(config.quality().get_PORTABILITY_ADAPTABILITY()));
        GStatic.gQualityAlgo.setPORTABILITY_INSTALLATION(Integer.valueOf(config.quality().get_PORTABILITY_INSTALLATION()));
        GStatic.gQualityAlgo.setPORTABILITY_COEXISTENCE(Integer.valueOf(config.quality().get_PORTABILITY_COEXISTENCE()));
        GStatic.gQualityAlgo.setPORTABILITY_REPLACEABILITY(Integer.valueOf(config.quality().get_PORTABILITY_REPLACEABILITY()));
        GStatic.gQualityAlgo.setPORTABILITY_REGULATORY(Integer.valueOf(config.quality().get_PORTABILITY_REGULATORY()));
        
        GStatic.gTransfer.setNetworkSegment(config.sys().getStrFeaturesURL());
        GStatic.gTransfer.setReportPort(config.sys().getStrFeaturesPort());
        GStatic.gSubstitute.setSUBSTITUTE_URLOLD(config.sys().getStrReportURLOLD());
        if(GStatic.gTransfer.getNetworkSegment() == null || GStatic.gTransfer.getNetworkSegment().isEmpty()) {
            GStatic.gSubstitute.setSUBSTITUTE_URL(config.sys().getStrReportURL());
            GStatic.gSpendtimes.setREPORT_URL(config.sys().getStrSpendTimesURL());
            GCoverageReport.setREPORT_URL(config.coverage().getCoverageURL());
            GStatic.gQualityReport.setREPORT_URL(config.quality().getTC_QUALITY_URL());
        }else {
        	if(GStatic.gTransfer.getReportPort() != null && !GStatic.gTransfer.getReportPort().isEmpty()) {
        		String ip = GRemote.getNetworkIP(GStatic.gTransfer.getNetworkSegment());
        		String port = GStatic.gTransfer.getReportPort();
                GStatic.gSubstitute.setSUBSTITUTE_URL(ip + ":" + port + "/" + config.sys().getStrReportURL());
                GStatic.gSpendtimes.setREPORT_URL(ip + ":" + port + "/" + config.sys().getStrSpendTimesURL());
                GCoverageReport.setREPORT_URL(ip + ":" + port + "/" + config.coverage().getCoverageURL());
                GStatic.gQualityReport.setREPORT_URL(ip + ":" + port + "/" + config.quality().getTC_QUALITY_URL());
        	}
        }

        GStatic.gWebDiverParam.setBrowserOS(config.webdriver().getBrowserOS());
        GStatic.gWebDiverParam.setBrowserInstallPath(config.webdriver().getBrowserInstallPath());
        GStatic.gWebDiverParam.setBrowserApplication(config.webdriver().getBrowserApplication());
        GStatic.gWebDiverParam.setBrowserHeadless(config.webdriver().getBrowserHeadless());
        GStatic.gWebDiverParam.setBrowserDriverUpdate(config.webdriver().getBrowserDriverUpdate());
        GStatic.gWebDiverParam.setBrowserDriverType(config.webdriver().getBrowserDriverType());
        GStatic.gWebDiverParam.setBrowserDriverDownloadUrl(config.webdriver().getBrowserDriverDownloadUrl());
        GStatic.gWebDiverParam.setBrowserDriverVerision(config.webdriver().getBrowserDriverVerision());
        GStatic.gWebDiverParam.setBrowserDriverFileName(config.webdriver().getBrowserDriverFileName());
        GStatic.gWebDiverParam.setBrowserDriverFileFormat(config.webdriver().getBrowserDriverFileFormat());
        GStatic.gWebDiverParam.setBrowserDriverSavePath(config.webdriver().getBrowserDriverSavePath());
        GStatic.gWebDiverParam.setBrowserDriverSaveName(config.webdriver().getBrowserDriverSaveName());
        GStatic.gWebDiverParam.setBrowserDriverDirName(config.webdriver().getBrowserDriverDirName());
        GStatic.gWebDiverParam.setBrowserLibPath(config.webdriver().getBrowserlibPath());
        GStatic.gWebDiverParam.setBrowserDriverbin(config.webdriver().getBrowserDriverbin());
        GStatic.gWebDiverParam.setBrowserDriverLibName(config.webdriver().getBrowserDriverLibName());
        GStatic.gWebDiverParam.setBrowserDriverProcessName(config.webdriver().getBrowserDriverProcessName());
        GStatic.gWebDiverParam.setBrowserLogType(config.webdriver().getBrowserLogType());
    }

    /**
     *  将配置文件中各项的值赋给全局变量
     */
    public static void loadConfig() {
    	long startTime;
        GFile.writeStringToGuideBottom(GMissionMsg.getStepStart("GConfig"));
		startTime = System.currentTimeMillis();
        try {
            appContext = new ClassPathXmlApplicationContext(GPath.XML_TEMP_PATH + "spring.xml");
            // 输出实际生效的配置来源：现场最常见的问题是"改了配置却没生效"(改的是 jar 内那一份)
            GConfig activeConfig = appContext.getBean(GConfig.class);
            for (String source : activeConfig.getLoadedSources()) {
                GFile.writeStringToGuideBottom("CONFIG SOURCE: " + source);
            }

            GStatic.gP.loadConfig();
        	GTestMission.gTestPlan.loadConfig();
            GStatic.gTestMission.loadConfig();
    		GStatic.gTransfer.loadConfig();
    		GDBConnector.loadConfig();
    		GStatic.gMail.loadConfig();
    		GStatic.gSut.loadConfig();
            GStatic.gWebDiverParam.loadConfig();
    		GCoverageReport.loadConfig();
    		GStatic.gQualityReport.loadConfig();

        } catch (Exception e) {
            if (appContext != null) {
                ((ConfigurableApplicationContext) appContext).close();
            }
            // 配置加载失败必须留下根因并中止启动：
            // 原实现完全吞掉异常，后续只在业务处表现为 NPE / 空 URL / 空报告，定位成本极高。
            GLog.logSysFunctionException("loadConfig", e);
            throw new IllegalStateException("配置文件加载失败，请检查 xml/spring.xml 与 config/*.ini", e);
        } finally {
            GStatic.gSys.logShowAndRecordGuide(startTime, "GConfig");
            GFile.writeStringToGuideBottom(GMissionMsg.getStepEnd());
        }
    }
}
