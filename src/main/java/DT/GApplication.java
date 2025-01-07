package main.java.DT;

import main.java.Base.GClazz;

/**
 *  应用
 */
public class GApplication {
		
    /**
     *  构造函数
     */
	private GApplication(){
		GClazz.thisAToolClass();
	}
	
	/**
	 *  一类应用名称
	 */
	private static String applicationName;
	
	public static String getApplicationName() {
		return applicationName;
	}

	public static void setApplicationName(String gApplicationName) {
		GApplication.applicationName = gApplicationName;
	}

	/**
	 *  一类应用OU
	 */
	private static String applicationOU;
	
	public static String getApplicationOU() {
		return applicationOU;
	}

	public static void setApplicationOU(String gApplicationOU) {
		GApplication.applicationOU = gApplicationOU;
	}

	/**
	 *  一类应用CA名称
	 */
	private static String applicationCAName;
	
	public static String getApplicationCAName() {
		return applicationCAName;
	}

	public static void setApplicationCAName(String gApplicationCAName) {
		GApplication.applicationCAName = gApplicationCAName;
	}

	/**
	 *  一类应用系统标识（DN规则为5.1时的输入项）
	 */
	private static String applicationSysIdent;
	
	public static String getApplicationSysIdent() {
		return applicationSysIdent;
	}

	public static void setApplicationSysIdent(String gApplicationSysIdent) {
		GApplication.applicationSysIdent = gApplicationSysIdent;
	}

	/**
	 *  二类应用名称
	 */
	private static String application2Name;
	
	public static String getApplication2Name() {
		return application2Name;
	}

	public static void setApplication2Name(String gApplication2Name) {
		GApplication.application2Name = gApplication2Name;
	}

	/**
	 *  二类应用OU
	 */
	private static String application2OU;
	
	public static String getApplication2OU() {
		return application2OU;
	}

	public static void setApplication2OU(String gApplication2OU) {
		GApplication.application2OU = gApplication2OU;
	}

	/**
	 *  二类应用CA名称
	 */
	private static String application2CAName;
	
	public static String getApplication2CAName() {
		return application2CAName;
	}

	public static void setApplication2CAName(String gApplication2CAName) {
		GApplication.application2CAName = gApplication2CAName;
	}

	/**
	 *  二类应用系统标识（DN规则为5.1时的输入项）
	 */
	private static String application2SysIdent;
	
	public static String getApplication2SysIdent() {
		return application2SysIdent;
	}

	public static void setApplication2SysIdent(String gApplication2SysIdent) {
		GApplication.application2SysIdent = gApplication2SysIdent;
	}

	/**
	 *  三类类应用名称
	 */
	private static String application3Name;
	
	public static String getApplication3Name() {
		return application3Name;
	}

	public static void setApplication3Name(String gApplication3Name) {
		GApplication.application3Name = gApplication3Name;
	}

	/**
	 *  三类应用OU
	 */
	private static String application3OU;
	
	public static String getApplication3OU() {
		return application3OU;
	}

	public static void setApplication3OU(String gApplication3OU) {
		GApplication.application3OU = gApplication3OU;
	}

	/**
	 *  三类应用CA名称
	 */
	private static String application3CAName;
	
	public static String getApplication3CAName() {
		return application3CAName;
	}

	public static void setApplication3CAName(String gApplication3CAName) {
		GApplication.application3CAName = gApplication3CAName;
	}

	/**
	 *  三类应用系统标识（DN规则为5.1时的输入项）
	 */
	private static String application3SysIdent;
	
	public static String getApplication3SysIdent() {
		return application3SysIdent;
	}

	public static void setApplication3SysIdent(String gApplication3SysIdent) {
		GApplication.application3SysIdent = gApplication3SysIdent;
	}

	/**
	 * 恢复默认
	 */
	public static void setDefault() {
		applicationName = "";
		applicationOU = "";
		applicationCAName = "";
		applicationSysIdent = "";
		application2Name = "";
		application2OU = "";
		application2CAName = "";
		application2SysIdent = "";
		application3Name = "";
		application3OU = "";
		application3CAName = "";
		application3SysIdent = "";
	}
}
