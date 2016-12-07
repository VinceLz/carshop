package com.xawl.car.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xawl.car.dao.HomeMapper;
import com.xawl.car.dao.JedisDao;
import com.xawl.car.domain.Goods;
import com.xawl.car.domain.HomeTop;
import com.xawl.car.domain.VO.ModelVO;
import com.xawl.car.pagination.Page;
import com.xawl.car.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	private HomeMapper homeMapper;


	@Override
	public List<HomeTop> getHomeTop() {
		System.out.println(homeMapper.getHomeTop().toString());
		return homeMapper.getHomeTop();
	}

	@Override
	public List<HomeTop> getHomeActice() {
		System.out.println(homeMapper.getHomeActice().toString());
		return homeMapper.getHomeActice();
	}

	@Override
	public List<HomeTop> findPage(Page<HomeTop> page) {
		return homeMapper.findPage(page);
	}

	

	@Override
	public List<HomeTop> getCarCaseActive() {
		return homeMapper.getCarCaseActive();
	}

	@Override
	public List<HomeTop> getSearchImage() {
		return homeMapper.getSearchImage();
	}

}
