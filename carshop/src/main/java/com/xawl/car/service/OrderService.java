package com.xawl.car.service;

import java.util.List;

import com.xawl.car.domain.Order;
import com.xawl.car.domain.YcOrder;

public interface OrderService {

	void insert(Order order);

	void insertYcorder(YcOrder ycorder);

	List<YcOrder> getOrders(int mbid);

}
