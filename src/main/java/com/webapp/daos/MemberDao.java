package com.webapp.daos;

import java.util.List;

import com.webapp.models.MemberModel;

public interface MemberDao {

	public List<MemberModel> fetchMembersList();

}
