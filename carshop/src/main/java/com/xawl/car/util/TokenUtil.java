package com.xawl.car.util;

import java.security.MessageDigest;

public class TokenUtil {
	static String token = null;

	public static String generateToken(String uniq) {
		synchronized (TokenUtil.class) {
			token = MD5(System.currentTimeMillis() + uniq
					+ System.currentTimeMillis());
		}
		return token;
	}

	public final static String MD5(String s) {
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			return byte2hex(mdInst.digest());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String byte2hex(byte[] b) {
		StringBuilder sbDes = new StringBuilder();
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				sbDes.append("0");
			}
			sbDes.append(tmp);
		}
		return sbDes.toString();
	}
	

}