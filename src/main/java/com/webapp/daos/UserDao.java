package com.webapp.daos;

import java.util.Map;

import com.webapp.models1.User;

public interface UserDao {


	User getUserAccountDetailsByEmailId(String emailId);

	User getUserAccountDetailsById(String userId);

	int updatePassword(User userModel);

	boolean isEmailIdExists(String emailId);

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

	String checkLoginCredentials(User userModel);

}