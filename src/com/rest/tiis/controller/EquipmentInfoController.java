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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rest.tiis.beans.ControlHost;
import com.rest.tiis.beans.Equipment;
import com.rest.tiis.beans.User;
import com.rest.tiis.listener.Login;
import com.rest.tiis.service.ControlHostInfoService;
import com.rest.tiis.service.EquipmentInfoService;
import com.rest.tiis.util.AjaxResponseUtil;
import com.rest.tiis.util.CommonUtiles;

/**
 * @project:					TIIS 
 * @Title: 						EquipmentInfoController.java 		
 * @Package 					com.rest.tiis.controller		
 * @Description: 				节点信息控制层
 * @author 						杨贵松   
 * @date 						2014年2月24日 下午11:24:55 
 * @version 					V1.0
 */
@Controller
@RequestMapping("Equipment")
public class EquipmentInfoController {
	private static final Logger log = Logger.getLogger(EquipmentInfoController.class.getName());
	@Autowired
	private EquipmentInfoService equipmentInfoService;
	@Autowired
	private ControlHostInfoService controlHostInfoService;
	
	/**
	 * @Title: 				addAllEquipment 
	 * @author 				杨贵松
	 * @date 				2014年4月23日 下午3:00:45
	 * @Description: 		批量注册节点信息到数据库，同时将删除所有已存在的节点及其相关的所有数据
	 * @param emt
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="addAllEquipment.json",method=RequestMethod.POST)
	public ModelAndView addAllEquipment(@RequestBody String emt,HttpSession session){
		ModelAndView model = new ModelAndView();
		User user = (User) session.getAttribute("user");
		String[] eStr = emt.split("&");
		try {
			List<Equipment> list = new ArrayList<Equipment>();
			ControlHost chost = controlHostInfoService.selectByProjectId(eStr[0]);
			if(chost == null){
				model.addObject("null");
				return model;
			}
			String[] equipmentStr = eStr[1].split(";");
			Date date = CommonUtiles.getSystemDateTime();
			for(int i=0;i<equipmentStr.length;i++){
				Equipment e = new Equipment();
				e = (Equipment) AjaxResponseUtil.toObject(equipmentStr[i], Equipment.class);
				e.setControlhostid(chost.getId());
				e.setCreatetime(date);
				e.setCreateuser(user.getId());
				e.setModifytime(date);
				e.setModifyuser(user.getId());
				list.add(e);
			}
			//先删除节点采集的状态数据
			equipmentInfoService.deleteEquipmentStatusByHostId(chost.getId());
			//先删除节点采集的用水量数据
			equipmentInfoService.deleteEquipmentDataByHostId(chost.getId());
			//再删除节点信息数据
			equipmentInfoService.deleteEquipmentByHostId(chost.getId());
			//最后再注册所有节点
			equipmentInfoService.addAllEquipment(list);
			model.addObject("ok");
			log.info("用户："+user.getLoginname()+"批量注册了新节点!");
		} catch (Exception e) {
			model.addObject("error");
			log.info("用户："+user.getLoginname()+"批量注册新节点异常！"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				selectEquipmentByProjectId 
	 * @author 				杨贵松
	 * @date 				2014年2月24日 下午11:43:08
	 * @Description: 		根据项目id查询节点信息 
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectEquipmentByProjectId.json",method=RequestMethod.POST)
	public ModelAndView selectEquipmentByProjectId(@RequestBody String projectId){
		String pid= projectId.split("=")[1];
		ModelAndView model = new ModelAndView();
		List<Equipment> list = new ArrayList<Equipment>();
		try {
			list = equipmentInfoService.selectEquipmentByProjectId(pid);
			if(list.size()>0){
				model.addObject(list);
			}else{
				model.addObject("null");
			}
		} catch (Exception e) {
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				selectAllEquipmentCounts 
	 * @author 				杨贵松
	 * @date 				2014年4月9日 上午11:52:54
	 * @Description: 		查询主机数
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectAllEquipmentCounts.json",method=RequestMethod.POST)
	public ModelAndView selectAllEquipmentCounts(@RequestBody String projectId, HttpSession session){
		ModelAndView model = new ModelAndView();
		Map<String,Object> map = new HashMap<String, Object>();
		if(projectId.equals("0000")){
			map.put("projectID", "");
		}else{
			map.put("projectID", projectId);
		}
		int counts = 0;
		try {
			User user = (User) session.getAttribute("user");
			//管理员查看所有主机信息
			if(user.getRole()==0){
				counts = equipmentInfoService.selectAllEquipmentCounts(map);
			}else{
				//普通用户查看自己关联的主机信息
				map.put("id", user.getId());
				counts = equipmentInfoService.selectAllEquipmentCountsByUser(map);
			}
			model.addObject(counts);
		} catch (Exception e) {
			model.addObject("error");
			log.info("查询主机数异常！"+e);
		}
		return model;
	}
	/**
	 * @Title: 				selectAllEquipment 
	 * @author 				杨贵松
	 * @date 				2014年2月24日 下午11:24:29
	 * @Description: 		查询所有节点信息 
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectAllEquipmentPages.json",method=RequestMethod.POST)
	public ModelAndView selectAllEquipmentPages(@RequestBody String index , HttpSession session){
		ModelAndView model = new ModelAndView();
		List<Equipment> list = new ArrayList<Equipment>();
		int pageIndex = 0;
		int pageSize = 0;
		String projectId = "0000";
		if(!index.equals("")){
			String[] str = index.split(",");
			pageIndex = Integer.parseInt(str[0]);
			pageSize = Integer.parseInt(str[1]);
			projectId = str[2];
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("index", pageIndex*pageSize);
		map.put("pageSize", pageSize);
		if(projectId.equals("0000")){
			map.put("projectID", "");
		}else{
			map.put("projectID", projectId);
		}
		try {
			User user = (User) session.getAttribute("user");
			//管理员查看所有主机信息
			if(user.getRole()==0){
				list = equipmentInfoService.selectAllEquipmentPages(map);
			}else{
				//普通用户查看自己关联的主机信息
				map.put("id", user.getId());
				list = equipmentInfoService.selectAllEquipmentPagesByUser(map);
			}
			if(list==null || list.size()==0){
				model.addObject("null");
			}else{
				model.addObject(list);
			}
		} catch (Exception e) {
			model.addObject("error");
			log.info("查询所有节点信息异常！"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				selectEquipmentById 
	 * @author 				杨贵松
	 * @date 				2014年4月9日 上午9:38:31
	 * @Description: 		根据节点id查询节点信息
	 * @param eId
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectEquipmentById.json",method=RequestMethod.POST)
	public ModelAndView selectEquipmentById(@RequestBody String eId){
		ModelAndView model = new ModelAndView();
		Equipment equipment = new Equipment();
		try {
			equipment = equipmentInfoService.selectEquipmentById(eId);
			if(equipment != null){
				model.addObject(equipment);
			}else{
				model.addObject("null");
			}
		} catch (Exception e) {
			model.addObject("error");
			log.info("根据节点id查询节点信息异常！"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				selectEquipmentByPId 
	 * @author 				杨贵松
	 * @date 				2014年6月30日 下午4:18:39
	 * @Description: 		根据项目id查询节点信息
	 * @param pId
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectEquipmentByPId.json",method=RequestMethod.POST)
	public ModelAndView selectEquipmentByPId(@RequestBody String pId){
		ModelAndView model = new ModelAndView();
		List<Equipment> list = new ArrayList<Equipment>();
		try {
			list = equipmentInfoService.selectEquipmentByProjectId(pId);
			if(CollectionUtils.isEmpty(list)){
				model.addObject("null");
			}else{
				model.addObject(list);
			}
		} catch (Exception e) {
			model.addObject("error");
			log.info("根据节点id查询节点信息异常！"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				validOtherEquipmentNameAndCode 
	 * @author 				杨贵松
	 * @date 				2014年4月9日 上午11:55:58
	 * @Description: 		验证修改的节点名称和地址是否唯一
	 * @param eId
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="validOtherEquipmentNameAndCode.json",method=RequestMethod.POST)
	public ModelAndView validOtherEquipmentNameAndCode(@RequestBody String eId){
		ModelAndView model = new ModelAndView();
		Equipment equipment = new Equipment();
		try {
			String[] str = eId.split(",");
			Equipment eqpt = equipmentInfoService.selectEquipmentById(str[0]);
			equipment = equipmentInfoService.validOtherEquipmentNameAndCode(str[0],str[1],str[2],eqpt.getControlhostid());
			if(equipment == null){
				model.addObject("ok");
			}else{
				model.addObject("error");
			}
		} catch (Exception e) {
			model.addObject("error");
			log.info("验证修改的节点名称和地址是否唯一异常！"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				modifyEquipment 
	 * @author 				杨贵松
	 * @date 				2014年4月9日 下午12:12:21
	 * @Description: 		修改节点信息
	 * @param eId
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="modifyEquipment.json",method=RequestMethod.POST)
	public ModelAndView modifyEquipment(@RequestBody String equipmentStr , HttpSession session){
		ModelAndView model = new ModelAndView();
		Equipment equipment = new Equipment();
		User user = (User) session.getAttribute("user");
		try {
			if(!equipmentStr.equals("") && equipmentStr != null){
				equipment = (Equipment) AjaxResponseUtil.toObject(equipmentStr, Equipment.class);
				ControlHost chost = controlHostInfoService.selectByProjectId(equipment.getProject().getId()+"");
				
				Date date = CommonUtiles.getSystemDateTime();
				equipment.setModifytime(date);
				equipment.setModifyuser(user.getId());
				equipment.setControlhostid(chost.getId());
				
				equipmentInfoService.modifyEquipment(equipment);
				model.addObject("ok");
				log.info("用户："+user.getLoginname()+"修改节点成功！");
			}else{
				model.addObject("error");
				log.info("用户："+user.getLoginname()+"修改节点失败！");
			}
		} catch (Exception e) {
			model.addObject("error");
			log.info("用户："+user.getLoginname()+"修改节点异常！"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				deleteEquipmentById 
	 * @author 				杨贵松
	 * @date 				2014年4月9日 下午4:25:03
	 * @Description: 		删除节点及其关联的信息
	 * @param equipmentStr
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="deleteEquipmentById.json",method=RequestMethod.POST)
	public ModelAndView deleteEquipmentById(@RequestBody String eId , HttpSession session){
		ModelAndView model = new ModelAndView();
		String[] str = eId.split(",");
		User user = (User) session.getAttribute("user");
		try {
			if(str[1].equals("1")){
				equipmentInfoService.deleteEquipmentByEquipmentId(Integer.parseInt(str[0]));
				model.addObject("ok");
				log.info("用户："+user.getLoginname()+"删除了节点ID为："+eId+"的节点。");
			}else if(str[1].equals("2")){
				//先删除节点采集的状态数据
				equipmentInfoService.deleteEquipmentStatusByEquipmentId(Integer.parseInt(str[0]));
				//先删除节点采集的用水量数据
				equipmentInfoService.deleteEquipmentDataByEquipmentId(Integer.parseInt(str[0]));
				//再删除节点信息数据
				equipmentInfoService.deleteEquipmentByEquipmentId(Integer.parseInt(str[0]));
				model.addObject("ok");
				log.info("用户："+user.getLoginname()+"删除了节点ID为："+eId+"的节点,包括其关联的所有信息。");
			}else{
				model.addObject("error");
				log.info("用户："+user.getLoginname()+"删除节点ID为："+eId+"的节点失败。");
			}
			return model;
		} catch (Exception e) {
			model.addObject("error");
			log.info("用户："+user.getLoginname()+"删除节点ID为："+eId+"的节点异常。(若首次删除，即str[1]==1，存在外键异常，属于正常)"+e);
			return model;
		}
	}
}
