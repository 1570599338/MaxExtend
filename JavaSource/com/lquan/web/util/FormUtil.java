package com.lquan.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.lquan.common.Constants;
import com.lquan.util.DateTimeUtil;
import com.lquan.util.Utils;
import com.lquan.util.Validator;



/**
* @ClassName: util 
* @Description: web下Form通用公共工具类 
* @author wuzhenfang Email:wzfbj2008@163.com 
* @date 2013-5-14 上午9:57:21
 */
public class FormUtil {

	/**
	* @Description:  获取表单中的字符串类型的数据
	* @author wuzhenfang Email:wzfbj2008@163.com  
	* @param @param request
	* @param @param filedName
	* @return String    返回类型 
	* @throws
	 */
    public static String getStringFiledValue(HttpServletRequest request,String filedName){
    	return Validator.isEmpty(request.getParameter(filedName))?null:Utils.reCharset(request.getParameter(filedName));
    }
    
    /**
    * @Description: 获取表单中的整形类型的数据
    * @author wuzhenfang Email:wzfbj2008@163.com  
    * @param @param request
    * @param @param filedName
    * @return int    返回类型 
    * @throws
     */
    public static int getIntegerFiledValue(HttpServletRequest request,String filedName){
    	String value = getStringFiledValue(request, filedName);
    	return value!=null?Integer.valueOf(value):0;
    }
    /**
    * @Description: 获取表单中的长整形类型的数据
    * @author wuzhenfang Email:wzfbj2008@163.com  
    * @return long    返回类型 
    * @throws
     */
    public static long getLongFiledValue(HttpServletRequest request,String filedName){
    	String value = getStringFiledValue(request, filedName);
    	return value!=null?Long.valueOf(value):0l;
    }
    /**
    * @Description: 获取表单中的浮点型类型的数据
    * @author wuzhenfang Email:wzfbj2008@163.com  
    * @return float    返回类型 
    * @throws
     */
    public static float getfloatFiledValue(HttpServletRequest request,String filedName){
    	String value = getStringFiledValue(request, filedName);
    	return value!=null?Float.valueOf(value):0f;
    }
    /**
     * @throws ParseException 
    * @Description: 获取表单中日期类型的数据
    * @author wuzhenfang Email:wzfbj2008@163.com  
    * @return Date    返回类型 
    * @throws
     */
    public static Date getDateFiledValue(HttpServletRequest request,String filedName){
    	String value = getStringFiledValue(request, filedName);
    	return value!=null? DateTimeUtil.parseDateDayFormat(value):null;
    }
    
}
