/**
 * this file is called in input.js
 * @returns
 */
function checkedFun(){
				//将状态切换
				$(this).attr("checked",$(this).attr("checked")!="checked");
}

function checked2(){
	//全选
	//将所有的全部选中
	$("[name='resUuids']").attr("checked",$(this).attr("checked")=="checked");
}
