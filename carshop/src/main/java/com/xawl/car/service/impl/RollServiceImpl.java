package com.xawl.car.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xawl.car.dao.RollMapper;
import com.xawl.car.domain.RollGrant;
import com.xawl.car.domain.RollUser;
import com.xawl.car.domain.VO.RollVO;
import com.xawl.car.service.RollService;

@Service
public class RollServiceImpl implements RollService {

	@Autowired
	private RollMapper rollMapper;

	@Override
	public RollGrant getRollGrant(String uSER_REGIST) {
		return rollMapper.getRollGrant(uSER_REGIST);
	}

	@Override
	public void insertRollByUid(RollUser map) {
		rollMapper.insertRollByUid(map);
	}

	@Override
	public List<RollVO> getRollByType2Uid(Map map) {
		return rollMapper.getRollByType2Uid(map);
	}

	@Override
	public void deleteByruid(int ruid) {
		rollMapper.deleteByruid(ruid);
	}

	@Override
	public List<RollVO> getRollByUid(int uid) {
		return rollMapper.getRollByUid(uid);
	}

	@Override
	public void updateRollStatus(Map map) {
		rollMapper.updateRollStatus(map);
	}


	


}
