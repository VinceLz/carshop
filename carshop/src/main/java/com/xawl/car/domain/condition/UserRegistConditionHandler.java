package com.xawl.car.domain.condition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xawl.car.domain.ConditionHandler;
import com.xawl.car.domain.Param;

public class UserRegistConditionHandler implements ConditionHandler {

	@Override
	public Set<Integer> statisfy(Object object, List<Param> params) {
		// 注册传入的object为null
		Set<Integer> set = new HashSet();
		for (Param p : params) {
			if(p.getRids()!=null){
				set.addAll(p.getRids());
			}
			
		}
		return set;
	}

}
