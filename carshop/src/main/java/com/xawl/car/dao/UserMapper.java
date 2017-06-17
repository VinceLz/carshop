package com.xawl.car.dao;

import java.util.Map;

import com.xawl.car.domain.User;

public interface UserMapper {

	User getUserByUlogin(String ulogin);

	void insertregist(User user);

	void updateImage(Map map);

	User getUser(Map map);

	void update(User user);

	User getUserByToken(String token);

	void updatePwd(User user);

	int updatePwdByOld(Map map);

}
