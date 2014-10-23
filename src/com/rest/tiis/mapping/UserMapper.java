package com.rest.tiis.mapping;

import java.util.List;
import java.util.Map;

import com.rest.tiis.beans.User;

public interface UserMapper {

    User selectByPrimaryKey(Integer id) throws Exception;

	User selectByUserName(String userName) throws Exception;

	void register(User user) throws Exception;

	int selectAllUserCounts(Map<String, Object> map) throws Exception;

	List<User> selectAllUserPages(Map<String, Object> map) throws Exception;

	List<User> selectAllUser() throws Exception;

	User selectUserById(Integer id) throws Exception;

	void update(User user) throws Exception;

	void deleteUserById(int id) throws Exception;

	void updateClientID(User user) throws Exception;

	List<User> selectUserByHostCode(String hostCode) throws Exception;
}