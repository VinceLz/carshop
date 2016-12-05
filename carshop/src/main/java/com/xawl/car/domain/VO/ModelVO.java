package com.xawl.car.domain.VO;

import java.util.List;


//车
public class ModelVO {
	// 级别，价格，排量，驱动，燃料，变速箱，国别，生产方式，结构，座位
	// 品牌，型号，车名
	private Integer mid;
	private String gname;
	private String bname;// 店家名
	private List<String> level; // 等级
	private List<String> output;// 排量
	private List<String> drive;// 驱动
	private List<String> fuel;// 燃料
	private List<String> transmission;// 变速相
	private List<String> country;// 国家
	private List<String> produce;// 生产方式
	private List<String> structure;// 结构
	private List<String> seat;// 座位
	private String keyword; // 关键字
	private Double maxprice; // 最大价格
	private Double minprice; // 最小价格

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public List<String> getLevel() {
		return level;
	}

	public void setLevel(List<String> level) {
		if (level==null||level.size() == 0) {
			this.level = null;
		} else {
			this.level = level;
		}

	}

	public List<String> getOutput() {
		return output;
	}

	public void setOutput(List<String> output) {
		if (output==null||output.size() == 0) {
			this.output = null;
		} else {
			this.output = output;
		}
	}

	public List<String> getDrive() {
		return drive;
	}

	public void setDrive(List<String> drive) {
		if (drive==null||drive.size() == 0) {
			this.drive = null;
		} else {
			this.drive = drive;
		}

	}

	public List<String> getFuel() {
		return fuel;
	}

	public void setFuel(List<String> fuel) {
		if (fuel==null||fuel.size() == 0) {
			this.fuel = null;
		} else {
			this.fuel = fuel;
		}
	}

	public List<String> getTransmission() {
		return transmission;
	}

	public void setTransmission(List<String> transmission) {
		if (transmission==null||transmission.size() == 0) {
			this.transmission = null;
		} else {
			this.transmission = transmission;
		}

	}

	public List<String> getCountry() {
		return country;
	}

	public void setCountry(List<String> country) {
		if (country==null||country.size() == 0) {
			this.country = null;
		} else {
			this.country = country;
		}

	}

	public List<String> getProduce() {
		return produce;
	}

	public void setProduce(List<String> produce) {
		if (produce==null||produce.size() == 0) {
			this.produce = null;
		} else {
			this.produce = produce;
		}

	}

	public List<String> getStructure() {
		return structure;
	}

	public void setStructure(List<String> structure) {
		if (structure==null||structure.size() == 0) {
			this.structure = null;
		} else {
			this.structure = structure;
		}
	}

	public List<String> getSeat() {
		return seat;
	}

	public void setSeat(List<String> seat) {
		if (seat==null||seat.size() == 0) {
			this.seat = null;
		} else {
			this.seat = seat;
		}
	}

	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Double getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(Double maxprice) {
		this.maxprice = maxprice;
	}

	public Double getMinprice() {
		return minprice;
	}

	public void setMinprice(Double minprice) {
		this.minprice = minprice;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return "ModelVO [mid=" + mid + ", gname=" + gname + ", bname=" + bname
				+ ", level=" + level + ", output=" + output + ", drive="
				+ drive + ", fuel=" + fuel + ", transmission=" + transmission
				+ ", country=" + country + ", produce=" + produce
				+ ", structure=" + structure + ", seat=" + seat + ", keyword="
				+ keyword + ", maxprice=" + maxprice + ", minprice=" + minprice
				+ "]";
	}
}
