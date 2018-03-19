package org.uy.record.tools;


import org.apache.log4j.Logger;
import org.uy.record.system.SystemCount;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.Properties;

public class MailTool{

    private final Logger log = Logger.getLogger(MailTool.class);

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

    public void sendMail(String title, String html, File[] files, String[] fileNames){
        try{

            Session session = Session.getInstance(props);

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(myEmailAccount, "汇报", "UTF-8"));

            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMailAccount, "org.uy", "UTF-8"));

            message.setSubject(title, "UTF-8");

            message.setSentDate(new Date());

            MimeMultipart msgMultipart = new MimeMultipart("mixed");//混合的组合关系

            if(files != null && files.length != 0){
                for(int i=0;i<files.length;i++){
                    MimeBodyPart attch = new MimeBodyPart();
                    DataSource ds = new FileDataSource(files[i]);
                    DataHandler dh = new DataHandler(ds);
                    attch.setDataHandler(dh);
                    attch.setFileName(MimeUtility.encodeText(fileNames[i]));
                    msgMultipart.addBodyPart(attch);
                }
            }

            //正文内容
            MimeBodyPart content = new MimeBodyPart();
            MimeMultipart bodyMultipart  = new MimeMultipart("related");
            //设置内容为正文
            content.setContent(bodyMultipart);
            msgMultipart.addBodyPart(content);
            content.setContent(html,"text/html;charset=utf-8");

            message.setContent(msgMultipart);

            message.saveChanges();

            Transport transport = session.getTransport();
            transport.connect(myEmailAccount, myEmailPassword);

            transport.sendMessage(message, message.getAllRecipients());

            transport.close();
        } catch (Exception e) {
            log.error("邮件发送异常 "+e.toString());
            SystemCount.errorCount++;
        }
    }
}
