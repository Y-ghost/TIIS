package com.rest.tiis.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rest.tiis.listener.Login;

/**
 * @project:					TIIS 
 * @Title: 						SystemMngController.java 		
 * @Package 					com.rest.tiis.controller		
 * @Description: 				系统管理公共类
 * @author 						杨贵松   
 * @date 						2014年4月8日 下午5:39:08 
 * @version 					V1.0
 */
@Controller
@RequestMapping("system")
public class SystemMngController {
	private static final Logger log = Logger.getLogger(SystemMngController.class.getName());
	
	/**
	 * @Title: 				selectUserInfo 
	 * @author 				杨贵松
	 * @date 				2014年4月6日 下午10:44:16
	 * @Description: 		用户个人信息
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="findNowLocalTime.json",method=RequestMethod.POST)
	public ModelAndView findNowLocalTime(){
		ModelAndView model = new ModelAndView();
		try {
			Date date = new Date();
			model.addObject(date);
		} catch (Exception e) {
			model.addObject("error");
			log.error("获取系统时间异常!"+e);
		}
		return model;
	}
}
