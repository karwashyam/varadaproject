package com.webapp.services;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fnf.utils.DateUtils;
import com.webapp.daos.AppUserDao;
import com.webapp.daos.UserDao;
import com.webapp.models1.AppUser;

@Service("appUserService")
public class AppUserService {


	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AppUserDao appUserDao;

	


	public int updatePassword(String userId, String password) {
		return appUserDao.updatePassword(userId,password);
	}




	public HashMap<String, Object> getProfiles(Long updatedAt, String userId) {
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		AppUser appUser=appUserDao.getProfiles(userId,updatedAt);
		if(appUser==null||appUser.getUserId()==null){
			appUser = new AppUser();
		}
		outputMap.put("appUser", appUser);
		return outputMap;
	}




	public int updateProfile(AppUser appUser, String userId) {
		appUser.setUpdatedAt(DateUtils.nowAsGmtMillisec());
		appUser.setUpdatedBy(userId);
		int status=appUserDao.updateProfile(appUser);
		return status;
	}

}