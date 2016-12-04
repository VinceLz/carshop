package com.xawl.car.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xawl.car.dao.ModelMapper;
import com.xawl.car.domain.Goods;
import com.xawl.car.domain.Model;
import com.xawl.car.pagination.Page;
import com.xawl.car.service.ModelService;
@Service
public class ModelServiceImpl implements ModelService {

	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<Model> getAllById(Serializable gid) {
		return modelMapper.getAllById(gid);
	}


	@Override
	public List<Goods> getCarByProperty(Page<Goods> page) {
		return modelMapper.getCarByProperty(page);
	}

}
