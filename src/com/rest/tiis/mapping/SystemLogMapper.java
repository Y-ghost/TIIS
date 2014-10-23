package com.rest.tiis.mapping;

import java.util.List;
import java.util.Map;

import com.rest.tiis.beans.Project;
import com.rest.tiis.beans.SystemLog;

public interface SystemLogMapper {

    int insert(List<SystemLog> list) throws Exception;

	void deleteSystemLogByUserId(int userId) throws Exception;

	List<Project> selectAllMessagePages(Map<String, Object> map) throws Exception;

	List<Project> selectAllMessagePagesByUser(Map<String, Object> map) throws Exception;

	int selectAllMessageCounts(Map<String, String> map) throws Exception;

	int selectAllMessageCountsByUser(Map<String, String> map) throws Exception;

	SystemLog selectMessageById(int id) throws Exception;

	void markSystemLog(int parseInt) throws Exception;
}