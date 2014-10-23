package com.rest.tiis.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rest.tiis.beans.Equipment;
import com.rest.tiis.beans.User;
import com.rest.tiis.mapping.EquipmentDataMapper;
import com.rest.tiis.mapping.EquipmentMapper;
import com.rest.tiis.service.EquipmentDataService;
/**
 * @project:					TIIS 
 * @Title: 						EquipmentDataServiceImpl.java 		
 * @Package 					com.rest.tiis.service.impl		
 * @Description: 				统计分析业务层
 * @author 						杨贵松   
 * @date 						2014年6月23日 下午7:31:26 
 * @version 					V1.0
 */
@Service
public class EquipmentDataServiceImpl implements EquipmentDataService {
	@Autowired
	private EquipmentMapper equipmentMapper;
	@Autowired
	private EquipmentDataMapper equipmentDataMapper;

	
	/**
	 * @Title: 				getValueForYear 
	 * @author 				杨贵松
	 * @date 				2014年6月23日 下午7:31:26 
	 * @Description: 		以年为单位，根据统计周期获取用水量,并返回json类型的Map数据
	 * @param circleDate
	 * @return 
	 * Map<String, Long> 				返回
	 */
	@Override
	public Map<String, Double> getValueForYear(User user , String pId, String circleDate) throws Exception {
		List<Equipment> eList = new ArrayList<Equipment>();
		Map<String, Double> map = new HashMap<String, Double>();
		String[] years = circleDate.split(",");
		if(pId.equals("0000")){
			if(user.getRole()==0){
				eList = equipmentMapper.selectAllEquipment();
			}else{
				eList = equipmentMapper.selectAllEquipmentByUser(user.getId());
			}
		}else{
			eList = equipmentMapper.selectEquipmentByProjectId(Integer.parseInt(pId));
		}
		
		if(!CollectionUtils.isEmpty(eList) && years.length>0){
			DecimalFormat df = new DecimalFormat("#0.00");
			for(int i=0;i<years.length;i++){
				double waterData = 0;
				for(Equipment equipment:eList){
					Map<String, Object> mapTmp = new HashMap<String, Object>();
					mapTmp.put("eId", equipment.getId());
					mapTmp.put("circleYear", years[i]);
					double fp = (double)equipment.getFlowparameter();//获取节点流量参数
					long val = equipmentDataMapper.getValueForYear(mapTmp);//获取节点统计周期内累计采集量
					if(fp>0){//保证除数不为0
						waterData += val/fp;//计算总该节点总的用水量
					}
				}
				String waterDataStr = df.format(waterData);
				map.put(years[i], Double.parseDouble(waterDataStr));
			}
		}else{
			for(int i=0;i<years.length;i++){
				map.put(years[i], 0.00);
			}
		}
		return map;
	}

	/**
	 * @Title: 				getValueForMouth 
	 * @author 				杨贵松
	 * @date 				2014年6月17日 下午6:21:25
	 * @Description: 		管理员以月为单位，根据统计周期获取用水量,并返回json类型的Map数据
	 * @param pId
	 * @param circleDate
	 * @return 
	 * String 				返回
	 */
	@Override
	public Map<String, Double> getValueForMouth(User user , String pId, String circleDate) throws Exception {
		List<Equipment> eList = new ArrayList<Equipment>();
		Map<String, Double> map = new HashMap<String, Double>();
		String[] mouths = circleDate.split(",");
		if(pId.equals("0000")){
			if(user.getRole()==0){
				eList = equipmentMapper.selectAllEquipment();
			}else{
				eList = equipmentMapper.selectAllEquipmentByUser(user.getId());
			}
		}else{
			eList = equipmentMapper.selectEquipmentByProjectId(Integer.parseInt(pId));
		}
		
		if(!CollectionUtils.isEmpty(eList) && mouths.length>0){
			DecimalFormat df = new DecimalFormat("#0.00");
			for(int i=0;i<mouths.length;i++){
				double waterData = 0;
				for(Equipment equipment:eList){
					Map<String, Object> mapTmp = new HashMap<String, Object>();
					mapTmp.put("eId", equipment.getId());
					mapTmp.put("circleMouth", mouths[i]);
					double fp = (double)equipment.getFlowparameter();//获取节点流量参数
					long val = equipmentDataMapper.getValueForMouth(mapTmp);//获取节点统计周期内累计采集量
					if(fp>0){//保证除数不为0
						waterData += val/fp;//计算总该节点总的用水量
					}
				}
				String waterDataStr = df.format(waterData);
				map.put(mouths[i], Double.parseDouble(waterDataStr));
			}
		}else{
			for(int i=0;i<mouths.length;i++){
				map.put(mouths[i], 0.00);
			}
		}
		return map;
	}

	/**
	 * 查询节点采集的最新一条数据
	 */
	@Override
	public long selectDataByEId(Integer id) throws Exception {
		return equipmentDataMapper.selectDataByEId(id);
	}
}
