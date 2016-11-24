package com.xawl.car.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xawl.car.domain.Business;
import com.xawl.car.domain.Goods;
import com.xawl.car.domain.HomeTop;
import com.xawl.car.domain.JSON;
import com.xawl.car.domain.VO.GoodsVO;
import com.xawl.car.pagination.Page;
import com.xawl.car.service.BusinessService;
import com.xawl.car.service.GoodsService;
import com.xawl.car.service.HomeService;

/*
 * 首页
 */
@Controller
public class HomeController {

	@Resource
	private HomeService homeService;

	@Resource
	private GoodsService goodsService;

	@Resource
	private BusinessService businessService;

	
	// 测试

	@RequestMapping("/home")
	@ResponseBody
	public String getTop(JSON json, String longitude, String latitude) {
		
		List<HomeTop> top = homeService.getHomeTop();
		List<HomeTop> homeActice = homeService.getHomeActice();
		List<Business> homeHot = null;
		if (longitude != null && latitude != null && !longitude.isEmpty()
				&& !latitude.isEmpty()) {
			Map map = new HashMap<String, String>();
			map.put("longitude", longitude);
			map.put("latitude", latitude);
			homeHot = businessService.getHomeBydistance(map);
		} else {
			// 通过热门来检索
			homeHot = businessService.getHomeHot();
		}
		// 封装完毕
		json.add("homeImage", top);
		json.add("homeActive", homeActice);
		json.add("carstore", homeHot); // 热门4S店
		return json + "";
	}

	// 首页活动 更多
	@RequestMapping("/home/active")
	@ResponseBody
	public String getTop3(JSON json, Page<HomeTop> page) {
		List<HomeTop> result = homeService.findPage(page);
		page.setResults(result);
		json.add("page", page);
		return json + "";
	}

	// 首页车 符合购车方案
	// @RequestMapping("/home/car")
	// @ResponseBody
	// public String getTop4(JSON json, Goods goods) {
	// List<Goods> result = homeService.getCarByProperty(goods);
	// json.add("list", result);
	// return json + "";
	// }

	// 首页车 符合购车方案
	@RequestMapping("/home/car/{param}")
	@ResponseBody
	public String getTop5(JSON json, @PathVariable() String param) {
		GoodsVO jsonToPojo = JSONObject.parseObject(param, GoodsVO.class);
		List<Goods> result = homeService.getCarByProperty(jsonToPojo);
		json.add("list", result);
		return json + "";
	}

	// 养车
	@RequestMapping("/home/case")
	@ResponseBody
	public String getTop5(JSON json) {
		List<HomeTop> result = homeService.getCarCaseActive();
		json.add("active", result);
		// List<Business> homeHot = businessService.getHomeHot();
		// json.add("carstore", homeHot);
		return json + "";
	}
	
	
	
	
}
