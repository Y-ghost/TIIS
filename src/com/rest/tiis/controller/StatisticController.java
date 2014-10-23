package com.rest.tiis.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rest.tiis.beans.User;
import com.rest.tiis.listener.Login;
import com.rest.tiis.service.EquipmentDataService;
/**
 * @project:					TIIS 
 * @Title: 						StatisticController.java 		
 * @Package 					com.rest.tiis.controller		
 * @Description: 				统计分析
 * @author 						杨贵松   
 * @date 						2014年6月17日 下午5:48:46 
 * @version 					V1.0
 */
@Controller
@RequestMapping("statistic")
public class StatisticController {
	private static final Logger log = Logger.getLogger(StatisticController.class.getName());
	@Autowired
	private EquipmentDataService equipmentDataService;

	/**
	 * @Title: 				login 
	 * @author 				杨贵松
	 * @date 				2014年3月24日 下午5:20:47
	 * @Description: 		用户登录
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value = "statisticToWater.json",method=RequestMethod.POST)
	public ModelAndView statisticToWater(@RequestBody String str , HttpSession session) {
		ModelAndView modelView=new ModelAndView();
		User user = (User) session.getAttribute("user");
		try {
			String[] headStr = str.split(";");
			Map<String,Double> map = new HashMap<String, Double>();
			if(headStr[0].equals("0")){
				map = equipmentDataService.getValueForYear(user,headStr[1],headStr[2]);
			}else{
				map = equipmentDataService.getValueForMouth(user, headStr[1],headStr[2]);
			}
			modelView.addObject(map);
			return modelView;
		} catch (Exception e) {
			modelView.addObject("error");
			log.info("用户： " +user.getLoginname()+" 进行统计分析时发生异常，异常代码： "+e);
			return modelView;
		}
	}
	
}
