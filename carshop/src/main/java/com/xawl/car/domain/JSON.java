package com.xawl.car.domain;

import java.io.Serializable;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

public class JSON implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSONObject json;

	public JSON(JSONObject jsonObject) {
		jsonConfig.setJsonPropertyFilter(filter);
		this.json = jsonObject;
	}

	public void add(String key, Object value) {
		json.element(key, value, jsonConfig);
	}

	@Override
	public String toString() {
		return json.toString();
	}

	JsonConfig jsonConfig = new JsonConfig();
	PropertyFilter filter = new PropertyFilter() {
		public boolean apply(Object object, String fieldName, Object fieldValue) {
			return null == fieldValue || "".equals(fieldValue);
		}
	};

}
