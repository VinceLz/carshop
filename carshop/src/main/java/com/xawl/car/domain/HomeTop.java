package com.xawl.car.domain;

/**
 * 首页类型
 * 
 * @author kernel 0 是轮播图 1 是活动表    2是搜索轮播图   3养车轮播图
 * order   0是购车   1是养车
 */
public class HomeTop {
	private  Integer hid;
	private Integer type;// 类型
	private String image;
	private String url;
	private String title;
	private Integer isHot;
	private Integer orders;
	private String date;
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "HomeTop [type=" + type + ", image=" + image + ", url=" + url
				+ ", title=" + title + ", isHot=" + isHot + "]";
	}

	public Integer getHid() {
		return hid;
	}

	public void setHid(Integer hid) {
		this.hid = hid;
	}
	

}
