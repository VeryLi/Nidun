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
import org.glassfish.grizzly.http.util.Base64Utils;

/**
 * the CryptoUtil class is used for encryption/decryption. The methond enCrypto() and deCrypto
 * is thread-unsafe.
 * */
public class CryptoUtil {

      // this crypto key, its length must be 16.
      private static final String cryptoKey = "YeaDun123.com.cn";
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

      private CryptoUtil(String cryptoKey, String ivKey){
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
      private byte[] enCrypto(String input){
            return enCrypto(getUTF8Bytes(input));
      }

      public String enCryptoReturnStr(String input){
            return Base64Utils.encodeToString(enCrypto(input), true);
      }

      /**
       * this method is used for encrypting byte array type message.
       *
       * @param input the message which need be encrypted, type is byte[].
       * @return return the byte[] which has been encrypted.
       **/
      private byte[] enCrypto(byte[] input){
            //Creates a CryptoCipher instance with the transformation and properties.
            CryptoCipher encipher ;
            byte[] encode = new byte[input.length + 16];
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

      private byte[] deCrypto(String input, int finalNum) {
            return deCrypto(Base64Utils.decode(input), finalNum);
      }

      public String deCryptoReturnStr(String input, int finalNum) {
            return new String(deCrypto(input, finalNum));
      }

      /**
       * this method is used for decrypting byte array type message.
       *
       * @param input the message which need be decrypted, type is byte[].
       * @return return the byte[] which has been decrypted.
       **/
      private byte[] deCrypto(byte[] input, int finalNum){
            byte[] decoded = new byte[input.length + 16];

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

      public int getFinalNum(){
            return finalNum;
      }

      /**
       * Converts String to UTF8 bytes.
       *
       * @param input the input string
       * @return UTF8 bytes
       */
      private byte[] getUTF8Bytes(String input) {
            return input.getBytes(StandardCharsets.UTF_8);
      }
}
