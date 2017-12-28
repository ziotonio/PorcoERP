/**
 * this function is called in paging.jsp file
 * @returns
 */
$(function() {
	var pageCount = $pageCount;
	var pageNum = $pageNum;
		if(pageCount == 1){
			$("#fir").css("display","none");
			$("#pre").css("display","none");
			$("#next").css("display","none");
			$("#last").css("display","none");
		}else if(pageNum == 1){
			$("#fir").css("display","none");
			$("#pre").css("display","none");
			$("#next").css("display","inline");
			$("#last").css("display","inline");
		}else if(pageNum == pageCount){
			$("#fir").css("display","inline");
			$("#pre").css("display","inline");
			$("#next").css("display","none");
			$("#last").css("display","none");
		}else{
			$("#fir").css("display","inline");
			$("#pre").css("display","inline");
			$("#next").css("display","inline");
			$("#last").css("display","inline");
		}
		$("#fir").click(function(){
			$("[name='pageNum']").val(1);
			$("form:first").submit();
		});
		$("#pre").click(function(){
			$("[name='pageNum']").val($("[name='pageNum']").val()-1);
			$("form:first").submit();
		});
		$("#next").click(function(){
			$("[name='pageNum']").val($("[name='pageNum']").val()*1+1);
			$("form:first").submit();
		});
		$("#last").click(function(){
			$("[name='pageNum']").val(pageCount);
			$("form:first").submit();
		});

});