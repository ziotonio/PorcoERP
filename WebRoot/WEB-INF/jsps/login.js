/**
 * this file is called in outer login.jsp
 * @param srcObj
 * @param image_src
 * @returns
 */
function MM_swapImage(srcObj,image_src){
		srcObj.src=image_src;
	}
	
	$(function(){
		//为login_ok绑定点击事件
		$("#login_ok").click(function(){
			//提交form
			$("form:first").submit();
		});
		
	});