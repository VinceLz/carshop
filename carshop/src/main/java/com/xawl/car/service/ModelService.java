package com.xawl.car.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.xawl.car.domain.Goods;
import com.xawl.car.domain.Model;
import com.xawl.car.pagination.Page;

public interface ModelService {
	List<Model> getAllById(Serializable gid);
	List<Goods> getCarByProperty(Page<Goods> page);
	Model getById(Serializable mid);
	List<Model> findByPrice(Map map);
}
