/**
 * this file is called in list.jsp
 * @returns
 */
$(function() {
		$("#query").click(function() {
			$("[name='pageNum']").val(1);
			$("form:first").submit();
		});
});