package main.java.Base;

import main.java.DT.GLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  进程管理
 */
public class GProcess {
	
    /**
     *  构造函数
     */
	private GProcess(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  按照程序名称查找并关闭进程
	 *
	 *  @param processName 传入进程名称processName
	 *
	 *  @return 结束成功返回true
	 */
	public static boolean findAddKillProcess(String processName) {
	        
	        Process proc = null;
			try {
				proc = Runtime.getRuntime().exec("tasklist -fi " + '"' + "imagename eq " + processName +'"');
			} catch (IOException e) {
				GLog.logSysFunctionException("findAddKillProcess", e);
			}
			if(proc != null) {
		        try(InputStreamReader isr = new InputStreamReader(proc.getInputStream());
			        	BufferedReader bufferedReader = new BufferedReader(isr)) {
			            String line;
			            while ((line = bufferedReader.readLine()) != null) {
			            	GLog.logRecord(8, "findProcess()获取到的进程信息：" + line);
			                if (line.contains(processName)) {
			                	killProcess(processName);
			                	GLog.logRecord(8, "找到并且杀死进程：" + processName);
			                    return true;
			                }
			            }
			            return false;
			        } catch (Exception e) {
			        	GLog.logSysFunctionException("findAddKillProcess", e);
			            return false;
			        }
			}else{
				return false;
			}
	  }
	
	/**
	 *  按照程序名称查找并关闭进程
	 *  @param processName 传入进程名称processName
	 */
	public static void killProcess(String processName) {
		try {  
			if(processName != null) {
				Process pro = Runtime.getRuntime().exec("taskkill /F /im "+ processName); 
				BufferedReader brStd = new BufferedReader(new InputStreamReader(pro.getInputStream()));
				BufferedReader brErr = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
				long time = System.currentTimeMillis();
				while (true) {
					if (brStd.ready()) {
						GLog.logRecord(8, "killProcess()进程正常返回:" + processName);
						break;
					} 
					if (brErr.ready()) {
						GLog.logRecord(8, "killProcess()进程出错返回:" + processName);
						break;
					}
					if(System.currentTimeMillis() - time > 3000) {
						GLog.logRecord(8, "killProcess()等待超时:" + processName);
						return;
					}
				}
			}
		} catch (IOException e) {
			GLog.logSysFunctionException("killProcess", e);
		}  
	}
}