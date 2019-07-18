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
	public static String TestInputType = "";
	
	/**
	 *  测试输入获取位置：如果是外部输入参数文件，从第几行开始读取
	 */
	public static String TestInputBeginRowIndex = "";
	
	/**
	 *  测试输入获取位置：如果是外部输入参数文件，从第几行开始读取
	 */
	public static String TestInputBeginColumnIndex = "";
	
	/**
	 *  是否在加载输入参数成功后单独打印输入参数表：0-否，非0-允许打印的条数
	 */
	public static String IsLoggedInputs = "";
	
	/**
	 *  是否只校验不执行
	 */
	public static String CheckOnly = "";
	
	/**
	 *  被测件名称及版本号
	 */
	public static String WelcomeStr = "";
	
	/**
	 *  测试输入集合名称：当用例输入使用内置方式时，系统可能内置了很多集合，此参数可以作为选择标记
	 */
	public static String TestCaseType = "";
	
	/**
	 *  测试执行轮数，必须大于0
	 */
	public static int LoopCourt = 0;
	
	/**
	 *  测试用例执行时间间隔
	 */
	public static int TimeWait = 0;
	
	/**
	 *  是否打包测试结果
	 */
	public static String IsBackup = "";
	
	/**
	 *  服务连接方式
	 */
	public static String ServerConnType = "";
	
	/**
	 *  服务端口
	 */
	public static String ServerPort = "80";

	/**
	 *  初始化配置文件
	 *  
	 *  @param appContext 参数集
	 */
	public static void init(ApplicationContext appContext) throws Exception {
		applicationContext = appContext;
		property = (Properties) applicationContext.getBean("property");
		
		//被测件名称及版本号
		WelcomeStr = (String) property.get("WelcomeStr");
		//输入参数提供方式：0-Object[][]，1-Excel，2-txt
		TestInputType = (String) property.get("TestInputType");
		//输入参数提供来源：0-内置，1-外置 
		TestInputSource = (String) property.get("TestInputSource");
		//输入参数开始位置-行：默认为0 
		TestInputBeginRowIndex = (String) property.get("TestInputBeginRowIndex");
		//输入参数开始位置-列：默认为0 
		TestInputBeginColumnIndex = (String) property.get("TestInputBeginColumnIndex");
		//是否在加载输入参数成功后单独打印输入参数表：0-否，非0-允许打印的条数,开启后参数表加载较慢
		IsLoggedInputs = (String) property.get("IsLoggedInputs");
		//测试执行轮数 
		LoopCourt =  Integer.valueOf((String) property.get("LoopCourt"));
		//测试用例执行时间间隔 
		TimeWait =  Integer.valueOf((String) property.get("TimeWait"));
		//是否记录缓存文件：设置为true时系统会记录一些中间状态的日志文件，便于排查
		IsBackup = (String) property.get("IsBackup");
		//通信连接方式 
		ServerConnType = (String) property.get("ServerConnType");
		//是否只校验不执行
		CheckOnly = (String) property.get("CheckOnly");
		
		if((!WelcomeStr.equals("")) && (!TestInputType.equals("")) && (!TestInputSource.equals("")) 
				&& (!TestInputBeginRowIndex.equals("")) && (!TestInputBeginColumnIndex.equals("")) && (!IsLoggedInputs.equals("")) 
				&& (LoopCourt >= 1) && (TimeWait >= 0) && (!IsBackup.equals("")) 
				&& (!ServerConnType.equals("")) && (!CheckOnly.equals(""))) {
			GParam.TestVersion = WelcomeStr;
			GTestCase.TestInputType = Integer.valueOf(TestInputType);
			GTestCase.TestInputSource = Integer.valueOf(TestInputSource);
			GTestCase.TestInputBeginRowIndex = (Integer.valueOf(TestInputBeginRowIndex)).intValue();
			GTestCase.TestInputBeginColumnIndex = (Integer.valueOf(TestInputBeginColumnIndex)).intValue();
			GParam.isRecordInputParamListInTxt = (Integer.valueOf(IsLoggedInputs)).intValue();
			if(IsBackup.equals("true")) {
				GParam.TestOutputBackupResult = true;
			}
			if(CheckOnly.equals("false")) {
				GTestCase.TestCheckOnly = false;
			}
		}else {
			GFile.WriteStringToBottom(GSys.Guide, "One of these system params may has no value, Please check again!");
			System.exit(0);
		}
		
		GTransfer.gServerUrl[0] = (String) property.get("ServerUrl");
		GTransfer.gServerWWW[0] = (String) property.get("ServerWWW");
		GTransfer.gServerIp[0] = (String) property.get("ServerIp");
		GTransfer.gServerPort[0] = (Integer.valueOf(ServerPort)).intValue();
		GTransfer.gServerName = (String) property.get("ServerName");
		GTransfer.gKeyStorePath = (String) property.get("JKS_PATH");
		GTransfer.gKeyStorePW = (String) property.get("JKS_PWD");
		GTransfer.gTrustStorePath = (String) property.get("JKS_PATH");
		GTransfer.gTrustStorePW = (String) property.get("JKS_PWD");
		GTransfer.gCommunicationUserALIAS = (String) property.get("CommunicationUserALIAS");
		GTransfer.gServerConnType = (Integer.valueOf(ServerConnType)).intValue();
		GTransfer.gCommunicationOrgID = (String) property.get("CommunicationOrgID");
		GTransfer.gCommunicationUserID = (String) property.get("CommunicationUserID");
		GTransfer.gCommunicationImg = (String) property.get("CommunicationImg");
		GTransfer.gCommunicationSeal = (String) property.get("CommunicationSeal");
	}

	/**
	 *  获得配置文件主体内容
	 *  
	 *  @return 返回参数集
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 *  获得配置文件处理类
	 *  
	 *  @param name 参数集名称
	 *  @return 参数集类
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 *  获得配置文件属性
	 *  
	 *  @return 属性值
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
