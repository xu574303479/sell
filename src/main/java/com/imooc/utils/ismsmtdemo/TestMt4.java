/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imooc.utils.ismsmtdemo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author Administrator
 */
public class TestMt4 {

    public static void test() throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        String ip = "210.51.190.232";
        int port = 8085;
        HttpClientUtil util = new HttpClientUtil(ip, port, "/mt/MT4.ashx");

        // 用户名
        String user = "";

        //密码：
        String pwd = "";

        String key=""; // 秘钥 DES, 8 位 byte

        String ServiceID = "SEND";

        // 目的号码
        String dest = "8613701387330";

        // 原号码
        String sender = "8613701234567";

        // 短信内容
        String msg = "大家好 test mt4 232:8085 。。";

        // UTF-16BE

        byte[] bytSource = null;
        //bytSource = msg.getBytes("GBK");  //&codec=15
        //bytSource = msg.getBytes("ISO-8859-1"); //&codec=3
        bytSource = msg.getBytes("UTF-16BE"); //&codec=8
        //bytSource = msg.getBytes("ASCII");
        String hex = EncryptString(bytSource, key);

        hex = hex.trim() + "&codec=8";
      



        // GET
        System.out.println("msgid = " + util.sendGetMessage(user, pwd, ServiceID, dest, sender, hex));

        //POST
          System.out.println("msgid = " + util.sendPostMessage(user, pwd, ServiceID, dest, sender, hex));
    }

    /// <summary>
    /// 对字符串进行DES加密
    /// </summary>
    /// <param >将要加密的 byte[] 流</param>
    /// <param >密钥值（需为8位字符串）</param>
    /// <returns></returns>
    public static String EncryptString(byte[] sInputBytes, String sKey) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] DESkey = sKey.getBytes();// 设置密钥
        byte[] DESIV = sKey.getBytes();// 设置向量

        AlgorithmParameterSpec iv = null;// 加密算法的参数接口，IvParameterSpec是它的一个实现
        Key key = null;

        DESKeySpec keySpec = new DESKeySpec(DESkey);// 设置密钥参数
        iv = new IvParameterSpec(DESIV);// 设置向量
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
        key = keyFactory.generateSecret(keySpec);// 得到密钥对象

        Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher
        enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量
        byte[] pasByte = enCipher.doFinal(sInputBytes);
        return bytesToHexString(pasByte);
    }

    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
}
