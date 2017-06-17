package com.xawl.car.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xawl.car.dao.OrderMapper;
import com.xawl.car.dao.RollMapper;
import com.xawl.car.domain.Order;
import com.xawl.car.domain.YcOrder;
import com.xawl.car.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private RollMapper rollMapper;

	@Override
	public void insert(Order order) {
		orderMapper.insert(order);
	}

	@Override
	public void insertYcorder(YcOrder ycorder) {
		orderMapper.insertYcorder(ycorder);// 插入订单
	}

	@Override
	public List<YcOrder> getOrders(int mbid) {
		return orderMapper.getOrders(mbid);
	}

	@Override
	public void updateOrderStatusByYc(Map map) {
		orderMapper.updateOrderStatusByYc(map);
	}

	@Override
	public List<YcOrder> getByGoodid(String yoid) {
		return orderMapper.getByGoodid(yoid);
	}

	@Override
	public void updateOrderYcStatus(Map map, String goodid) {
		orderMapper.updateOrderYcStatus(map);
		if (goodid != null) {
			List<YcOrder> byGoodid = orderMapper.getByGoodid(goodid);
			for (YcOrder yc : byGoodid) {
				if (yc.getRuid() != null) {
					// 使用了优惠劵，修改为已使用
					Map map2 = new HashMap();
					map2.put("ruid", yc.getRuid());
					map2.put("status", 1);
					rollMapper.updateRollStatus(map2);
				}
			}
		}
	}

	@Override
	public void updateQid(YcOrder byGoodid) {
		// 使用了优惠劵，占用中
		orderMapper.updateQid(byGoodid);
	}

	@Override
	public List<YcOrder> findOrderByMap(Map map) {
		return orderMapper.findOrderByMap(map);

	}

	@Override
	public YcOrder getById(int yoid) {
		return orderMapper.getById(yoid);
	}

}
