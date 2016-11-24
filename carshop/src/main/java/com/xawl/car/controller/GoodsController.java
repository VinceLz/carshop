package com.xawl.car.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xawl.car.domain.Goods;
import com.xawl.car.domain.JSON;
import com.xawl.car.pagination.Page;
import com.xawl.car.service.GoodsService;
import com.xawl.car.util.DateUtil;

@Controller
public class GoodsController {
	@Resource
	private GoodsService goodsService;

	@RequestMapping("/admin/car/list")
	public @ResponseBody
	Object getList(JSON json, Page<Goods> page) {
		List<Goods> list = goodsService.findPage(page);
		page.setResults(list);
		json.add("page", page);
		return json + "";
	}

	@RequestMapping("/admin/car/add")
	@ResponseBody
	public String add(JSON json, Goods goods) {
		goods.setGdate(DateUtil.getSqlDate());
		goodsService.insert(goods);
		return json + "";
	}

	@RequestMapping("/admin/car/get")
	@ResponseBody
	public String add(JSON json, @RequestParam() String gid) {
		Goods bean = goodsService.getById(gid);
		json.add("car", bean);
		return json + "";
	}
	
}