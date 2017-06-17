package com.xawl.car.dao;

import java.util.List;
import java.util.Map;

import com.xawl.car.domain.Order;
import com.xawl.car.domain.YcOrder;

public interface OrderMapper {

	void insert(Order order);

	void insertYcorder(YcOrder ycorder);

	List<YcOrder> getOrders(int mbid);

	void updateOrderStatusByYc(Map map);

	void updateOrderYcStatus(Map map);

	List<YcOrder> getByGoodid(String yoid);

	void updateQid(YcOrder byGoodid);

	List<YcOrder> findOrderByMap(Map map);

	YcOrder getById(int yoid);

}
