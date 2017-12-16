package cn.itcast.invoice.invoice.supplier.dao.dao;

import java.util.List;

import cn.itcast.invoice.invoice.supplier.vo.SupplierModel;
import cn.itcast.invoice.util.base.BaseDao;

public interface SupplierDao extends BaseDao<SupplierModel> {

	public List<SupplierModel> getAllUnion();

	public List<SupplierModel> getAllUnionTwo();
}
