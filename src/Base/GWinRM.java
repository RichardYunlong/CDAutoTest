package Base;

import org.apache.http.client.config.AuthSchemes;

import DT.GLog;
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
	private String ip;
	
	/**
	 * 主控账户名称
	 */
	private String username;
	
	/**
	 * 主控账户密码 
	 */
	private String password;
	
	/**
	 * 默认远程控制端口 
	 */
	public static final int DEFAULT_PORT = 5985;

	/**
	 * 构造函数 
	 */
	public GWinRM(final String ip,final String username,final String password) {
		this.ip = ip;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * 命令执行器 
	 */
	public String execute(final String command) {
		WinRmClientContext context = WinRmClientContext.newInstance();
		WinRmTool tool = WinRmTool.Builder.builder(ip, username, password).setAuthenticationScheme(AuthSchemes.NTLM).port(DEFAULT_PORT).useHttps(false).context(context).build();
		tool.setOperationTimeout(5000L);
		WinRmToolResponse resp = tool.executeCommand(command);
		context.shutdown();
		
		return "{resCode:" + String.valueOf(resp.getStatusCode()) + "," + "resMsg:" + "{StdOut:" + resp.getStdOut() + ",StdErr:" + resp.getStdErr() + "}" + "}";
	}
	
	/**
	 * 命令执行器 
	 */
	public boolean executeBool(final String command) {
		boolean resBool = true;
		
		try {
			WinRmClientContext context = WinRmClientContext.newInstance();
			WinRmTool tool = WinRmTool.Builder.builder(ip, username, password).setAuthenticationScheme(AuthSchemes.NTLM).port(DEFAULT_PORT).useHttps(false).context(context).build();
			tool.setOperationTimeout(5000L);
			WinRmToolResponse resp = tool.executeCommand(command);
			context.shutdown();
			
			if(resp.getStdOut().equals("") && resp.getStdErr().equals("")) {
				resBool = false;
			}
		}catch(Exception e) {
			GLog.logRecord(9, "REMOTE CONNECT FAILED");
			resBool = false;
		}
		
		return resBool;
	}
}
