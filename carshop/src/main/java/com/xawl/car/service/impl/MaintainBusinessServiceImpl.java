package com.xawl.car.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xawl.car.dao.MaintainBusinessMapper;
import com.xawl.car.domain.Comment;
import com.xawl.car.domain.Image;
import com.xawl.car.domain.MaintainBusiness;
import com.xawl.car.service.MaintainBusinessService;

@Service
public class MaintainBusinessServiceImpl implements MaintainBusinessService {

	@Autowired
	private MaintainBusinessMapper maintainBusinessMapper;

	@Override
	public List<MaintainBusiness> getHomeBydistance(Map map) {
		return maintainBusinessMapper.getHomeBydistance(map);
	}

	@Override
	public List<MaintainBusiness> getHomeHot() {
		return maintainBusinessMapper.getHomeHot();
	}

	@Override
	public MaintainBusiness getStore(String mbid) {
		return maintainBusinessMapper.getStore(mbid);
	}

	@Override
	public List<String> getImage(String mbid) {
		return maintainBusinessMapper.getImage(mbid);
	}

	@Override
	public List<Comment> getCommentList(String mbid) {
		return maintainBusinessMapper.getCommentList(mbid);
	}

	@Override
	public List<com.xawl.car.domain.Service> getClean(Map mbid) {
		return maintainBusinessMapper.getClean(mbid);
	}
}
