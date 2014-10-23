package com.rest.tiis.service;

import java.util.List;
import java.util.Map;

import com.rest.tiis.beans.ControlHost;

public interface ControlHostInfoService {
	public ControlHost selectByProjectId(String projectId) throws Exception;

	public ControlHost selectById(Integer controlhostid) throws Exception;

	public void deleteControlHost(int pId) throws Exception;
	
	void addHost(ControlHost host) throws Exception;

	ControlHost selectHostByCode(String hostCode) throws Exception;

	int selectAllHostCounts(Map<String,Object> map) throws Exception;

	int selectAllHostCountsByUser(Map<String,Object> map) throws Exception ;

	List<ControlHost> selectAllHostPages(Map<String,Object> map) throws Exception;

	List<ControlHost> selectAllHostPagesByUser(Map<String,Object> map) throws Exception;

	public ControlHost selectHostById(String hId) throws Exception ;

	public ControlHost selectHostByIdAndCode(String hostCode) throws Exception;

	public void modifyHost(ControlHost host) throws Exception;

	public void deleteHostById(int hId) throws Exception;
}
