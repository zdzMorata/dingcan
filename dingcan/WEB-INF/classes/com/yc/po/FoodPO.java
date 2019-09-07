package com.yc.po;

public class FoodPO {

	private Integer cid;
	private String caixi;
	private String cname;
	private Integer cprice;
	private String cphoto;
	private String leix;
	private Integer didan;
	private Integer lirun;
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCaixi() {
		return caixi;
	}
	public void setCaixi(String caixi) {
		this.caixi = caixi;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Integer getCprice() {
		return cprice;
	}
	public void setCprice(Integer cprice) {
		this.cprice = cprice;
	}
	public String getCphoto() {
		return cphoto;
	}
	public void setCphoto(String cphoto) {
		this.cphoto = cphoto;
	}
	public String getLeix() {
		return leix;
	}
	public void setLeix(String leix) {
		this.leix = leix;
	}
	
	public Integer getDidan() {
		return didan;
	}
	public void setDidan(Integer didan) {
		this.didan = didan;
	}
	public Integer getLirun() {
		return lirun;
	}
	public void setLirun(Integer lirun) {
		this.lirun = lirun;
	}
	@Override
	public String toString() {
		return "FoodPO [cid=" + cid + ", caixi=" + caixi + ", cname=" + cname + ", cprice=" + cprice + ", cphoto="
				+ cphoto + ", leix=" + leix + ", didan=" + didan + ", lirun=" + lirun + "]";
	}
	
	
}
