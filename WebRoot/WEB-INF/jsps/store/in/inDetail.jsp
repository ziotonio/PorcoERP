<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	
	//将storeList转化为两个数组
	
	var uuids = new Array();
	var names = new Array();
	var i = 0;
	<s:iterator value="storeList">
		uuids[i] = ${uuid};
		names[i] = "${name}";
		i++;
	</s:iterator>
	
	$(function(){
		$(".oper").click(function(){
			//创建行对象，添加到当前行下方
			$nowTr = $(this).parent().parent();
			
			var odmUuid = $nowTr.attr("odm");
			
			$(".in").remove();
			
			$.post("order_ajaxGetSurplusByOdmUuid.action",{"odmUuid":odmUuid},function(data){
				$tr = $("<tr class='in'></tr>");			
			
				$td1 = $("<td height='30px' align='right'>仓库：</td>");
				$tr.append($td1);

				$td2 = $("<td></td>");
				$select = $("<select></select>");
				for(var i = 0;i<uuids.length;i++){
					$op = $("<option value="+uuids[i]+">"+names[i]+"</option>");
					$select.append($op);
				}
				$td2.append($select);
				$tr.append($td2);
			
				$td3 = $("<td align='right'>入库量：</td>");
				$tr.append($td3);
			
				var surplus = data.surplus;
				var num = data.num;
			
				$td4 = $("<td><input type='text' value='"+surplus+"'/></td>");
				$tr.append($td4);
			
				$td5 = $('<td align="center"><img src="images/icon_3.gif" /><a href="javascript:void(0)" class="btn_ok xiu">确定</a></td>');
				$tr.append($td5);
			
				$nowTr.after($tr);
			});
		});
		
		//确定按钮
		$(".btn_ok").live("click",function(){
			//真实的入库操作
			//传递到后台：哪个商品？入到哪个仓库？入多少个？
			$nowTr = $(this).parent().parent();
			
			var jsonParam = {};
			jsonParam["goodsUuid"] = $nowTr.prev().attr("gm");
			jsonParam["storeUuid"] = $nowTr.children("td:eq(1)").children("select").val();
			jsonParam["num"] = $nowTr.children("td:eq(3)").children("input").val();
			jsonParam["odmUuid"] = $nowTr.prev().attr("odm");
			$.post("store_ajaxInGoods.action",jsonParam,function(data){
				//if(msg){
					//输入的东西有问题
					//找个合适的地方提示用户
				//}
				var	surplus = data.odm.surplus;
    			var num = data.odm.num;
    			var has = data.has
    			//获取该订单是否全部操作完毕
    			if(has){
					//已经全部入库完毕
					$("#inOrderTitle").remove();
					$("#inOrder").remove();
					$("#allInTitle").css("display","block");
					$("#return").css("display","block");
    			}
    			
    			if(surplus == 0){
    				//删除两个行对象
    				$nowTr.prev().remove();
    				$nowTr.remove();
    			}else{
		  			//设置剩余量
		  			$nowTr.prev().children("td:eq(3)").html(surplus);
		  			//设置值已入库数量
		  			$nowTr.prev().children("td:eq(2)").html(num-surplus);
		  			//设置下次入库数量
		  			$nowTr.children("td:eq(3)").children("input").val(surplus);
		  		}
			});
		});
	});
</script>
<div class="content-right">
	<div class="content-r-pic_w">
		<div style="margin:8px auto auto 12px;margin-top:6px">
			<span class="page_title">入库</span>
		</div>
	</div>
	<div class="content-text">
			<div class="square-o-top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					style="font-size:14px; font-weight:bold; font-family:"黑体";">
					<tr>
						<td>订 单 号:</td>
						<td class="order_show_msg">${om.orderNum}</td>
						<td>商品总量:</td>
						<td class="order_show_msg">${om.totalNum}</td>
					</tr>
				</table>
			</div>
			<!--"square-o-top"end-->
			<div class="square-order">
				<center id="inOrderTitle" style="text-decoration:underline;font-size:16px; font-weight:bold; font-family:"黑体";">&nbsp;&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;据&nbsp;&nbsp;明&nbsp;&nbsp;细&nbsp;&nbsp;&nbsp;&nbsp;</center>
				<br/>
				<table id="inOrder" width="100%" border="1" cellpadding="0" cellspacing="0">
					<tr align="center"
						style="background:url(images/table_bg.gif) repeat-x;">
						<td width="20%" height="30">商品名称</td>
						<td width="30%">总数量</td>
						<td width="10%">已入库数量</td>
						<td width="30%">剩余数量</td>
						<td width="10%">入库</td>
					</tr>
						<s:iterator value="om.odms">
							<s:if test="surplus>0">
								<tr odm="${uuid}" gm="${gm.uuid}" aa="bb" align="center" bgcolor="#FFFFFF">
									<s:hidden value="%{uuid}"/>
									<td height="30">${gm.name}</td>
									<td>${num}</td>
									<td>${num-surplus}</td>
									<td>${surplus}</td>
									<td><a href="javascript:void(0)" class="oper xiu"><img src="images/icon_3.gif" />入库</a></td>
							</tr>
							</s:if>
						</s:iterator>
				</table>
				
				<center id="allInTitle" style="display:none;font-size:16px; font-weight:bold; font-family:"黑体";">&nbsp;&nbsp;&nbsp;&nbsp;全&nbsp;&nbsp;部&nbsp;&nbsp;入&nbsp;&nbsp;库&nbsp;&nbsp;&nbsp;&nbsp;</center>
				<table id="return" style="display:none" >
					<tr>
						<td>&nbsp;</td>
						<td width="100%" align="center">
							<a href="order_inGoodsList.action" style="color:#f00;font-size:20px;padding-top:2px;font-weight:bold;text-decoration:none;width:82px;height:28px;display:block;background:url(images/btn_bg.jpg)">
								返回
							</a>
						</td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>
	</div>
	<div class="content-bbg"></div>
</div>
