package cn.itcast.invoice.invoice.goodstype.dao.dao;

import java.util.List;

import cn.itcast.invoice.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.invoice.util.base.BaseDao;

public interface GoodsTypeDao extends BaseDao<GoodsTypeModel> {

	public List<GoodsTypeModel> getAllBySupUuid(Long supplierUuid);

	public List<GoodsTypeModel> getAllUnionBySupplier(Long uuid);
}
