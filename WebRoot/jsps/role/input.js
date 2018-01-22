/**
 * this function is called in input.js file
 * @returns
 */
$(function() {
	document.writeln("<script type='text/javascript' src='inputFanOut.js'></script>");

		$("#all").click(checkBox());
		$("#reverse").click(function() {
			$("[name=resources]:checkbox").each(checkedFun());

		});
});
