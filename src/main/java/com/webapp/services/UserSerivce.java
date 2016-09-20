package com.webapp.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fnf.utils.EncryptionUtils;
import com.webapp.daos.UserDao;
import com.webapp.models.User;

@Service("userService")
public class UserSerivce {

	private static final Logger logger = Logger.getLogger(UserSerivce.class);

	@Autowired
	private UserDao userDao;
	

	public User getUserFromCredentials(String userName, String password) {

		User userModel = null;

		userModel = userDao.getUserAccountDetailsById(userName.toLowerCase());

		if (userModel != null && !EncryptionUtils.isValidPassword(password, userModel.getPassword()) ) {
			userModel = null;
			logger.debug("invalid passsword>>>>>>>>>>>>>>>");
		}

		return userModel;
	}
	
	

//	public AppUser getUserFromCredentialsForApi(String email, String password) {
//
//		AppUser userModel = null;
//
//		userModel = userDao.getUserAccountDetailsByEmailIdAndPhone(email);
//
//		if (userModel != null && !EncryptionUtils.isValidPassword(password, userModel.getPassword()) ) {
//			userModel = null;
//			logger.debug("invalid passsword>>>>>>>>>>>>>>>");
//			
//		}else{
//			userModel.setPassword("");
//		}
//
//		return userModel;
//	}

	public User getUserAccountDetailsById(String userId) {

		User userModel = null;

		userModel = userDao.getUserAccountDetailsById(userId);

		return userModel;
	}

	public User getUserAccountDetailsByEmailId(String email) {

		User userModel = null;

		userModel = userDao.getUserAccountDetailsByEmailId(email.toLowerCase());

		return userModel;
	}



	public String fetchPassword(String userId) {
		// TODO Auto-generated method stub
		return userDao.fetchPassword(userId);
	}



	public int updatePassword(User userModel) {
		return userDao.updatePassword(userModel);
		
	}

}