package com.xawl.car.domain;

public class Service {
	/*
	 * type 1 清洗 2保养 3 装潢
	 */
	public static int CLEAN = 1;
	public static int MAINTAIN = 2;
	public static int DECORATION = 3;
	private int sid;
	private String sname;
	private String sdesc;
	private float oldprice;
	private float newprice;
	private int mbid;
	private int type;
	
	private String creatdate;
	public int getSid() {
		return sid;
	}
	
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSdesc() {
		return sdesc;
	}
	public void setSdesc(String sdesc) {
		this.sdesc = sdesc;
	}
	public float getOldprice() {
		return oldprice;
	}
	public void setOldprice(float oldprice) {
		this.oldprice = oldprice;
	}
	public float getNewprice() {
		return newprice;
	}
	public void setNewprice(float newprice) {
		this.newprice = newprice;
	}
	public int getMbid() {
		return mbid;
	}
	public void setMbid(int mbid) {
		this.mbid = mbid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCreatdate() {
		return creatdate;
	}
	public void setCreatdate(String creatdate) {
		this.creatdate = creatdate;
	}
	@Override
	public String toString() {
		return "Service [sid=" + sid + ", sname=" + sname + ", sdesc=" + sdesc
				+ ", oldprice=" + oldprice + ", newprice=" + newprice
				+ ", mbid=" + mbid + ", type=" + type + ", creatdate="
				+ creatdate + "]";
	}
	
}
