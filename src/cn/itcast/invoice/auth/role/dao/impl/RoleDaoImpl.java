package cn.itcast.invoice.auth.role.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.invoice.auth.role.dao.dao.RoleDao;
import cn.itcast.invoice.auth.role.vo.RoleModel;
import cn.itcast.invoice.auth.role.vo.RoleQueryModel;
import cn.itcast.invoice.util.base.BaseDaoImpl;
import cn.itcast.invoice.util.base.BaseQueryModel;

public class RoleDaoImpl extends BaseDaoImpl<RoleModel> implements RoleDao{
	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		RoleQueryModel rqm = (RoleQueryModel) qm;
		//TODO 添加自定义查询规则
	}
}
