package com.rest.tiis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.tiis.beans.User;
import com.rest.tiis.mapping.UserMapper;
import com.rest.tiis.service.UserService;

/**
 * @project:					TIIS 
 * @Title: 						UserServiceImpl.java 		
 * @Package 					com.rest.tiis.service.impl		
 * @Description: 				用户信息业务层 
 * @author 						杨贵松   
 * @date 						2014年3月24日 下午6:05:05 
 * @version 					V1.0
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 用户登录
	 */
	public User login(String userName) throws Exception{
		User user = userMapper.selectByUserName(userName);
		return user;
	}

	/**
	 * 用户名校验
	 */
	@Override
	public User validUserName(String userName) throws Exception{
		User user = userMapper.selectByUserName(userName);
		return user;
	}

	/**
	 * 用户注册
	 */
	@Override
	public void register(User user) throws Exception{
		userMapper.register(user);
	}

	@Override
	public int selectAllUserCounts(String userID) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userID", userID);
		return userMapper.selectAllUserCounts(map);
	}

	@Override
	public List<User> selectAllUserPages(int index, int pageSize,String userID) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("pageSize", pageSize);
		if(userID.equals("0000")){
			map.put("userID", "");
		}else{
			map.put("userID", userID);
		}
		return userMapper.selectAllUserPages(map);
	}

	@Override
	public List<User> selectAllUser() throws Exception {
		return userMapper.selectAllUser();
	}

	@Override
	public User selectUserById(Integer id) throws Exception {
		return userMapper.selectUserById(id);
	}

	@Override
	public void update(User user) throws Exception {
		userMapper.update(user);
	}

	@Override
	public void deleteUserById(int id) throws Exception {
		userMapper.deleteUserById(id);
	}

	@Override
	public void updateClientID(User userInfo) throws Exception {
		userMapper.updateClientID(userInfo);
	}

	@Override
	public User selectUserByLoginName(String loginname) throws Exception {
		return userMapper.selectByUserName(loginname);
	}

	@Override
	public List<User> selectUserByHostCode(String hostCode) throws Exception {
		return userMapper.selectUserByHostCode(hostCode);
	}
}
