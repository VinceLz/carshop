package com.xawl.car.dao;

import java.util.List;
import java.util.Map;

import com.xawl.car.domain.RollGrant;
import com.xawl.car.domain.RollUser;
import com.xawl.car.domain.VO.RollVO;

public interface RollMapper {

	RollGrant getRollGrant(String uSER_REGIST);

	void insertRollByUid(RollUser map);


	List<RollVO> getRollByType2Uid(Map map);

	void deleteByruid(int ruid);

	List<RollVO> getRollByUid(int uid);

	
}
