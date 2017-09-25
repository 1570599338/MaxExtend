package com.lquan.web.controller.uploadFileManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import snt.common.dao.base.PaginationSupport;
import snt.common.web.util.WebUtils;

import com.google.gson.Gson;
import com.lquan.business.uploadFileManager.IUploadFileService;
import com.lquan.util.MyFileUtil;
import com.lquan.web.util.FormUtil;

/**
 * 下载专区
 * @author liuquan
 *
 */
@Controller
@RequestMapping(value="upload")
public class UploadController {
	Log log = LogFactory.getLog(UploadController.class);
	
	@Resource(name="uploadFileServe")
	private  IUploadFileService uploadFileService;
	
	private static String table_uploadFile=WebUtils.getModuleProperty("table.uploadFile");
	private static String table_uploadFileType = WebUtils.getModuleProperty("table.uploadFileType");
	private static String table_uploadFileModel=WebUtils.getModuleProperty("table.uploadFileModel");
	
	/**
	 * 跳转到模板类型的页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/toPage")
	public String toLogin(HttpServletRequest request) throws Exception{
		
		List<Map<String, Object>>  list = uploadFileService.getuploadList(table_uploadFileModel,table_uploadFileType);
		request.setAttribute("uploadList", list);
		
		 return "uploadfile/uploadFileModelPage";
		//return "redirect:http://www.baidu.com/";
	}
	
	/**
	 * 添加下载模板
	 */
	@RequestMapping(value="addModelFile")
	public ModelAndView addAd(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 
		String modelFile = FormUtil.getStringFiledValue(request, "model_name");
		Boolean p;
		try {

			p = this.uploadFileService.addUploadFilemodel(modelFile,table_uploadFileModel);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "添加成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "添加失败.");
			}
		} catch (Exception e) {
			log.error("插入公告信息出错", e);
		}
		return new ModelAndView("redirect:/upload/toPage");
	}
	
	/**
	 * 修改文件模板
	 */
	@RequestMapping(value="editFileModel")
	public ModelAndView editFileModel(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 文件模板
		String model_name = FormUtil.getStringFiledValue(request, "model_name");
		// 文件类型
		String model_Id = FormUtil.getStringFiledValue(request, "model_Id");
		Boolean p;
		try {

			p = this.uploadFileService.editUploadFileModel(model_Id, model_name,table_uploadFileModel);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "修改成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "修改失败.");
			}
		} catch (Exception e) {
			log.error("修改公告信息出错", e);
		}
		return new ModelAndView("redirect:/upload/toPage");
	}
	
	/**
	 * 添加下载文件类型
	 */
	@RequestMapping(value="addFileType")
	public ModelAndView addFileType(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 文件模板
		String modelId = FormUtil.getStringFiledValue(request, "model_name_type");
		// 文件类型
		String type = FormUtil.getStringFiledValue(request, "model_type");
		Boolean p;
		try {

			p = this.uploadFileService.addUploadFileType(modelId, type,table_uploadFileType);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "添加成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "添加失败.");
			}
		} catch (Exception e) {
			log.error("插入公告信息出错", e);
		}
		return new ModelAndView("redirect:/upload/toPage");
	}
	
	/**
	 * 添加下载文件类型
	 */
	@RequestMapping(value="editFileType")
	public ModelAndView editFileType(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 文件模板
		String typeName = FormUtil.getStringFiledValue(request, "model_type");
		// 文件类型
		String typeID = FormUtil.getStringFiledValue(request, "typeId");
		Boolean p;
		try {

			p = this.uploadFileService.editUploadFileType(typeID, typeName,table_uploadFileType);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "修改成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "修改失败.");
			}
		} catch (Exception e) {
			log.error("修改公告信息出错", e);
		}
		return new ModelAndView("redirect:/upload/toPage");
	}
	
	
	/**
	 * 查询上传文件的模型
	 * @param request
	 * @returngetuploadModelList
	 */
	@RequestMapping(value ="queryFileModelList")
	public void  queryFileModelList(HttpServletRequest request,HttpServletResponse response){
		
		try {
			List<Map<String, Object>> modellist = this.uploadFileService.getuploadModelList(table_uploadFileModel);
			//json形式返回以树形展示
			JSONArray json=JSONArray.fromObject(modellist);
			
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();			
			out.write(json.toString());			
			out.flush();			
			out.close();
			
		} catch (NumberFormatException e) {
			log.error("queryFileTypeList,查询类型出错！", e);
		} catch (Exception e) {
			log.error("queryFileTypeList,查询模板信息出错！", e);
		}
		
	}
	/**
	 * 查询上传文件的类型
	 * @param request
	 * @returngetuploadModelList
	 */
	@RequestMapping(value ="queryFileTypeList")
	public void  queryFileTypeList(HttpServletRequest request,HttpServletResponse response){
		
		String modelId = FormUtil.getStringFiledValue(request, "modelID");
		try {
			List<Map<String, Object>> modellist = this.uploadFileService.getuploadTypeList(modelId,table_uploadFileType);
			//json形式返回以树形展示
			JSONArray json=JSONArray.fromObject(modellist);
			
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();			
			out.write(json.toString());			
			out.flush();			
			out.close();
			
		} catch (NumberFormatException e) {
			log.error("queryFileTypeList,查询类型出错！", e);
		} catch (Exception e) {
			log.error("queryFileTypeList,查询模板信息出错！", e);
		}
		
	}
	
// *******************************************************************************
//*																			 	 *
// ******************************* 上传文件  *****************************************
//*																				 *
// *******************************************************************************	
	/**
	 * 跳转到模板类型的页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/uploadFile")
	public String uploadFile(HttpServletRequest request) throws Exception{
		
		
		return "uploadfile/uploadFile";
	}
	
	/**
	 * 上传规章制度
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadFilex")
	public ModelAndView topReport(HttpServletRequest request,HttpServletResponse response,RedirectAttributes  redirect) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		//当前用户
		String userName =  "admin";
		// 获取当前文件的存放的路径
		String path = WebUtils.getModuleProperty("upload.fileDir");
		String modelId = FormUtil.getStringFiledValue(request, "model_name");
		String typeId = FormUtil.getStringFiledValue(request, "type_name");
		// 经过转换后的文件的文件名称 -- > 实际文件名转换成数字类型的
		String fileAllName = MyFileUtil.oneFileUploadName(request, path);
		// IT未来发展之路.pdf
		String fileName = fileAllName.substring(0, fileAllName.lastIndexOf("."));
	
		
		if(fileName != null && !"".equals(fileName)){
			if(this.uploadFileService.addUploadFile(userName, modelId, typeId, fileName, fileAllName,table_uploadFile)){
				redirect.addFlashAttribute("message", "上传规章制度成功！");
				return  new ModelAndView("redirect:/upload/uploadFile");
			}else{
				redirect.addFlashAttribute("message", "上传规章制度失败！");
				return  new ModelAndView("redirect:/upload/uploadFile");
			}
				
			
		}	
		log.error("没有选中要上传的文件！");
		redirect.addFlashAttribute("message", "请选择你要上传的文件！");
		return  new ModelAndView("redirect:/upload/toPage");
	}
	
	/**
	 * 以json数据查询项目列表
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="selectfileList")
	public void selectRuleList(HttpServletRequest request, HttpServletResponse response) {
		
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		//查询所有的经销商
		try {
			//当前页
			String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
			//每页显示的条数
			String rows = request.getParameter("rows") == null ? "20" : request.getParameter("rows").toString();
			//排序字段
			String sort = request.getParameter("sort") == null ? "fileName" : request.getParameter("sort").toString();
			//排序顺序
			String order = request.getParameter("order") == null ? "asc" : request.getParameter("order").toString();
			// 条件查询公告标题
			String fileName = request.getParameter("fileName") == null ? "" : request.getParameter("fileName").toString();
			
		/*	String table_uploadFile=WebUtils.getModuleProperty("table.uploadFile");
			String table_uploadFileType = WebUtils.getModuleProperty("table.uploadFileType");
			String table_uploadFileModel=WebUtils.getModuleProperty("table.uploadFileModel");*/
			
			String modelId = FormUtil.getStringFiledValue(request, "modelId");
			String typeId = FormUtil.getStringFiledValue(request, "typeId");
			Map<String,String> condition = new HashMap<String, String>();
			condition.put("modelId", modelId==null?"":modelId);
			condition.put("typeId", typeId==null?"":typeId);
			
			PaginationSupport ps = this.uploadFileService.getFile(table_uploadFile,table_uploadFileType,table_uploadFileModel,page, rows,sort,order,fileName,condition);
			Map<String,Object> result = new HashMap<String,Object>();
			List<Map<String,Object>> list = null;
			if(null != ps){
				list = ps.getItems();
				result.put("total", ps.getTotalCount());
				result.put("rows", list);
			}else{
				result.put("total", 0);
				result.put("rows", 0);
			}
			
			//json形式返回以树形表格展示
			JSONArray json=JSONArray.fromObject(result);
			PrintWriter out = response.getWriter();	
			//拼接的json数据多出一对“【】”，所以先去掉
			String resultStr = json.toString().substring(1,json.toString().length()-1);
			out.write(resultStr);			
			out.flush();			
			out.close();
			
		} catch (Exception e) {
			log.error("selectRuleList,以json数据查询经销商列表出错", e);
		}
	}
	
	/**
	 * 下载按文件
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value="downfile")
	public void viewRuleFile(HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException{
		String fileAllName = FormUtil.getStringFiledValue(request, "fileAllName");
		String suffix =fileAllName.substring(fileAllName.length()-3,fileAllName.length());
		// 获取当前文件的存放的路径
		String path = WebUtils.getModuleProperty("upload.fileDir");
		if("pdf".equalsIgnoreCase(suffix)){
			MyFileUtil.onLinePdf(response, path+"//", fileAllName);
		}else{
			MyFileUtil.download(response, path, fileAllName);
		}
		
		
		
	}
	
	
	/**
	 * 根据pk_id查询指标数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryFileType")
	public void queryFileType(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		
		String pk_id = request.getParameter("pk_id");
		try {
			if(pk_id != null && !"".equals(pk_id)){
				Map<String,Object> list = uploadFileService.queryFileType(pk_id,table_uploadFileType,table_uploadFileModel);
				
				//返回参数
				Gson gson = new Gson();
				String jsonObject = gson.toJson(list);
				PrintWriter out = response.getWriter();
				out.print(jsonObject);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
               log.error("根据pk_id查询文件类型数据出错",e);			
		}
	}
	
	/**
	 * 删除文件类型
	 */
	@RequestMapping(value="delFileType")
	public ModelAndView delFileType(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 获取文件的主键
		String pk_id = FormUtil.getStringFiledValue(request, "pk_id");
		Boolean p;
		try {

			p = this.uploadFileService.delUploadFileType(pk_id,table_uploadFileType);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "删除成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "删除失败.");
			}
		} catch (Exception e) {
			log.error("删除文件模板类型出错！", e);
		}
		return new ModelAndView("redirect:/upload/toPage");
	}
	
	/**
	 * 删除文件类型
	 */
	@RequestMapping(value="delFileModel")
	public ModelAndView delFileModel(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 获取文件的主键
		String pk_id = FormUtil.getStringFiledValue(request, "pk_id");
		Boolean p;
		try {

			p = this.uploadFileService.delUploadFileModel(pk_id,table_uploadFileModel);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "删除成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "删除失败.");
			}
		} catch (Exception e) {
			log.error("删除文件模板类型出错！", e);
		}
		return new ModelAndView("redirect:/upload/toPage");
	}
	
	
	/**
	 * 根据文件的查询文件模板下面是否有文件类型
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryFileModelType")
	public void queryFileModelType(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		
		String pk_id = request.getParameter("pk_id");
		try {
			if(pk_id != null && !"".equals(pk_id)){
				List<Map<String, Object>> result = uploadFileService.queryFileModelType(pk_id,table_uploadFileType);
				
				//返回参数
				Gson gson = new Gson();
				String jsonObject = gson.toJson(result);
				PrintWriter out = response.getWriter();
				out.print(jsonObject);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
               log.error("根据pk_id查询文件类型数据出错",e);			
		}
	}	
	
	/**
	 * 删除文件类型
	 */
	@RequestMapping(value="delUpLoadFile")
	public ModelAndView delUpLoadFile(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 获取文件的主键
		String pk_id = FormUtil.getStringFiledValue(request, "pk_id");
		Boolean p;
		try {

			p = this.uploadFileService.delUpLoadFile(pk_id,table_uploadFile);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "删除成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "删除失败.");
			}
		} catch (Exception e) {
			log.error("删除文件模板类型出错！", e);
		}
		return new ModelAndView("redirect:/upload/uploadFile");
	}	
	
}
