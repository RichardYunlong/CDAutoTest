package AutoTest;

/**
 * 提示语常量
 */
public class GMsg {
	private GMsg(){
		GLog.logShowConsole("This is a tool class.");
	}
	
	/**
	 *  自动化测试框架名称
	 */
	public static final String SYSTEM_NAME = "Autotest";
	
	/**
	 *  自动化测试框架版本号
	 */
	public static final String SYSTEM_VERSION = "3.1.0.0";
	
	/**
	 *  自动化测试框架制作人
	 */
	public static final String SYSTEM_DEVELOPER = "Wei·He";
	
	/**
	 *  自动化测试框架出品人
	 */
	public static final String SYSTEM_PRODUCER = "Richard·CDragon";
	
	/**
	 *  自动化测试框架发行单位
	 */
	public static final String SYSTEM_ISSUER = "CDragon Studio & Glodon";
	
	/**
	 *  自动化测试框架联系邮箱
	 */
	public static final String SYSTEM_EMAIL = "hewei200685@hotmail.com";
	
	/**
	 *  自动化测试框架使用欢迎语
	 */
	public static final String SYSTEM_WELCOME = 
			"###################################################################################################" + "\r\n"
		  + "#                                         WELCOME TO USE                                          #" + "\r\n"
		  + "#                                            " + SYSTEM_NAME + "                                             #" + "\r\n"
		  + "#                                            VERSION                                              #" + "\r\n"
		  + "#                                            " + SYSTEM_VERSION + "                                              #" + "\r\n"
		  + "#                                             DEV BY                                              #" + "\r\n"
		  + "#                                             " + SYSTEM_DEVELOPER + "                                              #" + "\r\n"
		  + "#                                             PRO BY                                              #" + "\r\n"
		  + "#                                         " + SYSTEM_PRODUCER + "                                         #" + "\r\n"
		  + "#                                              FROM                                               #" + "\r\n"
		  + "#                                      " + SYSTEM_ISSUER + "                                      #" + "\r\n"
		  + "#                                                                                                 #" + "\r\n"
		  + "#                         For heaven and earth, for the people's livelihood;                      #" + "\r\n"
		  + "#           for the saints of the past to continue learning, for all ages to open peace           #" + "\r\n"
		  + "###################################################################################################" + "\r\n"
		  + "\r\n\r\n";
	
	/**
	 *  自动化测试框架使用结束语
	 */
	public static final String SYSTEM_ENDING = 
			"###################################################################################################" + "\r\n"
		  + "#                                       THANK YOU FOR USING                                       #" + "\r\n"
		  + "#                                         " + SYSTEM_NAME + " v" + SYSTEM_VERSION + "                                       #" + "\r\n"
		  + "#                                  ANY QUESTIONS TO SEND E-MAIL TO                                #" + "\r\n"
		  + "#                                      " + SYSTEM_EMAIL + "                                    #" + "\r\n"
		  + "###################################################################################################" + "\r\n"
		  + "\r\n\r\n";
	
	public static final String MSG_NOTFOUND[] = {"NO VALUE BE FOUND","NO AVAILABLE VALUE BE FOUND","NO FILE BE FOUND","NO AVAILABLE FILE BE FOUND"};
	public static final String MSG_IOFAILED[] = {"READ FAILED","WRITE FAILED", "CREATE FAILED", "MUST BE OVERWRITTEN"};
	public static final String MSG_ISOPENED[] = {"THE TARGET FILE IS NOT OPENEDT","THE TARGET FILE MUST BE CLOSE FIRST"};
	public static final String MSG_FOUND[] = {"A VALUE BE FOUND","AVAILABLE VALUES BE FOUND","A FILE BE FOUND","AVAILABLE FILES BE FOUND"};
	public static final String MSG_EXIST[] = {"FILE EXIST","FILE NOT EXIST"};
	public static final String MSG_EMPTY[] = {"FILE IS EMPTY","FILE IS NOT EMPTY"};
	public static final String MSG_MEASUREMENT[] = {"","TIMES","B","KB","MB","GB","TB"};
	public static final String MSG_CONSOLE[] = {"SOMETHING WRONG WITH PRINTING IN CONSOLE,DETAIL:"};
	public static final String MSG_LOGERROR[] = {"UNKOWN LOG TYPE"};
	public static final String MSG_DB[] = {"EFFECT LINE NUMBER:","FIND LINE NUMBER:"};
	public static final String MSG_EMAIL[] = {"EMAIL SENT SUCCESS", "EMAIL SENT FAILED"};
}
