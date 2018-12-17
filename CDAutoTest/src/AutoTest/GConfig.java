package AutoTest;

import java.util.Properties;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import Plugins.CommonUtil;
import Plugins.SystemConst;

public class GConfig {
	public static ApplicationContext applicationContext;
	private static Properties property;
	
	public static String TestInputType ="";
	public static String TestInputSource = "";
	public static String CheckOnly ="";
	public static String ServerPort ="";
	public static String WelcomeStr ="";
	public static String TestCaseType ="";
	public static int LoopCourt = 0;
	public static String IsBackup = "";
	public static String ServerConnType ="";
	public static String JKS_PATH ="";
	public static String JKS_PWD ="";

	public static void init(ApplicationContext appContext) throws Exception {
		applicationContext = appContext;
		property = (Properties) applicationContext.getBean("property");
		
		TestInputType = (String) property.get("TestInputType");
		if(TestInputType.equals("")) {
			System.out.println("TestInputType has no value");
			new Scanner(System.in);
			System.exit(0);
		}else {
			GTestCase.TestInputType = Integer.valueOf(TestInputType);
		}
		
		TestInputSource = (String) property.get("TestInputSource");
		if(TestInputSource.equals("")) {
			System.out.println("TestInputSource has no value");
			new Scanner(System.in);
			System.exit(0);
		}else {
			GTestCase.TestInputSource = Integer.valueOf(TestInputSource);
		}
		
		CheckOnly = (String) property.get("CheckOnly");
		if(CheckOnly.equals("")) {
			System.out.println("CheckOnly has no value");
			new Scanner(System.in);
			System.exit(0);
		}else {
			if(CheckOnly.equals("false")) {
				GTestCase.TestCheckOnly = false;
			}
		}
		
		IsBackup = (String) property.get("IsBackup");
		if(IsBackup.equals("")) {
			System.out.println("IsBackup has no value");
			new Scanner(System.in);
			System.exit(0);
		}else {
			if(CheckOnly.equals("true")) {
				GLog.IsBackup = true;
			}
		}
		
		WelcomeStr = (String) property.get("WelcomeStr");
		if(WelcomeStr.equals("")) {
			System.out.println("WelcomeStr has no value");
			new Scanner(System.in);
			System.exit(0);
		}
		
		TestCaseType = (String) property.get("TestCaseType");
		if(TestCaseType.equals("")) {
			System.out.println("TestCaseType has no value");
			new Scanner(System.in);
			System.exit(0);
		}
		
		LoopCourt =  Integer.valueOf((String) property.get("LoopCourt"));
		if(LoopCourt < 1) {
			System.out.println("LoopCourt has no value");
			new Scanner(System.in);
			System.exit(0);
		}
		
		ServerConnType = (String) property.get("ServerConnType");
		if(ServerConnType.equals("")) {
			System.out.println("ServerConnType has no value");
			new Scanner(System.in);
			System.exit(0);
		}
		
		ServerPort = (String) property.get("ServerPort");
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

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public static Properties getProperty() {
		return property;
	}
	
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
