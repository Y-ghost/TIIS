package com.rest.tiis.mapping;

import java.util.List;

import com.rest.tiis.beans.UserProjectRel;

public interface UserProjectRelMapper {

    int insert(UserProjectRel record) throws Exception;

	void deleteUserProjectR(int pId) throws Exception;

	List<UserProjectRel> selectRelBypId(Integer id) throws Exception;
}