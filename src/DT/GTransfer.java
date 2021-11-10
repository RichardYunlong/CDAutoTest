package DT;

import Base.GClazz;
import Sys.GSys;

/**
 *  通信管理
 */
public class GTransfer {
	
    /**
     *  构造函数
     */
	private GTransfer(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  运行环境国家代码
	 */
	private static String locale = "";
	
	/**
	 *  报文编码
	 */
	private static String characterSet = "";
	
	/**
	 *  网段
	 */
	private static String networkSegment = "";
	
	public static String getNetworkSegment() {
		return networkSegment;
	}

	public static void setNetworkSegment(String networkSegment) {
		GTransfer.networkSegment = networkSegment;
	}
	
	/**
	 *  报告服务端口
	 */
	private static String reportPort = "";
	
	public static String getReportPort() {
		return reportPort;
	}

	public static void setReportPort(String reportPort) {
		GTransfer.reportPort = reportPort;
	}

	/**
	 *  服务ip和端口组合全url,String[]类型，最大支持存储4个
	 */
	private static String[] gServerUrl = {"","","",""};
	
	/**
	 *  服务域名全url,String[]类型，最大支持存储4个
	 */
	private static String[] gServerWWW = {"","","",""};
	
	/**
	 *  服务ip,String[]类型，请使用gServerIp[0]作为变量，gServerIp[1]、gServerIp[2]微系统预留
	 */
	private static String[] gServerIp = {"","localhost","127.0.0.1"};// 服务器IP
	
	/**
	 *  服务端口,int[]类型，请使用gServerPort[0]作为变量，gServerPort[1]、gServerPort[2]微系统预留
	 */
	private static int[] gServerPort = {0,80,443};
	
	public static String[] getgServerUrl() {
		return gServerUrl;
	}

	public static void setgServerUrl(String[] gServerUrl) {
		GTransfer.gServerUrl = gServerUrl.clone();
	}

	public static String[] getgServerWWW() {
		return gServerWWW;
	}

	public static void setgServerWWW(String[] gServerWWW) {
		GTransfer.gServerWWW = gServerWWW.clone();
	}

	public static String[] getgServerIp() {
		return gServerIp;
	}

	public static void setgServerIp(String[] gServerIp) {
		GTransfer.gServerIp = gServerIp.clone();
	}

	public static int[] getgServerPort() {
		return gServerPort;
	}

	public static void setgServerPort(int[] gServerPort) {
		GTransfer.gServerPort = gServerPort.clone();
	}

	/**
	 *  服务名
	 */	
	private static String serverName = "";
	
	/**
	 *  通信证书路径全名
	 */	
	private static String keyStorePath = "";
	
	/**
	 *  通信证书密码
	 */	
	private static String keyStorePW = ""; 
	
	/**
	 *  信任证书链路劲全名
	 */	
	private static String trustStorePath = "";
	
	/**
	 *  信任证书链密码
	 */	
	private static String trustStorePW = ""; 
	
	/**
	 *  通信证书的别名
	 */	
	private static String communicationUserALIAS = "";
	
	/**
	 *  服务器连接方式
	 */	
	private static int serverConnType = 0;
	
	/**
	 *  是否连接CS服务器,测试脚本结构时使用
	 */
	private static boolean bIsConnCSServer = false;
	
	/**
	 *  访问超时时间限制，单位ms
	 */
	private static int connectTimeout = 5000;
	
	/**
	 *  读超时时间限制，单位ms
	 */
	private static int readTimeout = 3000;
	
	/**
	 *  访问者为企业法人时，访问者ID或其他唯一标识
	 */	
	private static String communicationOrgID = "";
	
	/**
	 *  访问者为自然人时，访问者ID或其他唯一标识
	 */
	private static String communicationUserID = "";
	
	/**
	 *  访问者身份图形证明，例如营业执照扫描件、身份证照片、头像等
	 */
	private static String communicationImg = "";
	
	/**
	 *  访问者身份编码证明，例如签名ID等
	 */
	private static String communicationSeal = "";

	/**
	 *  重置连接参数
	 *  
	 *  @param gEnvironment 环境标识
	 */
	public static void reDefault(int gEnvironment) {
		switch (gEnvironment) {
		case 0: {
			locale = "CN";
			characterSet = "UTF-8";
			keyStorePath = "";
			keyStorePW = "";
			trustStorePath = "";
			trustStorePW = "";
			serverName = "";
			serverConnType = 0;
			communicationOrgID = "";
			communicationUserID = "";
			communicationImg = "";
			communicationSeal = "";
			communicationUserALIAS = "";
			for(int i=0;i<4;i++) {
				gServerUrl[i] = "";
				gServerWWW[i] = "";
			}
			gServerIp[0] = "";
			gServerIp[1] = "localhost";
			gServerIp[2] = "127.0.0.1";
			gServerPort[0] = 0;
			gServerPort[1] = 80;
			gServerPort[2] = 443;
			connectTimeout = 5000;
			readTimeout = 3000;
			break;
		}
		case 1: {
			break;
		}
		case 2: {
			break;
		}
		default:
			break;
		}
	}

	public static String getLocale() {
		return locale;
	}

	public static void setLocale(String locale) {
		GTransfer.locale = locale;
	}

	public static String getCharacterSet() {
		return characterSet;
	}

	public static void setCharacterSet(String characterSet) {
		GTransfer.characterSet = characterSet;
	}

	public static String getServerName() {
		return serverName;
	}

	public static void setServerName(String serverName) {
		GTransfer.serverName = serverName;
	}

	public static String getKeyStorePath() {
		return keyStorePath;
	}

	public static void setKeyStorePath(String keyStorePath) {
		GTransfer.keyStorePath = keyStorePath;
	}

	public static String getKeyStorePW() {
		return keyStorePW;
	}

	public static void setKeyStorePW(String keyStorePW) {
		GTransfer.keyStorePW = keyStorePW;
	}

	public static String getTrustStorePath() {
		return trustStorePath;
	}

	public static void setTrustStorePath(String trustStorePath) {
		GTransfer.trustStorePath = trustStorePath;
	}

	public static String getTrustStorePW() {
		return trustStorePW;
	}

	public static void setTrustStorePW(String trustStorePW) {
		GTransfer.trustStorePW = trustStorePW;
	}

	public static String getCommunicationUserALIAS() {
		return communicationUserALIAS;
	}

	public static void setCommunicationUserALIAS(String communicationUserALIAS) {
		GTransfer.communicationUserALIAS = communicationUserALIAS;
	}

	public static int getServerConnType() {
		return serverConnType;
	}

	public static void setServerConnType(int serverConnType) {
		GTransfer.serverConnType = serverConnType;
	}

	public static boolean isbIsConnCSServer() {
		return bIsConnCSServer;
	}

	public static void setbIsConnCSServer(boolean bIsConnCSServer) {
		GTransfer.bIsConnCSServer = bIsConnCSServer;
	}

	public static int getConnectTimeout() {
		return connectTimeout;
	}

	public static void setConnectTimeout(int connectTimeout) {
		GTransfer.connectTimeout = connectTimeout;
	}

	public static int getReadTimeout() {
		return readTimeout;
	}

	public static void setReadTimeout(int readTimeout) {
		GTransfer.readTimeout = readTimeout;
	}

	public static String getCommunicationOrgID() {
		return communicationOrgID;
	}

	public static void setCommunicationOrgID(String communicationOrgID) {
		GTransfer.communicationOrgID = communicationOrgID;
	}

	public static String getCommunicationUserID() {
		return communicationUserID;
	}

	public static void setCommunicationUserID(String communicationUserID) {
		GTransfer.communicationUserID = communicationUserID;
	}

	public static String getCommunicationImg() {
		return communicationImg;
	}

	public static void setCommunicationImg(String communicationImg) {
		GTransfer.communicationImg = communicationImg;
	}

	public static String getCommunicationSeal() {
		return communicationSeal;
	}

	public static void setCommunicationSeal(String communicationSeal) {
		GTransfer.communicationSeal = communicationSeal;
	}
	
	/**
	 *  加载参数
	 */	
	public static void loadConfig() {
		if(serverConnType >= 0 && gServerUrl != null){
			;
		}else {
			GSys.logErrorSys("One of these GTransfer params from sysConfig may has no value, Please check again!");
			System.exit(0);
		}
	}
}
