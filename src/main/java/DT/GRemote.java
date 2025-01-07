package main.java.DT;

import main.java.Base.GClazz;
import main.java.Base.GFile;
import main.java.Base.GShell;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

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
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private Map<String, Map<String, String>> AliveRemoteList = new HashMap<>();
	
	/**
	 *  失效子服务器列表
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private Map<String, Map<String, String>> DeadRemoteList = new HashMap<>();
	
	public Map<String, Map<String, String>> getAliveRemoteList() {
		return AliveRemoteList;
	}

	public Map<String, Map<String, String>> getDeadRemoteList() {
		return DeadRemoteList;
	}
	
	/**
	 *  有效remote序号
	 */
	private int AliveRemoteIndex = 0;
	
	/**
	 *  无效remote序号
	 */
	private int DeadRemoteIndex = 0;
	
	/**
	 *  首次心跳检测
	 *
	 *  @param strIp 目标ip
	 *  @param strAccount 账号
	 *  @param strPassword 密码
	 */
	public void checkRemote(String strIp, String strAccount, String strPassword) {
		Map<String, String> Remote = new HashMap<>();
		boolean bRemote = GShell.runWinCmdBool(strIp, strAccount, strPassword, "ipconfig");
		Remote.put("ip", strIp);
		Remote.put("user", strAccount);
		Remote.put("password", strPassword);
		if(bRemote) {
			GLog.logRecord(8, strIp + " is alive");
			Remote.put("status", "true");
			this.AliveRemoteList.put(String.valueOf(this.AliveRemoteIndex), Remote);
			this.AliveRemoteIndex++;
		}else {
			GLog.logRecord(8, strIp + " is not alive");
			Remote.put("status", "false");
			this.DeadRemoteList.put(String.valueOf(this.DeadRemoteIndex), Remote);
			this.DeadRemoteIndex++;
		}
	}
	
	/**
	 *  心跳
	 */
	public void checkHeartBeat() {
		Map<String, String> Remote;
		String index;
		for(int i=0;i<=this.AliveRemoteIndex;i++) {
			index = String.valueOf(i);
			String ip = this.AliveRemoteList.get(index).get("ip");
			String user = this.AliveRemoteList.get(index).get("user");
			String password = this.AliveRemoteList.get(index).get("password");
			boolean bRemote = GShell.runWinCmdBool(ip, user, password, "ipconfig");
			if(!bRemote) {
				GLog.logRecord(8, this.AliveRemoteList.get(index).get("ip") + " is not alive");
				Remote = new HashMap<>();
				Remote.put("ip", ip);
				Remote.put("user", user);
				Remote.put("password", password);
				Remote.put("status", "false");
				this.AliveRemoteList.replace(String.valueOf(this.AliveRemoteIndex), Remote);
			}
		}
	}
	
	/**
	 *  获得运行所在机器IP
	 *
	 *  @return 目标主机信息
	 */
	public static String getIP() {
		String info = "";
		
        List<String> ipList = new ArrayList<>();
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
			GFile.writeStringErrorToGuideBottom("getIP[" + Arrays.toString(e.getStackTrace()) +"]");
        }

		InetAddress ia;
		try {
			ia = InetAddress.getLocalHost();
			String localName = ia.getHostName();
			StringBuilder localIp = new StringBuilder();

            for (String s : ipList) {
                if (!s.equals("127.0.0.1")) {
                    localIp.append("<").append(s).append(">");
                }
            }
			
			info = "[" + localName  + ":"  + localIp + "]";
		} catch (Exception e) {
			GFile.writeStringErrorToGuideBottom("getIP[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		
		return info;
	}
	
	/**
	 *  获得运行所在机器符合指定特征的IP
	 *  
	 *  @param networkSegment 网段
	 *
	 *  @return 目标主机信息
	 */
	public static String getNetworkIP(String networkSegment) {
		String info = "";
		
        List<String> ipList = new ArrayList<>();
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
			GFile.writeStringErrorToGuideBottom("getNetworkIP[" + Arrays.toString(e.getStackTrace()) +"]");
        }

		try {
            for (String s : ipList) {
                if (ipCheck(s) && !s.equals("127.0.0.1")) {
                    //与网段特征值进行比较
                    String mark = "\\.";
                    String[] networkSegments = networkSegment.split(mark);
                    String[] ipInfos = s.split(mark);

                    if (!networkSegments[1].equals("*")) {
                        if (networkSegments[0].equals(ipInfos[0]) && networkSegments[1].equals(ipInfos[1])) {
                            info = s;
                        }
                    } else {
                        if (networkSegments[0].equals(ipInfos[0])) {
                            info = s;
                        }
                    }
                }
            }
		} catch (Exception e) {
			GFile.writeStringErrorToGuideBottom("getNetworkIP[" + Arrays.toString(e.getStackTrace()) +"]");
		}
		
		return info;
	}
	
  /**
   * 判断IP地址的合法性，这里采用了正则表达式的方法来判断
   *
   * @param text 表达式
   *
   * @return true，合法
   */
  public static boolean ipCheck(String text) {
    if (text != null && !text.isEmpty()) {
      // 定义正则表达式
      String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
           + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
           + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
           + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
      // 判断ip地址是否与正则表达式匹配
        // 返回判断信息
        return text.matches(regex);
    }
    return false;
  }
}
