/**
 * this file is called in input.jsp
 * @returns
 */
$(function() {
	document.writeln("<script type='text/javascript' src='empFanOut.js'></script>");
	reverseClick();

		$("#all").click(function() {
			$("[name=roles]:checkbox").attr("checked",$("#all").attr("checked")=="checked");
		});
});
