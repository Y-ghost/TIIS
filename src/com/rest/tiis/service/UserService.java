package com.rest.tiis.service;

import java.util.List;

import com.rest.tiis.beans.User;

public interface UserService {

	User login(String userName) throws Exception;

	User validUserName(String userName) throws Exception;

	void register(User user) throws Exception;

	int selectAllUserCounts(String userID) throws Exception;

	List<User> selectAllUserPages(int index, int pageSize,String userID) throws Exception;

	List<User> selectAllUser() throws Exception;

	User selectUserById(Integer id) throws Exception;

	void update(User user) throws Exception;

	void deleteUserById(int id) throws Exception;

	void updateClientID(User userInfo) throws Exception;

	User selectUserByLoginName(String loginname) throws Exception;

	List<User> selectUserByHostCode(String hostCode) throws Exception;

}
