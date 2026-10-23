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

/**
 * 邮件
 *
 * <p>基于 {@code javax.mail} 的 SMTP 邮件发送器，用于把测试报告、运行日志或异常告警
 * 以正文加附件的形式发送给相关人员。</p>
 *
 * <p>使用前需要先通过各个 setter 注入 SMTP 服务器与账号信息，并调用
 * {@link #setMailOn(boolean)} 打开发送开关；开关关闭时所有发送调用都会被静默跳过，
 * 便于在本地调试时避免误发邮件。</p>
 *
 * @see DT.GLog
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
	/**
	 *  获得是否允许发送邮件
	 *
	 *  @return 是否允许发送邮件
	 */
	public boolean isMailOn() {
		return this.isMailOn;
	}
	/**
	 *  设置是否允许发送邮件
	 *
	 *  @param isMailOn 是否允许发送邮件
	 */
	public void setMailOn(boolean isMailOn) { this.isMailOn = isMailOn; }
	
    /**
     * 协议类型
     */
	private String serverProtocol = "";
	/**
	 *  获得协议类型
	 *
	 *  @return 协议类型
	 */
	public String getServerProtocol() { return serverProtocol; }
	/**
	 *  设置协议类型
	 *
	 *  @param serverProtocol 协议类型
	 */
	public void setServerProtocol(String serverProtocol) { this.serverProtocol = serverProtocol; }

    /**
     * 地址
     */
	private String serverHost = "";
	/**
	 *  获得地址
	 *
	 *  @return 地址
	 */
	public String getServerHost() { return serverHost; }
	/**
	 *  设置地址
	 *
	 *  @param serverHost 地址
	 */
	public void setServerHost(String serverHost) { this.serverHost = serverHost; }
	
    /**
     * 后缀
     */
	private String serverSuffix = "";
	/**
	 *  获得后缀
	 *
	 *  @return 后缀
	 */
	public String getServerSuffix() { return serverSuffix; }
	/**
	 *  设置后缀
	 *
	 *  @param serverSuffix 后缀
	 */
	public void setServerSuffix(String serverSuffix) { this.serverSuffix = serverSuffix; }
	
    /**
     * 账号
     */
	private String serverAccount = "";
	/**
	 *  获得账号
	 *
	 *  @return 账号
	 */
	public String getServerAccount() { return serverAccount; }
	/**
	 *  设置账号
	 *
	 *  @param serverAccount 账号
	 */
	public void setServerAccount(String serverAccount) { this.serverAccount = serverAccount; }
    
    /**
     * 密码
     */
	private String serverPassword = "";
	/**
	 *  获得密码
	 *
	 *  @return 密码
	 */
	public String getServerPassword() { return serverPassword; }
	/**
	 *  设置密码
	 *
	 *  @param serverPassword 密码
	 */
	public void setServerPassword(String serverPassword) { this.serverPassword = serverPassword; }
    
    /**
     * 端口
     */
	private String serverPort = "";
	/**
	 *  获得端口
	 *
	 *  @return 端口
	 */
	public String getServerPort() { return serverPort; }
	/**
	 *  设置端口
	 *
	 *  @param serverPort 端口
	 */
	public void setServerPort(String serverPort) { this.serverPort = serverPort; }
    
    /**
     * 认证方式
     */
	private String serverAuth = "";
	/**
	 *  获得认证方式
	 *
	 *  @return 认证方式
	 */
	public String getServerAuth() { return serverAuth; }
	/**
	 *  设置认证方式
	 *
	 *  @param serverAuth 认证方式
	 */
	public void setServerAuth(String serverAuth) { this.serverAuth = serverAuth; }
    
    /**
     * 是否开启tls
     */
	private String serverTls = "";
	/**
	 *  获得是否开启tls
	 *
	 *  @return 是否开启tls
	 */
	public String getServerTls() { return serverTls; }
	/**
	 *  设置是否开启tls
	 *
	 *  @param serverTls 是否开启tls
	 */
	public void setServerTls(String serverTls) { this.serverTls = serverTls; }
    
    /**
     * 是否开启debug
     */
	private String serverDebug = "";
	/**
	 *  获得是否开启debug
	 *
	 *  @return 是否开启debug
	 */
	public String getServerDebug() { return serverDebug; }
	/**
	 *  设置是否开启debug
	 *
	 *  @param serverDebug 是否开启debug
	 */
	public void setServerDebug(String serverDebug) { this.serverDebug = serverDebug;}
    
    /**
     * 收件人是否生效
     */
	private boolean receiveMailIsUsed = false;
	/**
	 *  获得收件人是否生效
	 *
	 *  @return 收件人是否生效
	 */
	public boolean getReceiveMailIsUsed() { return receiveMailIsUsed; }
	/**
	 *  设置收件人是否生效
	 *
	 *  @param receiveMailIsUsedTemp 收件人是否生效
	 */
	public void setReceiveMailIsUsed(boolean receiveMailIsUsedTemp) { this.receiveMailIsUsed = receiveMailIsUsedTemp; }

	/**
	 * 开发者
	 */
	private String receiveMailAccountDev = "";
	/**
	 *  获得开发者
	 *
	 *  @return 开发者
	 */
	public String getReceiveMailAccountDev() { return receiveMailAccountDev; }
	/**
	 *  设置开发者
	 *
	 *  @param receiveMailAccountDev 开发者
	 */
	public void setReceiveMailAccountDev(String receiveMailAccountDev) { this.receiveMailAccountDev = receiveMailAccountDev; }

	/**
	 * 测试员
	 */
	private String receiveMailAccountTest = "";
	/**
	 *  获得测试员
	 *
	 *  @return 测试员
	 */
	public String getReceiveMailAccountTest() { return receiveMailAccountTest; }
	/**
	 *  设置测试员
	 *
	 *  @param receiveMailAccountTest 测试员
	 */
	public void setReceiveMailAccountTest(String receiveMailAccountTest) { this.receiveMailAccountTest = receiveMailAccountTest; }

	/**
	 * 管理员
	 */
	private String receiveMailAccountManager = "";
	/**
	 *  获得管理员
	 *
	 *  @return 管理员
	 */
	public String getReceiveMailAccountManager() { return receiveMailAccountManager; }
	/**
	 *  设置管理员
	 *
	 *  @param receiveMailAccountManager 管理员
	 */
	public void setReceiveMailAccountManager(String receiveMailAccountManager) { this.receiveMailAccountManager = receiveMailAccountManager; }

	/**
	 * 需求员
	 */
	private String receiveMailAccountReseacher = "";
	/**
	 *  获得需求员
	 *
	 *  @return 需求员
	 */
	public String getReceiveMailAccountReseacher() { return receiveMailAccountReseacher; }
	/**
	 *  设置需求员
	 *
	 *  @param receiveMailAccountReseacher 需求员
	 */
	public void setReceiveMailAccountReseacher(String receiveMailAccountReseacher) { this.receiveMailAccountReseacher = receiveMailAccountReseacher; }

    /**
     * 主送
     */
	private InternetAddress[] listRecipientsMC = null;
	/**
	 *  获得主送
	 *
	 *  @return 主送
	 */
	public InternetAddress[] getListRecipientsMC() { return listRecipientsMC; }
	/**
	 *  设置主送
	 *
	 *  @param listRecipientsMC 主送
	 */
	public void setListRecipientsMC(InternetAddress[] listRecipientsMC) { this.listRecipientsMC = listRecipientsMC; }

    /**
     * 抄送
     */
	private InternetAddress[] listRecipientsCC = null;
	/**
	 *  获得抄送
	 *
	 *  @return 抄送
	 */
	public InternetAddress[] getListRecipientsCC() { return listRecipientsCC; }
	/**
	 *  设置抄送
	 *
	 *  @param listRecipientsCC 抄送
	 */
	public void setListRecipientsCC(InternetAddress[] listRecipientsCC) { this.listRecipientsCC = listRecipientsCC; }
	
    /**
     * 密送
     */
	private InternetAddress[] listRecipientsSC = null;
	/**
	 *  获得密送
	 *
	 *  @return 密送
	 */
	public InternetAddress[] getListRecipientsSC() { return listRecipientsSC; }
	/**
	 *  设置密送
	 *
	 *  @param listRecipientsSC 密送
	 */
	public void setListRecipientsSC(InternetAddress[] listRecipientsSC) { this.listRecipientsSC = listRecipientsSC; }

	/**
	 * 主题
	 */
	private String subject =  "";
	/**
	 *  获得主题
	 *
	 *  @return 主题
	 */
	public String getSubject() { return subject; }
	/**
	 *  设置主题
	 *
	 *  @param subject 主题
	 */
	public void setSubject(String subject) { this.subject = subject; }

	/**
     * 邮件服务器属性加载器
     */
	private Properties props = new Properties();
	/**
	 *  获得邮件服务器属性加载器
	 *
	 *  @return 邮件服务器属性加载器
	 */
	public Properties getProps() { return props; }
	/**
	 *  设置邮件服务器属性加载器
	 *
	 *  @param props 邮件服务器属性加载器
	 */
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
			throw new IllegalStateException("SMTP 配置不完整，请检查 smtp.ini，并确认已设置环境变量 CDTEST_SMTP_PASSWORD");
		}
		
		if(!receiveMailAccountTest.isEmpty() || (listRecipientsMC != null)){
			GFile.writeStringToGuideBottom("GMail Params Loaded");
		}else {
			GFile.writeStringToBottom(GSys.GUIDE ,"ERROR----" + "One of these email params from smtpConfig may has no value, Please check again!");
			throw new IllegalStateException("邮件收发账号配置不完整，请检查 smtp.ini");
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
	 *
	 * @throws MessagingException 连接邮件服务器或认证失败时抛出
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
