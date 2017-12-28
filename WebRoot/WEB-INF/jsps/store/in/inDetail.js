/**
 * this file is called in inDetail.jsp
 */
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