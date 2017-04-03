package com.xawl.car.domain;

import java.util.List;

import com.xawl.car.util.JsonUtils;

/*
 * 优惠劵方法规则：
 * 	用户注册或者动作触发
 *  用户购买某产品后赠送
 *  
 *  如何实现后台完全动态控制优惠劵的发放规则
 *  
 *  status 0 表示停止  1 表示启用
 */
public class RollGrant {
	public static String USER_REGIST = "注册发放";
	public static String USER_PAY = "购买发放";
	private int rgid;

	// 是什么活动
	private String rgname;// 活动名
	private int status;// 该规则是否启用
	// 需要发放哪些优惠劵
	private List<Param> param;// 优惠条件判断类Param
	// 是否满足条件的处理类
	private ConditionHandler handler; // 对应的数据库中保存对应的处理类的全路径名

	public int getRgid() {
		return rgid;
	}

	public void setRgid(int rgid) {
		this.rgid = rgid;
	}

	

	public String getRgname() {
		return rgname;
	}

	public void setRgname(String rgname) {
		this.rgname = rgname;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Param> getParam() {
		return param;
	}

	public void setParam(String param) {
		
		List<Param> jsonToList = JsonUtils.jsonToList(param, Param.class);
		System.out.println(jsonToList);
		this.param = jsonToList;

	}

	public ConditionHandler getHandler() {
		return handler;
	}

	public void setHandler(String classname) {
		try {
			ConditionHandler newInstance = (ConditionHandler) Class.forName(
					classname).newInstance();
			this.handler = newInstance;
		} catch (Exception e) {
			e.printStackTrace();
			this.handler = null;
		}
	}

}
