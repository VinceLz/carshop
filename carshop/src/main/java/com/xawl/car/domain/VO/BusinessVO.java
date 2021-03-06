package com.xawl.car.domain.VO;

import java.util.List;

import com.xawl.car.domain.Goods;

public class BusinessVO {

	// 4S店的详情页实体类
	private Integer bid;
	private String bname;
	private List<String> bimage;
	private String baddress;
	private String bdate;// 创建时间
	private String bphone;// 电话
	private String uname;// 店主名字
	private String majorbusiness; // 主营车型
	private List<Goods> childs;
	private String bshowImage;
	private String title1;
	private String title2;
	private Double stages12;
	private Double stages24;
	private Double stages36;
	private String stages="分期政策"; //为了防止app坏掉，先向下兼容
	private String longitude;
	private String latitude;
	


	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getStages() {
		return stages;
	}

	public void setStages(String stages) {
		this.stages = stages;
	}
	public Double getStages12() {
		return stages12;
	}

	public void setStages12(Double stages12) {
		this.stages12 = stages12;
	}

	public Double getStages24() {
		return stages24;
	}

	public void setStages24(Double stages24) {
		this.stages24 = stages24;
	}

	public Double getStages36() {
		return stages36;
	}

	public void setStages36(Double stages36) {
		this.stages36 = stages36;
	}

	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public String getTitle2() {
		return title2;
	}

	

	public void setTitle2(String title2) {
		this.title2 = title2;
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

	public List<String> getBimage() {
		return bimage;
	}

	public void setBimage(List<String> bimage) {
		this.bimage = bimage;
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

	public String getBshowImage() {
		return bshowImage;
	}

	public void setBshowImage(String bshowImage) {
		this.bshowImage = bshowImage;
	}

}
