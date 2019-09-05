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
	private GConfig(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  配置文件主体内容
	 */
	public static ApplicationContext applicationContext;
	
	/**
	 *  配置文件属性
	 */
	private static Properties property;
	
	/**
	 *  测试输入获取方式
	 *  0-ObJect[][]集合；
	 *  1-Excel表格；
	 *  2-Txt文本 。
	 */
	public static String strTestInputType = "";
	
	/**
	 *  测试输入来源：
	 *  0-工具内置，
	 *  1-外部输入 。
	 */
	public static String strTestInputSource = "";
	
	/**
	 *  测试输入获取位置：如果是外部输入参数文件，从第几行开始读取
	 *  通常情况下，第1行为字段名称，不构成参数表，配置文件中该值为1， 如果有若干行注释文字，则配置文件中改为从第几行开始即可
	 *  后续构造的参数表为String[][]类型，默认迭代器有为0的值，为保证参数表中保存的全部为有效用例参数，则形式上认为配置文件第1行的【行号】为“0”，则真正的参数读取开始的【行号】为“[index+1][]”
	 */
	public static String strTestInputBeginRowIndex = "";
	
	/**
	 *  测试输入获取位置：如果是外部输入参数文件，从第几列开始读取
	 *  根据目前输入文件的字段：序号,系统模块,功能点,用例说明,前置条件,步骤描述,测试环境类型,用例类型,用例编号,用户名,证件类型,证件号码
	 *  通常情况下，前6项为说明文本，用于填充输出报告，不构成参数表，配置文件中该值为5， 如果有若干行说明文本，则配置文件中改为从第几列开始即可
	 *  后续构造的参数表为String[][]类型，默认迭代器有为0的值，为保证参数表中保存的全部为有效用例参数，则形式上认为配置文件第1列的【列号】为“0”，则真正的参数读取开始的【列号】为“[][index+6]”
	 */
	public static String strTestInputBeginColumnIndex = "";
	
	/**
	 *  是否在加载输入参数成功后单独打印输入参数表：0-否，非0-允许打印的条数
	 */
	public static String strIsLoggedInputs = "";
	
	/**
	 *  是否只校验不执行
	 */
	public static String strCheckOnly = "";
	
	/**
	 *  是否在测试完成后自动打开测试报告
	 */
	public static String strAutoCheckReport = "";
	
	/**
	 *  被测件名称及版本号
	 */
	public static String strWelcomeStr = "";
	
	/**
	 *  测试输入集合名称：当用例输入使用内置方式时，系统可能内置了很多集合，此参数可以作为选择标记
	 */
	public static String strTestCaseType = "";
	
	/**
	 *  测试执行轮数，必须大于0
	 */
	public static int dLoopCourt = 0;
	
	/**
	 *  测试用例执行时间间隔
	 */
	public static int dTimeWait = 0;
	
	/**
	 *  是否打包测试结果
	 */
	public static String strIsBackup = "";
	
	/**
	 *  服务连接方式
	 */
	public static String strServerConnType = "";
	
	/**
	 *  服务端口
	 */
	public static String strServerPort = "";

	/**
	 *  初始化配置文件
	 *  
	 *  @param appContext 参数集
	 */
	public static void init() {
		property = (Properties) applicationContext.getBean("property");
		
		//被测件名称及版本号
		strWelcomeStr = (String) property.get("WelcomeStr");
		//输入参数提供方式：0-Object[][]，1-Excel，2-txt
		strTestInputType = (String) property.get("TestInputType");
		//输入参数提供来源：0-内置，1-外置 
		strTestInputSource = (String) property.get("TestInputSource");
		//输入参数开始位置-行：默认为0 
		strTestInputBeginRowIndex = (String) property.get("TestInputBeginRowIndex");
		//输入参数开始位置-列：默认为0 
		strTestInputBeginColumnIndex = (String) property.get("TestInputBeginColumnIndex");
		//是否在加载输入参数成功后单独打印输入参数表：0-否，非0-允许打印的条数,开启后参数表加载较慢
		strIsLoggedInputs = (String) property.get("IsLoggedInputs");
		//测试执行轮数 
		dLoopCourt = (Integer.valueOf((String) property.get("LoopCourt"))).intValue();;
		//测试用例执行时间间隔 
		dTimeWait = (Integer.valueOf((String) property.get("TimeWait"))).intValue();
		//是否记录缓存文件：设置为true时系统会记录一些中间状态的日志文件，便于排查
		strIsBackup = (String) property.get("IsBackup");
		//通信连接方式 
		strServerConnType = (String) property.get("ServerConnType");
		//是否只校验不执行
		strCheckOnly = (String) property.get("CheckOnly");
		//是否在测试完成后自动打开测试报告
		strAutoCheckReport = (String) property.get("AutoCheckReport");
		
		if((!strWelcomeStr.equals("")) && (!strTestInputType.equals("")) && (!strTestInputSource.equals("")) 
				&& (!strTestInputBeginRowIndex.equals("")) && (!strTestInputBeginColumnIndex.equals("")) && (!strIsLoggedInputs.equals("")) 
				&& (dLoopCourt >= 1) && (dTimeWait >= 0) && (!strIsBackup.equals("")) 
				&& (!strServerConnType.equals("")) && (!strCheckOnly.equals(""))
				&& (!strAutoCheckReport.equals(""))){
			GParam.strTestVersion = strWelcomeStr;
			GTestCase.dTestInputType = Integer.valueOf(strTestInputType);
			GTestCase.dTestInputSource = Integer.valueOf(strTestInputSource);
			GTestCase.dTestInputBeginRowIndex = (Integer.valueOf(strTestInputBeginRowIndex)).intValue();
			GTestCase.dTestInputBeginColumnIndex = (Integer.valueOf(strTestInputBeginColumnIndex)).intValue();
			GParam.dRecordInputParamListInTxt = (Integer.valueOf(strIsLoggedInputs)).intValue();
			if(strIsBackup.equals("true")) {
				GParam.bTestOutputBackupResult = true;
			}
			if(strIsBackup.equals("true")) {
				GResult.bAutoCheckReport = true;
			}
			if(strCheckOnly.equals("false")) {
				GTestCase.bTestCheckOnly = false;
			}
		}else {
			GSys.logErrorSys("One of these system params from config may has no value, Please check again!");
			System.exit(0);
		}
		
		GTransfer.gServerUrl[0] = (String) property.get("ServerUrl");
		GTransfer.gServerWWW[0] = (String) property.get("ServerWWW");
		GTransfer.gServerIp[0] = (String) property.get("ServerIp");
		String innerPort = (String) property.get("ServerPort");
		if(innerPort == null || innerPort.equals("")) {
			GTransfer.gServerPort[0] = 80;
		}else {
			GTransfer.gServerPort[0] = (Integer.valueOf(innerPort)).intValue();
		}
		GTransfer.gServerName = (String) property.get("ServerName");
		GTransfer.gKeyStorePath = (String) property.get("JKS_PATH");
		GTransfer.gKeyStorePW = (String) property.get("JKS_PWD");
		GTransfer.gTrustStorePath = (String) property.get("JKS_PATH");
		GTransfer.gTrustStorePW = (String) property.get("JKS_PWD");
		GTransfer.gCommunicationUserALIAS = (String) property.get("CommunicationUserALIAS");
		GTransfer.gServerConnType = (Integer.valueOf(strServerConnType)).intValue();
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
	public static void loadConfig() {
		try {
			String configLocation = "config";
			System.setProperty(SystemConst.CONFIG_LOCATION, configLocation);

			String springConfigFile = CommonUtil.getConfigPath() + SystemConst.SPRING_CONFIG_FILE;
			applicationContext = new FileSystemXmlApplicationContext(springConfigFile);
			GConfig.init();
		}catch(Exception e){
			GSys.logErrorSys(GMsg.MSG_IOFAILED[0]);
			e.printStackTrace();
			System.exit(0);
		}
	}
}
