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
import com.xawl.car.dao.OptionLogMapper;
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
import com.xawl.car.util.PayUtil;
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
	private OptionLogMapper optionLogMapper;

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
		orderService.insertYcorder(ycorder);// 插入成功
		OptionLog op = new OptionLog();
		op.setGoodid(goodid);
		op.setUlogin(user.getUlogin());
		op.setCreatedate(DateUtil.getSqlDate());
		op.setContent(("创建养车订单/订单价格:" + ycorder.getPrice() + "/商家:"
				+ ycorder.getBmname() + "/手机号:" + ycorder.getUphone() + "/是否使用优惠劵:")
				+ ycorder.getYoid());
		op.setResult(json.toString());
		op.setType(OptionLog.ORDER_KEEP_CAR);
		optionLogMapper.insertLog(op);
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
		String appmessage = PayUtil.payMent(goodid, byGoodid.getPrice(),
				bankId.getBankId());

		String respCode = JSONObject.parseObject(appmessage).getString(
				"respCode");
		if ("00".equals(respCode)) {
			// 成功
			byGoodid.setQid(JSONObject.parseObject(appmessage).getString("qid"));
			orderService.updateQid(byGoodid);
		} else {
			// 获取失败
			json.add("status", 0);
		}
		json.add("paycode", appmessage);// 银行返回

		OptionLog op = new OptionLog();
		op.setBankname(bankId.getBankname());
		op.setContent("用户/" + user.getUlogin() + "发起交易请求/银行返回结果/" + appmessage
				+ "");
		op.setCreatedate(DateUtil.getSqlDate());
		op.setGoodid(goodid);
		op.setResult(json.toString());
		op.setType(OptionLog.ORDER_KEEP_CAR);
		op.setUlogin(user.getUlogin());
		optionLogMapper.insertLog(op);
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
		json.add("resp", respMap);
		return json.toString();
	}

	@RequestMapping("/ycorder/BackSelect")
	@ResponseBody
	public String getTop14(JSON json, String goodid, String qid)
			throws Exception {
		Map<String, Object> queryMoneyBlack = PayUtil.queryMoneyBlack(goodid,
				qid);
		json.add("resp", queryMoneyBlack);
		return json.toString();
	}

	// 发起退款操作
	@RequestMapping("/ycorder/moneyBack")
	@ResponseBody
	public String getTop12(JSON json, String goodid, String qid)
			throws Exception {

		Map<String, String> moneyBlack = PayUtil.moneyBlack(goodid, qid);
		// 00成功
		// 01失败
		// 02不确定
		String respcode = moneyBlack.get("respCode");
		json.add("resp", moneyBlack);
		return json.toString();
	}

	// @OpLog(OpLogType = OpLog.SERVICE_BLAK)
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

		OptionLog op = new OptionLog();
		op.setContent("收到银行异步回调");
		op.setCreatedate(DateUtil.getSqlDate());
		op.setType(OptionLog.ORDER_KEEP_CAR);
		// 签名验证
		boolean validate = AppUtils.validate(respMap, PayConf.SIGNKEY, charset);
		if (validate) {
			logger.info("签名验证成功.");
			String goodid = respMap.get("orderNumber");
			if (goodid == null) {
				respMap.put("msg", "服务器回调异常---orderNumber为空 丢弃不处理");
				op.setResult(respMap.toString());
				optionLogMapper.insertLog(op);
				return respMap.toString();
			}
			op.setGoodid(goodid);
			YcOrder byGoodid = orderService.getByGoodid(goodid);
			if (byGoodid.getStatus() == YcOrder.ORDER_PAY) {
				// 已经校验成功 丢弃后面的消息
				respMap.put("msg", "订单已经校验过,服务器重复回调,丢弃不处理");
				op.setResult(respMap.toString());
				optionLogMapper.insertLog(op);
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
					op.setResult(respMap.toString());
					optionLogMapper.insertLog(op);
					return respMap.toString();
				} else {
					// 不相等表示订单异常
					Map map = new HashMap();
					map.put("status", YcOrder.ORDER_EXCEPTION);
					map.put("goodid", goodid);
					orderService.updateOrderYcStatus(map, null);
					respMap.put("xawl.msg", "交易额异常");
					op.setResult(respMap.toString());
					optionLogMapper.insertLog(op);
					return respMap.toString();
				}
			} else if ("2".equals(respMap.get("state"))) {
				logger.info("交易失败.");
				respMap.put("xawl.msg", "交易失败");
				op.setResult(respMap.toString());
				optionLogMapper.insertLog(op);
				return respMap.toString();
			} else {
				// 状态码异常
				respMap.put("xawl.msg", "状态码未知");
				op.setResult(respMap.toString());
				optionLogMapper.insertLog(op);
				return respMap.toString();
			}
		} else {
			respMap.put("xawl.msg", "签名验证失败");
			op.setResult(respMap.toString());
			optionLogMapper.insertLog(op);
			return respMap.toString();
		}
	}

	// 客户端支付成功回调我的接口
	// @OpLog(OpLogType = OpLog.Client_BLAK)
	@Role
	@RequestMapping("/ycorder/sblack")
	@ResponseBody
	public String getTop10(JSON json, User user, String goodid, Integer status)
			throws Exception {
		OptionLog op = new OptionLog();
		op.setCreatedate(DateUtil.getSqlDate());
		op.setGoodid(goodid);
		op.setType(OptionLog.ORDER_KEEP_CAR);
		op.setUlogin(user.getUlogin());
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
			op.setContent("客户端状态码/" + status + "/发送订单查询不到");
			op.setResult(json.toString());
			optionLogMapper.insertLog(op);
			return json.toString();
		}
		// 先判断服务器&客户端状态全是交易完成，那么就不查询了
		if (status == 0 && order.getStatus() == YcOrder.ORDER_PAY) {
			// 服务器也被回调了显示成功、客户端也显示成功，那么说明这笔交易应该没问题
			op.setResult(json.toString());
			op.setContent("客户端状态码/" + status + "/服务器以回调,交易完毕");
			optionLogMapper.insertLog(op);
			return json.toString();
		} else {
			Map<String, Object> queryPay = PayUtil.queryPay(goodid,
					order.getQid());
			json.add("select", queryPay);
			// 判断 map.put("status", YcOrder.ORDER_CHECK);
			Object object = queryPay.get("state");
			if (object != null) {
				if ("1".equals(queryPay.get("state"))) {
					// 成功
					Map<String, String> map2 = (Map) queryPay.get("queryorder");
					String pay = map2.get("payAmount");// 实付金额
					String realpay = map2.get("orderAmount");
					if (pay.equals(realpay)
							&& pay.equals((int) (order.getPrice() * 100))) {
						// 正常
						Map map3 = new HashMap();
						map3.put("status", YcOrder.ORDER_PAY);
						map3.put("goodid", goodid);
						orderService.updateOrderYcStatus(map3, goodid);
						op.setContent("客户端状态码/" + status + "/无法确认订单发起查询请求&返回结果"
								+ queryPay.toString() + "/请求交易正常");
					} else {
						Map map4 = new HashMap();
						map4.put("status", YcOrder.ORDER_EXCEPTION);
						map4.put("goodid", goodid);
						op.setContent("客户端状态码/" + status + "/无法确认订单发起查询请求&返回结果"
								+ queryPay.toString() + "/请求金额异常");
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
					op.setContent("客户端状态码/" + status + "/无法确认订单发起查询请求&返回结果"
							+ queryPay.toString() + "/订单支付失败");
				}
			} else {
				// 没有查询到这笔或者其他错误
				Map map6 = new HashMap<>();
				map6.put("status", YcOrder.ORDER_NO_PAY);
				map6.put("goodid", goodid);
				orderService.updateOrderYcStatus(map6, null);
				op.setContent("客户端状态码/" + status + "/无法确认订单发起查询请求&返回结果"
						+ queryPay.toString() + "/没有查询到这笔订单");
			}
		}
		op.setResult(json.toString());
		optionLogMapper.insertLog(op);
		return json.toString();
	}
}
