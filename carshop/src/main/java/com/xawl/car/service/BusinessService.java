package com.xawl.car.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.xawl.car.domain.Business;
import com.xawl.car.domain.Image;
import com.xawl.car.domain.VO.BusinessVO;
import com.xawl.car.pagination.Page;

public interface BusinessService {

	List<Business> findPage(Page page);

	Business getById(Serializable id);

	void insert(Business carStore);

	List<Business> getHomeHot();

	List<Business> getHomeBydistance(Map map);

	List<BusinessVO> getAll(Map map);

	BusinessVO getStore2Car(Serializable bid);

	List<String> getImage(Serializable id);

	BusinessVO get(String bid);

}
