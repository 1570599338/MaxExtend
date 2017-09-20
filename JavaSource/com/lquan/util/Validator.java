package com.lquan.util;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import snt.common.web.util.WebUtils;

/**
 * 
 * 通用验证类,因为有验证每个项目里都在重复着编写，<br/>
 * 时间长了就想写一个通用的，每个项目都能用上,<br/>
 * 敝人才疏学浅写的验证方法并不是所有情况都适用。<br/>
 * 望后来者加以修补!
 * 
 * @author <a href="mailto:huangqiuxiang@sinotrust.cn">hqx</a>
 * 
 * @since 2007-05-27
 * 
 * @version 2.0
 * 
 * Copyright sinotrust.cn 
 * 
 */
public class Validator {
	/**
	 * 判断当前日期是否是客户指定的定时统计时间
	 * @param propkey 系统资源文件如：misc.properties中的key值
	 * @return 当前日期否是定时计算时间，true是计算时间，false不是计算时间
	 */
	public static boolean isTimeToStat(String propkey) throws IllegalArgumentException {
		String timeconfig = WebUtils.getModuleProperty(propkey);
		if(timeconfig != null){
			timeconfig = timeconfig.replaceAll("　", " ");
			String[] str = timeconfig.trim().split(" ");
			Calendar c = Calendar.getInstance();
			Integer num = null; 
			if("DAY_OF_WEEK".equals(str[0])){
				num = c.get(Calendar.DAY_OF_WEEK);
			} else if("DAY_OF_MONTH".equals(str[0])){
				num = c.get(Calendar.DAY_OF_MONTH);
			} else if("DAY_OF_YEAR".equals(str[0])){
				num = c.get(Calendar.DAY_OF_YEAR);
			} else {
				throw new IllegalArgumentException("参数值错误，目前只支持DAY_OF_WEEK、DAY_OF_MONTH、DAY_OF_YEAR三种参数");
			}
			if(isInteger(str[1]) && num == Integer.parseInt(str[1])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断对象是否为空,这里只写了本人最常用的数据类型的判断
	 * @param arg
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Object arg){
		if(arg==null) return true;
		if (arg instanceof String) {//字符串
			if("".equals(((String)arg).trim()))//空字符串
				return true;
		}else if(arg instanceof Collection){
			Iterator it = ((Collection)arg).iterator();
			while(it.hasNext()){
				Object obj = it.next();
				if(obj!=null && !"".equals(obj))
					return false;
			}
			return true;
		}else if(arg instanceof Object[]){
			for(int i=0; i<((Object[])arg).length; i++){
				Object obj = ((Object[])arg)[i];
				if(obj!=null && !"".equals(obj))
					return false;
			}
			return true;
		} else if(arg instanceof Number 
				|| arg instanceof Boolean 
				|| arg instanceof Date){
			return false;
		} else{
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为电子邮箱格式
	 * @param arg
	 * @return
	 */
	public static boolean isEmail(String arg){
		return (arg.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$"));
	}
	/**
	 * 判断是否为手机格式
	 * @param arg
	 * @return
	 */
	public static boolean isMobile(String arg){
		return (arg.matches("^((\\(\\d{3}\\))|(\\d{3}\\-))?13[0-9]\\d{8}|15[0-9]\\d{8}|18[0-9]\\d{8}$"));
	}
	/**
	 * 判断是否是电话格式
	 * @param arg
	 * @return
	 */
	public static boolean isTelephone(String arg){
		return (arg.matches("^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$"));
	}
	/**
	 * 是否是货币格式
	 * @param arg
	 * @return
	 */
	public static boolean isCurrency(String arg){
		return (arg.matches("^\\d+(\\.\\d+)?$"));
	}
	
	public static boolean isDouble(String arg){
		return (arg.matches("^[-|+]?\\d+\\.(\\d+)?$"));
	}
	
	/**
	 * 是否数字
	 * @param arg
	 * @return
	 */
	public static boolean isNumber(String arg) {
		return (arg.matches("^[-|+]?\\d+(\\.\\d+)?$"));
	}
	/**
	 *@Description: 是否为区号
	 * @param arg
	 * @return
	 */
	public static boolean isAreaCode(String arg){
		return (arg.matches("^0?([1-9]\\d|[0-9][13579]\\d|[0-9][24680]\\d{2})$"));
	}
	
	/**
	 * 是否是邮编号码
	 * @param arg
	 * @return
	 */
	public static boolean isZip(String arg){
		return (arg.matches("^[1-9]\\d{5}$"));
	}
	
	/**
	 * 是否是身份证号码
	 * @param arg
	 * @return
	 */
	public static boolean isIDCard(String arg){
		return (arg.matches("^\\d{15}(\\d{2}[A-Za-z0-9])?$"));
	}
	
	/**
	 * 是否是中文
	 * @param arg
	 * @return
	 */
	public static boolean isChinese(String arg){
		return (arg.matches("^[\u4e00-\u9fa5]+$"));
	}
	
	/**
	 * 是否是正负整数
	 * @param arg
	 * @return
	 */
	public static boolean isInteger(String arg){
		return (arg.matches("^[-\\+]?\\d+$"));
	}
	
	/**
	 * 是否是正整数
	 * @param arg
	 * @return
	 */
	public static boolean isPlusInteger(String arg){
		return (arg.matches("^[+]?\\d+$"));
	}
	/**
	 * 是否是负整数
	 * @param arg
	 * @return
	 */
	public static boolean isNegativeInteger(String arg){
		return (arg.matches("^[-]?\\d+$"));
	}
	
	/**
	 * 是否日期
	 * @param arg
	 * @return
	 */
	public static boolean isDate(String arg) {
		return (arg.matches("((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))"));
	}
	/**
	 *@Description:验证日期时间格式
	 * @param arg
	 * @return
	 */
	public static boolean isDateTime(String arg){
		String datetim1="((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])\\s((([0]?[1-9]|1[0-2])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?( )?(AM|am|aM|Am|PM|pm|pM|Pm))|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?))$)" +
				"|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])\\s((([0]?[1-9]|1[0-2])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?( )?(AM|am|aM|Am|PM|pm|pM|Pm))|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?))$)" +
				"|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])\\s((([0]?[1-9]|1[0-2])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?( )?(AM|am|aM|Am|PM|pm|pM|Pm))|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?))$)" +
				"|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)\\s((([0]?[1-9]|1[0-2])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?( )?(AM|am|aM|Am|PM|pm|pM|Pm))|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?))$)" +
				"|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)\\s((([0]?[1-9]|1[0-2])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?( )?(AM|am|aM|Am|PM|pm|pM|Pm))|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?))$)" +
				"|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)\\s((([0]?[1-9]|1[0-2])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?( )?(AM|am|aM|Am|PM|pm|pM|Pm))|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?))$)" +
				"|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)\\s((([0]?[1-9]|1[0-2])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?( )?(AM|am|aM|Am|PM|pm|pM|Pm))|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?))$)" +
				"|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)\\s((([0]?[1-9]|1[0-2])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?( )?(AM|am|aM|Am|PM|pm|pM|Pm))|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?))$)" +
				"|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)\\s((([0]?[1-9]|1[0-2])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?( )?(AM|am|aM|Am|PM|pm|pM|Pm))|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?))$)" +
				"|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)\\s((([0]?[1-9]|1[0-2])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?( )?(AM|am|aM|Am|PM|pm|pM|Pm))|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?))$)" +
				"|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)\\s((([0]?[1-9]|1[0-2])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?( )?(AM|am|aM|Am|PM|pm|pM|Pm))|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?))$))";
		return (arg.matches(datetim1));
	}
	/**
	 *@Description: 判断是否为时间
	 *   1:01 AM | 23:52:01 | 03.24.36 AM 
	 *   Non-Matches 19:31 AM | 9:9 PM | 25:60:61 
	 * @param arg
	 * @return
	 */
	public static boolean isTime(String arg){
		String timestr ="^((([0]?[1-9]|1[0-2])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?( )?(AM|am|aM|Am|PM|pm|pM|Pm))|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?))$";
		return (arg.matches(timestr));
	}
	/**判断一个字符是否为汉字
	 * @param c
	 * @return
	 */
	public static boolean isChineseCharacter(char c) {
		int v = (int) c;
		return ((v >= 19968) && (v <= 171941));
	}
	
	
	public static void main(String[] args) {
		String date = "1984/09/02 00:00:00";
		String areaCode="010";
		String time = "00:00:01";
		System.out.println(isAreaCode(areaCode));
	}
}
