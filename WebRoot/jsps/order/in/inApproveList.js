/**
 * this function is called in inApproveList.jsp file
 * @returns
 */
$(function() {
		$("#query").click(function() {
			$("[name='pageNum']").val(1);
			$("form:first").submit();
		});
});