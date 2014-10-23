package com.rest.tiis.mapping;

import java.util.List;

import com.rest.tiis.beans.Equipment;



public interface EquipmentStatusMapper {

	void deleteEquipmentStatus(int pId) throws Exception;

	void deleteEquipmentStatusByHostId(int hId) throws Exception;

	void deleteEquipmentStatusByEquipmentId(int eId) throws Exception;

	void deleteAllEquipmentStatus(List<Equipment> list) throws Exception;
}