package com.xawl.car.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xawl.car.dao.ModelMapper;
import com.xawl.car.domain.CarColor;
import com.xawl.car.domain.Goods;
import com.xawl.car.domain.Model;
import com.xawl.car.domain.Order;
import com.xawl.car.domain.VO.GoodsVO;
import com.xawl.car.domain.VO.ModelVO;
import com.xawl.car.pagination.Page;
import com.xawl.car.service.ModelService;
@Service
public class ModelServiceImpl implements ModelService {

	@Autowired
	private ModelMapper modelMapper;
	
	
	

	@Override
	public List<Goods> getCarByProperty(ModelVO obj) {
		
		return modelMapper.getCarByProperty(obj);
	}


	@Override
	public Model getById(Serializable mid) {
		return modelMapper.getById(mid);
	}


	@Override
	public List<Model> findByPrice(Map price) {
		return modelMapper.findByPrice(price);
	}


	@Override
	public List<CarColor> getColors(Serializable mid) {
		return modelMapper.getColors(mid);
	}


	@Override
	public Order getbyMid2All(Serializable mid) {
		return modelMapper.getbyMid2All(mid);
	}


	@Override
	public GoodsVO getAllById(Serializable gid) {
		return modelMapper.getAllById(gid);
	}


	@Override
	public List<String> getImage(Serializable id) {
		return modelMapper.getImage(id);
	}





}
