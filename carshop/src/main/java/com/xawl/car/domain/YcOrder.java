package com.xawl.car.domain;

/*
 * 养车模块中的订单
 */
public class YcOrder {
	public static int ORDER_NO_PAY = -1;// 买家未支付钱 后台屏蔽(不需要注意的状态)
	public static int ORDER_PAY = 0;// 买付钱了 需要注意的状态
	public static int ORDER_CHECK = -2;// 前台支付成功，等待服务器异步回调确认
	public static int ORDER_FAIL = 1;// 卖家取消了订单 等待退款
	public static int ORDER_BLACK = 3; // 退款成功
	public static int ORDER_SUCCESS = 2;// 卖家确认 完成
	public static int ORDER_EXCEPTION = -3;// 订单异常，是订单金额与支付金额不一致
	// 异常订单应该是取消订单并且退款
	private int yoid;
	private String goodid;
	private String bmname;
	private double price; // 支付的钱
	private Integer ruid;
	private String sname;
	private int mbid;
	private int uid;
	private String uname;
	private String uphone;
	private double realprice; // 原价
	private int status;
	private String qid;
	private String date;
	private int type = 1;// 购车&养车 0 购车 1养车 默认养车
	private String bphone;
	private String mname;
	private int mid;
	private int gid;
	private String gname;
	private String color;
	private String buyWay;
	private String city;
	private String cardCity;
	private int bid;
	private String buytime;
	private String bname;
	private String lastTime;//最后修改时间
	
	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getBuytime() {
		return buytime;
	}

	public void setBuytime(String buytime) {
		this.buytime = buytime;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBphone() {
		return bphone;
	}

	public void setBphone(String bphone) {
		this.bphone = bphone;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
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
				+ ", status=" + status + ", qid=" + qid + ", date=" + date
				+ ", type=" + type + ", bphone=" + bphone + ", mname=" + mname
				+ ", mid=" + mid + ", gid=" + gid + ", gname=" + gname
				+ ", color=" + color + ", buyWay=" + buyWay + ", city=" + city
				+ ", cardCity=" + cardCity + ", bid=" + bid + ", buytime="
				+ buytime + "]";
	}

}
