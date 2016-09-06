package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.models.FranchiseModel;
import com.webapp.models.MemberModel;

public interface MemberDao {

	public List<MemberModel> fetchMembersList();
	
	public MemberModel fetchMemberById(@Param("memberId") String memberId);

	public List<MemberModel> fetchTotalMemberList(@Param("JQTableUtils")JQTableUtils tableUtils);

	public List<MemberModel> fetchAllMemberList(@Param("JQTableUtils")JQTableUtils tableUtils);

	public boolean fetchMemberByEmail(MemberModel model);

	public boolean fetchMemberByPhone(MemberModel model);

	public long fetchTotalMemberCountList(@Param("JQTableUtils")JQTableUtils tableUtils);

	public boolean fetchMemberByPan(MemberModel model);

	public int postAddMember(MemberModel memberModel);

	public MemberModel fetchMemberDetail(String memberId);

	public int editMember(MemberModel memberModel);

	public int changeMemberStatus(MemberModel memberModel);

}
