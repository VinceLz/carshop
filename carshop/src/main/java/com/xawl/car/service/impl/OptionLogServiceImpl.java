package com.xawl.car.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xawl.car.dao.OptionLogMapper;
import com.xawl.car.domain.OptionLog;
import com.xawl.car.service.OptionLogService;

@Service
public class OptionLogServiceImpl implements OptionLogService {
	@Autowired
	private OptionLogMapper optionLogMapperion;

	@Override
	public void insert(OptionLog log) {
		optionLogMapperion.insertLog(log);
	}
}
