package com.xawl.car.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/*
 * 送优惠劵的满足条件处理类
 */
public interface ConditionHandler extends Serializable {
	/*
	 * 根据传入的条件object 判断是否满足 true 满足 false 不满足
	 */

	Set<Integer> statisfy(Object object, List<Param> params);
}
