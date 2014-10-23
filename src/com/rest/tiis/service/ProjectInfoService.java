package com.rest.tiis.service;

import java.util.List;
import java.util.Map;

import com.rest.tiis.beans.Project;

public interface ProjectInfoService {
	public int addProject(Project project) throws Exception;

	public List<Project> selectAllProject() throws Exception;
	
	public Project selectProjectById(String projectId) throws Exception;

	public Project selectProjectByName(String pName) throws Exception;

	public List<Project> selectAllProjectByUser(Integer id) throws Exception;

	public int selectAllProjectCounts(Map<String,String> map) throws Exception ;

	public int selectAllProjectCountsByUser(Map<String,String> map) throws Exception ;

	public List<Project> selectAllProjectPages(Map<String,Object> map) throws Exception ;

	public List<Project> selectAllProjectPagesByUser(Map<String,Object> map) throws Exception ;

	public void deleteProjectById(int id) throws Exception;

	public Project selectProjectByIdAndName(String pId, String pName) throws Exception;

	public void modifyProject(Project project)  throws Exception;
}
