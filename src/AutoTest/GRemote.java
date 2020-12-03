package AutoTest;

import java.util.HashMap;
import java.util.Map;

/**
 *  心跳
 */
public class GRemote {
	
	/**
	 *  有效子服务器列表
	 */
	public static Map<String, Map<String, String>> AliveRemoteList = new HashMap<String,Map<String, String>>();
	
	/**
	 *  失效子服务器列表
	 */
	public static Map<String, Map<String, String>> DeadRemoteList = new HashMap<String,Map<String, String>>();
	
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
			System.out.println(strIp + " is alive");
			Remote.put("status", "true");
			AliveRemoteList.put(String.valueOf(AliveRemoteIndex), Remote);
			AliveRemoteIndex++;
		}else {
			System.out.println(strIp + " is not alive");
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
				System.out.println(AliveRemoteList.get(index).get("ip") + " is not alive");
				Remote = new HashMap<String,String>();
				Remote.put("ip", ip);
				Remote.put("user", user);
				Remote.put("password", password);
				Remote.put("status", "false");
				AliveRemoteList.replace(String.valueOf(AliveRemoteIndex), Remote);
			}
			Remote = null;
		}
	}
}
