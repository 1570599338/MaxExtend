$(function(){
	getdata();
});

//获取数据及查询
function getdata(){
	var ctxPath = $("#ctxPath").val();
	var fileName  = $('#log_title').val();
	 // 通过路径查询项目的数据列表
	$('#dg').datagrid({
						rownumbers:true,
						singleSelect:true,
						autoRowHeight:false,
						resizable:true,
						pagination:true,
						fit:true,
						pageSize:20,
						pageNumber:1,
						url : ctxPath + encodeURI(encodeURI('/rule/selectRuleList')),
						idField : 'pk_id',
						queryParams:{
							fileName:fileName
						},
						columns : [ 
						            [ 
						              	{ field : 'type', title : '类型', width : 80, sortable : true}, 
										{ field : 'fileName',title : '文件名',width : 100}, 
										{field : 'ruleTime',title : '时间',width : 180},
										{field : 'createBy',title : '上传者',width : 80}
									] 
						          ]
			});
}


// 添加模板类型
function addUploadModelType(){
	// 清空数据
	$("#model_Id").val("");
	$("#model_name").val("");
	//restFrom();
	$("#addUploadDialog").dialog({title: "添加模板类型"});
	$("#addUploadDialog").dialog('open');
}

//添加模板类型
function addUploadFileType(){
	var ctxPath = $("#ctxPath").val();
	// 清空编辑标志位
	$("#editTypeId").val("");
	//restFrom();
	$('#model_name_type').combobox({  
	    url:ctxPath + '/upload/queryFileModelList',
	    panelHeight: 'auto',//自动高度适合
	    valueField:'pk_id',  
	    textField:'title',
	});
	$("#addTargetDialog").dialog({title: "文件类型"});
	$("#addTargetDialog").dialog('open');
}

// 提交文件的模板类型
function submitModelDialog(){
	var model_name = $("#model_name").val();
	var model_Id = $("#model_Id").val();
	var url ="";
	if(model_Id){
		// 修改文件的模板
		url = $("#ctxPath").val() + '/upload/editFileModel';
	}else{
		// 添加文件的模板
		url = $("#ctxPath").val() + '/upload/addModelFile';
	}
	if(!model_name){
		sayInfo("请填写文件的模板类型！");
		return false;
	}
	$("#addTacheForm").attr("action",url);
	$("#addTacheForm").submit();
	
	
}

function cancleModelDialog(){
	$("#addUploadDialog").dialog('close');
}

//添加文件的上传类型
function submitTypeDialog(ctxPath){
	// 获取文件模板类型的编辑标志位
	var editType = $("#editTypeId").val();
	if("editTypeId"==editType){
		var model_name = $("#model_name_type").combobox('getValue');
		var type = $("#model_type").val();
		
		if(!model_name){
			sayInfo("请选择文件的模板类型！");
			return false;
		}
		if(!type){
			sayInfo("请选择文件的类型！");
			return false;
		}
		$("#addTypeForm").attr("action",ctxPath + '/upload/editFileType');
		$("#addTypeForm").submit();
		
	}else{
		var model_name = $("#model_name_type").combobox('getValue');
		var type = $("#model_type").val();
		
		if(!model_name){
			sayInfo("请选择文件的模板类型！");
			return false;
		}
		if(!type){
			sayInfo("请选择文件的类型！");
			return false;
		}
		$("#addTypeForm").attr("action",ctxPath + '/upload/addFileType');
		$("#addTypeForm").submit();
	}
	
	
}

// 取消按钮
function cancleTypeDialog(){
	$("#addTargetDialog").dialog('close');
}



/**
 * 编辑指标，打开DIV
 */
function editFileType(ctxPath,pk_id){
	//resetForm();
	$.ajax({
		url : ctxPath+'/upload/queryFileType',
		data : {"pk_id":pk_id},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			$("#editTypeId").val("editTypeId");
			$("#typeId").val(data.pk_id);
			$("#model_type").val(data.title);
			$("#model_name_type").combobox('setValue',data.Mtitle);
			
			$("#addTargetDialog").dialog({title: "编辑文件模板类型"});
			$("#addTargetDialog").dialog('open');
		},
		error : function() {
			sayInfo("出错了。");
		}
	}); 
}
/**
 * 删除指定的问卷流程指标
 * @param ctxPath
 * @param pk_id
 */
function deleFileType(ctxPath,pk_id){
	$.messager.confirm("提示","删除操作不可恢复，您确定要删除该类型吗？",function(data){
		if(data){
			$("#pk_id").val(pk_id);
			$("#deleteForm").attr("action",ctxPath + "/upload/delFileType");
			$("#deleteForm").submit();
		}
	});
}


/**
 * 编辑文件模板
 */
function editUploadModel(){
	var p = $('#dataList').accordion('getSelected'); 
	if (p){
		var model_Id = p.panel('options').id;
		$("#model_Id").val(model_Id);
		var title = p.panel('options').title;
		$("#model_name").val(title);
		$("#addUploadDialog").dialog({title: "编辑文件模板类型"});
		$("#addUploadDialog").dialog('open');
	}else{
		sayInfo("请选择文件模板！");
	}
}


/**
 * 删除文件模板
 */
function delUploadModel(){
	var ctxPath = $("#ctxPath").val();
	var p = $('#dataList').accordion('getSelected'); 
	if (p){
		var pk_id = p.panel('options').id;
		$.ajax({
			url : ctxPath+'/upload/queryFileModelType',
			data : {"pk_id":pk_id},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if(data.length>0){
					sayInfo("对不起，该文件模板下面有文件类型，咱不能删除该文件模板！");
					return;
				}else{
					var pk_id = p.panel('options').id;
					$("#pk_id").val(pk_id);
					$("#deleteForm").attr("action",ctxPath + "/upload/delFileModel");
					$("#deleteForm").submit();
				}
			},
			error : function() {
				sayInfo("出错了。");
			}
		}); 
	}else{
		sayInfo("请选择文件模板！");
	}
	
}










