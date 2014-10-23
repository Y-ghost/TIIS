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

import com.rest.tiis.beans.ControlHost;
import com.rest.tiis.beans.User;
import com.rest.tiis.listener.Login;
import com.rest.tiis.service.ControlHostInfoService;
import com.rest.tiis.service.EquipmentInfoService;
import com.rest.tiis.util.AjaxResponseUtil;
import com.rest.tiis.util.CommonUtiles;

@Controller
@RequestMapping("controlHost")
public class ControlHostController {
	private static final Logger log = Logger.getLogger(ControlHostController.class.getName());
	@Autowired
	private ControlHostInfoService controlHostInfoService;
	@Autowired
	private EquipmentInfoService equipmentInfoService;
	
	/**
	 * @Title: 				addHost 
	 * @author 				杨贵松
	 * @date 				2014年3月31日 下午6:51:42
	 * @Description: 		添加新主机信息
	 * @param hostStr
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="addHost.json",method=RequestMethod.POST)
	public ModelAndView addHost(@RequestBody String hostStr,HttpSession session){
		ModelAndView model = new ModelAndView();
		ControlHost host = new ControlHost();
		User user = (User) session.getAttribute("user");
		try {
			if(!hostStr.equals("") && hostStr != null){
				host = (ControlHost) AjaxResponseUtil.toObject(hostStr, ControlHost.class);
				
				Date date = CommonUtiles.getSystemDateTime();
				host.setCreatetime(date);
				host.setModifytime(date);
				host.setCreateuser(user.getId());
				host.setModifyuser(user.getId());
				controlHostInfoService.addHost(host);
				model.addObject("ok");
				
				log.info("用户："+user.getLoginname()+"添加了新主机，编号为："+host.getCode());
			}else{
				model.addObject("error");
				log.info("用户："+user.getLoginname()+"添加新主机失败!");
			}
			return model;
		} catch (Exception e) {
			model.addObject("error");
			log.info("用户："+user.getLoginname()+"添加新主机异常!"+e);
			return model;
		}
	}
	/**
	 * @Title: 				validHostCode 
	 * @author 				杨贵松
	 * @date 				2014年3月31日 下午6:51:18
	 * @Description: 		验证主机编号是否唯一
	 * @param hostCode
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="validHostCode.json",method=RequestMethod.POST)
	public ModelAndView validHostCode(@RequestBody String hostStr){
		ModelAndView model = new ModelAndView();
		ControlHost host1 = new ControlHost();
		ControlHost host2 = new ControlHost();
		ControlHost hostTmp = new ControlHost();
		try {
			if(!hostStr.equals("") && hostStr != null){
				hostTmp = (ControlHost) AjaxResponseUtil.toObject(hostStr, ControlHost.class);
				host1 = controlHostInfoService.selectHostByCode(hostTmp.getCode());
				host2 = controlHostInfoService.selectByProjectId(hostTmp.getProjectid()+"");
			}
			if(host1 == null && host2 == null){
				model.addObject("ok");
			}else if(host2 != null) {
				model.addObject("NotOnly");
			}else {
				model.addObject("error");
			}
			return model;
		} catch (Exception e) {
			model.addObject("error");
			return model;
		}
	}
	
	/**
	 * @Title: 				validOtherHostCode 
	 * @author 				杨贵松
	 * @date 				2014年4月8日 下午11:51:32
	 * @Description: 		验证要修改的主机编码是否唯一
	 * @param hostCode
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="validOtherHostCode.json",method=RequestMethod.POST)
	public ModelAndView validOtherHostCode(@RequestBody String hostCode){
		ModelAndView model = new ModelAndView();
		ControlHost host = new ControlHost();
		try {
			host = controlHostInfoService.selectHostByIdAndCode(hostCode);
			if(host == null){
				model.addObject("ok");
			}else{
				model.addObject("error");
			}
			return model;
		} catch (Exception e) {
			model.addObject("error");
			return model;
		}
	}
	
	/**
	 * @Title: 				selectAllHostCounts 
	 * @author 				杨贵松
	 * @date 				2014年4月5日 下午3:44:53
	 * @Description: 		获取主机总数 
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectAllHostCounts.json",method=RequestMethod.POST)
	public ModelAndView selectAllHostCounts(@RequestBody String projectId , HttpSession session){
		ModelAndView model = new ModelAndView();
		int counts = 0;
		Map<String,Object> map = new HashMap<String, Object>();
		if(projectId.equals("0000")){
			map.put("projectID", "");
		}else{
			map.put("projectID", projectId);
		}
		try {
			User user = (User) session.getAttribute("user");
			//管理员查看所有主机信息
			if(user.getRole()==0){
				counts = controlHostInfoService.selectAllHostCounts(map);
			}else{
				//普通用户查看自己关联的主机信息
				map.put("id", user.getId());
				counts = controlHostInfoService.selectAllHostCountsByUser(map);
			}
			model.addObject(counts);
		} catch (Exception e) {
			model.addObject("error");
			log.info("获取主机总数异常!"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				selectAllHostPages 
	 * @author 				杨贵松
	 * @date 				2014年4月5日 下午4:08:18
	 * @Description: 		查询主机信息
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectAllHostPages.json",method=RequestMethod.POST)
	public ModelAndView selectAllHostPages(@RequestBody String index , HttpSession session){
		ModelAndView model = new ModelAndView();
		List<ControlHost> list = new ArrayList<ControlHost>();
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
				list = controlHostInfoService.selectAllHostPages(map);
			}else{
				//普通用户查看自己关联的主机信息
				map.put("id", user.getId());
				list = controlHostInfoService.selectAllHostPagesByUser(map);
			}
			if(list==null || list.size()==0){
				model.addObject("null");
			}else{
				model.addObject(list);
			}
		} catch (Exception e) {
			model.addObject("error");
			log.info("查询主机信息异常!"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				selectHostById 
	 * @author 				杨贵松
	 * @date 				2014年4月8日 下午11:32:14
	 * @Description: 		根据主机id查询主机信息
	 * @param index
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectHostById.json",method=RequestMethod.POST)
	public ModelAndView selectHostById(@RequestBody String hId){
		ModelAndView model = new ModelAndView();
		ControlHost chost = new ControlHost();
		try {
			chost = controlHostInfoService.selectHostById(hId);
			if(chost==null){
				model.addObject("null");
			}else{
				model.addObject(chost);
			}
		} catch (Exception e) {
			model.addObject("error");
			log.info("根据主机id查询主机信息异常!"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				modifyHost 
	 * @author 				杨贵松
	 * @date 				2014年4月8日 下午11:57:31
	 * @Description: 		修改主机信息
	 * @param hId
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="modifyHost.json",method=RequestMethod.POST)
	public ModelAndView modifyHost(@RequestBody String hostStr,HttpSession session){
		ModelAndView model = new ModelAndView();
		ControlHost host = new ControlHost();
		User user = (User) session.getAttribute("user");
		try {
			if(!hostStr.equals("") && hostStr != null){
				host = (ControlHost) AjaxResponseUtil.toObject(hostStr, ControlHost.class);
				
				Date date = CommonUtiles.getSystemDateTime();
				host.setModifytime(date);
				host.setModifyuser(user.getId());
				
				controlHostInfoService.modifyHost(host);
				model.addObject("ok");
				log.info("用户："+user.getLoginname()+"修改了主机，编号为："+host.getCode());
			}else{
				model.addObject("error");
				log.info("用户："+user.getLoginname()+"修改主机失败!");
			}
			return model;
		} catch (Exception e) {
			model.addObject("error");
			log.info("用户："+user.getLoginname()+"修改主机异常!"+e);
			return model;
		}
	}
	
	/**
	 * @Title: 				deleteHostById 
	 * @author 				杨贵松
	 * @date 				2014年4月9日 上午12:23:16
	 * @Description: 		删除主机，并删除其关联的相关信息
	 * @param hId
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="deleteHostById.json",method=RequestMethod.POST)
	public ModelAndView deleteHostById(@RequestBody String hId,HttpSession session){
		ModelAndView model = new ModelAndView();
		String[] str = hId.split(",");
		User user = (User) session.getAttribute("user");
		try {
			if(str[1].equals("1")){
				controlHostInfoService.deleteHostById(Integer.parseInt(str[0]));
				model.addObject("ok");
				log.info("用户："+user.getLoginname()+"删除了主机ID为："+hId+"的主机。");
			}else if(str[1].equals("2")){
				//先删除节点采集的状态数据
				equipmentInfoService.deleteEquipmentStatusByHostId(Integer.parseInt(str[0]));
				//先删除节点采集的用水量数据
				equipmentInfoService.deleteEquipmentDataByHostId(Integer.parseInt(str[0]));
				//再删除节点信息数据
				equipmentInfoService.deleteEquipmentByHostId(Integer.parseInt(str[0]));
				//再删除主机信息数据
				controlHostInfoService.deleteHostById(Integer.parseInt(str[0]));
				model.addObject("ok");
				log.info("用户："+user.getLoginname()+"删除了主机ID为："+hId+"的主机,包括其关联的所有信息。");
			}else{
				model.addObject("error");
				log.info("用户："+user.getLoginname()+"删除主机ID为："+hId+"的主机失败。");
			}
			return model;
		} catch (Exception e) {
			model.addObject("error");
			log.info("用户："+user.getLoginname()+"删除主机ID为："+hId+"的主机异常。(若首次删除，即str[1]==1，存在外键异常，属于正常)"+e);
			return model;
		}
	}
}
