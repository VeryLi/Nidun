package com.idun.common;

import com.sun.mail.util.MailSSLSocketFactory;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Send163Mail {
    public static void main(String [] args){

//        String to = "397954006@qq.com";
        String to = "xueping.wen@yeadun.com";

        String from = "chenweili121@163.com";

        String host = "smtp.163.com";
        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.prot", 25);
        properties.put("mail.smtp.auth", true);

        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("chenweili121@163.com", "chenweili121");
            }
        });

        try{
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            message.setSubject("This is the Subject Line!");

            message.setText("Hello");

            Transport.send(message);
            System.out.println("hello");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
