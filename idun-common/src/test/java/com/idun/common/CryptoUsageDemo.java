package com.idun.common;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CryptoUsageDemo {
    public static void main(String[] args) throws Exception {
        String test = "cbw@123456$$HudoP";
        // encode
        BASE64Encoder e = new BASE64Encoder();
        String a = e.encode(test.getBytes());
        System.out.println(a);
        // decode
        BASE64Decoder d = new BASE64Decoder();
        String result = new String(d.decodeBuffer(a));
        System.out.println(result);


//        // 1. step create CrytoUtil instance.
//        AECCryptoUtil cryptoUtil = new AECCryptoUtil();
//
//        // 2. Encrypto your message and return byte array which has been crypted.
//        String encode = cryptoUtil.enCryptoReturnStr(test);
//        System.out.println("加密后字符串 ： " + encode);
//
//        // 3. Decrypto your message and return byte array which has bee decrypted.
//        String decode = cryptoUtil.deCryptoReturnStr(encode, cryptoUtil.getFinalNum());
//        System.out.println("解密后字符串 ： " + decode);
    }
}
