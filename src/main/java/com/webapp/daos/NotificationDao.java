package com.webapp.daos;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface NotificationDao {

	List<HashMap<String, Object>> getNotification();

	int addNotification(@Param("notificationId") String generateUUID,@Param("createdAt") long nowAsGmtMillisec,@Param("text") String text);



}