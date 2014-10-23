package com.rest.tiis.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rest.tiis.beans.User;
import com.rest.tiis.service.UserService;
import com.rest.tiis.util.AjaxResponseUtil;
import com.rest.tiis.util.CommonUtiles;
import com.rest.tiis.util.MD5;
/**
 * @project:					TIIS 
 * @Title: 						Login.java 		
 * @Package 					com.rest.tiis.controller		
 * @Description: 				用户登录、注册、找回密码等操作
 * @author 						杨贵松   
 * @date 						2014年3月24日 下午5:19:38 
 * @version 					V1.0
 */
@Controller
@RequestMapping("index")
public class LoginController {
	private static final Logger log = Logger.getLogger(LoginController.class.getName());
	@Autowired
	private UserService userService;

	/**
	 * @Title: 				login 
	 * @author 				杨贵松
	 * @date 				2014年3月24日 下午5:20:47
	 * @Description: 		用户登录
	 * @return 
	 * ModelAndView 				返回
	 */
	@RequestMapping(value = "login.json",method=RequestMethod.POST)
	public ModelAndView login(@RequestBody String userStr , HttpSession session) {
		ModelAndView modelView=new ModelAndView();
		String[] uStr = userStr.split(",");
		User user = new User();
		try {
			user = userService.login(uStr[0]);
			if (user == null) {
				modelView.addObject("null");
				return modelView;
			} else {
				if(user.getPassword().equals(MD5.getMD5Str(uStr[1].trim()))){
					session.setAttribute("user", user);
					modelView.addObject("ok");
				}else{
					modelView.addObject("pswError");
				}
				return modelView;
			}
		} catch (Exception e) {
			modelView.addObject("error");
			return modelView;
		}
	}
	
	/**
	 * @Title: 				login 
	 * @author 				杨贵松
	 * @date 				2014年3月24日 下午5:20:47
	 * @Description: 		验证用户名是否存在
	 * @return 
	 * ModelAndView 				返回
	 */
	@RequestMapping(value = "validUserName.json",method=RequestMethod.POST )
	public ModelAndView validUserName(@RequestBody String userName) {
		ModelAndView modelView=new ModelAndView();
		User user = new User();
		try {
			user = userService.validUserName(userName);
			if (user == null) {
				modelView.addObject("user","null");
				return modelView;
			} else {
				modelView.addObject("user","ok");
				return modelView;
			}
		} catch (Exception e) {
			modelView.addObject("error");
			return modelView;
		}
	}
	
	/**
	 * @Title: 				register 
	 * @author 				杨贵松
	 * @date 				2014年3月26日 上午10:56:23
	 * @Description: 		用户注册
	 * @return 
	 * ModelAndView 				返回
	 */
	@RequestMapping(value="register.json",method=RequestMethod.POST)
	public ModelAndView register(@RequestBody String userStr){
		ModelAndView model = new ModelAndView();
		User user = new User();
		try {
			if(!userStr.equals("") || userStr != null){
				user = (User) AjaxResponseUtil.toObject(userStr, User.class);
				
				Date date = CommonUtiles.getSystemDateTime();
				user.setPassword(MD5.getMD5Str(user.getPassword().trim()));
				user.setCreatetime(date);
				user.setModifytime(date);
				user.setRole(1);
				user.setRightContent("01");
				userService.register(user);
				model.addObject("ok");
				
				log.info("新用户："+user.getLoginname()+"注册成功!");
			}
		} catch (Exception e) {
			model.addObject("error");
			log.info("新用户："+user.getLoginname()+"尝试注册异常失败!"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				exit 
	 * @author 				杨贵松
	 * @date 				2014年3月30日 下午11:35:08
	 * @Description: 		退出当前用户
	 * @return 
	 * ModelAndView 				返回
	 */
	@RequestMapping(value="exit.json",method=RequestMethod.POST)
	public ModelAndView exit(HttpSession session){
		ModelAndView model = new ModelAndView();
		try {
			session.removeAttribute("user");
			model.addObject("ok");
		} catch (Exception e) {
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				test 
	 * @author 				杨贵松
	 * @date 				2014年3月24日 下午5:21:08
	 * @Description: 		域名访问时用户已登录则跳转到主页，否则跳转到登录界面
	 * @return 
	 * ModelAndView 				返回
	 */
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String index(HttpSession session){
		User user = (User) session.getAttribute("user");
		if(null == user){
			return "redirect:/index/login";
		}
		return "redirect:/index/main";
	}
	/**
	 * @Title: 				test1 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 上午12:08:12
	 * @Description: 		用户未登录跳转到登录界面
	 * @param session
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String login(){
		return "/login";
	}
	
	/**
	 * @Title: 				test2 
	 * @author 				杨贵松
	 * @date 				2014年4月19日 上午12:08:24
	 * @Description: 		域名访问时用户已登录则跳转到主页
	 * @param session
	 * @return 
	 * String 				返回
	 */
	@RequestMapping(value="main",method=RequestMethod.GET)
	public String main(){
		return "/main";
	}
}
