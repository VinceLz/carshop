package com.xawl.car.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xawl.car.domain.JSON;
import com.xawl.car.domain.Order;
import com.xawl.car.service.ModelService;
import com.xawl.car.service.OrderService;
import com.xawl.car.util.DateUtil;

/*
 * 订单
 */
@Controller
public class OrderController {
	@Resource
	private ModelService modelService;
	@Resource
	private OrderService orderService;

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

	public static void main(String[] args) {

		System.out
				.println(Math.round(6378.1380 * 2.0 * Math.asin(Math.sqrt(Math
						.pow((Math.sin(34.21377469  * Math.PI / 180.0 - 34.33
								* Math.PI / 180.0) / 2.0), 2.0)
						+ Math.cos(34.33 * Math.PI / 180.0)
						* Math.cos(34.33 * Math.PI / 180.0)
						* Math.pow(
								Math.sin(( 108.90635118* Math.PI / 180.0 - 108.93 * Math.PI / 180.0) / 2.0),
								2.0))) * 1000.0));
		
		
		
		System.out.println(getDistance(108.93,34.33 ,108.90635118, 34.21377469 ));

	}

	public static double getDistance(double long1, double lat1, double long2,
			double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* R
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
						* Math.cos(lat2) * sb2 * sb2));
		return d;
	}
}
