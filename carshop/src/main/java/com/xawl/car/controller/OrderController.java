package com.xawl.car.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xawl.car.domain.JSON;
import com.xawl.car.domain.Order;
import com.xawl.car.domain.RollGrant;
import com.xawl.car.domain.User;
import com.xawl.car.domain.YcOrder;
import com.xawl.car.interceptor.Role;
import com.xawl.car.service.ModelService;
import com.xawl.car.service.OrderService;
import com.xawl.car.service.RollService;
import com.xawl.car.util.DateUtil;
import com.xawl.car.util.ExecutorUtil;
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

	// 生成订单
	@Role
	@RequestMapping("/ycorder/add")
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

}
