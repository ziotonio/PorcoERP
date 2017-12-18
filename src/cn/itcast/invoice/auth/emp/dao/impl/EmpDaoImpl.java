package cn.itcast.invoice.auth.emp.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.hql.ast.tree.RestrictableStatement;

import cn.itcast.invoice.auth.emp.dao.dao.EmpDao;
import cn.itcast.invoice.auth.emp.vo.EmpModel;
import cn.itcast.invoice.auth.emp.vo.EmpQueryModel;
import cn.itcast.invoice.util.base.BaseDaoImpl;
import cn.itcast.invoice.util.base.BaseQueryModel;

public class EmpDaoImpl extends BaseDaoImpl<EmpModel> implements EmpDao{
	
	public void addUserName(DetachedCriteria dc,EmpQueryModel eqm) {
		if(eqm.getUserName()!=null && eqm.getUserName().trim().length()>0){
			dc.add(Restrictions.like("userName", "%"+eqm.getUserName().trim()+"%"));
		}
	}
	
	public void addName(DetachedCriteria dc,EmpQueryModel eqm) {
		if(eqm.getName()!=null && eqm.getName().trim().length()>0){
			dc.add(Restrictions.like("name", "%"+eqm.getName().trim()+"%"));
		}
	}
	
	public void addTele(DetachedCriteria dc,EmpQueryModel eqm) {
		if(eqm.getTele()!=null && eqm.getTele().trim().length()>0){
			dc.add(Restrictions.like("tele", "%"+eqm.getTele().trim()+"%"));
		}
	}
	
	public void addGender(DetachedCriteria dc,EmpQueryModel eqm) {
		if(eqm.getGender()!=null && eqm.getGender()!=-1){
			dc.add(Restrictions.eq("gender", eqm.getGender()));
		}
	}
	
	public void addEmail(DetachedCriteria dc,EmpQueryModel eqm) {
		if(eqm.getEmail()!=null && eqm.getEmail().trim().length()>0){
			dc.add(Restrictions.like("email", "%"+eqm.getEmail().trim()+"%"));
		}
	}
	
	public void addLastLoginTime(DetachedCriteria dc,EmpQueryModel eqm) {
		if(eqm.getLastLoginTime()!=null){
			dc.add(Restrictions.ge("lastLoginTime", eqm.getLastLoginTime()));
		}
	}
	
	public void addLastLoginTime2(DetachedCriteria dc,EmpQueryModel eqm) {
		if(eqm.getLastLoginTime2()!=null){
			dc.add(Restrictions.le("lastLoginTime", eqm.getLastLoginTime2()+86400000L));
		}
	}
	
	public void addDm(DetachedCriteria dc,EmpQueryModel eqm) {
		if(eqm.getDm()!=null && eqm.getDm().getUuid()!=null && eqm.getDm().getUuid()!=-1){
			dc.createAlias("dm", "d");
			dc.add(Restrictions.eq("d.uuid", eqm.getDm().getUuid()));
		}
	}	
	
	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		EmpQueryModel eqm = (EmpQueryModel) qm;
		
		addUserName(dc,eqm);
		addName(dc,eqm);
		addTele(dc,eqm);
		addEmail(dc,eqm);
		addLastLoginTime(dc,eqm);
		addLastLoginTime2(dc,eqm);
		addDm(dc,eqm);
	
	}
	
	public static void main(String[] args) {
		//System.out.println(new Date().getTime());		//1406275328183
		//System.out.println(1406275328183L/86400000*86400000);	//1406246400000
		//System.out.println(new Date(1406246400000L));
		/*
		System.out.println(new Date(0L));
		1406246400000 
		1406246400000+86400000
		8:00:00 - 7:59:59
		0-8ä¸Šç�­
		12æœˆ13 -12æœˆ13æ—¥
		12æœˆ14æ—¥0ç‚¹åˆ°7:59:59
		*/
	}
	
	
	public EmpModel getByNameAndPwd(String userName, String pwd) {
		String hql = "from EmpModel where userName = ? and pwd = ?";
		List<EmpModel> temp = this.getHibernateTemplate().find(hql,userName,pwd);
		return temp.size()>0 ? temp.get(0):null;
	}

	public boolean updatePwdByUserNameAndPwd(String userName, String oldPwd, String newPwd) {
		//æ‰§è¡Œupdateæ“�ä½œ 	update
		String hql = "update EmpModel set pwd = ? where userName = ? and pwd = ?";
		return this.getHibernateTemplate().bulkUpdate(hql,newPwd,userName,oldPwd)>0; 
	}

	public List<EmpModel> getAllByDepUuid(Long depUuid) {
		String hql = "from EmpModel where dm.uuid = ?";
		return this.getHibernateTemplate().find(hql,depUuid);
	}
}
