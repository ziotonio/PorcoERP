package cn.itcast.invoice.auth.res.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.invoice.auth.res.dao.dao.ResDao;
import cn.itcast.invoice.auth.res.vo.ResModel;
import cn.itcast.invoice.auth.res.vo.ResQueryModel;
import cn.itcast.invoice.util.base.BaseDaoImpl;
import cn.itcast.invoice.util.base.BaseQueryModel;

public class ResDaoImpl extends BaseDaoImpl<ResModel> implements ResDao{
	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		ResQueryModel rqm = (ResQueryModel) qm;
		//TODO 添加自定义查询规则
	}

	public List<String> getAllResByEmp(Long empUuid) {
		//from tbl_res where .....
		//from ResModel
		//查询两个表，要求关联的数据才出现，不关联的数据不出现(6选1)
		//内连：
		//外连：
		
		//from ResModel res join res.roles  
		//from RoleModel role join role.emps
		//from EmpModel em join em.roles role join role.reses res
		//from ResModel res join res.roles role join role.emps emp
		String hql = " select distinct res.url from ResModel res join res.roles role join role.emps em where em.uuid = ?";
		return this.getHibernateTemplate().find(hql,empUuid);
	}
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml","applicationContext-res.xml");
		ResDao dao = (ResDao) ctx.getBean("resDao");
		System.out.println(dao.getAllResByEmp(1L));
	}

	public List<String> getUrls() {
		String hql = "select url from ResModel";
		return this.getHibernateTemplate().find(hql);
	}
}
