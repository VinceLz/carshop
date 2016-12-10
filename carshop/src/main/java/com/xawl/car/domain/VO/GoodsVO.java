package com.xawl.car.domain.VO;

import java.util.List;

import com.xawl.car.domain.Model;

public class GoodsVO {
	private Integer gid;
	private String gname;
	private Integer bid;// 商家id
	private String gimage[];// 图片
	private String bname;// 店家名
	private String gdate;// 上传时间
	private Integer sale;// 售量
	private String title; // 优惠信息
	private Integer isHot;
	private double maxprice;
	private double minprice;
	private String gshowImage;

	private List<Model> childs;

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public String[] getGimage() {
		return gimage;
	}

	public void setGimage(String gimage) {
		if (gimage != null && !gimage.isEmpty()) {
			this.gimage = gimage.split(",");
		} else {
			this.gimage = null;
		}
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getGdate() {
		return gdate;
	}

	public void setGdate(String gdate) {
		this.gdate = gdate;
	}

	public Integer getSale() {
		return sale;
	}

	public void setSale(Integer sale) {
		this.sale = sale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public double getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(double maxprice) {
		this.maxprice = maxprice;
	}

	public double getMinprice() {
		return minprice;
	}

	public void setMinprice(double minprice) {
		this.minprice = minprice;
	}

	public String getGshowImage() {
		return gshowImage;
	}

	public void setGshowImage(String gshowImage) {
		this.gshowImage = gshowImage;
	}

	public List<Model> getChild() {
		return childs;
	}

	public void setChild(List<Model> childs) {
		this.childs = childs;
	}

}
