/**
 * this function is called inDetail.jsp file
 * @returns
 */
$(function() {
		//初始化仓库数据
		var storeUuidArr = new Array();
		var storeNameArr = new Array();
			
			storeUuidArr[0] = 11;
			storeNameArr[0] = "1号仓库";
			storeUuidArr[1] = 22;
			storeNameArr[1] = "2号仓库";
			storeUuidArr[2] = 33;
			storeNameArr[2] = "3号仓库";
		var omUuid = 123;
		
		$(".oper").click(function() {
			var $myTr = $(this).parent().parent();
			var $nextTr = $myTr.next();
			if($nextTr.attr("class") == "in"){
				return;
			}
			if($(".in").length>0){
				$(".in").remove();
			}
			var $newTr = $("<tr class='in'></tr>");
			var $td1 = $("<td align='right'>仓库：</td>");
			$newTr.append($td1);	
				var storeSelectStr = "<select style='width:200px'>";
				for(var i = 0;i<storeUuidArr.length;i++){
					storeSelectStr+="<option value='";
					storeSelectStr+=storeUuidArr[i];
					storeSelectStr+="'>";
					storeSelectStr+=storeNameArr[i];
					storeSelectStr+="</option>";
				}
				storeSelectStr += "</select>";
			var $td2 = $("<td height='30'>"+storeSelectStr+"</td>");
			$newTr.append($td2);	
			//2.3入库多少
			var $td3 = $("<td align='right'>入库量：</td>");
			$newTr.append($td3);	
			//获取当前入库数据总量
			var totalNum = $(this).parent().prev().text();
			var $td4 = $("<td><input id='inNum' type='text' value='"+totalNum+"'/></td>");
			$newTr.append($td4);	
			var $td5 = $("<td align='center'><a href='javascript:void(0)' class='ajaxIn xiu'><img src='../../../images/icon_3.gif' />确定</a></td>");
			$newTr.append($td5);
			//3.将新的行对象添加到当前按钮所在的行对象后面
			$myTr.after($newTr);
		});
		$(".ajaxIn").live("click",function(){
			//0.页面校验输入是否合法（省略）
			//1.组织ajax提交的数据
			jsonParam ={};
			//主单编号
			jsonParam["odm.order.uuid"] = omUuid;
			//获取当前链接所在行的上一行中隐藏的第一个子节点和第二个子节点的值
			//子单编号
			jsonParam["odm.uuid"] = $(this).parent().parent().prev().children("input:eq(0)").val();
			//货物编号
			jsonParam["odm.goods.uuid"] = $(this).parent().parent().prev().children("input:eq(1)").val();
			//入库数量
			jsonParam["som.num"] = $(this).parent().prev().children("input:eq(0)").val();
			//仓库编号
			jsonParam["som.sdm.sm.uuid"] = $(this).parent().parent().children("td:eq(1)").children("select").val();
			
			//为ajax提交操作后的操作对象进行初始化
			var $upTr = $(this).parent().parent().prev();
			var $upCenter =  $upTr.children("td:eq(2)");
			var $upRight = $upTr.children("td:eq(3)");
			var $myTr = $(this).parent().parent();
				if(false){
					//显示返回按钮
					$("#inOrderTitle").remove();
					$("#inOrder").remove();
					$("#allInTitle").css("display","block");
					$("#return").css("display","block");
					return;
				}
				
				if(0 == 0){
					$upTr.remove();
					$myTr.remove();
				}else{
					$upCenter.text(20);
					$upRight.text(10);	
				}
			});
			
	});