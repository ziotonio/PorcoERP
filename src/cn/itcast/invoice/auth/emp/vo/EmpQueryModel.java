package cn.itcast.invoice.auth.emp.vo;

import cn.itcast.invoice.auth.dep.vo.DepModel;
import cn.itcast.invoice.util.base.BaseQueryModel;
import cn.itcast.invoice.util.format.FormatUtil;

public class EmpQueryModel extends EmpModel implements BaseQueryModel{
	//此处添加的是所有的要查询的字段名
	private Long birthday2;
	private Long lastLoginTime2;
	
	private String birthday2View;
	private String lastLoginTime2View;
	
	public String getBirthday2View() {
		return birthday2View;
	}
	public String getLastLoginTime2View() {
		return lastLoginTime2View;
	}
	public Long getBirthday2() {
		return birthday2;
	}
	public void setBirthday2(Long birthday2) {
		this.birthday2 = birthday2;
		this.birthday2View = FormatUtil.formatDate(birthday2);
	}
	public Long getLastLoginTime2() {
		return lastLoginTime2;
	}
	public void setLastLoginTime2(Long lastLoginTime2) {
		this.lastLoginTime2 = lastLoginTime2;
		this.lastLoginTime2View = FormatUtil.formatDate(lastLoginTime2);
	}
}
