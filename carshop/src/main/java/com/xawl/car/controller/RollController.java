package com.xawl.car.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xawl.car.domain.JSON;
import com.xawl.car.domain.User;
import com.xawl.car.domain.VO.RollVO;
import com.xawl.car.interceptor.Role;
import com.xawl.car.service.RollService;

@Controller
public class RollController {
	@Resource
	private RollService rollService;

	// 写一个定时器，每到00:00即遍历一次数据中的优惠劵日期表，将优惠劵过期的status 置为2
	/*
	 * 方案1：mysql存储过程+定时器 方案2：linux 的crontab定时器执行sql脚本 方案3: java 定时调度 spring的定时调度
	 * scheduleExecutorSerice 暂时先用mysql的存储过程+定时器方案
	 */

	@Role
	@ResponseBody
	@RequestMapping("/roll/get")
	public String getHome2(JSON json, User user, @RequestParam() String type) {
		// 获取user对应的优惠劵
		System.out.println(type);
		Map map = new HashMap();
		map.put("uid", user.getUid());
		String[] split = type.split(",");

		int[] arr = new int[split.length + 1];
		
		for (int i = 0; i < split.length; i++) {
			arr[i] = Integer.valueOf(split[i]);
		}

		arr[split.length] = 0;// 通用优惠劵
		System.out.println(arr.toString());
		map.put("type", arr);
		List<RollVO> volist = rollService.getRollByType2Uid(map);
		json.add("rolls", volist);
		return json.toString();
	}

	@Role
	@ResponseBody
	@RequestMapping("/roll/getall")
	public String getHome3(JSON json, User user) {
		// 获取user对应的优惠劵
		List<RollVO> volist = rollService.getRollByUid(user.getUid());
		json.add("rolls", volist);
		return json.toString();
	}

}
