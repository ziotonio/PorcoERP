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

	//è·³è½¬åˆ°åˆ—è¡¨é¡µé�¢
	public String list(){
		setDataTotal(orderEbi.getCount(oqm));
		List<OrderModel> orderList = orderEbi.getAll(oqm,pageNum,pageCount);
		put("orderList",orderList);
		return LIST;
	}

	//ä¿�å­˜/ä¿®æ”¹
	public String save(){
		if(om.getUuid()== null){
			orderEbi.save(om);
		}else{
			orderEbi.update(om);
		}
		return TO_LIST;
	}

	//è·³è½¬åˆ°æ·»åŠ /ä¿®æ”¹é¡µé�¢
	public String input(){
		if(om.getUuid()!=null){
			om = orderEbi.get(om.getUuid());
		}
		return INPUT;
	}
	//è·³è½¬åˆ°é‡‡è´­è®¢å�•é¡µ
	
	public String buyInput(){
		List<SupplierModel> supplierList = supplierEbi.getAll();
		//å�˜æˆ�è¿‡æ»¤è¯¥æ•°æ�®
		//æœ‰ç±»åˆ«ä½†æ˜¯ç±»åˆ«ä¸­æ²¡æœ‰å•†å“�çš„ç±»åˆ«è¿‡æ»¤æŽ‰
		//æ²¡æœ‰ç±»åˆ«çš„ä¾›åº”å•†è¿‡æ»¤æŽ‰
		//å¾ªçŽ¯æ‰€æœ‰çš„ä¾›åº”å•†ï¼Œåˆ¤å®šè¯¥ä¾›åº”å•†æ˜¯å�¦ä¿�ç•™ï¼Œå¦‚æžœä¸�ä¿�ç•™åˆ é™¤
		/*
		for(int i = supplierList.size()-1;i>=0;i--){
			SupplierModel sm = supplierList.get(i);
			List<GoodsTypeModel> gtms = new ArrayList(sm.getGtms());
			//å¾ªçŽ¯å•†å“�ç±»åˆ«ä¸­çš„æ•°æ�®
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
		int flag=0;
		for(int i = supplierList.size()-1;i>=0;i--){
			flag=0;
			SupplierModel sm = supplierList.get(i);
			//List<GoodsTypeModel> gtms = new ArrayList(sm.getGtms());
			//æ€§èƒ½ä¼˜äºŽä¸Šé�¢çš„æ–¹æ¡ˆ
			List<GoodsTypeModel> gtms = goodsTypeEbi.getAllBySupplier(sm.getUuid());
			//å¾ªçŽ¯å•†å“�ç±»åˆ«ä¸­çš„æ•°æ�®
			for(int j = gtms.size()-1;j>=0;j--){
				GoodsTypeModel gtm = gtms.get(j);
				if(gtm.getGms().size() > 0){
					flag=1;
					continue;
				}
			}
			if(flag==1) {
				continue;
		    }else supplierList.remove(i);
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
		//åŠ è½½ä¾›åº”å•†æ•°æ�®
		//é—®é¢˜ï¼šä¸ªåˆ«æ²¡æœ‰å•†å“�ç±»åˆ«çš„ä¾›åº”å•†ä¸�åº”è¯¥è¢«åŠ è½½
		//è§£å†³æ–¹æ¡ˆä¸€ï¼š
		//è¯»å�–æ‰€æœ‰æ•°æ�®å�Žï¼Œé€šè¿‡è¿­ä»£é›†å�ˆå°†æ‰€æœ‰æ²¡æœ‰ç±»åˆ«çš„ä¾›åº”å•†åˆ é™¤æŽ‰
		//è§£å†³æ–¹æ¡ˆäºŒï¼š
		//æŸ¥è¯¢æ•°æ�®æ—¶ï¼Œç›´æŽ¥å°†æ²¡æœ‰å•†å“�ç±»åˆ«çš„ä¾›åº”å•†è¿‡æ»¤æŽ‰
		//æŸ¥ä¾›åº”å•†  ä¾›åº”å•†å…³è�”ç±»åˆ«
		//é—®é¢˜åˆ†æž�ï¼šæŸ�ä¸ªä¾›åº”å•†å…·æœ‰å¤šä¸ªå•†å“�ç±»åˆ«ï¼Œä½†æ˜¯ç±»åˆ«ä¸­æ²¡æœ‰å•†å“�ï¼Ÿ
		//æŸ¥ä¾›åº”å•†  ä¾›åº”å•†å…³è�”ç±»åˆ«ï¼Œç±»åˆ«å…³è�”å•†å“� distinct
		List<SupplierModel> supplierList = supplierEbi.getAllUnionTwo();
		//åŠ è½½ç¬¬ä¸€ä¸ªä¾›åº”å•†çš„ç±»åˆ«æ•°æ�®
		/*
		1å�·		A	B	C
				a1	b1	
				a2	b2
				a3
		*/
	/*
		List<GoodsTypeModel> gtmList = goodsTypeEbi.getAllUnionBySupplier(supplierList.get(0).getUuid());
		//åŠ è½½ç¬¬ä¸€ä¸ªç±»åˆ«çš„å•†å“�æ•°æ�®
		List<GoodsModel> gmList = goodsEbi.getAllByGtmUuid(gtmList.get(0).getUuid());
		//åŠ è½½ç¬¬ä¸€ä¸ªå•†å“�çš„ä»·æ ¼æ•°æ�®(çœ�ç•¥)
		//GoodsModel gm = gmList.get(0);
		//put("gm",gm);
		/*
		put("supplierList",supplierList);
		put("gtmList",gtmList);
		put("gmList",gmList);
		
		return "buyInput";
	}*/

	//åˆ é™¤
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
		//æ ¹æ�®ä¾›åº”å•†çš„uuidèŽ·å�–ç±»åˆ«æ•°æ�®ä¸Žå•†å“�æ•°æ�®
		//ç±»åˆ«ä¸­å¿…é¡»æœ‰å•†å“�
		gtmList = goodsTypeEbi.getAllUnionBySupplier(supplierUuid);
		//æ ¹æ�®ç¬¬ä¸€ä¸ªå•†å“�ç±»åˆ«èŽ·å�–å¯¹åº”çš„æ‰€æœ‰å•†å“�
		gmList = goodsEbi.getAllByGtmUuid(gtmList.get(0).getUuid());
		//èŽ·å�–ç¬¬ä¸€ä¸ªå•†å“�ä¿¡æ�¯
		gm = gmList.get(0);
		return "ajaxGetGtmAndGm";
	}
	
	//éœ€è¦�è¿‡æ»¤å·²ç»�ä½¿ç”¨çš„æ•°æ�®
	public String ajaxGetGtmAndGm2(){
		//è§£æž�å‡ºå·²ç»�ä½¿ç”¨çš„å•†å“�å¯¹åº”çš„uuid
		String[] uuidsArr = used.split(",");
		//å°†ä½¿ç”¨è¿‡çš„å•†å“� uuidè½¬æ�¢ä¸ºä¸€ä¸ªæ•°ç»„/é›†å�ˆ
		Set<Long> uuids = new HashSet<Long>();
		for(String uuidStr:uuidsArr){
			uuids.add(new Long(uuidStr));
		}
		
		//æ ¹æ�®ä¾›åº”å•†çš„uuidèŽ·å�–ç±»åˆ«æ•°æ�®ä¸Žå•†å“�æ•°æ�®
		//ç±»åˆ«ä¸­å¿…é¡»æœ‰å•†å“�
		gtmList = goodsTypeEbi.getAllUnionBySupplier(supplierUuid);
		//1.å¦‚æžœç±»åˆ«ä¸­çš„æ‰€æœ‰å•†å“�éƒ½ä½¿ç”¨è¿‡ï¼Œè¯¥ç±»åˆ«åˆ é™¤
		//1.xå¦‚æžœç±»åˆ«ä¸­æŸ�ä¸ªå•†å“�æ²¡æœ‰ä½¿ç”¨è¿‡ï¼Œè¯¥ç±»åˆ«ä¿�ç•™
		int flag=0;
		for(int i = gtmList.size()-1;i>=0;i--){
			flag = 0;
			GoodsTypeModel gtm = gtmList.get(i);
			//根据商品类别获取商品
			gmList = goodsEbi.getAllByGtmUuid(gtm.getUuid());
			for(GoodsModel temp:gmList){
				if(!uuids.contains(temp.getUuid())){
					flag = 1;
					continue;
				}
			}
			//该类别中所有商品全部使用过
			if(flag ==1){
				continue;
			} else gtmList.remove(i);
		}

		
		//æ ¹æ�®ç¬¬ä¸€ä¸ªå•†å“�ç±»åˆ«èŽ·å�–å¯¹åº”çš„æ‰€æœ‰å•†å“�
		gmList = goodsEbi.getAllByGtmUuid(gtmList.get(0).getUuid());
		//åˆ é™¤æŽ‰å·²ç»�ä½¿ç”¨è¿‡çš„å•†å“�
		for(int i = gmList.size()-1;i>=0;i--){
			GoodsModel gm = gmList.get(i);
			if(uuids.contains(gm.getUuid())){
				//è¯¥å•†å“�å·²ç»�ä½¿ç”¨è¿‡
				gmList.remove(i);
			}
		}
		
		//èŽ·å�–ç¬¬ä¸€ä¸ªå•†å“�ä¿¡æ�¯
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
	
	//ç”Ÿæˆ�é‡‡è´­è®¢å�•
	public String buyOrder(){
		//æ”¶é›†é¡µé�¢çš„å€¼
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
	
	//--å®¡æ ¸ç›¸å…³----------------------
	public String buyCheck(){
		setDataTotal(orderEbi.getCountByTypes(oqm));
		List<OrderModel> orderList = orderEbi.getAllNoCheckOrder(oqm,pageNum,pageCount);
		put("orderList",orderList);
		return "buyCheck";
	}
	//è½¬åˆ°å®¡æ ¸è¯¦æƒ…é¡µ
	public String toBuyCheckDetail(){
		//æ ¹æ�®è®¢å�•çš„uuidèŽ·å�–è®¢å�•æ•°æ�®
		om = orderEbi.get(om.getUuid());
		return "toBuyCheckDetail";
	}
	//é‡‡è´­å®¡æ ¸é€šè¿‡
	public String buyCheckPass(){
		orderEbi.buyCheckPass(om.getUuid(),getLogin());
		return "toBuyCheck";
	}
	//é‡‡è´­å®¡æ ¸é©³å›ž
	public String buyCheckNoPass(){
		orderEbi.buyCheckNoPass(om.getUuid(),getLogin());
		return "toBuyCheck";
	}
	
	
	//--ä»»åŠ¡åˆ†é…�----------------------
	public String assignTaskList(){
		//èŽ·å�–å¾…åˆ†é…�çš„ä»»åŠ¡æ•°æ�®é›†å�ˆ
		List<OrderModel> orderList = orderEbi.getAllTasks(oqm,pageNum,pageCount);
		put("orderList",orderList);
		//è·³è½¬é¡µé�¢
		return "assignTaskList";
	}
	
	public String assignTaskDetail(){
		//åŠ è½½è¿�è¾“éƒ¨é—¨çš„æ‰€æœ‰å‘˜å·¥
		Long depUuid = getLogin().getDm().getUuid();
		List<EmpModel> empList = empEbi.getAllByDep(depUuid);
		put("empList",empList);
		om = orderEbi.get(om.getUuid());
		return "assignTaskDetail";
	}
	
	//æŒ‡æ´¾å…·ä½“ä»»åŠ¡äºº
	public String assignTask(){
		//æŒ‡æ´¾ä»»åŠ¡äºº  om.uuid   om.completer.uuid
		orderEbi.assignTask(om);
		return "toAssignTaskList";
	}
	
	public String queryTask(){
		//æ ¹æ�®ç™»é™†äººä¿¡æ�¯èŽ·å�–å¯¹åº”çš„ä»»åŠ¡åˆ—è¡¨
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
	
	//--ä»»åŠ¡åˆ†é…�ç»“æ�Ÿ----------------------
	
	//ä»“åº“å…¥åº“
	public String inGoodsList(){
		//å±•ç¤ºæ‰€æœ‰æ²¡æœ‰å…¥åº“å®Œæ¯•çš„è®¢å�•æ•°æ�®
		List<OrderModel> orderList = orderEbi.getAllNotIn(oqm,pageNum,pageCount);
		put("orderList",orderList);
		return "inGoodsList";
	}
	public String inGoodsDetail(){
		//åŠ è½½æ‰€æœ‰ä»“åº“æ•°æ�®
		List<StoreModel> storeList = storeEbi.getAll();
		put("storeList",storeList);
		om = orderEbi.get(om.getUuid());
		return "inGoodsDetail";
	}
	
	//--å…¥åº“--------------------------------
	public Long odmUuid;
	private OrderDetailModel odm;
	
	public OrderDetailModel getOdm() {
		return odm;
	}

	public String ajaxGetSurplusByOdmUuid(){
		//æ ¹æ�®odmUuidèŽ·å�–å¯¹åº”çš„è´§ç‰©å‰©ä½™æ•°é‡�
		odm = orderDetailEbi.get(odmUuid);
		return "ajaxGetSurplusByOdmUuid";
	}
}
