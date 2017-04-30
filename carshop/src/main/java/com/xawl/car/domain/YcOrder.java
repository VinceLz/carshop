package com.xawl.car.domain;

/*
 * 养车模块中的订单
 */
public class YcOrder {
	public static int ORDER_NO_PAY = -1;// 买家未支付钱
	public static int ORDER_PAY = 0;// 买付钱了
	public static int ORDER_CHECK = -2;// 前台支付成功，等待服务器异步回调确认
	public static int ORDER_FAIL = 1;// 卖家取消了订单
	public static int ORDER_SUCCESS = 2;// 卖家确认
	public static int ORDER_EXCEPTION = -3;// 订单异常，是订单金额与支付金额不一致
	private int yoid;
	private String goodid;
	private String bmname;
	private double price; // 支付的钱
	private Integer ruid;
	private String sname;
	private int mbid;
	private int uid;
	private int type;
	private String uname;
	private String uphone;
	private double realprice; // 原价
	private int status;
	private String qid;
	private String date;
	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getYoid() {
		return yoid;
	}

	public void setYoid(int yoid) {
		this.yoid = yoid;
	}

	public String getGoodid() {
		return goodid;
	}

	public void setGoodid(String goodid) {
		this.goodid = goodid;
	}

	public String getBmname() {
		return bmname;
	}

	public void setBmname(String bmname) {
		this.bmname = bmname;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getRuid() {
		return ruid;
	}

	public void setRuid(Integer ruid) {
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

	public double getRealprice() {
		return realprice;
	}

	public void setRealprice(double realprice) {
		this.realprice = realprice;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "YcOrder [yoid=" + yoid + ", goodid=" + goodid + ", bmname="
				+ bmname + ", price=" + price + ", ruid=" + ruid + ", sname="
				+ sname + ", mbid=" + mbid + ", uid=" + uid + ", uname="
				+ uname + ", uphone=" + uphone + ", realprice=" + realprice
				+ ", status=" + status + "]";
	}

}
