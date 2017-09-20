package com.lquan.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * 
 * @author liuquan
 *只是在长期的写导出excel的时候，总感觉每次都要一列一列的手动插入很是纠结
 *为了把偷懒的精神发扬光大就只能在偷会懒了，鄙人第一次写类似工具方法，写的不
 *好大神不要喷，
 */
public class WriteExcelUtil{
	
	/**
	 * 此方法是只能在一个sheet并且是带有自动加入序列号——第一列为序列号
	 * @param wookBook
	 * @param bodyCellStyle		样式
	 * @param list				传入的数据为List<Map<String, Object>>
	 * @param Keys				Map数据的key				
	 * @param startRowIndex		写入excel的实际起始行
	 * @author liuquan 2015年4月10日11:45:21
	 */
	public static void ExportDateOneSheetWithCount(HSSFWorkbook wookBook, HSSFCellStyle bodyCellStyle,List<Map<String, Object>> list,String[] Keys,int startRowIndex){
		
		// 页面显示的插入的行和程序中的插入行进行转换
		int startRowIndex_insider = startRowIndex-1;
 	   
		wookBook.getSheetAt(0).setColumnWidth(startRowIndex_insider, 6000);
        long count=1;
	    for (Map<String, Object> map:list) {  
          
          HSSFRow row = wookBook.getSheetAt(0).createRow((short)startRowIndex_insider);  
          wookBook.getSheetAt(0).setColumnWidth(startRowIndex_insider, 6000);
          
          // 插入数据的序号
          HSSFCell cell0 = row.createCell((short)0); 
          cell0.setCellStyle(bodyCellStyle); 
          cell0.setCellValue(count);  
          
          // 因为集合中的数据是无需的而数组的数据是有序的，因此，需要进行的对比防止数据插入有误
          Set<String> keystr = map.keySet();
          int arrayNum= 0;
          int countx = Keys.length;
          while(countx>0){
        	  for(String key :keystr){
        		  if(key.equals(Keys[arrayNum])){
	        		  HSSFCell cell1 = row.createCell((short)(arrayNum)); 
	                  cell1.setCellStyle(bodyCellStyle); 
	                  cell1.setCellValue(Validator.isEmpty(map.get(key))?"":map.get(key).toString()); 
	                  arrayNum++;
	                  countx--;
	                  break;
        		  }
        	  } 
          }
          count++;
          startRowIndex_insider++;
	   }
 	   
    }
	
	
	/**
	 * 此方法没前面的序号，直接把数据导入到excel里面，仅适用于一个sheet
	 * @param wookBook	
	 * @param bodyCellStyle		样式
	 * @param list				获取的要导入的数据
	 * @param Keys				Map中的key
	 * @param startRowIndex		excel的行数
	 * @author liuquan 	2015年5月6日20:30:47
	 */
	public static void ExportDateOneSheet(HSSFWorkbook wookBook, HSSFCellStyle bodyCellStyle,List<Map<String, Object>> list,String[] Keys,int startRowIndex){
		// 页面显示的插入的行和程序中的插入行进行转换
		int startRowIndex_insider = startRowIndex-1;
 	   
		wookBook.getSheetAt(0).setColumnWidth(startRowIndex_insider, 6000);
	    for (Map<String, Object> map:list) {  
          
          HSSFRow row = wookBook.getSheetAt(0).createRow((short)startRowIndex_insider);  
          wookBook.getSheetAt(0).setColumnWidth(startRowIndex_insider, 6000);
          
          // 因为集合中的数据是无需的而数组的数据是有序的，因此，需要进行的对比防止数据插入有误
          Set<String> keystr = map.keySet();
          int arrayNum= 0;
          int count = Keys.length;
          while(count>0){
        	  for(String key :keystr){
        		  if(key.equals(Keys[arrayNum])){
	        		  HSSFCell cell1 = row.createCell((short)(arrayNum)); 
	                  cell1.setCellStyle(bodyCellStyle); 
	                  cell1.setCellValue(Validator.isEmpty(map.get(key))?"":map.get(key).toString()); 
	                  arrayNum++;
	                  count--;
	                  break;
        		  }
        	  } 
          }
        	 
          
          
          
          startRowIndex_insider++;
	   }
 	   
    }
	
	
	
	
	
	
	
	


}

