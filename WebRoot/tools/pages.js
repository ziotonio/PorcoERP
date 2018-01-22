/**
 * this file is called in pages.jsp
 * @returns
 */
$(function(){
		var maxPageNum = $maxPageNum;
		var pageNum = $pageNum;

		if(maxPageNum == 1){
			$("#fir").css("display","none");
			$("#pre").css("display","none");
			$("#next").css("display","none");
			$("#last").css("display","none");
		}else if(pageNum == 1){
			$("#fir").css("display","none");
			$("#pre").css("display","none");
			$("#next").css("display","inline");
			$("#last").css("display","inline");
		}else if(pageNum == maxPageNum){
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
			$("[name=pageNum]").val(1);
			$("form:first").submit();
		});
		$("#pre").click(function(){
			$("[name=pageNum]").val($("[name=pageNum]").val()-1);
			$("form:first").submit();
		});
		$("#next").click(function(){

			$("[name=pageNum]").val($("[name=pageNum]").val()*1+1);
			$("form:first").submit();
		});
		$("#last").click(function(){
			$("[name=pageNum]").val(maxPageNum);
			$("form:first").submit();
		});
	});
