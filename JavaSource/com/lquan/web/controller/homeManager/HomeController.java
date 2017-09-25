package com.lquan.web.controller.homeManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.util.MapFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import snt.common.dao.base.PaginationSupport;
import snt.common.web.util.WebUtils;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.lquan.business.homeManager.IHomeMangeService;
import com.lquan.business.uploadFileManager.IUploadFileService;
import com.lquan.util.MyFileUtil;
import com.lquan.util.Utils;
import com.lquan.web.util.FormUtil;

/**
 * @description 首页
 * @author liuquan
 * @date 2016年10月18日10:22:40
 *
 */

@Controller
@RequestMapping(value="/home")
public class HomeController {
	Log log = LogFactory.getLog(HomeController.class);
	
	@Resource(name="homeMangeService")
	private IHomeMangeService   homeMangeService;
	
	@Resource(name="uploadFileServe")
	private  IUploadFileService uploadFileService;
	
	private static String table_uploadFile=WebUtils.getModuleProperty("table.uploadFile");
	private static String table_uploadFileType = WebUtils.getModuleProperty("table.uploadFileType");
	private static String table_uploadFileModel=WebUtils.getModuleProperty("table.uploadFileModel");
	
	
	/**
	 * @Decription  登录首页
	 * @param request
	 * @return
	 */
	@RequestMapping(value="toHome")
	public String getHome(HttpServletRequest request,HttpServletResponse response){
		List<Map<String, Object>> notice = null ;
		List<Map<String, Object>> active = null ;
		List<Map<String, Object>> rule = null ;
		List<Map<String, Object>>  list = null;
		// 文件的相对路径
		//String basePath = "upload/Rule/";
		String basePath = WebUtils.getModuleProperty("down.fileRule");
		// 上传的活动展示的文件的相对路径  upload/Img/
		String activityBasePath = WebUtils.getModuleProperty("down.fileImg");
		String ctxPath= request.getContextPath();
		// 系统公告
		try {
			notice = this.homeMangeService.systemAd(ctxPath);
			
			//active = this.homeMangeService.getHomeImglist();
			active = this.homeMangeService.getHomeActive(activityBasePath,ctxPath);
					
			rule =  this.homeMangeService.getHomeruler(basePath,ctxPath);
			
			list = uploadFileService.getuploadList(table_uploadFileModel,table_uploadFileType);
			
		} catch (Exception e) {
			log.error("系统公告出现问题啦！", e);
		}
		// 活动展示
		request.setAttribute("active", active);
		//系统公告
		request.setAttribute("notice", notice);
		// 规章制度
		request.setAttribute("rule", rule);
		
		request.setAttribute("uploadList", list);
		return "home/home";
	}
	
	/**
	 * @Decription  登录首页
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/down")
	public void downRuleFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String page = request.getParameter("fileId") == null ? "0" : request.getParameter("page").toString();
		
	}
	
	
	/**
	 * @Decription  登录首页
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/viewRuleFile")
	public void viewRuleFile(HttpServletRequest request,HttpServletResponse response){
		try {
			String pk_id = request.getParameter("fileName") == null ? "" : request.getParameter("fileName").toString();
			String path = WebUtils.getModuleProperty("upload.fileRule");
			List<Map<String, Object>>  pdfFile = this.homeMangeService.getOnePdfflow(pk_id);
			String fileAllName = null;
			for(Map<String, Object> mapPdf :pdfFile ){
				path = path + "//" + mapPdf.get("type").toString() + "//"  ;
				fileAllName =  mapPdf.get("fileAllName").toString();
			}
			MyFileUtil.onLinePdf(response, path, fileAllName);
			
		} catch (Exception e) {
			String path = WebUtils.getModuleProperty("upload.errorFile");
			try {
				MyFileUtil.onLineErrorPdf(response, path);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.error("在线预览出错啦！", e);
		}
       
	}
	
	
	/**
	 * @Decription  登录首页
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="toRuleBase")
	public String getRuleBase(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String type = request.getParameter("fileType") == null ? "" : request.getParameter("fileType").toString();
		request.setAttribute("filetype", type);
		 return "home/rule_base";
	}
	
	/**
	 * @Decription  规章制度
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="toRuleList")
	public void getRulelist(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		//当前页
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		//每页显示的条数
		String rows = request.getParameter("rows") == null ? "20" : request.getParameter("rows").toString();
		//排序字段
		String sort = request.getParameter("sort") == null ? "fileName" : request.getParameter("sort").toString();
		//排序顺序
		String order = request.getParameter("order") == null ? "asc" : request.getParameter("order").toString();
		
		String fileName = request.getParameter("fileName") == null ? "" : request.getParameter("fileName").toString();
		String type = request.getParameter("filetype") == null ? "" : request.getParameter("filetype").toString();
		
		PaginationSupport ps = this.homeMangeService.getRuleList(page, rows, sort,order, fileName,type);
		
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
	}
	
	
	/**
	 * @Decription  获取当前的公告的信息
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="noticePage")
	public String getNoticePage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		 return "home/notice_base";
	}
	
	/**
	 * @Decription  最新公告
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="toNoticeList")
	public void getNoticelist(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		//当前页
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		//每页显示的条数
		String rows = request.getParameter("rows") == null ? "20" : request.getParameter("rows").toString();
		//排序字段
		String sort = request.getParameter("sort") == null ? "noticeTime" : request.getParameter("sort").toString();
		//排序顺序
		String order = request.getParameter("order") == null ? "asc" : request.getParameter("order").toString();
		
		String fileName = request.getParameter("fileName") == null ? "" : request.getParameter("fileName").toString();
		String type = request.getParameter("filetype") == null ? "" : request.getParameter("filetype").toString();
		
		PaginationSupport ps = this.homeMangeService.getNoticeList(page, rows, sort,order, fileName,type);
		
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
	}
	

	
	
	/**
	 * @Decription  登录首页
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="todownFilePage")
	public String todownFilePage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		String type = FormUtil.getStringFiledValue(request, "type");
		String modelId = FormUtil.getStringFiledValue(request, "modelId");
		String modelTitle = FormUtil.getStringFiledValue(request, "modelTitle");
		
		String typeId = FormUtil.getStringFiledValue(request, "typeId");
		String title = FormUtil.getStringFiledValue(request, "title");
		if("1".equals(type)){
			typeId = null;
			title = null;
		}
		request.setAttribute("modelId", modelId);
		request.setAttribute("modelTitle", modelTitle);
		request.setAttribute("typeId", typeId);
		request.setAttribute("title", title);
		 return "home/downFile_base";
	}
	
	
}




