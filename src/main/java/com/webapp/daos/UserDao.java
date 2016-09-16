package com.webapp.daos;

import java.util.Map;

import com.webapp.models.User;

public interface UserDao {


	User getUserAccountDetailsByEmailId(String emailId);

	User getUserAccountDetailsById(String userId);

	int updatePassword(User userModel);

	boolean isEmailIdExists(String emailId);

	int addUser(User userModel);

	boolean deleteUserById(User userModel);

	User getUserModelById(String userId);

	boolean editUser(User userModel);

	public User getUserModel(Map<String, String> map);

	User getUserAccountDetailsByUsername(String username);

	String checkLoginCredentials(User userModel);

	String fetchPassword(String userId);

}