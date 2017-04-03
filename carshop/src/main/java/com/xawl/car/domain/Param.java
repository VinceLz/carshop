package com.xawl.car.domain;

import java.util.Set;

/*
 * 条件类
 */
public class Param {
	private String condition;
	private Set<Integer> rids;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	

	public Set<Integer> getRids() {
		return rids;
	}

	public void setRids(Set<Integer> rids) {
		this.rids = rids;
	}

	@Override
	public String toString() {
		return "Param [condition=" + condition + ", rids=" + rids + "]";
	}
	
}
