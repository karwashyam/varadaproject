package com.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fnf.utils.DateUtils;
import com.fnf.utils.UUIDGenerator;
import com.webapp.daos.LoginDao;

@Service("apiLoginService")
public class ApiLoginService {

	@Autowired
	public LoginDao loginDao;

	public String createApiSessionKey(String userId, String deviceUid) {

		String apiSessionKey = null;
		apiSessionKey = UUIDGenerator.generateUUID();
		loginDao.deleteApiSessionsByDeviceUid(deviceUid);
		loginDao.insertApiSessionKey(apiSessionKey, userId, deviceUid, DateUtils.nowAsGmtMillisec());
		return apiSessionKey;
	}
	
	/*public String createApiSessionKeyForDeviceType(String userId, String deviceUid, String deviceType) {

		String apiSessionKey = null;
		apiSessionKey = UUIDGenerator.generateUUID();
		loginDao.deleteApiSessionsByDeviceUid(deviceUid);
		if(deviceType==null||"".equalsIgnoreCase(deviceType)){
			deviceType=ProjectConstant.IPAD_DEVICE_TYPE;
		}
		loginDao.deleteApiSessionsByDeviceType(userId,deviceType);
		loginDao.insertApiSessionKeyForDeviceType(apiSessionKey, userId, deviceUid, DateUtils.nowAsGmtMillisec(),deviceType);
		return apiSessionKey;
	}*/

	public String getUserIdBySessionKey(String sessionKey) {

		String userId = null;
		userId = loginDao.isSessionExists(sessionKey);
		return userId;
	}

	public String getSessionKey(String userId) {

		String sessionKey = null;
		sessionKey = loginDao.getSessionKey(userId);
		return sessionKey;
	}

	public int deleteApiSessionKey(String userId) {

		int deleteStatus = -1;
		deleteStatus = loginDao.deleteApiSessionKey(userId);
		return deleteStatus;
	}

}