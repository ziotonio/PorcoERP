package cn.itcast.invoice.invoice.goods.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.invoice.invoice.goods.dao.dao.GoodsDao;
import cn.itcast.invoice.invoice.goods.vo.GoodsModel;
import cn.itcast.invoice.invoice.goods.vo.GoodsQueryModel;
import cn.itcast.invoice.invoice.storedetail.vo.StoreDetailModel;
import cn.itcast.invoice.util.base.BaseDaoImpl;
import cn.itcast.invoice.util.base.BaseQueryModel;

public class GoodsDaoImpl extends BaseDaoImpl<GoodsModel> implements GoodsDao{
	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		GoodsQueryModel gqm = (GoodsQueryModel) qm;
		
		if(gqm.getGtm()!=null && gqm.getGtm().getSm()!=null && gqm.getGtm().getSm().getUuid()!=null && gqm.getGtm().getSm().getUuid()!=-1){
			/*
			dc.createAlias("gtm", "gt");
			dc.createAlias("gt.sm", "s");
			dc.add(Restrictions.eq("s.uuid", gqm.getGtm().getSm().getUuid()));
			*/
			dc.createAlias("gtm", "gt");
			dc.add(Restrictions.eq("gt.sm", gqm.getGtm().getSm()));
		}
		
		if(gqm.getName()!=null && gqm.getName().trim().length()>0){
			dc.add(Restrictions.like("name", "%"+gqm.getName().trim()+"%"));
		}
		if(gqm.getProducer()!=null && gqm.getProducer().trim().length()>0){
			dc.add(Restrictions.like("producer", "%"+gqm.getProducer().trim()+"%"));
		}
		if(gqm.getUnit()!=null && gqm.getUnit().trim().length()>0){
			dc.add(Restrictions.eq("unit", gqm.getUnit().trim()));
		}
		if(gqm.getInPrice()!=null && gqm.getInPrice()>0){
			dc.add(Restrictions.ge("inPrice", gqm.getInPrice()));
		}
		if(gqm.getInPrice2()!=null && gqm.getInPrice2()>0){
			dc.add(Restrictions.le("inPrice", gqm.getInPrice2()));
		}
		if(gqm.getOutPrice()!=null && gqm.getOutPrice()>0){
			dc.add(Restrictions.ge("outPrice", gqm.getOutPrice()));
		}
		if(gqm.getOutPrice2()!=null && gqm.getOutPrice2()>0){
			dc.add(Restrictions.le("outPrice", gqm.getOutPrice2()));
		}
	}

	public List<GoodsModel> getAllByGtmUuid(Long gtmUuid) {
		String hql = "from GoodsModel where gtm.uuid = ?";
		return this.getHibernateTemplate().find(hql,gtmUuid);		
	}

	public void updateUseNum() {
		//String sql = "update tbl_goods g set useNum = ( select count(goodsUuid) from tbl_orderdetail where goodsUuid = g.uuid )";
		String hql = "update GoodsModel g set g.useNum = ( select count(odm.gm.uuid) from OrderDetailModel odm where odm.gm.uuid = g.uuid )";
		this.getHibernateTemplate().bulkUpdate(hql);
	}

	public List<Object[]> getStoreWarnInfo() {
		/*
		String hql = "select gm.name,sum(sdm.num)>gm.maxNum,sum(sdm.num)<gm.minNum from StoreDetailModel sdm,GoodsModel gm where gm.uuid = sdm.gm.uuid group by sdm.gm ";
		List<Object> temp = this.getHibernateTemplate().find(hql);
		System.out.println(temp.size());
		System.out.println(temp.get(0));
		 */
		
		String sql = "select gm.name,sum(sdm.num)>gm.maxNum,sum(sdm.num)<gm.minNum from tbl_storedetail sdm,tbl_goods gm where gm.uuid = sdm.goodsUuid group by goodsUuid ";
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		SQLQuery sq = s.createSQLQuery(sql);
		return sq.list();
	}
}




