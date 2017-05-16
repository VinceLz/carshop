package com.xawl.car.domain;

import java.util.List;

//4s 店
public class Business {
	private int bid;
	private String bname;
	private List<String> bimage; // 4S店的 照片
	private String baddress;// 地址
	private String bdate;// 创建时间
	private String bphone;// 电话
	private String uname;// 店主名字
	private String longitude;// 经度
	private String latitude;// 纬度
	private String type;// 类型
	private Integer isHot; // 热门
	private String myphone;// 私有电话
	private String majorbusiness; // 主营车型
	private String title1;
	private String title2;
	private String distance;
	private String bshowImage;
	private Boolean isActivity = true;
	

	public Boolean getIsActivity() {
		return isActivity;
	}

	public void setIsActivity(Boolean isActivity) {
		if (!isActivity) {
			this.title2 = null;
		}
		this.isActivity = isActivity;
	}

	public String getBshowImage() {
		return bshowImage;
	}

	public void setBshowImage(String bshowImage) {
		this.bshowImage = bshowImage;
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
		if (isActivity) {
			this.title2 = title2;
		}
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getMajorbusiness() {
		return majorbusiness;
	}

	public void setMajorbusiness(String majorbusiness) {
		this.majorbusiness = majorbusiness;
	}

	public String getMyphone() {
		return myphone;
	}

	public void setMyphone(String myphone) {
		this.myphone = myphone;
	}

	public int getBid() {
		return bid;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public void setBid(int bid) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
