package com.xawl.car.util;

import java.util.Set;

import com.xawl.car.domain.RollGrant;
import com.xawl.car.domain.RollUser;
import com.xawl.car.service.RollService;

public class RollUtil {

	public static void insert(RollGrant rollGrant, RollService rollService,
			int uid, Object object) {

		// 获取rollGrant的条件list
		// object 是活动传入的用户已有的条件
		// 然后根据该条件与param组合中的条件进行判断返回rids的集合
		if (rollGrant.getStatus() == 1) {
			// 该规则启动
			Set<Integer> rids = rollGrant.getHandler().statisfy(object,
					rollGrant.getParam());
			// rids数组表示我们要赠送给uid对应的用户rids中的各类优惠劵
			RollUser rollUser = new RollUser();
			rollUser.setUid(uid);
			rollUser.setStatus(0);
			rollUser.setCreatedate(DateUtil.getSqlDate());
			rollUser.setPastdate(DateUtil.getSqlDate2addMouth(1));
			for (int i : rids) {
				rollUser.setRid(i);
				rollService.insertRollByUid(rollUser);// 插入优惠劵
			}
			// 插入完毕
			return;

		} else {
			// 该规则暂停
			return;
		}
	}

}
