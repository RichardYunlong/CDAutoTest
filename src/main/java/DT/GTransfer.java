package main.java.DT;

import main.java.Base.GClazz;
import main.java.Base.GFile;

/**
 *  通信管理
 */
public class GTransfer {
	
    /**
     *  构造函数
     */
	public GTransfer(){
		GClazz.thisADataUnitClass();
	}
	
	/**
	 *  运行环境国家代码
	 */
	private String locale = "";
	public String getLocale() { return locale; }
	public void setLocale(String locale) { this.locale = locale; }
	
	/**
	 *  报文编码
	 */
	private String characterSet = "";
	public String getCharacterSet() {
		return characterSet;
	}
	public void setCharacterSet(String characterSet) {
		this.characterSet = characterSet;
	}
	
	/**
	 *  网段
	 */
	private String networkSegment = "";
	public String getNetworkSegment() {
		return networkSegment;
	}
	public void setNetworkSegment(String networkSegment) {
		this.networkSegment = networkSegment;
	}
	
	/**
	 *  报告服务端口
	 */
	private String reportPort = "";
	public String getReportPort() {
		return reportPort;
	}
	public void setReportPort(String reportPort) {
		this.reportPort = reportPort;
	}

	/**
	 *  服务ip和端口组合全url,String[]类型，最大支持存储4个
	 */
	private String[] gServerUrl = {"","","",""};
	public String[] getgServerUrl() {
		return gServerUrl;
	}
	public void setgServerUrl(String[] gServerUrl) {
		this.gServerUrl = gServerUrl.clone();
	}
	
	/**
	 *  服务域名全url,String[]类型，最大支持存储4个
	 */
	private String[] gServerWWW = {"","","",""};
	public String[] getgServerWWW() {
		return gServerWWW;
	}
	public void setgServerWWW(String[] gServerWWW) {
		this.gServerWWW = gServerWWW.clone();
	}

	/**
	 *  服务ip,String[]类型，请使用gServerIp[0]作为变量，gServerIp[1]、gServerIp[2]微系统预留
	 */
	private String[] gServerIp = {"","localhost","127.0.0.1"};// 服务器IP
	public String[] getgServerIp() {
		return gServerIp;
	}
	public void setgServerIp(String[] gServerIp) {
		this.gServerIp = gServerIp.clone();
	}

	/**
	 *  服务端口,int[]类型，请使用gServerPort[0]作为变量，gServerPort[1]、gServerPort[2]微系统预留
	 */
	private int[] gServerPort = {0,80,443};
	public int[] getgServerPort() {
		return gServerPort;
	}
	public void setgServerPort(int[] gServerPort) {
		this.gServerPort = gServerPort.clone();
	}

	/**
	 *  服务名
	 */	
	private String serverName = "";
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 *  通信证书路径全名
	 */	
	private String keyStorePath = "";
	public String getKeyStorePath() {
		return keyStorePath;
	}
	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}
	
	/**
	 *  通信证书密码
	 */	
	private String keyStorePW = "";
	public String getKeyStorePW() {
		return keyStorePW;
	}
	public void setKeyStorePW(String keyStorePW) {
		this.keyStorePW = keyStorePW;
	}
	
	/**
	 *  信任证书链路劲全名
	 */	
	private String trustStorePath = "";
	public String getTrustStorePath() {
		return trustStorePath;
	}
	public void setTrustStorePath(String trustStorePath) {
		this.trustStorePath = trustStorePath;
	}
	
	/**
	 *  信任证书链密码
	 */	
	private String trustStorePW = "";
	public String getTrustStorePW() {
		return trustStorePW;
	}
	public void setTrustStorePW(String trustStorePW) {
		this.trustStorePW = trustStorePW;
	}

	/**
	 *  通信证书的别名
	 */	
	private String communicationUserALIAS = "";
	public String getCommunicationUserALIAS() {
		return communicationUserALIAS;
	}
	public void setCommunicationUserALIAS(String communicationUserALIAS) { this.communicationUserALIAS = communicationUserALIAS; }
	
	/**
	 *  服务器连接方式
	 */	
	private int serverConnType = 0;
	public int getServerConnType() {
		return serverConnType;
	}
	public void setServerConnType(int serverConnType) {
		this.serverConnType = serverConnType;
	}
	
	/**
	 *  是否连接CS服务器,测试脚本结构时使用
	 */
	private boolean bIsConnCSServer = false;
	public boolean isbIsConnCSServer() {
		return bIsConnCSServer;
	}
	public void setbIsConnCSServer(boolean bIsConnCSServer) {
		this.bIsConnCSServer = bIsConnCSServer;
	}

	/**
	 *  访问超时时间限制，单位ms
	 */
	private int connectTimeout = 5000;
	public int getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	
	/**
	 *  读超时时间限制，单位ms
	 */
	private int readTimeout = 3000;
	public int getReadTimeout() {
		return readTimeout;
	}
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
	/**
	 *  访问者为企业法人时，访问者ID或其他唯一标识
	 */	
	private String communicationOrgID = "";
	public String getCommunicationOrgID() {
		return communicationOrgID;
	}
	public void setCommunicationOrgID(String communicationOrgID) { this.communicationOrgID = communicationOrgID; }
	
	/**
	 *  访问者为自然人时，访问者ID或其他唯一标识
	 */
	private String communicationUserID = "";
	public String getCommunicationUserID() {
		return communicationUserID;
	}
	public void setCommunicationUserID(String communicationUserID) { this.communicationUserID = communicationUserID; }
	
	/**
	 *  访问者身份图形证明，例如营业执照扫描件、身份证照片、头像等
	 */
	private String communicationImg = "";
	public String getCommunicationImg() {
		return communicationImg;
	}
	public void setCommunicationImg(String communicationImg) {
		this.communicationImg = communicationImg;
	}
	
	/**
	 *  访问者身份编码证明，例如签名ID等
	 */
	private String communicationSeal = "";
	public String getCommunicationSeal() {
		return communicationSeal;
	}
	public void setCommunicationSeal(String communicationSeal) {
		this.communicationSeal = communicationSeal;
	}

	/**
	 *  重置连接参数
	 *  
	 *  @param gEnvironment 环境标识
	 */
	public void reDefault(int gEnvironment) {
		switch (gEnvironment) {
		case 0: {
			this.locale = "CN";
			this.characterSet = "UTF-8";
			this.keyStorePath = "";
			this.keyStorePW = "";
			this.trustStorePath = "";
			this.trustStorePW = "";
			this.serverName = "";
			this.serverConnType = 0;
			this.communicationOrgID = "";
			this.communicationUserID = "";
			this.communicationImg = "";
			this.communicationSeal = "";
			this.communicationUserALIAS = "";
			for(int i=0;i<4;i++) {
				this.gServerUrl[i] = "";
				this.gServerWWW[i] = "";
			}
			this.gServerIp[0] = "";
			this.gServerIp[1] = "localhost";
			this.gServerIp[2] = "127.0.0.1";
			this.gServerPort[0] = 0;
			this.gServerPort[1] = 80;
			this.gServerPort[2] = 443;
			this.connectTimeout = 5000;
			this.readTimeout = 3000;
			break;
		}
		case 1: {
			break;
		}
		case 2:
        default:
			break;
		}
	}
	
	/**
	 *  加载参数
	 */	
	public void loadConfig() {
		if(this.serverConnType >= 0 && this.gServerUrl != null){
			GFile.writeStringToGuideBottom("GTransfer Params Loaded");
		}else {
			GFile.writeStringErrorToGuideBottom("One of these GTransfer params from sysConfig may has no value, Please check again!");
			System.exit(0);
		}
	}
}
