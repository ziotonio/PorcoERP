package cn.itcast.invoice.invoice.goodstype.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.invoice.invoice.goodstype.dao.dao.GoodsTypeDao;
import cn.itcast.invoice.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.invoice.invoice.goodstype.vo.GoodsTypeQueryModel;
import cn.itcast.invoice.util.base.BaseDaoImpl;
import cn.itcast.invoice.util.base.BaseQueryModel;

public class GoodsTypeDaoImpl extends BaseDaoImpl<GoodsTypeModel> implements GoodsTypeDao{
	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		GoodsTypeQueryModel gqm = (GoodsTypeQueryModel) qm;
		//TODO 添加自定义查询规则
	}

	public List<GoodsTypeModel> getAllBySupUuid(Long supplierUuid) {
		String hql = "from GoodsTypeModel where sm.uuid = ?";
		return this.getHibernateTemplate().find(hql,supplierUuid);
	}

	public List<GoodsTypeModel> getAllUnionBySupplier(Long uuid) {
		String hql = "select distinct gtm from GoodsTypeModel gtm join gtm.gms where gtm.sm.uuid = ?";
		return this.getHibernateTemplate().find(hql,uuid);
	}
}
