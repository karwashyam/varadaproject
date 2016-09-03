package com.webapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	



}