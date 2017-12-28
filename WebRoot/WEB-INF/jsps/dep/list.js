/**
 * this file is called in list.jsp file
 * @returns
 */
$(function() {
		$("#query").click(function() {
			$("[name=pageNum]").val(1);
			$("form:first").submit();
		});
	});
	/*
	function showMsg(msg,uuid){
		//top.document.getElementById("context-msg").style.display = "block";
		top.$('context-msg').style.display = "block";
		top.$('context-msg-text').innerHTML=msg;
		top.$('hid-action').value="actionName";
		top.lock.show();
	}
	*/
	function showMsg(msg,uuid){
		//打开遮罩层
		top.lock.show();
		top.$("context-msg").style.display="block";
		top.$("context-msg-text").innerHTML = msg;
		//修改删除操作确定按钮的事件
		top.$("hid-action").value = "dep_delete.action?dm.uuid="+uuid;
}