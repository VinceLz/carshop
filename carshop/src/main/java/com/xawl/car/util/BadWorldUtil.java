package com.xawl.car.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class BadWorldUtil {
	private String src;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	private Map readProperties() {
		Properties props = new Properties();
		Map map = new HashMap();

		try {

			InputStream in = BadWorldUtil.class.getClassLoader()
					.getResourceAsStream(src);
			props.load(in);

			Enumeration en = props.propertyNames();

			while (en.hasMoreElements()) {

				String key = (String) en.nextElement();

				String value = props.getProperty(key);

				map.put(key, value);// 把properties文件中的key-value存放到一个map中

			}
			return map;

		} catch (Exception e) {

			e.printStackTrace();

		}
		return null;

	}

	public String replaceCheck(String name) {
		Map map = readProperties();
		Set<String> keys = map.keySet();
		Iterator<String> iter = keys.iterator();
		while (iter.hasNext()) {

			String key = iter.next();

			String value = (String) map.get(key);
			if (name.contains(key)) {
				name = name.replace(key, value);// 对于符合map中的key值实现替换功能

			}
		}
		return name;

	}

}
