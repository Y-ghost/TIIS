package com.rest.tiis.service;

import com.rest.tiis.beans.UserProjectRel;

public interface UserProjectService {

	void addUserProjectR(UserProjectRel upr) throws Exception;

	void deleteUserProjectR(int pId) throws Exception;
	
}
