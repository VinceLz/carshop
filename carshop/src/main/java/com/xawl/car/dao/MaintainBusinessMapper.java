package com.xawl.car.dao;

import java.util.List;
import java.util.Map;

import com.xawl.car.domain.Comment;
import com.xawl.car.domain.MaintainBusiness;

public interface MaintainBusinessMapper {

	List<MaintainBusiness> getHomeBydistance(Map map);

	List<MaintainBusiness> getHomeHot();

	MaintainBusiness getStore(String mbid);

	List<String> getImage(String mbid);

	List<Comment> getCommentList(String mbid);

	List<com.xawl.car.domain.Service> getClean(Map mbid);

	MaintainBusiness login(Map map);

	void deleteByService(String sid);

	void updateService(com.xawl.car.domain.Service service);

	void insertService(com.xawl.car.domain.Service service);

	List<MaintainBusiness> query(int type);

	int updatePwd(Map map);

}
