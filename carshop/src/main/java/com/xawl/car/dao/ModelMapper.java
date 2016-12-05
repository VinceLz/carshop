package com.xawl.car.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.xawl.car.domain.Goods;
import com.xawl.car.domain.Model;
import com.xawl.car.pagination.Page;

public interface ModelMapper {
	List<Model> getAllById(Serializable gid);

	List<Goods> getCarByProperty(Page<Goods> page);

	Map<String, BigDecimal> getMax2Min(Serializable gid);

	Model getById(Serializable mid);

	List<Model> findByPrice(Map price);
}
