/**
 * this function is called in taskDetail.jsp file
 * @returns
 */
$(function() {
		$("#task").click(function() {
			$("form:first").submit();
		});
});