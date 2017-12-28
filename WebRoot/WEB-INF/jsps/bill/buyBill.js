/**
 * this function is called in buyBill.jsp file
 * @returns
 */
$(function() {
		$("#query").click(function() {
			$("form:first").submit();
		});
		
		$(".info").click(function(){
			$nowTr = $(this).parent().parent();
			
			$(".ajaxMsg").remove();
			
			//将当前货物的uuid配置公共查询条件，发送ajax请求，获取对应货物的所有明细数据
			var jsonParma = {};
			jsonParma["bqm.goodsUuid"] = $(this).attr("val");
			jsonParma["bqm.begin"] = $("[name='bqm.begin']").val();
			jsonParma["bqm.end"] = $("[name='bqm.end']").val();
			jsonParma["bqm.type"] = $("[name='bqm.type']").val();
			jsonParma["bqm.supplierUuid"] = $("[name='bqm.supplierUuid']").val();
			$.post("bill_ajaxBillDetailByGoods.action",jsonParma,function(data){
				//后台发送了明细数据
				var odmList = data.odmList;
				
				//使用上述值拼写tr
					//添加表头
				$headTr = $('<tr align="center" style="background:url(images/table_bg.gif) repeat-x;" class="ajaxMsg"><td height="30">订单号</td><td>订单时间</td><td>数量</td><td>单价</td><td>合计</td></tr>') 
				$nowTr.after($headTr);
				$nowTr = $headTr;
				var sum = 0;
				for(var i = 0;i<odmList.length;i++){
					var odm = odmList[i];
					
					var orderNum = odm.om.orderNum;
					var createTimeView = odm.om.createTimeView;
					var num = odm.num;
					var priceView = odm.priceView;
				
					sum+=num*priceView;
					
					//添加数据（循环）
					$dataTr = $('<tr align="center" class="ajaxMsg"><td height="30">'+orderNum+'</td><td>'+createTimeView+'</td><td>'+num+'</td><td align="right">'+priceView+'&nbsp;元</td><td align="right">'+intToFloat(num*priceView)+'&nbsp;元</td></tr>');
					$nowTr.after($dataTr);
					$nowTr = $dataTr;
				}
				//添加表尾
				$tailTr = $('<tr align="center" class="ajaxMsg"><td height="30" align="right" colspan="4">总计：</td><td align="right">'+intToFloat(sum)+' 元</td></tr>'); 
				$nowTr.after($tailTr);
				
			});
			
			$(".ajaxMsg").live("click",function(){
				$(".ajaxMsg").remove();
			});
		});
		
		
	});
	function intToFloat(val){
		return new Number(val).toFixed(2);
	}