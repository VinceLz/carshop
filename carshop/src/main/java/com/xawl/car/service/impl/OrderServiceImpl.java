package com.xawl.car.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xawl.car.dao.OrderMapper;
import com.xawl.car.domain.Order;
import com.xawl.car.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderMapper orderMapper; 
	@Override
	public void insert(Order order) {
		orderMapper.insert(order);
	}

}
