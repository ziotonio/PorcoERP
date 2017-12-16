package cn.itcast.invoice.invoice.storedetail.dao.dao;

import cn.itcast.invoice.invoice.storedetail.vo.StoreDetailModel;
import cn.itcast.invoice.util.base.BaseDao;

public interface StoreDetailDao extends BaseDao<StoreDetailModel> {
	/**
	 * 根据商品uuid与仓库uuid获取商品在仓库中的存储数据
	 * @param storeUuid
	 * @param goodsUuid
	 * @return
	 */
	public StoreDetailModel getBySmAndGm(Long storeUuid, Long goodsUuid);
}
