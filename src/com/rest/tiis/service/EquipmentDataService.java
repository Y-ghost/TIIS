package com.rest.tiis.service;

import java.util.Map;

import com.rest.tiis.beans.User;

public interface EquipmentDataService {

	Map<String, Double> getValueForYear(User user, String pId, String circleDate) throws Exception;

	Map<String, Double> getValueForMouth(User user, String pId, String circleDate) throws Exception;

	long selectDataByEId(Integer id) throws Exception;
	
}
