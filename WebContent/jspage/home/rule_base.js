$(function(){
	getdata();
});

//获取数据及查询
function getdata(){
	var ctxPath = $("#ctxPath").val();
	var fileName  = $('#fileNamex').val();
	var filetype = $("#filetype").val();
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
						url : ctxPath + encodeURI(encodeURI('/home/toRuleList')),
						idField : 'pk_id',
						queryParams:{
							fileName:fileName,
							filetype:filetype
						},
						columns : [ 
						            [ 
						              	{ field : 'type', title : '制度类型', width : 35}, 
										{ field : 'fileName',title : '文件名称',width : 180}, 
										{field : 'createBy',title : '文件上传者',width : 40},
										{field : 'dateTime',title : '上传日期',width : 60}
									] 
						          ],
				          onClickRow: function(node){
				        	  serchProject();
				  	    	/*var sel=$('#dg').datagrid('getSelected');
				  	    	$.messager.confirm("提示","确定查看：<font color=red>"+sel.fileName+"</font> ?",function(data){
				  	    		if(data){
				  	    			serchProject();
				  	    		}
				  	    	});*/
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
	//var menu = $("#dg").datagrid("getSelected");
	var menu = node;
	if (null == menu || menu == '') {
		sayInfo("请选择要查看的文件选项。");
		return;
	} else {
		$("#fileName").val(menu.pk_id);
		$("#dataForm").attr("action",ctxPath + '/home/viewRuleFile');
		$("#dataForm").submit();
	}
}

//在浏览器上直接查看pdf格式数据
function viewPdf(ctxPath){
	
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要查看的文件选项。");
		return;
	} else {
		$("#fileName").val(menu.pk_id);
		$("#dataForm").attr("action",ctxPath + '/home/viewRuleFile');
		$("#dataForm").submit();
	}
	
}
