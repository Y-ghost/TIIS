package com.rest.tiis.filter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rest.tiis.beans.ControlHost;
import com.rest.tiis.beans.DataTemp;
import com.rest.tiis.beans.Equipment;
import com.rest.tiis.beans.EquipmentData;
import com.rest.tiis.beans.SystemLog;
import com.rest.tiis.beans.UserProjectRel;
import com.rest.tiis.mapping.ControlHostMapper;
import com.rest.tiis.mapping.DataTempMapper;
import com.rest.tiis.mapping.EquipmentDataMapper;
import com.rest.tiis.mapping.EquipmentMapper;
import com.rest.tiis.mapping.SystemLogMapper;
import com.rest.tiis.mapping.UserProjectRelMapper;
import com.rest.tiis.service.NetWorkService;
import com.rest.tiis.util.CheckReceiveCodingUtil;
import com.rest.tiis.util.CodingFactoryUtil;
import com.rest.tiis.util.CommonUtiles;
import com.rest.tiis.util.network.Client;

/**
 * @project:					TIIS 
 * @Title: 						MyTimer.java 		
 * @Package 					com.rest.tiis.filter		
 * @Description: 				时钟任务
 * @author 						杨贵松   
 * @date 						2014年4月22日 下午5:12:16 
 * @version 					V1.0
 */
@Component
public class MyTimer {
	private Logger log = Logger.getLogger(MyTimer.class.getName());
	private CodingFactoryUtil codingFactory = new CodingFactoryUtil();
	private CheckReceiveCodingUtil checkCoding = new CheckReceiveCodingUtil();
	@Autowired
	private NetWorkService netWorkService;
	@Autowired
	private DataTempMapper dataTempMapper;
	@Autowired
	private ControlHostMapper controlHostMapper;
	@Autowired
	private EquipmentMapper equipmentMapper;
	@Autowired
	private EquipmentDataMapper equipmentDataMapper;
	@Autowired
	private UserProjectRelMapper userProjectRelMapper;
	@Autowired
	private SystemLogMapper systemLogMapper;

	/**
	 * @Title: 				autoDeleteOldTempData 
	 * @author 				杨贵松
	 * @date 				2014年5月1日 下午3:28:32
	 * @Description: 		每天定时01：00整，系统自动删除存储接收指令的临时表旧数据（大于一天的数据）
	 * void 				返回
	 */
	public void autoDeleteOldTempData() {
		// 先查询数据list
		try {
			long startTime = System.currentTimeMillis();
			List<DataTemp> list = new ArrayList<DataTemp>();
			list = dataTempMapper.selectAllOldData();
			if (list.size()>0) {
				// 删除历史数据
				dataTempMapper.deleteAllOldData(list);
				log.info("每天定时01：00整，系统自动删除存储接收指令的临时表旧数据成功");
			}
			long endTime = System.currentTimeMillis();
			log.info("每天定时01：00整，系统自动删除存储接收指令的临时表旧数据,总用时：" + (endTime - startTime) + " ms.");
		} catch (Exception e) {
			log.error("每天定时01：00整，系统自动删除存储接收指令的临时表旧数据异常!" + e);
		}
	}
	
	/**
	 * @Title: 				autoValidContrlHostTime 
	 * @author 				杨贵松
	 * @date 				2014年5月1日 下午3:28:20
	 * @Description: 		每天定时03：00整，系统自动对主机进行校时
	 * void 				返回
	 */
	public void autoValidContrlHostTime() {
		try {
			// 查询主机列表list
			List<ControlHost> list = new ArrayList<ControlHost>();
			Map<String,Object> mapTmp = new HashMap<String, Object>();
			mapTmp.put("projectId", "");//查询公用方法
			int hostCount = controlHostMapper.selectAllHostCounts(mapTmp);
			
			if (hostCount > 0) {
				int num = 0;
				if(hostCount / 500 > 0 && hostCount % 500 == 0){
					num = hostCount / 500;
				}else{
					num = hostCount / 500 + 1;
				}
				for (int i = 0; i < num; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("index", i);
					map.put("pageSize", 500);
					map.put("projectId", "");//查询公用方法
					list = controlHostMapper.selectAllHostPages(map);
					if (list.size() > 0) {
						// 多线程校验时间
						for (final ControlHost host : list) {
							validControlHostTime validControlHostTime = new validControlHostTime(host.getCode());
							validControlHostTime.start();
						}
						log.info("每天定时03：00整，系统自动对控制器主机校时，第 ‘" + (i + 1) + "’组 完成!");
					} else {
						log.info("每天定时03：00整，系统自动对控制器主机校时，第 ‘" + (i + 1) + "’组 失败，未查询到主机信息!");
					}
					Thread.sleep(1000 * 60);// 校时分组进行，500一组，每组停顿60秒
				}
			} else {
				log.info("每天定时03：00整，系统自动对控制器主机校时,未查询到主机信息!");
			}
		} catch (Exception e) {
			log.error("每天定时03：00整，系统自动对控制器主机校时异常!" + e);
		}
	}
	
	/**
	 * @Title: 				validControlHostTime 
	 * @author 				杨贵松
	 * @date 				2014年4月27日 下午2:07:02
	 * @Description: 		多线程--现场控制器主机校时 
	 * @param code
	 * @return 
	 * boolean 				返回
	 */
	class validControlHostTime extends Thread {
		private String code;

		public validControlHostTime(String code) {
			this.code = code;
		}

		@Override
		public void run() {
			try {
				String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				byte[] data = codingFactory.string2BCD(time);

				byte[] sendData = codingFactory.coding((byte) 0x01, code, (byte) 0x0C, data);
				// 开始的时间
				Date startDate = new Date();
				Client.sendToServer(sendData);
				// 等待获取主机返回的指令，等待10秒
				String dataContext = netWorkService.waitData(code, "1C", startDate);

				boolean flag = false;
				byte[] receiveData = null;
				if (!dataContext.equals("")) {
					receiveData = codingFactory.string2BCD(dataContext);
					flag = checkCoding.checkReceiveCoding(receiveData, sendData);
				}
				if (flag) {
					byte num = receiveData[12];
					if (num == (byte)0xA0) {
						// 校时失败
						log.info("编号为： " + code + " 的监控主机校时失败!");
					}
				} else {
					// 校时失败
					log.info("编号为： " + code + " 的监控主机校时失败(未接收到指令或者接收的指令格式不对)!");
				}
			} catch (Exception e) {
				// 校时异常
				log.info("编号为： " + code + " 的监控主机校时异常!" + e);
			}
		}
	}
	
	
	/**
	 * @Title: 				autoCollectData 
	 * @author 				杨贵松
	 * @date 				2014年6月10日 下午5:17:21
	 * @Description: 		每天定时04：00整，系统自动采集现场节点数据并存储到数据库
	 * void 				返回
	 */
	
	public void autoCollectData() {
		try {
			// 查询主机列表list
			List<ControlHost> list = new ArrayList<ControlHost>();
			Map<String,Object> mapTmp = new HashMap<String, Object>();
			mapTmp.put("projectId", "");//查询公用方法
			int hostCount = controlHostMapper.selectAllHostCounts(mapTmp);
			
			if (hostCount > 0) {
				int num = 0;
				if(hostCount / 500 > 0 && hostCount % 500 == 0){
					num = hostCount / 500;
				}else{
					num = hostCount / 500 + 1;
				}
				for (int i = 0; i < num; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("index", i);
					map.put("pageSize", 500);
					map.put("projectId", "");//查询公用方法
					list = controlHostMapper.selectAllHostPages(map);
					if (list.size() > 0) {
						// 多线程校验时间
						for (final ControlHost host : list) {
							CollectData collectData = new CollectData(host.getId(),host.getCode());
							collectData.start();
						}
						log.info("每天定时04：00整，系统自动采集现场节点数据，第 ‘" + (i + 1) + "’组 完成!");
					} else {
						log.info("每天定时04：00整，系统自动采集现场节点数据，第 ‘" + (i + 1) + "’组 失败，未查询到主机信息!");
					}
					Thread.sleep(1000 * 60);// 校时分组进行，500一组，每组停顿60秒
				}
			} else {
				log.info("每天定时04：00整，系统自动采集现场节点数据,未查询到主机信息!");
			}
		} catch (Exception e) {
			log.error("每天定时04：00整，系统自动采集现场节点数据异常!" + e);
		}
	}
	
	/**
	 * @Title: 				CollectData 
	 * @author 				杨贵松
	 * @date 				2014年6月10日 下午5:17:21
	 * @Description: 		多线程--采集现场节点数据并存储
	 * @param code
	 * @return 
	 * boolean 				返回
	 */
	class CollectData extends Thread {
		private int hId;
		private String code;
		
		public CollectData(int hId,String code) {
			this.hId = hId;
			this.code = code;
		}
		
		@Override
		public void run() {
			try {
				byte[] data = {};
				
				byte[] sendData = codingFactory.coding((byte) 0x01, code, (byte) 0x05, data);
				// 开始的时间
				Date startDate = new Date();
				Client.sendToServer(sendData);
				// 等待获取主机返回的指令，等待10秒
				String dataContext = netWorkService.waitData(code, "15", startDate);
				
				boolean flag = false;
				byte[] receiveData = null;
				if (!dataContext.equals("")) {
					receiveData = codingFactory.string2BCD(dataContext);
					flag = checkCoding.checkReceiveCoding(receiveData, sendData);
				}
				if (flag) {
					int num = receiveData[12];
					String usingData = dataContext.substring(26,dataContext.length()-3);
					
					List<EquipmentData> list = new ArrayList<EquipmentData>();
					
					for(int i = 0 ;i < num ; i++){
						String eCode = usingData.substring(i*12,i*12+4);
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("hId", hId);
						map.put("eCode", eCode);
						Equipment equipment = equipmentMapper.selectEquipmentByhIdAndEcode(map);
						
						long times = Long.valueOf(usingData.substring(i*12+4,i*12+12),16);
						
						if(equipment!=null){
							EquipmentData equipmentData1 = new EquipmentData();
							Date date = CommonUtiles.getSystemDateTime();
							//查询数据库中最新的一条采集数据
							equipmentData1 = equipmentDataMapper.selectEquipmentDataByeId(equipment.getId());
							
							if(equipmentData1!=null && equipmentData1.getTimes() <= times){//当天采集的数据合理，保存
								EquipmentData equipmentData = new EquipmentData();
								equipmentData.setTimes(times);
								equipmentData.setCreatetime(date);
								equipmentData.setEquipmentid(equipment.getId());
								list.add(equipmentData);
							}else if(equipmentData1!=null && equipmentData1.getTimes() > times){//采集异常保存系统日志
								List<UserProjectRel> relList = userProjectRelMapper.selectRelBypId(equipment.getProject().getId());
								List<SystemLog> sysLogList = new ArrayList<SystemLog>();
								for(UserProjectRel Rel:relList){
									SystemLog systemLog = new SystemLog();
									systemLog.setUserid(Rel.getUserid());
									systemLog.setLogcontext("采集：" + code + "号主机下【" + equipment.getName() + "】监控节点数据异常，当天采集的数据小于历史数据!");
									systemLog.setLogtime(date);
									systemLog.setLogtype(0);// 0 表示采集异常报警日志 , 1 表示实时土壤温度过低报警日志
									systemLog.setLogstatus("0");// 0 表示未读, 1 表示已读
									sysLogList.add(systemLog);
								}
								systemLogMapper.insert(sysLogList);
								log.info("数据采集--" + code + " 号主机下【" + equipment.getName() + "】监控节点数据异常，当天采集的数据小于历史数据!");
							}else if(equipmentData1==null){//这是块新节点，没有采集的数据，开始保存
								EquipmentData equipmentData = new EquipmentData();
								equipmentData.setTimes(times);
								equipmentData.setCreatetime(CommonUtiles.getSystemDateTime());
								equipmentData.setEquipmentid(equipment.getId());
								list.add(equipmentData);
							}
						}else{
							log.info("数据采集--" + code + " 号主机下【" + eCode + "】监控节点数据采集失败，节点信息为空，可能未注册!");
						}
					}
					//将采集的节点数据保存到数据库
					if(!CollectionUtils.isEmpty(list)){
						equipmentDataMapper.insert(list);
					}
				} else {
					// 数据采集失败
					ControlHost host = controlHostMapper.selectById(hId);
					List<UserProjectRel> relList = userProjectRelMapper.selectRelBypId(host.getProject().getId());
					List<SystemLog> sysLogList = new ArrayList<SystemLog>();
					Date date = CommonUtiles.getSystemDateTime();
					for(UserProjectRel Rel:relList){
						SystemLog systemLog = new SystemLog();
						systemLog.setUserid(Rel.getUserid());
						systemLog.setLogcontext("采集：" + code + "号主机的数据失败(未接收到指令或者接收的指令格式不对)!");
						systemLog.setLogtime(date);
						systemLog.setLogtype(0);// 0 表示采集异常报警日志 , 1 表示实时土壤温度过低报警日志
						systemLog.setLogstatus("0");// 0 表示未读, 1 表示已读
						sysLogList.add(systemLog);
					}
					systemLogMapper.insert(sysLogList);
					log.info("数据采集--" + code + "号主机数据采集失败(未接收到指令或者接收的指令格式不对)!");
				}
			} catch (Exception e) {
				// 数据采集异常
				log.info("数据采集--" + code + "号主机数据采集异常!" + e);
			}
		}
	}
}
