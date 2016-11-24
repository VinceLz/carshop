package com.xawl.car.util;

public class ArrayUtil {

	public static String[] stringImage2Array(String img) {
		if (img==null||img.isEmpty()) {
			return null;
		}
		String[] split = img.split(",");
		return split;
		// StringBuffer result = new StringBuffer();
		// result.append("[");
		// for (int i = 0; i < split.length; i++) {
		// if (i == split.length - 1) {
		// result.append("\"").append(split[i]).append("\"");
		// } else {
		// result.append("\"").append(split[i]).append("\",");
		// }
		// }
		// result.append("]");
		// return result.toString();

	}

	public static void main(String[] args) {
		// String sq =
		// "/img/95532daf-69c6-4d16-b98c-bef534b21c47惹人20160814164713338.jpg,stringImage2Array";
		// String a = stringImage2Array(sq);
		// System.out.println(a);

	}

	public static String Array2String(String[] a) {
		if (a == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			builder.append(a[i] + ",");
		}
		return builder.substring(0, builder.length() - 1);
	}
}
