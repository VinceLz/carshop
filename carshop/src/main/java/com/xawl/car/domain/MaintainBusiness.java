package com.xawl.car.domain;

import java.util.List;

public class MaintainBusiness {
	private int mbid;
	private String mbname;
	private String bshowimage;
	private List<String> bimage;
	private String baddress;
	private String bdate;
	private String bphone;
	private String uname;
	private String longitude;
	private String latitude;
	private int isHot;
	private String title1;
	private String title2;
	private float score;
	private int purchase; // 已经购买的人
	private int commentcount;// 已经评价的人数
	private String time;
	
	private List<Service> clean;
	private List<Service> mainclean;
	private List<Service> decoration;
	
	public List<Service> getClean() {
		return clean;
	}

	public void setClean(List<Service> clean) {
		this.clean = clean;
	}

	public List<Service> getMainclean() {
		return mainclean;
	}

	public void setMainclean(List<Service> mainclean) {
		this.mainclean = mainclean;
	}

	public List<Service> getDecoration() {
		return decoration;
	}

	public void setDecoration(List<Service> decoration) {
		this.decoration = decoration;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getMbid() {
		return mbid;
	}

	public void setMbid(int mbid) {
		this.mbid = mbid;
	}

	public String getMbname() {
		return mbname;
	}

	public void setMbname(String mbname) {
		this.mbname = mbname;
	}

	

	public String getBshowimage() {
		return bshowimage;
	}

	public void setBshowimage(String bshowimage) {
		this.bshowimage = bshowimage;
	}

	public List<String> getBimage() {
		return bimage;
	}

	public void setBimage(List<String> bimage) {
		this.bimage = bimage;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
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

	public int getIsHot() {
		return isHot;
	}

	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}

	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getPurchase() {
		return purchase;
	}

	public void setPurchase(int purchase) {
		this.purchase = purchase;
	}

	public int getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(int commentcount) {
		this.commentcount = commentcount;
	}

}
