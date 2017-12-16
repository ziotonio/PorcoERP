package cn.itcast.invoice.invoice.goods.dao.dao;

import java.util.List;

import cn.itcast.invoice.invoice.goods.vo.GoodsModel;
import cn.itcast.invoice.util.base.BaseDao;

public interface GoodsDao extends BaseDao<GoodsModel> {

	public List<GoodsModel> getAllByGtmUuid(Long gtmUuid);

	public void updateUseNum();

	public List<Object[]> getStoreWarnInfo();
}
