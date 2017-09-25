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
						url : ctxPath + encodeURI(encodeURI('/home/toNoticeList')),
						idField : 'pk_id',
						queryParams:{
							fileName:fileName,
							filetype:filetype
						},
						columns : [ 
						            [ 
						              	{ field : 'title', title : '公告标题', width : 180, sortable : true}, 
										{ field : 'noticeTime',title : '发布时间',width : 40} 
										
									
									] 
						          ],
						 onClickRow: function(node){
				        	  serchProject();
				  		}
			});
}

function serchProject() {
	var node = $('#dg').datagrid('getSelected');
	if (node) {
		changeDialog(node);
	} else {
		sayInfo('请选择要查看的公告选项！');
	}
}

function changeDialog(node){
	
	var ctxPath = $("#ctxPath").val();
	//var menu = $("#dg").datagrid("getSelected");
	var menu = node;
	$("#id").val(menu.pk_id);
	var title = "<font size='40px'><b>" + menu.title +"</b></font><br/>作者：lquan<br/>"+ menu.noticeTime+"<br/><br/><hr width=80% size=1 color=#a6a6a7 style='border:1 dashed #5151A2'/>";
	$('#notice_title').html(title);
	$('#notice_content').html(menu.content);
	$("#noticeDialog").dialog({title: "显示公告信息"});
	$("#noticeDialog").dialog('open');
}



//***************编辑公告**************
function editNoticeDiv(){
	var menu = $("#dg").datagrid("getSelected");
	if (null == menu || menu == '') {
		sayInfo("请选择要查看的公告选项。");
		return;
	} else {
		$("#id").val(menu.pk_id);
		var title = "<font size='40px'><b>" + menu.title +"</b></font><br/>作者：lquan<br/>2016年10月1日<br/><br/><hr width=80% size=1 color=#a6a6a7 style='border:1 dashed #5151A2'/>";
		$('#notice_title').html(title);
		$('#notice_content').html(menu.content);
		$("#noticeDialog").dialog({title: "显示公告信息"});
		$("#noticeDialog").dialog('open');
	}
}

// 关闭打开的弹窗
function noticeClose(){
	$("#noticeDialog").dialog('close');
}



