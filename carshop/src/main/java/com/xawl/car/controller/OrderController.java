package com.xawl.car.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.xawl.car.domain.JSON;
import com.xawl.car.domain.Order;
import com.xawl.car.domain.RollGrant;
import com.xawl.car.domain.User;
import com.xawl.car.domain.YcOrder;
import com.xawl.car.interceptor.Role;
import com.xawl.car.service.ModelService;
import com.xawl.car.service.OrderService;
import com.xawl.car.service.RollService;
import com.xawl.car.util.AppUtils;
import com.xawl.car.util.Crypto;
import com.xawl.car.util.DateUtil;
import com.xawl.car.util.ExecutorUtil;
import com.xawl.car.util.HttpClientUtil;
import com.xawl.car.util.JsonUtils;
import com.xawl.car.util.PayConf;
import com.xawl.car.util.RollUtil;

/*
 * 订单
 */
@Controller
public class OrderController {
	@Resource
	private ModelService modelService;
	@Resource
	private OrderService orderService;

	@Resource
	private RollService rollService;
	public Logger logger = LoggerFactory.getLogger(getClass());

	// 生成订单
	@RequestMapping("/order/up")
	@ResponseBody
	public String getTop5(JSON json, String mid, String total, Integer uid,
			String uname, String color, String buyWay, String city,
			String cardCity) {
		// 通过mid 获取一些gid bid 等信息
		Order order = modelService.getbyMid2All(mid);
		order.setOrdertime(DateUtil.getSqlDate());
		order.setTotal(Double.parseDouble(total));
		order.setStatus(-1);
		order.setUid(uid);
		order.setUname(uname);
		order.setColor(color);
		order.setBuyWay(buyWay);
		order.setCity(city);
		order.setCardCity(cardCity);
		orderService.insert(order);
		return json + "";
	}

	@Role
	@RequestMapping("/ycorder/add1")
	@ResponseBody
	public String getTop5(JSON json, YcOrder ycorder, User user) {
		ycorder.setUid(user.getUid());
		ycorder.setStatus(YcOrder.ORDER_PAY);
		orderService.insertYcorder(ycorder);// 插入成功
		rollService.deleteByruid(ycorder.getRuid()); // 优惠劵做废
		final int uid = user.getUid();
		final float price = ycorder.getPrice();
		// 发放优惠劵
		ExecutorUtil.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				// 获取注册规则类
				RollGrant rollGrant = rollService
						.getRollGrant(RollGrant.USER_PAY);
				RollUtil.insert(rollGrant, rollService, uid, price);// 根据规则进行放发优惠劵
			}
		});
		// TODO 发放完优惠劵后考虑站内信或者短信提示
		return json.toString();
	}

	@Role
	@RequestMapping("/ycorder/add")
	@ResponseBody
	public String getTop51(JSON json, YcOrder ycorder, User user) throws UnsupportedEncodingException {
		ycorder.setUid(user.getUid());
		ycorder.setStatus(YcOrder.ORDER_NO_PAY);
		String goodid = DateUtil.currentTimeToSS();
		ycorder.setGoodid(goodid);
		orderService.insertYcorder(ycorder);// 插入成功
		if (ycorder.getRuid() != null) {
			// 使用了优惠劵，占用中
			Map map = new HashMap();
			map.put("ruid", ycorder.getRuid());
			map.put("status", 3);
			rollService.updateRollStatus(map);
		}
		// 请求清算平台

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
			reqMap.put("transType", "01");
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
			reqMap.put("orderTime", goodid);
		}
		if (StringUtils.isBlank(reqMap.get("orderNumber"))) {
			reqMap.put("orderNumber", DateUtil.currentTimeToSS());
		}
		if (StringUtils.isBlank(reqMap.get("orderAmount"))) {
			reqMap.put("orderAmount", "1");
		}
		if (StringUtils.isBlank(reqMap.get("orderCurrency"))) {
			reqMap.put("orderCurrency", "156");
		}
		if (StringUtils.isBlank(reqMap.get("defaultBankNumber"))) {
			reqMap.put("defaultBankNumber", "999");
		}
		if (StringUtils.isBlank(reqMap.get("merReserved1"))) {
			reqMap.put("merReserved1", "");
		} else {
			String merReserved1 = new String(reqMap.get("merReserved1")
					.getBytes("ISO8859-1"), charset);
			reqMap.put("merReserved1",
					AppUtils.URLEncode(merReserved1, charset));
		}
		if (StringUtils.isBlank(reqMap.get("merReserved2"))) {
			reqMap.put("merReserved2", "");
		} else {
			String merReserved2 = new String(reqMap.get("merReserved2")
					.getBytes("ISO8859-1"), charset);
			reqMap.put("merReserved2",
					AppUtils.URLEncode(merReserved2, charset));
		}
		if (StringUtils.isBlank(reqMap.get("merReserved3"))) {
			reqMap.put("merReserved3", "");
		} else {
			String merReserved3 = new String(reqMap.get("merReserved3")
					.getBytes("ISO8859-1"), charset);
			reqMap.put("merReserved3",
					AppUtils.URLEncode(merReserved3, charset));
		}
		if (StringUtils.isBlank(reqMap.get("agentAmount"))) {
			reqMap.put("agentAmount", "");
		}

		if (StringUtils.isBlank(reqMap.get("appSchema"))) {
			reqMap.put("appSchema", "ronglian10001mobilepay");
		}

		reqMap.put("merReserved1", new String(reqMap.get("merReserved1")
				.getBytes("ISO8859-1"), charset));
		reqMap.put("merReserved2", new String(reqMap.get("merReserved2")
				.getBytes("ISO8859-1"), charset));
		reqMap.put("merReserved3", new String(reqMap.get("merReserved3")
				.getBytes("ISO8859-1"), charset));

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

		// 生成支付报文信息，发送报文推送请求
		String ret = HttpClientUtil.httpPost(PayConf.EZFMER_PAY_URL, paramMap,
				charset);
		logger.info("收到报文推送请求响应串：" + ret);

		// 响应报文解析
		Map<String, String> msgMap = readHttpPost(ret, signKey);



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
		logger.info(appmessage + "------------");
		return json.toString();
	}

	// 生成提交订单

	@RequestMapping("/ycorder/black")
	@ResponseBody
	public String getTop9(JSON json) {
		System.out.println("------------------------");
		return json.toString();
	}

	/**
	 * 响应报文解析
	 * 
	 * @return String
	 */
	private Map<String, String> readHttpPost(String ret, String signKey) {

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
