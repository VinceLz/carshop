package com.xawl.car.domain.condition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xawl.car.domain.ConditionHandler;
import com.xawl.car.domain.Param;

public class PayConditionHandler implements ConditionHandler {

	@Override
	public Set<Integer> statisfy(Object object, List<Param> params) {
		Set<Integer> set = new HashSet();
		float price = (float) object;
		for (Param p : params) {
			String[] split = p.getCondition().split("-");
			if (Float.parseFloat(split[0]) <= price
					&& Float.parseFloat(split[1]) >= price) {
				if (p.getRids() != null) {
					System.out.println(p.getRids());
					set.addAll(p.getRids());
				}
			}
		}
		System.out.println(set);
		return set;
	}

}
