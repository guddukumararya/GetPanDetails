package org.example.panstatus.utils;

import org.example.panstatus.service.CVLInquiry;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
public class DecryptionUtils {
    public static void main(String args[]) {
        try {
            String aesKey = "4c8c98585cfb425bb8ee3a003d535c8c";
            String data = "{'username':'WEBADMIN','poscode':'5100262320','password':'8VUkutfRyU'}";
            String panData = "{'pan':'CXDPK1880Q','poscode':'5100262320'}";
            String EncryptedCVLToken = EncryptionUtils.getEncryptedText(data);
            String EncryptedResponsePanData = EncryptionUtils.getEncryptedText(panData);
            String panResponse = CVLInquiry.requestPan(EncryptedResponsePanData, EncryptedCVLToken);
            System.out.println("Pan Response : "+panResponse);
            String[] splittedStrings = panResponse.split(":");
            String iv = splittedStrings[0];
            String encryptedText = splittedStrings[1];
            String DecryptedResponseData = decryptString(aesKey, encryptedText, iv);
            System.out.println("Decrypted Response Data: " + DecryptedResponseData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String decryptString(String aesKey, String encryptedText, String iv) {
        String decryptedText = null;
        try {
            byte[] keyBytes = Base64.getDecoder().decode(aesKey);
            byte[] ivBytes = Base64.getUrlDecoder().decode(iv);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(ivBytes));
            byte[] encryptedBytes = Base64.getUrlDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            decryptedText = new String(decryptedBytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
}