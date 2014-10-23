package com.rest.tiis.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.rest.tiis.beans.ControlHost;
import com.rest.tiis.beans.DataTemp;
import com.rest.tiis.beans.SystemLog;
import com.rest.tiis.beans.User;
import com.rest.tiis.mapping.ControlHostMapper;
import com.rest.tiis.mapping.DataTempMapper;
import com.rest.tiis.mapping.SystemLogMapper;
import com.rest.tiis.mapping.UserMapper;
import com.rest.tiis.service.NetWorkService;
import com.rest.tiis.util.CommonUtiles;

/**
 * @project:					TIIS 
 * @Title: 						NetWorkUtil.java 		
 * @Package 					com.rest.tiis.util		
 * @Description: 				公共指令解析存储类 
 * @author 						杨贵松   
 * @date 						2014年3月6日 下午5:30:54 
 * @version 					V1.0
 */
@Service
public class NetWorkServiceImpl implements NetWorkService{
	private static final Logger log = Logger.getLogger(NetWorkServiceImpl.class.getName());
	@Autowired
	private DataTempMapper dataTempMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ControlHostMapper controlHostMapper;
	@Autowired
	private SystemLogMapper systemLogMapper;
	
	/**
	 * @Title: 				saveNetData 
	 * @author 				杨贵松
	 * @date 				2014年3月6日 下午8:40:45
	 * @Description: 		服务器获取的指令，需保存到临时的存储空间中，供请求调用 
	 * @param data 
	 * void 				返回
	 */
	public void saveNetData(String address,String controlId,String data){
		try {
			Date date = CommonUtiles.getSystemDateTime();
			DataTemp dataTemp = new DataTemp();
			dataTemp.setAddress(address);
			dataTemp.setControlid(controlId);
			dataTemp.setDatacontext(data);
			dataTemp.setReceivetime(date);
			dataTempMapper.insert(dataTemp);
		} catch (Exception e) {
			log.error("服务器保存接收的数据到DataTemp表中异常!"+e);
		}
	}
	
	/**
	 * @Title: 				getNetData 
	 * @author 				杨贵松
	 * @date 				2014年3月6日 下午8:45:25
	 * @Description: 		从临时存储空间中获取服务器返回的指令 
	 * @param data 
	 * void 				返回
	 */
	public DataTemp getNetData(String address,String controlId,Date dateTime,Date startDate){
		DataTemp dateTemp = new DataTemp();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("address", address);
		map.put("controlId", controlId);
		map.put("startDate", startDate);
		map.put("endDate", dateTime);
		try {
			dateTemp = dataTempMapper.selectDataTemp(map);
		} catch (Exception e) {
			log.error("获取服务器保存的DataTemp临时数据异常!"+e);
		}
		return dateTemp;
	}
	
	/**
	 * @Title: 				waitData 
	 * @author 				杨贵松
	 * @date 				2014年3月7日 上午1:20:29
	 * @Description: 		等待接收主机返回数据，超过10秒结束等待 
	 * @return 
	 * String 				返回
	 * @throws ParseException 
	 */
	public String waitData(String address,String ContralCode,Date startDate) throws Exception{
		Date endDate = CommonUtiles.getLastOneSecondDate();
		String dataContext = "";
		long time = 0;
		while(dataContext.equals("") && time<endDate.getTime()){
			Thread.sleep(500);
			DataTemp dataTemp = new DataTemp();
			dataTemp = getNetData(address, ContralCode,endDate,startDate);
			if(dataTemp==null){
				time = System.currentTimeMillis();
				continue;
			}else if(dataTemp.getDatacontext().equals("")){
				time = System.currentTimeMillis();
				continue;
			}else{
				dataContext = dataTemp.getDatacontext();
				break;
			}
		}
		return dataContext;
	}
	
	/**
	 * @Title: 				waitDataForSearchEquipment 
	 * @author 				杨贵松
	 * @date 				2014年4月22日 下午3:51:33
	 * @Description: 		等待接收主机返回的搜索节点地址数据，超过20秒结束等待 
	 * @param address
	 * @param ContralCode
	 * @param startDate
	 * @return
	 * @throws Exception 
	 * String 				返回
	 */
	public String waitDataForSearchEquipment(String address,String ContralCode,Date startDate) throws Exception{
		Date endDate = CommonUtiles.getLastTwoSecondDate();
		String dataContext = "";
		long time = 0;
		while(dataContext.equals("") && time<endDate.getTime()){
			Thread.sleep(500);
			DataTemp dataTemp = new DataTemp();
			dataTemp = getNetData(address, ContralCode,endDate,startDate);
			if(dataTemp==null){
				time = System.currentTimeMillis();
				continue;
			}else if(dataTemp.getDatacontext().equals("")){
				time = System.currentTimeMillis();
				continue;
			}else{
				dataContext = dataTemp.getDatacontext();
				break;
			}
		}
		return dataContext;
	}
	
	/**
	 * @Title: 				pushMsg 
	 * @author 				杨贵松
	 * @date 				2014年7月12日 下午4:22:28
	 * @Description: 		通知推送 
	 * @param hostCode 
	 * void 				返回
	 */
	public void pushMsg(String hostCode) {
		String APPID = "djUGtMrQ8A64fC664vH137";
		String APPKEY = "hKGLMQHjGr88YgzVlEzkb8";
		String MASTERSECRET = "1vCOsPMlFu7dUzZZWVm1c9";
		String CLIENTID = "";
		String API = "http://sdk.open.api.igexin.com/apiex.htm"; // OpenService接口地址
		// 推送主类
		IIGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);
		try {
			List<User> list = new ArrayList<User>();
			list = userMapper.selectUserByHostCode(hostCode);
			ControlHost host = controlHostMapper.selectHostByCode(hostCode); 
			if (!CollectionUtils.isEmpty(list)) {
				// 接收者
				List<Target> targets = new ArrayList<Target>();
				ListMessage message = new ListMessage();
				
				// 通知模版：NotificationTemplate
				NotificationTemplate template = new NotificationTemplate();
				template.setAppId(APPID);
				template.setAppkey(APPKEY);
				template.setTitle("警告【Rainet云灌溉】"); // 通知标题
				template.setText("项目【"+host.getProject().getName()+"】下有一个节点监测的湿度过低!");//通知内容
				template.setLogo("push.png");//通知logo
				
				// 收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动
				template.setTransmissionType(1);
				
				message.setData(template);
				message.setOffline(true); // 用户当前不在线时，是否离线存储,可选
				message.setOfflineExpireTime(72 * 3600 * 1000); // 离线有效时间，单位为毫秒，可选
				List<SystemLog> sysLogList = new ArrayList<SystemLog>();
				Date date = CommonUtiles.getSystemDateTime();
				for (User user : list) {
					//保存日志
					SystemLog systemLog = new SystemLog();
					systemLog.setUserid(user.getId());
					systemLog.setLogcontext("项目【"+host.getProject().getName()+"】下有一个节点监测的湿度过低,请及时查看!");
					systemLog.setLogtime(date);
					systemLog.setLogtype(1);// 0 表示采集异常报警日志 , 1 表示实时土壤温度过低报警日志 
					systemLog.setLogstatus("0");// 0 表示未读, 1 表示已读
					sysLogList.add(systemLog);
					
					//声明推送用户对象
					CLIENTID = user.getClientid();

					Target target = new Target();
					target.setAppId(APPID);
					target.setClientId(CLIENTID);
					targets.add(target);
				}
				systemLogMapper.insert(sysLogList);
				
				String contentId = push.getContentId(message);
				push.pushMessageToList(contentId, targets);
				log.error("警告推送完成!");
			}
		} catch (Exception e) {
			log.error("通知推送异常!"+e);
		}
	}

	/**
	 * @Title: 				selectData 
	 * @author 				杨贵松
	 * @date 				2014年7月14日 下午4:33:28
	 * @Description: 		获取最新的一条报警指令
	 * @param hostCode 
	 * void 				返回
	 */
	@Override
	public DataTemp selectData(String address, String controlId) throws Exception {
		DataTemp dateTemp = new DataTemp();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("address", address);
		map.put("controlId", controlId);
		dateTemp = dataTempMapper.selectDataMax(map);
		return dateTemp;
	}
}
