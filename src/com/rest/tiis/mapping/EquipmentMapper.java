package com.rest.tiis.mapping;

import java.util.List;
import java.util.Map;

import com.rest.tiis.beans.Equipment;

public interface EquipmentMapper {

	void addEquipment(Equipment equipment) throws Exception;

	List<Equipment> selectAllEquipment() throws Exception;
	
	List<Equipment> selectAllEquipmentByUser(Integer id) throws Exception;

	List<Equipment> selectEquipmentByProjectId(int projectId) throws Exception;

	Equipment selectEquipmentById(int equipmentId) throws Exception;

	void deleteEquipment(int pId) throws Exception;
	
	List<Equipment> selectAllEquipmentPages(Map<String,Object> map) throws Exception;

	List<Equipment> selectAllEquipmentPagesByUser(Map<String,Object> map) throws Exception;

	int selectAllEquipmentCounts(Map<String,Object> map) throws Exception;

	int selectAllEquipmentCountsByUser(Map<String,Object> map) throws Exception;

	void deleteEquipmentByHostId(int hId) throws Exception;

	Equipment validOtherEquipmentNameAndCode(Map<String,Object> map) throws Exception;

	void modifyEquipment(Equipment equipment) throws Exception;

	void deleteEquipmentByEquipmentId(int eId) throws Exception;

	void addAllEquipment(List<Equipment> list) throws Exception;

	Equipment selectEquipmentByhIdAndEcode(Map<String,Object> map) throws Exception;

}