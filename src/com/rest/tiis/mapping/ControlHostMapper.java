package com.rest.tiis.mapping;

import java.util.List;
import java.util.Map;

import com.rest.tiis.beans.ControlHost;

public interface ControlHostMapper {

	ControlHost selectByProjectId(int projectId) throws Exception;

	ControlHost selectById(Integer controlhostid) throws Exception;

	void addHost(ControlHost host) throws Exception;

	ControlHost selectHostByCode(String hostCode) throws Exception;

	void deleteControlHost(int pId) throws Exception;

	int selectAllHostCounts(Map<String,Object> map) throws Exception ;

	int selectAllHostCountsByUser(Map<String,Object> map) throws Exception ;

	List<ControlHost> selectAllHostPages(Map<String, Object> map) throws Exception;

	List<ControlHost> selectAllHostPagesByUser(Map<String, Object> map) throws Exception;

	ControlHost selectHostByIdAndCode(Map<String, Object> map) throws Exception;

	void modifyHost(ControlHost host) throws Exception;

	void deleteHostById(int hId) throws Exception;

}