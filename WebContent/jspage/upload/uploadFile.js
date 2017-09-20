
$(function(){
	var ctxPath = $("#ctxPath").val();
	getdata();
	$('#model_name').combobox({ 
		url:ctxPath + '/upload/queryFileModelList',
	    editable:false, //不可编辑状态
	    cache: false,
	    panelHeight: 'auto',//自动高度适合
	    valueField:'pk_id',  
	    textField:'title',
	    
	    onSelect: function(record){
	    	_type_name.combobox({
	    		editable:false, //不可编辑状态
	    		panelHeight: 'auto',//自动高度适合
                disabled: false,
                url: ctxPath + '/upload/queryFileTypeList?modelID=' + record.pk_id,
                valueField:'pk_id',  
        	    textField:'title',
            }).combobox('clear');
			}
       }); 
	
	var _type_name = $('#type_name').combobox({
		editable:false, //不可编辑状态
		panelHeight: 'auto',//自动高度适合
        disabled: true,
        valueField:'pk_id',  
	    textField:'title',
    });

});

//获取数据及查询
function getdata(){
	var ctxPath = $("#ctxPath").val();
	var fileName  = $('#fileName').val();
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
						//url : ctxPath + encodeURI(encodeURI('/notice/selectNoticeList?title='+title)),
						url : ctxPath + encodeURI(encodeURI('/upload/selectfileList')),
						idField : 'pk_id',
						queryParams:{
							fileName:fileName
						},
						columns : [ 
						            [ 
						              	{ field : 'modelName', title : '文件模型', width : 80, sortable : true}, 
										{ field : 'typeName',title : '文件类型',width : 100}, 
										{ field : 'fileName',title : '文件名',width : 100}, 
										{field : 'createTime',title : '时间',width : 180},
										{field : 'createBy',title : '上传者',width : 80}
									] 
						          ]
			});
}

// 打开上传按钮
function updateAdDiv(){
	$("#addDialog").dialog({title: "上传文件"});
	$("#addDialog").dialog('open');
	
}

// 取消上传的按钮
function cancleDialogFile(){
	$("#addDialog").dialog('close');
}

// 上传文件
function onSubmitFile(){
	var ctxPath = $("#ctxPath").val();
	// 上传问价的类型
	var model_name = $("#model_name").combobox("getValue");
	// 上传文件类型
	var type_name = $("#type_name").combobox("getValue");
	// 文件及其路径
	var file = $('#file_path').val();
	if(!model_name){
		sayInfo("请选择上文件的模型！");
		return false;
	}
	if(!type_name){
		sayInfo("请选择上文件的类型！");
		return false;
	}
	if($.trim(file)==''){
		sayInfo("请选择要上传的文件");
		return false;
	}
	var postfixxx = document.upfileForm.file_path.value;
	var postfixx = document.upfileForm.file_path.value.trim().match(/^(.*)(\.)(.{1,8})$/);
	var postfix = document.upfileForm.file_path.value.trim().match(/^(.*)(\.)(.{1,8})$/)[3].toLowerCase(); //获得选择的上传文件的后缀名的正则表达式
	if (postfix == "ZIP" || postfix == "zip" || postfix == "PDF" || postfix == "pdf") {
		$("#upfileForm").attr("action",ctxPath + "/upload/uploadFilex");
		$("#upfileForm").submit();
	}else{
    	sayInfo("上传文件类型不对，请选择zip、pdf格式文件上传!" + postfix);
        return false;
    }
	
}


// 清空之前的数据
function restFrom(){
	$("#upfileType").combobox('setValue','0');
	$("#file_path").val("");
}

// 获取文件的上传类型
function getFileType(){
	var ctxPath = $("#ctxPath").val();
	var model_name = $("#model_name").combobox('getValue');
	if(model_name){
		$('#type_name').combobox({  
		    url:ctxPath + '/upload/queryFileTypeList?model_name='+model_name,
		    valueField:'pk_id',  
		    textField:'title'  
		});
	}
}

// 下载文档
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


//删除规章制度
function delUpLoadFile(ctxPath){
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要查看的文件选项。");
		return;
	} else {
		$.messager.confirm("提示","删除操作不可恢复，您确定要删除该上传的文件吗？",function(data){
			if(data){
				$("#pk_id").val(menu.pk_id);
				$("#dataForm").attr("action",ctxPath + "/upload/delUpLoadFile");
				$("#dataForm").submit();
				
			}
		});
	}
	
	
}







