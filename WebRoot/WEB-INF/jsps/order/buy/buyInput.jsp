<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function(){
		//修改供应商（暂定）
		$("#supplier").change(function(){
			//发送当前的供应商uuid到action
			//获取当前供应商对应的商品类别数据和商品数据
			$(".goodsType").empty();
			$(".goods").empty();
			$.post("order_ajaxGetGtmAndGm.action",{"supplierUuid":$(this).val()},function(data){
				//获取商品类别信息组织成select
				var gtmList = data.gtmList;
				for(var i = 0;i<gtmList.length;i++){
					var gtm = gtmList[i];
					$op = $("<option value='"+gtm.uuid+"'>"+gtm.name+"</option>");
					$(".goodsType").append($op);
				}
				//获取商品信息组织成select
				var gmList = data.gmList;
				for(var i = 0;i<gmList.length;i++){
					var gm = gmList[i];
					$op = $("<option value='"+gm.uuid+"'>"+gm.name+"</option>");
					$(".goods").append($op);
				}
				//更新商品价格
				var gm = data.gm;
				//修改单价
				$(".prices").val(gm.inPriceView);
				//修改合计
				$(".total").html(gm.inPriceView+" 元");
				//总计(省略)
				sum();
			});
		});
		
		//修改商品类别（暂定）
		//$(".goodsType").change(function(){
		$(".goodsType").live("change",function(){
			//由当前激活事件的select查找
			$nowTr = $(this).parent().parent();
			$goodsSelect = $nowTr.children("td:eq(1)").children("select");
			$num = $nowTr.children("td:eq(2)").children("input");
			$price = $nowTr.children("td:eq(3)").children("input");
			$total = $nowTr.children("td:eq(4)");
			//将goodsTypeUuid传递到后台
			var goodsTypeUuid = $(this).val();
			$goodsSelect.empty();
			//发送请求
			$.post("order_ajaxGetGm.action",{"goodsTypeUuid":goodsTypeUuid},function(data){
				//获取商品信息组织成select
				var gmList = data.gmList;
				for(var i = 0;i<gmList.length;i++){
					var gm = gmList[i];
					$op = $("<option value='"+gm.uuid+"'>"+gm.name+"</option>");
					$goodsSelect.append($op);
				}
				//更新商品价格
				var gm = data.gm;
				
				$num.val(1);
				//修改单价
				$price.val(gm.inPriceView);
				//修改合计
				$total.html(gm.inPriceView+" 元");
				
				//总计(省略)
				sum();
			});
		});
		
		//修改商品（暂定）
		$(".goods").live("change",function(){
			$nowTr = $(this).parent().parent();
			//$price = $nowTr.children("td:eq(3)").children("input");
			$num = $nowTr.children("td:eq(2)").children("input");
			$price = $(this).parent().next().next().children("input");
			$total = $nowTr.children("td:eq(4)");
		
			var goodsUuid = $(this).val();
			$.post("order_ajaxGetOne.action",{"goodsUuid":goodsUuid},function(data){
				//更新商品价格
				//修改数量为1
				$num.val(1);				
				//修改单价
				$price.val(data.inPriceView);
				//修改合计
				$total.html(data.inPriceView+" 元");
				
				//总计(省略)
				sum();
			});
		});
		
		//添加新商品项
		$("#add").click(function(){
			//设置之前所有的select均处于不可操作状态
			$("#supplier").attr("disabled",true);
			$(".goodsType").attr("disabled",true);
			$(".goods").attr("disabled",true);
		
			//获取已经使用过的所有商品
			//页面读取已经使用的商品uuid
			var goodsArr = $(".goods");
			var used = "";
			for(var i = 0;i<goodsArr.length;i++){
				used += goodsArr[i].value;
				used += ",";
			}
			
			//发送ajax提交(暂定)
			var supplierUuid = $("#supplier").val();
			$.post("order_ajaxGetGtmAndGm2.action",{"supplierUuid":supplierUuid,"used":used},function(data){
				//data.gtmList	uuid name
				var gtmList = data.gtmList;
				//data.gmList		uuid name
				var gmList = data.gmList;
				
				$tr = $('<tr align="center" bgcolor="#FFFFFF"></tr>');
				
				$td1 = $("<td height='30px'></td>");
				$selectGtm = $("<select class='goodsType' style='width:200px'></select>");
				for(var i = 0;i<gtmList.length;i++){
					var gtm = gtmList[i];
					$op = $("<option value='"+gtm.uuid+"'>"+gtm.name+"</option>")
					$selectGtm.append($op);
				}
				$td1.append($selectGtm);
				$tr.append($td1);
				
				$td2 = $("<td></td>");
				$selectGm = $("<select name='goodsUuids' class='goods' style='width:200px'></select>");
				for(var i = 0;i<gmList.length;i++){
					var gm = gmList[i];
					$op = $("<option value='"+gm.uuid+"'>"+gm.name+"</option>")
					$selectGm.append($op);
				}
				$td2.append($selectGm);
				$tr.append($td2);
				
				$td3 = $('<td><input name="nums" class="num order_num" style="width:67px;border:1px solid black;text-align:right;padding:2px" type="text" value="1"/></td>');
				$tr.append($td3);
				
				$td4 = $('<td><input name="prices" class="prices order_num" style="width:93px;border:1px solid black;text-align:right;padding:2px" type="text" value="'+data.gm.inPriceView+'"/> 元</td>');
				$tr.append($td4);
				
				$td5 = $('<td class="total" align="right">'+data.gm.inPriceView+'&nbsp;元</td>');
				$tr.append($td5);
				
				$td6 = $('<td><a class="deleteBtn delete xiu" value="4"><img src="images/icon_04.gif" />删除</a></td>');
				$tr.append($td6);
				
				$("#finalTr").before($tr);
				
				//判断是否还有商品可用，如果没有，设置添加按钮隐藏起来
				if(gtmList.length == 1 && gmList.length == 1){
					//隐藏按钮
					$("#add").css("display","none");
				}
				sum();
			});
		});
		
		//删除
		$(".deleteBtn").live("click",function(){
			//0.如果只剩余一个就不让删除，只剩余一个时，不执行下面的操作
			if( $(".deleteBtn").length == 1){
				return;
			}
			
			$(".goodsType").attr("disabled",true);
			$(".goods").attr("disabled",true);
			
			//1.获取当前行对象
			$nowTr = $(this).parent().parent();
			//2.删除行对象
			//表格删除行
			//行自己删除
			$nowTr.remove();
			//3.设置添加按钮出现
			$("#add").css("display","inline");
			sum();
		});
		
		//修改订单项数量
		$(".num").live("keyup",function(event){
			//1.设置键盘不能点击数字之外的按键
			//event.code
			//2.使用过滤————正则
			$(this).val($(this).val().replace(/[^\d]/g,""));
			//获取数量
			var num = $(this).val();
			//获取价格
			var price = $(this).parent().parent().children("td:eq(3)").children("input").val();
			//计算合计
			var total = num * price ;
			//修改显示合计
			$(this).parent().parent().children("td:eq(4)").html(intToFloat(total)+" 元");
			//修改总计
			sum();
		});
		
		//修改订单项数量
		$(".prices").live("keyup",function(event){
			//先把非数字的都替换掉，除了数字和. 
			$(this).val($(this).val().replace(/[^\d.]/g,""));
	        //必须保证第一个为数字而不是. 
	        $(this).val($(this).val().replace(/^\./g,"0."));
	        //保证只有出现一个.而没有多个. 
	        $(this).val($(this).val().replace(/\.{2,}/g,"."));
	        //保证.只出现一次，而不能出现两次以上
	        $(this).val($(this).val().replace(".","$#$").replace(/\./g,"").replace("$#$",".")); 
	        
			//获取数量
			var num = $(this).parent().parent().children("td:eq(2)").children("input").val();
			//获取价格
			var price = $(this).val();
			//计算合计
			var total = num * price ;
			//修改显示合计
			$(this).parent().parent().children("td:eq(4)").html(intToFloat(total)+" 元");
			//修改总计
			sum();
		});
		//求总计
		function sum(){
			//1.获取所有购买的数量
			var numArr = $(".num");
			//2.获取所有购买的价格
			var priceArr = $(".prices");
			var sums = 0;
			//4.合计求和
			for(var i = 0;i<numArr.length;i++){
				//3.计算合计
				sums += numArr[i].value*priceArr[i].value;
			}
			//5.设置总计值
			$(".all").html(intToFloat(sums)+" 元");
		}
		
		$("#submitOrder").click(function(){
			//恢复被提交的不可用的select
			$("#supplier").attr("disabled",false);
			$(".goods").attr("disabled",false);
			//提交form
			$("form:first").submit();
		});		
	});
	
	function intToFloat(val){
		return new Number(val).toFixed(2);
	}
</script>
<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">订单管理</span>
		</div>
	</div>
	<div class="content-text">
		<s:form action="order_buyOrder.action" method="post">
			<div class="square-o-top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					style="font-size:14px; font-weight:bold; font-family:"黑体";">
					<tr>
						<td width="68px" height="30">供应商：</td>
						<td width="648px">
							<s:select name="om.sm.uuid" id="supplier" list="supplierList" listKey="uuid" listValue="name" cssStyle="width:190px"></s:select>
						</td>
						<td height="30">
							<a id="add"><img src="images/can_b_02.gif" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<!--"square-o-top"end-->
			<div class="square-order">
				<table id="order" width="100%" border="1" cellpadding="0" cellspacing="0">
					<tr align="center"
						style="background:url(images/table_bg.gif) repeat-x;">
						<td width="25%" height="30">商品类别</td>
						<td width="25%">商品名称</td>
						<td width="10%">采购数量</td>
						<td width="15%">单价</td>
						<td width="15%">合计</td>
						<td width="10%">操作</td>
					</tr>
					<tr align="center" bgcolor="#FFFFFF">
						<td height="30">
							<s:select cssClass="goodsType" list="gtmList" listKey="uuid" listValue="name" cssStyle="width:200px"></s:select>
						</td>
						<td>
							<s:select name="goodsUuids" cssClass="goods" list="gmList" listKey="uuid" listValue="name" cssStyle="width:200px"></s:select>
						</td>
						<td><input name="nums" class="num order_num" style="width:67px;border:1px solid black;text-align:right;padding:2px" type="text" value="1"/></td>
						<td><input name="prices" class="prices order_num" style="width:93px;border:1px solid black;text-align:right;padding:2px" type="text" value="${gmList.get(0).inPriceView}"/> 元</td>
						<td class="total" align="right">${gmList.get(0).inPriceView}&nbsp;元</td>
						<td>
							<a class="deleteBtn delete xiu" value="4"><img src="images/icon_04.gif" />删除</a>
						</td>
					</tr>
					<tr id="finalTr" align="center"
						style="background:url(images/table_bg.gif) repeat-x;">
						<td height="30" colspan="4" align="right">总&nbsp;计:&nbsp;</td>
						<td class="all" width="16%" align="right">${gmList.get(0).inPriceView}&nbsp;元</td>
						<td>&nbsp;</td>
					</tr>
				</table>
				<div class="order-botton">
				<div style="margin:1px auto auto 1px;">
					<table width="100%"  border="0" cellpadding="0" cellspacing="0">
					  <tr>
					    <td>
					    	<a href="javascript:void(0)" id="submitOrder"><img src="images/order_tuo.gif" border="0" /></a>
					    </td>
					    <td>&nbsp;</td>
					    <td><a href="#"><img src="images/order_tuo.gif" border="0" /></a></td>
					    <td>&nbsp;</td>
					    <td><a href="#"><img src="images/order_tuo.gif" border="0" /></a></td>
					  </tr>
					</table>
				</div>
			</div>
			</div>
		</s:form>
	</div>
	
	<div class="content-bbg"></div>
</div>
