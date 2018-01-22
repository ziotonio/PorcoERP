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
	document.writeln("<script type='text/javascript' src='inputFanOut.js'></script>");

		
		$("#all").click(checked2());
		$("#reverse").click(function(){

			//反选
			//将所有选项的状态进行切换
			$("[name='resUuids']").each(checkedFun());
		});
		
	});
