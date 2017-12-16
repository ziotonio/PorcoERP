package cn.itcast.invoice.util.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.invoice.util.exception.AppException;

public abstract class BaseDaoImpl<T> extends HibernateDaoSupport{
	//当前类中泛型的类型
	private Class<T> entityClass;
	
	//何时初始化:当前类的对象创建完毕之前
	public BaseDaoImpl(){
		Class clazz = this.getClass();
		Type type = clazz.getGenericSuperclass();
		ParameterizedType pType =(ParameterizedType)type;
		Type[] types = pType.getActualTypeArguments(); 
		entityClass = (Class) types[0];
	}
	
	public void save(T t) {
		this.getHibernateTemplate().save(t);
		/*try {
		} catch (Exception e) {
			throw new AppException("对不起，数据库服务器没有启动，请联系管理员！",e);
		}*/
	}

	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}

	public T get(Serializable uuid) {
		return this.getHibernateTemplate().get(entityClass,uuid);
	}
	
	public List<T> getAll() {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		return this.getHibernateTemplate().findByCriteria(dc);
	}

	public List<T> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		doQbc(dc,qm);
		return this.getHibernateTemplate().findByCriteria(dc,(pageNum-1)*pageCount,pageCount);
	}
	
	public Integer getCount(BaseQueryModel qm) {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		dc.setProjection(Projections.rowCount());
		doQbc(dc,qm);
		List<Long> count = this.getHibernateTemplate().findByCriteria(dc);
		return count.get(0).intValue();
	}
	
	//强制子类覆盖doQbc方法
	protected abstract void doQbc(DetachedCriteria dc,BaseQueryModel qm);
}
/*
public static void main(String[] args) {
	ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml","applicationContext-dep.xml");
	DepDao dao = (DepDao) ctx.getBean("depDao");
	System.out.println(dao.getCount(new DepQueryModel()));
}
*/
/*
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
*/