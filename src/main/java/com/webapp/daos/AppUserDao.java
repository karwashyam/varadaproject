package com.webapp.daos;

import org.apache.ibatis.annotations.Param;

import com.webapp.models1.AppUser;
import com.webapp.models1.SignupModel;

public interface AppUserDao {


	AppUser getUserAccountDetailsByEmailId(@Param("email") String emailId);
	
	boolean isEmailIdExists(@Param("email") String email);
	
	boolean isEmailIdExistsForOtherUser(@Param("email") String email,@Param("userId") String userId);

	int addUser(SignupModel signupModel);

	int setNewPassword(AppUser appUser);

	boolean isPhoneExists(@Param("phone") String phone);

	int setNewPasswordByPhone(AppUser userModel);

	String fetchPassword(@Param("userId") String userId);

	int updatePassword(@Param("userId") String userId,@Param("password") String password);

	AppUser getUserAccountDetailsById(@Param("userId") String userId);
	
	AppUser getUserAccountDetailsByPhone(@Param("phone") String phone);

	AppUser getUserAccountDetailsByEmailIdAndPhone( @Param("email") String email);

	AppUser getProfiles(@Param("userId") String userId,@Param("updatedAt") Long updatedAt);

	int updateProfile(AppUser appUser);

	boolean isPhoneExistsForOtherUser(@Param("email") String email,@Param("userId") String userId);

	/*User getUserAccountDetailsById(String userId);

	int updatePassword(User userModel);

	String getUserIdFromTwitterId(String twitterId);

	String getUserIdFromGoogleId(String googleId);

	int addUser(User userModel);

	boolean deleteUserById(User userModel);

	User getUserModelById(String userId);

	boolean editUser(User userModel);

	public User getUserModel(Map<String, String> map);

	public int updateGoogleIdIfNotExists(Map<String, Object> map);

	public int updateFacebookIdIfNotExists(Map<String, Object> map);

	User getUserAccountDetailsByUsername(String username);

	String checkLoginCredentials(User userModel);*/

}