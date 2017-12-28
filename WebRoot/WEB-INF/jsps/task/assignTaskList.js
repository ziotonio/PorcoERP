/**
 * this file is called in assignTaskList.jsp
 * @returns
 */
$(function() {
		$("#query").click(function() {
			$("[name='pageNum']").val(1);
			$("form:first").submit();
		});
	});