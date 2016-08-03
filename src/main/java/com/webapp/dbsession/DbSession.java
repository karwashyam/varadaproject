package com.webapp.dbsession;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fnf.utils.CookieUtils;
import com.fnf.utils.DateUtils;
import com.fnf.utils.UUIDGenerator;
import com.webapp.models1.User;
import com.webapp.services.SessionService;

public class DbSession {

	private static final Logger logger = Logger.getLogger(DbSession.class);

	public static final String USER_ID = "user_id";
	public static final String ROLE_ID = "role_id";
	public static final String TIME_ZONE = "timeZone";
	public static final String LONG_DATE_FORMAT = "longDateformat";
	public static final String SHORT_DATE_FORMAT = "shortDateformat";
	public static final String TIME_FORMAT = "timeFormat";
	public static final String DASHBOARD_URL = "dashboard_url";
	public static final String USER_FULL_NAME = "full_name";

	private String sessionKey;
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;

	private boolean valid;

	private DbSession(String sessionKey, HttpServletRequest request, HttpServletResponse response) {

		this.sessionKey = sessionKey;
		this.valid = true;
		this.servletRequest = request;
		this.servletResponse = response;

	}

	public boolean isValid() {
		return valid;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	private static String sessionKeyForCookie(Cookie cookie) {

		return cookie.getValue();
	}

	public static DbSession getSession(HttpServletRequest request, HttpServletResponse response, SessionService sessionService, String sessionCookieName, boolean create) {

		logger.debug("SessionCookieName>>>>>" + sessionCookieName);

		Cookie sessionCookie = CookieUtils.getCookie(sessionCookieName, request);

		if (sessionCookie != null) {
			if (CookieUtils.DELETED_COOKIE_VALUE.equals(sessionCookie.getValue())) {
				sessionCookie = null;
				logger.debug("sessionCookie was set to null");
			}

		} else {
			logger.debug("sessionCookie was null");
		}

		DbSession session = null;

		if (sessionCookie != null) {
			String sessionKey = sessionKeyForCookie(sessionCookie);
			boolean sessionExists = sessionService.sessionExistsForKey(sessionKey);
			if (sessionExists) {
				session = new DbSession(sessionKey, request, response);
				session.setLastAccessedAt(sessionService);
			} else {
				session = null;
			}
		}

		if (session == null && create) {
			String newSessionKey = sessionService.newSessionKey();

			Cookie cookie = new Cookie(sessionCookieName, "" + newSessionKey);
			String ctxPath = request.getContextPath();
			if (ctxPath.length() == 0) {
				ctxPath = "/";
			}
			cookie.setPath(ctxPath);
			CookieUtils.setCookie(cookie, request, response);
			session = new DbSession(newSessionKey, request, response);
			logger.info(" New Session Created: " + session.getSessionKey());
		}

		return session;

	}

	public static DbSession createSession(String userId, HttpServletRequest request, HttpServletResponse response, SessionService sessionService, String sessionCookieName) {

		DbSession session = DbSession.getSession(request, response, sessionService, sessionCookieName, true);
		addLoginTrail(userId, session.getSessionKey(), sessionService);
		session.setAttribute(USER_ID, userId, sessionService);
		logger.info("Session ID is " + session.getSessionKey());
		return session;
	}

	public static String processPostLogin(User userModel, DbSession session, SessionService sessionService, String defaultDashBoardURL) {

		String url = null;
		url = defaultDashBoardURL;
//		session.setAttribute(DASHBOARD_URL, url, sessionService);
//		session.setAttribute(USER_FULL_NAME, userModel.getUserName(), sessionService);
		/*List<UserRole> roles = userModel.getUserRoles();

		if (roles != null && !roles.isEmpty()) {

			session.setAttribute(ROLE_ID, roles.get(0).getRoleId(), sessionService);
			url = defaultDashBoardURL;
			session.setAttribute(DASHBOARD_URL, url, sessionService);
		}*/
		return url;
	}

	private void checkValidity() {
		if (!valid) {
			throw new IllegalStateException("Session is invalid");
		}
	}

	private void setLastAccessedAt(SessionService sessionService) {

		sessionService.setSessionLastAccessedTimeStamp(sessionKey, DateUtils.nowAsGmtMillisec());

	}

	private long attributeExists(String attribute, SessionService sessionService) {

		long attributeId = -1;

		sessionService.attributeExistsForSession(sessionKey, attribute);

		return attributeId;
	}

	private void insertAttribute(String attribute, String attributeValue, SessionService sessionService) {

		sessionService.insertAttributeForSession(sessionKey, attribute, attributeValue);

	}

	private void updateAttribute(long attributeId, String attributeValue, SessionService sessionService) {

		sessionService.updateAttribute(attributeId, attributeValue);

	}

	public void setAttribute(String attribute, String attributeValue, SessionService sessionService) {

		checkValidity();

		long attributeId = attributeExists(attribute, sessionService);

		if (attributeId == -1) {
			insertAttribute(attribute, attributeValue, sessionService);
		} else {
			updateAttribute(attributeId, attributeValue, sessionService);
		}
	}

	public String getAttribute(String attribute, SessionService sessionService) {

		checkValidity();

		String attributeValue = null;

		attributeValue = sessionService.attributeValue(sessionKey, attribute);

		return attributeValue;
	}

	public boolean removeAttribute(String attribute, SessionService sessionService) {
		checkValidity();

		boolean isRemoved = false;

		sessionService.removeAttributeForSession(sessionKey, attribute);
		isRemoved = true;

		return isRemoved;
	}

	private void deleteSessionCookie(String sessionCookieName) {

		Cookie sessionCookie = CookieUtils.getCookie(sessionCookieName, servletRequest);

		if (sessionCookie != null) {
			CookieUtils.deleteCookie(sessionCookie, servletRequest, servletResponse);
		}
	}

	public void invalidate(SessionService sessionService, String sessionCookieName) {
		checkValidity();

		sessionService.destroySessionAttribute(sessionKey);
		sessionService.destroySession(sessionKey);

		valid = false;
		deleteSessionCookie(sessionCookieName);

	}

	public static boolean isValidLogin(DbSession session, SessionService sessionService) {

		if (session == null) {
			return false;
		} else {
			return isLoggedIn(session.getAttribute(USER_ID, sessionService), sessionService);
		}
	}

	public void destoryCurrentSession(HttpServletRequest request, HttpServletResponse response, SessionService sessionService, String sessionCookieName) {

		DbSession session = DbSession.getSession(request, response, sessionService, sessionCookieName, false);
		String userId = session.getAttribute(USER_ID, sessionService);
		if (userId != null) {
			closeLoginTrail(userId, session.getSessionKey(), sessionService);
		}
		session.invalidate(sessionService, sessionCookieName);
	}

	private static void addLoginTrail(String userId, String sessionId, SessionService sessionService) {

		Map<String, Object> map = new HashMap<String, Object>();
		String loginTrailId = UUIDGenerator.generateUUID();
		map.put("loginTrailId", loginTrailId);
		map.put("userId", userId);
		map.put("loggedInAt", DateUtils.nowAsGmtMillisec());
		map.put("loggedOutAt",Integer.toString(Types.TIMESTAMP));
		map.put("sessionId", sessionId);

		sessionService.addLoginTrail(map);

	}

	private void closeLoginTrail(String actorId, String sessionId, SessionService sessionService) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loggedOutAt", DateUtils.nowAsGmtMillisec());
		map.put("userId", actorId);
		map.put("sessionId", sessionId);

		sessionService.closeLoginTrail(map);

	}

	public static boolean isLoggedIn(String userId, SessionService sessionService) {

		boolean loggedIn = false;

		long loginTrailIdCount = sessionService.isLoggedIn(userId);
		if (loginTrailIdCount > 0) {
			loggedIn = true;
		}

		return loggedIn;
	}

	public static String addRememberMeKey(String userId, SessionService sessionService) {
		String recordId = UUIDGenerator.generateUUID();
		recordId = sessionService.addRememberMeKey(recordId, userId, DateUtils.nowAsGmtMillisec()) > 0 ? recordId : null;

		return recordId;
	}

	public static void deleteRememberMeKey(String userId, SessionService sessionService) {

		sessionService.deleteRememberMeKey(userId);

	}

	public static String getUserIdByRememberMeId(String keyId, SessionService sessionService) {

		String actorId = "";

		actorId = sessionService.getUserIdByRememberMeKeyId(keyId);

		return actorId;
	}

}