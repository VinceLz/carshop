package com.xawl.car.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.xawl.car.domain.CarColor;
import com.xawl.car.domain.Goods;
import com.xawl.car.domain.Model;
import com.xawl.car.domain.Order;
import com.xawl.car.domain.VO.GoodsVO;
import com.xawl.car.domain.VO.ModelVO;

public interface ModelMapper {
	GoodsVO getAllById(Serializable gid);

	List<Goods> getCarByProperty(ModelVO obj);

	Map<String, BigDecimal> getMax2Min(Serializable gid);

	Model getById(Serializable mid);

	List<Model> findByPrice(Map price);

	Order getbyMid2All(Serializable mid);
	List<CarColor> getColors(Serializable mid);
}
