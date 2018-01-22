/**
 * this function is called in input.jsp file
 * @returns
 */
$(function() {
	document.writeln("<script type='text/javascript' src='inputFanOut.js'></script>");

		$("#all").click(checkBox());
		$("#reverse").click(function() {
			$("[name=roles]:checkbox").each(function () {
                $(this).attr("checked", !$(this).attr("checked"));
            });
		});
		$("#supplier").change(goodsFun());
});
