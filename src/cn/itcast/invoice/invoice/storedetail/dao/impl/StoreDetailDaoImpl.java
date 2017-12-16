package cn.itcast.invoice.invoice.storedetail.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.invoice.invoice.storedetail.dao.dao.StoreDetailDao;
import cn.itcast.invoice.invoice.storedetail.vo.StoreDetailModel;
import cn.itcast.invoice.invoice.storedetail.vo.StoreDetailQueryModel;
import cn.itcast.invoice.util.base.BaseDaoImpl;
import cn.itcast.invoice.util.base.BaseQueryModel;

public class StoreDetailDaoImpl extends BaseDaoImpl<StoreDetailModel> implements StoreDetailDao{
	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		StoreDetailQueryModel sqm = (StoreDetailQueryModel) qm;
		//TODO 添加自定义查询规则
	}

	public StoreDetailModel getBySmAndGm(Long storeUuid, Long goodsUuid) {
		String hql = "from StoreDetailModel where sm.uuid = ? and gm.uuid = ?";
		List<StoreDetailModel> temp = this.getHibernateTemplate().find(hql,storeUuid,goodsUuid);
		return temp.size()>0 ? temp.get(0) : null;
	}
}
