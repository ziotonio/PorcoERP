package cn.itcast.invoice.invoice.order.vo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cn.itcast.invoice.auth.emp.vo.EmpModel;
import cn.itcast.invoice.invoice.supplier.vo.SupplierModel;
import cn.itcast.invoice.util.format.FormatUtil;

public class OrderModel {
	public static final Integer ORDER_TYPE_OF_BUY_NO_CHECK = 111;
	public static final Integer ORDER_TYPE_OF_BUY_CHECK_NO_PASS = 120;
	public static final Integer ORDER_TYPE_OF_BUY_CHECK_PASS = 121;
	public static final Integer ORDER_TYPE_OF_BUY_BUYING = 131;
	public static final Integer ORDER_TYPE_OF_BUY_IN_STORE = 141;
	public static final Integer ORDER_TYPE_OF_BUY_END = 199;
	
	public static final String ORDER_TYPE_OF_BUY_NO_CHECK_VIEW = "æœªå®¡æ ¸";
	public static final String ORDER_TYPE_OF_BUY_CHECK_NO_VIEW = "é©³å›ž";
	public static final String ORDER_TYPE_OF_BUY_CHECK_OK_VIEW = "ä»»åŠ¡æŒ‡æ´¾ä¸­";
	public static final String ORDER_TYPE_OF_BUY_BUYING_VIEW = "é‡‡è´­ä¸­";
	public static final String ORDER_TYPE_OF_BUY_IN_STORE_VIEW = "å…¥åº“ä¸­";
	public static final String ORDER_TYPE_OF_BUY_END_VIEW = "å·²ç»“å�•";
	
	public static final Map<Integer, String> orderTypeMap = new HashMap<Integer, String>();
	public static final Map<Integer, String> buyTypeMap = new TreeMap<Integer, String>();
	public static final Map<Integer, String> saleTypeMap = new TreeMap<Integer, String>();
	private static final Map<Integer, String> typeMap = new TreeMap<Integer, String>();
	
	public static final Integer ORDER_ORDERTYPE_OF_BUY = 1;
	public static final Integer ORDER_ORDERTYPE_OF_SALE = 2;
	public static final Integer ORDER_ORDERTYPE_OF_RETURN_BUY = 3;
	public static final Integer ORDER_ORDERTYPE_OF_RETURN_SALE = 4;
	
	public static final String ORDER_ORDERTYPE_OF_BUY_VIEW = "é‡‡è´­";
	public static final String ORDER_ORDERTYPE_OF_SALE_VIEW = "é”€å”®";
	public static final String ORDER_ORDERTYPE_OF_RETURN_BUY_VIEW = "é‡‡è´­é€€è´§";
	public static final String ORDER_ORDERTYPE_OF_RETURN_SALE_VIEW = "é”€å”®é€€è´§";
	
	static{
		orderTypeMap.put(ORDER_ORDERTYPE_OF_BUY,ORDER_ORDERTYPE_OF_BUY_VIEW);
		orderTypeMap.put(ORDER_ORDERTYPE_OF_SALE,ORDER_ORDERTYPE_OF_SALE_VIEW);
		orderTypeMap.put(ORDER_ORDERTYPE_OF_RETURN_BUY,ORDER_ORDERTYPE_OF_RETURN_BUY_VIEW);
		orderTypeMap.put(ORDER_ORDERTYPE_OF_RETURN_SALE,ORDER_ORDERTYPE_OF_RETURN_SALE_VIEW);
		
		buyTypeMap.put(ORDER_TYPE_OF_BUY_NO_CHECK, ORDER_TYPE_OF_BUY_NO_CHECK_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_CHECK_NO_PASS, ORDER_TYPE_OF_BUY_CHECK_NO_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_CHECK_PASS, ORDER_TYPE_OF_BUY_CHECK_OK_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_BUYING, ORDER_TYPE_OF_BUY_BUYING_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_IN_STORE, ORDER_TYPE_OF_BUY_IN_STORE_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_END, ORDER_TYPE_OF_BUY_END_VIEW);
		
		typeMap.putAll(buyTypeMap);
	}
	
	//typeçŠ¶æ€�ç±»åž‹
	/*
	ç”³è¯·æœªå®¡æ‰¹					æœªå®¡æ ¸			111
	ç”³è¯·å·²å®¡æ‰¹é©³å›ž				é©³å›ž				120
	ç”³è¯·å·²å®¡æ‰¹é€šè¿‡/æœªæŒ‡æ´¾ä»»åŠ¡äºº	ä»»åŠ¡æŒ‡æ´¾ä¸­			121
	å·²æŒ‡æ´¾ä»»åŠ¡äºº/ä»»åŠ¡æœªå®Œæˆ�		é‡‡è´­ä¸­			131
	ä»»åŠ¡å·²ç»�å®Œæˆ�/è®¢å�•æœªå…¥åº“		å…¥åº“ä¸­			141
	è®¢å�•å…¥åº“å®Œæ¯•				å·²ç»“å�•			199
	*/
	/*
	211	æœªå®¡æ ¸									
	220	é©³å›ž
	221	ä»»åŠ¡æŒ‡æ´¾ä¸­
	231	å‡ºåº“ä¸­
	241	é€�è´§ä¸­
	299	å·²ç»“å�•
	*/
	
	private Long uuid;
	private String orderNum;

	private Integer totalNum;
	
	private Double totalPrice;
	
	private Integer orderType;
	private Integer type;
	
	private Long createTime;
	private Long checkTime;
	private Long completeTime;
	
	//è§†å›¾å€¼
	private String totalPriceView;
	private String createTimeView;
	private String checkTimeView;
	private String completeTimeView;
	private String orderTypeView;
	private String typeView;
	
	//å…³ç³»
	private EmpModel creater;
	private EmpModel checker;
	private EmpModel completer;
	private SupplierModel sm;
	private Set<OrderDetailModel> odms;

	public Set<OrderDetailModel> getOdms() {
		return odms;
	}
	public void setOdms(Set<OrderDetailModel> odms) {
		this.odms = odms;
	}
	public String getTotalPriceView() {
		return totalPriceView;
	}
	public String getOrderTypeView() {
		return orderTypeView;
	}
	public String getTypeView() {
		return typeView;
	}
	public String getCreateTimeView() {
		return createTimeView;
	}
	public String getCheckTimeView() {
		return checkTimeView;
	}
	public String getCompleteTimeView() {
		return completeTimeView;
	}
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
		this.totalPriceView = FormatUtil.formatMoney(totalPrice);
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
		this.orderTypeView =orderTypeMap.get(orderType);
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
		this.typeView = typeMap.get(type);
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
		this.createTimeView = FormatUtil.formatDateTime(createTime);
	}
	public Long getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Long checkTime) {
		this.checkTime = checkTime;
		this.checkTimeView = FormatUtil.formatDateTime(checkTime);
	}
	public Long getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Long completeTime) {
		this.completeTime = completeTime;
		this.completeTimeView = FormatUtil.formatDateTime(completeTime);
	}
	public EmpModel getCreater() {
		return creater;
	}
	public void setCreater(EmpModel creater) {
		this.creater = creater;
	}
	public EmpModel getChecker() {
		return checker;
	}
	public void setChecker(EmpModel checker) {
		this.checker = checker;
	}
	public EmpModel getCompleter() {
		return completer;
	}
	public void setCompleter(EmpModel completer) {
		this.completer = completer;
	}
	public SupplierModel getSm() {
		return sm;
	}
	public void setSm(SupplierModel sm) {
		this.sm = sm;
	}
	
}
