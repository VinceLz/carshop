package com.xawl.car.domain.VO;

public class RollVO {
	private int rid;
	private int type;
	private String rname;
	private int price;
	private String pastdate;// 到期日期
	private String createdate;// 开始日期
	private int status;// 优惠劵状态
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
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
