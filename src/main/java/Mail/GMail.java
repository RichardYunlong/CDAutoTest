package Mail;

import Base.GClazz;
import Base.GFile;
import Base.GMsg;
import DT.GLog;
import Sys.GSys;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/*
 * 邮件
 */
public class GMail {
	
    /**
     *  构造函数
     */
	public GMail(){
		GClazz.thisAToolClass();
	}
	
    /**
     * 编码
     */
	@SuppressWarnings("FieldCanBeLocal")
    private final String CHARSET = "UTF-8";
	
    /**
     * 是否允许发送邮件
     */
	private boolean isMailOn = false;
	public boolean isMailOn() {
		return this.isMailOn;
	}
	public void setMailOn(boolean isMailOn) { this.isMailOn = isMailOn; }
	
    /**
     * 协议类型
     */
	private String serverProtocol = "";
	public String getServerProtocol() { return serverProtocol; }
	public void setServerProtocol(String serverProtocol) { this.serverProtocol = serverProtocol; }

    /**
     * 地址
     */
	private String serverHost = "";
	public String getServerHost() { return serverHost; }
	public void setServerHost(String serverHost) { this.serverHost = serverHost; }
	
    /**
     * 后缀
     */
	private String serverSuffix = "";
	public String getServerSuffix() { return serverSuffix; }
	public void setServerSuffix(String serverSuffix) { this.serverSuffix = serverSuffix; }
	
    /**
     * 账号
     */
	private String serverAccount = "";
	public String getServerAccount() { return serverAccount; }
	public void setServerAccount(String serverAccount) { this.serverAccount = serverAccount; }
    
    /**
     * 密码
     */
	private String serverPassword = "";
	public String getServerPassword() { return serverPassword; }
	public void setServerPassword(String serverPassword) { this.serverPassword = serverPassword; }
    
    /**
     * 端口
     */
	private String serverPort = "";
	public String getServerPort() { return serverPort; }
	public void setServerPort(String serverPort) { this.serverPort = serverPort; }
    
    /**
     * 认证方式
     */
	private String serverAuth = "";
	public String getServerAuth() { return serverAuth; }
	public void setServerAuth(String serverAuth) { this.serverAuth = serverAuth; }
    
    /**
     * 是否开启tls
     */
	private String serverTls = "";
	public String getServerTls() { return serverTls; }
	public void setServerTls(String serverTls) { this.serverTls = serverTls; }
    
    /**
     * 是否开启debug
     */
	private String serverDebug = "";
	public String getServerDebug() { return serverDebug; }
	public void setServerDebug(String serverDebug) { this.serverDebug = serverDebug;}
    
    /**
     * 收件人是否生效
     */
	private boolean receiveMailIsUsed = false;
	public boolean getReceiveMailIsUsed() { return receiveMailIsUsed; }
	public void setReceiveMailIsUsed(boolean receiveMailIsUsedTemp) { this.receiveMailIsUsed = receiveMailIsUsedTemp; }

	/**
	 * 开发者
	 */
	private String receiveMailAccountDev = "";
	public String getReceiveMailAccountDev() { return receiveMailAccountDev; }
	public void setReceiveMailAccountDev(String receiveMailAccountDev) { this.receiveMailAccountDev = receiveMailAccountDev; }

	/**
	 * 测试员
	 */
	private String receiveMailAccountTest = "";
	public String getReceiveMailAccountTest() { return receiveMailAccountTest; }
	public void setReceiveMailAccountTest(String receiveMailAccountTest) { this.receiveMailAccountTest = receiveMailAccountTest; }

	/**
	 * 管理员
	 */
	private String receiveMailAccountManager = "";
	public String getReceiveMailAccountManager() { return receiveMailAccountManager; }
	public void setReceiveMailAccountManager(String receiveMailAccountManager) { this.receiveMailAccountManager = receiveMailAccountManager; }

	/**
	 * 需求员
	 */
	private String receiveMailAccountReseacher = "";
	public String getReceiveMailAccountReseacher() { return receiveMailAccountReseacher; }
	public void setReceiveMailAccountReseacher(String receiveMailAccountReseacher) { this.receiveMailAccountReseacher = receiveMailAccountReseacher; }

    /**
     * 主送
     */
	private InternetAddress[] listRecipientsMC = null;
	public InternetAddress[] getListRecipientsMC() { return listRecipientsMC; }
	public void setListRecipientsMC(InternetAddress[] listRecipientsMC) { this.listRecipientsMC = listRecipientsMC; }

    /**
     * 抄送
     */
	private InternetAddress[] listRecipientsCC = null;
	public InternetAddress[] getListRecipientsCC() { return listRecipientsCC; }
	public void setListRecipientsCC(InternetAddress[] listRecipientsCC) { this.listRecipientsCC = listRecipientsCC; }
	
    /**
     * 密送
     */
	private InternetAddress[] listRecipientsSC = null;
	public InternetAddress[] getListRecipientsSC() { return listRecipientsSC; }
	public void setListRecipientsSC(InternetAddress[] listRecipientsSC) { this.listRecipientsSC = listRecipientsSC; }

	/**
	 * 主题
	 */
	private String subject =  "";
	public String getSubject() { return subject; }
	public void setSubject(String subject) { this.subject = subject; }

	/**
     * 邮件服务器属性加载器
     */
	private Properties props = new Properties();
	public Properties getProps() { return props; }
	public void setProps(Properties props) { this.props = props; }
    
	/**
	 *  加载参数
	 */	
	public void loadConfig() {
		if((!serverProtocol.isEmpty())
		&& (!serverHost.isEmpty())
		&& (!serverSuffix.isEmpty())
		&& (!serverAccount.isEmpty())
		&& (!serverPassword.isEmpty())
		&& (!serverPort.isEmpty())
		&& (!serverAuth.isEmpty())
		&& (!serverTls.isEmpty())
		&& (!serverDebug.isEmpty())){
			GFile.writeStringToGuideBottom("GMail Params Loaded");
		}else {
			GFile.writeStringToBottom(GSys.GUIDE ,"ERROR----" + "One of these smtp params from smtpConfig may has no value, Please check again!");
			System.exit(0);
		}
		
		if(!receiveMailAccountTest.isEmpty() || (listRecipientsMC != null)){
			GFile.writeStringToGuideBottom("GMail Params Loaded");
		}else {
			GFile.writeStringToBottom(GSys.GUIDE ,"ERROR----" + "One of these email params from smtpConfig may has no value, Please check again!");
			System.exit(0);
		}
	}
    
    /**
     * 初始化邮件服务器 暂时使用内置参数
     */
    public void initEmailServer() {
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
     * @param mailContent 邮件内容
	 *
	 * @return 邮件对象
     */
    public MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, StringBuilder mailContent) {
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
     * @param htmlPath 指定邮件内容
     * @param emailContentType 指定邮件内容格式 默认为“text/html”
     */
    public void sentEmail(String htmlPath, String emailContentType) {
    	try {
            // 2. 根据配置创建会话对象, 用于和邮件服务器交互
            Session session = Session.getInstance(props);
            // 设置为debug模式, 可以查看详细的发送 log
            session.setDebug(true);
     
            // 3. 创建一封邮件
            MimeMessage message = createMimeMessage(session, serverAccount, receiveMailAccountTest, GFile.html2StringBuffer(htmlPath));

            // 4. 根据 Session 获取邮件传输对象
			Transport transport = getTransport(session);

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
	 * 获取邮件传输对象
	 *
	 * @param session 指定邮件内容
	 *
	 * @return Transport对象
	 */
	private Transport getTransport(Session session) throws MessagingException {
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
		//           (3) 邮箱服务器要求必须使用 SSL 安全连接;
		//           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
		//           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
		//
		transport.connect(serverAccount, serverPassword);
		return transport;
	}

	/**
     *  构造函数
     *  
     *  @param Subject 邮件主题
	 *  @param reportFile 报告文件全名
     */
    public void sendEmail(String Subject, String reportFile) {
    	if(this.isMailOn && this.receiveMailIsUsed) {
        	 this.initEmailServer();
        	 this.subject += Subject;
     	     this.sentEmail(reportFile,"");
        } 
    }
}
