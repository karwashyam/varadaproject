package com.webapp.daos;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SessionDao {

	public int newSession(@Param("sessionId") String sessionId, @Param("createdAt") long createdAt, @Param("accessedAt") long accessedAt);

	public void setSessionLastAccessedTimeStamp(@Param("sessionId") String sessionId, @Param("accessedAt") long accessedAt);

	public boolean sessionExistsForKey(@Param("sessionId") String sessionId);

	public String attributeExistsForSession(@Param("sessionId") String sessionId, @Param("attribute") String attribute);

	public String attributeValue(@Param("sessionId") String sessionId, @Param("attribute") String attribute);

	public void updateAttribute(@Param("attributeId") long attributeId, @Param("attributeValue") String attributeValue);

	public void insertAttributeForSession(@Param("sessionId") String sessionId, @Param("attribute") String attribute, @Param("attributeValue") String attributeValue);

	public boolean removeAttributeForSession(@Param("sessionId") String sessionId, @Param("attribute") String attribute);

	public void destroySession(@Param("sessionId") String sessionId);

	public void destroySessionAttribute(@Param("sessionId") String sessionId);

	// remembered_login_sessions //

	public String getUserIdByRememberMeKeyId(@Param("rememberedLoginId") String rememberedLoginId);

	public int addRememberMeKey(@Param("rememberedLoginId") String apiSessionId, @Param("userId") String userId, @Param("createdAt") long createdAt);

	public void deleteRememberMeKey(@Param("userId") String userId);

	// login_trails //

	public long isLoggedIn(@Param("userId") String userId);

	public void closeLoginTrail(Map<String, Object> map);

	public void addLoginTrail(Map<String, Object> map);

}
