package cn.itcast.invoice.invoice.store.business.ebo;

import java.io.Serializable;
import java.util.List;

import cn.itcast.invoice.auth.emp.vo.EmpModel;
import cn.itcast.invoice.invoice.goods.vo.GoodsModel;
import cn.itcast.invoice.invoice.order.dao.dao.OrderDetailDao;
import cn.itcast.invoice.invoice.order.vo.OrderDetailModel;
import cn.itcast.invoice.invoice.order.vo.OrderModel;
import cn.itcast.invoice.invoice.store.business.ebi.StoreEbi;
import cn.itcast.invoice.invoice.store.dao.dao.StoreDao;
import cn.itcast.invoice.invoice.store.vo.StoreModel;
import cn.itcast.invoice.invoice.storedetail.dao.dao.StoreDetailDao;
import cn.itcast.invoice.invoice.storedetail.vo.StoreDetailModel;
import cn.itcast.invoice.invoice.storeoper.dao.dao.StoreOperDao;
import cn.itcast.invoice.invoice.storeoper.vo.StoreOperModel;
import cn.itcast.invoice.util.base.BaseQueryModel;
import cn.itcast.invoice.util.exception.AppException;

public class StoreEbo implements StoreEbi{
	private StoreDao storeDao;
	private StoreDetailDao storeDetailDao;
	private OrderDetailDao orderDetailDao;
	private StoreOperDao storeOperDao;
	
	public void setStoreOperDao(StoreOperDao storeOperDao) {
		this.storeOperDao = storeOperDao;
	}

	public void setOrderDetailDao(OrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}

	public void setStoreDetailDao(StoreDetailDao storeDetailDao) {
		this.storeDetailDao = storeDetailDao;
	}

	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public void save(StoreModel sm) {
		storeDao.save(sm);
	}

	public void delete(StoreModel sm) {
		storeDao.delete(sm);
	}

	public void update(StoreModel sm) {
		storeDao.update(sm);
	}

	public StoreModel get(Serializable uuid) {
		return storeDao.get(uuid);
	}

	public List<StoreModel> getAll() {
		return storeDao.getAll();
	}

	public List<StoreModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return storeDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return storeDao.getCount(qm);
	}

	public OrderDetailModel inGoods(Long odmUuid ,Long goodsUuid, Long storeUuid, Integer num,EmpModel login) {
		GoodsModel gm = new GoodsModel();
		gm.setUuid(goodsUuid);
		StoreModel sm = new StoreModel();
		sm.setUuid(storeUuid);
		
		
		//入库究竟要做什么？
		//1.原始订单明细中的已入库数量更新
		//update 订单明细  
		//快照更新
		OrderDetailModel odm = orderDetailDao.get(odmUuid);
		
		//校验
		if(odm.getSurplus() < num){
			throw new AppException("aa");
		}
		
		
		odm.setSurplus(odm.getSurplus()-num);
		
		//2.记录入库的记录
		StoreOperModel som = new StoreOperModel();
		//入库操作时间
		som.setOperTime(System.currentTimeMillis());
		//本次操作数量
		som.setNum(num);
		//设置操作类型为入库
		som.setType(StoreOperModel.STOREOPER_TYPE_OF_IN);
		//设置操作的商品
		som.setGm(gm);
		//设置操作人
		som.setEm(login);
		//设置对应的仓库
		som.setSm(sm);
		//设置操作对应的订单
		som.setOm(odm.getOm());
		storeOperDao.save(som);
		
		//3.仓库中的现有商品数量更新
		//A B两个仓库
		//入X商品，A仓库X商品100个，B仓库从没有放过X商品
		//X商品入B
		//根据商品uuid与仓库的uuid获取商品在仓库中的数据记录
		StoreDetailModel sdm = storeDetailDao.getBySmAndGm(storeUuid,goodsUuid);
		if(sdm == null){
			//该仓库中没有存储过该商品
			//初始化数据，save
			sdm = new StoreDetailModel();
			sdm.setNum(num);
			sdm.setGm(gm);
			sdm.setSm(sm);
			storeDetailDao.save(sdm);
		}else{
			//该仓库中存储过该商品
			//利用快照更新数量
			sdm.setNum(sdm.getNum()+num);
		}
		
		//4.当订单中的所有商品全部入库完毕后，修改订单的状态，同时修改完成时间
		OrderModel om = odm.getOm();
		int sum = 0;
		for(OrderDetailModel temp:om.getOdms()){
			sum += temp.getSurplus();
		}
		if(sum == 0){
			//快照更新
			//修改订单状态
			om.setType(OrderModel.ORDER_TYPE_OF_BUY_END);
			//修改结单时间
			om.setCompleteTime(System.currentTimeMillis());
		}
		return odm;
	}

}
