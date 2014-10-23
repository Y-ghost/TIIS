package com.rest.tiis.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rest.tiis.beans.Project;
import com.rest.tiis.beans.User;
import com.rest.tiis.listener.Login;
import com.rest.tiis.mapping.SystemLogMapper;
import com.rest.tiis.service.ControlHostInfoService;
import com.rest.tiis.service.EquipmentInfoService;
import com.rest.tiis.service.ProjectInfoService;
import com.rest.tiis.service.UserProjectService;
import com.rest.tiis.service.UserService;
import com.rest.tiis.util.AjaxResponseUtil;
import com.rest.tiis.util.CommonUtiles;
import com.rest.tiis.util.MD5;
import com.rest.tiis.util.mail.MailSender;

/**
 * @project:					TIIS 
 * @Title: 						UserInfoController.java 		
 * @Package 					com.rest.tiis.controller		
 * @Description: 				用户信息管理 
 * @author 						杨贵松   
 * @date 						2014年4月6日 下午9:40:50 
 * @version 					V1.0
 */
@Controller
@RequestMapping("user")
public class UserInfoController {
	private static final Logger log = Logger.getLogger(UserInfoController.class.getName());
	@Autowired
	private UserService userService;
	@Autowired
	private EquipmentInfoService equipmentInfoService;
	@Autowired
	private ControlHostInfoService controlHostInfoService;
	@Autowired
	private UserProjectService userProjectService;
	@Autowired
	private ProjectInfoService projectInfoService;
	@Autowired
	private SystemLogMapper systemLogMapper;
	
	/**
	 * @Title: 				selectAllUserCounts 
	 * @author 				杨贵松
	 * @date 				2014年4月6日 下午9:42:49
	 * @Description: 		查询用户分页数
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectAllUserCounts.json",method=RequestMethod.POST)
	public ModelAndView selectAllUserCounts(@RequestBody String userID){
		ModelAndView model = new ModelAndView();
		int counts = 0;
		try {
			//管理员查看所有用户信息
			if(userID.equals("0000")){
				counts = userService.selectAllUserCounts("");
			}else{
				counts = userService.selectAllUserCounts(userID);
			}
			model.addObject(counts);
		} catch (Exception e) {
			model.addObject("error");
			log.error("查询用户分页数异常!"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				selectAllUserPages
	 * @author 				杨贵松
	 * @date 				2014年4月6日 下午9:42:46
	 * @Description: 		查询用户分页列表 
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectAllUserPages.json",method=RequestMethod.POST)
	public ModelAndView selectAllUserPages(@RequestBody String index){
		ModelAndView model = new ModelAndView();
		List<User> list = new ArrayList<User>();
		int pageIndex = 0;
		int pageSize = 0;
		String userID = "0000";
		if(!index.equals("")){
			String[] str = index.split(",");
			pageIndex = Integer.parseInt(str[0]);
			pageSize = Integer.parseInt(str[1]);
			userID = str[2];
		}
		try {
			//管理员查看所有用户信息
			list = userService.selectAllUserPages(pageIndex*pageSize,pageSize,userID);
			if(list==null || list.size()==0){
				model.addObject("null");
			}else{
				model.addObject(list);
			}
		} catch (Exception e) {
			model.addObject("error");
			log.error("查询用户分页列表异常!"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				selectAllUser
	 * @author 				杨贵松
	 * @date 				2014年4月6日 下午9:42:46
	 * @Description: 		查询所有用户
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectAllUser.json",method=RequestMethod.POST)
	public ModelAndView selectAllUser(){
		ModelAndView model = new ModelAndView();
		List<User> list = new ArrayList<User>();
		try {
			//管理员查看所有用户信息
			list = userService.selectAllUser();
			if(list==null || list.size()==0){
				model.addObject("null");
			}else{
				model.addObject(list);
			}
		} catch (Exception e) {
			model.addObject("error");
			log.error("查询所有用户异常!"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				selectUserInfo 
	 * @author 				杨贵松
	 * @date 				2014年4月6日 下午10:44:16
	 * @Description: 		用户个人信息
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectUserInfo.json",method=RequestMethod.POST)
	public ModelAndView selectUserInfo(HttpSession session){
		ModelAndView model = new ModelAndView();
		User userInfo = new User();
		User user = (User) session.getAttribute("user");
		try {
			//管理员查看所有用户信息
			userInfo = userService.selectUserById(user.getId());
			if(userInfo==null){
				model.addObject("null");
			}else{
				model.addObject(userInfo);
			}
		} catch (Exception e) {
			model.addObject("error");
			log.error("查询用户个人信息异常!"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				forgetPassword 
	 * @author 				杨贵松
	 * @date 				2014年4月18日 下午3:17:52
	 * @Description: 		忘记密码，邮箱找回 
	 * @param userName
	 * @return 
	 * ModelAndView 				返回
	 */
	@RequestMapping(value="i_forget_password.json",method=RequestMethod.POST)
	public ModelAndView forgetPassword(@RequestBody String userName,HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		User user = new User();
		boolean flag = false;
		try {
			user = userService.validUserName(userName);
			if(user==null){
				model.addObject("userNameIsNull");
			}else if(user.getEmail().equals("")){
				model.addObject("emailIsNull");
			}else{
				String secretKey= UUID.randomUUID().toString();  //密钥
	            Date outDate = new Date(System.currentTimeMillis()+60*60*1000);//60分钟后过期
	            long date = outDate.getTime()/1000*1000;        //忽略毫秒数
	            
	            String key = user.getLoginname()+"$"+date+"$"+secretKey;
	            String validCode = MD5.getMD5Str(key);
	            user.setValidcode(validCode);
	            user.setOutdate(outDate);
	            userService.update(user);    //保存到数据库
	            
	            String path = request.getContextPath();
	            String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
	            String urlStr =  basePath+"user/reset_password?userName="+user.getLoginname()+"&sid="+validCode;
	            flag = MailSender.iForgetPassword(user.getEmail(), urlStr, user.getLoginname(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(outDate));
	            if(flag){
	            	String[] email = user.getEmail().split("@");
	            	model.addObject("ok,"+email[0].substring(0,1)+"******@"+email[1]);
	            }else{
	            	model.addObject("error");
	            }
			}
		} catch (Exception e) {
			model.addObject("error");
			log.error("忘记密码，邮箱找回异常!"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				reset_password 
	 * @author 				杨贵松
	 * @date 				2014年4月18日 下午3:37:09
	 * @Description: 		找回链接已经发到邮箱了。进入邮箱点开链接
							以下为链接检验代码，验证通过 跳转到修改密码界面,否则跳转到失败界面
	 * @param sid
	 * @return 
	 * ModelAndView 				返回
	 */
	@RequestMapping(value="reset_password",method=RequestMethod.GET)
	public String reset_password(String sid,String userName,HttpSession session){
		User user = new User();
		try {
			user = userService.validUserName(userName);
			if(user==null){
				return "/user/resetPasswordErr";
			}else if(user.getValidcode()==null || user.getValidcode().equals("")){
				return "/user/resetPasswordErr";
			}else if(user.getValidcode().equals(sid)){
				long date = System.currentTimeMillis();
				if(user.getOutdate().getTime()<date){
					return "/user/resetPasswordErr";
				}else{
					session.setAttribute("userName", userName);
					return "/user/resetPassword";
				}
			}
			return "/user/resetPasswordErr";
		} catch (Exception e) {
			log.error("忘记密码，邮箱找回异常!"+e);
			return "/user/resetPasswordErr";
		}
	}
	
	/**
	 * @Title: 				modifyPassword 
	 * @author 				杨贵松
	 * @date 				2014年4月18日 下午6:32:59
	 * @Description: 		修改密码
	 * @param str
	 * @return 
	 * ModelAndView 				返回
	 */
	@RequestMapping(value = "modifyPassword.json", method = RequestMethod.POST)
	public ModelAndView modifyPassword(@RequestBody String str) {
		ModelAndView model = new ModelAndView();
		User user = new User();
		String[] userStr = str.split(",");
		try {
			user = userService.validUserName(userStr[0]);
			if (user == null) {
				model.addObject("null");
			} else {
				user.setPassword(MD5.getMD5Str(userStr[1].trim()));
				user.setValidcode("");
				userService.update(user);
				model.addObject("ok");
			}
		} catch (Exception e) {
			log.error("修改密码异常!" + e);
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				modifyUserInfo 
	 * @author 				杨贵松
	 * @date 				2014年4月23日 下午5:30:26
	 * @Description: 		修改用户信息
	 * @param userStr
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value = "modifyUserInfo.json", method = RequestMethod.POST)
	public ModelAndView modifyUserInfo(@RequestBody String userStr,HttpSession session) {
		ModelAndView model = new ModelAndView();
		User u = (User) session.getAttribute("user");
		try {
			User user = (User) AjaxResponseUtil.toObject(userStr, User.class);
			if (user == null) {
				model.addObject("null");
			} else {
				User userTemp = userService.selectUserById(user.getId());
				userTemp.setUsername(user.getUsername());
				userTemp.setEmail(user.getEmail());
				userTemp.setPhone(user.getPhone());
				userTemp.setAddress(user.getAddress());
				userTemp.setWebsite(user.getWebsite());
				userTemp.setModifytime(CommonUtiles.getSystemDateTime());
				userTemp.setModifyuser(u.getId());
				userService.update(userTemp);
				model.addObject("ok");
				log.error("用户：‘"+u.getLoginname()+"’ 成功修改了  ‘"+user.getLoginname()+"’ 的用户信息!");
			}
		} catch (Exception e) {
			log.error("修改用户信息异常!" + e);
			model.addObject("error");
		}
		return model;
	}
	
	
	/**
	 * @Title: 				selectUserInfoById 
	 * @author 				杨贵松
	 * @date 				2014年6月25日 下午6:13:43
	 * @Description: 		查询单个用户信息 
	 * @param userId
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectUserInfoById.json",method=RequestMethod.POST)
	public ModelAndView selectUserInfoById(@RequestBody String userId){
		ModelAndView model = new ModelAndView();
		User userInfo = new User();
		try {
			//管理员查看所有用户信息
			userInfo = userService.selectUserById(Integer.parseInt(userId));
			if(userInfo==null){
				model.addObject("null");
			}else{
				model.addObject(userInfo);
			}
		} catch (Exception e) {
			model.addObject("error");
			log.error("查询单个用户信息异常!"+e);
		}
		return model;
	}
	
	/**
	 * @Title: 				deleteUserInfoById 
	 * @author 				杨贵松
	 * @date 				2014年6月26日 下午4:35:30
	 * @Description: 		删除用户及其关联的所有数据信息
	 * @param userId
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="deleteUserInfoById.json",method=RequestMethod.POST)
	public ModelAndView deleteUserInfoById(@RequestBody String userId, HttpSession session){
		ModelAndView model = new ModelAndView();
		String[] str = userId.split(",");
		User user = (User) session.getAttribute("user");
		try {
			if(str[1].equals("1")){
				userService.deleteUserById(Integer.parseInt(str[0]));
				model.addObject("ok");
				log.info("用户：‘"+user.getLoginname()+"’ 成功删除了ID为：  ‘"+userId+"’ 的用户信息!");
			}else if(str[1].equals("2")){
				List<Project> pList = new ArrayList<Project>();
				pList = projectInfoService.selectAllProjectByUser(Integer.parseInt(str[0]));
				if(!CollectionUtils.isEmpty(pList)){
					for(Project p:pList){
						//先删除节点采集的状态数据
						equipmentInfoService.deleteEquipmentStatus(p.getId());
						//再删除节点采集的用水量数据
						equipmentInfoService.deleteEquipmentData(p.getId());
						//再删除节点信息数据
						equipmentInfoService.deleteEquipment(p.getId());
						//再删除主机信息数据
						controlHostInfoService.deleteControlHost(p.getId());
						//再删除用户项目关联数据
						userProjectService.deleteUserProjectR(p.getId());
						//再删除项目信息数据
						projectInfoService.deleteProjectById(p.getId());
					}
				}
				
				//再删除日志信息信息数据
				systemLogMapper.deleteSystemLogByUserId(Integer.parseInt(str[0]));
				//最后删除用户信息数据
				userService.deleteUserById(Integer.parseInt(str[0]));
				model.addObject("ok");
				log.info("用户：‘"+user.getLoginname()+"’ 成功删除了ID为：  ‘"+userId+"’ 的用户信息,包括其关联的所有信息。");
			}else{
				model.addObject("error");
				log.info("用户：‘"+user.getLoginname()+"’ 删除ID为：  ‘"+userId+"’ 的用户信息失败!");
			}
			return model;
		} catch (Exception e) {
			model.addObject("error");
			log.info("用户：‘"+user.getLoginname()+"’ 删除ID为：  ‘"+userId+"’ 的用户信息异常!(若首次删除，即str[1]==1，存在外键异常，属于正常)"+e);
			return model;
		}
	}
	
	
	/**
	 * @Title: 				saveUIDAndCID 
	 * @author 				杨贵松
	 * @date 				2014年7月12日 上午11:55:23
	 * @Description: 		将cid保存到服务器，每次登录都更新
	 * @param str
	 * @return 
	 * ModelAndView 				返回
	 */
	@RequestMapping(value="saveUIDAndCID.json",method=RequestMethod.POST)
	public ModelAndView saveUIDAndCID(@RequestBody String str){
		ModelAndView model = new ModelAndView();
		String[] clientStr = str.split(",");
		User userInfo = new User();
		try {
			userInfo = userService.selectUserByLoginName(clientStr[0]);
			if(userInfo.getLoginname().equals(clientStr[0])){
				userInfo.setClientid(clientStr[1]);
				userService.updateClientID(userInfo);
			}else{
				log.info("用户信息异常!");
			}
			model.addObject("null");
		} catch (Exception e) {
			model.addObject("error");
			log.error("将cid保存到服务器，每次登录都更新异常!"+e);
		}
		return model;
	}
}
