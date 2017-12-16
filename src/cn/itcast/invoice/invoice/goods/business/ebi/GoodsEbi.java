package cn.itcast.invoice.invoice.goods.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.invoice.invoice.goods.vo.GoodsModel;
import cn.itcast.invoice.util.base.BaseEbi;

@Transactional
public interface GoodsEbi extends BaseEbi<GoodsModel> {
	/**
	 * 获取指定商品类别的所有商品信息
	 * @param gtmUuid 商品类别uuid
	 * @return
	 */
	public List<GoodsModel> getAllByGtmUuid(Long gtmUuid);
}
