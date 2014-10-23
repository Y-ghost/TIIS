package com.rest.tiis.controller;

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

import com.rest.tiis.beans.ControlHost;
import com.rest.tiis.beans.Equipment;
import com.rest.tiis.beans.User;
import com.rest.tiis.listener.Login;
import com.rest.tiis.listener.ResultTypeEnum;
import com.rest.tiis.service.ControlHostInfoService;
import com.rest.tiis.service.EquipmentInfoService;
import com.rest.tiis.service.NetWorkService;
import com.rest.tiis.util.AjaxResponseUtil;
import com.rest.tiis.util.CheckReceiveCodingUtil;
import com.rest.tiis.util.CodingFactoryUtil;
import com.rest.tiis.util.CommonUtiles;
import com.rest.tiis.util.network.Client;

/**
 * @project:					TIIS 
 * @Title: 						NetClient.java 		
 * @Package 					com.rest.tiis.controller		
 * @Description: 				客户端访问
 * @author 						杨贵松   
 * @date 						2014年3月4日 下午6:06:04 
 * @version 					V1.0
 */
@Controller
@RequestMapping("NetWork")
public class NetClient {
	private final static Logger log = Logger.getLogger(NetClient.class.getName());
	private final static CodingFactoryUtil codingFactory = new CodingFactoryUtil();
	private final static CheckReceiveCodingUtil checkCoding = new CheckReceiveCodingUtil();
	@Autowired
	private EquipmentInfoService equipmentInfoService;
	@Autowired
	private ControlHostInfoService controlHostInfoService;
	@Autowired
	private NetWorkService netWorkService;
	/**
	 * @Title: 				selectEquipmentsStatus 
	 * @author 				杨贵松
	 * @date 				2014年3月6日 下午5:31:08
	 * @Description: 		根据项目id查询所有节点状态  
	 * void 				返回
	 */
	@Login(ResultTypeEnum.json)//登录控制
//	@FireAuthority(authorityTypes=AuthorityType.NORMAL_USER)//权限控制
	@RequestMapping(value="selectEquipmentsStatus.json",method=RequestMethod.POST)
	public ModelAndView selectEquipmentsStatus(@RequestBody String pId,HttpSession session){
		User user = (User) session.getAttribute("user");
		String projectId = pId.split("=")[1];
		ModelAndView model = new ModelAndView();
		try {
			if(user==null){
				model.addObject("noLogin");
				return model;
			}
			ControlHost chost = controlHostInfoService.selectByProjectId(projectId);
			List<Equipment> eList = equipmentInfoService.selectEquipmentByProjectId(projectId);
			if(chost == null || eList.size()==0){
				model.addObject("null");
				return model;
			}
			byte[] data = {};
			
			byte[] sendData = codingFactory.coding((byte)0x01, chost.getCode(), (byte)0x06, data);
			//开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);
			//等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "16",startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			if(!dataContext.equals("")){
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			if(flag){
				int num = receiveData[12];
				String usingData = dataContext.substring(26,dataContext.length()-3);
				for (Equipment equipment : eList) {
					for(int i=0;i<num;i++){
						if(equipment.getCode().equals(usingData.substring(i*12,i*12+4))){
							equipment.setSoilhumidity((int)receiveData[12+i*6+3]);
							if(receiveData[12+i*6+4]<0){
								equipment.setSoiltemperatrue((float)Math.round(((float)(receiveData[12+i*6+4]-receiveData[12+i*6+5]*0.01))*10)/10);
							}else{
								equipment.setSoiltemperatrue((float)Math.round(((float)(receiveData[12+i*6+4]+receiveData[12+i*6+5]*0.01))*10)/10);
							}
							byte status = receiveData[12+i*6+6];
							switch(status){
							case 0 : 
								equipment.setStatus("水阀故障");
								break;
							case (byte)0xFF : 
								equipment.setStatus("供水故障");
								break;
							case 2 : 
								equipment.setStatus("等待关水");
								break;
							case (byte)0xF1 :
								equipment.setStatus("等待出水");
								break;
							case (byte)0xF0 :
								equipment.setStatus("出水正常");
								break;
							case 0x0F :
								equipment.setStatus("关水正常");
								break;
							}
							break;
						}
					}
				}
				log.info("根据项目id查询所有节点状态成功!");
			}else{
				log.info("根据项目id查询所有节点状态失败!");
			}
			model.addObject(eList);
			return model;
		} catch (Exception e) {
			log.error("根据项目id查询所有节点状态异常!"+e);
			model.addObject("error");
			return model;
		}
	}
	
	/**
	 * @Title: 				openEquipment 
	 * @author 				杨贵松
	 * @date 				2014年3月7日 上午3:58:59
	 * @Description: 		开启节点进行浇灌
	 * @param eId
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="equipmentOpen.json",method=RequestMethod.POST)
	public ModelAndView openEquipment(@RequestBody String eId){
		String equipmentId = eId.split("=")[1];
		ModelAndView model = new ModelAndView();
		try {
			Equipment equipment = equipmentInfoService.selectEquipmentById(equipmentId);
			if(equipment == null){
				model.addObject("null");
				return model;
			}
			ControlHost chost = controlHostInfoService.selectById(equipment.getControlhostid());
			if(chost == null){
				model.addObject("null");
				return model;
			}
			byte[] eCode = codingFactory.string2BCD(equipment.getCode());
			byte[] data = {0,eCode[0],eCode[1]};//发送data
			
			byte[] sendData = codingFactory.coding((byte)0x01, chost.getCode(), (byte)0x26, data);
			//开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);
			//等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "36",startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			if(!dataContext.equals("")){
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			
			String mark = "";
			if(flag){
				byte resultData = receiveData[12];
				switch(resultData){
				case 0 :
					mark = "开启";
					break;
				case 0x0f :
					mark = "关闭";
					break;
				case 0x55 :
					mark = "非手动模式";
					break;
				}
				log.info("开启节点成功!");
			}else{
				mark = "error";
				log.info("开启节点失败!");
			}
			model.addObject(mark);
		} catch (Exception e) {
			log.error("开启节点异常!"+e);
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				openEquipment 
	 * @author 				杨贵松
	 * @date 				2014年3月8日 上午12:32:16
	 * @Description: 		关闭节点
	 * @param eId
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="equipmentClose.json",method=RequestMethod.POST)
	public ModelAndView closeEquipment(@RequestBody String eId){
		String equipmentId = eId.split("=")[1];
		ModelAndView model = new ModelAndView();
		try {
			Equipment equipment = equipmentInfoService.selectEquipmentById(equipmentId);
			if(equipment == null){
				model.addObject("null");
				return model;
			}
			ControlHost chost = controlHostInfoService.selectById(equipment.getControlhostid());
			if(chost == null){
				model.addObject("null");
				return model;
			}
			byte[] eCode = codingFactory.string2BCD(equipment.getCode());
			byte[] data = {0x0f,eCode[0],eCode[1]};//发送data
			
			byte[] sendData = codingFactory.coding((byte)0x01, chost.getCode(), (byte)0x26, data);
			//开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);
			//等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "36",startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			if(!dataContext.equals("")){
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			
			String mark = "";
			if(flag){
				byte resultData = receiveData[12];
				switch(resultData){
				case 0 :
					mark = "开启";
					break;
				case 0x0f :
					mark = "关闭";
					break;
				case 0x55 :
					mark = "非手动模式";
					break;
				}
				log.info("关闭节点成功!");
			}else{
				mark = "error";
				log.info("关闭节点失败!");
			}
			model.addObject(mark);
		} catch (Exception e) {
			log.error("关闭节点异常!"+e);
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				openEquipment 
	 * @author 				杨贵松
	 * @date 				2014年3月7日 上午3:58:59
	 * @Description: 		开启节点进行浇灌
	 * @param eId
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="equipmentAllOpen.json",method=RequestMethod.POST)
	public ModelAndView openAllEquipment(@RequestBody String projectId){
		String pId = projectId.split("=")[1];
		ModelAndView model = new ModelAndView();
		try {
			ControlHost chost = controlHostInfoService.selectByProjectId(pId);
			if(chost == null){
				model.addObject("null");
				return model;
			}
			byte[] data = {0};//发送data
			
			byte[] sendData = codingFactory.coding((byte)0x01, chost.getCode(), (byte)0x0B, data);
			//开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);
			//等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "1B",startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			if(!dataContext.equals("")){
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			
			String mark = "";
			if(flag){
				byte resultData = receiveData[12];
				switch(resultData){
				case 0 :
					mark = "开启";
					break;
				case 0x0f :
					mark = "关闭";
					break;
				case 0x55 :
					mark = "非手动模式";
					break;
				}
				log.info("开启全部节点成功!");
			}else{
				mark = "error";
				log.info("开启全部节点失败!");
			}
			model.addObject(mark);
		} catch (Exception e) {
			log.error("开启全部节点异常!"+e);
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				openEquipment 
	 * @author 				杨贵松
	 * @date 				2014年3月8日 上午12:32:16
	 * @Description: 		关闭节点
	 * @param eId
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="equipmentAllClose.json",method=RequestMethod.POST)
	public ModelAndView closeAllEquipment(@RequestBody String projectId){
		String pId = projectId.split("=")[1];
		ModelAndView model = new ModelAndView();
		try {
			ControlHost chost = controlHostInfoService.selectByProjectId(pId);
			if(chost == null){
				model.addObject("null");
				return model;
			}
			byte[] data = {0x0f};//发送data
			
			byte[] sendData = codingFactory.coding((byte)0x01, chost.getCode(), (byte)0x0B, data);
			//开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);
			//等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "1B",startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			if(!dataContext.equals("")){
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			
			String mark = "";
			if(flag){
				byte resultData = receiveData[12];
				switch(resultData){
				case 0 :
					mark = "开启";
					break;
				case 0x0f :
					mark = "关闭";
					break;
				case 0x55 :
					mark = "非手动模式";
					break;
				}
				log.info("关闭全部节点成功!");
			}else{
				mark = "error";
				log.info("关闭全部节点失败!");
			}
			model.addObject(mark);
		} catch (Exception e) {
			log.error("关闭全部节点异常!"+e);
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				modifyEquipment 
	 * @author 				杨贵松
	 * @date 				2014年4月21日 上午11:16:21
	 * @Description: 		修改现场节点注册信息
	 * @param equipmentStr
	 * @param session
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="modifyEquipment.json",method=RequestMethod.POST)
	public ModelAndView modifyEquipment(@RequestBody String equipmentStr){
		String id = equipmentStr.split(",")[0];
		String oldCode = equipmentStr.split(",")[1];
		String newCode = equipmentStr.split(",")[2];
		ModelAndView model = new ModelAndView();
		try {
			ControlHost chost = controlHostInfoService.selectByProjectId(id);
			if(chost == null){
				model.addObject("null");
				return model;
			}
			byte[] data = codingFactory.string2BCD(oldCode+newCode);//发送data
			
			byte[] sendData = codingFactory.coding((byte)0x01, chost.getCode(), (byte)0x28, data);
			//开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);
			//等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "38",startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			if(!dataContext.equals("")){
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			
			String mark = "";
			if(flag){
				byte resultData = receiveData[12];
				switch(resultData){
				case 0x0A :
					mark = "ok";
					break;
				case (byte) 0xA0 :
					mark = "error";
					break;
				}
				log.info("修改现场节点注册信息成功!");
			}else{
				mark = "error";
				log.info("修改现场节点注册信息失败!");
			}
			model.addObject(mark);
		} catch (Exception e) {
			log.error("修改现场节点注册信息异常!"+e);
			model.addObject("error");
		}
		return model;
	}
	/**
	 * @Title: 				setTimesLen 
	 * @author 				杨贵松
	 * @date 				2014年4月8日 上午12:22:54
	 * @Description: 		设置时段
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="setTimesLen.json",method=RequestMethod.POST)
	public ModelAndView setTimesLen(@RequestBody String Str){
		ModelAndView model = new ModelAndView();
		//截取项目id
		String pId = Str.substring(0,Str.indexOf(","));
		//截取时间段，转换成字符串数组
		String[] times = Str.substring(Str.indexOf(",")+1,Str.length()).split(",");
		try {
			ControlHost chost = controlHostInfoService.selectByProjectId(pId);
			if(chost == null){
				model.addObject("null");
				return model;
			}
			byte[] data = codingFactory.string2BCD(times);//发送data
			
			byte[] sendData = codingFactory.coding((byte)0x01, chost.getCode(), (byte)0x0E, data);
			//开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);
			//等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "1E",startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			if(!dataContext.equals("")){
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			
			String mark = "";
			if(flag){
				byte resultData = receiveData[12];
				switch(resultData){
				case 0x0A :
					mark = "ok";
					break;
				case (byte)0xA0 :
					mark = "error";
					break;
				}
				log.info("设置时间段完成!");
			}else{
				mark = "error";
				log.info("设置时间段失败!");
			}
			model.addObject(mark);
		}catch(Exception e){
			log.error("设置时间段异常!"+e);
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				selectTimesLen 
	 * @author 				杨贵松
	 * @date 				2014年4月13日 上午1:16:55
	 * @Description: 		查询时间段
	 * @param Str
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectTimesLen.json",method=RequestMethod.POST)
	public ModelAndView selectTimesLen(@RequestBody String Str){
		ModelAndView model = new ModelAndView();
		try {
			ControlHost chost = controlHostInfoService.selectByProjectId(Str);
			if(chost == null){
				model.addObject("null");
				return model;
			}
			byte[] data = {};//发送data
			
			byte[] sendData = codingFactory.coding((byte)0x01, chost.getCode(), (byte)0x0F, data);
			//开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);
			//等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "1F",startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			if(!dataContext.equals("")){
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			
			String mark = "";
			flag = true;
			if(flag){
				mark = dataContext.substring(24,dataContext.length()-6);
				log.info("查询时间段完成!");
			}else{
				mark = "error";
				log.info("查询时间段失败!");
			}
			model.addObject(mark);
		}catch(Exception e){
			log.error("查询时间段异常!"+e);
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				setModel 
	 * @author 				杨贵松
	 * @date 				2014年4月8日 下午5:07:32
	 * @Description: 		设置控制模式
	 * @param Str
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="setModel.json",method=RequestMethod.POST)
	public ModelAndView setModel(@RequestBody String Str){
		ModelAndView model = new ModelAndView();
		String[] s = Str.split(",");
		try {
			ControlHost chost = controlHostInfoService.selectByProjectId(s[0]);
			if(chost == null){
				model.addObject("null");
				return model;
			}
			byte[] data = codingFactory.string2BCD(s[1]);//发送data
			
			byte[] sendData = codingFactory.coding((byte)0x01, chost.getCode(), (byte)0x07, data);
			//开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);  
			//等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "17",startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			if(!dataContext.equals("")){
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			
			String mark = "";
			if(flag){
				byte resultData = receiveData[12];
				switch(resultData){
				case (byte)0xF0 :
					mark = "F0";
					break;
				case (byte)0xF1 :
					mark = "F1";
					break;
				case (byte)0xF2 :
					mark = "F2";
					break;
				case (byte)0xF3 :
					mark = "F3";
					break;
				}
				log.info("设置控制模式完成!");
			}else{
				mark = "error";
				log.info("设置控制模式失败!");
			}
			model.addObject(mark);
		}catch(Exception e){
			log.error("设置控制模式异常!"+e);
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				selectModel 
	 * @author 				杨贵松
	 * @date 				2014年4月13日 上午1:13:46
	 * @Description: 		查询控制模式
	 * @param Str
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectModel.json",method=RequestMethod.POST)
	public ModelAndView selectModel(@RequestBody String Str){
		ModelAndView model = new ModelAndView();
		try {
			ControlHost chost = controlHostInfoService.selectByProjectId(Str);
			if(chost == null){
				model.addObject("null");
				return model;
			}
			byte[] data = {};//发送data
			
			byte[] sendData = codingFactory.coding((byte)0x01, chost.getCode(), (byte)0x08, data);
			//开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);  
			//等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "18",startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			if(!dataContext.equals("")){
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			
			String mark = "";
			if(flag){
				byte resultData = receiveData[12];
				switch(resultData){
				case (byte)0xF0 :
					mark = "F0";
				break;
				case (byte)0xF1 :
					mark = "F1";
				break;
				case (byte)0xF2 :
					mark = "F2";
				break;
				case (byte)0xF3 :
					mark = "F3";
				break;
				}
				log.info("查询控制模式完成!");
			}else{
				mark = "error";
				log.info("查询控制模式失败!");
			}
			model.addObject(mark);
		}catch(Exception e){
			log.error("查询控制模式异常!"+e);
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				setParameter 
	 * @author 				杨贵松
	 * @date 				2014年4月13日 下午5:25:42
	 * @Description: 		设置自控参数
	 * @param Str
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="setParameter.json",method=RequestMethod.POST)
	public ModelAndView setParameter(@RequestBody String Str){
		ModelAndView model = new ModelAndView();
		String[] s = Str.split(",");
		int tUp = (int)(Float.parseFloat(s[2])*100);
		int tDown = (int)(Float.parseFloat(s[3])*100);
		byte[] data = new byte[5];//发送data
		data[0] = Byte.parseByte(s[1]);
		if(tUp<0){
			data[1] = (byte) (tUp/100) ;
			data[2] = (byte) (-tUp%100) ;
		}else{
			data[1] = (byte) (tUp/100) ;
			data[2] =(byte) (tUp%100);
		}
		if(tDown<0){
			data[3] = (byte) (tDown/100);
			data[4] = (byte) (-tDown%100);
		}else{
			data[3] = (byte) (tDown/100);
			data[4] = (byte) (tDown%100);
		}
		
		try {
			ControlHost chost = controlHostInfoService.selectByProjectId(s[0]);
			if(chost == null){
				model.addObject("null");
				return model;
			}
			
			byte[] sendData = codingFactory.coding((byte)0x01, chost.getCode(), (byte)0x09, data);
			//开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);  
			//等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "19",startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			if(!dataContext.equals("")){
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			
			String mark = "";
			if(flag){
				byte resultData = receiveData[12];
				switch(resultData){
					case (byte)0x0A :
						mark = "ok";
						break;
					case (byte)0xA0 :
						mark = "error";
						break;
					}
				log.info("设置自控参数完成!");
			}else{
				mark = "error";
				log.info("设置自控参数失败!");
			}
			model.addObject(mark);
		}catch(Exception e){
			log.error("设置自控参数异常!"+e);
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				selectParameter 
	 * @author 				杨贵松
	 * @date 				2014年4月13日 下午6:17:26
	 * @Description: 		查询参数
	 * @param Str
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="selectParameter.json",method=RequestMethod.POST)
	public ModelAndView selectParameter(@RequestBody String Str){
		ModelAndView model = new ModelAndView();
		
		try {
			ControlHost chost = controlHostInfoService.selectByProjectId(Str);
			if(chost == null){
				model.addObject("null");
				return model;
			}
			byte[] data = {};//发送data
			
			byte[] sendData = codingFactory.coding((byte)0x01, chost.getCode(), (byte)0x0A, data);
			//开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);  
			//等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "1A",startDate);
			
			boolean flag = false;
			byte[] receiveData = null;
			if(!dataContext.equals("")){
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			
			String mark = "";
			if(flag){
				mark = (int)receiveData[12]+",";
				if(receiveData[13]<0){
					mark += (float)Math.round(((float)(receiveData[13]-receiveData[14]*0.01))*10)/10+",";
				}else{
					mark += (float)Math.round(((float)(receiveData[13]+receiveData[14]*0.01))*10)/10+",";
				}
				
				if(receiveData[15]<0){
					mark += (float)Math.round(((float)(receiveData[15]-receiveData[16]*0.01))*10)/10+",";
				}else{
					mark += (float)Math.round(((float)(receiveData[15]+receiveData[16]*0.01))*10)/10+",";
				}
				log.info("查询自控参数完成!");
			}else{
				mark = "error";
				log.info("查询自控参数失败!");
			}
			model.addObject(mark);
		}catch(Exception e){
			log.error("查询自控参数异常!"+e);
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				searchEquipment 
	 * @author 				杨贵松
	 * @date 				2014年4月22日 下午3:40:41
	 * @Description: 		搜索现场节点地址
	 * @param pId
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="searchEquipment.json",method=RequestMethod.POST)
	public ModelAndView searchEquipment(@RequestBody String pId){
		ModelAndView model = new ModelAndView();
		try {
			ControlHost chost = controlHostInfoService.selectByProjectId(pId);
			if(chost == null){
				model.addObject("null");
				return model;
			}
			byte[] data = {};//发送data
			
			byte[] sendData = codingFactory.coding((byte)0x01, chost.getCode(), (byte)0x25, data);
			//开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);
			//等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitDataForSearchEquipment(chost.getCode(), "35",startDate).trim();
			
			boolean flag = false;
			byte[] receiveData = null;
			if(!dataContext.equals("")){
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			
			String mark = "";
			if(flag){
				int num = receiveData[12];
				String resultData = dataContext.substring(26,dataContext.length()-6);
				String address = "";
				for(int i=0;i<num;i++){
					address += resultData.substring(i*4,(i+1)*4)+",";
				}
				if(num==0){
					mark = "0";
				}else {
					mark = address.substring(0,address.length()-1);
				}
				log.info("搜索现场节点地址成功!");
			}else{
				mark = "error";
				log.info("搜索现场节点地址失败!");
			}
			model.addObject(mark);
		} catch (Exception e) {
			log.error("搜索现场节点地址异常!"+e);
			model.addObject("error");
		}
		return model;
	}
	
	/**
	 * @Title: 				addNetWorkEquipment 
	 * @author 				杨贵松
	 * @date 				2014年4月22日 下午7:58:37
	 * @Description: 		注册节点信息到现场控制器
	 * @param equipment
	 * @return 
	 * ModelAndView 				返回
	 */
	@Login
	@RequestMapping(value="addNetWorkEquipment.json",method=RequestMethod.POST)
	public ModelAndView addNetWorkEquipment(@RequestBody String equipmentList,HttpSession session){
		ModelAndView model = new ModelAndView();
		String[] eStr = equipmentList.split("&");
		User user = (User) session.getAttribute("user");
		try {
			List<Equipment> list = new ArrayList<Equipment>();
			ControlHost chost = controlHostInfoService.selectByProjectId(eStr[0]);
			if(chost == null){
				model.addObject("null");
				return model;
			}
			String[] equipmentStr = eStr[1].split(";");
			String eCodeStr = "";
			if(equipmentStr.length<10){
				eCodeStr += "0"+equipmentStr.length;
			}else{
				eCodeStr += ""+equipmentStr.length;
			}
			
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
				eCodeStr += e.getCode();
			}
			byte[] data = codingFactory.string2BCD(eCodeStr);//发送data
			
			byte[] sendData = codingFactory.coding((byte)0x01, chost.getCode(), (byte)0x22, data);
			//开始的时间
			Date startDate = new Date();
			Client.sendToServer(sendData);
			//等待获取主机返回的指令，等待10秒
			String dataContext = netWorkService.waitData(chost.getCode(), "32",startDate).trim();
			
			boolean flag = false;
			byte[] receiveData = null;
			if(!dataContext.equals("")){
				receiveData = codingFactory.string2BCD(dataContext);
				flag = checkCoding.checkReceiveCoding(receiveData, sendData);
			}
			
			String mark = "";
			if(flag){
				byte num = receiveData[12];
				if(num == 0x0A){
					mark ="ok";
				}else if(num == (byte)0xA0){
					mark = "error";
				}
				log.info("注册节点信息到现场控制器成功!");
			}else{
				mark = "error";
				log.info("注册节点信息到现场控制器失败!");
			}
			model.addObject(mark);
		} catch (Exception e) {
			log.error("注册节点信息到现场控制器异常!"+e);
			model.addObject("error");
		}
		return model;
	}
}

