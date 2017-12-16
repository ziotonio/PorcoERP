package cn.itcast.invoice.invoice.bill.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.itcast.invoice.invoice.bill.business.ebi.BillEbi;
import cn.itcast.invoice.invoice.bill.vo.BillQueryModel;
import cn.itcast.invoice.invoice.order.vo.OrderDetailModel;
import cn.itcast.invoice.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.invoice.invoice.supplier.vo.SupplierModel;
import cn.itcast.invoice.util.base.BaseAction;
import cn.itcast.invoice.util.format.FormatUtil;

public class BillAction extends BaseAction{
	public BillQueryModel bqm = new BillQueryModel();

	private BillEbi billEbi;
	private SupplierEbi supplierEbi;
	
	public void setSupplierEbi(SupplierEbi supplierEbi) {
		this.supplierEbi = supplierEbi;
	}

	public void setBillEbi(BillEbi billEbi) {
		this.billEbi = billEbi;
	}

	public String buyBill(){
		/*
		select 
			g.uuid,		
			g.name,			
			sum(od.num)
		from 
			tbl_orderdetail od,
			tbl_goods g
		where
			od.goodsUuid = g.uuid
		group by
			od.goodsUuid 
		*/
		//获取采购报表数据
		List<Object[]> billList = billEbi.getBillByGoods(bqm);
		/*
		for(Object[] objs:billList){
			GoodsModel temp = (GoodsModel) objs[0];
			Long sum = (Long) objs[1];
			System.out.println(temp.getName()+","+sum);
			System.out.println("------------------");
		}
		*/
		put("billList",billList);
		//加载所有供应商数据
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList",supplierList);
		return "buyBill";
	}
	private List<OrderDetailModel> odmList;
	public List<OrderDetailModel> getOdmList() {
		return odmList;
	}
	public String ajaxBillDetailByGoods(){
		odmList = billEbi.getBillDetailByGoods(bqm);
		return "ajaxBillDetailByGoods";
	}
	//获取饼图
	public void billForPie() throws IOException{
		//1.获取jfreechart对象，将其装入流对象
		//2.传递到后台一个流对象，在后台完成jfreechart对象转入流的操作
		//准备数据bqm->list
		List<Object[]> billList = billEbi.getBillByGoods(bqm);
		//准备一个流对象
		OutputStream os = getResponse().getOutputStream();
		//传递到后台，将jfreechart转换到流中
		billEbi.getBillForPie(os,billList);
		//刷新流，将图像送回页面
		os.flush();
	}
	//下载Excel报表
	private InputStream downloadExcel;
	public InputStream getDownloadExcel() {
		return downloadExcel;
	}
	private String xlsName;
	public String getXlsName() throws UnsupportedEncodingException {
		System.out.println(xlsName);
		return new String(xlsName.getBytes("UTF-8"),"ISO8859-1");
	}

	public String downloadExcelBill() throws Exception{
		xlsName = "货物统计报表［"+FormatUtil.formatDate(System.currentTimeMillis())+"］.xls";
		List<Object[]> billList = billEbi.getBillByGoods(bqm);
		downloadExcel = billEbi.getExcelBill(billList);
		return "downloadExcelBill";
	}
}
