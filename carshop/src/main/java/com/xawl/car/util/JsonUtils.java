package com.xawl.car.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 淘淘商城自定义响应结构
 */

public class JsonUtils {
private static JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
	
	/**
	 * 从Json构造MAP
	 * @param jsonStr
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> buildMapFormJson(String jsonStr) {
		Map<String, Object> objMap = Maps.newHashMap();
		if (StringUtils.isBlank(jsonStr)) {
			return objMap;
		}
		objMap = jsonBinder.fromJson(jsonStr, HashMap.class);
		return objMap;
	}
	
	/**
	 * json串转化为List
	 * 
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> buildListFormJson(String jsonStr) {
		List<Map<String, Object>> objList = Lists.newArrayList();
		objList = jsonBinder.fromJson(jsonStr, ArrayList.class);
		return objList;
	}

	/**
	 * 对象转化为json串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJosnFromObject(Object object) {
		return jsonBinder.toJson(object);
	}


	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 将对象转换成json字符串。
	 * <p>
	 * Title: pojoToJson
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param data
	 * @return
	 */
	public static String objectToJson(Object data) {
		try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json结果集转化为对象
	 * 
	 * @param jsonData
	 *            json数据
	 * @param clazz
	 *            对象中的object类型
	 * @return
	 */
	public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
		try {
			T t = MAPPER.readValue(jsonData, beanType);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json数据转换成pojo对象list
	 * <p>
	 * Title: jsonToList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
		JavaType javaType = MAPPER.getTypeFactory().constructParametricType(
				List.class, beanType);
		try {
			List<T> list = MAPPER.readValue(jsonData, javaType);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
