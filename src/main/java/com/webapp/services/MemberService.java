package com.webapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fnf.utils.DateUtils;
import com.fnf.utils.JQTableUtils;
import com.fnf.utils.UUIDGenerator;
import com.utils.constant.ProjectConstant;
import com.webapp.daos.MemberDao;
import com.webapp.models.MemberModel;

@Service("memberService")
public class MemberService {

//	private static final Logger logger = Logger.getLogger(StateSerivce.class);

	@Autowired
	private MemberDao memberDao;

	public List<MemberModel> fetchMembersList() {	
		return memberDao.fetchMembersList();
	}

	public boolean fetchMemberByEmail(MemberModel model) {
		return memberDao.fetchMemberByEmail(model);
	}

	public boolean fetchMemberByPhone(MemberModel model) {
		return memberDao.fetchMemberByPhone(model);
	}

	public List<MemberModel> fetchAllMemberList(JQTableUtils tableUtils) {
		return memberDao.fetchAllMemberList(tableUtils);
	}

	public long fetchTotalMemberCountList(JQTableUtils tableUtils) {
		return memberDao.fetchTotalMemberCountList(tableUtils);
	}

	public int postAddMember(MemberModel memberModel, String userId) {
		long time = DateUtils.nowAsGmtMillisec();
		memberModel.setMemberId(UUIDGenerator.generateUUID());
		memberModel.setCreatedBy(userId);
		memberModel.setUpdatedBy(userId);
		memberModel.setCreatedAt(time);
		memberModel.setUpdatedAt(time);
		memberModel.setRecordStatus(ProjectConstant.ACTIVE_RECORD_STATUS);
		return memberDao.postAddMember(memberModel);
	}

	public MemberModel fetchMemberDetail(String memberId) {
		return memberDao.fetchMemberDetail(memberId);
	}

	public boolean fetchMemberByPan(MemberModel model) {
		return memberDao.fetchMemberByPan(model);
	}

	public int editMember(MemberModel memberModel) {
		long time = DateUtils.nowAsGmtMillisec();
		memberModel.setUpdatedAt(time);
		return memberDao.editMember(memberModel);
	}

	public int changeMemberStatus(MemberModel memberModel) {
		long time = DateUtils.nowAsGmtMillisec();
		memberModel.setUpdatedAt(time);
		return memberDao.changeMemberStatus(memberModel);
	}
	
}