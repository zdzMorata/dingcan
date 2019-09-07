package com.yc.po;

public class HistoryPO {

	private Integer hid;
	private String taocan;
	private String cid;
	private Integer cprice;
	private Integer zuoshu;
	private Integer dindan;
	private Integer lirun;
	private String name;
	private Integer phone;
	public Integer getHid() {
		return hid;
	}
	public void setHid(Integer hid) {
		this.hid = hid;
	}
	public String getTaocan() {
		return taocan;
	}
	public void setTaocan(String taocan) {
		this.taocan = taocan;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public Integer getCprice() {
		return cprice;
	}
	public void setCprice(Integer cprice) {
		this.cprice = cprice;
	}
	public Integer getZuoshu() {
		return zuoshu;
	}
	public void setZuoshu(Integer zuoshu) {
		this.zuoshu = zuoshu;
	}
	public Integer getDindan() {
		return dindan;
	}
	public void setDindan(Integer dindan) {
		this.dindan = dindan;
	}
	public Integer getLirun() {
		return lirun;
	}
	public void setLirun(Integer lirun) {
		this.lirun = lirun;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPhone() {
		return phone;
	}
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "History [hid=" + hid + ", taocan=" + taocan + ", cid=" + cid + ", cprice=" + cprice + ", zuoshu="
				+ zuoshu + ", dindan=" + dindan + ", lirun=" + lirun + ", name=" + name + ", phone=" + phone + "]";
	}
	
	
}
