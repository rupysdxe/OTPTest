package com.example.otp_test.Service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Service
public class EmailClass {
    private final String senderGmailId="dc.rupys156@gmail.com";
    private final String senderGmailPassword="neverhackgmail";
    private Session session;
    private MimeMessage mimeMessage;

    public EmailClass(){
        session();
    }
    private void session(){
        Properties properties=new Properties();
        ///host set
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderGmailId,senderGmailPassword);
            }
        });
        session.setDebug(true);
        this.session=session;
    }
    public void setSubjectAndMessage(String subject, String message,String receiverGmailId){
        try {
            MimeMessage mimeMessage=new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(receiverGmailId));
            mimeMessage.setSubject(subject);
            mimeMessage.setContent("<h6>"+message+"</h6>","text/html");
            this.mimeMessage=mimeMessage;
            send();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
    public void setMessageAndAttachment(String subject, String message, File file,String receiverGmailId){
        try {
            MimeMessage mimeMessage=new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(receiverGmailId));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            ///Attaching the file
            MimeMultipart attachment=new MimeMultipart();
            MimeBodyPart mimeBodyPart=new MimeBodyPart();
            try {
                mimeBodyPart.attachFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            attachment.addBodyPart(mimeBodyPart);

            mimeMessage.setContent(attachment);
            ///Send the message
            this.mimeMessage=mimeMessage;
            send();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
    public void send(){
        try {
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
