package com.rest.tiis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rest.tiis.beans.Project;
import com.rest.tiis.beans.SystemLog;
import com.rest.tiis.beans.User;
import com.rest.tiis.listener.Login;
import com.rest.tiis.mapping.SystemLogMapper;

/**
 * @project:					TIIS 
 * @Title: 						MessageController.java 		
 * @Package 					com.rest.tiis.controller		
 * @Description: 				消息管理
 * @author 						杨贵松   
 * @date 						2014年7月23日 下午5:13:01 
 * @version 					V1.0
 */
@Controller
@RequestMapping("message")
public class MessageController {
	private static final Logger log = Logger.getLogger(MessageController.class.getName());
	@Autowired
	private SystemLogMapper systemLogMapper;

	
	/**
	 * @Title: 				selectAllMessagePages 
	 * @author 				杨贵松
	 * @date 				2014年7月23日 下午5:14:37
	 * @Description: 		分页查询消息
	 * @param index
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value = "selectAllMessagePages.json", method = RequestMethod.POST)
	public ModelAndView selectAllMessagePages(@RequestBody String index , HttpSession session){
		ModelAndView model = new ModelAndView();
		int pageIndex = 0;
		int pageSize = 0;
		String type = "0000";
		String status = "";
		if(!index.equals("")){
			String[] str = index.split(",");
			pageIndex = Integer.parseInt(str[0]);
			pageSize = Integer.parseInt(str[1]);
			type = str[2];
			if(!str[3].equals("0000")){
				status = str[3];
			}
		}
		List<Project> list = new ArrayList<Project>();
		User user = (User) session.getAttribute("user");
		try {
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", user.getId());
			map.put("index", pageIndex*pageSize);
			map.put("pageSize", pageSize);
			map.put("status", status);
			
			if(type.equals("0000")){
				map.put("type", "");
			}else{
				map.put("type", type);
			}
			
			//管理员查看所有项目信息
			if(user.getRole()==0){
				list = systemLogMapper.selectAllMessagePages(map);
			}else{
				//普通用户查看自己关联的项目信息
				list = systemLogMapper.selectAllMessagePagesByUser(map);
			}
			if(list==null||list.size()==0){
				model.addObject("null");
			}else{
				model.addObject(list);
			}
		} catch (Exception e) {
			model.addObject("error");
			log.info("用户："+user.getLoginname()+" 分页查询报警信息异常！"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				selectAllMessageCounts 
	 * @author 				杨贵松
	 * @date 				2014年7月23日 下午5:15:00
	 * @Description: 		查询消息总数
	 * @param params
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value = "selectAllMessageCounts.json", method = RequestMethod.POST)
	public ModelAndView selectAllMessageCounts(@RequestBody String params ,HttpSession session){
		ModelAndView model = new ModelAndView();
		String[] param = params.split(",");
		int counts = 0;
		String status = "";
		User user = (User) session.getAttribute("user");
		try {
			//判断查询条件
			Map<String,String> map = new HashMap<String, String>();
			if(param[0].equals("0000")){
				map.put("type", "");
			}else{
				map.put("type", param[0]);
			}
			if(!param[1].equals("0000")){
				status = param[1];
			}
			map.put("status", status);
			//管理员查看所有项目信息
			if(user.getRole()==0){
				counts = systemLogMapper.selectAllMessageCounts(map);
			}else{
				//普通用户查看自己关联的项目信息
				map.put("id", user.getId()+"");
				counts = systemLogMapper.selectAllMessageCountsByUser(map);
			}
			model.addObject(counts);
		} catch (Exception e) {
			model.addObject("error");
			log.info("用户："+user.getLoginname()+" 查询报警消息总数异常！"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				selectMessageById 
	 * @author 				杨贵松
	 * @date 				2014年7月23日 下午5:15:26
	 * @Description: 		根据消息id查询消息详情
	 * @param pId
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping("selectMessageById.json")
	public ModelAndView selectMessageById(@RequestBody String id){
		ModelAndView model = new ModelAndView();
		SystemLog msg = new SystemLog();
		try {
			msg = systemLogMapper.selectMessageById(Integer.parseInt(id));
			if(msg==null){
				model.addObject("null");
			}else{
				model.addObject(msg);
			}
			return model;
		} catch (Exception e) {
			model.addObject("error");
			return model;
		}
	}

	/**
	 * @Title: 				modifyMessage 
	 * @author 				杨贵松
	 * @date 				2014年7月23日 下午5:15:56
	 * @Description: 		标记消息为已读
	 * @param projectStr
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping (value = "markSystemLog.json", method = RequestMethod.POST)  
	public ModelAndView markSystemLog(@RequestBody String id,HttpSession session) {
		ModelAndView model = new ModelAndView();
		User user = (User) session.getAttribute("user");
		try {
			systemLogMapper.markSystemLog(Integer.parseInt(id));
			model.addObject("ok");
		} catch (Exception e) {
			model.addObject("error");
			log.info("用户："+user.getLoginname()+" 标记消息为已读异常！"+e);
		}
		return model;
	}
}
