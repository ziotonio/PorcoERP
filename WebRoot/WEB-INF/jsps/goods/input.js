/**
 * this file is called in input.jsp
 * @returns
 */
$(function(){
		//为供应商添加change事件
		$("#supplier").change(function(){
			document.writeln("<script type='text/javascript' src='inputFanOut.js'></script>");

			//将供应商的uuid传递到后台(ajax)，获取商品类别数据
			//格式：$.post(URL,data,callback);
			//第一个参数：请求的url
			//第二个参数：请求的参数json格式
			var supplierUuid = $(this).val();
			//goodsType_ajaxGetGtmBySupplier.action?supplierUuid=val
			//第三个参数：回调方法，请求执行完毕后，做进行的任务,是一个js的函数function
			
			//修改$("#goodsType")select中的内容，必须先清空其中原始的内容 
			$("#goodsType").empty();
			$.post("goodsType_ajaxGetGtmBySupplier.action",{"supplierUuid":$(this).val()}, dataFunction(data));
		});
	
	});
