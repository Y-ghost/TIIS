package com.rest.tiis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.tiis.beans.Equipment;
import com.rest.tiis.mapping.EquipmentDataMapper;
import com.rest.tiis.mapping.EquipmentMapper;
import com.rest.tiis.mapping.EquipmentStatusMapper;
import com.rest.tiis.service.EquipmentInfoService;

/**
 * @project:					TIIS 
 * @Title: 						EquipmentInfoServiceImpl.java 		
 * @Package 					com.rest.tiis.service.impl		
 * @Description: 				节点信息业务逻辑层
 * @author 						杨贵松   
 * @date 						2014年2月24日 下午11:27:00 
 * @version 					V1.0
 */
@Service
public class EquipmentInfoServiceImpl implements EquipmentInfoService {

	@Autowired
	private EquipmentMapper equipmentMapper;
	@Autowired
	private EquipmentDataMapper equipmentDataMapper;
	@Autowired
	private EquipmentStatusMapper equipmentStatusMapper;
	/**
	 * 添加新节点
	 */
	@Override
	public void addEquipment(Equipment equipment) throws Exception{
		equipmentMapper.addEquipment(equipment);
	}
	
	/**
	 * 根据项目id查询节点信息
	 */
	@Override
	public List<Equipment> selectEquipmentByProjectId(String projectId)  throws Exception{
		List<Equipment> list = equipmentMapper.selectEquipmentByProjectId(Integer.parseInt(projectId));
		return list;
	}
	
	/**
	 * 根据节点id查询节点信息
	 */
	@Override
	public Equipment selectEquipmentById(String equipmentId) throws Exception {
		Equipment equipment = equipmentMapper.selectEquipmentById(Integer.parseInt(equipmentId));
		return equipment;
	}

	@Override
	public void deleteEquipmentData(int pId)  throws Exception{
		equipmentDataMapper.deleteEquipmentData(pId);
	}

	@Override
	public void deleteEquipment(int pId)  throws Exception{
		equipmentMapper.deleteEquipment(pId);
	}

	@Override
	public void deleteEquipmentStatus(int pId)  throws Exception{
		equipmentStatusMapper.deleteEquipmentStatus(pId);
	}

	@Override
	public List<Equipment> selectAllEquipmentPages(Map<String,Object> map) throws Exception {
		return equipmentMapper.selectAllEquipmentPages(map);
	}

	@Override
	public List<Equipment> selectAllEquipmentPagesByUser(Map<String,Object> map)  throws Exception{
		return equipmentMapper.selectAllEquipmentPagesByUser(map);
	}

	@Override
	public int selectAllEquipmentCounts(Map<String,Object> map) throws Exception{
		return equipmentMapper.selectAllEquipmentCounts(map);
	}

	@Override
	public int selectAllEquipmentCountsByUser(Map<String,Object> map) throws Exception{
		return equipmentMapper.selectAllEquipmentCountsByUser(map);
	}

	@Override
	public void deleteEquipmentStatusByHostId(int hId) throws Exception {
		equipmentStatusMapper.deleteEquipmentStatusByHostId(hId);
	}

	@Override
	public void deleteEquipmentDataByHostId(int hId) throws Exception {
		equipmentDataMapper.deleteEquipmentDataByHostId(hId);
	}

	@Override
	public void deleteEquipmentByHostId(int hId) throws Exception {
		equipmentMapper.deleteEquipmentByHostId(hId);
	}

	@Override
	public Equipment validOtherEquipmentNameAndCode(String id, String name, String code, int controlHostId) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", Integer.parseInt(id));
		map.put("name", name);
		map.put("code", code);
		map.put("controlHostId", controlHostId);
		return equipmentMapper.validOtherEquipmentNameAndCode(map);
	}

	@Override
	public void modifyEquipment(Equipment equipment) throws Exception {
		equipmentMapper.modifyEquipment(equipment);
	}

	@Override
	public void deleteEquipmentByEquipmentId(int eId) throws Exception {
		equipmentMapper.deleteEquipmentByEquipmentId(eId);
	}

	@Override
	public void deleteEquipmentStatusByEquipmentId(int eId) throws Exception {
		equipmentStatusMapper.deleteEquipmentStatusByEquipmentId(eId);
	}

	@Override
	public void deleteEquipmentDataByEquipmentId(int eId) throws Exception {
		equipmentDataMapper.deleteEquipmentDataByEquipmentId(eId);
	}

	@Override
	public void addAllEquipment(List<Equipment> list) throws Exception {
		equipmentMapper.addAllEquipment(list);
	}

	@Override
	public List<Equipment> selectAllEquipment() throws Exception {
		return equipmentMapper.selectAllEquipment();
	}

}
