package AutoTest;

import java.lang.reflect.Field;

/**
 *  通信管理
 */
public class GTransfer {
	
	/**
	 *  服务ip和端口组合全url,String[]类型，最大支持存储4个
	 */
	public static String[] gServerUrl = {"","","",""};
	
	/**
	 *  服务域名全url,String[]类型，最大支持存储4个
	 */
	public static String[] gServerWWW = {"","","",""};
	
	/**
	 *  服务ip,String[]类型，请使用gServerIp[0]作为变量，gServerIp[1]、gServerIp[2]微系统预留
	 */
	public static String[] gServerIp = {"","localhost","127.0.0.1"};// 服务器IP
	
	/**
	 *  服务端口,int[]类型，请使用gServerPort[0]作为变量，gServerPort[1]、gServerPort[2]微系统预留
	 */
	public static int[] gServerPort = {0,80,443};
	
	/**
	 *  服务名
	 */	
	public static String gServerName = "";
	
	/**
	 *  通信证书路径全名
	 */	
	public static String gKeyStorePath = "";
	
	/**
	 *  通信证书密码
	 */	
	public static String gKeyStorePassword = ""; 
	
	/**
	 *  信任证书链路劲全名
	 */	
	public static String gTrustStorePath = "";
	
	/**
	 *  信任证书链密码
	 */	
	public static String gTrustStorePassword = ""; 
	
	/**
	 *  通信证书的别名
	 */	
	public static String gCommunicationUserALIAS = "";
	
	/**
	 *  服务器连接方式
	 */	
	public static int gServerConnType = 0;
	
	/**
	 *  访问者为企业法人时，访问者ID或其他唯一标识
	 */	
	public static String gCommunicationOrgID = "";
	
	/**
	 *  访问者为自然人时，访问者ID或其他唯一标识
	 */
	public static String gCommunicationUserID = "";
	
	/**
	 *  访问者身份图形证明，例如营业执照扫描件、身份证照片、头像等
	 */
	public static String gCommunicationImg = "";
	
	/**
	 *  访问者身份编码证明，例如签名ID等
	 */
	public static String gCommunicationSeal = "";
	
	/**
	 *  显示类成员变量
	 */
	public void showClassParams() {
		try {
			GTransfer aInstance = new GTransfer();
			Field[] fields = aInstance.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				System.out.print("成员变量" + i + "类型 : " + fields[i].getType().getName());
				System.out.print("\t成员变量" + i + "变量名: " + fields[i].getName() + "\t");
				System.out.println("成员变量" + i + "值: " + fields[i].get(aInstance));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  重置连接参数
	 */
	public static void reSetGTransfer(int gEnvironment) {
		switch (gEnvironment) {
		case 0: {
			gKeyStorePath = "";
			gKeyStorePassword = "";
			gTrustStorePath = "";
			gTrustStorePassword = "";
			gServerName = "";
			gServerConnType = 0;
			gCommunicationOrgID = "";
			gCommunicationUserID = "";
			gCommunicationImg = "";
			gCommunicationSeal = "";
			gCommunicationUserALIAS = "";
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
}
