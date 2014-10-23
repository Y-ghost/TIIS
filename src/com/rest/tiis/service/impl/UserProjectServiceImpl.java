package com.rest.tiis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.tiis.beans.UserProjectRel;
import com.rest.tiis.mapping.UserProjectRelMapper;
import com.rest.tiis.service.UserProjectService;

@Service
public class UserProjectServiceImpl implements UserProjectService {
	@Autowired
	private UserProjectRelMapper userProjectRelMapper;
	
	@Override
	public void addUserProjectR(UserProjectRel upr)  throws Exception{
		userProjectRelMapper.insert(upr);
	}

	@Override
	public void deleteUserProjectR(int pId)  throws Exception{
		userProjectRelMapper.deleteUserProjectR(pId);
	}
	
}
