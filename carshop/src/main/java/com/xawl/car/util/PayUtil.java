package com.xawl.car.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.xawl.car.domain.YcOrder;

public class PayUtil {

	public static double sumPrice(List<YcOrder> list) {
		BigDecimal priBigDecimal = new BigDecimal(0);
		for (YcOrder ycOrder : list) {
			priBigDecimal = priBigDecimal
					.add(new BigDecimal(ycOrder.getPrice()));
		}
		return priBigDecimal.doubleValue();
	}

	private static Logger logger = null;

	static {
		URL BLCX_XML_CONFIG_FILEPATH = PayUtil.class
				.getResource("/PayLog.properties");
		System.out.println(BLCX_XML_CONFIG_FILEPATH.getFile());
		logger = Logger.getLogger(PayUtil.class);
		PropertyConfigurator.configure(BLCX_XML_CONFIG_FILEPATH);
	}

	/**
	 * 查询退款情况
	 * 
	 * @param goodid
	 *            商品id
	 * @param qid
	 *            流水id
	 * @return 银行返回参数
	 * @throws MalformedURLException
	 */
	public static Map<String, Object> queryMoneyBlack(String goodid, String qid) {
		// 去查询
		logger.info("申请退款查询操作---商品id:" + goodid + "---流水id:" + qid);
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
		reqMap.put("sign", sign);
		logger.info("申请退款查询操作---商品id:" + goodid + "---流水id:" + qid + "---请求报文:"
				+ reqMap);
		String httpPost = HttpClientUtil.httpPost(PayConf.REFUND_QUERY_URL,
				reqMap, charset);
		String readStringXml = readStringXml(httpPost, signKey, charset);
		logger.info("申请退款查询操作---商品id:" + goodid + "---流水id:" + qid + "---响应报文:"
				+ readStringXml);
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(readStringXml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = Xml2Map.Dom2Map(doc);
		logger.info("申请退款查询操作---商品id:" + goodid + "---流水id:" + qid + "---返回值:"
				+ map);
		return map;
	}

	/**
	 * 发起退款操作
	 * 
	 * @param goodid
	 *            商品id
	 * @param qid
	 *            流水id
	 * @return 银行返回的参数
	 */
	public static Map<String, String> moneyBlack(String goodid, String qid) {
		logger.info("申请退款操作---商品id:" + goodid + "---流水id:" + qid);
		String charset = PayConf.CHARSET;
		String signKey = PayConf.SIGNKEY;
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
		paramMap = Maps.filterEntries(paramMap,
				new Predicate<Map.Entry<String, String>>() {
					@Override
					public boolean apply(Map.Entry<String, String> entry) {
						return StringUtils.isNotBlank(entry.getValue());
					}
				});
		String sign = AppUtils.signBeforePost(paramMap, signKey, charset);

		paramMap.put("sign", sign);
		logger.info("申请退款操作---商品id:" + goodid + "---流水id:" + qid + "---请求报文:"
				+ paramMap);
		String ret = HttpClientUtil.httpPost(PayConf.REFUND_PAY_URL, paramMap,
				charset);
		logger.info("申请退款操作---商品id:" + goodid + "---流水id:" + qid + "---响应报文:"
				+ ret);
		Map<String, String> retMap = Maps.newHashMap();
		if (StringUtils.isNotBlank(ret)) {
			String[] retArray = ret.split("&");
			for (String item : retArray) {
				String[] itemArray = item.split("=");
				retMap.put(itemArray[0], (2 == itemArray.length ? itemArray[1]
						: StringUtils.EMPTY));
			}

		}
		logger.info("申请退款操作---商品id:" + goodid + "---流水id:" + qid + "---返回值:"
				+ retMap);
		return retMap;
	}

	/**
	 * 查询支付情况
	 * 
	 * @param goodid
	 *            商品id
	 * @param qid
	 *            流水id
	 * @return 返回银行返回数据
	 */
	public static Map<String, Object> queryPay(String goodid, String qid) {
		logger.info("申请支付查询操作---商品id:" + goodid + "---流水id:" + qid);
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
		String sign = AppUtils.signBeforePost(reqMap, signKey, charset);
		reqMap.put("sign", sign);
		logger.info("申请支付查询操作---商品id:" + goodid + "---流水id:" + qid + "---请求报文:"
				+ reqMap);
		String httpPost = HttpClientUtil.httpPost(PayConf.JSPT_QUERY_URL,
				reqMap, charset);
		String readStringXml = readStringXml(httpPost, signKey, charset);
		logger.info("申请支付查询操作---商品id:" + goodid + "---流水id:" + qid + "---响应报文:"
				+ readStringXml);
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(readStringXml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = Xml2Map.Dom2Map(doc);
		logger.info("申请支付查询操作---商品id:" + goodid + "---流水id:" + qid + "---返回值:"
				+ map);
		return map;
	}

	/**
	 * 请求支付接口
	 * 
	 * @param goodid
	 *            商品id
	 * @param Price
	 *            支付金额 以元为单位
	 * @param bankId
	 *            支付方式id
	 * @return
	 */

	public static String payMent(String goodid, Double Price, String bankId) {
		logger.info("申请支付操作---商品id:" + goodid + "---金额:" + Price + "---银行id:"
				+ bankId);

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
		String sign = AppUtils.signBeforePost(paramMap, signKey, charset);
		paramMap.put("sign", sign);

		logger.info("申请支付操作---商品id:" + goodid + "---金额:" + Price + "---银行id:"
				+ bankId + "---请求报文:" + paramMap);
		String ret = HttpClientUtil.httpPost(PayConf.JSPT_PAY_URL, paramMap,
				charset);
		logger.info("申请支付操作---商品id:" + goodid + "---金额:" + Price + "---银行id:"
				+ bankId + "---响应报文:" + ret);

		// 响应报文解析
		Map<String, String> msgMap = readHttpPost(ret, signKey);
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
		logger.info("申请支付操作---商品id:" + goodid + "---金额:" + Price + "---银行id:"
				+ bankId + "---返回值:" + appmessage);
		return appmessage;
	}

	/**
	 * 银行异步退款回调
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static Map<String, String> AsyncMoneyBlack(HttpServletRequest req,
			HttpServletResponse resp) {
		resp.setStatus(HttpServletResponse.SC_OK);// 响应清算平台通知
		// 获取通知参数
		Map<String, String> reqMap = RequestUtils.getReqMap(req);
		// 转换编码
		if (null != reqMap.get("merReserved1")) {
			String merReserved1 = reqMap.get("merReserved1");
			if (StringUtils.isNotBlank(merReserved1)) {
				try {
					reqMap.put("merReserved1",
							new String(merReserved1.getBytes("ISO8859-1"),
									reqMap.get("charset")));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		logger.info("银行异步退款回调---通知报文:" + reqMap);
		return reqMap;
	}

	/**
	 * 银行异步支付回调
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static Map<String, String> AsyncPayInfo(HttpServletRequest req,
			HttpServletResponse resp) {

		resp.setStatus(HttpServletResponse.SC_OK);// 响应清算平台通知
		Map<String, String> respMap = RequestUtils.getReqMap(req);
		String charset = respMap.get("charset");
		// 转换编码
		String merReserved = respMap.get("merReserved1");
		if (StringUtils.isNotBlank(merReserved)) {
			if (StringUtils.isNotBlank(merReserved)) {
				try {
					respMap.put("merReserved1",
							new String(merReserved.getBytes("ISO8859-1"),
									charset));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		String merReserved2 = respMap.get("merReserved2");
		if (StringUtils.isNotBlank(merReserved2)) {
			if (StringUtils.isNotBlank(merReserved2)) {
				try {
					respMap.put("merReserved2",
							new String(merReserved2.getBytes("ISO8859-1"),
									charset));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		String merReserved3 = respMap.get("merReserved3");
		if (StringUtils.isNotBlank(merReserved3)) {
			if (StringUtils.isNotBlank(merReserved3)) {
				try {
					respMap.put("merReserved3",
							new String(merReserved3.getBytes("ISO8859-1"),
									charset));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		logger.info("银行异步支付回调---通知报文:" + respMap);
		return respMap;
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
		// logger.info("响应报文解析串" + builder.toString());
		return msgMap;
	}

}