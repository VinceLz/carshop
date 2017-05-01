package com.xawl.car.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

public class PayUtil {
	public static Logger logger = LoggerFactory.getLogger(PayUtil.class);

	public static Map<String, Object> queryMoneyBlack(String goodid, String qid) {
		// 去查询
		Map<String, String> reqMap = new HashMap<String, String>();
		if (StringUtils.isBlank(reqMap.get("version"))) {
			reqMap.put("version", PayConf.VERSION);
		}
		if (StringUtils.isBlank(reqMap.get("charset"))) {
			reqMap.put("charset", PayConf.CHARSET);
		}
		if (StringUtils.isBlank(reqMap.get("signMethod"))) {
			reqMap.put("signMethod", PayConf.SIGNMETHOD);
		}
		if (StringUtils.isBlank(reqMap.get("transType"))) {
			reqMap.put("transType", "02");
		}
		if (StringUtils.isBlank(reqMap.get("merId"))) {
			reqMap.put("merId", PayConf.MERID);
		}
		if (StringUtils.isBlank(reqMap.get("orderNumber"))) {
			reqMap.put("orderNumber", goodid);
		}
		if (StringUtils.isBlank(reqMap.get("qid"))) {
			reqMap.put("qid", qid);
		}
		if (StringUtils.isBlank(reqMap.get("orderTime"))) {
			reqMap.put("orderTime", DateUtil.currentTime());
		}
		String charset = reqMap.get("charset");
		if (StringUtils.isBlank(charset)) {
			charset = PayConf.CHARSET;
		}
		String signKey = PayConf.SIGNKEY;
		reqMap = Maps.filterEntries(reqMap,
				new Predicate<Map.Entry<String, String>>() {
					@Override
					public boolean apply(Map.Entry<String, String> entry) {
						return StringUtils.isNotBlank(entry.getValue());
					}
				});

		// 生成签名
		String sign = AppUtils.signBeforePost(reqMap, signKey, charset);
		System.out.println("向清算平台发送支付请求:" + reqMap.toString());
		System.out.println("原始签名：" + sign);
		reqMap.put("sign", sign);
		String httpPost = HttpClientUtil.httpPost(PayConf.REFUND_QUERY_URL,
				reqMap, charset);
		String readStringXml = readStringXml(httpPost, signKey, charset);// xml格式的响应报文
		logger.info("收到退款同步查询响应---" + readStringXml);
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(readStringXml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = Xml2Map.Dom2Map(doc);
		return map;
	}

	public static Map<String, String> moneyBlack(String goodid, String qid) {
		String charset = PayConf.CHARSET;
		String signKey = PayConf.SIGNKEY;
		// 定义接口参数
		Map<String, String> reqMap = new HashMap<>();
		if (StringUtils.isBlank(reqMap.get("version"))) {
			reqMap.put("version", PayConf.VERSION);
		}
		if (StringUtils.isBlank(reqMap.get("charset"))) {
			reqMap.put("charset", PayConf.CHARSET);
		}
		if (StringUtils.isBlank(reqMap.get("signMethod"))) {
			reqMap.put("signMethod", PayConf.SIGNMETHOD);
		}
		if (StringUtils.isBlank(reqMap.get("transType"))) {
			reqMap.put("transType", "02");
		}
		if (StringUtils.isBlank(reqMap.get("merId"))) {
			reqMap.put("merId", PayConf.MERID);
		}
		if (StringUtils.isBlank(reqMap.get("orderTime"))) {
			reqMap.put("orderTime", DateUtil.currentTime());
		}
		if (StringUtils.isBlank(reqMap.get("orderNumber"))) {
			reqMap.put("orderNumber", goodid);
		}
		if (StringUtils.isBlank(reqMap.get("qid"))) {
			reqMap.put("qid", qid);
		}
		if (StringUtils.isBlank(reqMap.get("refAmount"))) {
			reqMap.put("refAmount", "1");
		}
		if (StringUtils.isBlank(reqMap.get("orderCurrency"))) {
			reqMap.put("orderCurrency", "156");
		}
		if (StringUtils.isBlank(reqMap.get("backEndUrl"))) {
			reqMap.put("backEndUrl",
					"http://www.singpa.com/carshop/ycorder/moneysBack.action");
		}
		String[] paramKeys = new String[] { "version", "charset", "signMethod",
				"transType", "merId", "orderTime", "orderNumber", "qid",
				"refAmount", "orderCurrency", "sign", "backEndUrl",
				"merReserved1" };

		Map<String, String> paramMap = Maps.newTreeMap();
		for (String key : paramKeys) {
			paramMap.put(key, reqMap.get(key));
		}
		// 过滤掉空格
		paramMap = Maps.filterEntries(paramMap,
				new Predicate<Map.Entry<String, String>>() {
					@Override
					public boolean apply(Map.Entry<String, String> entry) {
						return StringUtils.isNotBlank(entry.getValue());
					}
				});
		String sign = AppUtils.signBeforePost(paramMap, signKey, charset);
		paramMap.put("sign", sign);
		System.out.println("向清算平台发送退款请求:" + paramMap.toString());
		String ret = HttpClientUtil.httpPost(PayConf.REFUND_PAY_URL, paramMap,
				charset);
		System.out.println("清算平台同步响应:" + ret);
		// 处理清算平台同步返回结果
		Map<String, String> retMap = Maps.newHashMap();
		if (StringUtils.isNotBlank(ret)) {
			String[] retArray = ret.split("&");
			for (String item : retArray) {
				String[] itemArray = item.split("=");
				retMap.put(itemArray[0], (2 == itemArray.length ? itemArray[1]
						: StringUtils.EMPTY));
			}

		}
		return retMap;
	}

	public static Map<String, Object> queryPay(String goodid, String qid) {
		// 去查询
		Map<String, String> reqMap = new HashMap<String, String>();
		if (StringUtils.isBlank(reqMap.get("version"))) {
			reqMap.put("version", PayConf.VERSION);
		}
		if (StringUtils.isBlank(reqMap.get("charset"))) {
			reqMap.put("charset", PayConf.CHARSET);
		}
		if (StringUtils.isBlank(reqMap.get("signMethod"))) {
			reqMap.put("signMethod", PayConf.SIGNMETHOD);
		}
		if (StringUtils.isBlank(reqMap.get("transType"))) {
			reqMap.put("transType", "01");
		}
		if (StringUtils.isBlank(reqMap.get("merId"))) {
			reqMap.put("merId", PayConf.MERID);
		}
		if (StringUtils.isBlank(reqMap.get("orderNumber"))) {
			reqMap.put("orderNumber", goodid);
		}
		if (StringUtils.isBlank(reqMap.get("qid"))) {
			reqMap.put("qid", qid);
		}
		if (StringUtils.isBlank(reqMap.get("queryTime"))) {
			reqMap.put("queryTime", DateUtil.currentTime());
		}
		if (StringUtils.isBlank(reqMap.get("payType"))) {
			reqMap.put("payType", "B2C");
		}
		String charset = reqMap.get("charset");
		if (StringUtils.isBlank(charset)) {
			charset = PayConf.CHARSET;
		}
		String signKey = PayConf.SIGNKEY;
		reqMap = Maps.filterEntries(reqMap,
				new Predicate<Map.Entry<String, String>>() {
					@Override
					public boolean apply(Map.Entry<String, String> entry) {
						return StringUtils.isNotBlank(entry.getValue());
					}
				});

		// 生成签名
		String sign = AppUtils.signBeforePost(reqMap, signKey, charset);
		System.out.println("向清算平台发送支付请求:" + reqMap.toString());
		System.out.println("原始签名：" + sign);
		reqMap.put("sign", sign);
		String httpPost = HttpClientUtil.httpPost(PayConf.JSPT_QUERY_URL,
				reqMap, charset);
		System.out.println("清算平台同步响应:" + httpPost);// 返回的响应报文
		String readStringXml = readStringXml(httpPost, signKey, charset);// xml格式的响应报文
		logger.info("收到查询报文内容为" + readStringXml);
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(readStringXml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		Map<String, Object> map = Xml2Map.Dom2Map(doc);
		return map;
	}

	public static String payMent(String goodid, Double Price, String bankId) {
		// 获取客户端商品信息
		Map<String, String> reqMap = new HashMap<String, String>();

		String charset = reqMap.get("charset");
		if (StringUtils.isBlank(charset)) {
			charset = PayConf.CHARSET;
		}
		String signKey = PayConf.SIGNKEY;

		// 本实例模拟商户系统发送交易请求 手机demo中提交信息仅作参考在后台补全所有字段 具体使用请结合商户实际情况
		if (StringUtils.isBlank(reqMap.get("version"))) {
			reqMap.put("version", PayConf.VERSION);
		}
		if (StringUtils.isBlank(reqMap.get("charset"))) {
			reqMap.put("charset", PayConf.CHARSET);
		}
		if (StringUtils.isBlank(reqMap.get("signMethod"))) {
			reqMap.put("signMethod", PayConf.SIGNMETHOD);
		}
		if (StringUtils.isBlank(reqMap.get("transType"))) {
			reqMap.put("transType", PayConf.TRANSTYPE);
		}
		if (StringUtils.isBlank(reqMap.get("merId"))) {
			reqMap.put("merId", PayConf.MERID);
		}
		if (StringUtils.isBlank(reqMap.get("signKey"))) {
			reqMap.put("signKey", PayConf.SIGNKEY);
		}
		if (StringUtils.isBlank(reqMap.get("backEndUrl"))) {
			reqMap.put("backEndUrl", PayConf.EZFMER_BACKENDURL);
		}
		if (StringUtils.isBlank(reqMap.get("orderTime"))) {
			reqMap.put("orderTime", DateUtil.currentTime());
		}
		if (StringUtils.isBlank(reqMap.get("orderNumber"))) {
			reqMap.put("orderNumber", goodid);
		}
		if (StringUtils.isBlank(reqMap.get("orderAmount"))) {
			reqMap.put("orderAmount", (int) (Price * 100) + "");
		}
		if (StringUtils.isBlank(reqMap.get("orderCurrency"))) {
			reqMap.put("orderCurrency", "156");
		}
		if (StringUtils.isBlank(reqMap.get("defaultBankNumber"))) {
			reqMap.put("defaultBankNumber", bankId);
		}
		if (StringUtils.isBlank(reqMap.get("merReserved1"))) {
			reqMap.put("merReserved1", "");
		} else {
			String merReserved1 = null;
			try {
				merReserved1 = new String(reqMap.get("merReserved1").getBytes(
						"ISO8859-1"), charset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			reqMap.put("merReserved1",
					AppUtils.URLEncode(merReserved1, charset));
		}
		if (StringUtils.isBlank(reqMap.get("merReserved2"))) {
			reqMap.put("merReserved2", "");
		} else {
			String merReserved2 = null;
			try {
				merReserved2 = new String(reqMap.get("merReserved2").getBytes(
						"ISO8859-1"), charset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			reqMap.put("merReserved2",
					AppUtils.URLEncode(merReserved2, charset));
		}
		if (StringUtils.isBlank(reqMap.get("merReserved3"))) {
			reqMap.put("merReserved3", "");
		} else {
			String merReserved3 = null;
			try {
				merReserved3 = new String(reqMap.get("merReserved3").getBytes(
						"ISO8859-1"), charset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			reqMap.put("merReserved3",
					AppUtils.URLEncode(merReserved3, charset));
		}
		if (StringUtils.isBlank(reqMap.get("agentAmount"))) {
			reqMap.put("agentAmount", "");
		}

		if (StringUtils.isBlank(reqMap.get("appSchema"))) {
			reqMap.put("appSchema", "ronglian10001mobilepay");
		}

		try {
			reqMap.put("merReserved1", new String(reqMap.get("merReserved1")
					.getBytes("ISO8859-1"), charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			reqMap.put("merReserved2", new String(reqMap.get("merReserved2")
					.getBytes("ISO8859-1"), charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			reqMap.put("merReserved3", new String(reqMap.get("merReserved3")
					.getBytes("ISO8859-1"), charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 定义接口数据集合
		Map<String, String> paramMap = Maps.newHashMap();

		// 构造签名参数数组
		String[] paramKeys = new String[] { "version", "charset", "signMethod",
				"transType", "merId", "backEndUrl", "orderTime", "orderNumber",
				"orderAmount", "orderCurrency", "defaultBankNumber",
				"merReserved1", "merReserved2", "merReserved3", "agentAmount",
				"appSchema" };
		for (String key : paramKeys) {
			paramMap.put(key, reqMap.get(key));
		}

		// 生成签名
		String sign = AppUtils.signBeforePost(paramMap, signKey, charset);
		System.out.println("向清算平台发送支付请求:" + paramMap.toString());
		System.out.println("原始签名：" + sign);
		paramMap.put("sign", sign);
		logger.info("响应报文" + paramMap);
		// 生成支付报文信息，发送报文推送请求
		String ret = HttpClientUtil.httpPost(PayConf.EZFMER_PAY_URL, paramMap,
				charset);
		logger.info("收到报文推送请求响应串：" + ret);

		// 响应报文解析
		Map<String, String> msgMap = readHttpPost(ret, signKey);

		logger.info("消息", msgMap);

		// 去掉没用项
		msgMap.remove("version");
		msgMap.remove("charset");
		msgMap.remove("signMethod");
		msgMap.remove("transType");
		msgMap.remove("merReserved1");
		msgMap.remove("merReserved2");
		msgMap.remove("merReserved3");
		msgMap.remove("sign");
		String appmessage = JsonUtils.toJosnFromObject(msgMap);
		logger.info("向商户客户端发送响应结果为：" + appmessage);

		return appmessage;
	}

	private static String readStringXml(String xml, String signKey,
			String charset) {

		String respMsg = StringUtils.EMPTY;

		if (StringUtils.isBlank(xml))
			return respMsg;

		List<String> strings = Splitter.on('&').splitToList(xml);

		if (strings.size() != 2)
			return respMsg;

		String content = new String(new Base64().decode(StringUtils
				.substringAfter(strings.get(0), "=")), Charset.forName(charset));
		String sign = StringUtils.substringAfter(strings.get(1), "=");

		String calcSign = Crypto.GetMessageDigest(
				content + "&"
						+ Crypto.GetMessageDigest(signKey, "MD5", charset),
				"MD5", charset);
		if (!calcSign.equals(sign)) {
			System.out.println("签名错误");
			return "签名错误";
		}

		respMsg = content;

		try {
			Document doc = DocumentHelper.parseText(content);
			Element rootElt = doc.getRootElement();

			String respCode = rootElt.elementText("respCode");
			switch (respCode) {
			case "00":
				System.out.println("单笔查询成功：");
				System.out.println(content);
				break;
			case "03":
				System.out.println("无此记录");
				break;
			case "04":
				System.out.println("数据格式错误");
				break;
			case "05":
				System.out.println("商户订单号,原始流水号不能全部为空");
				break;
			case "06":
				System.out.println("签名校验未通过");
				break;
			}
		} catch (Exception e) {
			System.out.println("返回的XML文件解析错误!" + e);
			respMsg = "解析失败";
		}

		return respMsg;
	}

	/**
	 * 响应报文解析
	 * 
	 * @return String
	 */
	private static Map<String, String> readHttpPost(String ret, String signKey) {

		Map<String, String> msgMap = new TreeMap<String, String>();

		if (StringUtils.isBlank(ret)) {
			msgMap.put("respCode", "03");
			msgMap.put("respMsg", AppUtils.URLEncode("响应报文为空", PayConf.CHARSET));
			return msgMap;
		}

		String[] retArray = ret.split("&");
		for (String val : retArray) {
			String[] temp = val.split("=");
			if (val.indexOf("=") != (val.length() - 1)) {
				msgMap.put(temp[0], temp[1]);
			} else {
				msgMap.put(temp[0], "");
			}
		}
		// 构造签名参数数组
		String[] paramKeys = new String[] { "version", "charset", "signMethod",
				"transType", "qid", "respCode", "respMsg" };

		boolean verifySign = AppUtils.validate(msgMap, signKey,
				msgMap.get("charset"));
		if (verifySign) {
			System.out.println("验签通过");
		} else {
			System.out.println("验签失败");
			return null;
		}

		// 生成响应报文解析串
		StringBuilder builder = new StringBuilder("");
		for (String key : paramKeys) {
			builder.append("&").append(key).append("=").append(msgMap.get(key));
		}
		builder.append("&").append(
				Crypto.GetMessageDigest(PayConf.SIGNKEY, "MD5",
						msgMap.get("charset")));
		logger.info("响应报文解析串" + builder.toString());
		return msgMap;
	}

}
