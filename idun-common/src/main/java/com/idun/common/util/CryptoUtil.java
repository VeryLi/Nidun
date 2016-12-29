package com.idun.common.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.cipher.CryptoCipherFactory.CipherProvider;
import org.apache.commons.crypto.utils.Utils;

/**
 * the CryptoUtil class is used for encryption/decryption. The methond enCrypto() and deCrypto
 * is thread-unsafe.
 * */
public class CryptoUtil {

      // this crypto key, its length must be 16.
      private static final String cryptoKey = "YeaDun123.crypto";
      // this crypto vector key, its length must be 16.
      private static final String ivKey = "1234567890123456";
      private static final String transform = "AES/CBC/PKCS5Padding";

      private SecretKeySpec key;
      private IvParameterSpec iv;
      private Properties properties;
      private int finalNum;

      public CryptoUtil(){
           this(cryptoKey, ivKey);
      }

      public CryptoUtil(String cryptoKey, String ivKey){
            key = new SecretKeySpec(getUTF8Bytes(cryptoKey),"AES");
            iv = new IvParameterSpec(getUTF8Bytes(ivKey));
            properties = new Properties();
            properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CipherProvider.OPENSSL.getClassName());
      }

      /**
       * this method is used for encrypting string type message.
       *
       * @param input the message which need be encrypted, type is String.
       * @return return the byte[] which has been encrypted.
       **/
      public byte[] enCrypto(String input){
            return enCrypto(getUTF8Bytes(input));
      }

      /**
       * this method is used for encrypting byte array type message.
       *
       * @param input the message which need be encrypted, type is byte[].
       * @return return the byte[] which has been encrypted.
       **/
      public byte[] enCrypto(byte[] input){
            //Creates a CryptoCipher instance with the transformation and properties.
            CryptoCipher encipher ;
            byte[] encode = new byte[input.length + 20];
            try {
                  encipher = Utils.getCipherInstance(transform, properties);
                  //Initializes the cipher with ENCRYPT_MODE, key and iv.
                  encipher.init(Cipher.ENCRYPT_MODE, key, iv);

                  //Continues a multiple-part encryption/decryption operation for byte array.
                  int updateBytes = encipher.update(input, 0, input.length, encode, 0);

                  //We must call doFinal at the end of encryption/decryption.
                  int finalBytes = encipher.doFinal(input, 0, 0, encode, updateBytes);
                  finalNum = updateBytes + finalBytes;

                  //Closes the cipher.
                  encipher.close();
            } catch (IOException | InvalidKeyException | InvalidAlgorithmParameterException |
                    BadPaddingException | IllegalBlockSizeException | ShortBufferException e) {
                  e.printStackTrace();
            }
            return encode;

      }

      /**
       * this method is used for decrypting byte array type message.
       *
       * @param input the message which need be decrypted, type is byte[].
       * @return return the byte[] which has been decrypted.
       **/
      public byte[] deCrypto(byte[] input){
            byte[] decoded = new byte[input.length + 20];

            // Now reverse the process using a different implementation with the same settings
            properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CipherProvider.JCE.getClassName());
            CryptoCipher decipher;
            try {
                  decipher = Utils.getCipherInstance(transform, properties);
                  decipher.init(Cipher.DECRYPT_MODE, key, iv);
                  decipher.doFinal(input, 0, finalNum, decoded, 0);
            } catch (IOException | InvalidKeyException | InvalidAlgorithmParameterException |
                    BadPaddingException | IllegalBlockSizeException | ShortBufferException e) {
                  e.printStackTrace();
            }
            return decoded;
      }

      /**
       * Converts String to UTF8 bytes
       *
       * @param input the input string
       * @return UTF8 bytes
       */
      private byte[] getUTF8Bytes(String input) {
            return input.getBytes(StandardCharsets.UTF_8);
      }

      /**
       * Usage Demo.
       * */
      public static void main(String[] args) throws Exception {
            String test = "this is my crypto test.";

            // 1. step create CrytoUtil instance.
            CryptoUtil crytoUtil = new CryptoUtil();
            // 2. Encrypto your message and return byte array which has been crypted.
            byte[] enCode = crytoUtil.enCrypto(test);
            // 3. Decrypto your message and return byte array which has bee decrypted.
            byte[] deCode = crytoUtil.deCrypto(enCode);

            System.out.println(new String(deCode));
      }


}
