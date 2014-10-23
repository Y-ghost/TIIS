package com.rest.tiis.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rest.tiis.service.UserService;
/**
 * @project:					TIIS 
 * @Title: 						IndexController.java 		
 * @Package 					com.rest.tiis.controller		
 * @Description: 				用户登录、注册、找回密码等操作
 * @author 						杨贵松   
 * @date 						2014年3月24日 下午5:19:38 
 * @version 					V1.0
 */
@Controller
@RequestMapping("indexs")
public class IndexController {
	@Autowired
	private UserService userService;
	/**
	 * @Title: 				test 
	 * @author 				杨贵松
	 * @date 				2014年3月24日 下午5:21:08
	 * @Description: 		域名访问时用户已登录则跳转到主页，否则跳转到登录界面
	 * @return 
	 * ModelAndView 				返回
	 */
	@RequestMapping(value="redirect",method=RequestMethod.GET)
	public String redirect(){
		return "/redirect";
	}
	/**
	 * @Title: 				statistics 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 上午12:19:58
	 * @Description: 		打开统计分析页面
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="statistics",method=RequestMethod.GET)
	public String statistics(){
		return "/statistics";
	}
	/**
	 * @Title: 				systemSet 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 上午12:20:29
	 * @Description: 		打开系统设置页面
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="systemSet",method=RequestMethod.GET)
	public String systemSet(){
		return "/systemSet";
	}
	/**
	 * @Title: 				messages 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 上午12:23:28
	 * @Description: 		打开消息中心页面 
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="messages",method=RequestMethod.GET)
	public String messages(){
		return "/messages";
	}
	/**
	 * @Title: 				forgetPasswordMail 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 上午12:37:43
	 * @Description: 		跳转到forgetPasswordMail页面
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="forgetPasswordMail",method=RequestMethod.GET)
	public String forgetPasswordMail(){
		return "/user/forgetPasswordMail";
	}
	/**
	 * @Title: 				3GForgetPasswordMail 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 上午12:37:43
	 * @Description: 		跳转到3GForgetPasswordMail页面
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="3GForgetPasswordMail",method=RequestMethod.GET)
	public String wapForgetPasswordMail(){
		return "/user/3GForgetPasswordMail";
	}
	/**
	 * @Title: 				forgotPassword 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 上午12:58:01
	 * @Description: 		提示邮件发送成功
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="forgotPassword",method=RequestMethod.GET)
	public String forgotPassword(String email,HttpSession session){
		session.setAttribute("email", email);
		return "/user/forgotPassword";
	}
	/**
	 * @Title: 				wapForgotPassword 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 上午12:58:01
	 * @Description: 		wap提示邮件发送成功
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="3GForgotPassword",method=RequestMethod.GET)
	public String wapForgotPassword(String email,HttpSession session){
		session.setAttribute("email", email);
		return "/user/3GForgotPassword";
	}
	
	/**
	 * @Title: 				forgotPasswordErr 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 下午8:53:11
	 * @Description: 		发送邮件失败
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="forgotPasswordErr",method=RequestMethod.GET)
	public String forgotPasswordErr(){
		return "/user/forgotPasswordErr";
	}
	
	/**
	 * @Title: 				resetPasswordErr 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 下午11:26:41
	 * @Description: 		 密码修改错误
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="resetPasswordErr",method=RequestMethod.GET)
	public String resetPasswordErr(){
		return "/user/resetPasswordErr";
	}
	
	/**
	 * @Title: 				error404 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 下午3:28:54
	 * @Description: 		404错误提示界面
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="404",method=RequestMethod.GET)
	public String error404(){
		return "/error/error";
	}
	
	/**
	 * @Title: 				error500 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 下午3:28:50
	 * @Description: 		500错误提示界面
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="500",method=RequestMethod.GET)
	public String error500(){
		return "/error/error500";
	}
	
	/**
	 * @Title: 				register 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 下午9:43:15
	 * @Description: 		返回注册页面
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="register",method=RequestMethod.GET)
	public String register(){
		return "/register";
	}
	
	/**
	 * @Title: 				wapRegister 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 下午9:43:15
	 * @Description: 		3G注册页面
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="3GRegister",method=RequestMethod.GET)
	public String wapRegister(){
		return "/3GRegister";
	}
	
	/**
	 * @Title: 				redirectLogin 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 下午11:50:32
	 * @Description: 		修改密码成功跳转页面 
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="redirectLogin",method=RequestMethod.GET)
	public String redirectLogin(HttpSession session){
		session.removeAttribute("user");
		return "/user/redirectLogin";
	}
	
	/**
	 * @Title: 				modifyPassword
	 * @author 				杨贵松
	 * @date 				2014年4月23日 下午11:20:14
	 * @Description: 		修改密码
	 * @param session
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="modifyPassword",method=RequestMethod.GET)
	public String modifyPassword(){
		return "/user/modifyPassword";
	}
}
