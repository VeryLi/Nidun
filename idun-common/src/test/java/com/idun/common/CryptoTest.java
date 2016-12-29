package com.idun.common;


import com.idun.common.util.CryptoUtil;

public class CryptoTest {
    public static void main(String[] args) throws Exception {
        String test = "cbw@123456$$HudoP";
        // 1. step create CrytoUtil instance.
        CryptoUtil cryptoUtil = new CryptoUtil();

        // 2. Encrypto your message and return byte array which has been crypted.
        String encode = cryptoUtil.enCryptoReturnStr(test);
        System.out.println("加密后字符串 ： " + encode);

        // 3. Decrypto your message and return byte array which has bee decrypted.
        String decode = cryptoUtil.deCryptoReturnStr(encode, cryptoUtil.getFinalNum());
        System.out.println("解密后字符串 ： " + decode);
    }
}
