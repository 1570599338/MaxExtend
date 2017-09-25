$(function(){
	getdata();
});

//获取数据及查询
function getdata(){
	var ctxPath = $("#ctxPath").val();
	var fileName  = $('#fileNamex').val();
	var modelId = $("#modelId").val();
	var typeId = $("#typeId").val();
	//sayInfo(filetype);
	//return;
	 // 通过路径查询项目的数据列表
	$('#dg').datagrid({
						rownumbers:true,
						singleSelect:true,
						autoRowHeight:false,
						resizable:true,
						pagination:true,
						fit:true,
						pageSize:10,
						pageNumber:1,
						//url : ctxPath + encodeURI(encodeURI('/notice/selectNoticeList?title='+title)),
						url : ctxPath + encodeURI(encodeURI('/upload/selectfileList')),
						idField : 'pk_id',
						queryParams:{
							fileName:fileName,
							modelId:modelId,
							typeId:typeId
						},
						columns : [ 
						            [ 
						              	{ field : 'modelName', title : '文件模型', width : 80, sortable : true}, 
										{ field : 'typeName',title : '文件类型',width : 100}, 
										{ field : 'fileName',title : '文件名',width : 100}, 
										{field : 'createTime',title : '时间',width : 180},
										{field : 'createBy',title : '上传者',width : 80}
									] 
						          ],
						          onClickRow: function(node){
							  	    	var sel=$('#dg').datagrid('getSelected');
							  	    	$.messager.confirm("提示","确定要下载：<font color=red>"+sel.fileName+"</font> ?",function(data){
							  	    		if(data){
							  	    			serchProject();
							  	    		}
							  	    	});
							  		}
			});
}

function serchProject() {
	var node = $('#dg').datagrid('getSelected');
	if (node) {
		changeDialog(node);
	} else {
		sayInfo('请先选择目标项目！');
	}
}

function changeDialog(node){
	
	var ctxPath = $("#ctxPath").val();
	var menu = node;
	if (null == menu || menu == '') {
		sayInfo("请选择要查看的文件选项。");
		return;
	} else {
		$("#fileAllName").val(menu.fileAllName);
		$("#dataForm").attr("action",$("#ctxPath").val() + '/upload/downfile');
		$("#dataForm").submit();
	}
}


//下载文档
function downFile(ctxPath){
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要查看的文件选项。");
		return;
	} else {
		$("#fileAllName").val(menu.fileAllName);
		$("#dataForm").attr("action",$("#ctxPath").val() + '/upload/downfile');
		$("#dataForm").submit();
	}
}
