package cn.itcast.invoice.invoice.goodstype.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.invoice.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.invoice.util.base.BaseEbi;

@Transactional
public interface GoodsTypeEbi extends BaseEbi<GoodsTypeModel> {
	/**
	 * 获取指定供应商的产品类别信息
	 * @param supplierUuid 供应商uuid
	 * @return
	 */
	public List<GoodsTypeModel> getAllBySupplier(Long supplierUuid);
	/**
	 * 获取指定供应商具有商品信息的商品类别信息集合
	 * @param uuid	供应商uuid
	 * @return
	 */
	public List<GoodsTypeModel> getAllUnionBySupplier(Long uuid);
}
