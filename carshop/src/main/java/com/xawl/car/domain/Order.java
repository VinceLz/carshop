package com.xawl.car.domain;

public class Order {
	private Integer oid;
	private String ordertime;

	private Integer status;
	private Integer bid;
	private String bname;
	private String bphone;
	private String uphone;
	private String mname;
	private Integer gid;
	private String gname;  
	private Double total;  //价格
	private String uname;  //用户名
	private Integer uid;  //用户id
	private Integer mid;  //型号id
	private String color;  //颜色
	private String buyWay; //购买方式
	private String city; //城市
	private String cardCity;  //牌
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getBphone() {
		return bphone;
	}
	public void setBphone(String bphone) {
		this.bphone = bphone;
	}
	public String getUphone() {
		return uphone;
	}
	public void setUphone(String uphone) {
		this.uphone = uphone;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getBuyWay() {
		return buyWay;
	}
	public void setBuyWay(String buyWay) {
		this.buyWay = buyWay;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCardCity() {
		return cardCity;
	}
	public void setCardCity(String cardCity) {
		this.cardCity = cardCity;
	}
	
}
