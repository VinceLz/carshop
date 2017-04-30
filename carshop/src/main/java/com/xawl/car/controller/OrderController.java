package com.xawl.car.controller;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.xawl.car.domain.Bank;
import com.xawl.car.domain.JSON;
import com.xawl.car.domain.OptionLog;
import com.xawl.car.domain.Order;
import com.xawl.car.domain.User;
import com.xawl.car.domain.YcOrder;
import com.xawl.car.domain.VO.RollVO;
import com.xawl.car.interceptor.OpLog;
import com.xawl.car.interceptor.Role;
import com.xawl.car.service.ModelService;
import com.xawl.car.service.OptionLogService;
import com.xawl.car.service.OrderService;
import com.xawl.car.service.RollService;
import com.xawl.car.util.AppUtils;
import com.xawl.car.util.Crypto;
import com.xawl.car.util.DateUtil;
import com.xawl.car.util.HttpClientUtil;
import com.xawl.car.util.JsonUtils;
import com.xawl.car.util.PayConf;
import com.xawl.car.util.RequestUtils;
import com.xawl.car.util.Xml2Map;

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

	@Resource
	private OptionLogService optionLogService;

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

	// @OpLog(OpLogType = OpLog.ORDER_CREATE)
	@Role
	@RequestMapping("/ycorder/add")
	@ResponseBody
	public String getTop51(JSON json, YcOrder ycorder, User user)
			throws UnsupportedEncodingException, ParseException {
		ycorder.setUid(user.getUid());
		ycorder.setStatus(YcOrder.ORDER_NO_PAY);
		ycorder.setDate(new DateUtil().getNowDT());
		String goodid = user.getUphone() + DateUtil.currentTimeToSS();// 生成订单
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
		}
		json.add("ycorder", ycorder);// 订单
		try {
			orderService.insertYcorder(ycorder);// 插入成功
			OptionLog op = new OptionLog();
			op.setGoodid(goodid);
			op.setUlogin(user.getUlogin());
			op.setCreatedate(DateUtil.getSqlDate());
			op.setContent("创建养车订单/订单价格:" + ycorder.getPrice() + "/商家:"
					+ ycorder.getBmname() + "/手机号:" + ycorder.getUphone()
					+ "/是否使用优惠劵" + ycorder.getYoid() == null ? "否" : "是");
			op.setResult(json.toString());
			op.setType(OptionLog.ORDER_KEEP_CAR);
		} catch (Exception e) {
			return json.toString();
		}
		return json.toString();
	}

	// 请求支付接口
	// @OpLog(OpLogType = OpLog.REQUEST_PAY)
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

	@RequestMapping("/ycorder/moneysBack")
	@ResponseBody
	public String getTop13(JSON json, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		resp.setStatus(HttpServletResponse.SC_OK);// 响应清算平台通知
		Map<String, String> respMap = RequestUtils.getReqMap(req);
		System.out.println("-------退款异步回答了");
		System.out.println(respMap);
		return null;
	}

	@RequestMapping("/ycorder/BackSelect")
	@ResponseBody
	public String getTop14(JSON json, String goodid, String qid)
			throws Exception {
		logger.info("同步查询退款消息");

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
		logger.info("收到退款同步查询响应" + httpPost);
		String readStringXml = readStringXml(httpPost, signKey, charset);// xml格式的响应报文
		logger.info("收到退款同步查询响应2---" + readStringXml);
		return null;
	}

	@RequestMapping("/ycorder/moneyBack")
	@ResponseBody
	public String getTop12(JSON json, String goodid, String qid)
			throws Exception {
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
		if (StringUtils.isNotBlank(ret)) {
			String[] retArray = ret.split("&");
			Map<String, String> retMap = Maps.newHashMap();
			for (String item : retArray) {
				String[] itemArray = item.split("=");
				retMap.put(itemArray[0], (2 == itemArray.length ? itemArray[1]
						: StringUtils.EMPTY));
			}

		}
		return goodid;
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
		if (validate) {
			logger.info("签名验证成功.");
			String goodid = respMap.get("orderNumber");
			if (goodid == null) {
				respMap.put("msg", "服务器回调异常---orderNumber为空 丢弃不处理");
				return respMap.toString();
			}
			YcOrder byGoodid = orderService.getByGoodid(goodid);
			if (byGoodid.getStatus() == YcOrder.ORDER_PAY) {
				// 已经校验成功 丢弃后面的消息
				respMap.put("msg", "订单已经校验过,服务器重复回调,丢弃不处理");
				return respMap.toString();
			}
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
					orderService.updateOrderYcStatus(map, goodid);
					// 并且根据订单的优惠劵状态
					respMap.put("xawl.msg", "交易正常");
					return respMap.toString();
				} else {
					// 不相等表示订单异常
					Map map = new HashMap();
					map.put("status", YcOrder.ORDER_EXCEPTION);
					map.put("goodid", goodid);
					orderService.updateOrderYcStatus(map, null);
					respMap.put("xawl.msg", "交易额异常");
					return respMap.toString();
				}
			} else if ("2".equals(respMap.get("state"))) {
				logger.info("交易失败.");
				respMap.put("xawl.msg", "交易失败");
				return respMap.toString();
			} else {
				// 状态码异常
				respMap.put("xawl.msg", "状态码未知");
				return respMap.toString();
			}
		} else {
			respMap.put("xawl.msg", "签名验证失败");
			return respMap.toString();
		}
	}

	// 客户端支付成功回调我的接口
	@OpLog(OpLogType = OpLog.Client_BLAK)
	@Role
	@RequestMapping("/ycorder/sblack")
	@ResponseBody
	public String getTop10(JSON json, User user, String goodid, Integer status)
			throws Exception {
		// 返回4种状态
		// 支付成功 3 0
		// 支付失败2 -1
		// 支付取消1 -1
		// 处理中0 -1
		if (status == 3) {
			status = 0;
		} else {
			status = -1;
		}
		YcOrder order = orderService.getByGoodid(goodid);
		if (order == null) {
			// 查询不到这个订单
			json.add("status", 0);
			json.add("msg", "no order id");
			return json.toString();
		}
		// 先判断服务器&客户端状态全是交易完成，那么就不查询了
		if (status == 0 && order.getStatus() == YcOrder.ORDER_PAY) {
			// 服务器也被回调了显示成功、客户端也显示成功，那么说明这笔交易应该没问题
			return json.toString();
		} else {
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
				reqMap.put("qid", order.getQid());
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
			Document doc = DocumentHelper.parseText(readStringXml);

			Map<String, Object> map = Xml2Map.Dom2Map(doc);
			json.add("select", map);
			// 判断 map.put("status", YcOrder.ORDER_CHECK);
			Object object = map.get("state");
			if (object != null) {
				if ("1".equals(map.get("state"))) {
					// 成功
					Map<String, String> map2 = (Map) map.get("queryorder");
					String pay = map2.get("payAmount");// 实付金额
					String realpay = map2.get("orderAmount");
					if (pay.equals(realpay)
							&& pay.equals((int) (order.getPrice() * 100))) {
						// 正常
						Map map3 = new HashMap();
						map3.put("status", YcOrder.ORDER_PAY);
						map3.put("goodid", goodid);
						orderService.updateOrderYcStatus(map3, goodid);
					} else {
						Map map4 = new HashMap();
						map4.put("status", YcOrder.ORDER_EXCEPTION);
						map4.put("goodid", goodid);
						orderService.updateOrderYcStatus(map4, null);
					}
				} else {
					// 订单失败或者没有支付
					Map map5 = new HashMap();
					// 失败
					if (status == 0) {
						map5.put("status", YcOrder.ORDER_CHECK);
					} else {
						map5.put("status", YcOrder.ORDER_NO_PAY);
					}
					map5.put("goodid", goodid);
					orderService.updateOrderYcStatus(map5, null);
				}
			} else {
				// 没有查询到这笔或者其他错误
				Map map6 = new HashMap<>();
				map6.put("status", YcOrder.ORDER_NO_PAY);
				map6.put("goodid", goodid);
				orderService.updateOrderYcStatus(map6, null);
			}
		}
		return json.toString();
	}



	
}
