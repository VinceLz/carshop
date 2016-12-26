package com.xawl.car.domain;


/*
 * 咨询表
 */
public class Consult {
	private Integer consultId;
	
	private String phone; //电话
	private String uname; //用户名
	private String date;
	private Integer mid;
	public Integer getConsultId() {
		return consultId;
	}
	public void setConsultId(Integer consultId) {
		this.consultId = consultId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	

	
}
