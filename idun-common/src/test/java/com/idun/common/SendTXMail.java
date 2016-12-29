package com.idun.common;


import com.idun.common.util.MailUtil;

public class SendTXMail {
    public static void main(String[] args){
        MailUtil mailUtil = new MailUtil("This is a test email.");
        mailUtil.send("weili.chen@yeadun.com");
    }
}
