package AutoTest;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import Plugins.CommonUtil;
import Plugins.SystemConst;

/**
 *  配置文件驱动
 */
public class GConfig {
	/**
	 *  配置文件主体内容
	 */
	public static ApplicationContext applicationContext;
	
	/**
	 *  配置文件属性
	 */
	private static Properties property;
	
	/**
	 *  测试输入来源：外部或内置
	 */
	public static String TestInputSource = "";
	
	/**
	 *  测试输入获取方式：外部支持Excel、TXT，内置支持ObJect[][]
	 */
	public static String TestInputType ="";
	
	/**
	 *  是否在加载输入参数成功后单独打印输入参数表：0-否，非0-允许打印的条数
	 */
	public static String IsLoggedInputs ="";
	
	/**
	 *  是否只校验不执行
	 */
	public static String CheckOnly ="";
	
	/**
	 *  服务端口
	 */
	public static String ServerPort ="";
	
	/**
	 *  问候语
	 */
	public static String WelcomeStr ="";
	
	/**
	 *  测试输入集合名称：当用例输入使用内置方式时，系统可能内置了很多集合，此参数可以作为选择标记
	 */
	public static String TestCaseType ="";
	
	/**
	 *  测试执行轮数
	 */
	public static int LoopCourt = 0;
	
	/**
	 *  是否打包测试结果
	 */
	public static String IsBackup = "";
	
	/**
	 *  服务连接方式
	 */
	public static String ServerConnType ="";
	
	/**
	 *  通信证书全名
	 */
	public static String JKS_PATH ="";
	
	/**
	 *  通信证书密码
	 */
	public static String JKS_PWD ="";

	/**
	 *  初始化配置文件
	 */
	public static void init(ApplicationContext appContext) throws Exception {
		applicationContext = appContext;
		property = (Properties) applicationContext.getBean("property");
		
		TestInputType = (String) property.get("TestInputType");
		if(TestInputType.equals("")) {
			System.out.println("TestInputType has no value");
			System.exit(0);
		}else {
			GTestCase.TestInputType = Integer.valueOf(TestInputType);
		}
		
		TestInputSource = (String) property.get("TestInputSource");
		if(TestInputSource.equals("")) {
			System.out.println("TestInputSource has no value");
			System.exit(0);
		}else {
			GTestCase.TestInputSource = Integer.valueOf(TestInputSource);
		}
		
		IsLoggedInputs = (String) property.get("IsLoggedInputs");
		if(IsLoggedInputs.equals("")) {
			System.out.println("IsLoggedInputs has no value");
			System.exit(0);
		}else {
			GParam.isRecordInputParamListInTxt = (Integer.valueOf(IsLoggedInputs)).intValue();
		}
		
		CheckOnly = (String) property.get("CheckOnly");
		if(CheckOnly.equals("")) {
			System.out.println("CheckOnly has no value");
			System.exit(0);
		}else {
			if(CheckOnly.equals("false")) {
				GTestCase.TestCheckOnly = false;
			}else{
				GTestCase.TestCheckOnly = true;
			}
		}
		
		IsBackup = (String) property.get("IsBackup");
		if(IsBackup.equals("")) {
			System.out.println("IsBackup has no value");
			System.exit(0);
		}else {
			if(IsBackup.equals("true")) {
				GParam.TestOutputBackupResult = true;
			}else {
				GParam.TestOutputBackupResult = false;
			}
		}
		
		WelcomeStr = (String) property.get("WelcomeStr");
		if(WelcomeStr.equals("")) {
			System.out.println("WelcomeStr has no value");
			System.exit(0);
		}
		
		TestCaseType = (String) property.get("TestCaseType");
		if(TestCaseType.equals("")) {
			System.out.println("TestCaseType has no value");
			System.exit(0);
		}
		
		LoopCourt =  Integer.valueOf((String) property.get("LoopCourt"));
		if(LoopCourt < 1) {
			System.out.println("LoopCourt has no value");
			System.exit(0);
		}
		
		ServerConnType = (String) property.get("ServerConnType");
		if(ServerConnType.equals("")) {
			System.out.println("ServerConnType has no value");
			System.exit(0);
		}
		
		ServerPort = (String) property.get("ServerPort");
		if(ServerPort == null || ServerPort == "") {ServerPort="8080";}
		JKS_PATH = (String) property.get("JKS_PATH");
		JKS_PWD = (String) property.get("JKS_PWD");
		
		GTransfer.gServerUrl[0] = (String) property.get("ServerUrl");
		GTransfer.gServerWWW[0] = (String) property.get("ServerWWW");
		GTransfer.gServerIp[0] = (String) property.get("ServerIp");	
		GTransfer.gServerPort[0] = (Integer.valueOf(ServerPort)).intValue();
		GTransfer.gServerName = (String) property.get("ServerName");
		GTransfer.gKeyStorePath = JKS_PATH;
		GTransfer.gKeyStorePassword = JKS_PWD;
		GTransfer.gTrustStorePath = JKS_PATH;
		GTransfer.gTrustStorePassword = JKS_PWD;
		GTransfer.gCommunicationUserALIAS = (String) property.get("CommunicationUserALIAS");
		GTransfer.gServerConnType = (Integer.valueOf(ServerConnType)).intValue();
		GTransfer.gCommunicationOrgID = (String) property.get("CommunicationOrgID");
		GTransfer.gCommunicationUserID = (String) property.get("CommunicationUserID");
		GTransfer.gCommunicationImg = (String) property.get("CommunicationImg");
		GTransfer.gCommunicationSeal = (String) property.get("CommunicationSeal");
		
	}

	/**
	 *  获得配置文件主体内容
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 *  获得配置文件处理类
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 *  获得配置文件属性
	 */
	public static Properties getProperty() {
		return property;
	}
	
	/**
	 *  配置文件驱动构造
	 */
	public GConfig() {
		try {
			String configLocation = "config";
			System.setProperty(SystemConst.CONFIG_LOCATION, configLocation);

			String springConfigFile = CommonUtil.getConfigPath() + SystemConst.SPRING_CONFIG_FILE;
			ApplicationContext appContext = new FileSystemXmlApplicationContext(springConfigFile);
			GConfig.init(appContext);
			//CommonUtil.welcome();
		}catch(Throwable e){
			CommonUtil.dealException(e, "初始化异常");
		}
	}
}
