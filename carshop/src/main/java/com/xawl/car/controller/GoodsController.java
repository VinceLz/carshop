package com.xawl.car.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xawl.car.domain.Business;
import com.xawl.car.domain.JSON;
import com.xawl.car.domain.Model;
import com.xawl.car.domain.VO.BusinessVO;
import com.xawl.car.domain.VO.GoodsVO;
import com.xawl.car.service.BusinessService;
import com.xawl.car.service.GoodsService;
import com.xawl.car.service.ModelService;

@Controller
public class GoodsController {
	@Resource
	private GoodsService goodsService;
	@Resource
	private ModelService modelService;
	@Resource
	private BusinessService businessService;

	@RequestMapping("/car/getModels")
	@ResponseBody
	public String get(JSON json, @RequestParam() String gid) {
		// 通过gid拿到型号
		GoodsVO allById = modelService.getAllById(gid);
		System.out.println(gid);
		List<String> image = goodsService.getImage(gid);
		if (image != null && image.size() != 0) {
			allById.setGimage(image);
		}
		json.add("car", allById);

		return json + "";
	}

	@RequestMapping("/car/models/get")
	@ResponseBody
	public String get2(JSON json, @RequestParam() String mid) {
		// 通过gid拿到型号
		Model main = modelService.getById(mid);

		List<String> image = modelService.getImage(mid);
		if (image != null && image.size() != 0) {
			main.setMimage(image);
		}
		// 推荐
		if (main == null) {
			return json + "";
		}
		Map map = new HashMap<String, Object>();
		map.put("min", main.getGuidegprice() * 0.9);
		map.put("max", main.getGuidegprice() * 1.1);
		map.put("mid", main.getMid());
		List<Model> recommend = modelService.findByPrice(map);
		json.add("car", main);// 主要的车
		json.add("recommend", recommend); // 推荐的
		return json + "";
	}

	@RequestMapping("/car/models/getColor")
	@ResponseBody
	public String get3(JSON json, @RequestParam() String mid) {
		// 通过gid拿到型号
		List<String> list = modelService.getColors(mid);
		// 推荐
		json.add("colors", list);
		return json + "";
	}

	// 获取车的详细配置
	@RequestMapping("/car/models/getconf")
	@ResponseBody
	public String get4(JSON json, @RequestParam() String mid) {
		Model conf = modelService.getConf(mid);
		json.add("model", conf);
		return json + "";
	}

	// 获取车的详细配置
	@RequestMapping("/car/goods/getAll")
	@ResponseBody
	public String get42(JSON json) {
		List<String> list = goodsService.getAll();
		json.add("list", list);
		return json + "";
	}

	// 获取车的详细配置
	@RequestMapping("/car/goods/query")
	@ResponseBody
	public String get1142(JSON json, String gname, String longitude,
			String latitude) {
		List<BusinessVO> list = null;
		Map map = new HashMap<String, Object>();
		map.put("longitude", longitude);
		map.put("latitude", latitude);
		if ("-1".equals(gname)) {
			// 默认随机一个
			list = businessService.getAll(map);
		} else {
			map.put("gname", gname);
			list = goodsService.getBusiness(map);
		}
		json.add("list", list);
		return json + "";
	}

}
