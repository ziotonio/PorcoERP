package cn.itcast.invoice.invoice.store.vo;

import cn.itcast.invoice.auth.emp.vo.EmpModel;

public class StoreModel {
	private Long uuid;
	private String name;
	private String address;
	
	private EmpModel em;
	
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public EmpModel getEm() {
		return em;
	}
	public void setEm(EmpModel em) {
		this.em = em;
	}
	
}
