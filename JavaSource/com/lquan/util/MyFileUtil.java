package com.lquan.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import snt.common.dao.base.PrimaryKeyGenerator;

/**
* @ClassName: MyFileUtil 
* @Description:文件工具类 
* @author hurong
* @date 2013-07-17
 */
public class MyFileUtil {
	
	
	/**
	 * 下载文件
	 * (常用于导出格式错误的excel)
	 * @param response
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static OutputStream download(HttpServletResponse response, String fileName) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("attachment;  filename=").append(fileName);
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Disposition", new String(sb.toString().getBytes(), "ISO8859-1"));
		OutputStream out = response.getOutputStream();
		return out;
	}

	/**
     * 下载文件.
     * path:文件真实路径，不包括文件名
     * fileName:文件名，包括后缀
     * @param response
     * @param path
     * @param fileName
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void download(HttpServletResponse response, String path,
            String fileName) throws FileNotFoundException, IOException
    {
        download(response, path, fileName, fileName);
    }
    
    /**
     * 下载文件
     * @param respnse
     * @param path
     * @param fileName
     * @param rename
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void download(HttpServletResponse response, String path,
            String fileName, String rename) throws FileNotFoundException, IOException
    {
        StringBuffer sb = new StringBuffer();
        sb.append("attachment;  filename=").append(rename);
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentType("application/x-msdownload;charset=UTF-8");
        response.setHeader("Content-Disposition", 
                   new String(sb.toString().getBytes(), "ISO8859-1"));
        OutputStream out = response.getOutputStream();
        File file = new File(path, fileName);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        byte[] b = new byte[1024*100];
        while ( in.available() > 0)
        {
            if (in.available() < 1024*100)
            {
                byte[] lastByte = new byte[in.available()];
                in.read(lastByte);
                out.write(lastByte);
            }
            else
            {
                in.read(b);
                out.write(b);
            }
        }
        out.flush();
        
    }
    
   /**
    * spring mvc中上传文件
	* 返回文件上传后在服务器中的真实路径数组
	* dirPath-文件存储路径
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
	public static String[] uploadFile(HttpServletRequest request,
			String dirPath) throws Exception {
		
		String path="";
		String fileName="";
		List<String> pathList=new ArrayList<String>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyymmddhhmmsssss");
		
		if(multipartResolver.isMultipart(request)){//判断 request 是否有文件上传,即多部分请求...   
			MultipartHttpServletRequest multiRequest =  (MultipartHttpServletRequest)request;
			Iterator<String> iter = multiRequest.getFileNames();
			
			File filePath = new File(dirPath);
			if(!filePath.exists()){
				filePath.mkdirs();
			}
			while(iter.hasNext()){
				MultipartFile file = multiRequest.getFile((String)iter.next()); 
				fileName = file.getOriginalFilename();
				//文件中文名问题
				fileName=new String(fileName.getBytes("ISO8859-1"),"utf-8");
				if("".equals(fileName)){
					continue;
				}
				//组成新的存放路径
				long key = PrimaryKeyGenerator.getLongKey();
				path = dirPath+"\\"+sdf.format(new Date())+key+fileName.substring(fileName.lastIndexOf("."));
				File localFile= new File(path);
				//将上传文件写入服务器指定的文件中
				file.transferTo(localFile);
				pathList.add(path);
			}
		}

		return pathList.toArray(new String[pathList.size()]);
	}
	
	
	 /**
	    * spring mvc中上传文件
		* 返回文件上传后在服务器中的真实路径数组
		* dirPath-文件存储路径
		* 此方法返回的文件名为真实的文件名
	    * @param request
	    * @param response
	    * @return
	    * @throws Exception
	    */
		public static String[] uploadFileWithRealName(HttpServletRequest request,
				String dirPath) throws Exception {
			
			String path="";
			String fileName="";
			List<String> pathList=new ArrayList<String>();
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			
			if(multipartResolver.isMultipart(request)){
				MultipartHttpServletRequest multiRequest =  (MultipartHttpServletRequest)request;
				Iterator<String> iter = multiRequest.getFileNames();
				
				File filePath = new File(dirPath);
				if(!filePath.exists()){
					filePath.mkdirs();
				}
				while(iter.hasNext()){
					MultipartFile file = multiRequest.getFile((String)iter.next()); 
					fileName = file.getOriginalFilename();
					//文件中文名问题
					fileName=new String(fileName.getBytes("ISO8859-1"),"utf-8");
					if("".equals(fileName)){
						continue;
					}
					//组成新的存放路径
					long key = PrimaryKeyGenerator.getLongKey();
					path = dirPath+"\\"+key+fileName;
					File localFile= new File(path);
					//将上传文件写入服务器指定的文件中
					file.transferTo(localFile);
					pathList.add(path);
				}
			}

			return pathList.toArray(new String[pathList.size()]);
		}
	
	 /**
    * spring mvc中上传文件
	* 返回文件上传后在服务器中的真实路径数组
	* dirPath-文件存储路径
	* newFileName-可以定义新的名称（不带后缀，默认与上传文件后缀相同）
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
	public static String[] uploadFile(HttpServletRequest request,
			String dirPath,String newFileName) throws Exception {
		
		String fileName="";
		String path="";
		List<String> pathList=new ArrayList<String>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest =  (MultipartHttpServletRequest)request;
			Iterator<String> iter = multiRequest.getFileNames();
			
			File filePath = new File(dirPath);
			if(!filePath.exists()){
				filePath.mkdirs();
			}
			
			while(iter.hasNext()){
				MultipartFile file = multiRequest.getFile((String)iter.next()); 
				fileName=file.getOriginalFilename();
				//文件中文名问题
				fileName=new String(fileName.getBytes("ISO8859-1"),"utf-8");
				if("".equals(fileName)){
					continue;
				}
				//组成新的存放路径
				path = dirPath+"\\"+newFileName+fileName.substring(fileName.lastIndexOf("."));
				File localFile= new File(path);
				//将上传文件写入服务器指定的文件中
				file.transferTo(localFile);
				pathList.add(path);
			}
		}

		return pathList.toArray(new String[pathList.size()]);
	}
	
	/**
	 * 导入数据前，校验excel是否有正确数量的工作表 
	 * 校验是否excel标题格式正确 
	 * 默认选择第一个工作表的标题头数据 
	 * @param workBookStream
	 * @param titles
	 * @return
	 * @throws Exception
	 */
	public static String exameIfExcelFormatRight(FileInputStream workBookStream,String[] titles) throws Exception{
		if(workBookStream==null || titles==null){
			return "导入出错，请尝试重新上传导入文件。";
		}
		
		POIFSFileSystem excelBook = new POIFSFileSystem(workBookStream);
		//得到工作簿
		HSSFWorkbook wookBook=new HSSFWorkbook(excelBook);
		
		if (wookBook.getNumberOfSheets()<=0) {
			return "excel缺少工作表，请检查上传的导入文件。";
		}else{//默认取第一个excel的标题头数据
			HSSFSheet sheet=wookBook.getSheetAt(0);
			//得到上传文件的表头数据
			List<String> excelTitles=gainColNameList(sheet);
			List<String> titlesList=new ArrayList<String>();
			//转成list形式用来比较
			for (String string : titles) {
				titlesList.add(string);
			}
			
			if(!excelTitles.equals(titlesList)){
				return "导入文件的工作表标题行和模板文件标题行不一致，请重新下载模板文件，装载数据后尝试再次导入。";
			}
			
		}
		
		return "";
	}
	
	/**
	 * 得到sheet中第一行的值，存在List中
	 * @return
	 * @throws POIException
	 */
	public static List<String> gainColNameList(HSSFSheet sheet) throws Exception{
		if(sheet == null){
			return null;
		}
 		List<String> titles = new ArrayList<String>();
 		
 		//第一行代表表头
		HSSFRow row=sheet.getRow(0);
 		HSSFCell cell;
 		
 		if(sheet!=null){
 			for(int i=0;row.getCell(i)!=null;i++){
 				cell=row.getCell(i);
 				titles.add(getStringCellValue(cell));
 			}
 		}
 		return titles;
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * @param cell Excel单元格
	 * @return String 单元格数据内容
	 */
	public static String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell == null || strCell.equals("")) {
			return "";
		}
		return strCell;
	}
	/**
	 * 导出数据到excel
	 * （常用与错误格式数据，写入错误详细，返回给用户）
	 * @param wb
	 * @param data
	 */
	public static void exportDataToExcel(HSSFWorkbook wb, List<Map<String, Object>> data){
		
		HSSFSheet sheet = wb.getSheetAt(0);
		
		//  设置新增单元格的样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    style.setFillForegroundColor(HSSFColor.ROSE.index);
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setWrapText(true);  // 设置自动换行
        
       
		HSSFRow row0 = sheet.getRow(0);
	    // 在原有数据上加上一列，因此要得到 列的值
	    int cellNum = row0.getLastCellNum() - row0.getFirstCellNum() + 1;
	    // 得到 当前的sheet，并设置新增列的宽度
		sheet.setColumnWidth(cellNum, (80 * 256));
		
		HSSFCell cell0 = row0.createCell(cellNum);
		
		//poi3.5版本以后去掉了setEncoding ,暂时注释
		//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
	    cell0.setCellStyle(style);
		cell0.setCellValue("Result");
		
        // 循环错误的数据，在对应行，加上错误列的标识
		for (int i=0; i<data.size(); i++){
			Map map = data.get(i);
			HSSFRow row = sheet.getRow(Integer.parseInt(map.get("pk_id").toString()));  // 因为表中的主键和行号是匹配的
            
            HSSFCell cell = row.getCell(cellNum) != null ? row.getCell(cellNum) : row.createCell(cellNum);
			
            //暂时注释
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellStyle(style);
            cell.setCellValue(cell.getStringCellValue() != null && !cell.getStringCellValue().trim().equals("") ? cell.getStringCellValue() + "***" + map.get("Result").toString() : map.get("Result").toString());
		}
	}
	
	
	
	
	/**
	 * 删除文件后删除文件夹 
	 * @param folderPath
	 */
	public static void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //删除完里面所有内容
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //删除空文件夹
	     } catch (Exception e) {
	     }
	}

	/**
	 * 删除指定文件夹下所有文件 
	 * @param path
	 * @return
	 */
    public static boolean delAllFile(String path) {
       boolean flag = false;
       File file = new File(path);
       if (!file.exists()) {
         return flag;
       }
       if (!file.isDirectory()) {
         return flag;
       }
       String[] tempList = file.list();
       File temp = null;
       for (int i = 0; i < tempList.length; i++) {
          if (path.endsWith(File.separator)) {
             temp = new File(path + tempList[i]);
          } else {
              temp = new File(path + File.separator + tempList[i]);
          }
          if (temp.isFile()) {
             temp.delete();
          }
          if (temp.isDirectory()) {
             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
             delFolder(path + "/" + tempList[i]);//再删除空文件夹
             flag = true;
          }
       }
       return flag;
     }
    
    /**
	 * spring mvc中上传文件 dirPath-文件存储路径 此方法返回的文件名为随机数加真实的文件名
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String oneFileUpload(HttpServletRequest request, String dirPath) throws Exception {

		String path = "";
		String fileName = "";
		List<String> pathList = new ArrayList<String>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmmsssss");
		long key = PrimaryKeyGenerator.getLongKey();
		if (multipartResolver.isMultipart(request)) {// 判断 request
														// 是否有文件上传,即多部分请求...
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multiRequest.getFileNames();

			File filePath = new File(dirPath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile((String) iter.next());
				fileName = file.getOriginalFilename();
				// 文件中文名问题

				// String fileName1=new
				// String(fileName.getBytes("ISO8859-1"),"GBK");
				// fileName=new String(fileName.getBytes("ISO8859-1"),"utf-8");
				if ("".equals(fileName)) {
					continue;
				}
				// 组成新的存放路径
				
				 fileName = key+fileName.substring(fileName.lastIndexOf("."));

				path = dirPath + "\\" + fileName;
				File localFile = new File(path);
				// 将上传文件写入服务器指定的文件中
				file.transferTo(localFile);
				pathList.add(path);
			}
		}

		return fileName;
	}
	
	
	 
    /**
	 * spring mvc中上传文件 dirPath-文件存储路径 此方法返回的文件名为随机数加真实的文件名
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String oneFileUploadName(HttpServletRequest request, String dirPath) throws Exception {

		String path = "";
		String fileName = "";
		List<String> pathList = new ArrayList<String>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmmsssss");
		long key = PrimaryKeyGenerator.getLongKey();
		if (multipartResolver.isMultipart(request)) {// 判断 request
														// 是否有文件上传,即多部分请求...
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multiRequest.getFileNames();

			File filePath = new File(dirPath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile((String) iter.next());
				fileName = file.getOriginalFilename();
				// 文件中文名问题

				// String fileName1=new
				// String(fileName.getBytes("ISO8859-1"),"GBK");
				 fileName=new String(fileName.getBytes("ISO8859-1"),"utf-8");
				if ("".equals(fileName)) {
					continue;
				}
				// 组成新的存放路径
				
				// fileName = key+fileName.substring(fileName.lastIndexOf("."));

				path = dirPath + "\\" + fileName;
				File localFile = new File(path);
				// 将上传文件写入服务器指定的文件中
				file.transferTo(localFile);
				pathList.add(path);
			}
		}

		return fileName;
	}
	
	
    /**
     * 在线预览pdf
     * @param response
     * @param path
     * @param fileAllName
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void onLinePdf(HttpServletResponse response, String path,
            String fileAllName) throws FileNotFoundException, IOException
    {
    	response.setContentType("application/pdf;charset=UTF-8");
    	OutputStream out = response.getOutputStream();
        File file = new File(path, fileAllName);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        byte[] b = new byte[1024*100];
        while ( in.available() > 0)
        {
            if (in.available() < 1024*100)
            {
                byte[] lastByte = new byte[in.available()];
                in.read(lastByte);
                out.write(lastByte);
            }
            else
            {
                in.read(b);
                out.write(b);
            }
        }
        out.flush();
        out.close();
        
    }
    
    /**
     * 在线预览pdf
     * @param response
     * @param path
     * @param fileAllName
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void onLineErrorPdf(HttpServletResponse response, String path) throws FileNotFoundException, IOException
    {
    	response.setContentType("application/pdf;charset=UTF-8");
    	OutputStream out = response.getOutputStream();
        File file = new File(path);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        byte[] b = new byte[1024*100];
        while ( in.available() > 0)
        {
            if (in.available() < 1024*100)
            {
                byte[] lastByte = new byte[in.available()];
                in.read(lastByte);
                out.write(lastByte);
            }
            else
            {
                in.read(b);
                out.write(b);
            }
        }
        out.flush();
      //  out.close();
        
    }
    
    
    /**
     * 拷贝文件并重命名
     * @param oldPath
     * @param fileName
     * @param newpath
     * @return
     * @throws Exception
     */
    public static String CopyFile(String oldPath,String fileName,String newpath) throws Exception{
    	String pathTemp =  oldPath +"\\" +fileName;
    	long key = PrimaryKeyGenerator.getLongKey();
    	String newFileName = key+fileName.substring(fileName.lastIndexOf("."),fileName.length());
    	File filePathx = new File(newpath);
		if (!filePathx.exists()) {
			filePathx.mkdirs();
		}
    	newpath = newpath + "\\" + newFileName;
    	FileInputStream is = new FileInputStream(new File(pathTemp));  
         FileOutputStream os = new FileOutputStream(new File(newpath));  

         byte[] buf = new byte[1024];  

         while (is.read(buf) != -1) {  
             os.write(buf);  
         } 
         os.flush();
         os.close();
         is.close();
         
		return newFileName;
    	
    }
	
}
