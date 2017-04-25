package com.xawl.car.service;

import java.util.List;
import java.util.Map;

import com.xawl.car.domain.RollGrant;
import com.xawl.car.domain.RollUser;
import com.xawl.car.domain.VO.RollVO;

public interface RollService {

	RollGrant getRollGrant(String uSER_REGIST);

	void insertRollByUid(RollUser rollUser);

	List<RollVO> getRollByType2Uid(Map map);

	void deleteByruid(int ruid);

	List<RollVO> getRollByUid(int uid);

	void updateRollStatus(Map map);

	RollVO getByRuid(Integer ruid);


}
