function uppage(pk_id){
	var ctxPath = $('#ctxPath').val();
	$("#activeID").val(pk_id);
	$("#dataManage").attr("action",ctxPath + "/manage/seeManage");
	$("#dataManage").submit();
}