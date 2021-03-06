package com.xawl.car.domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import com.xawl.car.util.JsonUtils;

/*
 * 车的 型号
 */
public class Model {
	private Integer mid;//
	private String mname; // 型号名字
	private Integer gid;//
	private LinkedHashMap<String, LinkedHashMap<String, String>> configure;// 配置详细表，目前用json解析保存
	private String level; // 等级
	private String output;// 排量
	private String drive;// 驱动
	private String fuel;// 燃料
	private String transmission;// 变速相
	private String country;// 国家
	private String produce;// 生产方式
	private String structure;// 结构
	private String seat;// 座位
	private String keyword; // 关键字
	private Integer isHot;
	private Double guidegprice;// 指导价
	private Double gprice;// 价格
	private Integer bid;
	private String gname; //
	private List<String> mimage;// 大图片
	private String mshowImage;
	private String mtitle;
	private String bphone;
	
	
	public String getBphone() {
		return bphone;
	}

	public void setBphone(String bphone) {
		this.bphone = bphone;
	}

	public String getMshowImage() {
		return mshowImage;
	}

	public void setMshowImage(String mshowImage) {
		this.mshowImage = mshowImage;
	}

	public String getMtitle() {
		return mtitle;
	}

	public void setMtitle(String mtitle) {
		this.mtitle = mtitle;
	}

	public List<String> getMimage() {
		return mimage;
	}

	public void setMimage(List<String> mimage) {
		this.mimage = mimage;
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

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> getConfigure() {
		return configure;
	}

	public void setConfigure(String configure) {
		if (configure == null || configure.isEmpty()) {
			this.configure = null;
		} else {
			LinkedHashMap<String, LinkedHashMap<String, String>> jsonToPojo = (LinkedHashMap<String, LinkedHashMap<String, String>>) JsonUtils
					.jsonToPojo(configure, LinkedHashMap.class);
			// 解析为map形式
			this.configure = jsonToPojo;
		}
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getDrive() {
		return drive;
	}

	public void setDrive(String drive) {
		this.drive = drive;
	}

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProduce() {
		return produce;
	}

	public void setProduce(String produce) {
		this.produce = produce;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public Double getGuidegprice() {
		return guidegprice;
	}

	public void setGuidegprice(Double guidegprice) {
		this.guidegprice = guidegprice;
	}

	public Double getGprice() {
		return gprice;
	}

	public void setGprice(Double gprice) {
		this.gprice = gprice;
	}

	JsonConfig jsonConfig = new JsonConfig();
	PropertyFilter filter = new PropertyFilter() {
		public boolean apply(Object object, String fieldName, Object fieldValue) {
			return null == fieldValue || "".equals(fieldValue);
		}
	};
}
