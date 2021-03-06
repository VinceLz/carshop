package com.xawl.car.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xawl.car.domain.Business;
import com.xawl.car.domain.Goods;
import com.xawl.car.domain.HomeTop;
import com.xawl.car.domain.JSON;
import com.xawl.car.domain.VO.ModelVO;
import com.xawl.car.service.BusinessService;
import com.xawl.car.service.GoodsService;
import com.xawl.car.service.HomeService;
import com.xawl.car.service.ModelService;
import com.xawl.car.util.JsonUtils;

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

	@Resource
	private ModelService modelService;

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
		List<HomeTop> image = null;
		ModelVO obj = null;
		if (param != null && !param.isEmpty()) {
			obj = JsonUtils.jsonToPojo(param, ModelVO.class);
		}
		if (obj == null) {
			obj = new ModelVO();
		}
		System.out.println(obj);
		 if(obj.getPageNo()==1){
		 image = homeService.getSearchImage();
		 }
		List<Goods> result = modelService.getCarByProperty(obj);
		json.add("params", obj);
		json.add("result", result);
		json.add("image", image);
		return json + "";
	}

	// 首页车 符合购车方案
	@RequestMapping("/home/car")
	@ResponseBody
	public String getTop5(JSON json, ModelVO obj) {
		List<HomeTop> image = null;
		List<Goods> result = modelService.getCarByProperty(obj);
		// if(obj.getPageNo()==1){
		// image = homeService.getSearchImage();
		// }
		json.add("params", obj);
		json.add("result", result);
		json.add("image", image);
		return json + "";
	}

}
