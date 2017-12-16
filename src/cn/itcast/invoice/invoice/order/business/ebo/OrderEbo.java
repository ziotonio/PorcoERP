package cn.itcast.invoice.invoice.order.business.ebo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.itcast.invoice.auth.emp.vo.EmpModel;
import cn.itcast.invoice.invoice.goods.vo.GoodsModel;
import cn.itcast.invoice.invoice.order.business.ebi.OrderEbi;
import cn.itcast.invoice.invoice.order.dao.dao.OrderDao;
import cn.itcast.invoice.invoice.order.vo.OrderDetailModel;
import cn.itcast.invoice.invoice.order.vo.OrderModel;
import cn.itcast.invoice.invoice.order.vo.OrderQueryModel;
import cn.itcast.invoice.util.base.BaseQueryModel;
import cn.itcast.invoice.util.exception.AppException;
import cn.itcast.invoice.util.format.MD5Utils;

public class OrderEbo implements OrderEbi{
	private OrderDao orderDao;
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void save(OrderModel om) {
		orderDao.save(om);
	}

	public void delete(OrderModel om) {
		orderDao.delete(om);
	}

	public void update(OrderModel om) {
		orderDao.update(om);
	}

	public OrderModel get(Serializable uuid) {
		return orderDao.get(uuid);
	}

	public List<OrderModel> getAll() {
		return orderDao.getAll();
	}

	public List<OrderModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return orderDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return orderDao.getCount(qm);
	}
	
	public void save(EmpModel em,OrderModel om, Long[] goodsUuids, Integer[] nums,Double[] prices) {
		//将订单信息组织好，保存
		//om中保存有对应供应商的uuid
		//设置订单号:系统时间+登陆人uuid
		String orderNum = System.currentTimeMillis()+""+em.getUuid();
		om.setOrderNum(MD5Utils.md5(orderNum));
		//设置订单类别
		om.setOrderType(OrderModel.ORDER_ORDERTYPE_OF_BUY);
		//设置订单状态
		om.setType(OrderModel.ORDER_TYPE_OF_BUY_NO_CHECK);
		//设置订单创建时间为当前系统时间
		om.setCreateTime(System.currentTimeMillis());
		//设置当前登陆人为制单人
		om.setCreater(em);
		
		Integer totalNum = 0;
		Double totalPrice = 0.0d;
		
		//将订单明细信息组织包，保存
		Set<OrderDetailModel> odms = new HashSet<OrderDetailModel>();
		for(int i = 0;i<goodsUuids.length;i++){
			Long goodsUuid = goodsUuids[i];
			Integer num = nums[i];
			Double price = prices[i];
			
			totalNum+=num;
			totalPrice+=num*price;
			
			OrderDetailModel odm = new OrderDetailModel();
			odm.setNum(num);
			//设置订单明细中当前货物完成量为订单商品货物总量
			odm.setSurplus(num);
			odm.setPrice(price);
			
			GoodsModel gm = new GoodsModel();
			gm.setUuid(goodsUuid);
			odm.setGm(gm);
			//绑定明细到订单的关系
			odm.setOm(om);
			odms.add(odm);
		}
		//设置所有的订单明细集合
		om.setOdms(odms);
		//设置商品总数量
		om.setTotalNum(totalNum);
		//设置订单总价格
		om.setTotalPrice(totalPrice);
		//现在的状态：om中包含有odms ,odms中的odm包含om
		//当使用级联添加时，保存的是om，基于关联关系，会级联到odms中的所有对象
		//谁给orderDetail表中的orderUuid赋值的  update?insert?
		//此处设置了cascade=save-update那么，保存om将保存其中odms中的odm
		//inverse=true则断开了om维护odm中的关联关系的可能性update将不执行
		//由于odm中绑定了与om的关系，因此在添加时，insert语句中将出现orderUuid这个字段
		orderDao.save(om);
	}

	
	private Integer[] buyCheckTypes = {
			OrderModel.ORDER_TYPE_OF_BUY_NO_CHECK,
			//OrderModel.ORDER_TYPE_OF_BUY_RETURN_NO_CHECK
			};
	public List<OrderModel> getAllNoCheckOrder(OrderQueryModel oqm,Integer pageNum, Integer pageCount) {
			//采购未审核
			//采购退货未审核
			//将两种状态提交到数据层	采购未审核状态，采购退货未审核状态
			//传递的条件是多个值，因此需要将数据进行包装，称为数组/集合
		return orderDao.getAllByTypes(oqm,pageNum,pageCount,buyCheckTypes);
	}

	public Integer getCountByTypes(OrderQueryModel oqm) {
		return orderDao.getCountByTypes(oqm,buyCheckTypes);
	}

	public void buyCheckPass(Long uuid,EmpModel em) {
		//如果该订单没有审核
		//修改状态将未审核状态修改为审核通过状态	ORDER_TYPE_OF_BUY_CHECK_PASS
		//快照更新
		OrderModel om = orderDao.get(uuid);
		//逻辑判定
		if(!Arrays.asList(buyCheckTypes).contains(om.getType())){
			throw new AppException("对不起,请不要进行非法操作！");
		}
		om.setType(OrderModel.ORDER_TYPE_OF_BUY_CHECK_PASS);
		//谁什么时间审核的？
		om.setCheckTime(System.currentTimeMillis());
		om.setChecker(em);
	}

	public void buyCheckNoPass(Long uuid,EmpModel em) {
		OrderModel om = orderDao.get(uuid);
		//逻辑判定
		if(!Arrays.asList(buyCheckTypes).contains(om.getType())){
			throw new AppException("对不起,请不要进行非法操作！");
		}
		om.setType(OrderModel.ORDER_TYPE_OF_BUY_CHECK_NO_PASS);
		om.setCheckTime(System.currentTimeMillis());
		om.setChecker(em);
	}
	private Integer[] taskTypes = {
			OrderModel.ORDER_TYPE_OF_BUY_CHECK_PASS,
			OrderModel.ORDER_TYPE_OF_BUY_BUYING,
			OrderModel.ORDER_TYPE_OF_BUY_IN_STORE,
			OrderModel.ORDER_TYPE_OF_BUY_END,
			//缺少12种
			//共计16种状态
			};
	public List<OrderModel> getAllTasks(OrderQueryModel oqm, Integer pageNum,Integer pageCount) {
		//获取的数据有哪些？无论何种类别的订单，只要是审核通过后，所有状态均显示
		return orderDao.getAllByTypes(oqm, pageNum, pageCount, taskTypes);
	}
	
	public static Integer[] taskTypes2 = {
			OrderModel.ORDER_TYPE_OF_BUY_CHECK_PASS,
			//缺少3种
			//共计4种状态
			};
	
	public static final Set<Integer> taskTypesSet = new HashSet<Integer>();
	static{
		taskTypesSet.add(OrderModel.ORDER_TYPE_OF_BUY_CHECK_PASS);
		//taskTypesSet.add(OrderModel.);
		//taskTypesSet.add(OrderModel.);
		//taskTypesSet.add(OrderModel.);
	}
	
	public void assignTask(OrderModel om) {
		OrderModel temp = orderDao.get(om.getUuid());
		if(!Arrays.asList(taskTypes2).contains(temp.getType())){
			throw new AppException("对不起,请不要进行非法操作！");
		}
		//当前任务分配完毕后，切换状态为正在采购
		//采购和采购退货都归同一个人审批
		//if(原始是采购业务，修改为采购....)
		//else if(原始是采购退货任务,修改为采购退货....)
		//else if(原始是采购退货任务,修改为采购退货....)
		//else if(原始是采购退货任务,修改为采购退货....)
		temp.setType(OrderModel.ORDER_TYPE_OF_BUY_BUYING);
		//修改任务人
		temp.setCompleter(om.getCompleter());
	}

	public List<OrderModel> getAllByCompleter(OrderQueryModel oqm,Integer pageNum, Integer pageCount, EmpModel login) {
		oqm.setCompleter(login);
		return orderDao.getAll(oqm, pageNum, pageCount);
	}

	public void endTask(Long uuid) {
		OrderModel om = orderDao.get(uuid);
		/*
		if(....){
			throw new AppException("对不起,请不要进行非法操作！");
		}
		*/
		//仅需要修改一个状态
		om.setType(OrderModel.ORDER_TYPE_OF_BUY_IN_STORE);
	}

	private Integer[] inTypes = {
			OrderModel.ORDER_TYPE_OF_BUY_IN_STORE,
			//缺少1种
			};
	public List<OrderModel> getAllNotIn(OrderQueryModel oqm, Integer pageNum,Integer pageCount) {
		//
		return orderDao.getAllByTypes(oqm, pageNum, pageCount, inTypes);
	}

}
