package com.webapp.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnf.utils.DateUtils;
import com.fnf.utils.JQTableUtils;
import com.fnf.utils.UUIDGenerator;
import com.webapp.daos.FnFDao;
import com.webapp.daos.UsersDao;
import com.webapp.dto.UsersJsonDto;
import com.webapp.models1.UserReferralHistory;

@Service("usersService")
public class UsersService {

	protected static Logger logger = Logger.getLogger(UsersService.class);

	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private FnFDao fnFDao;

	@Transactional
	public List<UsersJsonDto> fetchUsers(JQTableUtils tableUtils, String userId) {

		return usersDao.fetchUsers(tableUtils);
	}
	
	public List<UsersJsonDto> fetchAllUsers() {
		return usersDao.fetchAllUsers();
	}

	public long fetchTotalUsers(JQTableUtils tableUtils, String userId) {
		return usersDao.fetchTotalUsers(tableUtils);
	}

	public UsersJsonDto fetchUserDetails(String user_id) {
		return usersDao.fetchUserDetails(user_id);
	}

	public int updateUsers(UsersJsonDto usersJsonDto) {
		return usersDao.updateUsers(usersJsonDto);
	}

	public List<UserReferralHistory> getReferralHistoryOfUser(JQTableUtils tableUtils, String userId) {
		return usersDao.getReferralHistoryOfUser(tableUtils, userId);
	}

	@Transactional
	public int addWalletBalanceOfUser(UserReferralHistory userReferralHistoryModel) {
		
		long currentTime = DateUtils.nowAsGmtMillisec();
		
		int addStatus = usersDao.updateUserWalletBalance(userReferralHistoryModel.getUserId(), userReferralHistoryModel.getAmount(), currentTime);
		
		String referralId = UUIDGenerator.generateUUID();
		
		addStatus = fnFDao.addReferralHistory(referralId, userReferralHistoryModel.getUserId(), null, null, userReferralHistoryModel.getAmount(), userReferralHistoryModel.getHistoryNote(), currentTime);
		
		return addStatus;
	}

	
}