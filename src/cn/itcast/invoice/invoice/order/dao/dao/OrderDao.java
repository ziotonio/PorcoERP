package cn.itcast.invoice.invoice.order.dao.dao;

import java.util.List;

import cn.itcast.invoice.invoice.order.vo.OrderModel;
import cn.itcast.invoice.invoice.order.vo.OrderQueryModel;
import cn.itcast.invoice.util.base.BaseDao;

public interface OrderDao extends BaseDao<OrderModel> {

	public List<OrderModel> getAllByTypes(OrderQueryModel oqm, Integer pageNum,	Integer pageCount, Integer[] types);

	public Integer getCountByTypes(OrderQueryModel oqm, Integer[] types);
}
