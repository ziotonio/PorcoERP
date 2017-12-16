package cn.itcast.invoice.invoice.store.business.ebi;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.invoice.auth.emp.vo.EmpModel;
import cn.itcast.invoice.invoice.order.vo.OrderDetailModel;
import cn.itcast.invoice.invoice.store.vo.StoreModel;
import cn.itcast.invoice.util.base.BaseEbi;

@Transactional
public interface StoreEbi extends BaseEbi<StoreModel> {
	/**
	 * 入库
	 * @param odmUuid	订单明细编号 
	 * @param goodsUuid 商品编号
	 * @param storeUuid 仓库编号
	 * @param num 操作数量
	 * @param login
	 * 
	 */
	public OrderDetailModel inGoods(Long odmUuid ,Long goodsUuid, Long storeUuid, Integer num, EmpModel login);
}
