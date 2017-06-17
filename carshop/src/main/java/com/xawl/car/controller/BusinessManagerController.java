package com.xawl.car.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xawl.car.domain.JSON;
import com.xawl.car.domain.MaintainBusiness;
import com.xawl.car.domain.Service;
import com.xawl.car.domain.YcOrder;
import com.xawl.car.interceptor.Role;
import com.xawl.car.service.MaintainBusinessService;
import com.xawl.car.service.OrderService;
import com.xawl.car.util.DateUtil;
import com.xawl.car.util.DesUtil;
import com.xawl.car.util.ResourceUtil;
import com.xawl.car.util.TokenUtil;

/*
 * 养车商家
 */
@Controller
public class BusinessManagerController {

	@Resource
	private MaintainBusinessService maintainBusinessService;

	@Resource
	private OrderService orderService;

	// 1 登陆接口
	@ResponseBody
	@RequestMapping("/manager/login")
	public String login(JSON json, @RequestParam() String mlogin,
			@RequestParam() String mpassword, HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		map.put("mlogin", mlogin);
		System.out.println("mlogin" + mlogin);
		map.put("mpassword", DesUtil.encode(DesUtil.DES, mpassword));
		MaintainBusiness manager = maintainBusinessService.login(map);
		if (manager != null) {
			// 登陆成功
			request.getSession().setAttribute(ResourceUtil.CURRENT_BUSINESS,
					manager);
			json.add("business", manager);
			return json + "";
		}
		json.add("status", 0);
		return json.toString();
	}

	// 1.1 注销接口
	@ResponseBody
	@RequestMapping("/manager/loginout")
	public String login2(JSON json, HttpServletRequest request) {
		request.getSession().removeAttribute(ResourceUtil.CURRENT_BUSINESS);
		request.getSession().invalidate();
		return json.toString();
	}

	// 2 注册接口应该在后台生成

	// 3 增删改查 自己店里提供的服务
	/*
	 * 不需要任何参数，因为这个操作必须是登陆后才可以调用的
	 */
	@ResponseBody
	@Role(role = Role.ROLE_BUSINESS)
	@RequestMapping("/manager/get")
	public String get(JSON json, MaintainBusiness business) {
		// 通过商家获取到
		Map map = new HashMap<String, String>();
		map.put("mbid", business.getMbid());
		map.put("type", Service.CLEAN);
		List<Service> clean = maintainBusinessService.getClean(map);
		map.put("type", Service.MAINTAIN);
		List<Service> baoyang = maintainBusinessService.getClean(map);
		map.put("type", Service.DECORATION);
		List<Service> zh = maintainBusinessService.getClean(map);
		json.add("clean", clean);
		json.add("mainclean", baoyang);
		json.add("decoration", zh);
		return json.toString();
	}

	// 4 查看处理自己的订单

	// 删除接口
	@ResponseBody
	@Role(role = Role.ROLE_BUSINESS)
	@RequestMapping("/manager/delete")
	public String get3(JSON json, @RequestParam() String sid) {
		// 通过商家获取到
		maintainBusinessService.deleteByService(sid);
		return json.toString();
	}

	// 新增
	@ResponseBody
	@Role(role = Role.ROLE_BUSINESS)
	@RequestMapping("/manager/add")
	public String get4(JSON json, Service service, MaintainBusiness business) {
		// 通过商家获取到
		System.out.println(service);
		service.setMbid(business.getMbid());
		service.setCreatdate(DateUtil.getSqlDate());
		maintainBusinessService.insertService(service);
		return json.toString();
	}

	// 修改
	@ResponseBody
	@Role(role = Role.ROLE_BUSINESS)
	@RequestMapping("/manager/update")
	public String get5(JSON json, Service service) {
		// 通过商家获取到
		maintainBusinessService.updateService(service);
		return json.toString();
	}

	// 获取订单信息
	@ResponseBody
	@Role(role = Role.ROLE_BUSINESS)
	@RequestMapping("/manager/orders")
	public String get6(JSON json, MaintainBusiness business) {
		// 获取付款、取消、确认订单。
		List<YcOrder> list = orderService.getOrders(business.getMbid());
		json.add("orders", list);
		return json.toString();
	}

	// 商家确认订单
	@ResponseBody
	@Role(role = Role.ROLE_BUSINESS)
	@RequestMapping("/manager/affirm")
	public String get7(JSON json, MaintainBusiness business,
			@RequestParam int yoid, int ok) {
		// ok 传1 表示接单
		// 0表示取消订单
		Map map = new HashMap();
		map.put("mbid", business.getMbid());
		map.put("yoid", yoid);
		if (ok == 0) {
			YcOrder order = orderService.getById(yoid);
			if (order.getPrice() == 0 && order.getStatus() == 0) {
				// 0元单
				map.put("status", YcOrder.ORDER_BLACK);// 直接退款
			} else {

				map.put("status", YcOrder.ORDER_FAIL);// 取消了订单
				// 检查订单如果是0元，直接显示退款
			}

		} else {
			map.put("status", YcOrder.ORDER_SUCCESS);// 确认了订单
		}
		orderService.updateOrderStatusByYc(map);// 确认
		return json.toString();
	}

	// 修改密码
	@ResponseBody
	@Role(role = Role.ROLE_BUSINESS)
	@RequestMapping("/manager/updatePwd")
	public String changpwd(JSON json, MaintainBusiness business, String old,
			String news) {
		Map map = new HashMap<String, String>();
		map.put("old", DesUtil.encode(DesUtil.DES, old));
		map.put("news", DesUtil.encode(DesUtil.DES, news));
		map.put("mbid", business.getMbid());
		int sum = maintainBusinessService.updatePwd(map);
		if (sum == 0) {
			json.add("status", 0);
		}
		return json.toString();
	}
}
