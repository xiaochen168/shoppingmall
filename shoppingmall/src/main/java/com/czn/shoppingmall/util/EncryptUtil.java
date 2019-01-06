package com.czn.shoppingmall.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
    private static final String crypt[] = {"0","a","!", "1","b","@","c","2","#","d", "3", "e","$", "4", "f","%","5",
            "h","^","6","i","&", "7","j", "*","8", "k","9","l" };

    public static String byteArrayToEncryptString(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length ;i++) {
            sb.append(byteToEncryptString(bytes[i]));
        }
        return sb.toString();
    }

    private static String byteToEncryptString(byte b){
        int n = b;
        if (n < 0){
            n += 256;
        }
        int d1 = n / 29;
        int d2 = n % 29;
        return crypt[d1] + crypt[d2];
    }

    private static String encode(String origin, String charset) {
        String encryptString = null;
            try {
                encryptString = new String(origin);
                MessageDigest md = MessageDigest.getInstance("MD5");
                if (null == charset || "".equals(charset)){
                    encryptString = byteArrayToEncryptString(md.digest(encryptString.getBytes()));
                } else {
                    encryptString = byteArrayToEncryptString(md.digest(encryptString.getBytes(charset)));
                }
            } catch (Exception e) {
            }
        return encryptString.toUpperCase();
    }

    public static String encoderUTF8(String origin){
        return encode(origin,"utf-8");
    }

}
