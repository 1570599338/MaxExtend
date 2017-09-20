<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String ctxPath =request.getContextPath();
request.setAttribute("ctxPath", ctxPath);
String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ ctxPath + "/";
request.setAttribute("basePath",basePath);

//清理缓存----绕过代理服务器
response.setHeader("Pragma", "No-cache"); 
response.setHeader("Cache-Control", "no-cache"); 
response.setDateHeader("Expires", 0);
%>


</script>