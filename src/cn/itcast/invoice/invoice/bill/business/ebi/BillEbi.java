package cn.itcast.invoice.invoice.bill.business.ebi;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.invoice.invoice.bill.vo.BillQueryModel;
import cn.itcast.invoice.invoice.order.vo.OrderDetailModel;
import cn.itcast.invoice.util.base.BaseEbi;

@Transactional
public interface BillEbi {

	public List<Object[]> getBillByGoods(BillQueryModel bqm);

	public List<OrderDetailModel> getBillDetailByGoods(BillQueryModel bqm);

	public void getBillForPie(OutputStream os, List<Object[]> billList);

	public InputStream getExcelBill(List<Object[]> billList) throws Exception;
}
