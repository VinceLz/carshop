package com.xawl.car.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xawl.car.dao.UserMapper;
import com.xawl.car.domain.User;
import com.xawl.car.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserByUlogin(String ulogin) {
		return userMapper.getUserByUlogin(ulogin);
	}

	@Override
	public void insertregist(User user) {
		userMapper.insertregist(user);
	}

	@Override
	public void updateImage(Map map) {
		userMapper.updateImage(map);
	}

	@Override
	public User getUser(Map map) {
		return userMapper.getUser(map);
	}

	@Override
	public void update(User user) {
		userMapper.update(user);
	}

	@Override
	public User getUserByToken(String token) {
		return userMapper.getUserByToken(token);
	}

	@Override
	public void updatePwd(User user) {
		userMapper.updatePwd(user);
	}

}
