/**
 * this file is called in input.jsp
 * @returns
 */
$(function() {
		/*
		$("#all").click(function() {
			$("[name=resources]:checkbox").attr("checked",$("#all").attr("checked")=="checked");
		});
		$("#reverse").click(function() {
			$("[name=resources]:checkbox").each(function () {
                $(this).attr("checked", !$(this).attr("checked"));
            });
		});
		*/
		
		$("#all").click(function(){
			//全选
			//将所有的全部选中
			$("[name='resUuids']").attr("checked",$(this).attr("checked")=="checked");
		});
		$("#reverse").click(function(){
			//反选
			//将所有选项的状态进行切换
			$("[name='resUuids']").each(function(){
				//将状态切换
				$(this).attr("checked",$(this).attr("checked")!="checked");
			});
		});
		
	});