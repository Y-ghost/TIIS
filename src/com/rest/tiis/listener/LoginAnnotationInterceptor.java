package com.rest.tiis.listener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.rest.tiis.beans.User;
import com.rest.tiis.util.AjaxResponseUtil;

/**
 * @project:					TIIS 
 * @Title: 						a.java 		
 * @Package 					com.rest.tiis.listener		
 * @Description: 				检查是否已经登录
 * @author 						杨贵松   
 * @date 						2014年2月21日 上午12:43:06 
 * @version 					V1.0
 */
public class LoginAnnotationInterceptor extends HandlerInterceptorAdapter {

	final Logger logger = Logger.getLogger(LoginAnnotationInterceptor.class);

	/**
	 * 验证用户是否登录
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

		HandlerMethod handler2 = (HandlerMethod) handler;
		Login login = handler2.getMethodAnnotation(Login.class);

		if (null == login) {
			// 没有声明权限,放行
			return true;
		}

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (null == user) {
			// 需要登录
			if (login.value() == ResultTypeEnum.page) {
				//采用传统页面进行登录提示
				try {
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (login.value() == ResultTypeEnum.json) {
				//采用ajax方式的进行登录提示
				AjaxResponseUtil.toJson(response, "noLogin");
			}
			return false;
		}

		return true;

	}
}
