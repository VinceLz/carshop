package com.xawl.car.domain;

/*
 * 用户--优惠劵关系表
 * 规定：uid：0 表示全体用户都拥有。
 * status: 0待使用 1 已使用 2已失效过期   扩展状态3:占用中。防止2个账号同时登陆购买。todo:后期需要
 * 做单点登陆
 */
public class RollUser {
	private int ruid;
	private int uid;
	private int rid;
	private String pastdate;//到期日期
	private String createdate;//开始日期
	private int status;//优惠劵状态
	public int getRuid() {
		return ruid;
	}
	public void setRuid(int ruid) {
		this.ruid = ruid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getPastdate() {
		return pastdate;
	}
	public void setPastdate(String pastdate) {
		this.pastdate = pastdate;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
