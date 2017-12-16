package cn.itcast.invoice.auth.res.vo;

import java.util.Set;

import cn.itcast.invoice.auth.role.vo.RoleModel;

public class ResModel {
	private Long uuid;
	private String name;
	//action访问路径
	private String url;
	
	//角色
	private Set<RoleModel> roles;
	
	public Set<RoleModel> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
