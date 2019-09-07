package com.yc.po;

public class TaoCanPO {

	private Integer tid;
	private String tname;
	private String cname;
	private String tphoto;
	private Integer tprice;
	private String caixi;
	private String yanxi;
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getTphoto() {
		return tphoto;
	}
	public void setTphoto(String tphoto) {
		this.tphoto = tphoto;
	}
	public Integer getTprice() {
		return tprice;
	}
	public void setTprice(Integer tprice) {
		this.tprice = tprice;
	}
	public String getCaixi() {
		return caixi;
	}
	public void setCaixi(String caixi) {
		this.caixi = caixi;
	}
	public String getYanxi() {
		return yanxi;
	}
	public void setYanxi(String yanxi) {
		this.yanxi = yanxi;
	}
	@Override
	public String toString() {
		return "TaoCanPO [tid=" + tid + ", tname=" + tname + ", cname=" + cname + ", tphoto=" + tphoto + ", tprice="
				+ tprice + ", caixi=" + caixi + ", yanxi=" + yanxi + "]";
	}
	
	
}
