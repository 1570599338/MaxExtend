package com.lquan.business.uploadFileManager;

import java.util.List;
import java.util.Map;

import snt.common.dao.base.PaginationSupport;

public interface IUploadFileService {
	/**
	 * 显示上传模板类型
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>>  getuploadList(String table_uploadFileModel,String table_uploadFileType) throws Exception;
	/**
	 * 上传模板的类型的查询
	 * @return
	 */
	public List<Map<String, Object>>  getuploadModelList(String table_uploadFileModel);
	
	/**
	 * 获取当前条件下的类型
	 * @param modelId
	 * @return
	 */
	public List<Map<String, Object>>  getuploadTypeList(String modelId,String uploadFileType);

	
	/**
	 * 新增文件的模板类型
	 * @param title
	 * @return
	 */
	public boolean addUploadFilemodel(String title,String uploadFileModel);
	/**
	 * 新增文件的文件类型
	 * @param title
	 * @return
	 */
	public boolean addUploadFileType(String modelId, String title,String table_uploadFileType);
	
	/**
	 * 删除文件的文件类型
	 * @param pk_id
	 * @return
	 */
	public boolean delUploadFileType( String pk_id,String table_uploadFileType);
	
	/**
	 * 删除文件的文件类型
	 * @param pk_id
	 * @return
	 */
	public boolean delUploadFileModel( String pk_id,String table_uploadFileModel);
	
	/**
	 * 编辑文件的文件类型
	 * @param title
	 * @return
	 */
	public boolean editUploadFileType(String modelId, String title,String uploadFileType);
	/**
	 * 编辑文件的文件类型
	 * @param title
	 * @return
	 */
	public boolean editUploadFileModel(String modelId, String title,String table_uploadFileModel);
	
	/**
	 * 上传的文件的
	 * @param user
	 * @param modelId
	 * @param typeId
	 * @param fileName
	 * @param path
	 * @return
	 */
	public boolean addUploadFile(String user,String modelId, String typeId,String fileName,String path,String table_uploadFile);

	/**
	 * 获取当前的上传文件的列表
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param title
	 * @return
	 */
	public PaginationSupport getFile(String table_uploadFile,String table_uploadFileType,String table_uploadFileModel,String page, String rows, String sort,String order, String title,Map<String,String> condition);
	
	/**
	 * 编辑上传文件的类型
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryFileType(String pk_id,String table_uploadFileType,String table_uploadFileModel) throws Exception;
	
	/**
	 * 查询对应的文件模板下面是否有多余的文件类型
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryFileModelType(String pk_id,String table_uploadFileType) throws Exception;
	
	/**
	 * 删除上传的文件
	 * @param pk_id
	 * @return
	 */
	public boolean delUpLoadFile(String pk_id,String table_uploadFile);
}
