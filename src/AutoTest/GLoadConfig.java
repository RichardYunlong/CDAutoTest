package AutoTest;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class GLoadConfig {
    @Autowired
    private GSysConfig sysConfig;
    @Autowired
    private GDBConfig dbConfig;

    public static String strTestInputType = "";
	public static String strTestInputSource = "";
	public static String strTestInputBeginRowIndex = "";
	public static String strTestInputBeginColumnIndex = "";
	public static String strIsLoggedInputs = "";
	public static String strIsCheckOnly = "";
	public static String strIsAutoCheckReport = "";
	public static String strWelcomeStr = "";
	public static String strTestCaseType = "";
	public static int dLoopCourt = 0;
	public static int dTimeWait = 0;
	public static String strIsBackup = "";
	public static String strServerConnType = "";
    public static String strServerUrl = "";
    public static String strServerWWW = "";
    public static String strServerIp = "";
    public static String strServerPort = "";
    public static String strServerName = "";
    public static String strJKS_PATH = "";
    public static String strJKS_PWD;
    public static String strCommunicationUserALIAS = "";
    public static String strCommunicationOrgID = "";
    public static String strCommunicationUserID = "";
    public static String strCommunicationImg = "";
    public static String strCommunicationSeal = "";
    
    public static String driverClassName = "";
    public static String url = "";
    public static String username = "";
    public static String pwd;
    public static String validationQuery = "";
    public static String connectTimeoutAndReadTimeout = "";
    public static String initialSize = "";
    public static String minIdle = "";
    public static String maxIdle = "";
    public static String maxActive = "";
    public static String maxWait = "";
    public static String defaultAutoCommit = "";
    public static String removeAbandoned = "";
    public static String removeAbandonedTimeout = "";
    public static String testWhileIdle = "";
    public static String timeBetweenEvictionRunsMillis = "";
    public static String minEvictableIdleTimeMillis = "";
    public static String numTestsPerEvictionRun = "";
    public static String testOnReturn = "";
    public static String testOnBorrow = "";
    
    private static final Logger LOG = LoggerFactory.getLogger(GLoadConfig.class);
    
    @PostConstruct
    public void init() {
        strTestInputType = sysConfig.getTestInputType();
    	strTestInputSource = sysConfig.getTestInputSource();
    	strTestInputBeginRowIndex = sysConfig.getTestInputBeginRowIndex();
    	strTestInputBeginColumnIndex = sysConfig.getTestInputBeginColumnIndex();
    	strIsLoggedInputs = sysConfig.getIsLoggedInputs();
    	strIsCheckOnly = sysConfig.getIsCheckOnly();
    	strIsAutoCheckReport = sysConfig.getIsAutoCheckReport();
    	strWelcomeStr = sysConfig.getWelcomeStr();
    	strTestCaseType = sysConfig.getTestCaseType();
    	dLoopCourt = sysConfig.getLoopCourt();
    	dTimeWait = sysConfig.getTimeWait();
    	strIsBackup = sysConfig.getIsBackup();
    	strServerConnType = sysConfig.getServerConnType();
        strServerUrl = sysConfig.getServerUrl();
        strServerWWW = sysConfig.getServerWWW();
        strServerIp = sysConfig.getServerIp();
        strServerPort = sysConfig.getServerPort();
        strServerName = sysConfig.getServerName();
        strJKS_PATH = sysConfig.getJKS_PATH();
        strJKS_PWD = sysConfig.getJKS_PWD();
        strCommunicationUserALIAS = sysConfig.getCommunicationUserALIAS();
        strCommunicationOrgID = sysConfig.getCommunicationOrgID();
        strCommunicationUserID = sysConfig.getCommunicationUserID();
        strCommunicationImg = sysConfig.getCommunicationImg();
        strCommunicationSeal = sysConfig.getCommunicationSeal();
        
        driverClassName = dbConfig.getDriverClassName();
        url = dbConfig.getUrl();
        username = dbConfig.getUsername();
        pwd = dbConfig.getPassword();
        validationQuery = dbConfig.getValidationQuery();
        connectTimeoutAndReadTimeout = dbConfig.getConnectTimeoutAndReadTimeout();
        initialSize = dbConfig.getInitialSize();
        minIdle = dbConfig.getMinIdle();
        maxIdle = dbConfig.getMaxIdle();
        maxActive = dbConfig.getMaxActive();
        maxWait = dbConfig.getMaxWait();
        defaultAutoCommit = dbConfig.getDefaultAutoCommit();
        removeAbandoned = dbConfig.getRemoveAbandoned();
        removeAbandonedTimeout = dbConfig.getRemoveAbandonedTimeout();
        testWhileIdle = dbConfig.getTestWhileIdle();
        timeBetweenEvictionRunsMillis = dbConfig.getTimeBetweenEvictionRunsMillis();
        minEvictableIdleTimeMillis = dbConfig.getMinEvictableIdleTimeMillis();
        numTestsPerEvictionRun = dbConfig.getNumTestsPerEvictionRun();
        testOnReturn = dbConfig.getTestOnReturn();
        testOnBorrow = dbConfig.getTestOnBorrow();
    }

    public static void loadConfig() {
        ApplicationContext appContext = null;
        try {
            appContext = new FileSystemXmlApplicationContext("./config/spring.xml");
    		
    		if((!strWelcomeStr.equals("")) && (!strTestInputType.equals("")) && (!strTestInputSource.equals("")) 
    				&& (!strTestInputBeginRowIndex.equals("")) && (!strTestInputBeginColumnIndex.equals("")) && (!strIsLoggedInputs.equals("")) 
    				&& (dLoopCourt >= 1) && (dTimeWait >= 0) && (!strIsBackup.equals("")) 
    				&& (!strServerConnType.equals("")) && (!strIsCheckOnly.equals(""))
    				&& (!strIsAutoCheckReport.equals(""))){
    			GParam.strTestVersion = strWelcomeStr;
    			GTestCase.dTestInputType = Integer.valueOf(strTestInputType);
    			GTestCase.dTestInputSource = Integer.valueOf(strTestInputSource);
    			GTestCase.dTestInputBeginRowIndex = (Integer.valueOf(strTestInputBeginRowIndex)).intValue();
    			GTestCase.dTestInputBeginColumnIndex = (Integer.valueOf(strTestInputBeginColumnIndex)).intValue();
    			GTestPlan.dRecordInputParamListInTxt = (Integer.valueOf(strIsLoggedInputs)).intValue();
    			if(strIsBackup.equals("true")) {
    				GTestPlan.bTestOutputBackupResult = true;
    			}
    			if(strIsBackup.equals("true")) {
    				GResult.bAutoCheckReport = true;
    			}
    			if(strIsCheckOnly.equals("false")) {
    				GTestCase.bTestCheckOnly = false;
    			}
    		}else {
    			GSys.logErrorSys("One of these system params from sysconfig may has no value, Please check again!");
    			System.exit(0);
    		}
    		
    		GTransfer.gServerUrl[0] = strServerUrl;
    		GTransfer.gServerWWW[0] = strServerWWW;
    		GTransfer.gServerIp[0] = strServerIp;
    		String innerPort = strServerPort;
    		if(innerPort == null || innerPort.equals("")) {
    			GTransfer.gServerPort[0] = 80;
    		}else {
    			GTransfer.gServerPort[0] = (Integer.valueOf(innerPort)).intValue();
    		}
    		GTransfer.gServerName = strServerName;
    		GTransfer.gKeyStorePath = strJKS_PATH;
    		GTransfer.gKeyStorePW = strJKS_PWD;
    		GTransfer.gTrustStorePath = strJKS_PATH;
    		GTransfer.gTrustStorePW = strJKS_PWD;
    		GTransfer.gCommunicationUserALIAS = strCommunicationUserALIAS;
    		GTransfer.gServerConnType = (Integer.valueOf(strServerConnType)).intValue();
    		GTransfer.gCommunicationOrgID = strCommunicationOrgID;
    		GTransfer.gCommunicationUserID = strCommunicationUserID;
    		GTransfer.gCommunicationImg = strCommunicationImg;
    		GTransfer.gCommunicationSeal = strCommunicationSeal;
    		
    		if((!driverClassName.equals("")) && (!url.equals("")) && (!username.equals("")) && (!pwd.equals(""))){
    			GDBConnector.driverClassName = driverClassName;
    			GDBConnector.url = url;
    			GDBConnector.username = username;
    			GDBConnector.pwd = pwd;
    		}else {
    			GSys.logErrorSys("One of these database params from dbconfig may has no value, Please check again!");
    			System.exit(0);
    		}
            
        } catch (Exception e) {
            if (appContext != null) {
                ((ConfigurableApplicationContext) appContext).close();
            }
            LOG.error("error", e);
        }
    }
    
//    public static void showSysConfigInfo() {
//		System.out.println("\r\nSYSTEM CONFIGURAION INFORMATION\r\n");
//		
//		System.out.println("-------------DRIVER------------");
//		System.out.println("必填，无默认值 输入参数提供方式：0-集合，1-Excel表格，2-txt文本");
//		System.out.println("TestInputType=" + GLoadConfig.strTestInputType);	
//		System.out.println("必填，无默认值 输入参数提供来源：0-工具内置，1-外部输入");
//		System.out.println("TestInputSource=" + GLoadConfig.strTestInputSource);
//		System.out.println("必填，无默认值 如果是外部输入参数文件，从第几行开始读取，默认填1，第0行为注释 ");
//		System.out.println("TestInputBeginRowIndex=" + GLoadConfig.strTestInputBeginRowIndex);
//		System.out.println("必填，无默认值 如果是外部输入参数文件，从第几列开始读取，默认填0,使用外置文件输入时为6");
//		System.out.println("TestInputBeginColumnIndex=" + GLoadConfig.strTestInputBeginColumnIndex);
//		System.out.println("必填，无默认值 是否在加载输入参数成功后单独打印输入参数表：0-否，非0-允许打印的条数");
//		System.out.println("IsLoggedInputs=" + GLoadConfig.strIsLoggedInputs);
//		System.out.println("必填，无默认值 测试执行轮数，必须大于0");
//		System.out.println("LoopCourt=" + GLoadConfig.dLoopCourt);
//		System.out.println("必填，无默认值 测试用例与用例之间的执行时间间隔");
//		System.out.println("TimeWait=" + GLoadConfig.dTimeWait);
//		System.out.println("必填，无默认值 用例类型");
//		System.out.println("TestCaseType=" + GLoadConfig.strTestCaseType);
//		System.out.println("必填，无默认值 是否记录缓存文件：设置为true时系统会记录一些中间状态的日志文件，便于排查");
//		System.out.println("IsBackup=" + GLoadConfig.strIsBackup);
//		System.out.println("必填，无默认值 通信连接方式 ");
//		System.out.println("ServerConnType=" +  GLoadConfig.strServerConnType);
//		System.out.println("必填，无默认值 是否只校验不执行 ");
//		System.out.println("CheckOnly=" + GLoadConfig.strIsCheckOnly);
//		System.out.println("必填，无默认值 是否在测试完成后自动打开测试报告:目前仅windows系统有效");
//		System.out.println("AutoCheckReport=" + GLoadConfig.strIsAutoCheckReport);
//		System.out.println("选填，被测件名称及版本号，默认值为“TARGETv1.0.0.0” ");
//		System.out.println("WelcomeStr=" + GLoadConfig.strWelcomeStr);
//		System.out.println("选填，无默认值 服务完整地址 ");
//		System.out.println("ServerUrl=" + GLoadConfig.strServerUrl);
//		System.out.println("选填，无默认值 服务完整域名");
//		System.out.println("ServerWWW=" + GLoadConfig.strServerWWW);
//		System.out.println("选填，无默认值 服务IP");
//		System.out.println("ServerIp=" + GLoadConfig.strServerIp);
//		System.out.println("选填，无默认值 服务端口");
//		System.out.println("ServerPort=" + GLoadConfig.strServerPort);
//		System.out.println("选填，无默认值 服务名");
//		System.out.println("ServerName=" + GLoadConfig.strServerName);
//		System.out.println("选填，无默认值 通信证书路径");
//		System.out.println("JKS_PATH=" + GLoadConfig.strJKS_PATH);
//		System.out.println("选填，无默认值 通信证书密码");
//		System.out.println("JKS_PWD=" + GLoadConfig.strJKS_PWD);
//		System.out.println("选填，无默认值 通信用户别名");
//		System.out.println("CommunicationUserALIAS=" + GLoadConfig.strCommunicationUserALIAS);
//		System.out.println("选填，无默认值 通信机构ID");
//		System.out.println("CommunicationOrgID=" + GLoadConfig.strCommunicationOrgID);
//		System.out.println("选填，无默认值 通信用户账号");
//		System.out.println("CommunicationUserID=" + GLoadConfig.strCommunicationUserID);
//		System.out.println("选填，无默认值 通信用户证件照片");
//		System.out.println("CommunicationImg=" + GLoadConfig.strCommunicationImg);
//		System.out.println("选填，无默认值 通信用户签章照片");
//		System.out.println("CommunicationSeal=" + GLoadConfig.strCommunicationSeal + "\r\n");
//		
//		System.out.println("---------------DB--------------\r\n");
//		System.out.println("驱动信息");
//		System.out.println("db.driverClassName=" + GLoadConfig.driverClassName);
//		System.out.println("db.url=" + GLoadConfig.url);
//		System.out.println("db.username=" + GLoadConfig.username);
//		System.out.println("db.password=" + GLoadConfig.password);
//		System.out.println("db.validationQuery=" + GLoadConfig.validationQuery);
//		System.out.println("db.connectTimeoutAndReadTimeout=" + GLoadConfig.connectTimeoutAndReadTimeout);
//		System.out.println("连接池初始化连接数量");
//		System.out.println("db.initialSize=" + GLoadConfig.initialSize);
//		System.out.println("连接池中最小的空闲的连接数");
//		System.out.println("db.minIdle=" + GLoadConfig.minIdle);
//		System.out.println("连接池中最大的空闲的连接数");
//		System.out.println("db.maxIdle=" + GLoadConfig.maxIdle);
//		System.out.println("连接池中可同时连接的最大的连接数");
//		System.out.println("db.maxActive=" + GLoadConfig.maxActive);
//		System.out.println("最大建立连接等待时间,单位为毫秒");
//		System.out.println("db.maxWait=" + GLoadConfig.maxWait);
//		System.out.println("是否自动提交");
//		System.out.println("db.defaultAutoCommit=" + GLoadConfig.defaultAutoCommit);
//		System.out.println("是否自动回收超时连接");
//		System.out.println("db.removeAbandoned=" + GLoadConfig.removeAbandoned);
//		System.out.println("超时时间(以秒数为单位)");
//		System.out.println("db.removeAbandonedTimeout=" + GLoadConfig.removeAbandonedTimeout);
//		System.out.println("是否在空闲时间测试连接");
//		System.out.println("db.testWhileIdle=" + GLoadConfig.testWhileIdle);
//		System.out.println("一个连接在池中最小生存的时间,单位为毫秒");
//		System.out.println("db.timeBetweenEvictionRunsMillis=" + GLoadConfig.timeBetweenEvictionRunsMillis);
//		System.out.println("连接池中连接可空闲的时间,单位为毫秒");
//		System.out.println("db.minEvictableIdleTimeMillis=" + GLoadConfig.minEvictableIdleTimeMillis);
//		System.out.println("每次检查链接的数量");
//		System.out.println("db.numTestsPerEvictionRun=" + GLoadConfig.numTestsPerEvictionRun);
//		System.out.println("返回对象时是否进行验证");
//		System.out.println("db.testOnReturn=" + GLoadConfig.testOnReturn);
//		System.out.println("取出对象时是否进行验证");
//		System.out.println("db.testOnBorrow=" + GLoadConfig.testOnBorrow + "\r\n");
//		
//		System.out.println("--------------LOCAL------------\r\n");
//    }
//    
//    public static void main(String[] args) {
//        ApplicationContext appContext = null;
//        try {
//            appContext = new FileSystemXmlApplicationContext("./config/spring.xml");
//        } catch (Exception e) {
//            if (appContext != null) {
//                ((ConfigurableApplicationContext) appContext).close();
//            }
//            LOG.error("error", e);
//        }
//        
//        showSysConfigInfo();
//    }
}
