<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/inc/inc.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
	/* form {
		margin: 0;
	} */
	textarea {
		display: block;
	}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>上传文件</title>
</head>
<body>
	<form id="deleteForm" name="deleteForm" action="" method="post" target="_self">
	    <input id="tacheTitle" name="tacheTitle" type="hidden" value="${tacheName_one }" />
	    <input id="tache_oldName" name="tache_oldName" type="hidden" value="${tacheName }" />
	    <input id="projectId_del" name="projectId_del" type="hidden" value="${projectId }" />
	    <input id="pk_id" name="pk_id" type="hidden"  />
	</form>
	<h2>添加下载文件模板与类型</h2>
	
	<div style="background:#f0f0f0;width: 1200px;height: 35px;">&nbsp;&nbsp;
		 <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-add'"  style="margin-top: 4px;" onclick="addUploadModelType()">添加模板</a>
		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-edit' "  style="margin-top: 4px;" onclick="editUploadModel('${ctxPath}')">编辑模板</a>
		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-remove' "  style="margin-top: 4px;" onclick="delUploadModel('${ctxPath}')">删除模板</a>
		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-add'"  style="margin-top: 4px;" onclick="addUploadFileType()">添加类型</a>
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<br />
	
	<div id="dataList" class="easyui-accordion">
	   <c:forEach items="${uploadList }" var="tache" >
	        <div title="${tache.title }" id="${tache.PK_ID}"  class="tacheDIV" style="overflow:auto;padding:10px;" iconCls="icon-flow">
	        	<table class="gridtable" id="${tache.PK_ID}">
	        	   <TR>
					  <th width="5%" nowrap="nowrap">编号</th>
					  <th width="12%" nowrap="nowrap">类型名称</th>
					  <th width="8%" nowrap="nowrap">类型ID</th>
					  <th width="18%" nowrap="nowrap">创建时间</th>
					  <th width="5%" nowrap="nowrap">操作</th>

				   </TR>
				   <c:forEach items="${tache.typeList }" var="list" varStatus="ss">
					   <TR>
					      <td>${ss.count}</td>
					      <td>${list.title }</td>
					      <td>${list.uploadFileModelID }</td>
					      <td>${list.createAt }</td>
					      <td>
					      	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain="true" onclick="editFileType('${ctxPath}','${list.PK_ID }')"></a>
					      	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true" onclick="deleFileType('${ctxPath}','${list.PK_ID }')"></a>
					      </td>
					   </TR>
					</c:forEach>
				 </table>
			</div>
	   </c:forEach>
	</div>
	 
	<!--数据显示 --END -->
	<!--文件模板类型-->
<div id="addUploadDialog" class="easyui-dialog" title="文件模板类型"
	data-options="closed: true,modal:true" 
	style="width:350px;height:200px;padding-left:20px; padding-top:20px;text-align:center">
	<form id="addTacheForm" name="addTacheForm" action="" method="post" target="_self">
		<input id="ctxPath" name="ctxPath" type="hidden" value="${ctxPath }" />
		<input id="model_Id" name="model_Id" type="hidden" value="" />
		 <table  border="0" cellpadding="5" cellspacing="2"  style="text-align: left;margin-left: 20px; "  >
			 <tr>
				 <td style="text-align: right">文件模板：</td>
				 <td><input  id="model_name" name="model_name"  class="easyui-validatebox" data-options="required:true"  />
				 </td>
			 </tr>		 		 
			 <tr><td  colspan="2" >
				   <a href="#" class="easyui-linkbutton"  plain="false" onclick="submitModelDialog('${ctxPath}');">&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;</a>
				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       <a href="#" class="easyui-linkbutton"  plain="false" onclick="cancleModelDialog();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
			 </td></tr>
	    </table>
    </form>
</div>

<div id="addTargetDialog" class="easyui-dialog" title="增加所属模板类型"
	data-options="closed: true,modal:true" 
	style="width:580px;height:180px;padding-left:20px; padding-top:20px;text-align:center">
	<form id="addTypeForm" name="addTargetForm" action="" method="post" target="_self">
		<input id="typeId" name="typeId" type="hidden"  />
		<input id="editTypeId" name="editTypeId" type="hidden"  />
		 <table  border="0" cellpadding="3" cellspacing="1"  style="text-align: left; width:530px;height:80px;"  >
		 	 <tr>
				 <td style="text-align: right" nowrap="nowrap">所属模板：</td>
				 <td> 
					 <input  id="model_name_type" name="model_name_type"  class="easyui-combobox"  data-options="required:true"  />
				</td>
				<td style="text-align: right" nowrap="nowrap">分类：</td>
				<td><input type="text" id="model_type" name="model_type" class="easyui-validatebox" data-options="required:true,validType:'length[1,99]'" /></td>
			 </tr>
			 <tr><td  colspan="5" style="padding-left: 130px;">
				   <a href="#" class="easyui-linkbutton"  plain="false" onclick="submitTypeDialog('${ctxPath}');">&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;</a>
				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       <a href="#" class="easyui-linkbutton"  plain="false" onclick="cancleTypeDialog();">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
			 </td></tr>
	    </table>
    </form>
</div>
<script type="text/javascript" src="${ctxPath}/jspage/upload/upload.js"></script>
</body>
</html>