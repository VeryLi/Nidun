package com.idun.common.util;
import com.sun.mail.util.MailSSLSocketFactory;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {

    // Email Server properties.
    private String account  = "service01@yeadun.com";//登录用户名
    private String pass     = "YeaDun789";           //登录密码
    private String from     = "service01@yeadun.com";  //发件地址
    private String host     = "smtp.exmail.qq.com";  //服务器地址
    private String port     = "465";                 //端口
    private String protocol = "smtp";                //协议
    private Properties prop = System.getProperties();

    // Email context.
    private String emailUser;
    private String subject;
    private String text;

    public MailUtil(String emailUser, String subject, String text){
        this.emailUser = emailUser;
        this.subject = subject;
        this.text = text;
        init();
    }

    public MailUtil(String text){
        this.emailUser = "YeaDun";
        this.subject = "您收到 <羿盾科技> 的通知";
        this.text = text;
        init();
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    //用户名密码验证，需要实现抽象类Authenticator的抽象方法PasswordAuthentication
    private class MyAuthenricator extends Authenticator {
        String u = null;
        String p = null;
        MyAuthenricator(String u, String p){
            this.u=u;
            this.p=p;
        }
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(u,p);
        }
    }

    private void init(){
        //协议
        prop.setProperty("mail.transport.protocol", protocol);
        //服务器
        prop.setProperty("mail.smtp.host", host);
        //端口
        prop.setProperty("mail.smtp.port", port);
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
    }

    public void send(String to){
        Session session = Session.getDefaultInstance(prop,
                new MyAuthenricator(account, pass));
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(from,emailUser));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setSentDate(new Date());
            mimeMessage.setText(text);
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
