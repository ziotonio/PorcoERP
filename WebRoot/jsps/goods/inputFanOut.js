/**
 * this file is called in input.js
 * @returns
 */
function goodsFun(){
			$.post("../../goodsTypeAction_getAll.action",{"gm.supplier.uuid":$(this).val()},function(data){
				$("#goodsType").empty();
				for(var i = 0;i<data.gtList.length;i++){
					var goodsType = data.gtList[i];
					var $option = $("<option value='"+goodsType.uuid+"'>"+goodsType.goodsTypeName+"</option>");	//创建option对象(jQuery格式)
					$("#goodsType").append($option);				//将option对象添加到select组件中
				}
			});
		}

function checkBox() {
	$("[name=roles]:checkbox").attr("checked",$("#all").attr("checked")=="checked");
}
