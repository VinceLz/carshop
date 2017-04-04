package com.xawl.car.util;



import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * ClassName: RequestUtils <br/>
 * Function: 交易请求工具类 . <br/>
 * date: 2016年10月9日 下午5:33:07 <br/>
 *
 * @author 清算项目组
 * @version 2.0
 * @since JDK 1.7
 */
public class RequestUtils {
	
	
	/**
	 * 
	 * getReqMap: 获取请求参数并转换为map . <br/>
	 * 
	 * @author tolly
	 * @return Map<String, String>
	 * @since JDK 1.7
	 */
	public static Map<String, String> getReqMap(HttpServletRequest req) {
		Map<String, String> params = Maps.newHashMap();
		HttpServletRequest request = req;
		Map<?, ?> requestParams = request.getParameterMap();
		for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = StringUtils.EMPTY;
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, StringUtils.trimToEmpty(valueStr));// 去除多余的空格
		}
		return params;
	}
	
	/**
	 * 
	 * getReqStr: 请求参数转换为字符串 . <br/>
	 *
	 * @author tolly
	 * @return String
	 * @since JDK 1.7
	 */
	public static String getReqStr(HttpServletRequest req) {
		Map<String, String> params = getReqMap(req);
		String retStr = StringUtils.EMPTY;
		StringBuilder sbd = new StringBuilder(StringUtils.EMPTY);
		for (Object key : params.keySet()) {
			sbd.append(key.toString()).append("=").append(StringUtils.trimToEmpty(params.get(key.toString()))).append("&");
		}
		if (sbd.length() >= 1) {
			retStr = sbd.subSequence(0, sbd.length() - 1).toString();
		}
		return retStr;
	}
}
