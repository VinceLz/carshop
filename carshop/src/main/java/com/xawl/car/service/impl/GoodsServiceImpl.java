package com.xawl.car.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xawl.car.dao.GoodsMapper;
import com.xawl.car.dao.JedisDao;
import com.xawl.car.domain.Goods;
import com.xawl.car.domain.HomeTop;
import com.xawl.car.pagination.Page;
import com.xawl.car.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private JedisDao jedisDao;

	@Override
	public Goods getById(Serializable id) {
		return goodsMapper.getById(id);
	}

	@Override
	public void insert(Goods goods) {
		goodsMapper.insert(goods);
		jedisDao.hdel("goods", "all");
	}

	@Override
	public List<Goods> findPage(Page page) {
		return goodsMapper.findPage(page);
	}

	@Override
	public List<HomeTop> getHomeHot() {
		return goodsMapper.getHomeHot();
	}

	@Override
	public List<String> getImage(Serializable id) {
		return goodsMapper.getImage(id);
	}
}
