package com.xawl.car.service;

import java.util.List;
import java.util.Map;

import com.xawl.car.domain.Comment;
import com.xawl.car.domain.Image;
import com.xawl.car.domain.MaintainBusiness;
import com.xawl.car.domain.Service;

public interface MaintainBusinessService {

	List<MaintainBusiness> getHomeBydistance(Map map);

	List<MaintainBusiness> getHomeHot();

	MaintainBusiness getStore(String mbid);

	List<String> getImage(String mbid);

	List<Comment> getCommentList(String mbid);

	List<Service> getClean(Map map);

	MaintainBusiness login(Map map);

	void deleteByService(String sid);

	void updateService(Service service);

	void insertService(Service service);

	List<MaintainBusiness> query(int type);

}
