package cn.itcast.invoice.invoice.bill.vo;

import cn.itcast.invoice.util.format.FormatUtil;


public class BillQueryModel{
	private Integer type;
	private Long supplierUuid;
	private Long begin;
	private Long end;
	private Long goodsUuid;
	
	private String beginView;
	private String endView;
	
	public String getBeginView() {
		return beginView;
	}
	public String getEndView() {
		return endView;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getSupplierUuid() {
		return supplierUuid;
	}
	public void setSupplierUuid(Long supplierUuid) {
		this.supplierUuid = supplierUuid;
	}
	public Long getGoodsUuid() {
		return goodsUuid;
	}
	public void setGoodsUuid(Long goodsUuid) {
		this.goodsUuid = goodsUuid;
	}
	public Long getBegin() {
		return begin;
	}
	public void setBegin(Long begin) {
		this.begin = begin;
		this.beginView = FormatUtil.formatDate(begin);
	}
	public Long getEnd() {
		return end;
	}
	public void setEnd(Long end) {
		this.end = end;
		this.endView = FormatUtil.formatDate(end);
	}
	
}
