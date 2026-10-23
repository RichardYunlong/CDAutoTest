package DT;

import Base.GClazz;
import Base.GFile;

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
	/**
	 *  获得运行环境国家代码
	 *
	 *  @return 运行环境国家代码
	 */
	public String getLocale() { return locale; }
	/**
	 *  设置运行环境国家代码
	 *
	 *  @param locale 运行环境国家代码
	 */
	public void setLocale(String locale) { this.locale = locale; }
	
	/**
	 *  报文编码
	 */
	private String characterSet = "";
	/**
	 *  获得报文编码
	 *
	 *  @return 报文编码
	 */
	public String getCharacterSet() {
		return characterSet;
	}
	/**
	 *  设置报文编码
	 *
	 *  @param characterSet 报文编码
	 */
	public void setCharacterSet(String characterSet) {
		this.characterSet = characterSet;
	}
	
	/**
	 *  网段
	 */
	private String networkSegment = "";
	/**
	 *  获得网段
	 *
	 *  @return 网段
	 */
	public String getNetworkSegment() {
		return networkSegment;
	}
	/**
	 *  设置网段
	 *
	 *  @param networkSegment 网段
	 */
	public void setNetworkSegment(String networkSegment) {
		this.networkSegment = networkSegment;
	}
	
	/**
	 *  报告服务端口
	 */
	private String reportPort = "";
	/**
	 *  获得报告服务端口
	 *
	 *  @return 报告服务端口
	 */
	public String getReportPort() {
		return reportPort;
	}
	/**
	 *  设置报告服务端口
	 *
	 *  @param reportPort 报告服务端口
	 */
	public void setReportPort(String reportPort) {
		this.reportPort = reportPort;
	}

	/**
	 *  服务ip和端口组合全url,String[]类型，最大支持存储4个
	 */
	private String[] gServerUrl = {"","","",""};
	/**
	 *  获得服务ip和端口组合全url,String[]类型，最大支持存储4个
	 *
	 *  @return 服务ip和端口组合全url,String[]类型，最大支持存储4个
	 */
	public String[] getgServerUrl() {
		return gServerUrl;
	}
	/**
	 *  设置服务全 url 数组
	 *
	 *  <p>内部保存副本，避免调用方后续修改数组影响本对象。</p>
	 *
	 *  @param gServerUrl 服务全 url 数组
	 */
	public void setgServerUrl(String[] gServerUrl) {
		this.gServerUrl = gServerUrl.clone();
	}
	
	/**
	 *  服务域名全url,String[]类型，最大支持存储4个
	 */
	private String[] gServerWWW = {"","","",""};
	/**
	 *  获得服务域名全url,String[]类型，最大支持存储4个
	 *
	 *  @return 服务域名全url,String[]类型，最大支持存储4个
	 */
	public String[] getgServerWWW() {
		return gServerWWW;
	}
	/**
	 *  设置服务域名全 url 数组
	 *
	 *  <p>内部保存副本，避免调用方后续修改数组影响本对象。</p>
	 *
	 *  @param gServerWWW 服务域名全 url 数组，最多 4 个
	 */
	public void setgServerWWW(String[] gServerWWW) {
		this.gServerWWW = gServerWWW.clone();
	}

	/**
	 *  服务ip,String[]类型，请使用gServerIp[0]作为变量，gServerIp[1]、gServerIp[2]微系统预留
	 */
	private String[] gServerIp = {"","localhost","127.0.0.1"};// 服务器IP
	/**
	 *  获得服务ip,String[]类型，请使用gServerIp[0]作为变量，gServerIp[1]、gServerIp[2]微系统预留
	 *
	 *  @return 服务ip,String[]类型，请使用gServerIp[0]作为变量，gServerIp[1]、gServerIp[2]微系统预留
	 */
	public String[] getgServerIp() {
		return gServerIp;
	}
	/**
	 *  设置服务 IP 数组
	 *
	 *  <p>内部保存副本，避免调用方后续修改数组影响本对象。</p>
	 *
	 *  @param gServerIp 服务 IP 数组，请使用下标 0 作为当前值
	 */
	public void setgServerIp(String[] gServerIp) {
		this.gServerIp = gServerIp.clone();
	}

	/**
	 *  服务端口,int[]类型，请使用gServerPort[0]作为变量，gServerPort[1]、gServerPort[2]微系统预留
	 */
	private int[] gServerPort = {0,80,443};
	/**
	 *  获得服务端口,int[]类型，请使用gServerPort[0]作为变量，gServerPort[1]、gServerPort[2]微系统预留
	 *
	 *  @return 服务端口,int[]类型，请使用gServerPort[0]作为变量，gServerPort[1]、gServerPort[2]微系统预留
	 */
	public int[] getgServerPort() {
		return gServerPort;
	}
	/**
	 *  设置服务端口数组
	 *
	 *  <p>内部保存副本，避免调用方后续修改数组影响本对象。</p>
	 *
	 *  @param gServerPort 服务端口数组，请使用下标 0 作为当前值
	 */
	public void setgServerPort(int[] gServerPort) {
		this.gServerPort = gServerPort.clone();
	}

	/**
	 *  服务名
	 */	
	private String serverName = "";
	/**
	 *  获得服务名
	 *
	 *  @return 服务名
	 */
	public String getServerName() {
		return serverName;
	}
	/**
	 *  设置服务名
	 *
	 *  @param serverName 服务名
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 *  通信证书路径全名
	 */	
	private String keyStorePath = "";
	/**
	 *  获得通信证书路径全名
	 *
	 *  @return 通信证书路径全名
	 */
	public String getKeyStorePath() {
		return keyStorePath;
	}
	/**
	 *  设置通信证书路径全名
	 *
	 *  @param keyStorePath 通信证书路径全名
	 */
	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}
	
	/**
	 *  通信证书密码
	 */	
	private String keyStorePW = "";
	/**
	 *  获得通信证书密码
	 *
	 *  @return 通信证书密码
	 */
	public String getKeyStorePW() {
		return keyStorePW;
	}
	/**
	 *  设置通信证书密码
	 *
	 *  @param keyStorePW 通信证书密码
	 */
	public void setKeyStorePW(String keyStorePW) {
		this.keyStorePW = keyStorePW;
	}
	
	/**
	 *  信任证书链路劲全名
	 */	
	private String trustStorePath = "";
	/**
	 *  获得信任证书链路劲全名
	 *
	 *  @return 信任证书链路劲全名
	 */
	public String getTrustStorePath() {
		return trustStorePath;
	}
	/**
	 *  设置信任证书链路劲全名
	 *
	 *  @param trustStorePath 信任证书链路劲全名
	 */
	public void setTrustStorePath(String trustStorePath) {
		this.trustStorePath = trustStorePath;
	}
	
	/**
	 *  信任证书链密码
	 */	
	private String trustStorePW = "";
	/**
	 *  获得信任证书链密码
	 *
	 *  @return 信任证书链密码
	 */
	public String getTrustStorePW() {
		return trustStorePW;
	}
	/**
	 *  设置信任证书链密码
	 *
	 *  @param trustStorePW 信任证书链密码
	 */
	public void setTrustStorePW(String trustStorePW) {
		this.trustStorePW = trustStorePW;
	}

	/**
	 *  通信证书的别名
	 */	
	private String communicationUserALIAS = "";
	/**
	 *  获得通信证书的别名
	 *
	 *  @return 通信证书的别名
	 */
	public String getCommunicationUserALIAS() {
		return communicationUserALIAS;
	}
	/**
	 *  设置通信证书的别名
	 *
	 *  @param communicationUserALIAS 通信证书的别名
	 */
	public void setCommunicationUserALIAS(String communicationUserALIAS) { this.communicationUserALIAS = communicationUserALIAS; }
	
	/**
	 *  服务器连接方式
	 */	
	private int serverConnType = 0;
	/**
	 *  获得服务器连接方式
	 *
	 *  @return 服务器连接方式
	 */
	public int getServerConnType() {
		return serverConnType;
	}
	/**
	 *  设置服务器连接方式
	 *
	 *  @param serverConnType 服务器连接方式
	 */
	public void setServerConnType(int serverConnType) {
		this.serverConnType = serverConnType;
	}
	
	/**
	 *  是否连接CS服务器,测试脚本结构时使用
	 */
	private boolean bIsConnCSServer = false;
	/**
	 *  获得是否连接CS服务器,测试脚本结构时使用
	 *
	 *  @return 是否连接CS服务器,测试脚本结构时使用
	 */
	public boolean isbIsConnCSServer() {
		return bIsConnCSServer;
	}
	/**
	 *  设置是否连接CS服务器,测试脚本结构时使用
	 *
	 *  @param bIsConnCSServer 是否连接CS服务器,测试脚本结构时使用
	 */
	public void setbIsConnCSServer(boolean bIsConnCSServer) {
		this.bIsConnCSServer = bIsConnCSServer;
	}

	/**
	 *  访问超时时间限制，单位ms
	 */
	private int connectTimeout = 5000;
	/**
	 *  获得访问超时时间限制，单位ms
	 *
	 *  @return 访问超时时间限制，单位ms
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}
	/**
	 *  设置访问超时时间限制，单位ms
	 *
	 *  @param connectTimeout 访问超时时间限制，单位ms
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	
	/**
	 *  读超时时间限制，单位ms
	 */
	private int readTimeout = 3000;
	/**
	 *  获得读超时时间限制，单位ms
	 *
	 *  @return 读超时时间限制，单位ms
	 */
	public int getReadTimeout() {
		return readTimeout;
	}
	/**
	 *  设置读超时时间限制，单位ms
	 *
	 *  @param readTimeout 读超时时间限制，单位ms
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
	/**
	 *  访问者为企业法人时，访问者ID或其他唯一标识
	 */	
	private String communicationOrgID = "";
	/**
	 *  获得访问者为企业法人时，访问者ID或其他唯一标识
	 *
	 *  @return 访问者为企业法人时，访问者ID或其他唯一标识
	 */
	public String getCommunicationOrgID() {
		return communicationOrgID;
	}
	/**
	 *  设置访问者为企业法人时，访问者ID或其他唯一标识
	 *
	 *  @param communicationOrgID 访问者为企业法人时，访问者ID或其他唯一标识
	 */
	public void setCommunicationOrgID(String communicationOrgID) { this.communicationOrgID = communicationOrgID; }
	
	/**
	 *  访问者为自然人时，访问者ID或其他唯一标识
	 */
	private String communicationUserID = "";
	/**
	 *  获得访问者为自然人时，访问者ID或其他唯一标识
	 *
	 *  @return 访问者为自然人时，访问者ID或其他唯一标识
	 */
	public String getCommunicationUserID() {
		return communicationUserID;
	}
	/**
	 *  设置访问者为自然人时，访问者ID或其他唯一标识
	 *
	 *  @param communicationUserID 访问者为自然人时，访问者ID或其他唯一标识
	 */
	public void setCommunicationUserID(String communicationUserID) { this.communicationUserID = communicationUserID; }
	
	/**
	 *  访问者身份图形证明，例如营业执照扫描件、身份证照片、头像等
	 */
	private String communicationImg = "";
	/**
	 *  获得访问者身份图形证明，例如营业执照扫描件、身份证照片、头像等
	 *
	 *  @return 访问者身份图形证明，例如营业执照扫描件、身份证照片、头像等
	 */
	public String getCommunicationImg() {
		return communicationImg;
	}
	/**
	 *  设置访问者身份图形证明，例如营业执照扫描件、身份证照片、头像等
	 *
	 *  @param communicationImg 访问者身份图形证明，例如营业执照扫描件、身份证照片、头像等
	 */
	public void setCommunicationImg(String communicationImg) {
		this.communicationImg = communicationImg;
	}
	
	/**
	 *  访问者身份编码证明，例如签名ID等
	 */
	private String communicationSeal = "";
	/**
	 *  获得访问者身份编码证明，例如签名ID等
	 *
	 *  @return 访问者身份编码证明，例如签名ID等
	 */
	public String getCommunicationSeal() {
		return communicationSeal;
	}
	/**
	 *  设置访问者身份编码证明，例如签名ID等
	 *
	 *  @param communicationSeal 访问者身份编码证明，例如签名ID等
	 */
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
			throw new IllegalStateException("通信配置不完整(serverConnType/gServerUrl)，请检查 sys.ini");
		}
	}
}
