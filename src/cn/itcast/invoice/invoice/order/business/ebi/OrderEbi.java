package cn.itcast.invoice.invoice.order.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.invoice.auth.emp.vo.EmpModel;
import cn.itcast.invoice.invoice.order.vo.OrderModel;
import cn.itcast.invoice.invoice.order.vo.OrderQueryModel;
import cn.itcast.invoice.util.base.BaseEbi;

@Transactional
public interface OrderEbi extends BaseEbi<OrderModel> {
	/**
	 * 保存采购订单
	 * @param empModel 当前登陆人(制单人)
	 * @param om 订单
	 * @param goodsUuids 订单明细货物uuid
	 * @param nums 订单明细数量
	 * @param prices 订单明细价格
	 */
	public void save(EmpModel empModel, OrderModel om, Long[] goodsUuids, Integer[] nums, Double[] prices);
	/**
	 * 获取所有未审核的采购相关的订单
	 * @param pageCount 
	 * @param pageNum 
	 * @param oqm 
	 * @return
	 */
	public List<OrderModel> getAllNoCheckOrder(OrderQueryModel oqm, Integer pageNum, Integer pageCount);
	public Integer getCountByTypes(OrderQueryModel oqm);
	/**
	 * 采购订单审核通过
	 * @param uuid 待审核订单编号
	 * @param em 审核人
	 */
	public void buyCheckPass(Long uuid, EmpModel em);
	/**
	 * 采购订单审核驳回
	 * @param uuid 待审核订单编号
	 * @param em 审核人
	 */
	public void buyCheckNoPass(Long uuid, EmpModel em);
	/**
	 * 获取所有未指派任务人的订单任务
	 * @param pageCount 
	 * @param pageNum 
	 * @param oqm 
	 * @return
	 */
	public List<OrderModel> getAllTasks(OrderQueryModel oqm, Integer pageNum, Integer pageCount);
	/**
	 * 指派任务
	 * @param om
	 */
	public void assignTask(OrderModel om);
	public List<OrderModel> getAllByCompleter(OrderQueryModel oqm,
			Integer pageNum, Integer pageCount, EmpModel login);
	public void endTask(Long uuid);
	/**
	 * 获取所有未完全入库的订单信息
	 * @param oqm
	 * @param pageNum
	 * @param pageCount
	 * @return
	 */
	public List<OrderModel> getAllNotIn(OrderQueryModel oqm, Integer pageNum,
			Integer pageCount);
}
