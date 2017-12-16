package cn.itcast.invoice.invoice.goodstype.web;

import java.util.List;

import cn.itcast.invoice.invoice.goodstype.business.ebi.GoodsTypeEbi;
import cn.itcast.invoice.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.invoice.invoice.goodstype.vo.GoodsTypeQueryModel;
import cn.itcast.invoice.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.invoice.invoice.supplier.vo.SupplierModel;
import cn.itcast.invoice.util.base.BaseAction;

public class GoodsTypeAction extends BaseAction{
	public GoodsTypeModel gm = new GoodsTypeModel();
	public GoodsTypeQueryModel gqm = new GoodsTypeQueryModel();

	private GoodsTypeEbi goodsTypeEbi;
	private SupplierEbi supplierEbi;
	
	public void setSupplierEbi(SupplierEbi supplierEbi) {
		this.supplierEbi = supplierEbi;
	}

	public void setGoodsTypeEbi(GoodsTypeEbi goodsTypeEbi) {
		this.goodsTypeEbi = goodsTypeEbi;
	}

	//跳转到列表页面
	public String list(){
		setDataTotal(goodsTypeEbi.getCount(gqm));
		List<GoodsTypeModel> goodsTypeList = goodsTypeEbi.getAll(gqm,pageNum,pageCount);
		put("goodsTypeList",goodsTypeList);
		return LIST;
	}

	//保存/修改
	public String save(){
		if(gm.getUuid()== null){
			goodsTypeEbi.save(gm);
		}else{
			goodsTypeEbi.update(gm);
		}
		return TO_LIST;
	}

	//跳转到添加/修改页面
	public String input(){
		//加载供应商信息列表
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList",supplierList);
		if(gm.getUuid()!=null){
			gm = goodsTypeEbi.get(gm.getUuid());
		}
		return INPUT;
	}

	//删除
	public String delete(){
		goodsTypeEbi.delete(gm);
		return TO_LIST;
	}
	
	//--ajax---------------------------------------------
	public Long supplierUuid;
	
	private List<GoodsTypeModel> gtmList;
	public List<GoodsTypeModel> getGtmList(){
		return gtmList;
	}
	
	public String ajaxGetGtmBySupplier(){
		//根据供应商的uuid获取对应的所有商品类别信息
		gtmList = goodsTypeEbi.getAllBySupplier(supplierUuid);
		//将数据传递到页面,json格式
		//如何将数据转换为json格式？（使用struts2-json-plugin-2.3.7.jar完成)
		//将对应Action类中所有get开头的方法对应的数据，转换为json格式，json属性名为get方法的名称(不包含get)
		return "ajaxGetGtmBySupplier";
	}
	/*
	//{"abc":"heihei"}
	public String getAbc(){
		return "heihei";
	}
	*/
}
