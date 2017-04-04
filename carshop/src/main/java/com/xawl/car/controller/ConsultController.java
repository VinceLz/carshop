package com.xawl.car.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xawl.car.domain.Consult;
import com.xawl.car.domain.JSON;
import com.xawl.car.service.ConsultService;
import com.xawl.car.util.DateUtil;

@Controller
public class ConsultController {

	@Resource
	private ConsultService consultService;

	// 首页活动 更多
	@RequestMapping("/consult/add")
	@ResponseBody
	public String insert(JSON json, Consult consult) {
		System.out.println(consult);
		consult.setDate(DateUtil.getSqlDate());
		consultService.insert(consult);
		return json + "";
	}

	


}
