package com.xawl.car.util;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

/**
 * 
 * @ClassName: AppUtils
 * @Description: App应用工具类
 * @author tolly
 * @date 2013-7-5 下午3:10:11
 *
 */
public class AppUtils {
	
	protected static Logger logger = LoggerFactory.getLogger(AppUtils.class);
	
	public final static String SIGNMETHOD = "MD5";
	
	/**
	 * URL编码
	 * @param src
	 * @param charset
	 * @return
	 */
	public static String URLEncode(String src,String charset){
		String srcEncode = StringUtils.EMPTY;
		if(StringUtils.isBlank(src)){
			srcEncode = src;
		}
		else{
			try {
				srcEncode = URLEncoder.encode(src, charset);
			} catch (UnsupportedEncodingException e) {
				//logger.error("URL编码错误:{}",e.getMessage());
			}
		}
		return srcEncode;
	}
	
	/**
	 * URL解码
	 * @param src
	 * @param charset
	 * @return
	 */
	public static String URLDecode(String src,String charset){
		String srcEncode = StringUtils.EMPTY;
		if(StringUtils.isBlank(src)){
			srcEncode = src;
		}
		else{
			try {
				srcEncode = URLDecoder.decode(src, charset);
			} catch (UnsupportedEncodingException e) {
				//logger.error("URL解码错误:{}",e.getMessage());
			}
		}
		return srcEncode;
	}
	
	/**
	 * 
	 * coverMap2String: 请求数据域转换为字符串,按照Key排序 . <br/>
	 *
	 * @author tolly
	 * @param dataMap
	 * @return String
	 */
	public static String coverMap2String(Map<String, String> dataMap) {
//		Predicate<Map.Entry<String, String>> predicate = entry -> !"sign".equals(entry.getKey());
		Predicate<Map.Entry<String, String>> predicate = new Predicate<Map.Entry<String, String>>() {
			@Override
			public boolean apply(Map.Entry<String, String> entry) {
				return !"sign".equals(entry.getKey());
			}
		};
		Map<String, String> treeMap = Maps.newTreeMap();
	    treeMap.putAll(Maps.filterEntries(dataMap, predicate));
		// null置为空
	    String join = Joiner.on('&').withKeyValueSeparator("=").useForNull("").join(treeMap);
	    return join;
	}
	
	public static void convertRetToMap(String res, Map<String, String> map) {
		if (StringUtils.isNotBlank(res)) {
			String[] resArray = res.split("&");
			if (resArray.length != 0)
				for (String arrayStr : resArray) {
					if (arrayStr == null)
						continue;
					if ("".equals(arrayStr.trim())) {
						continue;
					}
					int index = arrayStr.indexOf("=");
					if (-1 == index) {
						continue;
					}
					map.put(arrayStr.substring(0, index), arrayStr.substring(index + 1));
				}
		}
	}
	
	/**
	 * 
	 * validate: 同步响应签名验证 . <br/>
	 *
	 * @author tolly
	 * @param res
	 * @param signKey
	 * @param charset
	 * @return boolean
	 */
	public static boolean validate(String res, String signKey, String charset){

		if (StringUtils.isBlank(res)) {
			return false;
		}

		boolean ret = false;
		//签名报文
		Map<String, String> resMap = Maps.newHashMap();
		convertRetToMap(res,resMap);
		String signSrc = coverMap2String(resMap);
		//logger.debug("res="+res);
		//logger.debug(resMap.toString());
		String md5Str = StringUtils.EMPTY;
		try {
			md5Str = Crypto.GetMessageDigest(signKey, SIGNMETHOD, charset);
		} catch (Exception e) {
			//logger.error("获取MD5错误:{}", e.getMessage());
			return ret;
		}
		signSrc = (signSrc + "&" + md5Str);
		logger.info("签名报文:{}",signSrc);
		//签名
		String newSign = Crypto.GetMessageDigest(signSrc, SIGNMETHOD, charset);
		logger.info("签名:{}",newSign);
		String sign = resMap.get("sign");
		if(sign != null && sign.equalsIgnoreCase(newSign)){
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 
	 * validate: 签名验证 . <br/>
	 *
	 * @author tolly
	 * @param resMap
	 * @param signKey
	 * @param charset
	 * @return boolean
	 */
	public static boolean validate(Map<String, String> resMap, String signKey, String charset){
		boolean ret = false;
		
		//签名报文
		String signSrc = coverMap2String(resMap);
		//logger.debug("res="+res);
		//logger.debug(resMap.toString());
		String md5Str = StringUtils.EMPTY;
		try {
			md5Str = Crypto.GetMessageDigest(signKey, SIGNMETHOD, charset);
		} catch (Exception e) {
			logger.error("获取MD5错误:{}", e.getMessage());
			return ret;
		}
		signSrc = (signSrc + "&" + md5Str);
		logger.info("签名报文:{}",signSrc);
		//签名
		String newSign = Crypto.GetMessageDigest(signSrc, SIGNMETHOD, charset);
		logger.info("签名:{}",newSign);
		String sign = resMap.get("sign");
		if(sign.equalsIgnoreCase(newSign)){
			ret = true;
		}
		return ret;
	}
	
	/**
	 * 
	 * prepareDataMap: 数据预处理,去除多余的接口字段及空键值 . <br/>
	 *
	 * @author tolly
	 * @param dataMap 元数据集合
	 * @param dataKey 接口字段
	 * @return Map
	 */
	public static Map<String, String> prepareDataMap(Map<String, String> dataMap, String[] dataKey) {
		Map<String, String> paraMap = Maps.newHashMap();
		List<String> kList = Arrays.asList(dataKey);
		
		//剔除多余的字段,剔除空值
		for (String item : dataMap.keySet()) {
			if(kList.contains(item) && (StringUtils.isNotBlank(dataMap.get(item)) && !"null".equalsIgnoreCase(dataMap.get(item)))){
				paraMap.put(item, dataMap.get(item));
			}
		}
		return paraMap;
	}
	
	/**
	 * 
	 * signBeforePost: 发往清算平台前生成签名 . <br/>
	 *
	 * @param reqMap
	 * @param signKey
	 * @param charset
	 * @return String
	 */
	public static String signBeforePost(Map<String, String> reqMap, String signKey, String charset) {
		//Predicate<Map.Entry<String, String>> predicate = entry -> !"sign".equals(entry.getKey()) && StringUtils.isNotBlank(entry.getValue());
//		Predicate<Map.Entry<String, String>> predicate = entry -> !"sign".equals(entry.getKey());
		Predicate<Map.Entry<String, String>> predicate = new Predicate<Map.Entry<String, String>>() {
			@Override
			public boolean apply(Map.Entry<String, String> entry) {
				return !"sign".equals(entry.getKey());
			}
		};
		return AppUtils.mdsign(reqMap, signKey, charset, predicate);
	}
	
	/**
	 * 签名计算
	 *
	 * @param reqMap
	 * @param signKey
	 * @param charset
	 * @param predicate
	 * @return
	 */
	public static String mdsign(Map<String, String> reqMap, String signKey, String charset, Predicate<Map.Entry<String, String>> predicate) {
	    if (StringUtils.isBlank(charset)) {
	        charset = PayConf.CHARSET;
	    }
	    Map<String, String> treeMap = Maps.newTreeMap();
	    treeMap.putAll(Maps.filterEntries(reqMap, predicate));
		// 将null置空
	    String join = Joiner.on('&').withKeyValueSeparator("=").useForNull("").join(treeMap);
	    String signSrc = join + "&" + Crypto.GetMessageDigest(signKey, PayConf.SIGNMETHOD, charset);
	    logger.info("向清算系统发起交易签名原文:{}", signSrc);
	    String sign = Crypto.GetMessageDigest(signSrc, PayConf.SIGNMETHOD, charset);
	    logger.info("向清算系统发起交易签名:{}", sign);
	    return sign;
	}
	
	
}
