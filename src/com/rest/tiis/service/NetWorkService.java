package com.rest.tiis.service;

import java.util.Date;

import com.rest.tiis.beans.DataTemp;

public interface NetWorkService {

	public void saveNetData(String address,String contralId,String data);
	
	public DataTemp getNetData(String address,String contralId,Date dateTime,Date startDate);
	
	public String waitData(String address,String ContralCode,Date startDate) throws Exception;
	
	public String waitDataForSearchEquipment(String address,String ContralCode,Date startDate) throws Exception;
	
	public void pushMsg(String hostCode) throws Exception;

	public DataTemp selectData(String address, String contralId) throws Exception;
}
