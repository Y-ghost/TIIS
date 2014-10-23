package com.rest.tiis.service;

import java.util.List;
import java.util.Map;

import com.rest.tiis.beans.Equipment;

public interface EquipmentInfoService {

	List<Equipment> selectAllEquipment() throws Exception;
	
	void addEquipment(Equipment equipment) throws Exception;

	List<Equipment> selectEquipmentByProjectId(String projectId) throws Exception;

	Equipment selectEquipmentById(String equipmentId) throws Exception;

	void deleteEquipmentData(int pId) throws Exception;

	void deleteEquipmentStatus(int pId) throws Exception;

	void deleteEquipment(int pId) throws Exception;

	List<Equipment> selectAllEquipmentPages(Map<String,Object> map) throws Exception;

	List<Equipment> selectAllEquipmentPagesByUser(Map<String,Object> map) throws Exception;

	int selectAllEquipmentCounts(Map<String,Object> map) throws Exception;

	int selectAllEquipmentCountsByUser(Map<String,Object> map) throws Exception;

	void deleteEquipmentStatusByHostId(int hId) throws Exception;

	void deleteEquipmentDataByHostId(int hId) throws Exception;

	void deleteEquipmentByHostId(int hId) throws Exception;

	Equipment validOtherEquipmentNameAndCode(String id, String name, String code, int controlHostId) throws Exception;

	void modifyEquipment(Equipment equipment) throws Exception;

	void deleteEquipmentByEquipmentId(int eId) throws Exception;

	void deleteEquipmentStatusByEquipmentId(int eId) throws Exception;

	void deleteEquipmentDataByEquipmentId(int eId) throws Exception;

	void addAllEquipment(List<Equipment> list) throws Exception;
}
