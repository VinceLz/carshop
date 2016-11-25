package com.xawl.car.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xawl.car.dao.ConsultMapper;
import com.xawl.car.domain.Consult;
import com.xawl.car.service.ConsultService;

/**
 * @author kernel
 * 
 */
@Service
public class ConsultServiceImpl implements ConsultService {
	@Autowired
	private ConsultMapper consultMapper;

	@Override
	public void insert(Consult consult) {
		consultMapper.insert(consult);
	}
}
