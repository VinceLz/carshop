package com.xawl.car.domain;


/*
 * 咨询表
 */
public class Consult {
	private Integer consultId;
	private Integer gid;  //汽车id
	private Integer bid;  //商家id
	private String gname; //汽车名字
	private String model; //型号
	private String phone; //电话
	private String uname; //用户名
	private String bname;  //4S店名
	private String date;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getConsultId() {
		return consultId;
	}

	public void setConsultId(Integer consultId) {
		this.consultId = consultId;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
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

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

}
