<%-- inGoods.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="../../../css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../../js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../../../js/Calendar.js"></script>
<%-- Here starts the javascript call function --%>
<script type="text/javascript" src="inGoods.js">
</script>
<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">进货报表</span>
		</div>
	</div>
	<div class="content-text">
		<form action="inGoods.jsp" method="post">
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
							<select>
								<option value="-1">----请-选-择----</option>
								<option value="1">未审核</option>
								<option value="0">正在派单</option>
								<option value="0">正在采购</option>
								<option value="0">正在入库</option>
							</select>
						</td>
						<td width="70">开始日期:</td>
						<td width="190">
							<input type="text" size="18" onfocus="c.showMoreDay=false;c.show(this);" />
						<td ><a id="query"> <img
								src="../../../images/can_b_01.gif" border="0" /> </a></td>
					</tr>
					<tr>
						<td height="30">&nbsp;</td>
						<td>
							<input type="radio" name="all">销售人员
						</td>
						<td>厂商名称:</td>
						<td>
							<select class="kuan">
								<option value="-1">----请-选-择----</option>
								<option value="1">七匹狼</option>
								<option value="2">康师傅</option>
							</select>
						</td>
						<td>结束日期:</td>
						<td width="190">
							<input type="text" size="18" onfocus="c.showMoreDay=false;c.show(this);" />
						<td>
							<a href="demo.xls">
								<img src="../../../images/can_b_03.gif" border="0" />
							</a>
					</tr>
				</table>
			</div>
			<!--"square-o-top"end-->
			<div class="square-order">
				<table width="70%" border="1" cellpadding="0" cellspacing="0" style="float:left;">
					<tr align="center"
						style="background:url(images/table_bg.gif) repeat-x;">
						<td colspan="2" width="49%" height="30">商品名称</td>
						<td colspan="2" width="28%">总数量</td>
						<td width="23%">详情</td>
					</tr>
						<tr align="center" bgcolor="#FFFFFF">
							<td colspan="2" width="30%" height="30">狼皮背心</td>
							<td colspan="2">300</td>
							<td>
								<a href="javascript:void(0)" class="xiu info" value="1">
									详情
								</a>
							</td>
						</tr>
						<tr align="center" bgcolor="#FFFFFF">
							<td colspan="2" width="30%" height="30">狼皮背心</td>
							<td colspan="2">300</td>
							<td>
								<a href="javascript:void(0)" class="xiu info" value="1">
									详情
								</a>
							</td>
						</tr>
						<tr align="center" bgcolor="#FFFFFF">
							<td colspan="2" width="30%" height="30">狼皮背心</td>
							<td colspan="2">300</td>
							<td>
								<a href="javascript:void(0)" class="xiu info" value="1">
									详情
								</a>
							</td>
						</tr>
				</table>
				<div style="float:right;"> 
					<a href="demo.png">
						<img id="pei" src="demo.png" width="228px" height="180px">
					</a>
				</div>
			</div>
		</form>
	</div>
	<div class="content-bbg"></div>
</div>
<%-- end of inGoods.jsp --%>