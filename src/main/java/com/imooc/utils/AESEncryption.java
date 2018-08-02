package com.imooc.utils;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;

/**
 * @ClassName: AESEncryption
 * @Description:
 * @Auther: xuhaibin-work-PC
 * @Date: 2018-07-30 17:57
 * @Version: 1.0.0
 */
public class AESEncryption implements Serializable {

    /**
     * @param src      要加密的字符串
     * @param password 　加密KEY
     * @return
     * @throws Exception
     */
    public static String encrypt(String src, String password) throws Exception {
        try {
            password = UtilMd5.getMD5Encode(password).substring(16);

            byte[] key = password.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] decryptText = cipher.doFinal(src.getBytes("utf-8"));
            return byte2hex(decryptText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param src      要解密的字符串
     * @param password 　解密KEY
     * @return
     */
    public static String decrypt(String src, String password) {
        try {
            password = UtilMd5.getMD5Encode(password).substring(16);

            byte[] key = password.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] hbyte = hex2byte(src);
            if (hbyte == null) {
                return null;
            }
            byte[] plainText = cipher.doFinal(hbyte);
            return new String(plainText, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串转成二进制
     *
     * @param strhex
     * @return
     */
    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }

    /**
     * 二进制转成字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    // ----------------------------------------------------------------test
    /* test */
    public static void main(String[] args) throws Exception {
        String pwd = "!&#123rasdfasdfasdfasdfasfqi23rupoweurqeruqperu*2015";
        // 9CCBBB1E50565519C83078C7426E59DA "15010060990"
        //
        String phone = encrypt("7osAwfzDzG4REr43", pwd);
        System.out.println(phone);
        pwd = decrypt(phone, pwd);
        System.out.println(pwd);


        String aaa = "";
        String bbb = null;

        boolean blank = StringUtils.isBlank(aaa);
        boolean blank2 = StringUtils.isBlank(bbb);

        System.out.println();

    }
}
