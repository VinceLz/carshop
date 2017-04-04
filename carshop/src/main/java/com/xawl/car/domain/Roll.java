package com.xawl.car.domain;

/*
 * 优惠劵类
 * type 表示优惠劵用于指定的产品类 目前规定：type 1 清洗 2保养 3 装潢  0表示通用
 */
public class Roll {
	private int rid;
	private int type;
	private String rname;
	private int price;
	private int leng;// 过期长度 单位：月 暂时不做，后序扩展 TODO

	public int getLeng() {
		return leng;
	}

	public void setLeng(int leng) {
		this.leng = leng;
	}

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

}
