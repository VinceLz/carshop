package com.xawl.car.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xawl.car.dao.OptionLogMapper;
import com.xawl.car.domain.Bank;
import com.xawl.car.domain.JSON;
import com.xawl.car.domain.OptionLog;
import com.xawl.car.domain.User;
import com.xawl.car.domain.YcOrder;
import com.xawl.car.domain.VO.RollVO;
import com.xawl.car.interceptor.Role;
import com.xawl.car.service.ModelService;
import com.xawl.car.service.OrderService;
import com.xawl.car.service.RollService;
import com.xawl.car.util.AppUtils;
import com.xawl.car.util.DateUtil;
import com.xawl.car.util.PayConf;
import com.xawl.car.util.PayUtil;

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
	@Resource
	private OptionLogMapper optionLogMapper;

	/**
	 * 返回在线订车订单
	 * 
	 * @param json
	 * @return
	 */
	@Role
	@RequestMapping("/order/myOrder")
	@ResponseBody
	public String getTop125(JSON json, User user, int type) {
		Map map = new HashMap<String, String>();
		map.put("uid", user.getUid());
		map.put("type", type);
		List<YcOrder> yc = orderService.findOrderByMap(map);
		json.add("orders", yc);
		return json.toString();

	}

	// 生成订单
	@Role
	@RequestMapping("/order/up")
	@ResponseBody
	public String getTop5(JSON json, String orderList, User user) {
		System.out.println("----" + orderList);
		List<YcOrder> parseArray = com.alibaba.fastjson.JSON.parseArray(
				orderList, YcOrder.class);
		if (parseArray == null || parseArray.size() == 0) {
			json.add("msg", "no param");
			return json.toString();
		}
		YcOrder ycOrder = parseArray.get(0);
		// 通过mid 获取一些gid bid 等信息
		YcOrder order = modelService.getbyMid2All(ycOrder.getMid());
		if (order == null) {
			json.add("msg", "no car");
			return json.toString();
		}
		String datas = new DateUtil().getNowDT();
		String goodid = user.getUphone() + DateUtil.currentTimeToSS();// 生成订单
		order.setStatus(YcOrder.ORDER_NO_PAY);
		order.setUid(user.getUid());
		order.setUname(user.getUname());
		order.setUphone(user.getUphone());
		order.setColor(ycOrder.getColor());
		order.setBuyWay(ycOrder.getBuyWay());
		order.setType(0);// 0表示购车 1养车
		order.setCity(ycOrder.getCity());
		order.setCardCity(ycOrder.getCardCity());
		order.setGoodid(goodid);
		order.setDate(datas);
		order.setPrice(ycOrder.getPrice());
		order.setSname(ycOrder.getSname());
		order.setBmname(ycOrder.getBmname());
		order.setBuytime(ycOrder.getBuytime());
		System.out.println(order);
		orderService.insertYcorder(order);
		OptionLog op = new OptionLog();
		op.setGoodid(goodid);
		op.setUlogin(user.getUlogin());
		op.setCreatedate(DateUtil.getSqlDate());
		// 写日志,
		op.setContent(("创建购车车订单/订单价格:" + order.getPrice() + "/商家:"
				+ order.getBmname() + "/手机号:" + order.getUphone()));
		op.setResult(json.toString());
		op.setType(OptionLog.ORDER_KEEP_CAR);
		optionLogMapper.insertLog(op);
		json.add("ycorder", order);
		return json + "";
	}

	/*
	 * 此接口支持批量订单和单个订单
	 */
	// @OpLog(OpLogType = OpLog.ORDER_CREATE)
	@Role
	@RequestMapping("/ycorder/add")
	@ResponseBody
	public String getTop52(JSON json, String orderList, User user)
			throws UnsupportedEncodingException, ParseException {
		System.out.println(orderList);
		// 获取订单集合
		// 如果使用了优惠劵，那么默认优惠劵在订单集合中的第一个
		// 总价
		List<YcOrder> parseArray = com.alibaba.fastjson.JSON.parseArray(
				orderList, YcOrder.class);
		if (parseArray == null || parseArray.size() == 0) {
			json.add("msg", "no order");
			return json.toString();
		}
		String datas = new DateUtil().getNowDT();
		String goodid = user.getUphone() + DateUtil.currentTimeToSS();// 生成订单
		for (YcOrder ycorder : parseArray) {
			ycorder.setUid(user.getUid());
			ycorder.setStatus(YcOrder.ORDER_NO_PAY);
			ycorder.setDate(datas);
			ycorder.setGoodid(goodid);
		}
		if (parseArray != null && parseArray.size() > 0) {
			YcOrder head = parseArray.get(0);// 获取第一个
			if (head.getRuid() != null) {
				// 检查优惠劵是否可用，并修改优惠劵为占用状态
				RollVO roll = rollService.getByRuid(head.getRuid());
				if (roll.getStatus() != 0) {
					json.add("status", 0);
					json.add("msg", "status no");
					return json.toString();
				}
			}
		}
		json.add("ycorder", parseArray.get(0));// 返回订单号
		for (YcOrder ycorder2 : parseArray) {
			orderService.insertYcorder(ycorder2);// 插入成功
		}
		OptionLog op = new OptionLog();
		op.setGoodid(goodid);
		op.setUlogin(user.getUlogin());
		op.setCreatedate(DateUtil.getSqlDate());
		// 写日志,
		op.setContent(("创建养车订单/订单价格:" + parseArray.get(0).getPrice() + "/商家:"
				+ parseArray.get(0).getBmname() + "/手机号:" + parseArray.get(0)
				.getUphone()));
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
		List<YcOrder> byGoodid = orderService.getByGoodid(goodid);// 根据订单号获取订单集合
		System.out.println("----支付金额：" + byGoodid.get(0).getPrice());
		double sumPrice = byGoodid.get(0).getPrice();
		String appmessage = PayUtil.payMent(goodid, sumPrice,
				bankId.getBankId());

		String respCode = JSONObject.parseObject(appmessage).getString(
				"respCode");
		if ("00".equals(respCode)) {
			// 成功
			for (YcOrder order : byGoodid) {
				order.setQid(JSONObject.parseObject(appmessage)
						.getString("qid"));
				orderService.updateQid(order);
			}
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

	// 银行退款异步回调
	@RequestMapping("/ycorder/moneysBack")
	@ResponseBody
	public String getTop13(JSON json, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		Map<String, String> reqMap = PayUtil.AsyncMoneyBlack(req, resp);
		// 签名验证
		boolean validate = AppUtils.validate(reqMap, PayConf.SIGNKEY,
				reqMap.get("charset"));
		if (validate) {
			// 签名验证通过
			System.out.println("签名验证成功.");
			// 此处添加商户业务处理逻辑
			if ("1".equals(reqMap.get("state"))) {
				// 订单退款成功
				System.out.println("订单退款成功.");
			} else if ("2".equals(reqMap.get("state"))) {
				// 订单退款失败
				System.out.println("订单退款失败.");
			}
		} else {
			// 签名验证失败
			System.out.println("签名验证失败.");
			// 此处添加商户业务处理逻辑
		}
		System.out.println("接收清算平台通知消息完成.");
		json.add("resp", reqMap);
		return json.toString();
	}

	// 退款查询
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
		Map<String, String> respMap = PayUtil.AsyncPayInfo(request, resp);
		String charset = respMap.get("charset");
		OptionLog op = new OptionLog();
		op.setContent("收到银行异步回调");
		op.setCreatedate(DateUtil.getSqlDate());
		op.setType(OptionLog.ORDER_KEEP_CAR);
		// 签名验证
		boolean validate = AppUtils.validate(respMap, PayConf.SIGNKEY, charset);
		if (validate) {
			String goodid = respMap.get("orderNumber");
			if (goodid == null) {
				respMap.put("msg", "服务器回调异常---orderNumber为空 丢弃不处理");
				op.setResult(respMap.toString());
				optionLogMapper.insertLog(op);
				return respMap.toString();
			}
			op.setGoodid(goodid);
			List<YcOrder> byGoodid = orderService.getByGoodid(goodid);
			if (byGoodid == null || byGoodid.size() == 0)
				return null;
			// 先用第一个校验 后面保证原子性
			if (byGoodid.get(0).getStatus() == YcOrder.ORDER_PAY) {
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
		List<YcOrder> order = orderService.getByGoodid(goodid);

		if (order == null || order.size() == 0) {
			// 查询不到这个订单
			json.add("status", 0);
			json.add("msg", "no order id");
			op.setContent("客户端状态码/" + status + "/发送订单查询不到");
			op.setResult(json.toString());
			optionLogMapper.insertLog(op);
			return json.toString();
		}
		// 先判断服务器&客户端状态全是交易完成，那么就不查询了
		if (status == 0 && order.get(0).getStatus() == YcOrder.ORDER_PAY) {
			// 服务器也被回调了显示成功、客户端也显示成功，那么说明这笔交易应该没问题
			op.setResult(json.toString());
			op.setContent("客户端状态码/" + status + "/服务器以回调,交易完毕");
			optionLogMapper.insertLog(op);
			return json.toString();
		} else {
			Map<String, Object> queryPay = PayUtil.queryPay(goodid, order
					.get(0).getQid());
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
							&& pay.equals((int) (order.get(0).getPrice() * 100))) {
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
