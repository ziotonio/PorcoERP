/**
 * this function is called in input.jps
 * @returns
 */
$(function() {
	document.writeln("<script type='text/javascript' src='inputFanOut.js'></script>");

		$("#all").click(function() {
			$("[name=roles]:checkbox").attr("checked",$("#all").attr("checked")=="checked");
		});
		$("#reverse").click(checkBox());
});
