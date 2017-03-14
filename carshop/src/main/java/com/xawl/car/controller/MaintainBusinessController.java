package com.xawl.car.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xawl.car.domain.Comment;
import com.xawl.car.domain.HomeTop;
import com.xawl.car.domain.Image;
import com.xawl.car.domain.JSON;
import com.xawl.car.domain.MaintainBusiness;
import com.xawl.car.domain.Service;
import com.xawl.car.service.HomeService;
import com.xawl.car.service.MaintainBusinessService;

@Controller
public class MaintainBusinessController {

	@Resource
	private MaintainBusinessService maintainBusinessService;

	@Resource
	private HomeService homeService;

	@ResponseBody
	@RequestMapping("/ycstore/home")
	public String getHome(JSON json, String longitude, String latitude) {
		// 根据距离来获取最近的
		List<HomeTop> top = homeService.getcyTop();// 获取到养车首页轮播图
		List<MaintainBusiness> homeHot = null;
		if (longitude != null && latitude != null && !longitude.isEmpty()
				&& !latitude.isEmpty()) {
			Map map = new HashMap<String, String>();
			map.put("longitude", longitude);
			map.put("latitude", latitude);
			homeHot = maintainBusinessService.getHomeBydistance(map);
		} else {
			// 通过热门来检索
			homeHot = maintainBusinessService.getHomeHot();
		}
		json.add("homeImage", top);
		json.add("ycstore", homeHot);
		return json.toString();
	}

	@ResponseBody
	@RequestMapping("/ycstore/get")
	public String getHome2(JSON json, String mbid) {
		MaintainBusiness store = maintainBusinessService.getStore(mbid);
		List<String> image = maintainBusinessService.getImage(mbid);
		if (image != null && image.size() != 0) {
			store.setBimage(image);
		}
		// 获取商家对应的服务 3种
		Map map = new HashMap<String, String>();
		map.put("mbid", mbid);
		map.put("type", Service.CLEAN);
		List<Service> clean = maintainBusinessService.getClean(map);
		map.put("type", Service.MAINTAIN);
		List<Service> baoyang = maintainBusinessService.getClean(map);
		map.put("type", Service.DECORATION);
		List<Service> zh = maintainBusinessService.getClean(map);
		store.setClean(clean);
		store.setMainclean(baoyang);
		store.setDecoration(zh);
		json.add("ycstore", store);
		return json.toString();
	}

	@ResponseBody
	@RequestMapping("/ycstore/getComment")
	public String getHome3(JSON json, String mbid) {
		List<Comment> comment = maintainBusinessService.getCommentList(mbid);
		json.add("comment", comment);
		return json.toString();
	}

}
