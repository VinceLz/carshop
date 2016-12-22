package com.xawl.car.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xawl.car.domain.Business;
import com.xawl.car.domain.Image;
import com.xawl.car.domain.JSON;
import com.xawl.car.domain.VO.BusinessVO;
import com.xawl.car.pagination.Page;
import com.xawl.car.service.BusinessService;
import com.xawl.car.util.DateUtil;

@Controller
public class BusinessController {
	@Resource
	private BusinessService businessService;

	@RequestMapping("/store/getall")
	@ResponseBody
	public String add2(JSON json, @RequestParam() String bid) {
		BusinessVO bean = businessService.getStore2Car(bid);
		List<String> image = businessService.getImage(bid);
		bean.setBimage(image);//获取图片
		json.add("business", bean);
		return json + "";
	}

	@RequestMapping("/store/get")
	@ResponseBody
	public String add3(JSON json, @RequestParam() String bid) {
		BusinessVO bean = businessService.get(bid);
		json.add("business", bean);
		return json + "";
	}

}
