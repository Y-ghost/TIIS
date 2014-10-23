package com.rest.tiis.mapping;

import java.util.List;
import java.util.Map;

import com.rest.tiis.beans.Project;

public interface ProjectMapper {
    int insert(Project record) throws Exception;

	List<Project> selectAllProject() throws Exception;

	Project selectByPrimaryKey(int projectId) throws Exception;

	Project selectProjectByName(String pName) throws Exception;

	List<Project> selectAllProjectByUser(Integer id) throws Exception;

	int selectAllProjectCounts(Map<String,String> map) throws Exception;

	int selectAllProjectCountsByUser(Map<String,String> map) throws Exception;

	List<Project> selectAllProjectPages(Map<String, Object> map) throws Exception;

	List<Project> selectAllProjectPagesByUser(Map<String, Object> map) throws Exception;

	void deleteProjectById(int id) throws Exception;

	Project selectProjectByIdAndName(Map<String, Object> map)  throws Exception;

	void modifyProject(Project project) throws Exception;
}