package com.xawl.car.dao;

import java.util.List;

import com.xawl.car.domain.Goods;
import com.xawl.car.domain.HomeTop;
import com.xawl.car.domain.VO.ModelVO;
import com.xawl.car.pagination.Page;


public interface HomeMapper {
	List<HomeTop> getHomeTop();
	
	List<HomeTop> getHomeActice();
	List<HomeTop> findPage(Page<HomeTop> page);
	List<Goods> getCarByProperty(ModelVO goods);
	List<HomeTop> getCarCaseActive();
}	
