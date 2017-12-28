/**
 * This function is called in input.jsp
 * @returns
 */
$(function() {
		$("#commit").click(function() {
			$("form:first").submit();
		});
});