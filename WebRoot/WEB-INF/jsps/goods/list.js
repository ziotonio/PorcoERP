/**
 * this file is called in list.jsp
 * @returns
 */
$(function() {
		$("#query").click(function() {
			$("[name='pageNum']").val(1);
			$("form:first").submit();
		});
		
		$(".unit").click(function(){
			//将选中的内容作为查询条件出现
			$("[name='gqm.unit']").val($(this).text());
			$("[name='pageNum']").val(1);
			$("form:first").submit();
		});
		
	});
	function showMsg(msg,uuid){
		//top.document.getElementById("context-msg").style.display = "block";
		top.$('context-msg').style.display = "block";
		top.$('context-msg-text').innerHTML=msg;
		top.$('hid-action').value="actionName";
		top.lock.show();
	}/**
	 * this file is called in list.jsp
	 * @returns
	 */
	$(function() {
			$("#query").click(function() {
				$("[name='pageNum']").val(1);
				$("form:first").submit();
			});
			
			$(".unit").click(function(){
				//将选中的内容作为查询条件出现
				$("[name='gqm.unit']").val($(this).text());
				$("[name='pageNum']").val(1);
				$("form:first").submit();
			});
			
		});
		function showMsg(msg,uuid){
			//top.document.getElementById("context-msg").style.display = "block";
			top.$('context-msg').style.display = "block";
			top.$('context-msg-text').innerHTML=msg;
			top.$('hid-action').value="actionName";
			top.lock.show();
		}