package cn.itcast.invoice.invoice.order.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import cn.itcast.invoice.invoice.order.dao.dao.OrderDao;
import cn.itcast.invoice.invoice.order.vo.OrderModel;
import cn.itcast.invoice.invoice.order.vo.OrderQueryModel;
import cn.itcast.invoice.util.base.BaseDaoImpl;
import cn.itcast.invoice.util.base.BaseQueryModel;

public class OrderDaoImpl extends BaseDaoImpl<OrderModel> implements OrderDao{
	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		OrderQueryModel oqm = (OrderQueryModel) qm;
		//TODO 添加自定义查询规则
		if(oqm.getCompleter()!=null && oqm.getCompleter().getUuid()!=null){
			dc.add(Restrictions.eq("completer", oqm.getCompleter()));
		}
	}
	
	public void doQbc2(DetachedCriteria dc,OrderQueryModel oqm,Integer[] types){
		dc.add(Restrictions.in("type", types));
		doQbc(dc,oqm);
	}
	
	//暂定
	public List<OrderModel> getAllByTypes(OrderQueryModel oqm, Integer pageNum,Integer pageCount, Integer[] types) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderModel.class);
		doQbc2(dc,oqm,types);
		return this.getHibernateTemplate().findByCriteria(dc,(pageNum-1)*pageCount,pageCount);
	}
	public Integer getCountByTypes(OrderQueryModel oqm, Integer[] types) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderModel.class);
		dc.setProjection(Projections.rowCount());
		doQbc2(dc,oqm,types);
		List<Long> count = this.getHibernateTemplate().findByCriteria(dc);
		return count.get(0).intValue();
	}
}
