package com.webapp.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fnf.utils.JQTableUtils;
import com.webapp.dto.DripMarkettingDto;
import com.webapp.dto.UsersJsonDto;
import com.webapp.models1.UserReferralHistory;

public interface UsersDao {

	List<UsersJsonDto> fetchUsers(@Param("JQTableUtils") JQTableUtils tableUtils);

	long fetchTotalUsers(@Param("JQTableUtils") JQTableUtils tableUtils);

	UsersJsonDto fetchUserDetails(@Param("user_id") String user_id);

	int updateUsers(@Param("usersJsonDto") UsersJsonDto usersJsonDto);
	
	List<UserReferralHistory> getReferralHistoryOfUser(@Param("JQTableUtils") JQTableUtils tableUtils, @Param("userId") String userId);
	
	int updateUserWalletBalance(@Param("userId") String userId, @Param("referralBalance") int referralBalance, @Param("currentTime") long currentTime);

	List<UsersJsonDto> fetchAllUsers();

	List<DripMarkettingDto> getDripMarketDetails();
}
