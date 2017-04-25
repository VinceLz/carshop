package com.xawl.car.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xawl.car.domain.Bank;
import com.xawl.car.domain.JSON;
import com.xawl.car.domain.Order;
import com.xawl.car.domain.User;
import com.xawl.car.domain.YcOrder;
import com.xawl.car.domain.VO.RollVO;
import com.xawl.car.interceptor.OpLog;
import com.xawl.car.interceptor.Role;
import com.xawl.car.service.ModelService;
import com.xawl.car.service.OrderService;
import com.xawl.car.service.RollService;
import com.xawl.car.util.AppUtils;
import com.xawl.car.util.Crypto;
import com.xawl.car.util.DateUtil;
import com.xawl.car.util.HttpClientUtil;
import com.xawl.car.util.JsonUtils;
import com.xawl.car.util.PayConf;
import com.xawl.car.util.RequestUtils;

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

	// @Role
	// @RequestMapping("/ycorder/add1")
	// @ResponseBody
	// public String getTop5(JSON json, YcOrder ycorder, User user) {
	// ycorder.setUid(user.getUid());
	// ycorder.setStatus(YcOrder.ORDER_PAY);
	// orderService.insertYcorder(ycorder);// 插入成功
	// rollService.deleteByruid(ycorder.getRuid()); // 优惠劵做废
	// final int uid = user.getUid();
	// final float price = ycorder.getPrice();
	// // 发放优惠劵
	// ExecutorUtil.getInstance().execute(new Runnable() {
	// @Override
	// public void run() {
	// // 获取注册规则类
	// RollGrant rollGrant = rollService
	// .getRollGrant(RollGrant.USER_PAY);
	// RollUtil.insert(rollGrant, rollService, uid, price);// 根据规则进行放发优惠劵
	// }
	// });
	// // TODO 发放完优惠劵后考虑站内信或者短信提示
	// return json.toString();
	// }
	@OpLog(OpLogType = OpLog.ORDER_CREATE)
	@Role
	@RequestMapping("/ycorder/add")
	@ResponseBody
	public String getTop51(JSON json, YcOrder ycorder, User user)
			throws UnsupportedEncodingException, ParseException {
		ycorder.setUid(user.getUid());
		ycorder.setStatus(YcOrder.ORDER_NO_PAY);
		ycorder.setDate(new DateUtil().getNowDT());
		String goodid = DateUtil.currentTimeToSS();
		ycorder.setGoodid(goodid);
		if (ycorder.getRuid() != null) {
			// 检查优惠劵是否可用，并修改优惠劵为占用状态
			RollVO roll = rollService.getByRuid(ycorder.getRuid());
			// 1判断优惠劵类型与订单类型是否一致
			if (roll.getType() != ycorder.getType()) {
				json.add("status", 0);
				json.add("msg", "type no");
				return json.toString();
			}
			if (roll.getStatus() != 0) {
				json.add("status", 0);
				json.add("msg", "status no");
				return json.toString();
			}
			if (roll.getCondition() > ycorder.getRealprice()) {
				json.add("status", 0);
				json.add("msg", "condition no");
				return json.toString();
			}
			if (!DateUtil.compTo2(roll.getPastdate(), 0)) {
				json.add("status", 0);
				json.add("msg", "roll endtime");
				return json.toString();
			}
			orderService.insertYcorder(ycorder);// 插入成功
			// 使用了优惠劵，占用中
			Map map = new HashMap();
			map.put("ruid", ycorder.getRuid());
			map.put("status", 3);
			rollService.updateRollStatus(map);
			json.add("ycorder", ycorder);// 订单
		} else {
			orderService.insertYcorder(ycorder);// 插入成功
			json.add("ycorder", ycorder);// 订单
		}
		return json.toString();
	}

	// 请求支付接口
	@OpLog(OpLogType = OpLog.REQUEST_PAY)
	@Role
	@RequestMapping("/ycorder/payment")
	@ResponseBody
	public String getTop11(JSON json, User user, String goodid, Bank bankId)
			throws Exception {

		YcOrder byGoodid = orderService.getByGoodid(goodid);

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
			reqMap.put("orderTime", DateUtil.currentTime());
		}
		if (StringUtils.isBlank(reqMap.get("orderNumber"))) {
			reqMap.put("orderNumber", goodid);
		}
		if (StringUtils.isBlank(reqMap.get("orderAmount"))) {
			logger.debug("支付的金额为" + byGoodid.getPrice() * 100 + "---"
					+ byGoodid);
			reqMap.put("orderAmount", (int) (byGoodid.getPrice() * 100) + "");
		}
		if (StringUtils.isBlank(reqMap.get("orderCurrency"))) {
			reqMap.put("orderCurrency", "156");
		}
		if (StringUtils.isBlank(reqMap.get("defaultBankNumber"))) {
			reqMap.put("defaultBankNumber", bankId.getBankId());
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
		String respCode = JSONObject.parseObject(appmessage).getString(
				"respCode");
		if ("00".equals(respCode)) {
			// 成功
			byGoodid.setQid(JSONObject.parseObject(appmessage).getString("qid"));
			orderService.updateQid(byGoodid);
		}
		json.add("paycode", appmessage);// 银行返回
		return json.toString();
	}

	@OpLog(OpLogType = OpLog.SERVICE_BLAK)
	// 生成提交订单的回调接口
	@RequestMapping("/ycorder/black")
	public String getTop9(HttpServletRequest request, HttpServletResponse resp)
			throws Exception {
		resp.setStatus(HttpServletResponse.SC_OK);// 响应清算平台通知
		System.out.println("------回调我了");
		// 获取通知参数
		Map<String, String> respMap = RequestUtils.getReqMap(request);

		String charset = respMap.get("charset");

		// 转换编码
		String merReserved = respMap.get("merReserved1");
		if (StringUtils.isNotBlank(merReserved)) {
			if (StringUtils.isNotBlank(merReserved)) {
				respMap.put("merReserved1",
						new String(merReserved.getBytes("ISO8859-1"), charset));
			}
		}
		String merReserved2 = respMap.get("merReserved2");
		if (StringUtils.isNotBlank(merReserved2)) {
			if (StringUtils.isNotBlank(merReserved2)) {
				respMap.put("merReserved2",
						new String(merReserved2.getBytes("ISO8859-1"), charset));
			}
		}
		String merReserved3 = respMap.get("merReserved3");
		if (StringUtils.isNotBlank(merReserved3)) {
			if (StringUtils.isNotBlank(merReserved3)) {
				respMap.put("merReserved3",
						new String(merReserved3.getBytes("ISO8859-1"), charset));
			}
		}

		logger.info("接收到异步通知报文:" + respMap.toString());

		// 签名验证
		boolean validate = AppUtils.validate(respMap, PayConf.SIGNKEY, charset);
		String goodid = respMap.get("orderNumber");
		if (validate) {
			logger.info("签名验证成功.");
			// 此处添加商户业务处理逻辑
			if ("1".equals(respMap.get("state"))) {
				// 判断金额
				String string = respMap.get("orderAmount"); // 订单金额
				String string2 = respMap.get("payAmount");// 实际支付金额
				if (string.equals(string2)) {
					// 金额相同 更新订单状态
					Map map = new HashMap();
					map.put("status", YcOrder.ORDER_PAY);
					map.put("goodid", goodid);
					orderService.updateOrderYcStatus(map);
					// 并且根据订单的优惠劵状态
					YcOrder byGoodid = orderService.getByGoodid(goodid);
					if (byGoodid.getRuid() != null) {
						// 使用了优惠劵，修改为已使用
						Map map2 = new HashMap();
						map2.put("ruid", byGoodid.getRuid());
						map2.put("status", 1);
						rollService.updateRollStatus(map2);

					}
					respMap.put("xawl.msg", "交易正常");
					return respMap.toString();
				} else {
					// 不相等表示订单异常
					Map map = new HashMap();
					map.put("status", YcOrder.ORDER_EXCEPTION);
					map.put("goodid", goodid);
					orderService.updateOrderYcStatus(map);
					respMap.put("xawl.msg", "交易额异常");
					return respMap.toString();
				}
			} else if ("2".equals(respMap.get("state"))) {
				Map map = new HashMap();
				map.put("status", YcOrder.ORDER_NO_PAY);
				map.put("goodid", goodid);
				orderService.updateOrderYcStatus(map);
				logger.info("交易失败.");
				respMap.put("xawl.msg", "交易失败");
				return respMap.toString();
			} else {
				// 状态码异常
				respMap.put("xawl.msg", "状态码未知");
				return respMap.toString();
			}
		} else {
			logger.info("签名验证失败.");
			Map map = new HashMap();
			map.put("status", YcOrder.ORDER_NO_PAY);
			map.put("goodid", goodid);
			orderService.updateOrderYcStatus(map);
			// 此处添加商户业务处理逻辑
			respMap.put("xawl.msg", "签名验证失败");
			return respMap.toString();
		}
	}

	// 客户端支付成功回调我的接口
	@OpLog(OpLogType = OpLog.Client_BLAK)
	@Role
	@RequestMapping("/ycorder/sblack")
	@ResponseBody
	public String getTop10(JSON json, User user, String goodid, Integer status) {
		// 返回4种状态
		// 支付成功 3 0
		// 支付失败2 -1
		// 支付取消1 -1
		// 处理中0 -1
		//
		
	
		if (status == 3) {
			status = 0;
		} else {
			status = -1;
		}

		// status 只能是返回0 或者-1
		// 考虑到客户端与建行的回调先后顺序，所以需要判断
		YcOrder order = orderService.getByGoodid(goodid);
		if (order == null) {
			// 查询不到这个订单
			json.add("status", 0);
			json.add("msg", "no order id");
			return json.toString();
		}
		if (order.getStatus() == YcOrder.ORDER_EXCEPTION) {
			json.add("status", 0);
			json.add("orderStatus", YcOrder.ORDER_EXCEPTION);

			// 客户端成功
		} else if (order.getStatus() == YcOrder.ORDER_NO_PAY && status == 0) {
			Map map = new HashMap();
			map.put("status", YcOrder.ORDER_CHECK);
			map.put("goodid", goodid);
			orderService.updateOrderYcStatus(map); // 修改状态
		}
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
