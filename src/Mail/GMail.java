package Mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Base.GClazz;
import Base.GFile;
import Base.GMsg;
import DT.GLog;
import Sys.GSys;

/*
 * 邮件
 */
public class GMail {
	
    /**
     *  构造函数
     */
	private GMail(){
		GClazz.thisAToolClass();
	}
	
    /*
     * 编码
     */
	private static final String CHARSET = "UTF-8";
	
    /*
     * 是否允许发送邮件
     */
	private static boolean isMailOn = false;
	
    /*
     * 协议类型
     */
	private static String serverProtocol = "";

    /*
     * 地址
     */
	private static String serverHost = "";
	
    /*
     * 后缀
     */
	private static String serverSuffix = "";
	
    /*
     * 账号
     */
	private static String serverAccount = "";
    
    /*
     * 密码
     */
	private static String serverPassword = "";
    
    /*
     * 端口
     */
	private static String serverPort = "";
    
    /*
     * 认证方式
     */
	private static String serverAuth = "";
    
    /*
     * 是否开启tls
     */
	private static String serverTls = "";
    
    /*
     * 是否开启debug
     */
	private static String serverDebug = "";
    
    /*
     * 收件人邮箱（替换为自己知道的有效邮箱）
     */
	private static boolean receiveMailIsUsed = false;//收件人是否生效
	private static String receiveMailAccountDev = "";//开发者
	private static String receiveMailAccountTest = "";//测试员
	private static String receiveMailAccountManager = "";//管理员
	private static String receiveMailAccountReseacher = "";//需求员
	
    /*
     * 主送
     */
	private static InternetAddress[] listRecipientsMC = null;
	
    /*
     * 抄送
     */
	private static InternetAddress[] listRecipientsCC = null;
	
    /*
     * 密送
     */
	private static InternetAddress[] listRecipientsSC = null;
    
	private static String subject =  ""; //报告主题
	
	
    
    public static boolean isMailOn() {
		return isMailOn;
	}

	public static void setMailOn(boolean isMailOn) {
		GMail.isMailOn = isMailOn;
	}

	public static String getServerProtocol() {
		return serverProtocol;
	}

	public static void setServerProtocol(String serverProtocol) {
		GMail.serverProtocol = serverProtocol;
	}

	public static String getServerHost() {
		return serverHost;
	}

	public static void setServerHost(String serverHost) {
		GMail.serverHost = serverHost;
	}

	public static String getServerSuffix() {
		return serverSuffix;
	}

	public static void setServerSuffix(String serverSuffix) {
		GMail.serverSuffix = serverSuffix;
	}

	public static String getServerAccount() {
		return serverAccount;
	}

	public static void setServerAccount(String serverAccount) {
		GMail.serverAccount = serverAccount;
	}

	public static String getServerPassword() {
		return serverPassword;
	}

	public static void setServerPassword(String serverPassword) {
		GMail.serverPassword = serverPassword;
	}

	public static String getServerPort() {
		return serverPort;
	}

	public static void setServerPort(String serverPort) {
		GMail.serverPort = serverPort;
	}

	public static String getServerAuth() {
		return serverAuth;
	}

	public static void setServerAuth(String serverAuth) {
		GMail.serverAuth = serverAuth;
	}

	public static String getServerTls() {
		return serverTls;
	}

	public static void setServerTls(String serverTls) {
		GMail.serverTls = serverTls;
	}

	public static String getServerDebug() {
		return serverDebug;
	}

	public static void setServerDebug(String serverDebug) {
		GMail.serverDebug = serverDebug;
	}

	public static boolean isReceiveMailIsUsed() {
		return receiveMailIsUsed;
	}

	public static void setReceiveMailIsUsed(boolean receiveMailIsUsed) {
		GMail.receiveMailIsUsed = receiveMailIsUsed;
	}

	public static String getReceiveMailAccountDev() {
		return receiveMailAccountDev;
	}

	public static void setReceiveMailAccountDev(String receiveMailAccountDev) {
		GMail.receiveMailAccountDev = receiveMailAccountDev;
	}

	public static String getReceiveMailAccountTest() {
		return receiveMailAccountTest;
	}

	public static void setReceiveMailAccountTest(String receiveMailAccountTest) {
		GMail.receiveMailAccountTest = receiveMailAccountTest;
	}

	public static String getReceiveMailAccountManager() {
		return receiveMailAccountManager;
	}

	public static void setReceiveMailAccountManager(String receiveMailAccountManager) {
		GMail.receiveMailAccountManager = receiveMailAccountManager;
	}

	public static String getReceiveMailAccountReseacher() {
		return receiveMailAccountReseacher;
	}

	public static void setReceiveMailAccountReseacher(String receiveMailAccountReseacher) {
		GMail.receiveMailAccountReseacher = receiveMailAccountReseacher;
	}

	public static InternetAddress[] getListRecipientsMC() {
		return listRecipientsMC;
	}

	public static void setListRecipientsMC(InternetAddress[] listRecipientsMC) {
		GMail.listRecipientsMC = listRecipientsMC;
	}

	public static InternetAddress[] getListRecipientsCC() {
		return listRecipientsCC;
	}

	public static void setListRecipientsCC(InternetAddress[] listRecipientsCC) {
		GMail.listRecipientsCC = listRecipientsCC;
	}

	public static InternetAddress[] getListRecipientsSC() {
		return listRecipientsSC;
	}

	public static void setListRecipientsSC(InternetAddress[] listRecipientsSC) {
		GMail.listRecipientsSC = listRecipientsSC;
	}

	public static String getSubject() {
		return subject;
	}

	public static void setSubject(String subject) {
		GMail.subject = subject;
	}

	public static Properties getProps() {
		return props;
	}

	public static void setProps(Properties props) {
		GMail.props = props;
	}

	/*
     * 邮件服务器属性加载器
     */
	private static Properties props = new Properties();  
    
	/**
	 *  加载参数
	 */	
	public static void loadConfig() {
		if((!serverProtocol.equals("")) 
		&& (!serverHost.equals("")) 
		&& (!serverSuffix.equals("")) 
		&& (!serverAccount.equals(""))
		&& (!serverPassword.equals(""))
		&& (!serverPort.equals(""))
		&& (!serverAuth.equals(""))
		&& (!serverTls.equals(""))
		&& (!serverDebug.equals(""))){
			;
		}else {
			GSys.logErrorSys("One of these smtp params from smtpConfig may has no value, Please check again!");
			System.exit(0);
		}
		
		if(!receiveMailAccountTest.equals("") || (listRecipientsMC != null)){			
			;
		}else {
			GSys.logErrorSys("One of these email params from smtpConfig may has no value, Please check again!");
			System.exit(0);
		}
	}
    
    /**
     * 初始化邮件服务器 暂时使用内置参数
     * 
     * @param bDebug 是否启用debug模式，用于调试
     */
    public static void initEmailServer() {
    	// 1. 配置属性
        props.setProperty("mail.transport.protocol", serverProtocol);// 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", serverHost);// 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", serverAuth);// 需要请求认证
        props.setProperty("mail.smtp.port", serverPort);
        props.setProperty("mail.debug", serverDebug); //控制台打印信息，调试
    }
 
    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, StringBuilder mailContent) {
        MimeMessage message = null;
        
        try {
        	// 1. 创建一封邮件
            message = new MimeMessage(session);
            
        	// 2. From: 发件人
			message.setFrom(new InternetAddress(sendMail, subject, CHARSET));
			
			// 3. To: 收件人
			if(listRecipientsMC != null) {
				message.setRecipients(MimeMessage.RecipientType.TO, listRecipientsMC);
				message.setRecipients(MimeMessage.RecipientType.CC, listRecipientsCC);
				message.setRecipients(MimeMessage.RecipientType.BCC, listRecipientsSC);
			}else {
				//主送
		        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMailAccountTest, "hewei", CHARSET));
		        //抄送
		        InternetAddress[] listRecipientsCC = {new InternetAddress(receiveMailAccountManager, "hewei", CHARSET), new InternetAddress(receiveMailAccountReseacher, "hewei", CHARSET)};
		        message.setRecipients(MimeMessage.RecipientType.CC, listRecipientsCC);
		        //密送
		        message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(receiveMailAccountDev, "hewei", CHARSET));
			}
			
	        // 4. Subject: 邮件主题
	        message.setSubject(subject, CHARSET);
	 
	        // 5. Content: 邮件正文（可以使用html标签）
	        message.setContent(mailContent.toString(), "text/html;charset=" + CHARSET);
	        
	        // 6. 设置发件时间
	        message.setSentDate(new Date());
	 
	        // 7. 保存设置
	        message.saveChanges();
		} catch (Exception e) {
			GLog.logSysFunctionException("createMimeMessage", e);
		}
        
        return message;
    }
    
    /**
     * 指定内容，发送邮件
     * 
     * @param emailContent 指定邮件内容
     * @param emailContentType 指定邮件内容格式 默认为“text/html”
     */
    public static void sentEmail(String htmlPath, String emailContentType) {
    	try {
            // 2. 根据配置创建会话对象, 用于和邮件服务器交互
            Session session = Session.getInstance(props);
            // 设置为debug模式, 可以查看详细的发送 log
            session.setDebug(true);
     
            // 3. 创建一封邮件
            MimeMessage message = createMimeMessage(session, serverAccount, receiveMailAccountTest, GFile.html2StringBuffer(htmlPath));
     
            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();
     
            // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            //
            //    PS_01: 如果连接服务器失败, 都会在控制台输出相应失败原因的log。
            //    仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接,
            //    根据给出的错误类型到对应邮件服务器的帮助网站上查看具体失败原因。
            //
            //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
            //           (1) 邮箱没有开启 SMTP 服务;
            //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
            //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
            //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
            //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
            //
            transport.connect(serverAccount, serverPassword);
     
            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
     
            // 7. 关闭连接
            transport.close();
            
            GLog.logShowConsole(GMsg.MSG_EMAIL[0]);
    	}catch(Exception e){
    		GLog.logShowConsole(GMsg.MSG_EMAIL[1]);
    		GLog.logSysFunctionException("sentEmail", e);
    	}
    }
    
    /**
     *  构造函数
     *  
     *  @param Subject 邮件主题
     */
    public static void sendEmail(String Subject, String reportFile) {
    	if(GMail.isMailOn && GMail.receiveMailIsUsed) {
        	 GMail.initEmailServer();
        	 GMail.subject += Subject;
     	     GMail.sentEmail(reportFile,"");
        } 
    }
}
