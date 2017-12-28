/**
 * this file is called in input.jsp
 * @returns
 */
$(function(){
		//为供应商添加change事件
		$("#supplier").change(function(){
			//将供应商的uuid传递到后台(ajax)，获取商品类别数据
			//格式：$.post(URL,data,callback);
			//第一个参数：请求的url
			//第二个参数：请求的参数json格式
			var supplierUuid = $(this).val();
			//goodsType_ajaxGetGtmBySupplier.action?supplierUuid=val
			//第三个参数：回调方法，请求执行完毕后，做进行的任务,是一个js的函数function
			
			//修改$("#goodsType")select中的内容，必须先清空其中原始的内容 
			$("#goodsType").empty();
			$.post("goodsType_ajaxGetGtmBySupplier.action",{"supplierUuid":$(this).val()},function(data){
				//请求完毕后完成的工作，需要获取json数据
				//如何获取json数据?
				//获取到json数据后，需要将其转换为select中的数据
				var gtmList = data.gtmList;
				for(var i = 0;i<gtmList.length;i++){
					var gtm = gtmList[i];
					//alert(gtm.uuid+","+gtm.name);
					//将json数据中的每个项转换成select中的option选项
					var $op = $("<option value='"+gtm.uuid+"'>"+gtm.name+"</option>");
					//然后添加到select中
					$("#goodsType").append($op);
				}
			});
		});
	
	});