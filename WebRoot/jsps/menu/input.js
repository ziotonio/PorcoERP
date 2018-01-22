/**
 * function called in input.jsp file
 * @returns
 */
$(function() {
	document.writeln("<script type='text/javascript' src='inputFanOut.js'></script>");

		$("#all").click(function() {
			$("[name=resources]:checkbox").attr("checked",$("#all").attr("checked")=="checked");
		});
		$("#reverse").click(checkBox());
});
