/**
 * this file is called in login.jsp
 * @returns
 */
$(function() {
		$("#login_ok").click(function() {
			$("form:first").submit();
		});
	});
	function MM_swapImage(srcObj,image_src){
		srcObj.src=image_src;
	}
