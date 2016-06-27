package com.webapp.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fnf.utils.DateUtils;
import com.fnf.utils.UUIDGenerator;
import com.webapp.daos.SessionDao;

@Service("sessionService")
public class SessionService {

	@Autowired
	public SessionDao sessionDao;

	public String newSessionKey() {

		String sessionKey = "";

		sessionKey = UUIDGenerator.generateUUID();
		long gmtTimeStamp = DateUtils.nowAsGmtMillisec();

		sessionDao.newSession(sessionKey, gmtTimeStamp, gmtTimeStamp);

		return sessionKey;
	}

	public boolean sessionExistsForKey(String sessionKey) {

		boolean sessionExists = false;

		sessionExists = sessionDao.sessionExistsForKey(sessionKey);

		return sessionExists;
	}

	public long attributeExistsForSession(String sessionKey, String attribute) {

		long attributeId = -1;

		sessionDao.attributeExistsForSession(sessionKey, attribute);

		return attributeId;
	}

	public void insertAttributeForSession(String sessionKey, String attribute, String attributeValue) {

		sessionDao.insertAttributeForSession(sessionKey, attribute, attributeValue);

	}

	public void updateAttribute(long attributeId, String attributeValue) {

		sessionDao.updateAttribute(attributeId, attributeValue);

	}

	public String attributeValue(String sessionKey, String attribute) {

		String attributeValue = null;

		attributeValue = sessionDao.attributeValue(sessionKey, attribute);

		return attributeValue;
	}

	public boolean removeAttributeForSession(String sessionKey, String attribute) {

		boolean isRemoved = false;

		sessionDao.removeAttributeForSession(sessionKey, attribute);
		isRemoved = true;

		return isRemoved;
	}

	public void closeLoginTrail(Map<String, Object> map) {
		sessionDao.closeLoginTrail(map);

	}

	public void addLoginTrail(Map<String, Object> map) {

		sessionDao.addLoginTrail(map);
	}

	public long isLoggedIn(String userId) {

		return sessionDao.isLoggedIn(userId);

	}

	public String getUserIdByRememberMeKeyId(String rememberedLoginId) {
		String recordId = "";
		recordId = sessionDao.getUserIdByRememberMeKeyId(rememberedLoginId);
		return recordId;
	}

	public int addRememberMeKey(String apiSessionId, String userId, long createdAt) {

		return sessionDao.addRememberMeKey(apiSessionId, userId, createdAt);
	}

	public void deleteRememberMeKey(String userId) {
		sessionDao.deleteRememberMeKey(userId);
	}

	public void destroySession(String sessionId) {

		sessionDao.destroySession(sessionId);
	}

	public void destroySessionAttribute(String sessionId) {
		sessionDao.destroySessionAttribute(sessionId);

	}

	public void setSessionLastAccessedTimeStamp(String sessionId, long accessedAt) {
		sessionDao.setSessionLastAccessedTimeStamp(sessionId, accessedAt);
	}

}
