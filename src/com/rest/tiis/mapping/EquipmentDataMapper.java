package com.rest.tiis.mapping;

import java.util.List;
import java.util.Map;

import com.rest.tiis.beans.Equipment;
import com.rest.tiis.beans.EquipmentData;


public interface EquipmentDataMapper {

	void deleteEquipmentData(int pId) throws Exception;

	void deleteEquipmentDataByHostId(int hId) throws Exception;

	void deleteEquipmentDataByEquipmentId(int eId) throws Exception;

	void deleteAllEquipmentData(List<Equipment> list) throws Exception;

	EquipmentData selectEquipmentDataByeId(Integer id) throws Exception;

	void insert(List<EquipmentData> list) throws Exception;

	long getValueForYear(Map<String, Object> map) throws Exception;

	long getValueForMouth(Map<String, Object> map) throws Exception;

	long selectDataByEId(Integer id) throws Exception;
}