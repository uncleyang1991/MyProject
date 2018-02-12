package org.uy.record.tools;

import org.springframework.stereotype.Component;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Component
public class MailTool {

    //邮箱参数
    private final Properties props = new Properties();

    private final String myEmailAccount = "uypingtai01@163.com";
    private final String myEmailPassword = "c327433910";

    private final String receiveMailAccount = "846819045@qq.com";

    public MailTool() {
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.163.com");
        props.setProperty("mail.smtp.auth", "true");
    }

    public void sendMail(String title,String text){
        try{
            Session session = Session.getInstance(props);

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(myEmailAccount, "记录预警", "UTF-8"));

            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMailAccount, "羊叔", "UTF-8"));

            message.setSubject(title, "UTF-8");

            message.setContent(text, "text/html;charset=UTF-8");

            message.setSentDate(new Date());

            message.saveChanges();

            Transport transport = session.getTransport();
            transport.connect(myEmailAccount, myEmailPassword);

            transport.sendMessage(message, message.getAllRecipients());

            transport.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MailTool().sendMail("测试邮件1","你好,羊叔");
    }
}
