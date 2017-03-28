package com.xawl.car.service;

import java.util.Map;

import com.xawl.car.domain.User;

public interface UserService {
	User getUserByUlogin(String ulogin);

	void insertregist(User user);

	void updateImage(Map map);

	User getUser(Map map);

	void update(User user);

	User getUserByToken(String token);
}
