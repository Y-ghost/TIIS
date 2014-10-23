package com.rest.tiis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.tiis.beans.Project;
import com.rest.tiis.mapping.ProjectMapper;
import com.rest.tiis.service.ProjectInfoService;

@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {
	@Autowired
	private ProjectMapper projectMapper;

	@Override
	public int addProject(Project project)  throws Exception{
		return projectMapper.insert(project);
	}

	@Override
	public List<Project> selectAllProject()  throws Exception{
		return projectMapper.selectAllProject();
	}

	@Override
	public Project selectProjectById(String projectId)  throws Exception{
		Project project = projectMapper.selectByPrimaryKey(Integer.parseInt(projectId));
		return project;
	}

	@Override
	public Project selectProjectByName(String pName) throws Exception {
		return projectMapper.selectProjectByName(pName);
	}

	@Override
	public List<Project> selectAllProjectByUser(Integer id) throws Exception {
		return projectMapper.selectAllProjectByUser(id);
	}

	@Override
	public int selectAllProjectCounts(Map<String,String> map)  throws Exception {
		return projectMapper.selectAllProjectCounts(map);
	}

	@Override
	public int selectAllProjectCountsByUser(Map<String,String> map)  throws Exception {
		return projectMapper.selectAllProjectCountsByUser(map);
	}

	@Override
	public List<Project> selectAllProjectPages(Map<String,Object> map)  throws Exception {
		return projectMapper.selectAllProjectPages(map);
	}

	@Override
	public List<Project> selectAllProjectPagesByUser(Map<String,Object> map)  throws Exception {
		return projectMapper.selectAllProjectPagesByUser(map);
	}

	@Override
	public void deleteProjectById(int id)  throws Exception{
		projectMapper.deleteProjectById(id);
	}

	@Override
	public Project selectProjectByIdAndName(String pId, String pName)  throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", Integer.parseInt(pId));
		map.put("pName", pName);
		return projectMapper.selectProjectByIdAndName(map);
	}

	@Override
	public void modifyProject(Project project)  throws Exception {
		projectMapper.modifyProject(project);
	}
}
