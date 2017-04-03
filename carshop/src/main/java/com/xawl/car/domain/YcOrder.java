package com.xawl.car.domain;
/*
 * 养车模块中的订单
 */
public class YcOrder {
	public static int ORDER_PAY=0;//买付钱了
	public static int ORDER_FAIL=1;//卖家取消了订单
	public static int ORDER_SUCCESS=2;//卖家确认
	private int yoid;
	private String bmname;
	private float price; //支付的钱
	private int ruid;
	private String sname;
	private int mbid;
	private int uid;
	private String uname;
	private String uphone;
	private float realprice; //原价
	private int status; 
	public int getYoid() {
		return yoid;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setYoid(int yoid) {
		this.yoid = yoid;
	}
	public String getBmname() {
		return bmname;
	}
	public void setBmname(String bmname) {
		this.bmname = bmname;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getRuid() {
		return ruid;
	}
	public void setRuid(int ruid) {
		this.ruid = ruid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public int getMbid() {
		return mbid;
	}
	public void setMbid(int mbid) {
		this.mbid = mbid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUphone() {
		return uphone;
	}
	public void setUphone(String uphone) {
		this.uphone = uphone;
	}
	public float getRealprice() {
		return realprice;
	}
	public void setRealprice(float realprice) {
		this.realprice = realprice;
	}
	
}
