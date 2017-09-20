package com.lquan.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;




/**
 * 拦截器，过滤掉不合法的路径
 * @author liuqaun
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
		/**
		//return true;///
		System.out.println(request.getServletPath().toString());
		//接口的放行
		if (request.getServletPath().startsWith("/home/")||
				request.getServletPath().startsWith("/active/")||
				request.getServletPath().startsWith("/connnect/")||
				request.getServletPath().startsWith("/department/")||
				request.getServletPath().startsWith("/meet/")||
				request.getServletPath().startsWith("/notice/")||
				request.getServletPath().startsWith("/rule/")||
				request.getServletPath().startsWith("/staff/")||
				request.getServletPath().startsWith("/tour/")||
				request.getServletPath().startsWith("/senderMail/")||
				request.getServletPath().startsWith("/upload/")||
				request.getServletPath().startsWith("/manage/seeManage")
				){
			return true;
		}
		//用户登录--放行
		if (request.getServletPath().startsWith("/user/toMain") || request.getServletPath().startsWith("/user/toLogin")) {
			// add lquan
			// 在登录和退出的时候对session中的所有的
			if (request.getSession().getAttribute(Constants.SESSION_USER) != null)
				request.getSession().removeAttribute(Constants.SESSION_USER);
			// --end
			
			return true;
		}
		// 静态资源访问--放行
		if(request.getServletPath().startsWith("/css/") || 
				request.getServletPath().startsWith("/img/") ||
				request.getServletPath().startsWith("/js/") ||
				request.getServletPath().startsWith("/jspage/") ||
				request.getServletPath().startsWith("/vm/") ||
				request.getServletPath().startsWith("/upload/") ||
				request.getServletPath().startsWith("/logs/")){
			return true;
		}
		// session不为空--放行
		if (request.getSession().getAttribute(Constants.SESSION_USER) != null) {
			return true;
		}
		// --end
		//其他的操作则直接跳转到登录界面
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write("<script>parent.location.href='"+request.getContextPath()+"/user/toLogin'</script>");			
			out.flush();			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}			
		return false;
		**/
	}
}
