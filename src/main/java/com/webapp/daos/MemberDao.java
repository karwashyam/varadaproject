package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.webapp.models.MemberModel;

public interface MemberDao {

	public List<MemberModel> fetchMembersList();
	
	public MemberModel fetchMemberById(@Param("memberId") String memberId);

}
