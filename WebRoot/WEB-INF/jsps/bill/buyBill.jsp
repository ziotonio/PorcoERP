<%-- buyBill.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/Calendar.js"></script>
<%-- Here starts the javascript call function --%>
<script type="text/javascript" src="buyBill.js">
</script>
<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">进货报表</span>
		</div>
	</div>
	<div class="content-text">
		<s:form action="bill_buyBill" method="post">
			<div class="square-o-top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					style="font-size:14px; font-weight:bold; font-family:"黑体";">
					<tr>
						<td width="70" height="30">报表类别:</td>
						<td width="140">
							<input type="radio" name="all" checked="checked">商品名称
						</td>
						<td width="70">订单类别:</td>
						<td width="190">
							<s:select name="bqm.type" list="@cn.itcast.invoice.invoice.order.vo.OrderModel@buyTypeMap" headerKey="-1" headerValue="----请-选-择----"></s:select>
						</td>
						<td width="70">开始日期:</td>
						<td width="190">
							<input type="text" size="18" onfocus="c.showMoreDay=false;c.show(this);" value="${bqm.beginView}"/>
							<s:hidden name="bqm.begin"/>
						<td ><a id="query"> <img
								src="images/can_b_01.gif" border="0" /> </a></td>
					</tr>
					<tr>
						<td height="30">&nbsp;</td>
						<td>
							<input type="radio" name="all">销售人员
						</td>
						<td>厂商名称:</td>
						<td>
							<s:select name="bqm.supplierUuid" list="supplierList" listKey="uuid" listValue="name" headerKey="-1" headerValue="----请-选-择----"></s:select>
						</td>
						<td>结束日期:</td>
						<td width="190">
							<input type="text" size="18" onfocus="c.showMoreDay=false;c.show(this);" value="${bqm.endView}" />
							<s:hidden name="bqm.end"/>
						<td>
							<a href="bill_downloadExcelBill.action?bqm.begin=${param['bqm.begin']}&bqm.end=${param['bqm.end']}&bqm.type=${param['bqm.type']}&bqm.supplierUuid=${param['bqm.supplierUuid']}">
								<img src="images/can_b_03.gif" border="0" />
							</a>
					</tr>
				</table>
			</div>
			<!--"square-o-top"end-->
			<div class="square-order">
				<table width="70%" border="1" cellpadding="0" cellspacing="0" style="float:left;">
					<tr align="center"
						style="background:url(images/table_bg.gif) repeat-x;">
						<td colspan="2" width="58%" height="30">商品名称</td>
						<td colspan="2" width="32%">总数量</td>
						<td width="10%">详情</td>
					</tr>
					<s:iterator value="billList" var="obj">
						<tr align="center" bgcolor="#FFFFFF">
							<td colspan="2" width="30%" height="30">${obj[0].name}</td>
							<td colspan="2">${obj[1]}</td>
							<td>
								<a href="javascript:void(0)" class="xiu info" val="${obj[0].uuid}">
									详情
								</a>
							</td>
						</tr>
					</s:iterator>	
				</table>
				<div style="float:right;"> 
					<a href="bill_billForPie.action?bqm.begin=${param['bqm.begin']}&bqm.end=${param['bqm.end']}&bqm.type=${param['bqm.type']}&bqm.supplierUuid=${param['bqm.supplierUuid']}">
						<img id="pei" src="bill_billForPie.action?bqm.begin=${param['bqm.begin']}&bqm.end=${param['bqm.end']}&bqm.type=${param['bqm.type']}&bqm.supplierUuid=${param['bqm.supplierUuid']}" width="228px" height="180px">
					</a>
				</div>
			</div>
		</s:form>
	</div>
	<div class="content-bbg"></div>
</div>
<%-- end of buyBill.jsp --%>