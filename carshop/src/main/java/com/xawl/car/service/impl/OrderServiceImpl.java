package com.xawl.car.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xawl.car.dao.OrderMapper;
import com.xawl.car.domain.Order;
import com.xawl.car.domain.YcOrder;
import com.xawl.car.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderMapper orderMapper;

	@Override
	public void insert(Order order) {
		orderMapper.insert(order);
	}

	@Override
	public void insertYcorder(YcOrder ycorder) {
		orderMapper.insertYcorder(ycorder);
	}

	@Override
	public List<YcOrder> getOrders(int mbid) {
		return orderMapper.getOrders(mbid);
	}

}
