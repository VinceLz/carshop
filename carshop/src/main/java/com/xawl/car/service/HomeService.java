package com.xawl.car.service;

import java.util.List;

import com.xawl.car.domain.Goods;
import com.xawl.car.domain.HomeTop;
import com.xawl.car.domain.VO.GoodsVO;
import com.xawl.car.pagination.Page;

public interface HomeService {
	List<HomeTop> getHomeTop();
	List<Goods> getCarByProperty(GoodsVO goods);
	List<HomeTop> getHomeActice();
	List<HomeTop> findPage(Page<HomeTop> page);
	
	
	List<HomeTop> getCarCaseActive();
}
