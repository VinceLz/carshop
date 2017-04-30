package com.xawl.car.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xawl.car.dao.ModelMapper;
import com.xawl.car.domain.Goods;
import com.xawl.car.domain.Model;
import com.xawl.car.domain.Order;
import com.xawl.car.domain.VO.GoodsVO;
import com.xawl.car.domain.VO.ModelVO;
import com.xawl.car.service.ModelService;
import com.xawl.car.util.JsonUtils;

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
	public List<String> getColors(Serializable mid) {
		String config = modelMapper.getColors(mid);
		System.out.println(config);
		try {
			LinkedHashMap<String, LinkedHashMap<String, String>> jsonToPojo = (LinkedHashMap<String, LinkedHashMap<String, String>>) JsonUtils
					.jsonToPojo(config, LinkedHashMap.class);
			String string = jsonToPojo.get("基本参数").get("车身颜色");
			System.out.println(string);
			if (org.apache.commons.lang.StringUtils.isEmpty(string)) {
				return null;
			} else {

				String[] split = string.split("、");
				List<String> asList = Arrays.asList(split);
				return asList;
			}
		} catch (Exception e) {

		}
		return null;
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

	@Override
	public Model getConf(Serializable id) {
		return modelMapper.getConf(id);
	}

}
