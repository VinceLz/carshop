package com.xawl.car.service;

import java.io.Serializable;
import java.util.List;

import com.xawl.car.domain.Goods;
import com.xawl.car.domain.Model;
import com.xawl.car.domain.VO.ModelVO;

public interface ModelService {
	List<Model> getAllById(Serializable gid);
	List<Goods> getCarByProperty(ModelVO goods);
}
