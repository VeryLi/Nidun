package com.idun.common;


import com.idun.common.mail.Mail;

public class SendTXMail {
    public static void main(String[] args){
        Mail mail = new Mail("This is a test email.");
        mail.send("weili.chen@yeadun.com");
    }
}
