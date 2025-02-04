package org.example.panstatus.utils;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
public class EncryptionUtils {
    public static String getEncryptedText(String data) {
        try {
            String aesKey = "4c8c98585cfb425bb8ee3a003d535c8c";
            byte[] ivForEncryption = generateIV();
            String ivForPayload = Base64.getUrlEncoder().encodeToString(ivForEncryption);
            String encryptedData = encryptString(aesKey, data, ivForEncryption);
            String encryptedRequestData = ivForPayload + ":" + encryptedData;
            System.out.println("Encrypted Request Data: " + encryptedRequestData);
            return encryptedRequestData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static byte[] generateIV() {
        byte[] ivForEncryption = null;
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] iv = new byte[16];
            secureRandom.nextBytes(iv);
            ivForEncryption = iv;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ivForEncryption;
    }

    public static String encryptString(String aesKey, String data, byte[] iv) {
        String encryptedText = null;
        try {
            byte[] keyBytes = Base64.getDecoder().decode(aesKey);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] encryptedBytes = cipher.doFinal(data.getBytes("UTF-8"));
            encryptedText = Base64.getUrlEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }
}
