package com.xawl.car.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xawl.car.domain.Business;
import com.xawl.car.domain.JSON;
import com.xawl.car.domain.VO.BusinessVO;
import com.xawl.car.pagination.Page;
import com.xawl.car.service.BusinessService;
import com.xawl.car.util.DateUtil;

@Controller
public class BusinessController {
	@Resource
	private BusinessService businessService;

	@RequestMapping("/admin/store/list")
	public @ResponseBody
	Object getList(JSON json, Page<Business> page) {
		List<Business> list = businessService.findPage(page);
		page.setResults(list);
		json.add("page", page);
		return json + "";
	}

	@RequestMapping("/admin/store/add")
	@ResponseBody
	public String add(JSON json, Business business) {
		business.setBdate(DateUtil.getSqlDate());
		businessService.insert(business);
		return json + "";
	}

	@RequestMapping("/admin/store/get")
	@ResponseBody
	public String add(JSON json, @RequestParam() String bid) {
		Business bean = businessService.getById(bid);
		json.add("business", bean);
		return json + "";
	}

	@RequestMapping("/store/getall")
	@ResponseBody
	public String add2(JSON json, @RequestParam() String bid) {
		BusinessVO bean = businessService.getStore2Car(bid);
		json.add("business", bean);
		return json + "";
	}

}
