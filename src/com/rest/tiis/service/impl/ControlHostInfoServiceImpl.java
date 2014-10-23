package com.rest.tiis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.tiis.beans.ControlHost;
import com.rest.tiis.mapping.ControlHostMapper;
import com.rest.tiis.service.ControlHostInfoService;

@Service
public class ControlHostInfoServiceImpl implements ControlHostInfoService {
	@Autowired
	private ControlHostMapper controlHostMapper;

	@Override
	public ControlHost selectByProjectId(String projectId) throws Exception {
		ControlHost chost = controlHostMapper.selectByProjectId(Integer.parseInt(projectId));
		return chost;
	}

	@Override
	public ControlHost selectById(Integer controlhostid) throws Exception {
		ControlHost controlHost = controlHostMapper.selectById(controlhostid);
		return controlHost;
	}

	@Override
	public void deleteControlHost(int pId)  throws Exception{
		controlHostMapper.deleteControlHost(pId);
	}

	@Override
	public void addHost(ControlHost host) throws Exception {
		controlHostMapper.addHost(host);
	}

	@Override
	public ControlHost selectHostByCode(String hostCode) throws Exception {
		return controlHostMapper.selectHostByCode(hostCode);
	}

	@Override
	public int selectAllHostCounts(Map<String,Object> map)  throws Exception {
		return controlHostMapper.selectAllHostCounts(map);
	}

	@Override
	public int selectAllHostCountsByUser(Map<String,Object> map)  throws Exception {
		return controlHostMapper.selectAllHostCountsByUser(map);
	}

	@Override
	public List<ControlHost> selectAllHostPages(Map<String,Object> map)  throws Exception{
		return controlHostMapper.selectAllHostPages(map);
	}

	@Override
	public List<ControlHost> selectAllHostPagesByUser(Map<String,Object> map)  throws Exception{
		return controlHostMapper.selectAllHostPagesByUser(map) ;
	}

	@Override
	public ControlHost selectHostById(String hId) throws Exception {
		return controlHostMapper.selectById(Integer.parseInt(hId));
	}

	@Override
	public ControlHost selectHostByIdAndCode(String hostCode) throws Exception {
		String[] str = hostCode.split(",");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", Integer.parseInt(str[0]));
		map.put("code", str[1]);
		return controlHostMapper.selectHostByIdAndCode(map);
	}

	@Override
	public void modifyHost(ControlHost host) throws Exception {
		controlHostMapper.modifyHost(host);
	}

	@Override
	public void deleteHostById(int hId) throws Exception {
		controlHostMapper.deleteHostById(hId);
	}
}
