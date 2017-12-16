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
	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		EmpQueryModel eqm = (EmpQueryModel) qm;
		if(eqm.getUserName()!=null && eqm.getUserName().trim().length()>0){
			dc.add(Restrictions.like("userName", "%"+eqm.getUserName().trim()+"%"));
		}
		if(eqm.getName()!=null && eqm.getName().trim().length()>0){
			dc.add(Restrictions.like("name", "%"+eqm.getName().trim()+"%"));
		}
		if(eqm.getTele()!=null && eqm.getTele().trim().length()>0){
			dc.add(Restrictions.like("tele", "%"+eqm.getTele().trim()+"%"));
		}
		if(eqm.getGender()!=null && eqm.getGender()!=-1){
			dc.add(Restrictions.eq("gender", eqm.getGender()));
		}
		if(eqm.getEmail()!=null && eqm.getEmail().trim().length()>0){
			dc.add(Restrictions.like("email", "%"+eqm.getEmail().trim()+"%"));
		}
		if(eqm.getLastLoginTime()!=null){
			dc.add(Restrictions.ge("lastLoginTime", eqm.getLastLoginTime()));
		}
		if(eqm.getLastLoginTime2()!=null){
			dc.add(Restrictions.le("lastLoginTime", eqm.getLastLoginTime2()+86400000L));
		}
		if(eqm.getDm()!=null && eqm.getDm().getUuid()!=null && eqm.getDm().getUuid()!=-1){
			//dc.add(Restrictions.eq("dm", eqm.getDm()));
			dc.createAlias("dm", "d");
			dc.add(Restrictions.eq("d.uuid", eqm.getDm().getUuid()));
			
			/*
			am.bm.cm.dm.uuid
			
			dc.createAlias("am", "a");
			dc.createAlias("a.bm", "b");
			dc.createAlias("b.cm", "c");
			dc.createAlias("c.dm", "d");
			dc.add(Restrictions.eq("d.uuid", eqm.getDm().getUuid()));
			
			dc.createAlias("am", "a");
			dc.createAlias("a.bm", "b");
			dc.createAlias("b.cm", "c");
			dc.add(Restrictions.eq("c.dm", eqm.getDm()));
			*/
		}
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
		0-8上班
		12月13 -12月13日
		12月14日0点到7:59:59
		*/
	}
	
	
	public EmpModel getByNameAndPwd(String userName, String pwd) {
		String hql = "from EmpModel where userName = ? and pwd = ?";
		List<EmpModel> temp = this.getHibernateTemplate().find(hql,userName,pwd);
		return temp.size()>0 ? temp.get(0):null;
	}

	public boolean updatePwdByUserNameAndPwd(String userName, String oldPwd, String newPwd) {
		//执行update操作 	update
		String hql = "update EmpModel set pwd = ? where userName = ? and pwd = ?";
		return this.getHibernateTemplate().bulkUpdate(hql,newPwd,userName,oldPwd)>0; 
	}

	public List<EmpModel> getAllByDepUuid(Long depUuid) {
		String hql = "from EmpModel where dm.uuid = ?";
		return this.getHibernateTemplate().find(hql,depUuid);
	}
}
