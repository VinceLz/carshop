package com.xawl.car.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.xawl.car.domain.Goods;
import com.xawl.car.domain.Model;
import com.xawl.car.domain.Order;
import com.xawl.car.domain.YcOrder;
import com.xawl.car.domain.VO.GoodsVO;
import com.xawl.car.domain.VO.ModelVO;

public interface ModelService {
	GoodsVO getAllById(Serializable gid);

	List<Goods> getCarByProperty(ModelVO obj);

	Model getById(Serializable mid);

	List<Model> findByPrice(Map map);

	List<String> getColors(Serializable mid);

	YcOrder getbyMid2All(Serializable mid);

	List<String> getImage(Serializable id);

	Model getConf(Serializable id);
}
