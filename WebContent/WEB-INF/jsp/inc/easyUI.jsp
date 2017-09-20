<%
String message = request.getAttribute("message") != null ? request.getAttribute("message").toString() : "";


%>

<!--easyui 加载文件   start-->
<link rel="stylesheet" type="text/css" href="${ctxPath}/js/easyui/themes/bootstrap/easyui.css" id="swicth-style">
<link rel="stylesheet" type="text/css" href="${ctxPath}/js/easyui/body.css">
<%-- <link rel="stylesheet" type="text/css" href="${ctxPath}/js/easyui/demo.css"> --%>
<link rel="stylesheet" type="text/css" href="${ctxPath}/js/easyui/themes/icon.css">
<script type="text/javascript" src="${ctxPath}/js/easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctxPath}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctxPath}/js/easyui/easyui-lang-zh_CN.js"></script>
<!--easyui 加载文件   end-->
<!-- kindeditor 加载文件   start -->
<link rel="stylesheet" href="${ctxPath}/js/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="${ctxPath}/js/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="${ctxPath}/js/kindeditor/lang/zh_CN.js"></script>
<!-- kindeditor 加载文件   end-->

<script type="text/javascript" > 
$(function(){
	sayMessage();
});
function sayMessage(){
	var mess = '<%=message%>';
	if(''!=mess){
		sayInfo(mess)
	}
}

function sayInfo(mess){
	$.messager.show({
		title:'Tip',
		msg:mess,
		height:160,
		width:410,
		showType:'show'
	});
}

function tipVanish(){
	$('#tip').css('display','none');
}

</script>