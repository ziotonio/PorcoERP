<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="51%">&nbsp;</td>
		<td width="13%">共${dataTotal}条记录
		<td width="6%">
			<a id="fir" class="sye">首&nbsp;&nbsp;页</a>
		</td>
		<td width="6%">
			<a id="pre" class="sye">上一页</a>
		</td>
		<td width="6%">
			<a id="next" class="sye">下一页</a>
		</td>
		<td width="6%">
			<a id="last" class="sye">末&nbsp;&nbsp;页</a>
		</td>
		<td width="12%">当前第<span style="color:red;">${pageNum}</span>/${maxPageNum}页</td>
	</tr>
</table>
<s:hidden name="pageNum"/>
<script type="text/javascript">
	$(function(){
		var maxPageNum = ${maxPageNum};
		var pageNum = ${pageNum};
		//控制分页控件的显示
		//最大页码值=1：都不显示
		//第一页：显示后2
		//最后一页:显示前2
		//其他：全部显示
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
			//将页码值传递到后台
			//查询对应的数据
			//alert(parseInt($("[name=pageNum]").val())+1);
			//alert($("[name=pageNum]").val()*1+1);
			$("[name=pageNum]").val($("[name=pageNum]").val()*1+1);
			//页面上展示
			$("form:first").submit();
		});
		$("#last").click(function(){
			$("[name=pageNum]").val(maxPageNum);
			$("form:first").submit();
		});
	});
</script>