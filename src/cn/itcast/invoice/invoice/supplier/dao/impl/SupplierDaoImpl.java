package cn.itcast.invoice.invoice.supplier.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.invoice.invoice.supplier.dao.dao.SupplierDao;
import cn.itcast.invoice.invoice.supplier.vo.SupplierModel;
import cn.itcast.invoice.invoice.supplier.vo.SupplierQueryModel;
import cn.itcast.invoice.util.base.BaseDaoImpl;
import cn.itcast.invoice.util.base.BaseQueryModel;

public class SupplierDaoImpl extends BaseDaoImpl<SupplierModel> implements SupplierDao{
	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		SupplierQueryModel sqm = (SupplierQueryModel) qm;
		
		if(sqm.getName()!=null && sqm.getName().trim().length()>0){
			dc.add(Restrictions.like("name", "%"+sqm.getName().trim()+"%"));
		}
		if(sqm.getContact()!=null && sqm.getContact().trim().length()>0){
			dc.add(Restrictions.like("contact", "%"+sqm.getContact().trim()+"%"));
		}
		if(sqm.getTele()!=null && sqm.getTele().trim().length()>0){
			dc.add(Restrictions.like("tele", "%"+sqm.getTele().trim()+"%"));
		}
		if(sqm.getNeeds()!=null && sqm.getNeeds()!= -1){
			dc.add(Restrictions.eq("needs", sqm.getNeeds()));
		}
	}

	public List<SupplierModel> getAllUnion() {
		//获取所有与商品类别具有关联数据的供应商信息
		String hql = "select distinct sm from SupplierModel sm join sm.gtms";
		return this.getHibernateTemplate().find(hql);
	}

	public List<SupplierModel> getAllUnionTwo() {
		String hql = "select distinct sm from SupplierModel sm join sm.gtms gtm join gtm.gms";
		return this.getHibernateTemplate().find(hql);
	}
}
