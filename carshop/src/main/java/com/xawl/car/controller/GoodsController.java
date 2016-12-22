package com.xawl.car.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xawl.car.domain.CarColor;
import com.xawl.car.domain.Goods;
import com.xawl.car.domain.JSON;
import com.xawl.car.domain.Model;
import com.xawl.car.domain.VO.GoodsVO;
import com.xawl.car.pagination.Page;
import com.xawl.car.service.GoodsService;
import com.xawl.car.service.ModelService;
import com.xawl.car.util.DateUtil;

@Controller
public class GoodsController {
	@Resource
	private GoodsService goodsService;
	@Resource
	private ModelService modelService;


	@RequestMapping("/car/getModels")
	@ResponseBody
	public String get(JSON json, @RequestParam() String gid) {
		// 通过gid拿到型号
		GoodsVO allById = modelService.getAllById(gid);
		List<String> image = goodsService.getImage(gid);
		allById.setGimage(image);
		json.add("car", allById);
		return json + "";
	}

	@RequestMapping("/car/models/get")
	@ResponseBody
	public String get2(JSON json, @RequestParam() String mid) {
		// 通过gid拿到型号
		Model main = modelService.getById(mid);
		List<String> image = modelService.getImage(mid);
		main.setMimage(image);
		// 推荐
		Map map = new HashMap<String, Object>();
		map.put("min", main.getGuidegprice() * 0.9);
		map.put("max", main.getGuidegprice() * 1.1);
		map.put("mid", main.getMid());
		List<Model> recommend = modelService.findByPrice(map);
		json.add("car", main);//主要的车
		json.add("recommend", recommend); //推荐的
		return json + "";
	}

	@RequestMapping("/car/models/getColor")
	@ResponseBody
	public String get3(JSON json, @RequestParam() String mid) {
		// 通过gid拿到型号
		List<CarColor> list = modelService.getColors(mid);
		// 推荐
		json.add("colors", list);
		return json + "";
	}
}
