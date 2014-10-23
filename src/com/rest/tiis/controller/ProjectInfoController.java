package com.rest.tiis.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.rest.tiis.beans.User;
import com.rest.tiis.beans.UserProjectRel;
import com.rest.tiis.listener.Login;
import com.rest.tiis.service.ControlHostInfoService;
import com.rest.tiis.service.EquipmentInfoService;
import com.rest.tiis.service.ProjectInfoService;
import com.rest.tiis.service.UserProjectService;
import com.rest.tiis.util.AjaxResponseUtil;
import com.rest.tiis.util.CommonUtiles;

/**
 * @project:					TIIS 
 * @Title: 						ProjectInfoController.java 		
 * @Package 					com.rest.tiis.controller		
 * @Description: 				项目信息控制层
 * @author 						杨贵松   
 * @date 						2014年2月24日 下午11:25:27 
 * @version 					V1.0
 */
@Controller
@RequestMapping("project")
public class ProjectInfoController {
	private static final Logger log = Logger.getLogger(ProjectInfoController.class.getName());
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private ControlHostInfoService controlHostInfoService;
	@Autowired
	private EquipmentInfoService equipmentInfoService;
	@Autowired
	private UserProjectService userProjectService;

	/**
	 * @Title: 				addProject 
	 * @author 				杨贵松
	 * @date 				2014年2月24日 下午11:25:31
	 * @Description: 		添加新的项目信息
	 * @param s
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping (value = "addProject.json", method = RequestMethod.POST)  
	public ModelAndView addProject(@RequestBody String projectStr,HttpSession session) {
		ModelAndView model = new ModelAndView();
		User user = (User) session.getAttribute("user");
		try {
			Project project = (Project) AjaxResponseUtil.toObject(projectStr,Project.class);
			if(project != null){
				Date date = CommonUtiles.getSystemDateTime();
				
				project.setCreatetime(date);
				project.setModifytime(date);
				project.setModifyuser(user.getId());
				project.setCreateuser(user.getId());
				
				projectInfoService.addProject(project);
				
				Project p = projectInfoService.selectProjectByName(project.getName());
				if(p!=null){
					UserProjectRel upr = new UserProjectRel();
					upr.setUserid(user.getId());
					upr.setProjectid(p.getId());
					upr.setCreatetime(date);
					upr.setModifytime(date);
					upr.setCreateuser(user.getId());
					upr.setModifyuser(user.getId());
					userProjectService.addUserProjectR(upr);
					model.addObject("ok");
				}else{
					model.addObject("error");
					log.info("用户："+user.getLoginname()+"添加了新项目，名为："+project.getName());
				}
			}else{
				model.addObject("error");
			}
		} catch (Exception e) {
			model.addObject("error");
			log.info("用户："+user.getLoginname()+"添加了新项目异常！"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				selectAllProject 
	 * @author 				杨贵松
	 * @date 				2014年2月24日 下午11:25:40
	 * @Description: 		查询所有项目信息
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value = "selectAllProject.json", method = RequestMethod.POST)
	public ModelAndView selectAllProject(HttpSession session){
		ModelAndView model = new ModelAndView();
		List<Project> list = new ArrayList<Project>();
		try {
			User user = (User) session.getAttribute("user");
			//管理员查看所有项目信息
			if(user.getRole()==0){
				list = projectInfoService.selectAllProject();
			}else{
				//普通用户查看自己关联的项目信息
				list = projectInfoService.selectAllProjectByUser(user.getId());
			}
			if(list==null || list.size()==0){
				model.addObject("null");
			}else{
				model.addObject(list);
			}
		} catch (Exception e) {
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				selectAllProjectPages 
	 * @author 				杨贵松
	 * @date 				2014年4月1日 下午3:59:27
	 * @Description: 		根据页码查询项目信息列表
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value = "selectAllProjectPages.json", method = RequestMethod.POST)
	public ModelAndView selectAllProjectPages(@RequestBody String index , HttpSession session){
		ModelAndView model = new ModelAndView();
		int pageIndex = 0;
		int pageSize = 0;
		String province = "-1";
		String city = "-1";
		if(!index.equals("")){
			String[] str = index.split(",");
			pageIndex = Integer.parseInt(str[0]);
			pageSize = Integer.parseInt(str[1]);
			province = str[2];
			city = str[3];
		}
		List<Project> list = new ArrayList<Project>();
		try {
			User user = (User) session.getAttribute("user");
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", user.getId());
			map.put("index", pageIndex*pageSize);
			map.put("pageSize", pageSize);
			if(province.equals("-1")){
				map.put("province", "");
				map.put("city", "");
			}else if(city.equals("-1")){
				map.put("province", province);
				map.put("city", "");
			}else{
				map.put("province", province);
				map.put("city",city);
			}
			
			//管理员查看所有项目信息
			if(user.getRole()==0){
				list = projectInfoService.selectAllProjectPages(map);
			}else{
				//普通用户查看自己关联的项目信息
				list = projectInfoService.selectAllProjectPagesByUser(map);
			}
			if(list==null||list.size()==0){
				model.addObject("null");
			}else{
				model.addObject(list);
			}
		} catch (Exception e) {
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				selectAllProjectCounts 
	 * @author 				杨贵松
	 * @date 				2014年4月1日 下午3:59:31
	 * @Description: 		查询项目列表总数
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value = "selectAllProjectCounts.json", method = RequestMethod.POST)
	public ModelAndView selectAllProjectCounts(@RequestBody String params ,HttpSession session){
		ModelAndView model = new ModelAndView();
		String[] param = params.split(",");
		int counts = 0;
		try {
			User user = (User) session.getAttribute("user");
			//判断查询条件
			Map<String,String> map = new HashMap<String, String>();
			if(param[0].equals("-1")){
				map.put("province", "");
				map.put("city", "");
			}else if(param[1].equals("-1")){
				map.put("province", param[0]);
				map.put("city", "");
			}else{
				map.put("province", param[0]);
				map.put("city", param[1]);
			}
			//管理员查看所有项目信息
			if(user.getRole()==0){
				counts = projectInfoService.selectAllProjectCounts(map);
			}else{
				//普通用户查看自己关联的项目信息
				map.put("id", user.getId()+"");
				counts = projectInfoService.selectAllProjectCountsByUser(map);
			}
			model.addObject(counts);
		} catch (Exception e) {
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				validProjectName 
	 * @author 				杨贵松
	 * @date 				2014年3月31日 下午4:01:36
	 * @Description: 		验证项目名称是否已存在
	 * @param pName
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value = "validProjectName.json", method = RequestMethod.POST)
	public ModelAndView validProjectName(@RequestBody String pName){
		ModelAndView model = new ModelAndView();
		Project p = new Project();
		try {
			p = projectInfoService.selectProjectByName(pName);
			if(p==null){
				model.addObject("ok");
			}else{
				model.addObject("error");
			}
		} catch (Exception e) {
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				validProjectName 
	 * @author 				杨贵松
	 * @date 				2014年4月8日 下午10:07:22
	 * @Description: 		验证修改的项目名是否已存在
	 * @param pName
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value = "validProjectOtherName.json", method = RequestMethod.POST)
	public ModelAndView validProjectOtherName(@RequestBody String pName){
		ModelAndView model = new ModelAndView();
		String[] str = pName.split(",");
		Project p = new Project();
		try {
			p = projectInfoService.selectProjectByIdAndName(str[0],str[1]);
			if(p==null){
				model.addObject("ok");
			}else{
				model.addObject("error");
			}
		} catch (Exception e) {
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				selectProjectById 
	 * @author 				杨贵松
	 * @date 				2014年2月24日 下午11:25:45
	 * @Description: 		根据项目id查询项目信息
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping("selectProjectById.json")
	public ModelAndView selectProjectById(@RequestBody String pId){
		ModelAndView model = new ModelAndView();
		Project project = new Project();
		try {
			project = projectInfoService.selectProjectById(pId);
			if(project==null){
				model.addObject("null");
			}else{
				model.addObject(project);
			}
			return model;
		} catch (Exception e) {
			model.addObject("error");
			return model;
		}
	}

	/**
	 * @Title: 				modifyProject 
	 * @author 				杨贵松
	 * @date 				2014年4月8日 下午10:26:59
	 * @Description: 		修改项目信息 
	 * @param projectStr
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping (value = "modifyProject.json", method = RequestMethod.POST)  
	public ModelAndView modifyProject(@RequestBody String projectStr,HttpSession session) {
		ModelAndView model = new ModelAndView();
		User user = (User) session.getAttribute("user");
		try {
			Project project = (Project) AjaxResponseUtil.toObject(projectStr,Project.class);
			if(project != null){
				Date date = CommonUtiles.getSystemDateTime();
				
				project.setModifytime(date);
				project.setModifyuser(user.getId());
				
				projectInfoService.modifyProject(project);
				model.addObject("ok");
				log.info("用户："+user.getLoginname()+"更新了项目，名为："+project.getName());
			}else{
				model.addObject("error");
			}
		} catch (Exception e) {
			model.addObject("error");
			log.info("用户："+user.getLoginname()+"更新项目异常！"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				deleteProjectById 
	 * @author 				杨贵松
	 * @date 				2014年4月3日 下午5:28:23
	 * @Description: 		删除一个项目
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="deleteProjectById.json",method=RequestMethod.POST)
	public ModelAndView deleteProjectById(@RequestBody String pId , HttpSession session){
		ModelAndView model = new ModelAndView();
		String[] str = pId.split(",");
		User user = (User) session.getAttribute("user");
		try {
			if(str[1].equals("1")){
				projectInfoService.deleteProjectById(Integer.parseInt(str[0]));
				model.addObject("ok");
				log.info("用户："+user.getLoginname()+"删除了项目ID为："+pId+"的项目。");
			}else if(str[1].equals("2")){
				//先删除节点采集的状态数据
				equipmentInfoService.deleteEquipmentStatus(Integer.parseInt(str[0]));
				//先删除节点采集的用水量数据
				equipmentInfoService.deleteEquipmentData(Integer.parseInt(str[0]));
				//再删除节点信息数据
				equipmentInfoService.deleteEquipment(Integer.parseInt(str[0]));
				//再删除主机信息数据
				controlHostInfoService.deleteControlHost(Integer.parseInt(str[0]));
				//再删除用户项目关联数据
				userProjectService.deleteUserProjectR(Integer.parseInt(str[0]));
				//最后删除项目信息数据
				projectInfoService.deleteProjectById(Integer.parseInt(str[0]));
				model.addObject("ok");
				log.info("用户："+user.getLoginname()+"删除了项目ID为："+pId+"的项目,包括其关联的所有信息。");
			}else{
				model.addObject("error");
				log.info("用户："+user.getLoginname()+"删除项目ID为："+pId+"的项目失败。");
			}
			return model;
		} catch (Exception e) {
			model.addObject("error");
			log.info("用户："+user.getLoginname()+"删除项目ID为："+pId+"的项目异常。(若首次删除，即str[1]==1，存在外键异常，属于正常)"+e);
			return model;
		}
	}
	
}
