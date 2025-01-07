package main.java.Base;

import org.apache.http.client.config.AuthSchemes;

import main.java.DT.GLog;
import io.cloudsoft.winrm4j.client.WinRmClientContext;
import io.cloudsoft.winrm4j.winrm.WinRmTool;
import io.cloudsoft.winrm4j.winrm.WinRmToolResponse;


/**
 * Windows远程控制-RM方式
 */
public class GWinRM {
	
	/**
	 * 目标IP 
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private String ip;
	
	/**
	 * 主控账户名称
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private String username;
	
	/**
	 * 主控账户密码 
	 */
	@SuppressWarnings({"CanBeFinal", "FieldMayBeFinal"})
    private String password;
	
	/**
	 * 默认远程控制端口 
	 */
	public static final int DEFAULT_PORT = 5985;

	/**
	 * 构造函数
	 *
	 * @param ip ip
	 * @param username 用户名
	 * @param password 密码
	 */
	public GWinRM(final String ip,final String username,final String password) {
		this.ip = ip;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * 命令执行器
	 *
	 * @param command 命令内容
	 *
	 * @return 命令内容
	 */
	public String execute(final String command) {
		WinRmClientContext context = WinRmClientContext.newInstance();
		WinRmTool tool = WinRmTool.Builder.builder(ip, username, password).setAuthenticationScheme(AuthSchemes.NTLM).port(DEFAULT_PORT).useHttps(false).context(context).build();
		tool.setOperationTimeout(5000L);
		WinRmToolResponse resp = tool.executeCommand(command);
		context.shutdown();
		
		return "{resCode:" + resp.getStatusCode() + "," + "resMsg:" + "{StdOut:" + resp.getStdOut() + ",StdErr:" + resp.getStdErr() + "}" + "}";
	}
	
	/**
	 * 命令执行器
	 *
	 * @param command 命令内容
	 *
	 * @return 命令内容
	 */
	public boolean executeBool(final String command) {
		boolean resBool = true;
		
		try {
			WinRmClientContext context = WinRmClientContext.newInstance();
			WinRmTool tool = WinRmTool.Builder.builder(ip, username, password).setAuthenticationScheme(AuthSchemes.NTLM).port(DEFAULT_PORT).useHttps(false).context(context).build();
			tool.setOperationTimeout(5000L);
			WinRmToolResponse resp = tool.executeCommand(command);
			context.shutdown();
			
			if(resp.getStdOut().isEmpty() && resp.getStdErr().isEmpty()) {
				resBool = false;
			}
		}catch(Exception e) {
			GLog.logRecord(9, "REMOTE CONNECT FAILED");
			resBool = false;
		}
		
		return resBool;
	}
}
