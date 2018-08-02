
package com.imooc.utils;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5加密算法,规定调用接口传输数据的加密规则
 * @author Acer
 *
 */
public class UtilMd5 implements Serializable {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * @param origin
	 *            String
	 * @return String
	 * @throws Exception
	 */
	public static String getMD5Encode(String origin) throws Exception {
		origin=origin+"ipayWeb";
		if (!inited) {
			throw new Exception("MD5 算法实例初始化错误！");
		}
		if (origin == null) {
			return null;
		}
		byte[] temp = null;

		synchronized (md) {
			temp = md.digest(origin.getBytes());
		}

		return byteArrayToHexString(temp);

	}

	private static MessageDigest md = null;
	private static boolean inited = false;
	static {
		try {
			md = MessageDigest.getInstance("MD5");
			inited = true;
		} catch (NoSuchAlgorithmException ex) {
			inited = false;
		}
	}

	public static void main(String[] args) {
		try {
			// System.err.println(getMD5Encode("b")+"
			// "+getMD5Encode("a").length()); 
			//111111  77caacab2e7f4ef1f5d97c39dcbf73df
			//123456 0c3f0073a3c2e12a63040b0ab9ee91f0
//			String str ="5r4c8dd9";
			
//			String str = "free_2012";
			
			String str = "647.99_00004_E160421225431449_20160421_05B7F13E5CE154DAF209439D99428845";
			
			System.out.println(getMD5Encode(str));
			//c4c2ae16235285a01f8a2208c5499857
			//System.out.println(getMD5Encode(str).length());
			
			System.out.println(md5(str));
			
			System.out.println((int)Math.ceil(1200.0 / 1000));
			
			//System.out.println(md5(str).length());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** *基本的** */
	public static String md5(String str) {
		String s = str;
		if (s == null) {
			return "";
		} else {
			String value = null;
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException ex) {
			}
			sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
			try {
				value = baseEncoder.encode(md5.digest(s.getBytes("utf-8")));
			} catch (Exception ex) {

			}
			return value;
		}
	}

}