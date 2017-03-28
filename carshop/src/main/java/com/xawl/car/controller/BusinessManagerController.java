package com.xawl.car.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xawl.car.domain.JSON;
import com.xawl.car.domain.MaintainBusiness;
import com.xawl.car.domain.Service;
import com.xawl.car.interceptor.Role;
import com.xawl.car.service.MaintainBusinessService;
import com.xawl.car.util.ResourceUtil;
import com.xawl.car.util.TokenUtil;

/*
 * 养车商家
 */
@Controller
public class BusinessManagerController {

	@Resource
	private MaintainBusinessService maintainBusinessService;

	// 1 登陆接口
	@ResponseBody
	@RequestMapping("/manager/login")
	public String login(JSON json, String mlogin, String mpassword,
			HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		map.put("mlogin", mlogin);
		map.put("mpassword", TokenUtil.MD5(mpassword));
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
}
