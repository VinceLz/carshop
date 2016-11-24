package com.xawl.car.domain.VO;

import java.util.List;

import com.xawl.car.domain.Goods;

public class BusinessVO {

	// 4S店的详情页实体类
	private Integer bid;
	private String bname;
	private String bimage[];
	private String baddress;
	private String bdate;// 创建时间
	private String bphone;// 电话
	private String uname;// 店主名字
	private String majorbusiness; // 主营车型
	private List<Goods> childs;

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

	public String[] getBimage() {
		return bimage;
	}

	public void setBimage(String bimage) {
		if (bimage != null && !bimage.isEmpty()) {
			this.bimage = bimage.split(",");
		} else {
			this.bimage = null;
		}

	}

	public String getBaddress() {
		return baddress;
	}

	public void setBaddress(String baddress) {
		this.baddress = baddress;
	}

	public String getBdate() {
		return bdate;
	}

	public void setBdate(String bdate) {
		this.bdate = bdate;
	}

	public String getBphone() {
		return bphone;
	}

	public void setBphone(String bphone) {
		this.bphone = bphone;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getMajorbusiness() {
		return majorbusiness;
	}

	public void setMajorbusiness(String majorbusiness) {
		this.majorbusiness = majorbusiness;
	}

	public List<Goods> getChilds() {
		return childs;
	}

	public void setChilds(List<Goods> childs) {
		this.childs = childs;
	}

	
}
