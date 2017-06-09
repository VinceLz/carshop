package com.xawl.car.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xawl.car.dao.BusinessMapper;
import com.xawl.car.dao.JedisDao;
import com.xawl.car.dao.ModelMapper;
import com.xawl.car.domain.Business;
import com.xawl.car.domain.Goods;
import com.xawl.car.domain.Image;
import com.xawl.car.domain.Model;
import com.xawl.car.domain.VO.BusinessVO;
import com.xawl.car.pagination.Page;
import com.xawl.car.service.BusinessService;

@Service
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	private BusinessMapper businessMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Business getById(Serializable id) {
		return businessMapper.getById(id);
	}

	@Override
	public void insert(Business carStore) {
		businessMapper.insert(carStore);
	}

	@Override
	public List<Business> findPage(Page page) {
		return businessMapper.findPage(page);
	}

	@Override
	public List<Business> getHomeHot() {
		return businessMapper.getHomeHot();
	}

	@Override
	public List<BusinessVO> getAll(Map map) {
		return businessMapper.getAll(map);
	}

	@Override
	public List<Business> getHomeBydistance(Map map) {
		return businessMapper.getHomeBydistance(map);
	}

	@Override
	public BusinessVO getStore2Car(Serializable bid) {
		return businessMapper.getStore2Car(bid);
	}

	@Override
	public BusinessVO get(String bid) {
		return businessMapper.get(bid);
	}

	@Override
	public List<String> getImage(Serializable id) {
		return businessMapper.getImage(id);
	}


}
