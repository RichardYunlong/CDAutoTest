package main.java.Base;

/**
 *  远程执行命令
 */
public class GShell {
	
    /**
     *  构造函数
     */
	private GShell(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  远程Windows执行命令
	 *
	 *  @param strIp 目标主机ip
	 *  @param strAccount 目标主机账号
	 *  @param strPassword 目标主机登录密码
	 *  @param strWinCMD 目标主机win格式的命令
	 *
	 *  @return 返回命令执行结果控制台输出
	 */
	public static String runWinCmd(String strIp, String strAccount, String strPassword, String strWinCMD) {
		GWinRM exec = new GWinRM(strIp, strAccount, strPassword);
//		GWinRM exec = new GWinRM("10.2.94.26", "hew-d", "Pass!2345");
//		ResStr = exec.execute("start /D C:\\Users\\Hew-d\\Desktop\\UIAutoTest /B startup.bat");//正确返回样例
//		ResStr = exec.execute("cmd /k start /D C:\\Users\\Hew-d\\Desktop\\UIAutoTest startup.bat");//一直等待样例，由于远程唤起的一级窗口一直未关闭或结束进程
//		ResStr = exec.execute("start /D C:\\Users\\Hew-d\\Desktop\\UIAutoTest /B startup.bat");
		return exec.execute(strWinCMD);
	}
	
	/**
	 *  远程Windows执行命令
	 *
	 *  @param strIp 目标主机ip
	 *  @param strAccount 目标主机账号
	 *  @param strPassword 目标主机登录密码
	 *  @param strWinCMD 目标主机win格式的命令
	 *
	 *  @return 真值，命令是否已执行，但不记录执行结果
	 */
	public static boolean runWinCmdBool(String strIp, String strAccount, String strPassword, String strWinCMD) {
		GWinRM exec = new GWinRM(strIp, strAccount, strPassword);
		return exec.executeBool(strWinCMD);
	}
}
