package cn.itcast.invoice.auth.dep.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.invoice.auth.dep.dao.dao.DepDao;
import cn.itcast.invoice.auth.dep.vo.DepModel;
import cn.itcast.invoice.auth.dep.vo.DepQueryModel;
import cn.itcast.invoice.util.base.BaseDaoImpl;
import cn.itcast.invoice.util.base.BaseQueryModel;

public class DepDaoImpl extends BaseDaoImpl<DepModel> implements DepDao{
	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		DepQueryModel dqm = (DepQueryModel) qm;
		//TODO 添加自定义查询规则
		/*
		if(dqm.getName()!=null && dqm.getName().trim().length()>0){
			dc.add(Restrictions.like("name", "%"+dqm.getName().trim()+"%"));
		}
		if(dqm.getTele()!=null && dqm.getTele().trim().length()>0){
			dc.add(Restrictions.like("tele", "%"+dqm.getTele().trim()+"%"));
		}
		*/
	}
}

/*
public class DepDaoImpl extends HibernateDaoSupport implements DepDao{

	public void save(DepModel dm) {
		this.getHibernateTemplate().save(dm);
	}

	public List<DepModel> getAll() {
		String hql = "from DepModel";
		return this.getHibernateTemplate().find(hql);
	}

	public DepModel get(Long uuid) {
		return this.getHibernateTemplate().get(DepModel.class,uuid);
	}

	public void update(DepModel dm) {
		this.getHibernateTemplate().update(dm);
	}

	public void delete(DepModel dm) {
		this.getHibernateTemplate().delete(dm);
	}

	public List<DepModel> getAll(DepQueryModel dqm) {
		//按照条件进行查询
		//QBC
		DetachedCriteria dc = DetachedCriteria.forClass(DepModel.class);
		//添加条件
		if(dqm.getName()!=null && dqm.getName().trim().length()>0){
			dc.add(Restrictions.like("name", "%"+dqm.getName().trim()+"%"));
		}
		if(dqm.getTele()!=null && dqm.getTele().trim().length()>0){
			dc.add(Restrictions.like("tele", "%"+dqm.getTele().trim()+"%"));
		}
		return this.getHibernateTemplate().findByCriteria(dc);
	}
	public List<DepModel> getAll(DepQueryModel dqm, Integer pageNum,Integer pageCount) {
		DetachedCriteria dc = DetachedCriteria.forClass(DepModel.class);
		//添加条件
		if(dqm.getName()!=null && dqm.getName().trim().length()>0){
			dc.add(Restrictions.like("name", "%"+dqm.getName().trim()+"%"));
		}
		if(dqm.getTele()!=null && dqm.getTele().trim().length()>0){
			dc.add(Restrictions.like("tele", "%"+dqm.getTele().trim()+"%"));
		}
		return this.getHibernateTemplate().findByCriteria(dc,(pageNum-1)*pageCount,pageCount);
	}
	
	public Integer getCount(DepQueryModel dqm) {
		DetachedCriteria dc = DetachedCriteria.forClass(DepModel.class);
		//select * form tbl_dep
		//select count(uuid) from tbl_dep
		//设置查询投影
		dc.setProjection(Projections.rowCount());
		if(dqm.getName()!=null && dqm.getName().trim().length()>0){
			dc.add(Restrictions.like("name", "%"+dqm.getName().trim()+"%"));
		}
		if(dqm.getTele()!=null && dqm.getTele().trim().length()>0){
			dc.add(Restrictions.like("tele", "%"+dqm.getTele().trim()+"%"));
		}
		List<Long> count = this.getHibernateTemplate().findByCriteria(dc);
		return count.get(0).intValue();
	}
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml","applicationContext-dep.xml");
		DepDao dao = (DepDao) ctx.getBean("depDao");
		System.out.println(dao.getCount(new DepQueryModel()));
	}
	public Integer getCount(DepQueryModel dqm) {
		String hql = "select count(uuid) from DepModel where 1 = 1 ";
		if(dqm.getName()!=null && dqm.getName().trim().length()>0){
			hql += " and name like ? ";
		}
		if(dqm.getTele()!=null && dqm.getTele().trim().length()>0){
			hql += " and tele like ? ";
		}
		List<Long> count = this.getHibernateTemplate().find(hql,"%"+dqm.getName()+"%","%"+dqm.getTele()+"%");
		return count.get(0).intValue();
	}
}
*/