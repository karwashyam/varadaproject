package com.webapp.daos;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.webapp.models1.User;

public interface LoginDao {

	public String isValidLoginCredentials(Map<String, String> map);

	public int deleteApiSessionKey(String userId);

	public int deleteApiSessionsByDeviceUid(String deviceUid);

	void insertApiSessionKey(@Param("apiSessionId") String apiSessionId, @Param("userId") String userId, @Param("deviceUid") String deviceUid, @Param("createdAt") long createdAt);

	String isSessionExists(String apiSessionId);

	String getSessionKey(String userId);

	public User fetchUserBySessionKey(String sessionKey);
}