<%-- mask.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
#pageOverlay {
	visibility: hidden;
	position: fixed;
	top: 0;
	left: 0;
	z-index: 1987;
	width: 100%;
	height: 100%;
	background: #ccc;
	filter: alpha(opacity = 70);
	opacity: 0.7;
}
/*IE6 fixed*/
* html {
	background: url(*) fixed;
}
* html body {
	margin: 0;
	height: 100%;
}
* html #pageOverlay {
	position: absolute;
	left: expression(documentElement.scrollLeft +   documentElement.clientWidth -
		  this.offsetWidth);
	top: expression(documentElement.scrollTop +   documentElement.clientHeight -
		  this.offsetHeight);
}
.content-msg-size{
	width:300px;
}
.content-msg-sizeh{
	height:180px;
}
.content-msg-top{
	background:url("images/content_tmsg.jpg") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
}
.content-msg-body{
	width:298px;
}
.content-msg-bottom{
	background:url("images/content_bmsg.jpg") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
}
.context-msg{
	width:300px;
	height:200px;
	position: absolute; 
	left:40%; 
	top:30%;
	z-index:2000; 
	display:none;
}
.context-msg-txt{
	width:262px;
	height:122px;
	margin-left:18px;
	margin-right:18px;
	margin-top:15px;
	font-size:18px;
	font-family:'黑体';
	color:#202677;
}
.context-msg-btn{
	width:262px;
	height:40px;
	margin-left:18px;
	margin-right:18px;
}
</style>
<%-- Here starts the javascript call function --%>
<script type="text/javascript" src="mask.js">
</script>

<div id="pageOverlay"></div>
</div>
<div id="context-msg" class="context-msg" id="context-msg">
	<div class="content-r-pic content-msg-size content-msg-top">
	</div>
	<div class="content-text content-msg-size content-msg-sizeh content-msg-body">
		<div id="context-msg-text" class="context-msg-txt">
			临时消息文字内容，测试专用，看看宽度临时消息文字内容，测试专用，看看宽度临时消息文字内容，测试专用，看看宽度
		</div>
		<input id="hid-action" type="hidden" value=""/>
		<div class="context-msg-btn">
			<center>
			<input id="btn_ok" type="image" src="../images/content_msg_btn_ok.jpg"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="btn_cancel" type="image" src="../images/content_msg_btn_cancel.jpg"/>
			</center>
		</div>
	</div>
	<div class="content-bbg content-msg-size content-msg-bottom"></div>
</div>
<%-- end of mask.jsp --%>