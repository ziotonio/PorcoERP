package cn.itcast.invoice.invoice.bill.dao.dao;

import java.util.List;

import cn.itcast.invoice.invoice.bill.vo.BillQueryModel;
import cn.itcast.invoice.invoice.order.vo.OrderDetailModel;


public interface BillDao  {

	public List<Object[]> getBillByGoods(BillQueryModel bqm);

	public List<OrderDetailModel> getBillDetailByGoods(BillQueryModel bqm);
}
