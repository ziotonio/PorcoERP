package cn.itcast.invoice.invoice.supplier.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.invoice.invoice.supplier.vo.SupplierModel;
import cn.itcast.invoice.util.base.BaseEbi;

@Transactional
public interface SupplierEbi extends BaseEbi<SupplierModel> {

	public List<SupplierModel> getAllUnion();
	/**
	 * 获取具有商品的所有供应商信息
	 * @return
	 */
	public List<SupplierModel> getAllUnionTwo();
}
