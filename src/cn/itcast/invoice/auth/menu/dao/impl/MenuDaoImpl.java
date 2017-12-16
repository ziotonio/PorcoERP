package cn.itcast.invoice.auth.menu.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.invoice.auth.menu.dao.dao.MenuDao;
import cn.itcast.invoice.auth.menu.vo.MenuModel;
import cn.itcast.invoice.auth.menu.vo.MenuQueryModel;
import cn.itcast.invoice.util.base.BaseDaoImpl;
import cn.itcast.invoice.util.base.BaseQueryModel;

public class MenuDaoImpl extends BaseDaoImpl<MenuModel> implements MenuDao{
	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		MenuQueryModel mqm = (MenuQueryModel) qm;
		//此处过滤掉系统菜单
		dc.add(Restrictions.not(Restrictions.eq("uuid",1L)));

		if(mqm.getName()!=null && mqm.getName().trim().length()>0){
			dc.add(Restrictions.like("name", "%"+mqm.getName().trim()+"%"));
		}
		if(mqm.getParent()!=null && mqm.getParent().getUuid()!=null && mqm.getParent().getUuid()!= -1L){
			dc.createAlias("parent", "p");
			dc.add(Restrictions.eq("p.uuid", mqm.getParent().getUuid()));
		}
		
	}
	//待定
	public List<MenuModel> getByUuidAndPuuidIsOne() {
		String hql = "from MenuModel where uuid = ? or parent.uuid = ?";
		//TODO 此处使用的是固定值（基于需求决定的）
		return this.getHibernateTemplate().find(hql,1L,1L);
	}

	public List<MenuModel> getByPuuidIsOne() {
		String hql = "from MenuModel where parent.uuid = ?";
		//TODO 此处使用的是固定值（基于需求决定的）
		return this.getHibernateTemplate().find(hql,1L);
	}
	
	public List<MenuModel> getByPuuid(Long puuid) {
		String hql = "from MenuModel where parent.uuid = ?";
		return this.getHibernateTemplate().find(hql,puuid);
	}
	
	public List<MenuModel> getParentByEmpUuid(Long empUuid) {
		//条件是人的uuid
		//查询的是菜单
		//Menu->Role->Emp
		//from MenuModel mm join mm.roles rm join rm.emps em where em.uuid = ?
		//String hql = "select distinct mm from MenuModel mm join mm.roles rm join rm.emps em where em.uuid = ? and mm.parent.uuid = ?";
		//return this.getHibernateTemplate().find(hql,uuid,1L);
		return getMenusByPuuidAndEmp(1L,empUuid);
	}

	public List<MenuModel> getMenusByPuuidAndEmp(Long puuid, Long empUuid) {
		//父uuid指定，需要使用用户过滤一下
		//from MenuModel mm join mm.roles rm join rm.emps em where em.uuid = ? 
		String hql = "select distinct mm from MenuModel mm join mm.roles rm join rm.emps em where mm.parent.uuid = ? and em.uuid = ? order by mm.uuid";
		return this.getHibernateTemplate().find(hql,puuid,empUuid);
	}
}





