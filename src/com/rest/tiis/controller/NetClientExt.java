package com.rest.tiis.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.rest.tiis.beans.ControlHost;
import com.rest.tiis.beans.Equipment;
import com.rest.tiis.beans.User;
import com.rest.tiis.listener.Login;
import com.rest.tiis.listener.ResultTypeEnum;
import com.rest.tiis.mapping.UserMapper;
import com.rest.tiis.service.ControlHostInfoService;
import com.rest.tiis.service.EquipmentDataService;
import com.rest.tiis.service.EquipmentInfoService;
import com.rest.tiis.service.NetWorkService;
import com.rest.tiis.util.CheckReceiveCodingUtil;
import com.rest.tiis.util.CodingFactoryUtil;
import com.rest.tiis.util.network.Client;

/**
 * @project:					TIIS 
 * @Title: 						NetClientExt.java 		
 * @Package 					com.rest.tiis.controller		
 * @Description: 				客户端访问扩展类
 * @author 						杨贵松   
 * @date 						2014年4月27日 下午12:51:14 
 * @version 					V1.0
 */
@Controller
@RequestMapping("NetWorkExt")
public class NetClientExt {
	private final static Logger log = Logger.getLogger(NetClientExt.class.getName());
	private final static CodingFactoryUtil codingFactory = new CodingFactoryUtil();
	private final static CheckReceiveCodingUtil checkCoding = new CheckReceiveCodingUtil();
	private static final String APPID = "djUGtMrQ8A64fC664vH137";
	private static final String APPKEY = "hKGLMQHjGr88YgzVlEzkb8";
	private static String CLIENTID = "";
	private static final String MASTERSECRET = "1vCOsPMlFu7dUzZZWVm1c9";
	private static final String API = "http://sdk.open.api.igexin.com/apiex.htm"; // OpenService接口地址
	@Autowired
	private EquipmentInfoService equipmentInfoService;
	@Autowired
	private ControlHostInfoService controlHostInfoService;
	@Autowired
	private NetWorkService netWorkService;
	@Autowired
	private EquipmentDataService equipmentDataService;
	@Autowired
	private UserMapper userMapper;
	/**
	 * @Title: 				setControlHostTime 
	 * @author 				杨贵松
	 * @date 				2014年4月27日 下午12:52:32
	 * @Description: 		校验控制器主机时间 
	 * @param pId
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login(ResultTypeEnum.json)
	@RequestMapping(value="setControlHostTime.json",method=RequestMethod.POST)
	public ModelAndView setControlHostTime(@RequestBody String projectId,HttpSession session){
		User user = (User) session.getAttribute("user");
		ModelAndView model = new ModelAndView();
		try {
			ControlHost chost = controlHostInfoService.selectByProjectId(projectId);
			if (chost == null) {
				model.addObject("null");
				return model;
			}
			String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			byte[] data = codingFactory.string2BCD(time);

			byte[] sendData = codingFactory.coding((byte) 0x01, chost.getCode(), (byte) 0x0C, data);
			// 开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);
			// 等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "1C", startDate);

			boolean flag = false;
			byte[] receiveData = null;
			if (!dataContext.equals("")) {
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			if (flag) {
				byte num = receiveData[12];
				if (num == 0x0A) {
					model.addObject("ok");
					log.info("用户： " + user.getLoginname() + " 校验控制器主机时间成功!");
				} else {
					model.addObject("error");
					log.info("用户： " + user.getLoginname() + " 校验控制器主机时间失败!");
				}
			} else {
				model.addObject("error");
				log.info("用户： " + user.getLoginname() + " 校验控制器主机时间失败!");
			}
			return model;
		} catch (Exception e) {
			log.error("用户： " + user.getLoginname() + " 校验控制器主机时间异常!" + e);
			model.addObject("error");
			return model;
		}
	}
	
	/**
	 * @Title: 				selectControlHostTime 
	 * @author 				杨贵松
	 * @date 				2014年4月27日 下午5:06:04
	 * @Description: 		查看控制主机时间
	 * @param projectId
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login(ResultTypeEnum.json)
	@RequestMapping(value="selectControlHostTime.json",method=RequestMethod.POST)
	public ModelAndView selectControlHostTime(@RequestBody String projectId,HttpSession session){
		User user = (User) session.getAttribute("user");
		ModelAndView model = new ModelAndView();
		try {
			ControlHost chost = controlHostInfoService.selectByProjectId(projectId);
			if (chost == null) {
				model.addObject("null");
				return model;
			}
			byte[] data = {};
			
			byte[] sendData = codingFactory.coding((byte) 0x01, chost.getCode(), (byte) 0x0D, data);
			// 开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);
			// 等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "1D", startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			if (!dataContext.equals("")) {
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			if (flag) {
				String time = dataContext.substring(24,28)+"年"+dataContext.substring(28,30)+"月"
							+dataContext.substring(30,32)+"日 "+dataContext.substring(32,34)+"时"
							+dataContext.substring(34,36)+"分"+dataContext.substring(36,38)+"秒";
				model.addObject(time);
			} else {
				model.addObject("error");
				log.info("用户： " + user.getLoginname() + " 查询控制器主机时间失败!");
			}
			return model;
		} catch (Exception e) {
			log.error("用户： " + user.getLoginname() + " 查询控制器主机时间异常!" + e);
			model.addObject("error");
			return model;
		}
	}
	
	/**
	 * @Title: 				selectData 
	 * @author 				杨贵松
	 * @date 				2014年6月30日 下午5:05:48
	 * @Description: 		赋值查询，采集实时数据和查询数据库中存储的最新的一条数据
	 * @param Str
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login(ResultTypeEnum.json)
	@RequestMapping(value="selectData.json",method=RequestMethod.POST)
	public ModelAndView selectData(@RequestBody String Str,HttpSession session){
		User user = (User) session.getAttribute("user");
		ModelAndView model = new ModelAndView();
		String[] valStr = Str.split(",");
		try {
			ControlHost chost = controlHostInfoService.selectByProjectId(valStr[0]);
			if (chost == null) {
				model.addObject("null");
				return model;
			}
			byte[] data = {};
			
			byte[] sendData = codingFactory.coding((byte) 0x01, chost.getCode(), (byte) 0x05, data);
			// 开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);
			// 等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "15", startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			
			//该项目下全部节点数据
			List<Equipment> listE = new ArrayList<Equipment>();
			if(valStr[1].equals("0000")){
				listE = equipmentInfoService.selectEquipmentByProjectId(valStr[0]);
			}else{//该项目下单个节点数据
				Equipment e = equipmentInfoService.selectEquipmentById(valStr[1]);
				listE.add(e);
			}
			
			if (!dataContext.equals("")) {
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			if (flag) {
				int num = receiveData[12];
				String usingData = dataContext.substring(26,dataContext.length()-3);
				
				for(int i = 0 ;i < num ; i++){
					String eCode = usingData.substring(i*12,i*12+4);
					for(Equipment equipment:listE){
						if(equipment.getCode().equals(eCode)){
							long times = Long.valueOf(usingData.substring(i*12+4,i*12+12),16);
							long eData = equipmentDataService.selectDataByEId(equipment.getId());
							equipment.setTimes(times);
							equipment.seteData(eData);
						}
					}
				}
				model.addObject(listE);
			} else {
				model.addObject("error");
				log.info("用户： " + user.getLoginname() + " 赋值采集数据失败!");
			}
			return model;
		} catch (Exception e) {
			log.error("用户： " + user.getLoginname() + " 赋值采集数据异常!" + e);
			model.addObject("error");
			return model;
		}
	}
	
	/**
	 * @Title: 				setData 
	 * @author 				杨贵松
	 * @date 				2014年7月3日 上午11:54:01
	 * @Description: 		赋值
	 * @param Str
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login(ResultTypeEnum.json)
	@RequestMapping(value="setData.json",method=RequestMethod.POST)
	public ModelAndView setData(@RequestBody String Str,HttpSession session){
		User user = (User) session.getAttribute("user");
		ModelAndView model = new ModelAndView();
		String[] valStr = Str.split(",");
		try {
			ControlHost chost = controlHostInfoService.selectByProjectId(valStr[0]);
			if (chost == null) {
				model.addObject("null");
				return model;
			}
			
			byte[] eCode = codingFactory.string2BCD(valStr[1]);
			byte[] eData = codingFactory.longToByte(Long.parseLong(valStr[2]));
			byte[] data = codingFactory.byteMerger(eCode, eData);
			
			byte[] sendData = codingFactory.coding((byte) 0x01, chost.getCode(), (byte) 0x2A, data);
			// 开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);
			// 等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "3A", startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			
			if (!dataContext.equals("")) {
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			if (flag) {
				byte val = receiveData[12];
				if(val==0x0A){
					model.addObject("ok");
				}else{
					model.addObject("error");
				}
			} else {
				model.addObject("error");
				log.info("用户： " + user.getLoginname() + " 赋值采集数据失败!");
			}
			return model;
		} catch (Exception e) {
			log.error("用户： " + user.getLoginname() + " 赋值采集数据异常!" + e);
			model.addObject("error");
			return model;
		}
	}
	
	/**
	 * @Title: 				pushAllMsg 
	 * @author 				杨贵松
	 * @date 				2014年7月12日 下午5:52:53
	 * @Description: 		向所有用户推送消息
	 * @param Str
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login(ResultTypeEnum.json)
	@RequestMapping(value="pushAllMsg.json",method=RequestMethod.POST)
	public ModelAndView pushAllMsg(@RequestBody String Notice){
		ModelAndView model = new ModelAndView();
		// 推送主类
		IIGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);
		try {
			AppMessage message = new AppMessage();
			// 通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate，此处以LinkTemplate为例
			NotificationTemplate template = new NotificationTemplate();
			
			template.setAppId(APPID); // 应用APPID
			template.setAppkey(APPKEY); // 应用APPKEY
			
			// 通知属性设置：如通知的标题，内容
			template.setTitle("通知【Rainet云灌溉】"); // 通知标题
			template.setText(Notice); // 通知内容
			template.setLogo("push.png");
			// 收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动
			template.setTransmissionType(1);
			
			message.setData(template);
			message.setOffline(true); // 用户当前不在线时，是否离线存储，可选，默认不存储
			message.setOfflineExpireTime(72 * 3600 * 1000);
			
			List<String> appIdList = new ArrayList<String>();
			appIdList.add(APPID);
			
			message.setAppIdList(appIdList);
			push.pushMessageToApp(message);
			model.addObject("ok");
			return model;
		} catch (Exception e) {
			log.error("向所有用户推送消息异常!" + e);
			model.addObject("error");
			return model;
		}
	}
		
		
		/**
		 * @Title: 				pushTestMsg 
		 * @author 				杨贵松
		 * @date 				2014年8月2日 上午11:45:52
		 * @Description: 		测试消息
		 * @param Notice
		 * @return 
		 * ModelAndView 				返回
		 */
		@Login(ResultTypeEnum.json)
		@RequestMapping(value="pushTestMsg.json",method=RequestMethod.POST)
		public ModelAndView pushTestMsg(){
			ModelAndView model = new ModelAndView();
			// 推送主类
			IIGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);
			try {
				User user = new User();
				user = userMapper.selectByPrimaryKey(1);
				if (user != null) {
					// 接收者
					List<Target> targets = new ArrayList<Target>();
					ListMessage message = new ListMessage();
					
					// 通知模版：NotificationTemplate
					NotificationTemplate template = new NotificationTemplate();
					template.setAppId(APPID);
					template.setAppkey(APPKEY);
					template.setTitle("警告【Rainet云灌溉】"); // 通知标题
					template.setText("项目【实验平台】下有一个节点监测的湿度过低!");//通知内容
					template.setLogo("push.png");//通知logo
					
					// 收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动
					template.setTransmissionType(1);
					
					message.setData(template);
					message.setOffline(true); // 用户当前不在线时，是否离线存储,可选
					message.setOfflineExpireTime(72 * 3600 * 1000); // 离线有效时间，单位为毫秒，可选
					//声明推送用户对象
					CLIENTID = user.getClientid();
					
					Target target = new Target();
					target.setAppId(APPID);
					target.setClientId(CLIENTID);
					targets.add(target);
					
					String contentId = push.getContentId(message);
					push.pushMessageToList(contentId, targets);
					log.error("警告推送完成!");
					model.addObject("ok");
					return model;
				}else{
					model.addObject("error");
					return model;
				}
			} catch (Exception e) {
				log.error("通知推送异常!"+e);
				model.addObject("error");
				return model;
			}
	}
}

