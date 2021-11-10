package DT;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Base.GClazz;
import Base.GShell;
import Sys.GSys;

/**
 *  心跳
 */
public class GRemote {

    /**
     *  构造函数
     */
	private GRemote(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  有效子服务器列表
	 */
	private static Map<String, Map<String, String>> AliveRemoteList = new HashMap<String,Map<String, String>>();
	
	/**
	 *  失效子服务器列表
	 */
	private static Map<String, Map<String, String>> DeadRemoteList = new HashMap<String,Map<String, String>>();
	
	public static Map<String, Map<String, String>> getAliveRemoteList() {
		return AliveRemoteList;
	}

	public static Map<String, Map<String, String>> getDeadRemoteList() {
		return DeadRemoteList;
	}
	
	/**
	 *  有效remote序号
	 */
	private static int AliveRemoteIndex = 0;
	
	/**
	 *  无效remote序号
	 */
	private static int DeadRemoteIndex = 0;
	
	/**
	 *  首次心跳检测
	 */
	public static void checkRemote(String strIp, String strAccount, String strPassword) {
		Map<String, String> Remote = new HashMap<String,String>();
		boolean bRemote = GShell.runWinCmdBool(strIp, strAccount, strPassword, "ipconfig");
		Remote.put("ip", strIp);
		Remote.put("user", strAccount);
		Remote.put("password", strPassword);
		if(bRemote) {
			GLog.logRecord(8, strIp + " is alive");
			Remote.put("status", "true");
			AliveRemoteList.put(String.valueOf(AliveRemoteIndex), Remote);
			AliveRemoteIndex++;
		}else {
			GLog.logRecord(8, strIp + " is not alive");
			Remote.put("status", "false");
			DeadRemoteList.put(String.valueOf(DeadRemoteIndex), Remote);
			DeadRemoteIndex++;
		}
	}
	
	/**
	 *  心跳
	 */
	public static void checkHeartBeat() {
		Map<String, String> Remote = null;
		String index = null;
		for(int i=0;i<=AliveRemoteIndex;i++) {
			index = String.valueOf(i);
			String ip = AliveRemoteList.get(index).get("ip");
			String user = AliveRemoteList.get(index).get("user");
			String password = AliveRemoteList.get(index).get("password");
			boolean bRemote = GShell.runWinCmdBool(ip, user, password, "ipconfig");
			if(!bRemote) {
				GLog.logRecord(8, AliveRemoteList.get(index).get("ip") + " is not alive");
				Remote = new HashMap<String,String>();
				Remote.put("ip", ip);
				Remote.put("user", user);
				Remote.put("password", password);
				Remote.put("status", "false");
				AliveRemoteList.replace(String.valueOf(AliveRemoteIndex), Remote);
			}
		}
	}
	
	/**
	 *  获得运行所在机器IP
	 */
	public static String getIP() {
		String info = "";
		
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
        	GSys.logErrorSys("getIP[" + Arrays.toString(e.getStackTrace()) +"]");
        }

		InetAddress ia=null;
		try {
			ia = InetAddress.getLocalHost();
			String localName = ia.getHostName();
			StringBuilder localIp = new StringBuilder();
			
			for(int i = 0;i < ipList.size();i++) {
				if(!ipList.get(i).equals("127.0.0.1")){
					localIp.append("<" + ipList.get(i) + ">");
				}
			}
			
			info = "[" + localName  + ":"  + localIp.toString() + "]";
		} catch (Exception e) {
			GSys.logErrorSys("getIP[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		
		return info;
	}
	
	/**
	 *  获得运行所在机器符合指定特征的IP
	 *  
	 *  @param networkSegment 网段
	 */
	public static String getNetworkIP(String networkSegment) {
		String info = "";
		
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
        	GSys.logErrorSys("getNetworkIP[" + Arrays.toString(e.getStackTrace()) +"]");
        }

		try {
			for(int i = 0;i < ipList.size();i++) {
				if(ipCheck(ipList.get(i)) && !ipList.get(i).equals("127.0.0.1")){
					//与网段特征值进行比较
					String mark = "\\.";
					String[] networkSegments = networkSegment.split(mark);
					String[] ipInfos = ipList.get(i).split(mark);
					
					if(!networkSegments[1].equals("*")) {
						if(networkSegments[0].equals(ipInfos[0]) && networkSegments[1].equals(ipInfos[1])) {
							info = ipList.get(i);
						}
					}else {
						if(networkSegments[0].equals(ipInfos[0])) {
							info = ipList.get(i);
						}
					}
				}
			}
		} catch (Exception e) {
			GSys.logErrorSys("getNetworkIP[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		
		return info;
	}
	
  /**
   * 判断IP地址的合法性，这里采用了正则表达式的方法来判断
   * return true，合法
   * */
  public static boolean ipCheck(String text) {
    if (text != null && !text.isEmpty()) {
      // 定义正则表达式
      String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
           + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
           + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
           + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
      // 判断ip地址是否与正则表达式匹配
      if (text.matches(regex)) {
        // 返回判断信息
        return true;
      } else {
        // 返回判断信息
        return false;
      }
    }
    return false;
  }
}
