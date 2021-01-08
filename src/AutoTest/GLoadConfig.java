package AutoTest;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 *  家在所有配置文件
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
     *  SUT配置项
     */
    @Autowired
    private GSutConfig sutConfig;

    /**
     *  系统配置项
     */
    public static String strDragonShow;
    public static String strTestInputType;
	public static String strTestInputSource;
	public static String strTestInputBeginRowIndex;
	public static String strTestInputBeginColumnIndex;
	public static String strIsLoggedInputs;
	public static String strIsCheckOnly;
	public static String strIsAutoCheckReport;
	public static String strWelcomeStr;
	public static String strTestCaseType;
	public static int dLoopCourt = 0;
	public static int dTimeWait = 0;
	public static String strIsBackup;
	public static String strServerConnType;
    public static String strServerUrl;
    public static String strServerWWW;
    public static String strServerIp;
    public static String strServerPort;
    public static String strServerName;
    public static String strJKS_PATH;
    public static String strJKS_PWD;
    public static String strCommunicationUserALIAS;
    public static String strCommunicationOrgID;
    public static String strCommunicationUserID;
    public static String strCommunicationImg;
    public static String strCommunicationSeal;
    
    /**
     *  数据库配置项
     */
    public static String driverClassName;
    public static String url;
    public static String username;
    public static String pwd;
    public static String validationQuery;
    public static String connectTimeoutAndReadTimeout;
    public static String initialSize;
    public static String minIdle;
    public static String maxIdle;
    public static String maxActive;
    public static String maxWait;
    public static String defaultAutoCommit;
    public static String removeAbandoned;
    public static String removeAbandonedTimeout;
    public static String testWhileIdle;
    public static String timeBetweenEvictionRunsMillis;
    public static String minEvictableIdleTimeMillis;
    public static String numTestsPerEvictionRun;
    public static String testOnReturn;
    public static String testOnBorrow;
    
    /**
     *  邮件服务器配置项
     */
    public static String ismailon;
    public static String protocol;
    public static String host;
    public static String suffix;
    public static String account;
    public static String password;
    public static String port;
    public static String auth;
    public static String tls;
    public static String debug;
    
    /**
     *  被测件配置项
     */
    public static String sysName;
    public static String sectionName1;
    public static String sectionName2;
    public static String sectionName3;
    public static String sid;
    
    /**
     *  读取配置文件中各项的值
     */
    @PostConstruct
    public void init() {
    	strDragonShow = sysConfig.getDragonShow();
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
        
        ismailon = smtpConfig.getIsMailOn();
        protocol = smtpConfig.getProtocol();
        host = smtpConfig.getHost();
        suffix = smtpConfig.getSuffix();
        account = smtpConfig.getAccount();
        password = smtpConfig.getPassword();
        port = smtpConfig.getPort();
        auth = smtpConfig.getAuth();
        tls = smtpConfig.getTls();
        debug = smtpConfig.getDebug();
        
        sysName = sutConfig.getSysName();
        sectionName1 = sutConfig.getSectionName1();
        sectionName2 = sutConfig.getSectionName2();
        sectionName3 = sutConfig.getSectionName3();
        sid = sutConfig.getSid();
    }

    /**
     *  将配置文件中各项的值赋给全局变量
     */
    public static void loadConfig() {
        ApplicationContext appContext = null;
        try {
            appContext = new FileSystemXmlApplicationContext("./xml/spring.xml");
    		
    		if((!strWelcomeStr.equals("")) && (!strDragonShow.equals("")) && (!strTestInputType.equals("")) && (!strTestInputSource.equals("")) 
    				&& (!strTestInputBeginRowIndex.equals("")) && (!strTestInputBeginColumnIndex.equals("")) && (!strIsLoggedInputs.equals("")) 
    				&& (dLoopCourt >= 1) && (dTimeWait >= 0) && (!strIsBackup.equals("")) 
    				&& (!strServerConnType.equals("")) && (!strIsCheckOnly.equals(""))
    				&& (!strIsAutoCheckReport.equals(""))){
    			GParam.gVersion = strWelcomeStr;
    			
    			GParam.INPUT_TYPE = Integer.valueOf(strTestInputType);
    			GParam.INPUT_FROM = Integer.valueOf(strTestInputSource);
    			GParam.INPUT_ROW_FROM = (Integer.valueOf(strTestInputBeginRowIndex)).intValue();
    			GParam.INPUT_COL_FROM = (Integer.valueOf(strTestInputBeginColumnIndex)).intValue();
    			GTestPlan.dRecordInputParamListInTxt = (Integer.valueOf(strIsLoggedInputs)).intValue();
    			if(strIsBackup.equals("true")) {
    				GTestPlan.bTestOutputBackupResult = true;
    			}
    			if(strIsBackup.equals("true")) {
    				GParam.bAutoCheckReport = true;
    			}
    			if(strIsCheckOnly.equals("false")) {
    				GTestMission.TM_CHECK_ONLY = false;
    			}
    			if(strDragonShow.equals("true")) {
    				GParam.gDragonShow = true;
    			}
    			
    		}else {
    			GSys.logErrorSys("One of these sys params from sysConfig may has no value, Please check again!");
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
    			GSys.logErrorSys("One of these database params from dbConfig may has no value, Please check again!");
    			System.exit(0);
    		}
    		
    		if((!ismailon.equals("")) 
    				&& (!protocol.equals("")) 
    				&& (!host.equals("")) 
    				&& (!suffix.equals("")) 
    				&& (!account.equals(""))
    				&& (!password.equals(""))
    				&& (!port.equals(""))
    				&& (!auth.equals(""))
    				&& (!tls.equals(""))
    				&& (!debug.equals(""))){
    			
    			if(ismailon.equals("true")) {
    				GMail.IsMailOn = true;
    			}else{
    				GMail.IsMailOn = false;
    			}
    			GMail.ServerProtocol = protocol;
    			GMail.ServerHost = host;
    			GMail.ServerSuffix = suffix;
    			GMail.ServerAccount = account;
    			GMail.ServerPassword = password;
    			GMail.ServerPort = port;
    			GMail.ServerAuth = auth;
    			GMail.ServerTls = tls;
    			GMail.ServerDebug = debug;
    			
    		}else {
    			GSys.logErrorSys("One of these smtp params from smtpConfig may has no value, Please check again!");
    			System.exit(0);
    		}
    		
    		if((!sysName.equals("")) 
    				&& (!sectionName1.equals("")) 
    				&& (!sectionName2.equals("")) 
    				&& (!sectionName3.equals(""))
    				&& (!sid.equals(""))){
    			GSut.SysName = sysName;
    			GSut.SectionName1 = sectionName1;
    			GSut.SectionName2 = sectionName2;
    			GSut.SectionName3 = sectionName3;
    			GSut.Sid = sid;
    			
    		}else {
    			GSys.logErrorSys("One of these sut params from sutConfig may has no value, Please check again!");
    			System.exit(0);
    		}
        } catch (Exception e) {
            if (appContext != null) {
                ((ConfigurableApplicationContext) appContext).close();
            }
        }
    }

	/**
	 * 	打印参数配置文件加载结果
	 */
    public static void showloadConfig() {
    	int splitNum = 16;
        
		GFile.writeStringToBottom(GSys.GUIDE,GText.getRandomStringByLength(splitNum, "-") + "LOAD SYSTEM CONFIGURAION START" + GText.getRandomStringByLength(splitNum, "-") + "\r\n");
		GFile.writeStringToBottom(GSys.GUIDE,GText.getRandomStringByLength(splitNum, "-") + "SYSTEM CONFIGURAION INFORMATION" + GText.getRandomStringByLength(splitNum, "-") + "\r\n");
	
		GFile.writeStringToBottom(GLog.strLogStyle[8],GText.getRandomStringByLength(splitNum, "-") + "SYSTEM" + GText.getRandomStringByLength(splitNum, "-"));
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 是否开启AI助理");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"DragonShow=" + GLoadConfig.strDragonShow);	
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 输入参数提供方式：0-集合，1-Excel表格，2-txt文本");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"TestInputType=" + GLoadConfig.strTestInputType);	
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 输入参数提供来源：0-工具内置，1-外部输入");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"TestInputSource=" + GLoadConfig.strTestInputSource);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 如果是外部输入参数文件，从第几行开始读取，默认填1，第0行为注释 ");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"TestInputBeginRowIndex=" + GLoadConfig.strTestInputBeginRowIndex);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 如果是外部输入参数文件，从第几列开始读取，默认填0,使用外置文件输入时为6");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"TestInputBeginColumnIndex=" + GLoadConfig.strTestInputBeginColumnIndex);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 是否在加载输入参数成功后单独打印输入参数表：0-否，非0-允许打印的条数");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"IsLoggedInputs=" + GLoadConfig.strIsLoggedInputs);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 测试执行轮数，必须大于0");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"LoopCourt=" + GLoadConfig.dLoopCourt);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 测试用例与用例之间的执行时间间隔");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"TimeWait=" + GLoadConfig.dTimeWait);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 用例类型");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"TestCaseType=" + GLoadConfig.strTestCaseType);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 是否记录缓存文件：设置为true时系统会记录一些中间状态的日志文件，便于排查");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"IsBackup=" + GLoadConfig.strIsBackup);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 通信连接方式 ");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"ServerConnType=" +  GLoadConfig.strServerConnType);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 是否只校验不执行 ");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"CheckOnly=" + GLoadConfig.strIsCheckOnly);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"必填，无默认值 是否在测试完成后自动打开测试报告:目前仅windows系统有效");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"AutoCheckReport=" + GLoadConfig.strIsAutoCheckReport);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，被测件名称及版本号，默认值为“TARGETv1.0.0.0” ");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"WelcomeStr=" + GLoadConfig.strWelcomeStr);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 服务完整地址 ");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"ServerUrl=" + GLoadConfig.strServerUrl);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 服务完整域名");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"ServerWWW=" + GLoadConfig.strServerWWW);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 服务IP");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"ServerIp=" + GLoadConfig.strServerIp);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 服务端口");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"ServerPort=" + GLoadConfig.strServerPort);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 服务名");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"ServerName=" + GLoadConfig.strServerName);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 通信证书路径");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"JKS_PATH=" + GLoadConfig.strJKS_PATH);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 通信证书密码");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"JKS_PWD=" + GLoadConfig.strJKS_PWD);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 通信用户别名");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"CommunicationUserALIAS=" + GLoadConfig.strCommunicationUserALIAS);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 通信机构ID");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"CommunicationOrgID=" + GLoadConfig.strCommunicationOrgID);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 通信用户账号");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"CommunicationUserID=" + GLoadConfig.strCommunicationUserID);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 通信用户证件照片");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"CommunicationImg=" + GLoadConfig.strCommunicationImg);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"选填，无默认值 通信用户签章照片");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"CommunicationSeal=" + GLoadConfig.strCommunicationSeal);
		GFile.writeStringToBottom(GLog.strLogStyle[8],GText.getRandomStringByLength(splitNum, "-") + "SYSTEM" + GText.getRandomStringByLength(splitNum, "-") + "\r\n");
		
		GFile.writeStringToBottom(GLog.strLogStyle[8],GText.getRandomStringByLength(splitNum, "-") + "DB" + GText.getRandomStringByLength(splitNum, "-"));
		GFile.writeStringToBottom(GLog.strLogStyle[8],"数据库驱动类型");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.driverClassName=" + GLoadConfig.driverClassName);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"数据库地址");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.url=" + GLoadConfig.url);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"数据库用户名");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.username=" + GLoadConfig.username);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"数据库密码");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.password=" + GLoadConfig.pwd);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"数据库校验语句");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.validationQuery=" + GLoadConfig.validationQuery);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"数据库连接超时时间");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.connectTimeoutAndReadTimeout=" + GLoadConfig.connectTimeoutAndReadTimeout);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"连接池初始化连接数量");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.initialSize=" + GLoadConfig.initialSize);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"连接池中最小的空闲的连接数");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.minIdle=" + GLoadConfig.minIdle);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"连接池中最大的空闲的连接数");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.maxIdle=" + GLoadConfig.maxIdle);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"连接池中可同时连接的最大的连接数");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.maxActive=" + GLoadConfig.maxActive);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"最大建立连接等待时间,单位为毫秒");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.maxWait=" + GLoadConfig.maxWait);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"是否自动提交");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.defaultAutoCommit=" + GLoadConfig.defaultAutoCommit);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"是否自动回收超时连接");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.removeAbandoned=" + GLoadConfig.removeAbandoned);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"超时时间(以秒数为单位)");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.removeAbandonedTimeout=" + GLoadConfig.removeAbandonedTimeout);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"是否在空闲时间测试连接");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.testWhileIdle=" + GLoadConfig.testWhileIdle);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"一个连接在池中最小生存的时间,单位为毫秒");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.timeBetweenEvictionRunsMillis=" + GLoadConfig.timeBetweenEvictionRunsMillis);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"连接池中连接可空闲的时间,单位为毫秒");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.minEvictableIdleTimeMillis=" + GLoadConfig.minEvictableIdleTimeMillis);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"每次检查链接的数量");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.numTestsPerEvictionRun=" + GLoadConfig.numTestsPerEvictionRun);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"返回对象时是否进行验证");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.testOnReturn=" + GLoadConfig.testOnReturn);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"取出对象时是否进行验证");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"db.testOnBorrow=" + GLoadConfig.testOnBorrow);
		GFile.writeStringToBottom(GLog.strLogStyle[8],GText.getRandomStringByLength(splitNum, "-") + "DB" + GText.getRandomStringByLength(splitNum, "-") + "\r\n");
		
		GFile.writeStringToBottom(GLog.strLogStyle[8],GText.getRandomStringByLength(splitNum, "-") + "SMTP" + GText.getRandomStringByLength(splitNum, "-"));
		GFile.writeStringToBottom(GLog.strLogStyle[8],"协议类型");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"smtp.protocol=" + GLoadConfig.protocol);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"服务地址");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"smtp.host=" + GLoadConfig.host);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"后缀");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"smtp.suffix=" + GLoadConfig.suffix);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"账号");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"smtp.account=" + GLoadConfig.account);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"密码");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"smtp.password=" + GLoadConfig.password);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"端口");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"smtp.port=" + GLoadConfig.port);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"身份校验是否启用");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"smtp.auth=" + GLoadConfig.auth);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"协议版本");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"smtp.tls=" + GLoadConfig.tls);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"是否启用debug模式");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"smtp.debug=" + GLoadConfig.debug);
		GFile.writeStringToBottom(GLog.strLogStyle[8],GText.getRandomStringByLength(splitNum, "-") + "SMTP" + GText.getRandomStringByLength(splitNum, "-") + "\r\n");
		
		GFile.writeStringToBottom(GLog.strLogStyle[8],GText.getRandomStringByLength(splitNum, "-") + "SUT" + GText.getRandomStringByLength(splitNum, "-"));
		GFile.writeStringToBottom(GLog.strLogStyle[8],"系统名称");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"sut.sysName=" + GLoadConfig.sysName);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"配置项名称1");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"sut.sectionName1=" + GLoadConfig.sectionName1);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"配置项名称2");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"sut.sectionName2=" + GLoadConfig.sectionName2);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"配置项名称3");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"sut.sectionName3=" + GLoadConfig.sectionName3);
		GFile.writeStringToBottom(GLog.strLogStyle[8],"数据库服务名称");
		GFile.writeStringToBottom(GLog.strLogStyle[8],"sut.sid=" + GLoadConfig.sid);
		GFile.writeStringToBottom(GLog.strLogStyle[8],GText.getRandomStringByLength(splitNum, "-") + "SUT" + GText.getRandomStringByLength(splitNum, "-") + "\r\n");
		
		GFile.writeStringToBottom(GLog.strLogStyle[8],GText.getRandomStringByLength(splitNum, "-") + "LOCAL" + GText.getRandomStringByLength(splitNum, "-"));
		GFile.writeStringToBottom(GLog.strLogStyle[8],"自定义");
		GFile.writeStringToBottom(GLog.strLogStyle[8],GText.getRandomStringByLength(splitNum, "-") + "LOCAL" + GText.getRandomStringByLength(splitNum, "-") + "\r\n");
		

		GFile.writeStringToBottom(GSys.GUIDE,GText.getRandomStringByLength(splitNum, "-") + "LOAD SYSTEM CONFIGURAION END" + GText.getRandomStringByLength(splitNum, "-") + "\r\n");	
    }
    
	/**
	 * 	配置文件读取结果调试文档
	 */
    public static void main(String[] args) {
		ApplicationContext appContext = null;
		try {
			appContext = new FileSystemXmlApplicationContext("./xml/spring.xml");
		} catch (Exception e) {
			if (appContext != null) {
				((ConfigurableApplicationContext) appContext).close();
			}
			 e.getStackTrace();
		}
		GTestMission.tmInit();
		GTestMission.tmLogOn();
		if(GTestPlan.bTestOutputBackupResult){
			showloadConfig();
			GSys.PROGRESS_CUR = 100;
		}
		GLog.logOff();
    }
}
