package com.example.otp_test.Service;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailClass {
    public void setSubjectAndMessage(String subject, String message,String email){
        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");
        Session session = Session.getInstance(properties, null);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom("Test");
            msg.setRecipients(Message.RecipientType.TO,email);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(message);
            /// Set your gmail id and password. and MAKE SURE TO ENABLE ACCESS TO LESS SECURE OPTION IN ACCOUNT SETTING
             String senderGmailId = "YOUREMAILID";
            String senderGmailPassword = "YOURPASSWORD";
            //Ends here
            Transport.send(msg, senderGmailId, senderGmailPassword);
        } catch (MessagingException mex) {
            System.out.println("send failed, exception: " + mex);
        }
    }
}
