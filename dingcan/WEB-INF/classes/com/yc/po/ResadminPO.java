package com.yc.po;

public class ResadminPO {

	private Integer Id;
	private String name;
	private String passwd;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	@Override
	public String toString() {
		return "ResadminPO [Id=" + Id + ", name=" + name + ", passwd=" + passwd + "]";
	}
	
}
