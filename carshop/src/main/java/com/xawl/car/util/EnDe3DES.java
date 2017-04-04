package com.xawl.car.util;



import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * ClassName: EnDe3DES <br/>
 * Function: DES加解密 . <br/>
 * date: 2016年10月9日 下午5:20:36 <br/>
 *
 * @author 清算项目组
 * @version 2.0
 * @since JDK 1.7
 */
public class EnDe3DES {
	
	private static String DesCharset = "UTF-8";// 加密算法字符集
	
	private static String Algorithm = "DESede";
	
	static {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}
	
	public static byte[] encode(byte[] input, byte[] key)
		throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] cipherByte = c1.doFinal(input);
		return cipherByte;
	}
	
	public static byte[] decode(byte[] input, byte[] key)
		throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		byte[] clearByte = c1.doFinal(input);
		return clearByte;
	}
	
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}
	
	public static byte[] hexStr2ByteArr(String strIn)
		throws Exception {
		byte[] arrB = strIn.getBytes(DesCharset);
		int iLen = arrB.length;
		byte[] arrOut = new byte[iLen / 2];
		
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
	
	public static byte[] hexStr2ByteArr(String strIn, String charSet)
		throws Exception {
		byte[] arrB = strIn.getBytes(charSet);
		int iLen = arrB.length;
		byte[] arrOut = new byte[iLen / 2];
		
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
	
	/**
	 * 
	 * EnycrptDes: 加密，默认字符集 . <br/>
	 * 
	 * @author tolly
	 * @param src
	 * @param key 24位定长
	 * @return String
	 * @throws Exception
	 */
	public static String EnycrptDes(String src, String key)
		throws Exception {
		key = getKeyLen(key, 24);// 生成24位密钥
		String ret = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(src)) {
			ret = byte2hex(encode(src.getBytes(DesCharset), key.getBytes(DesCharset)));
		}
		return ret;
	}
	
	/**
	 * 
	 * EnycrptDes: 加密，自定义字符集 . <br/>
	 * 
	 * @author tolly
	 * @param src
	 * @param key 24位定长
	 * @param charSet
	 * @return String
	 * @throws Exception
	 */
	public static String EnycrptDes(String src, String key, String charSet)
		throws Exception {
		key = getKeyLen(key, 24);// 生成24位密钥
		String ret = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(src)) {
			ret = byte2hex(encode(src.getBytes(charSet), key.getBytes(charSet)));
		}
		return ret;
	}
	
	/**
	 * 
	 * DeEnycrptDes: 解密，默认字符集 . <br/>
	 * 
	 * @author tolly
	 * @param src
	 * @param key 24位定长
	 * @return String
	 * @throws Exception
	 */
	public static String DeEnycrptDes(String src, String key)
		throws Exception {
		key = getKeyLen(key, 24);// 生成24位密钥
		String ret = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(src)) {
			ret = (new String(decode(hexStr2ByteArr(src), key.getBytes(DesCharset)), DesCharset));// 不同平台编码
		}
		return ret;
	}
	
	/**
	 * 
	 * DeEnycrptDes: 解密，自定义字符集 . <br/>
	 * 
	 * @author tolly
	 * @param src
	 * @param key 24位定长
	 * @param charSet
	 * @return String
	 * @throws Exception
	 */
	public static String DeEnycrptDes(String src, String key, String charSet)
		throws Exception {
		key = getKeyLen(key, 24);// 生成24位密钥
		String ret = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(src)) {
			ret = (new String(decode(hexStr2ByteArr(src, charSet), key.getBytes(charSet)), charSet));// 不同平台编码
		}
		return ret;
	}
	
	/**
	 * 
	 * getKeyLen: 字符串后固定长度位，不够左补0 . <br/>
	 * 
	 * @author tolly
	 * @param key
	 * @param len
	 * @return String
	 */
	public static String getKeyLen(String key, int len) {
		String keyLen = StringUtils.EMPTY;
		if (key.length() >= len) {
			keyLen = StringUtils.substring(key, key.length() - len);
		} else {
			keyLen = StringUtils.leftPad(key, len, "0");
		}
		return keyLen;
	}
}
