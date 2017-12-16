package cn.itcast.invoice.invoice.order.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.itcast.invoice.auth.emp.business.ebi.EmpEbi;
import cn.itcast.invoice.auth.emp.vo.EmpModel;
import cn.itcast.invoice.invoice.goods.business.ebi.GoodsEbi;
import cn.itcast.invoice.invoice.goods.vo.GoodsModel;
import cn.itcast.invoice.invoice.goodstype.business.ebi.GoodsTypeEbi;
import cn.itcast.invoice.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.invoice.invoice.order.business.ebi.OrderDetailEbi;
import cn.itcast.invoice.invoice.order.business.ebi.OrderEbi;
import cn.itcast.invoice.invoice.order.vo.OrderDetailModel;
import cn.itcast.invoice.invoice.order.vo.OrderModel;
import cn.itcast.invoice.invoice.order.vo.OrderQueryModel;
import cn.itcast.invoice.invoice.store.business.ebi.StoreEbi;
import cn.itcast.invoice.invoice.store.vo.StoreModel;
import cn.itcast.invoice.invoice.supplier.business.ebi.SupplierEbi;
import cn.itcast.invoice.invoice.supplier.vo.SupplierModel;
import cn.itcast.invoice.util.base.BaseAction;

public class OrderAction extends BaseAction{
	public OrderModel om = new OrderModel();
	public OrderQueryModel oqm = new OrderQueryModel();

	private OrderEbi orderEbi;
	private SupplierEbi supplierEbi;
	private GoodsTypeEbi goodsTypeEbi;
	private GoodsEbi goodsEbi;
	private EmpEbi empEbi;
	private StoreEbi storeEbi;
	private OrderDetailEbi orderDetailEbi;
	
	public void setOrderDetailEbi(OrderDetailEbi orderDetailEbi) {
		this.orderDetailEbi = orderDetailEbi;
	}

	public void setStoreEbi(StoreEbi storeEbi) {
		this.storeEbi = storeEbi;
	}

	public void setEmpEbi(EmpEbi empEbi) {
		this.empEbi = empEbi;
	}

	public void setGoodsEbi(GoodsEbi goodsEbi) {
		this.goodsEbi = goodsEbi;
	}

	public void setGoodsTypeEbi(GoodsTypeEbi goodsTypeEbi) {
		this.goodsTypeEbi = goodsTypeEbi;
	}

	public void setSupplierEbi(SupplierEbi supplierEbi) {
		this.supplierEbi = supplierEbi;
	}

	public void setOrderEbi(OrderEbi orderEbi) {
		this.orderEbi = orderEbi;
	}

	//跳转到列表页面
	public String list(){
		setDataTotal(orderEbi.getCount(oqm));
		List<OrderModel> orderList = orderEbi.getAll(oqm,pageNum,pageCount);
		put("orderList",orderList);
		return LIST;
	}

	//保存/修改
	public String save(){
		if(om.getUuid()== null){
			orderEbi.save(om);
		}else{
			orderEbi.update(om);
		}
		return TO_LIST;
	}

	//跳转到添加/修改页面
	public String input(){
		if(om.getUuid()!=null){
			om = orderEbi.get(om.getUuid());
		}
		return INPUT;
	}
	//跳转到采购订单页
	
	public String buyInput(){
		List<SupplierModel> supplierList = supplierEbi.getAll();
		//变成过滤该数据
		//有类别但是类别中没有商品的类别过滤掉
		//没有类别的供应商过滤掉
		//循环所有的供应商，判定该供应商是否保留，如果不保留删除
		/*
		for(int i = supplierList.size()-1;i>=0;i--){
			SupplierModel sm = supplierList.get(i);
			List<GoodsTypeModel> gtms = new ArrayList(sm.getGtms());
			//循环商品类别中的数据
			for(int j = gtms.size()-1;j>=0;j--){
				GoodsTypeModel gtm = gtms.get(j);
				if(gtm.getGms().size() == 0){
					gtms.remove(j);
				}
			}
			if(gtms.size() == 0){
				supplierList.remove(i);
			}
		}
		*/
		A:
		for(int i = supplierList.size()-1;i>=0;i--){
			SupplierModel sm = supplierList.get(i);
			//List<GoodsTypeModel> gtms = new ArrayList(sm.getGtms());
			//性能优于上面的方案
			List<GoodsTypeModel> gtms = goodsTypeEbi.getAllBySupplier(sm.getUuid());
			//循环商品类别中的数据
			for(int j = gtms.size()-1;j>=0;j--){
				GoodsTypeModel gtm = gtms.get(j);
				if(gtm.getGms().size() > 0){
					continue A;
				}
			}
			supplierList.remove(i);
		}
		List<GoodsTypeModel> gtmList = goodsTypeEbi.getAllUnionBySupplier(supplierList.get(0).getUuid());
		List<GoodsModel> gmList = goodsEbi.getAllByGtmUuid(gtmList.get(0).getUuid());
		put("supplierList",supplierList);
		put("gtmList",gtmList);
		put("gmList",gmList);
		return "buyInput";
	}
	
	/*
	public static void main(String[] args) {
		List<String> al = new ArrayList<String>();
		
		al.add("aa1");
		al.add("bb1");
		al.add("cc1");
		al.add("dd");
		
		for(int i = al.size()-1;i>=0;i--){
			String s = al.get(i);
			if(s.contains("1")){
				al.remove(i);
			}
		}
		
		for(String s:al){
			System.out.println(s);
		}
	}
	*/
	
	
	/*
	public String buyInput(){
		//加载供应商数据
		//问题：个别没有商品类别的供应商不应该被加载
		//解决方案一：
		//读取所有数据后，通过迭代集合将所有没有类别的供应商删除掉
		//解决方案二：
		//查询数据时，直接将没有商品类别的供应商过滤掉
		//查供应商  供应商关联类别
		//问题分析：某个供应商具有多个商品类别，但是类别中没有商品？
		//查供应商  供应商关联类别，类别关联商品 distinct
		List<SupplierModel> supplierList = supplierEbi.getAllUnionTwo();
		//加载第一个供应商的类别数据
		/*
		1号		A	B	C
				a1	b1	
				a2	b2
				a3
		*/
	/*
		List<GoodsTypeModel> gtmList = goodsTypeEbi.getAllUnionBySupplier(supplierList.get(0).getUuid());
		//加载第一个类别的商品数据
		List<GoodsModel> gmList = goodsEbi.getAllByGtmUuid(gtmList.get(0).getUuid());
		//加载第一个商品的价格数据(省略)
		//GoodsModel gm = gmList.get(0);
		//put("gm",gm);
		/*
		put("supplierList",supplierList);
		put("gtmList",gtmList);
		put("gmList",gmList);
		
		return "buyInput";
	}*/

	//删除
	public String delete(){
		orderEbi.delete(om);
		return TO_LIST;
	}
	
	//--ajax----------------------------
	public Long supplierUuid;
	public Long goodsTypeUuid;
	public Long goodsUuid;
	public String used;
	
	private List<GoodsTypeModel> gtmList;
	private List<GoodsModel> gmList;
	private GoodsModel gm;
	
	public GoodsModel getGm() {
		return gm;
	}
	public List<GoodsTypeModel> getGtmList() {
		return gtmList;
	}
	public List<GoodsModel> getGmList() {
		return gmList;
	}

	public String ajaxGetGtmAndGm(){
		//根据供应商的uuid获取类别数据与商品数据
		//类别中必须有商品
		gtmList = goodsTypeEbi.getAllUnionBySupplier(supplierUuid);
		//根据第一个商品类别获取对应的所有商品
		gmList = goodsEbi.getAllByGtmUuid(gtmList.get(0).getUuid());
		//获取第一个商品信息
		gm = gmList.get(0);
		return "ajaxGetGtmAndGm";
	}
	
	//需要过滤已经使用的数据
	public String ajaxGetGtmAndGm2(){
		//解析出已经使用的商品对应的uuid
		String[] uuidsArr = used.split(",");
		//将使用过的商品 uuid转换为一个数组/集合
		Set<Long> uuids = new HashSet<Long>();
		for(String uuidStr:uuidsArr){
			uuids.add(new Long(uuidStr));
		}
		
		//根据供应商的uuid获取类别数据与商品数据
		//类别中必须有商品
		gtmList = goodsTypeEbi.getAllUnionBySupplier(supplierUuid);
		//1.如果类别中的所有商品都使用过，该类别删除
		//1.x如果类别中某个商品没有使用过，该类别保留
		goodsType:
		for(int i = gtmList.size()-1;i>=0;i--){
			GoodsTypeModel gtm = gtmList.get(i);
			//根据商品类别获取商品
			gmList = goodsEbi.getAllByGtmUuid(gtm.getUuid());
			for(GoodsModel temp:gmList){
				if(!uuids.contains(temp.getUuid())){
					continue goodsType;
				}
			}
			//该类别中所有商品全部使用过
			gtmList.remove(i);
		}
		
		//根据第一个商品类别获取对应的所有商品
		gmList = goodsEbi.getAllByGtmUuid(gtmList.get(0).getUuid());
		//删除掉已经使用过的商品
		for(int i = gmList.size()-1;i>=0;i--){
			GoodsModel gm = gmList.get(i);
			if(uuids.contains(gm.getUuid())){
				//该商品已经使用过
				gmList.remove(i);
			}
		}
		
		//获取第一个商品信息
		gm = gmList.get(0);
		return "ajaxGetGtmAndGm";
	}
	
	public String ajaxGetGm(){
		gmList = goodsEbi.getAllByGtmUuid(goodsTypeUuid);
		gm = gmList.get(0);
		return "ajaxGetGm";
	}
	
	public String ajaxGetOne(){
		gm = goodsEbi.get(goodsUuid);
		return "ajaxGetOne";
	}
	
	public Long[] goodsUuids;
	public Integer[] nums;
	public Double[] prices;
	
	//生成采购订单
	public String buyOrder(){
		//收集页面的值
		//om.sm.uuid->om
		/*
		System.out.println(om.getSm().getUuid());
		System.out.println("-----------------");
		for(Long temp:goodsUuids){
			System.out.println(temp);
		}
		System.out.println("-----------------");
		for(Integer temp:nums){
			System.out.println(temp);
		}
		System.out.println("-----------------");
		for(Double temp:prices){
			System.out.println(temp);
		}
		*/
		orderEbi.save(getLogin(),om,goodsUuids,nums,prices);
		return TO_LIST;
	}
	
	public String buyDetailList(){
		om = orderEbi.get(om.getUuid());
		return "buyDetailList";
	}
	
	//--审核相关----------------------
	public String buyCheck(){
		setDataTotal(orderEbi.getCountByTypes(oqm));
		List<OrderModel> orderList = orderEbi.getAllNoCheckOrder(oqm,pageNum,pageCount);
		put("orderList",orderList);
		return "buyCheck";
	}
	//转到审核详情页
	public String toBuyCheckDetail(){
		//根据订单的uuid获取订单数据
		om = orderEbi.get(om.getUuid());
		return "toBuyCheckDetail";
	}
	//采购审核通过
	public String buyCheckPass(){
		orderEbi.buyCheckPass(om.getUuid(),getLogin());
		return "toBuyCheck";
	}
	//采购审核驳回
	public String buyCheckNoPass(){
		orderEbi.buyCheckNoPass(om.getUuid(),getLogin());
		return "toBuyCheck";
	}
	
	
	//--任务分配----------------------
	public String assignTaskList(){
		//获取待分配的任务数据集合
		List<OrderModel> orderList = orderEbi.getAllTasks(oqm,pageNum,pageCount);
		put("orderList",orderList);
		//跳转页面
		return "assignTaskList";
	}
	
	public String assignTaskDetail(){
		//加载运输部门的所有员工
		Long depUuid = getLogin().getDm().getUuid();
		List<EmpModel> empList = empEbi.getAllByDep(depUuid);
		put("empList",empList);
		om = orderEbi.get(om.getUuid());
		return "assignTaskDetail";
	}
	
	//指派具体任务人
	public String assignTask(){
		//指派任务人  om.uuid   om.completer.uuid
		orderEbi.assignTask(om);
		return "toAssignTaskList";
	}
	
	public String queryTask(){
		//根据登陆人信息获取对应的任务列表
		List<OrderModel> orderList = orderEbi.getAllByCompleter(oqm,pageNum,pageCount,getLogin());
		put("orderList",orderList);
		return "queryTask";
	}
	
	public String toTaskDetail(){
		om = orderEbi.get(om.getUuid());
		return "toTaskDetail";
	}
	
	public String completeTask(){
		orderEbi.endTask(om.getUuid());
		return "toQueryTask";
	}
	
	//--任务分配结束----------------------
	
	//仓库入库
	public String inGoodsList(){
		//展示所有没有入库完毕的订单数据
		List<OrderModel> orderList = orderEbi.getAllNotIn(oqm,pageNum,pageCount);
		put("orderList",orderList);
		return "inGoodsList";
	}
	public String inGoodsDetail(){
		//加载所有仓库数据
		List<StoreModel> storeList = storeEbi.getAll();
		put("storeList",storeList);
		om = orderEbi.get(om.getUuid());
		return "inGoodsDetail";
	}
	
	//--入库--------------------------------
	public Long odmUuid;
	private OrderDetailModel odm;
	
	public OrderDetailModel getOdm() {
		return odm;
	}

	public String ajaxGetSurplusByOdmUuid(){
		//根据odmUuid获取对应的货物剩余数量
		odm = orderDetailEbi.get(odmUuid);
		return "ajaxGetSurplusByOdmUuid";
	}
}
