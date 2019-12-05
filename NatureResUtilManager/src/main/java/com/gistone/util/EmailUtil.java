package com.gistone.util;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {


//    private static String mailFrom = null;// 指明邮件的发件人
//    private static String password_mailFrom = null;// 指明邮件的发件人登陆密码
//    private static String mailTo = null;	// 指明邮件的收件人
//    private static String mailTittle = null;// 邮件的标题
//    private static String mailText =null;	// 邮件的文本内容
//    private static String mail_host =null;	// 邮件的服务器域名

    public static void main(String[] args) throws Exception {
        String mailFrom = "anhuihongxian@163.com";
        String password_mailFrom = "ahhx1234";
        String mailTo = "229496661@qq.com";
        String mailTittle="节日快乐2！";
        String mailText = "这是一个简单的邮件";
        String mail_host="smtp.163.com";

        sendMail(mailFrom,password_mailFrom,mailTo,mailTittle,mailText,mail_host);
    }


    /**
     *
     * @param mailfrom      发件邮箱
     * @param password_mailFrom  发件邮箱密码
     * @param mailTo        收件邮箱
     * @param mailTittle    邮件标题
     * @param mailText      邮件内容
     * @param mail_host    发件邮箱服务域名smtp
     * @return
     */
    public static boolean sendMail(String mailfrom,String password_mailFrom, String mailTo, String mailTittle, String mailText,String mail_host){
        try {
            Properties prop = new Properties();
            prop.setProperty("mail.host", mail_host);
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.auth", "true");
            // 1、创建session
            Session session = Session.getInstance(prop);
            // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
//            session.setDebug(true);
            // 2、通过session得到transport对象
            Transport ts = session.getTransport();
            // 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect(mail_host,mailfrom, password_mailFrom);
            // 创建邮件对象
            MimeMessage message = new MimeMessage(session);
            // 指明邮件的发件人
            message.setFrom(new InternetAddress(mailfrom));
            // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            // 邮件的标题
            message.setSubject(mailTittle);
            // 邮件的文本内容
            message.setContent(mailText, "text/html;charset=UTF-8");
            // 返回创建好的邮件对象

            // 5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();

            return true;
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }


}
