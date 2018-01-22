/**
 * this file is called in input.jsp
 * @returns
 */
$(function() {
	document.writeln("<script type='text/javascript' src='inputFanOut.js'></script>");

		$("#all").click(clickAction());
		$("#reverse").click(function() {
			$("[name=resources]:checkbox").each(checkedFun());

		});
	});
