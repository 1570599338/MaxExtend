package com.lquan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import snt.common.encrypt.EncodeUtil;

public class Utils {
    
    /**
     * 用户密码加密字符串.
     * 
     * @param loginPassword
     * @return string
     * @throws Exception
     */
    public static String encryptPassword(String loginPassword) throws Exception
    {
    	return snt.common.string.StringUtil.MD5Encode(loginPassword);
    }
    
    /**
     * 加密字符串.
     * 
     * @param str
     * @return
     * @throws Exception
     */
    public static String encrypt(String str) throws Exception
    {
        return EncodeUtil.encodeStr(str);
    }
    
    /**
     * 解密字符串.
     * 
     * @param str
     * @return
     * @throws Exception
     */
    public static String decrypt(String str) throws Exception
    {
        return EncodeUtil.decodeStr(str);
    }
    /**
     * 取随机字符串, 长度由length控制, 返回的字符串英文只可能26个字母的大小写或数字.
     * 
     * @param length
     * @return
     */
    public static String getRandomString(int length)
    {
        if (length < 1)
            return "";
        char[] c = new char[length];
        for (int i=0; i<length; i++)
            c[i] = getRandomChar();
        return new String(c);
    }
    
    /**
     * 取随机字符, 只可能会返回英文26个字母的大小写或数字.
     * @return
     */
    public static char getRandomChar()
    {
        SecureRandom rand = new SecureRandom();
        int i = rand.nextInt(123);
        while((i>57 && i<65) || (i>90 && i<97) || i<48)
            i = rand.nextInt(123);
        return (char) i;
    }
    
    /**
     * 转为utf-8 样式
     * @param befor
     * @return
     */
    public static String reCharset(String befor){
      try {   
    	    if(null!=befor && !"".equals(befor)){
             return new String(befor.getBytes("ISO-8859-1"), "UTF-8"); 	
    	    }else return null; 
     
           } catch (UnsupportedEncodingException e) {   
            e.printStackTrace();   
               return null;   
           }  
    }
    
    /**
     *获得客户端真实IP地址的方法
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) { 
        String ip = request.getHeader("x-forwarded-for"); 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        } 
        return ip; 
    } 
 
    /**
     * 通过Ip地址找到 相应的MaC 地址
     * @param ip
     * @return
     */
    public static String getMACAddress(String ipAddress) {
        	if(ipAddress.equalsIgnoreCase("localhost")||ipAddress.equalsIgnoreCase("127.0.0.1")){
        		return getLocalMAC();
        	}
        	String address = "ERROR";
            String os = System.getProperty("os.name");
            if (os != null && os.startsWith("Windows")) {
                try {
                    String command = "cmd.exe /c nbtstat -a "+ipAddress;
                    Process p = Runtime.getRuntime().exec(command);
                    BufferedReader br =
                            new BufferedReader(
                                    new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.indexOf("MAC") > 0) {
                            int index = line.indexOf("=");
                            index+=2;
                            address = line.substring(index);
                            break;
                        }
                    }
                    br.close();
                    return address.trim();
                } catch (IOException e) {}
            }
            return address;
        }
    
    /**
     * 得到本地访问的Mac地址
     * @return
     */
    public static String getLocalMAC(){
    	InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			String ip=addr.getHostAddress().toString();//获得本机IP
	    	return getMACAddress(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "ERROR";
    }
    
	/**
	 *@Description: 在验证之前需要去掉一些字符的空格，回车，换行，制表符。
	 * @param str
	 * @param middleSpace 如果为ture 需要去除中间的空格
	 * @return
	 */
	public static String replaceBlank(String str,boolean middleSpace){
		String patStr = "\\s*|\t|\r|\n";
		String patStr2 = "\\t*|\r|\n";
		if(middleSpace){
			Pattern p = Pattern.compile(patStr);
			Matcher m = p.matcher(str);
			return m.replaceAll("");
		}else{
			Pattern p = Pattern.compile(patStr2);
			Matcher m = p.matcher(str);
			return m.replaceAll("").trim();
		}
		
	}
	
	/**
	 * 获取字符串的长度，中文占一个字符,英文数字占半个字符
	 * @param value 指定的字符串
	 * @return 字符串的长度
	 */
	public static int StringByteLength(String value) {
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < value.length(); i++) {
			// 获取一个字符
			String temp = value.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为2
				valueLength += 2;
			} else {
				// 其他字符长度为1
				valueLength += 1;
			}
		}
		// 进位取整
		return valueLength;
	}
}
