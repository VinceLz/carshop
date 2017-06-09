package com.xawl.car.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xawl.car.domain.Business;
import com.xawl.car.domain.Goods;
import com.xawl.car.domain.HomeTop;
import com.xawl.car.domain.VO.BusinessVO;
import com.xawl.car.pagination.Page;

public interface GoodsService {

	List<Goods> findPage(Page page);

	Goods getById(Serializable id);

	void insert(Goods goods);

	List<HomeTop> getHomeHot();
	
	List<String> getImage(Serializable id);

	List<String> getAll();

	List<BusinessVO> getBusiness(Map map);
}
