package AutoTest;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * 邮件
 */
public class GMail {
	
    /*
     * 是否允许发送邮件
     */
	public static boolean IsMailOn = false;
	
    /*
     * 协议类型
     */
	public static String ServerProtocol = "";

    /*
     * 地址
     */
	public static String ServerHost = "*.example.com";
	
    /*
     * 后缀
     */
	public static String ServerSuffix = "@example.com";
	
    /*
     * 账号
     */
    public static String ServerAccount = "*@example.com";
    
    /*
     * 密码
     */
    public static String ServerPassword = "********";
    
    /*
     * 端口
     */
    public static String ServerPort = "***";
    
    /*
     * 认证方式
     */
    public static String ServerAuth = "";
    
    /*
     * 是否开启tls
     */
    public static String ServerTls = "";
    
    /*
     * 是否开启debug
     */
    public static String ServerDebug = "";
    
    /*
     * 收件人邮箱（替换为自己知道的有效邮箱）
     */
    public static String receiveMailAccountDev = "";//开发者
    public static String receiveMailAccountTest = "";//测试员
    public static String receiveMailAccountManager = "";//管理员
    public static String receiveMailAccountReseacher = "";//需求员
    
    public static String strSubject =  ""; //报告主题
    
    /*
     * 邮件服务器属性加载器
     */
    public static Properties props = new Properties();  
    
    /**
     * 初始化邮件服务器 暂时使用内置参数
     * 
     * @param bDebug 是否启用debug模式，用于调试
     */
    public static void initEmailServer() {
    	// 1. 配置属性
        props.setProperty("mail.transport.protocol", ServerProtocol);// 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", ServerHost);// 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", ServerAuth);// 需要请求认证
        props.setProperty("mail.smtp.port", ServerPort);
        props.setProperty("mail.debug", ServerDebug); //控制台打印信息，调试
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
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, StringBuffer mailContent) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
 
        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, strSubject, "UTF-8"));
 
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMailAccountTest, "hewei", "UTF-8"));
        //抄送
        InternetAddress[] listRecipientsCC = {new InternetAddress(receiveMailAccountManager, "hewei", "UTF-8"), new InternetAddress(receiveMailAccountReseacher, "hewei", "UTF-8")}; 
        message.setRecipients(MimeMessage.RecipientType.CC, listRecipientsCC);
        //密送
        message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(receiveMailAccountDev, "hewei", "UTF-8"));
 
        // 4. Subject: 邮件主题
        message.setSubject(strSubject, "UTF-8");
 
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(mailContent.toString(), "text/html;charset=UTF-8");
        
        // 6. 设置发件时间
        message.setSentDate(new Date());
 
        // 7. 保存设置
        message.saveChanges();
 
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
            MimeMessage message = createMimeMessage(session, ServerAccount, receiveMailAccountTest, GFile.html2StringBuffer(htmlPath));
     
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
            transport.connect(ServerAccount, ServerPassword);
     
            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
     
            // 7. 关闭连接
            transport.close();
            
            GLog.logShowConsole(GMsg.MSG_EMAIL[0]);
    	}catch(Exception e){
    		GLog.logShowConsole(GMsg.MSG_EMAIL[1]);
			e.printStackTrace();
    	}
    }
    
    public static void main(String[] args) {
    	initEmailServer();
    	sentEmail("./html/template.html","");
    }
}
